/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ckycr;

import com.cbs.constant.CkycrEnum;
import com.cbs.dto.ckycr.CKYCRDownloadDetail30;
import com.cbs.dto.ckycr.CKYCRDownloadDetail60;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.dto.ckycr.CKYCRRequestPojo;
import com.cbs.dto.ckycr.UploadResponseDTO;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.pojo.CKYCRDownloadPojo;
import com.cbs.utils.Base64;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ParseFileUtil;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import net.sf.opensftp.SftpException;
import net.sf.opensftp.SftpSession;
import net.sf.opensftp.SftpUtil;
import net.sf.opensftp.SftpUtilFactory;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.vfs2.FileFilterSelector;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

@Stateless(mappedName = "CkycrProcessMgmtFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class CkycrProcessMgmtFacade implements CkycrProcessMgmtFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote ftsRemote;
    @EJB
    private CommonReportMethodsRemote commonReport;
    @EJB
    private CkycrViewMgmtFacadeRemote ckycrViewMgmtRemote;
    @EJB
    private CkycrCommonMgmtFacadeRemote ckycrCommonMgmtRemote;
    @EJB
    private InterBranchTxnFacadeRemote txnRemote;
    private Properties props = null;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmyhms = new SimpleDateFormat("ddMMyyyyhhmmss");
    SimpleDateFormat ddMMyyyy = new SimpleDateFormat("ddMMyyyy");
    SimpleDateFormat dMyyyy = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    private final String propFileName = "/opt/conf/wslocation.properties";
    
    @PostConstruct
    private void loadConfig() {
        try {
            props = new Properties();
            props.load(new FileReader(propFileName));
        } catch (Exception ex) {
            System.out.println("Problem In Bean Initialization And Loading The "
                    + "WSLOCATION Properties File. " + ex.getMessage());
        }
    }

    public void ckycrProcess() {
        System.out.println("In ckycrProcess method()");
        try {
            try {
                processUploadUpdateRequest();
            } catch (Exception e) {
                System.out.println("Problem in processUploadUpdateRequest() method......" + e.getMessage());
                e.printStackTrace();
            }
            Thread.sleep(5000);
            try {
                processUploadResponseStage_1_N();
            } catch (Exception e) {
                System.out.println("Problem in processUploadResponseStage_1_N() method......" + e.getMessage());
                e.printStackTrace();
            }
            Thread.sleep(5000);
            try {
                processDownloadRequest();
            } catch (Exception e) {
                System.out.println("Problem in processDownloadRequest() method......" + e.getMessage());
                e.printStackTrace();
            }
            Thread.sleep(5000);
            try {
                processDownloadResponse();
            } catch (Exception e) {
                System.out.println("Problem in processDownloadResponse() method......" + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception ex) {
            System.out.println("Problem In ckycrProcess() method.");
            ex.printStackTrace();
        }
    }

    private void processUploadUpdateRequest() throws Exception {
        System.out.println("In processUploadUpdateRequest Method");
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List<CKYCRRequestPojo> requestList = new ArrayList<>();
            String[] params = getParameterInfoReportCodes();
            int totalCount = Integer.parseInt(params[1]) - Integer.parseInt(params[2]); //No of items will upload at a time.
            //Extracting Region Code
            List regionList = em.createNativeQuery("select ss_gno,sss_gno from cbs_ref_rec_mapping where gno='04' and s_gno='HO'").getResultList();
            if (regionList.isEmpty()) {
                throw new Exception("Please map region code");
            }
            Vector regionVec = (Vector) regionList.get(0);
            String regionCode = regionVec.get(0).toString().trim();
            String ckycrBrCode = regionVec.get(1).toString().trim();

            List formRequestList = ckycrViewMgmtRemote.getUploadUpdateRequestFromBranches();  //Check if list is empty

            String allCustomers = "";
            int incrementVar = (totalCount - formRequestList.size()) <= 0 ? (totalCount - formRequestList.size()) : 0;
            for (int i = 0; i < formRequestList.size() + incrementVar; i++) {
                CKYCRRequestPojo obj = new CKYCRRequestPojo();
                Vector ele = (Vector) formRequestList.get(i);
                obj.setMode(ele.get(0).toString());
                obj.setCustomerId(ele.get(1).toString());
                obj.setPrimaryBrCode(ele.get(2).toString());
                obj.setRequestBy(ele.get(4).toString());
                obj.setRequestDate(ele.get(5).toString());
                obj.setFetchMode("Form");

                requestList.add(obj);

                if (allCustomers.equals("")) {
                    allCustomers = "\'" + obj.getCustomerId() + "\'";
                } else {
                    allCustomers = allCustomers + ",\'" + obj.getCustomerId() + "\'";
                }
            }

            if ((totalCount - formRequestList.size()) > 0 && Integer.parseInt(params[4]) == 1) {
                //Fetching uploading data from CBS based on scheduler.
                List schedulerRequestList = ckycrViewMgmtRemote.getUploadRequestFromScheduler(totalCount - formRequestList.size());
                for (int i = 0; i < schedulerRequestList.size(); i++) {
                    CKYCRRequestPojo obj = new CKYCRRequestPojo();
                    Vector ele = (Vector) schedulerRequestList.get(i);
                    obj.setMode("UPLOAD");
                    obj.setCustomerId(ele.get(0).toString());
                    obj.setPrimaryBrCode(ele.get(1).toString());
                    obj.setRequestBy("System");
                    obj.setRequestDate(ymd.format(new Date()));
                    obj.setFetchMode("Scheduler");

                    requestList.add(obj);

                    if (allCustomers.equals("")) {
                        allCustomers = "\'" + obj.getCustomerId() + "\'";
                    } else {
                        allCustomers = allCustomers + ",\'" + obj.getCustomerId() + "\'";
                    }
                }
            }

            //Generation of upload zip file.
            try {
                generateUploadUpdateZipFile(totalCount, allCustomers, requestList, regionCode, commonReport.getBrncodeByAlphacode("HO"), ckycrBrCode);
            } catch (Exception ex) {
                System.out.println("Problem In generateUploadUpdateZipFile() method-->" + ex.getMessage());
                ex.printStackTrace();
            }
            //Putting file on sftp server
            File curDtDir = new File(props.getProperty("cbsUploadLocation").trim() + ymd.format(new Date()) + "/");
            if (!curDtDir.exists()) {
                curDtDir.mkdirs();
            }

            //Comment on 05/12/2018
//            SftpUtil util = SftpUtilFactory.getSftpUtil();
//            SftpSession session = getSftpSession(props.getProperty("ckycrSftpHost").trim(), props.getProperty("ckycrSftpUser").trim(),
//                    props.getProperty("ckycrSftpPassword").trim());
//
//            util.put(session, curDtDir + "/" + "*.zip", props.getProperty("ckycrSftpUploadLocation").trim());
//            Thread.sleep(5000);
//            util.put(session, curDtDir + "/" + "*.trg", props.getProperty("ckycrSftpUploadLocation").trim());
//
//            File cbsUploadLocationBackupDir = new File(props.getProperty("cbsUploadLocationBackup").trim() + ymd.format(new Date()) + "/");
//            if (!cbsUploadLocationBackupDir.exists()) {
//                cbsUploadLocationBackupDir.mkdirs();
//            }
//            createBackupAndThenRemoveFile(curDtDir + "/", cbsUploadLocationBackupDir + "/");
//            util.disconnect(session);
            //End here

            System.out.println("Before putting on sftp");
            File cbsUploadLocationBackupDir = new File(props.getProperty("cbsUploadLocationBackup").trim() + ymd.format(new Date()) + "/");
            if (!cbsUploadLocationBackupDir.exists()) {
                cbsUploadLocationBackupDir.mkdirs();
            }

            upload("*.zip", curDtDir + "/", props.getProperty("ckycrSftpUploadLocation").trim(), cbsUploadLocationBackupDir + "/");
            System.out.println("After putting zip file");
            Thread.sleep(5000);
            upload("*.trg", curDtDir + "/", props.getProperty("ckycrSftpUploadLocation").trim(), cbsUploadLocationBackupDir + "/");
            System.out.println("After putting trg file");

            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                ex.printStackTrace();
                throw new Exception(ex.getMessage());
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
    }

    public String generateUploadUpdateZipFile(int totalCount, String allCustomers,
            List<CKYCRRequestPojo> requestList, String regionCode, String branchCode, String ckycrBrCode) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        try {
            if (!requestList.isEmpty() && !allCustomers.equals("")) {
                String[] params = getCbsParameterInfoCodes();
                //Fetching all customers details to upload and/or update.
                List dataList = em.createNativeQuery("select customer.customerid,ifnull(customer.PrimaryBrCode,''),"
                        + "ifnull(customer.CustEntityType,''),ifnull(customer.AcHolderTypeFlag,''),"
                        + "ifnull(customer.AcHolderType,''),ifnull(customer.AcType,''),ifnull(customer.CKYCNo,''),"
                        + "ifnull(customer.title,''),ifnull(customer.custname,''),ifnull(customer.middle_name,''),"
                        + "ifnull(customer.last_name,''),ifnull(customer.fathername,''),ifnull(customer.FatherMiddleName,''),"
                        + "ifnull(customer.FatherLastName,''),ifnull(customer.spouse_name,''),"
                        + "ifnull(customer.SpouseMiddleName,''),ifnull(customer.SpouseLastName,''),"
                        + "ifnull(customer.mothername,''),ifnull(customer.MotherMiddleName,''),ifnull(customer.MotherLastName,''),"
                        + "ifnull(customer.gender,''),ifnull(customer.maritalstatus,''),ifnull(customer.nationality,''),"
                        + "date_format(customer.DateOfBirth,'%Y%m%d'),ifnull(customer.legal_document,''),"
                        + "ifnull(customer.IdentityNo,''),ifnull(customer.IdExpiryDate,''),ifnull(customer.TinIssuingCountry,''),"
                        + "ifnull(customer.nriflag,''),ifnull(customer.PerAddType,''),ifnull(customer.PerAddressLine1,''),"
                        + "ifnull(customer.PerVillage,''),ifnull(customer.PerDistrict,''),ifnull(customer.PerStateCode,''),"
                        + "ifnull(customer.PerCountryCode,''),ifnull(customer.PerPostalCode,''),ifnull(customer.poa,''),"
                        + "ifnull(customer.PerOtherPOA,''),ifnull(customer.PerMailAddSameFlagIndicate,''),"
                        + "ifnull(customer.MailAddType,''),ifnull(customer.MailAddressLine1,''),ifnull(customer.MailVillage,''),"
                        + "ifnull(customer.MailDistrict,''),ifnull(customer.MailStateCode,''),ifnull(customer.MailCountryCode,''),"
                        + "ifnull(customer.MailPostalCode,''),ifnull(customer.MailPoa,''),ifnull(customer.MailOtherPOA,''),"
                        + "ifnull(customer.JuriAddBasedOnFlag,''),ifnull(customer.JuriAddType,''),ifnull(customer.juri_add1,''),"
                        + "ifnull(customer.juri_city,''),ifnull(customer.juri_state,''),ifnull(customer.juri_country,''),"
                        + "ifnull(customer.juri_postal,''),ifnull(customer.JuriPoa,''),ifnull(customer.JuriOtherPOA,''),"
                        + "ifnull(customer.isd_code,''),ifnull(customer.mobilenumber,''),date_format(customer.creationtime,'%Y%m%d'),"
                        + "ifnull(mis.ConstitutionCode,''),ifnull(mis.OccupationCode,''),ifnull(mis.Incorporation_Place,''),"
                        + "ifnull(mis.Commencement_Date,''),ifnull(mis.Mis_Juri_Residence,''),ifnull(mis.Mis_Tin,''),"
                        + "ifnull(mis.Mis_Birth_Country,''),ifnull(mis.Mis_City,''),ifnull(kyc.TypeOfDocSubmitted,''),"
                        + "ifnull(kyc.KycVerifiedUserName,''),ifnull(kyc.BrnCode,''),ifnull(kyc.KycVerifiedBy,''),"
                        + "ifnull(customer.father_spouse_flag,''),ifnull(customer.PAN_GIRNumber,''),ifnull(mis.CountryOfIncorp,''),"
                        + "ifnull(mis.ResidenceCountryTaxLaw,''),date_format(customer.CustUpdationDate,'%Y%m%d'),"
                        + "date_format(kyc.KycVerifiedDate,'%Y%m%d'),ifnull(customer.custimage,'') from cbs_customer_master_detail customer,"
                        + "cbs_cust_misinfo mis,cbs_cust_kyc_details kyc where customer.customerid=mis.customerid and "
                        + "mis.customerid=kyc.customerid and customer.customerid=kyc.customerid and "
                        + "customer.customerid in(" + allCustomers + ") and kyc.txnid in(select max(txnid) from "
                        + "cbs_cust_kyc_details where customerid in(" + allCustomers + ") group by customerid)").getResultList();

                if (dataList.isEmpty()) {
                    throw new Exception("There is no data to upload and update.");
                }
                if (requestList.size() != dataList.size()) {
                    //Fetching matched customer list and updation of unmatched customer.
                    requestList = unMatchedCustomerUpdation(requestList, dataList);
                }
                //Now requestList/dataList is the data to upload. Both have been equals in size.
                requestList = combiningRequestAndData(requestList, dataList);   //Combining the requestList and dataList.
                //Now we have to validate all the request.
                List list = em.createNativeQuery("select bank_code from mb_sms_sender_bank_detail").getResultList();
                if (list.isEmpty()) {
                    throw new Exception("Please define bank code.");
                }
                Vector ele = (Vector) list.get(0);

                List<List<CKYCRRequestPojo>> validCustomers;
                List<CKYCRRequestPojo> uploadRequestList;
                List<CKYCRRequestPojo> updateRequestList;
                try {
                    validCustomers = validateCustomersToUpload(requestList, ele.get(0).toString()); //Now this is the final request to upload/update
                    ut.commit();
                } catch (Exception ex) {
                    System.out.println("In PPP");
                    ex.printStackTrace();
                    throw new Exception(ex.getMessage());
                }
                uploadRequestList = validCustomers.get(0);
                updateRequestList = validCustomers.get(1);
                ut.begin();

                if (uploadRequestList.isEmpty() && updateRequestList.isEmpty()) {
                    throw new Exception("There is no data to upload.");
                }
                //Here total size of valid record
                Integer totalRecord = uploadRequestList.size() + updateRequestList.size(); //If we implement update also then we have to add update list size also.
                String nextBatchNo = ckycrViewMgmtRemote.getNextBatchNo("UPLOAD");  //Upload and update will go in same batch
                String commonFileName = params[1] + "_" + regionCode + "_" + dmyhms.format(new Date()) + "_"
                        + params[2] + "_U" + nextBatchNo;  //Here userid[params[2]] can be modified.

                //Current date directory creation
                File curDtDir = new File(props.getProperty("cbsUploadLocation").trim() + ymd.format(new Date()) + "/");
                if (!curDtDir.exists()) {
                    curDtDir.mkdirs();
                }
                //Upload folder creation.
                File uploadFolder = new File(curDtDir + "/" + commonFileName + "/");
                if (!uploadFolder.exists()) {
                    uploadFolder.mkdirs();
                }
                String commonImgFolderName = "IMG_U" + nextBatchNo;
                //Generation of upload data
                FileWriter fw = null;
                if (!uploadRequestList.isEmpty()) {
                    fw = new FileWriter(uploadFolder + "/" + commonFileName + ".txt");

                    createUploadData(uploadFolder, commonFileName, nextBatchNo, params, totalRecord,
                            ele.get(0).toString(), commonImgFolderName, uploadRequestList, updateRequestList, fw, regionCode, ckycrBrCode);
                }
                System.out.println("After createUploadData");
                //Generation of update data
                if (!updateRequestList.isEmpty()) {
                    createUpdateData(uploadFolder, commonFileName, nextBatchNo, params, totalRecord,
                            ele.get(0).toString(), commonImgFolderName, updateRequestList, uploadRequestList, fw, regionCode, ckycrBrCode);
                    //Note in case of update LINE NUMBER will start from uploadRequestList.size()+1
                }
                System.out.println("After createUpdateData");
                ckycrCommonMgmtRemote.zipFolder(uploadFolder + "/", curDtDir + "/" + commonFileName + ".zip");
                File uploadFolderToDelete = new File(curDtDir + "/" + commonFileName + "/");
                if (uploadFolderToDelete.exists()) {
                    ckycrCommonMgmtRemote.delete(uploadFolderToDelete);
                }
                System.out.println("After zipFolder");
                //Now writing the empty trg file
                FileWriter trgFile = new FileWriter(curDtDir + "/" + commonFileName + ".trg");
                trgFile.write(" ");
                trgFile.close();
                System.out.println("Before Log maintainance of uploaded files");
                //Log maintainance of uploaded files.
                int n = em.createNativeQuery("insert into ckycr_file_detail(mode,batch_no,uploaded_file_name,"
                        + "uploaded_gen_date,response_file_name,actual_mode,response_by,uploaded_branch) "
                        + "values('UPLOAD','" + nextBatchNo + "','" + commonFileName + "',now(),'','','',"
                        + "'" + branchCode + "')").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem in file log maintainance.");
                }
                System.out.println("After Log maintainance of uploaded files");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
        return "true";
    }

    //Note: From here any upload record will not return due to any reason.
    public void createUploadData(File uploadFolder, String commonFileName, String nextBatchNo,
            String[] params, Integer totalRecord, String bankCode, String commonImgFolderName,
            List<CKYCRRequestPojo> uploadRequestList, List<CKYCRRequestPojo> updateRequestList,
            FileWriter fw, String regionCode, String ckycrBrCode) throws Exception {
        try {
            //Text file header preparation
            String header = "10|" + nextBatchNo + "|" + params[1] + "|" + regionCode + "|"
                    + totalRecord + "|" + dMyyyy.format(new Date()) + "|" + params[5] + "|||||" + "\n";
            fw.write(header);
            for (int i = 0; i < uploadRequestList.size(); i++) {
                CKYCRRequestPojo object = uploadRequestList.get(i);
                System.out.println("createUploadData() Method----->" + object.getCustomerId());
                String title = "", applicantFirstName = "", applicantMiddleName = "", applicantLastName = "", applicantName = "",
                        fatherSpouseFlag = "", fatherSpouseTitle = "", fatherSpouseFirstName = "", fatherSpouseMiddleName = "",
                        fatherSpouseLastName = "", motherTitle = "", motherFirstName = "", motherMiddleName = "",
                        motherLastName = "", gender = "", maritalStatus = "", nationality = "", occupationType = "",
                        placeOfIncorp = "", dateOfCommencement = "", incorpCountry = "", countryOfResidenceTaxLaws = "",
                        identificationType = "", legalTin = "", tinIssuingCountry = "", residentialStatus = "",
                        juridictionOfResidence = "", flagJuriOutsideIndia = "", misTin = "", misCountry = "", misCity = "",
                        perPostalCode = "", perOtherPoa = "", mailPostalCode = "", mailPoa = "", juriPostalCode = "", juriAddSameFlag = "";

                String perPoa = object.getPoa().trim();
                if (object.getCustEntityType().equalsIgnoreCase("01")) { //Individual
                    title = object.getTitle().trim().length() > 5 ? object.getTitle().trim().substring(0, 5).trim() : object.getTitle().trim();
                    applicantFirstName = object.getCustName().trim().length() > 50 ? object.getCustName().trim().substring(0, 50).trim() : object.getCustName().trim();
                    applicantMiddleName = !object.getMiddleName().trim().equals("") ? (object.getMiddleName().trim().length() > 50 ? object.getMiddleName().trim().substring(0, 50).trim() : object.getMiddleName().trim()) : object.getMiddleName().trim();
                    applicantLastName = object.getLastName().trim().length() > 50 ? object.getLastName().trim().substring(0, 50).trim() : object.getLastName().trim();
                    fatherSpouseFlag = (object.getPanGirNumber().trim().length() == 10
                            && ParseFileUtil.isValidPAN(object.getPanGirNumber().trim())
                            && object.getFatherSpouseFlag().equalsIgnoreCase("Y")) ? "02" : "01"; //01-Father, 02-Spouse
                    fatherSpouseTitle = getFatherSpouseTitle(fatherSpouseFlag, object.getGender().trim());
                    if (fatherSpouseFlag.equalsIgnoreCase("01")) { //Father
                        fatherSpouseFirstName = object.getFatherName().trim().length() > 50 ? object.getFatherName().trim().substring(0, 50).trim() : object.getFatherName().trim();
                        fatherSpouseMiddleName = !object.getFatherMiddleName().trim().equals("") ? (object.getFatherMiddleName().trim().length() > 50 ? object.getFatherMiddleName().trim().substring(0, 50).trim() : object.getFatherMiddleName().trim()) : object.getFatherMiddleName().trim();
                        fatherSpouseLastName = object.getFatherLastName().trim().length() > 50 ? object.getFatherLastName().trim().substring(0, 50).trim() : object.getFatherLastName().trim();
                    } else if (fatherSpouseFlag.equalsIgnoreCase("02")) { //Spouse
                        fatherSpouseFirstName = object.getSpouseName().trim().length() > 50 ? object.getSpouseName().trim().substring(0, 50).trim() : object.getSpouseName().trim();
                        fatherSpouseMiddleName = !object.getSpouseMiddleName().trim().equals("") ? (object.getSpouseMiddleName().trim().length() > 50 ? object.getSpouseMiddleName().trim().substring(0, 50).trim() : object.getSpouseMiddleName().trim()) : object.getSpouseMiddleName().trim();
                        fatherSpouseLastName = object.getSpouseLastName().trim().length() > 50 ? object.getSpouseLastName().trim().substring(0, 50).trim() : object.getSpouseLastName().trim();
                    }
                    motherTitle = "MRS";
                    motherFirstName = object.getMotherName().trim().length() > 50 ? object.getMotherName().trim().substring(0, 50).trim() : object.getMotherName().trim();
                    motherMiddleName = !object.getMotherMiddleName().trim().equals("") ? (object.getMotherMiddleName().trim().length() > 50 ? object.getMotherMiddleName().trim().substring(0, 50).trim() : object.getMotherMiddleName().trim()) : object.getMotherMiddleName().trim();
                    motherLastName = object.getMotherLastName().trim().length() > 50 ? object.getMotherLastName().trim().substring(0, 50).trim() : object.getMotherLastName().trim();
                    gender = object.getGender().trim();
                    maritalStatus = object.getMaritalStatus().trim();
                    nationality = object.getNationality().trim();
                    occupationType = ckycrCommonMgmtRemote.ckycrOccupationCode(object.getOccupationCode().trim());
                    residentialStatus = object.getNriFlag().trim();
                    juridictionOfResidence = object.getMisJuriResidence().trim();
                    flagJuriOutsideIndia = juridictionOfResidence.equalsIgnoreCase("IN") ? "02" : "01";
                    misTin = flagJuriOutsideIndia.equalsIgnoreCase("01") ? object.getMisTin().trim() : "";
                    misCountry = flagJuriOutsideIndia.equalsIgnoreCase("01") ? object.getMisBirthCountry().trim() : "";
                    misCity = flagJuriOutsideIndia.equalsIgnoreCase("01") ? (object.getMisCity().trim().length() > 50 ? object.getMisCity().trim().substring(0, 50) : object.getMisCity().trim()) : "";
                    perOtherPoa = perPoa.equalsIgnoreCase("99") ? (object.getPerOtherPoa().trim().length() > 75 ? object.getPerOtherPoa().trim().substring(0, 75) : object.getPerOtherPoa().trim()) : "";
                    juriAddSameFlag = object.getJuriAddBasedOnFlag().trim();
                } else if (object.getCustEntityType().equalsIgnoreCase("02")) { //Legal Entity
//                    title = object.getTitle().trim().length() > 5 ? object.getTitle().trim().substring(0, 5).trim() : object.getTitle().trim();
//                    applicantFirstName = object.getCustName().trim().length() > 50 ? object.getCustName().trim().substring(0, 50).trim() : object.getCustName().trim();
//                    applicantMiddleName = !object.getMiddleName().trim().equals("") ? (object.getMiddleName().trim().length() > 50 ? object.getMiddleName().trim().substring(0, 50).trim() : object.getMiddleName().trim()) : object.getMiddleName().trim();
//                    applicantLastName = object.getLastName().trim().length() > 50 ? object.getLastName().trim().substring(0, 50).trim() : object.getLastName().trim();
                    applicantName = object.getCustName().trim().length() > 150 ? object.getCustName().trim().substring(0, 150).trim() : object.getCustName().trim();

                    placeOfIncorp = object.getIncorporationPlace().trim().length() > 50 ? object.getIncorporationPlace().trim().substring(0, 50).trim() : object.getIncorporationPlace().trim();
                    dateOfCommencement = dMyyyy.format(ymd.parse(object.getCommencementDate().trim()));
                    incorpCountry = object.getCountryIncorporation();
                    //placeOfIncorp = incorpCountry.equalsIgnoreCase("IN") ? commonReport.getRefRecDesc("011", object.getIncorporationPlace().trim()) : (object.getIncorporationPlace().trim().length() > 50 ? object.getIncorporationPlace().trim().substring(0, 50).trim() : object.getIncorporationPlace().trim());
                    countryOfResidenceTaxLaws = object.getCountryResidenceTaxLaws();
                    identificationType = object.getLegalDocument().trim();
                    legalTin = identificationType.equalsIgnoreCase("T") ? object.getIdentityNo().trim() : "";
                    tinIssuingCountry = identificationType.equalsIgnoreCase("T") ? object.getTinIssuingCountry().trim() : "";
                    mailPoa = object.getMailPoa().trim();
                }
                //Both
                String dob = dMyyyy.format(ymd.parse(object.getDateOfBirth().trim()));
                String perAddLineOne = object.getPerAddressLineOne().trim().length() > 55 ? object.getPerAddressLineOne().trim().substring(0, 55).trim() : object.getPerAddressLineOne().trim();
                String perVillage = object.getPerVillage().trim().length() > 50 ? object.getPerVillage().trim().substring(0, 50).trim() : object.getPerVillage().trim();
                String perDistrict = object.getPerDistrict().trim().length() > 50 ? object.getPerDistrict().trim().substring(0, 50).trim() : object.getPerDistrict().trim();
                String perCountry = object.getPerCountryCode().trim();
                perPostalCode = perCountry.equalsIgnoreCase("IN") ? object.getPerPostalCode().trim() : "";
                String perMailAddSameFlag = object.getPerMailAddSameFlagIndicate().trim();
                String mailAddLineOne = object.getMailAddressLineOne().trim().length() > 55 ? object.getMailAddressLineOne().trim().substring(0, 55).trim() : object.getMailAddressLineOne().trim();
                String mailVillage = object.getMailVillage().trim().length() > 50 ? object.getMailVillage().trim().substring(0, 50).trim() : object.getMailVillage().trim();
                String mailDistrict = object.getMailDistrict().trim().length() > 50 ? object.getMailDistrict().trim().substring(0, 50).trim() : object.getMailDistrict().trim();
                String mailStateCode = object.getMailStateCode().trim();
                String mailCountry = object.getMailCountryCode().trim();
                mailPostalCode = mailCountry.equalsIgnoreCase("IN") ? object.getMailPostalCode().trim() : "";

//                String juriAddSameFlag = object.getJuriAddBasedOnFlag().trim();
                String juriAddLineOne = object.getJuriAddOne().trim().length() > 55 ? object.getJuriAddOne().trim().substring(0, 55).trim() : object.getJuriAddOne().trim();
                String juriCity = object.getJuriCity().trim().length() > 50 ? object.getJuriCity().trim().substring(0, 50).trim() : object.getJuriCity().trim();
                String juriState = object.getJuriState().trim().length() > 50 ? object.getJuriState().trim().substring(0, 50).trim() : object.getJuriState().trim();
                String juriCountry = object.getJuriCountry().trim();
                juriPostalCode = juriCountry.equalsIgnoreCase("IN") ? object.getJuriPostal().trim() : "";
                String dateOfDeclaration = dMyyyy.format(ymd.parse(object.getCustomerUpdationDate().trim()));
                String placeOfDeclaration = ckycrCommonMgmtRemote.getBranchCity(Integer.parseInt(object.getPrimaryBrCode().trim()));
                String kycVerficationDate = dMyyyy.format(ymd.parse(object.getKycVerifiedDate().trim()));
                String kycVerficationName = object.getKycVerifiedUserName().trim().length() > 150 ? object.getKycVerifiedUserName().trim().substring(0, 150) : object.getKycVerifiedUserName().trim();
                String kycVerificationEmpCode = object.getKycVerifiedBy().trim().length() > 50 ? object.getKycVerifiedBy().trim().substring(0, 50) : object.getKycVerifiedBy().trim();

                //Total No Of Images
                Map<String, String> imageMap = extractCustImage(object.getCustImage());
                String imageFolderName = commonImgFolderName + "_" + (i + 1);
                File imageFolderDir = new File(uploadFolder + "/" + imageFolderName + "/");
                if (!imageFolderDir.exists()) {
                    imageFolderDir.mkdirs();
                }

                //Total No of other POIs
                List<CKYCRDownloadDetail30> otherIdList = fetch30Records(object.getCustomerId().trim());
                int totalIds = otherIdList.size() + 1;

                String twentyRecords = "20|" + (i + 1) + "|" + "01" + "|" + ckycrBrCode + "|"
                        + object.getApplicantNameUpdateFlag() + "|" + object.getPersonalEntityUpdateFlag() + "|"
                        + object.getAddressUpdateFlag() + "|" + object.getContactUpdateFlag() + "|"
                        + object.getRemarksUpdateFlag() + "|" + object.getKycVerificationUpdateFlag() + "|"
                        + object.getIdentityUpdateFlag() + "|" + object.getRelatedPersonUpdateFlag() + "|"
                        + object.getControllingPersonUpdateFlag() + "|" + object.getImageUpdateFlag() + "|"
                        + object.getConstitutionCode() + "|" + object.getAcHolderTypeFlag() + "|"
                        + object.getAcHolderType() + "|" + object.getAcType() + "|" + imageFolderName + "|"
                        + title + "|" + applicantFirstName + "|" + applicantMiddleName + "|" + applicantLastName
                        + "|" + applicantName + "||||||" + fatherSpouseFlag + "|" + fatherSpouseTitle + "|" + fatherSpouseFirstName + "|"
                        + fatherSpouseMiddleName + "|" + fatherSpouseLastName + "||" + motherTitle + "|"
                        + motherFirstName + "|" + motherMiddleName + "|" + motherLastName + "||" + gender + "|"
                        + maritalStatus + "|" + nationality + "|" + occupationType + "|" + dob + "|" + placeOfIncorp + "|"
                        + dateOfCommencement + "|" + incorpCountry + "|" + countryOfResidenceTaxLaws + "|"
                        + identificationType + "|" + legalTin + "|" + tinIssuingCountry + "||" + residentialStatus + "|"
                        + flagJuriOutsideIndia + "|" + juridictionOfResidence + "|" + misTin + "|" + misCountry + "|"
                        + misCity + "|" + object.getPerAddType().trim() + "|" + perAddLineOne + "|||" + perVillage + "|"
                        + perDistrict + "|" + object.getPerStateCode().trim() + "|" + perCountry + "|" + perPostalCode + "|"
                        + perPoa + "|" + perOtherPoa + "|" + perMailAddSameFlag + "|" + object.getMailAddType().trim() + "|"
                        + mailAddLineOne + "|||" + mailVillage + "|" + mailDistrict + "|" + mailStateCode + "|" + mailCountry + "|"
                        + mailPostalCode + "|" + mailPoa + "|" + juriAddSameFlag + "|" + object.getJuriAddType().trim()
                        + "|" + juriAddLineOne + "|||" + juriCity + "|" + juriState + "|" + juriCountry + "|" + juriPostalCode
                        + "||||||" + object.getIsdCode().trim() + "|" + object.getMobileNo().trim() + "|||||" + dateOfDeclaration
                        + "|" + placeOfDeclaration + "|" + kycVerficationDate + "|" + object.getTypeOfDocSubmitted().trim() + "|"
                        + kycVerficationName + "|" + "Manager" + "|" + object.getBrnCode().trim() + "|"
                        + kycVerificationEmpCode + "|" + params[4] + "|" + params[1] + "|" + String.valueOf(totalIds) + "|0|0|0|" + imageMap.size() + "||||||" + "\n";
                fw.write(twentyRecords);
                //Thirty Record,POI
                String identityNo = "", expiryDate = "";
                String identityType = object.getLegalDocument().trim();
                if (object.getCustEntityType().equalsIgnoreCase("01")) {
                    if (!identityType.equalsIgnoreCase("Z")) {
                        identityNo = object.getIdentityNo();
                    }
                    if (identityType.equalsIgnoreCase("A") || identityType.equalsIgnoreCase("D")) {
                        expiryDate = dMyyyy.format(ymd.parse(object.getIdExpiryDate().trim()));
                    }
                }
                String thirtyRecord = "30|" + (i + 1) + "|" + object.getLegalDocument().trim() + "|" + identityNo + "|"
                        + expiryDate + "|01|02|||||" + "\n";
                fw.write(thirtyRecord);
                //For Other POI If Available
//                List<CKYCRDownloadDetail30> otherIdList = fetch30Records(object.getCustomerId().trim());
                for (CKYCRDownloadDetail30 idObj : otherIdList) {
                    identityNo = "";
                    expiryDate = "";
                    identityType = idObj.getIdentificationtype();
                    if (object.getCustEntityType().equalsIgnoreCase("01")) {
                        if (!identityType.equalsIgnoreCase("Z")) {
                            identityNo = idObj.getIdentityNumber();
                        }
                        if (identityType.equalsIgnoreCase("A") || identityType.equalsIgnoreCase("D")) {
                            expiryDate = dMyyyy.format(ymd.parse(idObj.getExpiryDate()));
                        }
                    }
                    thirtyRecord = "30|" + (i + 1) + "|" + identityType + "|" + identityNo + "|"
                            + expiryDate + "|01|02|||||" + "\n";
                    fw.write(thirtyRecord);
                }
                //Forty Record - Related Info (At present it should go only in the case of Individual).No need to validate related name fields.
                if (object.getMinorFlag().equalsIgnoreCase("MINOR")) {
                    String deletionFlag = object.getRelatedAdditionFlag().equalsIgnoreCase("F") ? "01" : "02";
                    String fortyRecord = "40|" + (i + 1) + "|" + object.getRelationType() + "|" + deletionFlag + "|"
                            + object.getRelatedCkycNo().trim() + "|" + object.getRelatedNamePrefix() + "|" + object.getRelatedFirstName()
                            + "|" + object.getRelatedMiddleName() + "|" + object.getRelatedLastName() + "|||||||||||||||||"
                            + "||||||||||||||||||||||||||||||||||||||||||||" + "\n";
                    fw.write(fortyRecord);
                }
                //Seventy Record,Images
                Set<Entry<String, String>> set = imageMap.entrySet();
                Iterator<Entry<String, String>> it = set.iterator();
                while (it.hasNext()) {
                    Entry<String, String> entry = it.next();
                    String key = entry.getKey(); //key and value is same in both case
                    System.out.println("Key Is-->" + key);
                    //String imageName = params[1] + "_" + key + object.getCustomerId().trim() + ".jpg";
                    String imageName = imageFolderName + "_" + key + object.getCustomerId().trim() + ".jpg";
                    //Writing the image file
                    String imageData = CbsUtil.readImageFromXMLFile(new File("/" + bankCode + "/CI/" + object.getCustomerId().trim() + "/" + key + ".xml"));
                    byte[] imageInByte = Base64.decode(imageData);
                    //convert byte array back to BufferedImage
                    InputStream in = new ByteArrayInputStream(imageInByte);
                    BufferedImage bImageFromConvert = ImageIO.read(in);
                    ImageIO.write(bImageFromConvert, "jpg", new File(imageFolderDir + "/" + imageName));
                    //Writing the image data
                    String seventyRecord = "70|" + (i + 1) + "|" + imageName + "|" + key + "|01|"
                            + ckycrBrCode + "||||" + "\n"; //Check for pipe separator count for data.
                    fw.write(seventyRecord);
                }
                //Status updation or log maintainance in ckycr_request_detail table.
                System.out.println("Before scheduledRequestUpdation");
                scheduledRequestUpdation(object, nextBatchNo, (i + 1));
                System.out.println("After scheduledRequestUpdation");

                //Now zipping the image folder
                ckycrCommonMgmtRemote.zipFolder(imageFolderDir + "/", uploadFolder + "/" + imageFolderName + ".zip");
                //Deleting The Image Folder
                File imageFolderToDelete = new File(uploadFolder + "/" + imageFolderName + "/");
                if (imageFolderToDelete.exists()) {
                    ckycrCommonMgmtRemote.delete(imageFolderToDelete);
                }
            }
            if (updateRequestList.isEmpty()) {
                fw.close();
            }
            System.out.println("scheduledRequestUpdation end");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public static Map<String, String> extractCustImage(String custImages) throws Exception {
        Map<String, String> custImageMap = new HashMap<String, String>();
        try {
            String[] arr = custImages.trim().split("\\|");
            if (arr.length == 0) {
                custImageMap.put(custImages, custImages);
            } else {
                for (int i = 0; i < arr.length; i++) {
                    custImageMap.put(arr[i], arr[i]);
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return custImageMap;
    }

    public String getFatherSpouseTitle(String fatherSpouseFlag, String gender) throws Exception {
        String fatherSpouseTitle = "";
        try {
            if (fatherSpouseFlag.equalsIgnoreCase("01")) {
                fatherSpouseTitle = "MR";
            } else if (fatherSpouseFlag.equalsIgnoreCase("02")) {
                if (gender.equalsIgnoreCase("M")) {
                    fatherSpouseTitle = "MRS";
                } else if (gender.equalsIgnoreCase("F")) {
                    fatherSpouseTitle = "MR";
                } else if (gender.equalsIgnoreCase("T")) {
                    fatherSpouseTitle = "MX";
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return fatherSpouseTitle;
    }

    public List<List<CKYCRRequestPojo>> validateCustomersToUpload(List<CKYCRRequestPojo> requestList, String bankCode) throws Exception {
        List<List<CKYCRRequestPojo>> validCustomers = new ArrayList<List<CKYCRRequestPojo>>();
        List<CKYCRRequestPojo> uploadRequestList = new ArrayList<CKYCRRequestPojo>();
        List<CKYCRRequestPojo> updateRequestList = new ArrayList<CKYCRRequestPojo>();
        try {
            String[] parameterInfoReportCodes = getParameterInfoReportCodes();
            for (int i = 0; i < requestList.size(); i++) {
                String errorCode = "";
                CKYCRRequestPojo object = requestList.get(i);
                //Setting of update flag
                String applicantNameUpdateFlag = "", personalEntityUpdateFlag = "", addressUpdateFlag = "",
                        contactUpdateFlag = "", remarksUpdateFlag = "", kycVerificationUpdateFlag = "",
                        identityUpdateFlag = "", relatedPersonUpdateFlag = "", controllingPersonUpdateFlag = "",
                        imageUpdateFlag = "";

                object.setApplicantNameUpdateFlag(applicantNameUpdateFlag);
                object.setPersonalEntityUpdateFlag(personalEntityUpdateFlag);
                object.setAddressUpdateFlag(addressUpdateFlag);
                object.setContactUpdateFlag(contactUpdateFlag);
                object.setRemarksUpdateFlag(remarksUpdateFlag);
                object.setKycVerificationUpdateFlag(kycVerificationUpdateFlag);
                object.setIdentityUpdateFlag(identityUpdateFlag);
                object.setRelatedPersonUpdateFlag(relatedPersonUpdateFlag);
                object.setControllingPersonUpdateFlag(controllingPersonUpdateFlag); //This is only for Legal Entity.
                object.setImageUpdateFlag(imageUpdateFlag);

                //Setting minor data - 24/01/2018
                object.setMinorFlag(""); //Either it will be blank or MINOR
                object.setRelatedCkycNo("");
                object.setRelationType("");
                object.setRelatedAdditionFlag("");
                object.setRelatedNamePrefix("");
                object.setRelatedFirstName("");
                object.setRelatedMiddleName("");
                object.setRelatedLastName("");
                //End Here
                if (object.getMode().equalsIgnoreCase("upload")) {
                    if (object.getCustEntityType().equals("") || object.getCustEntityType().equals("03")) {
                        errorCode = returnErrorCode(errorCode, CkycrEnum.ERR_064.getCode());
                        errorUpdationInValidation(object, errorCode, CkycrEnum.ERR_064.getValue());
                        continue;
                    }
                    //Common Validation
                    errorCode = object.getConstitutionCode().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_002.getCode()) : errorCode;
                    errorCode = object.getPoa().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_083.getCode()) : errorCode;
                    //Separate Validation
                    if (object.getCustEntityType().equalsIgnoreCase("01")) { //Individual
                        errorCode = object.getAcType().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_005.getCode()) : errorCode;
                        errorCode = object.getTitle().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_008.getCode()) : errorCode;
                        //Customer Name
                        errorCode = object.getCustName().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_009.getCode()) : errorCode;
                        boolean flag;
                        if (!object.getCustName().equals("")) {
                            flag = ckycrCommonMgmtRemote.isValidIndFirstAndLastName(object.getCustName());
                            errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_065.getCode()) : errorCode;
                        }

                        if (!object.getMiddleName().equals("")) {
                            flag = ckycrCommonMgmtRemote.isValidIndMiddleName(object.getMiddleName());
                            errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_066.getCode()) : errorCode;
                        }

//                        errorCode = object.getLastName().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_010.getCode()) : errorCode;
                        if (!object.getLastName().equals("")) {
                            flag = ckycrCommonMgmtRemote.isValidIndFirstAndLastName(object.getLastName());
                            errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_067.getCode()) : errorCode;
                        }

                        //Checking of minor if this customer is -- 24/01/2018 (We assume that only one entry will be in related info)
                        String minor = isMinor(object.getDateOfBirth(), Integer.parseInt(parameterInfoReportCodes[5]));
                        if (minor.equalsIgnoreCase("MINOR")) {
                            List<String> relatedDetail = relatedDetail(object.getCustomerId().trim());

                            errorCode = relatedDetail.get(2).equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_104.getCode()) : errorCode; //CkycNo
                            errorCode = relatedDetail.get(0).equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_105.getCode()) : errorCode; //Relation Type
                            errorCode = relatedDetail.get(1).equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_106.getCode()) : errorCode; //Addition Flag
                            errorCode = relatedDetail.get(3).equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_107.getCode()) : errorCode; //Related Name Prefix
                            //Related Name
                            errorCode = relatedDetail.get(4).equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_108.getCode()) : errorCode;
                            if (!relatedDetail.get(4).equals("")) {
                                flag = ckycrCommonMgmtRemote.isValidIndFirstAndLastName(relatedDetail.get(4));
                                errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_111.getCode()) : errorCode;
                            }
                            if (!relatedDetail.get(5).equals("")) {
                                flag = ckycrCommonMgmtRemote.isValidIndMiddleName(relatedDetail.get(5));
                                errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_109.getCode()) : errorCode;
                            }
                            if (!relatedDetail.get(6).equals("")) {
                                flag = ckycrCommonMgmtRemote.isValidIndFirstAndLastName(relatedDetail.get(6));
                                errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_110.getCode()) : errorCode;
                            }
                            //Setting values of related person if customer is minor
                            object.setMinorFlag(minor); //Either it will be blank or MINOR
                            object.setRelatedCkycNo(relatedDetail.get(2).trim());
                            object.setRelationType(relatedDetail.get(0).trim());
                            object.setRelatedAdditionFlag(relatedDetail.get(1).trim());
                            object.setRelatedNamePrefix(relatedDetail.get(3).trim());
                            object.setRelatedFirstName(relatedDetail.get(4).trim());
                            object.setRelatedMiddleName(relatedDetail.get(5).trim());
                            object.setRelatedLastName(relatedDetail.get(6).trim());
                        }
                        //End Here
                        //Spouse And Father
                        errorCode = object.getFatherSpouseFlag().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_068.getCode()) : errorCode;
                        if (object.getPanGirNumber().trim().length() == 10
                                && ParseFileUtil.isValidPAN(object.getPanGirNumber().trim())
                                && object.getFatherSpouseFlag().equalsIgnoreCase("Y")) {
                            errorCode = object.getSpouseName().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_072.getCode()) : errorCode;
                            if (!object.getSpouseName().equals("")) {
                                flag = ckycrCommonMgmtRemote.isValidIndFirstAndLastName(object.getSpouseName());
                                errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_075.getCode()) : errorCode;
                            }

                            if (!object.getSpouseMiddleName().equals("")) {
                                flag = ckycrCommonMgmtRemote.isValidIndMiddleName(object.getSpouseMiddleName());
                                errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_073.getCode()) : errorCode;
                            }

//                            errorCode = object.getSpouseLastName().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_074.getCode()) : errorCode;
                            if (!object.getSpouseLastName().equals("")) {
                                flag = ckycrCommonMgmtRemote.isValidIndFirstAndLastName(object.getSpouseLastName());
                                errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_076.getCode()) : errorCode;
                            }
                        } else {
                            errorCode = object.getFatherName().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_012.getCode()) : errorCode;
                            if (!object.getFatherName().equals("")) {
                                flag = ckycrCommonMgmtRemote.isValidIndFirstAndLastName(object.getFatherName());
                                errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_069.getCode()) : errorCode;
                            }

                            if (!object.getFatherMiddleName().equals("")) {
                                flag = ckycrCommonMgmtRemote.isValidIndMiddleName(object.getFatherMiddleName());
                                errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_070.getCode()) : errorCode;
                            }

//                            errorCode = object.getFatherLastName().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_013.getCode()) : errorCode;
                            if (!object.getFatherLastName().equals("")) {
                                flag = ckycrCommonMgmtRemote.isValidIndFirstAndLastName(object.getFatherLastName());
                                errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_071.getCode()) : errorCode;
                            }
                        }

                        //Mother
                        errorCode = object.getMotherName().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_014.getCode()) : errorCode;
                        if (!object.getMotherName().equals("")) {
                            flag = ckycrCommonMgmtRemote.isValidIndFirstAndLastName(object.getMotherName());
                            errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_077.getCode()) : errorCode;
                        }

                        if (!object.getMotherMiddleName().equals("")) {
                            flag = ckycrCommonMgmtRemote.isValidIndMiddleName(object.getMotherMiddleName());
                            errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_078.getCode()) : errorCode;
                        }

//                        errorCode = object.getMotherLastName().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_015.getCode()) : errorCode;
                        if (!object.getMotherLastName().equals("")) {
                            flag = ckycrCommonMgmtRemote.isValidIndFirstAndLastName(object.getMotherLastName());
                            errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_079.getCode()) : errorCode;
                        }
                        //Gender
                        errorCode = object.getGender().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_016.getCode()) : errorCode;
                        if (!object.getGender().equals("") && object.getGender().equalsIgnoreCase("o")) {
                            errorCode = returnErrorCode(errorCode, CkycrEnum.ERR_080.getCode());
                        }
                        //Marital Status
                        errorCode = object.getMaritalStatus().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_017.getCode()) : errorCode;
                        errorCode = object.getNationality().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_018.getCode()) : errorCode;
                        errorCode = object.getOccupationCode().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_019.getCode()) : errorCode;
                        errorCode = object.getDateOfBirth().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_020.getCode()) : errorCode;
                        errorCode = object.getNriFlag().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_027.getCode()) : errorCode;
                        errorCode = object.getMisJuriResidence().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_082.getCode()) : errorCode;
                        if (!object.getMisJuriResidence().equals("IN")) {
                            errorCode = object.getMisTin().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_028.getCode()) : errorCode;
                            errorCode = object.getMisBirthCountry().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_029.getCode()) : errorCode;
                            errorCode = object.getMisCity().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_030.getCode()) : errorCode;
                        }
                        if (object.getPoa().equalsIgnoreCase("99")) {
                            errorCode = object.getPerOtherPoa().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_084.getCode()) : errorCode;
                        }
                        errorCode = object.getLegalDocument().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_025.getCode()) : errorCode;
                        if (!object.getLegalDocument().equalsIgnoreCase("Z")) {
                            errorCode = object.getIdentityNo().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_081.getCode()) : errorCode;
                        }
                        if (object.getLegalDocument().equalsIgnoreCase("A") || object.getLegalDocument().equalsIgnoreCase("D")) {  //Passport, DL
                            errorCode = object.getIdExpiryDate().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_091.getCode()) : errorCode;
                        }
                        System.out.println("DPS-Test");
                    } else if (object.getCustEntityType().equalsIgnoreCase("02")) { //Legal Entity
                        errorCode = object.getAcHolderTypeFlag().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_003.getCode()) : errorCode;
                        errorCode = object.getAcHolderType().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_004.getCode()) : errorCode;

                        //For Name of the applicant/entity
                        errorCode = object.getCustName().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_009.getCode()) : errorCode;
                        boolean flag;
                        if (!object.getCustName().equals("")) {
                            flag = ckycrCommonMgmtRemote.isValidApplicantNameEntity(object.getCustName());
                            errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_065.getCode()) : errorCode;
                        }
                        errorCode = object.getDateOfBirth().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_020.getCode()) : errorCode;
                        errorCode = object.getIncorporationPlace().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_021.getCode()) : errorCode;
                        errorCode = object.getCommencementDate().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_022.getCode()) : errorCode;
                        errorCode = object.getCountryIncorporation().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_023.getCode()) : errorCode;
                        errorCode = object.getCountryResidenceTaxLaws().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_024.getCode()) : errorCode;
                        //Identification Type
                        errorCode = object.getLegalDocument().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_025.getCode()) : errorCode;
                        errorCode = object.getIdentityNo().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_081.getCode()) : errorCode;
                        if (object.getLegalDocument().equals("T")) {   //For TIN
                            errorCode = object.getTinIssuingCountry().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_026.getCode()) : errorCode;
                        }

                    }
                    //Common Validations
                    errorCode = object.getPerAddType().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_031.getCode()) : errorCode;
                    errorCode = object.getPerAddressLineOne().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_032.getCode()) : errorCode;
                    errorCode = object.getPerVillage().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_033.getCode()) : errorCode;
                    errorCode = object.getPerDistrict().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_034.getCode()) : errorCode;
                    errorCode = object.getPerStateCode().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_035.getCode()) : errorCode;
                    errorCode = object.getPerCountryCode().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_036.getCode()) : errorCode;
                    if (object.getPerCountryCode().equalsIgnoreCase("IN")) {
                        errorCode = object.getPerPostalCode().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_037.getCode()) : errorCode;
                        if (!object.getPerPostalCode().equals("")) {
                            errorCode = (object.getPerPostalCode().trim().length() != 6 || !ParseFileUtil.isNumeric(object.getPerPostalCode().trim())) ? returnErrorCode(errorCode, CkycrEnum.ERR_090.getCode()) : errorCode;
                        }
                    }
                    errorCode = object.getPerMailAddSameFlagIndicate().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_038.getCode()) : errorCode;
                    errorCode = object.getMailAddType().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_039.getCode()) : errorCode;
                    errorCode = object.getMailAddressLineOne().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_040.getCode()) : errorCode;
                    errorCode = object.getMailVillage().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_041.getCode()) : errorCode;
                    errorCode = object.getMailDistrict().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_042.getCode()) : errorCode;
                    errorCode = object.getMailStateCode().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_043.getCode()) : errorCode;
                    errorCode = object.getMailCountryCode().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_044.getCode()) : errorCode;
                    if (object.getMailCountryCode().equalsIgnoreCase("IN")) {
                        errorCode = object.getMailPostalCode().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_045.getCode()) : errorCode;
                        if (!object.getMailPostalCode().equals("")) {
                            errorCode = (object.getMailPostalCode().trim().length() != 6 || !ParseFileUtil.isNumeric(object.getMailPostalCode().trim())) ? returnErrorCode(errorCode, CkycrEnum.ERR_089.getCode()) : errorCode;
                        }
                    }
                    errorCode = object.getMailPoa().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_046.getCode()) : errorCode;
                    errorCode = object.getJuriAddBasedOnFlag().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_085.getCode()) : errorCode;
                    errorCode = object.getJuriAddType().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_047.getCode()) : errorCode;

                    errorCode = object.getJuriAddOne().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_048.getCode()) : errorCode;
                    errorCode = object.getJuriCity().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_049.getCode()) : errorCode;
                    errorCode = object.getJuriState().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_051.getCode()) : errorCode;
                    errorCode = object.getJuriCountry().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_052.getCode()) : errorCode;
                    if (object.getJuriCountry().equalsIgnoreCase("IN")) {
                        errorCode = object.getJuriPostal().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_053.getCode()) : errorCode;
                        if (!object.getJuriPostal().equals("")) {
                            errorCode = (object.getJuriPostal().trim().length() != 6 || !ParseFileUtil.isNumeric(object.getJuriPostal().trim())) ? returnErrorCode(errorCode, CkycrEnum.ERR_088.getCode()) : errorCode;
                        }
                    }
                    errorCode = object.getIsdCode().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_054.getCode()) : errorCode;
                    if (!object.getIsdCode().equals("")) {
                        errorCode = (object.getIsdCode().trim().length() > 3 || !ParseFileUtil.isNumeric(object.getIsdCode().trim())) ? returnErrorCode(errorCode, CkycrEnum.ERR_086.getCode()) : errorCode;
                    }
                    errorCode = object.getMobileNo().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_055.getCode()) : errorCode;
                    if (!object.getMobileNo().equals("") && (object.getMobileNo().trim().length() != 10) && !ParseFileUtil.isNumeric(object.getMobileNo().trim())) {
                        errorCode = (object.getMobileNo().trim().length() != 10 || !ParseFileUtil.isNumeric(object.getMobileNo().trim())) ? returnErrorCode(errorCode, CkycrEnum.ERR_087.getCode()) : errorCode;
                    }
                    errorCode = (object.getCustomerUpdationDate() == null || object.getCustomerUpdationDate().equals("") || object.getCustomerUpdationDate().equals("00000000")) ? returnErrorCode(errorCode, CkycrEnum.ERR_056.getCode()) : errorCode;
                    errorCode = (object.getKycVerifiedDate() == null || object.getKycVerifiedDate().equals("") || object.getKycVerifiedDate().equals("00000000")) ? returnErrorCode(errorCode, CkycrEnum.ERR_058.getCode()) : errorCode;
                    errorCode = object.getTypeOfDocSubmitted().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_059.getCode()) : errorCode;
                    errorCode = object.getKycVerifiedUserName().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_060.getCode()) : errorCode;
                    errorCode = object.getBrnCode().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_061.getCode()) : errorCode;
                    errorCode = object.getKycVerifiedBy().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_062.getCode()) : errorCode;
                    errorCode = object.getCustImage().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_092.getCode()) : errorCode;
                    if (!object.getCustImage().equals("")) {
                        errorCode = isAllRequiredImagesScaned(bankCode, object.getCustomerId().trim(), object.getCustImage().trim()) == false ? returnErrorCode(errorCode, CkycrEnum.ERR_093.getCode()) : errorCode;
                    }
                    //Multiple 30 Type of Records
                    List<CKYCRDownloadDetail30> identityDetails = fetch30Records(object.getCustomerId().trim());
                    for (CKYCRDownloadDetail30 idObj : identityDetails) {
                        errorCode = idObj.getIdentificationtype().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_094.getCode()) : errorCode;
                        if (object.getCustEntityType().equalsIgnoreCase("01") && !idObj.getIdentificationtype().equalsIgnoreCase("Z")) {
                            errorCode = idObj.getIdentityNumber().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_095.getCode()) : errorCode;
                        }
                        if (object.getCustEntityType().equalsIgnoreCase("01") && (idObj.getIdentificationtype().equalsIgnoreCase("A")
                                || idObj.getIdentificationtype().equalsIgnoreCase("D"))) {
                            errorCode = idObj.getExpiryDate().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_096.getCode()) : errorCode;
                        }
                    }
                } else if (object.getMode().equalsIgnoreCase("update")) {
                    //Minor checking of customer
                    relatedPersonUpdateFlag = "02"; //It will create issue when minor become major
                    if (object.getCustEntityType().equalsIgnoreCase("01")) { //Only for individual
                        String minor = isMinor(object.getDateOfBirth(), Integer.parseInt(parameterInfoReportCodes[5]));
                        if (minor.equalsIgnoreCase("MINOR")) {
                            List<String> relatedDetail = relatedDetail(object.getCustomerId().trim());

                            errorCode = relatedDetail.get(2).equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_104.getCode()) : errorCode; //CkycNo
                            errorCode = relatedDetail.get(0).equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_105.getCode()) : errorCode; //Relation Type
                            errorCode = relatedDetail.get(1).equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_106.getCode()) : errorCode; //Addition Flag
                            errorCode = relatedDetail.get(3).equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_107.getCode()) : errorCode; //Related Name Prefix
                            //Related Name
                            boolean flag;
                            errorCode = relatedDetail.get(4).equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_108.getCode()) : errorCode;
                            if (!relatedDetail.get(4).equals("")) {
                                flag = ckycrCommonMgmtRemote.isValidIndFirstAndLastName(relatedDetail.get(4));
                                errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_111.getCode()) : errorCode;
                            }
                            if (!relatedDetail.get(5).equals("")) {
                                flag = ckycrCommonMgmtRemote.isValidIndMiddleName(relatedDetail.get(5));
                                errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_109.getCode()) : errorCode;
                            }
                            if (!relatedDetail.get(6).equals("")) {
                                flag = ckycrCommonMgmtRemote.isValidIndFirstAndLastName(relatedDetail.get(6));
                                errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_110.getCode()) : errorCode;
                            }
                            //Setting values of related person if customer is minor
                            object.setMinorFlag(minor); //Either it will be blank or MINOR
                            object.setRelatedCkycNo(relatedDetail.get(2).trim());
                            object.setRelationType(relatedDetail.get(0).trim());
                            object.setRelatedAdditionFlag(relatedDetail.get(1).trim());
                            object.setRelatedNamePrefix(relatedDetail.get(3).trim());
                            object.setRelatedFirstName(relatedDetail.get(4).trim());
                            object.setRelatedMiddleName(relatedDetail.get(5).trim());
                            object.setRelatedLastName(relatedDetail.get(6).trim());
                            //Update Flag Validation.
                            relatedPersonUpdateFlag = ckycrCommonMgmtRemote.isRelatedDetailUpdate(object) > 0 ? "01" : "02"; //We are sending the data in spite of the value of the flag.
                        }
                    }
                    //End Here

                    applicantNameUpdateFlag = ckycrCommonMgmtRemote.isApplicantNameUpdate(object) > 0 ? "01" : "02"; //01-Yes(Modified),02-No(Un-Modified)
                    personalEntityUpdateFlag = ckycrCommonMgmtRemote.isPersonalEntityDetailUpdate(object) > 0 ? "01" : "02";
                    addressUpdateFlag = ckycrCommonMgmtRemote.isAddressDetailUpdate(object) > 0 ? "01" : "02";
                    contactUpdateFlag = ckycrCommonMgmtRemote.isContactDetailUpdate(object) > 0 ? "01" : "02"; //We are sending the data in spite of the value of the flag.
                    kycVerificationUpdateFlag = ckycrCommonMgmtRemote.isKycDetailUpdate(object) > 0 ? "01" : "02";
                    identityUpdateFlag = ckycrCommonMgmtRemote.isIdentityDetailUpdate(object) > 0 ? "01" : "02"; //We are sending the data in spite of the value of the flag.
                    imageUpdateFlag = ckycrCommonMgmtRemote.isImageDetailUpdate(object) > 0 ? "01" : "02";

                    object.setApplicantNameUpdateFlag(applicantNameUpdateFlag);
                    object.setPersonalEntityUpdateFlag(personalEntityUpdateFlag);
                    object.setAddressUpdateFlag(addressUpdateFlag);
                    object.setContactUpdateFlag(contactUpdateFlag);
                    object.setRemarksUpdateFlag("02");
                    object.setKycVerificationUpdateFlag(kycVerificationUpdateFlag);
                    object.setIdentityUpdateFlag(identityUpdateFlag);
                    object.setRelatedPersonUpdateFlag(relatedPersonUpdateFlag);
                    object.setControllingPersonUpdateFlag("02"); //This is only for Legal Entity.
                    object.setImageUpdateFlag(imageUpdateFlag);

                    String constitutionCode = "", dob = "";
                    if (personalEntityUpdateFlag.equals("01")) {
                        errorCode = object.getConstitutionCode().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_002.getCode()) : errorCode;
                        constitutionCode = !object.getConstitutionCode().equals("") ? object.getConstitutionCode() : constitutionCode;

                        errorCode = object.getDateOfBirth().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_020.getCode()) : errorCode;
                        dob = !object.getDateOfBirth().equals("") ? object.getDateOfBirth() : dob;
                    }
                    object.setConstitutionCode(constitutionCode); //No need to size validation.
                    object.setDateOfBirth(dob);

                    String poa = "", perAddType = "", perAddOne = "", perVillage = "", perDistrict = "", perState = "",
                            perCountry = "", perPostal = "", perMailSameFlag = "", mailAddType = "", mailAddOne = "",
                            mailVillage = "", mailDistrict = "", mailState = "", mailCountry = "", mailPostal = "",
                            mailPoa = "", juriAddSameFlag = "", juriAddType = "", juriAddOne = "", juriCity = "",
                            juriState = "", juriCountry = "", juriPostal = "";
                    if (addressUpdateFlag.equals("01")) {
                        errorCode = object.getPoa().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_083.getCode()) : errorCode;
                        poa = !object.getPoa().equals("") ? object.getPoa() : poa;
                        errorCode = object.getPerAddType().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_031.getCode()) : errorCode;
                        perAddType = !object.getPerAddType().equals("") ? object.getPerAddType() : perAddType;
                        errorCode = object.getPerAddressLineOne().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_032.getCode()) : errorCode;
                        perAddOne = !object.getPerAddressLineOne().equals("") ? object.getPerAddressLineOne() : perAddOne;
                        errorCode = object.getPerVillage().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_033.getCode()) : errorCode;
                        perVillage = !object.getPerVillage().equals("") ? object.getPerVillage() : perVillage;
                        errorCode = object.getPerDistrict().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_034.getCode()) : errorCode;
                        perDistrict = !object.getPerDistrict().equals("") ? object.getPerDistrict() : perDistrict;
                        errorCode = object.getPerStateCode().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_035.getCode()) : errorCode;
                        perState = !object.getPerStateCode().equals("") ? object.getPerStateCode() : perState;
                        errorCode = object.getPerCountryCode().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_036.getCode()) : errorCode;
                        perCountry = !object.getPerCountryCode().equals("") ? object.getPerCountryCode() : perCountry;
                        if (object.getPerCountryCode().equalsIgnoreCase("IN")) {
                            errorCode = object.getPerPostalCode().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_037.getCode()) : errorCode;
                            if (!object.getPerPostalCode().equals("")) {
                                errorCode = (object.getPerPostalCode().trim().length() != 6 || !ParseFileUtil.isNumeric(object.getPerPostalCode().trim())) ? returnErrorCode(errorCode, CkycrEnum.ERR_090.getCode()) : errorCode;
                                perPostal = object.getPerPostalCode();
                            }
                        }
                        errorCode = object.getPerMailAddSameFlagIndicate().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_038.getCode()) : errorCode;
                        perMailSameFlag = !object.getPerMailAddSameFlagIndicate().equals("") ? object.getPerMailAddSameFlagIndicate() : perMailSameFlag;
                        errorCode = object.getMailAddType().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_039.getCode()) : errorCode;
                        mailAddType = !object.getMailAddType().equals("") ? object.getMailAddType() : mailAddType;

                        errorCode = object.getMailAddressLineOne().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_040.getCode()) : errorCode;
                        mailAddOne = !object.getMailAddressLineOne().equals("") ? object.getMailAddressLineOne() : mailAddOne;
                        errorCode = object.getMailVillage().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_041.getCode()) : errorCode;
                        mailVillage = !object.getMailVillage().equals("") ? object.getMailVillage() : mailVillage;
                        errorCode = object.getMailDistrict().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_042.getCode()) : errorCode;
                        mailDistrict = !object.getMailDistrict().equals("") ? object.getMailDistrict() : mailDistrict;
                        errorCode = object.getMailStateCode().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_043.getCode()) : errorCode;
                        mailState = !object.getMailStateCode().equals("") ? object.getMailStateCode() : mailState;
                        errorCode = object.getMailCountryCode().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_044.getCode()) : errorCode;
                        mailCountry = !object.getMailCountryCode().equals("") ? object.getMailCountryCode() : mailCountry;
                        if (object.getMailCountryCode().equalsIgnoreCase("IN")) {
                            errorCode = object.getMailPostalCode().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_045.getCode()) : errorCode;
                            if (!object.getMailPostalCode().equals("")) {
                                errorCode = (object.getMailPostalCode().trim().length() != 6 || !ParseFileUtil.isNumeric(object.getMailPostalCode().trim())) ? returnErrorCode(errorCode, CkycrEnum.ERR_089.getCode()) : errorCode;
                                mailPostal = object.getMailPostalCode();
                            }
                        }
                        errorCode = object.getMailPoa().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_046.getCode()) : errorCode;
                        mailPoa = !object.getMailPoa().equals("") ? object.getMailPoa() : mailPoa;
                        errorCode = object.getJuriAddBasedOnFlag().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_085.getCode()) : errorCode;
                        juriAddSameFlag = !object.getJuriAddBasedOnFlag().equals("") ? object.getJuriAddBasedOnFlag() : juriAddSameFlag;

                        errorCode = object.getJuriAddType().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_047.getCode()) : errorCode;
                        juriAddType = !object.getJuriAddType().equals("") ? object.getJuriAddType() : juriAddType;
                        errorCode = object.getJuriAddOne().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_048.getCode()) : errorCode;
                        juriAddOne = !object.getJuriAddOne().equals("") ? object.getJuriAddOne() : juriAddOne;
                        errorCode = object.getJuriCity().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_049.getCode()) : errorCode;
                        juriCity = !object.getJuriCity().equals("") ? object.getJuriCity() : juriCity;
                        errorCode = object.getJuriState().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_051.getCode()) : errorCode;
                        juriState = !object.getJuriState().equals("") ? object.getJuriState() : juriState;
                        errorCode = object.getJuriCountry().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_052.getCode()) : errorCode;
                        juriCountry = !object.getJuriCountry().equals("") ? object.getJuriCountry() : juriCountry;
                        if (object.getJuriCountry().equalsIgnoreCase("IN")) {
                            errorCode = object.getJuriPostal().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_053.getCode()) : errorCode;
                            if (!object.getJuriPostal().equals("")) {
                                errorCode = (object.getJuriPostal().trim().length() != 6 || !ParseFileUtil.isNumeric(object.getJuriPostal().trim())) ? returnErrorCode(errorCode, CkycrEnum.ERR_088.getCode()) : errorCode;
                                juriPostal = object.getJuriPostal();
                            }
                        }
                    }
                    object.setPoa(poa); //No need to size validation.
                    object.setPerAddType(perAddType);
                    object.setPerAddressLineOne(perAddOne);
                    object.setPerVillage(perVillage);
                    object.setPerDistrict(perDistrict);
                    object.setPerStateCode(perState);
                    object.setPerCountryCode(perCountry);
                    object.setPerPostalCode(perPostal);
                    object.setPerMailAddSameFlagIndicate(perMailSameFlag);
                    object.setMailAddType(mailAddType);
                    object.setMailAddressLineOne(mailAddOne);
                    object.setMailVillage(mailVillage);
                    object.setMailDistrict(mailDistrict);
                    object.setMailStateCode(mailState);
                    object.setMailCountryCode(mailCountry);
                    object.setMailPostalCode(mailPostal);
                    object.setMailPoa(mailPoa);
                    object.setJuriAddBasedOnFlag(juriAddSameFlag);
                    object.setJuriAddType(juriAddType);
                    object.setJuriAddOne(juriAddOne);
                    object.setJuriCity(juriCity);
                    object.setJuriState(juriState);
                    object.setJuriCountry(juriCountry);
                    object.setJuriPostal(juriPostal);

                    String title = "", custName = "", custMiddleName = "", custLastName = "";
                    if (applicantNameUpdateFlag.equals("01") && object.getCustEntityType().equalsIgnoreCase("01")) {
                        errorCode = object.getTitle().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_008.getCode()) : errorCode;
                        title = object.getTitle();

                        errorCode = object.getCustName().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_009.getCode()) : errorCode;
                        boolean flag;
                        if (!object.getCustName().equals("")) {
                            flag = ckycrCommonMgmtRemote.isValidIndFirstAndLastName(object.getCustName());
                            errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_065.getCode()) : errorCode;
                            custName = object.getCustName();
                        }

                        if (!object.getMiddleName().equals("")) {
                            flag = ckycrCommonMgmtRemote.isValidIndMiddleName(object.getMiddleName());
                            errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_066.getCode()) : errorCode;
                            custMiddleName = object.getMiddleName();
                        }

//                        errorCode = object.getLastName().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_010.getCode()) : errorCode;
                        if (!object.getLastName().equals("")) {
                            flag = ckycrCommonMgmtRemote.isValidIndFirstAndLastName(object.getLastName());
                            errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_067.getCode()) : errorCode;
                            custLastName = object.getLastName();
                        }
                    }
                    object.setTitle(title);
                    object.setCustName(custName);
                    object.setMiddleName(custMiddleName);
                    object.setLastName(custLastName);

                    String fatherFirstName = "", fatherMiddleName = "", fatherLastName = "", spouseFirstName = "",
                            spouseMiddleName = "", spouseLastName = "", motherFirstName = "", motherMiddleName = "",
                            motherLastName = "", gender = "", maritalStatus = "", nationality = "", occupationType = "",
                            residentialStatus = "", juridictionOfResidence = "", misTin = "", misBirthCountry = "", misCity = "";
                    if (personalEntityUpdateFlag.equals("01") && object.getCustEntityType().equalsIgnoreCase("01")) {
                        boolean flag;
                        if (object.getPanGirNumber().trim().length() == 10
                                && ParseFileUtil.isValidPAN(object.getPanGirNumber().trim())
                                && object.getFatherSpouseFlag().equalsIgnoreCase("Y")) {
                            errorCode = object.getSpouseName().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_072.getCode()) : errorCode;
                            if (!object.getSpouseName().equals("")) {
                                flag = ckycrCommonMgmtRemote.isValidIndFirstAndLastName(object.getSpouseName());
                                errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_075.getCode()) : errorCode;
                                spouseFirstName = object.getSpouseName();
                            }

                            if (!object.getSpouseMiddleName().equals("")) {
                                flag = ckycrCommonMgmtRemote.isValidIndMiddleName(object.getSpouseMiddleName());
                                errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_073.getCode()) : errorCode;
                                spouseMiddleName = object.getSpouseMiddleName();
                            }

//                            errorCode = object.getSpouseLastName().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_074.getCode()) : errorCode;
                            if (!object.getSpouseLastName().equals("")) {
                                flag = ckycrCommonMgmtRemote.isValidIndFirstAndLastName(object.getSpouseLastName());
                                errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_076.getCode()) : errorCode;
                                spouseLastName = object.getSpouseLastName();
                            }
                        } else {
                            errorCode = object.getFatherName().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_012.getCode()) : errorCode;
                            if (!object.getFatherName().equals("")) {
                                flag = ckycrCommonMgmtRemote.isValidIndFirstAndLastName(object.getFatherName());
                                errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_069.getCode()) : errorCode;
                                fatherFirstName = object.getFatherName();
                            }

                            if (!object.getFatherMiddleName().equals("")) {
                                flag = ckycrCommonMgmtRemote.isValidIndMiddleName(object.getFatherMiddleName());
                                errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_070.getCode()) : errorCode;
                                fatherMiddleName = object.getFatherMiddleName();
                            }

//                            errorCode = object.getFatherLastName().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_013.getCode()) : errorCode;
                            if (!object.getFatherLastName().equals("")) {
                                flag = ckycrCommonMgmtRemote.isValidIndFirstAndLastName(object.getFatherLastName());
                                errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_071.getCode()) : errorCode;
                                fatherLastName = object.getFatherLastName();
                            }
                        }
                        //Mother
                        errorCode = object.getMotherName().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_014.getCode()) : errorCode;
                        if (!object.getMotherName().equals("")) {
                            flag = ckycrCommonMgmtRemote.isValidIndFirstAndLastName(object.getMotherName());
                            errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_077.getCode()) : errorCode;
                            motherFirstName = object.getMotherName();
                        }

                        if (!object.getMotherMiddleName().equals("")) {
                            flag = ckycrCommonMgmtRemote.isValidIndMiddleName(object.getMotherMiddleName());
                            errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_078.getCode()) : errorCode;
                            motherMiddleName = object.getMotherMiddleName();
                        }

//                        errorCode = object.getMotherLastName().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_015.getCode()) : errorCode;
                        if (!object.getMotherLastName().equals("")) {
                            flag = ckycrCommonMgmtRemote.isValidIndFirstAndLastName(object.getMotherLastName());
                            errorCode = (flag == false) ? returnErrorCode(errorCode, CkycrEnum.ERR_079.getCode()) : errorCode;
                            motherLastName = object.getMotherLastName();
                        }

                        errorCode = object.getGender().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_016.getCode()) : errorCode;
                        if (!object.getGender().equals("")) {
                            errorCode = (object.getGender().equalsIgnoreCase("o")) ? returnErrorCode(errorCode, CkycrEnum.ERR_080.getCode()) : errorCode;
                            gender = object.getGender();
                        }
                        errorCode = object.getMaritalStatus().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_017.getCode()) : errorCode;
                        maritalStatus = !object.getMaritalStatus().equals("") ? object.getMaritalStatus() : maritalStatus;
                        errorCode = object.getNationality().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_018.getCode()) : errorCode;
                        nationality = !object.getNationality().equals("") ? object.getNationality() : nationality;
                        errorCode = object.getOccupationCode().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_019.getCode()) : errorCode;
                        occupationType = !object.getOccupationCode().equals("") ? object.getOccupationCode() : occupationType;
                        errorCode = object.getNriFlag().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_027.getCode()) : errorCode;
                        residentialStatus = !object.getNriFlag().equals("") ? object.getNriFlag() : residentialStatus;

                        errorCode = object.getMisJuriResidence().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_082.getCode()) : errorCode;
                        juridictionOfResidence = !object.getMisJuriResidence().equals("") ? object.getMisJuriResidence() : juridictionOfResidence;
                        if (!object.getMisJuriResidence().equals("IN")) {
                            errorCode = object.getMisTin().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_028.getCode()) : errorCode;
                            misTin = !object.getMisTin().equals("") ? object.getMisTin() : misTin;
                            errorCode = object.getMisBirthCountry().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_029.getCode()) : errorCode;
                            misBirthCountry = !object.getMisBirthCountry().equals("") ? object.getMisBirthCountry() : misBirthCountry;
                            errorCode = object.getMisCity().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_030.getCode()) : errorCode;
                            misCity = !object.getMisCity().equals("") ? object.getMisCity() : misCity;
                        }

                    }
                    object.setFatherName(fatherFirstName);
                    object.setFatherMiddleName(fatherMiddleName);
                    object.setFatherLastName(fatherLastName);
                    object.setSpouseName(spouseFirstName);
                    object.setSpouseMiddleName(spouseMiddleName);
                    object.setSpouseLastName(spouseLastName);
                    object.setMotherName(motherFirstName);
                    object.setMotherMiddleName(motherMiddleName);
                    object.setMotherLastName(motherLastName);
                    object.setGender(gender);
                    object.setMaritalStatus(maritalStatus);
                    object.setNationality(nationality);
                    object.setOccupationCode(occupationType);
                    object.setNriFlag(residentialStatus);
                    object.setMisJuriResidence(juridictionOfResidence);
                    object.setMisTin(misTin);
                    object.setMisBirthCountry(misBirthCountry);
                    object.setMisCity(misCity);


                    String placeOfIncorp = "", dateOfCommBusiness = "", countryOfIncorp = "", countryOfResiAsTaxLaws = "",
                            legalDoc = "", identityNo = "", tinIssuingCountry = "";
                    if (personalEntityUpdateFlag.equals("01") && object.getCustEntityType().equalsIgnoreCase("02")) {
                        errorCode = object.getIncorporationPlace().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_021.getCode()) : errorCode;
                        placeOfIncorp = !object.getIncorporationPlace().equals("") ? object.getIncorporationPlace() : placeOfIncorp;
                        errorCode = object.getCommencementDate().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_022.getCode()) : errorCode;
                        dateOfCommBusiness = !object.getCommencementDate().equals("") ? object.getCommencementDate() : dateOfCommBusiness;
                        errorCode = object.getCountryIncorporation().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_023.getCode()) : errorCode;
                        countryOfIncorp = !object.getCountryIncorporation().equals("") ? object.getCountryIncorporation() : countryOfIncorp;
                        errorCode = object.getCountryResidenceTaxLaws().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_024.getCode()) : errorCode;
                        countryOfResiAsTaxLaws = !object.getCountryResidenceTaxLaws().equals("") ? object.getCountryResidenceTaxLaws() : countryOfResiAsTaxLaws;

                        errorCode = object.getLegalDocument().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_025.getCode()) : errorCode;
                        legalDoc = !object.getLegalDocument().equals("") ? object.getLegalDocument() : legalDoc;
                        errorCode = object.getIdentityNo().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_081.getCode()) : errorCode;
                        identityNo = !object.getIdentityNo().equals("") ? object.getIdentityNo() : identityNo;
                        if (object.getLegalDocument().equals("T")) {   //For TIN
                            errorCode = object.getTinIssuingCountry().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_026.getCode()) : errorCode;
                            tinIssuingCountry = !object.getTinIssuingCountry().equals("") ? object.getTinIssuingCountry() : tinIssuingCountry;
                        }
                    }
                    object.setIncorporationPlace(placeOfIncorp);
                    object.setCommencementDate(dateOfCommBusiness);
                    object.setCountryIncorporation(countryOfIncorp);
                    object.setCountryResidenceTaxLaws(countryOfResiAsTaxLaws);
                    object.setLegalDocument(legalDoc);
                    object.setIdentityNo(identityNo);
                    object.setTinIssuingCountry(tinIssuingCountry);

                    String poaOthers = "";
                    if (addressUpdateFlag.equals("01") && object.getCustEntityType().equalsIgnoreCase("01")) {
                        if (object.getPoa().equalsIgnoreCase("99")) {
                            errorCode = object.getPerOtherPoa().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_084.getCode()) : errorCode;
                            poaOthers = object.getPoa();
                        }
                    }
                    object.setPerOtherPoa(poaOthers);

                    //Separate Validation
                    if (object.getCustEntityType().equalsIgnoreCase("01")) { //For Only Individual
                        errorCode = object.getAcType().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_005.getCode()) : errorCode;    //No need to size validation.
                        errorCode = object.getFatherSpouseFlag().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_068.getCode()) : errorCode;
                    } else if (object.getCustEntityType().equalsIgnoreCase("02")) { //For Only Legal Entity
                        errorCode = object.getAcHolderTypeFlag().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_003.getCode()) : errorCode;  //No need to size validation.
                        errorCode = object.getAcHolderType().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_004.getCode()) : errorCode;  //No need to size validation.
                    }
                    //For Both
                    errorCode = object.getCkycNo().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_006.getCode()) : errorCode;    //No need to size validation.
                    errorCode = object.getIsdCode().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_054.getCode()) : errorCode;
                    if (!object.getIsdCode().equals("")) {
                        errorCode = (object.getIsdCode().trim().length() > 3 || !ParseFileUtil.isNumeric(object.getIsdCode().trim())) ? returnErrorCode(errorCode, CkycrEnum.ERR_086.getCode()) : errorCode;
                    }
                    errorCode = object.getMobileNo().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_055.getCode()) : errorCode;
                    if (!object.getMobileNo().equals("") && (object.getMobileNo().trim().length() != 10) && !ParseFileUtil.isNumeric(object.getMobileNo().trim())) {
                        errorCode = (object.getMobileNo().trim().length() != 10 || !ParseFileUtil.isNumeric(object.getMobileNo().trim())) ? returnErrorCode(errorCode, CkycrEnum.ERR_087.getCode()) : errorCode;
                    }
                    errorCode = (object.getCustomerUpdationDate() == null || object.getCustomerUpdationDate().equals("") || object.getCustomerUpdationDate().equals("00000000")) ? returnErrorCode(errorCode, CkycrEnum.ERR_056.getCode()) : errorCode;
                    errorCode = (object.getKycVerifiedDate() == null || object.getKycVerifiedDate().equals("") || object.getKycVerifiedDate().equals("00000000")) ? returnErrorCode(errorCode, CkycrEnum.ERR_058.getCode()) : errorCode;
                    errorCode = object.getTypeOfDocSubmitted().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_059.getCode()) : errorCode;
                    errorCode = object.getKycVerifiedUserName().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_060.getCode()) : errorCode;
                    errorCode = object.getBrnCode().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_061.getCode()) : errorCode;
                    errorCode = object.getKycVerifiedBy().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_062.getCode()) : errorCode;
                    errorCode = object.getCustImage().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_092.getCode()) : errorCode;
                    if (!object.getCustImage().equals("")) {
                        errorCode = isAllRequiredImagesScaned(bankCode, object.getCustomerId().trim(), object.getCustImage().trim()) == false ? returnErrorCode(errorCode, CkycrEnum.ERR_093.getCode()) : errorCode;
                    }
                    //Multiple Identity If Available
                    List<CKYCRDownloadDetail30> identityDetails = fetch30Records(object.getCustomerId().trim());
                    for (CKYCRDownloadDetail30 idObj : identityDetails) {
                        errorCode = idObj.getIdentificationtype().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_094.getCode()) : errorCode;
                        if (object.getCustEntityType().equalsIgnoreCase("01") && !idObj.getIdentificationtype().equalsIgnoreCase("Z")) {
                            errorCode = idObj.getIdentityNumber().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_095.getCode()) : errorCode;
                        }
                        if (object.getCustEntityType().equalsIgnoreCase("01") && (idObj.getIdentificationtype().equalsIgnoreCase("A")
                                || idObj.getIdentificationtype().equalsIgnoreCase("D"))) {
                            errorCode = idObj.getExpiryDate().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_096.getCode()) : errorCode;
                        }
                    }
                    //Multiple 60 Type of Records
                    List<CKYCRDownloadDetail60> contactDetails = fetch60Records(object.getCustomerId().trim());
                    for (CKYCRDownloadDetail60 contactObj : contactDetails) {
                        errorCode = contactObj.getAddressType().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_097.getCode()) : errorCode;
                        errorCode = contactObj.getLocalAddressLine1().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_098.getCode()) : errorCode;
                        errorCode = contactObj.getLocalAddressDistrict().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_099.getCode()) : errorCode;
                        errorCode = contactObj.getLocalAddressCountry().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_100.getCode()) : errorCode;
                        if (contactObj.getLocalAddressCountry().equals("IN")) { //For India
                            errorCode = contactObj.getLocalAddressPINCode().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_101.getCode()) : errorCode;
                        }
                        errorCode = contactObj.getDateofDeclaration().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_102.getCode()) : errorCode;
                        errorCode = contactObj.getPlaceofDeclaration().equals("") ? returnErrorCode(errorCode, CkycrEnum.ERR_103.getCode()) : errorCode;
                    }
                }
                if (errorCode.equals("")) { //Valid 
                    if (object.getMode().equalsIgnoreCase("upload")) {
                        uploadRequestList.add(object);
                    } else if (object.getMode().equalsIgnoreCase("update")) {
                        updateRequestList.add(object);
                    }
                } else {    //Invalid
                    errorUpdationInValidation(object, errorCode, "");
                }
            }
            validCustomers.add(uploadRequestList);
            validCustomers.add(updateRequestList);
        } catch (Exception ex) {
            System.out.println("In SSS");
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
        return validCustomers;
    }

    public String returnErrorCode(String previousErrors, String currentErrors) throws Exception {
        try {
            if (currentErrors.trim().equals("")) {
                return previousErrors.trim();
            } else if (previousErrors.trim().equals("") && !currentErrors.trim().equals("")) {
                return currentErrors.trim();
            } else {
                return previousErrors.trim() + "|" + currentErrors.trim();
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void errorUpdationInValidation(CKYCRRequestPojo object, String reasonCode,
            String reason) throws Exception {
        try {
            System.out.println("In errorUpdationInValidation");
            List list = em.createNativeQuery("select mode from ckycr_request_detail where "
                    + "customerid_ckycr_no='" + object.getCustomerId() + "'").getResultList();
            int n = 0;
            if (list.isEmpty()) {
                n = em.createNativeQuery("insert into ckycr_request_detail(mode,type,customerid_ckycr_no,"
                        + "branch_code,status,reason,reason_code,request_by,request_date,request_trantime,"
                        + "response_update_time,fetch_mode,batch_no,seq_no) "
                        + "values('UPLOAD','BULK','" + object.getCustomerId() + "','" + object.getPrimaryBrCode() + "',"
                        + "'FAILURE','" + reason + "','" + reasonCode + "','System',date_format(now(),'%Y%m%d'),now(),"
                        + "now(),'Scheduler','',0)").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem in ckycr_request_detail insertion.");
                }
            } else {
                n = em.createNativeQuery("insert into ckycr_request_detail_his(mode,type,customerid_ckycr_no,dob,"
                        + "branch_code,status,reason,reason_code,request_by,request_date,request_trantime,"
                        + "response_update_time,fetch_mode,batch_no,seq_no,reference_no,response_type,"
                        + "id_verification_status,remarks) select mode,type,customerid_ckycr_no,dob,branch_code,"
                        + "status,reason,reason_code,request_by,request_date,request_trantime,response_update_time,"
                        + "fetch_mode,batch_no,seq_no,reference_no,response_type,id_verification_status,remarks "
                        + "from ckycr_request_detail where customerid_ckycr_no='" + object.getCustomerId() + "'").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem in ckycr_request_detail_his maintainance.");
                }

                n = em.createNativeQuery("update ckycr_request_detail set status='FAILURE',reason='" + reason + "',"
                        + "reason_code='" + reasonCode + "',response_update_time=now(),seq_no=0 where "
                        + "customerid_ckycr_no='" + object.getCustomerId() + "'").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem in unmatched customer id updation.");
                }
            }
            System.out.println("In errorUpdationInValidation End");
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public List<CKYCRRequestPojo> combiningRequestAndData(List<CKYCRRequestPojo> requestList, List dataList) throws Exception {
        List<CKYCRRequestPojo> combinedRequestDataList = new ArrayList<CKYCRRequestPojo>();
        try {
            for (int i = 0; i < requestList.size(); i++) {
                CKYCRRequestPojo requestObj = requestList.get(i);
                for (int j = 0; j < dataList.size(); j++) {
                    Vector ele = (Vector) dataList.get(j);
                    if (requestObj.getCustomerId().equalsIgnoreCase(ele.get(0).toString().trim())) {
                        requestObj.setCustEntityType(ele.get(2).toString().trim());
                        requestObj.setAcHolderTypeFlag(ele.get(3).toString().trim());
                        requestObj.setAcHolderType(ele.get(4).toString().trim());
                        requestObj.setAcType(ele.get(5).toString().trim());
                        requestObj.setCkycNo(ele.get(6).toString().trim());
                        requestObj.setTitle(ele.get(7).toString().trim());
                        requestObj.setCustName(ele.get(8).toString().trim());
                        requestObj.setMiddleName(ele.get(9).toString().trim());
                        requestObj.setLastName(ele.get(10).toString().trim());
                        requestObj.setFatherName(ele.get(11).toString().trim());
                        requestObj.setFatherMiddleName(ele.get(12).toString().trim());
                        requestObj.setFatherLastName(ele.get(13).toString().trim());
                        requestObj.setSpouseName(ele.get(14).toString().trim());
                        requestObj.setSpouseMiddleName(ele.get(15).toString().trim());
                        requestObj.setSpouseLastName(ele.get(16).toString().trim());
                        requestObj.setMotherName(ele.get(17).toString().trim());
                        requestObj.setMotherMiddleName(ele.get(18).toString().trim());
                        requestObj.setMotherLastName(ele.get(19).toString().trim());
                        requestObj.setGender(ele.get(20).toString().trim());
                        requestObj.setMaritalStatus(ele.get(21).toString().trim());
                        requestObj.setNationality(ele.get(22).toString().trim());
                        requestObj.setDateOfBirth(ele.get(23).toString().trim());   //yyyyMMdd
                        requestObj.setLegalDocument(ele.get(24).toString().trim());
                        requestObj.setIdentityNo(ele.get(25).toString().trim());
                        requestObj.setIdExpiryDate(ele.get(26).toString().trim());  //yyyyMMdd
                        requestObj.setTinIssuingCountry(ele.get(27).toString().trim());
                        requestObj.setNriFlag(ele.get(28).toString().trim());
                        requestObj.setPerAddType(ele.get(29).toString().trim());
                        requestObj.setPerAddressLineOne(ele.get(30).toString().trim());
                        requestObj.setPerVillage(ele.get(31).toString().trim());
                        requestObj.setPerDistrict(ele.get(32).toString().trim());
                        requestObj.setPerStateCode(ele.get(33).toString().trim());
                        requestObj.setPerCountryCode(ele.get(34).toString().trim());
                        requestObj.setPerPostalCode(ele.get(35).toString().trim());
                        requestObj.setPoa(ele.get(36).toString().trim());
                        requestObj.setPerOtherPoa(ele.get(37).toString().trim());
                        requestObj.setPerMailAddSameFlagIndicate(ele.get(38).toString().trim());
                        requestObj.setMailAddType(ele.get(39).toString().trim());
                        requestObj.setMailAddressLineOne(ele.get(40).toString().trim());
                        requestObj.setMailVillage(ele.get(41).toString().trim());
                        requestObj.setMailDistrict(ele.get(42).toString().trim());
                        requestObj.setMailStateCode(ele.get(43).toString().trim());
                        requestObj.setMailCountryCode(ele.get(44).toString().trim());
                        requestObj.setMailPostalCode(ele.get(45).toString().trim());
                        requestObj.setMailPoa(ele.get(46).toString().trim());
                        requestObj.setMailOtherPoa(ele.get(47).toString().trim());
                        requestObj.setJuriAddBasedOnFlag(ele.get(48).toString().trim());
                        requestObj.setJuriAddType(ele.get(49).toString().trim());
                        requestObj.setJuriAddOne(ele.get(50).toString().trim());
                        requestObj.setJuriCity(ele.get(51).toString().trim());
                        requestObj.setJuriState(ele.get(52).toString().trim());
                        requestObj.setJuriCountry(ele.get(53).toString().trim());
                        requestObj.setJuriPostal(ele.get(54).toString().trim());
                        requestObj.setJuriPoa(ele.get(55).toString().trim());
                        requestObj.setJuriOtherPoa(ele.get(56).toString().trim());
                        requestObj.setIsdCode(ele.get(57).toString().trim());
                        requestObj.setMobileNo(ele.get(58).toString().trim());
                        requestObj.setCreationTime(ele.get(59).toString().trim());
                        requestObj.setConstitutionCode(ele.get(60).toString().trim());
                        requestObj.setOccupationCode(ele.get(61).toString().trim());
                        requestObj.setIncorporationPlace(ele.get(62).toString().trim());
                        requestObj.setCommencementDate(ele.get(63).toString().trim());  //yyyyMMdd
                        requestObj.setMisJuriResidence(ele.get(64).toString().trim());
                        requestObj.setMisTin(ele.get(65).toString().trim());
                        requestObj.setMisBirthCountry(ele.get(66).toString().trim());
                        requestObj.setMisCity(ele.get(67).toString().trim());
                        requestObj.setTypeOfDocSubmitted(ele.get(68).toString().trim());
                        requestObj.setKycVerifiedUserName(ele.get(69).toString().trim());
                        requestObj.setBrnCode(ele.get(70).toString().trim());
                        requestObj.setKycVerifiedBy(ele.get(71).toString().trim());
                        requestObj.setFatherSpouseFlag(ele.get(72).toString().trim());
                        requestObj.setPanGirNumber(ele.get(73).toString().trim());
                        requestObj.setCountryIncorporation(ele.get(74).toString().trim());
                        requestObj.setCountryResidenceTaxLaws(ele.get(75).toString().trim());
                        requestObj.setCustomerUpdationDate(ele.get(76) == null ? "" : ele.get(76).toString().trim());
                        requestObj.setKycVerifiedDate(ele.get(77) == null ? "" : ele.get(77).toString().trim());
                        requestObj.setCustImage(ele.get(78).toString().trim());

                        break;
                    }
                }
                combinedRequestDataList.add(requestObj);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return combinedRequestDataList;
    }

    public List<CKYCRRequestPojo> unMatchedCustomerUpdation(List<CKYCRRequestPojo> requestList, List dataList) throws Exception {
        List<CKYCRRequestPojo> matchedCustomerIdList = new ArrayList<CKYCRRequestPojo>();
        try {
            Map<String, String> unMatchedCustomerIdMap = new HashMap<String, String>();
            //There are some missing customerid in tables cbs_cust_misinfo, cbs_cust_kyc_details
            for (int i = 0; i < requestList.size(); i++) {
                CKYCRRequestPojo pojo = requestList.get(i);
                boolean found = false;
                for (int j = 0; j < dataList.size(); j++) {
                    Vector ele = (Vector) dataList.get(j);
                    if (ele.get(0).toString().trim().equalsIgnoreCase(pojo.getCustomerId())) {
                        found = true;
                        matchedCustomerIdList.add(pojo);
                        break;
                    }
                }
                if (found == false) {
                    unMatchedCustomerIdMap.put(pojo.getCustomerId(), pojo.getPrimaryBrCode());
                }
            }
            //Status updation of unmatched customer id request.
            Set<Entry<String, String>> unMatchedCustomerIdSet = unMatchedCustomerIdMap.entrySet();
            Iterator<Entry<String, String>> it = unMatchedCustomerIdSet.iterator();
            while (it.hasNext()) {
                Entry<String, String> entry = it.next();

                String customerId = entry.getKey();
                String primaryBrCode = entry.getValue();
                List list = em.createNativeQuery("select mode from ckycr_request_detail where "
                        + "customerid_ckycr_no='" + customerId + "'").getResultList();
                int n = 0;
                if (list.isEmpty()) {
                    n = em.createNativeQuery("insert into ckycr_request_detail(mode,type,customerid_ckycr_no,"
                            + "branch_code,status,reason,reason_code,request_by,request_date,request_trantime,"
                            + "response_update_time,fetch_mode,batch_no,seq_no) "
                            + "values('UPLOAD','BULK','" + customerId + "','" + primaryBrCode + "','FAILURE',"
                            + "'" + CkycrEnum.ERR_001.getValue() + "','" + CkycrEnum.ERR_001.getCode() + "',"
                            + "'System',date_format(now(),'%Y%m%d'),now(),now(),'Scheduler','',0)").executeUpdate();

                    if (n <= 0) {
                        throw new Exception("Problem in ckycr_request_detail table insertion.");
                    }
                } else {
                    n = em.createNativeQuery("insert into ckycr_request_detail_his(mode,type,customerid_ckycr_no,dob,"
                            + "branch_code,status,reason,reason_code,request_by,request_date,request_trantime,"
                            + "response_update_time,fetch_mode,batch_no,seq_no,reference_no,response_type,"
                            + "id_verification_status,remarks) select mode,type,customerid_ckycr_no,dob,branch_code,"
                            + "status,reason,reason_code,request_by,request_date,request_trantime,response_update_time,"
                            + "fetch_mode,batch_no,seq_no,reference_no,response_type,id_verification_status,remarks "
                            + "from ckycr_request_detail where customerid_ckycr_no='" + customerId + "'").executeUpdate();
                    if (n <= 0) {
                        throw new Exception("Problem in ckycr_request_detail_his maintainance.");
                    }

                    n = em.createNativeQuery("update ckycr_request_detail set status='FAILURE',"
                            + "reason='" + CkycrEnum.ERR_001.getValue() + "',reason_code='" + CkycrEnum.ERR_001.getCode() + "',"
                            + "response_update_time=now(),seq_no=0 where customerid_ckycr_no='" + customerId + "'").executeUpdate();
                    if (n <= 0) {
                        throw new Exception("Problem in ckycr_request_detail updation.");
                    }
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return matchedCustomerIdList;
    }

    public void scheduledRequestUpdation(CKYCRRequestPojo object, String batchNo, int seqNo) throws Exception {
        try {
            List list = em.createNativeQuery("select mode from ckycr_request_detail where "
                    + "customerid_ckycr_no='" + object.getCustomerId() + "'").getResultList();
            int n = 0;
            if (list.isEmpty()) {
                n = em.createNativeQuery("insert into ckycr_request_detail(mode,type,customerid_ckycr_no,"
                        + "branch_code,status,reason,reason_code,request_by,request_date,request_trantime,"
                        + "response_update_time,fetch_mode,batch_no,seq_no) "
                        + "values('UPLOAD','BULK','" + object.getCustomerId() + "','" + object.getPrimaryBrCode() + "',"
                        + "'UPLOADED','','','System',date_format(now(),'%Y%m%d'),now(),"
                        + "now(),'" + object.getFetchMode().trim() + "','" + batchNo + "'," + seqNo + ")").executeUpdate();
            } else {
                n = em.createNativeQuery("update ckycr_request_detail set status='UPLOADED',response_update_time=now(),"
                        + "batch_no='" + batchNo + "',seq_no=" + seqNo + " where customerid_ckycr_no='" + object.getCustomerId() + "'").executeUpdate();
            }
            if (n <= 0) {
                throw new Exception("Problem in scheduled request updation.");
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private void processUploadResponseStage_1_N() throws Exception {
        System.out.println("In processUploadResponseStage_1_N Method");
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            //Comment on 05/12/2018
//            SftpUtil util = SftpUtilFactory.getSftpUtil();
//            SftpSession session = getSftpSession(props.getProperty("ckycrSftpHost").trim(),
//                    props.getProperty("ckycrSftpUser").trim(),
//                    props.getProperty("ckycrSftpPassword").trim());
//
//            String remoteResponsePath = props.getProperty("ckycrSftpUploadResponseLocation").trim();
//            String localResponsePath = props.getProperty("cbsUploadResponseLocation").trim();
//            String remoteResponseDir = remoteResponsePath.substring(0, remoteResponsePath.lastIndexOf("/") + 1);
//            if (util.ls(session, remoteResponseDir).getSuccessFlag()) {
//                util.get(session, remoteResponsePath, localResponsePath);
//                //Stop the file removal because there is no permission to delete it on ckycr portal.
//            }
//            util.disconnect(session);
            //End here

            String localResponsePath = props.getProperty("cbsUploadResponseLocation").trim();
            String remoteResponsePath = props.getProperty("ckycrSftpUploadResponseLocation").trim();
            String remoteResponseDir = remoteResponsePath.substring(0, remoteResponsePath.lastIndexOf("/") + 1);

            String filePattern = remoteResponsePath.substring(remoteResponsePath.lastIndexOf("/") + 1);

            System.out.println("Before downloading the response file");
            download(localResponsePath, remoteResponseDir, filePattern);
            System.out.println("after downloading the response file");

            parseUploadResponse();
            System.out.println("After parseUploadResponse");
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

    private void parseUploadResponse() throws Exception {
        try {
            File dir = new File(props.getProperty("cbsUploadResponseLocation").trim());
            File[] files = dir.listFiles();
            for (File file : files) {
                String fileContentType = URLConnection.guessContentTypeFromName(file.getName());
                if (fileContentType.equals("text/plain")) {
                    List<UploadResponseDTO> pojoList = new ArrayList<UploadResponseDTO>();
                    try {
                        if (file.getName().trim().toLowerCase().contains("res")) { //Stage One 
                            pojoList = ckycrCommonMgmtRemote.parseUploadResponseStageOne(file);
                            updateStageOneUploadResponse(pojoList, "System");
                        } else { // n stage response
                            //To ignore the 1.2 version file
//                            String[] names = file.getName().split("\\_");
//                            if (!names[0].trim().equalsIgnoreCase(names[1].substring(0, 6).trim())) {
//                                System.out.println("Continue File Name-->" + file.getName());
//                                continue;
//                            }
                            //End here
                            pojoList = ckycrCommonMgmtRemote.parsePeriodicUploadResponse(file);
                            updateStageNUploadResponse(pojoList, "System");
                        }
                    } catch (Exception ex) {
                        System.out.println("Problem In parseUploadResponse() Method");
                    }
                } else {
                    System.out.println("Invalid File Extension !");
                }
            }
            if (files.length > 0) {
                createBackupAndThenRemoveFile(props.getProperty("cbsUploadResponseLocation").trim(),
                        props.getProperty("cbsUploadResponseBackupLocation").trim());
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private void updateStageOneUploadResponse(List<UploadResponseDTO> pojoList, String responseBy) throws Exception {
        try {
            String fileName = "", batchNo = "";
            for (int i = 0; i < pojoList.size(); i++) {
                UploadResponseDTO dto = pojoList.get(i);
                fileName = dto.getFileName().trim();
                batchNo = dto.getBatchNo().trim();
                Integer lineNo = dto.getLineNo();

                List list = em.createNativeQuery("select customerid_ckycr_no from ckycr_request_detail "
                        + "where batch_no='" + batchNo + "' and seq_no=" + lineNo + " and "
                        + "(mode='UPLOAD' or mode='UPDATE')").getResultList();
                if (!list.isEmpty()) {
                    Vector ele = (Vector) list.get(0);
                    String customerId = ele.get(0).toString();

                    int n = em.createNativeQuery("insert into ckycr_request_detail_his(mode,type,customerid_ckycr_no,dob,"
                            + "branch_code,status,reason,reason_code,request_by,request_date,request_trantime,"
                            + "response_update_time,fetch_mode,batch_no,seq_no,reference_no,response_type,"
                            + "id_verification_status,remarks) select mode,type,customerid_ckycr_no,dob,branch_code,"
                            + "status,reason,reason_code,request_by,request_date,request_trantime,response_update_time,"
                            + "fetch_mode,batch_no,seq_no,reference_no,response_type,id_verification_status,remarks "
                            + "from ckycr_request_detail where batch_no='" + batchNo + "' and seq_no=" + lineNo + "").executeUpdate();
                    if (n <= 0) {
                        throw new Exception("Problem in ckycr_request_detail_his maintainance");
                    }
                    if (dto.getRecordStatus().equalsIgnoreCase("01")) { //Record successfully uploaded
                        n = em.createNativeQuery("update ckycr_request_detail set status='" + dto.getRecordStatus() + "',"
                                + "reason='',reason_code='',response_update_time=now() where "
                                + "batch_no='" + batchNo + "' and seq_no=" + lineNo + "").executeUpdate();
                        if (n <= 0) {
                            throw new Exception("Problem in ckycr_request_detail status updation");
                        }
                    } else if (dto.getRecordStatus().equalsIgnoreCase("02")) { //Reject
                        //It can be changed in case of reason and reasoncode.
                        n = em.createNativeQuery("update ckycr_request_detail set status='" + dto.getRecordStatus() + "',"
                                + "reason='" + dto.getRemarks().trim() + "',reason_code='',response_update_time=now() where "
                                + "batch_no='" + batchNo + "' and seq_no=" + lineNo + "").executeUpdate();
                        if (n <= 0) {
                            throw new Exception("Problem in ckycr_request_detail status updation");
                        }
                    } else if (dto.getRecordStatus().equalsIgnoreCase("03")) { //Download (Confirmed Match)
                        n = em.createNativeQuery("update ckycr_request_detail set status='" + dto.getRecordStatus() + "',"
                                + "reason='',reason_code='',response_update_time=now() where "
                                + "batch_no='" + batchNo + "' and seq_no=" + lineNo + "").executeUpdate();
                        if (n <= 0) {
                            throw new Exception("Problem in ckycr_request_detail status updation");
                        }
                        n = em.createNativeQuery("update cbs_customer_master_detail set ckycno='" + dto.getCkycNo().trim() + "' where customerid='" + customerId + "'").executeUpdate();
                        if (n <= 0) {
                            throw new Exception("Problem in ckycrNo updation.");
                        }
                    }
                }
            }
            List list = em.createNativeQuery("select actual_mode from ckycr_file_detail where mode='UPLOAD' and "
                    + "batch_no='" + batchNo + "'").getResultList();
            if (!list.isEmpty()) {
                int n = em.createNativeQuery("insert into ckycr_file_detail_his(mode,batch_no,uploaded_file_name,"
                        + "uploaded_gen_date,response_file_name,response_date,actual_mode,response_by,uploaded_branch) "
                        + "select mode,batch_no,uploaded_file_name,uploaded_gen_date,response_file_name,response_date,"
                        + "actual_mode,response_by,uploaded_branch from ckycr_file_detail where mode='UPLOAD' and "
                        + "batch_no='" + batchNo + "'").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem In CKYCR File History Maintainance");
                }
                n = em.createNativeQuery("update ckycr_file_detail set response_file_name='" + fileName + "',"
                        + "response_date=now(),response_by='" + responseBy + "' where mode='UPLOAD' and "
                        + "batch_no='" + batchNo + "'").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem in CKYCR file updation.");
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private void updateStageNUploadResponse(List<UploadResponseDTO> pojoList, String responseBy) throws Exception {
        try {
            String fileName = "", batchNo = "";
            for (int i = 0; i < pojoList.size(); i++) {
                UploadResponseDTO dto = pojoList.get(i);
                fileName = dto.getFileName().trim();
//                batchNo = dto.getBatchNo().trim();
                batchNo = dto.getBatchNo().trim().split("\\_")[0].trim();
//                Integer lineNo = dto.getLineNo();
                Integer lineNo = Integer.parseInt(dto.getIdVerificationStatus().trim().split("\\_")[2].trim()); //Change on 26/02/2019

                List list = em.createNativeQuery("select customerid_ckycr_no from ckycr_request_detail "
                        + "where batch_no='" + batchNo + "' and seq_no=" + lineNo + " and "
                        + "(mode='UPLOAD' or mode='UPDATE')").getResultList();
                if (!list.isEmpty()) {
                    Vector ele = (Vector) list.get(0);
                    String customerId = ele.get(0).toString();

                    int n = em.createNativeQuery("insert into ckycr_request_detail_his(mode,type,customerid_ckycr_no,"
                            + "dob,branch_code,status,reason,reason_code,request_by,request_date,request_trantime,"
                            + "response_update_time,fetch_mode,batch_no,seq_no,reference_no,response_type,"
                            + "id_verification_status,remarks) select mode,type,customerid_ckycr_no,dob,branch_code,"
                            + "status,reason,reason_code,request_by,request_date,request_trantime,response_update_time,"
                            + "fetch_mode,batch_no,seq_no,reference_no,response_type,id_verification_status,remarks "
                            + "from ckycr_request_detail where batch_no='" + batchNo + "' and "
                            + "seq_no=" + lineNo + "").executeUpdate();
                    if (n <= 0) {
                        throw new Exception("Problem in ckycr_request_detail_his maintainance");
                    }
                    if (dto.getRecordStatus().equalsIgnoreCase("01")
                            || dto.getRecordStatus().equalsIgnoreCase("04")
                            || dto.getRecordStatus().equalsIgnoreCase("05")) { //Record successfully uploaded
                        n = em.createNativeQuery("update ckycr_request_detail set status='" + dto.getRecordStatus().trim() + "',"
                                + "reason='',reason_code='',response_update_time=now(),"
                                + "reference_no='" + dto.getReferenceNo().trim() + "',"
                                + "response_type='" + dto.getResponseType().trim() + "',"
                                + "id_verification_status='" + dto.getIdVerificationStatus().trim() + "',"
                                + "remarks='" + dto.getRemarks().trim() + "' where batch_no='" + batchNo + "' and "
                                + "seq_no=" + lineNo + "").executeUpdate();
                        if (n <= 0) {
                            throw new Exception("Problem in ckycr_request_detail status updation");
                        }
                        if (dto.getRecordStatus().equalsIgnoreCase("01")) {
                            n = em.createNativeQuery("update cbs_customer_master_detail set "
                                    + "ckycno='" + dto.getCkycNo().trim() + "' where customerid='" + customerId + "'").executeUpdate();
                            if (n <= 0) {
                                throw new Exception("Problem in ckycrNo updation.");
                            }
                        }
                    } else if (dto.getRecordStatus().equalsIgnoreCase("02")) { //Reject
                        //It can be changed in case of reason and reasoncode.
                        n = em.createNativeQuery("update ckycr_request_detail set status='" + dto.getRecordStatus() + "',"
                                + "reason='" + dto.getRemarks().trim() + "',reason_code='',response_update_time=now(),"
                                + "reference_no='" + dto.getReferenceNo().trim() + "',"
                                + "response_type='" + dto.getResponseType().trim() + "',"
                                + "id_verification_status='" + dto.getIdVerificationStatus().trim() + "',"
                                + "remarks='" + dto.getRemarks().trim() + "' where batch_no='" + batchNo + "' and "
                                + "seq_no=" + lineNo + "").executeUpdate();
                        if (n <= 0) {
                            throw new Exception("Problem in ckycr_request_detail status updation");
                        }
                    } else if (dto.getRecordStatus().equalsIgnoreCase("03")) { //Download (Confirmed Match)
                        n = em.createNativeQuery("update ckycr_request_detail set status='" + dto.getRecordStatus() + "',"
                                + "reason='',reason_code='',response_update_time=now(),"
                                + "reference_no='" + dto.getReferenceNo().trim() + "',"
                                + "response_type='" + dto.getResponseType().trim() + "',"
                                + "id_verification_status='" + dto.getIdVerificationStatus().trim() + "',"
                                + "remarks='" + dto.getRemarks().trim() + "' where batch_no='" + batchNo + "' and "
                                + "seq_no=" + lineNo + "").executeUpdate();
                        if (n <= 0) {
                            throw new Exception("Problem in ckycr_request_detail status updation");
                        }
                        n = em.createNativeQuery("update cbs_customer_master_detail set ckycno='" + dto.getCkycNo().trim() + "' where customerid='" + customerId + "'").executeUpdate();
                        if (n <= 0) {
                            throw new Exception("Problem in ckycrNo updation.");
                        }
                    }
                }
            }
            List list = em.createNativeQuery("select actual_mode from ckycr_file_detail where mode='UPLOAD' and "
                    + "batch_no='" + batchNo + "'").getResultList();
            if (!list.isEmpty()) {
                int n = em.createNativeQuery("insert into ckycr_file_detail_his(mode,batch_no,uploaded_file_name,"
                        + "uploaded_gen_date,response_file_name,response_date,actual_mode,response_by,uploaded_branch) "
                        + "select mode,batch_no,uploaded_file_name,uploaded_gen_date,response_file_name,response_date,"
                        + "actual_mode,response_by,uploaded_branch from ckycr_file_detail where mode='UPLOAD' and "
                        + "batch_no='" + batchNo + "'").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem In CKYCR File History Maintainance");
                }
                n = em.createNativeQuery("update ckycr_file_detail set response_file_name='" + fileName + "',"
                        + "response_date=now(),response_by='" + responseBy + "' where mode='UPLOAD' and "
                        + "batch_no='" + batchNo + "'").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem in CKYCR file updation.");
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void processDownloadRequest() throws Exception {
        System.out.println("In processDownloadRequest Method");
        try {
            List<CKYCRRequestPojo> requestList = new ArrayList<CKYCRRequestPojo>();

            List list = em.createNativeQuery("select customerid_ckycr_no,date_format(dob,'%Y%m%d') from ckycr_request_detail "
                    + "where mode='DOWNLOAD' and status ='ENTERED' and request_date<='" + ymd.format(new Date()) + "' order "
                    + "by request_date limit " + ckycrCommonMgmtRemote.getCkycrLimit() + "").getResultList();
            if (list.isEmpty()) {
                throw new Exception("There is no data to upload the download request");
            }
            for (int i = 0; i < list.size(); i++) {
                CKYCRRequestPojo obj = new CKYCRRequestPojo();
                Vector ele = (Vector) list.get(i);
                obj.setCustomerIdOrCKYCRNo(ele.get(0).toString().trim());
                obj.setDateOfBirth(ele.get(1).toString().trim());
                requestList.add(obj);
            }

            String cbsDownloadRequestLocation = props.getProperty("cbsDownloadRequestLocation").trim();
            String cbsDownloadRequestLocationDir = cbsDownloadRequestLocation.substring(0, cbsDownloadRequestLocation.lastIndexOf("/") + 1);

            generateDownloadRequest(cbsDownloadRequestLocationDir, "HO", requestList);

            SftpUtil util = SftpUtilFactory.getSftpUtil();
            SftpSession session = getSftpSession(props.getProperty("ckycrSftpHost").trim(),
                    props.getProperty("ckycrSftpUser").trim(),
                    props.getProperty("ckycrSftpPassword").trim());

//            util.put(session, cbsDownloadRequestLocation, props.getProperty("ckycrSftpDownloadRequestLocation").trim());
            util.put(session, cbsDownloadRequestLocationDir + "/" + "*.txt", props.getProperty("ckycrSftpDownloadRequestLocation").trim());
            Thread.sleep(5000);
            util.put(session, cbsDownloadRequestLocationDir + "/" + "*.trg", props.getProperty("ckycrSftpDownloadRequestLocation").trim());
            createBackupAndThenRemoveFile(cbsDownloadRequestLocationDir, props.getProperty("cbsDownloadRequestBackupLocation").trim());
            util.disconnect(session);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private void generateDownloadRequest(String cbsDownloadRequestLocationDir, String alphaCode, List<CKYCRRequestPojo> requestList) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String[] regionAndBranch = ckycrCommonMgmtRemote.getCkycrRegionCodeAndBranchCode(alphaCode);
            String[] cbsParameterInfoParams = getCbsParameterInfoCodes(); //It should not blank, there should be value for all parameters

            String nextBatchNo = ckycrViewMgmtRemote.getNextBatchNo("DOWNLOAD");
            String downloadRequestFileName = cbsParameterInfoParams[1] + "_"
                    + regionAndBranch[0] + "_" + dmyhms.format(new Date()) + "_"
                    + cbsParameterInfoParams[5] + "_" + "D" + nextBatchNo + "_" + cbsParameterInfoParams[2];
            FileWriter fw = new FileWriter(cbsDownloadRequestLocationDir + downloadRequestFileName + ".txt");
            //Header
            String headerRecord = "10|" + nextBatchNo + "|" + cbsParameterInfoParams[1] + "|"
                    + regionAndBranch[0] + "|" + regionAndBranch[1] + "|" + String.valueOf(requestList.size()) + "|||||" + "\n";
            fw.write(headerRecord);
            for (int i = 0; i < requestList.size(); i++) {
                CKYCRRequestPojo obj = requestList.get(i);
                String ckycNo = obj.getCustomerIdOrCKYCRNo().trim();
                String dob = obj.getDateOfBirth().trim();

                String individualDownloadRequest = "60|" + ckycNo + "|" + dMyyyy.format(ymd.parse(dob)) + "|||" + "\n";

                fw.write(individualDownloadRequest);
                //Status updation
//                int n = em.createNativeQuery("insert into ckycr_request_detail_his(mode,type,customerid_ckycr_no,dob,"
//                        + "branch_code,status,reason,reason_code,request_by,request_date,request_trantime,"
//                        + "response_update_time,fetch_mode,batch_no,seq_no,reference_no,response_type,"
//                        + "id_verification_status,remarks) select mode,type,customerid_ckycr_no,dob,branch_code,"
//                        + "status,reason,reason_code,request_by,request_date,request_trantime,response_update_time,"
//                        + "fetch_mode,batch_no,seq_no,reference_no,response_type,id_verification_status,remarks "
//                        + "from ckycr_request_detail where customerid_ckycr_no='" + ckycNo + "'").executeUpdate();
//                if (n <= 0) {
//                    throw new Exception("Problem in ckycr_request_detail_his maintainance");
//                }
                int n = em.createNativeQuery("update ckycr_request_detail set status='UPLOADED',reason='',"
                        + "reason_code='',response_update_time=now(),seq_no=0,batch_no='" + nextBatchNo + "' where "
                        + "customerid_ckycr_no='" + ckycNo + "'").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem in unmatched customer id updation.");
                }
            }
            fw.close();
            //Now writing the empty trg file
            FileWriter trgFile = new FileWriter(cbsDownloadRequestLocationDir + downloadRequestFileName + ".trg");
            trgFile.write(" ");
            trgFile.close();

            //Log maintainance of uploaded files.
            int n = em.createNativeQuery("insert into ckycr_file_detail(mode,batch_no,uploaded_file_name,"
                    + "uploaded_gen_date,response_file_name,actual_mode,response_by,uploaded_branch) "
                    + "values('DOWNLOAD','" + nextBatchNo + "','" + downloadRequestFileName + "',"
                    + "now(),'','','','" + commonReport.getBrncodeByAlphacode(alphaCode) + "')").executeUpdate();
            if (n <= 0) {
                throw new Exception("Problem in file log maintainance.");
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

    //Note: From here any update record will not return due to any reason.
    public void createUpdateData(File uploadFolder, String commonFileName, String nextBatchNo,
            String[] params, Integer totalRecord, String bankCode, String commonImgFolderName,
            List<CKYCRRequestPojo> updateRequestList, List<CKYCRRequestPojo> uploadRequestList,
            FileWriter fw, String regionCode, String ckycrBrCode) throws Exception {
        try {
            //Text file header preparation
            if (uploadRequestList.isEmpty()) {
                fw = new FileWriter(uploadFolder + "/" + commonFileName + ".txt");
                String header = "10|" + nextBatchNo + "|" + params[1] + "|" + regionCode + "|"
                        + totalRecord + "|" + dMyyyy.format(new Date()) + "|" + params[5] + "|||||" + "\n";
                fw.write(header);
            }

            Integer lineNo = uploadRequestList.size();

            for (int i = 0; i < updateRequestList.size(); i++) {
                CKYCRRequestPojo object = updateRequestList.get(i);
                System.out.println("createUpdateData() Method----->" + object.getCustomerId());
                String title = "", applicantFirstName = "", applicantMiddleName = "", applicantLastName = "",
                        completeApplicantName = "", fatherSpouseFlag = "", fatherSpouseTitle = "", fatherSpouseFirstName = "",
                        fatherSpouseMiddleName = "", fatherSpouseLastName = "", motherTitle = "", motherFirstName = "",
                        motherMiddleName = "", motherLastName = "", gender = "", maritalStatus = "", nationality = "",
                        occupationType = "", placeOfIncorp = "", dateOfCommencement = "", incorpCountry = "",
                        countryOfResidenceTaxLaws = "", identificationType = "", legalTin = "", tinIssuingCountry = "",
                        residentialStatus = "", juridictionOfResidence = "", flagJuriOutsideIndia = "", misTin = "",
                        misCountry = "", misCity = "", perAddType = "", perAddLineOne = "", perVillage = "", perDistrict = "",
                        perStateCode = "", perCountry = "", perPostalCode = "", perPoa = "", perOtherPoa = "",
                        perMailAddSameFlag = "", mailAddType = "", mailAddLineOne = "", mailVillage = "", mailDistrict = "",
                        mailStateCode = "", mailCountry = "", mailPostalCode = "", mailPoa = "", juriAddSameFlag = "",
                        juriAddType = "", juriAddLineOne = "", juriCity = "", juriState = "", juriCountry = "",
                        juriPostalCode = "";

                if (object.getCustEntityType().equalsIgnoreCase("01")) { //Individual
                    fatherSpouseFlag = (object.getPanGirNumber().trim().length() == 10
                            && ParseFileUtil.isValidPAN(object.getPanGirNumber().trim())
                            && object.getFatherSpouseFlag().equalsIgnoreCase("Y")) ? "02" : "01"; //01-Father, 02-Spouse
                } else if (object.getCustEntityType().equalsIgnoreCase("02")) { //Legal Entity
                }
                //Only if Applicant Name Details Update Flag is '01' and Cust Entity Type is '01'
                if (object.getApplicantNameUpdateFlag().equalsIgnoreCase("01")
                        && object.getCustEntityType().equalsIgnoreCase("01")) { //For updated and individual
                    title = object.getTitle().trim().length() > 5 ? object.getTitle().trim().substring(0, 5).trim() : object.getTitle().trim();
                    applicantFirstName = object.getCustName().trim().length() > 50 ? object.getCustName().trim().substring(0, 50).trim() : object.getCustName().trim();
                    applicantMiddleName = !object.getMiddleName().trim().equals("") ? (object.getMiddleName().trim().length() > 50 ? object.getMiddleName().trim().substring(0, 50).trim() : object.getMiddleName().trim()) : object.getMiddleName().trim();
                    applicantLastName = object.getLastName().trim().length() > 50 ? object.getLastName().trim().substring(0, 50).trim() : object.getLastName().trim();
                }
                //Only if Applicant Name Details Update Flag is '01' and Cust Entity Type is '02'
                if (object.getApplicantNameUpdateFlag().equalsIgnoreCase("01")
                        && object.getCustEntityType().equalsIgnoreCase("02")) {
                    String tempMiddleName = !object.getMiddleName().trim().equals("") ? (object.getMiddleName().trim().length() > 50 ? object.getMiddleName().trim().substring(0, 50).trim() : object.getMiddleName().trim()) : object.getMiddleName().trim();
                    completeApplicantName = (object.getCustName().trim().length() > 50 ? object.getCustName().trim().substring(0, 50).trim() : object.getCustName().trim()).trim() + " " + tempMiddleName.trim();
                    completeApplicantName = completeApplicantName.trim() + " " + (object.getLastName().trim().length() > 50 ? object.getLastName().trim().substring(0, 50).trim() : object.getLastName().trim()).trim();
                    completeApplicantName = completeApplicantName.trim().length() > 150 ? completeApplicantName.trim().substring(0, 150).trim() : completeApplicantName.trim();
                }
                //Only if Personal/Entity Details Update Flag  is '01' and Cust Entity Type is '01'
                if (object.getPersonalEntityUpdateFlag().equalsIgnoreCase("01")
                        && object.getCustEntityType().equalsIgnoreCase("01")) {
                    fatherSpouseTitle = getFatherSpouseTitle(fatherSpouseFlag, object.getGender().trim());
                    if (fatherSpouseFlag.equalsIgnoreCase("01")) { //Father
                        fatherSpouseFirstName = object.getFatherName().trim().length() > 50 ? object.getFatherName().trim().substring(0, 50).trim() : object.getFatherName().trim();
                        fatherSpouseMiddleName = !object.getFatherMiddleName().trim().equals("") ? (object.getFatherMiddleName().trim().length() > 50 ? object.getFatherMiddleName().trim().substring(0, 50).trim() : object.getFatherMiddleName().trim()) : object.getFatherMiddleName().trim();
                        fatherSpouseLastName = object.getFatherLastName().trim().length() > 50 ? object.getFatherLastName().trim().substring(0, 50).trim() : object.getFatherLastName().trim();
                    } else if (fatherSpouseFlag.equalsIgnoreCase("02")) { //Spouse
                        fatherSpouseFirstName = object.getSpouseName().trim().length() > 50 ? object.getSpouseName().trim().substring(0, 50).trim() : object.getSpouseName().trim();
                        fatherSpouseMiddleName = !object.getSpouseMiddleName().trim().equals("") ? (object.getSpouseMiddleName().trim().length() > 50 ? object.getSpouseMiddleName().trim().substring(0, 50).trim() : object.getSpouseMiddleName().trim()) : object.getSpouseMiddleName().trim();
                        fatherSpouseLastName = object.getSpouseLastName().trim().length() > 50 ? object.getSpouseLastName().trim().substring(0, 50).trim() : object.getSpouseLastName().trim();
                    }

                    motherTitle = "MRS";
                    motherFirstName = object.getMotherName().trim().length() > 50 ? object.getMotherName().trim().substring(0, 50).trim() : object.getMotherName().trim();
                    motherMiddleName = !object.getMotherMiddleName().trim().equals("") ? (object.getMotherMiddleName().trim().length() > 50 ? object.getMotherMiddleName().trim().substring(0, 50).trim() : object.getMotherMiddleName().trim()) : object.getMotherMiddleName().trim();
                    motherLastName = object.getMotherLastName().trim().length() > 50 ? object.getMotherLastName().trim().substring(0, 50).trim() : object.getMotherLastName().trim();
                    gender = object.getGender().trim();
                    maritalStatus = object.getMaritalStatus().trim();
                    nationality = object.getNationality().trim();
                    occupationType = ckycrCommonMgmtRemote.ckycrOccupationCode(object.getOccupationCode().trim()); //Check for error
                    residentialStatus = object.getNriFlag().trim();
                    juridictionOfResidence = object.getMisJuriResidence().trim();
                    flagJuriOutsideIndia = juridictionOfResidence.equalsIgnoreCase("IN") ? "02" : "01";
                    misTin = flagJuriOutsideIndia.equalsIgnoreCase("01") ? object.getMisTin().trim() : "";
                    misCountry = flagJuriOutsideIndia.equalsIgnoreCase("01") ? object.getMisBirthCountry().trim() : "";
                    misCity = flagJuriOutsideIndia.equalsIgnoreCase("01") ? (object.getMisCity().trim().length() > 50 ? object.getMisCity().trim().substring(0, 50) : object.getMisCity().trim()) : "";
                }
                //Only if Personal/Entity Details Update Flag  is '01' and Cust Entity Type is '02'
                if (object.getPersonalEntityUpdateFlag().equalsIgnoreCase("01")
                        && object.getCustEntityType().equalsIgnoreCase("02")) {
                    placeOfIncorp = object.getIncorporationPlace().trim().length() > 50 ? object.getIncorporationPlace().trim().substring(0, 50).trim() : object.getIncorporationPlace().trim();
                    dateOfCommencement = dMyyyy.format(ymd.parse(object.getCommencementDate().trim()));
                    incorpCountry = object.getCountryIncorporation();
                    countryOfResidenceTaxLaws = object.getCountryResidenceTaxLaws();
                    identificationType = object.getLegalDocument().trim();
                    legalTin = identificationType.equalsIgnoreCase("T") ? object.getIdentityNo().trim() : "";
                    tinIssuingCountry = identificationType.equalsIgnoreCase("T") ? object.getTinIssuingCountry().trim() : "";
                }
                //Only if Address Details Update Flag is '01'
                if (object.getAddressUpdateFlag().equalsIgnoreCase("01")) {
                    perAddType = object.getPerAddType().trim();
                    perAddLineOne = object.getPerAddressLineOne().trim().length() > 55 ? object.getPerAddressLineOne().trim().substring(0, 55).trim() : object.getPerAddressLineOne().trim();
                    perVillage = object.getPerVillage().trim().length() > 50 ? object.getPerVillage().trim().substring(0, 50).trim() : object.getPerVillage().trim();
                    perDistrict = object.getPerDistrict().trim().length() > 50 ? object.getPerDistrict().trim().substring(0, 50).trim() : object.getPerDistrict().trim();
                    perStateCode = object.getPerStateCode().trim();
                    perCountry = object.getPerCountryCode().trim();
                    perPostalCode = perCountry.equalsIgnoreCase("IN") ? object.getPerPostalCode().trim() : "";

                    perPoa = object.getPoa().trim();
                    perMailAddSameFlag = object.getPerMailAddSameFlagIndicate().trim();
                    mailAddLineOne = object.getMailAddressLineOne().trim().length() > 55 ? object.getMailAddressLineOne().trim().substring(0, 55).trim() : object.getMailAddressLineOne().trim();
                    mailVillage = object.getMailVillage().trim().length() > 50 ? object.getMailVillage().trim().substring(0, 50).trim() : object.getMailVillage().trim();
                    mailDistrict = object.getMailDistrict().trim().length() > 50 ? object.getMailDistrict().trim().substring(0, 50).trim() : object.getMailDistrict().trim();
                    mailStateCode = object.getMailStateCode().trim();
                    mailCountry = object.getMailCountryCode().trim();
                    mailPostalCode = mailCountry.equalsIgnoreCase("IN") ? object.getMailPostalCode().trim() : "";

                    if (object.getCustEntityType().equalsIgnoreCase("01")) {
                        juriAddSameFlag = object.getJuriAddBasedOnFlag().trim();
                    }
//                    juriAddSameFlag = object.getJuriAddBasedOnFlag().trim();

                    juriAddLineOne = object.getJuriAddOne().trim().length() > 55 ? object.getJuriAddOne().trim().substring(0, 55).trim() : object.getJuriAddOne().trim();
                    juriCity = object.getJuriCity().trim().length() > 50 ? object.getJuriCity().trim().substring(0, 50).trim() : object.getJuriCity().trim();
                    juriState = object.getJuriState().trim().length() > 50 ? object.getJuriState().trim().substring(0, 50).trim() : object.getJuriState().trim();
                    juriCountry = object.getJuriCountry().trim();
                    juriPostalCode = juriCountry.equalsIgnoreCase("IN") ? object.getJuriPostal().trim() : "";
                    if (object.getCustEntityType().equalsIgnoreCase("01")) {
                        perOtherPoa = perPoa.equalsIgnoreCase("99") ? (object.getPerOtherPoa().trim().length() > 75 ? object.getPerOtherPoa().trim().substring(0, 75) : object.getPerOtherPoa().trim()) : "";
                    }
                    if (object.getCustEntityType().equalsIgnoreCase("02")) {
                        mailAddType = object.getMailAddType().trim();
                        mailPoa = object.getMailPoa().trim();
                        juriAddType = object.getJuriAddType().trim();
                    }
                }
                //Both
                String dob = dMyyyy.format(ymd.parse(object.getDateOfBirth().trim()));
                String dateOfDeclaration = dMyyyy.format(ymd.parse(object.getCustomerUpdationDate().trim()));
                String placeOfDeclaration = ckycrCommonMgmtRemote.getBranchCity(Integer.parseInt(object.getPrimaryBrCode().trim()));
                String kycVerficationDate = dMyyyy.format(ymd.parse(object.getKycVerifiedDate().trim()));
                String kycVerficationName = object.getKycVerifiedUserName().trim().length() > 150 ? object.getKycVerifiedUserName().trim().substring(0, 150) : object.getKycVerifiedUserName().trim();
                String kycVerificationEmpCode = object.getKycVerifiedBy().trim().length() > 50 ? object.getKycVerifiedBy().trim().substring(0, 50) : object.getKycVerifiedBy().trim();

                //Line number
                lineNo = lineNo + 1;
                //Total No Of Images
                Map<String, String> imageMap = extractCustImage(object.getCustImage());
                String imageFolderName = commonImgFolderName + "_" + lineNo;
                File imageFolderDir = new File(uploadFolder + "/" + imageFolderName + "/");
                if (!imageFolderDir.exists()) {
                    imageFolderDir.mkdirs();
                }
                //Total No of other POIs
                List<CKYCRDownloadDetail30> otherIdList = fetch30Records(object.getCustomerId().trim());
                int totalIds = otherIdList.size() + 1;

                String twentyRecords = "20|" + lineNo + "|" + "03" + "|" + ckycrBrCode + "|"
                        + object.getApplicantNameUpdateFlag() + "|" + object.getPersonalEntityUpdateFlag() + "|"
                        + object.getAddressUpdateFlag() + "|" + object.getContactUpdateFlag() + "|"
                        + object.getRemarksUpdateFlag() + "|" + object.getKycVerificationUpdateFlag() + "|"
                        + object.getIdentityUpdateFlag() + "|" + object.getRelatedPersonUpdateFlag() + "|"
                        + object.getControllingPersonUpdateFlag() + "|" + object.getImageUpdateFlag() + "|"
                        + object.getConstitutionCode() + "|" + object.getAcHolderTypeFlag() + "|"
                        + object.getAcHolderType() + "|" + object.getAcType() + "|" + imageFolderName + "|"
                        + title + "|" + applicantFirstName + "|" + applicantMiddleName + "|" + applicantLastName
                        + "|" + completeApplicantName + "||||||" + fatherSpouseFlag + "|" + fatherSpouseTitle
                        + "|" + fatherSpouseFirstName + "|" + fatherSpouseMiddleName + "|" + fatherSpouseLastName
                        + "||" + motherTitle + "|" + motherFirstName + "|" + motherMiddleName + "|" + motherLastName
                        + "||" + gender + "|" + maritalStatus + "|" + nationality + "|" + occupationType + "|" + dob
                        + "|" + placeOfIncorp + "|" + dateOfCommencement + "|" + incorpCountry + "|"
                        + countryOfResidenceTaxLaws + "|" + identificationType + "|" + legalTin + "|" + tinIssuingCountry
                        + "||" + residentialStatus + "|" + flagJuriOutsideIndia + "|" + juridictionOfResidence + "|"
                        + misTin + "|" + misCountry + "|" + misCity + "|" + perAddType + "|" + perAddLineOne + "|||"
                        + perVillage + "|" + perDistrict + "|" + perStateCode + "|" + perCountry + "|" + perPostalCode + "|"
                        + perPoa + "|" + perOtherPoa + "|" + perMailAddSameFlag + "|" + mailAddType + "|"
                        + mailAddLineOne + "|||" + mailVillage + "|" + mailDistrict + "|" + mailStateCode + "|"
                        + mailCountry + "|" + mailPostalCode + "|" + mailPoa + "|" + juriAddSameFlag + "|"
                        + juriAddType + "|" + juriAddLineOne + "|||" + juriCity + "|" + juriState + "|"
                        + juriCountry + "|" + juriPostalCode + "||||||" + object.getIsdCode().trim() + "|"
                        + object.getMobileNo().trim() + "|||||" + dateOfDeclaration + "|" + placeOfDeclaration + "|"
                        + kycVerficationDate + "|" + object.getTypeOfDocSubmitted().trim() + "|" + kycVerficationName
                        + "|" + "Manager" + "|" + object.getBrnCode().trim() + "|" + kycVerificationEmpCode + "|"
                        + params[4] + "|" + params[1] + "|" + String.valueOf(totalIds) + "|0|0|0|" + imageMap.size() + "||||||" + "\n";
                fw.write(twentyRecords);
                //Thirty Record,POI. Either it is change or not it will go.
                String identityNo = "", expiryDate = "";
                String identityType = object.getLegalDocument().trim();
                if (object.getCustEntityType().equalsIgnoreCase("01")) {
                    if (!identityType.equalsIgnoreCase("Z")) {
                        identityNo = object.getIdentityNo();
                    }
                    if (identityType.equalsIgnoreCase("A") || identityType.equalsIgnoreCase("D")) {
                        expiryDate = dMyyyy.format(ymd.parse(object.getIdExpiryDate().trim()));
                    }
                }
                String thirtyRecord = "30|" + lineNo + "|" + object.getLegalDocument().trim() + "|" + identityNo + "|"
                        + expiryDate + "|01|01|||||" + "\n";
                fw.write(thirtyRecord);
                //For Other POI If Available
//                List<CKYCRDownloadDetail30> otherIdList = fetch30Records(object.getCustomerId().trim());
                for (CKYCRDownloadDetail30 idObj : otherIdList) {
                    identityNo = "";
                    expiryDate = "";
                    identityType = idObj.getIdentificationtype();
                    if (object.getCustEntityType().equalsIgnoreCase("01")) {
                        if (!identityType.equalsIgnoreCase("Z")) {
                            identityNo = idObj.getIdentityNumber();
                        }
                        if (identityType.equalsIgnoreCase("A") || identityType.equalsIgnoreCase("D")) {
                            expiryDate = dMyyyy.format(ymd.parse(idObj.getExpiryDate()));
                        }
                    }
                    thirtyRecord = "30|" + lineNo + "|" + identityType + "|" + identityNo + "|"
                            + expiryDate + "|01|01|||||" + "\n";
                    fw.write(thirtyRecord);
                }
                //Forty Record - Related Info (At present it should go only in the case of Individual).No need to validate related name fields.
                if (object.getMinorFlag().equalsIgnoreCase("MINOR")) {
                    String deletionFlag = object.getRelatedAdditionFlag().equalsIgnoreCase("F") ? "01" : "02";
                    String fortyRecord = "40|" + (i + 1) + "|" + object.getRelationType() + "|" + deletionFlag + "|"
                            + object.getRelatedCkycNo() + "|" + object.getRelatedNamePrefix() + "|" + object.getRelatedFirstName()
                            + "|" + object.getRelatedMiddleName() + "|" + object.getRelatedLastName() + "|||||||||||||||||"
                            + "||||||||||||||||||||||||||||||||||||||||||||" + "\n";
                    fw.write(fortyRecord);
                }
                //Sixty Record. Either it is change or not it will go.
                List<CKYCRDownloadDetail60> contactList = fetch60Records(object.getCustomerId().trim());
                for (CKYCRDownloadDetail60 contactObj : contactList) {
                    String localAddressLine1 = "", localAddressLine2 = "", localAddressLine3 = "", localAddressCity = "",
                            localAddressDistrict = "", localAddressPinCode = "", localAddressState = "";

                    localAddressLine1 = contactObj.getLocalAddressLine1().length() > 55 ? contactObj.getLocalAddressLine1().substring(0, 55).trim() : contactObj.getLocalAddressLine1();
                    localAddressLine2 = !contactObj.getLocalAddressLine2().equals("") ? (contactObj.getLocalAddressLine2().length() > 55 ? contactObj.getLocalAddressLine2().substring(0, 55).trim() : contactObj.getLocalAddressLine2()) : contactObj.getLocalAddressLine2();
                    localAddressLine3 = !contactObj.getLocalAddressLine3().equals("") ? (contactObj.getLocalAddressLine3().length() > 55 ? contactObj.getLocalAddressLine3().substring(0, 55).trim() : contactObj.getLocalAddressLine3()) : contactObj.getLocalAddressLine3();
                    localAddressCity = !contactObj.getLocalAddressCityVillage().equals("") ? (contactObj.getLocalAddressCityVillage().length() > 50 ? contactObj.getLocalAddressCityVillage().substring(0, 50).trim() : contactObj.getLocalAddressCityVillage()) : contactObj.getLocalAddressCityVillage();
                    localAddressDistrict = contactObj.getLocalAddressDistrict().length() > 50 ? contactObj.getLocalAddressDistrict().substring(0, 50).trim() : contactObj.getLocalAddressDistrict();
                    if (contactObj.getLocalAddressCountry().equalsIgnoreCase("IN")) {
                        localAddressPinCode = contactObj.getLocalAddressPINCode();
                    }
                    localAddressState = !contactObj.getLocalAddressState().equals("") ? (contactObj.getLocalAddressState().length() > 2 ? contactObj.getLocalAddressState().substring(0, 2).trim() : contactObj.getLocalAddressState()) : contactObj.getLocalAddressState();
                    String localDateDeclaration = dMyyyy.format(ymd.parse(contactObj.getDateofDeclaration()));
                    String localPlaceOfDeclaration = contactObj.getPlaceofDeclaration().length() > 50 ? contactObj.getPlaceofDeclaration().substring(0, 50).trim() : contactObj.getPlaceofDeclaration();

                    String sixtyRecord = "60|" + lineNo + "|" + regionCode + "|" + contactObj.getAddressType() + "|"
                            + localAddressLine1 + "|" + localAddressLine2 + "|" + localAddressLine3 + "|"
                            + localAddressCity + "|" + localAddressDistrict + "|" + localAddressPinCode + "|"
                            + localAddressState + "|" + contactObj.getLocalAddressCountry() + "||"
                            + contactObj.getResiTelSTDCode() + "|" + contactObj.getResiTelNo() + "|"
                            + contactObj.getOfficeTelSTDCode() + "|" + contactObj.getOfficeTelNo() + "|"
                            + contactObj.getMobISDCode() + "|" + contactObj.getMobileNo() + "|"
                            + contactObj.getFaxSTDCode() + "|" + contactObj.getFaxNo() + "|" + contactObj.getEmailID() + "|"
                            + localDateDeclaration + "|" + localPlaceOfDeclaration + "|||||" + "\n";
                    fw.write(sixtyRecord);
                }
                //Seventy Record,Images
                Set<Entry<String, String>> set = imageMap.entrySet();
                Iterator<Entry<String, String>> it = set.iterator();
                while (it.hasNext()) {
                    Entry<String, String> entry = it.next();
                    String key = entry.getKey(); //key and value is same in both case
                    System.out.println("Key Is-->" + key);
//                    String imageName = params[1] + "_" + key + object.getCustomerId().trim() + ".jpg";
                    String imageName = imageFolderName + "_" + key + object.getCustomerId().trim() + ".jpg";
                    //Writing the image file
                    String imageData = CbsUtil.readImageFromXMLFile(new File("/" + bankCode + "/CI/" + object.getCustomerId().trim() + "/" + key + ".xml"));
                    byte[] imageInByte = Base64.decode(imageData);
                    //convert byte array back to BufferedImage
                    InputStream in = new ByteArrayInputStream(imageInByte);
                    BufferedImage bImageFromConvert = ImageIO.read(in);
//                    ImageIO.write(bImageFromConvert, "jpg", new File(imageFolderDir + "/" + imageName));
                    ImageIO.write(bImageFromConvert, "jpg", new File(imageFolderDir + "/" + imageName));
                    //Writing the image data. Either it is change or not it will go.
                    String seventyRecord = "70|" + lineNo + "|" + imageName + "|" + key + "|01|"
                            + ckycrBrCode + "||||" + "\n"; //Check for pipe separator count for data.
                    fw.write(seventyRecord);
                }
                //Status updation or log maintainance in ckycr_request_detail table.
                scheduledRequestUpdation(object, nextBatchNo, lineNo);
                //Now zipping the image folder
//                ckycrCommonMgmtRemote.zipDir(imageFolderDir + "/", imageFolderName, uploadFolder + "/");
                ckycrCommonMgmtRemote.zipFolder(imageFolderDir + "/", uploadFolder + "/" + imageFolderName + ".zip");
                //Deleting The Image Folder
                File imageFolderToDelete = new File(imageFolderDir + "/");
                if (imageFolderToDelete.exists()) {
                    ckycrCommonMgmtRemote.delete(imageFolderToDelete);
                }
            }
            fw.close();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private void processDownloadResponse() throws Exception {
        System.out.println("In processDownloadResponse() Method");
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            SftpUtil util = SftpUtilFactory.getSftpUtil();
            SftpSession session = getSftpSession(props.getProperty("ckycrSftpHost").trim(),
                    props.getProperty("ckycrSftpUser").trim(),
                    props.getProperty("ckycrSftpPassword").trim());

            String sftpDownloadResponsePath = props.getProperty("ckycrSftpDownloadResponseLocation").trim();
            String cbsDownloadResposeLocation = props.getProperty("cbsDownloadResponseLocation").trim();
            String sftpDownloadResponseDir = sftpDownloadResponsePath.substring(0, sftpDownloadResponsePath.lastIndexOf("/") + 1);
            if (util.ls(session, sftpDownloadResponseDir).getSuccessFlag()) {
                util.get(session, sftpDownloadResponsePath, cbsDownloadResposeLocation);
                File dir = new File(cbsDownloadResposeLocation);
                File[] files = dir.listFiles();
                for (int i = 0; i < files.length; i++) {
                    util.rm(session, sftpDownloadResponseDir + files[i].getName());
                }
            }
            util.disconnect(session);
            parseDownloadResponse();
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

    private void parseDownloadResponse() throws Exception {
        try {
            File dir = new File(props.getProperty("cbsDownloadResponseLocation").trim());
            File[] files = dir.listFiles();
            for (File file : files) {
                System.out.println("file path------->>>>" + file.getCanonicalPath());
                ZipFile zipFile = new ZipFile(file.getCanonicalPath());
                for (Enumeration e = zipFile.entries(); e.hasMoreElements();) {
                    ZipEntry entry = (ZipEntry) e.nextElement();
                    System.out.println("Zip Folder Content----->>>" + entry.getName());
                    InputStream inputStream = zipFile.getInputStream(entry);
                    if (entry.getName().endsWith("txt")) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        BufferedReader bufr = new BufferedReader(reader);
                        String line;
                        String ckycLineNo = "";
                        String ckycrNo = "";
                        String batchCode = "";
                        while ((line = bufr.readLine()) != null) {
                            //------------ Read and store ckycr data into ckycr_download data for Detail 20 ------------------//
                            line = line + "'|";
                            String[] data = line.split("\\|");
                            if (data[0].equals("10")) {
                                batchCode = data[1];
                            }
                            if (data[0].equals("20")) {
                                CKYCRDownloadPojo pojo = ckycrViewMgmtRemote.setCKYCRDownload(data);
                                pojo.setZipFileName(file.getName());
                                pojo.setRequestType("DOWNLOAD");
                                pojo.setMode("MANUAL"); //Since it is from scheduler after feeding the download request. In case of real time value will be AUTO.
                                pojo.setRequestId(batchCode);
                                txnRemote.insertCKYCRDownload(pojo);
                                ckycLineNo = pojo.getLineNo();
                                ckycrNo = pojo.getcKYCno();
                            }
                            if (data[0].equals("30") && data[1].equals(ckycLineNo)) {
                                CKYCRDownloadDetail30 pojoDetail30 = ckycrViewMgmtRemote.setCKYCRDownloadDetail30(data);
                                pojoDetail30.setZipFileName(file.getName());
                                pojoDetail30.setRequestType("DOWNLOAD");
                                pojoDetail30.setMode("MANUAL");
                                pojoDetail30.setRequestId(batchCode);
                                pojoDetail30.setcKYCno(ckycrNo);
                                txnRemote.insertCKYCRDownloadDetail30(pojoDetail30);
                            }
                            if (data[0].equals("60") && data[1].equals(ckycLineNo)) {
                                CKYCRDownloadDetail60 pojoDetail60 = ckycrViewMgmtRemote.setCKYCRDownloadDetail60(data);
                                pojoDetail60.setZipFileName(file.getName());
                                pojoDetail60.setRequestType("DOWNLOAD");
                                pojoDetail60.setMode("MANUAL");
                                pojoDetail60.setRequestId(batchCode);
                                pojoDetail60.setcKYCno(ckycrNo);
                                txnRemote.insertCKYCRDownloadDetail60(pojoDetail60);
                            }
                        }
                        bufr.close();
                    }
                    //------------ unzip the downloaded ckycr file into temp folder ------------------//
                    if (entry.getName().endsWith("zip")) {
                        FileInputStream fileInp = new FileInputStream(file.getCanonicalPath());
                        ZipInputStream zipInputstream = new ZipInputStream(fileInp);
                        ZipEntry currentZipEntry = null;
                        while ((currentZipEntry = zipInputstream.getNextEntry()) != null) {
                            System.out.println(currentZipEntry.getName());
                            FileOutputStream outputfile = null;
                            if (currentZipEntry.getName().endsWith("zip")) {
                                File tempZip = new File("/home/ckycr/download-respons-temp/");
                                if (!tempZip.exists()) {
                                    tempZip.mkdirs();
                                }
                                outputfile = new FileOutputStream(tempZip.getCanonicalPath() + "/" + currentZipEntry.getName());
                                int data = 0;
                                while ((data = zipInputstream.read()) != - 1) {
                                    outputfile.write(data);
                                }
                                outputfile.close();
                            }
                        }
                        zipInputstream.close();
                    }
                }
                //------------ Read and store ckycr image data in the folder ------------------//
                File dirTemp = new File("/home/ckycr/download-respons-temp/");
                File[] filesTemp = dirTemp.listFiles();
                for (File fileTemp : filesTemp) {
                    System.out.println("file path------->>>>" + fileTemp.getCanonicalPath());
                    FileInputStream fileInp = new FileInputStream(fileTemp.getCanonicalPath());
                    ZipInputStream zipInputstream = new ZipInputStream(fileInp);
                    ZipEntry currentZipEntry = null;

                    File delFile = new File(props.getProperty("cbsDownloadImageLocation").trim() + fileTemp.getName().split("\\.")[0] + "/");
                    if (delFile.exists()) {
                        File[] fileTempArr = delFile.listFiles();
                        for (File f : fileTempArr) {
                            f.delete();
                        }
                    }

                    while ((currentZipEntry = zipInputstream.getNextEntry()) != null) {
                        System.out.println(currentZipEntry.getName());
                        FileOutputStream outputfile = null;
                        if (currentZipEntry.getName().endsWith("jpg") || currentZipEntry.getName().endsWith("jpeg") || currentZipEntry.getName().endsWith("pdf")
                                || currentZipEntry.getName().endsWith("tif") || currentZipEntry.getName().endsWith("tiff")) {
                            File downloadedImageLocation = new File(props.getProperty("cbsDownloadImageLocation").trim() + fileTemp.getName().split("\\.")[0] + "/");
                            if (!downloadedImageLocation.exists()) {
                                downloadedImageLocation.mkdirs();
                            }
                            outputfile = new FileOutputStream(downloadedImageLocation.getCanonicalPath() + "/" + currentZipEntry.getName().substring(currentZipEntry.getName().indexOf("\\") + 1));
                            int data = 0;
                            while ((data = zipInputstream.read()) != - 1) {
                                outputfile.write(data);
                            }
                            outputfile.close();
                        }
                    }
                    zipInputstream.close();
                }
            }
            //------------ move downloded ckycr zip file to backup and delete temp folder ------------------//
            if (files.length > 0) {
                createBackupAndThenRemoveFile(props.getProperty("cbsDownloadResponseLocation").trim(),
                        props.getProperty("cbsDownloadResponseBackupLocation").trim());
                File fileDelTemp = new File("/home/ckycr/download-respons-temp");
                File[] fileTempArr = fileDelTemp.listFiles();
                for (File f : fileTempArr) {
                    f.delete();
                }
                if (fileDelTemp.delete()) {
                    System.out.println(fileDelTemp.getName() + " is deleted!");
                } else {
                    System.out.println("Delete operation is failed.");
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void insertCKYCRDownload(CKYCRDownloadPojo pojo) throws Exception {
        try {
            if (!(pojo.getMode().equalsIgnoreCase("REAL"))) {
                List selectList = em.createNativeQuery("select distinct Request_Date from ckycr_request_detail where cast(Batch_No as unsigned)='" + pojo.getRequestId() + "' and mode='DOWNLOAD'").getResultList();
                if (selectList.isEmpty()) {
                    throw new ApplicationException("There is no such request to download it.");
                } else {
                    Vector vec = (Vector) selectList.get(0);
                    pojo.setRequestDate(vec.get(0).toString());
                }
            }
            String insertquery = "INSERT INTO ckycr_download (RecordType, LineNo, ApplicationType,"
                    + " BranchCode, ApplicantNameUpdateFlag, PersonalEntityDetailsUpdateFlag, AddressDetailsUpdateFlag,"
                    + " ContactDetailsUpdateFlag, RemarksUpdateFlag, KYCVerificationUpdateFlag, IdentityDetailsUpdateFlag,"
                    + " RelatedPersonDetailsFlag, ControllingPersonDetailsFlag, ImageDetailsUpdateFlag, ConstitutionType,"
                    + " AccHolderTypeFlag, AccHolderType, AccType, CKYCno, CustNamePrefix, CustFirstName, CustMiddleName,"
                    + " CustLastName, CustFullName, MaidenNamePrefix, MaidenFirstName, MaidenMiddleName, MaidenLastName,"
                    + " MaidenFullName, father_spouse_flag, FatherSpouseNamePrefix, FatherSpouseFirstName,"
                    + " FatherSpouseMiddleName, FatherSpouseLastName, FatherSpouseFullName, MotherNamePrefix,"
                    + " MotherFirstName, MotherMiddleName, MotherLastName, MotherFullName, Gender, MaritalStatus, "
                    + "Nationality, OccupationType, DateOfBirth, PlaceOfIncorporation, DateOfCommBusiness, "
                    + "CountryOfIncorporation, ResidenceCountryTaxLaw, IdentificationType, TINNo, TINIssuingCountry, "
                    + "PAN, ResidentialStatus, FlagCustResiForTaxJuriOutsideIN, JuriOfResidence, JuriTaxIdentificationNo,"
                    + " CountryOfBirth, PlaceOfBirth, PerAddType, PerAddressLine1, peraddressline2, peraddressline3, "
                    + "PerCityVillage, PerDistrict, PerState, PerCountryCode, PerPostalCode, PerPOA, PerOtherPOA, "
                    + "PerMailAddSameFlagIndicate, MailAddType, MailAddressLine1, MailAddressLine2, MailAddressLine3,"
                    + " MailCityVillage, MailDistrict, MailState, MailCountry, MailPostalCode, MailPOA,"
                    + " JuriAddBasedOnFlag, JuriAddType, JuriAddressLine1, JuriAddressLine2, JuriAddressLine3, "
                    + "JuriCityVillage, JuriState, JuriCountry, JuriPostCode, JuriPOA, ResidenceTelSTDCode,"
                    + " ResidenceTelNo, OfficeTeleSTDCode, OfficeTelNo, ISDCode, MobileNo, FaxSTDCode, FaxNo,"
                    + " EmailID, Remarks, DateOfDeclaration, PlaceOfDeclaration, KYCVerificationDate, "
                    + "TypeOfDocSubmitted, KYCVerificationName, KYCVerificDesignation, KYCVerificBranch,"
                    + " KYCVerificEMPCode, OrganisationName, OrganisationCode, NoOfIdentityDetails, "
                    + "NoOfrelatedpeople, NoOfControllingPersonResiOutsideIN, NoOfLocalAddDetails, NoOfImages, "
                    + "ErrorCode, Filler1, Filler2, Filler3, Filler4,ZipFileName,RequestType,Mode,RequestId,RequestDate) VALUES ('"
                    + pojo.getRecordType() + "','" + pojo.getLineNo() + "','"
                    + pojo.getApplicationType() + "','" + pojo.getBranchCode() + "','"
                    + pojo.getApplicantNameUpdateFlag() + "','" + pojo.getPersonalEntityDetailsUpdateFlag() + "','"
                    + pojo.getAddressDetailsUpdateFlag() + "','" + pojo.getContactDetailsUpdateFlag() + "','"
                    + pojo.getRemarksUpdateFlag() + "','" + pojo.getkYCVerificationUpdateFlag() + "','"
                    + pojo.getIdentityDetailsUpdateFlag() + "','" + pojo.getRelatedPersonDetailsFlag() + "','"
                    + pojo.getControllingPersonDetailsFlag() + "','" + pojo.getImageDetailsUpdateFlag() + "','"
                    + ParseFileUtil.addTrailingZeros(pojo.getConstitutionType(), 2) + "','" + pojo.getAccHolderTypeFlag() + "','"
                    + pojo.getAccHolderType() + "','" + pojo.getAccType() + "','"
                    + pojo.getcKYCno() + "','" + pojo.getCustNamePrefix() + "','"
                    + pojo.getCustFirstName() + "','" + pojo.getCustMiddleName() + "','"
                    + pojo.getCustLastName() + "','" + pojo.getCustFullName() + "','"
                    + pojo.getMaidenNamePrefix() + "','" + pojo.getMaidenFirstName() + "','"
                    + pojo.getMaidenMiddleName() + "','" + pojo.getMaidenLastName() + "','"
                    + pojo.getMaidenFullName() + "','" + pojo.getFather_spouse_flag() + "','"
                    + pojo.getFatherSpouseNamePrefix() + "','" + pojo.getFatherSpouseFirstName() + "','"
                    + pojo.getFatherSpouseMiddleName() + "','" + pojo.getFatherSpouseLastName() + "','"
                    + pojo.getFatherSpouseFullName() + "','" + pojo.getMotherNamePrefix() + "','"
                    + pojo.getMotherFirstName() + "','" + pojo.getMotherMiddleName() + "','"
                    + pojo.getMotherLastName() + "','" + pojo.getMotherFullName() + "','"
                    + pojo.getGender() + "','" + pojo.getMaritalStatus() + "','"
                    + pojo.getNationality() + "','" + pojo.getOccupationType() + "','"
                    + pojo.getDateOfBirth() + "','" + pojo.getPlaceOfIncorporation() + "','"
                    + pojo.getDateOfCommBusiness() + "','" + pojo.getCountryOfIncorporation() + "','"
                    + pojo.getResidenceCountryTaxLaw() + "','" + pojo.getIdentificationType() + "','"
                    + pojo.gettINNo() + "','" + pojo.gettINIssuingCountry() + "','"
                    + pojo.getpAN() + "','" + pojo.getResidentialStatus() + "','"
                    + pojo.getFlagCustResiForTaxJuriOutsideIN() + "','" + pojo.getJuriOfResidence() + "','"
                    + pojo.getJuriTaxIdentificationNo() + "','" + pojo.getCountryOfBirth() + "','"
                    + pojo.getPlaceOfBirth() + "','" + pojo.getPerAddType() + "','"
                    + pojo.getPerAddressLine1() + "','" + pojo.getPeraddressline2() + "','"
                    + pojo.getPeraddressline3() + "','" + pojo.getPerCityVillage() + "','"
                    + pojo.getPerDistrict() + "','" + pojo.getPerState() + "','"
                    + pojo.getPerCountryCode() + "','" + pojo.getPerPostalCode() + "','"
                    + pojo.getPerPOA() + "','" + pojo.getPerOtherPOA() + "','"
                    + pojo.getPerMailAddSameFlagIndicate() + "','" + pojo.getMailAddType() + "','"
                    + pojo.getMailAddressLine1() + "','" + pojo.getMailAddressLine2() + "','"
                    + pojo.getMailAddressLine3() + "','" + pojo.getMailCityVillage() + "','"
                    + pojo.getMailDistrict() + "','" + pojo.getMailState() + "','"
                    + pojo.getMailCountry() + "','" + pojo.getMailPostalCode() + "','"
                    + pojo.getMailPOA() + "','" + pojo.getJuriAddBasedOnFlag() + "','"
                    + pojo.getJuriAddType() + "','" + pojo.getJuriAddressLine1() + "','"
                    + pojo.getJuriAddressLine2() + "','" + pojo.getJuriAddressLine3() + "','"
                    + pojo.getJuriCityVillage() + "','" + pojo.getJuriState() + "','"
                    + pojo.getJuriCountry() + "','" + pojo.getJuriPostCode() + "','"
                    + pojo.getJuriPOA() + "','" + pojo.getResidenceTelSTDCode() + "','"
                    + pojo.getResidenceTelNo() + "','" + pojo.getOfficeTeleSTDCode() + "','"
                    + pojo.getOfficeTelNo() + "','" + pojo.getiSDCode() + "','"
                    + pojo.getMobileNo() + "','" + pojo.getFaxSTDCode() + "','"
                    + pojo.getFaxNo() + "','" + pojo.getEmailID() + "','"
                    + pojo.getRemarks() + "','" + pojo.getDateOfDeclaration() + "','"
                    + pojo.getPlaceOfDeclaration() + "','" + pojo.getkYCVerificationDate() + "','"
                    + pojo.getTypeOfDocSubmitted() + "','" + pojo.getkYCVerificationName() + "','"
                    + pojo.getkYCVerificDesignation() + "','" + pojo.getkYCVerificBranch() + "','"
                    + pojo.getkYCVerificEMPCode() + "','" + pojo.getOrganisationName() + "','"
                    + pojo.getOrganisationCode() + "','" + pojo.getNoOfIdentityDetails() + "','"
                    + pojo.getNoOfrelatedpeople() + "','" + pojo.getNoOfControllingPersonResiOutsideIN() + "','"
                    + pojo.getNoOfLocalAddDetails() + "','" + pojo.getNoOfImages() + "','"
                    + pojo.getErrorCode() + "','" + pojo.getFiller1() + "','"
                    + pojo.getFiller2() + "','" + pojo.getFiller3() + "','"
                    + pojo.getFiller4() + "','" + pojo.getZipFileName() + "','"
                    + pojo.getRequestType() + "','" + pojo.getMode() + "','"
                    + pojo.getRequestId() + "','" + pojo.getRequestDate() + "');";

            int n = em.createNativeQuery(insertquery).executeUpdate();

            if (!(pojo.getMode().equalsIgnoreCase("REAL"))) {

                if (n <= 0) {
                    throw new Exception("Problem in download response detail-20 dumping.");
                }
                List chqList = em.createNativeQuery("select status from ckycr_request_detail where "
                        + "customerid_ckycr_no='" + pojo.getcKYCno().trim() + "' and mode='DOWNLOAD' "
                        + "and status='UPLOADED'").getResultList();
                if (!chqList.isEmpty()) {
                    n = em.createNativeQuery("insert into ckycr_request_detail_his(mode,type,customerid_ckycr_no,dob,"
                            + "branch_code,status,reason,reason_code,request_by,request_date,request_trantime,"
                            + "response_update_time,fetch_mode,batch_no,seq_no,reference_no,response_type,"
                            + "id_verification_status,remarks) select mode,type,customerid_ckycr_no,dob,branch_code,"
                            + "status,reason,reason_code,request_by,request_date,request_trantime,response_update_time,"
                            + "fetch_mode,batch_no,seq_no,reference_no,response_type,id_verification_status,remarks "
                            + "from ckycr_request_detail where customerid_ckycr_no='" + pojo.getcKYCno().trim() + "'").executeUpdate();
                    if (n <= 0) {
                        throw new Exception("Problem in ckycr_request_detail_his maintainance.");
                    }

                    n = em.createNativeQuery("update ckycr_request_detail set status='DOWNLOADED',reason='',"
                            + "reason_code='',response_update_time=now(),seq_no=0 where "
                            + "customerid_ckycr_no='" + pojo.getcKYCno().trim() + "'").executeUpdate();
                    if (n <= 0) {
                        throw new Exception("Problem in download response updation.");
                    }
                }
            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void insertCKYCRDownloadDetail30(CKYCRDownloadDetail30 pojo) throws Exception {
        try {
            if (!(pojo.getMode().equalsIgnoreCase("REAL"))) {
                List selectList = em.createNativeQuery("select distinct Request_Date from ckycr_request_detail where cast(Batch_No as unsigned)='" + pojo.getRequestId() + "' and mode='DOWNLOAD'").getResultList();
                if (selectList.isEmpty()) {
                    throw new ApplicationException("There is no such request to download it.");
                } else {
                    Vector vec = (Vector) selectList.get(0);
                    pojo.setRequestDate(vec.get(0).toString());
                }
            }
            String insertquery = "INSERT INTO ckycr_download_detail_30 (CKYCno,RecordType,LineNumber,Identificationtype,"
                    + "IdentityNumber,ExpiryDate,IdProofSubmitted,IdVerificationStatus,Filler1,"
                    + "Filler2,Filler3,Filler4,ZipFileName,RequestType,Mode,RequestId,RequestDate) value ('"
                    + pojo.getcKYCno() + "','" + pojo.getRecordType() + "','"
                    + pojo.getLineNumber() + "','" + pojo.getIdentificationtype() + "','"
                    + pojo.getIdentityNumber() + "','" + pojo.getExpiryDate() + "','"
                    + pojo.getIdProofSubmitted() + "','" + pojo.getIdVerificationStatus() + "','"
                    + pojo.getFiller1() + "','" + pojo.getFiller2() + "','"
                    + pojo.getFiller3() + "','" + pojo.getFiller4() + "','"
                    + pojo.getZipFileName() + "','" + pojo.getRequestType() + "','"
                    + pojo.getMode() + "','" + pojo.getRequestId() + "','" + pojo.getRequestDate() + "');";
            int n = em.createNativeQuery(insertquery).executeUpdate();
            if (n <= 0) {
                throw new Exception("Problem in download response detail-30 dumping.");
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void insertCKYCRDownloadDetail60(CKYCRDownloadDetail60 pojo) throws Exception {
        try {
            if (!(pojo.getMode().equalsIgnoreCase("REAL"))) {
                List selectList = em.createNativeQuery("select distinct Request_Date from ckycr_request_detail where cast(Batch_No as unsigned)='" + pojo.getRequestId() + "' and mode='DOWNLOAD'").getResultList();
                if (selectList.isEmpty()) {
                    throw new ApplicationException("There is no such request to download it.");
                } else {
                    Vector vec = (Vector) selectList.get(0);
                    pojo.setRequestDate(vec.get(0).toString());
                }
            }
            String insertquery = "insert into ckycr_download_detail_60(CKYCno,RecordType,LineNumber,"
                    + "BranchCode,AddressType,LocalAddressLine1,LocalAddressLine2,LocalAddressLine3,"
                    + "LocalAddressCityVillage,LocalAddressDistrict,LocalAddressPINCode,LocalAddressState,"
                    + "LocalAddressCountry,ProofofAdd,ResiTelSTDCode,ResiTelNo,OfficeTelSTDCode,OfficeTelNo,"
                    + "MobISDCode,MobileNo,FaxSTDCode,FaxNo,EmailID,DateofDeclaration,PlaceofDeclaration,"
                    + "Filler1,Filler2,Filler3,Filler4,ZipFileName,RequestType,Mode,RequestId,RequestDate)"
                    + " value('"
                    + pojo.getcKYCno() + "','" + pojo.getRecordType() + "','"
                    + pojo.getLineNumber() + "','" + pojo.getBranchCode() + "','"
                    + pojo.getAddressType() + "','" + pojo.getLocalAddressLine1() + "','"
                    + pojo.getLocalAddressLine2() + "','" + pojo.getLocalAddressLine3() + "','"
                    + pojo.getLocalAddressCityVillage() + "','" + pojo.getLocalAddressDistrict() + "','"
                    + pojo.getLocalAddressPINCode() + "','" + pojo.getLocalAddressState() + "','"
                    + pojo.getLocalAddressCountry() + "','" + pojo.getProofofAdd() + "','"
                    + pojo.getResiTelSTDCode() + "','" + pojo.getResiTelNo() + "','"
                    + pojo.getOfficeTelSTDCode() + "','" + pojo.getOfficeTelNo() + "','"
                    + pojo.getMobISDCode() + "','" + pojo.getMobileNo() + "','"
                    + pojo.getFaxSTDCode() + "','" + pojo.getFaxNo() + "','"
                    + pojo.getEmailID() + "','" + pojo.getDateofDeclaration() + "','"
                    + pojo.getPlaceofDeclaration() + "','"
                    + pojo.getFiller1() + "','" + pojo.getFiller2() + "','"
                    + pojo.getFiller3() + "','" + pojo.getFiller4() + "','"
                    + pojo.getZipFileName() + "','" + pojo.getRequestType() + "','"
                    + pojo.getMode() + "','" + pojo.getRequestId() + "','" + pojo.getRequestDate() + "');";
            int n = em.createNativeQuery(insertquery).executeUpdate();
            if (n <= 0) {
                throw new Exception("Problem in download response detail-60 dumping.");
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private boolean isAllRequiredImagesScaned(String bankCode, String customerId, String custImages) throws Exception {
        boolean allImages = false;
        try {
            File imageDir = new File("/" + bankCode + "/CI/" + customerId + "/");
            if (!imageDir.exists()) {
                return allImages;
            }
            String[] scannedImages = imageDir.list();
            String[] feededImages = custImages.split("\\|");

            for (String feedImage : feededImages) {
                allImages = false;
                for (String scanImage : scannedImages) {
                    if (feedImage.equalsIgnoreCase(scanImage.substring(0, scanImage.indexOf(".")).trim())) {
                        allImages = true;
                        break;
                    }
                }
                if (allImages != true) {
                    return false;
                }
            }
            return true;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public SftpSession getSftpSession(String hostName, String user, String password) throws Exception {
        SftpUtil util = SftpUtilFactory.getSftpUtil();
        SftpSession session = null;
        try {
            session = util.connectByPasswdAuth(hostName.trim(), user.trim(), password.trim(),
                    SftpUtil.STRICT_HOST_KEY_CHECKING_OPTION_NO);
        } catch (SftpException e) {
            throw new ApplicationException(e.getMessage());
        }
        return session;
    }

    public void createBackupAndThenRemoveFile(String localIwFileDir, String localIwBackupDir) throws IOException {
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

    public boolean isModuleOn(String moduleName) throws Exception {
        try {
            List list = em.createNativeQuery("SELECT code from parameterinfo_report "
                    + "where reportname='" + moduleName + "'").getResultList();
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
            throw new Exception(e.getMessage());
        }
    }

    //Each parameter should have the values in database.
    public String[] getParameterInfoReportCodes() throws Exception {
        String[] arr = new String[6];
        try {
            List list = em.createNativeQuery("select a.code,b.code,c.code,d.code,e.code,f.code from "
                    + "(select * from parameterinfo_report where reportname in('CKYCR')) a,"
                    + "(select * from parameterinfo_report where reportname in('CKYCR-BULK-COUNT-LIMIT')) b,"
                    + "(select * from parameterinfo_report where reportname in('CKYCR-COUNT-MINUS')) c,"
                    + "(select * from parameterinfo_report where reportname in('BULK-CKYCR-FORM')) d,"
                    + "(select * from parameterinfo_report where reportname in('BULK-CKYCR-SCHEDULER')) e,"
                    + "(select * from parameterinfo_report where reportname in('CKYCR-MINOR-AGE')) f").getResultList();
            if (list.isEmpty()) {
                throw new Exception("Please define required parameterinfo report");
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                arr[0] = ele.get(0).toString(); //CKYCR
                arr[1] = ele.get(1).toString(); //CKYCR-BULK-COUNT-LIMIT
                arr[2] = ele.get(2).toString(); //CKYCR-COUNT-MINUS
                arr[3] = ele.get(3).toString(); //BULK-CKYCR-FORM
                arr[4] = ele.get(4).toString(); //BULK-CKYCR-SCHEDULER
                arr[5] = ele.get(5).toString(); //CKYCR-MINOR-AGE
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return arr;
    }

    //Each parameter should have the values in database.
    public String[] getCbsParameterInfoCodes() throws Exception {
        String[] arr = new String[6];
        try {
            List list = em.createNativeQuery("select a.code,b.code,c.code,d.code,e.code,f.code from "
                    + "(select * from cbs_parameterinfo where name in('MAX-FILE-SIZE-PER-KYC')) a,"
                    + "(select * from cbs_parameterinfo where name in('CKYCR-FI-CODE')) b,"
                    + "(select * from cbs_parameterinfo where name in('CKYCR-USER')) c,"
                    + "(select * from cbs_parameterinfo where name in('CKYCR-REGION-CODE')) d,"
                    + "(select * from cbs_parameterinfo where name in('ORGANIZATION-NAME')) e,"
                    + "(select * from cbs_parameterinfo where name in('VERSION')) f").getResultList();
            if (list.isEmpty()) {
                throw new Exception("Please define required parameters in cbs parameterinfo");
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                arr[0] = ele.get(0).toString(); //MAX-FILE-SIZE-PER-KYC
                arr[1] = ele.get(1).toString(); //CKYCR-FI-CODE
                arr[2] = ele.get(2).toString(); //CKYCR-USER
                arr[3] = ele.get(3).toString(); //CKYCR-REGION-CODE
                arr[4] = ele.get(4).toString(); //ORGANIZATION-NAME
                arr[5] = ele.get(5).toString(); //VERSION
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return arr;
    }

    public String generateManualUploadFile(List<CKYCRRequestPojo> requestList, String alphaCode) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String[] regionAndBranch = ckycrCommonMgmtRemote.getCkycrRegionCodeAndBranchCode(alphaCode);

            String allCustomers = "";
            for (int i = 0; i < requestList.size(); i++) {
                CKYCRRequestPojo obj = requestList.get(i);
                if (allCustomers.equals("")) {
                    allCustomers = "\'" + obj.getCustomerId() + "\'";
                } else {
                    allCustomers = allCustomers + ",\'" + obj.getCustomerId() + "\'";
                }
            }
            //Generation of upload zip file.
            generateUploadUpdateZipFile(requestList.size(), allCustomers, requestList, regionAndBranch[0], commonReport.getBrncodeByAlphacode(alphaCode), regionAndBranch[1]);
            //Putting file in filesystem
            File curDtDir = new File(props.getProperty("cbsUploadLocation").trim() + ymd.format(new Date()) + "/");
            if (!curDtDir.exists()) {
                curDtDir.mkdirs();
            }

//            SftpUtil util = SftpUtilFactory.getSftpUtil();
//            SftpSession session = getSftpSession(props.getProperty("ckycrSftpHost").trim(), props.getProperty("ckycrSftpUser").trim(),
//                    props.getProperty("ckycrSftpPassword").trim());
//            util.put(session, curDtDir + "/" + "*.*", props.getProperty("ckycrSftpUploadLocation").trim());

            File cbsUploadLocationBackupDir = new File(props.getProperty("cbsUploadLocationBackup").trim() + ymd.format(new Date()) + "/");
            if (!cbsUploadLocationBackupDir.exists()) {
                cbsUploadLocationBackupDir.mkdirs();
            }
            createBackupAndThenRemoveFile(curDtDir + "/", cbsUploadLocationBackupDir + "/");
//            util.disconnect(session);

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

    public String parseManualUploadResponse(File responseFile, String responseBy) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List<UploadResponseDTO> pojoList = new ArrayList<UploadResponseDTO>();
            if (responseFile.getName().trim().toLowerCase().contains("res")) { //Stage One 
                pojoList = ckycrCommonMgmtRemote.parseUploadResponseStageOne(responseFile);
                updateStageOneUploadResponse(pojoList, responseBy);
            } else { // n stage response
                pojoList = ckycrCommonMgmtRemote.parsePeriodicUploadResponse(responseFile);
                updateStageNUploadResponse(pojoList, responseBy);
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

    public String parseManualDownloadResponse() throws Exception {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            parseDownloadResponse();
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
    public List<CKYCRRequestPojo> getGenUploadData(String branchCode, String fromDate, String toDate) throws Exception {
        List<CKYCRRequestPojo> customerList = new ArrayList<>();
        try {
            int limitValue = ckycrCommonMgmtRemote.getCkycrLimit();
            List list = em.createNativeQuery("select mode,customerid_ckycr_no,branch_code,request_by,"
                    + "date_format(request_date,'%Y%m%d') as request_date,fetch_mode from ckycr_request_detail "
                    + "where fetch_mode='Manual' and mode in('UPLOAD','UPDATE') and status in('ENTERED','RESEND','UPDATE') and branch_code = '" + branchCode + "' "
                    + "and request_date between '" + fromDate + "' and '" + toDate + "' order by request_trantime "
                    + "limit " + limitValue + "").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no request to generate the upload file.");
            }
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                CKYCRRequestPojo pojo = new CKYCRRequestPojo();
                pojo.setMode(vtr.get(0).toString());
                pojo.setCustomerId(vtr.get(1).toString());
                pojo.setPrimaryBrCode(vtr.get(2).toString());
                pojo.setPrimaryBrName(commonReport.getAlphacodeByBrncode(vtr.get(2).toString()));
                pojo.setRequestBy(vtr.get(3).toString().toUpperCase());
                pojo.setRequestDate(dmyFormat.format(ymd.parse(vtr.get(4).toString())));
                pojo.setFetchMode(vtr.get(5).toString());
                pojo.setSelected(true);
                customerList.add(pojo);
            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return customerList;
    }

    public List<CKYCRRequestPojo> getGenDownloadManualData(String branchCode, String fromDate, String toDate) throws Exception {
        List<CKYCRRequestPojo> manualDownloadList = new ArrayList<CKYCRRequestPojo>();
        try {
            int limitValue = ckycrCommonMgmtRemote.getCkycrLimit();
            List list = em.createNativeQuery("select customerid_ckycr_no,date_format(dob,'%Y%m%d') from "
                    + "ckycr_request_detail where mode='DOWNLOAD' and status ='ENTERED' and "
                    + "request_date between '" + fromDate + "' and '" + toDate + "' and branch_code = '" + branchCode + "' order "
                    + "by request_date limit " + limitValue + "").getResultList();
            if (list.isEmpty()) {
                throw new Exception("There is no data to upload the download request");
            }
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                CKYCRRequestPojo pojo = new CKYCRRequestPojo();
                pojo.setSno(i + 1);
                pojo.setCustomerIdOrCKYCRNo(vtr.get(0).toString());
                pojo.setDateOfBirth(vtr.get(1).toString());
                pojo.setSelected(true);
                manualDownloadList.add(pojo);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return manualDownloadList;
    }

    public String generateManualDownloadFile(List<CKYCRRequestPojo> requestList, String alphaCode) throws Exception {
        System.out.println("In generateManualDownloadFile() method");
        try {
            String cbsDownloadRequestLocation = props.getProperty("cbsDownloadRequestLocation").trim();
            String cbsDownloadRequestLocationDir = cbsDownloadRequestLocation.substring(0, cbsDownloadRequestLocation.lastIndexOf("/") + 1);

            generateDownloadRequest(cbsDownloadRequestLocationDir, alphaCode, requestList);

//            SftpUtil util = SftpUtilFactory.getSftpUtil();
//            SftpSession session = getSftpSession(props.getProperty("ckycrSftpHost").trim(),
//                    props.getProperty("ckycrSftpUser").trim(),
//                    props.getProperty("ckycrSftpPassword").trim());

//            util.put(session, cbsDownloadRequestLocation, props.getProperty("ckycrSftpDownloadRequestLocation").trim());
            createBackupAndThenRemoveFile(cbsDownloadRequestLocationDir, props.getProperty("cbsDownloadRequestBackupLocation").trim());
//            util.disconnect(session);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return "true";
    }

    public List<CKYCRDownloadDetail30> fetch30Records(String customerId) throws Exception {
        List<CKYCRDownloadDetail30> identityList = new ArrayList<CKYCRDownloadDetail30>();
        List list = em.createNativeQuery("select identificationtype,ifnull(identityno,'') as identityno,"
                + "ifnull(idexpirydate,'') as idexpirydate from cbs_cust_identity_details where "
                + "customerid='" + customerId + "'").getResultList();
        for (int i = 0; i < list.size(); i++) {
            CKYCRDownloadDetail30 obj = new CKYCRDownloadDetail30();
            Vector ele = (Vector) list.get(i);
            obj.setIdentificationtype(ele.get(0).toString().trim());
            obj.setIdentityNumber(ele.get(1).toString().trim());
            obj.setExpiryDate(ele.get(2).toString().trim());

            identityList.add(obj);
        }
        return identityList;
    }

    public List<CKYCRDownloadDetail60> fetch60Records(String customerId) throws Exception {
        List<CKYCRDownloadDetail60> localAddressList = new ArrayList<CKYCRDownloadDetail60>();
        List list = em.createNativeQuery("select addresstype,ifnull(localaddressline1,'') as localaddressline1,"
                + "ifnull(localaddressline2,'') as localaddressline2,ifnull(localaddressline3,'') as "
                + "localaddressline3,ifnull(localaddresscityvillage,'') as localaddresscityvillage,"
                + "ifnull(localaddressdistrict,'') as localaddressdistrict,ifnull(localaddresspincode,'') as "
                + "localaddresspincode,ifnull(localaddressstate,'') as localaddressstate,ifnull(localaddresscountry,'') as "
                + "localaddresscountry,ifnull(proofofadd,'') as proofofadd,ifnull(resitelstdcode,'') as "
                + "resitelstdcode,ifnull(resitelno,'') as resitelno,ifnull(officetelstdcode,'') as officetelstdcode,"
                + "ifnull(officetelno,'') as officetelno,ifnull(mobisdcode,'') as mobisdcode,ifnull(mobileno,'') as "
                + "mobileno,ifnull(faxstdcode,'') as faxstdcode,ifnull(faxno,'') as faxno,ifnull(emailid,'') as emailid,"
                + "ifnull(dateofdeclaration,'') as dateofdeclaration,ifnull(placeofdeclaration,'') as placeofdeclaration "
                + "from cbs_cust_additional_address_details where customerid='" + customerId + "'").getResultList();
        for (int i = 0; i < list.size(); i++) {
            CKYCRDownloadDetail60 obj = new CKYCRDownloadDetail60();
            Vector ele = (Vector) list.get(i);
            obj.setAddressType(ele.get(0).toString().trim());
            obj.setLocalAddressLine1(ele.get(1).toString().trim());
            obj.setLocalAddressLine2(ele.get(2).toString().trim());
            obj.setLocalAddressLine3(ele.get(3).toString().trim());
            obj.setLocalAddressCityVillage(ele.get(4).toString().trim());
            obj.setLocalAddressDistrict(ele.get(5).toString().trim());
            obj.setLocalAddressPINCode(ele.get(6).toString().trim());
            obj.setLocalAddressState(ele.get(7).toString().trim());
            obj.setLocalAddressCountry(ele.get(8).toString().trim());
            obj.setProofofAdd(ele.get(9).toString().trim());
            obj.setResiTelSTDCode(ele.get(10).toString().trim());
            obj.setResiTelNo(ele.get(11).toString().trim());
            obj.setOfficeTelSTDCode(ele.get(12).toString().trim());
            obj.setOfficeTelNo(ele.get(13).toString().trim());
            obj.setMobISDCode(ele.get(14).toString().trim());
            obj.setMobileNo(ele.get(15).toString().trim());
            obj.setFaxSTDCode(ele.get(16).toString().trim());
            obj.setFaxNo(ele.get(17).toString().trim());
            obj.setEmailID(ele.get(18).toString().trim());
            obj.setDateofDeclaration(ele.get(19).toString().trim());
            obj.setPlaceofDeclaration(ele.get(20).toString().trim());

            localAddressList.add(obj);
        }
        return localAddressList;
    }

//    public String isMinor(String dob, int minorAge) throws Exception { //dob - yyyyMMdd
//        String isMinor = "";
//        int yearDiff = CbsUtil.yearDiff(ymd.parse(dob), ymd.parse(ymd.format(new Date())));
//        if (yearDiff <= minorAge) {
//            isMinor = "MINOR";
//        }
//        return isMinor;
//    }
    public String isMinor(String dob, int minorAge) {  //dob - yyyyMMdd
        String isMinor = "";
        int d1 = Integer.parseInt(dob);
        int d2 = Integer.parseInt(ymd.format(new Date()));
        int age = (d2 - d1) / 10000;
        System.out.println("Age Is>>>" + age);
        if (age < minorAge) {
            isMinor = "MINOR";
        }
        return isMinor;
    }

    public List<String> relatedDetail(String customerId) throws Exception {
        List<String> minorList = Arrays.asList(new String[7]);
        minorList.set(0, ""); //Type Of Relationship
        minorList.set(1, ""); //Addition/Deletion Flag
        minorList.set(2, ""); //KycNo Of Related Person
        minorList.set(3, ""); //Related Person Prefix
        minorList.set(4, ""); //Related Person First Name
        minorList.set(5, ""); //Related Person Middle Name
        minorList.set(6, ""); //Related Person Last Name

        List minorDataList = em.createNativeQuery("select a.customerid,a.related_custid,a.relation_type,a.del_flag,"
                + "ifnull(p.title,'') as title,ifnull(p.custname,'') as custname,ifnull(p.middle_name,'') as middle_name,"
                + "ifnull(p.last_name,'') as last_name,ifnull(p.ckycno,'') as ckyc_no from(select r.customerid,"
                + "ifnull(r.person_customerid,'') as related_custid,ifnull(r.relationship_code,'') as relation_type,"
                + "ifnull(r.del_flag,'') as del_flag from cbs_customer_master_detail c,cbs_related_persons_details r "
                + "where c.customerid=r.customerid and r.customerid='" + customerId + "') a,cbs_customer_master_detail p "
                + "where a.related_custid=p.customerid").getResultList();
        if (minorDataList.size() == 1) {
            for (int i = 0; i < minorDataList.size(); i++) {
                Vector ele = (Vector) minorDataList.get(i);
                minorList.set(0, ele.get(2).toString().trim());
                minorList.set(1, ele.get(3).toString().trim());
                minorList.set(2, ele.get(8).toString().trim());
                minorList.set(3, ele.get(4).toString().trim());
                minorList.set(4, ele.get(5).toString().trim());
                minorList.set(5, ele.get(6).toString().trim());
                minorList.set(6, ele.get(7).toString().trim());
            }
        }
        return minorList;
    }

    //remoteLocation - It can be absolute file path or absolute directory path
    public static String createConnectionString(String hostName, String username, String password,
            String remoteLocation) throws Exception {
        String userInfo = username + ":" + password;
        System.out.println("UserId-->" + username + ":::::Password-->" + password);
        URI uri = new URI("sftp", userInfo, hostName, 22, remoteLocation, null, null);
        return uri.toString();
    }

    //Here Paths are only the directory location.
    public void upload(String fileType, String localFileDir, String remoteFileDir, String backupDirectory) {
        StandardFileSystemManager manager = new StandardFileSystemManager();
        try {
            File localDir = new File(localFileDir);
            FileFilter fileFilter = new WildcardFileFilter(fileType);
            File[] localFiles = localDir.listFiles(fileFilter);
            manager.init();
            for (int i = 0; i < localFiles.length; i++) {
                String onlyFilename = localFiles[i].getName();
                //Create remote connection string
                String connectionStr = createConnectionString(props.getProperty("ckycrSftpHost").trim(),
                        props.getProperty("ckycrSftpUser").trim(), props.getProperty("ckycrSftpPassword").trim(), remoteFileDir + "/" + onlyFilename);

                //Setting the options
                FileSystemOptions options = createDefaultOptions();
                //Create local file object
                FileObject localFile = manager.resolveFile(localFiles[i].getAbsolutePath());
                //Create remote file object
                FileObject remoteFile = manager.resolveFile(connectionStr, options);
                //Copy local file to sftp server
                if (localFile.exists()) {
                    System.out.println("Putting Upload File-->" + localFile.getName());
                    remoteFile.copyFrom(localFile, Selectors.SELECT_SELF);
                    individualFileBackupAndRemoval(new File(localFileDir + "/" + onlyFilename), backupDirectory);
                }
                System.out.println("File upload success");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            manager.close();
        }
    }

    public FileSystemOptions createDefaultOptions() throws Exception {
        FileSystemOptions options = null;
        try {
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
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return options;
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

    //Here Paths are only the directory location.
    public void download(String localFileDir, String remoteFileDir, String filePattern) {
        StandardFileSystemManager manager = new StandardFileSystemManager();
        try {
            manager.init();
            //Create remote connection string
            String connectionStr = createConnectionString(props.getProperty("ckycrSftpHost").trim(),
                    props.getProperty("ckycrSftpUser").trim(), props.getProperty("ckycrSftpPassword").trim(), remoteFileDir);
            //Setting the options
            FileSystemOptions options = createDefaultOptions();
            //Fetching the files from remote location
            FileObject[] remoteFiles = manager.resolveFile(connectionStr, options).findFiles(new FileFilterSelector(new CkycrFileFilter(filePattern)));
            for (FileObject fileObject : remoteFiles) {
                String fileToDownload = fileObject.getName().toString().substring(fileObject.getName().toString().lastIndexOf("/") + 1); //Actual file name only without path
                FileObject localFile = manager.resolveFile(localFileDir + "/" + fileToDownload);

                localFile.copyFrom(fileObject, Selectors.SELECT_SELF);
            }
            System.out.println("File Download Success.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            manager.close();
        }
    }
    
    public String updateModuleConfigProperty(String key, String value) throws ApplicationException{
         try {
            //modifies existing or adds new property
            props.setProperty(key, value);
            
            //save modified property file
            FileOutputStream output = new FileOutputStream(propFileName);
            
            props.store(output, "Updated Key " + key);
            output.close();
            
            return "True";
        } catch (IOException ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }
}
