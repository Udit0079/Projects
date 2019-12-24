/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.clg;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.clg.CtsManagementFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.media.jai.JAI;
import javax.servlet.ServletContext;
import org.apache.myfaces.custom.fileupload.UploadedFile;

/**
 *
 * @author root
 */
public class UploadCTSBean extends BaseBean {

    private UploadedFile uploadedFile;
    private String message;
    private String branch;
    private List<SelectItem> branchList;
    Date dt = new Date();
    private final String jndiHomeNameCts = "CtsManagementFacade";
    private CtsManagementFacadeRemote ctsTxnRemote = null;
    private CommonReportMethodsRemote common;
    private FtsPostingMgmtFacadeRemote fts;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    /**
     * Creates a new instance of UploadCTSBean
     */
    public UploadCTSBean() {
        try {
            this.setMessage("Please select branch...");
            ctsTxnRemote = (CtsManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameCts);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            initData();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void initData() {
        this.setMessage("");
        try {
            List list = new ArrayList();
            branchList = new ArrayList<SelectItem>();

            if (fts.getCodeForReportName("CTS-SPONSOR") == 1) {
                list = common.getAlphacodeAllAndBranch(getOrgnBrCode());
                for (int i = 0; i < list.size(); i++) {
                    Vector vtr = (Vector) list.get(i);
                    if (CbsUtil.lPadding(2, Integer.parseInt(vtr.get(1).toString())).equalsIgnoreCase(getOrgnBrCode())) {
                        branchList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(vtr.get(1).toString())), vtr.get(0).toString()));
                    }
                }
            } else {
                list = common.getAlphacodeBasedOnBranch(getOrgnBrCode());
                for (int i = 0; i < list.size(); i++) {
                    Vector vtr = (Vector) list.get(i);
                    branchList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(vtr.get(1).toString())), vtr.get(0).toString()));
                }
            }

        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void uploadProcess() {
        this.setMessage("");
        if (uploadedFile == null) {
            this.setMessage("Please select appropriate file to upload");
            return;
        } else {
            String uploadedFileName = uploadedFile.getName();
            if (uploadedFileName == null || uploadedFileName.equalsIgnoreCase("")) {
                this.setMessage("Please select appropriate file to upload");
                return;
            }
            String actualFileName = uploadedFileName.trim().substring(0, uploadedFileName.indexOf("."));
            String fileContentType = uploadedFile.getContentType();
            System.out.println("The Content Tpye of the uploded file is >>>>>>>>>>>>>>>>>>>>>>>>>> " + fileContentType);
            if (fileContentType.equals("application/x-zip") || fileContentType.equals("application/zip") || fileContentType.equals("application/x-zip-compressed") || fileContentType.equals("application/octet-stream")) {
                try {
                    FileSeekableStream stream = null;
                    ImageDecoder dec = null;
                    RenderedImage image = null;
                    Integer dotIndex = null;
                    String brFileName = null;
                    File parBrFile = null;
                    ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                    String directory = context.getInitParameter("cts");
                    File dir = new File(directory + "/");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File branchZipFile = new File(dir.getCanonicalPath() + File.separatorChar + uploadedFileName);
                    //Writing zip file into filesystem**
                    byte[] b = uploadedFile.getBytes();
                    FileOutputStream fos = new FileOutputStream(branchZipFile);
                    fos.write(b);
                    fos.close();
                    //End here
                    Integer scheduleNo = ctsTxnRemote.getScheduleNo(ymd.format(dt), Integer.parseInt(this.branch));
                    String branchFolderName = scheduleNo.toString() + "_" + this.branch;

                    String cbsBrCode = branchFolderName.substring(branchFolderName.indexOf("_") + 1);
                    if (ctsTxnRemote.isZipFileAlreadyUploaded(actualFileName, Integer.parseInt(cbsBrCode), ymd.format(dt))) {
                        throw new ApplicationException("This zip file has been already uploaded.");
                    }
                    //Deletion of old files
                    File oldDirectory = new File(dir.getCanonicalPath() + "/" + branchFolderName + "/");
                    if (!oldDirectory.exists()) {
                        oldDirectory.mkdirs();
                    }
                    File[] oldFiles = oldDirectory.listFiles();
                    for (File file : oldFiles) {
                        if (file.exists()) {
                            file.delete();
                        }
                    }

                    String[] arr = unzipFolder(branchZipFile.getCanonicalPath(), dir.getCanonicalPath() + "/" + branchFolderName + "/");
                    File file = new File(directory + "/" + branchFolderName + "/");
                    String[] filesAre = file.list();
                    if (fts.getCodeForReportName("CTS-SPONSOR") == 1) { //For AMMCO Bank CTS
                        try {
                            for (int i = 0; i < filesAre.length; i++) {
                                if (filesAre[i].trim().toLowerCase().contains("ba")) {
                                    stream = new FileSeekableStream(directory + "/" + branchFolderName + "/" + filesAre[i]);
                                    dec = ImageCodec.createImageDecoder("tiff", stream, null);
                                    image = dec.decodeAsRenderedImage(0);
                                    dotIndex = filesAre[i].indexOf(".");

                                    brFileName = filesAre[i].substring(0, dotIndex);
                                    JAI.create("filestore", image, directory + "/" + branchFolderName + "/" + brFileName + ".jpg", "JPEG");
                                    parBrFile = new File(directory + "/" + branchFolderName + "/" + filesAre[i]);
                                    parBrFile.delete();
                                }
                                if (filesAre[i].trim().toLowerCase().contains("fb")) {
                                    stream = new FileSeekableStream(directory + "/" + branchFolderName + "/" + filesAre[i]);
                                    dec = ImageCodec.createImageDecoder("tiff", stream, null);
                                    image = dec.decodeAsRenderedImage(0);
                                    dotIndex = filesAre[i].indexOf(".");

                                    brFileName = filesAre[i].substring(0, dotIndex);
                                    JAI.create("filestore", image, directory + "/" + branchFolderName + "/" + brFileName + ".jpg", "JPEG");
                                    parBrFile = new File(directory + "/" + branchFolderName + "/" + filesAre[i]);
                                    parBrFile.delete();
                                }
                            }
                        } catch (Exception e) {
                            this.setMessage(e.getMessage());
                        } finally {
                            if (stream == null) {
                                throw new ApplicationException("Ba or Fb type files are not found");
                            } else {
                                stream.close();
                            }
                        }
                    } else { //For CCBL Bank CTS
                        try {
                            for (int i = 0; i < filesAre.length; i++) {
                                if (filesAre[i].endsWith("BR")) {
                                    stream = new FileSeekableStream(directory + "/" + branchFolderName + "/" + filesAre[i]);
                                    dec = ImageCodec.createImageDecoder("tiff", stream, null);
                                    image = dec.decodeAsRenderedImage(0);
                                    dotIndex = filesAre[i].indexOf(".");

                                    brFileName = filesAre[i].substring(0, dotIndex);
                                    JAI.create("filestore", image, directory + "/" + branchFolderName + "/" + brFileName + ".png", "PNG");
                                    parBrFile = new File(directory + "/" + branchFolderName + "/" + filesAre[i]);
                                    parBrFile.delete();
                                }
                                if (filesAre[i].endsWith("BF")) {
                                    stream = new FileSeekableStream(directory + "/" + branchFolderName + "/" + filesAre[i]);
                                    dec = ImageCodec.createImageDecoder("tiff", stream, null);
                                    image = dec.decodeAsRenderedImage(0);
                                    dotIndex = filesAre[i].indexOf(".");

                                    brFileName = filesAre[i].substring(0, dotIndex);
                                    JAI.create("filestore", image, directory + "/" + branchFolderName + "/" + brFileName + ".jpg", "JPEG");
                                    parBrFile = new File(directory + "/" + branchFolderName + "/" + filesAre[i]);
                                    parBrFile.delete();
                                }
                            }
                        } catch (Exception e) {
                            this.setMessage(e.getMessage());
                        } finally {
                            if (stream == null) {
                                throw new ApplicationException("BR or BF type files are not found");
                            } else {
                                stream.close();
                            }
                        }
                    }

                    String txtFilePath = "", pxfFilePath = "";
                    if (fts.getCodeForReportName("CTS-SPONSOR") == 1) {
                        txtFilePath = directory + "/" + branchFolderName + "/" + arr[0];
                    } else {
                        txtFilePath = directory + "/" + branchFolderName + "/" + arr[0];
                        pxfFilePath = directory + "/" + branchFolderName + "/" + arr[1];
                    }

                    String result = ctsTxnRemote.uploadDataInCell(txtFilePath, pxfFilePath, branchFolderName,
                            actualFileName, scheduleNo, getUserName());
                    if (result.equalsIgnoreCase("true")) {
                        this.setMessage("Data has been uploaded successfully");
                    } else {
                        this.setMessage(result);
                    }
                } catch (ApplicationException e) {
                    this.setMessage(e.getMessage());
                } catch (Exception ex) {
                    this.setMessage(ex.getMessage());
                }
            } else {
                this.setMessage("Please select appropriate file to upload");
                return;
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
//                    String fileName = "";
                    String fileType = item.getName().substring(item.getName().indexOf(".") + 1);
                    String fileName = item.getName().substring(item.getName().lastIndexOf("/") + 1, item.getName().indexOf("."));
//                    int idx = 0;
//                    if (item.getName().indexOf("/") > 0) {
//                        idx = item.getName().indexOf("/") + 1;
//                    }
//                    if (idx > 0) {
//                        fileName = item.getName().substring(idx, 15) + "01";
//                    } else {
//                        fileName = item.getName().substring(idx, 11) + "01";
//                    }
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

    public void refreshForm() {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        String date = sdf.format(dt);
        this.setTodayDate(getTodayDate());
        this.setMessage("Please select branch...");
    }

    public String exitForm() {
        return "case1";
    }
}
