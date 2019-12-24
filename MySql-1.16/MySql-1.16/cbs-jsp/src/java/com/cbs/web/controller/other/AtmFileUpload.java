/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.dto.AtmCardMappingPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.NpciMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.SignUtil;
import com.cbs.web.controller.BaseBean;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.servlet.ServletContext;
import org.apache.myfaces.custom.fileupload.UploadedFile;

/**
 *
 * @author root
 */
public class AtmFileUpload extends BaseBean {

    private String errMessage;
    private String fileType;
    private UploadedFile uploadedFile;
    private NpciMgmtFacadeRemote npciFacade = null;
    private CommonReportMethodsRemote reportRemote = null;
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymdOne = new SimpleDateFormat("ddMMyyyy");
    SimpleDateFormat ymmd = new SimpleDateFormat("dd-MM-yyyy");

    public AtmFileUpload() {
        try {
            reportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            npciFacade = (NpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciMgmtFacade");
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void uploadProcess() {
        this.setErrMessage("");
        try {
            if (uploadedFile == null) {
                this.setErrMessage("Please select appropriate file to upload !");
                return;
            }
            if (!getOrgnBrCode().equalsIgnoreCase("90")) {
                this.setErrMessage("This file upload only Head Office Level.");
                return;
            }
            String uploadedFileName = uploadedFile.getName();
            String fileContentType = uploadedFile.getContentType();
            System.out.println("Uploaded File Name is-->" + uploadedFileName + "::::File content type " + fileContentType);

            if (!uploadedFileName.toLowerCase().contains("csv")) {
                this.setErrMessage("This is not a valid uploaded file.");
                return;
            }

            if (!(fileContentType.equals("text/csv")
                    || fileContentType.equals("application/octet-stream")
                    || fileContentType.equals("application/vnd.ms-excel"))) {
                this.setErrMessage("It is not a proper file type.");
                return;
            }

            String alphaCode = reportRemote.getAlphacodeByBrncode(getOrgnBrCode());
            if (!alphaCode.equalsIgnoreCase("HO")) {
                this.setErrMessage("File will only upload from Head Office.");
                return;
            }
            ServletContext context = (ServletContext) getFacesContext().getCurrentInstance().getExternalContext().getContext();
            String directory = context.getInitParameter("cbsFiles");
            File dir = new File(directory + "/" + "ATM");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File inwFile = new File(dir.getCanonicalPath() + File.separatorChar + uploadedFileName);
            List<AtmCardMappingPojo> pojoList = new ArrayList<>();
            try {
                byte[] b = uploadedFile.getBytes();
                FileOutputStream fos = new FileOutputStream(inwFile);
                fos.write(b);
                fos.close();
//                if (ftsRemote.getCodeForReportName("DG-SIGN-UTILITY") == 1) {
//                    new SignUtil().parseSignedFile(dir.getCanonicalPath() + File.separatorChar + uploadedFileName, true, dir.getCanonicalPath() + File.separatorChar + uploadedFileName, false);
//                }
                pojoList = parseCsvFile(inwFile, uploadedFileName);
                for (AtmCardMappingPojo item : pojoList) {
                    List isAcNoExits = npciFacade.getAcNoInAtmCardMaster(item.getAccountNo());
                    if (!isAcNoExits.isEmpty()) {
                        String resultUpdate = npciFacade.updateAtmCardNoViaFile(item.getCardNo(), item.getAccountNo(), getUserName());
                        if (!resultUpdate.equalsIgnoreCase("true")) {
                            this.setErrMessage("Problem in card No. updation.");
                        }
                    }
                }
                this.setErrMessage("Data has been uploaded successfully.");
            } catch (Exception e) {
                this.setErrMessage(e.getMessage());
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    private List<AtmCardMappingPojo> parseCsvFile(File csvFile, String iwFileName) throws IOException, ApplicationException {
        FileInputStream fis = null;
        DataInputStream dis = null;
        BufferedReader br = null;
        try {
            List<AtmCardMappingPojo> pojoList = new ArrayList<AtmCardMappingPojo>();
            byte[] b = uploadedFile.getBytes();
            FileOutputStream fos = new FileOutputStream(csvFile);
            fos.write(b);
            fos.close();

            fis = new FileInputStream(csvFile);
            dis = new DataInputStream(fis);
            br = new BufferedReader(new InputStreamReader(dis));
            String readLine = "";
            String crd = "";
            while ((readLine = br.readLine()) != null) {
                String[] readArray = readLine.split(",");
                if (!readArray[0].equalsIgnoreCase("ApplNo")) {
                    AtmCardMappingPojo pojo = new AtmCardMappingPojo();
                    pojo.setApplNo(readArray[0].toString());
                    pojo.setCustNo(readArray[1].toString());
                    pojo.setCustName(readArray[2].toString());
                    if (readArray[3].toString().trim().contains("'")) {
                        crd = readArray[3].toString().replace("'", "");
                    } else {
                        crd = readArray[3].toString().trim();
                    }
                    pojo.setCardNo(crd);
                    pojo.setAccountNo(readArray[4].toString());
                    String date = CbsUtil.getMonthnoFromMonthName(readArray[5].toString());
                    pojo.setApplDate(ymd.format(ymmd.parse(date)));
                    pojo.setIssueDate(ymd.format(ymmd.parse(CbsUtil.getMonthnoFromMonthName(readArray[6].toString()))));
                    pojo.setExpiryDate(ymd.format(ymmd.parse(CbsUtil.getMonthnoFromMonthName(readArray[7].toString()))));
                    pojo.setAdd1(readArray[8].toString());
                    pojo.setAdd2(readArray[9].toString());
                    pojo.setAdd3(readArray[10].toString());
                    pojo.setCardStatus(readArray[11].toString());
                    if(readArray.length == 13){
                    pojo.setMobNo(readArray[12].toString()== null ? "" : readArray[12].toString());
                    }
                    pojoList.add(pojo);
                }
            }
            return pojoList;
        } catch (IOException ex) {
            throw new IOException("Invalid File Format " + ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException("Invalid File Format" + ex.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (dis != null) {
                dis.close();
            }
            if (br != null) {
                br.close();
            }
        }
    }

    public void refreshForm() {
        this.setErrMessage("");
        this.setFileType("0");
        this.setErrMessage("Please select file type.");
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

}
