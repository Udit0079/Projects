/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.TDLienMarkingFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.utils.Base64;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.faces.model.SelectItem;
import org.apache.myfaces.custom.fileupload.UploadedFile;

/**
 *
 * @author root
 */
public class SignatureUpload extends BaseBean {

    private UploadedFile uploadedFile;
    private String msg;
    private String successMsg;
    Date dt = new Date();
    private final String jndiHomeName = "TDLienMarkingFacade";
    private TDLienMarkingFacadeRemote isr = null;
    private String custId;
    private List<SelectItem> classificationList;
    private String classification;
    private List<SelectItem> addTypeList;
    private String aadType;
    private boolean disableFlag;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public List<SelectItem> getClassificationList() {
        return classificationList;
    }

    public void setClassificationList(List<SelectItem> classificationList) {
        this.classificationList = classificationList;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getAadType() {
        return aadType;
    }

    public void setAadType(String aadType) {
        this.aadType = aadType;
    }

    public List<SelectItem> getAddTypeList() {
        return addTypeList;
    }

    public void setAddTypeList(List<SelectItem> addTypeList) {
        this.addTypeList = addTypeList;
    }

    public boolean isDisableFlag() {
        return disableFlag;
    }

    public void setDisableFlag(boolean disableFlag) {
        this.disableFlag = disableFlag;
    }

    /**
     * Creates a new instance of SignatureUpload
     */
    public SignatureUpload() {
        try {
            setDisableFlag(true);
            classificationList = new ArrayList<SelectItem>();
            classificationList.add(new SelectItem("Select", "--Select--"));
            //          classificationList.add(new SelectItem("POI", "POI"));
            //           classificationList.add(new SelectItem("POA", "POA"));
            classificationList.add(new SelectItem("SIG", "Signature"));
            classificationList.add(new SelectItem("CKYCRIMAGE", "CKYCR Image"));

            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            this.setMsg("Click on browse button to upload the file...Like jpg, jpeg, png or gif type.");
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void setDisableTrue() {
        this.setMsg("");
        this.setSuccessMsg("");
        if (this.classification.equalsIgnoreCase("SIG")) {
            addTypeList = new ArrayList<SelectItem>();
            setDisableFlag(true);
            this.custId = "";
            addTypeList.add(new SelectItem("Select", "--Select--"));
            addTypeList.add(new SelectItem("SIG", "Signature"));
            this.setMsg("Signature file name must be valide account number.");
        } else {
            setDisableFlag(false);
        }
    }

    public void getTypeListValue() {
        this.setSuccessMsg("");
        Pattern pattern = Pattern.compile("\\d+");
        if (custId == null || custId.equalsIgnoreCase("")) {
            this.setMsg("Please enter Customer Id !");
            return;
        } else if (!pattern.matcher(custId).matches()) {
            this.setMsg("Customer Id can only numeric value !");
            return;
        } else {
            try {
                isr = (TDLienMarkingFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
                List list = isr.getDocumentType(custId, classification);
                if (!list.isEmpty()) {
                    addTypeList = new ArrayList<SelectItem>();
                    addTypeList.add(new SelectItem("Select", "--Select--"));
                    for (int i = 0; i < list.size(); i++) {
                        Vector ele1 = (Vector) list.get(i);
                        addTypeList.add(new SelectItem(ele1.get(0).toString(), ele1.get(1).toString()));
                    }
                }
            } catch (ApplicationException e) {
                this.setMsg(e.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                this.setMsg(ex.getMessage());
            }
        }
    }

    public void resetMsg() {
        this.setSuccessMsg("");
    }

    public void uploadProcess() {
        long fileSize = 200000; // size in byte 
        this.setMsg("");
        Pattern pattern = Pattern.compile("\\d+");
        if (this.classification.equalsIgnoreCase("Select")) {
            this.setSuccessMsg("");
            this.setMsg("Please select classification !");
            return;
        }
        if (this.aadType.equalsIgnoreCase("Select")) {
            this.setSuccessMsg("");
            this.setMsg("Please select type !");
            return;
        }
        if (uploadedFile == null) {
            this.setSuccessMsg("");
            this.setMsg("Please select appropriate file to upload !");
            return;
        }
        if (uploadedFile.getSize() > fileSize) {
            this.setSuccessMsg("");
            this.setMsg("File size should be less than equal 200kb !");
            return;
        }
        String uploadedFileType = uploadedFile.getContentType();
        if (uploadedFileType.equals("image/jpeg") || uploadedFileType.equals("image/gif") || uploadedFileType.equals("image/png") || uploadedFileType.equals("image/jpg")) {
            if (this.aadType.equalsIgnoreCase("SIG")) {
                String uploadedFileName = uploadedFile.getName();
                if (uploadedFileName == null || uploadedFileName.equalsIgnoreCase("")) {
                    this.setSuccessMsg("");
                    this.setMsg("File name should be proper");
                    return;
                }
                String actualFileName = uploadedFileName.trim().substring(0, uploadedFileName.indexOf("."));
                int actualFileLength = actualFileName.length();
                if (actualFileLength < 12 || actualFileLength > 12) {
                    this.setSuccessMsg("");
                    this.setMsg("Signature file name must be valide account number !");
                    return;
                }
                try {
                    File dir = null;
                    String osName = System.getProperty("os.name");
                    if (osName.equals("Linux")) {
                        dir = new File("/install/images");
                    } else {
                        dir = new File("C:/images");
                    }
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    File file1 = new File(dir.getCanonicalPath() + File.separatorChar + uploadedFileName);
                    /**
                     * *Writing zip file into filesystem**
                     */
                    byte[] b = uploadedFile.getBytes();
                    FileOutputStream fos = new FileOutputStream(file1);
                    fos.write(b);
                    fos.close();
                    /**
                     * *End here**
                     */
                    String enterBy = this.getUserName();
                    isr = (TDLienMarkingFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
                    String result = isr.saveSingleImg(file1, enterBy);
                    if (result.equalsIgnoreCase("true")) {
                        clear();
                        this.setSuccessMsg("Image has been saved successfully.");
                        delete(dir);
                    } else {
                        this.setMsg("Image was not saved !");
                        return;
                    }
                } catch (ApplicationException e) {
                    this.setMsg(e.getMessage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    this.setMsg(ex.getMessage());
                }
            } else {

                if (custId == null || custId.equalsIgnoreCase("")) {
                    this.setMsg("Please enter Customer Id !");
                    return;
                }
                if (!pattern.matcher(custId).matches()) {
                    this.setMsg("Customer Id can only numeric value !");
                    return;
                }
                try {
                    if (isr.checkPrimaryBrCode(custId, getOrgnBrCode()).equalsIgnoreCase("false")) {
                        this.setMsg("You can upload the CKYC image of only your branch");
                        return;
                    }
                    File dir = null;
                    String osName = System.getProperty("os.name");
                    if (osName.equals("Linux")) {
                        dir = new File("/" + ftsPostRemote.getBankCode() + "/CI/" + custId);
                    } else {
                        dir = new File("C:/" + ftsPostRemote.getBankCode() + "/CI/" + custId);
                    }
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    byte[] bytes = uploadedFile.getBytes();
                    String encBytes = Base64.encodeBytes(bytes);
                    String filePath = dir.getCanonicalPath() + "/" + aadType;
                    Boolean flag = CbsUtil.generateXML(filePath, "Img", encBytes);
                    if (flag == true) {
                        isr = (TDLienMarkingFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
                        String result = isr.saveCkycImage(custId, aadType, this.getUserName());
                        if (result.equalsIgnoreCase("true")) {
                            clear();
                            this.setSuccessMsg("Image has been saved successfully.");
                        } else {
                            this.setMsg("Image was not saved !");
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    this.setMsg(ex.getMessage());
                }
            }
        } else {
            this.setMsg("File must be jpg, jpeg, png or gif type !");
        }
    }

    public void unzipFolder(String zipFile, String destFolder) {
        ZipFile zf = null;
        InputStream is = null;
        FileOutputStream fos = null;
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
                    String newfilePath = dir + File.separator + item.getName();
                    File newFile = new File(newfilePath);
                    if (!newFile.getParentFile().exists()) {
                        newFile.getParentFile().mkdirs();
                    }

                    is = zf.getInputStream(item);
                    fos = new FileOutputStream(newfilePath);
                    int ch;
                    while ((ch = is.read()) != -1) {
                        fos.write(ch);
                    }
                }
            }
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        } finally {
            try {
                is.close();
                fos.close();
                zf.close();
            } catch (Exception e) {
                this.setMsg(e.getMessage());
            }
        }
    }

    public static void delete(File file) throws IOException {
        if (file.isDirectory()) {
            if (file.list().length == 0) {
                file.delete();
            } else {
                String files[] = file.list();
                for (String temp : files) {
                    File fileDelete = new File(file, temp);
                    delete(fileDelete);
                }
                if (file.list().length == 0) {
                    file.delete();
                }
            }
        } else {
            file.delete();
        }
    }

    public void refreshForm() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(dt);
        this.setTodayDate(date);
        this.setMsg("Click on browse button to upload the file...Like jpg, jpeg, png or gif type.");
        this.setSuccessMsg("");
        setCustId("");
        this.classification = "Select";
        addTypeList = null;
    }

    public void clear() {
        this.setMsg("");
        setCustId("");
        this.classification = "Select";
        addTypeList = null;
    }

    public String exitForm() {
        return "case1";
    }
}
