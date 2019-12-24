/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.dto.NpciFileDto;
import com.cbs.dto.other.AutoMandateTo;
import com.cbs.dto.report.AccWiesMMSRepPojo;
import com.cbs.dto.report.ho.MmsReportPojo;
import com.cbs.dto.sms.MbSmsSenderBankDetailTO;
import com.cbs.dto.sms.TransferSmsRequestTo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ckycr.CkycrCommonMgmtFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.npci.h2h.H2HNpciMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.pojo.mms.esign.pain012.BranchAndFinancialInstitutionIdentification5;
import com.cbs.pojo.mms.esign.pain012.ClearingSystemMemberIdentification2;
import com.cbs.pojo.mms.esign.pain012.FinancialInstitutionIdentification8;
import com.cbs.pojo.mms.esign.pain012.GroupHeader47;
import com.cbs.pojo.mms.esign.pain012.MandateAcceptance4;
import com.cbs.pojo.mms.esign.pain012.MandateAcceptanceReportV04;
import com.cbs.pojo.mms.pain009.Frequency1Code;
import com.cbs.pojo.mms.pain009.SequenceType2Code;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.ParseFileUtil;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.hibernate.type.DateType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@Stateless(mappedName = "NpciMandateFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class NpciMandateFacade implements NpciMandateFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsRemote;
    @EJB
    private CommonReportMethodsRemote commonReport;
    @EJB
    private OtherNpciMgmtFacadeRemote otherNpciMgmtFacadeRemote;
    @EJB
    private SmsManagementFacadeRemote smsFacade;
    @EJB
    private CkycrCommonMgmtFacadeRemote ckycrCommonMgmtRemote;
    @EJB
    private H2HNpciMgmtFacadeRemote h2hNpciRemote;
    private Properties props = null;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdOne = new SimpleDateFormat("ddMMyyyy");
    SimpleDateFormat ymdTwo = new SimpleDateFormat("yyyy-MM-dd");

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
    public List getAllUploadedFileNo(String uploadDt, String mandateType, String mmsMode) throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct file_no from mms_upload_xml_detail "
                    + "where upload_date='" + uploadDt + "' and mandate_type='" + mandateType + "' and "
                    + "mandate_mode='" + mmsMode + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getAllUploadedFileNoAtGenerationTime(String uploadDt, String mandateType, String mandateMode) throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct file_no from mms_upload_xml_detail "
                    + "where upload_date='" + uploadDt + "' and mandate_type='" + mandateType + "' and mandate_mode='" + mandateMode + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getMandateReverifyDetails(String date, String mandateType, String brnCode, String mandateMode, String Umrn) throws ApplicationException {
        List list = new ArrayList<>();
        try {

            String query = "select MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,InstgAgt_FinInstnId_ClrSysMmbId_MmbId,"
                    + "InstgAgt_FinInstnId_Nm,InstdAgt_FinInstnId_ClrSysMmbId_MmbId,InstdAgt_FinInstnId_Nm,MndtId,"
                    + "MndtReqId,Tp_SvcLvl_Prtry,Tp_LclInstrm_Prtry,Ocrncs_SeqTp,Ocrncs_Frqcy,Ocrncs_FrstColltnDt,"
                    + "Ocrncs_FnlColltnDt,ColltnAmt,MaxAmt,Cdtr_Nm,CdtrAcct_Id_Othr_Id,CdtrAgt_FinInstnId_ClrSysMmbId_MmbId,"
                    + "CdtrAgt_FinInstnId_Nm,Dbtr_Nm,Dbtr_Id_PrvtId_Othr_Id,Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry,"
                    + "Dbtr_CtctDtls_PhneNb,Dbtr_CtctDtls_MobNb,Dbtr_CtctDtls_EmailAdr,Dbtr_CtctDtls_Othr,"
                    + "DbtrAcct_Id_Othr_Id,DbtrAcct_Tp_Prtry,DbtrAgt_FinInstnId_ClrSysMmbId_MmbId,DbtrAgt_FinInstnId_Nm,"
                    + "Mandate_Type,Mandate_Status,Accept,Reject_Code,Image_Name,Verify_By from mms_upload_xml_detail where "
                    + "MndtId='" + Umrn + "'  and mandate_type='" + mandateType + "' and Mandate_Mode = '" + mandateMode + "' and Mandate_Status='V'";
            if (brnCode.equalsIgnoreCase("90")) {
                list = em.createNativeQuery(query).getResultList();
            } else {
                if (mandateMode.equalsIgnoreCase("N")) {
                    list = em.createNativeQuery(query + " and substring(dbtracct_id_othr_id,1,2)='" + brnCode + "'").getResultList();
                } else {
                    String[] arr = otherNpciMgmtFacadeRemote.getIfscAndMicrCodeByBrnCode(brnCode);
                    String ifscCode = arr[0];
                    String micrNo = arr[1];
                    list = em.createNativeQuery(query + " and (DbtrAgt_FinInstnId_ClrSysMmbId_MmbId='" + ifscCode + "' or "
                            + "DbtrAgt_FinInstnId_ClrSysMmbId_MmbId='" + micrNo + "')").getResultList();
                }
            }
        } catch (Exception ex) {
        }
        return list;
    }

    @Override
    public List getMandateDetails(String uploadDt, Integer fileNo, String mandateType, String brnCode, String mandateMode) throws ApplicationException {
        List list = new ArrayList();
        try {
            String alphaCode = commonReport.getAlphacodeByBrncode(brnCode);
            String query = "select MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,InstgAgt_FinInstnId_ClrSysMmbId_MmbId,"
                    + "InstgAgt_FinInstnId_Nm,InstdAgt_FinInstnId_ClrSysMmbId_MmbId,InstdAgt_FinInstnId_Nm,MndtId,"
                    + "MndtReqId,Tp_SvcLvl_Prtry,Tp_LclInstrm_Prtry,Ocrncs_SeqTp,Ocrncs_Frqcy,Ocrncs_FrstColltnDt,"
                    + "Ocrncs_FnlColltnDt,ColltnAmt,MaxAmt,Cdtr_Nm,CdtrAcct_Id_Othr_Id,CdtrAgt_FinInstnId_ClrSysMmbId_MmbId,"
                    + "CdtrAgt_FinInstnId_Nm,Dbtr_Nm,Dbtr_Id_PrvtId_Othr_Id,Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry,"
                    + "Dbtr_CtctDtls_PhneNb,Dbtr_CtctDtls_MobNb,Dbtr_CtctDtls_EmailAdr,Dbtr_CtctDtls_Othr,"
                    + "DbtrAcct_Id_Othr_Id,DbtrAcct_Tp_Prtry,DbtrAgt_FinInstnId_ClrSysMmbId_MmbId,DbtrAgt_FinInstnId_Nm,"
                    + "Mandate_Type,Mandate_Status,Accept,Reject_Code,Image_Name,Verify_By from mms_upload_xml_detail "
                    + "where upload_date='" + uploadDt + "' and file_no=" + fileNo + " and mandate_type='" + mandateType + "' and Mandate_Mode = '" + mandateMode + "' ";

            if (alphaCode.equalsIgnoreCase("HO")) {
                list = em.createNativeQuery(query).getResultList();
            } else {
                if (mandateMode.equalsIgnoreCase("N")) {
                    list = em.createNativeQuery(query + " and substring(dbtracct_id_othr_id,1,2)='" + brnCode + "'").getResultList();
                } else {
                    String[] arr = otherNpciMgmtFacadeRemote.getIfscAndMicrCodeByBrnCode(brnCode);
                    String ifscCode = arr[0];
                    String micrNo = arr[1];
                    list = em.createNativeQuery(query + " and (DbtrAgt_FinInstnId_ClrSysMmbId_MmbId='" + ifscCode + "' or "
                            + "DbtrAgt_FinInstnId_ClrSysMmbId_MmbId='" + micrNo + "')").getResultList();
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return list;
    }
    //before changing
//    public List<AutoMandateTo> getArchievingDetails(String acNo, String function) throws ApplicationException {
//        List<AutoMandateTo> gridlist = new ArrayList<AutoMandateTo>();
//        try {
//            List list = new ArrayList();
//            if (function.equalsIgnoreCase("IM")) {
//                list = em.createNativeQuery("select MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,InstgAgt_FinInstnId_ClrSysMmbId_MmbId,"
//                        + "InstgAgt_FinInstnId_Nm,InstdAgt_FinInstnId_ClrSysMmbId_MmbId,InstdAgt_FinInstnId_Nm,MndtId,"
//                        + "MndtReqId,Tp_SvcLvl_Prtry,Tp_LclInstrm_Prtry,Ocrncs_SeqTp,Ocrncs_Frqcy,Ocrncs_FrstColltnDt,"
//                        + "Ocrncs_FnlColltnDt,ColltnAmt,MaxAmt,Cdtr_Nm,CdtrAcct_Id_Othr_Id,CdtrAgt_FinInstnId_ClrSysMmbId_MmbId,"
//                        + "CdtrAgt_FinInstnId_Nm,Dbtr_Nm,Dbtr_Id_PrvtId_Othr_Id,Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry,"
//                        + "Dbtr_CtctDtls_PhneNb,Dbtr_CtctDtls_MobNb,Dbtr_CtctDtls_EmailAdr,Dbtr_CtctDtls_Othr,"
//                        + "DbtrAcct_Id_Othr_Id,DbtrAcct_Tp_Prtry,DbtrAgt_FinInstnId_ClrSysMmbId_MmbId,DbtrAgt_FinInstnId_Nm,"
//                        + "Mandate_Type,Mandate_Status,Accept,Reject_Code,File_No,Image_Name,Verify_By,Mandate_Mode,date_format(Upload_Date,'%d/%m/%Y') "
//                        + "from mms_upload_xml_detail where Mandate_Status='V' and Accept='A' and DbtrAcct_Id_Othr_Id='" + acNo + "'").getResultList();
//                if (list.isEmpty()) {
//                    throw new ApplicationException("data is not get from the table");
//                }
//                for (int i = 0; i < list.size(); i++) {
//                    Vector ele = (Vector) list.get(i);
//                    AutoMandateTo obj = new AutoMandateTo();
//                    obj.setMsgId(ele.get(0).toString().trim());
//                    obj.setCreDtTm(ele.get(1).toString().trim());
//                    obj.setInitgPtyIdOrgIdOthrId(ele.get(2).toString().trim());
//                    obj.setInstgAgtFinInstnIdClrSysMmbIdMmbId(ele.get(3).toString().trim());
//                    obj.setInstgAgtFinInstnIdNm(ele.get(4).toString().trim());
//                    obj.setInstdAgtFinInstnIdClrSysMmbIdMmbId(ele.get(5).toString().trim());
//                    obj.setInstdAgtFinInstnIdNm(ele.get(6).toString().trim());
//                    obj.setMndtId(ele.get(7).toString().trim());
//                    obj.setMndtReqId(ele.get(8).toString().trim());
//                    obj.setTpSvcLvlPrtry(ele.get(9).toString().trim());
//                    obj.setTpLclInstrmPrtry(ele.get(10).toString().trim());
//                    obj.setOcrncsSeqTp(ele.get(11).toString().trim());
//                    obj.setOcrncsFrqcy(ele.get(12).toString().trim());
//                    obj.setOcrncsFrstColltnDt(ele.get(13).toString().trim());
//                    obj.setOcrncsFnlColltnDt(ele.get(14).toString().trim());
//                    obj.setColltnAmt(new BigDecimal(ele.get(15).toString().trim()));
//                    obj.setMaxAmt(new BigDecimal(ele.get(16).toString().trim()));
//                    obj.setCdtrNm(ele.get(17).toString().trim());
//                    obj.setCdtrAcctIdOthrId(ele.get(18).toString().trim());
//                    obj.setCdtrAgtFinInstnIdClrSysMmbIdMmbId(ele.get(19).toString().trim());
//                    obj.setCdtrAgtFinInstnIdNm(ele.get(20).toString().trim());
//                    obj.setDbtrNm(ele.get(21).toString().trim());
//                    obj.setDbtrIdPrvtIdOthrId(ele.get(22).toString().trim());
//                    obj.setDbtrIdPrvtIdOthrSchmeNmPrtry(ele.get(23).toString().trim());
//                    obj.setDbtrCtctDtlsPhneNb(ele.get(24).toString().trim());
//                    obj.setDbtrCtctDtlsMobNb(ele.get(25).toString().trim());
//                    obj.setDbtrCtctDtlsEmailAdr(ele.get(26).toString().trim());
//                    obj.setDbtrCtctDtlsOthr(ele.get(27).toString().trim());
//                    obj.setDbtrAcctIdOthrId(ele.get(28).toString().trim());
//                    obj.setDbtrAcctTpPrtry(ele.get(29).toString().trim());
//                    obj.setDbtrAgtFinInstnIdClrSysMmbIdMmbId(ele.get(30).toString().trim());
//                    obj.setDbtrAgtFinInstnIdNm(ele.get(31).toString().trim());
//                    obj.setMandateType(ele.get(32).toString().trim());
//                    obj.setMandateStatus(ele.get(33).toString().trim());
//                    obj.setAccept(ele.get(34).toString().trim());
//                    obj.setRejectCode(ele.get(35).toString().trim());
//                    obj.setFileNo(ele.get(36).toString().trim());
//                    obj.setImageName(ele.get(37).toString().trim());
//                    obj.setVerifyBy(ele.get(38).toString().trim());
//                    obj.setMandateMode(ele.get(39).toString().trim());
//                    obj.setUploadDate(ele.get(40).toString().trim());
//
//                    gridlist.add(obj);
//                }
//            }
//        } catch (Exception ex) {
//            throw new ApplicationException(ex.getMessage());
//        }
//        return gridlist;
//    }

    //after change
    public List<AutoMandateTo> getArchievingDetails(String oldAcno, String newAcno, String function) throws ApplicationException {
        List<AutoMandateTo> gridlist = new ArrayList<AutoMandateTo>();
        try {
            List list = new ArrayList();
            if (function.equalsIgnoreCase("IM")) {
                list = em.createNativeQuery("select MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,InstgAgt_FinInstnId_ClrSysMmbId_MmbId,"
                        + "InstgAgt_FinInstnId_Nm,InstdAgt_FinInstnId_ClrSysMmbId_MmbId,InstdAgt_FinInstnId_Nm,MndtId,"
                        + "MndtReqId,Tp_SvcLvl_Prtry,Tp_LclInstrm_Prtry,Ocrncs_SeqTp,Ocrncs_Frqcy,Ocrncs_FrstColltnDt,"
                        + "Ocrncs_FnlColltnDt,ColltnAmt,MaxAmt,Cdtr_Nm,CdtrAcct_Id_Othr_Id,CdtrAgt_FinInstnId_ClrSysMmbId_MmbId,"
                        + "CdtrAgt_FinInstnId_Nm,Dbtr_Nm,Dbtr_Id_PrvtId_Othr_Id,Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry,"
                        + "Dbtr_CtctDtls_PhneNb,Dbtr_CtctDtls_MobNb,Dbtr_CtctDtls_EmailAdr,Dbtr_CtctDtls_Othr,"
                        + "DbtrAcct_Id_Othr_Id,DbtrAcct_Tp_Prtry,DbtrAgt_FinInstnId_ClrSysMmbId_MmbId,DbtrAgt_FinInstnId_Nm,"
                        + "Mandate_Type,Mandate_Status,Accept,Reject_Code,File_No,Image_Name,Verify_By,Mandate_Mode,date_format(Upload_Date,'%d/%m/%Y') "
                        + "from mms_upload_xml_detail where Mandate_Status='V' and Accept='A' and (DbtrAcct_Id_Othr_Id='" + oldAcno + "'OR DbtrAcct_Id_Othr_Id='" + newAcno + "')").getResultList();
                if (list.isEmpty()) {
                    throw new ApplicationException("data is not get from the table");
                }
                for (int i = 0; i < list.size(); i++) {
                    Vector ele = (Vector) list.get(i);
                    AutoMandateTo obj = new AutoMandateTo();
                    obj.setMsgId(ele.get(0).toString().trim());
                    obj.setCreDtTm(ele.get(1).toString().trim());
                    obj.setInitgPtyIdOrgIdOthrId(ele.get(2).toString().trim());
                    obj.setInstgAgtFinInstnIdClrSysMmbIdMmbId(ele.get(3).toString().trim());
                    obj.setInstgAgtFinInstnIdNm(ele.get(4).toString().trim());
                    obj.setInstdAgtFinInstnIdClrSysMmbIdMmbId(ele.get(5).toString().trim());
                    obj.setInstdAgtFinInstnIdNm(ele.get(6).toString().trim());
                    obj.setMndtId(ele.get(7).toString().trim());
                    obj.setMndtReqId(ele.get(8).toString().trim());
                    obj.setTpSvcLvlPrtry(ele.get(9).toString().trim());
                    obj.setTpLclInstrmPrtry(ele.get(10).toString().trim());
                    obj.setOcrncsSeqTp(ele.get(11).toString().trim());
                    obj.setOcrncsFrqcy(ele.get(12).toString().trim());
                    obj.setOcrncsFrstColltnDt(ele.get(13).toString().trim());
                    obj.setOcrncsFnlColltnDt(ele.get(14).toString().trim());
                    obj.setColltnAmt(new BigDecimal(ele.get(15).toString().trim()));
                    obj.setMaxAmt(new BigDecimal(ele.get(16).toString().trim()));
                    obj.setCdtrNm(ele.get(17).toString().trim());
                    obj.setCdtrAcctIdOthrId(ele.get(18).toString().trim());
                    obj.setCdtrAgtFinInstnIdClrSysMmbIdMmbId(ele.get(19).toString().trim());
                    obj.setCdtrAgtFinInstnIdNm(ele.get(20).toString().trim());
                    obj.setDbtrNm(ele.get(21).toString().trim());
                    obj.setDbtrIdPrvtIdOthrId(ele.get(22).toString().trim());
                    obj.setDbtrIdPrvtIdOthrSchmeNmPrtry(ele.get(23).toString().trim());
                    obj.setDbtrCtctDtlsPhneNb(ele.get(24).toString().trim());
                    obj.setDbtrCtctDtlsMobNb(ele.get(25).toString().trim());
                    obj.setDbtrCtctDtlsEmailAdr(ele.get(26).toString().trim());
                    obj.setDbtrCtctDtlsOthr(ele.get(27).toString().trim());
                    obj.setDbtrAcctIdOthrId(ele.get(28).toString().trim());
                    obj.setDbtrAcctTpPrtry(ele.get(29).toString().trim());
                    obj.setDbtrAgtFinInstnIdClrSysMmbIdMmbId(ele.get(30).toString().trim());
                    obj.setDbtrAgtFinInstnIdNm(ele.get(31).toString().trim());
                    obj.setMandateType(ele.get(32).toString().trim());
                    obj.setMandateStatus(ele.get(33).toString().trim());
                    obj.setAccept(ele.get(34).toString().trim());
                    obj.setRejectCode(ele.get(35).toString().trim());
                    obj.setFileNo(ele.get(36).toString().trim());
                    obj.setImageName(ele.get(37).toString().trim());
                    obj.setVerifyBy(ele.get(38).toString().trim());
                    String MandateModeValue = ele.get(39).toString().trim();
                    if (MandateModeValue.equalsIgnoreCase("N")) {
                        obj.setMandateModeDescription("New");
                        obj.setMandateMode("N");
                    } else if (MandateModeValue.equalsIgnoreCase("L")) {
                        obj.setMandateModeDescription("Legacy");
                        obj.setMandateMode("L");
                    }
                    obj.setUploadDate(ele.get(40).toString().trim());
                    gridlist.add(obj);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return gridlist;
    }

    public List getMandateAcceptStatus(String mandtID, String mandateType, String mandateMode) throws ApplicationException {
        List list = new ArrayList<>();
        try {
            list = em.createNativeQuery("select Accept from mms_upload_xml_detail where Mandate_Type='" + mandateType + "' and Mandate_Mode='" + mandateMode + "' and MndtId='" + mandtID + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return list;
    }

    public String reVerifymandate(AutoMandateTo obj, String mode, String returnCode, String date,
            String mandateType, String user, String mandateMode, String acno) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if (mandateType.equals("CREATE") && mode.equals("A")) {
                List list = em.createNativeQuery("select mndtid from mms_detail where mndtid='" + obj.getMndtId() + "'and Mandate_Mode = '" + mandateMode + "'").getResultList();
                if (!list.isEmpty()) {
                    throw new ApplicationException("This mandate is already exist.");
                }
                int n = em.createNativeQuery("INSERT INTO mms_detail(MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,"
                        + "InstgAgt_FinInstnId_ClrSysMmbId_MmbId,InstgAgt_FinInstnId_Nm,InstdAgt_FinInstnId_ClrSysMmbId_MmbId,"
                        + "InstdAgt_FinInstnId_Nm,MndtId,MndtReqId,Tp_SvcLvl_Prtry,Tp_LclInstrm_Prtry,Ocrncs_SeqTp,"
                        + "Ocrncs_Frqcy,Ocrncs_FrstColltnDt,Ocrncs_FnlColltnDt,ColltnAmt,MaxAmt,Cdtr_Nm,CdtrAcct_Id_Othr_Id,"
                        + "CdtrAgt_FinInstnId_ClrSysMmbId_MmbId,CdtrAgt_FinInstnId_Nm,Dbtr_Nm,"
                        + "Dbtr_Id_PrvtId_Othr_Id,Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry,Dbtr_CtctDtls_Nm,Dbtr_CtctDtls_PhneNb,Dbtr_CtctDtls_MobNb,"
                        + "Dbtr_CtctDtls_EmailAdr,Dbtr_CtctDtls_Othr,DbtrAcct_Id_Othr_Id,DbtrAcct_Tp_Prtry,"
                        + "DbtrAgt_FinInstnId_ClrSysMmbId_MmbId,DbtrAgt_FinInstnId_Nm,Mandate_Type,Update_By,Update_Date,"
                        + "Update_Time,Mandate_Status,Mandate_Mode,Cbs_Acno) "
                        + "VALUES('" + obj.getMsgId() + "','" + obj.getCreDtTm() + "','" + obj.getInitgPtyIdOrgIdOthrId() + "',"
                        + "'" + obj.getInstgAgtFinInstnIdClrSysMmbIdMmbId() + "','" + obj.getInstgAgtFinInstnIdNm() + "',"
                        + "'" + obj.getInstdAgtFinInstnIdClrSysMmbIdMmbId() + "','" + obj.getInstdAgtFinInstnIdNm() + "',"
                        + "'" + obj.getMndtId() + "','" + obj.getMndtReqId() + "','" + obj.getTpSvcLvlPrtry() + "',"
                        + "'" + obj.getTpLclInstrmPrtry() + "','" + obj.getOcrncsSeqTp() + "','" + obj.getOcrncsFrqcy() + "',"
                        + "'" + obj.getOcrncsFrstColltnDt() + "','" + obj.getOcrncsFnlColltnDt() + "'," + obj.getColltnAmt() + ","
                        + "" + obj.getMaxAmt() + ",'" + obj.getCdtrNm() + "','" + obj.getCdtrAcctIdOthrId() + "',"
                        + "'" + obj.getCdtrAgtFinInstnIdClrSysMmbIdMmbId() + "','" + obj.getCdtrAgtFinInstnIdNm() + "',"
                        + "'" + obj.getDbtrNm() + "','" + obj.getDbtrIdPrvtIdOthrId() + "','" + obj.getDbtrIdPrvtIdOthrSchmeNmPrtry() + "',"
                        + "'','" + obj.getDbtrCtctDtlsPhneNb() + "','" + obj.getDbtrCtctDtlsMobNb() + "','" + obj.getDbtrCtctDtlsEmailAdr() + "',"
                        + "'" + obj.getDbtrCtctDtlsOthr() + "','" + obj.getDbtrAcctIdOthrId() + "','" + obj.getDbtrAcctTpPrtry() + "',"
                        + "'" + obj.getDbtrAgtFinInstnIdClrSysMmbIdMmbId() + "','" + obj.getDbtrAgtFinInstnIdNm() + "',"
                        + "'" + mandateType + "','" + user + "','" + date + "',now(),'','" + mandateMode + "','" + acno + "')").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Mandate Insertion.");
                }
                n = em.createNativeQuery("update mms_upload_xml_detail set mandate_status='V',accept='" + mode + "',"
                        + "reject_code='" + returnCode + "',verify_by='" + user + "',verify_time=now() "
                        + "where mandate_type='" + mandateType + "'  and "
                        + "mndtid='" + obj.getMndtId() + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Mandate Updation.");
                }
            } else if (mandateType.equals("AMEND") && mode.equals("A")) {
                //First data will go into history table-mms_detail_his. Table-mms_detail will update with new values
                int n = em.createNativeQuery("insert into mms_detail_his(MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,"
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
                        + "Cbs_Acno from mms_detail where MndtId = '" + obj.getMndtId() + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Mandate History Creation.");
                }

                n = em.createNativeQuery("update mms_detail set MsgId='" + obj.getMsgId() + "',CreDtTm='" + obj.getCreDtTm() + "',"
                        + "InitgPty_Id_OrgId_Othr_Id='" + obj.getInitgPtyIdOrgIdOthrId() + "',"
                        + "InstgAgt_FinInstnId_ClrSysMmbId_MmbId='" + obj.getInstgAgtFinInstnIdClrSysMmbIdMmbId() + "',"
                        + "InstgAgt_FinInstnId_Nm='" + obj.getInstgAgtFinInstnIdNm() + "',"
                        + "InstdAgt_FinInstnId_ClrSysMmbId_MmbId='" + obj.getInstdAgtFinInstnIdClrSysMmbIdMmbId() + "',"
                        + "InstdAgt_FinInstnId_Nm='" + obj.getInstdAgtFinInstnIdNm() + "',MndtReqId='" + obj.getMndtReqId() + "',"
                        + "Tp_SvcLvl_Prtry='" + obj.getTpSvcLvlPrtry() + "',Tp_LclInstrm_Prtry='" + obj.getTpLclInstrmPrtry() + "',"
                        + "Ocrncs_SeqTp='" + obj.getOcrncsSeqTp() + "',Ocrncs_Frqcy='" + obj.getOcrncsFrqcy() + "',"
                        + "Ocrncs_FrstColltnDt='" + obj.getOcrncsFrstColltnDt() + "',"
                        + "Ocrncs_FnlColltnDt='" + obj.getOcrncsFnlColltnDt() + "',"
                        + "ColltnAmt=" + obj.getColltnAmt() + ",MaxAmt=" + obj.getMaxAmt() + ",Cdtr_Nm='" + obj.getCdtrNm() + "',"
                        + "CdtrAcct_Id_Othr_Id='" + obj.getCdtrAcctIdOthrId() + "',"
                        + "CdtrAgt_FinInstnId_ClrSysMmbId_MmbId='" + obj.getCdtrAgtFinInstnIdClrSysMmbIdMmbId() + "',"
                        + "CdtrAgt_FinInstnId_Nm='" + obj.getCdtrAgtFinInstnIdNm() + "',Dbtr_Nm='" + obj.getDbtrNm() + "',"
                        + "Dbtr_Id_PrvtId_Othr_Id='" + obj.getDbtrIdPrvtIdOthrId() + "',"
                        + "Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry='" + obj.getDbtrIdPrvtIdOthrSchmeNmPrtry() + "',"
                        + "Dbtr_CtctDtls_PhneNb='" + obj.getDbtrCtctDtlsPhneNb() + "',"
                        + "Dbtr_CtctDtls_MobNb='" + obj.getDbtrCtctDtlsMobNb() + "',"
                        + "Dbtr_CtctDtls_EmailAdr='" + obj.getDbtrCtctDtlsEmailAdr() + "',"
                        + "Dbtr_CtctDtls_Othr='" + obj.getDbtrCtctDtlsOthr() + "',"
                        + "DbtrAcct_Id_Othr_Id='" + obj.getDbtrAcctIdOthrId() + "',"
                        + "DbtrAcct_Tp_Prtry='" + obj.getDbtrAcctTpPrtry() + "',"
                        + "DbtrAgt_FinInstnId_ClrSysMmbId_MmbId='" + obj.getDbtrAgtFinInstnIdClrSysMmbIdMmbId() + "',"
                        + "DbtrAgt_FinInstnId_Nm='" + obj.getDbtrAgtFinInstnIdNm() + "',Mandate_Type='" + mandateType + "',"
                        + "Update_By='" + user + "',Update_Date='" + date + "',"
                        + "Update_Time=now(),Mandate_Mode='" + mandateMode + "',Cbs_Acno='" + acno + "' where "
                        + "MndtId = '" + obj.getMndtId() + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Mandate Updation.");
                }
                n = em.createNativeQuery("update mms_upload_xml_detail set mandate_status='V',accept='" + mode + "',"
                        + "reject_code='" + returnCode + "',verify_by='" + user + "',verify_time=now() "
                        + "where mandate_type='" + mandateType + "' and "
                        + "mndtid='" + obj.getMndtId() + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Mandate Updation.");
                }
            } else if ((mandateType.equalsIgnoreCase("CREATE") || mandateType.equals("AMEND")) && mode.equals("R")) {
                int n = em.createNativeQuery("insert into mms_detail_his(MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,"
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
                        + "Cbs_Acno from mms_detail where MndtId = '" + obj.getMndtId() + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Mandate History Creation.");
                }

                int t = em.createNativeQuery("DELETE from mms_detail where MndtId = '" + obj.getMndtId() + "' and Update_Date='" + date + "'").executeUpdate();
                if (t <= 0) {
                    throw new ApplicationException("Problem In Mandate Deletion.");
                }
                n = em.createNativeQuery("update mms_upload_xml_detail set mandate_status='',accept='" + mode + "',"
                        + "reject_code='" + returnCode + "',verify_by='" + user + "',verify_time=null "
                        + "where mandate_type='" + mandateType + "' and "
                        + "mndtid='" + obj.getMndtId() + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Mandate Updation.");
                }
            }
            ut.commit();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    public String verifyMms(AutoMandateTo obj, String mode, String returnCode, String uploadDt,
            String mandateType, Integer fileNo, String userName, String todayDate, String mandateMode, String acNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List list = em.createNativeQuery("select mandate_status from mms_upload_xml_detail where "
                    + "mandate_type='" + mandateType + "' and upload_date='" + uploadDt + "' and "
                    + "file_no=" + fileNo + " and mndtid='" + obj.getMndtId() + "' and Mandate_Mode = '" + mandateMode + "'").getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                String mandateStatus = ele.get(0).toString().trim();
                if (!mandateStatus.equals("")) {
                    throw new ApplicationException("This entry has been already verified.");
                }
            }
            if (mandateType.equals("CREATE") && mode.equals("A")) { //A-Accept, R-Reject
                list = em.createNativeQuery("select mndtid from mms_detail where mndtid='" + obj.getMndtId() + "'and Mandate_Mode = '" + mandateMode + "'").getResultList();
                if (!list.isEmpty()) {
                    throw new ApplicationException("This mandate is already exist.");
                }

                int n = em.createNativeQuery("INSERT INTO mms_detail(MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,"
                        + "InstgAgt_FinInstnId_ClrSysMmbId_MmbId,InstgAgt_FinInstnId_Nm,InstdAgt_FinInstnId_ClrSysMmbId_MmbId,"
                        + "InstdAgt_FinInstnId_Nm,MndtId,MndtReqId,Tp_SvcLvl_Prtry,Tp_LclInstrm_Prtry,Ocrncs_SeqTp,"
                        + "Ocrncs_Frqcy,Ocrncs_FrstColltnDt,Ocrncs_FnlColltnDt,ColltnAmt,MaxAmt,Cdtr_Nm,CdtrAcct_Id_Othr_Id,"
                        + "CdtrAgt_FinInstnId_ClrSysMmbId_MmbId,CdtrAgt_FinInstnId_Nm,Dbtr_Nm,"
                        + "Dbtr_Id_PrvtId_Othr_Id,Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry,Dbtr_CtctDtls_Nm,Dbtr_CtctDtls_PhneNb,Dbtr_CtctDtls_MobNb,"
                        + "Dbtr_CtctDtls_EmailAdr,Dbtr_CtctDtls_Othr,DbtrAcct_Id_Othr_Id,DbtrAcct_Tp_Prtry,"
                        + "DbtrAgt_FinInstnId_ClrSysMmbId_MmbId,DbtrAgt_FinInstnId_Nm,Mandate_Type,Update_By,Update_Date,"
                        + "Update_Time,Mandate_Status,Mandate_Mode,Cbs_Acno) "
                        + "VALUES('" + obj.getMsgId() + "','" + obj.getCreDtTm() + "','" + obj.getInitgPtyIdOrgIdOthrId() + "',"
                        + "'" + obj.getInstgAgtFinInstnIdClrSysMmbIdMmbId() + "','" + obj.getInstgAgtFinInstnIdNm() + "',"
                        + "'" + obj.getInstdAgtFinInstnIdClrSysMmbIdMmbId() + "','" + obj.getInstdAgtFinInstnIdNm() + "',"
                        + "'" + obj.getMndtId() + "','" + obj.getMndtReqId() + "','" + obj.getTpSvcLvlPrtry() + "',"
                        + "'" + obj.getTpLclInstrmPrtry() + "','" + obj.getOcrncsSeqTp() + "','" + obj.getOcrncsFrqcy() + "',"
                        + "'" + obj.getOcrncsFrstColltnDt() + "','" + obj.getOcrncsFnlColltnDt() + "'," + obj.getColltnAmt() + ","
                        + "" + obj.getMaxAmt() + ",'" + obj.getCdtrNm() + "','" + obj.getCdtrAcctIdOthrId() + "',"
                        + "'" + obj.getCdtrAgtFinInstnIdClrSysMmbIdMmbId() + "','" + obj.getCdtrAgtFinInstnIdNm() + "',"
                        + "'" + obj.getDbtrNm() + "','" + obj.getDbtrIdPrvtIdOthrId() + "','" + obj.getDbtrIdPrvtIdOthrSchmeNmPrtry() + "',"
                        + "'','" + obj.getDbtrCtctDtlsPhneNb() + "','" + obj.getDbtrCtctDtlsMobNb() + "','" + obj.getDbtrCtctDtlsEmailAdr() + "',"
                        + "'" + obj.getDbtrCtctDtlsOthr() + "','" + obj.getDbtrAcctIdOthrId() + "','" + obj.getDbtrAcctTpPrtry() + "',"
                        + "'" + obj.getDbtrAgtFinInstnIdClrSysMmbIdMmbId() + "','" + obj.getDbtrAgtFinInstnIdNm() + "',"
                        + "'" + mandateType + "','" + userName + "','" + ymd.format(dmy.parse(todayDate)) + "',now(),'','" + mandateMode + "','" + acNo + "')").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Mandate Insertion.");
                }
            } else if (mandateType.equals("AMEND") && mode.equals("A")) {
                //First data will go into history table-mms_detail_his. Table-mms_detail will update with new values
                int n = em.createNativeQuery("insert into mms_detail_his(MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,"
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
                        + "Cbs_Acno from mms_detail where MndtId = '" + obj.getMndtId() + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Mandate History Creation.");
                }

                n = em.createNativeQuery("update mms_detail set MsgId='" + obj.getMsgId() + "',CreDtTm='" + obj.getCreDtTm() + "',"
                        + "InitgPty_Id_OrgId_Othr_Id='" + obj.getInitgPtyIdOrgIdOthrId() + "',"
                        + "InstgAgt_FinInstnId_ClrSysMmbId_MmbId='" + obj.getInstgAgtFinInstnIdClrSysMmbIdMmbId() + "',"
                        + "InstgAgt_FinInstnId_Nm='" + obj.getInstgAgtFinInstnIdNm() + "',"
                        + "InstdAgt_FinInstnId_ClrSysMmbId_MmbId='" + obj.getInstdAgtFinInstnIdClrSysMmbIdMmbId() + "',"
                        + "InstdAgt_FinInstnId_Nm='" + obj.getInstdAgtFinInstnIdNm() + "',MndtReqId='" + obj.getMndtReqId() + "',"
                        + "Tp_SvcLvl_Prtry='" + obj.getTpSvcLvlPrtry() + "',Tp_LclInstrm_Prtry='" + obj.getTpLclInstrmPrtry() + "',"
                        + "Ocrncs_SeqTp='" + obj.getOcrncsSeqTp() + "',Ocrncs_Frqcy='" + obj.getOcrncsFrqcy() + "',"
                        + "Ocrncs_FrstColltnDt='" + obj.getOcrncsFrstColltnDt() + "',"
                        + "Ocrncs_FnlColltnDt='" + obj.getOcrncsFnlColltnDt() + "',"
                        + "ColltnAmt=" + obj.getColltnAmt() + ",MaxAmt=" + obj.getMaxAmt() + ",Cdtr_Nm='" + obj.getCdtrNm() + "',"
                        + "CdtrAcct_Id_Othr_Id='" + obj.getCdtrAcctIdOthrId() + "',"
                        + "CdtrAgt_FinInstnId_ClrSysMmbId_MmbId='" + obj.getCdtrAgtFinInstnIdClrSysMmbIdMmbId() + "',"
                        + "CdtrAgt_FinInstnId_Nm='" + obj.getCdtrAgtFinInstnIdNm() + "',Dbtr_Nm='" + obj.getDbtrNm() + "',"
                        + "Dbtr_Id_PrvtId_Othr_Id='" + obj.getDbtrIdPrvtIdOthrId() + "',"
                        + "Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry='" + obj.getDbtrIdPrvtIdOthrSchmeNmPrtry() + "',"
                        + "Dbtr_CtctDtls_PhneNb='" + obj.getDbtrCtctDtlsPhneNb() + "',"
                        + "Dbtr_CtctDtls_MobNb='" + obj.getDbtrCtctDtlsMobNb() + "',"
                        + "Dbtr_CtctDtls_EmailAdr='" + obj.getDbtrCtctDtlsEmailAdr() + "',"
                        + "Dbtr_CtctDtls_Othr='" + obj.getDbtrCtctDtlsOthr() + "',"
                        + "DbtrAcct_Id_Othr_Id='" + obj.getDbtrAcctIdOthrId() + "',"
                        + "DbtrAcct_Tp_Prtry='" + obj.getDbtrAcctTpPrtry() + "',"
                        + "DbtrAgt_FinInstnId_ClrSysMmbId_MmbId='" + obj.getDbtrAgtFinInstnIdClrSysMmbIdMmbId() + "',"
                        + "DbtrAgt_FinInstnId_Nm='" + obj.getDbtrAgtFinInstnIdNm() + "',Mandate_Type='" + mandateType + "',"
                        + "Update_By='" + userName + "',Update_Date='" + ymd.format(dmy.parse(todayDate)) + "',"
                        + "Update_Time=now(),Mandate_Mode='" + mandateMode + "',Cbs_Acno='" + acNo + "' where "
                        + "MndtId = '" + obj.getMndtId() + "'").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Problem In Mandate Updation.");
                }
            }

            int n = em.createNativeQuery("update mms_upload_xml_detail set mandate_status='V',accept='" + mode + "',"
                    + "reject_code='" + returnCode + "',verify_by='" + userName + "',verify_time=now() "
                    + "where mandate_type='" + mandateType + "' and upload_date='" + uploadDt + "' and "
                    + "file_no=" + fileNo + " and mndtid='" + obj.getMndtId() + "'").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In Mandate Updation.");
            }
            ut.commit();
            try {
                Map<String, String> createMap = getCreateReturnMap();
                Map<String, String> amendMap = getAmendReturnMap();

                if (mode.equals("R")) { //Return Case
                    List<MbSmsSenderBankDetailTO> bankTo = smsFacade.getBankAndSenderDetail();
                    String templateBankName = bankTo.get(0).getTemplateBankName().trim();

                    String umrn = obj.getMndtId();
                    String corporateName = obj.getCdtrNm();
                    Double collAmt = obj.getColltnAmt().doubleValue();
                    Double maxAmount = obj.getMaxAmt().doubleValue();
                    String frequency = obj.getOcrncsFrqcy();
                    String drAcno = obj.getDbtrAcctIdOthrId();

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
                        trfSmsTo.setTemplate(SmsType.MANDATE_REJECT);
                        trfSmsTo.setAcno(drAcno);
                        trfSmsTo.setBankName(templateBankName);
                        trfSmsTo.setPin(umrn);
                        trfSmsTo.setPullCode(corporateName);
                        trfSmsTo.setFirstCheque(actualAmt.toString());
                        trfSmsTo.setServices(frequency);
                        trfSmsTo.setUserName(userName);
                        trfSmsTo.setPromoMobile(mobileNo);

                        String returnDesc = "";
                        if (mandateType.equals("CREATE")) {
                            returnDesc = createMap.get(returnCode);
                        } else if (mandateType.equals("AMEND")) {
                            returnDesc = amendMap.get(returnCode);
                        }
                        trfSmsTo.setLastCheque(returnDesc);

                        smsFacade.sendSms(trfSmsTo);
                    }
                }
            } catch (Exception ex) {
                System.out.println("Problem In SMS Sending-->" + ex.getMessage());
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

    public Map<String, String> getCreateReturnMap() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        try {
            List list = commonReport.getRefRecList("320");
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                map.put(ele.get(0).toString().trim(), ele.get(1).toString().trim());
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return map;
    }

    public Map<String, String> getAmendReturnMap() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        try {
            List list = commonReport.getRefRecList("321");
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                map.put(ele.get(0).toString().trim(), ele.get(1).toString().trim());
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return map;
    }

    public List<NpciFileDto> showMmsFiles(String fileType, String mandateType, String fileShowDt, Double seqNo) throws ApplicationException {
        List<NpciFileDto> dataList = new ArrayList<NpciFileDto>();
        try {
            List list = null;
            if (fileType.equals("RETURN")) {
                list = em.createNativeQuery("select file_no,date_format(file_gen_date,'%d/%m/%Y'),file_name,"
                        + "file_gen_by from cbs_npci_mapper_files where file_gen_date='" + ymd.format(dmy.parse(fileShowDt)) + "' and "
                        + "file_gen_type='" + mandateType + "' and seq_no=" + seqNo + "").getResultList();
            } else if (fileType.equals("INITIATION")) {
                list = em.createNativeQuery("select file_no,date_format(file_gen_date,'%d/%m/%Y'),file_name,"
                        + "file_gen_by from cbs_npci_mapper_files where file_gen_date='" + ymd.format(dmy.parse(fileShowDt)) + "' and "
                        + "file_gen_type='" + getFileType(mandateType) + "'").getResultList();
            }
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

//    public String generateMmsCreateReturn(String mmsType, String fileUpDt, String fileNo,
//            String todayDt, String userName, String orgnBrCode) throws ApplicationException {
//        UserTransaction ut = context.getUserTransaction();
//        try {
//            ut.begin();
//            List list = em.createNativeQuery("select npciusername from securityinfo where "
//                    + "userid='" + userName + "'").getResultList();
//            Vector ele = (Vector) list.get(0);
//            if (list.isEmpty() || ele.get(0) == null || ele.get(0).toString().equals("")) {
//                throw new ApplicationException("You are not authorized person to generate the files.");
//            }
//            String npciUserName = ele.get(0).toString().trim();
//            //Either file has been generated already
//            list = em.createNativeQuery("select file_no from cbs_npci_mapper_files where "
//                    + "file_gen_date='" + fileUpDt + "' and file_gen_type='" + mmsType + "' and "
//                    + "seq_no=" + fileNo + "").getResultList();
//            if (!list.isEmpty()) {
//                throw new ApplicationException("File has been already generated for this details.");
//            }
//
//            //All entries should be verified
//            list = em.createNativeQuery("select MndtReqId from mms_upload_xml_detail where mandate_type='" + mmsType + "' and "
//                    + "upload_date='" + fileUpDt + "' and file_no=" + fileNo + " and mandate_status=''").getResultList();
//            if (!list.isEmpty()) {
//                throw new ApplicationException("Please verify all entries for Mandate Type-->" + mmsType + ":Date-->"
//                        + dmy.format(ymd.parse(fileUpDt)) + ":File No-->" + fileNo);
//            }
//
//            list = em.createNativeQuery("select iin,aadhar_location,npci_bank_code from "
//                    + "mb_sms_sender_bank_detail").getResultList();
//            ele = (Vector) list.get(0);
//            if (ele.get(0) == null || ele.get(1) == null || ele.get(2) == null
//                    || ele.get(0).toString().trim().equals("")
//                    || ele.get(1).toString().trim().equals("")
//                    || ele.get(2).toString().trim().equals("")
//                    || ele.get(2).toString().trim().length() != 4) {
//                throw new ApplicationException("Please define IIN, Aadhar Location and Bank Code.");
//            }
////            String iin = ele.get(0).toString().trim();    //Make to 9 digit.
//            String aadharLocation = ele.get(1).toString().trim();
//            String bankCode = ele.get(2).toString().trim();    //As it is
//
//            File directory = new File(aadharLocation + fileUpDt + "/" + fileNo + "_" + mmsType.toUpperCase() + "/");
//            if (!directory.exists()) {
//                directory.mkdirs();
//            }
//
//            List dataList = em.createNativeQuery("select MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,"
//                    + "InstgAgt_FinInstnId_ClrSysMmbId_MmbId,InstgAgt_FinInstnId_Nm,InstdAgt_FinInstnId_ClrSysMmbId_MmbId, "
//                    + "InstdAgt_FinInstnId_Nm,MndtReqId,Accept,Reject_code,MndtId from "
//                    + "mms_upload_xml_detail where mandate_type='" + mmsType + "' and upload_date='" + fileUpDt + "' and "
//                    + "file_no=" + fileNo + " and mandate_status='V'").getResultList();
//
//            String acceptFileName = "MMS-ACCEPT-" + bankCode.toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
//                    + ymdOne.format(ymd.parse(fileUpDt)) + "-" + ParseFileUtil.addTrailingZeros(fileNo, 6) + "-INP.xml";
//
//            Node node = getNodeForRootElement("Document");
//            for (int i = 0; i < dataList.size(); i++) {
//                MandateAcceptanceReportV01 MndtAccptncRpt = new MandateAcceptanceReportV01();
//                Vector elem = (Vector) dataList.get(i);
//                String msgId = elem.get(0).toString();
////                String CreDtTm = elem.get(1).toString();
////                String initgPtyIdOrgIdOthrId = elem.get(2).toString();
//                String instgAgtFinInstnIdClrSysMmbIdMmbId = elem.get(3).toString();
//                String instgAgtFinInstnIdNm = elem.get(4).toString();
//                String instdAgtFinInstnIdClrSysMmbIdMmbId = elem.get(5).toString();
//                String instdAgtFinInstnIdNm = elem.get(6).toString();
//                String mndtReqId = elem.get(7).toString();
//                String accept = elem.get(8).toString();
//                String rejectCode = elem.get(9).toString();
//                String mndtId = elem.get(10).toString();
//
//                GroupHeader31 groupHeader31 = new GroupHeader31();
//                //MsgId,CreDtTm
//
//                groupHeader31.setMsgId(msgId);
//                GregorianCalendar cal = new GregorianCalendar();
//                cal.setTime(new Date());
//                groupHeader31.setCreDtTm(DatatypeFactory.newInstance().newXMLGregorianCalendar(cal));
//
//                //GrpHdr-InstgAgt
//
//                ClearingSystemMemberIdentification2 clearingSystemMemberIdentification2 = new ClearingSystemMemberIdentification2();
//                clearingSystemMemberIdentification2.setMmbId(instgAgtFinInstnIdClrSysMmbIdMmbId);
//
//                FinancialInstitutionIdentification7 financialInstitutionIdentification7 = new FinancialInstitutionIdentification7();
//                financialInstitutionIdentification7.setNm(instgAgtFinInstnIdNm);
//                financialInstitutionIdentification7.setClrSysMmbId(clearingSystemMemberIdentification2);
//
//                BranchAndFinancialInstitutionIdentification4 branchAndFinancialInstitutionIdentification4 = new BranchAndFinancialInstitutionIdentification4();
//                branchAndFinancialInstitutionIdentification4.setFinInstnId(financialInstitutionIdentification7);
//
//                groupHeader31.setInstgAgt(branchAndFinancialInstitutionIdentification4);
//
//                //GrpHdr-InstdAgt
//
//                ClearingSystemMemberIdentification2 clearingSystemMemberIdentification3 = new ClearingSystemMemberIdentification2();
//                clearingSystemMemberIdentification3.setMmbId(instdAgtFinInstnIdClrSysMmbIdMmbId);
//
//                FinancialInstitutionIdentification7 financialInstitutionIdentification8 = new FinancialInstitutionIdentification7();
//                financialInstitutionIdentification8.setNm(instdAgtFinInstnIdNm);
//                financialInstitutionIdentification8.setClrSysMmbId(clearingSystemMemberIdentification3);
//
//                BranchAndFinancialInstitutionIdentification4 branchAndFinancialInstitutionIdentification5 = new BranchAndFinancialInstitutionIdentification4();
//                branchAndFinancialInstitutionIdentification5.setFinInstnId(financialInstitutionIdentification8);
//
//                groupHeader31.setInstdAgt(branchAndFinancialInstitutionIdentification5);
//
//                //UndrlygAccptncDtls
//
//                MandateAcceptance1 mandateAcceptance1 = new MandateAcceptance1();
//
//                OriginalMessageInformation1 originalMessageInformation1 = new OriginalMessageInformation1();
//                originalMessageInformation1.setMsgId(mndtReqId);
//                originalMessageInformation1.setMsgNmId("pain.009.001.01");
//                mandateAcceptance1.setOrgnlMsgInf(originalMessageInformation1);
//
//                AcceptanceResult6 acceptanceResult6 = new AcceptanceResult6();
//                acceptanceResult6.setAccptd(accept.equals("A") ? true : false);
//                MandateReason1Choice mandateReason1Choice = new MandateReason1Choice();
//                mandateReason1Choice.setPrtry(rejectCode.equals("") ? "AC01" : rejectCode);
//                acceptanceResult6.setRjctRsn(mandateReason1Choice);
//                mandateAcceptance1.setAccptncRslt(acceptanceResult6);
//
//                OriginalMandate1Choice originalMandate1Choice = new OriginalMandate1Choice();
//                originalMandate1Choice.setOrgnlMndtId(mndtId);
//                mandateAcceptance1.setOrgnlMndt(originalMandate1Choice);
//
//                MndtAccptncRpt.setGrpHdr(groupHeader31);
//                MndtAccptncRpt.setUndrlygAccptncDtls(mandateAcceptance1);
//
//                JAXBContext jaxbContext = JAXBContext.newInstance(MandateAcceptanceReportV01.class);
//                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//                // output pretty printed
//                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//                jaxbMarshaller.marshal(MndtAccptncRpt, node);
//                TransformerFactory transformerFactory = TransformerFactory.newInstance();
//                javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
//                DOMSource source = new DOMSource(node);
//                StreamResult result = new StreamResult(new File(directory + "/" + acceptFileName));
//
//                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
//                transformer.transform(source, result);
//            }
//            //Creating Zip File.
//            String ziplFileName = "MMS-CREATE-" + bankCode.toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
//                    + ymdOne.format(ymd.parse(fileUpDt)) + "-" + ParseFileUtil.addTrailingZeros(fileNo, 6) + "-ACCEPT.zip";
//            String zipFile = directory + "/" + ziplFileName;
//
//            String[] allXmlFiles = directory.list();
//            //create byte buffer
//            byte[] buffer = new byte[1024];
//
//            FileOutputStream fout = new FileOutputStream(zipFile);
//            ZipOutputStream zout = new ZipOutputStream(fout);
//            for (int i = 0; i < allXmlFiles.length; i++) {
//                System.out.println("Adding " + allXmlFiles[i]);
//                FileInputStream fin = new FileInputStream(directory + "/" + allXmlFiles[i]);
//
//                zout.putNextEntry(new ZipEntry(allXmlFiles[i]));
//                int length;
//                while ((length = fin.read(buffer)) > 0) {
//                    zout.write(buffer, 0, length);
//                }
//                zout.closeEntry();
//                fin.close();
//            }
//            zout.close();
//            //Logging of generated zip file.
//            String seqNo = "";
//            list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
//                    + "where file_gen_date='" + fileUpDt + "' and file_gen_type='" + mmsType + "'").getResultList();
//            ele = (Vector) list.get(0);
//            seqNo = "1";
//            if (ele.get(0) != null) {
//                seqNo = ele.get(0).toString().trim();  //Make to 5 digit.
//            }
//            int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
//                    + "file_gen_time,file_gen_brncode,file_gen_type,seq_no) values(" + Integer.parseInt(seqNo) + ",'"
//                    + fileUpDt + "','" + ziplFileName + "','" + userName + "',now(),'"
//                    + orgnBrCode + "','" + mmsType + "'," + Double.parseDouble(fileNo) + ")").executeUpdate();
//            if (n <= 0) {
//                throw new ApplicationException("Problem In MMS create return generation.");
//            }
//            System.out.println("Zip file has been created!");
//            ut.commit();
//        } catch (Exception ex) {
//            try {
//                ut.rollback();
//                throw new ApplicationException(ex.getMessage());
//            } catch (Exception e) {
//                throw new ApplicationException(e.getMessage());
//            }
//        }
//        return "true";
//    }
    private Node getNodeForRootElement(String rootElementName) throws ApplicationException {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            //root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement(rootElementName);
            doc.appendChild(rootElement);
            Node node = doc.getFirstChild();
            return node;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<AutoMandateTo> getPreviousMandateDetail(String umrn) throws ApplicationException {
        List<AutoMandateTo> dataList = new ArrayList<AutoMandateTo>();
        try {
            List list = em.createNativeQuery("SELECT MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,"
                    + "InstgAgt_FinInstnId_ClrSysMmbId_MmbId,InstgAgt_FinInstnId_Nm,InstdAgt_FinInstnId_ClrSysMmbId_MmbId,"
                    + "InstdAgt_FinInstnId_Nm,MndtId,MndtReqId,Tp_SvcLvl_Prtry,Tp_LclInstrm_Prtry,Ocrncs_SeqTp,"
                    + "Ocrncs_Frqcy,Ocrncs_FrstColltnDt,Ocrncs_FnlColltnDt,ColltnAmt,MaxAmt,Cdtr_Nm,CdtrAcct_Id_Othr_Id,"
                    + "CdtrAgt_FinInstnId_ClrSysMmbId_MmbId,CdtrAgt_FinInstnId_Nm,Dbtr_Nm,"
                    + "Dbtr_Id_PrvtId_Othr_Id,Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry,Dbtr_CtctDtls_PhneNb,"
                    + "Dbtr_CtctDtls_MobNb,Dbtr_CtctDtls_EmailAdr,Dbtr_CtctDtls_Othr,DbtrAcct_Id_Othr_Id,"
                    + "DbtrAcct_Tp_Prtry,DbtrAgt_FinInstnId_ClrSysMmbId_MmbId,DbtrAgt_FinInstnId_Nm,Mandate_Type,"
                    + "Update_By FROM mms_detail where MndtId='" + umrn + "'").getResultList();
            for (int i = 0; i < list.size(); i++) {
                AutoMandateTo to = new AutoMandateTo();
                Vector ele = (Vector) list.get(i);

                to.setMsgId(ele.get(0).toString().trim());
                to.setCreDtTm(ele.get(1).toString().trim());
                to.setInitgPtyIdOrgIdOthrId(ele.get(2).toString().trim());
                to.setInstgAgtFinInstnIdClrSysMmbIdMmbId(ele.get(3).toString().trim());
                to.setInstgAgtFinInstnIdNm(ele.get(4).toString().trim());

                to.setInstdAgtFinInstnIdClrSysMmbIdMmbId(ele.get(5).toString().trim());
                to.setInstdAgtFinInstnIdNm(ele.get(6).toString().trim());
                to.setMndtId(ele.get(7).toString().trim());
                to.setMndtReqId(ele.get(8).toString().trim());
                to.setTpSvcLvlPrtry(ele.get(9).toString().trim());

                to.setTpLclInstrmPrtry(ele.get(10).toString().trim());
                to.setOcrncsSeqTp(ele.get(11).toString().trim());
                to.setOcrncsFrqcy(ele.get(12).toString().trim());
                to.setOcrncsFrstColltnDt(ele.get(13).toString().trim());
                to.setOcrncsFnlColltnDt(ele.get(14).toString().trim());

                to.setColltnAmt(new BigDecimal(ele.get(15).toString().trim()));
                to.setMaxAmt(new BigDecimal(ele.get(16).toString().trim()));
                to.setCdtrNm(ele.get(17).toString().trim());
                to.setCdtrAcctIdOthrId(ele.get(18).toString().trim());
                to.setCdtrAgtFinInstnIdClrSysMmbIdMmbId(ele.get(19).toString().trim());

                to.setCdtrAgtFinInstnIdNm(ele.get(20).toString().trim());
                to.setDbtrNm(ele.get(21).toString().trim());
                to.setDbtrIdPrvtIdOthrId(ele.get(22).toString().trim());
                to.setDbtrIdPrvtIdOthrSchmeNmPrtry(ele.get(23).toString().trim());
                to.setDbtrCtctDtlsPhneNb(ele.get(24).toString().trim());

                to.setDbtrCtctDtlsMobNb(ele.get(25).toString().trim());
                to.setDbtrCtctDtlsEmailAdr(ele.get(26).toString().trim());
                to.setDbtrCtctDtlsOthr(ele.get(27).toString().trim());
                to.setDbtrAcctIdOthrId(ele.get(28).toString().trim());
                to.setDbtrAcctTpPrtry(ele.get(29).toString().trim());

                to.setDbtrAgtFinInstnIdClrSysMmbIdMmbId(ele.get(30).toString().trim());
                to.setDbtrAgtFinInstnIdNm(ele.get(31).toString().trim());
                to.setMandateType(ele.get(32).toString().trim());
                to.setUpdateBy(ele.get(33).toString().trim());

                dataList.add(to);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

//    public String generateMmsCreateReturn(String mmsType, String fileUpDt, String fileNo,
//            String todayDt, String userName, String orgnBrCode) throws ApplicationException {
//        UserTransaction ut = context.getUserTransaction();
//        try {
//            ut.begin();
//            String npciUserName = getNpciUserName(userName);
//            //All entries should be verified
//            List list = em.createNativeQuery("select MndtReqId from mms_upload_xml_detail where "
//                    + "mandate_type='" + mmsType + "' and upload_date='" + fileUpDt + "' and "
//                    + "file_no=" + fileNo + " and mandate_status=''").getResultList();
//            if (!list.isEmpty()) {
//                throw new ApplicationException("Please verify all entries for Mandate Type-->" + mmsType + ":Date-->"
//                        + dmy.format(ymd.parse(fileUpDt)) + ":File No-->" + fileNo);
//            }
//
//            String[] prelimiaryData = getNpciPrelimiaryData();
//            File directory = new File(prelimiaryData[1] + fileUpDt + "/" + fileNo + "_" + mmsType.toUpperCase() + "/");
//            if (!directory.exists()) {
//                directory.mkdirs();
//            }
//            String zipFolderName = "MMS-CREATE-" + prelimiaryData[2].toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
//                    + ymdOne.format(ymd.parse(fileUpDt)) + "-" + ParseFileUtil.addTrailingZeros(fileNo, 6) + "-ACCEPT";
//            File zipDirectory = new File(directory + "/" + zipFolderName);
//            if (!zipDirectory.exists()) {
//                zipDirectory.mkdirs();
//            }
//
//            List dataList = em.createNativeQuery("select MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,"
//                    + "InstgAgt_FinInstnId_ClrSysMmbId_MmbId,InstgAgt_FinInstnId_Nm,InstdAgt_FinInstnId_ClrSysMmbId_MmbId, "
//                    + "InstdAgt_FinInstnId_Nm,MndtReqId,Accept,Reject_code,MndtId from "
//                    + "mms_upload_xml_detail where mandate_type='" + mmsType + "' and upload_date='" + fileUpDt + "' and "
//                    + "file_no=" + fileNo + " and mandate_status='V'").getResultList();
//            if (dataList.isEmpty()) {
//                throw new Exception("There is no data to generate the acknowledgement.");
//            }
//
//            for (int i = 0; i < dataList.size(); i++) {
//                Vector elem = (Vector) dataList.get(i);
//
//                com.cbs.pojo.mms.pain012.Document document = new com.cbs.pojo.mms.pain012.Document();
//                com.cbs.pojo.mms.pain012.MandateAcceptanceReportV01 marv01 = new com.cbs.pojo.mms.pain012.MandateAcceptanceReportV01();
//                com.cbs.pojo.mms.pain012.GroupHeader31 gh31 = new com.cbs.pojo.mms.pain012.GroupHeader31();
//                com.cbs.pojo.mms.pain012.MandateAcceptance1 ma1 = new com.cbs.pojo.mms.pain012.MandateAcceptance1();
//
//                //GroupHeader
//                gh31.setMsgId(elem.get(0).toString());
//                gh31.setCreDtTm(DatatypeFactory.newInstance().newXMLGregorianCalendar(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date())));
//                //InstgAgt
//                com.cbs.pojo.mms.pain012.BranchAndFinancialInstitutionIdentification4 bfi = new com.cbs.pojo.mms.pain012.BranchAndFinancialInstitutionIdentification4();
//                com.cbs.pojo.mms.pain012.FinancialInstitutionIdentification7 fii = new com.cbs.pojo.mms.pain012.FinancialInstitutionIdentification7();
//                com.cbs.pojo.mms.pain012.ClearingSystemMemberIdentification2 csmi = new com.cbs.pojo.mms.pain012.ClearingSystemMemberIdentification2();
//                csmi.setMmbId(elem.get(5).toString().trim());
//                fii.setClrSysMmbId(csmi);
//                fii.setNm(elem.get(6).toString().trim());
//                bfi.setFinInstnId(fii);
//                gh31.setInstgAgt(bfi);
//                //InstdAgt
//                com.cbs.pojo.mms.pain012.BranchAndFinancialInstitutionIdentification4 bfii = new com.cbs.pojo.mms.pain012.BranchAndFinancialInstitutionIdentification4();
//                com.cbs.pojo.mms.pain012.FinancialInstitutionIdentification7 fiii = new com.cbs.pojo.mms.pain012.FinancialInstitutionIdentification7();
//                com.cbs.pojo.mms.pain012.ClearingSystemMemberIdentification2 csmii = new com.cbs.pojo.mms.pain012.ClearingSystemMemberIdentification2();
//                csmii.setMmbId(elem.get(3).toString().trim());
//                fiii.setClrSysMmbId(csmii);
//                fiii.setNm(elem.get(4).toString().trim());
//                bfii.setFinInstnId(fiii);
//                gh31.setInstdAgt(bfii);
//
//                marv01.setGrpHdr(gh31);
//                //UndrlygAccptncDtls-OrgnlMsgInf
//                com.cbs.pojo.mms.pain012.OriginalMessageInformation1 omi1 = new com.cbs.pojo.mms.pain012.OriginalMessageInformation1();
//                omi1.setMsgId(elem.get(7).toString().trim());
//                omi1.setMsgNmId("pain.009.001.01");
//                ma1.setOrgnlMsgInf(omi1);
//                //UndrlygAccptncDtls-AccptncRslt
//                com.cbs.pojo.mms.pain012.AcceptanceResult6 acr6 = new com.cbs.pojo.mms.pain012.AcceptanceResult6();
//                acr6.setAccptd(elem.get(8).toString().trim().equals("A") ? true : false);
//                com.cbs.pojo.mms.pain012.MandateReason1Choice mrc1 = new com.cbs.pojo.mms.pain012.MandateReason1Choice();
//                mrc1.setPrtry(elem.get(9).toString().trim().equals("") ? "AC01" : elem.get(9).toString().trim());
//                acr6.setRjctRsn(mrc1);
//                ma1.setAccptncRslt(acr6);
//                //UndrlygAccptncDtls-OrgnlMndt
//                com.cbs.pojo.mms.pain012.OriginalMandate1Choice ormc1 = new com.cbs.pojo.mms.pain012.OriginalMandate1Choice();
//                ormc1.setOrgnlMndtId(elem.get(10).toString().trim());
//                ma1.setOrgnlMndt(ormc1);
//
//                marv01.setUndrlygAccptncDtls(ma1);
//                document.setMndtAccptncRpt(marv01);
//
//                //Writing data xml file
//                String acceptFileName = "MMS-ACCEPT-" + prelimiaryData[2].toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
//                        + ymdOne.format(ymd.parse(fileUpDt)) + "-" + ParseFileUtil.addTrailingZeros(String.valueOf(i + 1), 6) + "-INP.xml";
//                File generatedXmlFile = new File(zipDirectory + "/" + acceptFileName);
//                JAXBContext jaxbContext = JAXBContext.newInstance(com.cbs.pojo.mms.pain012.Document.class);
//                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//                // output pretty printed
//                jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
//                jaxbMarshaller.setProperty("com.sun.xml.bind.xmlHeaders", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//                jaxbMarshaller.marshal(document, generatedXmlFile);
//            }
//            //Now zipping and deletion of folder
//            ckycrCommonMgmtRemote.zipFolder(zipDirectory + "/", directory + "/" + zipFolderName + ".zip");
//            File zipFolderToDelete = new File(zipDirectory + "/");
//            if (zipFolderToDelete.exists()) {
//                ckycrCommonMgmtRemote.delete(zipFolderToDelete);
//            }
//            //Logging of generated zip file.
//            String seqNo = "";
//            list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
//                    + "where file_gen_date='" + fileUpDt + "' and file_gen_type='" + mmsType + "'").getResultList();
//            Vector ele = (Vector) list.get(0);
//            seqNo = "1";
//            if (ele.get(0) != null) {
//                seqNo = ele.get(0).toString().trim();  //Make to 5 digit.
//            }
//            int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
//                    + "file_gen_time,file_gen_brncode,file_gen_type,seq_no) values(" + Integer.parseInt(seqNo) + ",'"
//                    + fileUpDt + "','" + zipFolderName + ".zip" + "','" + userName + "',now(),'"
//                    + orgnBrCode + "','" + mmsType + "'," + Double.parseDouble(fileNo) + ")").executeUpdate();
//            if (n <= 0) {
//                throw new ApplicationException("Problem In MMS create return generation.");
//            }
//            ut.commit();
//        } catch (Exception ex) {
//            try {
//                ut.rollback();
//                throw new ApplicationException(ex.getMessage());
//            } catch (Exception e) {
//                throw new ApplicationException(e.getMessage());
//            }
//        }
//        return "true";
//    }
    public String generateEsignMmsReturn(String mmsType, String fileUpDt, String fileNo,
            String todayDt, String userName, String orgnBrCode, String processingMode, String H2HLocation, String mandateMode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String npciUserName ="";
            String zipFolderName;
            File directory;

            List list = em.createNativeQuery("select MndtReqId from mms_upload_xml_detail where "
                    + "mandate_type='" + mmsType + "' and upload_date='" + fileUpDt + "' and "
                    + "file_no=" + fileNo + " and mandate_status='' and Mandate_Mode='" + mandateMode + "'").getResultList();
            if (!list.isEmpty()) {
                throw new ApplicationException("Please verify all entries for mandate Type-->" + mmsType + "Date-->"
                        + dmy.format(ymd.parse(fileUpDt)) + "File No-->" + fileNo);
            }
//            String[] prelimiaryData = getNpciPrelimiaryData();
//            File directory = new File(prelimiaryData[1] + fileUpDt + "/" + fileNo + "_" + mmsType.toUpperCase() + "/");
//            if (!directory.exists()) {
//                directory.mkdirs(); //Where zip file will generate
//            }
            
            //String npciUserName = getNpciUserName(userName);
            String[] prelimiaryData = getNpciPrelimiaryData();
            if (processingMode.equalsIgnoreCase("H2H")) {
                List H2huserList = em.createNativeQuery("select name,code from cbs_parameterinfo where name='NPCI-H2H-USER'").getResultList();
                Vector v = (Vector) H2huserList.get(0);
                if (H2huserList.isEmpty() || v.get(1) == null || v.get(1).toString().equalsIgnoreCase("")) {
                    throw new ApplicationException("H2H User can not be blank.");
                }
                npciUserName = v.get(1).toString();

                directory = new File(H2HLocation + "/");
            } else {
                npciUserName = getNpciUserName(userName);

                directory = new File(prelimiaryData[1] + fileUpDt + "/" + fileNo + "_" + mmsType.toUpperCase() + "/");
                if (!directory.exists()) {
                    directory.mkdirs(); //Where zip file will generate
                }
            }
            
            List dataList = em.createNativeQuery("select MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,"
                    + "InstgAgt_FinInstnId_ClrSysMmbId_MmbId,InstgAgt_FinInstnId_Nm,InstdAgt_FinInstnId_ClrSysMmbId_MmbId, "
                    + "InstdAgt_FinInstnId_Nm,MndtReqId,Accept,Reject_code,MndtId from "
                    + "mms_upload_xml_detail where mandate_type='" + mmsType + "' and upload_date='" + fileUpDt + "' and "
                    + "file_no=" + fileNo + " and mandate_status='V'").getResultList();
            if (dataList.isEmpty()) {
                throw new Exception("There is no data to generate the acknowledgement.");
            }
            //Minimum file sequence
            int seqNo = 1, zipFolderSeq = 0;
            list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
                    + "where file_gen_date='" + fileUpDt + "' and file_gen_type='" + mmsType + "'").getResultList();
            Vector ele = (Vector) list.get(0);
            if (ele.get(0) != null) {
                seqNo = Integer.parseInt(ele.get(0).toString().trim());  //Make to 5 digit.
            }
            zipFolderSeq = (seqNo == 1) ? dataList.size() : (seqNo + (dataList.size() - 1));
            // username changes 
             File zipDirectory = null;
            if (processingMode.equalsIgnoreCase("H2H")) {
                zipFolderName = "MMS-" + mmsType.toUpperCase() + "-" + prelimiaryData[2].toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
                        + ymdOne.format(new Date()) + "-" + ParseFileUtil.addTrailingZeros(String.valueOf(zipFolderSeq), 6) + "-ACCEPT"; //Zip Folder Name
            } else {
                zipFolderName = "MMS-" + mmsType.toUpperCase() + "-" + prelimiaryData[2].toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
                        + ymdOne.format(ymd.parse(fileUpDt)) + "-" + ParseFileUtil.addTrailingZeros(String.valueOf(zipFolderSeq), 6) + "-ACCEPT"; //Zip Folder Name
            }
            zipDirectory = new File(directory + "/" + zipFolderName);
           
//            if (processingMode.equalsIgnoreCase("H2H")) {
//                zipDirectory = new File(H2HLocation + "/" + zipFolderName);
//            } else {
//                zipDirectory = new File(directory + "/" + zipFolderName);
//            }
            if (!zipDirectory.exists()) {
                zipDirectory.mkdirs();
            }
            String acceptFileName = "";
            for (int i = 0; i < dataList.size(); i++) {
                seqNo = (i + seqNo);
                Vector elem = (Vector) dataList.get(i);
                com.cbs.pojo.mms.esign.pain012.Document document = new com.cbs.pojo.mms.esign.pain012.Document();
                com.cbs.pojo.mms.esign.pain012.MandateAcceptanceReportV04 marv01 = new com.cbs.pojo.mms.esign.pain012.MandateAcceptanceReportV04();
                com.cbs.pojo.mms.esign.pain012.GroupHeader47 gh47 = new com.cbs.pojo.mms.esign.pain012.GroupHeader47();
                List<com.cbs.pojo.mms.esign.pain012.MandateAcceptance4> undrlygAccptncDtls = marv01.getUndrlygAccptncDtls();
                com.cbs.pojo.mms.esign.pain012.MandateAcceptance4 ma4 = new com.cbs.pojo.mms.esign.pain012.MandateAcceptance4();
                //GroupHeader
                gh47.setMsgId(elem.get(0).toString());
                gh47.setCreDtTm(DatatypeFactory.newInstance().newXMLGregorianCalendar(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date())));
                //InstgAgt
                com.cbs.pojo.mms.esign.pain012.BranchAndFinancialInstitutionIdentification5 bfi5 = new com.cbs.pojo.mms.esign.pain012.BranchAndFinancialInstitutionIdentification5();
                com.cbs.pojo.mms.esign.pain012.FinancialInstitutionIdentification8 fii8 = new com.cbs.pojo.mms.esign.pain012.FinancialInstitutionIdentification8();
                com.cbs.pojo.mms.esign.pain012.ClearingSystemMemberIdentification2 csmi8 = new ClearingSystemMemberIdentification2();
                csmi8.setMmbId(elem.get(5).toString().trim());
                fii8.setClrSysMmbId(csmi8);
                fii8.setNm(elem.get(6).toString().trim().equals("") ? elem.get(5).toString().trim() : elem.get(6).toString().trim());
                bfi5.setFinInstnId(fii8);
                gh47.setInstgAgt(bfi5);
                //InstdAgt
                com.cbs.pojo.mms.esign.pain012.BranchAndFinancialInstitutionIdentification5 bfii = new com.cbs.pojo.mms.esign.pain012.BranchAndFinancialInstitutionIdentification5();
                com.cbs.pojo.mms.esign.pain012.FinancialInstitutionIdentification8 fiii = new com.cbs.pojo.mms.esign.pain012.FinancialInstitutionIdentification8();
                com.cbs.pojo.mms.esign.pain012.ClearingSystemMemberIdentification2 csmii = new com.cbs.pojo.mms.esign.pain012.ClearingSystemMemberIdentification2();
                csmii.setMmbId(elem.get(3).toString().trim());
                fiii.setClrSysMmbId(csmii);
                fiii.setNm(elem.get(4).toString().trim().equals("") ? elem.get(3).toString().trim() : elem.get(4).toString().trim());
                bfii.setFinInstnId(fiii);
                gh47.setInstdAgt(bfii);

                marv01.setGrpHdr(gh47);
                //UndrlygAccptncDtls-OrgnlMsgInf
                com.cbs.pojo.mms.esign.pain012.OriginalMessageInformation1 orgm = new com.cbs.pojo.mms.esign.pain012.OriginalMessageInformation1();
                orgm.setMsgId(elem.get(7).toString());

                if (mmsType.equalsIgnoreCase("CREATE")) {
                    orgm.setMsgNmId("pain.009.001.04");
                } else if (mmsType.equalsIgnoreCase("AMEND")) {
                    orgm.setMsgNmId("pain.010.001.04");
                }
                ma4.setOrgnlMsgInf(orgm);
                com.cbs.pojo.mms.esign.pain012.AcceptanceResult6 acr6 = new com.cbs.pojo.mms.esign.pain012.AcceptanceResult6();
                acr6.setAccptd(elem.get(8).toString().trim().equals("A") ? true : false);
                com.cbs.pojo.mms.esign.pain012.MandateReason1Choice mrc1 = new com.cbs.pojo.mms.esign.pain012.MandateReason1Choice();
                mrc1.setPrtry(elem.get(9).toString().trim().equals("") ? "ac01" : elem.get(9).toString().trim());
                acr6.setRjctRsn(mrc1);
                ma4.setAccptncRslt(acr6);

                com.cbs.pojo.mms.esign.pain012.OriginalMandate3Choice ormc3 = new com.cbs.pojo.mms.esign.pain012.OriginalMandate3Choice();
                ormc3.setOrgnlMndtId(elem.get(10).toString().trim());
                ma4.setOrgnlMndt(ormc3);
                undrlygAccptncDtls.add(ma4);

                document.setMndtAccptncRpt(marv01);

                //Writing data xml file
                if (processingMode.equalsIgnoreCase("H2H")) {
                    acceptFileName = "MMS-ACCEPT-" + prelimiaryData[2].toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
                            + ymdOne.format(new Date()) + "-" + ParseFileUtil.addTrailingZeros(String.valueOf(seqNo), 6) + "-INP.xml";  //i+1
                } else {
                    acceptFileName = "MMS-ACCEPT-" + prelimiaryData[2].toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
                            + ymdOne.format(ymd.parse(fileUpDt)) + "-" + ParseFileUtil.addTrailingZeros(String.valueOf(seqNo), 6) + "-INP.xml";  //i+1
                }

                File generatedXmlFile = new File(zipDirectory + "/" + acceptFileName);
                JAXBContext jaxbContext = JAXBContext.newInstance(com.cbs.pojo.mms.esign.pain012.Document.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                // output pretty printed
                jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
                jaxbMarshaller.setProperty("com.sun.xml.bind.xmlHeaders", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                jaxbMarshaller.marshal(document, generatedXmlFile);

                if (processingMode.equalsIgnoreCase("H2H")) {
                    int s = em.createNativeQuery(" update mms_upload_xml_detail set FILE_GEN_FLAG = 'Y' where MndtId ='" + ormc3.getOrgnlMndtId().trim() + "'").executeUpdate();
                    if (s <= 0) {
                        throw new ApplicationException("problem In File-gen-Flag updation. ");
                    }
                }
            }
            //Now zipping and deletion of folder
//            if (!processingMode.equalsIgnoreCase("H2H")) {
//                ckycrCommonMgmtRemote.addToZipWithOutFolder(zipDirectory + "/", directory + "/", zipFolderName + ".zip");
//                File zipFolderToDelete = new File(zipDirectory + "/");
//                if (zipFolderToDelete.exists()) {
//                    ckycrCommonMgmtRemote.delete(zipFolderToDelete);
//                }
//            }
            
            ckycrCommonMgmtRemote.addToZipWithOutFolder(zipDirectory + "/", directory + "/", zipFolderName + ".zip");
            File zipFolderToDelete = new File(zipDirectory + "/");
            if (zipFolderToDelete.exists()) {
                ckycrCommonMgmtRemote.delete(zipFolderToDelete);
            }
            //Logging of generated zip file.
            int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                    + "file_gen_time,file_gen_brncode,file_gen_type,seq_no) values(" + seqNo + ",'"
                    + fileUpDt + "','" + zipFolderName + ".zip" + "','" + userName + "',now(),'"
                    + orgnBrCode + "','" + mmsType + "'," + Double.parseDouble(fileNo) + ")").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In MMS create return generation.");
            }

            if (processingMode.equalsIgnoreCase("H2H")) {
                h2hNpciRemote.writeEncryptedFiles();
                h2hNpciRemote.upload(props.getProperty("npciSftpHost").trim(), props.getProperty("npciSftpUser").trim(),
                        props.getProperty("npciSftpPassword").trim(), props.getProperty("cbs.ow.encrypted.location").trim(),
                        props.getProperty("npciSftpFileUploadLocation").trim());

                h2hNpciRemote.createBackupAndThenRemoveFile(props.getProperty("cbs.ow.location").trim(), props.getProperty("cbs.ow.bkp.location").trim());
                h2hNpciRemote.createBackupAndThenRemoveFile(props.getProperty("cbs.ow.encrypted.location").trim(), props.getProperty("cbs.ow.bkp.encrypted.location").trim());

                em.createNativeQuery("insert into cbs_npci_h2h_file_detail(file_date,file_name,file_type) "
                        + "values('" + ymd.format(new Date()) + "','" + acceptFileName + "','OW')").executeUpdate();
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

    @Override
    public String generateMmsCreateReturn(String mmsType, String fileUpDt, String fileNo,
            String todayDt, String userName, String orgnBrCode, String processingMode, String H2HLocation,
            String mandateMode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            String zipFolderName, npciUserName = "";
            File directory;
            ut.begin();
            //All entries should be verified
            List list = em.createNativeQuery("select MndtReqId from mms_upload_xml_detail where "
                    + "mandate_type='" + mmsType + "' and upload_date='" + fileUpDt + "' and "
                    + "file_no=" + fileNo + " and mandate_status='' and mandate_mode ='" + mandateMode + "'").getResultList();
            if (!list.isEmpty()) {
                throw new ApplicationException("Please verify all entries for Mandate Type-->" + mmsType + ":Date-->"
                        + dmy.format(ymd.parse(fileUpDt)) + ":File No-->" + fileNo);
            }

            //String npciUserName = getNpciUserName(userName);
            String[] prelimiaryData = getNpciPrelimiaryData();
            if (processingMode.equalsIgnoreCase("H2H")) {
                List H2huserList = em.createNativeQuery("select name,code from cbs_parameterinfo where name='NPCI-H2H-USER'").getResultList();
                Vector v = (Vector) H2huserList.get(0);
                if (H2huserList.isEmpty() || v.get(1) == null || v.get(1).toString().equalsIgnoreCase("")) {
                    throw new ApplicationException("H2H User can not be blank.");
                }
                npciUserName = v.get(1).toString();

                directory = new File(H2HLocation + "/");
            } else {
                npciUserName = getNpciUserName(userName);

                directory = new File(prelimiaryData[1] + fileUpDt + "/" + fileNo + "_" + mmsType.toUpperCase() + "/");
                if (!directory.exists()) {
                    directory.mkdirs(); //Where zip file will generate
                }
            }


//            File directory = new File(prelimiaryData[1] + fileUpDt + "/" + fileNo + "_" + mmsType.toUpperCase() + "/");
//            if (!directory.exists()) {
//                directory.mkdirs(); //Where zip file will generate
//            }
            List dataList = em.createNativeQuery("select MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,"
                    + "InstgAgt_FinInstnId_ClrSysMmbId_MmbId,InstgAgt_FinInstnId_Nm,InstdAgt_FinInstnId_ClrSysMmbId_MmbId, "
                    + "InstdAgt_FinInstnId_Nm,MndtReqId,Accept,Reject_code,MndtId from "
                    + "mms_upload_xml_detail where mandate_type='" + mmsType + "' and upload_date='" + fileUpDt + "' and "
                    + "file_no=" + fileNo + " and mandate_status='V'").getResultList();
            if (dataList.isEmpty()) {
                throw new Exception("There is no data to generate the acknowledgement.");
            }
            //Minimum file sequence
            int seqNo = 1, zipFolderSeq = 0;
            list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
                    + "where file_gen_date='" + fileUpDt + "' and file_gen_type='" + mmsType + "'").getResultList();
            Vector ele = (Vector) list.get(0);
            if (ele.get(0) != null) {
                seqNo = Integer.parseInt(ele.get(0).toString().trim());  //Make to 5 digit.
            }
            zipFolderSeq = (seqNo == 1) ? dataList.size() : (seqNo + (dataList.size() - 1));

            File zipDirectory;
            if (processingMode.equalsIgnoreCase("H2H")) {
                zipFolderName = "MMS-" + mmsType.toUpperCase() + "-" + prelimiaryData[2].toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
                        + ymdOne.format(new Date()) + "-" + ParseFileUtil.addTrailingZeros(String.valueOf(zipFolderSeq), 6) + "-ACCEPT"; //Zip Folder Name
            } else {
                zipFolderName = "MMS-" + mmsType.toUpperCase() + "-" + prelimiaryData[2].toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
                        + ymdOne.format(ymd.parse(fileUpDt)) + "-" + ParseFileUtil.addTrailingZeros(String.valueOf(zipFolderSeq), 6) + "-ACCEPT"; //Zip Folder Name
            }
            zipDirectory = new File(directory + "/" + zipFolderName);

//            if (processingMode.equalsIgnoreCase("H2H")) {
//                zipDirectory = new File(H2HLocation + "/" + zipFolderName);
//            } else {
//                zipDirectory = new File(directory + "/" + zipFolderName);
//            }
            if (!zipDirectory.exists()) {
                zipDirectory.mkdirs(); //Where source files are and these to be added in zip file
            }
            String acceptFileName = "";
            for (int i = 0; i < dataList.size(); i++) {
                seqNo = (i + seqNo);
                Vector elem = (Vector) dataList.get(i);

                com.cbs.pojo.mms.pain012.Document document = new com.cbs.pojo.mms.pain012.Document();
                com.cbs.pojo.mms.pain012.MandateAcceptanceReportV01 marv01 = new com.cbs.pojo.mms.pain012.MandateAcceptanceReportV01();
                com.cbs.pojo.mms.pain012.GroupHeader31 gh31 = new com.cbs.pojo.mms.pain012.GroupHeader31();
                com.cbs.pojo.mms.pain012.MandateAcceptance1 ma1 = new com.cbs.pojo.mms.pain012.MandateAcceptance1();

                //GroupHeader
                gh31.setMsgId(elem.get(0).toString());
                gh31.setCreDtTm(DatatypeFactory.newInstance().newXMLGregorianCalendar(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date())));
                //InstgAgt
                com.cbs.pojo.mms.pain012.BranchAndFinancialInstitutionIdentification4 bfi = new com.cbs.pojo.mms.pain012.BranchAndFinancialInstitutionIdentification4();
                com.cbs.pojo.mms.pain012.FinancialInstitutionIdentification7 fii = new com.cbs.pojo.mms.pain012.FinancialInstitutionIdentification7();
                com.cbs.pojo.mms.pain012.ClearingSystemMemberIdentification2 csmi = new com.cbs.pojo.mms.pain012.ClearingSystemMemberIdentification2();
                csmi.setMmbId(elem.get(5).toString().trim());
                fii.setClrSysMmbId(csmi);
                fii.setNm(elem.get(6).toString().trim().equals("") ? elem.get(5).toString().trim() : elem.get(6).toString().trim());
                bfi.setFinInstnId(fii);
                gh31.setInstgAgt(bfi);
                //InstdAgt
                com.cbs.pojo.mms.pain012.BranchAndFinancialInstitutionIdentification4 bfii = new com.cbs.pojo.mms.pain012.BranchAndFinancialInstitutionIdentification4();
                com.cbs.pojo.mms.pain012.FinancialInstitutionIdentification7 fiii = new com.cbs.pojo.mms.pain012.FinancialInstitutionIdentification7();
                com.cbs.pojo.mms.pain012.ClearingSystemMemberIdentification2 csmii = new com.cbs.pojo.mms.pain012.ClearingSystemMemberIdentification2();
                csmii.setMmbId(elem.get(3).toString().trim());
                fiii.setClrSysMmbId(csmii);
                fiii.setNm(elem.get(4).toString().trim().equals("") ? elem.get(3).toString().trim() : elem.get(4).toString().trim());
                bfii.setFinInstnId(fiii);
                gh31.setInstdAgt(bfii);

                marv01.setGrpHdr(gh31);
                //UndrlygAccptncDtls-OrgnlMsgInf
                com.cbs.pojo.mms.pain012.OriginalMessageInformation1 omi1 = new com.cbs.pojo.mms.pain012.OriginalMessageInformation1();
                omi1.setMsgId(elem.get(7).toString().trim());
//                omi1.setMsgNmId("pain.009.001.01");
                if (mmsType.equalsIgnoreCase("CREATE")) {
                    omi1.setMsgNmId("pain.009.001.01");
                } else if (mmsType.equalsIgnoreCase("AMEND")) {
                    omi1.setMsgNmId("pain.010.001.01");
                }

                ma1.setOrgnlMsgInf(omi1);
                //UndrlygAccptncDtls-AccptncRslt
                com.cbs.pojo.mms.pain012.AcceptanceResult6 acr6 = new com.cbs.pojo.mms.pain012.AcceptanceResult6();
                acr6.setAccptd(elem.get(8).toString().trim().equals("A") ? true : false);
                com.cbs.pojo.mms.pain012.MandateReason1Choice mrc1 = new com.cbs.pojo.mms.pain012.MandateReason1Choice();
                mrc1.setPrtry(elem.get(9).toString().trim().equals("") ? "ac01" : elem.get(9).toString().trim());
                acr6.setRjctRsn(mrc1);
                ma1.setAccptncRslt(acr6);
                //UndrlygAccptncDtls-OrgnlMndt
                com.cbs.pojo.mms.pain012.OriginalMandate1Choice ormc1 = new com.cbs.pojo.mms.pain012.OriginalMandate1Choice();
                ormc1.setOrgnlMndtId(elem.get(10).toString().trim());
                ma1.setOrgnlMndt(ormc1);

                marv01.setUndrlygAccptncDtls(ma1);
                document.setMndtAccptncRpt(marv01);

                //Writing data xml file
                if (processingMode.equalsIgnoreCase("H2H")) {
                    acceptFileName = "MMS-ACCEPT-" + prelimiaryData[2].toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
                            + ymdOne.format(new Date()) + "-" + ParseFileUtil.addTrailingZeros(String.valueOf(seqNo), 6) + "-INP.xml";  //i+1
                } else {
                    acceptFileName = "MMS-ACCEPT-" + prelimiaryData[2].toUpperCase() + "-" + npciUserName.toUpperCase() + "-"
                            + ymdOne.format(ymd.parse(fileUpDt)) + "-" + ParseFileUtil.addTrailingZeros(String.valueOf(seqNo), 6) + "-INP.xml";
                }
                File generatedXmlFile = new File(zipDirectory + "/" + acceptFileName);
                JAXBContext jaxbContext = JAXBContext.newInstance(com.cbs.pojo.mms.pain012.Document.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                // output pretty printed

                jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
                jaxbMarshaller.setProperty("com.sun.xml.bind.xmlHeaders", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                jaxbMarshaller.marshal(document, generatedXmlFile);

                if (processingMode.equalsIgnoreCase("H2H")) {
                    int s = em.createNativeQuery(" update mms_upload_xml_detail set FILE_GEN_FLAG = 'Y' where MndtId ='" + ormc1.getOrgnlMndtId().trim() + "'").executeUpdate();
                    if (s <= 0) {
                        throw new ApplicationException("problem In File-gen-Flag updation. ");
                    }
                }
            }
            //Now zipping and deletion of folder
//            if (!processingMode.equalsIgnoreCase("H2H")) {
//                ckycrCommonMgmtRemote.addToZipWithOutFolder(zipDirectory + "/", directory + "/", zipFolderName + ".zip");
//                File zipFolderToDelete = new File(zipDirectory + "/");
//                if (zipFolderToDelete.exists()) {
//                    ckycrCommonMgmtRemote.delete(zipFolderToDelete);
//                }
//            }

            ckycrCommonMgmtRemote.addToZipWithOutFolder(zipDirectory + "/", directory + "/", zipFolderName + ".zip");
            File zipFolderToDelete = new File(zipDirectory + "/");
            if (zipFolderToDelete.exists()) {
                ckycrCommonMgmtRemote.delete(zipFolderToDelete);
            }

            //Logging of generated zip file.
            int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                    + "file_gen_time,file_gen_brncode,file_gen_type,seq_no) values(" + seqNo + ",'"
                    + fileUpDt + "','" + zipFolderName + ".zip" + "','" + userName + "',now(),'"
                    + orgnBrCode + "','" + mmsType + "'," + Double.parseDouble(fileNo) + ")").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In MMS create return generation.");
            }

            if (processingMode.equalsIgnoreCase("H2H")) {
                h2hNpciRemote.writeEncryptedFiles();
                h2hNpciRemote.upload(props.getProperty("npciSftpHost").trim(), props.getProperty("npciSftpUser").trim(),
                        props.getProperty("npciSftpPassword").trim(), props.getProperty("cbs.ow.encrypted.location").trim(),
                        props.getProperty("npciSftpFileUploadLocation").trim());

                h2hNpciRemote.createBackupAndThenRemoveFile(props.getProperty("cbs.ow.location").trim(), props.getProperty("cbs.ow.bkp.location").trim());
                h2hNpciRemote.createBackupAndThenRemoveFile(props.getProperty("cbs.ow.encrypted.location").trim(), props.getProperty("cbs.ow.bkp.encrypted.location").trim());

                em.createNativeQuery("insert into cbs_npci_h2h_file_detail(file_date,file_name,file_type) "
                        + "values('" + ymd.format(new Date()) + "','" + acceptFileName + "','OW')").executeUpdate();
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

    public List<MmsReportPojo> getMmsReport(String selectedBr, String manDateMode, String mandateType, String reportType,
            String frDt, String toDt) throws ApplicationException {
        List<MmsReportPojo> dataList = new ArrayList<MmsReportPojo>();
        String query;
        try {
            if (reportType.equalsIgnoreCase("AL")) {
                query = "select MndtId,dbtrAcct_Tp_Prtry,dbtrAcct_Id_Othr_Id,dbtr_Nm,ocrncs_Frqcy,ocrncs_FrstColltnDt,"
                        + "ocrncs_FnlColltnDt,colltnAmt,maxAmt,dbtrAgt_FinInstnId_ClrSysMmbId_MmbId,dbtrAgt_FinInstnId_Nm,"
                        + "Accept,reject_code,date_format(upload_date,'%d/%m/%Y'),zip_file_name from mms_upload_xml_detail "
                        + "where Mandate_Mode = '" + manDateMode + "' and mandate_type='" + mandateType + "' and upload_date "
                        + "between '" + frDt + "' and '" + toDt + "' and mandate_status='V'";
            } else {
                query = "select MndtId,dbtrAcct_Tp_Prtry,dbtrAcct_Id_Othr_Id,dbtr_Nm,ocrncs_Frqcy,ocrncs_FrstColltnDt,"
                        + "ocrncs_FnlColltnDt,colltnAmt,maxAmt,dbtrAgt_FinInstnId_ClrSysMmbId_MmbId,dbtrAgt_FinInstnId_Nm,"
                        + "Accept,reject_code,date_format(upload_date,'%d/%m/%Y'),zip_file_name from mms_upload_xml_detail "
                        + "where Mandate_Mode = '" + manDateMode + "' and mandate_type='" + mandateType + "' and accept='" + reportType + "' and upload_date "
                        + "between '" + frDt + "' and '" + toDt + "' and mandate_status='V'";
            }
            String alphaCode = commonReport.getAlphacodeByBrncode(selectedBr);

            List list = new ArrayList();
            if (alphaCode.equalsIgnoreCase("HO")) {
                list = em.createNativeQuery(query + "order by upload_date, accept").getResultList();
            } else {
                String[] arr = otherNpciMgmtFacadeRemote.getIfscAndMicrCodeByBrnCode(selectedBr);
                String ifscCode = arr[0];
                String micrNo = arr[1];

                list = em.createNativeQuery(query + " and (DbtrAgt_FinInstnId_ClrSysMmbId_MmbId='" + ifscCode + "' or "
                        + "DbtrAgt_FinInstnId_ClrSysMmbId_MmbId='" + micrNo + "') order by upload_date, accept").getResultList();
            }
            if (list.isEmpty()) {
                throw new ApplicationException("There is no data to show.");
            }
            for (int i = 0; i < list.size(); i++) {
                MmsReportPojo pojo = new MmsReportPojo();
                Vector tempVector = (Vector) list.get(i);
                pojo.setUmrn(tempVector.get(0).toString());
                pojo.setAcType(tempVector.get(1).toString());
                pojo.setAcNo(tempVector.get(2).toString());
                pojo.setAcName(tempVector.get(3).toString());
                pojo.setFrqncy(tempVector.get(4).toString());
                pojo.setFirstCollDt(tempVector.get(5).toString());
                pojo.setFinalCollDt(tempVector.get(6).toString());
                pojo.setCollectionAmt(new BigDecimal(tempVector.get(7).toString()));
                pojo.setMaxAmt(new BigDecimal(tempVector.get(8).toString()));
                pojo.setDebtorIFSC(tempVector.get(9).toString());
                pojo.setDebtorBnkName(tempVector.get(10).toString());
                String status = "";
                if (tempVector.get(11).toString().equalsIgnoreCase("A")) {
                    status = "ACCEPT";
                } else if (tempVector.get(11).toString().equalsIgnoreCase("R")) {
                    status = "REJECT";
                }
                pojo.setStatus(status);
                String mandateDes = "";
                String rejectionCode = tempVector.get(12) == null ? "" : tempVector.get(12).toString();
                if (tempVector.get(11).toString().equalsIgnoreCase("R")) {
                    if (mandateType.equalsIgnoreCase("create")) {
                        mandateDes = commonReport.getRefRecDesc("320", rejectionCode);
                    } else if (mandateType.equalsIgnoreCase("amend")) {
                        mandateDes = commonReport.getRefRecDesc("321", rejectionCode);
                    } else if (mandateType.equalsIgnoreCase("cancel")) {
                        mandateDes = commonReport.getRefRecDesc("322", rejectionCode);
                    }
                }
                pojo.setRejectRsn(mandateDes);
                pojo.setUploadDate(tempVector.get(13).toString());
                pojo.setZipFileName(tempVector.get(14).toString());
                List cbsAcList = getCbsAcnoFromMmsDetail(tempVector.get(0).toString());
                if (cbsAcList.isEmpty()) {
                    pojo.setCbsAcNo("");
                } else {
                    pojo.setCbsAcNo(cbsAcList.get(0).toString());
                }
                dataList.add(pojo);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List getCbsAcnoFromMmsDetail(String umrn) throws ApplicationException {
        try {
            return em.createNativeQuery("select ifnull(Cbs_Acno,'') as newAcNo from mms_detail  where MndtId='" + umrn + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String updateMandateStatus(String umrn, String debtorAccount, String orgnBrCode, String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List list = em.createNativeQuery("select mandate_status from mms_detail where "
                    + "mndtid='" + umrn.trim() + "' and dbtracct_id_othr_id='" + debtorAccount.trim() + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Mandate no and debit a/c no are different for each other.");
            }

            Vector ele = (Vector) list.get(0);
            String mandateStatus = ele.get(0).toString();
            if (mandateStatus.equalsIgnoreCase("S")) {
                throw new ApplicationException("This UMRN is already stopped.");
            }

            if (!orgnBrCode.equalsIgnoreCase(debtorAccount.substring(0, 2))) {
                throw new ApplicationException("You can process only your branch related UMRN.");
            }

            int n = em.createNativeQuery("insert into mms_detail_his(MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,"
                    + "InstgAgt_FinInstnId_ClrSysMmbId_MmbId,InstgAgt_FinInstnId_Nm,InstdAgt_FinInstnId_ClrSysMmbId_MmbId,"
                    + "InstdAgt_FinInstnId_Nm,MndtId,MndtReqId,Tp_SvcLvl_Prtry,Tp_LclInstrm_Prtry,Ocrncs_SeqTp,"
                    + "Ocrncs_Frqcy,Ocrncs_FrstColltnDt,Ocrncs_FnlColltnDt,ColltnAmt,MaxAmt,Cdtr_Nm,CdtrAcct_Id_Othr_Id,"
                    + "CdtrAgt_FinInstnId_ClrSysMmbId_MmbId,CdtrAgt_FinInstnId_Nm,Dbtr_Nm,Dbtr_Id_PrvtId_Othr_Id,"
                    + "Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry,Dbtr_CtctDtls_Nm,Dbtr_CtctDtls_PhneNb,Dbtr_CtctDtls_MobNb,Dbtr_CtctDtls_EmailAdr,"
                    + "Dbtr_CtctDtls_Othr,DbtrAcct_Id_Othr_Id,DbtrAcct_Tp_Prtry,DbtrAgt_FinInstnId_ClrSysMmbId_MmbId,"
                    + "DbtrAgt_FinInstnId_Nm,Mandate_Type,Update_By,Update_Date,Update_Time,Mandate_Status,Stop_Payment_Date,Stop_Payment_By) select MsgId,CreDtTm,"
                    + "InitgPty_Id_OrgId_Othr_Id,InstgAgt_FinInstnId_ClrSysMmbId_MmbId,InstgAgt_FinInstnId_Nm,"
                    + "InstdAgt_FinInstnId_ClrSysMmbId_MmbId,InstdAgt_FinInstnId_Nm,MndtId,MndtReqId,Tp_SvcLvl_Prtry,"
                    + "Tp_LclInstrm_Prtry,Ocrncs_SeqTp,Ocrncs_Frqcy,Ocrncs_FrstColltnDt,Ocrncs_FnlColltnDt,ColltnAmt,"
                    + "MaxAmt,Cdtr_Nm,CdtrAcct_Id_Othr_Id,CdtrAgt_FinInstnId_ClrSysMmbId_MmbId,CdtrAgt_FinInstnId_Nm,"
                    + "Dbtr_Nm,Dbtr_Id_PrvtId_Othr_Id,Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry,Dbtr_CtctDtls_Nm,Dbtr_CtctDtls_PhneNb,"
                    + "Dbtr_CtctDtls_MobNb,Dbtr_CtctDtls_EmailAdr,Dbtr_CtctDtls_Othr,DbtrAcct_Id_Othr_Id,"
                    + "DbtrAcct_Tp_Prtry,DbtrAgt_FinInstnId_ClrSysMmbId_MmbId,DbtrAgt_FinInstnId_Nm,Mandate_Type,"
                    + "Update_By,Update_Date,Update_Time,Mandate_Status,Stop_Payment_Date,Stop_Payment_By from mms_detail where MndtId = '" + umrn + "'").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In Mandate History Creation.");
            }

            n = em.createNativeQuery("update mms_detail set mandate_status='S',Stop_Payment_Date=current_timestamp,"
                    + "Stop_Payment_By='" + userName + "' where mndtid='" + umrn + "'").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem In Mandate Status Updation.");
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

    public List<AutoMandateTo> getStopUmrnDetails(String selectBranch, String orgnBrCode) throws ApplicationException {
        List<AutoMandateTo> dataList = new ArrayList<AutoMandateTo>();
        try {
            List list;

            String query = "SELECT MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,"
                    + "InstgAgt_FinInstnId_ClrSysMmbId_MmbId,InstgAgt_FinInstnId_Nm,InstdAgt_FinInstnId_ClrSysMmbId_MmbId,"
                    + "InstdAgt_FinInstnId_Nm,MndtId,MndtReqId,Tp_SvcLvl_Prtry,Tp_LclInstrm_Prtry,Ocrncs_SeqTp,"
                    + "Ocrncs_Frqcy,Ocrncs_FrstColltnDt,Ocrncs_FnlColltnDt,ColltnAmt,MaxAmt,Cdtr_Nm,CdtrAcct_Id_Othr_Id,"
                    + "CdtrAgt_FinInstnId_ClrSysMmbId_MmbId,CdtrAgt_FinInstnId_Nm,Dbtr_Nm,"
                    + "Dbtr_Id_PrvtId_Othr_Id,Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry,Dbtr_CtctDtls_PhneNb,"
                    + "Dbtr_CtctDtls_MobNb,Dbtr_CtctDtls_EmailAdr,Dbtr_CtctDtls_Othr,DbtrAcct_Id_Othr_Id,"
                    + "DbtrAcct_Tp_Prtry,DbtrAgt_FinInstnId_ClrSysMmbId_MmbId,DbtrAgt_FinInstnId_Nm,Mandate_Type,"
                    + "Update_By,date_format(Stop_Payment_Date,'%d/%m/%Y'),Stop_Payment_By FROM mms_detail where Mandate_Status='S'";

            if (selectBranch.equalsIgnoreCase("ALL")) {
                list = em.createNativeQuery(query).getResultList();
            } else {
                list = em.createNativeQuery(query + " and substring(DbtrAcct_Id_Othr_Id,1,2)='" + selectBranch + "'").getResultList();
            }

            for (int i = 0; i < list.size(); i++) {
                AutoMandateTo to = new AutoMandateTo();
                Vector ele = (Vector) list.get(i);

                to.setMsgId(ele.get(0).toString().trim());
                to.setCreDtTm(ele.get(1).toString().trim());
                to.setInitgPtyIdOrgIdOthrId(ele.get(2).toString().trim());
                to.setInstgAgtFinInstnIdClrSysMmbIdMmbId(ele.get(3).toString().trim());
                to.setInstgAgtFinInstnIdNm(ele.get(4).toString().trim());

                to.setInstdAgtFinInstnIdClrSysMmbIdMmbId(ele.get(5).toString().trim());
                to.setInstdAgtFinInstnIdNm(ele.get(6).toString().trim());
                to.setMndtId(ele.get(7).toString().trim());
                to.setMndtReqId(ele.get(8).toString().trim());
                to.setTpSvcLvlPrtry(ele.get(9).toString().trim());

                to.setTpLclInstrmPrtry(ele.get(10).toString().trim());
                to.setOcrncsSeqTp(ele.get(11).toString().trim());
                to.setOcrncsFrqcy(ele.get(12).toString().trim());
                to.setOcrncsFrstColltnDt(ele.get(13).toString().trim());
                to.setOcrncsFnlColltnDt(ele.get(14).toString().trim());

                to.setColltnAmt(new BigDecimal(ele.get(15).toString().trim()));
                to.setMaxAmt(new BigDecimal(ele.get(16).toString().trim()));
                to.setCdtrNm(ele.get(17).toString().trim());
                to.setCdtrAcctIdOthrId(ele.get(18).toString().trim());
                to.setCdtrAgtFinInstnIdClrSysMmbIdMmbId(ele.get(19).toString().trim());

                to.setCdtrAgtFinInstnIdNm(ele.get(20).toString().trim());
                to.setDbtrNm(ele.get(21).toString().trim());
                to.setDbtrIdPrvtIdOthrId(ele.get(22).toString().trim());
                to.setDbtrIdPrvtIdOthrSchmeNmPrtry(ele.get(23).toString().trim());
                to.setDbtrCtctDtlsPhneNb(ele.get(24).toString().trim());

                to.setDbtrCtctDtlsMobNb(ele.get(25).toString().trim());
                to.setDbtrCtctDtlsEmailAdr(ele.get(26).toString().trim());
                to.setDbtrCtctDtlsOthr(ele.get(27).toString().trim());
                to.setDbtrAcctIdOthrId(ele.get(28).toString().trim());
                to.setDbtrAcctTpPrtry(ele.get(29).toString().trim());

                to.setDbtrAgtFinInstnIdClrSysMmbIdMmbId(ele.get(30).toString().trim());
                to.setDbtrAgtFinInstnIdNm(ele.get(31).toString().trim());
                to.setMandateType(ele.get(32).toString().trim());
                to.setUpdateBy(ele.get(33).toString().trim());
                to.setStopPaymentDate(ele.get(34) == null ? "" : ele.get(34).toString().trim());
                to.setStopPaymentBy(ele.get(35) == null ? "" : ele.get(35).toString().trim());

                dataList.add(to);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List getLegacyMandateFileData(String fileType, String mandateType, String uploadDt) throws ApplicationException {
        List list = new ArrayList();
        try {
            if (fileType.equalsIgnoreCase("V")) { //For Verification File
                list = em.createNativeQuery("select MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,InstgAgt_FinInstnId_ClrSysMmbId_MmbId,"
                        + "InstgAgt_FinInstnId_Nm,InstdAgt_FinInstnId_ClrSysMmbId_MmbId,InstdAgt_FinInstnId_Nm,MndtId,MndtReqId,"
                        + "Tp_SvcLvl_Prtry,Tp_LclInstrm_Prtry,Ocrncs_SeqTp,Ocrncs_Frqcy,Ocrncs_FrstColltnDt,Ocrncs_FnlColltnDt,"
                        + "ColltnAmt,MaxAmt,Cdtr_Nm,CdtrAcct_Id_Othr_Id,CdtrAgt_FinInstnId_ClrSysMmbId_MmbId,CdtrAgt_FinInstnId_Nm,"
                        + "Dbtr_Nm,Dbtr_Id_PrvtId_Othr_Id,Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry,Dbtr_CtctDtls_PhneNb,"
                        + "Dbtr_CtctDtls_MobNb,Dbtr_CtctDtls_EmailAdr,Dbtr_CtctDtls_Othr,DbtrAcct_Id_Othr_Id,DbtrAcct_Tp_Prtry,"
                        + "DbtrAgt_FinInstnId_ClrSysMmbId_MmbId,DbtrAgt_FinInstnId_Nm,Mandate_Type,Mandate_Status,Accept,"
                        + "Reject_Code,Image_Name,Verify_By from mms_upload_xml_detail where Mandate_Mode='L' and "
                        + "Mandate_Status='' and Accept='' and Mandate_Type='" + mandateType + "' and "
                        + "Upload_Date='" + uploadDt + "'").getResultList();
            } else if (fileType.equalsIgnoreCase("R")) { //For Return File
//                list = em.createNativeQuery("select MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,InstgAgt_FinInstnId_ClrSysMmbId_MmbId,"
//                        + "InstgAgt_FinInstnId_Nm,InstdAgt_FinInstnId_ClrSysMmbId_MmbId,InstdAgt_FinInstnId_Nm,MndtId,MndtReqId,"
//                        + "Tp_SvcLvl_Prtry,Tp_LclInstrm_Prtry,Ocrncs_SeqTp,Ocrncs_Frqcy,Ocrncs_FrstColltnDt,Ocrncs_FnlColltnDt,"
//                        + "ColltnAmt,MaxAmt,Cdtr_Nm,CdtrAcct_Id_Othr_Id,CdtrAgt_FinInstnId_ClrSysMmbId_MmbId,"
//                        + "CdtrAgt_FinInstnId_Nm,Dbtr_Nm,Dbtr_Id_PrvtId_Othr_Id,Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry,"
//                        + "Dbtr_CtctDtls_PhneNb,Dbtr_CtctDtls_MobNb,Dbtr_CtctDtls_EmailAdr,Dbtr_CtctDtls_Othr,"
//                        + "DbtrAcct_Id_Othr_Id,DbtrAcct_Tp_Prtry,DbtrAgt_FinInstnId_ClrSysMmbId_MmbId,DbtrAgt_FinInstnId_Nm,"
//                        + "Mandate_Type,Mandate_Status,Accept,Reject_Code,Image_Name,Verify_By from mms_upload_xml_detail "
//                        + "where Mandate_Mode='L' and Mandate_Status='V' and Accept='R' and (Verify_By<>'' && "
//                        + "Verify_By is not null) and Mandate_Type='" + mandateType + "' and "
//                        + "Upload_Date='" + uploadDt + "'").getResultList();

                list = em.createNativeQuery("select MsgId,CreDtTm,InitgPty_Id_OrgId_Othr_Id,InstgAgt_FinInstnId_ClrSysMmbId_MmbId,"
                        + "InstgAgt_FinInstnId_Nm,InstdAgt_FinInstnId_ClrSysMmbId_MmbId,InstdAgt_FinInstnId_Nm,MndtId,MndtReqId,"
                        + "Tp_SvcLvl_Prtry,Tp_LclInstrm_Prtry,Ocrncs_SeqTp,Ocrncs_Frqcy,Ocrncs_FrstColltnDt,Ocrncs_FnlColltnDt,"
                        + "ColltnAmt,MaxAmt,Cdtr_Nm,CdtrAcct_Id_Othr_Id,CdtrAgt_FinInstnId_ClrSysMmbId_MmbId,CdtrAgt_FinInstnId_Nm,"
                        + "Dbtr_Nm,Dbtr_Id_PrvtId_Othr_Id,Dbtr_Id_PrvtId_Othr_SchmeNm_Prtry,Dbtr_CtctDtls_PhneNb,"
                        + "Dbtr_CtctDtls_MobNb,Dbtr_CtctDtls_EmailAdr,Dbtr_CtctDtls_Othr,DbtrAcct_Id_Othr_Id,DbtrAcct_Tp_Prtry,"
                        + "DbtrAgt_FinInstnId_ClrSysMmbId_MmbId,DbtrAgt_FinInstnId_Nm,Mandate_Type,Mandate_Status,Accept,"
                        + "Reject_Code,Image_Name,Verify_By from mms_upload_xml_detail where Mandate_Mode='L' and "
                        + "Mandate_Status='' and Accept='' and Mandate_Type='" + mandateType + "' and "
                        + "Upload_Date='" + uploadDt + "'").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return list;
    }

    @Override
    public String generateMmsInitiationFile(String mmsType, String tillDt,
            String todayDt, String userName, String orgnBrCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String npciUserName = getNpciUserName(userName);
            List dataList = em.createNativeQuery("select trans_type,cbs_umrn,chi_umrn,proprietary,category,"
                    + "amount_flag,amount,frequency,sequence_type,period_type,from_date,to_date,creditor_acno,"
                    + "creditor_name,creditor_actype,creditor_bankname,creditor_ifsc,creditor_mobile,creditor_email,"
                    + "creditor_utility_code,debtor_acno,debtor_name,debtor_actype,debtor_bankname,debtor_ifsc,"
                    + "debtor_mobile,debtor_email,debtor_utility_code,ref_1,ref_2,mandate_receiving_branch,mandate_received_by,"
                    + "date_format(mandate_received_date,'%Y%m%d') from cbs_mandate_detail where flag='N' and "
                    + "date_format(mandate_received_date,'%Y%m%d')<='" + ymd.format(dmy.parse(todayDt)) + "' and "
                    + "trans_type='" + mmsType + "' and txnfiletype='ACH'").getResultList();
            if (dataList.isEmpty()) {
                throw new Exception("There is no data to generate the file.");
            }
            String[] npciPrelimiaryData = getNpciPrelimiaryData();
            String zipFileSeqNo = getMmsInitiationZipFileSeqNo(mmsType);
            String zipFolderName = "MMS-" + mmsType.toUpperCase() + "-" + npciPrelimiaryData[2].toUpperCase() + "-"
                    + npciUserName.toUpperCase() + "-" + ymdOne.format(dmy.parse(todayDt)) + "-" + zipFileSeqNo + "-INP";
            //Logging the zip file generation.
            int n = em.createNativeQuery("insert into cbs_npci_mapper_files(file_no,file_gen_date,file_name,file_gen_by,"
                    + "file_gen_time,file_gen_brncode,file_gen_type) values(" + Integer.parseInt(zipFileSeqNo) + ",'"
                    + ymd.format(dmy.parse(todayDt)) + "','" + zipFolderName + ".zip" + "','" + userName + "',now(),'"
                    + orgnBrCode + "','" + getFileType(mmsType) + "')").executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem in zip file seq no maintainance.");
            }
            //Mandate images folder
            File imageFolder = new File("/opt/mms/mandate-images/");
            if (!imageFolder.exists()) {
                imageFolder.mkdirs();
            }

            File directory = new File(npciPrelimiaryData[1] + "/");
            if (!directory.exists()) {
                directory.mkdirs(); //Where zip file will generate
            }
            File zipFolderDirectory = new File(directory + "/" + zipFolderName);
            if (!zipFolderDirectory.exists()) {
                zipFolderDirectory.mkdirs(); //Where source files are and these to be added in zip file
            }

            for (int i = 0; i < dataList.size(); i++) {
                Vector element = (Vector) dataList.get(i);
                String proprietary = element.get(3).toString().trim();

                com.cbs.pojo.mms.pain009.Document document = new com.cbs.pojo.mms.pain009.Document();
                com.cbs.pojo.mms.pain009.MandateInitiationRequestV01 mndtInitnReq = new com.cbs.pojo.mms.pain009.MandateInitiationRequestV01();
                com.cbs.pojo.mms.pain009.GroupHeader31 grpHdr = new com.cbs.pojo.mms.pain009.GroupHeader31();
                com.cbs.pojo.mms.pain009.MandateInformation2 mndt = new com.cbs.pojo.mms.pain009.MandateInformation2();

                //GrpHdr
                grpHdr.setMsgId(element.get(1).toString().trim());
                grpHdr.setCreDtTm(DatatypeFactory.newInstance().newXMLGregorianCalendar(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date())));
                //InstgAgt
                com.cbs.pojo.mms.pain009.BranchAndFinancialInstitutionIdentification4 bfi = new com.cbs.pojo.mms.pain009.BranchAndFinancialInstitutionIdentification4();
                com.cbs.pojo.mms.pain009.FinancialInstitutionIdentification7 fii = new com.cbs.pojo.mms.pain009.FinancialInstitutionIdentification7();
                com.cbs.pojo.mms.pain009.ClearingSystemMemberIdentification2 csmi = new com.cbs.pojo.mms.pain009.ClearingSystemMemberIdentification2();
                if (proprietary.equalsIgnoreCase("debit")) {
                    csmi.setMmbId(element.get(16).toString().trim());
                } else if (proprietary.equalsIgnoreCase("credit")) {
                    csmi.setMmbId(element.get(24).toString().trim());
                }
                fii.setClrSysMmbId(csmi);
                if (proprietary.equalsIgnoreCase("debit")) {
                    fii.setNm(element.get(15).toString().trim());
                } else if (proprietary.equalsIgnoreCase("credit")) {
                    fii.setNm(element.get(23).toString().trim());
                }
                bfi.setFinInstnId(fii);
                grpHdr.setInstgAgt(bfi);
                //InstdAgt
                bfi = new com.cbs.pojo.mms.pain009.BranchAndFinancialInstitutionIdentification4();
                fii = new com.cbs.pojo.mms.pain009.FinancialInstitutionIdentification7();
                csmi = new com.cbs.pojo.mms.pain009.ClearingSystemMemberIdentification2();
                if (proprietary.equalsIgnoreCase("debit")) {
                    csmi.setMmbId(element.get(24).toString().trim());
                } else if (proprietary.equalsIgnoreCase("credit")) {
                    csmi.setMmbId(element.get(16).toString().trim());
                }
                fii.setClrSysMmbId(csmi);
                if (proprietary.equalsIgnoreCase("debit")) {
                    fii.setNm(element.get(23).toString().trim());
                } else if (proprietary.equalsIgnoreCase("credit")) {
                    fii.setNm(element.get(15).toString().trim());
                }
                bfi.setFinInstnId(fii);
                grpHdr.setInstdAgt(bfi);
                //Mndt
                mndt.setMndtReqId(element.get(1).toString().trim());
                //Mndt-TP
                com.cbs.pojo.mms.pain009.MandateTypeInformation1 mti = new com.cbs.pojo.mms.pain009.MandateTypeInformation1();
                com.cbs.pojo.mms.pain009.ServiceLevel8Choice slc = new com.cbs.pojo.mms.pain009.ServiceLevel8Choice();
                slc.setPrtry(element.get(4).toString().trim());
                mti.setSvcLvl(slc);
                com.cbs.pojo.mms.pain009.LocalInstrument2Choice lic = new com.cbs.pojo.mms.pain009.LocalInstrument2Choice();
                lic.setPrtry(element.get(3).toString().trim());
                mti.setLclInstrm(lic);
                mndt.setTp(mti);
                //Mndt-Ocrncs
                com.cbs.pojo.mms.pain009.MandateOccurrences1 mo = new com.cbs.pojo.mms.pain009.MandateOccurrences1();
                mo.setSeqTp(SequenceType2Code.fromValue(element.get(8).toString().trim()));
                mo.setFrqcy(Frequency1Code.fromValue(element.get(7).toString().trim()));
                String periodType = element.get(9).toString().trim();
                if (periodType.equalsIgnoreCase("B")) {
                    mo.setFrstColltnDt(DatatypeFactory.newInstance().newXMLGregorianCalendar(ymdTwo.format(ymd.parse(element.get(10).toString().trim()))));
                    mo.setFnlColltnDt(DatatypeFactory.newInstance().newXMLGregorianCalendar(ymdTwo.format(ymd.parse(element.get(11).toString().trim()))));
                } else if (periodType.equalsIgnoreCase("C")) {
                    mo.setFrstColltnDt(DatatypeFactory.newInstance().newXMLGregorianCalendar(ymdTwo.format(ymd.parse(element.get(10).toString().trim()))));
                }
                mndt.setOcrncs(mo);

                String amtFlg = element.get(5).toString().trim();
                com.cbs.pojo.mms.pain009.ActiveCurrencyAndAmount acam = new com.cbs.pojo.mms.pain009.ActiveCurrencyAndAmount();
                acam.setCcy("INR");
                acam.setValue(new BigDecimal(element.get(6).toString().trim()));
                if (amtFlg.equalsIgnoreCase("F")) {
                    mndt.setColltnAmt(acam);
                } else if (amtFlg.equalsIgnoreCase("M")) {
                    mndt.setMaxAmt(acam);
                }
                //Mndt-Cdtr
                com.cbs.pojo.mms.pain009.PartyIdentification32 pi3 = new com.cbs.pojo.mms.pain009.PartyIdentification32();
//                pi3.setNm(element.get(13).toString().trim());
                pi3.setNm(element.get(15).toString().trim());
                mndt.setCdtr(pi3);
                //Mndt-CdtrAcct
                com.cbs.pojo.mms.pain009.CashAccount16 ca1 = new com.cbs.pojo.mms.pain009.CashAccount16();
                com.cbs.pojo.mms.pain009.AccountIdentification4Choice ai4c = new com.cbs.pojo.mms.pain009.AccountIdentification4Choice();
                com.cbs.pojo.mms.pain009.GenericAccountIdentification1 gai = new com.cbs.pojo.mms.pain009.GenericAccountIdentification1();
                gai.setId(element.get(19).toString().trim());
                ai4c.setOthr(gai);
                ca1.setId(ai4c);
                mndt.setCdtrAcct(ca1);
                //Mndt-CdtrAgt
                com.cbs.pojo.mms.pain009.BranchAndFinancialInstitutionIdentification4 bafi = new com.cbs.pojo.mms.pain009.BranchAndFinancialInstitutionIdentification4();
                com.cbs.pojo.mms.pain009.FinancialInstitutionIdentification7 fii7 = new com.cbs.pojo.mms.pain009.FinancialInstitutionIdentification7();
                com.cbs.pojo.mms.pain009.ClearingSystemMemberIdentification2 csmi2 = new com.cbs.pojo.mms.pain009.ClearingSystemMemberIdentification2();
                csmi2.setMmbId(element.get(16).toString().trim());
                fii7.setClrSysMmbId(csmi2);
                fii7.setNm(element.get(15).toString().trim());
                bafi.setFinInstnId(fii7);
                mndt.setCdtrAgt(bafi);
                //Mndt-Dbtr
                com.cbs.pojo.mms.pain009.PartyIdentification32 pi32 = new com.cbs.pojo.mms.pain009.PartyIdentification32();
                pi32.setNm(element.get(21).toString().trim());
                com.cbs.pojo.mms.pain009.ContactDetails2 cd2 = new com.cbs.pojo.mms.pain009.ContactDetails2();
                String mobile = element.get(25).toString().trim();
                String email = element.get(26).toString().trim();
                if (!mobile.equals("")) {
                    mobile = "+91" + mobile;
                    cd2.setMobNb(mobile.substring(0, 3) + "-" + mobile.substring(3));
                } else {
                    cd2.setEmailAdr(email);
                }
                pi32.setCtctDtls(cd2);
                mndt.setDbtr(pi32);
                //Mndt-DbtrAcct
                com.cbs.pojo.mms.pain009.CashAccount16 ca16 = new com.cbs.pojo.mms.pain009.CashAccount16();
                com.cbs.pojo.mms.pain009.AccountIdentification4Choice aif4c = new com.cbs.pojo.mms.pain009.AccountIdentification4Choice();
                com.cbs.pojo.mms.pain009.GenericAccountIdentification1 gai1 = new com.cbs.pojo.mms.pain009.GenericAccountIdentification1();
                gai1.setId(element.get(20).toString().trim());
                aif4c.setOthr(gai1);
                ca16.setId(aif4c);
                com.cbs.pojo.mms.pain009.CashAccountType2 cat2 = new com.cbs.pojo.mms.pain009.CashAccountType2();
                cat2.setPrtry(element.get(22).toString().trim());
                ca16.setTp(cat2);
                mndt.setDbtrAcct(ca16);
                //Mndt-DbtrAgt
                com.cbs.pojo.mms.pain009.BranchAndFinancialInstitutionIdentification4 bafii4 = new com.cbs.pojo.mms.pain009.BranchAndFinancialInstitutionIdentification4();
                com.cbs.pojo.mms.pain009.FinancialInstitutionIdentification7 fiii7 = new com.cbs.pojo.mms.pain009.FinancialInstitutionIdentification7();
                com.cbs.pojo.mms.pain009.ClearingSystemMemberIdentification2 csmii2 = new com.cbs.pojo.mms.pain009.ClearingSystemMemberIdentification2();
                csmii2.setMmbId(element.get(24).toString().trim());
                fiii7.setClrSysMmbId(csmii2);
                fiii7.setNm(element.get(23).toString().trim());
                bafii4.setFinInstnId(fiii7);
                mndt.setDbtrAgt(bafii4);

                mndtInitnReq.setGrpHdr(grpHdr);
                mndtInitnReq.setMndt(mndt);
                document.setMndtInitnReq(mndtInitnReq);

                //Finding mandate images for particular cbs umrn and move to zip directory
                File tiffImage = new File(imageFolder + "/" + element.get(1).toString().trim() + ".tiff");
                if (!tiffImage.exists()) {
                    throw new Exception("There is no tiff image for internal UMRN:" + element.get(1).toString().trim());
                }
                File jpgImage = new File(imageFolder + "/" + element.get(1).toString().trim() + ".jpg");
                if (!jpgImage.exists()) {
                    throw new Exception("There is no jpg image for internal UMRN:" + element.get(1).toString().trim());
                }
                String dataFileSeqNo = getDataFileSeqNo(ymd.format(dmy.parse(todayDt)), mmsType);
                String dataFileName = "MMS-" + mmsType.toUpperCase() + "-" + npciPrelimiaryData[2].toUpperCase() + "-"
                        + npciUserName.toUpperCase() + "-" + ymdOne.format(dmy.parse(todayDt)) + "-" + dataFileSeqNo;
                Files.copy(tiffImage.toPath(), new File(zipFolderDirectory + "/" + dataFileName + "_front.tiff").toPath(), StandardCopyOption.REPLACE_EXISTING);
                Files.copy(jpgImage.toPath(), new File(zipFolderDirectory + "/" + dataFileName + "_detailfront.jpg").toPath(), StandardCopyOption.REPLACE_EXISTING);
                //Writing data xml file
                File generatedXmlFile = new File(zipFolderDirectory + "/" + dataFileName + "-INP.xml");
                JAXBContext jaxbContext = JAXBContext.newInstance(com.cbs.pojo.mms.pain009.Document.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                // output pretty printed
//                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

                jaxbMarshaller.setProperty(
                        "com.sun.xml.bind.xmlHeaders", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                jaxbMarshaller.marshal(document, generatedXmlFile);
                //Logging of data file seq no
                n = em.createNativeQuery("insert into mms_seq_detail(zip_gen_dt,trans_type,data_file_seq_no,"
                        + "trans_seq_no,gen_tran_time,gen_by) values('" + ymd.format(dmy.parse(todayDt)) + "',"
                        + "'" + getFileType(mmsType) + "'," + Integer.parseInt(dataFileSeqNo) + ","
                        + "" + Integer.parseInt(zipFileSeqNo) + ",now(),'" + userName + "')").executeUpdate();
                if (n
                        <= 0) {
                    throw new ApplicationException("Problem in data file seq no maintainance.");
                }
                //Updation of mandate status
                n = em.createNativeQuery("update cbs_mandate_detail set flag='Y',"
                        + "uploaded_zip_name='" + zipFolderName + ".zip" + "' where "
                        + "cbs_umrn='" + element.get(1).toString().trim() + "'").executeUpdate();
                if (n
                        <= 0) {
                    throw new ApplicationException("Problem in cbs umrn file generation updation.");
                }
            }
            ckycrCommonMgmtRemote.addToZipWithOutFolder(zipFolderDirectory + "/", directory + "/", zipFolderName + ".zip");
            File zipFolderToDelete = new File(zipFolderDirectory + "/");
            if (zipFolderToDelete.exists()) {
                ckycrCommonMgmtRemote.delete(zipFolderToDelete);
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

    public String getNpciUserName(String cbsUser) throws Exception {
        String npciUserName = "";
        try {
            List list = em.createNativeQuery("select npciusername from securityinfo where "
                    + "userid='" + cbsUser + "'").getResultList();
            Vector ele = (Vector) list.get(0);
            if (list.isEmpty() || ele.get(0) == null || ele.get(0).toString().trim().equals("")) {
                throw new Exception("You are not authorized person to generate the file.");
            }
            npciUserName = ele.get(0).toString().trim();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return npciUserName;
    }

    public String[] getNpciPrelimiaryData() throws Exception {
        String[] arr = new String[3];
        try {
            List list = em.createNativeQuery("select iin,aadhar_location,npci_bank_code from "
                    + "mb_sms_sender_bank_detail").getResultList();
            Vector ele = (Vector) list.get(0);
            if (ele.get(0) == null || ele.get(1) == null || ele.get(2) == null
                    || ele.get(0).toString().trim().equals("")
                    || ele.get(1).toString().trim().equals("")
                    || ele.get(2).toString().trim().equals("")
                    || ele.get(2).toString().trim().length() != 4) {
                throw new ApplicationException("Please define IIN, Aadhar Location and Bank Code in sender detail.");
            }
            arr[0] = ele.get(0).toString().trim(); //IIN
            arr[1] = ele.get(1).toString().trim(); //Aadhar Location
            arr[2] = ele.get(2).toString().trim(); //NPCI Bank Code
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return arr;
    }

    public String getMmsInitiationZipFileSeqNo(String mmsType) throws Exception {
        String fileNo = "";
        try {
            String fileType = getFileType(mmsType);
            fileNo = "1";

            List list = em.createNativeQuery("select max(file_no)+1 as file_no from cbs_npci_mapper_files "
                    + "where file_gen_type='" + fileType + "'").getResultList();
            Vector ele = (Vector) list.get(0);
            if (ele.get(0) != null) {
                fileNo = ele.get(0).toString().trim();
            }
            fileNo = ParseFileUtil.addTrailingZeros(fileNo, 6);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return fileNo;
    }

    public String getDataFileSeqNo(String todayDt, String mmsType) throws Exception {
        String fileNo = "";
        try {
            String fileType = getFileType(mmsType);
            fileNo = "1";

            List list = em.createNativeQuery("select max(data_file_seq_no)+1 as file_no from mms_seq_detail "
                    + "where trans_type='" + fileType + "' and zip_gen_dt='" + todayDt + "'").getResultList();
            Vector ele = (Vector) list.get(0);
            if (ele.get(0) != null) {
                fileNo = ele.get(0).toString().trim();
            }
            fileNo = ParseFileUtil.addTrailingZeros(fileNo, 6);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return fileNo;
    }

    /*
     * These filetype will store in cbs_npci_mapper table
     */
    public String getFileType(String mmsType) throws Exception {
        String fileType = "";
        if (mmsType.equalsIgnoreCase("CREATE")) {
            fileType = "MMSI";
        } else if (mmsType.equalsIgnoreCase("AMEND")) {
            fileType = "MMSA";
        } else if (mmsType.equalsIgnoreCase("CANCEL")) {
            fileType = "MMSC";
        }
        return fileType;
    }

    @Override
    public String mmsxmlandCancelUpdation(File directory, String uploadedFileName, String userName) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            File[] files = directory.listFiles();
            for (int n = 0; n < files.length; n++) {
                JAXBContext jaxbContext = JAXBContext.newInstance(com.cbs.pojo.mms.pain011.Document.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                com.cbs.pojo.mms.pain011.Document doc = (com.cbs.pojo.mms.pain011.Document) jaxbUnmarshaller.unmarshal(files[n]);

                System.out.println(doc);
                com.cbs.pojo.mms.pain011.MandateCancellationRequestV01 marv1 = doc.getMndtCxlReq();
//                if(marv1.getUndrlygCxlDtls().getOrgnlMsgInf() == null
//                        || marv1.getUndrlygCxlDtls().getOrgnlMsgInf().getMsgId()==null){
//                    continue;
//                }
                com.cbs.pojo.mms.pain011.CancellationReasonInformation2 result = marv1.getUndrlygCxlDtls().getCxlRsn();
                com.cbs.pojo.mms.pain011.OriginalMandate1Choice orgnlMandate = marv1.getUndrlygCxlDtls().getOrgnlMndt();
                String chiUmrn = orgnlMandate.getOrgnlMndtId();
                List list = em.createNativeQuery("select cbs_umrn,flag from cbs_mandate_detail where chi_umrn='" + chiUmrn + "'").getResultList();

                if (!list.isEmpty()) {
                    Vector ele = (Vector) list.get(0);
                    String flag = ele.get(1).toString().trim();
                    if (flag.equalsIgnoreCase("C")) {
                        continue;
                    }
                }
                int t = em.createNativeQuery("insert into cbs_mandate_detail_his(trans_type,cbs_umrn,chi_umrn,proprietary,"
                        + "category,amount_flag,amount,frequency,sequence_type,period_type,from_date,to_date,creditor_acno,"
                        + "creditor_name,creditor_actype,creditor_bankname,creditor_ifsc,creditor_mobile,creditor_email,"
                        + "creditor_utility_code,debtor_acno,debtor_name,debtor_actype,debtor_bankname,debtor_ifsc,"
                        + "debtor_mobile,debtor_email,debtor_utility_code,ref_1,ref_2,flag,mandate_receiving_branch,"
                        + "mandate_received_by,mandate_received_date,update_by,update_date,update_mode,response_code_in_updation,"
                        + "response_detail_in_updation,uploaded_zip_name,response_file_name,mandate_received_time,txnfiletype,"
                        + "debtor_fincodetype,creditor_fincodetype) "
                        + "select trans_type,cbs_umrn,chi_umrn,proprietary,category,amount_flag,amount,"
                        + "frequency,sequence_type,period_type,from_date,to_date,creditor_acno,creditor_name,creditor_actype,"
                        + "creditor_bankname,creditor_ifsc,creditor_mobile,creditor_email,creditor_utility_code,debtor_acno,"
                        + "debtor_name,debtor_actype,debtor_bankname,debtor_ifsc,debtor_mobile,debtor_email,debtor_utility_code,"
                        + "ref_1,ref_2,flag,mandate_receiving_branch,mandate_received_by,mandate_received_date,update_by,"
                        + "update_date,update_mode,response_code_in_updation,response_detail_in_updation,uploaded_zip_name,"
                        + "response_file_name,mandate_received_time,txnfiletype,debtor_fincodetype,creditor_fincodetype from "
                        + "cbs_mandate_detail where (chi_umrn='" + chiUmrn + "')").executeUpdate();
                if (t
                        <= 0) {
                    throw new Exception("Problem in mms initiation acknowledgement/response updation.");
                }

                if (uploadedFileName.toLowerCase()
                        .trim().contains("inp.xml")
                        && uploadedFileName.toLowerCase().trim().contains("cancel")) {
                    t = em.createNativeQuery("update cbs_mandate_detail set update_by='" + userName + "',"
                            + "update_date='" + ymd.format(new Date()) + "',update_mode='Cancel',response_code_in_updation='',"
                            + "response_detail_in_updation='',response_file_name='" + uploadedFileName + "',flag='C' where "
                            + "chi_umrn='" + chiUmrn + "'").executeUpdate();
                    if (t <= 0) {
                        throw new Exception("Problem in mms initiation acknowledgement/response updation.");
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
        return "true";
    }

    @Override
    public String mmsAcknowledgementAndResponseUpdation(File directory, String uploadedFileName, String userName)
            throws Exception {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            File[] filesAre = directory.listFiles();
            for (int e = 0; e < filesAre.length; e++) {
                //Here is question: What is the CBS UMRN in ACK and Res ? What is the value of success and fail ?
                JAXBContext jaxbContext = JAXBContext.newInstance(com.cbs.pojo.mms.pain012.Document.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                com.cbs.pojo.mms.pain012.Document doc = (com.cbs.pojo.mms.pain012.Document) jaxbUnmarshaller.unmarshal(filesAre[e]);

                System.out.println(doc);
                com.cbs.pojo.mms.pain012.MandateAcceptanceReportV01 marv1 = doc.getMndtAccptncRpt();

                //Currently we are treating that these both values are same for (ACK/NACK) as well as RES.
                //String cbsUmrn = marv1.getGrpHdr().getMsgId(); //Cbs Umrn
                if (marv1.getUndrlygAccptncDtls()
                        .getOrgnlMsgInf() == null
                        || marv1.getUndrlygAccptncDtls().getOrgnlMsgInf().getMsgId() == null) {
                    continue;
                }
                String cbsUmrn = marv1.getUndrlygAccptncDtls().getOrgnlMsgInf().getMsgId();
                com.cbs.pojo.mms.pain012.AcceptanceResult6 chiResult = marv1.getUndrlygAccptncDtls().getAccptncRslt(); //CHI Result
                List list = em.createNativeQuery("select cbs_umrn,flag from cbs_mandate_detail where (cbs_umrn='" + cbsUmrn + "' "
                        + "or chi_umrn='" + cbsUmrn + "')").getResultList();

                if (!list.isEmpty()) {
                    Vector ele = (Vector) list.get(0);
                    String flag = ele.get(1).toString().trim();
                    if (flag.equalsIgnoreCase("C")) {
                        continue;
                    }

                    //History maintainance
                    int n = em.createNativeQuery("insert into cbs_mandate_detail_his(trans_type,cbs_umrn,chi_umrn,proprietary,"
                            + "category,amount_flag,amount,frequency,sequence_type,period_type,from_date,to_date,creditor_acno,"
                            + "creditor_name,creditor_actype,creditor_bankname,creditor_ifsc,creditor_mobile,creditor_email,"
                            + "creditor_utility_code,debtor_acno,debtor_name,debtor_actype,debtor_bankname,debtor_ifsc,"
                            + "debtor_mobile,debtor_email,debtor_utility_code,ref_1,ref_2,flag,mandate_receiving_branch,"
                            + "mandate_received_by,mandate_received_date,update_by,update_date,update_mode,response_code_in_updation,"
                            + "response_detail_in_updation,uploaded_zip_name,response_file_name,mandate_received_time,txnfiletype,"
                            + "debtor_fincodetype,creditor_fincodetype) "
                            + "select trans_type,cbs_umrn,chi_umrn,proprietary,category,amount_flag,amount,"
                            + "frequency,sequence_type,period_type,from_date,to_date,creditor_acno,creditor_name,creditor_actype,"
                            + "creditor_bankname,creditor_ifsc,creditor_mobile,creditor_email,creditor_utility_code,debtor_acno,"
                            + "debtor_name,debtor_actype,debtor_bankname,debtor_ifsc,debtor_mobile,debtor_email,debtor_utility_code,"
                            + "ref_1,ref_2,flag,mandate_receiving_branch,mandate_received_by,mandate_received_date,update_by,"
                            + "update_date,update_mode,response_code_in_updation,response_detail_in_updation,uploaded_zip_name,"
                            + "response_file_name,mandate_received_time,txnfiletype,debtor_fincodetype,creditor_fincodetype from "
                            + "cbs_mandate_detail where (cbs_umrn='" + cbsUmrn + "' or chi_umrn='" + cbsUmrn + "')").executeUpdate();
                    if (n <= 0) {
                        throw new Exception("Problem in mms initiation acknowledgement/response updation.");
                    }
                    if (uploadedFileName.toLowerCase().trim().contains("inp-ack")) {  //ACK && NACK Processing
                        if (chiResult.isAccptd()) { //Success acknowledgement
                            n = em.createNativeQuery("update cbs_mandate_detail set chi_umrn='" + marv1.getUndrlygAccptncDtls().
                                    getOrgnlMndt().getOrgnlMndt().getMndtId() + "',update_by='" + userName + "',"
                                    + "update_date='" + ymd.format(new Date()) + "',update_mode='ACK',response_code_in_updation='',"
                                    + "response_detail_in_updation='',response_file_name='" + uploadedFileName + "',flag='A' where "
                                    + "cbs_umrn='" + cbsUmrn + "'").executeUpdate();
                        } else { //Negative acknowledgement
                            n = em.createNativeQuery("update cbs_mandate_detail set update_by='" + userName + "',"
                                    + "update_date='" + ymd.format(new Date()) + "',update_mode='NACK',response_code_in_updation='',"
                                    + "response_detail_in_updation='" + chiResult.getRjctRsn().getPrtry() + "',"
                                    + "response_file_name='" + uploadedFileName + "',flag='F' where "
                                    + "cbs_umrn='" + cbsUmrn + "'").executeUpdate();
                        }
                    } else if (uploadedFileName.toLowerCase().trim().contains("res")) {
                        if (chiResult.isAccptd()) { //Success response
                            n = em.createNativeQuery("update cbs_mandate_detail set update_by='" + userName + "',"
                                    + "update_date='" + ymd.format(new Date()) + "',update_mode='RES',"
                                    + "response_code_in_updation='',response_detail_in_updation='',"
                                    + "response_file_name='" + uploadedFileName + "',flag='S',"
                                    + "chi_umrn='" + marv1.getUndrlygAccptncDtls().getOrgnlMndt().getOrgnlMndtId() + "' where "
                                    + "(cbs_umrn='" + cbsUmrn + "' or chi_umrn='" + cbsUmrn + "')").executeUpdate();
                        } else { //Negative response
                            n = em.createNativeQuery("update cbs_mandate_detail set update_by='" + userName + "',"
                                    + "update_date='" + ymd.format(new Date()) + "',update_mode='NRES',response_code_in_updation='',"
                                    + "response_detail_in_updation='" + chiResult.getRjctRsn().getPrtry() + "',"
                                    + "response_file_name='" + uploadedFileName + "',flag='P',"
                                    + "chi_umrn='" + marv1.getUndrlygAccptncDtls().getOrgnlMndt().getOrgnlMndtId() + "' where "
                                    + "(cbs_umrn='" + cbsUmrn + "' or chi_umrn='" + cbsUmrn + "')").executeUpdate();
                        }
                    }
                    if (n <= 0) {
                        throw new Exception("Problem in mms initiation acknowledgement/response updation.");
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
        return "true";
    }

    public List<AccWiesMMSRepPojo> mmsReportData(String reportType, String accno, String umrn, String fromDate, String toDate) throws Exception {
        List<AccWiesMMSRepPojo> reportData = new ArrayList<AccWiesMMSRepPojo>();
        try {
            String condQuery = "";
            if (reportType.equalsIgnoreCase("ACCWISE")) {
                condQuery = " and OLD_ACNO='" + accno + "' ";
            } else if (reportType.equalsIgnoreCase("UMRNWIES")) {
                condQuery = " and  UMRN='" + umrn + "' ";
            }
            List result = em.createNativeQuery("select OLD_ACNO,OLD_ACNAME,AMOUNT,date_format(ENTER_DATE,'%d/%m/%Y'),AC_VAL_FLAG,"
                    + "RETURN_CODE,UMRN from cbs_npci_oac_detail where iw_type='ACH-DR' and (ENTER_DATE BETWEEN"
                    + " '" + ymd.format(dmy.parse(fromDate)) + "' AND '" + ymd.format(dmy.parse(toDate)) + "')"
                    + condQuery).getResultList();
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    AccWiesMMSRepPojo pojo = new AccWiesMMSRepPojo();
                    pojo.setAccNo(vtr.get(0).toString().trim());
//                        ftsRemote.getNewAccountNumber("");
                    try {
                        String acno = ftsRemote.getNewAccountNumber(vtr.get(0).toString().trim());
                        pojo.setAccHolderName(ftsRemote.getCustName(acno));
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        pojo.setAccHolderName("");
                    }
//                    pojo.setAccHolderName(vtr.get(1).toString().trim());
                    BigDecimal amount = new BigDecimal(vtr.get(2).toString().trim()).divide(new BigDecimal(100));
                    amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
                    pojo.setAmt(amount);
                    pojo.setFileComingDate(vtr.get(3).toString().trim());
                    String status = vtr.get(4).toString().trim();
                    if (status.equalsIgnoreCase("R")) {
                        status = "Reject";
                    } else if (status.equalsIgnoreCase("P")) {
                        status = "Pass";
                    }
                    pojo.setStatus(status);
                    pojo.setReason(commonReport.getRefRecDesc("319", vtr.get(5).toString().trim()));
                    reportData.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return reportData;
    }
}
