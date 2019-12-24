/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.clg.h2h;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.clg.CtsManagementFacadeRemote;
import static com.cbs.facade.clg.h2h.ClgH2hMgmtFacade.unzipFolder;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
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
import java.net.URI;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.activation.MimetypesFileTypeMap;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.media.jai.JAI;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.apache.commons.vfs2.FileFilterSelector;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

@Stateless(mappedName = "ClgH2hVfsMgmtFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class ClgH2hVfsMgmtFacade implements ClgH2hVfsMgmtFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote ftsRemote;
    @EJB
    private CtsManagementFacadeRemote ctsTxnRemote;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymdOne = new SimpleDateFormat("ddMMyyyy");
    private Properties props = null;

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
    public void processUploadCts() {
        System.out.println("In H2H Clearing Facade");
        try {
            String bankCode = ftsRemote.getBankCode();
            if (bankCode == null || bankCode.equals("")) {
                throw new Exception("Please define bank code");
            }
            List<String> micrs = allBranchMicrs();

            String hostname = props.getProperty("ctsSftpHost").trim();
            String username = props.getProperty("ctsSftpUser").trim();
            String password = props.getProperty("ctsSftpPassword").trim();
            String willDelete = props.getProperty("will.delete").trim();
            String remoteLocation = props.getProperty("ctsSftpFileLocation").trim();
            String localDownloadLocation = props.getProperty("cts.cbs.iw.location").trim();

            try {
                download(hostname, username, password, bankCode, localDownloadLocation, remoteLocation, micrs, willDelete);
            } catch (Exception ex) {
                System.out.println("Problem in downloading the cts files." + ex.getMessage());
            }
            processUploadData();
        } catch (Exception ex) {
            System.out.println("Problem In uploadCTSData() method.");
            ex.printStackTrace();
        }
    }

    public static void download(String hostName, String username, String password, String bankCode,
            String localFilePath, String remoteFilePath, List<String> micrs, String willDelete) {
        StandardFileSystemManager manager = new StandardFileSystemManager();
        try {
            manager.init();
            //Create remote connection string
            String connectionStr = createConnectionString(hostName, username, password, remoteFilePath);
            //Setting the options
            FileSystemOptions options = createDefaultOptions(bankCode);
            //Fetching the files from remote location
            FileObject[] remoteFile = manager.resolveFile(connectionStr, options).findFiles(new FileFilterSelector(new ClgCtsFileFilter(micrs)));
            for (FileObject fileObject : remoteFile) {
                String fileToDownload = fileObject.getName().toString().substring(fileObject.getName().toString().lastIndexOf("/") + 1);
                FileObject localFile = manager.resolveFile(localFilePath + "/" + fileToDownload);
                if (willDelete.equalsIgnoreCase("yes")) {
                    fileObject.moveTo(localFile);
                } else if (willDelete.equalsIgnoreCase("no")) {
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

    public static void delete(String hostName, String username, String password, String bankCode, String remoteFilePath) {
        StandardFileSystemManager manager = new StandardFileSystemManager();
        try {
            manager.init();
            //Create remote connection string
            String connectionStr = createConnectionString(hostName, username, password, remoteFilePath);
            //Setting the options
            FileSystemOptions options = createDefaultOptions(bankCode);
            FileObject remoteFile = manager.resolveFile(connectionStr, options);
            if (remoteFile.exists()) {
                remoteFile.delete();
                System.out.println("Delete remote file success");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            manager.close();
        }
    }

    private void processUploadData() throws Exception {
        FileSeekableStream stream = null;
        ImageDecoder dec = null;
        RenderedImage image = null;
        Integer dotIndex = null;
        String brFileName = null;
        File parBrFile = null;
        System.out.println("In processUploadData Method In CTS");
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String ctsLoacalPath = props.getProperty("cts.cbs.iw.location").trim();
            String ctsLoacalbkpPath = props.getProperty("cts.cbs.iw.bkp.location").trim();
            String ctsImageLocation = props.getProperty("cts.cbs.iw.ctsimage.location").trim();
            File dirAuto = new File(ctsLoacalPath);
            String directory = ctsImageLocation;
            File dir = new File(directory + "/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File[] files = dirAuto.listFiles();
            System.out.println("Files On Date-->" + ymdOne.format(new Date()) + "::Total No Of Files Are-->" + files.length);
            for (File file : files) {
                if (!(file.getName().trim().toLowerCase().contains("zip") && file.length() != 0)) {
                    createBackupAndThenRemoveFile(new File(ctsLoacalPath + file.getName()), ctsLoacalbkpPath);
                    continue;
                }

                String result = "";
                MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
                String fileContentType = mimeTypesMap.getContentType(file.getName());
                System.out.println("File-->" + file.getName() + ": content type-->" + fileContentType);

                if (fileContentType.equals("text/plain") || fileContentType.equals("application/octet-stream")) {
                    String uploadedFileName = file.getName();
                    String zipFileName = uploadedFileName.trim().substring(0, uploadedFileName.indexOf("."));
                    List fileChqList = em.createNativeQuery("select distinct ifnull(original_zip_name,'') as filename "
                            + "from cts_upload_txt_cell where original_zip_name='" + zipFileName + "' and "
                            + "date_format(dt,'%Y%m%d')='" + ymd.format(new Date()) + "'").getResultList();
                    if (!fileChqList.isEmpty()) {
                        System.out.println("File-->" + uploadedFileName + " Is Already Uploaded On " + ymdOne.format(new Date()));
                        createBackupAndThenRemoveFile(new File(ctsLoacalPath + uploadedFileName), ctsLoacalbkpPath);
                        continue;
                    }
                    String brnCode = zipFileName.split("\\_")[1].trim().substring(6, 9);
                    String branchCode = "";
                    List brList = getBranchCode(brnCode);
                    for (int j = 0; j < brList.size(); j++) {
                        Vector vtr = (Vector) brList.get(j);
                        branchCode = vtr.get(0).toString();
                        File branchZipFile = new File(dir.getCanonicalPath() + File.separatorChar + uploadedFileName);
                        byte[] bb = Files.readAllBytes(file.toPath());
                        FileOutputStream fos = new FileOutputStream(branchZipFile);
                        fos.write(bb);
                        fos.close();

                        Integer scheduleNo = ctsTxnRemote.getScheduleNo(ymd.format(new Date()), Integer.parseInt(branchCode));
                        String branchFolderName = "";
                        if (branchCode.length() < 2) {
                            branchFolderName = scheduleNo.toString() + "_0" + branchCode;
                        } else {
                            branchFolderName = scheduleNo.toString() + "_" + branchCode;
                        }
                        String[] arr = unzipFolder(branchZipFile.getCanonicalPath(), dir.getCanonicalPath() + "/" + branchFolderName + "/");
                        File destfile = new File(directory + "/" + branchFolderName + "/");
                        String[] filesAre = destfile.list();

                        try {
                            for (int i = 0; i < filesAre.length; i++) {
                                if (filesAre[i].endsWith("BR")) {
                                    stream = new FileSeekableStream(directory + "/" + branchFolderName + "/" + filesAre[i]);
                                    dec = ImageCodec.createImageDecoder("tiff", stream, null);
                                    try {
                                        image = dec.decodeAsRenderedImage(0);
                                    } catch (Exception ex) {
                                        continue;
                                    }
                                    dotIndex = filesAre[i].indexOf(".");
                                    brFileName = filesAre[i].substring(0, dotIndex);
                                    JAI.create("filestore", image, directory + "/" + branchFolderName + "/" + brFileName + ".png", "PNG");
                                    parBrFile = new File(directory + "/" + branchFolderName + "/" + filesAre[i]);
                                    parBrFile.delete();
                                }
                                if (filesAre[i].endsWith("BF")) {
                                    stream = new FileSeekableStream(directory + "/" + branchFolderName + "/" + filesAre[i]);
                                    dec = ImageCodec.createImageDecoder("tiff", stream, null);
                                    try {
                                        image = dec.decodeAsRenderedImage(0);
                                    } catch (Exception ex) {
                                        continue;
                                    }
                                    dotIndex = filesAre[i].indexOf(".");
                                    brFileName = filesAre[i].substring(0, dotIndex);
                                    JAI.create("filestore", image, directory + "/" + branchFolderName + "/" + brFileName + ".jpg", "JPEG");
                                    parBrFile = new File(directory + "/" + branchFolderName + "/" + filesAre[i]);
                                    parBrFile.delete();
                                }
                            }
                        } catch (Exception e) {
                            throw new ApplicationException(e.getMessage());
                        } finally {
                            if (stream == null) {
                                throw new ApplicationException("BR or BF type files are not found");
                            } else {
                                stream.close();
                            }
                        }

                        String txtFilePath = dir + "/" + branchFolderName + "/" + arr[0];
                        String pxfFilePath = dir + "/" + branchFolderName + "/" + arr[1];
                        System.out.println("Text" + txtFilePath + "  PXF" + pxfFilePath);
                        result = ctsTxnRemote.uploadDataInCell(txtFilePath, pxfFilePath, branchFolderName, zipFileName, scheduleNo, "System");
                    }
                }
                if (result.equalsIgnoreCase("true")) {
                    createBackupAndThenRemoveFile(new File(ctsLoacalPath + file.getName()), ctsLoacalbkpPath);
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

    public static String[] unzipFolder(String zipFile, String destFolder) throws ApplicationException {
        InputStream is = null;
        FileOutputStream fos = null;
        ZipFile zf = null;
        String[] arr = new String[2];
        try {
            zf = new ZipFile(zipFile);
            Enumeration<? extends ZipEntry> zipEnum = zf.entries();
            String dir = destFolder;

            while (zipEnum.hasMoreElements()) {
                ZipEntry item = (ZipEntry) zipEnum.nextElement();
                if (item.isDirectory()) {
                    File newdir = new File(dir + File.separator + item.getName());
                    newdir.mkdir();
                } else {
                    String fileType = item.getName().substring(item.getName().indexOf(".") + 1);
                    String fileName = item.getName().substring(item.getName().lastIndexOf("/") + 1, item.getName().indexOf("."));

                    if (fileType.equalsIgnoreCase("txt")) {
                        fileName = fileName + ".Txt";
                        arr[0] = fileName;
                    } else if (fileType.equalsIgnoreCase("pxf")) {
                        fileName = fileName + ".Pxf";
                        arr[1] = fileName;
                    } else {
                        fileName = item.getName().substring(item.getName().lastIndexOf("/") + 1);
                    }
                    String newfilePath = dir + File.separator + fileName;
                    File newFile = new File(newfilePath);
                    if (!newFile.getParentFile().exists()) {
                        newFile.getParentFile().mkdirs();
                    }
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
            //error in opening zip file java
            throw new ApplicationException(ex.getMessage());
        } finally {
            try {
                zf.close();
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return arr;
    }

    public void createBackupAndThenRemoveFile(File fileToDelete, String localIwBackupDir) throws IOException {
        try {
            FileInputStream fis = new FileInputStream(fileToDelete);
            FileOutputStream fos = new FileOutputStream(localIwBackupDir + fileToDelete.getName());

            byte[] buffer = new byte[1024];
            int length;
            //Copy the file content in bytes 
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            //Delete the original file
            fos.close();
            fis.close();
            fileToDelete.delete();
        } catch (Exception ex) {
            throw new IOException(ex.getMessage());
        }
    }

    public List getBranchCode(String mic) throws ApplicationException {
        try {
            return em.createNativeQuery("select b.brncode from bnkadd a,branchmaster b where branchcode = '" + mic + "' and "
                    + "a.alphacode = b.AlphaCode and a.alphacode <> 'HO' ").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<String> allBranchMicrs() throws Exception {
        List<String> micrs = new ArrayList<>();
        try {
            List list = em.createNativeQuery("select ifnull(micr,''),ifnull(micrcode,''),ifnull(branchcode,'') "
                    + "from bnkadd order by branchcode").getResultList();
            if (list.isEmpty()) {
                throw new Exception("Please define micrs for bank in bnkadd.");
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                String cityCode = ele.get(0).toString().trim();
                cityCode = cityCode.length() < 3 ? ParseFileUtil.addTrailingZeros(cityCode, 3) : cityCode;
                String bankCode = ele.get(1).toString().trim();
                bankCode = bankCode.length() < 3 ? ParseFileUtil.addTrailingZeros(bankCode, 3) : bankCode;
                String branchCode = ele.get(2).toString().trim();
                branchCode = branchCode.length() < 3 ? ParseFileUtil.addTrailingZeros(branchCode, 3) : branchCode;

                String micr = cityCode + bankCode + branchCode;
                if (!micrs.contains(micr)) {
                    micrs.add(micr);
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return micrs;
    }

    //remoteLocation - It can be absolute file path or absolute directory path
    public static String createConnectionString(String hostName, String username, String password,
            String remoteLocation) throws Exception {
        String userInfo = username + ":" + password;
        URI uri = new URI("sftp", userInfo, hostName, 22, remoteLocation, null, null);
        return uri.toString();
    }

    public static FileSystemOptions createDefaultOptions(String bankCode) throws FileSystemException {
        //Create options for sftp
        FileSystemOptions options = new FileSystemOptions();
        //SSH key
        SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(options, "no");
        //Set root directory to user home
        SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(options, false);
        //Timeout
        SftpFileSystemConfigBuilder.getInstance().setTimeout(options, 60000);
        if (bankCode.equalsIgnoreCase("kccb")) {
            SftpFileSystemConfigBuilder.getInstance().setIdentities(options, new File[]{new File("/root/.ssh/krn_ghosh_hdfc")});
        } else if (bankCode.equalsIgnoreCase("ccbl")) {
            SftpFileSystemConfigBuilder.getInstance().setIdentities(options, new File[]{new File("/root/.ssh/deepank_ccbl_hdfc")});
        } else if (bankCode.equalsIgnoreCase("nabu")) {
            SftpFileSystemConfigBuilder.getInstance().setIdentities(options, new File[]{new File("/root/.ssh/nawadip_cts")});
        }
        return options;
    }
}
