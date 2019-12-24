/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.npci;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.NpciMandateFacadeRemote;
import com.cbs.facade.other.OtherNpciMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.SignUtil;
import com.cbs.web.controller.BaseBean;
import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.faces.context.FacesContext;
import javax.media.jai.JAI;
import javax.servlet.ServletContext;
import org.apache.myfaces.custom.fileupload.UploadedFile;

public class UploadMms extends BaseBean {

    private UploadedFile uploadedFile;
    private String message;
    private OtherNpciMgmtFacadeRemote otherNpciRemote = null;
    private NpciMandateFacadeRemote mandateRemote = null;
    private FtsPostingMgmtFacadeRemote FtsPostMgmtRemote = null;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public UploadMms() {
        try {
            this.setMessage("Please select your file...");
            otherNpciRemote = (OtherNpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("OtherNpciMgmtFacade");
            mandateRemote = (NpciMandateFacadeRemote) ServiceLocator.getInstance().lookup("NpciMandateFacade");
            FtsPostMgmtRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");

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
            String uploadedFileName = uploadedFile.getName();    //With extension
            if (uploadedFileName == null || uploadedFileName.equalsIgnoreCase("")) {
                this.setMessage("Please select appropriate file to upload");
                return;
            }
            String actualFileName = uploadedFileName.trim().substring(0, uploadedFileName.indexOf(".")); //Without extension
            String fileContentType = uploadedFile.getContentType();
            System.out.println("The Content Tpye of the uploded file is >>>>>>>>>>>>>>>>>>>>>>>>>> " + fileContentType);
            if (fileContentType.equals("application/x-zip") || fileContentType.equals("application/zip")
                    || fileContentType.equals("application/x-zip-compressed")
                    || fileContentType.equals("application/octet-stream") || fileContentType.equals("text/xml")) {
                try {
                    ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                    String mmsDirectory = context.getInitParameter("mms");

                    if (uploadedFileName.toLowerCase().trim().contains("inp-ack")
                            || uploadedFileName.toLowerCase().trim().contains("res")) { //Mandate Initiation Ack And Res
                        File dir = new File(mmsDirectory + "/");
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        File zipFile = new File(dir.getCanonicalPath() + "/" + uploadedFileName);
                        //Writing zip file into filesystem**
                        byte[] b = uploadedFile.getBytes();
                        FileOutputStream fos = new FileOutputStream(zipFile);
                        fos.write(b);
                        fos.close();

                        if (FtsPostMgmtRemote.getCodeForReportName("DG-SIGN-UTILITY") == 1) {
                            new SignUtil().parseSignedFile(zipFile.getCanonicalPath(), true, zipFile.getCanonicalPath(), false);
                        }
                        //Unzipping the folder
                        File unzippedFolder = new File(mmsDirectory + "/" + actualFileName);
                        if (!unzippedFolder.exists()) {
                            unzippedFolder.mkdirs();
                        }
                        unzipFolder(zipFile.getCanonicalPath(), unzippedFolder + "/");
                        //Now ack and res file processing
                        String result = mandateRemote.mmsAcknowledgementAndResponseUpdation(unzippedFolder, uploadedFileName, getUserName());
                        if (!result.equalsIgnoreCase("true")) {
                            this.setMessage("Issue in response updation.");
                            return;
                        }
                        this.setMessage("Ack/Response has been successfully updated.");
                    } else if (uploadedFileName.toLowerCase().trim().contains("inp.xml")
                            && uploadedFileName.toLowerCase().trim().contains("cancel")) {
                        context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                        String mmsLocation = context.getInitParameter("cbsFiles");
                        File dir = new File(mmsLocation + "/");
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        File unzippedFolder = new File(mmsLocation + "/" + actualFileName);
                        if (!unzippedFolder.exists()) {
                            unzippedFolder.mkdirs();
                        }
                        File zipFile = new File(unzippedFolder.getCanonicalPath() + "/" + uploadedFileName);
                        //writing zip file into filesystem
                        byte[] b = uploadedFile.getBytes();
                        FileOutputStream fos = new FileOutputStream(zipFile);
                        //unzipped the folder
                        fos.write(b);
                        fos.close();
//                       unzipFolder(zipFile.getCanonicalPath(),unzippedFolder +"/");
                        String result = mandateRemote.mmsxmlandCancelUpdation(unzippedFolder, uploadedFileName, getUserName());
                        if (!result.equalsIgnoreCase("true")) {
                            this.setMessage("Issue in response updation.");
                            return;
                        }
                        this.setMessage("Cancel/Response has been successfully updated.");
                    } else { //Inward mandate as destination either it will be legacy or new or esign
                        List list = otherNpciRemote.isFileUploaded(uploadedFileName);
                        if (!list.isEmpty()) {
                            throw new ApplicationException("This zip file has been already uploaded.");
                        }
                        String mMode = "";
                        if (uploadedFileName.toUpperCase().contains("LEGACY")) {
                            mMode = "L";
                        } else if (uploadedFileName.toUpperCase().contains("ESIGN")) {
                            mMode = "E";
                        } else {
                            mMode = "N";
                        }
                        String currentDt = ymd.format(new Date());
                        Integer fileNo = otherNpciRemote.retrieveFileNo(actualFileName.split("-")[1], currentDt, "");
                        String imageUploadFolder = "";
                        if (mMode.equalsIgnoreCase("L")) {
                            imageUploadFolder = currentDt + "/" + fileNo.toString() + "_" + actualFileName.trim().split("-")[1] + "_LEGACY" + "/";
                        } else if (mMode.equalsIgnoreCase("N")) {
                            imageUploadFolder = currentDt + "/" + fileNo.toString() + "_" + actualFileName.trim().split("-")[1] + "/";
                        } else if (mMode.equalsIgnoreCase("E")) {
                            imageUploadFolder = currentDt + "/" + fileNo.toString() + "_" + actualFileName.trim().split("-")[1] + "_ESIGN" + "/";
                        }
                        File dir = new File(mmsDirectory + "/" + imageUploadFolder);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        File zipFile = new File(dir.getCanonicalPath() + File.separator + uploadedFileName);
                        //Writing zip file into filesystem**
                        byte[] b = uploadedFile.getBytes();
                        FileOutputStream fos = new FileOutputStream(zipFile);
                        fos.write(b);
                        fos.close();
                        if (FtsPostMgmtRemote.getCodeForReportName("DG-SIGN-UTILITY") == 1) {
                            new SignUtil().parseSignedFile(zipFile.getCanonicalPath(), true, zipFile.getCanonicalPath(), false);
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
                        File file = new File(dir + "/");
                        String[] filesAre = file.list();
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
                            this.setMessage(e.getMessage());
                        } finally {
                            if ((mMode.equalsIgnoreCase("L") || mMode.equalsIgnoreCase("E")) && stream != null) {
                                stream.close();
                            } else if (mMode.equalsIgnoreCase("N") && !(uploadedFileName.toUpperCase().contains("LVMWI")
                                    || uploadedFileName.toUpperCase().contains("CANCEL"))) {
                                //008 Circular- PMSYM (Pradhan Mantri Shram Yogi Maan-dhan)
                                if (stream == null) {
                                    throw new ApplicationException("tif type files are not found");
                                } else {
                                    stream.close();
                                }
                            }
                        }
                        String result = "";
                        if (mMode.equalsIgnoreCase("E")) {
                            result = otherNpciRemote.uploadEsignData(dir, currentDt, getUserName(), fileNo, uploadedFileName, mMode, "");
                        } else {
                            result = otherNpciRemote.uploadMmsData(dir, currentDt, getUserName(), fileNo, uploadedFileName, mMode, "");
                        }
                        if (!result.equalsIgnoreCase("true")) {
                            this.setMessage(result);
                            return;
                        }
                        this.setMessage("Data has been uploaded successfully");
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

    public void refreshForm() {
        this.setTodayDate(getTodayDate());
        this.setMessage("Please select your file...");
    }

    public String exitForm() {
        return "case1";
    }

    //Getter And Setter
    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
