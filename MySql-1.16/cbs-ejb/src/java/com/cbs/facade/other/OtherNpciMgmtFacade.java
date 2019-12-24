/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.constant.CbsConstant;
import com.cbs.dao.master.AbbParameterInfoDAO;
import com.cbs.dao.master.ParameterinfoReportDAO;
import com.cbs.dto.NpciFileDto;
import com.cbs.dto.Npcih2hfilePojo;
import com.cbs.dto.other.CbsMandateDetailPojo;
import com.cbs.dto.other.MandateRecordTo;
import com.cbs.dto.other.NpciOacDto;
import com.cbs.dto.sms.MbSmsSenderBankDetailTO;
import com.cbs.dto.sms.SmsToBatchTo;
import com.cbs.dto.sms.TransferSmsRequestTo;
import com.cbs.entity.ho.investment.ParameterinfoReport;
import com.cbs.entity.master.AbbParameterInfo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.npci.h2h.H2HNpciMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.pojo.NpciInputPojo;
import com.cbs.pojo.OnlineAadharRegistrationPojo;
import com.cbs.pojo.mms.pain009.Document;
import com.cbs.pojo.mms.pain009.GenericOrganisationIdentification1;
import com.cbs.pojo.mms.pain009.GenericPersonIdentification1;
import com.cbs.pojo.mms.pain009.GroupHeader31;
import com.cbs.pojo.mms.pain009.MandateInformation2;
import com.cbs.pojo.mms.pain009.MandateInitiationRequestV01;
import com.cbs.pojo.mms.pain010.MandateAmendmentRequestV01;
import com.cbs.pojo.mms.pain011.MandateCancellation1;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ParseFileUtil;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

@Stateless(mappedName = "OtherNpciMgmtFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class OtherNpciMgmtFacade implements OtherNpciMgmtFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsRemote;
    @EJB
    private CommonReportMethodsRemote commonReport;
    @EJB
    private InterBranchTxnFacadeRemote ibtRemote;
    @EJB
    private SmsManagementFacadeRemote smsFacade;
//    @EJB
//    private TxnAuthorizationManagementFacadeRemote txnAuth;
    @EJB
    private NpciMgmtFacadeRemote npciRemote;
    @EJB
    private H2HNpciMgmtFacadeRemote h2hNpciRemote;
    @EJB
    private NpciMandateFacadeRemote npcimandateFacade;
//    @EJB
//    private FtsPostingMgmtFacadeRemote ftsremote;
    private Properties props = null;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdOne = new SimpleDateFormat("ddMMyyyy");
    SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    SimpleDateFormat ymdSql = new SimpleDateFormat("yyyy-MM-dd");
    NumberFormat formatter = new DecimalFormat("#.##");

    @PostConstruct
    private void loadWebServicesConfig() {
        try {
            props = new Properties();
            props.load(new FileReader("/opt/conf/wslocation.properties"));
        } catch (Exception ex) {
            System.out.println("Problem In Bean Initialization And Loading The "
                    + "WSLOCATION Properties File In NpciMgmtFacade" + ex.getMessage());
        }
    }

    @Override
    public List findAllFileSeqNo(String fileComingDt, String iwType) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select distinct file_seq_no from cbs_npci_oac_detail "
                    + "where file_coming_dt='" + fileComingDt + "' and iw_type='" + iwType + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no Seq No on this date.");
            }
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public List findH2HAllFileSeqNo(String fileComingDt, String iwType) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select distinct file_seq_no from cbs_npci_oac_detail "
                    + "where file_coming_dt='" + fileComingDt + "' and iw_type='" + iwType + "' and file_gen_flag='N'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no Seq No on this date.");
            }
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<NpciOacDto> getDataForInComingDateAndSeqNo(String fileComingDt, String seqNo,
            String iwType, String brnCode, String selectedBranch) throws ApplicationException {
        List<NpciOacDto> dataList = new ArrayList<NpciOacDto>();
//        String branchMicr = "";
        try {
            List list;
            if (iwType.equalsIgnoreCase("ECS-DR")) {
                String alphaCode = commonReport.getAlphacodeByBrncode(selectedBranch);
                if (alphaCode.equalsIgnoreCase("HO")) {
                    list = em.createNativeQuery("select header_identifier,originator_code,responder_code,"
                            + "date_format(file_coming_dt,'%d/%m/%Y'),file_ref_no,file_seq_no,record_identifier,"
                            + "rrn,micr,ac_type,old_acno,old_acname,user_number,user_name,tran_ref,ac_val_flag,"
                            + "cbs_acno,cbs_name,return_code,ifnull(amount,0),ifnull(ach_item_seq_no,''),'UMRN' from "
                            + "cbs_npci_oac_detail where file_coming_dt='" + fileComingDt + "' and "
                            + "file_seq_no='" + seqNo + "' and iw_type='" + iwType + "'").getResultList();
                } else {
                    String[] arr = getIfscAndMicrCodeByBrnCode(selectedBranch);
                    list = em.createNativeQuery("select header_identifier,originator_code,responder_code,"
                            + "date_format(file_coming_dt,'%d/%m/%Y'),file_ref_no,file_seq_no,record_identifier,"
                            + "rrn,micr,ac_type,old_acno,old_acname,user_number,user_name,tran_ref,ac_val_flag,"
                            + "cbs_acno,cbs_name,return_code,ifnull(amount,0),ifnull(ach_item_seq_no,''),'UMRN' from "
                            + "cbs_npci_oac_detail where file_coming_dt='" + fileComingDt + "' and "
                            + "file_seq_no='" + seqNo + "' and iw_type='" + iwType + "' and "
                            + "(micr='" + arr[0] + "' or micr='" + arr[1] + "')").getResultList();
                }
            } else {
                String alphaCode = commonReport.getAlphacodeByBrncode(selectedBranch);
                if (alphaCode.equalsIgnoreCase("HO")) {
                    list = em.createNativeQuery("select header_identifier,originator_code,responder_code,"
                            + "date_format(file_coming_dt,'%d/%m/%Y'),file_ref_no,file_seq_no,record_identifier,"
                            + "rrn,micr,ac_type,old_acno,old_acname,user_number,user_name,tran_ref,ac_val_flag,"
                            + "cbs_acno,cbs_name,return_code,ifnull(amount,0),ifnull(ach_item_seq_no,''),umrn from "
                            + "cbs_npci_oac_detail where file_coming_dt='" + fileComingDt + "' and "
                            + "file_seq_no='" + seqNo + "' and iw_type='" + iwType + "'").getResultList();
                } else {
                    String[] arr = getIfscAndMicrCodeByBrnCode(selectedBranch);
                    list = em.createNativeQuery("select header_identifier,originator_code,responder_code,"
                            + "date_format(file_coming_dt,'%d/%m/%Y'),file_ref_no,file_seq_no,record_identifier,"
                            + "rrn,micr,ac_type,old_acno,old_acname,user_number,user_name,tran_ref,ac_val_flag,"
                            + "cbs_acno,cbs_name,return_code,ifnull(amount,0),ifnull(ach_item_seq_no,''),umrn from "
                            + "cbs_npci_oac_detail where file_coming_dt='" + fileComingDt + "' and "
                            + "file_seq_no='" + seqNo + "' and iw_type='" + iwType + "' and "
                            + "(micr='" + arr[0] + "' or micr='" + arr[1] + "')").getResultList();
                }
            }
            if (list.isEmpty()) {
                throw new ApplicationException("There is no data.");
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                NpciOacDto dto = new NpciOacDto();

                dto.setHeaderId(ele.get(0).toString());
                dto.setOrgCode(ele.get(1).toString());
                dto.setResCode(ele.get(2).toString());
                dto.setFileComingDt(ele.get(3).toString());
                dto.setFileRefNo(ele.get(4).toString());
                dto.setFileSeqNo(ele.get(5).toString());
                dto.setRecordId(ele.get(6).toString());
                dto.setRrn(ele.get(7).toString());
                dto.setMicr(ele.get(8).toString());
                dto.setAcType(ele.get(9).toString());
                dto.setOldAcno(ele.get(10).toString());
                dto.setOldAcName(ele.get(11).toString());
                dto.setUserNumber(ele.get(12).toString());
                dto.setUserName(ele.get(13).toString());
                dto.setTranRef(ele.get(14).toString());
                dto.setAcValFlag(ele.get(15).toString());
                dto.setCbsAcno(ele.get(16).toString());
                dto.setCbsName(ele.get(17).toString());
                dto.setReturnCode(ele.get(18).toString());
                dto.setAmount(ele.get(19).toString());
                dto.setAchItemSeqNo(ele.get(20).toString());
                dto.setUmrn(ele.get(21).toString());

                dataList.add(dto);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public String verifyEntry(NpciOacDto currentItem, String cbsAcno, String cbsName, String acValid,
            String reason, String userName, String fileComingDt, String fileSeqNo, String iwType) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List list = em.createNativeQuery("select ac_val_flag from cbs_npci_oac_detail "
                    + "where file_coming_dt='" + fileComingDt + "' and "
                    + "file_seq_no='" + fileSeqNo + "' and iw_type='" + iwType + "' and "
                    + "rrn='" + currentItem.getRrn().trim() + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no such entry to verify.");
            }

            Vector ele = (Vector) list.get(0);
            String dbAcValidFlag = ele.get(0).toString();
            if (!(dbAcValidFlag == null || dbAcValidFlag.equals(""))) {
                throw new ApplicationException("This entry has been already verified.");
            }

            int n = em.createNativeQuery("update cbs_npci_oac_detail set cbs_acno='" + cbsAcno + "',"
                    + "cbs_name='" + cbsName + "',ac_val_flag='" + acValid + "',"
                    + "return_code='" + reason + "',enter_by='" + userName + "',"
                    + "enter_date='" + ymd.format(new Date()) + "',enter_time=now() where "
                    + "file_coming_dt='" + fileComingDt + "' and file_seq_no='" + fileSeqNo + "' and "
                    + "iw_type='" + iwType + "' and rrn='" + currentItem.getRrn().trim() + "'").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem in entry verification.");
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
        return "true";
    }

    public String generateOacReturnFiles(String fileGenDt, String fileSeqNo, String orgnBrCode,
            String enterBy, String todayDt, String iwType, String processingMode, String h2hLocation) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String headerId = "", orgCode = "", resCode = "", fileUpDt = "", fileRefNo = "", totalRecord = "";
        String fileNo = "", genFileName = "", aadharLocation = "";
        try {
            ut.begin();

            if (processingMode.equalsIgnoreCase("H2H")) {
                List list = em.createNativeQuery("select * from cbs_npci_mapper_files where "
                        + "FILE_GEN_TYPE='OAC' AND DATE_OF_FILE_GEN='" + ymdSql.format(ymd.parse(fileGenDt)) + "' AND FILE_GEN_SEQN='" + fileSeqNo + "'").getResultList();
                if (list.size() >= 1) {
                    throw new ApplicationException("NPCI-OAC Return File already Generated...");
                }
            }

            List list = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + enterBy + "'").getResultList();
            Vector ele = (Vector) list.get(0);
            if (list.isEmpty() || ele.get(0) == null || ele.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to generate the files.");
            }
            String npciUserName = ele.get(0).toString().trim();
            //Total data to generate the file
            List dataList = em.createNativeQuery("select header_identifier,originator_code,responder_code,"
                    + "date_format(file_coming_dt,'%d/%m/%Y'),file_ref_no,file_seq_no,record_identifier,"
                    + "rrn,micr,ac_type,old_acno,old_acname,user_number,user_name,tran_ref,ac_val_flag,"
                    + "cbs_acno,cbs_name,return_code from cbs_npci_oac_detail where "
                    + "file_coming_dt='" + fileGenDt + "' and file_seq_no='" + fileSeqNo + "' and "
                    + "iw_type='" + iwType + "'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no data to generate the file.");
            }
            //Checking that all entries should be verified.
            list = em.createNativeQuery("select rrn from cbs_npci_oac_detail where file_coming_dt='" + fileGenDt + "' and "
                    + "file_seq_no='" + fileSeqNo + "' and iw_type='" + iwType + "' and ac_val_flag=''").getResultList();
            if (!list.isEmpty()) {
                throw new ApplicationException("All entries were not verified for "
                        + "File Upload Date:" + dmy.format(ymd.parse(fileGenDt)) + " and Seq No:" + fileSeqNo);
            }
            //For header details
            ele = (Vector) dataList.get(0);
            headerId = ele.get(0).toString().trim(); //As it is
            orgCode = ele.get(1).toString().trim(); //As it is
            resCode = ele.get(2).toString().trim(); //As it is
            fileUpDt = ymdOne.format(dmy.parse(ele.get(3).toString().trim())); //As it is
            fileRefNo = ele.get(4).toString().trim();

            list = em.createNativeQuery("select aadhar_location from mb_sms_sender_bank_detail").getResultList();
            ele = (Vector) list.get(0);
            if (ele.get(0) == null || ele.get(0).toString().trim().equals("")) {
                throw new ApplicationException("Please define Aadhar Location.");
            }
            aadharLocation = ele.get(0).toString().trim();

            list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
                    + "where file_gen_date='" + fileGenDt + "' and file_gen_type='OAC'").getResultList();
            ele = (Vector) list.get(0);
            fileNo = "1";
            if (ele.get(0) != null) {
                fileNo = ele.get(0).toString().trim();
            }
            //userName changes
            if (processingMode.equalsIgnoreCase("H2H")) {
                List H2huserList = em.createNativeQuery("select name,code from cbs_parameterinfo where name='NPCI-H2H-USER'").getResultList();
                Vector v = (Vector) H2huserList.get(0);
                if (H2huserList.isEmpty() || v.get(1) == null || v.get(1).toString().equalsIgnoreCase("")) {
                    throw new ApplicationException("H2H User can not be blank.");
                }
                npciUserName = v.get(1).toString();
//                genFileName = "OAC-" + orgCode.toUpperCase().trim() + "-" + resCode.toUpperCase().trim()
//                        + "-" + npciUserName.toUpperCase() + "-" + fileUpDt.trim() + "-" + fileSeqNo.trim() + "-RES.txt";

                genFileName = "OAC-" + orgCode.toUpperCase().trim() + "-" + resCode.toUpperCase().trim()
                        + "-" + npciUserName.toUpperCase() + "-" + ymdOne.format(new Date()) + "-" + fileSeqNo.trim() + "-RES.txt";
            } else {
                genFileName = "OAC-" + orgCode.toUpperCase().trim() + "-" + resCode.toUpperCase().trim()
                        + "-" + npciUserName.toUpperCase() + "-" + fileUpDt.trim() + "-" + fileSeqNo.trim() + "-RES.txt";
            }
            int n = 0;
            if (processingMode.equalsIgnoreCase("H2H")) {
                n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                        + "file_gen_time,file_gen_brncode,file_gen_type,DATE_OF_FILE_GEN,FILE_GEN_SEQN) values(" + Integer.parseInt(fileNo) + ",'"
                        + fileGenDt + "','" + genFileName + "','" + enterBy + "',now(),'"
                        + orgnBrCode + "','OAC','" + ymdSql.format(ymd.parse(fileGenDt)) + "','" + fileSeqNo + "')").executeUpdate();
                String updateFileGenFlagQuery = "UPDATE cbs_npci_oac_detail SET file_gen_flag = 'Y' where FILE_SEQ_NO='" + fileSeqNo + "' "
                        + "and IW_TYPE='NPCI-OAC' and FILE_COMING_DT='" + fileGenDt + "'";
                int fileGenFlag = em.createNativeQuery(updateFileGenFlagQuery).executeUpdate();
                if (fileGenFlag <= 0) {
                    throw new ApplicationException("Problem In Cbs Npci Mapper Files file_gen_flag updation .");
                }
            } else {
                n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                        + "file_gen_time,file_gen_brncode,file_gen_type) values(" + Integer.parseInt(fileNo) + ",'"
                        + fileGenDt + "','" + genFileName + "','" + enterBy + "',now(),'"
                        + orgnBrCode + "','OAC')").executeUpdate();
            }
            if (n <= 0) {
                throw new ApplicationException("Problem In Cbs Npci Mapper Files Insertion.");
            }

            totalRecord = String.valueOf(dataList.size()).trim(); //Make to 6 digit.
            //Header Preparation.
//            FileWriter fw = new FileWriter(aadharLocation + genFileName);
            FileWriter fw = null;
            if (processingMode.equalsIgnoreCase("H2H")) { //
                fw = new FileWriter(h2hLocation + genFileName);
            } else {
                fw = new FileWriter(aadharLocation + genFileName);
            }

            String header = headerId + ParseFileUtil.addSuffixSpaces(orgCode, 4) + ParseFileUtil.addSuffixSpaces(resCode, 4)
                    + ymdOne.format(dmy.parse(todayDt)) + ParseFileUtil.addSuffixSpaces(fileRefNo, 10) + ParseFileUtil.addTrailingZeros(totalRecord, 6)
                    + ParseFileUtil.addSuffixSpaces("", 316) + "\n";
            fw.write(header);
            //Data Preparation.
            for (int i = 0; i < dataList.size(); i++) {
                Vector element = (Vector) dataList.get(i);
                String recordId = element.get(6).toString().trim();
                String rrn = element.get(7).toString().trim();
                String micr = element.get(8).toString().trim();
                String acType = element.get(9).toString().trim();
                String oldAcno = element.get(10).toString().trim();
                String oldAcName = element.get(11).toString().trim();
                String userNumber = element.get(12).toString().trim();
                String userName = element.get(13).toString().trim();
                String tranRef = element.get(14).toString().trim();
                String acValidFlag = element.get(15).toString().trim();
                String cbsAcno = element.get(16).toString().trim();
                String cbsAcName = element.get(17).toString().trim();
                String returnCode = element.get(18).toString().trim();

                String individualStr = recordId + ParseFileUtil.addSuffixSpaces(rrn, 15)
                        + ParseFileUtil.addSuffixSpaces(micr, 11) + ParseFileUtil.addTrailingZeros(acType, 2)
                        + ParseFileUtil.addSuffixSpaces(oldAcno, 20) + ParseFileUtil.addSuffixSpaces(oldAcName, 100)
                        + ParseFileUtil.addTrailingZeros(userNumber, 7) + ParseFileUtil.addSuffixSpaces(userName, 20)
                        + ParseFileUtil.addSuffixSpaces(tranRef, 13) + acValidFlag + ParseFileUtil.addSuffixSpaces(cbsAcno, 35)
                        + ParseFileUtil.addSuffixSpaces(cbsAcName, 100) + ParseFileUtil.addSuffixSpaces(returnCode, 2)
                        + ParseFileUtil.addSuffixSpaces("", 10) + ParseFileUtil.addSuffixSpaces("", 12) + "\n";

                fw.write(individualStr);
            }
            fw.close();
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "true";
    }

    public List<NpciFileDto> showOacFiles(String fileType, String fileShowDt) throws ApplicationException {
        List<NpciFileDto> dataList = new ArrayList<NpciFileDto>();
        try {
            List list = em.createNativeQuery("select file_no,date_format(file_gen_date,'%d/%m/%Y'),file_name,"
                    + "file_gen_by from cbs_npci_mapper_files where file_gen_date='" + fileShowDt + "' and "
                    + "file_gen_type='" + fileType + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no data to show.");
            }
            for (int i = 0; i < list.size(); i++) {
                NpciFileDto dto = new NpciFileDto();
                Vector ele = (Vector) list.get(i);

                dto.setFileNo(new BigInteger(ele.get(0).toString()));
                dto.setFileGenDt(ele.get(1).toString());
                dto.setFileName(ele.get(2).toString());
                dto.setFileGenBy(ele.get(3).toString());

                dataList.add(dto);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public String ecsMandate(NpciOacDto currentItem, String cbsAcno, String cbsName, String status,
            String reason, String userName, String fileComingDt, String fileSeqNo, String iwType,
            Double amount, String todayDt, String loginBrCode, String selectedBranch) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        Date currentDt = new Date();
        List<SmsToBatchTo> smsBatchList = new ArrayList<SmsToBatchTo>();
        try {
            //AC_VAL_FLAG will be P(PASS)/R(RETURN)/BLANK
            ut.begin();
            synchronized (OtherNpciMgmtFacade.class) {
                List list = em.createNativeQuery("select ac_val_flag from cbs_npci_oac_detail where "
                        + "file_coming_dt='" + fileComingDt + "' and file_seq_no='" + fileSeqNo + "' and "
                        + "iw_type='" + iwType + "' and tran_ref='" + currentItem.getTranRef().trim() + "'").getResultList();
                if (list.isEmpty()) {
                    throw new ApplicationException("There is no such entry to verify.");
                }

                Vector ele = (Vector) list.get(0);
                String dbAcValidFlag = ele.get(0).toString();
                if (!(dbAcValidFlag == null || dbAcValidFlag.equals(""))) {
                    throw new ApplicationException("This entry has been already verified.");
                }

                if (status.equalsIgnoreCase("P")) {
                    int trfSeq = 0;
                    String narration = "By NPCI " + currentItem.getUserName() + " " + currentItem.getTranRef(); //"NPCI-ECS-Dr_" + todayDt + "_Entry_" + currentItem.getUserName()
                    //Addition on 16/08/2017
                    if (iwType.equalsIgnoreCase("ECS-DR")) {
                        list = em.createNativeQuery("select ifnull(code,'') as head from cbs_parameterinfo "
                                + "where name='NPCI-ECS-DR-HEAD'").getResultList();
                    } else if (iwType.equalsIgnoreCase("ACH-DR")) {
                        list = em.createNativeQuery("select ifnull(code,'') as head from cbs_parameterinfo "
                                + "where name='NPCI-ACH-DR-HEAD'").getResultList();
                    }
                    if (list.isEmpty()) {
                        throw new ApplicationException("Please define NPCI-ACH/ECS-DR-HEAD.");
                    }
                    //End Here

//                    list = em.createNativeQuery("select ifnull(code,'') as head from cbs_parameterinfo "
//                            + "where name='NPCI-ECS-DR-HEAD'").getResultList();
//                    if (list.isEmpty()) {
//                        throw new ApplicationException("Please define NPCI-ECS-DR-HEAD.");
//                    }
                    ele = (Vector) list.get(0);
                    String clgCellAc = ele.get(0).toString().trim();
                    if (clgCellAc.length() != 10) {
                        throw new ApplicationException("Please define 10 digit NPCI-ECS-DR-HEAD.");
                    }
                    clgCellAc = loginBrCode + clgCellAc;

                    Float trsno = ftsRemote.getTrsNo();
                    String loginAlphaCode = commonReport.getAlphacodeByBrncode(loginBrCode);
                    if (loginAlphaCode.equalsIgnoreCase("HO")) {
                        String result = ibtRemote.cbsPostingSx(cbsAcno, 1, ymd.format(dmy.parse(todayDt)),
                                amount, 0f, 2, narration, 0f, "A",
                                "", "", 3, 0f, ftsRemote.getRecNo(), 66, cbsAcno.substring(0, 2),
                                loginBrCode, userName, userName, trsno, "", "");
                        if (!result.substring(0, 4).equalsIgnoreCase("true")) {
                            throw new ApplicationException(result);
                        }
                        result = ftsRemote.updateBalance(ftsRemote.getAccountNature(cbsAcno), cbsAcno, 0,
                                amount, "Y", "Y");
                        if (!result.equalsIgnoreCase("true")) {
                            throw new ApplicationException(result);
                        }

//                        //***
//                        trfSeq = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,"
//                                + "trantype,recno,instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,"
//                                + "trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,adviceno,advicebrncode) "
//                                + "values('" + cbsAcno + "','" + ftsRemote.getCustName(cbsAcno) + "','" + ymd.format(currentDt) + "','" + ymd.format(currentDt) + "',0,"
//                                + "" + amount + ",1,2," + ftsRemote.getRecNo() + ",'','19000101',3,0,'Y','" + userName + "',66,0,'','" + "NPCI-ECS-Dr_" + todayDt + "_Entry_" + currentItem.getUserName() + "',"
//                                + "'" + userName + "'," + trsno + ",CURRENT_TIMESTAMP,'','" + loginBrCode + "',"
//                                + "'" + loginBrCode + "',0,'','')").executeUpdate();
//                        if (trfSeq <= 0) {
//                            throw new ApplicationException("Problem In Trf Scroll Insertion.");
//                        }
//                        //***


                        result = ibtRemote.cbsPostingCx(clgCellAc, 0, ymd.format(dmy.parse(todayDt)),
                                amount, 0f, 2, narration,
                                0f, "A", "", "", 3, 0f, ftsRemote.getRecNo(), 66, loginBrCode, loginBrCode,
                                userName, userName, trsno, "", "");
                        if (!result.substring(0, 4).equalsIgnoreCase("true")) {
                            throw new ApplicationException(result);
                        }

//                        ///*****
//                        trfSeq = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,"
//                                + "trantype,recno,instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,"
//                                + "trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,adviceno,advicebrncode) "
//                                + "values('" + clgCellAc + "','" + ftsRemote.getCustName(clgCellAc) + "','" + ymd.format(currentDt) + "','" + ymd.format(currentDt) + "'," + Double.parseDouble(formatter.format(amount)) + ","
//                                + "0,0,2," + ftsRemote.getRecNo() + ",'','19000101',3,0,'Y','" + userName + "',66,0,'','" + "NPCI-ECS-Dr_" + todayDt + "_Entry_" + currentItem.getUserName() + "',"
//                                + "'" + userName + "'," + trsno + ",CURRENT_TIMESTAMP,'','" + loginBrCode + "',"
//                                + "'" + loginBrCode + "',0,'','')").executeUpdate();
//                        if (trfSeq <= 0) {
//                            throw new ApplicationException("Problem In Trf Scroll Insertion.");
//                        }
//                        ///*****

                        //Adding Object For Sms
                        SmsToBatchTo to = new SmsToBatchTo();
                        to.setAcNo(cbsAcno);
                        to.setCrAmt(0d);
                        to.setDrAmt(amount);
                        to.setTranType(2);
                        to.setTy(1);
                        to.setTxnDt(todayDt);
                        to.setTemplate(SmsType.TRANSFER_WITHDRAWAL);
                        smsBatchList.add(to);
                        //End
                    } else {
                        //Debit Entry
//                        String orgnBrCode = ftsRemote.getCurrentBrnCode(cbsAcno);
                        String actNature = ftsRemote.getAccountNature(cbsAcno);
                        String msg = ftsRemote.insertRecons(actNature, cbsAcno, 1, amount, ymd.format(currentDt),
                                ymd.format(currentDt), 2, narration, userName, trsno, "", ftsRemote.getRecNo(), "Y",
                                userName, 66, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "", loginBrCode, loginBrCode, 0, "", "", "");
                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg);
                        }

                        msg = ftsRemote.updateBalance(actNature, cbsAcno, 0, amount, "Y", "Y");
                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg);
                        }
                        //***
                        trfSeq = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,"
                                + "trantype,recno,instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,"
                                + "trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,adviceno,advicebrncode) "
                                + "values('" + cbsAcno + "','" + ftsRemote.getCustName(cbsAcno) + "','" + ymd.format(currentDt) + "','" + ymd.format(currentDt) + "',0,"
                                + "" + amount + ",1,2," + ftsRemote.getRecNo() + ",'','19000101',3,0,'Y','" + userName + "',66,0,'','" + narration + "',"
                                + "'" + userName + "'," + trsno + ",CURRENT_TIMESTAMP,'','" + loginBrCode + "',"
                                + "'" + loginBrCode + "',0,'','')").executeUpdate();
                        if (trfSeq <= 0) {
                            throw new ApplicationException("Problem In Trf Scroll Insertion.");
                        }
                        //***

                        //Credit Entry
//                        list = em.createNativeQuery("select ifnull(code,'') as head from cbs_parameterinfo "
//                                + "where name='NPCI-ECS-DR-HEAD'").getResultList();
//                        if (list.isEmpty()) {
//                            throw new ApplicationException("Please define NPCI-ECS-DR-HEAD.");
//                        }
//                        ele = (Vector) list.get(0);
//                        String clgCellAc = ele.get(0).toString().trim();
//                        if (clgCellAc.length() != 10) {
//                            throw new ApplicationException("Please define 10 digit NPCI-ECS-DR-HEAD.");
//                        }
//                        clgCellAc = orgnBrCode + clgCellAc;

                        actNature = ftsRemote.getAccountNature(clgCellAc);
                        msg = ftsRemote.insertRecons(actNature, clgCellAc, 0, amount,
                                ymd.format(currentDt), ymd.format(currentDt), 2, narration, userName, trsno, "",
                                ftsRemote.getRecNo(), "Y", userName, 66, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "",
                                loginBrCode, loginBrCode, 0, "", "", "");
                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg);
                        }
                        msg = ftsRemote.updateBalance(actNature, clgCellAc, amount, 0, "Y", "Y");
                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg);
                        }
                        ///*****
                        trfSeq = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,cramt,dramt,ty,"
                                + "trantype,recno,instno,instdt, payby,iy,auth,enterby,trandesc,tokenno,subtokenno,details,authby,"
                                + "trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,adviceno,advicebrncode) "
                                + "values('" + clgCellAc + "','" + ftsRemote.getCustName(clgCellAc) + "','" + ymd.format(currentDt) + "','" + ymd.format(currentDt) + "'," + Double.parseDouble(formatter.format(amount)) + ","
                                + "0,0,2," + ftsRemote.getRecNo() + ",'','19000101',3,0,'Y','" + userName + "',66,0,'','" + narration + "',"
                                + "'" + userName + "'," + trsno + ",CURRENT_TIMESTAMP,'','" + loginBrCode + "',"
                                + "'" + loginBrCode + "',0,'','')").executeUpdate();
                        if (trfSeq <= 0) {
                            throw new ApplicationException("Problem In Trf Scroll Insertion.");
                        }
                        ///*****
                        //Adding Object For Sms
                        SmsToBatchTo to = new SmsToBatchTo();
                        to.setAcNo(cbsAcno);
                        to.setCrAmt(0d);
                        to.setDrAmt(amount);
                        to.setTranType(2);
                        to.setTy(1);
                        to.setTxnDt(todayDt);
                        to.setTemplate(SmsType.TRANSFER_WITHDRAWAL);
                        smsBatchList.add(to);
                        //End
                    }
                } else if (status.equalsIgnoreCase("R")) {
                    float trsno = ftsRemote.getTrsNo().floatValue();
                    if (iwType.equalsIgnoreCase("ACH-DR")) {
                        List returReasonCodelist = em.createNativeQuery("select REF_CODE from cbs_ref_rec_type where REF_REC_NO='386' and REF_CODE='" + reason + "'").getResultList();
                        int achInwReturnChgFlag = commonReport.getCodeByReportName("ACH-DR-INW-RET-CHG");
                        if ((!returReasonCodelist.isEmpty()) && (achInwReturnChgFlag == 1)) {
                            String result = ibtRemote.postACHDRreturnChargesAndTax(cbsAcno, userName, trsno, loginBrCode, ymd.format(dmy.parse(todayDt)));
                            if (!(result.equalsIgnoreCase("true") || result.equalsIgnoreCase("pending"))) {
                                throw new ApplicationException("Problem in ACH -DR return charge posting");
                            }
                        }
                    } else if (iwType.equalsIgnoreCase("ECS-DR")) {
                        List returReasonCodelist = em.createNativeQuery("select REF_CODE from cbs_ref_rec_type where REF_REC_NO='385' and REF_CODE='" + reason + "'").getResultList();
                        int ecsInwReturnChgFlag = commonReport.getCodeByReportName("ECS-DR-INW-RET-CHG");
                        if ((!returReasonCodelist.isEmpty()) && (ecsInwReturnChgFlag == 1)) {
                            String result = ibtRemote.postECSChargesAndTax("", cbsAcno, userName, trsno, loginBrCode, ymd.format(dmy.parse(todayDt)));
                            if (!(result.equalsIgnoreCase("true") || result.equalsIgnoreCase("pending"))) {
                                throw new ApplicationException("Problem in ECS -DR return charge posting");
                            }
                        }
                    }
                }

                int n = em.createNativeQuery("update cbs_npci_oac_detail set cbs_acno='" + cbsAcno + "',"
                        + "cbs_name='" + cbsName + "',ac_val_flag='" + status + "',"
                        + "return_code='" + reason + "',enter_by='" + userName + "',"
                        + "enter_date='" + ymd.format(currentDt) + "',enter_time=now() where "
                        + "file_coming_dt='" + fileComingDt + "' and file_seq_no='" + fileSeqNo + "' and "
                        + "iw_type='" + iwType + "' and tran_ref='" + currentItem.getTranRef().trim() + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in entry verification.");
                }
            }
            ut.commit();
            //Sending Sms
            try {
                smsFacade.sendSmsToBatch(smsBatchList);
            } catch (Exception e) {
                System.out.println("Problem In SMS Sending To Batch In "
                        + "Npci Ecs Debit." + e.getMessage());
            }
            //End here
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "true";
    }

    public String generateEcsDrReturnFiles(String fileGenDt, String fileSeqNo, String orgnBrCode,
            String enterBy, String todayDt, String iwType, String processingMode, String h2hLocation) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String fileNameDt = "", fileNo = "", genFileName = "", aadharLocation = "", npciBankCode = "", settlementDt = "";
        try {
            ut.begin();
            if (processingMode.equalsIgnoreCase("H2H")) {
                List list = em.createNativeQuery("select * from cbs_npci_mapper_files where "
                        + "FILE_GEN_TYPE='ECS-DR' AND DATE_OF_FILE_GEN='" + ymdSql.format(ymd.parse(fileGenDt)) + "' AND FILE_GEN_SEQN='" + fileSeqNo + "'").getResultList();
                if (list.size() >= 1) {
                    throw new ApplicationException("ECS-DR Return File already Generated...");
                }
            }
            List list = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + enterBy + "'").getResultList();
            Vector ele = (Vector) list.get(0);
            if (list.isEmpty() || ele.get(0) == null || ele.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to generate the files.");
            }
            String npciUserName = ele.get(0).toString().trim();
            //Total data to generate the file
            List dataList = em.createNativeQuery("select date_format(file_coming_dt,'%d/%m/%Y'),ach_item_seq_no,"
                    + "user_number,amount,micr,tran_ref,ac_val_flag,return_code,file_name_date from cbs_npci_oac_detail where "
                    + "file_coming_dt='" + fileGenDt + "' and file_seq_no='" + fileSeqNo + "' and "
                    + "iw_type='" + iwType + "' and ac_val_flag='R'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no data to generate the file.");
            }
            //Checking that all entries should be verified.
            list = em.createNativeQuery("select tran_ref from cbs_npci_oac_detail where "
                    + "file_coming_dt='" + fileGenDt + "' and file_seq_no='" + fileSeqNo + "' and "
                    + "iw_type='" + iwType + "' and ac_val_flag=''").getResultList();
            if (!list.isEmpty()) {
                throw new ApplicationException("All entries were not verified for "
                        + "File Upload Date:" + dmy.format(ymd.parse(fileGenDt)) + " and Seq No:" + fileSeqNo);
            }
            //For header details
            ele = (Vector) dataList.get(0);
//            fileUpDt = ymdOne.format(dmy.parse(ele.get(0).toString().trim())); //As it is

            settlementDt = ymdOne.format(dmy.parse(ele.get(0).toString().trim()));
            fileNameDt = ele.get(8).toString().trim();

            list = em.createNativeQuery("select aadhar_location,ifnull(npci_bank_code,'') from "
                    + "mb_sms_sender_bank_detail").getResultList();
            ele = (Vector) list.get(0);
            if (ele.get(0) == null || ele.get(1) == null || ele.get(0).toString().trim().equals("")
                    || ele.get(1).toString().trim().equals("")) {
                throw new ApplicationException("Please define Aadhar Location And NPCI Bank Code.");
            }
            aadharLocation = ele.get(0).toString().trim();
            npciBankCode = ele.get(1).toString().trim();

            list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
                    + "where file_gen_date='" + fileGenDt + "' and file_gen_type='ECS-DR'").getResultList();
            ele = (Vector) list.get(0);
            fileNo = "1";
            if (ele.get(0) != null) {
                fileNo = ele.get(0).toString().trim();
            }
            if (processingMode.equalsIgnoreCase("H2H")) {
                List H2huserList = em.createNativeQuery("select name,code from cbs_parameterinfo where name='NPCI-H2H-USER'").getResultList();
                Vector v = (Vector) H2huserList.get(0);
                if (H2huserList.isEmpty() || v.get(1) == null || v.get(1).toString().equalsIgnoreCase("")) {
                    throw new ApplicationException("H2H User can not be blank.");
                }
                npciUserName = v.get(1).toString();
//                genFileName = "ECS-DR-" + npciBankCode + "-" + npciUserName.toUpperCase() + "-"
//                        + settlementDt.trim() + "-" + fileSeqNo.trim() + "-RTN.txt";

                genFileName = "ECS-DR-" + npciBankCode + "-" + npciUserName.toUpperCase() + "-"
                        + ymdOne.format(new Date()) + "-" + fileSeqNo.trim() + "-RTN.txt";
            } else {
                genFileName = "ECS-DR-" + npciBankCode + "-" + npciUserName.toUpperCase() + "-"
                        + settlementDt.trim() + "-" + fileSeqNo.trim() + "-RTN.txt";
            }

            int n = 0;
            int fileGenFlag = 0;
            if (processingMode.equalsIgnoreCase("H2H")) {

                n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                        + "file_gen_time,file_gen_brncode,file_gen_type,DATE_OF_FILE_GEN,FILE_GEN_SEQN) values(" + Integer.parseInt(fileNo) + ",'"
                        + fileGenDt + "','" + genFileName + "','" + enterBy + "',now(),'"
                        + orgnBrCode + "','ECS-DR','" + ymdSql.format(dmy.parse(todayDt)) + "','" + fileSeqNo + "')").executeUpdate();
                String updateFileGenFlagQuery = "UPDATE cbs_npci_oac_detail SET file_gen_flag = 'Y' where FILE_SEQ_NO='" + fileSeqNo + "' "
                        + "and IW_TYPE='ECS-DR' and FILE_COMING_DT='" + fileGenDt + "'";
                fileGenFlag = em.createNativeQuery(updateFileGenFlagQuery).executeUpdate();
                if (fileGenFlag <= 0) {
                    throw new ApplicationException("Problem In Cbs Npci Mapper Files file_gen_flag updation .");
                }
            } else {
                n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                        + "file_gen_time,file_gen_brncode,file_gen_type) values(" + Integer.parseInt(fileNo) + ",'"
                        + fileGenDt + "','" + genFileName + "','" + enterBy + "',now(),'"
                        + orgnBrCode + "','ECS-DR')").executeUpdate();
            }
            if (n <= 0) {
                throw new ApplicationException("Problem In Cbs Npci Mapper Files Insertion.");
            }
            //File Writing
//            FileWriter fw = new FileWriter(aadharLocation + genFileName);
            FileWriter fw = null;
            if (processingMode.equalsIgnoreCase("H2H")) { //
                fw = new FileWriter(h2hLocation + genFileName);
            } else {
                fw = new FileWriter(aadharLocation + genFileName);
            }

            //Data Preparation.
            for (int i = 0; i < dataList.size(); i++) {
                Vector element = (Vector) dataList.get(i);
                String achItemSeqNo = element.get(1).toString().trim();
                String userNumber = element.get(2).toString().trim();
                String amount = element.get(3).toString().trim();
                String micr = element.get(4).toString().trim();
//                String acValidFlag = element.get(6).toString().trim();
                String returnCode = element.get(7).toString().trim(); //Return code(code in cbs_ref_rec_type) will be of 1 digit.
//                if (acValidFlag.equalsIgnoreCase("P")) {
//                    returnCode = ParseFileUtil.addSuffixSpaces("", 1);
//                }
//                String individualStr = fileUpDt + achItemSeqNo + userNumber + amount
//                        + returnCode + micr + ParseFileUtil.addSuffixSpaces("", 2) + "\n";

                String individualStr = settlementDt + achItemSeqNo + userNumber + amount
                        + returnCode + micr + ParseFileUtil.addSuffixSpaces("", 2) + "\n";

                fw.write(individualStr);
            }
            fw.close();
            //In case of H2H OW
            if (processingMode.equalsIgnoreCase("H2H")) {
                h2hNpciRemote.writeEncryptedFiles();
                h2hNpciRemote.upload(props.getProperty("npciSftpHost").trim(), props.getProperty("npciSftpUser").trim(),
                        props.getProperty("npciSftpPassword").trim(), props.getProperty("cbs.ow.encrypted.location").trim(),
                        props.getProperty("npciSftpFileUploadLocation").trim());

                h2hNpciRemote.createBackupAndThenRemoveFile(props.getProperty("cbs.ow.location").trim(), props.getProperty("cbs.ow.bkp.location").trim());
                h2hNpciRemote.createBackupAndThenRemoveFile(props.getProperty("cbs.ow.encrypted.location").trim(), props.getProperty("cbs.ow.bkp.encrypted.location").trim());

                em.createNativeQuery("insert into cbs_npci_h2h_file_detail(file_date,file_name,file_type) "
                        + "values('" + ymd.format(new Date()) + "','" + genFileName + "','OW')").executeUpdate();
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
        return "true";
    }

    public List<MandateRecordTo> retrieveMandateData(String acno) throws ApplicationException {
        List<MandateRecordTo> dataList = new ArrayList<MandateRecordTo>();
        try {
            List list = em.createNativeQuery("select umrn,scheme_name,instruction,date_format(date_of_effects,'%d/%m/%Y') as "
                    + "date_of_effects,periodicity,amt_flag,amt,mandate_dr_acno,mandate_received_at_branch,"
                    + "mandate_received_at_ifsc,mandate_received_at_micr,mandate_amt_periodicity_flag,no_of_installment,"
                    + "ifnull(from_date,'') as from_date,ifnull(to_date,'') as to_date,mandate_for_user_number,"
                    + "mandate_for_user_name,mandate_for_user_address,mandate_for_ifsc,mandate_for_micr,mandate_cr_acno,"
                    + "enter_by,ifnull(enter_date,'%d/%m/%Y') as enter_date from cbs_mandate_detail where "
                    + "mandate_dr_acno='" + acno + "'").getResultList();
            for (int i = 0; i < list.size(); i++) {
                MandateRecordTo to = new MandateRecordTo();
                Vector ele = (Vector) list.get(i);

                to.setUmrn(ele.get(0).toString().trim());
                to.setSchemeName(ele.get(1).toString().trim());
                to.setInstruction(ele.get(2).toString().trim());
                to.setDateOfEffect(ele.get(3).toString().trim());
                to.setPeriodicity(commonReport.getRefRecDesc("318", ele.get(4).toString().trim()));
                String amountStatus = ele.get(5).toString().trim();
                to.setAmtFlag(amountStatus.equalsIgnoreCase("F") ? "FIXED INSTALLMENT" : "MAX LIMIT");
                to.setAmt(ele.get(6).toString().trim());
                to.setDebitAccount(ele.get(7).toString().trim());
                to.setMandateReceivedBranch(ele.get(8).toString().trim());
                to.setMandateReceivedIfsc(ele.get(9).toString().trim());
                to.setMandateReceivedMicr(ele.get(10).toString().trim());
                String tempValidity = ele.get(11).toString().trim();
                if (tempValidity.equalsIgnoreCase("I")) {
                    to.setMandateAmtPeriodicityFlag("NO OF INSTALLMENT");
                } else if (tempValidity.equalsIgnoreCase("U")) {
                    to.setMandateAmtPeriodicityFlag("VALID UPTO");
                } else if (tempValidity.equalsIgnoreCase("B")) {
                    to.setMandateAmtPeriodicityFlag("BETWEEN PERIOD");
                } else if (tempValidity.equalsIgnoreCase("C")) {
                    to.setMandateAmtPeriodicityFlag("UNTIL CALCELLATION");
                }
                to.setInstallmentNo(ele.get(12).toString().trim());
                String frDt = ele.get(13).toString().trim();
                to.setFromDt(frDt.equals("") ? "" : frDt);
                String toDt = ele.get(14).toString().trim();
                to.setToDt(toDt.equals("") ? "" : toDt);
                to.setMandateUserNo(ele.get(15).toString().trim());
                to.setMandateUserName(ele.get(16).toString().trim());
                to.setMandateUserAdd(ele.get(17).toString().trim());
                to.setMandateForIfsc(ele.get(18).toString().trim());
                to.setMandateForMicr(ele.get(19).toString().trim());
                to.setCreditAccount(ele.get(20).toString().trim());
                to.setEnterBy(ele.get(21).toString().trim());
                to.setEnterDt(ele.get(22).toString().trim());

                dataList.add(to);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    @Override
    public List<CbsMandateDetailPojo> retrieveMandateDataNew(String npciFileType, String function, String modifyCriteria, String acno, String orgnCode) throws ApplicationException {
        List<CbsMandateDetailPojo> dataList = new ArrayList<CbsMandateDetailPojo>();
        try {
            String query = "";
            if (npciFileType.equalsIgnoreCase("ACH")) {
                if (function.equals("EDIT")) {
                    if (modifyCriteria.equalsIgnoreCase("CREDIT")) {
                        query = " and trans_type in ('CREATE','AMEND') and flag in('N','F','P','S') and Debtor_Acno='" + acno + "'";
                    } else if (modifyCriteria.equalsIgnoreCase("DEBIT")) {
                        query = " and trans_type in ('CREATE','AMEND') and flag in('N','F','P','S') and Creditor_Acno='" + acno + "'";
                    }
                } else if (function.equals("AMEND")) {
                    if (modifyCriteria.equalsIgnoreCase("CREDIT")) {
                        query = " and trans_type in ('CREATE') and flag='Y' and Debtor_Acno='" + acno + "'";
                    } else if (modifyCriteria.equalsIgnoreCase("DEBIT")) {
                        query = " and trans_type in ('CREATE') and flag='Y' and Creditor_Acno='" + acno + "'";
                    }
                } else if (function.equals("CANCEL")) {
                    if (modifyCriteria.equalsIgnoreCase("CREDIT")) {
                        query = " and trans_type in ('CREATE','AMEND') and flag='Y' and Debtor_Acno='" + acno + "'";
                    } else if (modifyCriteria.equalsIgnoreCase("DEBIT")) {
                        query = " and trans_type in ('CREATE','AMEND') and flag='Y' and Creditor_Acno='" + acno + "'";
                    }
                }
            } else if (npciFileType.equalsIgnoreCase("ECS")) {
                if (modifyCriteria.equalsIgnoreCase("CREDIT")) {
                    query = " and trans_type in ('CREATE') and flag='N' and Debtor_Acno='" + acno + "'";
                } else if (modifyCriteria.equalsIgnoreCase("DEBIT")) {
                    query = " and trans_type in ('CREATE') and flag='N' and Creditor_Acno='" + acno + "'";
                }
            }

            List list = em.createNativeQuery("SELECT Trans_Type,CBS_Umrn,CHI_Umrn,Proprietary,Category,Amount_Flag,Amount,"
                    + " Frequency,Sequence_Type,"
                    + " Period_Type,date_format(From_Date,'%d/%m/%Y'),date_format(To_Date,'%d/%m/%Y'),Creditor_Acno,Creditor_Name,Creditor_AcType,Creditor_BankName,"
                    + " Creditor_IFSC,Creditor_Mobile,Creditor_Email,Creditor_Utility_Code,Debtor_Acno,Debtor_Name,"
                    + " Debtor_AcType,Debtor_BankName,Debtor_IFSC,Debtor_Mobile,Debtor_Email,Debtor_Utility_Code,"
                    + " Ref_1,Ref_2,Update_Mode,Flag,Debtor_FinCodeType,Creditor_FinCodeType from cbs_mandate_detail where "
                    + " Mandate_Receiving_Branch ='" + orgnCode + "' and TxnFileType='" + npciFileType + "'  " + query + " ").getResultList();
            for (int i = 0; i < list.size(); i++) {
                CbsMandateDetailPojo to = new CbsMandateDetailPojo();
                Vector ele = (Vector) list.get(i);
                to.setTransType(ele.get(0).toString().trim());
                to.setcBSUmrn(ele.get(1).toString().trim());
                to.setcHIUmrn(ele.get(2).toString().trim());
                to.setProprietary(ele.get(3).toString().trim());
                to.setCategory(ele.get(4).toString().trim());
                to.setAmountFlag(ele.get(5).toString().trim());
                to.setAmount(ele.get(6).toString().trim());
                to.setFrequency(ele.get(7).toString().trim());
                to.setSequenceType(ele.get(8).toString().trim());
                to.setPeriodType(ele.get(9).toString().trim());
                to.setFromDate(ele.get(10).toString().trim());
                to.setToDate(ele.get(11) == null ? "" : ele.get(11).toString().trim());
                to.setCreditorAcno(ele.get(12).toString().trim());
                to.setCreditorName(ele.get(13).toString().trim());
                to.setCreditorAcType(ele.get(14).toString().trim());
                to.setCreditorBankName(ele.get(15).toString().trim());
                to.setCreditorIFSC(ele.get(16).toString().trim());
                to.setCreditorMobile(ele.get(17).toString().trim());
                to.setCreditorEmail(ele.get(18).toString().trim());
                to.setCreditorUtilityCode(ele.get(19).toString().trim());
                to.setDebtorAcno(ele.get(20).toString().trim());
                to.setDebtorName(ele.get(21).toString().trim());
                to.setDebtorAcType(ele.get(22).toString().trim());
                to.setDebtorBankName(ele.get(23).toString().trim());
                to.setDebtorIFSC(ele.get(24).toString().trim());
                to.setDebtorMobile(ele.get(25).toString().trim());
                to.setDebtorEmail(ele.get(26).toString().trim());
                to.setDebtorUtilityCode(ele.get(27).toString().trim());
                to.setRef1(ele.get(28).toString().trim());
                to.setRef2(ele.get(29).toString().trim());
                to.setUpdateMode(ele.get(30).toString().trim());
                to.setFlag(ele.get(31).toString().trim());
                to.setDebtorFinCodeType(ele.get(32).toString().trim());
                to.setCreditorFinCodeType(ele.get(33).toString().trim());
                dataList.add(to);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public Long getUmrn(String txnFileType) throws ApplicationException {
        Long umrn = 0l;
        try {
            String query = "";
            if (txnFileType.equalsIgnoreCase("ECS")) {
                query = "select ifnull(max(cast(substring(CBS_Umrn from 4) as unsigned)),0) "
                        + "as umrn from cbs_mandate_detail where TxnFileType='" + txnFileType + "' ";
            }
            if (txnFileType.equalsIgnoreCase("ACH")) {

                query = "select ifnull(max(cast(substring(CBS_Umrn from 5) as unsigned)),0) "
                        + "as umrn from cbs_mandate_detail where TxnFileType='" + txnFileType + "' ";
            }
            List list = em.createNativeQuery(query).getResultList();
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                umrn = Long.parseLong(ele.get(0).toString()) + 1;
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return umrn;
    }

    public String mandateProcess(CbsMandateDetailPojo cbsMandtDtl) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List list = em.createNativeQuery("select CBS_Umrn from cbs_mandate_detail where"
                    + " CBS_Umrn='" + cbsMandtDtl.getcBSUmrn() + "'").getResultList();
            if (cbsMandtDtl.getTransType().equals("CREATE")) {
                if (!list.isEmpty()) {
                    throw new ApplicationException("This UMRN is already exists.");
                }
                //    cbsMandtDtl.setFlag("N");
                int n = em.createNativeQuery("INSERT INTO cbs_mandate_detail (TxnFileType,Trans_Type, CBS_Umrn, CHI_Umrn, Proprietary,"
                        + " Category, Amount_Flag, Amount, Frequency, Sequence_Type, Period_Type, From_Date, To_Date, "
                        + "Creditor_Acno, Creditor_Name, Creditor_AcType, Creditor_BankName, Creditor_IFSC, Creditor_Mobile, "
                        + "Creditor_Email, Debtor_Acno, Debtor_Name, Debtor_AcType, Debtor_BankName, Debtor_IFSC, "
                        + "Debtor_Mobile, Debtor_Email, Debtor_Utility_Code, Ref_1, Ref_2, Flag, Mandate_Receiving_Branch, "
                        + "Mandate_Received_By, Mandate_Received_Date, Update_By, Update_Date, Update_Mode, "
                        + "Response_Code_In_Updation, Response_Detail_In_Updation, Uploaded_Zip_Name, Response_File_Name,"
                        + "Creditor_Utility_Code,Mandate_Received_Time,Debtor_FinCodeType,Creditor_FinCodeType) VALUES"
                        + "('" + cbsMandtDtl.getTxnFileType() + "','" + cbsMandtDtl.getTransType() + "','" + cbsMandtDtl.getcBSUmrn() + "','" + cbsMandtDtl.getcHIUmrn() + "',"
                        + "'" + cbsMandtDtl.getProprietary() + "','" + cbsMandtDtl.getCategory() + "','" + cbsMandtDtl.getAmountFlag() + "',"
                        + "'" + cbsMandtDtl.getAmount() + "','" + cbsMandtDtl.getFrequency() + "','" + cbsMandtDtl.getSequenceType() + "',"
                        + "'" + cbsMandtDtl.getPeriodType() + "','" + cbsMandtDtl.getFromDate() + "','" + cbsMandtDtl.getToDate() + "',"
                        + "'" + cbsMandtDtl.getCreditorAcno() + "','" + cbsMandtDtl.getCreditorName() + "','" + cbsMandtDtl.getCreditorAcType() + "',"
                        + "'" + cbsMandtDtl.getCreditorBankName() + "','" + cbsMandtDtl.getCreditorIFSC() + "','" + cbsMandtDtl.getCreditorMobile() + "',"
                        + "'" + cbsMandtDtl.getCreditorEmail() + "','" + cbsMandtDtl.getDebtorAcno() + "','" + cbsMandtDtl.getDebtorName() + "',"
                        + "'" + cbsMandtDtl.getDebtorAcType() + "','" + cbsMandtDtl.getDebtorBankName() + "','" + cbsMandtDtl.getDebtorIFSC() + "',"
                        + "'" + cbsMandtDtl.getDebtorMobile() + "','" + cbsMandtDtl.getDebtorEmail() + "','" + cbsMandtDtl.getDebtorUtilityCode() + "',"
                        + "'" + cbsMandtDtl.getRef1() + "','" + cbsMandtDtl.getRef2() + "','" + cbsMandtDtl.getFlag() + "','" + cbsMandtDtl.getMandateReceivingBranch() + "',"
                        + "'" + cbsMandtDtl.getMandateReceivedBy() + "','" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "',"
                        + "'','" + ymd.format(dmy.parse(cbsMandtDtl.getMandateReceivedDate())) + "','','','','','','" + cbsMandtDtl.getCreditorUtilityCode() + "',"
                        + "'" + yyyymmdd.format(new Date()) + "','" + cbsMandtDtl.getDebtorFinCodeType() + "','" + cbsMandtDtl.getCreditorFinCodeType() + "')").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("This UMRN is already exists.");
                }
            } else if (cbsMandtDtl.getTransType().equals("EDIT") || cbsMandtDtl.getTransType().equals("AMEND")
                    || cbsMandtDtl.getTransType().equals("CANCEL")) {
                List list1 = em.createNativeQuery("select CBS_Umrn from cbs_mandate_detail "
                        + "where CBS_Umrn='" + cbsMandtDtl.getcBSUmrn() + "' and "
                        + "Mandate_Receiving_Branch = '" + cbsMandtDtl.getMandateReceivingBranch() + "' ").getResultList();
                if (list1.isEmpty()) {
                    throw new ApplicationException("This UMRN is not exists.");
                }
                String updateQuery = "";
                if (cbsMandtDtl.getTransType().equals("AMEND") || cbsMandtDtl.getTransType().equals("EDIT")) {
                    if (cbsMandtDtl.getTransType().equals("AMEND")) {
                        //       cbsMandtDtl.setFlag("N");
                        cbsMandtDtl.setTransTypePrevious("AMEND");
                    } else {
                        cbsMandtDtl.setUpdateMode("EDIT");
                    }
                    updateQuery = "UPDATE cbs_mandate_detail SET Trans_Type='" + cbsMandtDtl.getTransTypePrevious() + "', Proprietary='" + cbsMandtDtl.getProprietary() + "',"
                            + " Category='" + cbsMandtDtl.getCategory() + "', Amount_Flag='" + cbsMandtDtl.getAmountFlag() + "',"
                            + " Amount='" + cbsMandtDtl.getAmount() + "',Frequency='" + cbsMandtDtl.getFrequency() + "',"
                            + " Sequence_Type='" + cbsMandtDtl.getSequenceType() + "', Period_Type='" + cbsMandtDtl.getPeriodType() + "',"
                            + " From_Date='" + cbsMandtDtl.getFromDate() + "',To_Date='" + cbsMandtDtl.getToDate() + "',"
                            + " Creditor_Acno='" + cbsMandtDtl.getCreditorAcno() + "',Flag='" + cbsMandtDtl.getFlag() + "',"
                            + " Creditor_Name='" + cbsMandtDtl.getCreditorName() + "',Creditor_AcType='" + cbsMandtDtl.getCreditorAcType() + "',"
                            + " Creditor_BankName='" + cbsMandtDtl.getCreditorBankName() + "',Creditor_IFSC='" + cbsMandtDtl.getCreditorIFSC() + "',"
                            + " Creditor_Mobile='" + cbsMandtDtl.getCreditorMobile() + "',Creditor_Email='" + cbsMandtDtl.getCreditorEmail() + "',"
                            + " Creditor_Utility_Code='" + cbsMandtDtl.getCreditorUtilityCode() + "',Debtor_Acno='" + cbsMandtDtl.getDebtorAcno() + "',"
                            + " Debtor_Name='" + cbsMandtDtl.getDebtorName() + "',Debtor_AcType='" + cbsMandtDtl.getDebtorAcType() + "',"
                            + " Debtor_BankName='" + cbsMandtDtl.getDebtorBankName() + "',Debtor_IFSC='" + cbsMandtDtl.getDebtorIFSC() + "',"
                            + " Debtor_Mobile='" + cbsMandtDtl.getDebtorMobile() + "', Debtor_Email='" + cbsMandtDtl.getDebtorEmail() + "',"
                            + " Debtor_Utility_Code='" + cbsMandtDtl.getDebtorUtilityCode() + "', flag='" + cbsMandtDtl.getFlag() + "',Ref_1='" + cbsMandtDtl.getRef1() + "',"
                            + " Ref_2='" + cbsMandtDtl.getRef2() + "',Update_By='" + cbsMandtDtl.getUpdateBy() + "',"
                            + " Update_Date=now(),Update_Mode='" + cbsMandtDtl.getUpdateMode() + "', "
                            + " Debtor_FinCodeType='" + cbsMandtDtl.getDebtorFinCodeType() + "', "
                            + "Creditor_FinCodeType='" + cbsMandtDtl.getCreditorFinCodeType() + "',response_code_in_updation='',"
                            + "response_detail_in_updation='',uploaded_zip_name='',response_file_name=''"
                            + " WHERE  CBS_Umrn='" + cbsMandtDtl.getcBSUmrn() + "' "
                            + " and Mandate_Receiving_Branch='" + cbsMandtDtl.getMandateReceivingBranch() + "'";
                } else if (cbsMandtDtl.getTransType().equals("CANCEL")) {
                    //       cbsMandtDtl.setFlag("N");
                    cbsMandtDtl.setTransTypePrevious("CANCLE");
                    updateQuery = "UPDATE cbs_mandate_detail SET Trans_Type='" + cbsMandtDtl.getTransTypePrevious() + "',"
                            + " Flag='" + cbsMandtDtl.getFlag() + "',"
                            + " Update_Date='" + ymd.format(dmy.parse(cbsMandtDtl.getUpdateDate())) + "',Update_By='" + cbsMandtDtl.getUpdateBy() + "'"
                            + " WHERE  CBS_Umrn='" + cbsMandtDtl.getcBSUmrn() + "' "
                            + " and Mandate_Receiving_Branch='" + cbsMandtDtl.getMandateReceivingBranch() + "'";
                }
                int n = em.createNativeQuery("insert into cbs_mandate_detail_his(TxnFileType,Trans_Type,CBS_Umrn,CHI_Umrn,Proprietary,Category,Amount_Flag,Amount,Frequency,"
                        + " Sequence_Type,Period_Type,From_Date,To_Date,Creditor_Acno,Creditor_Name,Creditor_AcType,Creditor_BankName,Creditor_IFSC,"
                        + " Creditor_Mobile,Creditor_Email,Creditor_Utility_Code,Debtor_Acno,Debtor_Name,Debtor_AcType,Debtor_BankName,Debtor_IFSC,Debtor_Mobile,Debtor_Email,"
                        + " Debtor_Utility_Code,Ref_1,Ref_2,Flag,Mandate_Receiving_Branch,Mandate_Received_By,Mandate_Received_Date,Update_By,Update_Date,"
                        + " Update_Mode,Response_Code_In_Updation,Response_Detail_In_Updation,Uploaded_Zip_Name,Response_File_Name,Mandate_Received_Time,Debtor_FinCodeType,Creditor_FinCodeType) "
                        + " select TxnFileType, Trans_Type,CBS_Umrn,CHI_Umrn,Proprietary,Category,Amount_Flag,Amount,Frequency,Sequence_Type,Period_Type,From_Date,"
                        + " To_Date,Creditor_Acno,Creditor_Name,Creditor_AcType,Creditor_BankName,Creditor_IFSC,Creditor_Mobile,Creditor_Email,Creditor_Utility_Code,"
                        + " Debtor_Acno,Debtor_Name,Debtor_AcType,Debtor_BankName,Debtor_IFSC,Debtor_Mobile,Debtor_Email,Debtor_Utility_Code,"
                        + " Ref_1,Ref_2,Flag,Mandate_Receiving_Branch,Mandate_Received_By,Mandate_Received_Date,Update_By,Update_Date,Update_Mode,"
                        + " Response_Code_In_Updation,Response_Detail_In_Updation,Uploaded_Zip_Name,Response_File_Name,Mandate_Received_Time,Debtor_FinCodeType,Creditor_FinCodeType  from  cbs_mandate_detail"
                        + " where CBS_Umrn='" + cbsMandtDtl.getcBSUmrn() + "' and Mandate_Receiving_Branch = '" + cbsMandtDtl.getMandateReceivingBranch() + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("problem in history insertion..!");
                }
                n = em.createNativeQuery(updateQuery).executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in mandate " + cbsMandtDtl.getTransType() + " Updation...!");
                }
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ex.printStackTrace();
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "true";
    }

    public String getRefRecCode(String refRecNo, String refDesc) throws ApplicationException {
        String refCode = "";
        try {
            List list = em.createNativeQuery("select ref_code from cbs_ref_rec_type where "
                    + "ref_rec_no='" + refRecNo + "' and ref_desc='" + refDesc + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please define master date for:" + refRecNo);
            }
            Vector ele = (Vector) list.get(0);
            refCode = ele.get(0).toString().trim();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return refCode;
    }

    @Override
    public List findAllFileSeqNoForCreditInward(String fileComingDt, String iwType) throws ApplicationException {
        try {
            List list;
            if (iwType.equalsIgnoreCase("ACH")) {
                list = em.createNativeQuery("select distinct file_name from "
                        + "cbs_npci_inward where iw_type='" + iwType + "' and "
                        + "settlement_date='" + fileComingDt + "'").getResultList();
            } else {
                list = em.createNativeQuery("select distinct ach_item_seq_no from "
                        + "cbs_npci_inward where iw_type='" + iwType + "' and "
                        + "settlement_date='" + fileComingDt + "'").getResultList();
            }
            if (list.isEmpty()) {
                throw new ApplicationException("There is no Seq No on this date.");
            }
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public List findH2HAllFileSeqNoForCreditInward(String fileComingDt, String iwType) throws ApplicationException {
        try {
            List list;
            if (iwType.equalsIgnoreCase("ACH")) {
                list = em.createNativeQuery("select distinct file_name from "
                        + "cbs_npci_inward where iw_type='" + iwType + "' and "
                        + "settlement_date='" + fileComingDt + "' and file_gen_flag='N'").getResultList();
            } else {
                list = em.createNativeQuery("select distinct ach_item_seq_no from "
                        + "cbs_npci_inward where iw_type='" + iwType + "' and "
                        + "settlement_date='" + fileComingDt + "' and file_gen_flag='N'").getResultList();
            }
            if (list.isEmpty()) {
                throw new ApplicationException("There is no Seq No on this date.");
            }
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public List<NpciOacDto> getDataForEcsCrInComingDateAndSeqNo(String fileComingDt, String seqNo,
            String iwType, String brnCode, String selectedBranch) throws ApplicationException {
        List<NpciOacDto> dataList = new ArrayList<>();
        try {
            String alphaCode = commonReport.getAlphacodeByBrncode(selectedBranch);
            List list = new ArrayList();
            if (alphaCode.equalsIgnoreCase("HO")) {
                List iinList = em.createNativeQuery("select iin from mb_sms_sender_bank_detail").getResultList();
                Vector iinEle = (Vector) iinList.get(0);
                if (iinEle.get(0) == null || iinEle.get(0).toString().trim().equals("")
                        || iinEle.get(0).toString().trim().length() > 9) {
                    throw new ApplicationException("Please define IIN.");
                }
                String iinNo = ParseFileUtil.addTrailingZeros(iinEle.get(0).toString().trim(), 9);
                if (iwType.equalsIgnoreCase("ACH")) {
                    list = em.createNativeQuery("select dest_bank_iin,ifnull(dest_actype,''),ifnull(bene_name,''),"
                            + "user_name_narration,user_credit_reference,amount,ifnull(dest_bank_acno,''),status,"
                            + "reason,cbs_acno,cbs_acname,auth_by,ach_product_type from cbs_npci_inward where "
                            + "iw_type='" + iwType + "' and settlement_date='" + fileComingDt + "' and "
                            + "dest_bank_iin='" + iinNo + "' and substring_index(substring_index(file_name,'-',-2),'-',1)='" + seqNo + "' and "
                            + "status='U' and (reason like'%No Such Account%' or reason like'%Frozen%' or "
                            + "reason like'%Account Closed or Transferred%' or reason like'%Invalid Account Status%')").getResultList();
                } else {
                    list = em.createNativeQuery("select dest_bank_iin,ifnull(dest_actype,''),ifnull(bene_name,''),"
                            + "user_name_narration,user_credit_reference,amount,ifnull(dest_bank_acno,''),status,"
                            + "reason,cbs_acno,cbs_acname,auth_by,ach_product_type from cbs_npci_inward where iw_type='" + iwType + "' and "
                            + "settlement_date='" + fileComingDt + "' and ach_item_seq_no='" + seqNo + "' "
                            + "and dest_bank_iin='" + iinNo + "'").getResultList();
                }
            } else {
                String[] arr = getIfscAndMicrCodeByBrnCode(selectedBranch);
                if (iwType.equalsIgnoreCase("ACH")) {
                    list = em.createNativeQuery("select dest_bank_iin,ifnull(dest_actype,''),ifnull(bene_name,''),"
                            + "user_name_narration,user_credit_reference,amount,ifnull(dest_bank_acno,''),status,"
                            + "reason,cbs_acno,cbs_acname,auth_by,ach_product_type from cbs_npci_inward where iw_type='" + iwType + "' and "
                            + "settlement_date='" + fileComingDt + "' and (dest_bank_iin='" + arr[0] + "' or "
                            + "dest_bank_iin='" + arr[1] + "') and substring_index(substring_index(file_name,'-',-2),'-',1)='" + seqNo + "' and "
                            + "status='U' and (reason like'%No Such Account%' or reason like'%Frozen%' or "
                            + "reason like'%Account Closed or Transferred%' or reason like'%Invalid Account Status%')").getResultList();
                } else {
                    list = em.createNativeQuery("select dest_bank_iin,ifnull(dest_actype,''),ifnull(bene_name,''),"
                            + "user_name_narration,user_credit_reference,amount,ifnull(dest_bank_acno,''),status,"
                            + "reason,cbs_acno,cbs_acname,auth_by,ach_product_type from cbs_npci_inward where iw_type='" + iwType + "' and "
                            + "settlement_date='" + fileComingDt + "' and ach_item_seq_no='" + seqNo + "' and "
                            + "(dest_bank_iin='" + arr[0] + "' or dest_bank_iin='" + arr[1] + "')").getResultList();
                }
            }
            if (list.isEmpty()) {
                if (iwType.equalsIgnoreCase("ACH")) {
                    return new ArrayList<NpciOacDto>();
                } else {
                    throw new ApplicationException("There is no data to verify.");
                }
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                NpciOacDto dto = new NpciOacDto();

                dto.setSno(String.valueOf(i + 1));
                dto.setMicr(ele.get(0).toString().trim());
                dto.setAcType(ele.get(1).toString().trim());
                dto.setOldAcName(ele.get(2).toString().trim());
                dto.setUserName(ele.get(3).toString().trim());
                dto.setTranRef(ele.get(4).toString().trim());
                BigDecimal amt = new BigDecimal(ele.get(5).toString().trim()).divide(new BigDecimal("100"));
                dto.setAmount(formatter.format(amt.doubleValue()));
                dto.setOldAcno(ele.get(6).toString().trim());
                String status = ele.get(7).toString().trim();
                if (status.equalsIgnoreCase("U")) {
                    dto.setStatus("UnSuccess");
                } else if (status.equalsIgnoreCase("S")) {
                    dto.setStatus("Success");
                } else {
                    dto.setStatus("");
                }
                dto.setReason(ele.get(8).toString().trim());
                dto.setCbsAcno(ele.get(9).toString().trim());
                dto.setCbsName(ele.get(10).toString().trim());
                dto.setAuthBy(ele.get(11).toString().trim());
                dto.setAchProductType(ele.get(12).toString().trim());

                dataList.add(dto);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public String[] getIfscAndMicrCodeByBrnCode(String orgnCode) throws ApplicationException {
        String[] arr = new String[2];
        try {
            String ifscCode = "", micrNo = "";
            List list = em.createNativeQuery("select ifnull(ifsc_code,'') from  branchmaster "
                    + "where brncode = " + Integer.parseInt(orgnCode) + "").getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                ifscCode = ele.get(0).toString().trim();
            }

            list = em.createNativeQuery("select ifnull(b.micr,'') as city_code,ifnull(b.micrcode,'') as "
                    + "bank_code,ifnull(b.branchcode,'') as brach_code from bnkadd b,branchmaster m where "
                    + "b.alphacode=m.alphacode and m.brncode=" + Integer.parseInt(orgnCode) + "").getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                String cityCode = ele.get(0).toString().trim();
                String bankCode = ele.get(1).toString().trim();
                String branchCode = ele.get(2).toString().trim();

                micrNo = ParseFileUtil.addTrailingZeros(cityCode, 3) + ParseFileUtil.addTrailingZeros(bankCode, 3)
                        + ParseFileUtil.addTrailingZeros(branchCode, 3);
            }
            arr[0] = ifscCode;
            arr[1] = micrNo;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return arr;
    }

    //New CECS Inward Credit Card Processing.
    @Override
    public String processCecsInwCredit(NpciOacDto obj, String userName, String todayDt, String orgnBrCode,
            String iwType, String settlementDt, String achSeqNo, String processAcNo, String processAcHolderName,
            String selectedBranch, String verificationStatus, String mode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        List<SmsToBatchTo> smsBatchList = new ArrayList<>();
        try {
            String result = "", queryParam = "";
            if (verificationStatus == null) {
                verificationStatus = "";
            }

            if (iwType.equalsIgnoreCase("ACH")) { //For ACH-CR-UNSUCCESS Verification
                verificationStatus = "P";
                queryParam = " and substring_index(substring_index(file_name,'-',-2),'-',1)='" + achSeqNo + "'";
            } else {
                queryParam = " and ach_item_seq_no='" + achSeqNo + "'";
            }

            int varifiACNameMismatchModule = commonReport.getCodeByReportName("ECSACH-CR-VERF-NAMEMISMCH");
            if (varifiACNameMismatchModule == 1 && (verificationStatus.equalsIgnoreCase("R"))) {
                return "true";
            }
            ut.begin();
            //Twice Verification Checking
//            if (!(varifiACNameMismatchModule == 1 && verificationStatus.equalsIgnoreCase("P"))) {
            if (!verificationStatus.equalsIgnoreCase("P")) { //P will only in case of pass when name mismatch in ECS only
                List list = em.createNativeQuery("select dest_bank_iin from cbs_npci_inward where "
                        + "iw_type='" + iwType + "' and settlement_date='" + settlementDt + "' and "
                        + "ach_item_seq_no='" + achSeqNo + "' and "
                        + "user_credit_reference='" + obj.getTranRef() + "' and status<>''").getResultList();
                if ((!list.isEmpty())) {
                    throw new ApplicationException("This entry has been already verified.");
                }
            }

            //Ifsc,Micr,IIN Checking
            String alphaCode = commonReport.getAlphacodeByBrncode(selectedBranch);
            if (alphaCode.equalsIgnoreCase("HO")) {
                List iinList = em.createNativeQuery("select iin from mb_sms_sender_bank_detail").getResultList();
                Vector iinEle = (Vector) iinList.get(0);
                if (iinEle.get(0) == null || iinEle.get(0).toString().trim().equals("")
                        || iinEle.get(0).toString().trim().length() > 9) {
                    throw new ApplicationException("Please define IIN.");
                }
                String iinNo = ParseFileUtil.addTrailingZeros(iinEle.get(0).toString().trim(), 9);

                List brList = em.createNativeQuery("select ifnull(b.micr,'') as city_code,ifnull(b.micrcode,'') as "
                        + "bank_code,ifnull(b.branchcode,'') as brach_code,ifnull(ifsc_code,''), m.brncode from "
                        + "bnkadd b,branchmaster m where b.alphacode=m.alphacode ").getResultList();

                if (!(npciRemote.isBankIfsc(brList, obj.getMicr().trim()) || npciRemote.isBankMicr(brList, obj.getMicr().trim())
                        || obj.getMicr().trim().equalsIgnoreCase(iinNo))) {
                    int n = em.createNativeQuery("update cbs_npci_inward set status='U',"
                            + "reason='Ifsc Code Not Found' where iw_type='" + iwType + "' and "
                            + "settlement_date='" + settlementDt + "' and "
                            + "user_credit_reference='" + obj.getTranRef() + "'" + queryParam).executeUpdate();
                    if (n <= 0) {
                        throw new ApplicationException("Problem in status updation. Error code-002");
                    }
                    ut.commit();
                    return "true";
                }
            } else {
                List brList = em.createNativeQuery("select ifnull(b.micr,'') as city_code,ifnull(b.micrcode,'') as "
                        + "bank_code,ifnull(b.branchcode,'') as brach_code,ifnull(ifsc_code,''), m.brncode from "
                        + "bnkadd b,branchmaster m where b.alphacode=m.alphacode ").getResultList();
                if (!(npciRemote.isBankIfsc(brList, obj.getMicr().trim())
                        || npciRemote.isBankMicr(brList, obj.getMicr().trim()))) {
                    int n = em.createNativeQuery("update cbs_npci_inward set status='U',"
                            + "reason='Ifsc Code Not Found' where iw_type='" + iwType + "' and "
                            + "settlement_date='" + settlementDt + "' and "
                            + "user_credit_reference='" + obj.getTranRef() + "'" + queryParam).executeUpdate();
                    if (n <= 0) {
                        throw new ApplicationException("Problem in status updation. Error code-002");
                    }
                    ut.commit();
                    return "true";
                }
            }
            //Account number and account status checking
            List list = em.createNativeQuery("select accstatus from accountmaster where acno='" + processAcNo + "'").getResultList();
            if (list == null || list.isEmpty()) {
                int n = em.createNativeQuery("update cbs_npci_inward set status='U',"
                        + "reason='No Such Account' where iw_type='" + iwType + "' and "
                        + "settlement_date='" + settlementDt + "' and "
                        + "user_credit_reference='" + obj.getTranRef() + "'" + queryParam).executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in status updation. Error code-003");
                }
                ut.commit();
                return "true";
            }

            Vector ele = (Vector) list.get(0);
            int acStatus = Integer.parseInt(ele.get(0).toString().trim());
            if (acStatus == 9) {
                int n = em.createNativeQuery("update cbs_npci_inward set status='U',"
                        + "reason='Account Closed or Transferred' where iw_type='" + iwType + "' and "
                        + "settlement_date='" + settlementDt + "' and "
                        + "user_credit_reference='" + obj.getTranRef() + "'" + queryParam).executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in status updation. Error code-004");
                }
                ut.commit();
                return "true";
            }
            
            if (acStatus == 2) {
                int n = em.createNativeQuery("update cbs_npci_inward set status='U',"
                        + "reason='Inoperative' where iw_type='" + iwType + "' and "
                        + "settlement_date='" + settlementDt + "' and "
                        + "user_credit_reference='" + obj.getTranRef() + "'" + queryParam).executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in status updation. Error code-044");
                }
                ut.commit();
                return "true";
            }
            
            if (acStatus == 15) {
                int n = em.createNativeQuery("update cbs_npci_inward set status='U',"
                        + "reason='Deaf' where iw_type='" + iwType + "' and "
                        + "settlement_date='" + settlementDt + "' and "
                        + "user_credit_reference='" + obj.getTranRef() + "'" + queryParam).executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in status updation. Error code-045");
                }
                ut.commit();
                return "true";
            }
            
            
            
            //Account Holder Name Validation
//            if (!verificationStatus.equalsIgnoreCase("P")) {
            if (!verificationStatus.equalsIgnoreCase("P") && !mode.equalsIgnoreCase("H2H")) {
                list = em.createNativeQuery("select custname from accountmaster where custname='" + processAcHolderName.trim() + "' "
                        + " and acno='" + processAcNo + "'").getResultList();
                if (list == null || list.isEmpty()) {
                    int n = em.createNativeQuery("update cbs_npci_inward set status='U',"
                            + "reason='account holder name invalid' where iw_type='" + iwType + "' and "
                            + "settlement_date='" + settlementDt + "' and "
                            + "user_credit_reference='" + obj.getTranRef() + "'" + queryParam).executeUpdate();
                    if (n <= 0) {
                        throw new ApplicationException("Problem in status updation. Error code-004");
                    }
                    ut.commit();
                    return "true";
                }
            }
            //Transaction preliminaries
            String glAccount = "";
            ParameterinfoReportDAO paramDao = new ParameterinfoReportDAO(em);
            AbbParameterInfoDAO abbParameterInfoDAO = new AbbParameterInfoDAO(em);

            ParameterinfoReport paramEntity = paramDao.getCodeByReportName("APB-ACH-HEAD");
            if (paramEntity.getCode() == null
                    || paramEntity.getCode().toString().equals("")
                    || paramEntity.getCode().toString().trim().length() > 1) {
                throw new ApplicationException("Please define code for::APB-ACH-HEAD");
            }
            if (!(paramEntity.getCode() == 0 || paramEntity.getCode() == 1)) {
                throw new ApplicationException("Please define proper code for APB-ACH-HEAD");
            }
            String apbAchHead = paramEntity.getCode().toString().trim();

            List<AbbParameterInfo> abbParameterInfoList = null;
            if (apbAchHead.equals("1")) {
                abbParameterInfoList = abbParameterInfoDAO.getEntityByPurpose("NPCI-INW-CR");
            } else if (apbAchHead.equals("0")) {
                String bankCode = ftsRemote.getBankCode();
                if (bankCode.equalsIgnoreCase("khat")) {
                    if (!obj.getAchProductType().equals("")) { //ACH-ECS
                        abbParameterInfoList = abbParameterInfoDAO.getEntityByPurpose("ACH-INW-CR");
                    } else { //OLD ECS
                        abbParameterInfoList = abbParameterInfoDAO.getEntityByPurpose("ECS-INW-CR");
                    }
                } else {
                    if (iwType.equalsIgnoreCase("ACH")) {
                        abbParameterInfoList = abbParameterInfoDAO.getEntityByPurpose("ACH-INW-CR");
                    } else {
                        abbParameterInfoList = abbParameterInfoDAO.getEntityByPurpose("ECS-INW-CR");
                    }
                }
            }
            if (abbParameterInfoList.isEmpty()) {
                throw new ApplicationException("Please define proper head for NPCI.");
            }
            for (AbbParameterInfo abbPojo : abbParameterInfoList) {
                if (alphaCode.equalsIgnoreCase("HO")) {
                    glAccount = orgnBrCode + abbPojo.getAcno();
                } else {
                    glAccount = "90" + abbPojo.getAcno();
                }
            }
            //Transaction processing
            Float trsNo = ftsRemote.getTrsNo();
            String loginAlphaCode = commonReport.getAlphacodeByBrncode(orgnBrCode);
            String narration = "By NPCI " + obj.getUserName() + " " + obj.getTranRef(); //"NPCI-ECS-Cr_" + ymd.format(dmy.parse(todayDt)) + "_Entry_"+ obj.getUserName()
            if (loginAlphaCode.equalsIgnoreCase("HO")) {
                result = ibtRemote.cbsPostingSx(processAcNo, 0, ymd.format(dmy.parse(todayDt)),
                        Double.parseDouble(obj.getAmount()), 0f, 2, narration, 0f, "A", "", "", 3, 0f, ftsRemote.getRecNo(), 66, processAcNo.substring(0, 2),
                        orgnBrCode, userName, userName, trsNo, "", "");
                if (result.substring(0, 4).equalsIgnoreCase("true")) {
                    result = ibtRemote.cbsPostingCx(glAccount, 1, ymd.format(dmy.parse(todayDt)),
                            Double.parseDouble(obj.getAmount()), 0f, 2, narration,
                            0f, "A", "", "", 3, 0f, ftsRemote.getRecNo(), 66, orgnBrCode, orgnBrCode, userName, userName,
                            trsNo, "", "");
                    if (result.substring(0, 4).equalsIgnoreCase("true")) {
                        result = ftsRemote.updateBalance(ftsRemote.getAccountNature(glAccount), glAccount, 0,
                                Double.parseDouble(obj.getAmount()), "Y", "Y");
                        if (result.equalsIgnoreCase("true")) {
                            ut.commit();
                            String updateResult = "";
                            try {
                                updateResult = updateStatusAndReason("S", "", iwType, settlementDt, achSeqNo,
                                        obj.getTranRef(), processAcNo, ftsRemote.getCustName(processAcNo));
                                if (updateResult.equalsIgnoreCase("true")) {
                                    return "true";
                                }
                            } catch (Exception ex) {
                                return updateResult;
                            }
                            //Adding Object For Sms
                            SmsToBatchTo to = new SmsToBatchTo();
                            to.setAcNo(processAcNo);
                            to.setCrAmt(Double.parseDouble(obj.getAmount()));
                            to.setDrAmt(0d);
                            to.setTranType(2);
                            to.setTy(0);
                            to.setTxnDt(todayDt);
                            to.setTemplate(SmsType.TRANSFER_DEPOSIT);
                            smsBatchList.add(to);
                            //End
                        } else {
                            ut.rollback();
                            String updateResult = "";
                            try {
                                updateResult = updateStatusAndReason("U", result, iwType, settlementDt, achSeqNo, obj.getTranRef(), "", "");
                                if (updateResult.equalsIgnoreCase("true")) {
                                    return "true";
                                }
                            } catch (Exception ex) {
                                return updateResult;
                            }
                        }
                    } else {
                        ut.rollback();
                        String updateResult = "";
                        try {
                            updateResult = updateStatusAndReason("U", result, iwType, settlementDt, achSeqNo, obj.getTranRef(), "", "");
                            if (updateResult.equalsIgnoreCase("true")) {
                                return "true";
                            }
                        } catch (Exception ex) {
                            return updateResult;
                        }
                    }
                } else {
                    ut.rollback();
                    String updateResult = "";
                    try {
                        updateResult = updateStatusAndReason("U", result, iwType, settlementDt, achSeqNo, obj.getTranRef(), "", "");
                        if (updateResult.equalsIgnoreCase("true")) {
                            return "true";
                        }
                    } catch (Exception ex) {
                        return updateResult;
                    }
                }
            } else {
                //Branch level verification.
                result = ibtRemote.cbsPostingSx(glAccount, 1, ymd.format(dmy.parse(todayDt)),
                        Double.parseDouble(obj.getAmount()), 0f, 2, narration, 0f, "A", "", "", 3, 0f, ftsRemote.getRecNo(), 66, "90",
                        orgnBrCode, userName, userName, trsNo, "", "");
                if (result.substring(0, 4).equalsIgnoreCase("true")) {
                    result = ftsRemote.updateBalance(ftsRemote.getAccountNature(glAccount), glAccount, 0,
                            Double.parseDouble(obj.getAmount()), "Y", "Y");
                    if (result.equalsIgnoreCase("true")) {
                        result = ibtRemote.cbsPostingCx(processAcNo, 0, ymd.format(dmy.parse(todayDt)),
                                Double.parseDouble(obj.getAmount()), 0f, 2, narration,
                                0f, "A", "", "", 3, 0f, ftsRemote.getRecNo(), 66, orgnBrCode, orgnBrCode, userName, userName,
                                trsNo, "", "");
                        if (result.substring(0, 4).equalsIgnoreCase("true")) {
                            ut.commit();
                            String updateResult = "";
                            try {
                                updateResult = updateStatusAndReason("S", "", iwType, settlementDt, achSeqNo, obj.getTranRef(),
                                        processAcNo, ftsRemote.getCustName(processAcNo));
                                if (updateResult.equalsIgnoreCase("true")) {
                                    return "true";
                                }
                            } catch (Exception ex) {
                                return updateResult;
                            }
                            //Adding Object For Sms
                            SmsToBatchTo to = new SmsToBatchTo();
                            to.setAcNo(processAcNo);
                            to.setCrAmt(Double.parseDouble(obj.getAmount()));
                            to.setDrAmt(0d);
                            to.setTranType(2);
                            to.setTy(0);
                            to.setTxnDt(todayDt);
                            to.setTemplate(SmsType.TRANSFER_DEPOSIT);
                            smsBatchList.add(to);
                            //End
                        } else {
                            ut.rollback();
                            String updateResult = "";
                            try {
                                updateResult = updateStatusAndReason("U", result, iwType, settlementDt, achSeqNo, obj.getTranRef(), "", "");
                                if (updateResult.equalsIgnoreCase("true")) {
                                    return "true";
                                }
                            } catch (Exception ex) {
                                return updateResult;
                            }
                        }
                    } else {
                        ut.rollback();
                        String updateResult = "";
                        try {
                            updateResult = updateStatusAndReason("U", result, iwType, settlementDt, achSeqNo, obj.getTranRef(), "", "");
                            if (updateResult.equalsIgnoreCase("true")) {
                                return "true";
                            }
                        } catch (Exception ex) {
                            return updateResult;
                        }
                    }
                } else {
                    ut.rollback();
                    String updateResult = "";
                    try {
                        updateResult = updateStatusAndReason("U", result, iwType, settlementDt, achSeqNo, obj.getTranRef(), "", "");
                        if (updateResult.equalsIgnoreCase("true")) {
                            return "true";
                        }
                    } catch (Exception ex) {
                        return updateResult;
                    }
                }
            }
//            ut.commit();
            //Sending Sms
            try {
                smsFacade.sendSmsToBatch(smsBatchList);
            } catch (Exception e) {
                System.out.println("Problem In SMS Sending To Batch In "
                        + "Npci Inward Credit." + e.getMessage());
            }
            //End here
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }

        return "true";
    }

    public String updateStatusAndReason(String status, String reason, String iwType, String settlementDt,
            String achSeqNo, String tranRefNo, String cbsAcno, String CbsName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int n;
            if (iwType.equalsIgnoreCase("ACH")) {
                n = em.createNativeQuery("update cbs_npci_inward set status='" + status + "',"
                        + "reason='" + reason + "',cbs_acno='" + cbsAcno + "',cbs_acname='" + CbsName + "' "
                        + "where iw_type='" + iwType + "' and settlement_date='" + settlementDt + "' and "
                        + "substring_index(substring_index(file_name,'-',-2),'-',1)='" + achSeqNo + "' and user_credit_reference='" + tranRefNo + "'").executeUpdate();
            } else {
                n = em.createNativeQuery("update cbs_npci_inward set status='" + status + "',"
                        + "reason='" + reason + "',cbs_acno='" + cbsAcno + "',cbs_acname='" + CbsName + "' "
                        + "where iw_type='" + iwType + "' and settlement_date='" + settlementDt + "' and "
                        + "ach_item_seq_no='" + achSeqNo + "' and user_credit_reference='" + tranRefNo + "'").executeUpdate();
            }

            if (n <= 0) {
                throw new ApplicationException("Problem in status updation. Error code-004");
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
        return "true";
    }

    public String generateEcsIputFile(String fileType, String fileGenDt, String orgnBrCode, String enterBy,
            String todayDt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String aadharLocation = "", fileNo = "", bankCode = "", genFileName = "", totalRecord = "";
        try {
            ut.begin();
            List list = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + enterBy + "'").getResultList();
            Vector elem = (Vector) list.get(0);
            if (list.isEmpty() || elem.get(0) == null || elem.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to generate the files.");
            }
            String npciUserName = elem.get(0).toString().trim();

            List dataList = em.createNativeQuery("select unique_ref_no,micr,ac_type,acno,name,amount "
                    + "from cbs_npci_ecs_input_txn where type='" + fileType + "' and "
                    + "enter_date='" + ymd.format(dmy.parse(fileGenDt)) + "' and flag='T'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no data to generate the files.");
            }
            //Required Values Extraction.
            list = em.createNativeQuery("select ifnull(a.code,'') as CR_HI,ifnull(b.code,'') as CR_DI,ifnull(c.code,'') "
                    + "as DR_HI,ifnull(d.code,'') as DR_DI,ifnull(e.code,'') as U_NO,ifnull(f.code,'') as U_NAME,"
                    + "ifnull(g.code,'') as MICR from (select ifnull((select code from cbs_parameterinfo  where "
                    + "name='CECS-CR-INP-HI'),'') as code) a,(select ifnull((select code from cbs_parameterinfo "
                    + "where name='CECS-CR-INP-DI'),'') as code) b,(select ifnull((select code from cbs_parameterinfo "
                    + "where name='CECS-DR-INP-HI'),'') as code) c,(select ifnull((select code from cbs_parameterinfo "
                    + "where name='CECS-DR-INP-DI'),'') as code) d,(select ifnull((select code from cbs_parameterinfo "
                    + "where name='CECS-INP-UNO'),'') as code) e,(select ifnull((select code from cbs_parameterinfo "
                    + "where name='CECS-INP-UNM'),'') as code) f,(select ifnull((select code from cbs_parameterinfo "
                    + "where name='CECS-INP-MICR'),'') as code) g").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please define required values.");
            }
            elem = (Vector) list.get(0);
            String creditHi = elem.get(0).toString().trim();
            String creditDi = elem.get(1).toString().trim();
            String debitHi = elem.get(2).toString().trim();
            String debitDi = elem.get(3).toString().trim();
            String userNo = elem.get(4).toString().trim();
            String userName = elem.get(5).toString().trim().toUpperCase();
            String bankMicr = elem.get(6).toString().trim();

            if (creditHi.equals("") || creditDi.equals("") || debitHi.equals("")
                    || debitDi.equals("") || userNo.equals("") || userName.equals("") || bankMicr.equals("")
                    || creditHi.length() != 2 || creditDi.length() != 2 || debitHi.length() != 2
                    || debitDi.length() != 2 || userName.length() > 20 || bankMicr.length() != 9) {
                throw new ApplicationException("Please define required values for file generation.");
            }

            list = em.createNativeQuery("select aadhar_location,npci_bank_code from mb_sms_sender_bank_detail").getResultList();
            elem = (Vector) list.get(0);
            if (elem.get(0) == null || elem.get(1) == null || elem.get(0).toString().trim().equals("")
                    || elem.get(1).toString().trim().equals("") || elem.get(1).toString().trim().length() != 4) {
                throw new ApplicationException("Please define Aadhar Location and Bank Code.");
            }
            aadharLocation = elem.get(0).toString().trim();
            bankCode = elem.get(1).toString().trim();

            list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
                    + "where file_gen_date='" + ymd.format(dmy.parse(todayDt)) + "' and "
                    + "file_gen_type='" + fileType + "'").getResultList();
            elem = (Vector) list.get(0);
            fileNo = "1";
            if (elem.get(0) != null) {
                fileNo = elem.get(0).toString().trim();
            }
            if (fileType.equalsIgnoreCase("ECT")) { //ECS Credit Input
                genFileName = "ACH-CR-" + bankCode.toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
                        + ymdOne.format(new Date()) + "-" + ParseFileUtil.addTrailingZeros(fileNo, 6) + "-INP.txt";
            } else if (fileType.equalsIgnoreCase("EDT")) { //ECS Debit Input
                genFileName = "ECS-DR-" + bankCode.toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
                        + ymdOne.format(new Date()) + "-" + ParseFileUtil.addTrailingZeros(fileNo, 6) + "-INP.txt";
            }

            int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                    + "file_gen_time,file_gen_brncode,file_gen_type) values(" + Integer.parseInt(fileNo) + ",'"
                    + ymd.format(dmy.parse(todayDt)) + "','" + genFileName + "','" + enterBy + "',now(),'"
                    + orgnBrCode + "','" + fileType + "')").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In ECS File Generation.");
            }

            BigDecimal totalSubAmt = new BigDecimal(0);
            for (int i = 0; i < dataList.size(); i++) {
                elem = (Vector) dataList.get(i);
                BigDecimal individualAmt = new BigDecimal(elem.get(5).toString().trim()).divide(new BigDecimal(100));
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

            totalRecord = String.valueOf(dataList.size()).trim(); //Make to 9 digit.
            //Header Preparation.
            FileWriter fw = new FileWriter(aadharLocation + genFileName);
            String header = "";
            if (fileType.equalsIgnoreCase("ECT")) { //ECS Credit Input
                header = creditHi + ParseFileUtil.addSuffixSpaces("", 7) + ParseFileUtil.addSuffixSpaces(userName, 40)
                        + ParseFileUtil.addSuffixSpaces("", 14) + ParseFileUtil.addTrailingZeros("", 9)
                        + ParseFileUtil.addSuffixSpaces("", 9) + ParseFileUtil.addSuffixSpaces("", 15)
                        + ParseFileUtil.addSuffixSpaces("", 3) + ParseFileUtil.addTrailingZeros("", 13)
                        + amtInPaisa + ymdOne.format(ymd.parse(CbsUtil.dateAdd(ymd.format(dmy.parse(todayDt)), 1)))
                        + ParseFileUtil.addSuffixSpaces("", 10) + ParseFileUtil.addSuffixSpaces("", 10)
                        + ParseFileUtil.addSuffixSpaces("", 3) + ParseFileUtil.addSuffixSpaces(userNo, 18)
                        + ParseFileUtil.addSuffixSpaces(bankCode.toUpperCase() + ymd.format(dmy.parse(todayDt)), 18)
                        + ParseFileUtil.addSuffixSpaces(bankMicr, 11)
                        + ParseFileUtil.addSuffixSpaces("", 35) + ParseFileUtil.addTrailingZeros(totalRecord, 9)
                        + ParseFileUtil.addSuffixSpaces("", 2) + ParseFileUtil.addSuffixSpaces("", 57) + "\n";
            } else if (fileType.equalsIgnoreCase("EDT")) { //ECS Debit Input
                header = debitHi + ParseFileUtil.addSuffixSpaces(userNo, 7) + ParseFileUtil.addSuffixSpaces(userName, 40)
                        + ParseFileUtil.addSuffixSpaces("", 14) + ParseFileUtil.addSuffixZeros("", 9) + bankMicr
                        + ParseFileUtil.addSuffixSpaces("", 15) + ParseFileUtil.addSuffixSpaces("", 3)
                        + ParseFileUtil.addSuffixZeros("", 13) + amtInPaisa
                        + ymdOne.format(ymd.parse(CbsUtil.dateAdd(ymd.format(dmy.parse(todayDt)), 1)))
                        + ParseFileUtil.addSuffixSpaces("", 10) + ParseFileUtil.addSuffixSpaces("", 10)
                        + ParseFileUtil.addSuffixSpaces("", 3) + "\n";
            }
            fw.write(header);
            //Data Preparation.
            for (int i = 0; i < dataList.size(); i++) {
                Vector ele = (Vector) dataList.get(i);
                String uniqueRefNo = ele.get(0).toString().trim();
                String destMicr = ele.get(1).toString().trim();
                String destAcType = ele.get(2).toString().trim();
                String acno = ele.get(3).toString().trim();
                String name = ele.get(4).toString().trim().toUpperCase();
                String amount = ele.get(5).toString().trim();

                String individualStr = "";
                if (fileType.equalsIgnoreCase("ECT")) { //ECS Credit Input
                    individualStr = creditDi + ParseFileUtil.addSuffixSpaces("", 9) + ParseFileUtil.addTrailingZeros(destAcType, 2)
                            + ParseFileUtil.addSuffixSpaces("", 3) + ParseFileUtil.addSuffixSpaces("", 15)
                            + ParseFileUtil.addSuffixSpaces(name, 40) + ParseFileUtil.addSuffixSpaces("", 9)
                            + ParseFileUtil.addSuffixSpaces("", 7) + ParseFileUtil.addSuffixSpaces(userName, 20)
                            + ParseFileUtil.addSuffixSpaces("", 13) + amount + ParseFileUtil.addSuffixSpaces("", 10)
                            + ParseFileUtil.addSuffixSpaces("", 10) + ParseFileUtil.addSuffixSpaces("", 1)
                            + ParseFileUtil.addSuffixSpaces("", 2) + ParseFileUtil.addSuffixSpaces(destMicr, 11)
                            + ParseFileUtil.addSuffixSpaces(acno, 35) + ParseFileUtil.addSuffixSpaces(bankMicr, 11)
                            + ParseFileUtil.addSuffixSpaces(userNo, 18) + ParseFileUtil.addSuffixSpaces(uniqueRefNo, 30)
                            + "ECS" + ParseFileUtil.addSuffixSpaces("", 15) + ParseFileUtil.addSuffixSpaces("", 20)
                            + ParseFileUtil.addSuffixSpaces("", 7) + "\n";
                } else if (fileType.equalsIgnoreCase("EDT")) { //ECS Debit Input
                    individualStr = debitDi + destMicr + ParseFileUtil.addTrailingZeros(destAcType, 2)
                            + ParseFileUtil.addSuffixSpaces("", 3) + ParseFileUtil.addSuffixSpaces(acno, 15)
                            + ParseFileUtil.addSuffixSpaces(name, 40) + bankMicr + ParseFileUtil.addSuffixSpaces(userNo, 7)
                            + ParseFileUtil.addSuffixSpaces(userName, 20) + uniqueRefNo.substring(0, 13) + amount
                            + ParseFileUtil.addSuffixSpaces("", 10) + ParseFileUtil.addSuffixSpaces("", 10)
                            + ParseFileUtil.addSuffixSpaces("", 1) + ParseFileUtil.addSuffixSpaces("", 2) + "\n";
                }
                fw.write(individualStr);

                //Updation of flag in table cbs_npci_ecs_input_txn.
                n = em.createNativeQuery("update cbs_npci_ecs_input_txn set flag='G' where "
                        + "unique_ref_no='" + uniqueRefNo + "' and type='" + fileType + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Updation problem in ECS input generation.");
                }
            }
            fw.close();
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "true";
    }

    public List<NpciFileDto> showGeneratedFiles(String fileType, String fromDate, String toDate) throws ApplicationException {
        List<NpciFileDto> dataList = new ArrayList<NpciFileDto>();
        try {
            List list = em.createNativeQuery("select file_no,date_format(file_gen_date,'%d/%m/%Y'),file_name,"
                    + " file_gen_by from cbs_npci_mapper_files where (file_gen_date BETWEEN '" + ymd.format(dmy.parse(fromDate)) + "' AND '" + ymd.format(dmy.parse(toDate)) + "') and "
                    + " file_gen_type='" + fileType + "'").getResultList();
//"select file_no,date_format(file_gen_date,'%d/%m/%Y'),file_name,"
//                    + "file_gen_by from cbs_npci_mapper_files where file_gen_date='" + ymd.format(dmy.parse(dt)) + "' and "
//                    + "file_gen_type='" + fileType + "'"
            if (list.isEmpty()) {
                throw new ApplicationException("There is no data to show.");
            }
            for (int i = 0; i < list.size(); i++) {
                NpciFileDto dto = new NpciFileDto();
                Vector ele = (Vector) list.get(i);

                dto.setFileNo(new BigInteger(ele.get(0).toString()));
                dto.setFileGenDt(ele.get(1).toString());
                dto.setFileName(ele.get(2).toString());
                dto.setFileGenBy(ele.get(3).toString());

                dataList.add(dto);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List retrieveTxnData(String txnType, String orgnCode) throws ApplicationException {
        try {
            return em.createNativeQuery("select unique_ref_no,micr,ac_type,acno,name,amount,own_bank_acno,"
                    + "entry_by from cbs_npci_ecs_input_detail where type='" + txnType + "' and "
                    + "substring(own_bank_acno,1,2)='" + orgnCode + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String processEcsInputTxn(List<NpciInputPojo> processList, String txnType,
            String userName, String todayDt, String orgnCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String ectHead = "", edtHead = "";
        List<SmsToBatchTo> smsBatchList = new ArrayList<SmsToBatchTo>();
        SimpleDateFormat ymdms = new SimpleDateFormat("yyMMddHHmmssSSS");
        try {
            ut.begin();
            Integer code = ftsRemote.getCodeForReportName("ECS-INPUT-PARAM");
            List list = em.createNativeQuery("select ifnull(a.code,'') as CR_INPUT_HEAD,ifnull(b.code,'') as "
                    + "DR_INPUT_HEAD,ifnull(c.code,'') as ECS_INPUT_HEAD from (select ifnull((select code from "
                    + "cbs_parameterinfo  where name='ECS-CR-INPUT-HEAD'),'') as code) a,(select ifnull((select "
                    + "code from cbs_parameterinfo where name='ECS-DR-INPUT-HEAD'),'') as code) b,(select "
                    + "ifnull((select code from cbs_parameterinfo where name='ECS-INPUT-HEAD'),'') as code) c").getResultList();
            Vector ele = (Vector) list.get(0);
            String crInputHead = ele.get(0).toString().trim();
            String drInputHead = ele.get(1).toString().trim();
            String inputHead = ele.get(2).toString().trim();
            if (code == 0 && (crInputHead.equals("") || crInputHead.length() != 12
                    || drInputHead.equals("") || drInputHead.length() != 12)) {
                throw new ApplicationException("Please define cbs param-->ECS-CR-INPUT-HEAD and ECS-DR-INPUT-HEAD");
            } else if (code == 1 && (inputHead.equals("") || inputHead.length() != 12)) {
                throw new ApplicationException("Please define cbs param-->ECS-INPUT-HEAD");
            }

            if (code == 0) {
                ectHead = crInputHead;
                edtHead = drInputHead;
            } else if (code == 1) {
                ectHead = inputHead;
                edtHead = inputHead;
            }

            Float trsNo = ftsRemote.getTrsNo();
            for (NpciInputPojo obj : processList) {
                if (txnType.equalsIgnoreCase("ECT")) {
                    String result = ibtRemote.cbsPostingSx(ectHead, 0, ymd.format(dmy.parse(todayDt)),
                            Double.parseDouble(obj.getAmount()), 0f, 2, "NPCI-ECS-CrInput_" + ymd.format(dmy.parse(todayDt))
                            + "_Entry", 0f, "A", "", "", 3, 0f, ftsRemote.getRecNo(), 66, "90", orgnCode, userName,
                            userName, trsNo, "", "");
                    if (!result.substring(0, 4).equalsIgnoreCase("true")) {
                        throw new ApplicationException(result + "::A/c No Is-->" + ectHead);
                    }
                    result = ftsRemote.updateBalance("PO", ectHead, Double.parseDouble(obj.getAmount()), 0, "Y", "Y");
                    if (!result.equalsIgnoreCase("true")) {
                        throw new ApplicationException(result + "::A/c No Is-->" + ectHead);
                    }
                    result = ibtRemote.cbsPostingCx(obj.getOwnAcno(), 1, ymd.format(dmy.parse(todayDt)),
                            Double.parseDouble(obj.getAmount()), 0f, 2, "NPCI-ECS-CrInput_" + todayDt + "_Entry",
                            0f, "A", "", "", 3, 0f, ftsRemote.getRecNo(), 66, orgnCode, orgnCode, userName,
                            userName, trsNo, "", "");
                    if (!result.substring(0, 4).equalsIgnoreCase("true")) {
                        throw new ApplicationException(result + "::A/c No Is-->" + obj.getOwnAcno());
                    }
                    result = ftsRemote.updateBalance(ftsRemote.getAccountNature(obj.getOwnAcno()),
                            obj.getOwnAcno(), 0, Double.parseDouble(obj.getAmount()), "Y", "Y");
                    if (!result.equalsIgnoreCase("true")) {
                        throw new ApplicationException(result + "::A/c No Is-->" + obj.getOwnAcno());
                    }
                } else if (txnType.equalsIgnoreCase("EDT")) {
                    String result = ibtRemote.cbsPostingSx(edtHead, 1, ymd.format(dmy.parse(todayDt)),
                            Double.parseDouble(obj.getAmount()), 0f, 2, "NPCI-ECS-DrInput_" + ymd.format(dmy.parse(todayDt))
                            + "_Entry", 0f, "A", "", "", 3, 0f, ftsRemote.getRecNo(), 66, "90", orgnCode, userName,
                            userName, trsNo, "", "");
                    if (!result.substring(0, 4).equalsIgnoreCase("true")) {
                        throw new ApplicationException(result + "::A/c No Is-->" + edtHead);
                    }
                    result = ftsRemote.updateBalance("PO", edtHead, 0, Double.parseDouble(obj.getAmount()), "Y", "Y");
                    if (!result.equalsIgnoreCase("true")) {
                        throw new ApplicationException(result + "::A/c No Is-->" + edtHead);
                    }
                    result = ibtRemote.cbsPostingCx(obj.getOwnAcno(), 0, ymd.format(dmy.parse(todayDt)),
                            Double.parseDouble(obj.getAmount()), 0f, 2, "NPCI-ECS-DrInput_" + todayDt + "_Entry",
                            0f, "A", "", "", 3, 0f, ftsRemote.getRecNo(), 66, orgnCode, orgnCode, userName,
                            userName, trsNo, "", "");
                    if (!result.substring(0, 4).equalsIgnoreCase("true")) {
                        throw new ApplicationException(result + "::A/c No Is-->" + obj.getOwnAcno());
                    }
                    result = ftsRemote.updateBalance(ftsRemote.getAccountNature(obj.getOwnAcno()),
                            obj.getOwnAcno(), Double.parseDouble(obj.getAmount()), 0, "Y", "Y");
                    if (!result.equalsIgnoreCase("true")) {
                        throw new ApplicationException(result + "::A/c No Is-->" + obj.getOwnAcno());
                    }
                    String acctNature = ftsRemote.getAccountNature(obj.getOwnAcno());
                    //Emi updation if exists for that a/c
                    if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)
                            || acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)
                            || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                        int intPostOnDeposit = ftsRemote.getCodeForReportName("INT_POST_DEPOSIT");
//                        if (intPostOnDeposit == 0) {
//                            String tmpNpaStatus = txnAuth.cbsAcStatus(obj.getOwnAcno());
//                            if (tmpNpaStatus.equals("11") || tmpNpaStatus.equals("12")
//                                    || tmpNpaStatus.equals("13") || tmpNpaStatus.equals("14")) {
//                                BigDecimal currentBal = new BigDecimal(ftsRemote.ftsGetBal(obj.getOwnAcno()));
//
//                                if (ibtRemote.npaPOrdChk(obj.getOwnAcno()).equalsIgnoreCase("true")) {
//                                    String msgRecAmt = ibtRemote.npaRecoveryAmtUpdation(obj.getOwnAcno(),
//                                            ymd.format(dmy.parse(todayDt)), Double.parseDouble(obj.getAmount()),
//                                            userName, orgnCode, orgnCode, ymd.format(dmy.parse(todayDt)), trsNo);
//                                    if (!msgRecAmt.equalsIgnoreCase("true")) {
//                                        throw new ApplicationException(msgRecAmt);
//                                    }
//                                } else if (ibtRemote.npaPOrdChk(obj.getOwnAcno()).equalsIgnoreCase("false")) {
//                                    double totInstallAmt =0;
//                                    if(acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
//                                        List installAmtList = em.createNativeQuery("select ifnull(sum(INSTALLAMT),0) from emidetails where acno = '" + obj.getOwnAcno() + "' and duedt<='" + ymd.format(dmy.parse(todayDt)) + "'").getResultList();
//                                        Vector instllAmtVect = (Vector) installAmtList.get(0);
//                                        totInstallAmt = Double.parseDouble(instllAmtVect.get(0).toString());
//                                    } else {
//                                        List drAmtList = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0)  from "
//                                                    + " (select ifnull(sum(ifnull(dramt,0)),0)  as dramt from "+commonReport.getTableName(acctNature)+" where acno =  '" + obj.getOwnAcno() + "' and dt<='" + ymd.format(dmy.parse(todayDt)) + "'  and auth = 'Y' and details not like '%INTT. TRF FOR MEM%' and details not like '%INTT. REC FOR MEM%' "
//                                                    + " union all "
//                                                    + " select ifnull(sum(ifnull(dramt,0)),0)  as dramt  from npa_recon where acno =  '" + obj.getOwnAcno() + "' and dt<='" + ymd.format(dmy.parse(todayDt)) + "'  and auth = 'Y' and details not like '%INTT. TRF FOR MEM%' and details not like '%INTT. REC FOR MEM%' ) a").getResultList();
//                                        Vector drAmtVect = (Vector) drAmtList.get(0);                            
//                                        totInstallAmt = Double.parseDouble(drAmtVect.get(0).toString());
//                                    }                           
//
//                                    List crAmtList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)),0)  from "
//                                            + "(select ifnull(sum(ifnull(cramt,0)),0)  as cramt from "+commonReport.getTableName(acctNature)+" where acno =  '" + obj.getOwnAcno() + "' and dt<='" + ymd.format(dmy.parse(todayDt)) + "'  and auth = 'Y' and details not like '%INTT. TRF FOR MEM%' and details not like '%INTT. REC FOR MEM%' "
//                                            + "union all "
//                                            + "select ifnull(sum(ifnull(cramt,0)),0)  as cramt  from npa_recon where acno =  '" + obj.getOwnAcno() + "' and dt<='" + ymd.format(dmy.parse(todayDt)) + "'   and auth = 'Y' and details not like '%INTT. TRF FOR MEM%' and details not like '%INTT. REC FOR MEM%') a").getResultList();
//                                    Vector crAmtVect = (Vector) crAmtList.get(0);
//                                    double totCrAmt = Double.parseDouble(crAmtVect.get(0).toString());
//
//                                    List drAmtList = em.createNativeQuery("select ifnull(sum(ifnull(dramt,0)),0)  from "
//                                            + " (select ifnull(sum(ifnull(dramt,0)),0)  as dramt from "+commonReport.getTableName(acctNature)+" where acno =  '" + obj.getOwnAcno() + "' and dt<='" + ymd.format(dmy.parse(todayDt)) + "'  and auth = 'Y' and trandesc in (3,4) and details not like '%INTT. TRF FOR MEM%' and details not like '%INTT. REC FOR MEM%' "
//                                            + " union all "
//                                            + " select ifnull(sum(ifnull(dramt,0)),0)  as dramt  from npa_recon where acno =  '" + obj.getOwnAcno() + "' and dt<='" + ymd.format(dmy.parse(todayDt)) + "'  and auth = 'Y' and trandesc in (3,4)  and details not like '%INTT. TRF FOR MEM%' and details not like '%INTT. REC FOR MEM%' ) a").getResultList();
//                                    Vector drAmtVect = (Vector) drAmtList.get(0);
//                                    double totIntDrAmt = Double.parseDouble(drAmtVect.get(0).toString());
//                                    double remainPriAmt = totInstallAmt - totIntDrAmt;
//                                    if ((remainPriAmt > 0) && (remainPriAmt <= totCrAmt)) {
//                                        String msg = ibtRemote.npaRecoveryAmtUpdation(obj.getOwnAcno(), ymd.format(dmy.parse(todayDt)), Double.parseDouble(obj.getAmount()), userName, orgnCode, orgnCode, ymd.format(dmy.parse(todayDt)), trsNo);
//                                        if (!msg.equalsIgnoreCase("True")) {
//                                            throw new ApplicationException(msg);
//                                        }
//                                    }
//                                }
//                            }
//                        }

                        list = em.createNativeQuery("select acno from emidetails where "
                                + "acno='" + obj.getOwnAcno() + "' and status='Unpaid'").getResultList();
                        if (!list.isEmpty()) {
                            result = ftsRemote.loanDisbursementInstallment(obj.getOwnAcno(),
                                    Double.parseDouble(obj.getAmount()), 0, "System", ymd.format(new Date()),
                                    ftsRemote.getRecNo(), 1, "Through CECS Inp Entry");
                            if (!result.equalsIgnoreCase("true")) {
                                throw new ApplicationException("Emi details updation issue-->" + result);
                            }
                        }
                    }
                    //Emd Here
                }
                //Insertion into ecs transaction table.
                String amtInPaise = new BigDecimal(obj.getAmount()).multiply(new BigDecimal("100")).toString();
                if (amtInPaise.contains(".")) {
                    amtInPaise = amtInPaise.substring(0, amtInPaise.indexOf("."));
                }
                amtInPaise = ParseFileUtil.addTrailingZeros(amtInPaise, 13);

                int n = em.createNativeQuery("INSERT INTO cbs_npci_ecs_input_txn(unique_ref_no,type,micr,ac_type,acno,"
                        + "name,amount,own_bank_acno,ledge_folio_no,ach_item_seq_no,checksum,flag,reason_code,"
                        + "entry_by,enter_date,enter_time,file_gen_by,file_gen_date,detail_ref_no,ECS_Tran_Code,Filler,Response_File_Name) "
                        + "VALUES('" + ymdms.format(new Date()) + "','" + txnType + "','" + obj.getMicr() + "',"
                        + "'" + obj.getAcType() + "','" + obj.getAcNo() + "','" + obj.getName() + "',"
                        + "'" + amtInPaise + "','" + obj.getOwnAcno() + "','','','','T','','" + userName + "','"
                        + ymd.format(dmy.parse(todayDt)) + "',now(),'',now(),"
                        + "'" + obj.getuRefNo() + "','','','')").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in ecs txn");
                }
                //Adding Object For Sms
                SmsToBatchTo to = new SmsToBatchTo();
                to.setAcNo(obj.getOwnAcno());
                to.setTxnDt(todayDt);
                to.setTranType(2);

                if (txnType.equalsIgnoreCase("ECT")) {
                    to.setCrAmt(Double.parseDouble(obj.getAmount()));
                    to.setDrAmt(0d);
                    to.setTy(0);
                    to.setTemplate(SmsType.TRANSFER_DEPOSIT);
                } else if (txnType.equalsIgnoreCase("EDT")) {
                    to.setCrAmt(0d);
                    to.setDrAmt(Double.parseDouble(obj.getAmount()));
                    to.setTy(1);
                    to.setTemplate(SmsType.TRANSFER_WITHDRAWAL);
                }
                smsBatchList.add(to);
                //End
            }
            ut.commit();
            try {
                smsFacade.sendSmsToBatch(smsBatchList);
            } catch (Exception e) {
                System.out.println("Problem In SMS Sending To Batch In "
                        + "CECS Cr-Dr Input Generation." + e.getMessage());
            }
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "true";
    }

    public String generateECS306ReturnFiles(String fileGenDt, String orgnBrCode, String enterBy,
            String todayDt, String seqNo, String processingMode, String h2hLocation) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        ParameterinfoReportDAO paramDao = new ParameterinfoReportDAO(em);
        String headerId = "", detailId = "", aadharLocation = "", headerDestIIN = "";
        String fileNo = "", totalRecordNo = "", bankCode = "", genFileName = "", settlementCycle = "";
        try {
            ut.begin();

            if (processingMode.equalsIgnoreCase("H2H")) {
                List list = em.createNativeQuery("select * from cbs_npci_mapper_files where "
                        + "FILE_GEN_TYPE='EHI' AND DATE_OF_FILE_GEN='" + ymdSql.format(dmy.parse(fileGenDt)) + "' AND FILE_GEN_SEQN='" + seqNo + "'").getResultList();
//                Vector ele = (Vector) list.get(0);
                if (list.size() >= 1) {
                    throw new ApplicationException("ACH-CR ECS Return File already Generated...");
                }
            }
            List list = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + enterBy + "'").getResultList();
            Vector ele = (Vector) list.get(0);
            if (list.isEmpty() || ele.get(0) == null || ele.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to generate the files.");
            }
            String npciUserName = ele.get(0).toString().trim();

            list = em.createNativeQuery("select dest_bank_iin from cbs_npci_inward where iw_type='ECS' and "
                    + "settlement_date='" + ymd.format(dmy.parse(fileGenDt)) + "' and "
                    + "ach_item_seq_no='" + seqNo + "' and status=''").getResultList();
            if (!list.isEmpty()) {
                throw new ApplicationException("Total entries were not verified for: "
                        + "Settlement Date->" + fileGenDt + " And Seq No-->" + seqNo);
            }

            List dataList = em.createNativeQuery("select apbs_tran_code,bene_name,user_name_narration,amount,reserved_one,"
                    + "ach_checksum,dest_bank_iin,dest_bank_acno,sponsor_bank_iin,user_no,user_credit_reference,"
                    + "ach_product_type,ifnull(ach_settlement_cycle,''),status,reason,ach_header_dest_iin,dest_actype,ledger_no"
                    + " from cbs_npci_inward where settlement_date='" + ymd.format(dmy.parse(fileGenDt)) + "' and "
                    + "ach_item_seq_no='" + seqNo + "' and iw_type='ECS'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no data to generate the files.");
            }
            //Required Header Values.
            for (int h = 0; h < 1; h++) { //Only For First Iteration.
                Vector hVec = (Vector) dataList.get(0);
                headerDestIIN = hVec.get(15).toString();        //Make to desired length
                settlementCycle = hVec.get(12).toString();      //Make to desired length
            }
            //Required Values Extraction. These values are also used in ACH
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
                throw new ApplicationException("Please define IIN, Location and Bank Code.");
            }
            aadharLocation = ele.get(1).toString().trim();
            bankCode = ele.get(2).toString().trim(); //As it is

            list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
                    + "where file_gen_date='" + ymd.format(dmy.parse(fileGenDt)) + "' and file_gen_type='EHI'").getResultList();
            ele = (Vector) list.get(0);
            fileNo = "1";
            if (ele.get(0) != null) {
                fileNo = ele.get(0).toString().trim();
            }
            if (processingMode.equalsIgnoreCase("H2H")) {
                List H2huserList = em.createNativeQuery("select name,code from cbs_parameterinfo where name='NPCI-H2H-USER'").getResultList();
                Vector v = (Vector) H2huserList.get(0);
                if (H2huserList.isEmpty() || v.get(1) == null || v.get(1).toString().equalsIgnoreCase("")) {
                    throw new ApplicationException("H2H User can not be blank.");
                }
                npciUserName = v.get(1).toString();
//                genFileName = "ACH-CR-" + bankCode.toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
//                        + ymdOne.format(dmy.parse(fileGenDt)) + "-" + seqNo + "-RTN.txt";

                genFileName = "ACH-CR-" + bankCode.toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
                        + ymdOne.format(new Date()) + "-" + seqNo + "-RTN.txt";
            } else {
                genFileName = "ACH-CR-" + bankCode.toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
                        + ymdOne.format(dmy.parse(fileGenDt)) + "-" + seqNo + "-RTN.txt";
            }
            int n = 0;
            int fileGenFlag = 0;
            if (processingMode.equalsIgnoreCase("H2H")) {
                n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                        + "file_gen_time,file_gen_brncode,file_gen_type,seq_no,DATE_OF_FILE_GEN,FILE_GEN_SEQN) values(" + Integer.parseInt(fileNo) + ",'"
                        + ymd.format(dmy.parse(fileGenDt)) + "','" + genFileName + "','" + enterBy + "',now(),'"
                        + orgnBrCode + "','EHI'," + Double.parseDouble(seqNo.substring(6)) + ",'" + ymdSql.format(dmy.parse(fileGenDt)) + "','" + seqNo + "')").executeUpdate();  //Seq No without String part
                String updateFileGenFlagQuery = "UPDATE cbs_npci_inward SET file_gen_flag = 'Y' where ACH_ITEM_SEQ_NO='" + seqNo + "' "
                        + "and IW_TYPE='ECS' and SETTLEMENT_DATE='" + ymd.format(dmy.parse(fileGenDt)) + "'";
                fileGenFlag = em.createNativeQuery(updateFileGenFlagQuery).executeUpdate();
                if (fileGenFlag <= 0) {
                    throw new ApplicationException("Problem In Cbs ECS 306 Return Files Insertion.");
                }
            } else {
                n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                        + "file_gen_time,file_gen_brncode,file_gen_type,seq_no) values(" + Integer.parseInt(fileNo) + ",'"
                        + ymd.format(dmy.parse(fileGenDt)) + "','" + genFileName + "','" + enterBy + "',now(),'"
                        + orgnBrCode + "','EHI'," + Double.parseDouble(seqNo.substring(6)) + ")").executeUpdate();  //Seq No without String part
            }
            if (n <= 0) {
                throw new ApplicationException("Problem In Cbs ECS 306 Return Files Insertion.");
            }
            totalRecordNo = String.valueOf(dataList.size()).trim();
            BigDecimal totalSubAmt = new BigDecimal(0);
            for (int i = 0; i < dataList.size(); i++) {
                ele = (Vector) dataList.get(i);
                BigDecimal individualAmt = new BigDecimal(ele.get(3).toString().trim()).divide(new BigDecimal(100));
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
//            FileWriter fw = new FileWriter(aadharLocation + genFileName)
            FileWriter fw = null;
            String headerDt = "";
            if (processingMode.equalsIgnoreCase("H2H")) { //
                fw = new FileWriter(h2hLocation + genFileName);
                headerDt = ymdOne.format(new Date());
            } else {
                fw = new FileWriter(aadharLocation + genFileName);
                headerDt = ymdOne.format(dmy.parse(fileGenDt));
            }

            String header = headerId + ParseFileUtil.addSuffixSpaces("", 7) + ParseFileUtil.addSuffixSpaces("", 87)
                    + ParseFileUtil.addSuffixSpaces("", 7) + ParseFileUtil.addTrailingZeros(totalRecordNo, 9) + amtInPaisa
                    + headerDt + ParseFileUtil.addSuffixSpaces("", 27)
                    + ParseFileUtil.addSuffixSpaces(headerDestIIN, 11) + ParseFileUtil.addTrailingZeros(settlementCycle, 2)
                    + ParseFileUtil.addSuffixSpaces("", 132) + "." + "\n";
            fw.write(header);
            //Data Preparation.
            for (int i = 0; i < dataList.size(); i++) {
                Vector element = (Vector) dataList.get(i);
                String benName = element.get(1).toString();
                String userNameNarration = element.get(2).toString();
                String amount = element.get(3).toString();
                String reservedACHItemSeqNo = element.get(4).toString();
                String checksum = element.get(5).toString();
                String destBankIIN = element.get(6).toString();
                String destBankAcno = element.get(7).toString();
                String sponserBankIIN = element.get(8).toString();
                String userNumber = element.get(9).toString();
                String tranRefNo = element.get(10).toString();
                String achProductType = element.get(11).toString();
                String status = element.get(13).toString();
                String reason = element.get(14).toString();
                String destAcType = element.get(16).toString();
                String ledgerNo = element.get(17).toString();
                String reservedFlag = "1";  //For Success
                String reasonCode = "00";
                String customReasonDescOne = "";
                String customReasonDescTwo = "";
                if (status.equalsIgnoreCase("U")) {
                    reservedFlag = "0";     //For Un-Success
                    reasonCode = npciRemote.getAchReturnReasonCode(reason);
                    if (reasonCode.equalsIgnoreCase("04")) {
                        String[] arr = npciRemote.makeAchMiscellaneousReturnDescription(reason);
                        customReasonDescOne = arr[0];
                        customReasonDescTwo = arr[1];
                    }
                }
                String individualStr = detailId + ParseFileUtil.addSuffixSpaces("", 9) + ParseFileUtil.addTrailingZeros(destAcType, 2)
                        + ParseFileUtil.addSuffixSpaces(ledgerNo, 3) + ParseFileUtil.addSuffixSpaces(customReasonDescOne, 15)
                        + ParseFileUtil.addSuffixSpaces(benName, 40) + ParseFileUtil.addSuffixSpaces("", 8)
                        + ParseFileUtil.addSuffixSpaces(customReasonDescTwo, 8) + ParseFileUtil.addSuffixSpaces(userNameNarration, 20)
                        + ParseFileUtil.addSuffixSpaces("", 13) + amount + ParseFileUtil.addTrailingZeros(reservedACHItemSeqNo, 10)
                        + ParseFileUtil.addTrailingZeros(checksum, 10) + ParseFileUtil.addSuffixSpaces("", 7)
                        + ParseFileUtil.addSuffixSpaces(destBankIIN, 11) + ParseFileUtil.addSuffixSpaces(destBankAcno, 35)
                        + ParseFileUtil.addSuffixSpaces(sponserBankIIN, 11) + ParseFileUtil.addSuffixSpaces(userNumber, 18)
                        + ParseFileUtil.addSuffixSpaces(tranRefNo, 30) + ParseFileUtil.addSuffixSpaces(achProductType, 3)
                        + ParseFileUtil.addSuffixSpaces("", 15) + ParseFileUtil.addSuffixSpaces("", 20) + reservedFlag + reasonCode
                        + "\n";
                fw.write(individualStr);
            }
            fw.close();
            //In case of H2H OW
            if (processingMode.equalsIgnoreCase("H2H")) {
                h2hNpciRemote.writeEncryptedFiles();
                h2hNpciRemote.upload(props.getProperty("npciSftpHost").trim(), props.getProperty("npciSftpUser").trim(),
                        props.getProperty("npciSftpPassword").trim(), props.getProperty("cbs.ow.encrypted.location").trim(),
                        props.getProperty("npciSftpFileUploadLocation").trim());

                h2hNpciRemote.createBackupAndThenRemoveFile(props.getProperty("cbs.ow.location").trim(), props.getProperty("cbs.ow.bkp.location").trim());
                h2hNpciRemote.createBackupAndThenRemoveFile(props.getProperty("cbs.ow.encrypted.location").trim(), props.getProperty("cbs.ow.bkp.encrypted.location").trim());

                em.createNativeQuery("insert into cbs_npci_h2h_file_detail(file_date,file_name,file_type) "
                        + "values('" + ymd.format(new Date()) + "','" + genFileName + "','OW')").executeUpdate();
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
        return "true";
    }

    public String generateAchDr306ReturnFiles(String fileGenDt, String orgnBrCode, String enterBy,
            String todayDt, String seqNo, String processingMode, String h2hLocation) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String headerId = "", detailId = "", aadharLocation = "", headerDestIIN = "";
        String fileNo = "", totalRecordNo = "", bankCode = "", genFileName = "", settlementCycle = "";
        try {
            ut.begin();
            if (processingMode.equalsIgnoreCase("H2H")) {
                List list = em.createNativeQuery("select * from cbs_npci_mapper_files where "
                        + "FILE_GEN_TYPE='ACH-DR' AND DATE_OF_FILE_GEN='" + ymdSql.format(dmy.parse(fileGenDt)) + "' AND FILE_GEN_SEQN='" + seqNo + "'").getResultList();
                if (list.size() >= 1) {
                    throw new ApplicationException("ACH-DR Return File already Generated...");
                }
            }
            List list = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + enterBy + "'").getResultList();
            Vector ele = (Vector) list.get(0);
            if (list.isEmpty() || ele.get(0) == null || ele.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to generate the files.");
            }
            String npciUserName = ele.get(0).toString().trim();

//            list = em.createNativeQuery("select micr from cbs_npci_oac_detail where iw_type='ECS-DR' and "
//                    + "file_coming_dt='" + ymd.format(dmy.parse(fileGenDt)) + "' and "
//                    + "file_seq_no='" + seqNo + "' and ac_val_flag=''").getResultList();
            list = em.createNativeQuery("select micr from cbs_npci_oac_detail where iw_type='ACH-DR' and "
                    + "file_coming_dt='" + ymd.format(dmy.parse(fileGenDt)) + "' and "
                    + "file_seq_no='" + seqNo + "' and ac_val_flag=''").getResultList();
            if (!list.isEmpty()) {
                throw new ApplicationException("Total entries were not verified for: "
                        + "Settlement Date->" + fileGenDt + " And Seq No-->" + seqNo);
            }

//            List dataList = em.createNativeQuery("select header_identifier,file_coming_dt,file_seq_no,record_identifier,"
//                    + "micr,ac_type,old_acno,old_acname,user_number,user_name,tran_ref,ac_val_flag,cbs_acno,cbs_name,"
//                    + "return_code,amount,ach_item_seq_no,header_settlement_cycle,header_dest_iin,ledger_folio,checksum,"
//                    + "sponsor_iin,product_type,umrn,success_flag,reason_code from cbs_npci_oac_detail where "
//                    + "file_coming_dt='" + ymd.format(dmy.parse(fileGenDt)) + "' and file_seq_no='" + seqNo + "' and "
//                    + "iw_type='ECS-DR'").getResultList();
            List dataList = em.createNativeQuery("select header_identifier,file_coming_dt,file_seq_no,record_identifier,"
                    + "micr,ac_type,old_acno,old_acname,user_number,user_name,tran_ref,ac_val_flag,cbs_acno,cbs_name,"
                    + "return_code,amount,ach_item_seq_no,header_settlement_cycle,header_dest_iin,ledger_folio,checksum,"
                    + "sponsor_iin,product_type,umrn,success_flag,reason_code from cbs_npci_oac_detail where "
                    + "file_coming_dt='" + ymd.format(dmy.parse(fileGenDt)) + "' and file_seq_no='" + seqNo + "' and "
                    + "iw_type='ACH-DR'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no data to generate the files.");
            }
            //Required Header Values.
            for (int h = 0; h < 1; h++) { //Only For First Iteration.
                Vector hVec = (Vector) dataList.get(0);
                headerId = hVec.get(0).toString();
                detailId = hVec.get(3).toString();
                settlementCycle = hVec.get(17).toString();      //Make to desired length
                headerDestIIN = hVec.get(18).toString();        //Make to desired length
            }

            list = em.createNativeQuery("select iin,aadhar_location,npci_bank_code from "
                    + "mb_sms_sender_bank_detail").getResultList();
            ele = (Vector) list.get(0);
            if (ele.get(0) == null || ele.get(1) == null || ele.get(2) == null
                    || ele.get(0).toString().trim().equals("")
                    || ele.get(1).toString().trim().equals("")
                    || ele.get(2).toString().trim().equals("")
                    || ele.get(2).toString().trim().length() != 4) {
                throw new ApplicationException("Please define IIN, Location and Bank Code.");
            }
            aadharLocation = ele.get(1).toString().trim();
            bankCode = ele.get(2).toString().trim();

//            list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
//                    + "where file_gen_date='" + ymd.format(dmy.parse(fileGenDt)) + "' and "
//                    + "file_gen_type='ECS-DR'").getResultList();
            list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
                    + "where file_gen_date='" + ymd.format(dmy.parse(fileGenDt)) + "' and "
                    + "file_gen_type='ACH-DR'").getResultList();
            ele = (Vector) list.get(0);
            fileNo = "1";
            if (ele.get(0) != null) {
                fileNo = ele.get(0).toString().trim();
            }
            if (processingMode.equalsIgnoreCase("H2H")) {
                List H2huserList = em.createNativeQuery("select name,code from cbs_parameterinfo where name='NPCI-H2H-USER'").getResultList();
                Vector v = (Vector) H2huserList.get(0);
                if (H2huserList.isEmpty() || v.get(1) == null || v.get(1).toString().equalsIgnoreCase("")) {
                    throw new ApplicationException("H2H User can not be blank.");
                }
                npciUserName = v.get(1).toString();
//                genFileName = "ACH-DR-" + bankCode.toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
//                        + ymdOne.format(dmy.parse(fileGenDt)) + "-" + seqNo + "-RTN.txt";

                genFileName = "ACH-DR-" + bankCode.toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
                        + ymdOne.format(new Date()) + "-" + seqNo + "-RTN.txt";
            } else {
                genFileName = "ACH-DR-" + bankCode.toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
                        + ymdOne.format(dmy.parse(fileGenDt)) + "-" + seqNo + "-RTN.txt";
            }
//            int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
//                    + "file_gen_time,file_gen_brncode,file_gen_type,seq_no) values(" + Integer.parseInt(fileNo) + ",'"
//                    + ymd.format(dmy.parse(fileGenDt)) + "','" + genFileName + "','" + enterBy + "',now(),'"
//                    + orgnBrCode + "','ECS-DR',0)").executeUpdate(); //No of file check karo
            int n = 0, fileGenFlag = 0;
            if (processingMode.equalsIgnoreCase("H2H")) {
                n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                        + "file_gen_time,file_gen_brncode,file_gen_type,seq_no,DATE_OF_FILE_GEN,FILE_GEN_SEQN) values(" + Integer.parseInt(fileNo) + ",'"
                        + ymd.format(dmy.parse(fileGenDt)) + "','" + genFileName + "','" + enterBy + "',now(),'"
                        + orgnBrCode + "','ACH-DR',0,'" + ymdSql.format(dmy.parse(fileGenDt)) + "','" + seqNo + "')").executeUpdate(); //No of file check karo
                String updateFileGenFlagQuery = "UPDATE cbs_npci_oac_detail SET file_gen_flag = 'Y' where FILE_SEQ_NO='" + seqNo + "' "
                        + "and IW_TYPE='ACH-DR' and FILE_COMING_DT='" + ymd.format(dmy.parse(fileGenDt)) + "'";
                fileGenFlag = em.createNativeQuery(updateFileGenFlagQuery).executeUpdate();
                if (fileGenFlag <= 0) {
                    throw new ApplicationException("Problem In  file_gen_flag updation .");
                }
            } else {
                n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                        + "file_gen_time,file_gen_brncode,file_gen_type,seq_no) values(" + Integer.parseInt(fileNo) + ",'"
                        + ymd.format(dmy.parse(fileGenDt)) + "','" + genFileName + "','" + enterBy + "',now(),'"
                        + orgnBrCode + "','ACH-DR',0)").executeUpdate();
            }
            if (n <= 0) {
                throw new ApplicationException("Problem In Cbs ACH Debit Return Files Insertion.");
            }
            totalRecordNo = String.valueOf(dataList.size()).trim();
            BigDecimal totalSubAmt = new BigDecimal(0);
            for (int i = 0; i < dataList.size(); i++) {
                ele = (Vector) dataList.get(i);
                BigDecimal individualAmt = new BigDecimal(ele.get(15).toString().trim()).divide(new BigDecimal(100));
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
            //FileWriter fw = new FileWriter(aadharLocation + genFileName);
            FileWriter fw = null;
            String headerDt = "";
            if (processingMode.equalsIgnoreCase("H2H")) { //
                fw = new FileWriter(h2hLocation + genFileName);
                headerDt = ymdOne.format(new Date());
            } else {
                fw = new FileWriter(aadharLocation + genFileName);
                headerDt = ymdOne.format(dmy.parse(fileGenDt));
            }

            String header = headerId + ParseFileUtil.addSuffixSpaces("", 7) + ParseFileUtil.addSuffixSpaces("", 87)
                    + ParseFileUtil.addSuffixSpaces("", 7) + ParseFileUtil.addTrailingZeros(totalRecordNo, 9) + amtInPaisa
                    + headerDt + ParseFileUtil.addSuffixSpaces("", 27)
                    + ParseFileUtil.addSuffixSpaces(headerDestIIN, 11) + ParseFileUtil.addTrailingZeros(settlementCycle, 2)
                    + ParseFileUtil.addSuffixSpaces("", 132) + "." + "\n";
            fw.write(header);
            //Data Preparation.
            for (int i = 0; i < dataList.size(); i++) {
                Vector element = (Vector) dataList.get(i);
                String destBankIIN = element.get(4).toString().trim();
                String destAcType = element.get(5).toString().trim();
                String userNumber = element.get(8).toString().trim();
                String userNameNarration = element.get(9).toString().trim();
                String tranRefNo = element.get(10).toString().trim();
                String acValFlag = element.get(11).toString().trim();
                String benAcno = element.get(6).toString().trim();
                String benName = element.get(7).toString().trim();
                String dbReturnCode = element.get(14).toString().trim();
                String amount = element.get(15).toString().trim();
                String achItemSeqNo = element.get(16).toString().trim();
                String ledgerFolio = element.get(19).toString().trim();
                String checkSum = element.get(20).toString().trim();
                String sponserBankIIN = element.get(21).toString().trim();
                String achProductType = element.get(22).toString().trim();
                String umrn = element.get(23).toString().trim();
                String reservedFlag = "1";  //For Success
                String reasonCode = "00";

                //We have to make confirm that old Ecs Dr and New Ach Dr return reason code will be same.
                //Otherwise it wiil change.
                if (acValFlag.equalsIgnoreCase("R")) {
                    reservedFlag = "0";     //For Un-Success
                    reasonCode = dbReturnCode.length() == 2 ? dbReturnCode : "0" + dbReturnCode;
                }

                String individualStr = detailId + ParseFileUtil.addSuffixSpaces("", 9) + ParseFileUtil.addTrailingZeros(destAcType, 2)
                        + ParseFileUtil.addSuffixSpaces(ledgerFolio, 3) + ParseFileUtil.addSuffixSpaces("", 15)
                        + ParseFileUtil.addSuffixSpaces(benName, 40) + ParseFileUtil.addSuffixSpaces("", 8)
                        + ParseFileUtil.addSuffixSpaces("", 8) + ParseFileUtil.addSuffixSpaces(userNameNarration, 20)
                        + ParseFileUtil.addSuffixSpaces("", 13) + amount + ParseFileUtil.addTrailingZeros(achItemSeqNo, 10)
                        + ParseFileUtil.addTrailingZeros(checkSum, 10) + ParseFileUtil.addSuffixSpaces("", 7)
                        + ParseFileUtil.addSuffixSpaces(destBankIIN, 11) + ParseFileUtil.addSuffixSpaces(benAcno, 35)
                        + ParseFileUtil.addSuffixSpaces(sponserBankIIN, 11) + ParseFileUtil.addSuffixSpaces(userNumber, 18)
                        + ParseFileUtil.addSuffixSpaces(tranRefNo, 30) + ParseFileUtil.addSuffixSpaces(achProductType, 3)
                        + ParseFileUtil.addSuffixSpaces("", 15) + ParseFileUtil.addSuffixSpaces(umrn, 20) + reservedFlag + reasonCode
                        + "\n";
                fw.write(individualStr);
            }
            fw.close();
            //In case of H2H OW
            if (processingMode.equalsIgnoreCase("H2H")) {
                h2hNpciRemote.writeEncryptedFiles();
                h2hNpciRemote.upload(props.getProperty("npciSftpHost").trim(), props.getProperty("npciSftpUser").trim(),
                        props.getProperty("npciSftpPassword").trim(), props.getProperty("cbs.ow.encrypted.location").trim(),
                        props.getProperty("npciSftpFileUploadLocation").trim());

                h2hNpciRemote.createBackupAndThenRemoveFile(props.getProperty("cbs.ow.location").trim(), props.getProperty("cbs.ow.bkp.location").trim());
                h2hNpciRemote.createBackupAndThenRemoveFile(props.getProperty("cbs.ow.encrypted.location").trim(), props.getProperty("cbs.ow.bkp.encrypted.location").trim());

                em.createNativeQuery("insert into cbs_npci_h2h_file_detail(file_date,file_name,file_type) "
                        + "values('" + ymd.format(new Date()) + "','" + genFileName + "','OW')").executeUpdate();
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
        return "true";
    }

    public List isFileUploaded(String zipFileName) throws ApplicationException {
        try {
            return em.createNativeQuery("select file_no from mms_upload_xml_detail "
                    + "where zip_file_name='" + zipFileName + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public Integer retrieveFileNo(String mandateType, String curDt, String mandateMode) throws ApplicationException {
        Integer fileNo = 0;
        try {
//            List list = em.createNativeQuery("select ifnull(max(file_no),0) as fileNo from mms_upload_xml_detail "
//                    + "where mandate_type='" + mandateType + "' and upload_date='" + curDt + "' and Mandate_Mode = '" + mandateMode + "'").getResultList();
            List list = em.createNativeQuery("select ifnull(max(file_no),0) as fileNo from mms_upload_xml_detail "
                    + "where mandate_type='" + mandateType + "' and upload_date='" + curDt + "'").getResultList();
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(0);
                fileNo = Integer.parseInt(ele.get(0).toString()) + 1;
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return fileNo;
    }

    @Override
    public String uploadMmsData(File dir, String currentDt, String userName, Integer fileNo, String uploadedFileName, String mandateMode, String processingMode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        SimpleDateFormat ymdFormatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            ut.begin();
            File[] files = dir.listFiles();
            if (files == null || files.length == 0) {
                throw new ApplicationException("There is no data to upload");
            }
            //Here New Code
            for (File file : files) {
                String fileName = file.getName().trim(); //Complete FileName
                String fileType = fileName.substring(fileName.indexOf(".") + 1).trim(); //Only Extension
                if (fileType.equalsIgnoreCase("xml")) {
                    String fileProcessType = fileName.split("-")[1]; //It will give either CREATE,AMEND,CANCEL
                    if (mandateMode.equalsIgnoreCase("N")) {
                        //Unmarshalling-->XML To Java Object
                        if (fileProcessType.equalsIgnoreCase("CREATE")) {   //Create Inward
                            JAXBContext jaxbContext = JAXBContext.newInstance(com.cbs.pojo.mms.pain009.ObjectFactory.class);
                            Document doc = ((JAXBElement<Document>) jaxbContext.createUnmarshaller().
                                    unmarshal(new File(dir + "/" + fileName))).getValue();
                            MandateInitiationRequestV01 mandateInitiationRequestV01 = doc.getMndtInitnReq();
                            GroupHeader31 groupHeader31 = mandateInitiationRequestV01.getGrpHdr();              //GrpHdr
                            MandateInformation2 mandateInformation2 = mandateInitiationRequestV01.getMndt();    //Mndt
                            //Fething other required values.
                            String instgAgtFinInstnIdNm = groupHeader31.getInstgAgt().getFinInstnId().getNm() == null ? ""
                                    : groupHeader31.getInstgAgt().getFinInstnId().getNm().trim();
                            String instdAgtFinInstnIdNm = groupHeader31.getInstdAgt().getFinInstnId().getNm() == null ? ""
                                    : groupHeader31.getInstdAgt().getFinInstnId().getNm().trim();
                            String ocrncsSeqTp = "", ocrncsFrqcy = "", ocrncsFrstColltnDt = "", ocrncsFnlColltnDt = "";

                            if (mandateInformation2.getOcrncs()
                                    != null) {
                                if (mandateInformation2.getOcrncs().getSeqTp() != null) {
                                    ocrncsSeqTp = mandateInformation2.getOcrncs().getSeqTp().value() == null ? ""
                                            : mandateInformation2.getOcrncs().getSeqTp().value().trim();
                                }
                                if (mandateInformation2.getOcrncs().getFrqcy() != null) {
                                    ocrncsFrqcy = mandateInformation2.getOcrncs().getFrqcy().value() == null ? ""
                                            : mandateInformation2.getOcrncs().getFrqcy().value().trim();
                                }

                                if (mandateInformation2.getOcrncs().getFrstColltnDt() != null) {
                                    Calendar calendar = mandateInformation2.getOcrncs().getFrstColltnDt().toGregorianCalendar();
                                    ymdFormatter.setTimeZone(calendar.getTimeZone());
                                    ocrncsFrstColltnDt = ymdFormatter.format(calendar.getTime());
                                }
                                if (mandateInformation2.getOcrncs().getFnlColltnDt() != null) {
                                    Calendar calendar = mandateInformation2.getOcrncs().getFnlColltnDt().toGregorianCalendar();
                                    ymdFormatter.setTimeZone(calendar.getTimeZone());
                                    ocrncsFnlColltnDt = ymdFormatter.format(calendar.getTime());
                                }
                            }
                            String cdtrAgtFinInstnIdNm = mandateInformation2.getCdtrAgt().getFinInstnId().getNm() == null ? ""
                                    : mandateInformation2.getCdtrAgt().getFinInstnId().getNm().trim();
                            String dbtrIdPrvtldOthrId = "", dbtrIdPrvtIdOthrIdSchmeNmPrtry = "";

                            if (mandateInformation2.getDbtr()
                                    .getId() != null) {
                                if (mandateInformation2.getDbtr().getId().getPrvtId() != null) {
                                    if (mandateInformation2.getDbtr().getId().getPrvtId().getOthr() != null) {
                                        List<GenericPersonIdentification1> list1 = mandateInformation2.getDbtr().getId().
                                                getPrvtId().getOthr();
                                        for (GenericPersonIdentification1 obj : list1) {
                                            dbtrIdPrvtldOthrId = obj.getId() == null ? "" : obj.getId().trim();
                                            if (obj.getSchmeNm() != null) {
                                                dbtrIdPrvtIdOthrIdSchmeNmPrtry = obj.getSchmeNm().getPrtry() == null ? ""
                                                        : obj.getSchmeNm().getPrtry().trim();
                                            }
                                        }
                                    }
                                }
                            }
                            String dbtrCtctDtlsPhneNb = "", dbtrCtctDtlsMobNb = "", dbtrCtctDtlsEmailAdr = "", dbtrCtctDtlsOthr = "";

                            if (mandateInformation2.getDbtr()
                                    .getCtctDtls() != null) {
                                dbtrCtctDtlsPhneNb = mandateInformation2.getDbtr().getCtctDtls().getPhneNb() == null ? ""
                                        : mandateInformation2.getDbtr().getCtctDtls().getPhneNb().trim();
                                dbtrCtctDtlsMobNb = mandateInformation2.getDbtr().getCtctDtls().getMobNb() == null ? ""
                                        : mandateInformation2.getDbtr().getCtctDtls().getMobNb().trim();
                                dbtrCtctDtlsEmailAdr = mandateInformation2.getDbtr().getCtctDtls().getEmailAdr() == null ? ""
                                        : mandateInformation2.getDbtr().getCtctDtls().getEmailAdr().trim();
                                dbtrCtctDtlsOthr = mandateInformation2.getDbtr().getCtctDtls().getOthr() == null ? ""
                                        : mandateInformation2.getDbtr().getCtctDtls().getOthr().trim();
                            }
                            String dbtrAgtFinInstnIdNm = mandateInformation2.getDbtrAgt().getFinInstnId().getNm() == null ? ""
                                    : mandateInformation2.getDbtrAgt().getFinInstnId().getNm().trim();
                            String initgPtyIdOrgIdOthrId = "";

                            if (groupHeader31.getInitgPty()
                                    != null) {
                                if (groupHeader31.getInitgPty().getId() != null) {
                                    if (groupHeader31.getInitgPty().getId().getOrgId() != null) {
                                        if (groupHeader31.getInitgPty().getId().getOrgId().getOthr() != null) {
                                            List<GenericOrganisationIdentification1> list = groupHeader31.getInitgPty().
                                                    getId().getOrgId().getOthr();
                                            for (GenericOrganisationIdentification1 obj : list) {
                                                initgPtyIdOrgIdOthrId = obj.getId() == null ? "" : obj.getId().trim();
                                            }
                                        }
                                    }

                                }
                            }
                            BigDecimal colltnAmt = mandateInformation2.getColltnAmt() == null ? new BigDecimal("0")
                                    : mandateInformation2.getColltnAmt().getValue();
                            BigDecimal maxAmount = mandateInformation2.getMaxAmt() == null ? new BigDecimal("0")
                                    : mandateInformation2.getMaxAmt().getValue();
                            //Insert Data Into Table
                            int n = em.createNativeQuery("INSERT INTO mms_upload_xml_detail(MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,"
                                    + "InstgAgt_FinInstnId_ClrSysMmbId_MmbId,InstgAgt_FinInstnId_Nm,"
                                    + "InstdAgt_FinInstnId_ClrSysMmbId_MmbId,InstdAgt_FinInstnId_Nm,MndtId,MndtReqId,"
                                    + "Tp_SvcLvl_Prtry,Tp_LclInstrm_Prtry,Ocrncs_SeqTp,Ocrncs_Frqcy,Ocrncs_FrstColltnDt,"
                                    + "Ocrncs_FnlColltnDt,ColltnAmt,MaxAmt,Cdtr_Nm,CdtrAcct_Id_Othr_Id,"
                                    + "CdtrAgt_FinInstnId_ClrSysMmbId_MmbId,CdtrAgt_FinInstnId_Nm,Dbtr_Nm,Dbtr_Id_PrvtId_Othr_Id,"
                                    + "Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry,Dbtr_CtctDtls_Nm,Dbtr_CtctDtls_PhneNb,Dbtr_CtctDtls_MobNb,"
                                    + "Dbtr_CtctDtls_EmailAdr,Dbtr_CtctDtls_Othr,DbtrAcct_Id_Othr_Id,DbtrAcct_Tp_Prtry,"
                                    + "DbtrAgt_FinInstnId_ClrSysMmbId_MmbId,DbtrAgt_FinInstnId_Nm,Mandate_Type,"
                                    + "Mandate_Status,Accept,Reject_Code,File_No,Image_Name,Upload_By,Upload_Date,Upload_Time,Verify_By,"
                                    + "Verify_Time,Zip_File_Name,Mandate_Mode) "
                                    + "VALUES('" + groupHeader31.getMsgId().trim() + "',"
                                    + "'" + groupHeader31.getCreDtTm() + "',"
                                    + "'" + initgPtyIdOrgIdOthrId.trim() + "',"
                                    + "'" + groupHeader31.getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                    + "'" + instgAgtFinInstnIdNm + "',"
                                    + "'" + groupHeader31.getInstdAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                    + "'" + instdAgtFinInstnIdNm + "',"
                                    + "'" + mandateInformation2.getMndtId().trim() + "',"
                                    + "'" + mandateInformation2.getMndtReqId() + "',"
                                    + "'" + mandateInformation2.getTp().getSvcLvl().getPrtry().trim() + "',"
                                    + "'" + mandateInformation2.getTp().getLclInstrm().getPrtry().trim() + "',"
                                    + "'" + ocrncsSeqTp + "',"
                                    + "'" + ocrncsFrqcy + "',"
                                    + "'" + ocrncsFrstColltnDt + "',"
                                    + "'" + ocrncsFnlColltnDt + "',"
                                    + "" + colltnAmt + ","
                                    + "" + maxAmount + ","
                                    + "'" + mandateInformation2.getCdtr().getNm().trim() + "',"
                                    + "'" + mandateInformation2.getCdtrAcct().getId().getOthr().getId() + "',"
                                    + "'" + mandateInformation2.getCdtrAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                    + "'" + cdtrAgtFinInstnIdNm + "',"
                                    + "'" + mandateInformation2.getDbtr().getNm().trim() + "',"
                                    + "'" + dbtrIdPrvtldOthrId + "',"
                                    + "'" + dbtrIdPrvtIdOthrIdSchmeNmPrtry + "',"
                                    + "'',"
                                    + "'" + dbtrCtctDtlsPhneNb + "',"
                                    + "'" + dbtrCtctDtlsMobNb + "',"
                                    + "'" + dbtrCtctDtlsEmailAdr + "',"
                                    + "'" + dbtrCtctDtlsOthr + "',"
                                    + "'" + mandateInformation2.getDbtrAcct().getId().getOthr().getId().trim() + "',"
                                    + "'" + mandateInformation2.getDbtrAcct().getTp().getPrtry().trim() + "',"
                                    + "'" + mandateInformation2.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                    + "'" + dbtrAgtFinInstnIdNm + "',"
                                    + "'" + fileProcessType.toUpperCase().trim() + "',"
                                    + "'',"
                                    + "'',"
                                    + "'',"
                                    + "" + fileNo + ","
                                    + "'" + fileName.substring(0, fileName.lastIndexOf("-")).trim() + "',"
                                    + "'" + userName + "',"
                                    + "'" + currentDt + "',"
                                    + "now(),"
                                    + "'',"
                                    + "null,"
                                    + "'" + uploadedFileName + "','" + mandateMode + "')").executeUpdate();
                            if (n <= 0) {
                                throw new ApplicationException("Problem In Data Uploading..");
                            }
                            if (processingMode.equalsIgnoreCase("H2H")) {
                                int s = em.createNativeQuery("update mms_upload_xml_detail set FILE_GEN_FLAG = 'N' where MndtId ='" + mandateInformation2.getMndtId().trim() + "'").executeUpdate();
                                if (s <= 0) {
                                    throw new ApplicationException("Problem In File_gen_flag updating.");
                                }
                            }
                        } else if (fileProcessType.equalsIgnoreCase("AMEND")) {     //Amend Inward
                            JAXBContext jaxbContext = JAXBContext.newInstance(com.cbs.pojo.mms.pain010.ObjectFactory.class);
                            com.cbs.pojo.mms.pain010.Document doc = ((JAXBElement<com.cbs.pojo.mms.pain010.Document>) jaxbContext.createUnmarshaller().
                                    unmarshal(new File(dir + "/" + fileName))).getValue();
                            MandateAmendmentRequestV01 mandateAmendmentRequestV01 = doc.getMndtAmdmntReq();
                            com.cbs.pojo.mms.pain010.GroupHeader31 groupHeader31 = mandateAmendmentRequestV01.getGrpHdr();     //GrpHdr
                            com.cbs.pojo.mms.pain010.MandateAmendment1 mandateAmendment1 = mandateAmendmentRequestV01.getUndrlygAmdmntDtls();  //UndrlygAmdmntDtls
                            String initgPtyIdOrgIdOthrId = "";

                            if (groupHeader31.getInitgPty()
                                    != null) {
                                if (groupHeader31.getInitgPty().getId() != null) {
                                    if (groupHeader31.getInitgPty().getId().getOrgId() != null) {
                                        if (groupHeader31.getInitgPty().getId().getOrgId().getOthr() != null) {
                                            List<com.cbs.pojo.mms.pain010.GenericOrganisationIdentification1> list = groupHeader31.getInitgPty().
                                                    getId().getOrgId().getOthr();
                                            for (com.cbs.pojo.mms.pain010.GenericOrganisationIdentification1 obj : list) {
                                                initgPtyIdOrgIdOthrId = obj.getId() == null ? "" : obj.getId().trim();
                                            }
                                        }
                                    }

                                }
                            }
                            String instgAgtFinInstnIdNm = groupHeader31.getInstgAgt().getFinInstnId().getNm() == null ? ""
                                    : groupHeader31.getInstgAgt().getFinInstnId().getNm().trim();
                            String instdAgtFinInstnIdNm = groupHeader31.getInstdAgt().getFinInstnId().getNm() == null ? ""
                                    : groupHeader31.getInstdAgt().getFinInstnId().getNm().trim();
                            String ocrncsSeqTp = "", ocrncsFrqcy = "", ocrncsFrstColltnDt = "", ocrncsFnlColltnDt = "";

                            if (mandateAmendment1.getMndt()
                                    .getOcrncs() != null) {
                                if (mandateAmendment1.getMndt().getOcrncs().getSeqTp() != null) {
                                    ocrncsSeqTp = mandateAmendment1.getMndt().getOcrncs().getSeqTp().value() == null ? ""
                                            : mandateAmendment1.getMndt().getOcrncs().getSeqTp().value().trim();
                                }
                                if (mandateAmendment1.getMndt().getOcrncs().getFrqcy() != null) {
                                    ocrncsFrqcy = mandateAmendment1.getMndt().getOcrncs().getFrqcy().value() == null ? ""
                                            : mandateAmendment1.getMndt().getOcrncs().getFrqcy().value().trim();
                                }

                                if (mandateAmendment1.getMndt().getOcrncs().getFrstColltnDt() != null) {
                                    Calendar calendar = mandateAmendment1.getMndt().getOcrncs().getFrstColltnDt().toGregorianCalendar();
                                    ymdFormatter.setTimeZone(calendar.getTimeZone());
                                    ocrncsFrstColltnDt = ymdFormatter.format(calendar.getTime());
                                }
                                if (mandateAmendment1.getMndt().getOcrncs().getFnlColltnDt() != null) {
                                    Calendar calendar = mandateAmendment1.getMndt().getOcrncs().getFnlColltnDt().toGregorianCalendar();
                                    ymdFormatter.setTimeZone(calendar.getTimeZone());
                                    ocrncsFnlColltnDt = ymdFormatter.format(calendar.getTime());
                                }
                            }
                            BigDecimal colltnAmt = mandateAmendment1.getMndt().getColltnAmt() == null ? new BigDecimal("0")
                                    : mandateAmendment1.getMndt().getColltnAmt().getValue();
                            BigDecimal maxAmount = mandateAmendment1.getMndt().getMaxAmt() == null ? new BigDecimal("0")
                                    : mandateAmendment1.getMndt().getMaxAmt().getValue();
                            String cdtrAgtFinInstnIdNm = mandateAmendment1.getMndt().getCdtrAgt().getFinInstnId().getNm() == null ? ""
                                    : mandateAmendment1.getMndt().getCdtrAgt().getFinInstnId().getNm().trim();
                            String dbtrIdPrvtldOthrId = "", dbtrIdPrvtIdOthrSchmeNmPrtry = "";

                            if (mandateAmendment1.getMndt()
                                    .getDbtr().getId() != null) {
                                if (mandateAmendment1.getMndt().getDbtr().getId().getPrvtId() != null) {
                                    if (mandateAmendment1.getMndt().getDbtr().getId().getPrvtId().getOthr() != null) {
                                        List<com.cbs.pojo.mms.pain010.GenericPersonIdentification1> list1 = mandateAmendment1.getMndt().
                                                getDbtr().getId().getPrvtId().getOthr();
                                        for (com.cbs.pojo.mms.pain010.GenericPersonIdentification1 obj : list1) {
                                            dbtrIdPrvtldOthrId = obj.getId() == null ? "" : obj.getId().trim();
                                            if (obj.getSchmeNm() != null) {
                                                dbtrIdPrvtIdOthrSchmeNmPrtry = obj.getSchmeNm().getPrtry() == null ? ""
                                                        : obj.getSchmeNm().getPrtry().trim();
                                            }
                                        }
                                    }
                                }
                            }
                            String dbtrCtctDtlsPhneNb = "", dbtrCtctDtlsMobNb = "", dbtrCtctDtlsEmailAdr = "", dbtrCtctDtlsOthr = "";

                            if (mandateAmendment1.getMndt()
                                    .getDbtr().getCtctDtls() != null) {
                                dbtrCtctDtlsPhneNb = mandateAmendment1.getMndt().getDbtr().getCtctDtls().getPhneNb() == null ? ""
                                        : mandateAmendment1.getMndt().getDbtr().getCtctDtls().getPhneNb().trim();
                                dbtrCtctDtlsMobNb = mandateAmendment1.getMndt().getDbtr().getCtctDtls().getMobNb() == null ? ""
                                        : mandateAmendment1.getMndt().getDbtr().getCtctDtls().getMobNb().trim();
                                dbtrCtctDtlsEmailAdr = mandateAmendment1.getMndt().getDbtr().getCtctDtls().getEmailAdr() == null ? ""
                                        : mandateAmendment1.getMndt().getDbtr().getCtctDtls().getEmailAdr().trim();
                                dbtrCtctDtlsOthr = mandateAmendment1.getMndt().getDbtr().getCtctDtls().getOthr() == null ? ""
                                        : mandateAmendment1.getMndt().getDbtr().getCtctDtls().getOthr().trim();
                            }
                            String dbtrAcctTpPrtry = "";

                            if (mandateAmendment1.getMndt()
                                    .getDbtrAcct().getTp() != null) {
                                dbtrAcctTpPrtry = mandateAmendment1.getMndt().getDbtrAcct().getTp().getPrtry() == null ? ""
                                        : mandateAmendment1.getMndt().getDbtrAcct().getTp().getPrtry();
                            }
                            String dbtrAgtFinInstnIdNm = mandateAmendment1.getMndt().getDbtrAgt().getFinInstnId().getNm() == null
                                    ? "" : mandateAmendment1.getMndt().getDbtrAgt().getFinInstnId().getNm();
                            //Insert Data Into Table
                            int n = em.createNativeQuery("INSERT INTO mms_upload_xml_detail(MsgId,CreDtTm,"
                                    + "InitgPty_Id_OrgId_Othr_Id,InstgAgt_FinInstnId_ClrSysMmbId_MmbId,"
                                    + "InstgAgt_FinInstnId_Nm,InstdAgt_FinInstnId_ClrSysMmbId_MmbId,InstdAgt_FinInstnId_Nm,"
                                    + "MndtId,MndtReqId,Tp_SvcLvl_Prtry,Tp_LclInstrm_Prtry,Ocrncs_SeqTp,Ocrncs_Frqcy,"
                                    + "Ocrncs_FrstColltnDt,Ocrncs_FnlColltnDt,ColltnAmt,MaxAmt,Cdtr_Nm,CdtrAcct_Id_Othr_Id,"
                                    + "CdtrAgt_FinInstnId_ClrSysMmbId_MmbId,CdtrAgt_FinInstnId_Nm,Dbtr_Nm,Dbtr_Id_PrvtId_Othr_Id,"
                                    + "Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry,Dbtr_CtctDtls_Nm,Dbtr_CtctDtls_PhneNb,Dbtr_CtctDtls_MobNb,"
                                    + "Dbtr_CtctDtls_EmailAdr,Dbtr_CtctDtls_Othr,DbtrAcct_Id_Othr_Id,DbtrAcct_Tp_Prtry,"
                                    + "DbtrAgt_FinInstnId_ClrSysMmbId_MmbId,DbtrAgt_FinInstnId_Nm,Mandate_Type,"
                                    + "Mandate_Status,Accept,Reject_Code,File_No,Image_Name,Upload_By,Upload_Date,"
                                    + "Upload_Time,Verify_By,Verify_Time,Zip_File_Name,UndrlygAmdmntDtls_AmdmntRsn_Rsn_Prtry,Mandate_Mode) "
                                    + "VALUES('" + groupHeader31.getMsgId().trim() + "',"
                                    + "'" + groupHeader31.getCreDtTm() + "',"
                                    + "'" + initgPtyIdOrgIdOthrId.trim() + "',"
                                    + "'" + groupHeader31.getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                    + "'" + instgAgtFinInstnIdNm + "',"
                                    + "'" + groupHeader31.getInstdAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                    + "'" + instdAgtFinInstnIdNm + "',"
                                    + "'" + mandateAmendment1.getOrgnlMndt().getOrgnlMndtId().trim() + "',"
                                    + "'" + mandateAmendment1.getMndt().getMndtReqId().trim() + "',"
                                    + "'" + mandateAmendment1.getMndt().getTp().getSvcLvl().getPrtry().trim() + "',"
                                    + "'" + mandateAmendment1.getMndt().getTp().getLclInstrm().getPrtry().trim() + "',"
                                    + "'" + ocrncsSeqTp + "',"
                                    + "'" + ocrncsFrqcy + "',"
                                    + "'" + ocrncsFrstColltnDt + "',"
                                    + "'" + ocrncsFnlColltnDt + "',"
                                    + "" + colltnAmt + ","
                                    + "" + maxAmount + ","
                                    + "'" + mandateAmendment1.getMndt().getCdtr().getNm().trim() + "',"
                                    + "'" + mandateAmendment1.getMndt().getCdtrAcct().getId().getOthr().getId() + "',"
                                    + "'" + mandateAmendment1.getMndt().getCdtrAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                    + "'" + cdtrAgtFinInstnIdNm + "','" + mandateAmendment1.getMndt().getDbtr().getNm().trim() + "',"
                                    + "'" + dbtrIdPrvtldOthrId + "',"
                                    + "'" + dbtrIdPrvtIdOthrSchmeNmPrtry + "',"
                                    + "'',"
                                    + "'" + dbtrCtctDtlsPhneNb + "',"
                                    + "'" + dbtrCtctDtlsMobNb + "',"
                                    + "'" + dbtrCtctDtlsEmailAdr + "',"
                                    + "'" + dbtrCtctDtlsOthr + "',"
                                    + "'" + mandateAmendment1.getMndt().getDbtrAcct().getId().getOthr().getId().trim() + "',"
                                    + "'" + dbtrAcctTpPrtry + "',"
                                    + "'" + mandateAmendment1.getMndt().getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                    + "'" + dbtrAgtFinInstnIdNm + "',"
                                    + "'" + fileProcessType.toUpperCase().trim() + "',"
                                    + "'',"
                                    + "'',"
                                    + "'',"
                                    + "" + fileNo + ","
                                    + "'" + fileName.substring(0, fileName.lastIndexOf("-")).trim() + "',"
                                    + "'" + userName + "',"
                                    + "'" + currentDt + "',"
                                    + "now(),'',"
                                    + "null,"
                                    + "'" + uploadedFileName + "',"
                                    + "'" + mandateAmendment1.getAmdmntRsn().getRsn().getPrtry() + "','" + mandateMode + "')").executeUpdate();
                            if (n <= 0) {
                                throw new ApplicationException("Problem In Data Uploading..");
                            }
                            if (processingMode.equalsIgnoreCase("H2H")) {
                                int s = em.createNativeQuery("update mms_upload_xml_detail set FILE_GEN_FLAG = 'N' where MndtId ='" + mandateAmendment1.getMndt().getMndtReqId().trim() + "'").executeUpdate();
                                if (s <= 0) {
                                    throw new ApplicationException("Problem In File_gen_flag updating.");
                                }
                            }
                        }else if (fileProcessType.equalsIgnoreCase("CANCEL")) { //Cancel Inward
                            JAXBContext jaxbContext = JAXBContext.newInstance(com.cbs.pojo.mms.pain011.ObjectFactory.class);
                            com.cbs.pojo.mms.pain011.Document doc = ((JAXBElement<com.cbs.pojo.mms.pain011.Document>) jaxbContext.
                                    createUnmarshaller().unmarshal(new File(dir + "/" + fileName))).getValue();
                            com.cbs.pojo.mms.pain011.MandateCancellationRequestV01 mandateCancelReqV01 = doc.getMndtCxlReq();
                            com.cbs.pojo.mms.pain011.GroupHeader31 groupHeader31 = mandateCancelReqV01.getGrpHdr();
                            com.cbs.pojo.mms.pain011.MandateCancellation1 mandate1List = mandateCancelReqV01.getUndrlygCxlDtls();


                            String groupHeaderMsgId = groupHeader31.getMsgId();
                            Calendar calendar = groupHeader31.getCreDtTm().toGregorianCalendar();
                            ymdFormatter.setTimeZone(calendar.getTimeZone());
                            String creDtTm = ymdFormatter.format(calendar.getTime());


                            String instgAgtFinInstnIdNm = groupHeader31.getInstgAgt().getFinInstnId().getNm() == null ? ""
                                    : groupHeader31.getInstgAgt().getFinInstnId().getNm().trim();
                            String instdAgtFinInstnIdNm = groupHeader31.getInstdAgt().getFinInstnId().getNm() == null ? ""
                                    : groupHeader31.getInstdAgt().getFinInstnId().getNm().trim();


                            //Fetching UnderLyingDtls required value
                            String ocrncsSeqTp = "", ocrncsFrqcyTp = "", ocrncsFrstColltnDt = "", ocrncsFnlColltnDt = "";
                            String prtry = "";
                            String rsnPrtry = "";
                            String mndtId = "";
                            String mndtReqId = "";
                            String instgAgtFinInstIdmemberId = "";
                            String instdAgtFinInstIdmemberId = "";

                            instgAgtFinInstIdmemberId = groupHeader31.getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId() == null ? ""
                                    : groupHeader31.getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId();

                            instgAgtFinInstIdmemberId = groupHeader31.getInstdAgt().getFinInstnId().getClrSysMmbId().getMmbId() == null ? ""
                                    : groupHeader31.getInstdAgt().getFinInstnId().getClrSysMmbId().getMmbId();

                            if (mandate1List != null) {
                                com.cbs.pojo.mms.pain011.MandateCancellation1 mandateCancel1 = mandate1List;
                                if (mandateCancel1.getOrgnlMndt().getOrgnlMndt() != null) {
                                    if (mandateCancel1.getOrgnlMndt().getOrgnlMndt().getOcrncs().getSeqTp() != null) {
                                        ocrncsSeqTp = mandateCancel1.getOrgnlMndt().getOrgnlMndt().getOcrncs().getSeqTp().value() == null ? ""
                                                : mandateCancel1.getOrgnlMndt().getOrgnlMndt().getOcrncs().getSeqTp().value().trim();
                                    }
                                    // no getTp() method
//                                    if (mandateCancel1.getOrgnlMndt().getOrgnlMndt().getOcrncs().getFrqcy() != null) {
//                                        ocrncsFrqcyTp = mandateCancel1.getOrgnlMndt().getOrgnlMndt().getOcrncs().getFrqcy().getTp().value() == null ? ""
//                                                : mandateCancel1.getOrgnlMndt().getOrgnlMndt().getOcrncs().getFrqcy().getTp().value().trim();  //New addition here
//                                    }

                                    if (mandateCancel1.getOrgnlMndt().getOrgnlMndt().getOcrncs().getFrstColltnDt() != null) {
                                        Calendar calendar1 = mandateCancel1.getOrgnlMndt().getOrgnlMndt().getOcrncs().getFrstColltnDt().toGregorianCalendar();
                                        ymdFormatter.setTimeZone(calendar.getTimeZone());
                                        ocrncsFrstColltnDt = ymdFormatter.format(calendar.getTime());
                                    }
                                    if (mandateCancel1.getOrgnlMndt().getOrgnlMndt().getOcrncs().getFnlColltnDt() != null) {
                                        Calendar calendar2 = mandateCancel1.getOrgnlMndt().getOrgnlMndt().getOcrncs().getFnlColltnDt().toGregorianCalendar();
                                        ymdFormatter.setTimeZone(calendar.getTimeZone());
                                        ocrncsFnlColltnDt = ymdFormatter.format(calendar.getTime());
                                    }
                                }

                                prtry = mandateCancel1.getCxlRsn().getRsn().getPrtry() == null ? ""
                                        : mandateCancel1.getCxlRsn().getRsn().getPrtry();

                                mndtId = mandateCancel1.getOrgnlMndt().getOrgnlMndtId() == null ? ""
                                        : mandateCancel1.getOrgnlMndt().getOrgnlMndtId();

                                String dbtrIdPrvtldOthrId = "", dbtrIdPrvtIdOthrIdSchmeNmPrtry = "", cdtrAgtFinInstnIdNm = "";

                                if (mandateCancel1.getOrgnlMndt().getOrgnlMndt() != null) {
                                    cdtrAgtFinInstnIdNm = mandateCancel1.getOrgnlMndt().getOrgnlMndt().getCdtrAgt().getFinInstnId().getNm() == null ? ""
                                            : mandateCancel1.getOrgnlMndt().getOrgnlMndt().getCdtrAgt().getFinInstnId().getNm().trim();


                                    if (mandateCancel1.getOrgnlMndt().getOrgnlMndt().getDbtr().getId().getPrvtId().getOthr() != null) {
                                        List<com.cbs.pojo.mms.pain011.GenericPersonIdentification1> list1 = mandateCancel1.getOrgnlMndt().getOrgnlMndt().getDbtr().
                                                getId().getPrvtId().getOthr();
                                        for (com.cbs.pojo.mms.pain011.GenericPersonIdentification1 obj : list1) {
                                            dbtrIdPrvtldOthrId = obj.getId() == null ? "" : obj.getId().trim();
                                            if (obj.getSchmeNm() != null) {
                                                dbtrIdPrvtIdOthrIdSchmeNmPrtry = obj.getSchmeNm().getPrtry() == null ? ""
                                                        : obj.getSchmeNm().getPrtry().trim();
                                            }
                                        }
                                    }
                                }

                                String amendgPtyIdOrgIdOthrId = "";
                                if (groupHeader31.getInitgPty() != null) {
                                    if (groupHeader31.getInitgPty().getId() != null) {
                                        if (groupHeader31.getInitgPty().getId().getOrgId() != null) {
                                            if (groupHeader31.getInitgPty().getId().getOrgId().getOthr() != null
                                                    && !groupHeader31.getInitgPty().getId().getOrgId().getOthr().isEmpty()) {
                                                List<com.cbs.pojo.mms.pain011.GenericOrganisationIdentification1> list = groupHeader31.getInitgPty().
                                                        getId().getOrgId().getOthr();
                                                for (com.cbs.pojo.mms.pain011.GenericOrganisationIdentification1 obj : list) {
                                                    amendgPtyIdOrgIdOthrId = obj.getId() == null ? "" : obj.getId().trim();
                                                }
                                            }
                                        }

                                    }
                                }


                                String dbtrCtctDtlsNm = "", dbtrCtctDtlsPhneNb = "", dbtrCtctDtlsMobNb = "",
                                        dbtrCtctDtlsEmailAdr = "", dbtrCtctDtlsOthr = "";
                                if (mandateCancel1.getOrgnlMndt().getOrgnlMndt() != null) {
                                    dbtrCtctDtlsNm = mandateCancel1.getOrgnlMndt().getOrgnlMndt().getDbtr().getCtctDtls().getNm() == null ? ""
                                            : mandateCancel1.getOrgnlMndt().getOrgnlMndt().getDbtr().getCtctDtls().getNm().trim(); //New addition here
                                    dbtrCtctDtlsPhneNb = mandateCancel1.getOrgnlMndt().getOrgnlMndt().getDbtr().getCtctDtls().getPhneNb() == null ? ""
                                            : mandateCancel1.getOrgnlMndt().getOrgnlMndt().getDbtr().getCtctDtls().getPhneNb().trim();
                                    dbtrCtctDtlsMobNb = mandateCancel1.getOrgnlMndt().getOrgnlMndt().getDbtr().getCtctDtls().getMobNb() == null ? ""
                                            : mandateCancel1.getOrgnlMndt().getOrgnlMndt().getDbtr().getCtctDtls().getMobNb().trim();
                                    dbtrCtctDtlsEmailAdr = mandateCancel1.getOrgnlMndt().getOrgnlMndt().getDbtr().getCtctDtls().getEmailAdr() == null ? ""
                                            : mandateCancel1.getOrgnlMndt().getOrgnlMndt().getDbtr().getCtctDtls().getEmailAdr().trim();
                                    dbtrCtctDtlsOthr = mandateCancel1.getOrgnlMndt().getOrgnlMndt().getDbtr().getCtctDtls().getOthr() == null ? ""
                                            : mandateCancel1.getOrgnlMndt().getOrgnlMndt().getDbtr().getCtctDtls().getOthr().trim();
                                }
                                String dbtrAgtFinInstnIdNm = "", orgnlMandateId = "";
                                BigDecimal collAmt = new BigDecimal(0), maxAmount = new BigDecimal(0);
                                if (mandateCancel1.getOrgnlMndt().getOrgnlMndt() != null) {
                                    dbtrAgtFinInstnIdNm = mandateCancel1.getOrgnlMndt().getOrgnlMndt().getDbtrAgt().getFinInstnId().getNm() == null ? ""
                                            : mandateCancel1.getOrgnlMndt().getOrgnlMndt().getDbtrAgt().getFinInstnId().getNm().trim();

                                    orgnlMandateId = mandateCancel1.getOrgnlMndt().getOrgnlMndt().getMndtId().trim() == null ? ""
                                            : mandateCancel1.getOrgnlMndt().getOrgnlMndt().getMndtId().trim();


                                    collAmt = mandateCancel1.getOrgnlMndt().getOrgnlMndt().getColltnAmt() == null ? new BigDecimal(0)
                                            : mandateCancel1.getOrgnlMndt().getOrgnlMndt().getColltnAmt().getValue();
                                    maxAmount = mandateCancel1.getOrgnlMndt().getOrgnlMndt().getMaxAmt() == null ? new BigDecimal("0")
                                            : mandateCancel1.getOrgnlMndt().getOrgnlMndt().getMaxAmt().getValue();
                                }


                                int n = em.createNativeQuery("INSERT INTO mms_upload_xml_detail(MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,"
                                        + "InstgAgt_FinInstnId_ClrSysMmbId_MmbId,InstgAgt_FinInstnId_Nm,"
                                        + "InstdAgt_FinInstnId_ClrSysMmbId_MmbId,InstdAgt_FinInstnId_Nm,MndtId,MndtReqId,"
                                        + "Tp_SvcLvl_Prtry,Tp_LclInstrm_Prtry,Ocrncs_SeqTp,Ocrncs_Frqcy,Ocrncs_FrstColltnDt,"
                                        + "Ocrncs_FnlColltnDt,ColltnAmt,MaxAmt,Cdtr_Nm,CdtrAcct_Id_Othr_Id,"
                                        + "CdtrAgt_FinInstnId_ClrSysMmbId_MmbId,CdtrAgt_FinInstnId_Nm,Dbtr_Nm,Dbtr_Id_PrvtId_Othr_Id,"
                                        + "Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry,Dbtr_CtctDtls_Nm,Dbtr_CtctDtls_PhneNb,Dbtr_CtctDtls_MobNb,"
                                        + "Dbtr_CtctDtls_EmailAdr,Dbtr_CtctDtls_Othr,DbtrAcct_Id_Othr_Id,DbtrAcct_Tp_Prtry,"
                                        + "DbtrAgt_FinInstnId_ClrSysMmbId_MmbId,DbtrAgt_FinInstnId_Nm,Mandate_Type,"
                                        + "Mandate_Status,Accept,Reject_Code,File_No,Image_Name,Upload_By,Upload_Date,Upload_Time,Verify_By,"
                                        + "Verify_Time,Zip_File_Name,Mandate_Mode,UnderlygAmdmntDtls_OrgnlMndt_OrgnlMndtId,UndrlygCxlDtls_CxlRsn_Rsn_Prtry) "
                                        + "VALUES('" + groupHeader31.getMsgId().trim() + "',"
                                        + "'" + groupHeader31.getCreDtTm() + "',"
                                        + "'" + amendgPtyIdOrgIdOthrId.trim() + "',"
                                        + "'" + groupHeader31.getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                        + "'" + instgAgtFinInstnIdNm + "',"
                                        + "'" + groupHeader31.getInstdAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                        + "'" + instdAgtFinInstnIdNm + "',"
                                        + "'" + mndtId.trim() + "',"
                                        + "'',"
                                        + "'',"
                                        + "'',"
                                        + "'" + ocrncsSeqTp + "',"
                                        + "'" + ocrncsFrqcyTp + "',"
                                        + "'" + ocrncsFrstColltnDt + "',"
                                        + "'" + ocrncsFnlColltnDt + "',"
                                        + "" + collAmt + ","
                                        + "" + maxAmount + ","
                                        + "'',"
                                        + "'',"
                                        + "'',"
                                        + "'" + cdtrAgtFinInstnIdNm + "',"
                                        + "'',"
                                        + "'" + dbtrIdPrvtldOthrId + "',"
                                        + "'" + dbtrIdPrvtIdOthrIdSchmeNmPrtry + "',"
                                        + "'" + dbtrCtctDtlsNm + "',"
                                        + "'" + dbtrCtctDtlsPhneNb + "',"
                                        + "'" + dbtrCtctDtlsMobNb + "',"
                                        + "'" + dbtrCtctDtlsEmailAdr + "',"
                                        + "'" + dbtrCtctDtlsOthr + "',"
                                        + "'',"
                                        + "'',"
                                        + "'',"
                                        + "'" + dbtrAgtFinInstnIdNm + "',"
                                        + "'" + fileProcessType.toUpperCase().trim() + "',"
                                        + "'',"
                                        + "'',"
                                        + "'',"
                                        + "" + fileNo + ","
                                        + "'" + fileName.substring(0, fileName.lastIndexOf("-")).trim() + "',"
                                        + "'" + userName + "',"
                                        + "'" + currentDt + "',"
                                        + "now(),"
                                        + "'',"
                                        + "null,"
                                        + "'" + uploadedFileName + "','" + mandateMode + "','" + orgnlMandateId + "','" + prtry + "')").executeUpdate();

                                if (n <= 0) {
                                    throw new ApplicationException("Problem In Data Uploading..");
                                }
                                if (processingMode.equalsIgnoreCase("H2H")) {
                                    int s = em.createNativeQuery("update mms_upload_xml_detail set FILE_GEN_FLAG = 'N' where MndtId ='" + mandateCancel1.getOrgnlMndt().getOrgnlMndt().getMndtId().trim() + "'").executeUpdate();
                                    if (s <= 0) {
                                        throw new ApplicationException("Problem In File_gen_flag updating.");
                                    }
                                }
                            }
                            int n = em.createNativeQuery("update mms_upload_xml_detail set mandate_status='V',accept='A',"
                                    + "reject_code='',verify_by='" + userName + "',verify_time=now() "
                                    + "where mandate_type='" + fileProcessType + "' and upload_date='" + currentDt + "' and "
                                    + "file_no=" + fileNo + " and mndtid='" + mndtId + "'").executeUpdate();
                            if (n <= 0) {
                                throw new ApplicationException("Problem In Mandate Updation.");
                            }

                            List list = em.createNativeQuery("select mndtid from mms_detail where mndtid='" + mndtId + "'and Mandate_Mode = '" + mandateMode + "'").getResultList();
                            if (!list.isEmpty()) {
                                int t = em.createNativeQuery("insert into mms_detail_his(MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,"
                                        + "InstgAgt_FinInstnId_ClrSysMmbId_MmbId,InstgAgt_FinInstnId_Nm,InstdAgt_FinInstnId_ClrSysMmbId_MmbId,"
                                        + "InstdAgt_FinInstnId_Nm,MndtId,MndtReqId,Tp_SvcLvl_Prtry,Tp_LclInstrm_Prtry,Ocrncs_SeqTp,"
                                        + "Ocrncs_Frqcy,Ocrncs_FrstColltnDt,Ocrncs_FnlColltnDt,ColltnAmt,MaxAmt,Cdtr_Nm,CdtrAcct_Id_Othr_Id,"
                                        + "CdtrAgt_FinInstnId_ClrSysMmbId_MmbId,CdtrAgt_FinInstnId_Nm,Dbtr_Nm,Dbtr_Id_PrvtId_Othr_Id,"
                                        + "Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry,Dbtr_CtctDtls_Nm,Dbtr_CtctDtls_PhneNb,Dbtr_CtctDtls_MobNb,"
                                        + "Dbtr_CtctDtls_EmailAdr,Dbtr_CtctDtls_Othr,DbtrAcct_Id_Othr_Id,DbtrAcct_Tp_Prtry,"
                                        + "DbtrAgt_FinInstnId_ClrSysMmbId_MmbId,DbtrAgt_FinInstnId_Nm,Mandate_Type,Update_By,Update_Date,"
                                        + "Update_Time,Mandate_Status,Stop_Payment_Date,Stop_Payment_By,Mandate_Mode,Cbs_Acno) select MsgId,CreDtTm,"
                                        + "InitgPty_Id_OrgId_Othr_Id,InstgAgt_FinInstnId_ClrSysMmbId_MmbId,InstgAgt_FinInstnId_Nm,"
                                        + "InstdAgt_FinInstnId_ClrSysMmbId_MmbId,InstdAgt_FinInstnId_Nm,MndtId,MndtReqId,Tp_SvcLvl_Prtry,"
                                        + "Tp_LclInstrm_Prtry,Ocrncs_SeqTp,Ocrncs_Frqcy,Ocrncs_FrstColltnDt,Ocrncs_FnlColltnDt,ColltnAmt,"
                                        + "MaxAmt,Cdtr_Nm,CdtrAcct_Id_Othr_Id,CdtrAgt_FinInstnId_ClrSysMmbId_MmbId,CdtrAgt_FinInstnId_Nm,"
                                        + "Dbtr_Nm,Dbtr_Id_PrvtId_Othr_Id,Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry,Dbtr_CtctDtls_Nm,Dbtr_CtctDtls_PhneNb,"
                                        + "Dbtr_CtctDtls_MobNb,Dbtr_CtctDtls_EmailAdr,Dbtr_CtctDtls_Othr,DbtrAcct_Id_Othr_Id,"
                                        + "DbtrAcct_Tp_Prtry,DbtrAgt_FinInstnId_ClrSysMmbId_MmbId,DbtrAgt_FinInstnId_Nm,Mandate_Type,"
                                        + "Update_By,Update_Date,Update_Time,Mandate_Status,Stop_Payment_Date,Stop_Payment_By,Mandate_Mode,"
                                        + "Cbs_Acno from mms_detail where MndtId = '" + mndtId + "'").executeUpdate();
                                if (t <= 0) {
                                    throw new ApplicationException("Problem In Mandate History Creation.");
                                }

                                int u = em.createNativeQuery("update mms_detail set Mandate_Status='C',Update_By='" + userName + "',Update_Date='" + currentDt + "',"
                                        + "Update_Time=now() where MndtId='" + mndtId + "'").executeUpdate();
                                if (u <= 0) {
                                    throw new ApplicationException("Problem In Mandate History Creation.");
                                }
                            }


                        }
                    } else if (mandateMode.equalsIgnoreCase("L")) {
                        if (fileProcessType.equalsIgnoreCase("CREATE")) {   //Create Inward
                            JAXBContext jaxbContext = JAXBContext.newInstance(com.cbs.pojo.mms.pain009.ObjectFactory.class);
                            Document doc = ((JAXBElement<Document>) jaxbContext.createUnmarshaller().
                                    unmarshal(new File(dir + "/" + fileName))).getValue();
                            MandateInitiationRequestV01 mandateInitiationRequestV01 = doc.getMndtInitnReq();
                            GroupHeader31 groupHeader31 = mandateInitiationRequestV01.getGrpHdr();              //GrpHdr
                            MandateInformation2 mandateInformation2 = mandateInitiationRequestV01.getMndt();    //Mndt
                            //Fething other required values.
                            String instgAgtFinInstnIdNm = groupHeader31.getInstgAgt().getFinInstnId().getNm() == null ? ""
                                    : groupHeader31.getInstgAgt().getFinInstnId().getNm().trim();
                            String instdAgtFinInstnIdNm = groupHeader31.getInstdAgt().getFinInstnId().getNm() == null ? ""
                                    : groupHeader31.getInstdAgt().getFinInstnId().getNm().trim();
                            String ocrncsSeqTp = "", ocrncsFrqcy = "", ocrncsFrstColltnDt = "", ocrncsFnlColltnDt = "";

                            if (mandateInformation2.getOcrncs()
                                    != null) {
                                if (mandateInformation2.getOcrncs().getSeqTp() != null) {
                                    ocrncsSeqTp = mandateInformation2.getOcrncs().getSeqTp().value() == null ? ""
                                            : mandateInformation2.getOcrncs().getSeqTp().value().trim();
                                }
                                if (mandateInformation2.getOcrncs().getFrqcy() != null) {
                                    ocrncsFrqcy = mandateInformation2.getOcrncs().getFrqcy().value() == null ? ""
                                            : mandateInformation2.getOcrncs().getFrqcy().value().trim();
                                }

                                if (mandateInformation2.getOcrncs().getFrstColltnDt() != null) {
                                    Calendar calendar = mandateInformation2.getOcrncs().getFrstColltnDt().toGregorianCalendar();
                                    ymdFormatter.setTimeZone(calendar.getTimeZone());
                                    ocrncsFrstColltnDt = ymdFormatter.format(calendar.getTime());
                                }
                                if (mandateInformation2.getOcrncs().getFnlColltnDt() != null) {
                                    Calendar calendar = mandateInformation2.getOcrncs().getFnlColltnDt().toGregorianCalendar();
                                    ymdFormatter.setTimeZone(calendar.getTimeZone());
                                    ocrncsFnlColltnDt = ymdFormatter.format(calendar.getTime());
                                }
                            }
                            String cdtrAgtFinInstnIdNm = mandateInformation2.getCdtrAgt().getFinInstnId().getNm() == null ? ""
                                    : mandateInformation2.getCdtrAgt().getFinInstnId().getNm().trim();
                            String dbtrIdPrvtldOthrId = "", dbtrIdPrvtIdOthrIdSchmeNmPrtry = "";

                            if (mandateInformation2.getDbtr()
                                    .getId() != null) {
                                if (mandateInformation2.getDbtr().getId().getPrvtId() != null) {
                                    if (mandateInformation2.getDbtr().getId().getPrvtId().getOthr() != null) {
                                        List<GenericPersonIdentification1> list1 = mandateInformation2.getDbtr().getId().
                                                getPrvtId().getOthr();
                                        for (GenericPersonIdentification1 obj : list1) {
                                            dbtrIdPrvtldOthrId = obj.getId() == null ? "" : obj.getId().trim();
                                            if (obj.getSchmeNm() != null) {
                                                dbtrIdPrvtIdOthrIdSchmeNmPrtry = obj.getSchmeNm().getPrtry() == null ? ""
                                                        : obj.getSchmeNm().getPrtry().trim();
                                            }
                                        }
                                    }
                                }
                            }
                            String dbtrCtctDtlsPhneNb = "", dbtrCtctDtlsMobNb = "", dbtrCtctDtlsEmailAdr = "", dbtrCtctDtlsOthr = "";

                            if (mandateInformation2.getDbtr()
                                    .getCtctDtls() != null) {
                                dbtrCtctDtlsPhneNb = mandateInformation2.getDbtr().getCtctDtls().getPhneNb() == null ? ""
                                        : mandateInformation2.getDbtr().getCtctDtls().getPhneNb().trim();
                                dbtrCtctDtlsMobNb = mandateInformation2.getDbtr().getCtctDtls().getMobNb() == null ? ""
                                        : mandateInformation2.getDbtr().getCtctDtls().getMobNb().trim();
                                dbtrCtctDtlsEmailAdr = mandateInformation2.getDbtr().getCtctDtls().getEmailAdr() == null ? ""
                                        : mandateInformation2.getDbtr().getCtctDtls().getEmailAdr().trim();
                                dbtrCtctDtlsOthr = mandateInformation2.getDbtr().getCtctDtls().getOthr() == null ? ""
                                        : mandateInformation2.getDbtr().getCtctDtls().getOthr().trim();
                            }
                            String dbtrAgtFinInstnIdNm = mandateInformation2.getDbtrAgt().getFinInstnId().getNm() == null ? ""
                                    : mandateInformation2.getDbtrAgt().getFinInstnId().getNm().trim();
                            String initgPtyIdOrgIdOthrId = "";

                            if (groupHeader31.getInitgPty()
                                    != null) {
                                if (groupHeader31.getInitgPty().getId() != null) {
                                    if (groupHeader31.getInitgPty().getId().getOrgId() != null) {
                                        if (groupHeader31.getInitgPty().getId().getOrgId().getOthr() != null) {
                                            List<GenericOrganisationIdentification1> list = groupHeader31.getInitgPty().
                                                    getId().getOrgId().getOthr();
                                            for (GenericOrganisationIdentification1 obj : list) {
                                                initgPtyIdOrgIdOthrId = obj.getId() == null ? "" : obj.getId().trim();
                                            }
                                        }
                                    }

                                }
                            }
                            BigDecimal colltnAmt = mandateInformation2.getColltnAmt() == null ? new BigDecimal("0")
                                    : mandateInformation2.getColltnAmt().getValue();
                            BigDecimal maxAmount = mandateInformation2.getMaxAmt() == null ? new BigDecimal("0")
                                    : mandateInformation2.getMaxAmt().getValue();
                            //Insert Data Into Table
                            int n = em.createNativeQuery("INSERT INTO mms_upload_xml_detail(MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,"
                                    + "InstgAgt_FinInstnId_ClrSysMmbId_MmbId,InstgAgt_FinInstnId_Nm,"
                                    + "InstdAgt_FinInstnId_ClrSysMmbId_MmbId,InstdAgt_FinInstnId_Nm,MndtId,MndtReqId,"
                                    + "Tp_SvcLvl_Prtry,Tp_LclInstrm_Prtry,Ocrncs_SeqTp,Ocrncs_Frqcy,Ocrncs_FrstColltnDt,"
                                    + "Ocrncs_FnlColltnDt,ColltnAmt,MaxAmt,Cdtr_Nm,CdtrAcct_Id_Othr_Id,"
                                    + "CdtrAgt_FinInstnId_ClrSysMmbId_MmbId,CdtrAgt_FinInstnId_Nm,Dbtr_Nm,Dbtr_Id_PrvtId_Othr_Id,"
                                    + "Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry,Dbtr_CtctDtls_Nm,Dbtr_CtctDtls_PhneNb,Dbtr_CtctDtls_MobNb,"
                                    + "Dbtr_CtctDtls_EmailAdr,Dbtr_CtctDtls_Othr,DbtrAcct_Id_Othr_Id,DbtrAcct_Tp_Prtry,"
                                    + "DbtrAgt_FinInstnId_ClrSysMmbId_MmbId,DbtrAgt_FinInstnId_Nm,Mandate_Type,"
                                    + "Mandate_Status,Accept,Reject_Code,File_No,Image_Name,Upload_By,Upload_Date,Upload_Time,Verify_By,"
                                    + "Verify_Time,Zip_File_Name,Mandate_Mode) "
                                    + "VALUES('" + groupHeader31.getMsgId().trim() + "',"
                                    + "'" + groupHeader31.getCreDtTm() + "',"
                                    + "'" + initgPtyIdOrgIdOthrId.trim() + "',"
                                    + "'" + groupHeader31.getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                    + "'" + instgAgtFinInstnIdNm + "',"
                                    + "'" + groupHeader31.getInstdAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                    + "'" + instdAgtFinInstnIdNm + "',"
                                    + "'" + mandateInformation2.getMndtId().trim() + "',"
                                    + "'" + mandateInformation2.getMndtReqId() + "',"
                                    + "'" + mandateInformation2.getTp().getSvcLvl().getPrtry().trim() + "',"
                                    + "'" + mandateInformation2.getTp().getLclInstrm().getPrtry().trim() + "',"
                                    + "'" + ocrncsSeqTp + "',"
                                    + "'" + ocrncsFrqcy + "',"
                                    + "'" + ocrncsFrstColltnDt + "',"
                                    + "'" + ocrncsFnlColltnDt + "',"
                                    + "" + colltnAmt + ","
                                    + "" + maxAmount + ","
                                    + "'" + mandateInformation2.getCdtr().getNm().trim() + "',"
                                    + "'" + mandateInformation2.getCdtrAcct().getId().getOthr().getId() + "',"
                                    + "'" + mandateInformation2.getCdtrAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                    + "'" + cdtrAgtFinInstnIdNm + "',"
                                    + "'" + mandateInformation2.getDbtr().getNm().trim() + "',"
                                    + "'" + dbtrIdPrvtldOthrId + "',"
                                    + "'" + dbtrIdPrvtIdOthrIdSchmeNmPrtry + "',"
                                    + "'',"
                                    + "'" + dbtrCtctDtlsPhneNb + "',"
                                    + "'" + dbtrCtctDtlsMobNb + "',"
                                    + "'" + dbtrCtctDtlsEmailAdr + "',"
                                    + "'" + dbtrCtctDtlsOthr + "',"
                                    + "'" + mandateInformation2.getDbtrAcct().getId().getOthr().getId().trim() + "',"
                                    + "'" + mandateInformation2.getDbtrAcct().getTp().getPrtry().trim() + "',"
                                    + "'" + mandateInformation2.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                    + "'" + dbtrAgtFinInstnIdNm + "',"
                                    + "'" + fileProcessType.toUpperCase().trim() + "',"
                                    + "'',"
                                    + "'',"
                                    + "'',"
                                    + "" + fileNo + ","
                                    + "'" + fileName.substring(0, fileName.lastIndexOf("-")).trim() + "',"
                                    + "'" + userName + "',"
                                    + "'" + currentDt + "',"
                                    + "now(),"
                                    + "'',"
                                    + "null,"
                                    + "'" + uploadedFileName + "','" + mandateMode + "')").executeUpdate();
                            if (n <= 0) {
                                throw new ApplicationException("Problem In Data Uploading..");
                            }
                            if (processingMode.equalsIgnoreCase("H2H")) {
                                int s = em.createNativeQuery("update mms_upload_xml_detail set FILE_GEN_FLAG = 'N' where MndtId ='" + mandateInformation2.getMndtId().trim() + "'").executeUpdate();
                                if (s <= 0) {
                                    throw new ApplicationException("Problem In File_gen_flag updating.");
                                }
                            }
                        } else if (fileProcessType.equalsIgnoreCase("AMEND")) {     //Amend Inward
                            JAXBContext jaxbContext = JAXBContext.newInstance(com.cbs.pojo.mms.pain010.ObjectFactory.class);
                            com.cbs.pojo.mms.pain010.Document doc = ((JAXBElement<com.cbs.pojo.mms.pain010.Document>) jaxbContext.createUnmarshaller().
                                    unmarshal(new File(dir + "/" + fileName))).getValue();
                            MandateAmendmentRequestV01 mandateAmendmentRequestV01 = doc.getMndtAmdmntReq();
                            com.cbs.pojo.mms.pain010.GroupHeader31 groupHeader31 = mandateAmendmentRequestV01.getGrpHdr();     //GrpHdr
                            com.cbs.pojo.mms.pain010.MandateAmendment1 mandateAmendment1 = mandateAmendmentRequestV01.getUndrlygAmdmntDtls();  //UndrlygAmdmntDtls
                            String initgPtyIdOrgIdOthrId = "";

                            if (groupHeader31.getInitgPty()
                                    != null) {
                                if (groupHeader31.getInitgPty().getId() != null) {
                                    if (groupHeader31.getInitgPty().getId().getOrgId() != null) {
                                        if (groupHeader31.getInitgPty().getId().getOrgId().getOthr() != null) {
                                            List<com.cbs.pojo.mms.pain010.GenericOrganisationIdentification1> list = groupHeader31.getInitgPty().
                                                    getId().getOrgId().getOthr();
                                            for (com.cbs.pojo.mms.pain010.GenericOrganisationIdentification1 obj : list) {
                                                initgPtyIdOrgIdOthrId = obj.getId() == null ? "" : obj.getId().trim();
                                            }
                                        }
                                    }

                                }
                            }
                            String instgAgtFinInstnIdNm = groupHeader31.getInstgAgt().getFinInstnId().getNm() == null ? ""
                                    : groupHeader31.getInstgAgt().getFinInstnId().getNm().trim();
                            String instdAgtFinInstnIdNm = groupHeader31.getInstdAgt().getFinInstnId().getNm() == null ? ""
                                    : groupHeader31.getInstdAgt().getFinInstnId().getNm().trim();
                            String ocrncsSeqTp = "", ocrncsFrqcy = "", ocrncsFrstColltnDt = "", ocrncsFnlColltnDt = "";

                            if (mandateAmendment1.getMndt()
                                    .getOcrncs() != null) {
                                if (mandateAmendment1.getMndt().getOcrncs().getSeqTp() != null) {
                                    ocrncsSeqTp = mandateAmendment1.getMndt().getOcrncs().getSeqTp().value() == null ? ""
                                            : mandateAmendment1.getMndt().getOcrncs().getSeqTp().value().trim();
                                }
                                if (mandateAmendment1.getMndt().getOcrncs().getFrqcy() != null) {
                                    ocrncsFrqcy = mandateAmendment1.getMndt().getOcrncs().getFrqcy().value() == null ? ""
                                            : mandateAmendment1.getMndt().getOcrncs().getFrqcy().value().trim();
                                }

                                if (mandateAmendment1.getMndt().getOcrncs().getFrstColltnDt() != null) {
                                    Calendar calendar = mandateAmendment1.getMndt().getOcrncs().getFrstColltnDt().toGregorianCalendar();
                                    ymdFormatter.setTimeZone(calendar.getTimeZone());
                                    ocrncsFrstColltnDt = ymdFormatter.format(calendar.getTime());
                                }
                                if (mandateAmendment1.getMndt().getOcrncs().getFnlColltnDt() != null) {
                                    Calendar calendar = mandateAmendment1.getMndt().getOcrncs().getFnlColltnDt().toGregorianCalendar();
                                    ymdFormatter.setTimeZone(calendar.getTimeZone());
                                    ocrncsFnlColltnDt = ymdFormatter.format(calendar.getTime());
                                }
                            }
                            BigDecimal colltnAmt = mandateAmendment1.getMndt().getColltnAmt() == null ? new BigDecimal("0")
                                    : mandateAmendment1.getMndt().getColltnAmt().getValue();
                            BigDecimal maxAmount = mandateAmendment1.getMndt().getMaxAmt() == null ? new BigDecimal("0")
                                    : mandateAmendment1.getMndt().getMaxAmt().getValue();
                            String cdtrAgtFinInstnIdNm = mandateAmendment1.getMndt().getCdtrAgt().getFinInstnId().getNm() == null ? ""
                                    : mandateAmendment1.getMndt().getCdtrAgt().getFinInstnId().getNm().trim();
                            String dbtrIdPrvtldOthrId = "", dbtrIdPrvtIdOthrSchmeNmPrtry = "";

                            if (mandateAmendment1.getMndt()
                                    .getDbtr().getId() != null) {
                                if (mandateAmendment1.getMndt().getDbtr().getId().getPrvtId() != null) {
                                    if (mandateAmendment1.getMndt().getDbtr().getId().getPrvtId().getOthr() != null) {
                                        List<com.cbs.pojo.mms.pain010.GenericPersonIdentification1> list1 = mandateAmendment1.getMndt().
                                                getDbtr().getId().getPrvtId().getOthr();
                                        for (com.cbs.pojo.mms.pain010.GenericPersonIdentification1 obj : list1) {
                                            dbtrIdPrvtldOthrId = obj.getId() == null ? "" : obj.getId().trim();
                                            if (obj.getSchmeNm() != null) {
                                                dbtrIdPrvtIdOthrSchmeNmPrtry = obj.getSchmeNm().getPrtry() == null ? ""
                                                        : obj.getSchmeNm().getPrtry().trim();
                                            }
                                        }
                                    }
                                }
                            }
                            String dbtrCtctDtlsPhneNb = "", dbtrCtctDtlsMobNb = "", dbtrCtctDtlsEmailAdr = "", dbtrCtctDtlsOthr = "";

                            if (mandateAmendment1.getMndt()
                                    .getDbtr().getCtctDtls() != null) {
                                dbtrCtctDtlsPhneNb = mandateAmendment1.getMndt().getDbtr().getCtctDtls().getPhneNb() == null ? ""
                                        : mandateAmendment1.getMndt().getDbtr().getCtctDtls().getPhneNb().trim();
                                dbtrCtctDtlsMobNb = mandateAmendment1.getMndt().getDbtr().getCtctDtls().getMobNb() == null ? ""
                                        : mandateAmendment1.getMndt().getDbtr().getCtctDtls().getMobNb().trim();
                                dbtrCtctDtlsEmailAdr = mandateAmendment1.getMndt().getDbtr().getCtctDtls().getEmailAdr() == null ? ""
                                        : mandateAmendment1.getMndt().getDbtr().getCtctDtls().getEmailAdr().trim();
                                dbtrCtctDtlsOthr = mandateAmendment1.getMndt().getDbtr().getCtctDtls().getOthr() == null ? ""
                                        : mandateAmendment1.getMndt().getDbtr().getCtctDtls().getOthr().trim();
                            }
                            String dbtrAcctTpPrtry = "";

                            if (mandateAmendment1.getMndt()
                                    .getDbtrAcct().getTp() != null) {
                                dbtrAcctTpPrtry = mandateAmendment1.getMndt().getDbtrAcct().getTp().getPrtry() == null ? ""
                                        : mandateAmendment1.getMndt().getDbtrAcct().getTp().getPrtry();
                            }
                            String dbtrAgtFinInstnIdNm = mandateAmendment1.getMndt().getDbtrAgt().getFinInstnId().getNm() == null
                                    ? "" : mandateAmendment1.getMndt().getDbtrAgt().getFinInstnId().getNm();
                            //Insert Data Into Table
                            int n = em.createNativeQuery("INSERT INTO mms_upload_xml_detail(MsgId,CreDtTm,"
                                    + "InitgPty_Id_OrgId_Othr_Id,InstgAgt_FinInstnId_ClrSysMmbId_MmbId,"
                                    + "InstgAgt_FinInstnId_Nm,InstdAgt_FinInstnId_ClrSysMmbId_MmbId,InstdAgt_FinInstnId_Nm,"
                                    + "MndtId,MndtReqId,Tp_SvcLvl_Prtry,Tp_LclInstrm_Prtry,Ocrncs_SeqTp,Ocrncs_Frqcy,"
                                    + "Ocrncs_FrstColltnDt,Ocrncs_FnlColltnDt,ColltnAmt,MaxAmt,Cdtr_Nm,CdtrAcct_Id_Othr_Id,"
                                    + "CdtrAgt_FinInstnId_ClrSysMmbId_MmbId,CdtrAgt_FinInstnId_Nm,Dbtr_Nm,Dbtr_Id_PrvtId_Othr_Id,"
                                    + "Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry,Dbtr_CtctDtls_Nm,Dbtr_CtctDtls_PhneNb,Dbtr_CtctDtls_MobNb,"
                                    + "Dbtr_CtctDtls_EmailAdr,Dbtr_CtctDtls_Othr,DbtrAcct_Id_Othr_Id,DbtrAcct_Tp_Prtry,"
                                    + "DbtrAgt_FinInstnId_ClrSysMmbId_MmbId,DbtrAgt_FinInstnId_Nm,Mandate_Type,"
                                    + "Mandate_Status,Accept,Reject_Code,File_No,Image_Name,Upload_By,Upload_Date,"
                                    + "Upload_Time,Verify_By,Verify_Time,Zip_File_Name,UndrlygAmdmntDtls_AmdmntRsn_Rsn_Prtry,Mandate_Mode) "
                                    + "VALUES('" + groupHeader31.getMsgId().trim() + "',"
                                    + "'" + groupHeader31.getCreDtTm() + "',"
                                    + "'" + initgPtyIdOrgIdOthrId.trim() + "',"
                                    + "'" + groupHeader31.getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                    + "'" + instgAgtFinInstnIdNm + "',"
                                    + "'" + groupHeader31.getInstdAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                    + "'" + instdAgtFinInstnIdNm + "',"
                                    + "'" + mandateAmendment1.getOrgnlMndt().getOrgnlMndtId().trim() + "',"
                                    + "'" + mandateAmendment1.getMndt().getMndtReqId().trim() + "',"
                                    + "'" + mandateAmendment1.getMndt().getTp().getSvcLvl().getPrtry().trim() + "',"
                                    + "'" + mandateAmendment1.getMndt().getTp().getLclInstrm().getPrtry().trim() + "',"
                                    + "'" + ocrncsSeqTp + "',"
                                    + "'" + ocrncsFrqcy + "',"
                                    + "'" + ocrncsFrstColltnDt + "',"
                                    + "'" + ocrncsFnlColltnDt + "',"
                                    + "" + colltnAmt + ","
                                    + "" + maxAmount + ","
                                    + "'" + mandateAmendment1.getMndt().getCdtr().getNm().trim() + "',"
                                    + "'" + mandateAmendment1.getMndt().getCdtrAcct().getId().getOthr().getId() + "',"
                                    + "'" + mandateAmendment1.getMndt().getCdtrAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                    + "'" + cdtrAgtFinInstnIdNm + "','" + mandateAmendment1.getMndt().getDbtr().getNm().trim() + "',"
                                    + "'" + dbtrIdPrvtldOthrId + "',"
                                    + "'" + dbtrIdPrvtIdOthrSchmeNmPrtry + "',"
                                    + "'',"
                                    + "'" + dbtrCtctDtlsPhneNb + "',"
                                    + "'" + dbtrCtctDtlsMobNb + "',"
                                    + "'" + dbtrCtctDtlsEmailAdr + "',"
                                    + "'" + dbtrCtctDtlsOthr + "',"
                                    + "'" + mandateAmendment1.getMndt().getDbtrAcct().getId().getOthr().getId().trim() + "',"
                                    + "'" + dbtrAcctTpPrtry + "',"
                                    + "'" + mandateAmendment1.getMndt().getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                    + "'" + dbtrAgtFinInstnIdNm + "',"
                                    + "'" + fileProcessType.toUpperCase().trim() + "',"
                                    + "'',"
                                    + "'',"
                                    + "'',"
                                    + "" + fileNo + ","
                                    + "'" + fileName.substring(0, fileName.lastIndexOf("-")).trim() + "',"
                                    + "'" + userName + "',"
                                    + "'" + currentDt + "',"
                                    + "now(),'',"
                                    + "null,"
                                    + "'" + uploadedFileName + "',"
                                    + "'" + mandateAmendment1.getAmdmntRsn().getRsn().getPrtry() + "','" + mandateMode + "')").executeUpdate();
                            if (n <= 0) {
                                throw new ApplicationException("Problem In Data Uploading..");
                            }
                            if (processingMode.equalsIgnoreCase("H2H")) {
                                int s = em.createNativeQuery("update mms_upload_xml_detail set FILE_GEN_FLAG = 'N' where MndtId ='" + mandateAmendment1.getMndt().getMndtReqId().trim() + "'").executeUpdate();
                                if (s <= 0) {
                                    throw new ApplicationException("Problem In File_gen_flag updating.");
                                }
                            }
                        }
                    }
                }
            }
            if (processingMode.equalsIgnoreCase("H2H")) {
                int zn = em.createNativeQuery("insert into cbs_npci_h2h_file_detail(file_date,file_name,file_type) "
                        + "values('" + ymd.format(new Date()) + "','" + uploadedFileName + "','IW')").executeUpdate();
                if (zn <= 0) {
                    throw new ApplicationException("Problem in cbs_npci_h2h_file_detail insertion.");
                }
            }
            ut.commit();
            //Sms Sending
            try {
                List list = em.createNativeQuery("select mndtid,cdtr_nm,colltnamt,maxamt,ocrncs_frqcy,"
                        + "dbtracct_id_othr_id from mms_upload_xml_detail where mandate_status='' and "
                        + "upload_date='" + ymd.format(new Date()) + "'").getResultList();
                if (!list.isEmpty()) {
                    List<TransferSmsRequestTo> smsList = new ArrayList<>();

                    List<MbSmsSenderBankDetailTO> bankTo = smsFacade.getBankAndSenderDetail();
                    String templateBankName = bankTo.get(0).getTemplateBankName().trim();

                    for (int i = 0; i < list.size(); i++) {
                        Vector ele = (Vector) list.get(i);
                        String umrn = ele.get(0).toString().trim();
                        String corporateName = ele.get(1).toString().trim();
                        Double collAmt = Double.parseDouble(ele.get(2).toString().trim());
                        Double maxAmount = Double.parseDouble(ele.get(3).toString().trim());
                        String frequency = ele.get(4).toString().trim();
                        String drAcno = ele.get(5).toString().trim();

                        Double actualAmt = collAmt != 0 ? collAmt : maxAmount;

                        //Extracting Mobile No
                        String mobileNo = "";
                        List smsData = em.createNativeQuery("select mobile_no from mb_subscriber_tab where "
                                + "acno='" + drAcno + "' and status=1 and auth='Y' and auth_status='V'").getResultList();
                        if (!smsData.isEmpty()) {
                            Vector smsEle = (Vector) smsData.get(0);
                            mobileNo = smsEle.get(0).toString().trim();
                        } else {
                            smsData = em.createNativeQuery("select ifnull(mobilenumber,'') as mobile from "
                                    + "cbs_customer_master_detail cu,customerid id where cu.customerid=id.custid "
                                    + "and id.acno='" + drAcno + "'").getResultList();
                            Vector smsEle = (Vector) smsData.get(0);
                            mobileNo = "+91" + smsEle.get(0).toString().trim();
                        }
                        mobileNo = mobileNo.trim().length() != 13 ? "" : mobileNo.trim();
                        if (mobileNo.length() == 13) {
                            TransferSmsRequestTo trfSmsTo = new TransferSmsRequestTo();
                            trfSmsTo.setMsgType("PAT");
                            trfSmsTo.setTemplate(SmsType.MANDATE_RECEIPT);
                            trfSmsTo.setAcno(drAcno);
                            trfSmsTo.setBankName(templateBankName);
                            trfSmsTo.setPin(umrn);
                            trfSmsTo.setPullCode(corporateName);
                            trfSmsTo.setFirstCheque(actualAmt.toString());
                            trfSmsTo.setServices(frequency);
                            trfSmsTo.setUserName(userName);
                            trfSmsTo.setPromoMobile(mobileNo);

                            smsList.add(trfSmsTo);
                        }
                    }
                    //Now sending sms
                    for (TransferSmsRequestTo obj : smsList) {
                        smsFacade.sendSms(obj);
                    }
                }
            } catch (Exception ex) {
                System.out.println("Problem In Sending the SMS In MMS Upload");
            }
        } catch (Exception ex) {
            try {
                ex.printStackTrace();
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "true";
    }

    public String uploadEsignData(File dir, String currentDt, String userName, Integer fileNo,
            String uploadedFileName, String mandateMode, String processingMode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        SimpleDateFormat ymdFormatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            ut.begin();
            File[] files = dir.listFiles();
            if (files == null || files.length == 0) {
                throw new ApplicationException("There is no data to upload");
            }
            //Here New Code
            for (File file : files) {
                String fileName = file.getName().trim(); //Complete FileName
                String fileType = fileName.substring(fileName.indexOf(".") + 1).trim(); //Only Extension
                if (fileType.equalsIgnoreCase("xml")) {
                    String fileProcessType = fileName.split("-")[1]; //It will give either CREATE,AMEND,CANCEL
                    //Unmarshalling-->XML To Java Object
                    if (fileProcessType.equalsIgnoreCase("CREATE")) {   //Create Inward
                        JAXBContext jaxbContext = JAXBContext.newInstance(com.cbs.pojo.mms.esign.pain009.ObjectFactory.class);
                        com.cbs.pojo.mms.esign.pain009.Document doc = ((JAXBElement<com.cbs.pojo.mms.esign.pain009.Document>) jaxbContext.createUnmarshaller().unmarshal(new File(dir + "/" + fileName))).getValue();
                        com.cbs.pojo.mms.esign.pain009.MandateInitiationRequestV04 mandateInitiationRequestV04 = doc.getMndtInitnReq();
                        com.cbs.pojo.mms.esign.pain009.GroupHeader47 groupHeader47 = mandateInitiationRequestV04.getGrpHdr();              //GrpHdr
                        List<com.cbs.pojo.mms.esign.pain009.Mandate7> mandate7List = mandateInitiationRequestV04.getMndt();    //Mndt
                        //Fething other required values.
                        String instgAgtFinInstnIdNm = groupHeader47.getInstgAgt().getFinInstnId().getNm() == null ? ""
                                : groupHeader47.getInstgAgt().getFinInstnId().getNm().trim();
                        String instdAgtFinInstnIdNm = groupHeader47.getInstdAgt().getFinInstnId().getNm() == null ? ""
                                : groupHeader47.getInstdAgt().getFinInstnId().getNm().trim();

                        String ocrncsSeqTp = "", ocrncsFrqcyTp = "", ocrncsFrstColltnDt = "", ocrncsFnlColltnDt = "";
                        //We are assuming that in a sinle xml file there will be only one MnDt
                        if (mandate7List != null && !mandate7List.isEmpty() && mandate7List.get(0) != null) {
                            com.cbs.pojo.mms.esign.pain009.Mandate7 mandate7 = mandate7List.get(0);

                            if (mandate7.getOcrncs() != null) {
                                if (mandate7.getOcrncs().getSeqTp() != null) {
                                    ocrncsSeqTp = mandate7.getOcrncs().getSeqTp().value() == null ? ""
                                            : mandate7.getOcrncs().getSeqTp().value().trim();
                                }
                                if (mandate7.getOcrncs().getFrqcy().getTp() != null) {
                                    ocrncsFrqcyTp = mandate7.getOcrncs().getFrqcy().getTp().value() == null ? ""
                                            : mandate7.getOcrncs().getFrqcy().getTp().value().trim();  //New addition here
                                }

                                if (mandate7.getOcrncs().getFrstColltnDt() != null) {
                                    Calendar calendar = mandate7.getOcrncs().getFrstColltnDt().toGregorianCalendar();
                                    ymdFormatter.setTimeZone(calendar.getTimeZone());
                                    ocrncsFrstColltnDt = ymdFormatter.format(calendar.getTime());
                                }
                                if (mandate7.getOcrncs().getFnlColltnDt() != null) {
                                    Calendar calendar = mandate7.getOcrncs().getFnlColltnDt().toGregorianCalendar();
                                    ymdFormatter.setTimeZone(calendar.getTimeZone());
                                    ocrncsFnlColltnDt = ymdFormatter.format(calendar.getTime());
                                }
                            }

                            String cdtrAgtFinInstnIdNm = mandate7.getCdtrAgt().getFinInstnId().getNm() == null ? ""
                                    : mandate7.getCdtrAgt().getFinInstnId().getNm().trim();
                            String dbtrIdPrvtldOthrId = "", dbtrIdPrvtIdOthrIdSchmeNmPrtry = "";

                            if (mandate7.getDbtr().getId() != null) {
                                if (mandate7.getDbtr().getId().getPrvtId() != null) {
                                    if (mandate7.getDbtr().getId().getPrvtId().getOthr() != null) {
                                        List<com.cbs.pojo.mms.esign.pain009.GenericPersonIdentification1> list1 = mandate7.getDbtr().
                                                getId().getPrvtId().getOthr();
                                        for (com.cbs.pojo.mms.esign.pain009.GenericPersonIdentification1 obj : list1) {
                                            dbtrIdPrvtldOthrId = obj.getId() == null ? "" : obj.getId().trim();
                                            if (obj.getSchmeNm() != null) {
                                                dbtrIdPrvtIdOthrIdSchmeNmPrtry = obj.getSchmeNm().getPrtry() == null ? ""
                                                        : obj.getSchmeNm().getPrtry().trim();
                                            }
                                        }
                                    }
                                }
                            }

                            String dbtrCtctDtlsNm = "", dbtrCtctDtlsPhneNb = "", dbtrCtctDtlsMobNb = "",
                                    dbtrCtctDtlsEmailAdr = "", dbtrCtctDtlsOthr = "";

                            if (mandate7.getDbtr().getCtctDtls() != null) {
                                dbtrCtctDtlsNm = mandate7.getDbtr().getCtctDtls().getNm() == null ? ""
                                        : mandate7.getDbtr().getCtctDtls().getNm().trim(); //New addition here
                                dbtrCtctDtlsPhneNb = mandate7.getDbtr().getCtctDtls().getPhneNb() == null ? ""
                                        : mandate7.getDbtr().getCtctDtls().getPhneNb().trim();
                                dbtrCtctDtlsMobNb = mandate7.getDbtr().getCtctDtls().getMobNb() == null ? ""
                                        : mandate7.getDbtr().getCtctDtls().getMobNb().trim();
                                dbtrCtctDtlsEmailAdr = mandate7.getDbtr().getCtctDtls().getEmailAdr() == null ? ""
                                        : mandate7.getDbtr().getCtctDtls().getEmailAdr().trim();
                                dbtrCtctDtlsOthr = mandate7.getDbtr().getCtctDtls().getOthr() == null ? ""
                                        : mandate7.getDbtr().getCtctDtls().getOthr().trim();
                            }
                            String dbtrAgtFinInstnIdNm = mandate7.getDbtrAgt().getFinInstnId().getNm() == null ? ""
                                    : mandate7.getDbtrAgt().getFinInstnId().getNm().trim();
                            String initgPtyIdOrgIdOthrId = "";

                            if (groupHeader47.getInitgPty() != null) {
                                if (groupHeader47.getInitgPty().getId() != null) {
                                    if (groupHeader47.getInitgPty().getId().getOrgId() != null) {
                                        if (groupHeader47.getInitgPty().getId().getOrgId().getOthr() != null
                                                && !groupHeader47.getInitgPty().getId().getOrgId().getOthr().isEmpty()) {
                                            List<com.cbs.pojo.mms.esign.pain009.GenericOrganisationIdentification1> list = groupHeader47.getInitgPty().
                                                    getId().getOrgId().getOthr();
                                            for (com.cbs.pojo.mms.esign.pain009.GenericOrganisationIdentification1 obj : list) {
                                                initgPtyIdOrgIdOthrId = obj.getId() == null ? "" : obj.getId().trim();
                                            }
                                        }
                                    }

                                }
                            }

                            BigDecimal colltnAmt = mandate7.getColltnAmt() == null ? new BigDecimal("0")
                                    : mandate7.getColltnAmt().getValue();
                            BigDecimal maxAmount = mandate7.getMaxAmt() == null ? new BigDecimal("0")
                                    : mandate7.getMaxAmt().getValue();

                            //Insert Data Into Table
                            int n = em.createNativeQuery("INSERT INTO mms_upload_xml_detail(MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,"
                                    + "InstgAgt_FinInstnId_ClrSysMmbId_MmbId,InstgAgt_FinInstnId_Nm,"
                                    + "InstdAgt_FinInstnId_ClrSysMmbId_MmbId,InstdAgt_FinInstnId_Nm,MndtId,MndtReqId,"
                                    + "Tp_SvcLvl_Prtry,Tp_LclInstrm_Prtry,Ocrncs_SeqTp,Ocrncs_Frqcy,Ocrncs_FrstColltnDt,"
                                    + "Ocrncs_FnlColltnDt,ColltnAmt,MaxAmt,Cdtr_Nm,CdtrAcct_Id_Othr_Id,"
                                    + "CdtrAgt_FinInstnId_ClrSysMmbId_MmbId,CdtrAgt_FinInstnId_Nm,Dbtr_Nm,Dbtr_Id_PrvtId_Othr_Id,"
                                    + "Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry,Dbtr_CtctDtls_Nm,Dbtr_CtctDtls_PhneNb,Dbtr_CtctDtls_MobNb,"
                                    + "Dbtr_CtctDtls_EmailAdr,Dbtr_CtctDtls_Othr,DbtrAcct_Id_Othr_Id,DbtrAcct_Tp_Prtry,"
                                    + "DbtrAgt_FinInstnId_ClrSysMmbId_MmbId,DbtrAgt_FinInstnId_Nm,Mandate_Type,"
                                    + "Mandate_Status,Accept,Reject_Code,File_No,Image_Name,Upload_By,Upload_Date,Upload_Time,Verify_By,"
                                    + "Verify_Time,Zip_File_Name,Mandate_Mode,UnderlygAmdmntDtls_OrgnlMndt_OrgnlMndtId,UndrlygCxlDtls_CxlRsn_Rsn_Prtry) "
                                    + "VALUES('" + groupHeader47.getMsgId().trim() + "',"
                                    + "'" + groupHeader47.getCreDtTm() + "',"
                                    + "'" + initgPtyIdOrgIdOthrId.trim() + "',"
                                    + "'" + groupHeader47.getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                    + "'" + instgAgtFinInstnIdNm + "',"
                                    + "'" + groupHeader47.getInstdAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                    + "'" + instdAgtFinInstnIdNm + "',"
                                    + "'" + mandate7.getMndtId().get(0).trim() + "',"
                                    + "'" + mandate7.getMndtReqId() + "',"
                                    + "'" + mandate7.getTp().getSvcLvl().getPrtry().trim() + "',"
                                    + "'" + mandate7.getTp().getLclInstrm().getPrtry().trim() + "',"
                                    + "'" + ocrncsSeqTp + "',"
                                    + "'" + ocrncsFrqcyTp + "',"
                                    + "'" + ocrncsFrstColltnDt + "',"
                                    + "'" + ocrncsFnlColltnDt + "',"
                                    + "" + colltnAmt + ","
                                    + "" + maxAmount + ","
                                    + "'" + mandate7.getCdtr().getNm().trim() + "',"
                                    + "'" + mandate7.getCdtrAcct().getId().getOthr().getId() + "',"
                                    + "'" + mandate7.getCdtrAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                    + "'" + cdtrAgtFinInstnIdNm + "',"
                                    + "'" + mandate7.getDbtr().getNm().trim() + "',"
                                    + "'" + dbtrIdPrvtldOthrId + "',"
                                    + "'" + dbtrIdPrvtIdOthrIdSchmeNmPrtry + "',"
                                    + "'" + dbtrCtctDtlsNm + "',"
                                    + "'" + dbtrCtctDtlsPhneNb + "',"
                                    + "'" + dbtrCtctDtlsMobNb + "',"
                                    + "'" + dbtrCtctDtlsEmailAdr + "',"
                                    + "'" + dbtrCtctDtlsOthr + "',"
                                    + "'" + mandate7.getDbtrAcct().getId().getOthr().getId().trim() + "',"
                                    + "'" + mandate7.getDbtrAcct().getTp().getPrtry().trim() + "',"
                                    + "'" + mandate7.getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                    + "'" + dbtrAgtFinInstnIdNm + "',"
                                    + "'" + fileProcessType.toUpperCase().trim() + "',"
                                    + "'',"
                                    + "'',"
                                    + "'',"
                                    + "" + fileNo + ","
                                    + "'" + fileName.substring(0, fileName.lastIndexOf("-")).trim() + "',"
                                    + "'" + userName + "',"
                                    + "'" + currentDt + "',"
                                    + "now(),"
                                    + "'',"
                                    + "null,"
                                    + "'" + uploadedFileName + "','" + mandateMode + "','','')").executeUpdate();
                            if (n <= 0) {
                                throw new ApplicationException("Problem In Data Uploading..");
                            }
                            if (processingMode.equalsIgnoreCase("H2H")) {
                                int s = em.createNativeQuery("update mms_upload_xml_detail set FILE_GEN_FLAG = 'N' where MndtId ='" + mandate7.getMndtId().get(0).trim() + "'").executeUpdate();
                                if (s <= 0) {
                                    throw new ApplicationException("Problem In File_gen_flag updating.");
                                }
                            }
                        }
                    } else if (fileProcessType.equalsIgnoreCase("AMEND")) {
                        JAXBContext jaxbContext = JAXBContext.newInstance(com.cbs.pojo.mms.esign.pain010.ObjectFactory.class);
                        com.cbs.pojo.mms.esign.pain010.Document doc = ((JAXBElement<com.cbs.pojo.mms.esign.pain010.Document>) jaxbContext.createUnmarshaller().unmarshal(new File(dir + "/" + fileName))).getValue();
                        com.cbs.pojo.mms.esign.pain010.MandateAmendmentRequestV04 mandateAmendReqV04 = doc.getMndtAmdmntReq();
                        com.cbs.pojo.mms.esign.pain010.GroupHeader47 groupHeader47 = mandateAmendReqV04.getGrpHdr();
                        List<com.cbs.pojo.mms.esign.pain010.MandateAmendment4> mandate4List = mandateAmendReqV04.getUndrlygAmdmntDtls();


                        //Fetching groupHeader required values.
                        String groupHeaderMsgId = groupHeader47.getMsgId();
                        Calendar calendar = groupHeader47.getCreDtTm().toGregorianCalendar();
                        ymdFormatter.setTimeZone(calendar.getTimeZone());
                        String creDtTm = ymdFormatter.format(calendar.getTime());


                        String instgAgtFinInstnIdNm = groupHeader47.getInstgAgt().getFinInstnId().getNm() == null ? ""
                                : groupHeader47.getInstgAgt().getFinInstnId().getNm().trim();
                        String instdAgtFinInstnIdNm = groupHeader47.getInstdAgt().getFinInstnId().getNm() == null ? ""
                                : groupHeader47.getInstdAgt().getFinInstnId().getNm().trim();

                        //Fetching UnderLyingDtls required value
                        String ocrncsSeqTp = "", ocrncsFrqcyTp = "", ocrncsFrstColltnDt = "", ocrncsFnlColltnDt = "";
                        String prtry = "";
                        String mndtId = "";
                        String mndtReqId = "";
                        //We are assuming that in a sinle xml file there will be only one MnDt    
                        if (mandate4List != null && !mandate4List.isEmpty() && mandate4List.get(0) != null) {
                            com.cbs.pojo.mms.esign.pain010.MandateAmendment4 mandateAmend4 = mandate4List.get(0);
                            if (mandateAmend4.getMndt().getOcrncs() != null) {
                                if (mandateAmend4.getMndt().getOcrncs().getSeqTp() != null) {
                                    ocrncsSeqTp = mandateAmend4.getMndt().getOcrncs().getSeqTp().value() == null ? ""
                                            : mandateAmend4.getMndt().getOcrncs().getSeqTp().value().trim();
                                }
                                if (mandateAmend4.getMndt().getOcrncs().getFrqcy() != null) {
                                    ocrncsFrqcyTp = mandateAmend4.getMndt().getOcrncs().getFrqcy().getTp().value() == null ? ""
                                            : mandateAmend4.getMndt().getOcrncs().getFrqcy().getTp().value().trim();  //New addition here
                                }

                                if (mandateAmend4.getMndt().getOcrncs().getFrstColltnDt() != null) {
                                    Calendar calendar1 = mandateAmend4.getMndt().getOcrncs().getFrstColltnDt().toGregorianCalendar();
                                    ymdFormatter.setTimeZone(calendar.getTimeZone());
                                    ocrncsFrstColltnDt = ymdFormatter.format(calendar.getTime());
                                }
                                if (mandateAmend4.getMndt().getOcrncs().getFnlColltnDt() != null) {
                                    Calendar calendar2 = mandateAmend4.getMndt().getOcrncs().getFnlColltnDt().toGregorianCalendar();
                                    ymdFormatter.setTimeZone(calendar.getTimeZone());
                                    ocrncsFnlColltnDt = ymdFormatter.format(calendar.getTime());
                                }
                            }
                            String cdtrAgtFinInstnIdNm = mandateAmend4.getMndt().getCdtrAgt().getFinInstnId().getNm() == null ? ""
                                    : mandateAmend4.getMndt().getCdtrAgt().getFinInstnId().getNm().trim();
                            String dbtrIdPrvtldOthrId = "", dbtrIdPrvtIdOthrIdSchmeNmPrtry = "";
                            if (mandateAmend4.getMndt().getDbtr().getId() != null) {
                                if (mandateAmend4.getMndt().getDbtr().getId().getPrvtId().getOthr() != null) {
                                    List<com.cbs.pojo.mms.esign.pain010.GenericPersonIdentification1> list1 = mandateAmend4.getMndt().getDbtr().
                                            getId().getPrvtId().getOthr();
                                    for (com.cbs.pojo.mms.esign.pain010.GenericPersonIdentification1 obj : list1) {
                                        dbtrIdPrvtldOthrId = obj.getId() == null ? "" : obj.getId().trim();
                                        if (obj.getSchmeNm() != null) {
                                            dbtrIdPrvtIdOthrIdSchmeNmPrtry = obj.getSchmeNm().getPrtry() == null ? ""
                                                    : obj.getSchmeNm().getPrtry().trim();
                                        }
                                    }
                                }
                            }
                            String dbtrCtctDtlsNm = "", dbtrCtctDtlsPhneNb = "", dbtrCtctDtlsMobNb = "",
                                    dbtrCtctDtlsEmailAdr = "", dbtrCtctDtlsOthr = "";

                            if (mandateAmend4.getMndt().getDbtr().getCtctDtls() != null) {
                                dbtrCtctDtlsNm = mandateAmend4.getMndt().getDbtr().getCtctDtls().getNm() == null ? ""
                                        : mandateAmend4.getMndt().getDbtr().getCtctDtls().getNm().trim(); //New addition here
                                dbtrCtctDtlsPhneNb = mandateAmend4.getMndt().getDbtr().getCtctDtls().getPhneNb() == null ? ""
                                        : mandateAmend4.getMndt().getDbtr().getCtctDtls().getPhneNb().trim();
                                dbtrCtctDtlsMobNb = mandateAmend4.getMndt().getDbtr().getCtctDtls().getMobNb() == null ? ""
                                        : mandateAmend4.getMndt().getDbtr().getCtctDtls().getMobNb().trim();
                                dbtrCtctDtlsEmailAdr = mandateAmend4.getMndt().getDbtr().getCtctDtls().getEmailAdr() == null ? ""
                                        : mandateAmend4.getMndt().getDbtr().getCtctDtls().getEmailAdr().trim();
                                dbtrCtctDtlsOthr = mandateAmend4.getMndt().getDbtr().getCtctDtls().getOthr() == null ? ""
                                        : mandateAmend4.getMndt().getDbtr().getCtctDtls().getOthr().trim();
                            }
                            String dbtrAgtFinInstnIdNm = mandateAmend4.getMndt().getDbtrAgt().getFinInstnId().getNm() == null ? ""
                                    : mandateAmend4.getMndt().getDbtrAgt().getFinInstnId().getNm().trim();

                            String orgnlMandateId = mandateAmend4.getOrgnlMndt().getOrgnlMndtId().trim() == null ? ""
                                    : mandateAmend4.getOrgnlMndt().getOrgnlMndtId().trim();

                            String amendgPtyIdOrgIdOthrId = "";
                            if (groupHeader47.getInitgPty() != null) {
                                if (groupHeader47.getInitgPty().getId() != null) {
                                    if (groupHeader47.getInitgPty().getId().getOrgId() != null) {
                                        if (groupHeader47.getInitgPty().getId().getOrgId().getOthr() != null
                                                && !groupHeader47.getInitgPty().getId().getOrgId().getOthr().isEmpty()) {
                                            List<com.cbs.pojo.mms.esign.pain010.GenericOrganisationIdentification1> list = groupHeader47.getInitgPty().
                                                    getId().getOrgId().getOthr();
                                            for (com.cbs.pojo.mms.esign.pain010.GenericOrganisationIdentification1 obj : list) {
                                                amendgPtyIdOrgIdOthrId = obj.getId() == null ? "" : obj.getId().trim();
                                            }
                                        }
                                    }

                                }
                            }

                            BigDecimal collAmt = mandateAmend4.getMndt().getColltnAmt() == null ? new BigDecimal(0)
                                    : mandateAmend4.getMndt().getColltnAmt().getValue();
                            BigDecimal maxAmount = mandateAmend4.getMndt().getMaxAmt() == null ? new BigDecimal("0")
                                    : mandateAmend4.getMndt().getMaxAmt().getValue();
                            //Insert Data Into Table
                            int n = em.createNativeQuery("INSERT INTO mms_upload_xml_detail(MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,"
                                    + "InstgAgt_FinInstnId_ClrSysMmbId_MmbId,InstgAgt_FinInstnId_Nm,"
                                    + "InstdAgt_FinInstnId_ClrSysMmbId_MmbId,InstdAgt_FinInstnId_Nm,MndtId,MndtReqId,"
                                    + "Tp_SvcLvl_Prtry,Tp_LclInstrm_Prtry,Ocrncs_SeqTp,Ocrncs_Frqcy,Ocrncs_FrstColltnDt,"
                                    + "Ocrncs_FnlColltnDt,ColltnAmt,MaxAmt,Cdtr_Nm,CdtrAcct_Id_Othr_Id,"
                                    + "CdtrAgt_FinInstnId_ClrSysMmbId_MmbId,CdtrAgt_FinInstnId_Nm,Dbtr_Nm,Dbtr_Id_PrvtId_Othr_Id,"
                                    + "Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry,Dbtr_CtctDtls_Nm,Dbtr_CtctDtls_PhneNb,Dbtr_CtctDtls_MobNb,"
                                    + "Dbtr_CtctDtls_EmailAdr,Dbtr_CtctDtls_Othr,DbtrAcct_Id_Othr_Id,DbtrAcct_Tp_Prtry,"
                                    + "DbtrAgt_FinInstnId_ClrSysMmbId_MmbId,DbtrAgt_FinInstnId_Nm,Mandate_Type,"
                                    + "Mandate_Status,Accept,Reject_Code,File_No,Image_Name,Upload_By,Upload_Date,Upload_Time,Verify_By,"
                                    + "Verify_Time,Zip_File_Name,Mandate_Mode,UnderlygAmdmntDtls_OrgnlMndt_OrgnlMndtId,UndrlygCxlDtls_CxlRsn_Rsn_Prtry) "
                                    + "VALUES('" + groupHeader47.getMsgId().trim() + "',"
                                    + "'" + groupHeader47.getCreDtTm() + "',"
                                    + "'" + amendgPtyIdOrgIdOthrId.trim() + "',"
                                    + "'" + groupHeader47.getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                    + "'" + instgAgtFinInstnIdNm + "',"
                                    + "'" + groupHeader47.getInstdAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                    + "'" + instdAgtFinInstnIdNm + "',"
                                    + "'" + mandateAmend4.getMndt().getMndtId().trim() + "',"
                                    + "'" + mandateAmend4.getMndt().getMndtReqId() + "',"
                                    + "'" + mandateAmend4.getMndt().getTp().getSvcLvl().getPrtry().trim() + "',"
                                    + "'" + mandateAmend4.getMndt().getTp().getLclInstrm().getPrtry().trim() + "',"
                                    + "'" + ocrncsSeqTp + "',"
                                    + "'" + ocrncsFrqcyTp + "',"
                                    + "'" + ocrncsFrstColltnDt + "',"
                                    + "'" + ocrncsFnlColltnDt + "',"
                                    + "" + collAmt + ","
                                    + "" + maxAmount + ","
                                    + "'" + mandateAmend4.getMndt().getCdtr().getNm().trim() + "',"
                                    + "'" + mandateAmend4.getMndt().getCdtrAcct().getId().getOthr().getId() + "',"
                                    + "'" + mandateAmend4.getMndt().getCdtrAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                    + "'" + cdtrAgtFinInstnIdNm + "',"
                                    + "'" + mandateAmend4.getMndt().getDbtr().getNm().trim() + "',"
                                    + "'" + dbtrIdPrvtldOthrId + "',"
                                    + "'" + dbtrIdPrvtIdOthrIdSchmeNmPrtry + "',"
                                    + "'" + dbtrCtctDtlsNm + "',"
                                    + "'" + dbtrCtctDtlsPhneNb + "',"
                                    + "'" + dbtrCtctDtlsMobNb + "',"
                                    + "'" + dbtrCtctDtlsEmailAdr + "',"
                                    + "'" + dbtrCtctDtlsOthr + "',"
                                    + "'" + mandateAmend4.getMndt().getDbtrAcct().getId().getOthr().getId().trim() + "',"
                                    + "'" + mandateAmend4.getMndt().getDbtrAcct().getTp().getPrtry().trim() + "',"
                                    + "'" + mandateAmend4.getMndt().getDbtrAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                    + "'" + dbtrAgtFinInstnIdNm + "',"
                                    + "'" + fileProcessType.toUpperCase().trim() + "',"
                                    + "'',"
                                    + "'',"
                                    + "'',"
                                    + "" + fileNo + ","
                                    + "'" + fileName.substring(0, fileName.lastIndexOf("-")).trim() + "',"
                                    + "'" + userName + "',"
                                    + "'" + currentDt + "',"
                                    + "now(),"
                                    + "'',"
                                    + "null,"
                                    + "'" + uploadedFileName + "','" + mandateMode + "','" + orgnlMandateId + "','')").executeUpdate();
                            if (n <= 0) {
                                throw new ApplicationException("Problem In Data Uploading..");
                            }
                            if (processingMode.equalsIgnoreCase("H2H")) {
                                int s = em.createNativeQuery("update mms_upload_xml_detail set FILE_GEN_FLAG = 'N' where MndtId ='" + mandateAmend4.getMndt().getMndtId().trim() + "'").executeUpdate();
                                if (s <= 0) {
                                    throw new ApplicationException("Problem In File_gen_flag updating.");
                                }
                            }
                        }
                    } else if (fileProcessType.equalsIgnoreCase("CANCEL")) {
                        JAXBContext jaxbContext = JAXBContext.newInstance(com.cbs.pojo.mms.esign.pain011.ObjectFactory.class);
                        com.cbs.pojo.mms.esign.pain011.Document doc = ((JAXBElement<com.cbs.pojo.mms.esign.pain011.Document>) jaxbContext.
                                createUnmarshaller().unmarshal(new File(dir + "/" + fileName))).getValue();
                        com.cbs.pojo.mms.esign.pain011.MandateCancellationRequestV04 mandateCancelReqV04 = doc.getMndtCxlReq();
                        com.cbs.pojo.mms.esign.pain011.GroupHeader47 groupHeader47 = mandateCancelReqV04.getGrpHdr();
                        List<com.cbs.pojo.mms.esign.pain011.MandateCancellation4> mandate4List = mandateCancelReqV04.getUndrlygCxlDtls();


                        String groupHeaderMsgId = groupHeader47.getMsgId();
                        Calendar calendar = groupHeader47.getCreDtTm().toGregorianCalendar();
                        ymdFormatter.setTimeZone(calendar.getTimeZone());
                        String creDtTm = ymdFormatter.format(calendar.getTime());


                        String instgAgtFinInstnIdNm = groupHeader47.getInstgAgt().getFinInstnId().getNm() == null ? ""
                                : groupHeader47.getInstgAgt().getFinInstnId().getNm().trim();
                        String instdAgtFinInstnIdNm = groupHeader47.getInstdAgt().getFinInstnId().getNm() == null ? ""
                                : groupHeader47.getInstdAgt().getFinInstnId().getNm().trim();


                        //Fetching UnderLyingDtls required value
                        String ocrncsSeqTp = "", ocrncsFrqcyTp = "", ocrncsFrstColltnDt = "", ocrncsFnlColltnDt = "";
                        String prtry = "";
                        String rsnPrtry = "";
                        String mndtId = "";
                        String mndtReqId = "";
                        String instgAgtFinInstIdmemberId = "";
                        String instdAgtFinInstIdmemberId = "";

                        instgAgtFinInstIdmemberId = groupHeader47.getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId() == null ? ""
                                : groupHeader47.getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId();

                        instgAgtFinInstIdmemberId = groupHeader47.getInstdAgt().getFinInstnId().getClrSysMmbId().getMmbId() == null ? ""
                                : groupHeader47.getInstdAgt().getFinInstnId().getClrSysMmbId().getMmbId();

                        if (mandate4List != null && !mandate4List.isEmpty() && mandate4List.get(0) != null) {
                            com.cbs.pojo.mms.esign.pain011.MandateCancellation4 mandateCancel4 = mandate4List.get(0);
                            if (mandateCancel4.getOrgnlMndt().getOrgnlMndt() != null) {
                                if (mandateCancel4.getOrgnlMndt().getOrgnlMndt().getOcrncs().getSeqTp() != null) {
                                    ocrncsSeqTp = mandateCancel4.getOrgnlMndt().getOrgnlMndt().getOcrncs().getSeqTp().value() == null ? ""
                                            : mandateCancel4.getOrgnlMndt().getOrgnlMndt().getOcrncs().getSeqTp().value().trim();
                                }
                                if (mandateCancel4.getOrgnlMndt().getOrgnlMndt().getOcrncs().getFrqcy() != null) {
                                    ocrncsFrqcyTp = mandateCancel4.getOrgnlMndt().getOrgnlMndt().getOcrncs().getFrqcy().getTp().value() == null ? ""
                                            : mandateCancel4.getOrgnlMndt().getOrgnlMndt().getOcrncs().getFrqcy().getTp().value().trim();  //New addition here
                                }

                                if (mandateCancel4.getOrgnlMndt().getOrgnlMndt().getOcrncs().getFrstColltnDt() != null) {
                                    Calendar calendar1 = mandateCancel4.getOrgnlMndt().getOrgnlMndt().getOcrncs().getFrstColltnDt().toGregorianCalendar();
                                    ymdFormatter.setTimeZone(calendar.getTimeZone());
                                    ocrncsFrstColltnDt = ymdFormatter.format(calendar.getTime());
                                }
                                if (mandateCancel4.getOrgnlMndt().getOrgnlMndt().getOcrncs().getFnlColltnDt() != null) {
                                    Calendar calendar2 = mandateCancel4.getOrgnlMndt().getOrgnlMndt().getOcrncs().getFnlColltnDt().toGregorianCalendar();
                                    ymdFormatter.setTimeZone(calendar.getTimeZone());
                                    ocrncsFnlColltnDt = ymdFormatter.format(calendar.getTime());
                                }
                            }

                            prtry = mandateCancel4.getCxlRsn().getRsn().getPrtry() == null ? ""
                                    : mandateCancel4.getCxlRsn().getRsn().getPrtry();

                            mndtId = mandateCancel4.getOrgnlMndt().getOrgnlMndtId() == null ? ""
                                    : mandateCancel4.getOrgnlMndt().getOrgnlMndtId();

                            String dbtrIdPrvtldOthrId = "", dbtrIdPrvtIdOthrIdSchmeNmPrtry = "", cdtrAgtFinInstnIdNm = "";

                            if (mandateCancel4.getOrgnlMndt().getOrgnlMndt() != null) {
                                cdtrAgtFinInstnIdNm = mandateCancel4.getOrgnlMndt().getOrgnlMndt().getCdtrAgt().getFinInstnId().getNm() == null ? ""
                                        : mandateCancel4.getOrgnlMndt().getOrgnlMndt().getCdtrAgt().getFinInstnId().getNm().trim();


                                if (mandateCancel4.getOrgnlMndt().getOrgnlMndt().getDbtr().getId().getPrvtId().getOthr() != null) {
                                    List<com.cbs.pojo.mms.esign.pain011.GenericPersonIdentification1> list1 = mandateCancel4.getOrgnlMndt().getOrgnlMndt().getDbtr().
                                            getId().getPrvtId().getOthr();
                                    for (com.cbs.pojo.mms.esign.pain011.GenericPersonIdentification1 obj : list1) {
                                        dbtrIdPrvtldOthrId = obj.getId() == null ? "" : obj.getId().trim();
                                        if (obj.getSchmeNm() != null) {
                                            dbtrIdPrvtIdOthrIdSchmeNmPrtry = obj.getSchmeNm().getPrtry() == null ? ""
                                                    : obj.getSchmeNm().getPrtry().trim();
                                        }
                                    }
                                }
                            }

                            String amendgPtyIdOrgIdOthrId = "";
                            if (groupHeader47.getInitgPty() != null) {
                                if (groupHeader47.getInitgPty().getId() != null) {
                                    if (groupHeader47.getInitgPty().getId().getOrgId() != null) {
                                        if (groupHeader47.getInitgPty().getId().getOrgId().getOthr() != null
                                                && !groupHeader47.getInitgPty().getId().getOrgId().getOthr().isEmpty()) {
                                            List<com.cbs.pojo.mms.esign.pain011.GenericOrganisationIdentification1> list = groupHeader47.getInitgPty().
                                                    getId().getOrgId().getOthr();
                                            for (com.cbs.pojo.mms.esign.pain011.GenericOrganisationIdentification1 obj : list) {
                                                amendgPtyIdOrgIdOthrId = obj.getId() == null ? "" : obj.getId().trim();
                                            }
                                        }
                                    }

                                }
                            }


                            String dbtrCtctDtlsNm = "", dbtrCtctDtlsPhneNb = "", dbtrCtctDtlsMobNb = "",
                                    dbtrCtctDtlsEmailAdr = "", dbtrCtctDtlsOthr = "";

                            if (mandateCancel4.getOrgnlMndt().getOrgnlMndt() != null) {
                                dbtrCtctDtlsNm = mandateCancel4.getOrgnlMndt().getOrgnlMndt().getDbtr().getCtctDtls().getNm() == null ? ""
                                        : mandateCancel4.getOrgnlMndt().getOrgnlMndt().getDbtr().getCtctDtls().getNm().trim(); //New addition here
                                dbtrCtctDtlsPhneNb = mandateCancel4.getOrgnlMndt().getOrgnlMndt().getDbtr().getCtctDtls().getPhneNb() == null ? ""
                                        : mandateCancel4.getOrgnlMndt().getOrgnlMndt().getDbtr().getCtctDtls().getPhneNb().trim();
                                dbtrCtctDtlsMobNb = mandateCancel4.getOrgnlMndt().getOrgnlMndt().getDbtr().getCtctDtls().getMobNb() == null ? ""
                                        : mandateCancel4.getOrgnlMndt().getOrgnlMndt().getDbtr().getCtctDtls().getMobNb().trim();
                                dbtrCtctDtlsEmailAdr = mandateCancel4.getOrgnlMndt().getOrgnlMndt().getDbtr().getCtctDtls().getEmailAdr() == null ? ""
                                        : mandateCancel4.getOrgnlMndt().getOrgnlMndt().getDbtr().getCtctDtls().getEmailAdr().trim();
                                dbtrCtctDtlsOthr = mandateCancel4.getOrgnlMndt().getOrgnlMndt().getDbtr().getCtctDtls().getOthr() == null ? ""
                                        : mandateCancel4.getOrgnlMndt().getOrgnlMndt().getDbtr().getCtctDtls().getOthr().trim();
                            }
                            String dbtrAgtFinInstnIdNm = "", orgnlMandateId = "";
                            BigDecimal collAmt = new BigDecimal(0), maxAmount = new BigDecimal(0);
                            if (mandateCancel4.getOrgnlMndt().getOrgnlMndt() != null) {
                                dbtrAgtFinInstnIdNm = mandateCancel4.getOrgnlMndt().getOrgnlMndt().getDbtrAgt().getFinInstnId().getNm() == null ? ""
                                        : mandateCancel4.getOrgnlMndt().getOrgnlMndt().getDbtrAgt().getFinInstnId().getNm().trim();

                                orgnlMandateId = mandateCancel4.getOrgnlMndt().getOrgnlMndt().getMndtId().trim() == null ? ""
                                        : mandateCancel4.getOrgnlMndt().getOrgnlMndt().getMndtId().trim();


                                collAmt = mandateCancel4.getOrgnlMndt().getOrgnlMndt().getColltnAmt() == null ? new BigDecimal(0)
                                        : mandateCancel4.getOrgnlMndt().getOrgnlMndt().getColltnAmt().getValue();
                                maxAmount = mandateCancel4.getOrgnlMndt().getOrgnlMndt().getMaxAmt() == null ? new BigDecimal("0")
                                        : mandateCancel4.getOrgnlMndt().getOrgnlMndt().getMaxAmt().getValue();
                            }


                            int n = em.createNativeQuery("INSERT INTO mms_upload_xml_detail(MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,"
                                    + "InstgAgt_FinInstnId_ClrSysMmbId_MmbId,InstgAgt_FinInstnId_Nm,"
                                    + "InstdAgt_FinInstnId_ClrSysMmbId_MmbId,InstdAgt_FinInstnId_Nm,MndtId,MndtReqId,"
                                    + "Tp_SvcLvl_Prtry,Tp_LclInstrm_Prtry,Ocrncs_SeqTp,Ocrncs_Frqcy,Ocrncs_FrstColltnDt,"
                                    + "Ocrncs_FnlColltnDt,ColltnAmt,MaxAmt,Cdtr_Nm,CdtrAcct_Id_Othr_Id,"
                                    + "CdtrAgt_FinInstnId_ClrSysMmbId_MmbId,CdtrAgt_FinInstnId_Nm,Dbtr_Nm,Dbtr_Id_PrvtId_Othr_Id,"
                                    + "Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry,Dbtr_CtctDtls_Nm,Dbtr_CtctDtls_PhneNb,Dbtr_CtctDtls_MobNb,"
                                    + "Dbtr_CtctDtls_EmailAdr,Dbtr_CtctDtls_Othr,DbtrAcct_Id_Othr_Id,DbtrAcct_Tp_Prtry,"
                                    + "DbtrAgt_FinInstnId_ClrSysMmbId_MmbId,DbtrAgt_FinInstnId_Nm,Mandate_Type,"
                                    + "Mandate_Status,Accept,Reject_Code,File_No,Image_Name,Upload_By,Upload_Date,Upload_Time,Verify_By,"
                                    + "Verify_Time,Zip_File_Name,Mandate_Mode,UnderlygAmdmntDtls_OrgnlMndt_OrgnlMndtId,UndrlygCxlDtls_CxlRsn_Rsn_Prtry) "
                                    + "VALUES('" + groupHeader47.getMsgId().trim() + "',"
                                    + "'" + groupHeader47.getCreDtTm() + "',"
                                    + "'" + amendgPtyIdOrgIdOthrId.trim() + "',"
                                    + "'" + groupHeader47.getInstgAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                    + "'" + instgAgtFinInstnIdNm + "',"
                                    + "'" + groupHeader47.getInstdAgt().getFinInstnId().getClrSysMmbId().getMmbId().trim() + "',"
                                    + "'" + instdAgtFinInstnIdNm + "',"
                                    + "'" + mndtId.trim() + "',"
                                    + "'',"
                                    + "'',"
                                    + "'',"
                                    + "'" + ocrncsSeqTp + "',"
                                    + "'" + ocrncsFrqcyTp + "',"
                                    + "'" + ocrncsFrstColltnDt + "',"
                                    + "'" + ocrncsFnlColltnDt + "',"
                                    + "" + collAmt + ","
                                    + "" + maxAmount + ","
                                    + "'',"
                                    + "'',"
                                    + "'',"
                                    + "'" + cdtrAgtFinInstnIdNm + "',"
                                    + "'',"
                                    + "'" + dbtrIdPrvtldOthrId + "',"
                                    + "'" + dbtrIdPrvtIdOthrIdSchmeNmPrtry + "',"
                                    + "'" + dbtrCtctDtlsNm + "',"
                                    + "'" + dbtrCtctDtlsPhneNb + "',"
                                    + "'" + dbtrCtctDtlsMobNb + "',"
                                    + "'" + dbtrCtctDtlsEmailAdr + "',"
                                    + "'" + dbtrCtctDtlsOthr + "',"
                                    + "'',"
                                    + "'',"
                                    + "'',"
                                    + "'" + dbtrAgtFinInstnIdNm + "',"
                                    + "'" + fileProcessType.toUpperCase().trim() + "',"
                                    + "'',"
                                    + "'',"
                                    + "'',"
                                    + "" + fileNo + ","
                                    + "'" + fileName.substring(0, fileName.lastIndexOf("-")).trim() + "',"
                                    + "'" + userName + "',"
                                    + "'" + currentDt + "',"
                                    + "now(),"
                                    + "'',"
                                    + "null,"
                                    + "'" + uploadedFileName + "','" + mandateMode + "','" + orgnlMandateId + "','" + prtry + "')").executeUpdate();

                            if (n <= 0) {
                                throw new ApplicationException("Problem In Data Uploading..");
                            }
                            if (processingMode.equalsIgnoreCase("H2H")) {
                                int s = em.createNativeQuery("update mms_upload_xml_detail set FILE_GEN_FLAG = 'N' where MndtId ='" + mandateCancel4.getOrgnlMndt().getOrgnlMndt().getMndtId().trim() + "'").executeUpdate();
                                if (s <= 0) {
                                    throw new ApplicationException("Problem In File_gen_flag updating.");
                                }
                            }
                        }
                        int n = em.createNativeQuery("update mms_upload_xml_detail set mandate_status='V',accept='A',"
                                + "reject_code='',verify_by='" + userName + "',verify_time=now() "
                                + "where mandate_type='" + fileProcessType + "' and upload_date='" + currentDt + "' and "
                                + "file_no=" + fileNo + " and mndtid='" + mndtId + "'").executeUpdate();
                        if (n <= 0) {
                            throw new ApplicationException("Problem In Mandate Updation.");
                        }

                        List list = em.createNativeQuery("select mndtid from mms_detail where mndtid='" + mndtId + "'and Mandate_Mode = '" + mandateMode + "'").getResultList();
                        if (!list.isEmpty()) {
                            int t = em.createNativeQuery("insert into mms_detail_his(MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,"
                                    + "InstgAgt_FinInstnId_ClrSysMmbId_MmbId,InstgAgt_FinInstnId_Nm,InstdAgt_FinInstnId_ClrSysMmbId_MmbId,"
                                    + "InstdAgt_FinInstnId_Nm,MndtId,MndtReqId,Tp_SvcLvl_Prtry,Tp_LclInstrm_Prtry,Ocrncs_SeqTp,"
                                    + "Ocrncs_Frqcy,Ocrncs_FrstColltnDt,Ocrncs_FnlColltnDt,ColltnAmt,MaxAmt,Cdtr_Nm,CdtrAcct_Id_Othr_Id,"
                                    + "CdtrAgt_FinInstnId_ClrSysMmbId_MmbId,CdtrAgt_FinInstnId_Nm,Dbtr_Nm,Dbtr_Id_PrvtId_Othr_Id,"
                                    + "Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry,Dbtr_CtctDtls_Nm,Dbtr_CtctDtls_PhneNb,Dbtr_CtctDtls_MobNb,"
                                    + "Dbtr_CtctDtls_EmailAdr,Dbtr_CtctDtls_Othr,DbtrAcct_Id_Othr_Id,DbtrAcct_Tp_Prtry,"
                                    + "DbtrAgt_FinInstnId_ClrSysMmbId_MmbId,DbtrAgt_FinInstnId_Nm,Mandate_Type,Update_By,Update_Date,"
                                    + "Update_Time,Mandate_Status,Stop_Payment_Date,Stop_Payment_By,Mandate_Mode,Cbs_Acno) select MsgId,CreDtTm,"
                                    + "InitgPty_Id_OrgId_Othr_Id,InstgAgt_FinInstnId_ClrSysMmbId_MmbId,InstgAgt_FinInstnId_Nm,"
                                    + "InstdAgt_FinInstnId_ClrSysMmbId_MmbId,InstdAgt_FinInstnId_Nm,MndtId,MndtReqId,Tp_SvcLvl_Prtry,"
                                    + "Tp_LclInstrm_Prtry,Ocrncs_SeqTp,Ocrncs_Frqcy,Ocrncs_FrstColltnDt,Ocrncs_FnlColltnDt,ColltnAmt,"
                                    + "MaxAmt,Cdtr_Nm,CdtrAcct_Id_Othr_Id,CdtrAgt_FinInstnId_ClrSysMmbId_MmbId,CdtrAgt_FinInstnId_Nm,"
                                    + "Dbtr_Nm,Dbtr_Id_PrvtId_Othr_Id,Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry,Dbtr_CtctDtls_Nm,Dbtr_CtctDtls_PhneNb,"
                                    + "Dbtr_CtctDtls_MobNb,Dbtr_CtctDtls_EmailAdr,Dbtr_CtctDtls_Othr,DbtrAcct_Id_Othr_Id,"
                                    + "DbtrAcct_Tp_Prtry,DbtrAgt_FinInstnId_ClrSysMmbId_MmbId,DbtrAgt_FinInstnId_Nm,Mandate_Type,"
                                    + "Update_By,Update_Date,Update_Time,Mandate_Status,Stop_Payment_Date,Stop_Payment_By,Mandate_Mode,"
                                    + "Cbs_Acno from mms_detail where MndtId = '" + mndtId + "'").executeUpdate();
                            if (t <= 0) {
                                throw new ApplicationException("Problem In Mandate History Creation.");
                            }

                            int u = em.createNativeQuery("update mms_detail set Mandate_Status='C',Update_By='" + userName + "',Update_Date='" + currentDt + "',"
                                    + "Update_Time=now() where MndtId='" + mndtId + "'").executeUpdate();
                            if (u <= 0) {
                                throw new ApplicationException("Problem In Mandate History Creation.");
                            }
                        }


                    }
                }
            }
            if (processingMode.equalsIgnoreCase("H2H")) {

                int zn = em.createNativeQuery("insert into cbs_npci_h2h_file_detail(file_date,file_name,file_type) "
                        + "values('" + ymd.format(new Date()) + "','" + uploadedFileName + "','IW')").executeUpdate();
                if (zn <= 0) {
                    throw new ApplicationException("Problem in cbs_npci_h2h_file_detail insertion.");
                }
            }
            ut.commit();
            //Sms Sending
            try {
                List list = em.createNativeQuery("select mndtid,cdtr_nm,colltnamt,maxamt,ocrncs_frqcy,"
                        + "dbtracct_id_othr_id from mms_upload_xml_detail where mandate_status='' and "
                        + "upload_date='" + ymd.format(new Date()) + "'").getResultList();
                if (!list.isEmpty()) {
                    List<TransferSmsRequestTo> smsList = new ArrayList<>();

                    List<MbSmsSenderBankDetailTO> bankTo = smsFacade.getBankAndSenderDetail();
                    String templateBankName = bankTo.get(0).getTemplateBankName().trim();

                    for (int i = 0; i < list.size(); i++) {
                        Vector ele = (Vector) list.get(i);
                        String umrn = ele.get(0).toString().trim();
                        String corporateName = ele.get(1).toString().trim();
                        Double collAmt = Double.parseDouble(ele.get(2).toString().trim());
                        Double maxAmount = Double.parseDouble(ele.get(3).toString().trim());
                        String frequency = ele.get(4).toString().trim();
                        String drAcno = ele.get(5).toString().trim();

                        Double actualAmt = collAmt != 0 ? collAmt : maxAmount;

                        //Extracting Mobile No
                        String mobileNo = "";
                        List smsData = em.createNativeQuery("select mobile_no from mb_subscriber_tab where "
                                + "acno='" + drAcno + "' and status=1 and auth='Y' and auth_status='V'").getResultList();
                        if (!smsData.isEmpty()) {
                            Vector smsEle = (Vector) smsData.get(0);
                            mobileNo = smsEle.get(0).toString().trim();
                        } else {
                            smsData = em.createNativeQuery("select ifnull(mobilenumber,'') as mobile from "
                                    + "cbs_customer_master_detail cu,customerid id where cu.customerid=id.custid "
                                    + "and id.acno='" + drAcno + "'").getResultList();
                            Vector smsEle = (Vector) smsData.get(0);
                            mobileNo = "+91" + smsEle.get(0).toString().trim();
                        }
                        mobileNo = mobileNo.trim().length() != 13 ? "" : mobileNo.trim();
                        if (mobileNo.length() == 13) {
                            TransferSmsRequestTo trfSmsTo = new TransferSmsRequestTo();
                            trfSmsTo.setMsgType("PAT");
                            trfSmsTo.setTemplate(SmsType.MANDATE_RECEIPT);
                            trfSmsTo.setAcno(drAcno);
                            trfSmsTo.setBankName(templateBankName);
                            trfSmsTo.setPin(umrn);
                            trfSmsTo.setPullCode(corporateName);
                            trfSmsTo.setFirstCheque(actualAmt.toString());
                            trfSmsTo.setServices(frequency);
                            trfSmsTo.setUserName(userName);
                            trfSmsTo.setPromoMobile(mobileNo);

                            smsList.add(trfSmsTo);
                        }
                    }
                    //Now sending sms
                    for (TransferSmsRequestTo obj : smsList) {
                        smsFacade.sendSms(obj);
                    }
                }
            } catch (Exception ex) {
                System.out.println("Problem In Sending the SMS In MMS Upload" + ex.getMessage());
            }
        } catch (Exception ex) {
            try {
                ex.printStackTrace();
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "true";
    }

//    public List<NpciOacDto> getRegisteredAadharAtCbs(List<String> list) throws ApplicationException {
//        List<NpciOacDto> dataList = new ArrayList<NpciOacDto>();
//        try {
//            if (list.isEmpty()) {
//                throw new ApplicationException("There is no inactive aadhar no.");
//            }
//            String aadharNoStr = "";
//            for (String str : list) {
//                if (aadharNoStr.equals("")) {
//                    aadharNoStr = "\'" + str + "\'";
//                } else {
//                    aadharNoStr = aadharNoStr + ",\'" + str + "\'";
//                }
//            }
//            List tempList = em.createNativeQuery("select cust_id,aadhar_no,custid_brncode from cbs_aadhar_registration "
//                    + "where reg_type='AD' and status='R'and aadhar_no in(" + aadharNoStr + ")").getResultList();
//            if (tempList.isEmpty()) {
//                throw new ApplicationException("There is no inactive aadhar.");
//            }
//            Map<String, String> map = getAllBranch();
//            for (int i = 0; i < tempList.size(); i++) {
//                NpciOacDto obj = new NpciOacDto();
//                Vector ele = (Vector) tempList.get(i);
//
//                obj.setCustId(ele.get(0).toString());
//                obj.setAadharNo(ele.get(1).toString());
//                obj.setBranch(map.get(ele.get(2).toString()));
//                dataList.add(obj);
//            }
//        } catch (Exception ex) {
//            throw new ApplicationException(ex.getMessage());
//        }
//        return dataList;
//    }
    public List<List<NpciOacDto>> getRegisteredAadharAtCbs(List<String> activeList, List<String> inActiveList) throws ApplicationException {
        List<List<NpciOacDto>> dataList = new ArrayList<List<NpciOacDto>>();
        List<NpciOacDto> returnInActiveList = new ArrayList<NpciOacDto>();
        List<NpciOacDto> returnActiveList = new ArrayList<NpciOacDto>();
        try {
            String inActiveAadharNoStr = "", activeAadharNoStr = "";
            Map<String, String> map = getAllBranch();
            for (String str : inActiveList) {
                if (inActiveAadharNoStr.equals("")) {
                    inActiveAadharNoStr = "\'" + str + "\'";
                } else {
                    inActiveAadharNoStr = inActiveAadharNoStr + ",\'" + str + "\'";
                }
            }
            if (!inActiveAadharNoStr.equals("")) {
                List tempList = em.createNativeQuery("select cust_id,aadhar_no,custid_brncode from cbs_aadhar_registration "
                        + "where reg_type='AD' and status='R'and aadhar_no in(" + inActiveAadharNoStr + ")").getResultList();

                for (int i = 0; i < tempList.size(); i++) {
                    NpciOacDto obj = new NpciOacDto();
                    Vector ele = (Vector) tempList.get(i);

                    obj.setCustId(ele.get(0).toString());
                    obj.setAadharNo(ele.get(1).toString());
                    obj.setBranch(map.get(ele.get(2).toString()));
                    returnInActiveList.add(obj);
                }
            }
            //For Active Return List
            for (String str : activeList) {
                if (activeAadharNoStr.equals("")) {
                    activeAadharNoStr = "\'" + str + "\'";
                } else {
                    activeAadharNoStr = activeAadharNoStr + ",\'" + str + "\'";
                }
            }
            if (!activeAadharNoStr.equals("")) {
                List tempList = em.createNativeQuery("select cust_id,aadhar_no,custid_brncode from cbs_aadhar_registration "
                        + "where reg_type='AD' and status<>'R' and aadhar_no in(" + activeAadharNoStr + ")").getResultList();
                for (int i = 0; i < tempList.size(); i++) {
                    NpciOacDto obj = new NpciOacDto();
                    Vector ele = (Vector) tempList.get(i);

                    obj.setCustId(ele.get(0).toString());
                    obj.setAadharNo(ele.get(1).toString());
                    obj.setBranch(map.get(ele.get(2).toString()));
                    returnActiveList.add(obj);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        dataList.add(returnInActiveList);
        dataList.add(returnActiveList);
        return dataList;
    }

    public Map<String, String> getAllBranch() throws ApplicationException {
        Map<String, String> map = new HashMap<String, String>();
        try {
            List list = em.createNativeQuery("select brncode,alphacode from branchmaster "
                    + "order by brncode").getResultList();
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                String brncode = ele.get(0).toString();
                brncode = brncode.trim().length() < 2 ? "0" + brncode : brncode;
                map.put(brncode, ele.get(1).toString());
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return map;
    }

    public String markAadharInactive(List<NpciOacDto> list, String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            for (NpciOacDto dto : list) {
                int n = em.createNativeQuery("insert into cbs_aadhar_registration_his(cust_id,aadhar_no,status,"
                        + "rrn,mandate_flag,mandate_date,od_flag,od_date,mapping_status,custid_brncode,enter_by,"
                        + "dt,tran_time,update_by,update_dt,lpg_id,responder_code,dest_bank_ifsc,dest_bank_acno,"
                        + "reg_type,reject_reason,res_update_by,res_update_dt,res_file_name,aadhaar_bank_iin) select cust_id,aadhar_no,"
                        + "status,rrn,mandate_flag,mandate_date,od_flag,od_date,mapping_status,custid_brncode,"
                        + "enter_by,dt,tran_time,'" + userName + "',now(),lpg_id,responder_code,dest_bank_ifsc,"
                        + "dest_bank_acno,reg_type,reject_reason,res_update_by,res_update_dt,res_file_name,aadhaar_bank_iin from "
                        + "cbs_aadhar_registration where cust_id='" + dto.getCustId() + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Aadhar History Creation In aadharDeactivation() Method.");
                }

                n = em.createNativeQuery("update cbs_aadhar_registration set status='D',mapping_status='I',"
                        + "enter_by='" + userName + "',dt='" + ymd.format(new Date()) + "',"
                        + "tran_time=now() where cust_id='" + dto.getCustId() + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Moved Out Updation.");
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
        return "true";
    }

    public List getMandateDetail(String umrn) throws ApplicationException {
        try {
            return em.createNativeQuery("select Mandate_Status from mms_detail where mndtid ='" + umrn + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public List<NpciOacDto> getDataForEcsCrOnInComingDate(String fileComingDt, String iwType) throws ApplicationException {
        List<NpciOacDto> dataList = new ArrayList<NpciOacDto>();
//        fileComingDt = "20160604";
        try {
            List list = new ArrayList();
            list = em.createNativeQuery("select dest_bank_iin,ifnull(dest_actype,''),ifnull(bene_name,''),"
                    + "user_name_narration,user_credit_reference,amount,ifnull(MAPPED_CBS_ACNO,'') as dest_bank_acno,"
                    + "status,reason,cbs_acno,cbs_acname,auth_by,ACH_ITEM_SEQ_NO from cbs_npci_inward where iw_type='" + iwType + "' and "
                    + "settlement_date='" + ymd.format(dmy.parse(fileComingDt)) + "'  and length(DEST_BANK_ACNO)=15 and not MAPPED_CBS_ACNO='' "
                    + " union "
                    + "select dest_bank_iin,ifnull(dest_actype,''),ifnull(bene_name,''),user_name_narration,"
                    + "user_credit_reference,amount,ifnull(dest_bank_acno,''),status,reason,cbs_acno,cbs_acname,"
                    + "auth_by,ACH_ITEM_SEQ_NO from cbs_npci_inward where iw_type='" + iwType + "' and settlement_date='" + ymd.format(dmy.parse(fileComingDt)) + "' "
                    + " and length(DEST_BANK_ACNO)=12").getResultList();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector ele = (Vector) list.get(i);
                    NpciOacDto dto = new NpciOacDto();

                    dto.setSno(String.valueOf(i + 1));
                    dto.setMicr(ele.get(0).toString().trim());
                    dto.setAcType(ele.get(1).toString().trim());
                    dto.setOldAcName(ele.get(2).toString().trim());
                    dto.setUserName(ele.get(3).toString().trim());
                    dto.setTranRef(ele.get(4).toString().trim());
                    BigDecimal amt = new BigDecimal(ele.get(5).toString().trim()).divide(new BigDecimal("100"));
                    dto.setAmount(formatter.format(amt.doubleValue()));
                    dto.setOldAcno(ele.get(6).toString().trim());
                    String status = ele.get(7).toString().trim();
                    if (status.equalsIgnoreCase("U")) {
                        dto.setStatus("UnSuccess");
                    } else if (status.equalsIgnoreCase("S")) {
                        dto.setStatus("Success");
                    } else {
                        dto.setStatus("");
                    }
                    dto.setReason(ele.get(8).toString().trim());
                    dto.setCbsAcno(ele.get(9).toString().trim());
                    dto.setCbsName(ele.get(10).toString().trim());
                    dto.setAuthBy(ele.get(11).toString().trim());

                    dto.setAchItemSeqNo(ele.get(12).toString().trim());

                    dataList.add(dto);
                }
            } else {
                System.out.println("There is no data to verify.");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<String[]> getUnAuthNPCIFile() throws ApplicationException {
        List<String[]> listObj = new ArrayList<String[]>();
        try {
            List list = em.createNativeQuery("select iw_type,DATE_FORMAT(settlement_date,'%d/%m/%Y') from cbs_npci_inward where status='' "
                    + " union "
                    + "select iw_type,DATE_FORMAT(file_coming_dt,'%d/%m/%Y') from cbs_npci_oac_detail where  ac_val_flag=''").getResultList();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    String[] str = new String[2];
                    Vector ele = (Vector) list.get(i);
                    str[0] = ele.get(0).toString();
                    str[1] = ele.get(1).toString();
                    listObj.add(str);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return listObj;


    }

    public String[] getAcHolderDetail(String acNo) throws ApplicationException {
        String[] str = new String[5];
        str[0] = "";
        str[1] = "";
        str[2] = "";
        str[3] = "";
        str[4] = "";
        try {
            List list = em.createNativeQuery("select custname, bankname ,IFSC_CODE,BranchName,b.AlphaCode ,acno,concat(lpad(micr,3,'0'),lpad(micrcode,3,'0'),lpad(branchcode,3,'0')) as micr "
                    + " from accountmaster a,branchmaster b ,bnkadd ba where a.acno='" + acNo + "'"
                    + " and lpad(b.BrnCode,2,'0')='" + acNo.substring(0, 2) + "' and b.alphacode=ba.alphacode").getResultList();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector ele = (Vector) list.get(i);
                    str[0] = ele.get(0).toString().trim();
                    str[1] = ele.get(1).toString().trim();
                    str[2] = ele.get(2).toString().trim();
                    str[3] = ele.get(3).toString().trim();
                    str[4] = ele.get(6).toString().trim();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new ApplicationException(ex.getMessage());
        }
        return str;
    }

    @Override
    public String generateMandateIputFile(List<CbsMandateDetailPojo> mandtDtlList, String txnType, String fileGenDt,
            String currentDate, String enterBy, String brnCode, String settleDt, String achNewOrLegacyFlag) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();

        String npciUserName = "", aadharLocation = "", bankCode = "", bankIIN = "", fileNo = "",
                totalItems = "", settlementDate = "", userReference = "";
        try {
            ut.begin();
            if (mandtDtlList.isEmpty()) {
                throw new ApplicationException("There is no data to generate the files.");
            }
            List<CbsMandateDetailPojo> checkdMandateList = new ArrayList<CbsMandateDetailPojo>();
            List<CbsMandateDetailPojo> uncheckMandateList = new ArrayList<CbsMandateDetailPojo>();
            for (CbsMandateDetailPojo obj : mandtDtlList) {
                if (obj.isCheckBox()) {
                    checkdMandateList.add(obj);
                } else {
                    uncheckMandateList.add(obj);
                    continue;
                }
            }
            if (checkdMandateList.isEmpty()) {
                throw new ApplicationException("There is no selected data to generate the files.");
            }
            List list = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + enterBy + "'").getResultList();
            Vector ele = (Vector) list.get(0);
            if (list.isEmpty() || ele.get(0) == null || ele.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to generate the files.");
            }
            npciUserName = ele.get(0).toString().trim();
            //Required Values Extraction.
            list = em.createNativeQuery("select ifnull(a.code,'') as CR_HI,ifnull(b.code,'') as CR_DI,"
                    + "ifnull(c.code,'') as DR_HI,ifnull(d.code,'') as DR_DI,ifnull(e.code,'') as U_NO,"
                    + "ifnull(f.code,'') as U_NAME,ifnull(g.code,'') as MICR from (select ifnull((select code "
                    + "from cbs_parameterinfo  where name='ACH-CR-INP-HI'),'') as code) a,(select "
                    + "ifnull((select code from cbs_parameterinfo where name='ACH-CR-INP-DI'),'') as code) b,"
                    + "(select ifnull((select code from cbs_parameterinfo where name='ACH-DR-INP-HI'),'') "
                    + "as code) c,(select ifnull((select code from cbs_parameterinfo where "
                    + "name='ACH-DR-INP-DI'),'') as code) d,(select ifnull((select code from cbs_parameterinfo "
                    + "where name='ACH-INP-UNO'),'') as code) e,(select ifnull((select code from cbs_parameterinfo "
                    + "where name='ACH-INP-UNM'),'') as code) f,(select ifnull((select code from cbs_parameterinfo "
                    + "where name='ACH-INP-MICR'),'') as code) g").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please define required values.");
            }
            ele = (Vector) list.get(0);
            String creditHi = ele.get(0).toString().trim();
            String creditDi = ele.get(1).toString().trim();
            String debitHi = ele.get(2).toString().trim();
            String debitDi = ele.get(3).toString().trim();
            String userNo = ele.get(4).toString().trim();
            String userName = ele.get(5).toString().trim().toUpperCase();
            String bankMicr = ele.get(6).toString().trim();

            if (creditHi.equals("") || creditDi.equals("") || debitHi.equals("") || debitDi.equals("")
                    || userNo.equals("") || userName.equals("") || bankMicr.equals("")
                    || creditHi.length() != 2 || creditDi.length() != 2 || debitHi.length() != 2 || debitDi.length() != 2
                    || userName.length() > 20 || bankMicr.length() > 11 || userNo.length() > 18) {
                throw new ApplicationException("Please define required values for file generation.");
            }

            list = em.createNativeQuery("select aadhar_location,npci_bank_code,iin from mb_sms_sender_bank_detail").getResultList();
            ele = (Vector) list.get(0);
            if (ele.get(0) == null || ele.get(1) == null || ele.get(2) == null || ele.get(0).toString().trim().equals("")
                    || ele.get(1).toString().trim().equals("") || ele.get(1).toString().trim().length() != 4
                    || ele.get(2).toString().trim().equals("") || ele.get(2).toString().trim().length() > 9) {
                throw new ApplicationException("Please define Aadhar Location and Bank Code.");
            }
            aadharLocation = ele.get(0).toString().trim();
            bankCode = ele.get(1).toString().trim();
            bankIIN = ele.get(2).toString().trim();

            String genFileName = "", fileGenType = "", productType = "";
            if (txnType.equalsIgnoreCase("DEBIT")) {
                fileGenType = "AINPSD";
                list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
                        + "where file_gen_date='" + ymd.format(dmy.parse(currentDate)) + "' and  "
                        + "file_gen_type='" + fileGenType + "'").getResultList();

                ele = (Vector) list.get(0);
                fileNo = "1";
                if (ele.get(0) != null) {
                    fileNo = ele.get(0).toString().trim();
                }

                if (achNewOrLegacyFlag.trim().equalsIgnoreCase("New")) {
                    genFileName = "ACH-DR-" + bankCode.toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
                            + ymdOne.format(dmy.parse(currentDate)) + "-" + ParseFileUtil.addTrailingZeros(fileNo, 6) + "-INP.txt";
                    productType = ParseFileUtil.addSuffixSpaces("10", 3);
                } else {
                    genFileName = "ACH-DR-" + bankCode.toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
                            + ymdOne.format(dmy.parse(currentDate)) + "-TMOLEG" + ParseFileUtil.addTrailingZeros(fileNo, 6) + "-INP.txt";
                    productType = ParseFileUtil.addSuffixSpaces("LEG", 3);
                }
            } else if (txnType.equalsIgnoreCase("CREDIT")) {
                productType = ParseFileUtil.addSuffixSpaces("10", 3);
                fileGenType = "AINPSC";
                list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
                        + "where file_gen_date='" + ymd.format(dmy.parse(currentDate)) + "' and "
                        + "file_gen_type='" + fileGenType + "'").getResultList();

                ele = (Vector) list.get(0);
                fileNo = "1";
                if (ele.get(0) != null) {
                    fileNo = ele.get(0).toString().trim();
                }

                genFileName = "ACH-CR-" + bankCode.toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
                        + ymdOne.format(dmy.parse(currentDate)) + "-" + ParseFileUtil.addTrailingZeros(fileNo, 6) + "-INP.txt";
            }

            totalItems = String.valueOf(checkdMandateList.size()).trim();
            BigDecimal totalSubAmt = new BigDecimal(0);
            for (CbsMandateDetailPojo mndEle : checkdMandateList) {
                BigDecimal individualAmt = new BigDecimal(mndEle.getAmount());
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

            settlementDate = new SimpleDateFormat("ddMMyyyy").format(dmy.parse(settleDt));
            userReference = fileGenType + settlementDate + ParseFileUtil.addTrailingZeros(fileNo, 4);

            int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                    + "file_gen_time,file_gen_brncode,file_gen_type,seq_no) values(" + Integer.parseInt(fileNo) + ",'"
                    + ymd.format(dmy.parse(currentDate)) + "','" + genFileName + "','" + enterBy + "',now(),'"
                    + brnCode + "','" + fileGenType + "'," + fileNo + ")").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In Cbs ACH Debit Input Files Insertion in NPCI Mapper.");
            }

            //Header Preparation.
            FileWriter fw = new FileWriter(aadharLocation + genFileName);

            String header = debitHi + ParseFileUtil.addSuffixSpaces("", 7) + ParseFileUtil.addSuffixSpaces(bankCode, 40)
                    + ParseFileUtil.addSuffixSpaces("", 14) + ParseFileUtil.addSuffixSpaces("", 9)
                    + ParseFileUtil.addSuffixSpaces("", 9) + ParseFileUtil.addSuffixSpaces("", 15)
                    + ParseFileUtil.addSuffixSpaces("", 3) + ParseFileUtil.addSuffixSpaces("", 13)
                    + amtInPaisa + settlementDate
                    + ParseFileUtil.addSuffixSpaces("", 10) + ParseFileUtil.addSuffixSpaces("", 10)
                    + ParseFileUtil.addSuffixSpaces("", 3) + ParseFileUtil.addSuffixSpaces(userNo, 18)
                    + ParseFileUtil.addSuffixSpaces(userReference, 18) + ParseFileUtil.addSuffixSpaces(bankMicr, 11)
                    + ParseFileUtil.addSuffixSpaces("", 35) + ParseFileUtil.addTrailingZeros(totalItems, 9)
                    + ParseFileUtil.addSuffixSpaces("", 2) + ParseFileUtil.addSuffixSpaces("", 57) + "\n";
            fw.write(header);
            // Unchecked mandate List data
            if (!uncheckMandateList.isEmpty()) {
                for (CbsMandateDetailPojo mndUnchecked : uncheckMandateList) {
                    String umrnUnchecked = mndUnchecked.getcBSUmrn();
                    int entrySeqNo = mndUnchecked.getEntrySeqNo();
                    n = em.createNativeQuery("INSERT INTO cbs_sponsor_txn_detail (CBS_Umrn,TxnFileType, Txn_Date,Entry_Date, Success_Flag, Return_Code, "
                            + "Response_File_Name, Update_By, Update_Date,Txn_File_Name,Entry_SeqNo,Cancel_By) VALUES "
                            + "('" + umrnUnchecked + "', '" + fileGenType + "', '" + ymdSql.format(ymd.parse(mndUnchecked.getTxnGenDate())) + "','" + yyyymmdd.format(new Date()) + "', '', '', "
                            + "'', '" + enterBy + "', '" + ymdSql.format(new Date()) + "',''," + entrySeqNo + ",'" + enterBy + "');").executeUpdate();
                    if (n <= 0) {
                        throw new ApplicationException("Problem in ACH Debit Input Files Insertion.");
                    }
                }
            }

            //Data Preparation.

            for (CbsMandateDetailPojo mndEle : checkdMandateList) {
                String beneAccHolderName = "", beneBankAccNumber = "", destBankIIN = "",
                        tranRef = "", chiUmrn = "", umrn = "", creditorUtilityCode = "";
                int entrySeqNo = mndEle.getEntrySeqNo();
                if (mndEle.getProprietary().equalsIgnoreCase("CREDIT")) {
                    beneAccHolderName = mndEle.getCreditorName().trim();
                    beneBankAccNumber = mndEle.getCreditorAcno().trim();
                    destBankIIN = mndEle.getCreditorIFSC().trim();
                } else if (mndEle.getProprietary().equalsIgnoreCase("DEBIT")) {
                    beneAccHolderName = mndEle.getDebtorName().trim();
                    beneBankAccNumber = mndEle.getDebtorAcno().trim();
                    destBankIIN = mndEle.getDebtorIFSC().trim();
                    creditorUtilityCode = mndEle.getCreditorUtilityCode() == null ? "" : mndEle.getCreditorUtilityCode().trim();
                }

                if (beneBankAccNumber == null || beneBankAccNumber.equals("") || beneBankAccNumber.length() > 35) {
                    throw new ApplicationException("Account length should be upto 35 digit:" + beneBankAccNumber);
                }
                if (beneAccHolderName == null || beneAccHolderName.equals("")) {
                    throw new ApplicationException("Account name should be upto 40 digit:" + beneAccHolderName);
                }
                beneAccHolderName = beneAccHolderName.replaceAll("[\\W_]", " ");
                beneAccHolderName = beneAccHolderName.length() > 40 ? beneAccHolderName.substring(0, 40) : beneAccHolderName;
                if (destBankIIN == null || destBankIIN.equals("") || destBankIIN.length() > 11) {
                    throw new ApplicationException("Destination bank IIN should be upto 11 digit:" + destBankIIN);
                }
                if (creditorUtilityCode.equals("") || creditorUtilityCode.length() > 18) {
                    throw new ApplicationException("There should be creditor utility code for:" + mndEle.getcBSUmrn().trim());
                }


                String entryDate = new SimpleDateFormat("ddMMyyyy").format(dmy.parse(currentDate));
//                amount = String.valueOf(new BigDecimal(mndEle.getAmount()).multiply(new BigDecimal(100)).intValue());

                String indAmt = new BigDecimal(mndEle.getAmount()).multiply(new BigDecimal(100)).toString();
                dotIndex = indAmt.toString().indexOf(".");
                if (dotIndex == -1) {
                    amtInPaisa = ParseFileUtil.addTrailingZeros(indAmt.toString().trim(), 13);
                } else {
                    amtInPaisa = ParseFileUtil.addTrailingZeros(indAmt.toString().substring(0, dotIndex).trim(), 13);
                }

                tranRef = ParseFileUtil.addTrailingZeros(mndEle.getcBSUmrn(), 17) + ParseFileUtil.addTrailingZeros(String.valueOf(entrySeqNo), 5) + ParseFileUtil.addTrailingZeros(entryDate, 8);
                umrn = mndEle.getcBSUmrn();
                chiUmrn = mndEle.getcHIUmrn();

                String individualData = debitDi + ParseFileUtil.addSuffixSpaces("", 9)
                        + ParseFileUtil.addSuffixSpaces("", 2) + ParseFileUtil.addSuffixSpaces("", 3)
                        + ParseFileUtil.addSuffixSpaces("", 15) + ParseFileUtil.addSuffixSpaces(beneAccHolderName, 40)
                        + ParseFileUtil.addSuffixSpaces("", 9) + ParseFileUtil.addSuffixSpaces("", 7)
                        + ParseFileUtil.addSuffixSpaces("", 20) + ParseFileUtil.addSuffixSpaces("", 13)
                        + amtInPaisa + ParseFileUtil.addSuffixSpaces("", 10)
                        + ParseFileUtil.addSuffixSpaces("", 10) + ParseFileUtil.addSuffixSpaces("", 1)
                        + ParseFileUtil.addSuffixSpaces("", 2) + ParseFileUtil.addSuffixSpaces(destBankIIN, 11)
                        + ParseFileUtil.addSuffixSpaces(beneBankAccNumber, 35) + ParseFileUtil.addSuffixSpaces(bankMicr, 11)
                        + ParseFileUtil.addSuffixSpaces(mndEle.getCreditorUtilityCode().trim(), 18) + ParseFileUtil.addSuffixSpaces(tranRef, 30)
                        + productType + ParseFileUtil.addSuffixSpaces("", 15)
                        + ParseFileUtil.addSuffixSpaces(chiUmrn, 20) + ParseFileUtil.addSuffixSpaces("", 7) + "\n";
                fw.write(individualData);

                n = em.createNativeQuery("INSERT INTO cbs_sponsor_txn_detail (CBS_Umrn,TxnFileType, Txn_Date,Entry_Date, Success_Flag, Return_Code, "
                        + "Response_File_Name, Update_By, Update_Date,Txn_File_Name,Entry_SeqNo) VALUES "
                        + "('" + umrn + "', '" + fileGenType + "', '" + ymdSql.format(ymd.parse(mndEle.getTxnGenDate())) + "','" + yyyymmdd.format(new Date()) + "', '', '', "
                        + "'', '" + enterBy + "', '" + ymdSql.format(new Date()) + "','" + genFileName + "'," + entrySeqNo + ");").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in ACH Debit Input Files Insertion.");
                }
            }
            fw.close();
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "true";
    }

    public List<CbsMandateDetailPojo> getUmrDetailsOnmandateDate(String fileType, String fileTransType, String fileGenDt,
            String forgetFlag, int forgetDay, String seqType, String freqnType, String orgnBrCode,
            String enterBy, String achNewOrLegacy) throws ApplicationException {
        List<CbsMandateDetailPojo> umrnList = new ArrayList<CbsMandateDetailPojo>();
        try {
            String fileGenDateForListymd = ymd.format(dmy.parse(fileGenDt));
            List<String> dateList = new ArrayList<String>();
            if (forgetFlag.equalsIgnoreCase("Y")) {
                for (int i = 0; i < forgetDay; i++) {
                    dateList.add(CbsUtil.dateAdd(fileGenDateForListymd, i));
                }
            } else if (forgetFlag.equalsIgnoreCase("N")) {
                dateList.add(fileGenDateForListymd);
            }
            for (int i = 0; i < dateList.size(); i++) {
                System.out.println("Date Is-->" + dateList.get(i));
            }
            for (String fileGenDateymd : dateList) {
                String query = "";
                String conditionQuery = "";
                String flag = "", achLegacyOrNewTypeDataQuery = "";
                if (fileType.equalsIgnoreCase("ACH")) {
                    flag = "S";
                    achLegacyOrNewTypeDataQuery = achNewOrLegacy.trim().equalsIgnoreCase("New") ? " and substring(chi_umrn,5,1)<>'9' " : " and substring(chi_umrn,5,1)='9' ";
                } else if (fileType.equalsIgnoreCase("ECS")) {
                    flag = "N";
//                " and Trans_Type in ('CREATE','AMEND')"
                }
                if (seqType.equalsIgnoreCase("OOFF")) {
                    conditionQuery = "date_format(From_Date,'%d')='" + new SimpleDateFormat("dd").format(ymd.parse(fileGenDateymd)) + "'"
                            + " and Proprietary='" + fileTransType + "' and Sequence_Type='OOFF' ";
                } else if (seqType.equalsIgnoreCase("RCUR")) {
                    if (freqnType.equalsIgnoreCase("WTOASWP")) {
                        conditionQuery = "(date_format(From_Date,'%d')='" + new SimpleDateFormat("dd").format(ymd.parse(fileGenDateymd)) + "'  or Frequency='DAIL' "
                                + "or Frequency='BIMN' or Frequency='WEEK') "
                                + "  and Proprietary='" + fileTransType + "' and Frequency<>'ASWP' and Sequence_Type='RCUR' ";
                    } else if (freqnType.equalsIgnoreCase("DAIL")) {
                        conditionQuery = "(date_format(From_Date,'%d')='" + new SimpleDateFormat("dd").format(ymd.parse(fileGenDateymd)) + "'  or Frequency='DAIL')"
                                + "  and Proprietary='" + fileTransType + "' and Sequence_Type='RCUR' and Frequency='DAIL' ";
                    } else if (freqnType.equalsIgnoreCase("WEEK")) {
                        conditionQuery = "(date_format(From_Date,'%d')='" + new SimpleDateFormat("dd").format(ymd.parse(fileGenDateymd)) + "'  or Frequency='WEEK')"
                                + "  and Proprietary='" + fileTransType + "' and Sequence_Type='RCUR' and Frequency='WEEK' ";
                    } else if (freqnType.equalsIgnoreCase("BIMN")) {
                        conditionQuery = "(date_format(From_Date,'%d')='" + new SimpleDateFormat("dd").format(ymd.parse(fileGenDateymd)) + "'  or Frequency='BIMN')"
                                + "  and Proprietary='" + fileTransType + "' and Sequence_Type='RCUR' and Frequency='BIMN' ";
                    } else if (freqnType.equalsIgnoreCase("ASWP")) {
                        conditionQuery = "(date_format(From_Date,'%d')='" + new SimpleDateFormat("dd").format(ymd.parse(fileGenDateymd)) + "'  or Frequency='ASWP')"
                                + "  and Proprietary='" + fileTransType + "' and Sequence_Type='RCUR' and Frequency='ASWP' ";
                    } else {
                        conditionQuery = "date_format(From_Date,'%d')='" + new SimpleDateFormat("dd").format(ymd.parse(fileGenDateymd)) + "'"
                                + "  and Proprietary='" + fileTransType + "' and Sequence_Type='RCUR' and Frequency='" + freqnType + "' ";
                    }
                }
                query = "select Trans_Type,CBS_Umrn,Proprietary,Sequence_Type,Frequency,Amount,"
                        + " From_Date,To_Date,Creditor_Acno,Creditor_Name,Creditor_AcType,"
                        + " Creditor_IFSC,Debtor_Acno,Debtor_Name,Debtor_AcType,"
                        + " Debtor_IFSC,Period_Type,CHI_Umrn,ifnull(Creditor_Utility_Code,'') from cbs_mandate_detail where " + conditionQuery + achLegacyOrNewTypeDataQuery + " and Trans_Type in ('CREATE','AMEND') and Flag='" + flag + "' and TxnFileType='" + fileType + "' and"
                        + " ((period_type='B' and Date(To_Date)>=Date('" + fileGenDateymd + "')) or ((period_type='C' and To_Date='')))";
                List umrnDatalist = em.createNativeQuery(query).getResultList();
                if (!umrnDatalist.isEmpty()) {
                    int seqNo = 1;
                    String umrnForCount = "";
                    for (int i = 0; i < umrnDatalist.size(); i++) {
                        CbsMandateDetailPojo pojo = new CbsMandateDetailPojo();
                        pojo.setTransType(((Vector) umrnDatalist.get(i)).get(0).toString());
                        pojo.setcBSUmrn(((Vector) umrnDatalist.get(i)).get(1).toString());
                        pojo.setProprietary(((Vector) umrnDatalist.get(i)).get(2).toString());
                        pojo.setSequenceType(((Vector) umrnDatalist.get(i)).get(3).toString());
                        pojo.setFrequency(((Vector) umrnDatalist.get(i)).get(4).toString());
                        pojo.setAmount(((Vector) umrnDatalist.get(i)).get(5).toString());
                        pojo.setFromDate(((Vector) umrnDatalist.get(i)).get(6).toString());
                        pojo.setToDate(((Vector) umrnDatalist.get(i)).get(7).toString());
                        pojo.setCreditorAcno(((Vector) umrnDatalist.get(i)).get(8).toString());
                        pojo.setCreditorName(((Vector) umrnDatalist.get(i)).get(9).toString());
                        pojo.setCreditorAcType(((Vector) umrnDatalist.get(i)).get(10).toString());
                        pojo.setCreditorIFSC(((Vector) umrnDatalist.get(i)).get(11).toString());
                        pojo.setDebtorAcno(((Vector) umrnDatalist.get(i)).get(12).toString());
                        pojo.setDebtorName(((Vector) umrnDatalist.get(i)).get(13).toString());
                        pojo.setDebtorAcType(((Vector) umrnDatalist.get(i)).get(14).toString());
                        pojo.setDebtorIFSC(((Vector) umrnDatalist.get(i)).get(15).toString());
                        pojo.setPeriodType(((Vector) umrnDatalist.get(i)).get(16).toString());
                        pojo.setcHIUmrn(((Vector) umrnDatalist.get(i)).get(17).toString());
                        pojo.setCreditorUtilityCode(((Vector) umrnDatalist.get(i)).get(18).toString());
                        if ((seqType.equalsIgnoreCase("RCUR")) && (freqnType.equalsIgnoreCase("ASWP"))) {
                            pojo.setCheckBox(false);
                        } else {
                            pojo.setCheckBox(true);
                        }
                        if (pojo.getSequenceType().equalsIgnoreCase("OOFF")) {
                            if ((ymd.parse(pojo.getFromDate()).compareTo(ymd.parse(fileGenDateymd)) != 0)) {
                                continue;
                            }
                            List list = em.createNativeQuery("select CBS_Umrn from cbs_sponsor_txn_detail where CBS_Umrn='" + pojo.getcBSUmrn() + "'"
                                    + " and Txn_Date='" + pojo.getFromDate() + "' ").getResultList();
                            if (!list.isEmpty()) {
                                continue;
                            }
                        } else if (pojo.getSequenceType().equalsIgnoreCase("RCUR")) {
                            List list = em.createNativeQuery("select max(Txn_Date),CBS_Umrn from "
                                    + "cbs_sponsor_txn_detail where CBS_Umrn='" + pojo.getcBSUmrn() + "'").getResultList();
                            String umrnTxnDt = "";
                            /*
                             ASWP	AS AND WHEN PRESENTED
                             BIMN	BI-MONTHLY
                             DAIL	DAILY
                             MAIN	HALF YEARLY
                             MNTH	MONTHLY
                             QURT	QUARTERLY
                             WEEK	WEEKLY
                             YEAR	YEARLY
                             */

                            if (!(list.isEmpty()) && (!(((Vector) list.get(0)).get(0) == null))) {
                                umrnTxnDt = ((Vector) list.get(0)).get(0).toString();
                                Date umrnTxnDate = yyyymmdd.parse(umrnTxnDt);
                                umrnTxnDt = ymd.format(umrnTxnDate);
                                String currTxnDate = "";

                                if (pojo.getFrequency().equalsIgnoreCase("YEAR")) {
                                    currTxnDate = CbsUtil.yearAdd(umrnTxnDt, 1);
//                                    currUtilTxnDate = ymd.parse(currTxnDate);
                                    if ((!currTxnDate.equalsIgnoreCase(fileGenDateymd))) {
                                        /*   || ((pojo.getPeriodType().equalsIgnoreCase("B")) && (currUtilTxnDate.compareTo(utilEndDate) > 0))) {*/
                                        continue;
                                    }
                                }
                                if (pojo.getFrequency().equalsIgnoreCase("MAIN")) {
                                    currTxnDate = CbsUtil.monthAdd(umrnTxnDt, 6);
                                    if ((!currTxnDate.equalsIgnoreCase(fileGenDateymd))) {
                                        continue;
                                    }
                                }
                                if (pojo.getFrequency().equalsIgnoreCase("QURT")) {
                                    currTxnDate = CbsUtil.monthAdd(umrnTxnDt, 4);
                                    if ((!currTxnDate.equalsIgnoreCase(fileGenDateymd))) {
                                        continue;
                                    }
                                }
                                if (pojo.getFrequency().equalsIgnoreCase("MNTH")) {
                                    currTxnDate = CbsUtil.monthAdd(umrnTxnDt, 1);
                                    if ((!currTxnDate.equalsIgnoreCase(fileGenDateymd))) {
                                        continue;
                                    }
                                }
                                if (pojo.getFrequency().equalsIgnoreCase("WEEK")) {
                                    currTxnDate = CbsUtil.dateAdd(umrnTxnDt, 7);
                                    if ((!currTxnDate.equalsIgnoreCase(fileGenDateymd))) {
                                        continue;
                                    }
                                }
                                if (pojo.getFrequency().equalsIgnoreCase("DAIL")) {
                                    currTxnDate = CbsUtil.dateAdd(umrnTxnDt, 1);
                                    if ((!currTxnDate.equalsIgnoreCase(fileGenDateymd))) {
                                        continue;
                                    }
                                }
                                if (pojo.getFrequency().equalsIgnoreCase("BIMN")) {

                                    //****** To Calculate period frame of month
                                    String periodFromDateymd = new SimpleDateFormat("yyyyMM").format(ymd.parse(umrnTxnDt)) + new SimpleDateFormat("dd").format(ymd.parse(pojo.getFromDate()));
                                    //******
                                    String netMonthDate = CbsUtil.monthAdd(periodFromDateymd, 1);

                                    List listCnt = em.createNativeQuery("select COUNT(CBS_Umrn) from cbs_sponsor_txn_detail where"
                                            + " CBS_Umrn='" + pojo.getcBSUmrn() + "' and DATE(Txn_Date) "
                                            + " BETWEEN '" + periodFromDateymd + "' AND '" + netMonthDate + "'").getResultList();
                                    int countOfTrnInMonth = 0;
                                    if (!listCnt.isEmpty()) {
                                        countOfTrnInMonth = Integer.parseInt(((Vector) listCnt.get(0)).get(0).toString());
                                        if ((countOfTrnInMonth >= 2)) {
                                            continue;
                                        }
                                    }
                                }
                                if (pojo.getFrequency().equalsIgnoreCase("ASWP")) {
                                    List listcount = em.createNativeQuery("select COUNT(CBS_Umrn) from cbs_sponsor_txn_detail where"
                                            + " CBS_Umrn='" + pojo.getcBSUmrn() + "' and DATE(Txn_Date)='" + umrnTxnDt + "'").getResultList();
                                    int countOfTrnInDay = 0;
                                    if (!listcount.isEmpty()) {
                                        countOfTrnInDay = Integer.parseInt(((Vector) listcount.get(0)).get(0).toString());
                                        if ((countOfTrnInDay >= 1)) {
                                            continue;
                                        }
                                    }
                                }
                            } else {
                                if (!(fileGenDateymd.equalsIgnoreCase(pojo.getFromDate()))) {
                                    continue;
                                }
                            }
                        }
                        pojo.setTxnGenDate(fileGenDateymd);
                        if (umrnForCount.equalsIgnoreCase(pojo.getcBSUmrn())) {
                            seqNo = seqNo + 1;
                        } else {
                            umrnForCount = pojo.getcBSUmrn();
                            seqNo = 1;
                        }
                        pojo.setEntrySeqNo(seqNo);
                        umrnList.add(pojo);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        for (CbsMandateDetailPojo pojo : umrnList) {
            System.out.println("" + pojo.getAmount());
        }
        return umrnList;
    }

    public List<String[]> getACHOrECSInputGeneratedFile(String fileGenType, String fromDate, String toDate, String brnCode) throws ApplicationException {
        List<String[]> listObj = new ArrayList<String[]>();
        try {
            List list = em.createNativeQuery("select FILE_NAME,date_format(FILE_GEN_DATE,'%d/%m/%Y') from cbs_npci_mapper_files where  FILE_GEN_TYPE='" + fileGenType + "' "
                    + "and FILE_GEN_DATE between '" + ymd.format(dmy.parse(fromDate)) + "' and '" + ymd.format(dmy.parse(toDate)) + "' order by FILE_GEN_DATE desc ;").getResultList();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    String[] str = new String[2];
                    Vector ele = (Vector) list.get(i);
                    str[0] = ele.get(0).toString();
                    str[1] = ele.get(1).toString();
                    listObj.add(str);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return listObj;
    }

    @Override
    public String generateECSNewIputFile(List<CbsMandateDetailPojo> mandtDtlList, String fileType,
            String fileGenDate, String todayDt, String enterBy, String brnCode, String settleDate) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String aadharLocation = "", fileNo = "", bankCode = "", genFileName = "", totalRecord = "";
        try {
            ut.begin();
            if (mandtDtlList.isEmpty()) {
                throw new ApplicationException("There is no data to generate the files.");
            }
            List<CbsMandateDetailPojo> checkdMandateList = new ArrayList<CbsMandateDetailPojo>();
            List<CbsMandateDetailPojo> uncheckedMandateList = new ArrayList<CbsMandateDetailPojo>();
            for (CbsMandateDetailPojo obj : mandtDtlList) {
                if (obj.isCheckBox()) {
                    checkdMandateList.add(obj);
                } else {
                    uncheckedMandateList.add(obj);
                    continue;
                }
            }
            if (checkdMandateList.isEmpty()) {
                throw new ApplicationException("There is no selected data to generate the files.");
            }

            List list = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + enterBy + "'").getResultList();
            Vector elem = (Vector) list.get(0);
            if (list.isEmpty() || elem.get(0) == null || elem.get(0).toString().equals("")) {
                throw new ApplicationException("You are not authorized person to generate the files.");
            }
            String npciUserName = elem.get(0).toString().trim();
            //Required Values Extraction.
            list = em.createNativeQuery("select ifnull(a.code,'') as CR_HI,ifnull(b.code,'') as CR_DI,ifnull(c.code,'') "
                    + "as DR_HI,ifnull(d.code,'') as DR_DI,ifnull(e.code,'') as U_NO,ifnull(f.code,'') as U_NAME,"
                    + "ifnull(g.code,'') as MICR from (select ifnull((select code from cbs_parameterinfo  where "
                    + "name='CECS-CR-INP-HI'),'') as code) a,(select ifnull((select code from cbs_parameterinfo "
                    + "where name='CECS-CR-INP-DI'),'') as code) b,(select ifnull((select code from cbs_parameterinfo "
                    + "where name='CECS-DR-INP-HI'),'') as code) c,(select ifnull((select code from cbs_parameterinfo "
                    + "where name='CECS-DR-INP-DI'),'') as code) d,(select ifnull((select code from cbs_parameterinfo "
                    + "where name='CECS-INP-UNO'),'') as code) e,(select ifnull((select code from cbs_parameterinfo "
                    + "where name='CECS-INP-UNM'),'') as code) f,(select ifnull((select code from cbs_parameterinfo "
                    + "where name='CECS-INP-MICR'),'') as code) g").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please define required values.");
            }
            elem = (Vector) list.get(0);
            String creditHi = elem.get(0).toString().trim();
            String creditDi = elem.get(1).toString().trim();
            String debitHi = elem.get(2).toString().trim();
            String debitDi = elem.get(3).toString().trim();
            String userNo = elem.get(4).toString().trim();
            String userName = elem.get(5).toString().trim().toUpperCase();
            String bankMicr = elem.get(6).toString().trim();

            if (creditHi.equals("") || creditDi.equals("") || debitHi.equals("") || debitDi.equals("")
                    || userNo.equals("") || userName.equals("") || bankMicr.equals("")
                    || creditHi.length() != 2 || creditDi.length() != 2 || debitHi.length() != 2 || debitDi.length() != 2
                    || userName.length() > 20 || bankMicr.length() != 9 || userNo.length() > 7) {
                throw new ApplicationException("Please define required values for file generation.");
            }

            list = em.createNativeQuery("select aadhar_location,npci_bank_code from mb_sms_sender_bank_detail").getResultList();
            elem = (Vector) list.get(0);
            if (elem.get(0) == null || elem.get(1) == null || elem.get(0).toString().trim().equals("")
                    || elem.get(1).toString().trim().equals("") || elem.get(1).toString().trim().length() != 4) {
                throw new ApplicationException("Please define Aadhar Location and Bank Code.");
            }
            aadharLocation = elem.get(0).toString().trim();
            bankCode = elem.get(1).toString().trim();

            list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
                    + "where file_gen_date='" + ymd.format(dmy.parse(todayDt)) + "' and "
                    + "file_gen_type='" + fileType + "'").getResultList();
            elem = (Vector) list.get(0);
            fileNo = "1";
            if (elem.get(0) != null) {
                fileNo = elem.get(0).toString().trim();
            }
            if (fileType.equalsIgnoreCase("EINPSC")) { //ECS Credit Input
                genFileName = "ECS-CR-" + bankCode.toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
                        + ymdOne.format(new Date()) + "-" + "TMO" + ParseFileUtil.addTrailingZeros(fileNo, 6) + "-INP.txt";
            } else if (fileType.equalsIgnoreCase("EINPSD")) { //ECS Debit Input
                genFileName = "ECS-DR-" + bankCode.toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
                        + ymdOne.format(new Date()) + "-" + "TMO" + ParseFileUtil.addTrailingZeros(fileNo, 6) + "-INP.txt";
            }
            int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                    + "file_gen_time,file_gen_brncode,file_gen_type) values(" + Integer.parseInt(fileNo) + ",'"
                    + ymd.format(dmy.parse(todayDt)) + "','" + genFileName + "','" + enterBy + "',now(),'"
                    + brnCode + "','" + fileType + "')").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In ECS File Generation.");
            }

            // Unchecked data insertion
            if (!uncheckedMandateList.isEmpty()) {
                for (CbsMandateDetailPojo uncheckedData : uncheckedMandateList) {
                    n = em.createNativeQuery("INSERT INTO cbs_sponsor_txn_detail (CBS_Umrn,TxnFileType, Txn_Date,Entry_Date, Success_Flag, Return_Code, "
                            + "Response_File_Name, Update_By, Update_Date,Txn_File_Name,Entry_SeqNo,Cancel_By) VALUES "
                            + "('" + uncheckedData.getcBSUmrn() + "','" + fileType + "', '" + ymdSql.format(ymd.parse(uncheckedData.getTxnGenDate())) + "', '" + yyyymmdd.format(new Date()) + "', '', '', "
                            + "'', '" + enterBy + "', '" + ymdSql.format(new Date()) + "',''," + uncheckedData.getEntrySeqNo() + ",'" + enterBy + "');").executeUpdate();
                    if (n <= 0) {
                        throw new ApplicationException("Problem in ACH Input Files Insertion.");
                    }
                }
            }
            BigDecimal totalSubAmt = new BigDecimal(0);
            for (CbsMandateDetailPojo pojo : checkdMandateList) {
                BigDecimal individualAmt = new BigDecimal(pojo.getAmount());
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

            totalRecord = String.valueOf(checkdMandateList.size()).trim(); //Make to 9 digit.
            String settlementDate = ymdOne.format(dmy.parse(settleDate));
            String refNo = ParseFileUtil.addTrailingZeros(ymd.format(dmy.parse(todayDt)), 14);
            //Header Preparation.
            FileWriter fw = new FileWriter(aadharLocation + genFileName);
            String header = "";

            /*
             1	ECS Transaction Code                	2	NUM
             2	Destination sort code                   9	NUM
             3	Destination Account Type                2	NUM
             4	Ledger Folio Number                     3	ALP NUM
             5	Beneficiary's Bank Account number	15	ALP NUM
             6	Beneficiary Account Holder's Name	40	ALP NUM
             7	Sponsor Bank MICR                       9	NUM
             8	User Number                             7	NUM
             9	User Name/ Narration                    20	ALP NUM
             10	Transaction Reference                   13	ALP NUM
             11	Amount                                  13	NUM
             12	Reserved (ACH Item Seq No.)             10	NUM
             13	Reserved (Checksum)                     10	NUM
             14	Reserved (Flag for success / return)	1	NUM
             15	Reserved (Reason Code)                  2	NUM
             */

            if (fileType.equalsIgnoreCase("EINPSC")) { //ECS Credit Input
                header = creditHi + ParseFileUtil.addSuffixSpaces(userNo, 7) + ParseFileUtil.addSuffixSpaces(userName, 40)
                        + ParseFileUtil.addSuffixSpaces(refNo, 14) + ParseFileUtil.addSuffixZeros("", 9) + bankMicr
                        + ParseFileUtil.addSuffixSpaces("", 15) + ParseFileUtil.addSuffixSpaces("", 3)
                        + ParseFileUtil.addSuffixZeros("", 13) + amtInPaisa
                        + settlementDate
                        + ParseFileUtil.addSuffixSpaces("", 10) + ParseFileUtil.addSuffixSpaces("", 10)
                        + ParseFileUtil.addSuffixSpaces("", 3) + "\n";
            } else if (fileType.equalsIgnoreCase("EINPSD")) { //ECS Debit Input
                header = debitHi + ParseFileUtil.addSuffixSpaces(userNo, 7) + ParseFileUtil.addSuffixSpaces(userName, 40)
                        + ParseFileUtil.addSuffixSpaces(refNo, 14) + ParseFileUtil.addSuffixSpaces("", 9) + bankMicr
                        + ParseFileUtil.addSuffixSpaces("", 15) + ParseFileUtil.addSuffixSpaces("", 3)
                        + ParseFileUtil.addSuffixSpaces("", 13) + amtInPaisa + settlementDate
                        + ParseFileUtil.addSuffixSpaces("", 10) + ParseFileUtil.addSuffixSpaces("", 10)
                        + ParseFileUtil.addSuffixSpaces("", 3) + "\n";
            }
            fw.write(header);
            //Data Preparation.
            for (CbsMandateDetailPojo pojo : checkdMandateList) {
                String uniqueRefNo = pojo.getcBSUmrn() + ParseFileUtil.addTrailingZeros(String.valueOf(pojo.getEntrySeqNo()), 3);
//                String amount = String.valueOf(new BigDecimal(pojo.getAmount()).multiply(new BigDecimal(100)).intValue());
//                amount = ParseFileUtil.addTrailingZeros(amount.toString().trim(), 13);

                String indAmt = new BigDecimal(pojo.getAmount()).multiply(new BigDecimal(100)).toString();
                dotIndex = indAmt.toString().indexOf(".");
                if (dotIndex == -1) {
                    amtInPaisa = ParseFileUtil.addTrailingZeros(indAmt.toString().trim(), 13);
                } else {
                    amtInPaisa = ParseFileUtil.addTrailingZeros(indAmt.toString().substring(0, dotIndex).trim(), 13);
                }

                String destMicr = "", acno = "", name = "";
                if (pojo.getProprietary().equalsIgnoreCase("DEBIT")) {
                    destMicr = pojo.getDebtorIFSC().trim();
                    acno = pojo.getDebtorAcno().trim();
                    name = pojo.getDebtorName().trim();
                } else if (pojo.getProprietary().equalsIgnoreCase("CREDIT")) {
                    destMicr = pojo.getCreditorIFSC().trim();
                    acno = pojo.getCreditorAcno().trim();
                    name = pojo.getCreditorName().trim();
                }
                if (acno == null || acno.equals("") || acno.length() > 15) {
                    throw new ApplicationException("Account length should be upto 15 digit:" + acno);
                }
                if (name == null || name.equals("")) {
                    throw new ApplicationException("Account name should be upto 40 digit:" + acno);
                }
                name = name.replaceAll("[\\W_]", " ");
                name = name.length() > 40 ? name.substring(0, 40) : name;




                String individualStr = "";

                /*
                 1	ECS Transaction Code                	2	NUM
                 2	Destination sort code                   9	NUM
                 3	Destination Account Type                2	NUM
                 4	Ledger Folio Number                     3	ALP NUM
                 5	Beneficiary's Bank Account number	15	ALP NUM
                 6	Beneficiary Account Holder's Name	40	ALP NUM
                 7	Sponsor Bank MICR                       9	NUM
                 8	User Number                             7	NUM
                 9	User Name/ Narration                    20	ALP NUM
                 10	Transaction Reference                   13	ALP NUM
                 11	Amount                                  13	NUM
                 12	Reserved (ACH Item Seq No.)             10	NUM
                 13	Reserved (Checksum)                     10	NUM
                 14	Reserved (Flag for success / return)	1	NUM
                 15	Reserved (Reason Code)                  2	NUM

                 */
                if (fileType.equalsIgnoreCase("EINPSC")) { //ECS Credit Input
                    individualStr = creditDi + destMicr + ParseFileUtil.addTrailingZeros("", 2)
                            + ParseFileUtil.addSuffixSpaces("", 3) + ParseFileUtil.addSuffixSpaces(acno, 15)
                            + ParseFileUtil.addSuffixSpaces(name, 40) + bankMicr + ParseFileUtil.addTrailingZeros(userNo, 7)
                            + ParseFileUtil.addSuffixSpaces(userName, 20) + ParseFileUtil.addTrailingZeros(uniqueRefNo, 13) + amtInPaisa
                            + ParseFileUtil.addTrailingZeros("", 10) + ParseFileUtil.addTrailingZeros("", 10)
                            + ParseFileUtil.addTrailingZeros("", 1) + ParseFileUtil.addTrailingZeros("", 2) + "\n";
                } else if (fileType.equalsIgnoreCase("EINPSD")) { //ECS Debit Input
                    individualStr = debitDi + ParseFileUtil.addSuffixSpaces(destMicr, 9) + ParseFileUtil.addSuffixSpaces("", 2)
                            + ParseFileUtil.addSuffixSpaces("", 3) + ParseFileUtil.addSuffixSpaces(acno, 15)
                            + ParseFileUtil.addSuffixSpaces(name, 40) + bankMicr + ParseFileUtil.addSuffixSpaces(userNo, 7)
                            + ParseFileUtil.addSuffixSpaces(userName, 20) + ParseFileUtil.addSuffixSpaces(uniqueRefNo, 13) + amtInPaisa
                            + ParseFileUtil.addSuffixSpaces("", 10) + ParseFileUtil.addSuffixSpaces("", 10)
                            + ParseFileUtil.addSuffixSpaces("", 1) + ParseFileUtil.addSuffixSpaces("", 2) + "\n";
                }
                fw.write(individualStr);
                n = em.createNativeQuery("INSERT INTO cbs_sponsor_txn_detail (CBS_Umrn,TxnFileType, Txn_Date,Entry_Date, Success_Flag, Return_Code, "
                        + "Response_File_Name, Update_By, Update_Date,Txn_File_Name,Entry_SeqNo) VALUES "
                        + "('" + pojo.getcBSUmrn() + "','" + fileType + "', '" + ymdSql.format(ymd.parse(pojo.getTxnGenDate())) + "', '" + yyyymmdd.format(new Date()) + "', '', '', "
                        + "'', '" + enterBy + "', '" + ymdSql.format(new Date()) + "','" + genFileName + "'," + pojo.getEntrySeqNo() + ");").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem in ACH Input Files Insertion.");
                }
            }
            fw.close();
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "true";
    }

    public List getOlineAadharRegistrationData(String frDt, String toDt, String brnCode) throws ApplicationException {
        try {
            return em.createNativeQuery("select customerid,d.acno,CustFullName,mobileno,aadharNo,concat(ifnull(fathername,''),' ',ifnull(FatherMiddleName,''),' ',ifnull(FatherLastName,'')) as FatherName,PerAddressLine1,date_format(DateOfBirth,'%d/%m/%Y') dob,requestNo,requestType from cbs_customer_master_detail a,\n"
                    + "(select max(request_no) requestNo,id_no mobileno,request aadharNo,request_type requestType from cbs_third_party_request  where date_format(received_time,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and status=2\n"
                    + "group by date_format(received_time,'%Y%m%d'),id_no) b,customerid c,accountmaster d where a.SuspensionFlg not in ('S','Y') and primarybrcode = '" + brnCode + "'\n"
                    + "and cast(a.customerid as unsigned) = c.custid and c.acno = d.acno and substring(b.aadharNo,14,12) = d.acno and (ifnull(d.closingdate,'')='' or d.closingdate>'" + toDt + "')  group by customerid\n"
                    + "union\n"
                    + "select customerid,d.acno,CustFullName,mobileno,aadharNo,concat(ifnull(fathername,''),' ',ifnull(FatherMiddleName,''),' ',ifnull(FatherLastName,'')) as FatherName,PerAddressLine1,date_format(DateOfBirth,'%d/%m/%Y') dob,requestNo,requestType from cbs_customer_master_detail a,\n"
                    + "(select max(request_no) requestNo,id_no mobileno,request aadharNo,request_type requestType from cbs_third_party_request  where date_format(received_time,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and status=2\n"
                    + "group by date_format(received_time,'%Y%m%d'),id_no) b,customerid c,td_accountmaster d where a.SuspensionFlg not in ('S','Y')  and primarybrcode = '" + brnCode + "'\n"
                    + "and cast(a.customerid as unsigned) = c.custid and c.acno = d.acno and substring(b.aadharNo,14,12) = d.acno and (ifnull(d.closingdate,'')='' or d.closingdate>'" + toDt + "')  group by customerid  \n"
                    + "order by 1").getResultList();

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String onlineAadharUpdation(List<OnlineAadharRegistrationPojo> dataList, String enterDate, String enterBy, String brnCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            for (OnlineAadharRegistrationPojo obj : dataList) {
                String custId = obj.getCustId();
                if (obj.isSelected()) {
//                    int p = em.createNativeQuery("insert into cbs_customer_master_detail_his(customerid,title,custname,shortname,gender,maritalstatus,"
//                            + "fathername,mothername,staffflag,staffid,minorflag,nriflag,UINCardNo,communicationPreference,employerid,employeeNo,"
//                            + "mobilenumber,CustStatus,issuingAuthority,Expirydate,PlaceOfissue,PreferredLanguage,chgTurnOver,PurgeAllowed,"
//                            + "AccountManager,AllowSweeps,TradeFinanceFlag,SwiftCodeStatus,SwiftCode,BCBFID,CombinedStmtFlag,StmtFreqType,StmtFreqWeekNo,StmtFreqWeekDay,"
//                            + "StmtFreqStartDate,StmtFreqNP,IntroCustomerId,CustTitle,name,AddressLine1,AddressLine2,village,block,CityCode,StateCode,PostalCode,CountryCode,"
//                            + "PhoneNumber,TelexNumber,FaxNumber,salary,ChargeStatus,ChargeLevelCode,ABBChargeCode,PaperRemittance,DeliveryChannelChargeCode,"
//                            + "AccountLevelCharges,CustLevelCharges,TaxSlab,TDSCode,TDSCustomerId,TDSExemptionReferenceNo,ExemptionRemarks,"
//                            + "ITFileNo,TDSFloorLimit,CustFinancialDetails,FinancialYearAndMonth,CurrencyCodeType,PropertyAssets,BusinessAssets,Investments,NetWorth,Deposits,"
//                            + "OtherBankCode,LimitsWithOtherBank,FundBasedLimit,NonFundBasedLimit,OfflineCustDebitLimit,CustSalary,CustFinancialDate,PAN_GIRNumber,TINNumber,"
//                            + "SalesTaxNo,ExciseNo,VoterIDNo,DrivingLicenseNo,CreditCard,CardNumber,CardIssuer,Bankname,AcctId,BranchName,PerAddressLine1,PerAddressLine2,"
//                            + "PerVillage,PerBlock,PerCityCode,PerStateCode,PerPostalCode,PerCountryCode,PerPhoneNumber,PerTelexNumber,PerFaxNumber,MailAddressLine1,MailAddressLine2,"
//                            + "MailVillage,MailBlock,MailCityCode,MailStateCode,MailPostalCode,MAilCountryCode,MAilPhoneNumber,MailTelexNumber,MailfaxNumber,EmpAddressLine1,EmpAddressLine2,"
//                            + "EmpVillage,EmpBlock,EmpCityCode,EmpStateCode,EmpPostalCode,EmpCountryCode,EmpPhoneNumber,EmpTelexNumber,EmpFaxNumber,EmailID,OperationalRiskRating,"
//                            + "CreditRiskRatingInternal,ExternalRatingShortTerm,ExternalRatingLongTerm,"
//                            + "ThresoldTransactionLimit,SuspensionFlg,PrimaryBrCode,LastUpdatedBr,FirstAccountDate,Auth,LastChangeUserID,"
//                            + "LastChangeTime,RecordCreaterID,AADHAAR_NO,LPG_ID,AADHAAR_LPG_ACNO,MANDATE_FLAG,MANDATE_DATE,REG_TYPE,middle_name,last_name,Spouse_name,"
//                            + "maiden_name,nrega_job_card,dl_expiry,legal_document,income_range,networth_as_on,qualification,Political_exposed,juri_add1,juri_add2,juri_city,"
//                            + "juri_state,juri_postal,juri_country,tan,cin,per_email,mail_email,nationality,other_identity,poa,AcHolderTypeFlag,AcHolderType,AcType,CKYCNo,FatherMiddleName,"
//                            + "FatherLastName,SpouseMiddleName,SpouseLastName,MotherMiddleName,MotherLastName, TinIssuingCountry,CustEntityType,IdentityNo,IdExpiryDate,PerAddType,PerMailAddSameFlagIndicate,MailAddType,MailPoa,"
//                            + "JuriAddBasedOnFlag,JuriAddType,JuriPoa,PerDistrict,MailDistrict,EmpDistrict,JuriDistrict,PerOtherPOA,MailOtherPOA,JuriOtherPOA,father_spouse_flag,"
//                            + "isd_code,CustImage,CustFullName,gstIdentificationNumber)"
//                            + "select customerid,title,custname,shortname,gender,maritalstatus,"
//                            + "fathername,mothername,staffflag,staffid,minorflag,nriflag,UINCardNo,communicationPreference,employerid,employeeNo,"
//                            + "mobilenumber,CustStatus,issuingAuthority,date_format(ifnull(Expirydate,'1900-01-01'),'%d/%m/%Y'),PlaceOfissue,PreferredLanguage,chgTurnOver,PurgeAllowed,"
//                            + "AccountManager,AllowSweeps,TradeFinanceFlag,SwiftCodeStatus,SwiftCode,BCBFID,CombinedStmtFlag,StmtFreqType,StmtFreqWeekNo,StmtFreqWeekDay,"
//                            + "StmtFreqStartDate,StmtFreqNP,IntroCustomerId,CustTitle,name,AddressLine1,AddressLine2,village,block,CityCode,StateCode,PostalCode,CountryCode,"
//                            + "PhoneNumber,TelexNumber,FaxNumber,salary,ChargeStatus,ChargeLevelCode,ABBChargeCode,PaperRemittance,DeliveryChannelChargeCode,"
//                            + "AccountLevelCharges,CustLevelCharges,TaxSlab,TDSCode,TDSCustomerId,TDSExemptionReferenceNo,ExemptionRemarks,"
//                            + "ITFileNo,TDSFloorLimit,CustFinancialDetails,FinancialYearAndMonth,CurrencyCodeType,PropertyAssets,BusinessAssets,Investments,NetWorth,Deposits,"
//                            + "OtherBankCode,LimitsWithOtherBank,FundBasedLimit,NonFundBasedLimit,OfflineCustDebitLimit,CustSalary,CustFinancialDate,PAN_GIRNumber,TINNumber,"
//                            + "SalesTaxNo,ExciseNo,VoterIDNo,DrivingLicenseNo,CreditCard,CardNumber,CardIssuer,Bankname,AcctId,BranchName,PerAddressLine1,PerAddressLine2,"
//                            + "PerVillage,PerBlock,PerCityCode,PerStateCode,PerPostalCode,PerCountryCode,PerPhoneNumber,PerTelexNumber,PerFaxNumber,MailAddressLine1,MailAddressLine2,"
//                            + "MailVillage,MailBlock,MailCityCode,MailStateCode,MailPostalCode,MAilCountryCode,MAilPhoneNumber,MailTelexNumber,MailfaxNumber,EmpAddressLine1,EmpAddressLine2,"
//                            + "EmpVillage,EmpBlock,EmpCityCode,EmpStateCode,EmpPostalCode,EmpCountryCode,EmpPhoneNumber,EmpTelexNumber,EmpFaxNumber,EmailID,OperationalRiskRating,"
//                            + "CreditRiskRatingInternal,ExternalRatingShortTerm,ExternalRatingLongTerm,"
//                            + "ThresoldTransactionLimit,SuspensionFlg,PrimaryBrCode,LastUpdatedBr,FirstAccountDate,Auth,LastChangeUserID,"
//                            + "LastChangeTime,RecordCreaterID,AADHAAR_NO,LPG_ID,AADHAAR_LPG_ACNO,MANDATE_FLAG,MANDATE_DATE,REG_TYPE,middle_name,last_name,Spouse_name,"
//                            + "maiden_name,nrega_job_card,dl_expiry,legal_document,income_range,networth_as_on,qualification,Political_exposed,juri_add1,juri_add2,juri_city,"
//                            + "juri_state,juri_postal,juri_country,tan,cin,per_email,mail_email,nationality,other_identity,poa,AcHolderTypeFlag,AcHolderType,AcType,CKYCNo,FatherMiddleName,"
//                            + "FatherLastName,SpouseMiddleName,SpouseLastName,MotherMiddleName,MotherLastName, TinIssuingCountry,CustEntityType,IdentityNo,IdExpiryDate,PerAddType,PerMailAddSameFlagIndicate,MailAddType,MailPoa,"
//                            + "JuriAddBasedOnFlag,JuriAddType,JuriPoa,PerDistrict,MailDistrict,EmpDistrict,JuriDistrict,PerOtherPOA,MailOtherPOA,JuriOtherPOA,father_spouse_flag,"
//                            + "isd_code,CustImage,CustFullName,gstIdentificationNumber from cbs_customer_master_detail  where customerid='" + custId + "'").executeUpdate();
//                    if (p <= 0) {
//                        throw new ApplicationException("problem ib customerDetail history creation .");
//                    }
//                    int m = em.createNativeQuery("UPDATE cbs_customer_master_detail SET legal_document = 'E',IdentityNo = '" + obj.getAadharNo() + "',"
//                            + "LastChangeUserID = '" + obj.getEntry_by() + "',LastChangeTime = now()  WHERE customerid = '" + custId + "'").executeUpdate();
//                    if (m <= 0) {
//                        throw new ApplicationException("Problem In Updation.");
//                    }
                    // Insert into secondary Table(cbs_cust_identity_details)
                    List list = em.createNativeQuery("select * from cbs_cust_identity_details where IdentificationType = 'E' and CustomerId = '" + custId + "' and IdentityNo = '" + obj.getAadharNo() + "'").getResultList();
                    if (!list.isEmpty()) {
                        ut.rollback();
                        throw new ApplicationException("Already exists Aadhar No. in CBS For this Id : " + custId);
                    }
                    if (list.isEmpty()) {
                        int mm = em.createNativeQuery("INSERT INTO cbs_cust_identity_details (CustomerId, IdentificationType, IdentityNo, IdExpiryDate, OtherIdentificationType, TinIssuingCountry, EnterDate, EnterTime, EnterBy) \n"
                                + "VALUES ('" + custId + "', 'E', '" + obj.getAadharNo() + "', '', '', '', curdate(), now(), '" + obj.getEntry_by() + "')").executeUpdate();
                        if (mm <= 0) {
                            throw new ApplicationException("Problem In Updation.");
                        }
                        int n = em.createNativeQuery("UPDATE cbs_third_party_request SET  status = 3, update_by = '" + obj.getEntry_by() + "',update_date= now() "
                                + "WHERE request_type = '" + obj.getRequestType() + "' and id_no = '" + obj.getMobileNo() + "' and request_no = " + Integer.parseInt(obj.getRequestNo())).executeUpdate();
                        if (n <= 0) {
                            throw new ApplicationException("Problem In Updation.");
                        }
                    }
                }
            }
            ut.commit();
            return "true";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public String getAadharNoByCustId(String custId) throws ApplicationException {
        String cbsAadhar = "";
        try {
            List list = em.createNativeQuery("select a.CustomerId, a.IdentityNo cbsAadhar from cbs_cust_identity_details a,cbs_customer_master_detail b where a.IdentificationType= 'E' "
                    + "and a.customerid= b.customerid and a.customerid = '" + custId + "' "
                    + "union "
                    + "select a.CustomerId, a.IdentityNo cbsAadhar from cbs_customer_master_detail a where a.legal_document = 'E' and a.customerid = '" + custId + "'").getResultList();
            if (!list.isEmpty()) {
                Vector vtr = (Vector) list.get(0);
                cbsAadhar = vtr.get(1).toString();
            }
            return cbsAadhar;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public List<Npcih2hfilePojo> getFileDetail(String date) throws ApplicationException {
        List<Npcih2hfilePojo> returnList = new ArrayList<>();
        try {
            List dataList = em.createNativeQuery("select file_name,file_date,file_type from cbs_npci_h2h_file_detail "
                    + "where cast(file_date as date) = '" + date + "'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no data to show.");
            }
            int srno = 1;
            for (int i = 0; i < dataList.size(); i++) {
                Vector vec = (Vector) dataList.get(i);
                Npcih2hfilePojo pojo = new Npcih2hfilePojo();
                pojo.setSrno(srno++);
                pojo.setFileName(vec.get(0).toString());
                pojo.setFileType(vec.get(2).toString());

                returnList.add(pojo);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return returnList;
    }

    @Override
    public List isH2HFileDetailexits(String fileName) throws ApplicationException {
        List list = new ArrayList<>();
        try {
            list = em.createNativeQuery("select file_name from cbs_npci_h2h_file_detail "
                    + "where file_name = '" + fileName + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return list;
    }

    @Override
    public String h2hfileinsertionResponseCase(String fileName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int i = em.createNativeQuery("insert into cbs_npci_h2h_file_detail(file_date,file_name,file_type) "
                    + "values('" + ymd.format(new Date()) + "','" + fileName + "','RES')").executeUpdate();
            ut.commit();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }
}