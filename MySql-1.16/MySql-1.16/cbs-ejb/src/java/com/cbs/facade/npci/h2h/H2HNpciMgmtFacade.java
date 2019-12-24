/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.npci.h2h;

import com.cbs.constant.BusinessConstant;
import com.cbs.dto.NpciInwardDto;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.other.NpciMgmtFacadeRemote;
import com.cbs.facade.other.OtherNpciMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ParseFileUtil;
import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.media.jai.JAI;
import net.sf.opensftp.SftpException;
import net.sf.opensftp.SftpSession;
import net.sf.opensftp.SftpUtil;
import net.sf.opensftp.SftpUtilFactory;
import com.cbs.dto.other.NpciOacDto;
import com.cbs.entity.sms.MbSmsSenderBankDetail;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import static com.cbs.facade.neftrtgs.H2HMgmtFacade.createConnectionString;
import com.cbs.facade.neftrtgs.UploadNeftRtgsMgmtFacadeRemote;
import com.cbs.facade.other.NpciMandateFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.SignUtil;
import java.io.FileFilter;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.vfs2.FileFilterSelector;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

@Stateless(mappedName = "H2HNpciMgmtFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class H2HNpciMgmtFacade implements H2HNpciMgmtFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private NpciMgmtFacadeRemote npciMgmtRemote = null;
    @EJB
    private CommonReportMethodsRemote commonReport;
    @EJB
    private NpciMgmtFacadeRemote npciFacade = null;
    @EJB
    private OtherNpciMgmtFacadeRemote otherNpciRemote = null;
    @EJB
    private FtsPostingMgmtFacadeRemote FtsPostMgmtRemote = null;
    @EJB
    private UploadNeftRtgsMgmtFacadeRemote remote;
    @EJB
    private NpciMandateFacadeRemote npciMandteRemote = null;
    private Properties props = null;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private String propFileName = "/opt/conf/wslocation.properties";

    @PostConstruct
    private void loadWebServicesConfig() {
        try {
            props = new Properties();
            props.load(new FileReader(propFileName));
        } catch (Exception ex) {
            System.out.println("Problem In Bean Initialization And Loading The "
                    + "WSLOCATION Properties File. " + ex.getMessage());
        }
    }

    @Override
    public void npciH2HProcess() {
        System.out.println("NPCI H2H:In npciH2HProcess method()");
        try {
            if (!isCentralDayBegin()) {
                throw new Exception("Central daybegin has not been done till time."); //Check for message either it throws or print
            }
            //Prerequisite values
            String cbsIwFileLocation = props.getProperty("cbs.iw.location").trim();
            String cbsResFileLocation = props.getProperty("cbs.res.location").trim();
            String hoBranchCode = commonReport.getBrncodeByAlphacode("HO");
            String cbsUserRegisteredOnNPCI = props.getProperty("cbs.npci.user");
            String currDate = dmy.format(new Date());
            //Downloading all coming files from NPCI
            try {
                downloadNPCIIwFiles(); //What will we do for old downloaded files.
            } catch (Exception e) {
                System.out.println("NPCI H2H:Problem in downloading all files coming from NPCI:" + e.getMessage());
            }
            Thread.sleep(5000);
            //Mapper Processing
            try {
                mapperProcessing();
            } catch (Exception e) {
                System.out.println("NPCI H2H:Problem in mapperProcessing() method......" + e.getMessage());
            }
            Thread.sleep(5000);
            //ACH Processing
            try {
                achNormalProcessing(cbsIwFileLocation, hoBranchCode, cbsUserRegisteredOnNPCI, currDate);
            } catch (Exception e) {
                System.out.println("NPCI H2H:Problem in achCreditProcessing() method......" + e.getMessage());
            }
            Thread.sleep(5000);
            //APB-CR Processing
            try {
                apbsProcessing(cbsIwFileLocation, hoBranchCode, cbsUserRegisteredOnNPCI, currDate);
            } catch (Exception e) {
                System.out.println("NPCI H2H:Problem in apbcrProcessing() method......" + e.getMessage());
            }
            Thread.sleep(5000);
            //ACH-CR ECS Processing
            try {
                achcrECSProcessing(cbsIwFileLocation, hoBranchCode, cbsUserRegisteredOnNPCI, currDate);
            } catch (Exception e) {
                System.out.println("NPCI H2H:Problem in achcrECSProcessing() method......" + e.getMessage());
            }
            Thread.sleep(5000);
            //ACH-DR, OAC And  ECS-DR Processing
            try {
                ecsDRAndAchDRProcessing(cbsIwFileLocation, hoBranchCode, cbsUserRegisteredOnNPCI, currDate);
            } catch (Exception e) {
                System.out.println("NPCI H2H:Problem in ecsDRAndAchDRProcessing() method......" + e.getMessage());
            }
            Thread.sleep(5000);
            //MMS Processing
            try {
//                mmsFileProcessing(cbsIwFileLocation, hoBranchCode, cbsUserRegisteredOnNPCI, ymd.format(new Date()));
            } catch (Exception e) {
                System.out.println("NPCI H2H:Problem in ecsDRAndAchDRProcessing() method......" + e.getMessage());
            }
            Thread.sleep(5000);
            //ESIGN Processing 
            try {
//                esignFileProcessing(cbsIwFileLocation, hoBranchCode, cbsUserRegisteredOnNPCI, ymd.format(new Date()));
            } catch (Exception e) {
                System.out.println("NPCI H2H : Problem in esignFileProcessing() method...." + e.getMessage());
            }
            Thread.sleep(5000);
            //File genaration of verified ECS file
            try {
                fileGenerationOfVerifiedEntries(hoBranchCode, cbsUserRegisteredOnNPCI, currDate);
            } catch (Exception e) {
                System.out.println("NPCI H2H:Problem in ecsDRAndAchDRProcessing() method......" + e.getMessage());
            }
            Thread.sleep(5000);
            try {
                avFileProcessing(cbsIwFileLocation, hoBranchCode, cbsUserRegisteredOnNPCI, currDate);
            } catch (Exception e) {
                System.out.println("NPCI H2H : Problem in avFileProcesing() method ....." + e.getMessage());
            }
            //Downloading of response file
            Thread.sleep(5000);
            try {
                downloadResponse();
            } catch (Exception e) {
                System.out.println("NPCI H2H : Problem in downloadResponse() method ....." + e.getMessage());
            }
            //Process Response Files
            Thread.sleep(5000);
            try {
                processResponse(cbsResFileLocation, hoBranchCode, cbsUserRegisteredOnNPCI, currDate);
            } catch (Exception e) {
                System.out.println("NPCI H2H : Problem in avFileProcesing() method ....." + e.getMessage());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("NPCI H2H:Problem in processResponse() method......" + ex.getMessage());
        }
    }

    public void downloadNPCIIwFiles() throws Exception {
        download(props.getProperty("npciSftpHost").trim(), props.getProperty("npciSftpUser").trim(),
                props.getProperty("npciSftpPassword").trim(), props.getProperty("cbs.iw.location").trim(),
                props.getProperty("npciSftpFileDownloadLocation").trim());
    }

    public void downloadResponse() throws Exception {
        download(props.getProperty("npciSftpHost").trim(), props.getProperty("npciSftpUser").trim(),
                props.getProperty("npciSftpPassword").trim(), props.getProperty("cbs.res.location").trim(),
                props.getProperty("npciSftpResponseFileDownloadLocation").trim());
    }

    public void download(String hostName, String username, String password,
            String localFilePath, String remoteFilePath) {
        StandardFileSystemManager manager = new StandardFileSystemManager();
        try {
            //All downloaded dates
            List<String> downloadedDates = getDownloadedDates();

            manager.init();
            //Create remote connection string
            String connectionStr = createConnectionString(hostName, username, password, remoteFilePath);
            //Setting the options
            FileSystemOptions options = createDefaultOptions();
            //Fetching the files from remote location
            FileObject[] remoteFile = manager.resolveFile(connectionStr, options).findFiles(new FileFilterSelector(new NpciFileFilter()));
            for (FileObject fileObject : remoteFile) {
                String fileObjectDt = ymd.format(new Date(fileObject.getContent().getLastModifiedTime()));
                if (downloadedDates.contains(fileObjectDt)) {
                    String fileToDownload = fileObject.getName().toString().substring(fileObject.getName().toString().lastIndexOf("/") + 1);
                    FileObject localFile = manager.resolveFile(localFilePath + "/" + fileToDownload);
                    localFile.copyFrom(fileObject, Selectors.SELECT_SELF);
                }
            }
            System.out.println("File Download Success.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            manager.close();
        }
    }

    public void upload(String hostname, String username, String password, String localFileDir, String remoteFileDir) throws Exception {
        StandardFileSystemManager manager = new StandardFileSystemManager();
        try {
            File localDir = new File(localFileDir);
            File[] localFiles = localDir.listFiles();
            manager.init();
            for (int i = 0; i < localFiles.length; i++) {
                String onlyFilename = localFiles[i].getName();
                //Create remote connection string
                String connectionStr = createConnectionString(hostname.trim(), username.trim(), password.trim(),
                        remoteFileDir + "/" + onlyFilename);
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
                }
                System.out.println("File upload success");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            manager.close();
        }
    }

    //remoteLocation - It can be absolute file path or absolute directory path
    public String createConnectionString(String hostName, String username, String password,
            String remoteLocation) throws Exception {
        String userInfo = username + ":" + password;
        //     URI uri = new URI("sftp", userInfo, hostName, 22, remoteLocation, null, null);
        URI uri = new URI("sftp", userInfo, hostName, 9293, remoteLocation, null, null);
        return uri.toString();
    }

    public FileSystemOptions createDefaultOptions() throws Exception {
        FileSystemOptions options = null;
        try {
            List<MbSmsSenderBankDetail> bankList = remote.getBankCode();
            if (!bankList.isEmpty()) {
                MbSmsSenderBankDetail bankEntity = bankList.get(0);
                String bankCode = bankEntity.getBankCode();
                //Create options for sftp
                options = new FileSystemOptions();
                //SSH key
                SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(options, "no");
                //Set root directory to user home
                SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(options, false);
                //Timeout
                SftpFileSystemConfigBuilder.getInstance().setTimeout(options, 60000);
                if (bankCode.equalsIgnoreCase("NABU")) { //Add Or Change Here For Banks,  NABU
                    SftpFileSystemConfigBuilder.getInstance().setIdentities(options, new File[]{new File("/root/.ssh/nawadwip_npci_h2h")});
                } else { //for all other banks //Badaun 
                    SftpFileSystemConfigBuilder.getInstance().setIdentities(options, new File[]{new File("/root/.ssh/priv_npci_h2h")});
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return options;
    }

    private void mapperProcessing() throws Exception {
        String result = npciMgmtRemote.generateNpciFiles(dmy.format(new Date()), "90", props.getProperty("cbs.npci.user"),
                dmy.format(new Date()), "H2H", props.getProperty("cbs.ow.location"));
        if (!result.equalsIgnoreCase("true")) {
            throw new Exception("Problem In H2H Mapper File Generation.");
        }
    }

    private void achNormalProcessing(String inwardDir, String brnCode, String user, String currDate) throws Exception {
        String result = "";
//        UserTransaction ut = context.getUserTransaction();
        FileFilter fileFilter = new WildcardFileFilter("ACH-CR*");
        File inwardFiles = new File(inwardDir);
        for (File file : inwardFiles.listFiles(fileFilter)) {// get all files from iw folder and process on it.
            String uploadedFileName = file.getName();
            File inwFile = new File(file.getCanonicalPath());
            File inwbkpFile = new File(props.getProperty("cbs.iw.bkp.location").trim() + uploadedFileName);

            List<NpciInwardDto> pojoList;
            if (uploadedFileName.toLowerCase().contains("ach-cr")) {
                if (!(uploadedFileName.toLowerCase().contains("ecs")
                        || uploadedFileName.toLowerCase().contains("cdr")
                        || uploadedFileName.toLowerCase().contains("cwr")
                        || uploadedFileName.toLowerCase().contains("cda")
                        || uploadedFileName.toLowerCase().contains("cwa"))) {
                    if (FtsPostMgmtRemote.getCodeForReportName("DG-SIGN-UTILITY") == 1) {
                        new SignUtil().parseSignedFile(file.getCanonicalPath(), true, file.getCanonicalPath(), false);
                    }

                    String fileSeqNo = uploadedFileName.split("-")[4].trim();
                    pojoList = ParseFileUtil.parseAchInwardTxtFile(inwFile, fileSeqNo); //For ACH
                    if (!pojoList.isEmpty()) {
                        String fileName = uploadedFileName.substring(0, uploadedFileName.indexOf("."));
                        try {
                            result = npciMgmtRemote.newNpciAchInwardUpload(pojoList, brnCode, user, currDate, fileName, "H2H"); //For ACH Inward
                        } catch (Exception ex) {
                            if (ex.getMessage().equalsIgnoreCase("This file has been already uploaded.")) {
                                //now move this folder to in backup folder
                                if (file.renameTo(new File(inwbkpFile.getCanonicalPath()))) {
                                    System.out.println("ACH File is moved successful!");
                                } else {
                                    System.out.println("ACH File is failed to move!");
                                }
                            } else {
                                throw new Exception(ex.getMessage());
                            }
                        }
                        if (result.equalsIgnoreCase("true")) {
                            //now move this folder to in backup folder
                            if (file.renameTo(new File(inwbkpFile.getCanonicalPath()))) {
                                System.out.println("ACH File is moved successful!");
                            } else {
                                System.out.println("ACH File is failed to move!");
                            }
                        }
                    }
                }
            }
        }

        List dateList = npciFacade.getSettlementDtForUnverifiedEntriesNPCIInward("ACH");
        if (!dateList.isEmpty()) {
            for (Object dateObj : dateList) {
                String settlmntDate = ((Vector) dateObj).get(0).toString();
                List list = npciFacade.getH2HSeqNoForSettlementDate(settlmntDate, "ACH");
                for (Object obj : list) {
                    String seqNo = ((Vector) obj).get(0).toString();
                    //currDate is SETTLEMENT_DATE in cbs_npci_inward as well as current Date
                    result = npciFacade.generateAchReturnFiles(settlmntDate, brnCode, user, currDate, Double.parseDouble(seqNo),
                            "H2H", props.getProperty("cbs.ow.location"));
                    if (!result.equalsIgnoreCase("true")) {
                        throw new Exception("Problem In ACH Cr Encryption And Uploading The file.");
                    }
                }
            }
        }
    }

    private void apbsProcessing(String inwardDir, String brnCode, String user, String currDate) throws Exception {
//        UserTransaction ut = context.getUserTransaction();
        String result = "";
        FileFilter fileFilter = new WildcardFileFilter("APB-CR*");
        File inwardFiles = new File(inwardDir);
        for (File file : inwardFiles.listFiles(fileFilter)) {// get all files from iw folder and procee on it
            String uploadedFileName = file.getName();
            File inwFile = new File(file.getCanonicalPath());
            File inwbkpFile = new File(props.getProperty("cbs.iw.bkp.location").trim() + uploadedFileName);
            List<NpciInwardDto> pojoList;
            if (uploadedFileName.toLowerCase().contains("apb-cr")) {
                //Convert into text file
                if (FtsPostMgmtRemote.getCodeForReportName("DG-SIGN-UTILITY") == 1) {
                    new SignUtil().parseSignedFile(file.getCanonicalPath(), true, file.getCanonicalPath(), false);
                }
                pojoList = ParseFileUtil.parseNpciInwardTxtFile(inwFile);//APBS Inward
                String fileName = uploadedFileName.substring(0, uploadedFileName.indexOf("."));
                try {
                    result = npciFacade.npciInwardUpload(pojoList, brnCode, user, currDate, fileName, "H2H");
                } catch (Exception ex) {
                    System.out.println("APBS File is failed to upload:--> " + ex.getMessage());
                    if (ex.getMessage().equalsIgnoreCase("This file has been already uploaded.")) {
                        //now move this folder to in backup folder
                        if (file.renameTo(new File(inwbkpFile.getCanonicalPath()))) {
                            System.out.println("APBS File is moved successful!");
                        } else {
                            System.out.println("APBS File is failed to move!");
                        }
                    }
                }
                if (result.equalsIgnoreCase("true")) {
                    //now move this folder to in backup folder
                    if (file.renameTo(new File(inwbkpFile.getCanonicalPath()))) {
                        System.out.println("APBS File is moved successful!");
                    } else {
                        System.out.println("APBS File is failed to move!");
                    }
                }
            }
        }
        List dateList = npciFacade.getSettlementDtForUnverifiedEntriesNPCIInward("APB");
        if (!dateList.isEmpty()) {
            for (Object dateObj : dateList) {
                String settlmntDate = ((Vector) dateObj).get(0).toString();
                List list = npciFacade.getH2HSeqNoForSettlementDate(settlmntDate, "APB");
                for (Object obj : list) {
                    String seqNo = ((Vector) obj).get(0).toString();
                    //curDate is SETTLEMENT_DATE in cbs_npci_inward as well as current Date
                    result = npciFacade.generateNpciReturnFiles(settlmntDate, brnCode, user, currDate,
                            Double.parseDouble(seqNo), "H2H", props.getProperty("cbs.ow.location"));
                    if (!result.equalsIgnoreCase("true")) {
                        throw new Exception("Problem In APB Cr Encryption And Uploading The file.");
                    }
                }
            }
        }
    }

    private void avFileProcessing(String avFileDir, String brnCode, String user, String currDate) throws Exception {
        String result = "";
        FileFilter fileFilter = new WildcardFileFilter("AV*");
        File avFile = new File(avFileDir);
        for (File file : avFile.listFiles(fileFilter)) {
            String uploadFileName = file.getName();
            String fileSeqNo = uploadFileName.split("-")[5].trim();
            File inwFile = new File(file.getCanonicalPath());
            File inwbkpFile = new File(props.getProperty("cbs.iw.bkp.location").trim() + uploadFileName);
            List<NpciInwardDto> pojoList;
            if (uploadFileName.toLowerCase().contains("av")) {
                if (FtsPostMgmtRemote.getCodeForReportName("DG-SIGN-UTILITY") == 1) {
                    new SignUtil().parseSignedFile(file.getCanonicalPath(), true, file.getCanonicalPath(), false);
                }
                pojoList = ParseFileUtil.parseNpciNonAadhaarInwardTxtFile(inwFile, fileSeqNo);
                String fileName = uploadFileName.substring(0, uploadFileName.indexOf("."));
                try {
                    result = npciFacade.npciNonAadhaarInwardUpload(pojoList, brnCode, user, currDate, "H2H", uploadFileName);
                } catch (Exception ex) {
                    System.out.println("AV File is failed to upload:--> " + ex.getMessage());
                    if (ex.getMessage().equalsIgnoreCase("This file has been already uploaded.")) {
                        //now move this folder to in backup folder
                        if (file.renameTo(new File(inwbkpFile.getCanonicalPath()))) {
                            System.out.println("AV File is moved successful!");
                        } else {
                            System.out.println("AV File is failed to move!");
                        }
                    }
                }
                if (result.equalsIgnoreCase("true")) {
                    //now move this folder to in backup folder
                    if (file.renameTo(new File(inwbkpFile.getCanonicalPath()))) {
                        System.out.println("AV File is moved successful!");
                    } else {
                        System.out.println("AV File is failed to move!");
                    }
                }
            }
        }
        String lpgType = "";
        List dateList = npciFacade.getFileComingDtForUnverifiedEntriesNpciNonAadharInward();
        if (!dateList.isEmpty()) {
            for (Object dateObj : dateList) {
                String comingDate = ((Vector) dateObj).get(0).toString();
                String fileSeqNo = ((Vector) dateObj).get(1).toString();
                List lpgTypeList = npciFacade.getLpgTypeForH2hAvFileGeneration(ymd.format(dmy.parse(comingDate)), fileSeqNo);
                for (int i = 0; i < lpgTypeList.size(); i++) {
                    Vector vec = (Vector) lpgTypeList.get(i);
                    lpgType = vec.get(0).toString().trim();
                }
                result = npciFacade.generateNonAadhaarReturn(lpgType, comingDate, brnCode, user,
                        currDate, fileSeqNo, "H2H", props.getProperty("cbs.ow.location"));
            }
        }
    }

    private void achcrECSProcessing(String inwardDir, String brnCode, String user, String currDate) throws Exception {
        String result = "";
        FileFilter fileFilter = new WildcardFileFilter("ACH-CR*");
        File inwardFiles = new File(inwardDir);
        try {
            for (File file : inwardFiles.listFiles(fileFilter)) {// get all files from iw folder and procee on it
                String uploadedFileName = file.getName();
                File inwFile = new File(file.getCanonicalPath());
                File inwbkpFile = new File(props.getProperty("cbs.iw.bkp.location").trim() + uploadedFileName);
                List<NpciInwardDto> pojoList;
                if (uploadedFileName.toLowerCase().contains("ach-cr")) {  //ACH Inward
                    if (uploadedFileName.toLowerCase().contains("ecs")) {
                        try {
                            //Convert into text file
                            if (FtsPostMgmtRemote.getCodeForReportName("DG-SIGN-UTILITY") == 1) {
                                new SignUtil().parseSignedFile(file.getCanonicalPath(), true, file.getCanonicalPath(), false);
                            }

                            String fileSeqNo = uploadedFileName.split("-")[4].trim();
                            pojoList = ParseFileUtil.parseEcsNewTxtFile(inwFile, fileSeqNo);
                            result = npciFacade.npciCECSCreditInwardUpload(pojoList, brnCode, currDate, user, "H2H");
                        } catch (Exception ex) {
                            System.out.println("ACH-CR ECS File is failed to upload:--> " + ex.getMessage());
                            if (ex.getMessage().equalsIgnoreCase("This file has been already uploaded.")) {
                                //now move this folder to in backup folder
                                if (file.renameTo(new File(inwbkpFile.getCanonicalPath()))) {
                                    System.out.println("ACH-CR ECS File is moved successful!");
                                } else {
                                    System.out.println("ACH-CR ECS File is failed to move!");
                                }
                            }
                        }
                        if (result.equalsIgnoreCase("true")) {
                            //now move this folder to in backup folder
                            if (file.renameTo(new File(inwbkpFile.getCanonicalPath()))) {
                                System.out.println("ACH-CR ECS File is moved successful!");
                            } else {
                                System.out.println("ACH-CR ECS File is failed to move!");
                            }
                            System.out.println("ACH-CR ECS File successfully uploaded.");
                        }
                        List dateList = npciFacade.getSettlementDtForUnverifiedEntriesNPCIInward("ECS");
                        if (!dateList.isEmpty()) {
                            for (Object dateObj : dateList) {
                                String settlmntDate = ((Vector) dateObj).get(0).toString();
                                List<NpciOacDto> listAcc = otherNpciRemote.getDataForEcsCrOnInComingDate(settlmntDate, "ECS");
                                for (NpciOacDto obj : listAcc) {
                                    //currDate is SETTLEMENT_DATE in cbs_npci_inward as well as current Date
                                    try {
                                        result = otherNpciRemote.processCecsInwCredit(obj, user, currDate, brnCode, "ECS",
                                                ymd.format(dmy.parse(settlmntDate)), obj.getAchItemSeqNo(), obj.getOldAcno(), "", brnCode, "", "H2H");
                                    } catch (Exception ex) {
                                        System.out.println(obj.getOldAcno() + "===> " + ex.getMessage());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            List dateList = npciFacade.getSettlementDtForUnverifiedEntriesNPCIInward("ECS");
            if (!dateList.isEmpty()) {
                for (Object dateObj : dateList) {
                    String settlmntDate = ((Vector) dateObj).get(0).toString();
                    List list = otherNpciRemote.findH2HAllFileSeqNoForCreditInward(ymd.format(dmy.parse(settlmntDate)), "ECS");
                    for (Object obj : list) {
                        String seqNo = ((Vector) obj).get(0).toString();
                        //fileGenerationDt is SETTLEMENT_DATE in cbs_npci_inward.
                        try {
                            result = otherNpciRemote.generateECS306ReturnFiles(settlmntDate, brnCode, user, currDate, seqNo, "H2H", props.getProperty("cbs.ow.location"));
                            if (result.equals("true")) {
                                System.out.println("ACH-CR ECS Return file for " + seqNo + " has been generated successfully.");
                            }
                        } catch (Exception ex) {
                            System.out.println("ACH-CR ECS Return file " + seqNo + ":=>" + ex.getMessage());
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void ecsDRAndAchDRProcessing(String inwardDir, String brnCode, String user, String currDate) throws Exception {
        List<String> fileTypes = new ArrayList<>();
        fileTypes.add("ACH-DR*"); //You can add more file type here also for ECS-DR*.

        FileFilter fileFilter = new WildcardFileFilter(fileTypes);
        File inwardFiles = new File(inwardDir);
        for (File file : inwardFiles.listFiles(fileFilter)) {//Get all files from iw folder and procee on it
            String uploadedFileName = file.getName();
            String fileType = uploadedFileName.split("-")[0].trim() + "-DR";
            File inwFile = new File(file.getCanonicalPath());
            File inwbkpFile = new File(props.getProperty("cbs.iw.bkp.location").trim() + uploadedFileName);
            List<NpciInwardDto> pojoList;
            String result = "";
            try {
                if (uploadedFileName.toLowerCase().contains("ecs-dr") //ecs dr
                        && uploadedFileName.toLowerCase().contains("inw.txt")) {
                    //Convert into text file
                    if (FtsPostMgmtRemote.getCodeForReportName("DG-SIGN-UTILITY") == 1) {
                        new SignUtil().parseSignedFile(file.getCanonicalPath(), true, file.getCanonicalPath(), false);
                    }
                    String fileNameDt = uploadedFileName.split("-")[3].trim();
                    String fileSeqNo = uploadedFileName.split("-")[4].trim();
                    pojoList = ParseFileUtil.parseEcsDrTxtFile(inwFile, fileSeqNo, fileNameDt);
                    result = npciFacade.npciEcsDrUpload(pojoList, brnCode, currDate, user, "ECS-DR", "H2H", uploadedFileName);
                } else if (uploadedFileName.toLowerCase().contains("ach-dr") //ach dr 306 format
                        && uploadedFileName.toLowerCase().contains("inw.txt")) {
                    //Convert into text file
                    if (FtsPostMgmtRemote.getCodeForReportName("DG-SIGN-UTILITY") == 1) {
                        new SignUtil().parseSignedFile(file.getCanonicalPath(), true, file.getCanonicalPath(), false);
                    }
                    //Parsing of ACH DR
                    String fileSeqNo = uploadedFileName.split("-")[4].trim();
                    pojoList = ParseFileUtil.parseAchDrNewTxtFile(inwFile, fileSeqNo);
                    result = npciFacade.npciAchDr306Upload(pojoList, brnCode, currDate, user, "ACH-DR", "H2H", uploadedFileName);
                } else if (uploadedFileName.toLowerCase().contains("oac") //oac incoming
                        && uploadedFileName.toLowerCase().contains("inp.txt")) {
                    //Convert into text file
                    if (FtsPostMgmtRemote.getCodeForReportName("DG-SIGN-UTILITY") == 1) {
                        new SignUtil().parseSignedFile(file.getCanonicalPath(), true, file.getCanonicalPath(), false);
                    }
                    String fileSeqNo = uploadedFileName.split("-")[5].trim();
                    pojoList = ParseFileUtil.parseOacTxtFile(inwFile, fileSeqNo);
                    result = npciFacade.npciOacUpload(pojoList, brnCode, currDate, user, "NPCI-OAC", "H2H");
                }
            } catch (Exception ex) {
                System.out.println(fileType + " File is failed to upload:--> " + ex.getMessage());
                if (ex.getMessage().equalsIgnoreCase("This file has been already uploaded.")) {
                    //now move this folder to in backup folder
                    if (file.renameTo(new File(inwbkpFile.getCanonicalPath()))) {
                        System.out.println(fileType + " File is moved successful!");
                    } else {
                        System.out.println(fileType + " File is failed to move!");
                    }
                }
            }
            if (result.equalsIgnoreCase("true")) {
                //now move this folder to in backup folder
                if (file.renameTo(new File(inwbkpFile.getCanonicalPath()))) {
                    System.out.println(fileType + " File is moved successful!");
                } else {
                    System.out.println(fileType + " File is failed to move!");
                }
                System.out.println(fileType + " File successfully uploaded.");
            }
        }
    }

    public void fileGenerationOfVerifiedEntries(String brnCode, String user, String currDate) {
        try {
            List dateList = npciFacade.getSettlementDtForUnverifiedEntriesNPCIOAC("NPCI-OAC");
            if (!dateList.isEmpty()) {
                for (Object dateObj : dateList) {
                    String settlmntDate = ((Vector) dateObj).get(0).toString();
                    List list = otherNpciRemote.findH2HAllFileSeqNo(ymd.format(dmy.parse(settlmntDate)), "NPCI-OAC");
                    for (Object obj : list) {
                        String seqNo = ((Vector) obj).get(0).toString();
                        //Here fileGenerationDt is the File Upload Date in coming file.
                        try {
                            String result = otherNpciRemote.generateOacReturnFiles(ymd.format(dmy.parse(settlmntDate)), seqNo, brnCode, user, currDate, "NPCI-OAC", "H2H", props.getProperty("cbs.ow.location"));
                            if (result.equals("true")) {
                                try {
                                    writeEncryptedFiles();
                                    upload(props.getProperty("npciSftpHost").trim(), props.getProperty("npciSftpUser").trim(),
                                            props.getProperty("npciSftpPassword").trim(), props.getProperty("cbs.ow.encrypted.location").trim(),
                                            props.getProperty("npciSftpFileUploadLocation").trim());

                                    createBackupAndThenRemoveFile(props.getProperty("cbs.ow.location").trim(), props.getProperty("cbs.ow.bkp.location").trim());
                                    createBackupAndThenRemoveFile(props.getProperty("cbs.ow.encrypted.location").trim(), props.getProperty("cbs.ow.bkp.encrypted.location").trim());
                                    System.out.println("NPCI-OAC Return file for " + seqNo + " has been generated successfully.");
                                } catch (Exception exc) {
                                    throw new Exception("Problem In Encryption And Uploading The file.");
                                }
                            }
                        } catch (Exception ex) {
                            System.out.println("NPCI-OAC Return file " + seqNo + ":=>" + ex.getMessage());
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        try {
            List dateList = npciFacade.getSettlementDtForUnverifiedEntriesNPCIOAC("ECS-DR");
            if (!dateList.isEmpty()) {
                for (Object dateObj : dateList) {
                    String settlmntDate = ((Vector) dateObj).get(0).toString();
                    List list = otherNpciRemote.findH2HAllFileSeqNo(ymd.format(dmy.parse(settlmntDate)), "ECS-DR");
                    for (Object obj : list) {
                        String seqNo = ((Vector) obj).get(0).toString();
                        try {
                            //Here fileGenerationDt is the File Settlement Date in coming file in header.
                            String result = otherNpciRemote.generateEcsDrReturnFiles(ymd.format(dmy.parse(settlmntDate)), seqNo, brnCode, user, currDate, "ECS-DR", "H2H", props.getProperty("cbs.ow.location"));
//                            if (result.equals("true")) {
//                                try {
//                                    writeEncryptedFiles();
//                                    upload(props.getProperty("npciSftpHost").trim(), props.getProperty("npciSftpUser").trim(),
//                                            props.getProperty("npciSftpPassword").trim(), props.getProperty("cbs.ow.encrypted.location").trim(),
//                                            props.getProperty("npciSftpFileUploadLocation").trim());
//
//                                    createBackupAndThenRemoveFile(props.getProperty("cbs.ow.location").trim(), props.getProperty("cbs.ow.bkp.location").trim());
//                                    createBackupAndThenRemoveFile(props.getProperty("cbs.ow.encrypted.location").trim(), props.getProperty("cbs.ow.bkp.encrypted.location").trim());
//                                    System.out.println("ECS-DR Return file for " + seqNo + " has been generated successfully.");
//                                } catch (Exception exc) {
//                                    throw new Exception("Problem In Encryption And Uploading The file.");
//                                }
//                            }
                            if (!result.equals("true")) {
                                throw new Exception("Problem In ECS DR Encryption And Uploading The file.");
                            }
                        } catch (Exception ex) {
                            System.out.println("ECS-DR Return file " + seqNo + ":=>" + ex.getMessage());
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        try {
            List dateList = npciFacade.getSettlementDtForUnverifiedEntriesNPCIOAC("ACH-DR");
            if (!dateList.isEmpty()) {
                for (Object dateObj : dateList) {
                    String settlmntDate = ((Vector) dateObj).get(0).toString();
                    List list = otherNpciRemote.findH2HAllFileSeqNo(ymd.format(dmy.parse(settlmntDate)), "ACH-DR");
                    for (Object obj : list) {
                        String seqNo = ((Vector) obj).get(0).toString();
                        try {
                            //Here fileGenerationDt is the File Settlement Date in coming file in header.
//                            String result = otherNpciRemote.generateEcsDrReturnFiles(ymd.format(dmy.parse(settlmntDate)), seqNo, brnCode, user, currDate, "ECS-DR", "H2H", props.getProperty("cbs.ow.location"));
                            String result = otherNpciRemote.generateAchDr306ReturnFiles(settlmntDate, brnCode, user, currDate, seqNo, "H2H", props.getProperty("cbs.ow.location"));
//                            if (result.equals("true")) {
//                                try {
//                                    writeEncryptedFiles();
//                                    upload(props.getProperty("npciSftpHost").trim(), props.getProperty("npciSftpUser").trim(),
//                                            props.getProperty("npciSftpPassword").trim(), props.getProperty("cbs.ow.encrypted.location").trim(),
//                                            props.getProperty("npciSftpFileUploadLocation").trim());
//
//                                    createBackupAndThenRemoveFile(props.getProperty("cbs.ow.location").trim(), props.getProperty("cbs.ow.bkp.location").trim());
//                                    createBackupAndThenRemoveFile(props.getProperty("cbs.ow.encrypted.location").trim(), props.getProperty("cbs.ow.bkp.encrypted.location").trim());
//                                    System.out.println("ACH-DR Return file for " + seqNo + " has been generated successfully.");
//                                } catch (Exception exc) {
//                                    throw new Exception("Problem In Encryption And Uploading The file.");
//                                }
//                            }
                            if (!result.equals("true")) {
                                throw new Exception("Problem In ACH DR 306 Encryption And Uploading The file.");
                            }
                        } catch (Exception ex) {
                            System.out.println("ACH-DR Return file " + seqNo + ":=>" + ex.getMessage());
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        try {
            List dateList = npciFacade.getUploadDateForUnverifiedEntriesForReturnMMs();
            if (!dateList.isEmpty()) {
                for (Object dateObj : dateList) {
                    String uploadDate = ((Vector) dateObj).get(0).toString();
                    String setteledDate = ymd.format(dmy.parse(uploadDate));
                    String mandateType = ((Vector) dateObj).get(1).toString();
                    String fileNo = ((Vector) dateObj).get(2).toString();
                    String mandateMode = ((Vector) dateObj).get(3).toString();
                    if (!mandateMode.equalsIgnoreCase("E")) {
                        String result = npciMandteRemote.generateMmsCreateReturn(mandateType, setteledDate,
                                fileNo, currDate, user, brnCode, "H2H", props.getProperty("cbs.ow.location"), mandateMode);
                        if (!result.equals("true")) {
                            throw new Exception("Problem In MMS create Return method.");
                        }
                    } else {
                        String result = npciMandteRemote.generateEsignMmsReturn(mandateType, setteledDate,
                                fileNo, currDate, user, brnCode, "H2H", props.getProperty("cbs.ow.location"), mandateMode);
                        if (!result.equals("true")) {
                            throw new Exception("Problem In Esign create Return method.");
                        }
                    }
                }
            }

        } catch (Exception ex) {
            System.out.println("MMS Return file =>" + ex.getMessage());
        }
    }

    private void esignFileProcessing(String inwardDir, String brnCode, String user, String currentDt) throws Exception {
        File inwardFiles = new File(inwardDir);
        for (File file : inwardFiles.listFiles()) {
            String uploadFileName = file.getName();
            File inwbkpFile = new File(props.getProperty("cbs.iw.bkp.location").trim() + uploadFileName);
            if (uploadFileName == null || uploadFileName.equalsIgnoreCase("")) {
                System.out.println("There is no any appropriadte file to upload.");
            }
            String actualFileName = uploadFileName.trim().substring(0, uploadFileName.indexOf("."));
            if (uploadFileName.contains("ESIGN") && uploadFileName.contains(".zip")) {
                try {
                    if (FtsPostMgmtRemote.getCodeForReportName("DG-SIGN-UTILITY") == 1) {
                        new SignUtil().parseSignedFile(file.getCanonicalPath(), true, file.getCanonicalPath(), false);
                    }
                    List list = otherNpciRemote.isFileUploaded(uploadFileName);
                    if (!list.isEmpty()) {
                        System.out.println(uploadFileName + " has been already uploaded.");
                        //move zip file into inward Backup Folder
                        if (file.renameTo(new File(inwbkpFile.getCanonicalPath()))) {
                            System.out.println("ESIGN File is moved successfully!");
                        } else {
                            System.out.println("ESIGN File is failed to move!");
                        }
                        continue;
                    }
                    String mMode = "";
                    if (uploadFileName.toUpperCase().contains("ESIGN")) {
                        mMode = "E";
                    }
                    Integer fileNo = otherNpciRemote.retrieveFileNo(actualFileName.split("-")[1], currentDt, mMode);
                    String imageUploadFolder = "";
                    if (mMode.equalsIgnoreCase("E")) {
                        imageUploadFolder = currentDt + "/" + fileNo.toString() + "_" + actualFileName.trim().split("-")[1] + "_ESIGN" + "/";
                    }
                    File dir = new File("/opt/mms/" + imageUploadFolder);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    File zipFile = new File(dir.getCanonicalPath() + File.separator + uploadFileName);
                    //Writing zip file into filesystem**
                    InputStream is = null;
                    OutputStream os = null;
                    try {
                        is = new FileInputStream(file);
                        os = new FileOutputStream(zipFile);
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = is.read(buffer)) > 0) {
                            os.write(buffer, 0, length);
                        }
                    } finally {
                        is.close();
                        os.close();
                    }
                    //End here
                    FileSeekableStream stream = null;
                    ImageDecoder dec = null;
                    RenderedImage image = null;
                    Integer dotIndex = null;
                    String brFileName = null;
                    File parBrFile = null;

                    //Unzipping the folder
                    unzipFolder(zipFile.getCanonicalPath(), dir.getCanonicalPath() + "/");
                    File filer = new File(dir + "/");
                    String[] filesAre = filer.list();
                    try {
                        for (int i = 0; i < filesAre.length; i++) {
                            if (filesAre[i].endsWith("tif") || filesAre[i].endsWith("tiff")) {
                                stream = new FileSeekableStream(dir + "/" + filesAre[i]);
                                dec = ImageCodec.createImageDecoder("tiff", stream, null);
                                image = dec.decodeAsRenderedImage(0);
                                dotIndex = filesAre[i].indexOf(".");

                                brFileName = filesAre[i].substring(0, dotIndex);
                                JAI.create("filestore", image, dir + "/" + brFileName + ".png", "PNG");
                                parBrFile = new File(dir + "/" + filesAre[i]);
                                parBrFile.delete();
                            }
                            if (filesAre[i].endsWith("jpeg")) {
                                stream = new FileSeekableStream(dir + "/" + filesAre[i]);
                                dec = ImageCodec.createImageDecoder("jpeg", stream, null);
                                image = dec.decodeAsRenderedImage(0);
                                dotIndex = filesAre[i].indexOf(".");

                                brFileName = filesAre[i].substring(0, dotIndex);
                                JAI.create("filestore", image, dir + "/" + brFileName + ".jpg", "JPEG");
                                parBrFile = new File(dir + "/" + filesAre[i]);
                                parBrFile.delete();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    } finally {
                        if (mMode.equalsIgnoreCase("E") && stream != null) {
                            stream.close();
                        }
                    }
                    String result = "";
                    if (mMode.equalsIgnoreCase("E")) {
                        result = otherNpciRemote.uploadEsignData(dir, currentDt, user, fileNo, uploadFileName, mMode, "H2H");
                    }
                    if (result.equalsIgnoreCase("true")) {
                        //move zip file into inward Backup Folder
                        if (file.renameTo(new File(inwbkpFile.getCanonicalPath()))) {
                            System.out.println("Esign File is moved successful!");
                        } else {
                            System.out.println("Esign File is failed to move!");
                        }
                    } else {
                        System.out.println(result);
                    }
                    System.out.println("Data has been uploaded successfully");

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    private void mmsFileProcessing(String inwardDir, String brnCode, String user, String currentDt) throws Exception {
        File inwardFiles = new File(inwardDir);
        for (File file : inwardFiles.listFiles()) {//get all files from iw folder and procee on it
            String uploadedFileName = file.getName();
            File inwbkpFile = new File(props.getProperty("cbs.iw.bkp.location").trim() + uploadedFileName);
            if (uploadedFileName == null || uploadedFileName.equalsIgnoreCase("")) {
                System.out.println("There is no any appropriate file to upload");
            }
            String actualFileName = uploadedFileName.trim().substring(0, uploadedFileName.indexOf(".")); //Without extension
            if (uploadedFileName.contains("MMS") && uploadedFileName.contains(".zip")) {
                try {
                    if (FtsPostMgmtRemote.getCodeForReportName("DG-SIGN-UTILITY") == 1) {
                        new SignUtil().parseSignedFile(file.getCanonicalPath(), true, file.getCanonicalPath(), false);
                    }

                    List list = otherNpciRemote.isFileUploaded(uploadedFileName);
                    if (!list.isEmpty()) {
                        System.out.println(uploadedFileName + " has been already uploaded.");
                        //move zip file into inward Backup Folder
                        if (file.renameTo(new File(inwbkpFile.getCanonicalPath()))) {
                            System.out.println("MMS File is moved successfully!");
                        } else {
                            System.out.println("MMS File is failed to move!");
                        }
                        continue;
                    }
                    String mMode = "";
                    if (uploadedFileName.toUpperCase().contains("LEGACY")) {
                        mMode = "L";
                    } else {
                        mMode = "N";
                    }

                    Integer fileNo = otherNpciRemote.retrieveFileNo(actualFileName.split("-")[1], currentDt, mMode);
                    String imageUploadFolder = "";
                    if (mMode.equalsIgnoreCase("L")) {
                        imageUploadFolder = currentDt + "/" + fileNo.toString() + "_" + actualFileName.trim().split("-")[1] + "_LEGACY" + "/";
                    } else if (mMode.equalsIgnoreCase("N")) {
                        imageUploadFolder = currentDt + "/" + fileNo.toString() + "_" + actualFileName.trim().split("-")[1] + "/";
                    }

                    File dir = new File("/opt/mms/" + imageUploadFolder);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    File zipFile = new File(dir.getCanonicalPath() + File.separator + uploadedFileName);
                    //Writing zip file into filesystem**
                    InputStream is = null;
                    OutputStream os = null;
                    try {
                        is = new FileInputStream(file);
                        os = new FileOutputStream(zipFile);
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = is.read(buffer)) > 0) {
                            os.write(buffer, 0, length);
                        }
                    } finally {
                        is.close();
                        os.close();
                    }
                    //End here

                    FileSeekableStream stream = null;
                    ImageDecoder dec = null;
                    RenderedImage image = null;
                    Integer dotIndex = null;
                    String brFileName = null;
                    File parBrFile = null;

                    //Unzipping the folder
                    unzipFolder(zipFile.getCanonicalPath(), dir.getCanonicalPath() + "/");
                    File filer = new File(dir + "/");
                    String[] filesAre = filer.list();
                    try {
                        for (int i = 0; i < filesAre.length; i++) {
                            if (filesAre[i].endsWith("tif") || filesAre[i].endsWith("tiff")) {
                                stream = new FileSeekableStream(dir + "/" + filesAre[i]);
                                dec = ImageCodec.createImageDecoder("tiff", stream, null);
                                image = dec.decodeAsRenderedImage(0);
                                dotIndex = filesAre[i].indexOf(".");

                                brFileName = filesAre[i].substring(0, dotIndex);
                                JAI.create("filestore", image, dir + "/" + brFileName + ".png", "PNG");
                                parBrFile = new File(dir + "/" + filesAre[i]);
                                parBrFile.delete();
                            }
                            if (filesAre[i].endsWith("jpeg")) {
                                stream = new FileSeekableStream(dir + "/" + filesAre[i]);
                                dec = ImageCodec.createImageDecoder("jpeg", stream, null);
                                image = dec.decodeAsRenderedImage(0);
                                dotIndex = filesAre[i].indexOf(".");

                                brFileName = filesAre[i].substring(0, dotIndex);
                                JAI.create("filestore", image, dir + "/" + brFileName + ".jpg", "JPEG");
                                parBrFile = new File(dir + "/" + filesAre[i]);
                                parBrFile.delete();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    } finally {
                        if (mMode.equalsIgnoreCase("L") && stream != null) {
                            stream.close();
                        } else if (mMode.equalsIgnoreCase("N")) {
                            if (stream == null) {
                                throw new ApplicationException("tif type files are not found");
                            } else {
                                stream.close();
                            }
                        }
                    }
                    String result = otherNpciRemote.uploadMmsData(dir, currentDt, user, fileNo, uploadedFileName, mMode, "H2H");
                    if (result.equalsIgnoreCase("true")) {
                        //move zip file into inward Backup Folder
                        if (file.renameTo(new File(inwbkpFile.getCanonicalPath()))) {
                            System.out.println("MMS File is moved successful!");
                        } else {
                            System.out.println("MMS File is failed to move!");
                        }
                    } else {
                        System.out.println(result);
                    }
                    System.out.println("Data has been uploaded successfully");
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    private void processResponse(String responseDir, String brnCode, String user, String currDate) throws Exception {
        FileFilter fileFilter = new WildcardFileFilter("*");
        File responseFiles = new File(responseDir);
        for (File file : responseFiles.listFiles(fileFilter)) {// get all files from iw folder and process on it.
            String uploadedFileName = file.getName();
            File resFile = new File(file.getCanonicalPath());
            try {
                List fileNameExits = otherNpciRemote.isH2HFileDetailexits(uploadedFileName);
                if (!fileNameExits.isEmpty()) {
                    System.out.println(uploadedFileName + " file already exits in h2hfileDetails. ");
                } else {
                    String result = otherNpciRemote.h2hfileinsertionResponseCase(uploadedFileName);
                    if (result.equalsIgnoreCase("true")) {
                        System.out.println(uploadedFileName + " file inserted successfully in h2hfileDetails.");
                    }
                }
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public void writeEncryptedFiles() throws Exception {
        //Writing encrypted files
        File owDir = new File(props.getProperty("cbs.ow.location").trim());
        File[] owFiles = owDir.listFiles();
        SignUtil su = null;
        for (File owFile : owFiles) {
            su = new SignUtil();
            String xml = new String(Files.readAllBytes(Paths.get(props.getProperty("cbs.ow.location").trim() + owFile.getName(), new String[0])));
            String output = su.getSignedEnvelope(false, "", xml, BusinessConstant.DSG_KEY_PATH, FtsPostMgmtRemote.getKeyPwd());
            FileOutputStream outStream = new FileOutputStream(new File(props.getProperty("cbs.ow.encrypted.location").trim() + File.separator + owFile.getName()));
            outStream.write(output.getBytes());
            outStream.close();
        }
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

    public static void unzipFolder(String zipFile, String dir) throws ApplicationException {
        InputStream is = null;
        FileOutputStream fos = null;
        ZipFile zf = null;
        try {
            zf = new ZipFile(zipFile);
            Enumeration<? extends ZipEntry> zipEnum = zf.entries();

            while (zipEnum.hasMoreElements()) {
                ZipEntry item = (ZipEntry) zipEnum.nextElement();
                if (item.isDirectory()) {
                    File newdir = new File(dir + item.getName());
                    newdir.mkdir();
                } else {
                    String newfilePath = dir + item.getName();
                    try {
                        is = zf.getInputStream(item);
                        fos = new FileOutputStream(newfilePath);
                        int ch;
                        while ((ch = is.read()) != -1) {
                            fos.write(ch);
                        }
                    } catch (Exception e) {
                        throw new ApplicationException(e.getMessage());
                    } finally {
                        is.close();
                        fos.close();
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        } finally {
            try {
                zf.close();
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public boolean isCentralDayBegin() throws Exception {
        boolean isDayBegin = false;
        List cbsBankDaysList = em.createNativeQuery("select date from cbs_bankdays where "
                + "date = '" + ymd.format(new Date()) + "' and daybeginflag='Y' and dayendflag='N'").getResultList();
        if (!cbsBankDaysList.isEmpty()) {
            return isDayBegin = true;
        }
        return isDayBegin;
    }

    public List<String> getDownloadedDates() throws Exception {
        List<String> dateList = new ArrayList<>();
        List lastDayEndDateList = em.createNativeQuery("select date_format(max(date),'%Y%m%d') as lastDayEndDate from "
                + "cbs_bankdays where daybeginflag='Y' and dayendflag='Y'").getResultList();
        Vector ele = (Vector) lastDayEndDateList.get(0);
        String lastDayEndDate = ele.get(0).toString();
        long dayDiff = CbsUtil.dayDiff(ymd.parse(lastDayEndDate), ymd.parse(ymd.format(new Date())));
        for (int i = 0; i <= dayDiff; i++) {
            dateList.add(CbsUtil.dateAdd(lastDayEndDate, i));
        }

        for (int i = 0; i < dateList.size(); i++) {
            System.out.println("Date Is-->" + dateList.get(i));
        }
        return dateList;
    }

    @Override
    public String updateModuleConfigProperty(String key, String value) throws ApplicationException {
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
