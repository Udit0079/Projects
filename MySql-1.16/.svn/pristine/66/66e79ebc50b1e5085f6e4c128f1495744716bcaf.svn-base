/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.neftrtgs;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.constant.IdbiReasonCodeEnum;
import com.cbs.constant.NeftEnum;
import com.cbs.constant.SbiNeftConstant;
import com.cbs.constant.SbiReasonCodeEnum;
import com.cbs.dao.master.AbbParameterInfoDAO;
import com.cbs.dao.master.BranchMasterDAO;
import com.cbs.dao.master.GlTableDAO;
import com.cbs.dao.neftrtgs.NeftFileDetailsDAO;
import com.cbs.dao.neftrtgs.NeftOwDetailsDAO;
import com.cbs.dao.neftrtgs.NeftRtgsStatusDAO;
import com.cbs.dao.sms.MbSmsSenderBankDetailDAO;
import com.cbs.dto.sms.TransferSmsRequestTo;
import com.cbs.entity.master.AbbParameterInfo;
import com.cbs.entity.master.BranchMaster;
import com.cbs.entity.master.Gltable;
import com.cbs.entity.neftrtgs.CbsAutoNeftDetails;
import com.cbs.entity.neftrtgs.NeftFileDetails;
import com.cbs.entity.neftrtgs.NeftFileDetailsPK;
import com.cbs.entity.neftrtgs.NeftOwDetails;
import com.cbs.entity.neftrtgs.NeftRtgsStatus;
import com.cbs.entity.sms.MbSmsSenderBankDetail;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.ibl.pojo.PaymentEnqResp;
import com.cbs.ibl.pojo.PaymentEnquiry;
import com.cbs.ibl.pojo.PaymentRequest;
import com.cbs.ibl.pojo.PaymentResponse;
import com.cbs.ibl.util.IblUtil;
import com.cbs.pojo.neftrtgs.ExcelReaderPojo;
import com.cbs.pojo.neftrtgs.F27;
import com.cbs.pojo.neftrtgs.H2HOwReportPojo;
import com.cbs.pojo.neftrtgs.N02;
import com.cbs.pojo.neftrtgs.N06;
import com.cbs.pojo.neftrtgs.N07;
import com.cbs.pojo.neftrtgs.R09;
import com.cbs.pojo.neftrtgs.R41;
import com.cbs.pojo.neftrtgs.R42;
import com.cbs.pojo.neftrtgs.R90;
import com.cbs.pojo.neftrtgs.sbi.AckR09Adapter;
import com.cbs.pojo.neftrtgs.sbi.AckR90Adapter;
import com.cbs.pojo.neftrtgs.sbi.BlockAItems;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.ParseFileUtil;
import com.cbs.utils.Validator;
import com.cbs.neftrtgs.yes.api.YesUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import javax.activation.MimetypesFileTypeMap;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.vfs2.FileFilterSelector;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.codec.Base64;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Stateless(mappedName = "H2HMgmtFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class H2HMgmtFacade implements H2HMgmtFacadeRemote {

    @PersistenceContext
    private EntityManager entityManager;
    @Resource
    EJBContext context;
    @EJB
    UploadNeftRtgsMgmtFacadeRemote remote;
    @EJB
    FtsPostingMgmtFacadeRemote ftsRemote;
    @EJB
    SmsManagementFacadeRemote smsMgmtFacade;
    @EJB
    private InterBranchTxnFacadeRemote interBranchFacade;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat ddmm = new SimpleDateFormat("ddMM");
    SimpleDateFormat ymdhh = new SimpleDateFormat("dd-MMM-yy-hh:mm:ss");
    private Properties props = null;

    @PostConstruct
    private void loadWebServicesConfig() {
        try {
            props = new Properties();
            props.load(new FileReader("/opt/conf/wslocation.properties"));
        } catch (Exception ex) {
            System.out.println("Problem In Bean Initialization And Loading The "
                    + "WSLOCATION Properties File. " + ex.getMessage());
        }
    }

    @Override
    public void eftProcess() {
        System.out.println("In eftProcess method()");
        try {
            NeftOwDetailsDAO neftOwDetailsDAO = new NeftOwDetailsDAO(entityManager);
            List<CbsAutoNeftDetails> resultList = neftOwDetailsDAO.getAutoNeftDetailsByProcess("auto");
            if (resultList == null || resultList.isEmpty()) {
                throw new Exception("Please fill neft auto details in the table !");
            }

            for (int i = 0; i < resultList.size(); i++) {
                CbsAutoNeftDetails neftAutoObj = resultList.get(i);
                if (neftAutoObj.getNeftBankName() == null
                        || neftAutoObj.getNeftBankName().trim().equals("")) {
                    throw new Exception("Please fill Neft Bank Name");
                }
                if (neftAutoObj.getProcessType().equalsIgnoreCase("iw")
                        && (neftAutoObj.getIwHead().trim().equals("")
                        || neftAutoObj.getIwHead().trim().length() != 12)) {
                    throw new Exception("Please fill Inward Credit Head");
                } else if (neftAutoObj.getProcessType().equalsIgnoreCase("ow")
                        && (neftAutoObj.getOwHead().trim().equals("")
                        || neftAutoObj.getOwHead().trim().length() != 12)) {
                    throw new Exception("Please fill Outward Credit Head");
                } else if (neftAutoObj.getProcessType().equalsIgnoreCase("bt")) {
                    if (neftAutoObj.getIwHead().trim().equals("")
                            || neftAutoObj.getIwHead().trim().length() != 12
                            || neftAutoObj.getOwHead().trim().equals("")
                            || neftAutoObj.getOwHead().trim().length() != 12) {
                        throw new Exception("Please fill Inward/Outward Credit Head");
                    }
                }
                try {
                    if (neftAutoObj.getProcessType().equalsIgnoreCase("bt")
                            || neftAutoObj.getProcessType().equalsIgnoreCase("ow")) {
                        owEftProcess(neftAutoObj);
                    }
                } catch (Exception e) {
                    System.out.println("Problem in owEftProcess() method......" + e.getMessage());
                }
                Thread.sleep(5000);
                try {
                    if (neftAutoObj.getProcessType().equalsIgnoreCase("bt")
                            || neftAutoObj.getProcessType().equalsIgnoreCase("ow")) {
                        owReportProcess(neftAutoObj);
                    }
                } catch (Exception e) {
                    System.out.println("Problem in owReportProcess() method......" + e.getMessage());
                }
                Thread.sleep(5000);
                try {
                    if (neftAutoObj.getProcessType().equalsIgnoreCase("bt")
                            || neftAutoObj.getProcessType().equalsIgnoreCase("iw")) {
                        iwEftProcess(neftAutoObj);
                    }
                } catch (Exception e) {
                    System.out.println("Problem in iwEftProcess() method......" + e.getMessage());
                }
                Thread.sleep(5000);
                try {
                    if (neftAutoObj.getProcessType().equalsIgnoreCase("bt")
                            || neftAutoObj.getProcessType().equalsIgnoreCase("iw")) {
                        iwReturnProcess(neftAutoObj);
                    }
                } catch (Exception e) {
                    System.out.println("Problem in iwReturnProcess() method......" + e.getMessage());
                }
            }
        } catch (Exception ex) {
//            ex.printStackTrace();
            System.out.println("Problem in eftProcess() method......" + ex.getMessage());
        }
    }

    public void exceptionExecution() {
        try {
            eftProcess();
        } catch (Exception ex) {
            System.out.println("Problem in exceptionExecution() method......" + ex.getMessage());
        }
    }

    public void owEftProcess(CbsAutoNeftDetails neftAutoObj) throws Exception {
        try {
            if (neftAutoObj.getNeftBankName().trim().equalsIgnoreCase("IBL")) {
                iblOWTxnProcess(neftAutoObj);
            } else {
                String owComLocalFilePath = neftAutoObj.getOwLocalFilePath().trim();
                String owLocalFilePath = owComLocalFilePath.substring(0, owComLocalFilePath.lastIndexOf("/") + 1);

                generateOwFile(owLocalFilePath, neftAutoObj);

                if (!(neftAutoObj.getNeftBankName().trim().equalsIgnoreCase("idbi")
                        || neftAutoObj.getNeftBankName().trim().equalsIgnoreCase("yes")
                        || neftAutoObj.getNeftBankName().trim().equalsIgnoreCase("sbi"))) {   //For Non-IDBI and Yes Bank.
                    try {
                        upload(neftAutoObj, owLocalFilePath + neftAutoObj.getNeftBankName().trim().toLowerCase(),
                                neftAutoObj.getOwRemoteFilePath().trim());
                    } catch (Exception ex) {
                        System.out.println("Problem in uploading the outward files.");
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private void generateOwFile(String owLocalFilePath, CbsAutoNeftDetails neftAutoObj) throws Exception {
        SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        SimpleDateFormat ddmmyyyySdf = new SimpleDateFormat("ddMMyyyy");
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            NeftOwDetailsDAO neftOwDetailsDAO = new NeftOwDetailsDAO(entityManager);
            NeftFileDetailsDAO neftFileDetailsDAO = new NeftFileDetailsDAO(entityManager);
            AbbParameterInfoDAO abbParameterInfoDAO = new AbbParameterInfoDAO(entityManager);
            MbSmsSenderBankDetailDAO bankDetailDao = new MbSmsSenderBankDetailDAO(entityManager);

            String parentDrAccount = "";
            //Evaluating Ow Details.
            List<NeftOwDetails> resultList = neftOwDetailsDAO.getNeftOwDetailsData(neftAutoObj.getNeftBankName().trim());
            if (!resultList.isEmpty()) {
                String neftBankName = neftAutoObj.getNeftBankName().trim();
                if (neftBankName.equalsIgnoreCase("yes") || neftBankName.equalsIgnoreCase("icici")) {
                    List<AbbParameterInfo> abbParameterInfoList = abbParameterInfoDAO.getEntityByPurpose("NEFT-OW-DEBIT-ACCOUNT");
                    if (abbParameterInfoList.isEmpty()) {
                        throw new ApplicationException("NEFT-OW-DEBIT-ACCOUNT head is not defined in ABB Parameterinfo");
                    }
                    for (AbbParameterInfo abbPojo : abbParameterInfoList) {
                        parentDrAccount = abbPojo.getAcno();
                    }
                }

                if (neftBankName.equalsIgnoreCase("yes")) {
                    System.out.println("In Yes Bank Ow Processing");
                    generateYesBankOutwardTextFile(parentDrAccount, owLocalFilePath, resultList,
                            neftAutoObj.getOwLocalFileBackupPath().trim(), neftBankName);
                } else if (neftBankName.equalsIgnoreCase("idbi")) {
                    System.out.println("In Idbi Ow Processing");
                    generateIDBIBankOutwardFiles(resultList, owLocalFilePath, neftAutoObj);
                } else if (neftBankName.equalsIgnoreCase("icici")) {
                    System.out.println("In Icici Ow Processing");
                    List<MbSmsSenderBankDetail> bankList = bankDetailDao.getBankAndSenderDetail();
                    if (bankList.isEmpty()) {
                        throw new ApplicationException("Please define bank detail.");
                    }
                    MbSmsSenderBankDetail bankDetail = bankList.get(0);
                    String bankCode = bankDetail.getBankCode();

                    String fileName = neftAutoObj.getFileNamePrefix() + "_" + yyyymmdd.format(new Date()) + ".txt";
                    System.out.println("In Icici Ow Processing filename is-->" + fileName);
                    FileWriter fw = new FileWriter(owLocalFilePath + neftBankName.toLowerCase() + "/" + fileName);
                    String uniqueRefNo = "";
                    for (NeftOwDetails entity : resultList) {
                        String paymentType = entity.getPaymentType();
                        String uCustNo = entity.getUniqueCustomerRefNo();
                        String beneficiaryName = entity.getBeneficiaryName();
                        String beneficiaryCode = entity.getBeneficiaryCode();
                        String amount = entity.getTxnAmt().toString();
                        String paymentDate = entity.getPaymentDate();
                        String crAcno = entity.getCreditAccountNo();
                        String ifscCode = entity.getOutsideBankIfscCode();
                        String beneEmail = entity.getBeneficiaryEmailId();
                        String beneMobile = entity.getBeneficiaryMobileNo();
                        String debitAcName = ftsRemote.getCustName(entity.getDebitAccountNo().trim());
                        debitAcName = debitAcName.replaceAll("[\\W_]", " ");
                        debitAcName = debitAcName.length() > 50 ? debitAcName.substring(0, 50) : debitAcName;

                        String singleInstrument = "";
                        if (bankCode.equalsIgnoreCase("sicb") || bankCode.equalsIgnoreCase("ucbb")) {
                            singleInstrument = paymentType + "|" + uCustNo + "|" + beneficiaryName + "|" + beneficiaryCode + "|" + amount + "|" + paymentDate + "|"
                                    + crAcno + "|" + ifscCode + "|"
                                    + entity.getDebitAccountNo().trim() + "|" + beneEmail + "|" + beneMobile + "|" + debitAcName + "|" + debitAcName + "|" + "\n";
                        } else {
                            singleInstrument = paymentType + "|" + uCustNo + "|" + beneficiaryName + "|" + beneficiaryCode + "|" + amount + "|" + paymentDate + "|"
                                    + crAcno + "|" + ifscCode + "|"
                                    + entity.getDebitAccountNo().trim() + "|" + beneEmail + "|" + beneMobile + "|" + debitAcName + "|" + "\n";
                        }

                        fw.write(singleInstrument);
                        //Preparation of all UniqueRefNo
                        if (uniqueRefNo.equals("")) {
                            uniqueRefNo = "\'" + uCustNo + "\'";
                        } else {
                            uniqueRefNo = uniqueRefNo + ",\'" + uCustNo + "\'";
                        }
                    }
                    fw.close();
                    //File name logging
                    int n = entityManager.createNativeQuery("update neft_ow_details set outward_file_name='" + fileName + "' where "
                            + "uniquecustomerrefno in(" + uniqueRefNo + ")").executeUpdate();
                    if (n <= 0) {
                        throw new Exception("Problem In Outward File Name Logging.");
                    }
                    System.out.println("Updating the outward file named as-->" + fileName);
                } else if (neftBankName.equalsIgnoreCase("hdfc")) {
                    System.out.println("In Hdfc Ow Processing");
                    MbSmsSenderBankDetailDAO bankDao = new MbSmsSenderBankDetailDAO(entityManager);
                    //Evaluating Bank Details.
                    List<MbSmsSenderBankDetail> bankList = bankDao.getBankAndSenderDetail();
                    if (bankList.isEmpty()) {
                        throw new ApplicationException("Please define bank details.");
                    }
                    MbSmsSenderBankDetail bankEntity = bankList.get(0);

                    String domainId = bankEntity.getHdfcDomainId() == null ? "" : bankEntity.getHdfcDomainId().trim().toUpperCase();
                    if (domainId.equals("")) {
                        throw new ApplicationException("Please define HDFC domain Id.");
                    }

                    String neftClientCode = bankEntity.getNeftClientCode() == null ? ""
                            : bankEntity.getNeftClientCode().trim().toUpperCase();

                    Date dt = new Date();
                    Date currentDt = ymd.parse(ymd.format(dt));
                    if (neftClientCode.equals("")) {
                        throw new ApplicationException("Please define Neft Client Code.");
                    }

                    Long seqNo = neftFileDetailsDAO.getMaxSrl(currentDt);
                    String fileName = domainId + "_" + neftClientCode + "_" + neftClientCode + ddmm.format(dt) + "."
                            + ParseFileUtil.addTrailingZeros(seqNo.toString(), 3);

                    FileWriter fw = new FileWriter(owLocalFilePath + neftBankName.toLowerCase() + "/" + fileName);
                    //Logging of generated file.
                    NeftFileDetails fileEntity = new NeftFileDetails();
                    NeftFileDetailsPK fileEntityPK = new NeftFileDetailsPK();
                    fileEntityPK.setFileDate(currentDt);
                    fileEntityPK.setSeqNo(seqNo);
                    fileEntity.setNeftFileDetailsPK(fileEntityPK);
                    fileEntity.setFileName(fileName);
                    fileEntity.setGenTime(new Date());
                    neftFileDetailsDAO.save(fileEntity);
                    //Outward File Generation.
                    String uniqueRefNo = "";
                    for (NeftOwDetails entity : resultList) {
                        String paymentType = entity.getPaymentType().trim();
                        String crAcno = entity.getCreditAccountNo().trim();
                        String amount = entity.getTxnAmt().toString();
                        String beneficiaryName = entity.getBeneficiaryName().trim();
                        beneficiaryName = beneficiaryName.replaceAll("[\\W_]", " ").trim();
                        beneficiaryName = beneficiaryName.length() > 40
                                ? beneficiaryName.substring(0, 40) : beneficiaryName;

                        String uCustNo = entity.getUniqueCustomerRefNo().trim();
                        String chqNo = entity.getInstNo().trim();
                        String paymentDate = entity.getPaymentDate();
                        String ifscCode = entity.getOutsideBankIfscCode().trim();

                        String debitAccount = entity.getDebitAccountNo().trim();
                        String debitAccountName = ftsRemote.getCustName(debitAccount);
                        debitAccountName = debitAccountName.replaceAll("[\\W_]", " ");
                        debitAccountName = debitAccountName.length() > 35 ? debitAccountName.substring(0, 35) : debitAccountName;
                        String debitAccountAddress = getAccountPermanentAddress(debitAccount);

                        String singleInstrument = paymentType + ",," + crAcno + "," + amount + ","
                                + "" + beneficiaryName + ",,,,,,,,," + uCustNo + ",,,,,"
                                + "" + debitAccountAddress + "," + debitAccount + "," + debitAccountName + ","
                                + "" + chqNo + "," + paymentDate + ",," + ifscCode + ","
                                + "" + ifscCode.substring(0, 4) + ",," + "\n";

                        fw.write(singleInstrument);
                        //Preparation of all UniqueRefNo
                        if (uniqueRefNo.equals("")) {
                            uniqueRefNo = "\'" + uCustNo + "\'";
                        } else {
                            uniqueRefNo = uniqueRefNo + ",\'" + uCustNo + "\'";
                        }
                    }
                    fw.close();
                    //File name logging
                    int n = entityManager.createNativeQuery("update neft_ow_details set outward_file_name='" + fileName + "' where "
                            + "uniquecustomerrefno in(" + uniqueRefNo + ")").executeUpdate();
                    if (n <= 0) {
                        throw new Exception("Problem In Outward File Name Logging.");
                    }
                } else if (neftBankName.equalsIgnoreCase("axis")) {
                    System.out.println("In AXIS Ow Processing");
                    String axisChnlId = "", axisCorpId = "", axisUserUpload = "", axisUserAuthOne = "",
                            axisUserAuthTwo = "", axisProductId = "", axisSubMemberIfsc = "";
                    //Prerequisite things
                    List preList = entityManager.createNativeQuery("select ifnull(a.code,'') as AXIS_CHNL_ID,ifnull(b.code,'') "
                            + "as AXIS_CORP_ID,ifnull(c.code,'') as AXIS_USER_UPLOAD,ifnull(d.code,'') as AXIS_USER_AUTH_ONE,"
                            + "ifnull(e.code,'') as AXIS_USER_AUTH_TWO,ifnull(f.code,'') as AXIS_PRODUCT_ID,ifnull(g.code,'') "
                            + "as AXIS_SUB_MEMBER_IFSC from (select ifnull((select code from cbs_parameterinfo  "
                            + "where name='AXIS-CHNL-ID'),'') as code) a,(select ifnull((select code from "
                            + "cbs_parameterinfo where name='AXIS-CORP-ID'),'') as code) b,(select ifnull((select "
                            + "code from cbs_parameterinfo where name='AXIX-USER-UPLOAD'),'') as code) c,(select "
                            + "ifnull((select code from cbs_parameterinfo where name='AXIS-USER-AUTH-ONE'),'') as "
                            + "code) d,(select ifnull((select code from cbs_parameterinfo where "
                            + "name='AXIS-USER-AUTH-TWO'),'') as code) e,(select ifnull((select code from "
                            + "cbs_parameterinfo where name='AXIS-PRODUCT-ID'),'') as code) f,(select "
                            + "ifnull((select code from cbs_parameterinfo where name='AXIS-SUB-MEMBER-IFSC'),'') "
                            + "as code) g").getResultList();
                    if (preList.isEmpty()) {
                        throw new ApplicationException("Please define prerequisite data for AXIS outward.");
                    }
                    Vector preVec = (Vector) preList.get(0);
                    axisChnlId = preVec.get(0).toString().trim();
                    axisCorpId = preVec.get(1).toString().trim();
                    axisUserUpload = preVec.get(2).toString().trim();
                    axisUserAuthOne = preVec.get(3).toString().trim();
                    axisUserAuthTwo = preVec.get(4).toString().trim();
                    axisProductId = preVec.get(5).toString().trim();
                    axisSubMemberIfsc = preVec.get(6).toString().trim();

                    //Validation can be modified based on field length.
                    if (axisChnlId.equals("") || axisCorpId.equals("") || axisUserUpload.equals("")
                            || axisUserAuthOne.equals("") || axisUserAuthTwo.equals("")
                            || axisProductId.equals("") || axisSubMemberIfsc.equals("")
                            || axisSubMemberIfsc.length() != 11) {
                        throw new ApplicationException("Please define proper prerequisite data for AXIS outward.");
                    }

                    List<AbbParameterInfo> abbParameterInfoList = abbParameterInfoDAO.getEntityByPurpose("AXIS-OW-DEBIT-ACCOUNT");
                    if (abbParameterInfoList.isEmpty()) {
                        throw new ApplicationException("AXIS-OW-DEBIT-ACCOUNT head is not defined in ABB Parameterinfo");
                    }
                    for (AbbParameterInfo abbPojo : abbParameterInfoList) {
                        parentDrAccount = abbPojo.getAcno();
                    }

//                    String fileName = yyyymmdd.format(new Date()) + "_H2H" + ".txt";

                    Date dt = new Date();
                    Date currentDt = ymd.parse(ymd.format(dt));
                    Long seqNo = neftFileDetailsDAO.getMaxSrl(currentDt);

                    String fileName = axisCorpId + "_" + axisProductId + "_" + ddmmyyyySdf.format(dt) + "_"
                            + ParseFileUtil.addTrailingZeros(seqNo.toString(), 7) + ".txt";

                    FileWriter fw = new FileWriter(owLocalFilePath + neftBankName.toLowerCase() + "/" + fileName);
                    //Logging of generated file.
                    NeftFileDetails fileEntity = new NeftFileDetails();
                    NeftFileDetailsPK fileEntityPK = new NeftFileDetailsPK();
                    fileEntityPK.setFileDate(currentDt);
                    fileEntityPK.setSeqNo(seqNo);
                    fileEntity.setNeftFileDetailsPK(fileEntityPK);
                    fileEntity.setFileName(fileName);
                    fileEntity.setGenTime(new Date());
                    neftFileDetailsDAO.save(fileEntity);
                    //Outward File Generation.
                    String uniqueRefNo = "";
                    for (NeftOwDetails entity : resultList) {
                        String paymentType = entity.getPaymentType();
                        String uCustNo = entity.getUniqueCustomerRefNo();
                        String beneficiaryName = entity.getBeneficiaryName();
                        String beneficiaryCode = entity.getBeneficiaryCode();
                        String amount = entity.getTxnAmt().toString();
                        String crAcno = entity.getCreditAccountNo();
                        String ifscCode = entity.getOutsideBankIfscCode();
                        String debitAcNo = entity.getDebitAccountNo().trim();
                        String debitAcName = ftsRemote.getCustName(entity.getDebitAccountNo().trim());
                        debitAcName = debitAcName.replaceAll("[\\W_]", " ");
                        debitAcName = debitAcName.length() > 50 ? debitAcName.substring(0, 50) : debitAcName;

                        String singleInstrument = axisChnlId + "^" + axisCorpId + "^" + axisUserUpload + "^"
                                + axisUserAuthOne + "^" + axisUserAuthTwo + "^^^^" + paymentType + "^"
                                + axisProductId + "^^" + beneficiaryName + "^" + crAcno + "^"
                                + beneficiaryCode + "^" + amount + "^" + ifscCode + "^" + axisSubMemberIfsc
                                + "^" + debitAcName + "^" + debitAcNo + "^" + debitAcNo.substring(2, 4) + "^^^"
                                + beneficiaryName + "^^^^^^^^" + parentDrAccount + "^^^^" + uCustNo + "^^^^^^^^^^^^^^^^^^^^^^^^^^^^"
                                + "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^" + "\n";

                        fw.write(singleInstrument);
                        //Preparation of all UniqueRefNo
                        if (uniqueRefNo.equals("")) {
                            uniqueRefNo = "\'" + uCustNo + "\'";
                        } else {
                            uniqueRefNo = uniqueRefNo + ",\'" + uCustNo + "\'";
                        }
                    }
                    fw.close();
                    //File name logging
                    int n = entityManager.createNativeQuery("update neft_ow_details set outward_file_name='" + fileName + "' where "
                            + "uniquecustomerrefno in(" + uniqueRefNo + ")").executeUpdate();
                    if (n <= 0) {
                        throw new Exception("Problem In Outward File Name Logging.");
                    }
                } else if (neftBankName.equalsIgnoreCase("sbi")) {
                    System.out.println("In SBI Outward Processing.");
                    sendOutwardMessages(resultList, neftAutoObj);
                }
                //Updating the status of instrument.
                for (NeftOwDetails entity : resultList) {
                    NeftOwDetails obj = neftOwDetailsDAO.getSingleNeftOwDetailsInstrument(entity.getUniqueCustomerRefNo());
                    if (obj != null) {
                        obj.setStatus("I");
                        if (neftBankName.equalsIgnoreCase("yes")) {
                            obj.setDetails("Awaiting Response From YES Bank.");
                        } else if (neftBankName.equalsIgnoreCase("icici")) {
                            obj.setDetails("Awaiting Response From ICICI Bank.");
                        } else if (neftBankName.equalsIgnoreCase("idbi")) {
                            obj.setDetails("Awaiting Response From IDBI Bank.");
                        } else if (neftBankName.equalsIgnoreCase("hdfc")) {
                            obj.setDetails("Awaiting Response From HDFC Bank.");
                        } else if (neftBankName.equalsIgnoreCase("axis")) {
                            obj.setDetails("Awaiting Response From AXIS Bank.");
                        } else if (neftBankName.equalsIgnoreCase("sbi")) {
                            obj.setDetails("Awaiting Response From SBI Bank.");
                        }
                        neftOwDetailsDAO.update(entity);
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
    }

    public void generateYesBankOutwardExcelFile(String parentDrAccount, String bankCode, String owLocalFilePath, List<NeftOwDetails> resultList) throws Exception {
        String customerCode = "", drCustName = "", beneName = "";
        SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        try {
            if (bankCode.equalsIgnoreCase("ucbb")) {
                customerCode = "UCBBASTI";
            }

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Outward Data");

            /**
             * *Header Line**
             */
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("H");

            cell = row.createCell(1);
            cell.setCellValue(dmy.format(new Date()));

            cell = row.createCell(2);
            cell.setCellValue(customerCode);

            /**
             * *Data Line**
             */
            int rownum = 1;
            BigDecimal amount = new BigDecimal("0");
            for (int i = 0; i < resultList.size(); i++) {
                NeftOwDetails entity = resultList.get(i);
                row = sheet.createRow(rownum++);

                cell = row.createCell(0);
                cell.setCellValue("D");

                String msgType = "";
                if (entity.getPaymentType().equalsIgnoreCase("N")) {
                    msgType = "N06";
                } else if (entity.getPaymentType().equalsIgnoreCase("R")) {
                    msgType = "R41";
                } else {
                    msgType = "A";
                }
                cell = row.createCell(1);
                cell.setCellValue(msgType);
                cell = row.createCell(2);
                cell.setCellValue(parentDrAccount);

                cell = row.createCell(3);
                drCustName = ftsRemote.ftsGetCustName(entity.getDebitAccountNo());
                drCustName = drCustName.length() > 35 ? drCustName.substring(0, 35) : drCustName;
                cell.setCellValue(drCustName);
                cell = row.createCell(4);
                cell.setCellValue("");
                cell = row.createCell(5);
                cell.setCellValue("");

                cell = row.createCell(6);
                cell.setCellValue("");
                cell = row.createCell(7);
                cell.setCellValue(entity.getOutsideBankIfscCode());
                cell = row.createCell(8);
                cell.setCellValue(entity.getCreditAccountNo());

                cell = row.createCell(9);
                beneName = entity.getBeneficiaryName();
                beneName = beneName.length() > 35 ? beneName.substring(0, 35) : beneName;
                cell.setCellValue(beneName);
                cell = row.createCell(10);
                cell.setCellValue("");
                cell = row.createCell(11);
                cell.setCellValue("");

                cell = row.createCell(12);
                cell.setCellValue("");
                cell = row.createCell(13);
                cell.setCellValue("");
                cell = row.createCell(14);
                cell.setCellValue(entity.getUniqueCustomerRefNo());

                cell = row.createCell(15);
                cell.setCellValue(entity.getPaymentDate());
                cell = row.createCell(16);
                cell.setCellValue(entity.getTxnAmt().toString());
                cell = row.createCell(17);
                cell.setCellValue("RTGS NEFT PAYMENT " + bankCode.toUpperCase());

                cell = row.createCell(18);
                cell.setCellValue("");
                cell = row.createCell(19);
                cell.setCellValue("");
                cell = row.createCell(20);
                cell.setCellValue("");
                cell = row.createCell(21);
                cell.setCellValue("");

                amount = amount.add(entity.getTxnAmt());
            }

            row = sheet.createRow(rownum);
            cell = row.createCell(0);
            cell.setCellValue("F");

            cell = row.createCell(1);
            cell.setCellValue(resultList.size());

            cell = row.createCell(2);
            cell.setCellValue(amount.toString());

            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(owLocalFilePath + yyyymmdd.format(new Date()) + ".xlsx"));
            workbook.write(out);
            out.close();
            System.out.println("Outward file is generated successfully.");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void generateYesBankOutwardTextFile(String parentDrAccount, String owLocalFilePath,
            List<NeftOwDetails> resultList, String owLocalBkpPath, String neftBankName) throws Exception {
        String drCustName = "", beneName = "";
        NeftFileDetailsDAO neftFileDetailsDAO = new NeftFileDetailsDAO(entityManager);
        MbSmsSenderBankDetailDAO bankDetailDao = new MbSmsSenderBankDetailDAO(entityManager);
        SimpleDateFormat dMyy = new SimpleDateFormat("ddMMyyyy");
        NumberFormat formatter = new DecimalFormat("#.##");
        String onlyAlphabetRegex = "[A-Za-z]*";
        try {
            List dataList = entityManager.createNativeQuery("select ifnull(neft_ow_bank_name,'') as neft_ow_bank_name,"
                    + "bank_code from mb_sms_sender_bank_detail").getResultList();
            if (dataList.isEmpty()) {
                throw new Exception("Please define neft_ow_bank_name in sender bank detail");
            }
            Vector ele = (Vector) dataList.get(0);
            String owBankName = ele.get(0).toString().trim();
            String bankCode = ele.get(1).toString().trim();
            if (owBankName.equals("") || !owBankName.matches(onlyAlphabetRegex)) {
                throw new Exception("Please define neft_ow_bank_name in sender bank detail");
            }
            owBankName = owBankName.length() > 24 ? owBankName.substring(0, 24) : owBankName;

            Date curDateTime = new Date();
            Date currentDt = ymd.parse(ymd.format(curDateTime));
            Long seqNo = neftFileDetailsDAO.getMaxSrl(currentDt);
//            Long seqNo = ftsRemote.getMaxSrNoNeftFileDetails(ymd.format(curDateTime));

            //File name length should not exceed 35 characters.
            String fileName = owBankName + dMyy.format(curDateTime)
                    + ParseFileUtil.addTrailingZeros(seqNo.toString(), 3) + ".txt";
            FileWriter fw = new FileWriter(owLocalFilePath + neftBankName.toLowerCase() + "/" + fileName);

            //Logging of generated file with seq no.
            NeftFileDetails fileEntity = new NeftFileDetails();
            NeftFileDetailsPK fileEntityPK = new NeftFileDetailsPK();
            fileEntityPK.setFileDate(currentDt);
            fileEntityPK.setSeqNo(seqNo);
            fileEntity.setNeftFileDetailsPK(fileEntityPK);
            fileEntity.setFileName(fileName);
            fileEntity.setGenTime(new Date());
            neftFileDetailsDAO.save(fileEntity);
//            ftsRemote.neftFileLogging(ymd.format(curDateTime), 1l, fileName);

            //Header Line
            String headerFileName = fileName.substring(0, fileName.indexOf("."));
            headerFileName = headerFileName.length() > 20 ? headerFileName.substring(0, 20)
                    : ParseFileUtil.addSuffixSpaces(headerFileName, 20);
            String singleLine = "H" + dmy.format(curDateTime) + headerFileName + "\n";
            fw.write(singleLine);
            //Data Line
            BigDecimal amount = new BigDecimal("0");
            String uniqueRefNo = "";
            for (int i = 0; i < resultList.size(); i++) {
                NeftOwDetails entity = resultList.get(i);
                String msgType = "";
                if (entity.getPaymentType().equalsIgnoreCase("N")) {
                    msgType = "N06";
                } else if (entity.getPaymentType().equalsIgnoreCase("R")) {
                    msgType = "R41";
                } else {
                    msgType = "A";
                }
                drCustName = ftsRemote.ftsGetCustName(entity.getDebitAccountNo());
                drCustName = drCustName.replaceAll("[\\W_]", " ");
                drCustName = drCustName.length() > 35 ? drCustName.substring(0, 35) : drCustName;
                beneName = entity.getBeneficiaryName();
                beneName = beneName.length() > 35 ? beneName.substring(0, 35) : beneName;

                amount = amount.add(entity.getTxnAmt());
                //Addiotion on 24/06/2019
                String remitterAccountNo = "";
                List<MbSmsSenderBankDetail> bankList = bankDetailDao.getBankAndSenderDetail();
                if (bankList.isEmpty()) {
                    throw new ApplicationException("Please define bank detail.");
                }
                MbSmsSenderBankDetail bankDetail = bankList.get(0);
                String outwardBankCode = bankDetail.getBankCode();
                if (outwardBankCode.equalsIgnoreCase("ucbh")) { //For bhadohi only
                    remitterAccountNo = entity.getDebitAccountNo().trim();
                }
                //Addition end here

                singleLine = "D" + ParseFileUtil.addSuffixSpaces(msgType, 3) + ParseFileUtil.addTrailingZeros(parentDrAccount, 15)
                        + ParseFileUtil.addSuffixSpaces(drCustName, 35) + ParseFileUtil.addSuffixSpaces("", 105)
                        + entity.getOutsideBankIfscCode()
                        + ParseFileUtil.addSuffixSpaces(entity.getCreditAccountNo(), 34) + ParseFileUtil.addSuffixSpaces(beneName, 35)
                        + ParseFileUtil.addSuffixSpaces("", 140) + ParseFileUtil.addTrailingZeros(entity.getUniqueCustomerRefNo(), 16)
                        + entity.getPaymentDate() + ParseFileUtil.addTrailingZeros(entity.getTxnAmt().toString(), 14)
                        + ParseFileUtil.addSuffixSpaces("RTGS NEFT PAYMENT BY " + bankCode.toUpperCase(), 27)
                        + ParseFileUtil.addSuffixSpaces(remitterAccountNo, 33) + ParseFileUtil.addSuffixSpaces("", 99) + "\n";

                fw.write(singleLine);
                //Preparation of all UniqueRefNo
                if (uniqueRefNo.equals("")) {
                    uniqueRefNo = "\'" + entity.getUniqueCustomerRefNo() + "\'";
                } else {
                    uniqueRefNo = uniqueRefNo + ",\'" + entity.getUniqueCustomerRefNo() + "\'";
                }
            }
            //Footer Line
            singleLine = "F" + ParseFileUtil.addTrailingZeros(String.valueOf(resultList.size()), 5)
                    + ParseFileUtil.addTrailingZeros(formatter.format(amount), 14) + "\n";
            fw.write(singleLine);
            fw.close();
            //Writing into outward backup folder
            createFilesBackup(owLocalFilePath + neftBankName.toLowerCase() + "/", owLocalBkpPath + neftBankName.toLowerCase() + "/");
            //File name logging
            int n = entityManager.createNativeQuery("update neft_ow_details set outward_file_name='" + fileName + "' where "
                    + "uniquecustomerrefno in(" + uniqueRefNo + ")").executeUpdate();
            if (n <= 0) {
                throw new Exception("Problem In Outward File Name Logging.");
            }
            System.out.println("YesBank-H2H-Outward file is generated successfully.........");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void iwEftProcess(CbsAutoNeftDetails neftAutoObj) throws Exception {
        try {
            MbSmsSenderBankDetailDAO bankDetailDao = new MbSmsSenderBankDetailDAO(entityManager);
            List<MbSmsSenderBankDetail> bankList = bankDetailDao.getBankAndSenderDetail();
            if (bankList.isEmpty()) {
                throw new ApplicationException("Please define bank detail.");
            }
            MbSmsSenderBankDetail bankDetail = bankList.get(0);

            if (!(neftAutoObj.getNeftBankName().trim().equalsIgnoreCase("idbi")
                    || neftAutoObj.getNeftBankName().trim().equalsIgnoreCase("sbi")
                    || (neftAutoObj.getNeftBankName().trim().equalsIgnoreCase("yes")
                    && (bankDetail.getBankCode().trim().equalsIgnoreCase("icub")
                    || bankDetail.getBankCode().trim().equalsIgnoreCase("onkr")
                    || bankDetail.getBankCode().trim().equalsIgnoreCase("ucbh")
                    || bankDetail.getBankCode().trim().equalsIgnoreCase("ucbb")
                    || bankDetail.getBankCode().trim().equalsIgnoreCase("bucb")
                    || bankDetail.getBankCode().trim().equalsIgnoreCase("fucb")
                    || bankDetail.getBankCode().trim().equalsIgnoreCase("gnsb")
                    || bankDetail.getBankCode().trim().equalsIgnoreCase("kucb")
                    || bankDetail.getBankCode().trim().equalsIgnoreCase("dcbk")
                    || bankDetail.getBankCode().trim().equalsIgnoreCase("vscb")
                    || bankDetail.getBankCode().trim().equalsIgnoreCase("shml")
                    || bankDetail.getBankCode().trim().equalsIgnoreCase("khat")
                    || bankDetail.getBankCode().trim().equalsIgnoreCase("rcbl")))
                    || (neftAutoObj.getNeftBankName().trim().equalsIgnoreCase("ibl")
                    && neftAutoObj.getIwFileType().trim().equals("")))) {   //For Other than IDBI Bank,Yes Fantal Inward(Innovative) And SBI.
                try {
                    String iwRemoteFilePath = neftAutoObj.getIwRemoteFilePath().trim();
                    String iwLocalDir = neftAutoObj.getIwLocalFilePath().trim()
                            + neftAutoObj.getNeftBankName().trim().toLowerCase() + "/";
                    String iwRemoteDir = iwRemoteFilePath.substring(0, iwRemoteFilePath.lastIndexOf("/") + 1);
                    String filePattern = iwRemoteFilePath.substring(iwRemoteFilePath.lastIndexOf("/") + 1);
                    try {
                        download(neftAutoObj, iwLocalDir, iwRemoteDir, filePattern);
                    } catch (Exception ex) {
                        System.out.println("Problem in downloading the inward files." + ex.getMessage());
                    }
                } catch (Exception ex) {
                    System.out.println("Sftp connection not found in inward for-->" + neftAutoObj.getHostName().trim());
                }
            }

//            List cbsBankDaysList = entityManager.createNativeQuery("SELECT DATE FROM cbs_bankdays "
//                    + "WHERE DATE = '" + ymd.format(new Date()) + "' AND DAYBEGINFLAG='Y' AND "
//                    + "DAYENDFLAG='N'").getResultList();
//            if (!cbsBankDaysList.isEmpty()) {
                parseIwFile(neftAutoObj);
           // }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void iwReturnProcess(CbsAutoNeftDetails neftAutoObj) throws Exception {
        try {
            MbSmsSenderBankDetailDAO bankDetailDao = new MbSmsSenderBankDetailDAO(entityManager);
            List<MbSmsSenderBankDetail> bankList = bankDetailDao.getBankAndSenderDetail();
            if (bankList.isEmpty()) {
                throw new ApplicationException("Please define bank detail.");
            }
            MbSmsSenderBankDetail bankDetail = bankList.get(0);
            String bankCode = bankDetail.getBankCode();

//            List cbsBankDaysList = entityManager.createNativeQuery("SELECT DATE FROM cbs_bankdays "
//                    + "WHERE DATE = '" + ymd.format(new Date()) + "' AND DAYBEGINFLAG='Y' AND "
//                    + "DAYENDFLAG='N'").getResultList();
//            if (!cbsBankDaysList.isEmpty()) {
                if (neftAutoObj.getNeftBankName().trim().equalsIgnoreCase("yes")
                        && (bankCode.equalsIgnoreCase("icub")
                        || bankCode.equalsIgnoreCase("vscb")
                        || bankCode.equalsIgnoreCase("onkr")
                        || bankCode.equalsIgnoreCase("shml")
                        || bankCode.equalsIgnoreCase("khat")
                        || bankCode.equalsIgnoreCase("rcbl")
                        || bankCode.equalsIgnoreCase("bucb")
                        || bankCode.equalsIgnoreCase("fucb")
                        || bankCode.equalsIgnoreCase("ucbh")
                        || bankCode.equalsIgnoreCase("hcbl")
                        || bankCode.equalsIgnoreCase("vacb"))) { //Innovative
                    generateYesBankIwReturnFiles(neftAutoObj);
                } else if (neftAutoObj.getNeftBankName().trim().equalsIgnoreCase("idbi")) {
                    String owComLocalFilePath = neftAutoObj.getOwLocalFilePath().trim();
                    String owLocalFilePath = owComLocalFilePath.substring(0, owComLocalFilePath.lastIndexOf("/") + 1);

                    generateIwNeftRtgsReturn(owLocalFilePath, neftAutoObj);
                } else if (neftAutoObj.getNeftBankName().trim().equalsIgnoreCase("sbi")) {
                    generateSBINeftRtgsReturn(neftAutoObj);
                } else if (neftAutoObj.getNeftBankName().trim().equalsIgnoreCase("ibl")) {
//                    generateIblIwReturnFiles(neftAutoObj);
                }
            //}
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private void parseIwFile(CbsAutoNeftDetails neftAutoObj) throws Exception {
        try {
            String neftBankName = neftAutoObj.getNeftBankName().trim();
            File dir = new File(neftAutoObj.getIwLocalFilePath().trim() + neftBankName.toLowerCase() + "/");
            File[] files = dir.listFiles();

            Arrays.sort(files, new Comparator<File>() {
                public int compare(File f1, File f2) {
                    return Long.compare(f1.lastModified(), f2.lastModified());
                }
            });

            if (neftBankName.equalsIgnoreCase("yes")) {
                if (neftAutoObj.getIwFileType().equalsIgnoreCase("txt")) {
                    System.out.println("In Yes Bank IW Text Processing");
                    yesBankInwardProcess(files, neftAutoObj);      //New txt format
                } else if (neftAutoObj.getIwFileType().equalsIgnoreCase("xls")) {
                    System.out.println("In Yes Bank IW XLS Processing");
                    yesBankProcess(files, neftAutoObj);            //Old xls format - Currently Running
                } else if (neftAutoObj.getIwFileType().equalsIgnoreCase("pghad")) {
                    System.out.println("In Yes Bank IW PGHAD Processing");
                    yesBankPghadProcess(files, neftAutoObj);       //Pratap Ghad Format
                }
            } else if (neftBankName.equalsIgnoreCase("icici")
                    && neftAutoObj.getIwFileType().equalsIgnoreCase("txt")) {
                System.out.println("In ICICI IW Text Processing");
                iciciBankInwardProcess(files, neftAutoObj);
            } else if (neftBankName.equalsIgnoreCase("hdfc")
                    && neftAutoObj.getIwFileType().equalsIgnoreCase("csv")) {
                System.out.println("In Hdfc IW CSV Processing");
                hdfcBankInwardProcess(files, neftAutoObj);
            } else if (neftBankName.equalsIgnoreCase("idbi")
                    && neftAutoObj.getIwFileType().equalsIgnoreCase("xml")) {
                System.out.println("In IDBI IW XML Processing");
                idbiInwardProcess(files, neftAutoObj.getIwLocalFilePath().trim() + neftBankName.toLowerCase() + "/", neftAutoObj);
            } else if (neftBankName.equalsIgnoreCase("sbi")) {
                System.out.println("In SBI IW Processing");
                sbiInwardProcess(neftAutoObj);
            } else if (neftBankName.equalsIgnoreCase("ibl")) {
                System.out.println("In IBL IW XLS Processing");
                iblBankInwardProcess(files, neftAutoObj);
            }
            if (!neftBankName.equalsIgnoreCase("sbi")) {
                createBackupAndThenRemoveFile(neftAutoObj.getIwLocalFilePath().trim() + neftBankName.toLowerCase() + "/",
                        neftAutoObj.getIwLocalFileBackupPath().trim() + neftBankName.toLowerCase() + "/");
            }
        } catch (Exception ex) {
//            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
    }

    public void iwFileConclusionAfterProcessing(String result) {
        if (!result.equalsIgnoreCase("true")) {
            System.out.println(result);
            return;
        }
        System.out.println("Data has been processed successfully. Please check the report.");
    }

    private void createBackupAndThenRemoveFile(String localIwFileDir, String localIwBackupDir) throws IOException {
        try {
            FileInputStream fis = null;
            FileOutputStream fos = null;

            File dir = new File(localIwFileDir);
            File[] files = dir.listFiles();
            for (File file : files) {
                fis = new FileInputStream(file);
                fos = new FileOutputStream(localIwBackupDir + file.getName());

                byte[] buffer = new byte[1024];
                int length;
                //Copy the file content in bytes 
                while ((length = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }
                //Delete the original file
                file.delete();
                fos.close();
                fis.close();
            }
        } catch (Exception ex) {
            throw new IOException(ex.getMessage());
        }
    }

    private void createFilesBackup(String localIwFileDir, String localIwBackupDir) throws IOException {
        try {
            FileInputStream fis = null;
            FileOutputStream fos = null;

            File dir = new File(localIwFileDir);
            File[] files = dir.listFiles();
            for (File file : files) {
                fis = new FileInputStream(file);
                fos = new FileOutputStream(localIwBackupDir + file.getName());

                byte[] buffer = new byte[1024];
                int length;
                //Copy the file content in bytes 
                while ((length = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }
//                //Delete the original file
//                file.delete();
                fos.close();
                fis.close();
            }
        } catch (Exception ex) {
            throw new IOException(ex.getMessage());
        }
    }

    private void yesBankInwardProcess(File[] files, CbsAutoNeftDetails neftAutoObj) throws Exception {
        try {
            for (File file : files) {
                MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
                String fileContentType = mimeTypesMap.getContentType(file.getName());
                System.out.println("File Content Type = " + fileContentType);
                if (fileContentType.equals("text/plain") || fileContentType.equals("application/octet-stream")) {
                    List<ExcelReaderPojo> pojoList = new ArrayList<ExcelReaderPojo>();
                    try {
                        pojoList = ParseFileUtil.parseYesBankInwardTxtFile(file);
                    } catch (IOException ex) {
                        System.out.println("Invalid File Format. " + ex.getMessage());
                    } catch (ApplicationException ex) {
                        System.out.println("Invalid File Format. " + ex.getMessage());
                    }
                    String result = remote.neftRtgsUploadProcess(pojoList, "90", "System",
                            neftAutoObj.getIwHead(), neftAutoObj.getProcess(), neftAutoObj.getNeftBankName());
                    iwFileConclusionAfterProcessing(result);
                } else {
                    System.out.println("Invalid File Extension !");
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private void yesBankProcess(File[] files, CbsAutoNeftDetails neftAutoObj) throws Exception {
        try {
            for (File file : files) {
                MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
                String fileContentType = mimeTypesMap.getContentType(file.getName());
                if (fileContentType.equals("application/xls")
                        || fileContentType.equals("application/vnd.ms-excel")
                        || fileContentType.equals("application/octet-stream")) {
                    List<ExcelReaderPojo> pojoList = new ArrayList<ExcelReaderPojo>();
                    try {
                        pojoList = ParseFileUtil.parseExcel(file);
                    } catch (IOException ex) {
                        pojoList = ParseFileUtil.parseXML(file);
                    } catch (ApplicationException ex) {
                        System.out.println("Invalid File Format.");
                    }
                    String result = remote.neftRtgsUploadProcess(pojoList, "90", "System",
                            neftAutoObj.getIwHead(), neftAutoObj.getProcess(), neftAutoObj.getNeftBankName());
                    iwFileConclusionAfterProcessing(result);
                } else {
                    System.out.println("Invalid File Extension !");
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private void yesBankPghadProcess(File[] files, CbsAutoNeftDetails neftAutoObj) throws Exception {
        try {
            for (File file : files) {
                MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
                String fileContentType = mimeTypesMap.getContentType(file.getName());
                if (fileContentType.equals("application/xls")
                        || fileContentType.equals("application/vnd.ms-excel")
                        || fileContentType.equals("application/octet-stream")
                        || fileContentType.equals("application/xsl")) {
                    List<ExcelReaderPojo> pojoList = new ArrayList<ExcelReaderPojo>();
                    try {
                        pojoList = ParseFileUtil.parsePghadExcel(file);
                    } catch (Exception ex) {
                        System.out.println("Invalid File Format. " + ex.getMessage());
                    }
                    String result = remote.neftRtgsUploadProcess(pojoList, "90", "System",
                            neftAutoObj.getIwHead(), neftAutoObj.getProcess(), neftAutoObj.getNeftBankName());
                    iwFileConclusionAfterProcessing(result);
                } else {
                    System.out.println("Invalid File Extension !");
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private void iciciBankInwardProcess(File[] files, CbsAutoNeftDetails neftAutoObj) throws Exception {
        try {
            for (File file : files) {
                MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
                String fileContentType = mimeTypesMap.getContentType(file.getName());
                System.out.println("Inward File Content Type::" + fileContentType);
                if (fileContentType.equals("text/plain")
                        || fileContentType.equals("application/octet-stream")) {
                    List<ExcelReaderPojo> pojoList = new ArrayList<ExcelReaderPojo>();
                    try {
                        pojoList = ParseFileUtil.parseIciciInwardTxtFile(file);
                    } catch (Exception ex) {
                        System.out.println("Invalid File Format. " + ex.getMessage());
                    }
                    String result = remote.neftRtgsUploadProcess(pojoList, "90", "System",
                            neftAutoObj.getIwHead(), neftAutoObj.getProcess(), neftAutoObj.getNeftBankName());
                    iwFileConclusionAfterProcessing(result);
                } else {
                    System.out.println("Invalid File Extension !");
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private void iblBankInwardProcess(File[] files, CbsAutoNeftDetails neftAutoObj) throws Exception {
        try {
            for (File file : files) {
                MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
                String fileContentType = mimeTypesMap.getContentType(file.getName());
                System.out.println("Inward File Content Type::" + fileContentType);
                if (fileContentType.equals("application/xls")
                        || fileContentType.equals("application/xsl")
                        || fileContentType.equals("application/vnd.ms-excel")
                        || fileContentType.equals("application/octet-stream")) {
                    List<ExcelReaderPojo> pojoList = new ArrayList<ExcelReaderPojo>();
                    try {
                        pojoList = ParseFileUtil.parseIBLInwardExcel(file);
                    } catch (Exception ex) {
                        System.out.println("Invalid File Format. " + ex.getMessage());
                    }
                    String result = remote.neftRtgsUploadProcess(pojoList, "90", "System",
                            neftAutoObj.getIwHead(), neftAutoObj.getProcess(), neftAutoObj.getNeftBankName());
                    iwFileConclusionAfterProcessing(result);
                } else {
                    System.out.println("Invalid File Extension !");
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private void hdfcBankInwardProcess(File[] files, CbsAutoNeftDetails neftAutoObj) throws Exception {
        try {
            for (File file : files) {
                MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
                String fileContentType = mimeTypesMap.getContentType(file.getName());
                if (fileContentType.equals("text/csv")
                        || fileContentType.equals("application/octet-stream")
                        || fileContentType.equals("application/vnd.ms-excel")) {
                    List<ExcelReaderPojo> pojoList = new ArrayList<ExcelReaderPojo>();
                    try {
                        pojoList = ParseFileUtil.parseSNSBCSV(file);
                    } catch (IOException ex) {
                        System.out.println("Invalid File Format. :" + ex.getMessage());
                    } catch (ApplicationException ex) {
                        System.out.println("Invalid File Format. :" + ex.getMessage());
                    }

                    String result = remote.neftRtgsUploadProcess(pojoList, "90", "System",
                            neftAutoObj.getIwHead(), neftAutoObj.getProcess(), neftAutoObj.getNeftBankName());
                    iwFileConclusionAfterProcessing(result);
                } else {
                    System.out.println("Invalid File Extension !");
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void owReportProcess(CbsAutoNeftDetails neftAutoObj) throws Exception {
        try {
            if (neftAutoObj.getNeftBankName().trim().equalsIgnoreCase("IBL")) {
                List cbsBankDaysList = entityManager.createNativeQuery("SELECT DATE FROM cbs_bankdays "
                        + "WHERE DATE = '" + ymd.format(new Date()) + "' AND DAYBEGINFLAG='Y' AND "
                        + "DAYENDFLAG='N'").getResultList();
                if (!cbsBankDaysList.isEmpty()) {
                    Thread.sleep(120000);
                    iblPaymentEnquiry(neftAutoObj);
                }
            } else {
                if (!(neftAutoObj.getNeftBankName().trim().equalsIgnoreCase("idbi")
                        || neftAutoObj.getNeftBankName().trim().equalsIgnoreCase("yes")
                        || neftAutoObj.getNeftBankName().trim().equalsIgnoreCase("sbi"))) {   //For Non-IDBI,Yes,SBI Bank.

                    String remoteReportPath = neftAutoObj.getRemoteReportPath().trim();
                    String localReportDir = neftAutoObj.getLocalReportPath().trim();
                    String remoteDir = remoteReportPath.substring(0, remoteReportPath.lastIndexOf("/") + 1);
                    String filePattern = remoteReportPath.substring(remoteReportPath.lastIndexOf("/") + 1);
                    try {
                        download(neftAutoObj, localReportDir + "/" + neftAutoObj.getNeftBankName().trim().toLowerCase() + "/",
                                remoteDir, filePattern);
                    } catch (Exception ex) {
                        System.out.println("Problem in downloading the outward report files." + ex.getMessage());
                    }
                }

                if (!(neftAutoObj.getNeftBankName().trim().equalsIgnoreCase("idbi")
                        || neftAutoObj.getNeftBankName().trim().equalsIgnoreCase("sbi"))) {
                    List cbsBankDaysList = entityManager.createNativeQuery("SELECT DATE FROM cbs_bankdays "
                            + "WHERE DATE = '" + ymd.format(new Date()) + "' AND DAYBEGINFLAG='Y' AND "
                            + "DAYENDFLAG='N'").getResultList();
                    if (!cbsBankDaysList.isEmpty()) {
                        parseReportFile(neftAutoObj);
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private void parseReportFile(CbsAutoNeftDetails neftAutoObj) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            File dir = new File(neftAutoObj.getLocalReportPath().trim() + neftAutoObj.getNeftBankName().trim().toLowerCase() + "/");
            File[] files = dir.listFiles();

            Arrays.sort(files, new Comparator<File>() {
                public int compare(File f1, File f2) {
                    return Long.compare(f1.lastModified(), f2.lastModified());
                }
            });

            String neftBankName = neftAutoObj.getNeftBankName().trim();
            if (neftBankName.equalsIgnoreCase("icici")) {
                System.out.println("In ICICI Report Processing");
                iciciBankOwReportProcess(files, neftAutoObj);
            } else if (neftBankName.equalsIgnoreCase("yes")) {
                System.out.println("In Yes Bank Report Processing");
                yesBankOwReportProcess(files, neftAutoObj);
            } else if (neftBankName.equalsIgnoreCase("hdfc")) {
                System.out.println("In Hdfc Report Processing");
                hdfcBankOwReportProcess(files, neftAutoObj);
            } else if (neftBankName.equalsIgnoreCase("axis")) {
                System.out.println("In AXIS Report Processing");
                axisBankOwReportProcess(files, neftAutoObj);
            }
            createBackupAndThenRemoveFile(neftAutoObj.getLocalReportPath().trim() + "/" + neftAutoObj.getNeftBankName().trim().toLowerCase() + "/",
                    neftAutoObj.getLocalReportBackupPath().trim() + "/" + neftAutoObj.getNeftBankName().trim().toLowerCase() + "/");
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
    }

    private void iciciBankOwReportProcess(File[] files, CbsAutoNeftDetails neftAutoObj) throws Exception {
        try {
            NeftOwDetailsDAO neftOwDetailsDAO = new NeftOwDetailsDAO(entityManager);
            for (File file : files) {
                String fileContentType = URLConnection.guessContentTypeFromName(file.getName());
                if (fileContentType.equals("text/plain")) {
                    List<H2HOwReportPojo> pojoList = new ArrayList<H2HOwReportPojo>();
                    try {
                        pojoList = ParseFileUtil.parseIciciReportTextFile(file);
                    } catch (IOException ex) {
                        System.out.println("Invalid File Format.");
                    } catch (ApplicationException ex) {
                        System.out.println("Invalid File Format.");
                    }
                    //Status updation after report file processing
                    if (!pojoList.isEmpty()) {
                        for (int i = 0; i < pojoList.size(); i++) {
                            H2HOwReportPojo obj = pojoList.get(i);
                            NeftOwDetails neftOwDetailsObj = neftOwDetailsDAO.getSingleNeftOwDetailsInstrument(obj.getuCustRefNo().trim());
                            if (neftOwDetailsObj != null) {
                                String status = obj.getStatus();
                                if (status.equalsIgnoreCase("P")) {
                                    if (!(neftOwDetailsObj.getStatus().equalsIgnoreCase("U")
                                            || neftOwDetailsObj.getStatus().equalsIgnoreCase("S"))) {
                                        neftOwDetailsObj.setStatus("S");
                                        neftOwDetailsObj.setDetails("Paid");
                                        System.out.println("Outward Report Process is Successfull.");
                                        neftOwDetailsObj.setCmsBankRefNo(obj.getCmsRefNo());
                                        String utrValue = obj.getUtrNo().trim();
                                        if (utrValue.contains(" ")) {
                                            neftOwDetailsObj.setReason(utrValue);
                                        } else {
                                            if (utrValue.length() != 22) {
                                                neftOwDetailsObj.setReason(utrValue);
                                            } else {
                                                neftOwDetailsObj.setUtrNo(utrValue);
                                            }
                                        }
                                        neftOwDetailsObj.setResponseUpdateTime(new Date());
                                        neftOwDetailsDAO.update(neftOwDetailsObj);
                                        //SMS Sending
                                        try {
                                            sendUtrSms(neftOwDetailsObj.getStatus(), neftOwDetailsObj, neftOwDetailsObj.getUtrNo(),
                                                    neftOwDetailsObj.getCmsBankRefNo());
                                        } catch (Exception ex) {
                                            System.out.println("Problem In H2H UTR updation message In ICICI-->" + ex.getMessage());
                                        }
                                    }
                                } else if (status.equalsIgnoreCase("C")) {
                                    if (!neftOwDetailsObj.getStatus().equalsIgnoreCase("U")) {
                                        String msg = reverseOutwardTransaction(neftOwDetailsObj, "90", "System", neftAutoObj);
                                        if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                                            if (neftOwDetailsObj.getStatus().equalsIgnoreCase("S")) {
                                                neftOwDetailsObj.setSuccessToFailureFlag("R");
                                            }
                                            neftOwDetailsObj.setStatus("U");
                                            neftOwDetailsObj.setDetails("Un-Paid");
                                            System.out.println("Outward Reversal is Successfull.");
                                            neftOwDetailsObj.setCmsBankRefNo(obj.getCmsRefNo());
                                            String utrValue = obj.getUtrNo().trim();
                                            if (utrValue.contains(" ")) {
                                                neftOwDetailsObj.setReason(utrValue);
                                            } else {
                                                if (utrValue.length() != 22) {
                                                    neftOwDetailsObj.setReason(utrValue);
                                                } else {
                                                    neftOwDetailsObj.setUtrNo(utrValue);
                                                }
                                            }
                                            neftOwDetailsObj.setResponseUpdateTime(new Date());
                                            neftOwDetailsDAO.update(neftOwDetailsObj);
                                            //Deaf Updation
                                            ftsRemote.lastTxnDateUpdation(neftOwDetailsObj.getDebitAccountNo().trim());
                                            //SMS Sending
                                            try {
                                                sendUtrSms(neftOwDetailsObj.getStatus(), neftOwDetailsObj, neftOwDetailsObj.getUtrNo(),
                                                        neftOwDetailsObj.getCmsBankRefNo());
                                            } catch (Exception ex) {
                                                System.out.println("Problem In H2H UTR updation message In ICICI-->" + ex.getMessage());
                                            }
                                        }
                                    }
                                } else {
                                    if (!(neftOwDetailsObj.getStatus().equalsIgnoreCase("U")
                                            || neftOwDetailsObj.getStatus().equalsIgnoreCase("S"))) {
                                        neftOwDetailsObj.setDetails(obj.getStatus().equalsIgnoreCase("A") ? "Awaiting From RBI"
                                                : obj.getStatus());

                                        String utrValue = obj.getUtrNo().trim();
                                        String tempUtr = "";
                                        if (utrValue.contains(" ")) {
                                            neftOwDetailsObj.setReason(utrValue);
                                        } else {
                                            if (utrValue.length() != 22) {
                                                neftOwDetailsObj.setReason(utrValue);
                                            } else {
                                                tempUtr = utrValue;
                                            }
                                        }
                                        //SMS Sending
                                        try {
                                            if (neftOwDetailsObj.getUtrNo().equals("") && !tempUtr.equals("")) {
                                                sendUtrSms("", neftOwDetailsObj, tempUtr, obj.getCmsRefNo());
                                            }
                                        } catch (Exception ex) {
                                            System.out.println("Problem In H2H UTR updation message In YES-->"
                                                    + ex.getMessage());
                                        }
                                        neftOwDetailsObj.setResponseUpdateTime(new Date());
                                        neftOwDetailsObj.setCmsBankRefNo(obj.getCmsRefNo());
                                        neftOwDetailsObj.setUtrNo(tempUtr);
                                        neftOwDetailsDAO.update(neftOwDetailsObj);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("Invalid File Extension !");
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private void yesBankOwReportProcess(File[] files, CbsAutoNeftDetails neftAutoObj) throws Exception {
        try {
            NeftOwDetailsDAO neftOwDetailsDAO = new NeftOwDetailsDAO(entityManager);
            for (File file : files) {
                MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
                String fileContentType = mimeTypesMap.getContentType(file.getName());
                if (fileContentType.equalsIgnoreCase("text/plain") || fileContentType.equalsIgnoreCase("application/octet-stream")) {
                    List<H2HOwReportPojo> pojoList = new ArrayList<H2HOwReportPojo>();
                    try {
                        pojoList = ParseFileUtil.parseYesBankReportTextFile(file);
                    } catch (IOException ex) {
                        System.out.println("Invalid File Format :" + ex.getMessage());
                    } catch (ApplicationException ex) {
                        System.out.println("Invalid File Format: " + ex.getMessage());
                    }
                    //Status updation after report file processing
                    if (!pojoList.isEmpty()) {
                        for (int i = 0; i < pojoList.size(); i++) {
                            H2HOwReportPojo obj = pojoList.get(i);
                            NeftOwDetails neftOwDetailsObj = neftOwDetailsDAO.
                                    getSingleNeftOwDetailsInstrument(obj.getuCustRefNo().trim().substring(1));
                            if (neftOwDetailsObj != null) {
                                String status = obj.getStatus().toUpperCase();
                                if (status.contains("REJECTED") || status.contains("REVERSED")) {
                                    if (!neftOwDetailsObj.getStatus().equalsIgnoreCase("U")) {
                                        String msg = reverseOutwardTransaction(neftOwDetailsObj, "90",
                                                "System", neftAutoObj);
                                        if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                                            if (neftOwDetailsObj.getStatus().equalsIgnoreCase("S")) {
                                                neftOwDetailsObj.setSuccessToFailureFlag("R");
                                            }
                                            neftOwDetailsObj.setStatus("U");
                                            neftOwDetailsObj.setDetails("Un-Paid");
                                            neftOwDetailsObj.setReason(obj.getRejectReason());

                                            neftOwDetailsObj.setUtrNo(obj.getUtrNo());
                                            neftOwDetailsObj.setResponseUpdateTime(new Date());
                                            neftOwDetailsDAO.update(neftOwDetailsObj);
                                            System.out.println("Outward Reversal is Successfull.");
                                            //Deaf Updation
                                            ftsRemote.lastTxnDateUpdation(neftOwDetailsObj.getDebitAccountNo().trim());
                                            //SMS Sending
                                            try {
                                                sendUtrSms(neftOwDetailsObj.getStatus(), neftOwDetailsObj, neftOwDetailsObj.getUtrNo(),
                                                        neftOwDetailsObj.getCmsBankRefNo());
                                            } catch (Exception ex) {
                                                System.out.println("Problem In H2H UTR updation message In YES-->"
                                                        + ex.getMessage());
                                            }
                                        }
                                    }
                                } else if (status.contains("COMPLETE")) {
                                    if (!(neftOwDetailsObj.getStatus().equalsIgnoreCase("U")
                                            || neftOwDetailsObj.getStatus().equalsIgnoreCase("S"))) {
                                        neftOwDetailsObj.setStatus("S");
                                        neftOwDetailsObj.setDetails("Paid");
                                        neftOwDetailsObj.setReason("");

                                        neftOwDetailsObj.setUtrNo(obj.getUtrNo());
                                        neftOwDetailsObj.setResponseUpdateTime(new Date());
                                        neftOwDetailsDAO.update(neftOwDetailsObj);
                                        //SMS Sending
                                        try {
                                            sendUtrSms(neftOwDetailsObj.getStatus(), neftOwDetailsObj, neftOwDetailsObj.getUtrNo(),
                                                    neftOwDetailsObj.getCmsBankRefNo());
                                        } catch (Exception ex) {
                                            System.out.println("Problem In H2H UTR updation message In YES-->"
                                                    + ex.getMessage());
                                        }
                                    }
                                } else {
                                    String resStatus = obj.getStatus();
                                    neftOwDetailsObj.setDetails(resStatus);
                                    neftOwDetailsObj.setResponseUpdateTime(new Date());
                                    //SMS Sending
                                    try {
                                        if (neftOwDetailsObj.getUtrNo().equals("") && !obj.getUtrNo().equals("")) {
                                            sendUtrSms("", neftOwDetailsObj, obj.getUtrNo(), obj.getCmsRefNo());
                                        }
                                    } catch (Exception ex) {
                                        System.out.println("Problem In H2H UTR updation message In YES-->"
                                                + ex.getMessage());
                                    }
                                    neftOwDetailsObj.setUtrNo(obj.getUtrNo());
                                    neftOwDetailsDAO.update(neftOwDetailsObj);
                                    System.out.println("In Else Part of Update After Report.");
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("Invalid File Extension !");
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private void hdfcBankOwReportProcess(File[] files, CbsAutoNeftDetails neftAutoObj) throws Exception {
        try {
            NeftOwDetailsDAO neftOwDetailsDAO = new NeftOwDetailsDAO(entityManager);
            for (File file : files) {
                List<H2HOwReportPojo> pojoList = new ArrayList<>();
                try {
                    pojoList = ParseFileUtil.parseHdfcReportTextFile(file);
                } catch (Exception ex) {
                    System.out.println("Hdfc Invalid File Format." + ex.getMessage());
                }
                //Status updation after report file processing
                if (!pojoList.isEmpty()) {
                    for (int i = 0; i < pojoList.size(); i++) {
                        H2HOwReportPojo obj = pojoList.get(i);
                        NeftOwDetails neftOwDetailsObj = neftOwDetailsDAO.getSingleNeftOwDetailsInstrument(obj.getuCustRefNo().trim());
                        if (neftOwDetailsObj != null) {
                            String status = obj.getStatus();
                            if (status.equalsIgnoreCase("E")) {
                                if (!(neftOwDetailsObj.getStatus().equalsIgnoreCase("S")
                                        || neftOwDetailsObj.getStatus().equalsIgnoreCase("U"))) {
                                    neftOwDetailsObj.setStatus("S");
                                    neftOwDetailsObj.setDetails("Paid");
                                    neftOwDetailsObj.setReason("");
                                    System.out.println("Outward Report Process is Successfull.");
                                    neftOwDetailsObj.setCmsBankRefNo(obj.getCmsRefNo());
                                    neftOwDetailsObj.setUtrNo(obj.getUtrNo());
                                    neftOwDetailsObj.setResponseUpdateTime(new Date());
                                    neftOwDetailsDAO.update(neftOwDetailsObj);
                                    //Sms Sending.
                                    try {
                                        sendUtrSms("S", neftOwDetailsObj, neftOwDetailsObj.getUtrNo(),
                                                neftOwDetailsObj.getCmsBankRefNo());
                                    } catch (Exception ex) {
                                        System.out.println(ex.getMessage());
                                    }
                                }
                            } else if (status.equalsIgnoreCase("R")) {
                                if (!neftOwDetailsObj.getStatus().equalsIgnoreCase("U")) {
                                    String msg = reverseOutwardTransaction(neftOwDetailsObj, "90", "System", neftAutoObj);
                                    if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                                        if (neftOwDetailsObj.getStatus().equalsIgnoreCase("S")) {
                                            neftOwDetailsObj.setSuccessToFailureFlag("R");
                                        }
                                        neftOwDetailsObj.setStatus("U");
                                        neftOwDetailsObj.setDetails("Un-Paid");
                                        neftOwDetailsObj.setReason(obj.getRejectReason());
                                        System.out.println("Outward Reversal is Successfull.");
                                        neftOwDetailsObj.setCmsBankRefNo(obj.getCmsRefNo());
                                        neftOwDetailsObj.setUtrNo(obj.getUtrNo());
                                        neftOwDetailsObj.setResponseUpdateTime(new Date());
                                        neftOwDetailsDAO.update(neftOwDetailsObj);
                                        //Deaf Updation
                                        ftsRemote.lastTxnDateUpdation(neftOwDetailsObj.getDebitAccountNo().trim());
                                        //Sms Sending.
                                        try {
                                            sendUtrSms("U", neftOwDetailsObj, neftOwDetailsObj.getUtrNo(),
                                                    neftOwDetailsObj.getCmsBankRefNo());
                                        } catch (Exception ex) {
                                            System.out.println(ex.getMessage());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private void axisBankOwReportProcess(File[] files, CbsAutoNeftDetails neftAutoObj) throws Exception {
        try {
            NeftOwDetailsDAO neftOwDetailsDAO = new NeftOwDetailsDAO(entityManager);
            for (File file : files) {
                String fileContentType = URLConnection.guessContentTypeFromName(file.getName());
                if (fileContentType.equals("text/plain")) {
                    List<H2HOwReportPojo> pojoList = new ArrayList<H2HOwReportPojo>();
                    try {
                        pojoList = ParseFileUtil.parseAxisReportTextFile(file);
                    } catch (IOException ex) {
                        System.out.println("Invalid File Format.");
                    } catch (ApplicationException ex) {
                        System.out.println("Invalid File Format.");
                    }
                    //Status updation after report file processing
                    if (!pojoList.isEmpty()) {
                        for (int i = 0; i < pojoList.size(); i++) {
                            H2HOwReportPojo obj = pojoList.get(i);
                            NeftOwDetails neftOwDetailsObj = neftOwDetailsDAO.getSingleNeftOwDetailsInstrument(obj.getuCustRefNo().trim());
                            if (neftOwDetailsObj != null) {
                                String status = obj.getStatus();
                                if (status.equalsIgnoreCase("PAID")) {
                                    if (!(neftOwDetailsObj.getStatus().equalsIgnoreCase("U")
                                            || neftOwDetailsObj.getStatus().equalsIgnoreCase("S"))) {
                                        neftOwDetailsObj.setStatus("S");
                                        neftOwDetailsObj.setDetails("Paid");
                                        neftOwDetailsObj.setReason("");
                                        System.out.println("Outward Report Process is Successfull.");
                                        neftOwDetailsObj.setUtrNo(obj.getUtrNo());
                                        neftOwDetailsObj.setResponseUpdateTime(new Date());
                                        neftOwDetailsDAO.update(neftOwDetailsObj);
                                        //SMS Sending
                                        try {
                                            sendUtrSms(neftOwDetailsObj.getStatus(), neftOwDetailsObj, neftOwDetailsObj.getUtrNo(),
                                                    neftOwDetailsObj.getCmsBankRefNo());
                                        } catch (Exception ex) {
                                            System.out.println("Problem In H2H UTR updation message In AXIS-->" + ex.getMessage());
                                        }
                                    }
                                } else if (status.equalsIgnoreCase("FAILED") || status.equalsIgnoreCase("RETURN")) {
                                    if (!neftOwDetailsObj.getStatus().equalsIgnoreCase("U")) {
                                        String msg = reverseOutwardTransaction(neftOwDetailsObj, "90", "System", neftAutoObj);
                                        if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                                            if (neftOwDetailsObj.getStatus().equalsIgnoreCase("S")) {
                                                neftOwDetailsObj.setSuccessToFailureFlag("R");
                                            }
                                            neftOwDetailsObj.setStatus("U");
                                            neftOwDetailsObj.setDetails("Un-Paid");
                                            System.out.println("Outward Reversal is Successfull.");
                                            neftOwDetailsObj.setReason(obj.getRejectReason().trim().equals("")
                                                    ? obj.getReversalReason().trim() : obj.getRejectReason().trim());
                                            neftOwDetailsObj.setResponseUpdateTime(new Date());
                                            neftOwDetailsDAO.update(neftOwDetailsObj);
                                            //Deaf Updation
                                            ftsRemote.lastTxnDateUpdation(neftOwDetailsObj.getDebitAccountNo().trim());
                                            //SMS Sending
                                            try {
                                                sendUtrSms(neftOwDetailsObj.getStatus(), neftOwDetailsObj, neftOwDetailsObj.getUtrNo(),
                                                        neftOwDetailsObj.getCmsBankRefNo());
                                            } catch (Exception ex) {
                                                System.out.println("Problem In H2H UTR updation message In ICICI-->" + ex.getMessage());
                                            }
                                        }
                                    }
                                } else {
                                    if (!(neftOwDetailsObj.getStatus().equalsIgnoreCase("U")
                                            || neftOwDetailsObj.getStatus().equalsIgnoreCase("S"))) {
                                        neftOwDetailsObj.setDetails(obj.getStatus());
                                        neftOwDetailsObj.setResponseUpdateTime(new Date());
                                        //SMS Sending
                                        try {
                                            if (neftOwDetailsObj.getUtrNo().equals("") && !obj.getUtrNo().equals("")) {
                                                sendUtrSms("", neftOwDetailsObj, obj.getUtrNo(), obj.getCmsRefNo());
                                            }
                                        } catch (Exception ex) {
                                            System.out.println("Problem In H2H UTR updation message In YES-->"
                                                    + ex.getMessage());
                                        }
                                        neftOwDetailsObj.setUtrNo(obj.getUtrNo());
                                        neftOwDetailsDAO.update(neftOwDetailsObj);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("Invalid File Extension !");
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * New Addition for IDBI Bank.
     */
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

    public void generateIDBIBankOutwardFiles(List<NeftOwDetails> resultList, String owLocalFilePath,
            CbsAutoNeftDetails neftAutoObj) throws Exception {
        SimpleDateFormat ymdms = new SimpleDateFormat("yyMMddHHmmssSSS");
        BranchMasterDAO branchMasterDAO = new BranchMasterDAO(entityManager);
        try {
            //Retrieve the common ifsc code from first object in the list.
            NeftOwDetails firstObj = resultList.get(0);
            BranchMaster branchMasterEntity = branchMasterDAO.getEntityByBrnCode(Integer.parseInt(ftsRemote.getCurrentBrnCode(firstObj.getDebitAccountNo().trim())));
            String branchMasterIfsc = branchMasterEntity.getIfscCode().trim();

            List list = entityManager.createNativeQuery("select ifnull(idbi_bank_code,'') as idbi_bank_code from "
                    + "mb_sms_sender_bank_detail").getResultList();
            if (list.isEmpty()) {
                throw new Exception("Please define idbi bank code in mb_sms_sender_bank_detail.");
            }
            Vector tempVec = (Vector) list.get(0);
            String idbiBankCode = tempVec.get(0).toString();
            if (idbiBankCode.trim().equals("") || idbiBankCode.trim().length() == 0) {
                throw new Exception("Please define idbi bank code in mb_sms_sender_bank_detail.");
            }

            boolean checkkPayType = false;
            for (int i = 0; i < resultList.size(); i++) {
                NeftOwDetails obj = resultList.get(i);
                if (obj.getPaymentType().equalsIgnoreCase("N")) {   //For Neft
                    checkkPayType = true;
                    break;
                }
            }
            //Writting of outward neft(N06) file.
            if (checkkPayType == true) {
                List<NeftOwDetails> onlyNeftData = new ArrayList<NeftOwDetails>();
                for (int i = 0; i < resultList.size(); i++) {
                    if (resultList.get(i).getPaymentType().equalsIgnoreCase("N")) {
                        onlyNeftData.add(resultList.get(i));
                    }
                }

                Integer neftFileLimit = Integer.parseInt(ftsRemote.getCodeByReportName("IDBI-NEFT-FILE-LIMIT"));
                List<List<NeftOwDetails>> fileList = new ArrayList<List<NeftOwDetails>>();
                for (int i = 0; i < onlyNeftData.size(); i = i + neftFileLimit) {
                    List<NeftOwDetails> subList;
                    if (i + neftFileLimit < onlyNeftData.size()) {
                        subList = onlyNeftData.subList(i, i + neftFileLimit);
                    } else {
                        subList = onlyNeftData.subList(i, onlyNeftData.size());
                    }
                    fileList.add(subList);
                }

                for (int z = 0; z < fileList.size(); z++) {
                    System.out.println("NEFT File List Number Is-->" + z + "\n");
                    List<NeftOwDetails> subList = fileList.get(z);
//                    String owNeftFileName = "N06" + branchMasterIfsc.substring(branchMasterIfsc.length() - 3) + ymdms.format(new Date()) + String.valueOf(z) + ".xml";
                    String owNeftFileName = "N06" + idbiBankCode.trim().toUpperCase() + ymdms.format(new Date()) + String.valueOf(z) + ".xml";
                    String uniqueRefNo = "";
                    Node node = getNodeForRootElement("NEFT");
                    for (int x = 0; x < subList.size(); x++) {
                        NeftOwDetails obj = subList.get(x);
                        N06 msg = new N06();
                        msg.setFTransaction_Reference_Number(obj.getUniqueCustomerRefNo());
                        msg.setFAmount(obj.getTxnAmt().toString());
                        msg.setFValue_Date(ymd.format(dmy.parse(obj.getPaymentDate())));
                        msg.setFSending_branchs_IFSC(branchMasterIfsc);
                        msg.setFSending_Customer_Account_Type("");
                        msg.setFSending_Customer_Account_Number(obj.getDebitAccountNo().trim());

                        String drCustName = ftsRemote.ftsGetCustName(obj.getDebitAccountNo());
                        drCustName = drCustName.replaceAll("[\\W_]", " ");
                        drCustName = drCustName.length() > 50 ? drCustName.substring(0, 50) : drCustName;
                        msg.setFSending_Customer_Account_Name(drCustName.trim());

                        msg.setFSending_Customer_Mobile_No_Email_Id(obj.getSenderCommModeType().trim());
                        msg.setFSending_Customer_Mobile_No_Email_Id_(obj.getSenderCommMode());

                        String remmitInfo = obj.getRemmitInfo().trim();
                        remmitInfo = remmitInfo.length() > 140 ? remmitInfo.substring(0, 140) : remmitInfo;
                        msg.setFOriginator_of_Remittance(remmitInfo);

                        msg.setFBeneficiary_branchs_IFSC(obj.getOutsideBankIfscCode().trim());
                        msg.setFBeneficiary_Customer_Account_Type("");
                        msg.setFBeneficiary_Customer_Account_Number(obj.getCreditAccountNo().trim());

                        String beneName = obj.getBeneficiaryName().trim();
                        beneName = beneName.replaceAll("[\\W_]", " ");
                        beneName = beneName.length() > 50 ? beneName.substring(0, 50) : beneName;
                        msg.setFBeneficiary_Customer_Account_Name(beneName);

                        msg.setFBeneficiary_Customer_Address("");
                        msg.setFSender_to_Receiver_Information("");

                        JAXBContext jaxbContext = JAXBContext.newInstance(N06.class);
                        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                        // output pretty printed
                        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                        jaxbMarshaller.marshal(msg, node);
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
                        DOMSource source = new DOMSource(node);
                        StreamResult result = new StreamResult(new File(owLocalFilePath + "/" + neftAutoObj.getNeftBankName().trim().toLowerCase() + "/" + owNeftFileName));
                        //Also Create in Outward Backup.
                        StreamResult backupResult = new StreamResult(new File(neftAutoObj.getOwLocalFileBackupPath() + "/" + neftAutoObj.getNeftBankName().trim().toLowerCase() + "/" + owNeftFileName));
                        // Output to console for testing
                        // StreamResult result = new StreamResult(System.out);
                        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                        transformer.transform(source, result);
                        //Backup Transform.
                        transformer.transform(source, backupResult);
                        //Preparation of all UniqueRefNo
                        if (uniqueRefNo.equals("")) {
                            uniqueRefNo = "\'" + obj.getUniqueCustomerRefNo() + "\'";
                        } else {
                            uniqueRefNo = uniqueRefNo + ",\'" + obj.getUniqueCustomerRefNo() + "\'";
                        }
                    }
                    //File name logging
                    int n = entityManager.createNativeQuery("update neft_ow_details set "
                            + "outward_file_name='" + owNeftFileName + "' where uniquecustomerrefno "
                            + "in(" + uniqueRefNo + ")").executeUpdate();
                    if (n <= 0) {
                        throw new Exception("Problem In Outward File Name Logging.");
                    }
                }
                checkkPayType = false;
            }

            for (int i = 0; i < resultList.size(); i++) {
                NeftOwDetails obj = resultList.get(i);
                if (obj.getPaymentType().equalsIgnoreCase("R")) {   //For Rtgs
                    checkkPayType = true;
                    break;
                }
            }
            //Writting of outward rtgs(R41) file.
            if (checkkPayType == true) {
                List<NeftOwDetails> onlyRtgsData = new ArrayList<NeftOwDetails>();
                for (int i = 0; i < resultList.size(); i++) {
                    if (resultList.get(i).getPaymentType().equalsIgnoreCase("R")) {
                        onlyRtgsData.add(resultList.get(i));
                    }
                }

                Integer rtgsFileLimit = Integer.parseInt(ftsRemote.getCodeByReportName("IDBI-RTGS-FILE-LIMIT"));
                List<List<NeftOwDetails>> fileList = new ArrayList<List<NeftOwDetails>>();
                for (int i = 0; i < onlyRtgsData.size(); i = i + rtgsFileLimit) {
                    List<NeftOwDetails> subList;
                    if (i + rtgsFileLimit < onlyRtgsData.size()) {
                        subList = onlyRtgsData.subList(i, i + rtgsFileLimit);
                    } else {
                        subList = onlyRtgsData.subList(i, onlyRtgsData.size());
                    }
                    fileList.add(subList);
                }

                for (int z = 0; z < fileList.size(); z++) {
                    System.out.println("RTGS File List Number Is-->" + z + "\n");
                    List<NeftOwDetails> subList = fileList.get(z);
//                    String owRtgsFileName = "R41" + branchMasterIfsc.substring(branchMasterIfsc.length() - 3) + ymdms.format(new Date()) + String.valueOf(z) + ".xml";
                    String owRtgsFileName = "R41" + idbiBankCode.trim().toUpperCase() + ymdms.format(new Date()) + String.valueOf(z) + ".xml";
                    String uniqueRefNo = "";
                    Node node = getNodeForRootElement("RTGS");
                    for (int x = 0; x < subList.size(); x++) {
                        NeftOwDetails obj = subList.get(x);
                        R41 msg = new R41();
                        msg.setFSRID_1(branchMasterIfsc);
                        msg.setFRCID_1(obj.getOutsideBankIfscCode().trim());
                        msg.setF2020_1(obj.getUniqueCustomerRefNo().trim());
                        msg.setF4488_1(ddMMyyyy.format(dmy.parse(obj.getPaymentDate())));
                        msg.setF4488_2(obj.getTxnAmt().toString());
                        msg.setF5500_1(obj.getDebitAccountNo().trim());

                        String drCustName = ftsRemote.ftsGetCustName(obj.getDebitAccountNo()).trim();
                        drCustName = drCustName.replaceAll("[\\W_]", " ");
                        drCustName = drCustName.length() > 35 ? drCustName.substring(0, 35) : drCustName;
                        msg.setF5500_2(drCustName.trim());
                        msg.setF5500_3(drCustName.trim());

                        msg.setF5517_1("");
                        msg.setF5516_1("");
                        msg.setF5516_2("");
                        msg.setF5516_3("");
                        msg.setF5518_1("");
                        msg.setF6717_1("");
                        msg.setF6717_2("");
                        msg.setF6717_3("");
                        msg.setF5521_1("");
                        msg.setF5521_2("");
                        msg.setF5521_3("");
                        msg.setF6500_1("");
                        msg.setF6718_1("");
                        msg.setF6718_2("");
                        msg.setF6718_3("");
                        msg.setF5526_1("");
                        msg.setF5526_2("");
                        msg.setF5526_3("");
                        msg.setF6511_1("");
                        msg.setF5546_1("");
                        msg.setF5546_2("");
                        msg.setF5546_3("");
                        msg.setF6516_1("");
                        msg.setF6719_1("");
                        msg.setF6719_2("");
                        msg.setF6719_3("");
                        msg.setF5551_1("");
                        msg.setF5551_2("");
                        msg.setF5551_3("");
                        msg.setF5561_1(obj.getCreditAccountNo().trim());
                        String beneName = obj.getBeneficiaryName().trim();
                        msg.setF5561_2(beneName.length() > 34 ? beneName.substring(0, 34) : beneName);
                        msg.setF7495_1("URGENT");
                        msg.setF7495_2("");

                        JAXBContext jaxbContext = JAXBContext.newInstance(R41.class);
                        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                        // output pretty printed
                        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                        jaxbMarshaller.marshal(msg, node);
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
                        DOMSource source = new DOMSource(node);
                        StreamResult result = new StreamResult(new File(owLocalFilePath + "/" + neftAutoObj.getNeftBankName().trim().toLowerCase() + "/" + owRtgsFileName));
                        //Also Create in Outward Backup.
                        StreamResult backupResult = new StreamResult(new File(neftAutoObj.getOwLocalFileBackupPath() + "/" + neftAutoObj.getNeftBankName().trim().toLowerCase() + "/" + owRtgsFileName));
                        // Output to console for testing
                        // StreamResult result = new StreamResult(System.out);
                        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                        transformer.transform(source, result);
                        //Backup Transform.
                        transformer.transform(source, backupResult);
                        //Preparation of all UniqueRefNo
                        if (uniqueRefNo.equals("")) {
                            uniqueRefNo = "\'" + obj.getUniqueCustomerRefNo() + "\'";
                        } else {
                            uniqueRefNo = uniqueRefNo + ",\'" + obj.getUniqueCustomerRefNo() + "\'";
                        }
                    }
                    //File name logging
                    int n = entityManager.createNativeQuery("update neft_ow_details set "
                            + "outward_file_name='" + owRtgsFileName + "' where uniquecustomerrefno "
                            + "in(" + uniqueRefNo + ")").executeUpdate();
                    if (n <= 0) {
                        throw new Exception("Problem In Outward File Name Logging.");
                    }
                }
                checkkPayType = false;
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void generateIwNeftRtgsReturn(String owLocalFilePath, CbsAutoNeftDetails neftAutoObj) throws Exception {
        SimpleDateFormat ymdms = new SimpleDateFormat("yyMMddHHmmssSSS");
        SimpleDateFormat yymdd = new SimpleDateFormat("yyMMdd");
        NeftRtgsStatusDAO neftRtgsStatusDAO = new NeftRtgsStatusDAO(entityManager);
        BranchMasterDAO branchMasterDAO = new BranchMasterDAO(entityManager);
        MbSmsSenderBankDetailDAO smsDao = new MbSmsSenderBankDetailDAO(entityManager);
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            System.out.println("In IDBI IW Return Processing");
            BranchMaster bmEntity = branchMasterDAO.getEntityByAlphacode("HO");
            if (bmEntity.getIfscCode() == null || bmEntity.getIfscCode().equals("")) {
                throw new ApplicationException("Please define IFSC for HO.");
            }
            String ifsc = bmEntity.getIfscCode().trim();
            //Generation of Inward Neft Return.
            List<NeftRtgsStatus> list = neftRtgsStatusDAO.getEntityByTxnTypeAndStatus("N02", "AUTO", neftAutoObj.getNeftBankName().trim());
            if (!list.isEmpty()) {
                Integer neftFileLimit = Integer.parseInt(ftsRemote.getCodeByReportName("IDBI-NEFT-FILE-LIMIT"));

                List<List<NeftRtgsStatus>> fileList = new ArrayList<List<NeftRtgsStatus>>();
                for (int i = 0; i < list.size(); i = i + neftFileLimit) {
                    List<NeftRtgsStatus> subList;
                    if (i + neftFileLimit < list.size()) {
                        subList = list.subList(i, i + neftFileLimit);
                    } else {
                        subList = list.subList(i, list.size());
                    }
                    fileList.add(subList);
                }

                List<MbSmsSenderBankDetail> smsList = smsDao.getBankAndSenderDetail();
                if (smsList.get(0).getBankEmail() == null || smsList.get(0).getBankEmail().equals("")) {
                    throw new ApplicationException("Please fill bank email Id in mb_sms_sender_bank_detail table.");
                }
                if (!new Validator().validateEmail(smsList.get(0).getBankEmail())) {
                    throw new ApplicationException("Please fill proper email id.");
                }

                for (int y = 0; y < fileList.size(); y++) {
                    System.out.println("Neft File Number Is-->" + y + "\n");
                    List<NeftRtgsStatus> subList = fileList.get(y);
//                    String iwNeftReturnFileName = "N06" + ifsc.substring(ifsc.length() - 3) + "RET" + ymdms.format(new Date()) + String.valueOf(y) + ".xml";
                    String iwNeftReturnFileName = "N06" + ifsc.substring(ifsc.length() - 3) + ymdms.format(new Date()) + String.valueOf(y) + ".xml";
                    Node node = getNodeForRootElement("NEFT");
                    for (int i = 0; i < subList.size(); i++) {
                        NeftRtgsStatus obj = subList.get(i);

                        N06 msg = new N06();
                        msg.setFTransaction_Reference_Number(yymdd.format(new Date()) + obj.getNeftRtgsStatusPK().getTxnId());
                        msg.setFAmount(obj.getAmount().toString());
                        msg.setFValue_Date(ymd.format(new Date()));
                        msg.setFSending_branchs_IFSC(obj.getReceiverIfsc());
                        msg.setFSending_Customer_Account_Type("");
                        msg.setFSending_Customer_Account_Number(obj.getBeneAccount());
                        msg.setFSending_Customer_Account_Name(obj.getBeneName());
                        msg.setFSending_Customer_Mobile_No_Email_Id("EML");            //It is mandatory in N06 format.But There is no value in N02.
                        msg.setFSending_Customer_Mobile_No_Email_Id_(smsList.get(0).getBankEmail());           //It is mandatory in N06 format.But There is no value in N02.
                        msg.setFOriginator_of_Remittance(obj.getRemittanceOriginator());
                        msg.setFBeneficiary_branchs_IFSC(obj.getSenderIfsc());
                        msg.setFBeneficiary_Customer_Account_Type("");
                        msg.setFBeneficiary_Customer_Account_Number(obj.getSenderAccount());
                        msg.setFBeneficiary_Customer_Account_Name(obj.getSenderName());
                        msg.setFBeneficiary_Customer_Address("");
//                        msg.setFSender_to_Receiver_Information(obj.getUtr());
                        String[] arr = idbiReturnDescription(obj.getReason() == null ? "" : obj.getReason().trim());
                        msg.setFSender_to_Receiver_Information("RETN06" + "|" + obj.getUtr().trim() + "|" + arr[0] + "|" + arr[1]);

                        JAXBContext jaxbContext = JAXBContext.newInstance(N06.class);
                        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                        // output pretty printed
                        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                        jaxbMarshaller.marshal(msg, node);
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
                        DOMSource source = new DOMSource(node);
                        StreamResult result = new StreamResult(new File(owLocalFilePath + neftAutoObj.getNeftBankName().trim().toLowerCase()
                                + "/" + iwNeftReturnFileName));
                        //Also Create in Outward Backup.
                        StreamResult backupResult = new StreamResult(new File(neftAutoObj.getOwLocalFileBackupPath()
                                + neftAutoObj.getNeftBankName().trim().toLowerCase() + "/" + iwNeftReturnFileName));
                        // Output to console for testing
                        // StreamResult result = new StreamResult(System.out);
                        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                        transformer.transform(source, result);
                        //Backup Transform.
                        transformer.transform(source, backupResult);
                    }
                    //Status Updation
                    for (int i = 0; i < subList.size(); i++) {
                        NeftRtgsStatus obj = subList.get(i);
                        obj.setStatus("Sent IDBI");
                        neftRtgsStatusDAO.update(obj);
                    }
                }
            }
            //Generation of Inward Rtgs Return.
            list = neftRtgsStatusDAO.getEntityByTxnTypeAndStatus("R41", "AUTO", neftAutoObj.getNeftBankName().trim());
            if (!list.isEmpty()) {
                Integer rtgsFileLimit = Integer.parseInt(ftsRemote.getCodeByReportName("IDBI-RTGS-FILE-LIMIT"));

                List<List<NeftRtgsStatus>> fileList = new ArrayList<List<NeftRtgsStatus>>();
                for (int i = 0; i < list.size(); i = i + rtgsFileLimit) {
                    List<NeftRtgsStatus> subList;
                    if (i + rtgsFileLimit < list.size()) {
                        subList = list.subList(i, i + rtgsFileLimit);
                    } else {
                        subList = list.subList(i, list.size());
                    }
                    fileList.add(subList);
                }

                for (int y = 0; y < fileList.size(); y++) {
                    System.out.println("RTGS File Number Is-->" + y + "\n");
                    List<NeftRtgsStatus> subList = fileList.get(y);
//                    String iwRtgsReturnFileName = "R42" + ifsc.substring(ifsc.length() - 3) + "RET" + ymdms.format(new Date()) + String.valueOf(y) + ".xml";
                    String iwRtgsReturnFileName = "R42" + ifsc.substring(ifsc.length() - 3) + ymdms.format(new Date()) + String.valueOf(y) + ".xml";
                    Node node = getNodeForRootElement("RTGS");
                    for (int i = 0; i < subList.size(); i++) {
                        NeftRtgsStatus obj = subList.get(i);

                        R42 msg = new R42();
                        msg.setFSRID_1(obj.getReceiverIfsc());
                        msg.setFRCID_1(obj.getSenderIfsc());
                        msg.setF2020_1(yymdd.format(new Date()) + obj.getNeftRtgsStatusPK().getTxnId());
//                        msg.setF2006_1(yymdd.format(new Date()) + obj.getNeftRtgsStatusPK().getTxnId());  //It is mandatory in R42(Inward Rtgs Return) but not in R41(Inward Rtgs)
                        msg.setF2006_1(obj.getUtr().trim());
                        msg.setF4488_1(ddMMyyyy.format(new Date()));
                        msg.setF4488_2(obj.getAmount().toString());
                        msg.setF5517_1("");
                        msg.setF5516_1("");
                        msg.setF5516_2("");
                        msg.setF5516_3("");
                        msg.setF5518_1("");
                        msg.setF6717_1("");
                        msg.setF6717_2("");
                        msg.setF6717_3("");
                        msg.setF5521_1("");
                        msg.setF5521_2("");
                        msg.setF5521_3("");
                        msg.setF6500_1("");
                        msg.setF6718_1("");
                        msg.setF6718_2("");
                        msg.setF6718_3("");
                        msg.setF5526_1("");
                        msg.setF5526_2("");
                        msg.setF5526_3("");
                        msg.setF6511_1("");
                        msg.setF5546_1("");
                        msg.setF5546_2("");
                        msg.setF5546_3("");
                        msg.setF6516_1("");
                        msg.setF6719_1("");
                        msg.setF6719_2("");
                        msg.setF6719_3("");
                        msg.setF5551_1("");
                        msg.setF5551_2("");
                        msg.setF5551_3("");
                        msg.setF6521_1(obj.getSenderIfsc()); //Not confirm what should be the value
                        msg.setF5556_1("");
                        msg.setF5556_2("");

                        String remitInfo = obj.getRemittanceInfo().replaceAll("[\\W_]", " ").trim();
                        remitInfo = remitInfo.length() > 33 ? remitInfo.substring(0, 33) : remitInfo;

                        msg.setF5556_3(remitInfo);     //It is mandatory in R42(Inward Rtgs Return) but what will be the value.
//                        msg.setF7495_1("URGENT");
//                        msg.setF7495_2(obj.getUtr());

                        String[] arr = idbiReturnDescription(obj.getReason() == null ? "" : obj.getReason().trim());
                        msg.setF7495_1(arr[0]);
                        msg.setF7495_2(arr[1]);

                        JAXBContext jaxbContext = JAXBContext.newInstance(R42.class);
                        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                        // output pretty printed
                        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                        jaxbMarshaller.marshal(msg, node);
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
                        DOMSource source = new DOMSource(node);
                        StreamResult result = new StreamResult(new File(owLocalFilePath
                                + neftAutoObj.getNeftBankName().trim().toLowerCase() + "/" + iwRtgsReturnFileName));
                        //Also Create in Outward Backup.
                        StreamResult backupResult = new StreamResult(new File(neftAutoObj.getOwLocalFileBackupPath()
                                + neftAutoObj.getNeftBankName().trim().toLowerCase() + "/" + iwRtgsReturnFileName));
                        // Output to console for testing
                        // StreamResult result = new StreamResult(System.out);
                        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                        transformer.transform(source, result);
                        //Backup Transform.
                        transformer.transform(source, backupResult);
                    }
                    //Status Updation
                    for (int i = 0; i < subList.size(); i++) {
                        NeftRtgsStatus obj = subList.get(i);
                        obj.setStatus("Sent IDBI");
                        neftRtgsStatusDAO.update(obj);
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
    }

    private void idbiInwardProcess(File[] files, String iwLocalFilePath,
            CbsAutoNeftDetails neftAutoObj) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        NeftOwDetailsDAO neftOwDetailsDAO = new NeftOwDetailsDAO(entityManager);
        try {
            if (files != null) {
                for (File file : files) {
                    MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
                    String fileContentType = mimeTypesMap.getContentType(file.getName());
                    if (fileContentType.equals("application/octet-stream")) {   //For xml files.
                        String msgName = "", r09UtrNo = "";
                        String comingFileName = file.getName().trim().toUpperCase();
                        if (comingFileName.contains("N02") || comingFileName.contains("R41")
                                || comingFileName.contains("F27") || comingFileName.contains("R09")
                                || comingFileName.contains("R42")) {
                            msgName = comingFileName.substring(0, 3);
                            if (comingFileName.contains("R09")) {
                                r09UtrNo = comingFileName.substring(12, comingFileName.indexOf(".")).trim();
                            }
                        } else if (comingFileName.contains("R90")) {
                            msgName = comingFileName.substring(3, 6);
                        }

                        String className = NeftEnum.getClassName(msgName.toUpperCase());
                        //Unmarshalling-->XML To Java Object
                        Class comingMsg = Class.forName(className);
                        JAXBContext jaxbContext = JAXBContext.newInstance(comingMsg);
                        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                        Document newDoc = docBuilder.parse(new File(iwLocalFilePath + file.getName()));
                        NodeList nodeList = newDoc.getElementsByTagName("MSG"); //For All Messages Tags.

                        F27 f27 = null; //Outward Neft Ack
                        N02 n02 = null; //Inward Neft
                        N06 n06 = null;
                        N07 n07 = null;
                        R09 r09 = null; //Outward Rtgs Ack
                        R41 r41 = null; //Inward Rtgs
                        R90 r90 = null; //Outward Rtgs Ack
                        R42 r42 = null;

                        //Is it dynamic mapping of classes(It can be modified with dynamic mapping).
                        List<ExcelReaderPojo> pojoList = new ArrayList<ExcelReaderPojo>();
                        if (msgName.equalsIgnoreCase("N02")) {
                            for (int i = 0; i < nodeList.getLength(); i++) {
                                n02 = (N02) jaxbUnmarshaller.unmarshal(nodeList.item(i));
                                pojoList.add(ParseFileUtil.adaptObjectFromN02Msg(n02));
                            }
                            if (pojoList.get(0).getReasonCode() == null || pojoList.get(0).getReasonCode().equals("")) {    //Checking is based on only first row.
                                //Inward Neft Case
                                String result = remote.neftRtgsUploadProcess(pojoList, "90", "System",
                                        neftAutoObj.getIwHead(), neftAutoObj.getProcess(), neftAutoObj.getNeftBankName());
                                iwFileConclusionAfterProcessing(result);
                            } else {
                                //Outward Neft Return--NeftOWDetails
                                try {
                                    ut.begin();
                                    for (int i = 0; i < pojoList.size(); i++) {
                                        ExcelReaderPojo obj = pojoList.get(i);
                                        NeftOwDetails neftOwDetailsObj = neftOwDetailsDAO.getOutwardDetailBasedOnUtr(obj.getRelatedRefNo().trim());
                                        if (neftOwDetailsObj != null) {
                                            if (!neftOwDetailsObj.getStatus().equalsIgnoreCase("U")) {
                                                String msg = reverseOutwardTransaction(neftOwDetailsObj, "90", "System", neftAutoObj);
                                                if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                    if (neftOwDetailsObj.getStatus().equalsIgnoreCase("S")) {
                                                        neftOwDetailsObj.setSuccessToFailureFlag("R");
                                                    }
                                                    neftOwDetailsObj.setStatus("U");
                                                    neftOwDetailsObj.setDetails("Un-Paid");
                                                    neftOwDetailsObj.setReason(obj.getReason());
                                                    System.out.println("Outward Neft Reversal is Successfull.");
                                                    //Deaf Updation
                                                    ftsRemote.lastTxnDateUpdation(neftOwDetailsObj.getDebitAccountNo().trim());
                                                    neftOwDetailsObj.setResponseUpdateTime(new Date());
                                                    neftOwDetailsDAO.update(neftOwDetailsObj);
                                                    //Sms Sending
                                                    try {
                                                        sendUtrSms(neftOwDetailsObj.getStatus(), neftOwDetailsObj, neftOwDetailsObj.getUtrNo(),
                                                                neftOwDetailsObj.getCmsBankRefNo());
                                                    } catch (Exception ex) {
                                                        System.out.println("Problem In Outward Neft Reversal In IDBI-->" + ex.getMessage());
                                                    }
                                                    //End here
                                                }
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
                            }
                        } else if (msgName.equalsIgnoreCase("R41")) {
                            //Inward Rtgs Case
                            for (int i = 0; i < nodeList.getLength(); i++) {
                                r41 = (R41) jaxbUnmarshaller.unmarshal(nodeList.item(i));
                                pojoList.add(ParseFileUtil.adaptObjectFromR41Msg(r41));
                            }
                            String result = remote.neftRtgsUploadProcess(pojoList, "90", "System",
                                    neftAutoObj.getIwHead(), neftAutoObj.getProcess(), neftAutoObj.getNeftBankName());
                            iwFileConclusionAfterProcessing(result);
                        } else if (msgName.equalsIgnoreCase("R42")) {
                            //Outward Rtgs Return--NeftOWDetails
                            for (int i = 0; i < nodeList.getLength(); i++) {
                                r42 = (R42) jaxbUnmarshaller.unmarshal(nodeList.item(i));
                                pojoList.add(ParseFileUtil.adaptObjectFromR42Msg(r42));
                            }
                            try {
                                ut.begin();
                                for (int i = 0; i < pojoList.size(); i++) {
                                    ExcelReaderPojo obj = pojoList.get(i);
                                    NeftOwDetails neftOwDetailsObj = neftOwDetailsDAO.getOutwardDetailBasedOnUtr(obj.getUtr().trim());
                                    if (neftOwDetailsObj != null && !neftOwDetailsObj.getStatus().equalsIgnoreCase("U")) {
                                        String msg = reverseOutwardTransaction(neftOwDetailsObj, "90", "System", neftAutoObj);
                                        if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                                            if (neftOwDetailsObj.getStatus().equalsIgnoreCase("S")) {
                                                neftOwDetailsObj.setSuccessToFailureFlag("R");
                                            }
                                            neftOwDetailsObj.setStatus("U");
                                            neftOwDetailsObj.setDetails("Un-Paid");
                                            neftOwDetailsObj.setReason("");
                                            System.out.println("Outward Neft Reversal is Successfull.");
                                            ftsRemote.lastTxnDateUpdation(neftOwDetailsObj.getDebitAccountNo().trim());
                                            neftOwDetailsObj.setResponseUpdateTime(new Date());
                                            neftOwDetailsDAO.update(neftOwDetailsObj);
                                            try {
                                                sendUtrSms(neftOwDetailsObj.getStatus(), neftOwDetailsObj, neftOwDetailsObj.getUtrNo(),
                                                        neftOwDetailsObj.getCmsBankRefNo());
                                            } catch (Exception ex) {
                                                System.out.println("Problem In Outward Rtgs Reversal In IDBI-->" + ex.getMessage());
                                            }
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
                        } else if (msgName.equalsIgnoreCase("F27")) {
                            try {
                                ut.begin();
                                for (int i = 0; i < nodeList.getLength(); i++) {
                                    f27 = (F27) jaxbUnmarshaller.unmarshal(nodeList.item(i));
                                    NeftOwDetails neftOwDetailsObj = neftOwDetailsDAO.getSingleNeftOwDetailsInstrument(f27.getFTransaction_Reference_Number());
                                    if (neftOwDetailsObj != null) {
                                        if (f27.getFSucess_Flag().equalsIgnoreCase("Y")) {
                                            if (!(neftOwDetailsObj.getStatus().equalsIgnoreCase("U")
                                                    || neftOwDetailsObj.getStatus().equalsIgnoreCase("S"))) {
                                                neftOwDetailsObj.setStatus("S");
                                                neftOwDetailsObj.setDetails("Paid");
                                                neftOwDetailsObj.setReason(f27.getFReason_Description());

                                                neftOwDetailsObj.setUtrNo(f27.getFICReference_Number());
                                                neftOwDetailsObj.setResponseUpdateTime(new Date());
                                                neftOwDetailsDAO.update(neftOwDetailsObj);
                                                //Sms Sending
                                                try {
                                                    sendUtrSms(neftOwDetailsObj.getStatus(), neftOwDetailsObj, neftOwDetailsObj.getUtrNo(),
                                                            neftOwDetailsObj.getCmsBankRefNo());
                                                } catch (Exception ex) {
                                                    System.out.println("Problem In H2H UTR updation message In IDBI-->" + ex.getMessage());
                                                }
                                                //End here
                                            }
                                        } else if (f27.getFSucess_Flag().equalsIgnoreCase("N")) {
                                            if (!neftOwDetailsObj.getStatus().equalsIgnoreCase("U")) {
                                                String msg = reverseOutwardTransaction(neftOwDetailsObj, "90", "System", neftAutoObj);
                                                if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                    if (neftOwDetailsObj.getStatus().equalsIgnoreCase("S")) {
                                                        neftOwDetailsObj.setSuccessToFailureFlag("R");
                                                    }
                                                    neftOwDetailsObj.setStatus("U");
                                                    neftOwDetailsObj.setDetails("Un-Paid");
                                                    neftOwDetailsObj.setReason("");
                                                    System.out.println("Outward Reversal is Successfull.");
                                                    //Deaf Updation
                                                    ftsRemote.lastTxnDateUpdation(neftOwDetailsObj.getDebitAccountNo().trim());

                                                    neftOwDetailsObj.setUtrNo(f27.getFICReference_Number());
                                                    neftOwDetailsObj.setResponseUpdateTime(new Date());
                                                    neftOwDetailsDAO.update(neftOwDetailsObj);
                                                    //Sms Sending
                                                    try {
                                                        sendUtrSms(neftOwDetailsObj.getStatus(), neftOwDetailsObj, neftOwDetailsObj.getUtrNo(),
                                                                neftOwDetailsObj.getCmsBankRefNo());
                                                    } catch (Exception ex) {
                                                        System.out.println("Problem In H2H UTR updation message In IDBI-->" + ex.getMessage());
                                                    }
                                                    //End here
                                                }
                                            }
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
                        } else if (msgName.equalsIgnoreCase("R09")) {
                            try {
                                ut.begin();
                                for (int i = 0; i < nodeList.getLength(); i++) {
                                    r09 = (R09) jaxbUnmarshaller.unmarshal(nodeList.item(i));
                                    NeftOwDetails neftOwDetailsObj = neftOwDetailsDAO.getSingleNeftOwDetailsInstrument(r09.getF2020_1());
                                    if (neftOwDetailsObj != null) {
                                        if (r09.getF6450_1().equalsIgnoreCase("Y")) {
                                            if (!(neftOwDetailsObj.getStatus().equalsIgnoreCase("U")
                                                    || neftOwDetailsObj.getStatus().equalsIgnoreCase("S"))) {
                                                neftOwDetailsObj.setStatus("S");
                                                neftOwDetailsObj.setDetails("Paid");
                                                neftOwDetailsObj.setReason("");
                                                neftOwDetailsObj.setUtrNo(r09UtrNo);
                                                neftOwDetailsObj.setResponseUpdateTime(new Date());
                                                neftOwDetailsDAO.update(neftOwDetailsObj);
                                                //Sms Sending
                                                try {
                                                    sendUtrSms(neftOwDetailsObj.getStatus(), neftOwDetailsObj, neftOwDetailsObj.getUtrNo(),
                                                            neftOwDetailsObj.getCmsBankRefNo());
                                                } catch (Exception ex) {
                                                    System.out.println("Problem In H2H UTR updation message In IDBI-->" + ex.getMessage());
                                                }
                                                //End here
                                            }
                                        } else if (r09.getF6450_1().equalsIgnoreCase("N")) {
                                            if (!neftOwDetailsObj.getStatus().equalsIgnoreCase("U")) {
                                                String msg = reverseOutwardTransaction(neftOwDetailsObj, "90", "System", neftAutoObj);
                                                if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                    if (neftOwDetailsObj.getStatus().equalsIgnoreCase("S")) {
                                                        neftOwDetailsObj.setSuccessToFailureFlag("R");
                                                    }
                                                    neftOwDetailsObj.setStatus("U");
                                                    neftOwDetailsObj.setDetails("Un-Paid");
                                                    neftOwDetailsObj.setReason("");
                                                    neftOwDetailsObj.setUtrNo(r09UtrNo);
                                                    neftOwDetailsObj.setResponseUpdateTime(new Date());
                                                    System.out.println("Outward Reversal is Successfull.");
                                                    //Deaf Updation
                                                    ftsRemote.lastTxnDateUpdation(neftOwDetailsObj.getDebitAccountNo().trim());

                                                    neftOwDetailsDAO.update(neftOwDetailsObj);
                                                    //Sms Sending
                                                    try {
                                                        sendUtrSms(neftOwDetailsObj.getStatus(), neftOwDetailsObj, neftOwDetailsObj.getUtrNo(),
                                                                neftOwDetailsObj.getCmsBankRefNo());
                                                    } catch (Exception ex) {
                                                        System.out.println("Problem In H2H UTR updation message In IDBI-->" + ex.getMessage());
                                                    }
                                                    //End here
                                                }
                                            }
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
                        } else if (msgName.equalsIgnoreCase("R90")) {
                            try {
                                ut.begin();
                                for (int i = 0; i < nodeList.getLength(); i++) {
                                    r90 = (R90) jaxbUnmarshaller.unmarshal(nodeList.item(i));
                                    NeftOwDetails neftOwDetailsObj = neftOwDetailsDAO.getSingleNeftOwDetailsInstrument(r90.getF2020_1());
                                    if (neftOwDetailsObj != null) {
                                        if (r90.getF1076_1().equalsIgnoreCase("Y")) {
                                            if (!(neftOwDetailsObj.getStatus().equalsIgnoreCase("U")
                                                    || neftOwDetailsObj.getStatus().equalsIgnoreCase("S"))) {
                                                neftOwDetailsObj.setStatus("S");
                                                neftOwDetailsObj.setDetails("Paid");
                                                neftOwDetailsObj.setReason("");
                                                neftOwDetailsObj.setResponseUpdateTime(new Date());

                                                neftOwDetailsDAO.update(neftOwDetailsObj);
                                                //Sms Sending
                                                try {
                                                    sendUtrSms(neftOwDetailsObj.getStatus(), neftOwDetailsObj, neftOwDetailsObj.getUtrNo(),
                                                            neftOwDetailsObj.getCmsBankRefNo());
                                                } catch (Exception ex) {
                                                    System.out.println("Problem In H2H UTR updation message In IDBI-->" + ex.getMessage());
                                                }
                                                //End here
                                            }
                                        } else if (r90.getF1076_1().equalsIgnoreCase("N")) {
                                            if (!neftOwDetailsObj.getStatus().equalsIgnoreCase("U")) {
                                                String msg = reverseOutwardTransaction(neftOwDetailsObj, "90", "System", neftAutoObj);
                                                if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                                                    if (neftOwDetailsObj.getStatus().equalsIgnoreCase("S")) {
                                                        neftOwDetailsObj.setSuccessToFailureFlag("R");
                                                    }
                                                    neftOwDetailsObj.setStatus("U");
                                                    neftOwDetailsObj.setDetails("Un-Paid");
                                                    neftOwDetailsObj.setReason("");
                                                    neftOwDetailsObj.setResponseUpdateTime(new Date());
                                                    System.out.println("Outward Reversal is Successfull.");
                                                    //Deaf Updation
                                                    ftsRemote.lastTxnDateUpdation(neftOwDetailsObj.getDebitAccountNo().trim());

                                                    neftOwDetailsDAO.update(neftOwDetailsObj);
                                                    //Sms Sending
                                                    try {
                                                        sendUtrSms(neftOwDetailsObj.getStatus(), neftOwDetailsObj, neftOwDetailsObj.getUtrNo(),
                                                                neftOwDetailsObj.getCmsBankRefNo());
                                                    } catch (Exception ex) {
                                                        System.out.println("Problem In H2H UTR updation message In IDBI-->" + ex.getMessage());
                                                    }
                                                    //End here
                                                }
                                            }
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
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    //Common code.
    public boolean isH2HModuleOn() throws ApplicationException {
        try {
            List list = entityManager.createNativeQuery("SELECT code from parameterinfo_report "
                    + "where reportname='H2H'").getResultList();
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
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public FileSystemOptions createDefaultOptions() throws Exception {
        FileSystemOptions options = null;
        try {
            List<MbSmsSenderBankDetail> bankList = remote.getBankCode();
            if (!bankList.isEmpty()) {
                MbSmsSenderBankDetail bankEntity = bankList.get(0);
                String bankCode = bankEntity.getBankCode();
                // Create SFTP options
                options = new FileSystemOptions();
                // SSH Key checking
                SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(options, "no");
                /*
                 * Using the following line will cause VFS to choose File System's Root
                 * as VFS's root. If I wanted to use User's home as VFS's root then set
                 * 2nd method parameter to "true".Root directory set to user home
                 */
                SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(options, false);
                // Timeout is count by Milliseconds
                SftpFileSystemConfigBuilder.getInstance().setTimeout(options, 60000);
                if (bankCode.equalsIgnoreCase("snsb")) {
                    SftpFileSystemConfigBuilder.getInstance().setIdentities(options,
                            new File[]{new File("/root/.ssh/hdfc_sonbhadra")});
                } else if (bankCode.equalsIgnoreCase("iucb")) {
                    SftpFileSystemConfigBuilder.getInstance().setIdentities(options,
                            new File[]{new File("/root/.ssh/hdfc_etawah")});
                } else if (bankCode.equalsIgnoreCase("bhin")) {
                    SftpFileSystemConfigBuilder.getInstance().setIdentities(options,
                            new File[]{new File("/root/.ssh/hdfc_bhind")});
                } else if (bankCode.equalsIgnoreCase("ujcb")) {
                    SftpFileSystemConfigBuilder.getInstance().setIdentities(options,
                            new File[]{new File("/root/.ssh/zakir_ujjain_hdfc")});
                } //                else if (bankCode.equalsIgnoreCase("kcbl")) {
                //                    SftpFileSystemConfigBuilder.getInstance().setIdentities(options,
                //                            new File[]{new File("/root/.ssh/madan_dhanbad_hdfc")});
                //                } 
                else if (bankCode.equalsIgnoreCase("kccb")) {
                    SftpFileSystemConfigBuilder.getInstance().setIdentities(options,
                            new File[]{new File("/root/.ssh/kccb_hdfc")});
                } else if (bankCode.equalsIgnoreCase("ccbl")) {
                    SftpFileSystemConfigBuilder.getInstance().setIdentities(options,
                            new File[]{new File("/root/.ssh/deepank_hdfc")});
                } else if (bankCode.equalsIgnoreCase("amco")) {
                    SftpFileSystemConfigBuilder.getInstance().setIdentities(options,
                            new File[]{new File("/root/.ssh/arti_amco_hdfc")});
                } else if (bankCode.equalsIgnoreCase("rlub")) {
                    SftpFileSystemConfigBuilder.getInstance().setIdentities(options,
                            new File[]{new File("/root/.ssh/vinod_hdfc")});
                } else if (bankCode.equalsIgnoreCase("veas")) {
                    SftpFileSystemConfigBuilder.getInstance().setIdentities(options,
                            new File[]{new File("/root/.ssh/sharma_hdfc")});
                } else if (bankCode.equalsIgnoreCase("ukcb")) {
                    SftpFileSystemConfigBuilder.getInstance().setIdentities(options,
                            new File[]{new File("/root/.ssh/rana_utti_hdfc")});
                } else if (bankCode.equalsIgnoreCase("nabu")) {
                    SftpFileSystemConfigBuilder.getInstance().setIdentities(options,
                            new File[]{new File("/root/.ssh/nawadip_hdfc")});
                }
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return options;
    }

    public void sendUtrSms(String txnStatus, NeftOwDetails neftOwDetailsObj, String utrNo, String cmsRefNo) throws ApplicationException {
        //txnStatus (S-Paid, U-Un-Paid,Intermediary Case)
        try {
            TransferSmsRequestTo tSmsRequestTo = new TransferSmsRequestTo();
            tSmsRequestTo.setMsgType("PAT");
            if (txnStatus.equalsIgnoreCase("S")) {
                tSmsRequestTo.setTemplate(SmsType.OW_UTR_PAID);
            } else if (txnStatus.equalsIgnoreCase("U")) {
                tSmsRequestTo.setTemplate(SmsType.OW_UTR_CANCEL);
            } else {
                tSmsRequestTo.setTemplate(SmsType.OW_UTR_UPDATION);
            }
            tSmsRequestTo.setAcno(neftOwDetailsObj.getDebitAccountNo());
            tSmsRequestTo.setAmount(neftOwDetailsObj.getTxnAmt().doubleValue());
            tSmsRequestTo.setPromoMobile(smsMgmtFacade.getSubscriberDetails(neftOwDetailsObj.getDebitAccountNo()).getMobileNo());

            utrNo = utrNo == null ? "" : utrNo;
            cmsRefNo = cmsRefNo == null ? "" : cmsRefNo;
            tSmsRequestTo.setFirstCheque(utrNo + "/" + cmsRefNo);
            smsMgmtFacade.sendSms(tSmsRequestTo);
        } catch (Exception ex) {
            throw new ApplicationException("Problem In Sending H2H SMS-->" + ex.getMessage());
        }
    }

    @Override
    public String reverseOutwardTransaction(NeftOwDetails obj, String brncode, String user,
            CbsAutoNeftDetails neftAutoObj) throws ApplicationException {
        Date curDt = new Date();
        String msg = "";
        try {
            Double txnAmount = obj.getTxnAmt().doubleValue();
            Float trsnumber = ftsRemote.getTrsNo();
            Float recnumber = ftsRemote.getRecNo();
            String creditAcNo = obj.getDebitAccountNo();

            String glAccount = "";

            String txnDetails = "Reversed Outward Neft-Rtgs";
            Integer tranDesc = 66;

            if (obj.getPaymentType().equalsIgnoreCase("IMPS")) {
                txnDetails = "Reversed Outward IMPS";
                tranDesc = 76;
                glAccount = ftsRemote.getCodeFromCbsParameterInfo("IMPS-OW-HEAD");
            } else {
                glAccount = neftAutoObj.getOwHead().trim();
            }
            if (obj.getCPSMSMessageId() != null && !obj.getCPSMSMessageId().trim().equals("")) {
                txnDetails = "CPSMS:-Cr Tran Id:" + ParseFileUtil.addTrailingZeros(obj.getCPSMSTranIdCrTranId(), 16) + ":-Batch No:" + ParseFileUtil.addTrailingZeros(obj.getCPSMSBatchNo(), 16) + ":-MessageId:" + obj.getCPSMSMessageId();
                tranDesc = 72;
            }

            if (creditAcNo.substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.SF_AC.getAcctCode())) {  //Dividend Case.
                GlTableDAO glTableDAO = new GlTableDAO(entityManager);
                Gltable glEntity = glTableDAO.findByMsgflag(40);
                if (glEntity == null || glEntity.getAcNo().equals("") || glEntity.getAcNo().length() != 12) {
                    throw new ApplicationException("Please define proper dividend payable head in gltable.");
                }
                String divDetails = "Dividend for acno " + creditAcNo;
                String fyear = obj.getRemmitInfo().substring(obj.getRemmitInfo().lastIndexOf(":") + 1).trim();
                int result = entityManager.createNativeQuery("Insert Into dividend_recon(acno,cramt,dramt,ty,"
                        + "trantype,iy,enterby,authby,details,bcode,dest_brnid, org_brnid,auth,disdate,trandesc,"
                        + "fyear,payby,trantime,recno,trsno) Values ('" + creditAcNo + "'," + txnAmount + ",0,"
                        + "0,2,0,'" + user + "','" + user + "','" + divDetails + "','HO',"
                        + "'" + brncode + "','" + brncode + "','Y','" + ymd.format(curDt) + "',110,'" + fyear + "',"
                        + "3,now()," + recnumber + "," + trsnumber + ")").executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Dividend Posting Failed");
                }
                creditAcNo = glEntity.getAcNo();
                msg = interBranchFacade.cbsPostingCx(creditAcNo, 0, ymd.format(curDt), txnAmount, 0,
                        2, txnDetails, 0f, "A", "", "", 3, 0f, recnumber, tranDesc,
                        ftsRemote.getCurrentBrnCode(creditAcNo), brncode, user, user, trsnumber, "", "");
                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
            } else {
                msg = interBranchFacade.cbsPostingSx(creditAcNo, 0, ymd.format(curDt), txnAmount, 0,
                        2, txnDetails, 0f, "A", "", "", 3, 0f, recnumber, tranDesc,
                        ftsRemote.getCurrentBrnCode(creditAcNo), brncode, user, user, trsnumber, "", "");
                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
            }

            recnumber = ftsRemote.getRecNo();

            msg = interBranchFacade.cbsPostingCx(glAccount, 1, ymd.format(curDt), txnAmount, 0,
                    2, txnDetails, 0f, "A", "", "", 3, 0f, recnumber, tranDesc, brncode,
                    brncode, user, user, trsnumber, "", "");
            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }
            msg = ftsRemote.updateBalance(ftsRemote.getAccountNature(glAccount), glAccount, 0, txnAmount, "Y", "Y");
            if (!(msg.equalsIgnoreCase("TRUE"))) {
                throw new ApplicationException(msg);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    public void generateYesBankIwReturnFiles(CbsAutoNeftDetails neftAutoObj) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        NeftFileDetailsDAO neftFileDetailsDAO = new NeftFileDetailsDAO(entityManager);
        NeftRtgsStatusDAO neftRtgsStatusDAO = new NeftRtgsStatusDAO(entityManager);
        SimpleDateFormat dMyy = new SimpleDateFormat("ddMMyyyy");
        NumberFormat formatter = new DecimalFormat("#.##");
        String onlyAlphabetRegex = "[A-Za-z]*";
        String drCustName = "", beneName = "";
        try {
            ut.begin();
            //Extract List To Return
            List<NeftRtgsStatus> returnList = neftRtgsStatusDAO.getUnSuccessEntity("AUTO", neftAutoObj.getNeftBankName().trim());
            if (returnList.isEmpty()) {
                throw new ApplicationException("There is no data to generate the inward return file.");
            }

            //Extracting Debit A/c Number
            String parentDrAccount = "";
            AbbParameterInfoDAO abbParameterInfoDAO = new AbbParameterInfoDAO(entityManager);
            List<AbbParameterInfo> abbParameterInfoList = abbParameterInfoDAO.getEntityByPurpose("NEFT-OW-DEBIT-ACCOUNT");
            if (abbParameterInfoList.isEmpty()) {
                throw new ApplicationException("NEFT-OW-DEBIT-ACCOUNT head is not defined in ABB Parameterinfo");
            }
            for (AbbParameterInfo abbPojo : abbParameterInfoList) {
                parentDrAccount = abbPojo.getAcno();
            }

            //Outward Location
            String owComLocalFilePath = neftAutoObj.getOwLocalFilePath().trim();
            String owLocalFilePath = owComLocalFilePath.substring(0, owComLocalFilePath.lastIndexOf("/") + 1);
            //Preliminary Data
            List dataList = entityManager.createNativeQuery("select ifnull(neft_ow_bank_name,'') as neft_ow_bank_name,"
                    + "bank_code from mb_sms_sender_bank_detail").getResultList();
            if (dataList.isEmpty()) {
                throw new Exception("Please define neft_ow_bank_name in sender bank detail");
            }
            Vector ele = (Vector) dataList.get(0);
            String owBankName = ele.get(0).toString().trim();
            String bankCode = ele.get(1).toString().trim();
            if (owBankName.equals("") || !owBankName.matches(onlyAlphabetRegex)) {
                throw new Exception("Please define neft_ow_bank_name in sender bank detail");
            }
            owBankName = owBankName.length() > 24 ? owBankName.substring(0, 24) : owBankName;

            Date curDateTime = new Date();
            Date currentDt = ymd.parse(ymd.format(curDateTime));
            Long seqNo = neftFileDetailsDAO.getMaxSrl(currentDt);
            String fileName = owBankName + dMyy.format(curDateTime)
                    + ParseFileUtil.addTrailingZeros(seqNo.toString(), 3) + ".txt";
            FileWriter fw = new FileWriter(owLocalFilePath + neftAutoObj.getNeftBankName().trim().toLowerCase() + "/" + fileName);
            //Logging of generated file with seq no.
            NeftFileDetails fileEntity = new NeftFileDetails();
            NeftFileDetailsPK fileEntityPK = new NeftFileDetailsPK();
            fileEntityPK.setFileDate(currentDt);
            fileEntityPK.setSeqNo(seqNo);
            fileEntity.setNeftFileDetailsPK(fileEntityPK);
            fileEntity.setFileName(fileName);
            fileEntity.setGenTime(new Date());
            neftFileDetailsDAO.save(fileEntity);

            //Header Line
            String headerFileName = fileName.substring(0, fileName.indexOf("."));
            headerFileName = headerFileName.length() > 20 ? headerFileName.substring(0, 20)
                    : ParseFileUtil.addSuffixSpaces(headerFileName, 20);
            String singleLine = "H" + dmy.format(curDateTime) + headerFileName + "\n";
            fw.write(singleLine);
            //Data Line
            BigDecimal amount = new BigDecimal("0");
            for (int i = 0; i < returnList.size(); i++) {
                NeftRtgsStatus entity = returnList.get(i);
                String msgType = "";
                if (entity.getTxnType().equalsIgnoreCase("NEFT")) {
                    msgType = "N06";
                } else if (entity.getTxnType().equalsIgnoreCase("RTGS")) {
                    msgType = "R41";
                } else {
                    msgType = "A";
                }

                drCustName = owBankName;
                beneName = entity.getSenderName().trim();
                if (beneName.equals("") || beneName.length() == 0) {
                    beneName = "RTN OF" + ParseFileUtil.addSuffixSpaces("", 1) + entity.getSenderIfsc().trim();
                } else {
                    beneName = beneName.length() > 35 ? beneName.substring(0, 35) : beneName;
                }

                amount = amount.add(entity.getAmount());
                String iwUtr = entity.getUtr().trim();
                String completeUtr = "";
                if (iwUtr.length() > 16) {
                    completeUtr = iwUtr;
                    iwUtr = iwUtr.substring(0, 16);
                } else if (iwUtr.length() < 16) {
                    iwUtr = ParseFileUtil.addSuffixSpaces(iwUtr, 16);
                }

//                singleLine = "D" + ParseFileUtil.addSuffixSpaces(msgType, 3) + ParseFileUtil.addTrailingZeros(parentDrAccount, 15)
//                        + ParseFileUtil.addSuffixSpaces(drCustName, 35) + ParseFileUtil.addSuffixSpaces("", 105)
//                        + entity.getSenderIfsc()
//                        + ParseFileUtil.addSuffixSpaces(entity.getSenderAccount(), 34) + ParseFileUtil.addSuffixSpaces(beneName, 35)
//                        + ParseFileUtil.addSuffixSpaces("", 140) + iwUtr
//                        + dmy.format(entity.getNeftRtgsStatusPK().getDt()) + ParseFileUtil.addTrailingZeros(entity.getAmount().toString(), 14)
//                        + ParseFileUtil.addSuffixSpaces("RTGS NEFT PAYMENT BY " + bankCode.toUpperCase(), 27)
//                        + ParseFileUtil.addSuffixSpaces(completeUtr, 33) + ParseFileUtil.addSuffixSpaces("", 99) + "\n";

                singleLine = "D" + ParseFileUtil.addSuffixSpaces(msgType, 3) + ParseFileUtil.addTrailingZeros(parentDrAccount, 15)
                        + ParseFileUtil.addSuffixSpaces(drCustName, 35) + ParseFileUtil.addSuffixSpaces("", 105)
                        + entity.getSenderIfsc()
                        + ParseFileUtil.addSuffixSpaces(entity.getSenderAccount(), 34) + ParseFileUtil.addSuffixSpaces(beneName, 35)
                        + ParseFileUtil.addSuffixSpaces("", 140) + iwUtr
                        + dmy.format(entity.getNeftRtgsStatusPK().getDt()) + ParseFileUtil.addTrailingZeros(entity.getAmount().toString(), 14)
                        + ParseFileUtil.addSuffixSpaces(entity.getUtr().trim(), 27)
                        + ParseFileUtil.addSuffixSpaces(completeUtr, 33) + ParseFileUtil.addSuffixSpaces("", 99) + "\n";

                fw.write(singleLine);
            }
            //Footer Line
            singleLine = "F" + ParseFileUtil.addTrailingZeros(String.valueOf(returnList.size()), 5)
                    + ParseFileUtil.addTrailingZeros(formatter.format(amount), 14) + "\n";
            fw.write(singleLine);
            fw.close();
            //Writing into outward backup folder
            createFilesBackup(owLocalFilePath + neftAutoObj.getNeftBankName().trim().toLowerCase() + "/",
                    neftAutoObj.getOwLocalFileBackupPath() + neftAutoObj.getNeftBankName().trim().toLowerCase() + "/");
            System.out.println("YesBank-H2H-IW-Return file is generated successfully.........");
            //Status Updation
            for (int i = 0; i < returnList.size(); i++) {
                NeftRtgsStatus obj = returnList.get(i);
                obj.setStatus("Sponsor");
                neftRtgsStatusDAO.update(obj);
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
    }

    @Override
    public String[] idbiReturnDescription(String cbsReason) throws Exception {
        String[] arr = new String[2];
        if (cbsReason.toLowerCase().contains("this account no does not exist")
                || cbsReason.toLowerCase().contains("a/c number does not exist")
                || cbsReason.toLowerCase().contains("acno is not in proper state")
                || cbsReason.toLowerCase().contains("account code does not exist for")
                || cbsReason.toLowerCase().contains("invalid a/c no")
                || cbsReason.toLowerCase().contains("error occured: - account no. can not be blank or should be in proper state")) {
            arr[0] = IdbiReasonCodeEnum.R03.getCode();
            arr[1] = IdbiReasonCodeEnum.R03.getReason();
        } else if (cbsReason.toLowerCase().contains("operation stopped for this account")) {
            arr[0] = IdbiReasonCodeEnum.R09.getCode();
            arr[1] = IdbiReasonCodeEnum.R09.getReason();
        } else if (cbsReason.toLowerCase().contains("account is closed")) {
            arr[0] = IdbiReasonCodeEnum.R01.getCode();
            arr[1] = IdbiReasonCodeEnum.R01.getReason();
        } else if (cbsReason.toLowerCase().contains("transaction is not allowed for this type of account")
                || cbsReason.toLowerCase().contains("account has been frozen")
                || cbsReason.toLowerCase().contains("sorry,invalid account status")) {
            arr[0] = IdbiReasonCodeEnum.R11.getCode();
            arr[1] = IdbiReasonCodeEnum.R11.getReason();
        } else {
            arr[0] = IdbiReasonCodeEnum.R11.getCode();
            arr[1] = IdbiReasonCodeEnum.R11.getReason();
        }
        return arr;
    }

    public void sendOutwardMessages(List<NeftOwDetails> resultList, CbsAutoNeftDetails neftAutoObj) throws Exception {
        BranchMasterDAO branchMasterDAO = new BranchMasterDAO(entityManager);
        try {
            //Prelimiary data Extraction
            List list = entityManager.createNativeQuery("select code from cbs_parameterinfo where "
                    + "name in('BANK_APP_IDENTIFIER')").getResultList();
            if (list.isEmpty()) {
                throw new Exception("Please define bank application identifier.");
            }
            Vector tempVec = (Vector) list.get(0);
            String bankAppIdentifier = tempVec.get(0).toString().trim();
            if (bankAppIdentifier.equals("") || bankAppIdentifier.length() == 0) {
                throw new Exception("Please define proper bank application identifier.");
            }
            //Separation of neft and rtgs transactions.
            List<NeftOwDetails> onlyNeftData = new ArrayList<NeftOwDetails>();
            List<NeftOwDetails> onlyRtgsData = new ArrayList<NeftOwDetails>();
            for (int i = 0; i < resultList.size(); i++) {
                if (resultList.get(i).getPaymentType().equalsIgnoreCase("N")) {
                    onlyNeftData.add(resultList.get(i));
                } else if (resultList.get(i).getPaymentType().equalsIgnoreCase("R")) {
                    onlyRtgsData.add(resultList.get(i));
                }
            }
            //For neft N06 transactions.
            for (NeftOwDetails obj : onlyNeftData) {
                //Block A
                BranchMaster branchMasterEntity = branchMasterDAO.getEntityByBrnCode(Integer.parseInt(ftsRemote.getCurrentBrnCode(obj.getDebitAccountNo().trim())));
                String branchMasterIfsc = branchMasterEntity.getIfscCode().trim();

//                BlockAItems blockA = getBlockAObject("N", bankAppIdentifier, obj, "N06", branchMasterIfsc);
                //Block 4
                com.cbs.pojo.neftrtgs.sbi.N06 block4 = new com.cbs.pojo.neftrtgs.sbi.N06();

                block4.setHeaderTranRefNo(SbiNeftConstant.HEADER_2020 + "M" + obj.getUniqueCustomerRefNo().trim());  //Confirm It
                block4.setTotalTran(SbiNeftConstant.HEADER_1106 + "1");

                String txnAmount = obj.getTxnAmt().toString();
                txnAmount = txnAmount.contains(".") ? txnAmount.replace(".", ",") : txnAmount + ",00";

                block4.setTotalAmount(SbiNeftConstant.HEADER_4063 + txnAmount); //Check it in testing
                block4.setDetailTranRefNo(SbiNeftConstant.DETAIL_2020 + "0" + obj.getUniqueCustomerRefNo().trim());    //Confirm It
                block4.setAmount(SbiNeftConstant.DETAIL_4038 + txnAmount); //Check it in testing
                block4.setValueDt(SbiNeftConstant.DETAIL_3380 + ymd.format(dmy.parse(obj.getPaymentDate())));
                block4.setSenderIfsc(SbiNeftConstant.DETAIL_5756 + branchMasterIfsc);
                block4.setSenderAccountNo(SbiNeftConstant.DETAIL_6021 + obj.getDebitAccountNo().trim());

                String debitAcName = ftsRemote.getCustName(obj.getDebitAccountNo().trim());
                debitAcName = debitAcName.replaceAll("[\\W_]", " ");
                debitAcName = debitAcName.length() > 50 ? debitAcName.substring(0, 50) : debitAcName;
                block4.setSenderAccountName(SbiNeftConstant.DETAIL_6091 + debitAcName);

                String remmitInfo = obj.getRemmitInfo();
                remmitInfo = remmitInfo.replaceAll("[\\W_]", " ");
                remmitInfo = remmitInfo.length() > 35 ? remmitInfo.substring(0, 35) : remmitInfo;
                block4.setOriginatorOfRemittance(SbiNeftConstant.DETAIL_7002 + remmitInfo);

                block4.setBeneficiaryIfsc(SbiNeftConstant.DETAIL_5569 + obj.getOutsideBankIfscCode().trim());
                block4.setBeneficiaryAccountNo(SbiNeftConstant.DETAIL_6061 + obj.getCreditAccountNo().trim());

                String beneficiaryName = obj.getBeneficiaryName().trim();
                beneficiaryName = beneficiaryName.replaceAll("[\\W_]", " ");
                beneficiaryName = beneficiaryName.length() > 50 ? beneficiaryName.substring(0, 50) : beneficiaryName;
                block4.setBeneficiaryAccountName(SbiNeftConstant.DETAIL_6081 + beneficiaryName);

                block4.setBeneficiaryAddress(SbiNeftConstant.DETAIL_5565 + beneficiaryName);
                block4.setSenderToReceiverInfo(SbiNeftConstant.DETAIL_7495 + ParseFileUtil.getFixedLengthNewLineStr(obj.getRemmitInfo(), 35));
                block4.setEndIdentifier(SbiNeftConstant.BLOCK4_END_IDENTIFIER);

//                String n06Message = blockA.toString() + SbiNeftConstant.BLOCK4_BEGIN_IDENTIFIER + "\n" + block4.toString();
                String n06Message = SbiNeftConstant.BLOCK4_BEGIN_IDENTIFIER + "\n" + block4.toString();
                outwardLoggingAndSendToWS(n06Message, obj, neftAutoObj);
            }
            //For neft R41 transactions.
            for (NeftOwDetails obj : onlyRtgsData) {
                //Block A
                BranchMaster branchMasterEntity = branchMasterDAO.getEntityByBrnCode(Integer.parseInt(ftsRemote.getCurrentBrnCode(obj.getDebitAccountNo().trim())));
                String branchMasterIfsc = branchMasterEntity.getIfscCode().trim();

                BlockAItems blockA = getBlockAObject("R", bankAppIdentifier, obj, "R41", branchMasterIfsc);
                //Block 4
                com.cbs.pojo.neftrtgs.sbi.R41 block4 = new com.cbs.pojo.neftrtgs.sbi.R41();
                block4.setTranRefNo(SbiNeftConstant.R41_DEATIL_2020 + "0" + obj.getUniqueCustomerRefNo().trim());

                String txnAmount = obj.getTxnAmt().toString();
                txnAmount = txnAmount.contains(".") ? txnAmount.replace(".", ",") : txnAmount + ",00";

                block4.setValueDtCurrencyAndAmount(SbiNeftConstant.R41_DEATIL_4488 + ymd.format(dmy.parse(obj.getPaymentDate())) + "INR" + txnAmount);

                String debitAcName = ftsRemote.getCustName(obj.getDebitAccountNo().trim());
                debitAcName = debitAcName.replaceAll("[\\W_]", " ");
                debitAcName = debitAcName.length() > 35 ? debitAcName.substring(0, 35) : debitAcName;
                block4.setOrderingCustomer(SbiNeftConstant.R41_DEATIL_5500 + debitAcName);

                block4.setCustomerMobileOrEmail(SbiNeftConstant.R41_DEATIL_5629 + obj.getSenderCommModeType().trim() + "\n" + obj.getSenderCommMode().trim());
                block4.setSenderIfsc(SbiNeftConstant.R41_DEATIL_5518 + branchMasterIfsc);
                block4.setReceiverIfsc(SbiNeftConstant.R41_DEATIL_6500 + obj.getOutsideBankIfscCode().trim());
                block4.setSenderAccount(SbiNeftConstant.R41_DEATIL_5546 + "/C/" + obj.getDebitAccountNo().trim());

                String beneficiaryName = obj.getBeneficiaryName().trim();
                beneficiaryName = beneficiaryName.replaceAll("[\\W_]", " ");
                beneficiaryName = beneficiaryName.length() > 35 ? beneficiaryName.substring(0, 35) : beneficiaryName;
                block4.setBeneficiaryCustomer(SbiNeftConstant.R41_DEATIL_5561 + "/" + obj.getCreditAccountNo() + "\n" + beneficiaryName);

                block4.setPaymentDetail(SbiNeftConstant.R41_DEATIL_7023 + debitAcName);
                block4.setSenderToReceiverInfo(SbiNeftConstant.R41_DEATIL_7495 + ParseFileUtil.getFixedLengthNewLineStr(obj.getRemmitInfo(), 35));

                block4.setEndIdentifier(SbiNeftConstant.BLOCK4_END_IDENTIFIER);

                String r41Message = blockA.toString() + SbiNeftConstant.BLOCK4_BEGIN_IDENTIFIER + "\n" + block4.toString();
                outwardLoggingAndSendToWS(r41Message, obj, neftAutoObj);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void outwardLoggingAndSendToWS(String message, NeftOwDetails obj,
            CbsAutoNeftDetails neftAutoObj) throws Exception {
        try {
            //Logging
            String fileName = obj.getPaymentType() + obj.getUniqueCustomerRefNo().trim() + ".txt";
            FileWriter fw = new FileWriter(neftAutoObj.getOwLocalFileBackupPath() + fileName);
            fw.write(message);
            fw.close();

            int n = entityManager.createNativeQuery("update neft_ow_details set outward_file_name='" + fileName + "' where "
                    + "uniquecustomerrefno='" + obj.getUniqueCustomerRefNo().trim() + "'").executeUpdate();
            if (n <= 0) {
                throw new Exception("Updation problem in outward details.");
            }
            System.out.println("Before Sending The Message-->" + message);
            //Sending message to bridge utility
            byte[] encodedBytes = Base64.encode(message.getBytes());
            message = new String(encodedBytes, Charset.forName("UTF-8"));
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForLocation(props.getProperty("sendUrl") + message, "");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public BlockAItems getBlockAObject(String txnType, String bankAppIdentifier, NeftOwDetails obj,
            String subMsgType, String senderIfsc) throws Exception {
        try {
            BlockAItems blockA = new BlockAItems();
            blockA.setBlockAIdentifier(SbiNeftConstant.BLOCKA_BEGIN_IDENTIFIER);
            blockA.setBnkAppIdentifier(bankAppIdentifier);
            blockA.setMsgIdentifier("F01");
            blockA.setiOIdentifier("O");
            blockA.setMsgType("298");
            blockA.setSubMsgType(subMsgType);

            blockA.setSenderIFSC(senderIfsc);
            blockA.setReceiverIFSC(obj.getOutsideBankIfscCode().trim());
            blockA.setDeliveryMonitoringFlag(2);
            blockA.setOpenNotificationFlag(2);
            blockA.setNonDeliveryWarningFlag(2);
            blockA.setObsolescencePeriod("000");
            blockA.setMsgUserReference("M" + obj.getUniqueCustomerRefNo().trim());  //Confirm It
            blockA.setPossibleDuplicateFlag(2);
            blockA.setServiceIdentifier("EFT");
            blockA.setOriginatingDate(ymd.format(dmy.parse(obj.getPaymentDate())));
            blockA.setOriginatingTime(new SimpleDateFormat("hhmm").format(new Date()));
            blockA.setTestingAndTrainingFlag(2);
            blockA.setSequenceNumber("000000000");
            blockA.setFiller("XXXXXXXXX");
            blockA.setUniqueTransactionReference("XXXXXXXXXXXXXXXX");
            blockA.setRtgsPriority(99);
            blockA.setBlockAEndIdentifier(SbiNeftConstant.BLOCKA_END_IDENTIFIER);

            return blockA;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private void sbiInwardProcess(CbsAutoNeftDetails neftAutoObj) throws Exception {
        SimpleDateFormat ymdms = new SimpleDateFormat("yyMMddHHmmssSSS");
        UserTransaction ut = context.getUserTransaction();
        NeftOwDetailsDAO neftOwDetailsDAO = new NeftOwDetailsDAO(entityManager);
        try {
            RestTemplate restTemplate = new RestTemplate();

            String receivedMessage = restTemplate.getForObject(props.getProperty("receiveUri"), String.class);

//            String receivedMessage = "{A:EFTF01I298N02HDFC0000002HDFC0000001122005abcdefgh123456782EFT2005090615062000000811"
//                    + "XXXXXXXXXXXXXXXXXXXXXXXXX00}{4::2020:ICICTRN100001487:3535:0900:5180:2:4110:400,00:"
//                    + "2020:0401434400009L01:5756:VIJB0000002:6021:333333:6091:Satyanarayana:7002:Credit to siva:"
//                    + "5569:HDFC0CCBL06:"
//                    + "6310:10:"
//                    + "6061:061000800401:"
//                    + "6081:Dinesh Pratap Singh:"
//                    + "5565:Vasundhara:"
//                    + "7495:Remit Info:"
//                    + "4038:300,00:"
//                    + "3380:20070706:"
//                    + "3375:20070707"
//                    + ":2020:0401434400009L02:"
//                    + "5756:VIJB0000002:6021:333333:6091:Satyanarayana:7002:Credit to siva:"
//                    + "5569:ICIC0000002:"
//                    + "6310:10:6061:7884585:6081:Siva prasad:5565:IDRBT,HYDERABAD:7495:IDRBT HYD:4038:100,00:"
//                    + "3380:20070706:3375:20070707-}";
//            String receivedMessage = "{A:RTGF01O298R41SBIN0000695SBIN0006240222000SBIN3152207799992XXX2015080806412000000000XXXXXXXXXSBIN31522077999999}{4::2020:SBIN315220779999:4488:20150808INR0010,00\n"
//                    + ":5500:SBI\n"
//                    + "PATHANKOT, GURDASPUR, PUNJAB\n"
//                    + ":5518:SBINB000695\n"
//                    + ":6500:SBINB006240\n"
//                    + ":5546:/C/00000010314671447\n"
//                    + "XXXXXXXXXXXXXXX\n"
//                    + ":5561:/061000800401\n"
//                    + "DINESH PRATAP SINGH\n"
//                    + "SIONTI KUTHER\n"
//                    + ":7023:GDR020708150059002\n"
//                    + ":7495:/URGENT/GDR0207270004236\n"
//                    + "//PAYB ADDITIONAL DEPUTY CO\n"
//                    + ":5629:EML\n"
//                    + "zilaparishad850@gmail.com\n"
//                    + "-}";
//            String receivedMessage = "{A:RTGF01I298R09RBIN0IFTP00HDFC0123098222000MUR12345678901232RTG2003081111252000000000XXXXXXXXXHDFCH0312700000300}{4:"
//                    + ":2020:0160627145202141"
//                    + ":6450:Y"
//                    + ":6346:R04"
//                    + ":3525:164143"
//                    + "-}";
//            String receivedMessage = "{A:RTGF01I298R90CANB0239777CANB0123ABD222000MUR12345678901232"
//                    + "RTG2003081111252000000000XXXXXXXXXCANBH0312700000300}{4:"
//                    + ":2020:0160627144504396"
//                    + ":1076:N"
//                    + ":6346:H00"
//                    + "-}";
            String messageType = receivedMessage.trim().substring(13, 16);

            //Message Logging for single message. If there is multiple receiving concept then here will change.
            //Currently I think we receive only single message at a time.
            String fileName = messageType + ymdms.format(new Date()) + ".txt";
            FileWriter fw = new FileWriter(neftAutoObj.getIwLocalFileBackupPath() + neftAutoObj.getNeftBankName().toLowerCase() + "/" + fileName);
            fw.write(receivedMessage);
            fw.close();
            //Parsing and processing of coming messages.
            if (messageType.equalsIgnoreCase("N02")) {
                Map<String, String> fieldNameMap = ParseFileUtil.getN02FieldNameMap();
                List<ExcelReaderPojo> pojoList = ParseFileUtil.parseSbiIncomingN02Message(receivedMessage, fieldNameMap);
                if (pojoList.get(0).getReasonCode() == null
                        || pojoList.get(0).getReasonCode().equals("")) {
                    //Inward Neft 
                    String result = remote.neftRtgsUploadProcess(pojoList, "90", "System",
                            neftAutoObj.getIwHead(), neftAutoObj.getProcess(), neftAutoObj.getNeftBankName());
                    iwFileConclusionAfterProcessing(result);
                } else {
                    //Outward Neft Return
                }
            } else if (messageType.equalsIgnoreCase("R41")) {
                Map<String, String> fieldNameMap = ParseFileUtil.getR41AdaptorFieldNameMap();

                String senderIfsc = receivedMessage.trim().substring(16, 27);
                String receiverIfsc = receivedMessage.trim().substring(27, 38);

                List<ExcelReaderPojo> pojoList = ParseFileUtil.parseSbiIncomingR41Message(receivedMessage, fieldNameMap,
                        senderIfsc, receiverIfsc);

                String result = remote.neftRtgsUploadProcess(pojoList, "90", "System",
                        neftAutoObj.getIwHead(), neftAutoObj.getProcess(), neftAutoObj.getNeftBankName());
                iwFileConclusionAfterProcessing(result);
            } else if (messageType.equalsIgnoreCase("R09")) {
                try {
                    ut.begin();
                    Map<String, String> fieldNameMap = ParseFileUtil.getR09AdaptorFieldNameMap();
                    AckR09Adapter obj = ParseFileUtil.parseR09AckMessage(receivedMessage, fieldNameMap);

                    NeftOwDetails neftOwDetailsObj = neftOwDetailsDAO.getSingleNeftOwDetailsInstrument(obj.getTranRefNo().trim().substring(1));
                    if (neftOwDetailsObj != null) {
                        if (obj.getSettledIndicator().equalsIgnoreCase("Y")) {
                            if (!(neftOwDetailsObj.getStatus().equalsIgnoreCase("U")
                                    || neftOwDetailsObj.getStatus().equalsIgnoreCase("S"))) {
                                neftOwDetailsObj.setStatus("S");
                                neftOwDetailsObj.setDetails("Paid");

                                neftOwDetailsDAO.update(neftOwDetailsObj);
                                //Sms Sending
                                try {
                                    sendUtrSms(neftOwDetailsObj.getStatus(), neftOwDetailsObj, neftOwDetailsObj.getUtrNo(),
                                            neftOwDetailsObj.getCmsBankRefNo());
                                } catch (Exception ex) {
                                    System.out.println("Problem In H2H UTR updation message In SBI-->" + ex.getMessage());
                                }
                                //End here
                            }
                        } else if (obj.getSettledIndicator().equalsIgnoreCase("N")) {
                            if (!neftOwDetailsObj.getStatus().equalsIgnoreCase("U")) {
                                String msg = reverseOutwardTransaction(neftOwDetailsObj, "90", "System", neftAutoObj);
                                if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                                    neftOwDetailsObj.setStatus("U");
                                    neftOwDetailsObj.setDetails("Un-Paid");
                                    System.out.println("Outward Reversal is Successfull.");
                                    //Deaf Updation
                                    ftsRemote.lastTxnDateUpdation(neftOwDetailsObj.getDebitAccountNo().trim());

                                    neftOwDetailsDAO.update(neftOwDetailsObj);
                                    //Sms Sending
                                    try {
                                        sendUtrSms(neftOwDetailsObj.getStatus(), neftOwDetailsObj, neftOwDetailsObj.getUtrNo(),
                                                neftOwDetailsObj.getCmsBankRefNo());
                                    } catch (Exception ex) {
                                        System.out.println("Problem In H2H UTR updation message In SBI-->" + ex.getMessage());
                                    }
                                    //End here
                                }
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
            } else if (messageType.equalsIgnoreCase("R90")) {
                try {
                    ut.begin();
                    Map<String, String> fieldNameMap = ParseFileUtil.getR90AdaptorFieldNameMap();
                    AckR90Adapter obj = ParseFileUtil.parseR90AckMessage(receivedMessage, fieldNameMap);
                    NeftOwDetails neftOwDetailsObj = neftOwDetailsDAO.getSingleNeftOwDetailsInstrument(obj.getTranRefNo().substring(1));
                    if (neftOwDetailsObj != null) {
                        if (obj.getAckIndicator().equalsIgnoreCase("Y")) {
                            if (!(neftOwDetailsObj.getStatus().equalsIgnoreCase("U")
                                    || neftOwDetailsObj.getStatus().equalsIgnoreCase("S"))) {
                                neftOwDetailsObj.setStatus("S");
                                neftOwDetailsObj.setDetails("Paid");

                                neftOwDetailsDAO.update(neftOwDetailsObj);
                                //Sms Sending
                                try {
                                    sendUtrSms(neftOwDetailsObj.getStatus(), neftOwDetailsObj, neftOwDetailsObj.getUtrNo(),
                                            neftOwDetailsObj.getCmsBankRefNo());
                                } catch (Exception ex) {
                                    System.out.println("Problem In H2H UTR updation message In SBI-->" + ex.getMessage());
                                }
                                //End here
                            }
                        } else if (obj.getAckIndicator().equalsIgnoreCase("N")) {
                            if (!neftOwDetailsObj.getStatus().equalsIgnoreCase("U")) {
                                String msg = reverseOutwardTransaction(neftOwDetailsObj, "90", "System", neftAutoObj);
                                if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                                    neftOwDetailsObj.setStatus("U");
                                    neftOwDetailsObj.setDetails("Un-Paid");
                                    System.out.println("Outward Reversal is Successfull.");
                                    //Deaf Updation
                                    ftsRemote.lastTxnDateUpdation(neftOwDetailsObj.getDebitAccountNo().trim());

                                    neftOwDetailsDAO.update(neftOwDetailsObj);
                                    //Sms Sending
                                    try {
                                        sendUtrSms(neftOwDetailsObj.getStatus(), neftOwDetailsObj, neftOwDetailsObj.getUtrNo(),
                                                neftOwDetailsObj.getCmsBankRefNo());
                                    } catch (Exception ex) {
                                        System.out.println("Problem In H2H UTR updation message In SBI-->" + ex.getMessage());
                                    }
                                    //End here
                                }
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
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void generateSBINeftRtgsReturn(CbsAutoNeftDetails neftAutoObj) throws Exception {
        NeftRtgsStatusDAO neftRtgsStatusDAO = new NeftRtgsStatusDAO(entityManager);
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            System.out.println("In SBI IW Return Processing");
            //Prelimiary data Extraction
            List list = entityManager.createNativeQuery("select code from cbs_parameterinfo where "
                    + "name in('BANK_APP_IDENTIFIER')").getResultList();
            if (list.isEmpty()) {
                throw new Exception("Please define bank application identifier.");
            }
            Vector tempVec = (Vector) list.get(0);
            String bankAppIdentifier = tempVec.get(0).toString().trim();
            if (bankAppIdentifier.equals("") || bankAppIdentifier.length() == 0) {
                throw new Exception("Please define proper bank application identifier.");
            }
            //Generation of Neft Inward Return.
            List<NeftRtgsStatus> dataList = neftRtgsStatusDAO.getEntityByTxnTypeAndStatus("N02", "AUTO", neftAutoObj.getNeftBankName().trim());
            for (int i = 0; i < dataList.size(); i++) {
                NeftRtgsStatus obj = dataList.get(i);
                //Block A
                BlockAItems blockA = getBlockAObjectForReturn("N", bankAppIdentifier, obj, "N07");
                //Block 4
                com.cbs.pojo.neftrtgs.sbi.N07 block4 = new com.cbs.pojo.neftrtgs.sbi.N07();

                block4.setHeaderTranRefNo(SbiNeftConstant.N07_HEADER_2020 + blockA.getMsgUserReference()); //Confirm it
                block4.setTotalNo(SbiNeftConstant.N07_HEADER_1106 + "1");

                String txnAmount = obj.getAmount().toString();
                txnAmount = txnAmount.contains(".") ? txnAmount.replace(".", ",") : txnAmount + ",00";

                block4.setTotalAmount(SbiNeftConstant.N07_HEADER_4063 + txnAmount);  //Check it in testing
                block4.setDetailTranRefNo(SbiNeftConstant.N07_DETAIL_2020 + "0" + blockA.getMsgUserReference().substring(1)); //Confirm it
                block4.setRelatedRefNo(SbiNeftConstant.N07_DETAIL_2006 + obj.getUtr());
                block4.setAmount(SbiNeftConstant.N07_DETAIL_4038 + txnAmount);
                block4.setValueDt(SbiNeftConstant.N07_DETAIL_3380 + blockA.getOriginatingDate()); //check it for 8 digit date
                block4.setSendingIfsc(SbiNeftConstant.N07_DETAIL_5756 + obj.getReceiverIfsc().trim());
                block4.setSendingAccountNo(SbiNeftConstant.N07_DETAIL_6021 + obj.getBeneAccount().trim());
                block4.setSenderName(SbiNeftConstant.N07_DETAIL_6091 + obj.getBeneName().trim());
                block4.setOriginatorOfRemittance(SbiNeftConstant.N07_DETAIL_7002 + obj.getRemittanceOriginator().trim());
                block4.setBeneficiaryIfsc(SbiNeftConstant.N07_DETAIL_5569 + obj.getSenderIfsc().trim());
                block4.setBeneficiaryAccountNo(SbiNeftConstant.N07_DETAIL_6061 + obj.getSenderAccount().trim());
                block4.setBeneficiaryName(SbiNeftConstant.N07_DETAIL_6081 + obj.getSenderName().trim());

                String[] arr = sbiReturnDescription(obj.getReason() == null ? "" : obj.getReason().trim());

                block4.setReasonCode(SbiNeftConstant.N07_DETAIL_6346 + arr[0]);
                block4.setReasonDesc(SbiNeftConstant.N07_DETAIL_6366 + arr[1]);
                block4.setEndIdentifier(SbiNeftConstant.BLOCK4_END_IDENTIFIER);

                String n07Message = blockA.toString() + SbiNeftConstant.BLOCK4_BEGIN_IDENTIFIER + "\n" + block4.toString();

                inwardReturnLoggingAndSendToWS(n07Message, obj, neftAutoObj, blockA.getMsgUserReference().substring(1).trim());
            }
            //Generation of Rtgs Inward Return.
            dataList = neftRtgsStatusDAO.getEntityByTxnTypeAndStatus("R41", "AUTO", neftAutoObj.getNeftBankName().trim());
            for (int i = 0; i < dataList.size(); i++) {
                NeftRtgsStatus obj = dataList.get(i);
                //Block A
                BlockAItems blockA = getBlockAObjectForReturn("R", bankAppIdentifier, obj, "R42");
                //Block 4
                com.cbs.pojo.neftrtgs.sbi.R42 block4 = new com.cbs.pojo.neftrtgs.sbi.R42();

                block4.setTranRefNo(SbiNeftConstant.R42_DEATIL_2020 + "0" + blockA.getMsgUserReference().substring(1));
                block4.setRelatedRefNo(SbiNeftConstant.R42_DEATIL_2006 + obj.getUtr().trim());

                String txnAmount = obj.getAmount().toString();
                txnAmount = txnAmount.contains(".") ? txnAmount.replace(".", ",") : txnAmount + ",00";

                block4.setValueDtCurrencyAndAmount(SbiNeftConstant.R42_DEATIL_4488 + ymd.format(obj.getNeftRtgsStatusPK().getDt()) + "INR" + txnAmount);
                block4.setBeneficiaryIfsc(SbiNeftConstant.R42_DEATIL_6521 + obj.getSenderIfsc().trim());
                block4.setEndIdentifier(SbiNeftConstant.BLOCK4_END_IDENTIFIER);

                String r42Message = blockA.toString() + SbiNeftConstant.BLOCK4_BEGIN_IDENTIFIER + "\n" + block4.toString();

                inwardReturnLoggingAndSendToWS(r42Message, obj, neftAutoObj, blockA.getMsgUserReference().substring(1).trim());
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
    }

    public BlockAItems getBlockAObjectForReturn(String txnType, String bankAppIdentifier,
            NeftRtgsStatus obj, String subMsgType) throws Exception {
        SimpleDateFormat ymdms = new SimpleDateFormat("yyMMddHHmmssSSS");
        try {
            BlockAItems blockA = new BlockAItems();
            blockA.setBlockAIdentifier(SbiNeftConstant.BLOCKA_BEGIN_IDENTIFIER);
            blockA.setBnkAppIdentifier(bankAppIdentifier);
            blockA.setMsgIdentifier("F01");
            blockA.setiOIdentifier("O");
            blockA.setMsgType("298");
            blockA.setSubMsgType(subMsgType);

            blockA.setSenderIFSC(obj.getReceiverIfsc());
            blockA.setReceiverIFSC(obj.getSenderIfsc());
            blockA.setDeliveryMonitoringFlag(2);
            blockA.setOpenNotificationFlag(2);
            blockA.setNonDeliveryWarningFlag(2);
            blockA.setObsolescencePeriod("000");
            blockA.setMsgUserReference("M" + ymdms.format(new Date()));  //Confirm It
            blockA.setPossibleDuplicateFlag(2);
            blockA.setServiceIdentifier("EFT");
            blockA.setOriginatingDate(ymd.format(obj.getNeftRtgsStatusPK().getDt()));  //Check it in testing
            blockA.setOriginatingTime(new SimpleDateFormat("hhmm").format(new Date()));
            blockA.setTestingAndTrainingFlag(2);
            blockA.setSequenceNumber("000000000");
            blockA.setFiller("XXXXXXXXX");
            blockA.setUniqueTransactionReference("XXXXXXXXXXXXXXXX");
            blockA.setRtgsPriority(99);
            blockA.setBlockAEndIdentifier(SbiNeftConstant.BLOCKA_END_IDENTIFIER);

            return blockA;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public String[] sbiReturnDescription(String cbsReason) throws Exception {
        String[] arr = new String[2];
        if (cbsReason.toLowerCase().contains("this account no does not exist")
                || cbsReason.toLowerCase().contains("a/c number does not exist")
                || cbsReason.toLowerCase().contains("acno is not in proper state")
                || cbsReason.toLowerCase().contains("account code does not exist for")
                || cbsReason.toLowerCase().contains("invalid a/c no")
                || cbsReason.toLowerCase().contains("error occured: - account no. can not be blank or should be in proper state")) {
            arr[0] = SbiReasonCodeEnum.R03.getCode();
            arr[1] = SbiReasonCodeEnum.R03.getReason();
        } else if (cbsReason.toLowerCase().contains("operation stopped for this account")) {
            arr[0] = SbiReasonCodeEnum.R09.getCode();
            arr[1] = SbiReasonCodeEnum.R09.getReason();
        } else if (cbsReason.toLowerCase().contains("account is closed")) {
            arr[0] = SbiReasonCodeEnum.R01.getCode();
            arr[1] = SbiReasonCodeEnum.R01.getReason();
        } else if (cbsReason.toLowerCase().contains("transaction is not allowed for this type of account")
                || cbsReason.toLowerCase().contains("account has been frozen")
                || cbsReason.toLowerCase().contains("sorry,invalid account status")) {
            arr[0] = SbiReasonCodeEnum.R11.getCode();
            arr[1] = SbiReasonCodeEnum.R11.getReason();
        } else {
            arr[0] = SbiReasonCodeEnum.R11.getCode();
            arr[1] = SbiReasonCodeEnum.R11.getReason();
        }
        return arr;
    }

    public void inwardReturnLoggingAndSendToWS(String message, NeftRtgsStatus obj,
            CbsAutoNeftDetails neftAutoObj, String returnTranRefNo) throws Exception {
        try {
            //Logging
            String fileName = "RET" + obj.getUtr().trim() + ".txt";
            FileWriter fw = new FileWriter(neftAutoObj.getOwLocalFileBackupPath() + fileName);
            fw.write(message);
            fw.close();

            int n = entityManager.createNativeQuery("update neft_rtgs_status set "
                    + "return_tran_ref_no='" + returnTranRefNo + "',status='Return' where "
                    + "utr='" + obj.getUtr().trim() + "' and status='Unsuccess' "
                    + "and reason<>'THIS UTR ALREADY PROCESSED.'").executeUpdate();
            if (n <= 0) {
                throw new Exception("Updtion problem in inward return.");
            }
            System.out.println("Before Sending The Message-->" + message);
            //Sending message to bridge utility
            byte[] encodedBytes = Base64.encode(message.getBytes());
            message = new String(encodedBytes, Charset.forName("UTF-8"));
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForLocation(props.getProperty("sendUrl") + message, "");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    //This is used for HDFC outward
    private String getAccountPermanentAddress(String accountNo) throws Exception {
        String accountAddress = "";
        try {
            List list;
            String acNature = ftsRemote.getAccountNature(accountNo);
            if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                list = entityManager.createNativeQuery("select ifnull(bankaddress,'') from bnkadd where alphacode='HO'").getResultList();
            } else {
                list = entityManager.createNativeQuery("select ifnull(peraddressline1,'') from cbs_customer_master_detail cust,"
                        + "customerid id where cust.customerid=id.custid and id.acno='" + accountNo + "'").getResultList();
            }
            if (list.isEmpty()) {
                throw new Exception("Please define permanent address one of customer having account no-->" + accountNo);
            }
            Vector ele = (Vector) list.get(0);
            accountAddress = ele.get(0).toString().trim();
            accountAddress = accountAddress.replaceAll("[\\W_]", " ");
            if (accountAddress.trim().equals("")) {
                throw new Exception("Please define permanent address one of customer having account no-->" + accountNo);
            }
            accountAddress = accountAddress.length() > 140 ? accountAddress.substring(0, 140) : accountAddress;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return accountAddress;
    }

    //IBL Outward process 
    private void iblOWTxnProcess(CbsAutoNeftDetails neftAutoObj) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            System.out.println("In IBL Ow Processing=========>");
            NeftOwDetailsDAO neftOwDetailsDAO = new NeftOwDetailsDAO(entityManager);
            //Evaluating Ow Details.
            List<NeftOwDetails> resultList = neftOwDetailsDAO.getNeftOwDetailsData(neftAutoObj.getNeftBankName().trim());
            if (resultList.isEmpty()) {
                throw new ApplicationException("There is no data to send the IBL request.");
            }

            String parentDrAccount = "";

            AbbParameterInfoDAO abbParameterInfoDAO = new AbbParameterInfoDAO(entityManager);
            List<AbbParameterInfo> abbParameterInfoList = abbParameterInfoDAO.getEntityByPurpose("IBL-OW-DEBIT-ACCOUNT");
            if (abbParameterInfoList.isEmpty()) {
                throw new ApplicationException("IBL-OW-DEBIT-ACCOUNT head is not defined in ABB Parameterinfo");
            }
            for (AbbParameterInfo abbPojo : abbParameterInfoList) {
                parentDrAccount = abbPojo.getAcno();
            }
            List dataList = entityManager.createNativeQuery("select * from cbs_parameterinfo where name in('IBL_CHECKER_ID','IBL_MAKER_ID','IBL_CLIENT_ID')").getResultList();
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

            for (NeftOwDetails owObj : resultList) {
                ut.begin();
                pymtReq = new PaymentRequest();
                txn = new PaymentRequest.Transaction();

                txn.setCustomerRefNum(owObj.getUniqueCustomerRefNo());
                txn.setTranType(owObj.getPaymentType());
                txn.setValueDate(ddMMyyyy.format(owObj.getDt()));
                txn.setDebitAccount(parentDrAccount);

                txn.setAmount(owObj.getTxnAmt().toString());
                txn.setBENEACNO(owObj.getCreditAccountNo());
                txn.setBENEBANK("");
                txn.setBENEBRANCH("");

                txn.setBENEIFSCCODE(owObj.getOutsideBankIfscCode());
                txn.setBenName(owObj.getBeneficiaryName());
                txn.setBeneEmailId(owObj.getBeneficiaryEmailId());

                txn.setBeneMMId("");
                txn.setBeneMobileNo(owObj.getBeneficiaryMobileNo());
                txn.setCheckerId(checkerId);
                txn.setMakerId(makerId);

                txn.setReserve1("");
                txn.setReserve2("");
                txn.setReserve3("");
                pymtReq.setTransaction(txn);

                String soapInput = IblUtil.createSOAPRequest(IblUtil.createPymtReq(clientId, IblUtil.getXmlStringWithoutPrifix(pymtReq)));
                System.out.println(soapInput);
                //Logging the payment request
                File owLogDirectory = new File(new File(neftAutoObj.getOwLocalFileBackupPath().trim())
                        + "/" + neftAutoObj.getNeftBankName().trim().toLowerCase() + "/" + ymd.format(new Date()) + "/");
                if (!owLogDirectory.exists()) {
                    owLogDirectory.mkdirs();
                }

                FileWriter fw = new FileWriter(owLogDirectory + "/" + owObj.getUniqueCustomerRefNo() + ".txt");
                fw.write(soapInput);
                fw.close();
                //Live
                Document responseDoc = IblUtil.executeWSOperation(wsUrl, "ProcessTxnInXml", soapInput);
                //Logging payment acknowledgement

                xmlDocumentToString(responseDoc, owLogDirectory, owObj.getUniqueCustomerRefNo().trim(), "ACK");

                NodeList nodeList = responseDoc.getElementsByTagName("PaymentResponse");
                JAXBContext jaxbContext = JAXBContext.newInstance(PaymentResponse.class);

                Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
                PaymentResponse res = (PaymentResponse) unMarshaller.unmarshal(nodeList.item(0));
                //End Here


                //For Testing
//                PaymentResponse res = new PaymentResponse();
//
//                PaymentResponse.Transaction val = new PaymentResponse.Transaction();
//                val.setCustomerRefNo("181128102845705");
//                val.setIBLRefNo("IBL1234");
//                val.setStatusCode("R004");
//                val.setStatusDesc("Record not found");
//                res.setTransaction(val);
                //End Here



                System.out.println("Ref no = " + res.getTransaction().getCustomerRefNo());
                System.out.println("IBL Ref no = " + res.getTransaction().getIBLRefNo());
                System.out.println("IBL Status Code = " + res.getTransaction().getStatusCode());
                System.out.println("IBL Status = " + res.getTransaction().getStatusDesc());

                if (res.getTransaction().getStatusCode().equals("R000")) {
                    owObj.setCmsBankRefNo(res.getTransaction().getIBLRefNo());
                    owObj.setStatus("I");
                    owObj.setDetails("Sent to IndusInd Bank");
                    neftOwDetailsDAO.update(owObj);
                    //System.out.println("IBL ow entry has been updated.");
                } else {
                    owObj.setStatus("E"); //E- Error from IBL
                    owObj.setDetails("Posting Error");
                    neftOwDetailsDAO.update(owObj);
                }
                System.out.println("IBL ow entry has been updated.");
                ut.commit();
            }
        } catch (Exception ex) {
            try {
                System.out.println("IBL Error Is>>>>" + ex.getMessage());
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
    }

    //IBL Payment Enquiry
    public void iblPaymentEnquiry(CbsAutoNeftDetails neftAutoObj) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            System.out.println("In IBL Payment Enquiry Processing=========>");
            NeftOwDetailsDAO neftOwDetailsDAO = new NeftOwDetailsDAO(entityManager);
            //Evaluating Ow Details.
            List<NeftOwDetails> resultList = neftOwDetailsDAO.getNeftOwPendingDetails(neftAutoObj.getNeftBankName().trim());
            if (resultList.isEmpty()) {
                throw new ApplicationException("There is no data to fetch enquiry.");
            }

            String clientId = ftsRemote.getCodeFromCbsParameterInfo("IBL_CLIENT_ID");

            String wsUrl = IblUtil.createIblWsUrl(neftAutoObj.getHostName(), neftAutoObj.getUserName(), neftAutoObj.getPassword());

            PaymentEnquiry pymtEnqReq;

            for (NeftOwDetails owObj : resultList) {
                ut.begin();

                System.out.println("Status-->" + owObj.getStatus() + " And Cms Ref No-->" + owObj.getCmsBankRefNo().trim());

                pymtEnqReq = new PaymentEnquiry();
                pymtEnqReq.setCustomerId(clientId);
                pymtEnqReq.setIBLRefNo(owObj.getCmsBankRefNo());

                String soapInput = IblUtil.createSOAPRequest(IblUtil.createPymtEnqReq(IblUtil.getXmlStringWithoutPrifix(pymtEnqReq)));
                System.out.println(soapInput);

                //Logging the payment request
                File reportLogDirectory = new File(new File(neftAutoObj.getLocalReportBackupPath().trim())
                        + "/" + neftAutoObj.getNeftBankName().trim().toLowerCase() + "/" + ymd.format(new Date()) + "/");
                if (!reportLogDirectory.exists()) {
                    reportLogDirectory.mkdirs();
                }

                FileWriter fw = new FileWriter(reportLogDirectory + "/" + owObj.getUniqueCustomerRefNo() + ".txt");
                fw.write(soapInput);
                fw.close();

                //Live
                Document responseDoc = IblUtil.executeWSOperation(wsUrl, "GetTxnResponseInXml", soapInput);
                //Logging payment acknowledgement
                xmlDocumentToString(responseDoc, reportLogDirectory, owObj.getUniqueCustomerRefNo().trim(), "ENQ-RES");

                NodeList nodeList = responseDoc.getElementsByTagName("PaymentEnqResp");
                JAXBContext jaxbContext = JAXBContext.newInstance(PaymentEnqResp.class);

                Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
                PaymentEnqResp res = (PaymentEnqResp) unMarshaller.unmarshal(nodeList.item(0));
                //End Here
//                
                //Testing
//                PaymentEnqResp res = new PaymentEnqResp();
//
//                PaymentEnqResp.Transaction val = new PaymentEnqResp.Transaction();
//                val.setCustomerRefNo("181128102845705");
//                val.setIBLRefNo("IBL1234");
//                val.setStatusCode("J");
//                val.setStatusDesc("Customer Reference Number : 181126101817260 already exists in System.--Customer Reference Number : 181126101817260 already exists in System..--");
//                val.setUTR("10101010");
//                res.setTransaction(val);
                //End Here

                System.out.println("Ref no = " + res.getTransaction().getCustomerRefNo());
                System.out.println("IBL Ref no = " + res.getTransaction().getIBLRefNo());
                System.out.println("IBL Status Code = " + res.getTransaction().getStatusCode());
                System.out.println("IBL Status = " + res.getTransaction().getStatusDesc());

                if (res.getTransaction().getStatusCode().length() < 3) {
                    String status = res.getTransaction().getStatusCode();
                    if (res.getTransaction().getStatusCode().equalsIgnoreCase("E") && res.getTransaction().getTranType().equalsIgnoreCase("IMPS")) {
                        status = "S";
                    }
                    if (status.equalsIgnoreCase("R") || status.equalsIgnoreCase("J")) {
                        if (!owObj.getStatus().equalsIgnoreCase("U")) {
                            String msg = reverseOutwardTransaction(owObj, "90", "System", neftAutoObj);
                            if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                                if (owObj.getStatus().equalsIgnoreCase("S")) {
                                    owObj.setSuccessToFailureFlag("R");
                                }
                                owObj.setStatus("U");
                                owObj.setDetails("Un-Paid");
                                owObj.setReason(res.getTransaction().getStatusDesc());

                                owObj.setUtrNo(res.getTransaction().getUTR());
                                owObj.setResponseUpdateTime(new Date());
                                neftOwDetailsDAO.update(owObj);
                                System.out.println("Outward Reversal is Successfull.");
                                //Deaf Updation
                                ftsRemote.lastTxnDateUpdation(owObj.getDebitAccountNo().trim());
                                //SMS Sending
                                try {
                                    sendUtrSms(owObj.getStatus(), owObj, owObj.getUtrNo(), owObj.getCmsBankRefNo());
                                } catch (Exception ex) {
                                    System.out.println("Problem In H2H UTR updation message In YES-->"
                                            + ex.getMessage());
                                }
                            }
                        }
                    } else if (status.equalsIgnoreCase("S")) {
                        if (!(owObj.getStatus().equalsIgnoreCase("U") || owObj.getStatus().equalsIgnoreCase("S"))) {
                            owObj.setStatus("S");
                            owObj.setDetails("Paid");
                            owObj.setReason("");

                            owObj.setUtrNo(res.getTransaction().getUTR());
                            owObj.setResponseUpdateTime(new Date());
                            neftOwDetailsDAO.update(owObj);
                            //SMS Sending
                            try {
                                sendUtrSms(owObj.getStatus(), owObj, owObj.getUtrNo(), owObj.getCmsBankRefNo());
                            } catch (Exception ex) {
                                System.out.println("Problem In H2H UTR updation message In YES-->"
                                        + ex.getMessage());
                            }
                        }
                    } else {
                        owObj.setStatus(status);
                        owObj.setDetails(res.getTransaction().getStatusDesc());
                        owObj.setReason("");

                        owObj.setUtrNo(res.getTransaction().getUTR());
                        owObj.setResponseUpdateTime(new Date());
                        neftOwDetailsDAO.update(owObj);
                    }
                } else {
                    if (res.getTransaction().getStatusCode().equalsIgnoreCase("R004")) {
                        owObj.setStatus("P");
                        owObj.setDetails("Re-Posting");
                        owObj.setResponseUpdateTime(new Date());
                        neftOwDetailsDAO.update(owObj);
                    } else if (res.getTransaction().getStatusCode().equalsIgnoreCase("R003")
                            || res.getTransaction().getStatusCode().equalsIgnoreCase("R005")
                            || res.getTransaction().getStatusCode().equalsIgnoreCase("R008")) {
                        if (!owObj.getStatus().equalsIgnoreCase("U")) {
                            String msg = reverseOutwardTransaction(owObj, "90", "System", neftAutoObj);
                            if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                                if (owObj.getStatus().equalsIgnoreCase("S")) {
                                    owObj.setSuccessToFailureFlag("R");
                                }
                                owObj.setStatus("U");
                                owObj.setDetails("Un-Paid");
                                owObj.setReason(res.getTransaction().getStatusDesc());

                                owObj.setUtrNo(res.getTransaction().getUTR());
                                owObj.setResponseUpdateTime(new Date());
                                neftOwDetailsDAO.update(owObj);
                                System.out.println("Outward Reversal is Successfull.");
                                //Deaf Updation
                                ftsRemote.lastTxnDateUpdation(owObj.getDebitAccountNo().trim());
                                //SMS Sending
                                try {
                                    sendUtrSms(owObj.getStatus(), owObj, owObj.getUtrNo(), owObj.getCmsBankRefNo());
                                } catch (Exception ex) {
                                    System.out.println("Problem In H2H UTR updation message In IBL-->"
                                            + ex.getMessage());
                                }
                            }
                        }
                    } else {
                        owObj.setStatus(res.getTransaction().getStatusCode());
                        owObj.setDetails(res.getTransaction().getStatusDesc());
                        owObj.setUtrNo(res.getTransaction().getUTR());

                        owObj.setResponseUpdateTime(new Date());
                        neftOwDetailsDAO.update(owObj);
                    }
                }
                ut.commit();
            }
        } catch (Exception ex) {
            try {
                System.out.println("IBL Enquiry Error Is>>>>" + ex.getMessage());
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
    }

    @Override
    public String finalIblReturn(List<ExcelReaderPojo> fileData, CbsAutoNeftDetails neftAutoObj) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        System.out.println("In IBL Final Return Processing=========>");
        NeftOwDetailsDAO neftOwDetailsDAO = new NeftOwDetailsDAO(entityManager);
        try {
            ut.begin();
            for (ExcelReaderPojo obj : fileData) {
                NeftOwDetails neftOwDetailsObj = neftOwDetailsDAO.getSingleNeftOwDetailsInstrument(obj.getRelatedRefNo().trim());
                if (neftOwDetailsObj == null) {
                    continue;
                }

                if (obj.getReasonCode().trim().equalsIgnoreCase("R") && !neftOwDetailsObj.getStatus().trim().equalsIgnoreCase("U")) {
                    String msg = reverseOutwardTransaction(neftOwDetailsObj, "90", "System", neftAutoObj);

                    if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                        if (neftOwDetailsObj.getStatus().equalsIgnoreCase("S")) {
                            neftOwDetailsObj.setSuccessToFailureFlag("R");
                        }
                        neftOwDetailsObj.setStatus("U");
                        neftOwDetailsObj.setDetails("Un-Paid");
                        neftOwDetailsObj.setReason(obj.getReason().trim());

                        neftOwDetailsObj.setResponseUpdateTime(new Date());
                        neftOwDetailsDAO.update(neftOwDetailsObj);
                        System.out.println("Outward Reversal is Successfull For Customer Ref No-->" + obj.getRelatedRefNo());
                        ftsRemote.lastTxnDateUpdation(neftOwDetailsObj.getDebitAccountNo().trim());
                        try {
                            sendUtrSms(neftOwDetailsObj.getStatus(), neftOwDetailsObj, neftOwDetailsObj.getUtrNo(),
                                    neftOwDetailsObj.getCmsBankRefNo());
                        } catch (Exception ex) {
                            System.out.println("Problem In H2H UTR updation message In YES-->"
                                    + ex.getMessage());
                        }
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
        return "success";
    }

    //Yes bank API Processing
    private void yesOWTxnProcess(CbsAutoNeftDetails neftAutoObj) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            System.out.println("In YES API OW Processing=========>");
            NeftOwDetailsDAO neftOwDetailsDAO = new NeftOwDetailsDAO(entityManager);
            AbbParameterInfoDAO abbParameterInfoDAO = new AbbParameterInfoDAO(entityManager);
            BranchMasterDAO branchMasterDAO = new BranchMasterDAO(entityManager);
            //Evaluating Ow Details.
            List<NeftOwDetails> resultList = neftOwDetailsDAO.getNeftOwDetailsData(neftAutoObj.getNeftBankName().trim());
            if (resultList.isEmpty()) {
                throw new ApplicationException("There is no data to send the YES API OW request.");
            }

            String parentDrAccount = "";
            List<AbbParameterInfo> abbParameterInfoList = abbParameterInfoDAO.getEntityByPurpose("NEFT-OW-DEBIT-ACCOUNT");
            if (abbParameterInfoList.isEmpty()) {
                throw new ApplicationException("NEFT-OW-DEBIT-ACCOUNT head is not defined in ABB Parameterinfo");
            }
            for (AbbParameterInfo abbPojo : abbParameterInfoList) {
                parentDrAccount = abbPojo.getAcno();
            }
            List dataList = entityManager.createNativeQuery("select * from cbs_parameterinfo where name "
                    + "in('YES-CUSTOMER-ID','YES-PARTNER-CODE')").getResultList();
            if (dataList.isEmpty()) {
                throw new Exception("Please define the YES CUSTOMER ID and PARTNER CODE.");
            }
            String yesCustomerId = "", yesPartnerCode = "";
            for (int i = 0; i < dataList.size(); i++) {
                Vector vect = (Vector) dataList.get(i);
                if (vect.get(0).toString().equalsIgnoreCase("YES-CUSTOMER-ID")) {
                    yesCustomerId = vect.get(1).toString();
                }
                if (vect.get(0).toString().equalsIgnoreCase("YES-PARTNER-CODE")) {
                    yesPartnerCode = vect.get(1).toString();
                }
            }

            String wsUrl = YesUtil.createYesWsUrl(neftAutoObj.getHostName(), neftAutoObj.getUserName(),
                    neftAutoObj.getPassword()); //Change

            com.cbs.neftrtgs.yes.api.PaymentRequest payReq;
            for (NeftOwDetails owObj : resultList) {
                payReq = new com.cbs.neftrtgs.yes.api.PaymentRequest();
                payReq.setVersion("1");
                payReq.setUniqueRequestNo(owObj.getUniqueCustomerRefNo());
                payReq.setPartnerCode(yesPartnerCode.trim());
                payReq.setCustomerId(yesCustomerId.trim());
                payReq.setDebitAccountNo(parentDrAccount.trim());
                payReq.setRemitterAccountNo(owObj.getDebitAccountNo());

                BranchMaster branchMasterEntity = branchMasterDAO.getEntityByBrnCode(Integer.parseInt(
                        ftsRemote.getCurrentBrnCode(owObj.getDebitAccountNo().trim())));
                String branchMasterIfsc = branchMasterEntity.getIfscCode().trim();
                payReq.setRemitterIFSC(branchMasterIfsc);

                String debitAcName = ftsRemote.getCustName(owObj.getDebitAccountNo().trim());
                debitAcName = debitAcName.replaceAll("[\\W_]", " ");
                debitAcName = debitAcName.length() > 50 ? debitAcName.substring(0, 50) : debitAcName;
                payReq.setRemitterName(debitAcName);
                payReq.setBeneficiaryName(owObj.getBeneficiaryName());
                payReq.setBeneficiaryAccountNo(owObj.getCreditAccountNo());
                payReq.setBeneficiaryIFSC(owObj.getOutsideBankIfscCode());
                String transferType = "";
                switch (owObj.getPaymentType()) {
                    case "N":
                        transferType = "NEFT";
                        break;
                    case "R":
                        transferType = "RTGS";
                        break;
                    case "A":
                        transferType = "FT";
                        break;
                }
                payReq.setTransferType(transferType);
                payReq.setTransferCurrencyCode("INR");
                payReq.setTransferAmount(owObj.getTxnAmt().toString());
                payReq.setRemitterToBeneficiaryInfo(owObj.getRemmitInfo());

                String soapInput = YesUtil.createSOAPRequest(payReq);
                System.out.println(soapInput);
                //Logging the payment request
                File owLogDirectory = new File(new File(neftAutoObj.getOwLocalFileBackupPath().trim())
                        + "/" + neftAutoObj.getNeftBankName().trim().toLowerCase() + "/" + ymd.format(new Date()) + "/");
                if (!owLogDirectory.exists()) {
                    owLogDirectory.mkdirs();
                }

                FileWriter fw = new FileWriter(owLogDirectory + "/" + owObj.getUniqueCustomerRefNo() + ".txt");
                fw.write(soapInput);
                fw.close();

                Document responseDoc = YesUtil.executeWSOperation(wsUrl, "ProcessTxnInXml", soapInput); //Change In This Method
                //Logging payment acknowledgement
                xmlDocumentToString(responseDoc, owLogDirectory, owObj.getUniqueCustomerRefNo().trim(), "ACK");

                NodeList nodeList = responseDoc.getElementsByTagName("PaymentResponse");
                JAXBContext jaxbContext = JAXBContext.newInstance(PaymentResponse.class);

                Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
                PaymentResponse res = (PaymentResponse) unMarshaller.unmarshal(nodeList.item(0));
                System.out.println("Ref no = " + res.getTransaction().getCustomerRefNo());
                System.out.println("IBL Ref no = " + res.getTransaction().getIBLRefNo());
                System.out.println("IBL Status Code = " + res.getTransaction().getStatusCode());
                System.out.println("IBL Status = " + res.getTransaction().getStatusDesc());

                if (res.getTransaction().getStatusCode().equals("R000")) {
                    owObj.setCmsBankRefNo(res.getTransaction().getIBLRefNo());
                    owObj.setStatus("I");
                    owObj.setDetails("Sent to IndusInd Bank");
                    neftOwDetailsDAO.update(owObj);
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
    }

    public static void xmlDocumentToString(Document newDoc, File owBackupPath, String cbsUniqueCustomerRefNo, String mode) throws Exception {
        DOMSource domSource = new DOMSource(newDoc);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        StringWriter sw = new StringWriter();
        StreamResult sr = new StreamResult(sw);
        transformer.transform(domSource, sr);
        FileWriter fw = new FileWriter(new File(owBackupPath + "/" + cbsUniqueCustomerRefNo + "-" + mode + ".txt"));
        fw.write(sw.toString());
        fw.close();
    }

    @Override
    public String outwardNeftRtgsReversalProcessing(List<ExcelReaderPojo> pojoList, CbsAutoNeftDetails autoObj,
            String orgnBrCode, String userName) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        NeftOwDetailsDAO neftOwDetailsDAO = new NeftOwDetailsDAO(entityManager);
        try {
            ut.begin();
            for (int i = 0; i < pojoList.size(); i++) {
                ExcelReaderPojo obj = pojoList.get(i);
                NeftOwDetails neftOwDetailsObj = neftOwDetailsDAO.getSingleNeftOwDetailsInstrument(obj.getRelatedRefNo().trim());
                if (neftOwDetailsObj != null) {
                    if (!neftOwDetailsObj.getStatus().equalsIgnoreCase("U")) {
                        String msg = reverseOutwardTransaction(neftOwDetailsObj, "90", "System", autoObj);
                        if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                            if (neftOwDetailsObj.getStatus().equalsIgnoreCase("S")) {
                                neftOwDetailsObj.setSuccessToFailureFlag("R");
                            }
                            neftOwDetailsObj.setStatus("U");
                            neftOwDetailsObj.setDetails("Un-Paid");
                            neftOwDetailsObj.setReason(obj.getReason());
                            System.out.println("Outward Neft Reversal is Successfull.");
                            //Deaf Updation
                            ftsRemote.lastTxnDateUpdation(neftOwDetailsObj.getDebitAccountNo().trim());
                            neftOwDetailsObj.setResponseUpdateTime(new Date());
                            neftOwDetailsDAO.update(neftOwDetailsObj);
                            //Sms Sending
                            try {
                                sendUtrSms(neftOwDetailsObj.getStatus(), neftOwDetailsObj, neftOwDetailsObj.getUtrNo(),
                                        neftOwDetailsObj.getCmsBankRefNo());
                            } catch (Exception ex) {
                                System.out.println("Problem In Outward Neft Reversal SMS In IDBI-->" + ex.getMessage());
                            }
                            //End here
                        }
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

    //Here Paths are only the directory location.
    public void download(CbsAutoNeftDetails neftAutoObj, String localFileDir, String remoteFileDir, String filePattern) {
        StandardFileSystemManager manager = new StandardFileSystemManager();
        try {
            manager.init();
            //Create remote connection string
            String connectionStr = createConnectionString(neftAutoObj.getHostName().trim(), neftAutoObj.getUserName().trim(),
                    neftAutoObj.getPassword().trim(), remoteFileDir);
            //Setting the options
            FileSystemOptions options = createDefaultOptions();
            //Fetching the files from remote location
            FileObject[] remoteFiles = manager.resolveFile(connectionStr, options).findFiles(new FileFilterSelector(new NeftFileFilter(filePattern)));
            for (FileObject fileObject : remoteFiles) {
                String fileToDownload = fileObject.getName().toString().substring(fileObject.getName().toString().lastIndexOf("/") + 1); //Actual file name only without path
                FileObject localFile = manager.resolveFile(localFileDir + "/" + fileToDownload);

                fileObject.moveTo(localFile);
            }
            System.out.println("File Download Success.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            manager.close();
        }
    }

    //Here Paths are only the directory location.
    public void upload(CbsAutoNeftDetails neftAutoObj, String localFileDir, String remoteFileDir) {
        StandardFileSystemManager manager = new StandardFileSystemManager();
        try {
            File localDir = new File(localFileDir);
            File[] localFiles = localDir.listFiles();
            manager.init();
            for (int i = 0; i < localFiles.length; i++) {
                String onlyFilename = localFiles[i].getName();
                //Create remote connection string
                String connectionStr = createConnectionString(neftAutoObj.getHostName().trim(), neftAutoObj.getUserName().trim(),
                        neftAutoObj.getPassword().trim(), remoteFileDir + "/" + onlyFilename);
                //Setting the options
                FileSystemOptions options = createDefaultOptions();
                //Create local file object
                FileObject localFile = manager.resolveFile(localFiles[i].getAbsolutePath());
                //Create remote file object
                FileObject remoteFile = manager.resolveFile(connectionStr, options);
                //Copy local file to sftp server
                if (localFile.exists()) {
                    System.out.println("Outward File Exists In Main Folder-->" + localFile.getName());
                    remoteFile.copyFrom(localFile, Selectors.SELECT_SELF);
                    individualFileBackupAndRemoval(new File(localFileDir + "/" + onlyFilename), neftAutoObj.getOwLocalFileBackupPath() + neftAutoObj.getNeftBankName().trim().toLowerCase() + "/");
                }
                System.out.println("File upload success");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            manager.close();
        }
    }

    //File deletion from remote location. Here path is absolute filepath with remote location.
    public void delete(CbsAutoNeftDetails neftAutoObj, String remoteFilePath) {
        StandardFileSystemManager manager = new StandardFileSystemManager();
        try {
            manager.init();
            //Create remote connection string
            String connectionStr = createConnectionString(neftAutoObj.getHostName().trim(),
                    neftAutoObj.getUserName().trim(), neftAutoObj.getPassword().trim(), remoteFilePath);
            //Setting the options
            FileSystemOptions options = createDefaultOptions();
            FileObject remoteFile = manager.resolveFile(connectionStr, options);
            if (remoteFile.exists()) {
                remoteFile.delete();
                System.out.println("Delete remote file success");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            manager.close();
        }
    }

    //remoteLocation - It can be absolute file path or absolute directory path
    public static String createConnectionString(String hostName, String username, String password,
            String remoteLocation) throws Exception {
        String userInfo = username + ":" + password;
        System.out.println("UserId-->" + username + ":::::Password-->" + password);
        URI uri = new URI("sftp", userInfo, hostName, 22, remoteLocation, null, null);
        return uri.toString();
    }

    public void individualFileBackupAndRemoval(File fileToDelete, String localIwBackupDir) throws IOException {
        try {
            System.out.println("localIwBackupDir-->" + localIwBackupDir);
            FileInputStream fis = new FileInputStream(fileToDelete);
            FileOutputStream fos = new FileOutputStream(localIwBackupDir + fileToDelete.getName());

            //FileOutputStream fos1 = new FileOutputStream("/opt/cbs-files/ow-bkp/" + fileToDelete.getName()); //For Test

            byte[] buffer = new byte[1024];
            int length;
            //Copy the file content in bytes 
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
                //fos1.write(buffer, 0, length); //For Test
            }
            //Delete the original file
            fos.close();
            //fos1.close(); //For Test
            fis.close();
            System.out.println("Outward File In Backup Folder. Name Is-->" + fileToDelete.getName());
            fileToDelete.delete();
            System.out.println("Original Outward Deleted-->" + fileToDelete.getName());
        } catch (Exception ex) {
            throw new IOException(ex.getMessage());
        }
    }
}
