/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.dto.NpciInwardDto;
import com.cbs.entity.sms.MbSmsSenderBankDetail;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.neftrtgs.UploadNeftRtgsMgmtFacadeRemote;
import com.cbs.facade.other.NpciMgmtFacadeRemote;
import com.cbs.facade.other.NpciYojnaMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ParseFileUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.SignUtil;
import com.cbs.web.controller.BaseBean;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import org.apache.myfaces.custom.fileupload.UploadedFile;

public class UploadNpciInward extends BaseBean {

    private String errMessage;
    private UploadedFile uploadedFile;
    private NpciMgmtFacadeRemote npciFacade = null;
    private UploadNeftRtgsMgmtFacadeRemote neftRemote = null;
    private CommonReportMethodsRemote reportRemote = null;
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    private NpciYojnaMgmtFacadeRemote npciYojnaRemote = null;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymdOne = new SimpleDateFormat("ddMMyyyy");
    private static final Object LOCK = new Object();

    public UploadNpciInward() {
        try {
            this.setErrMessage("Click on browse button to upload the file...");
            npciFacade = (NpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciMgmtFacade");
            neftRemote = (UploadNeftRtgsMgmtFacadeRemote) ServiceLocator.getInstance().lookup("UploadNeftRtgsMgmtFacade");
            reportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            npciYojnaRemote = (NpciYojnaMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciYojnaMgmtFacade");
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void uploadProcess() {
        this.setErrMessage("");
        if (uploadedFile == null) {
            System.out.println("Uploded File is null here");
            this.setErrMessage("Please select appropriate file to upload !");
            return;
        }
        String uploadedFileName = uploadedFile.getName();
        if (uploadedFileName == null || uploadedFileName.equalsIgnoreCase("")) {
            System.out.println("Uploded File Name is null or blank here");
            this.setErrMessage("Please select appropriate file to upload !");
            return;
        }
        System.out.println("Uploaded File Name is-->" + uploadedFileName);
        String fileContentType = uploadedFile.getContentType();
        System.out.println("File content type " + fileContentType);

        if (!(uploadedFileName.toLowerCase().contains("apb-cr") //aadhar credit
                || (uploadedFileName.toLowerCase().contains("av")
                && uploadedFileName.toLowerCase().contains("inp.txt")) //a/c validation inward
                || uploadedFileName.toLowerCase().contains("ach-cr") //non-aadhar credit
                || uploadedFileName.toLowerCase().contains("uid_response--ach-cm") //aadhar reg response
                || (uploadedFileName.toLowerCase().contains("av")
                && uploadedFileName.toLowerCase().contains("res.txt"))//nonaadhar reg response
                || (uploadedFileName.toLowerCase().contains("ecs-cr") //ecs credit inward
                && uploadedFileName.toLowerCase().contains("inw.txt"))
                || (uploadedFileName.toLowerCase().contains("oac") //oac incoming
                && uploadedFileName.toLowerCase().contains("inp.txt"))
                || (uploadedFileName.toLowerCase().contains("ecs-dr") //ecs dr
                && uploadedFileName.toLowerCase().contains("inw.txt"))
                || (uploadedFileName.toLowerCase().contains("ach-dr") //ach dr 306 format
                && uploadedFileName.toLowerCase().contains("inw.txt"))
                || (uploadedFileName.toLowerCase().contains("ach-dr") //ach dr Response 306 format
                && uploadedFileName.toLowerCase().contains("res.txt"))
                || (uploadedFileName.toLowerCase().contains("ecs-dr") //ECS dr Response 156 format
                && uploadedFileName.toLowerCase().contains("res.txt")))) {
            this.setErrMessage("This is not a valid aadhaar/non-aadhaar inward file.");
            return;
        }
        try {
            String alphaCode = reportRemote.getAlphacodeByBrncode(getOrgnBrCode());
            if (!alphaCode.equalsIgnoreCase("HO")) {
                this.setErrMessage("Aadhaar/Non-Aadhaar related any work will be perform from HO.");
                return;
            }
            List<MbSmsSenderBankDetail> bankList = neftRemote.getBankCode();
            if (bankList == null || bankList.isEmpty()) {
                this.setErrMessage("Please define bank code.");
                return;
            }
            ServletContext context = (ServletContext) getFacesContext().getCurrentInstance().
                    getExternalContext().getContext();
//            String directory = context.getInitParameter("cts");
            String directory = context.getInitParameter("cbsFiles");
            File dir = new File(directory + "/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File inwFile = new File(dir.getCanonicalPath() + File.separatorChar + uploadedFileName);
            if (!(fileContentType.equals("text/plain")
                    || fileContentType.equals("application/octet-stream")
                    || fileContentType.equals("text/xml"))) {
                this.setErrMessage("It is not a proper file type.");
                return;
            }
            List<NpciInwardDto> pojoList = new ArrayList<NpciInwardDto>();
            try {
                byte[] b = uploadedFile.getBytes();
                FileOutputStream fos = new FileOutputStream(inwFile);
                fos.write(b);
                fos.close();
                if (ftsRemote.getCodeForReportName("DG-SIGN-UTILITY") == 1) {
                    new SignUtil().parseSignedFile(dir.getCanonicalPath() + File.separatorChar + uploadedFileName, true, dir.getCanonicalPath() + File.separatorChar + uploadedFileName, false);
                }

                if (uploadedFileName.toLowerCase().contains("apb-cr")) {
                    pojoList = ParseFileUtil.parseNpciInwardTxtFile(inwFile);
                } else if (uploadedFileName.toLowerCase().contains("av")
                        && uploadedFileName.toLowerCase().contains("inp.txt")) {
                    String fileSeqNo = uploadedFileName.split("-")[5].trim();
                    pojoList = ParseFileUtil.parseNpciNonAadhaarInwardTxtFile(inwFile, fileSeqNo);
                } else if (uploadedFileName.toLowerCase().contains("ach-cr")) {
                    if (uploadedFileName.toLowerCase().contains("ecs")) {
                        //Work here
                        String fileSeqNo = uploadedFileName.split("-")[4].trim();
                        pojoList = ParseFileUtil.parseEcsNewTxtFile(inwFile, fileSeqNo);    //For ACH ECS of 306 format
                    } else if (uploadedFileName.toLowerCase().contains("cdr")
                            || uploadedFileName.toLowerCase().contains("cwr")
                            || uploadedFileName.toLowerCase().contains("cda")
                            || uploadedFileName.toLowerCase().contains("cwa")) { //Customer daily/weekly rupay,Customer daily/weekly aeps
                        pojoList = ParseFileUtil.parseCDRCWRCDAAndCWA(inwFile);
                    } else {
                        String fileSeqNo = uploadedFileName.split("-")[4].trim();
                        pojoList = ParseFileUtil.parseAchInwardTxtFile(inwFile, fileSeqNo); //For ACH
                    }
                } else if (uploadedFileName.toLowerCase().contains("uid_response--ach-cm")) {
                    pojoList = ParseFileUtil.parseNpciMapperResponse(inwFile);
                } else if (uploadedFileName.toLowerCase().contains("av")
                        && uploadedFileName.toLowerCase().contains("res.txt")) {
                    //Required Values Extraction.
                    String headerId = npciFacade.getParameterInfoReportValue("NA-HI");
                    String detailId = npciFacade.getParameterInfoReportValue("NA-DRI");
                    if (headerId.equals("") || detailId.equals("")) {
                        this.setErrMessage("Please define parameter value for NA-HI and NA-DRI.");
                        return;
                    }
                    pojoList = ParseFileUtil.parseNpciAvResponse(inwFile, headerId, detailId);
                } else if (uploadedFileName.toLowerCase().contains("ecs-cr") //ECS credit inward
                        && uploadedFileName.toLowerCase().contains("inw.txt")) {
                    String fileSeqNo = uploadedFileName.split("-")[4].trim();
                    pojoList = ParseFileUtil.parseNpciCecsCreditInwardTxtFile(inwFile, fileSeqNo);
                } else if (uploadedFileName.toLowerCase().contains("oac") //oac incoming
                        && uploadedFileName.toLowerCase().contains("inp.txt")) {
                    String fileSeqNo = uploadedFileName.split("-")[5].trim();
                    pojoList = ParseFileUtil.parseOacTxtFile(inwFile, fileSeqNo);
                } else if (uploadedFileName.toLowerCase().contains("ecs-dr") //ecs dr
                        && uploadedFileName.toLowerCase().contains("inw.txt")) {
                    String fileNameDt = uploadedFileName.split("-")[3].trim();
                    String fileSeqNo = uploadedFileName.split("-")[4].trim();
                    pojoList = ParseFileUtil.parseEcsDrTxtFile(inwFile, fileSeqNo, fileNameDt);
                } else if (uploadedFileName.toLowerCase().contains("ach-dr") //ach dr 306 format
                        && (uploadedFileName.toLowerCase().contains("inw.txt"))) {
                    //Parsing of ACH DR
                    String fileSeqNo = uploadedFileName.split("-")[4].trim();
                    pojoList = ParseFileUtil.parseAchDrNewTxtFile(inwFile, fileSeqNo);
                } else if (uploadedFileName.toLowerCase().contains("ach-dr") //ach dr Response 306 format
                        && (uploadedFileName.toLowerCase().contains("res.txt"))) {
                    //Parsing of ACH DR
                    String fileSeqNo = uploadedFileName.split("-")[5].trim();
                    pojoList = ParseFileUtil.parseAchDrResponseNewTxtFile(inwFile, fileSeqNo);
                } else if (uploadedFileName.toLowerCase().contains("ecs-dr") //ecs dr Response 156 format
                        && (uploadedFileName.toLowerCase().contains("res.txt"))) {
                    //Parsing of ECS DR
                    String fileSeqNo = uploadedFileName.split("-")[5].trim();
                    pojoList = ParseFileUtil.parseEcsDrResponseNewTxtFile(inwFile, fileSeqNo);
                }
            } catch (Exception e) {
                this.setErrMessage("Proble in reading the Text File." + e.getMessage());
            }
            //Processing of inward files.
            String result = "";
            if (uploadedFileName.toLowerCase().contains("apb-cr")) {    //APBS Inward
                String fileName = uploadedFileName.substring(0, uploadedFileName.indexOf("."));
                synchronized (LOCK) {
                    result = npciFacade.npciInwardUpload(pojoList, getOrgnBrCode(), getUserName(), getTodayDate(), fileName, "");
                }
            } else if (uploadedFileName.toLowerCase().contains("av")
                    && uploadedFileName.toLowerCase().contains("inp.txt")) { //Non-Aadhar A/c Verification Inward
                synchronized (LOCK) {
                    result = npciFacade.npciNonAadhaarInwardUpload(pojoList, getOrgnBrCode(), getUserName(), getTodayDate(), "", uploadedFileName);
                }
            } else if (uploadedFileName.toLowerCase().contains("ach-cr")) {  //ACH Inward
                if (uploadedFileName.toLowerCase().contains("ecs")) {
                    //work here
                    synchronized (LOCK) {
                        result = npciFacade.npciCECSCreditInwardUpload(pojoList, getOrgnBrCode(), getTodayDate(), getUserName(), "");    //For ECS Credit new 306 format
                    }
                } else if (uploadedFileName.toLowerCase().contains("cdr")
                        || uploadedFileName.toLowerCase().contains("cwr")
                        || uploadedFileName.toLowerCase().contains("cda")
                        || uploadedFileName.toLowerCase().contains("cwa")) { //Customer daily/weekly rupay,Customer daily/weekly aeps
                    String fileName = uploadedFileName.substring(0, uploadedFileName.indexOf("."));
                    synchronized (LOCK) {
                        result = npciYojnaRemote.uploadingOfYojnaSchemes(pojoList, getOrgnBrCode(), getUserName(), getTodayDate(), fileName);
                    }
                } else {
                    String fileName = uploadedFileName.substring(0, uploadedFileName.indexOf("."));
                    synchronized (LOCK) {
                        result = npciFacade.newNpciAchInwardUpload(pojoList, getOrgnBrCode(), getUserName(), getTodayDate(), fileName, ""); //For ACH Inward
                    }
                }
            } else if (uploadedFileName.toLowerCase().contains("uid_response--ach-cm")) { //Aadhar Registration Response
                synchronized (LOCK) {
                    result = npciFacade.npciAadharResponseUpload(pojoList, getUserName(), getTodayDate(), uploadedFileName);
                }
            } else if (uploadedFileName.toLowerCase().contains("av")
                    && uploadedFileName.toLowerCase().contains("res.txt")) { //Non-Aadhar Registration Response
                synchronized (LOCK) {
                    result = npciFacade.npciNonAadharResponseUpload(pojoList, getUserName(), getTodayDate(), uploadedFileName);
                }
            } else if (uploadedFileName.toLowerCase().contains("ecs-cr") //ecs credit inward
                    && uploadedFileName.toLowerCase().contains("inw.txt")) {
                synchronized (LOCK) {
                    result = npciFacade.npciCECSCreditInwardUpload(pojoList, getOrgnBrCode(), getTodayDate(), getUserName(), "");
                }
            } else if (uploadedFileName.toLowerCase().contains("oac") //oac incoming
                    && uploadedFileName.toLowerCase().contains("inp.txt")) {
                synchronized (LOCK) {
                    result = npciFacade.npciOacUpload(pojoList, getOrgnBrCode(), getTodayDate(), getUserName(), "NPCI-OAC", "");
                }
            } else if (uploadedFileName.toLowerCase().contains("ecs-dr") //ecs dr
                    && uploadedFileName.toLowerCase().contains("inw.txt")) {
                synchronized (LOCK) {
                    result = npciFacade.npciEcsDrUpload(pojoList, getOrgnBrCode(), getTodayDate(), getUserName(), "ECS-DR", "", "");
                }
            } else if (uploadedFileName.toLowerCase().contains("ach-dr") //ach dr 306 format
                    && uploadedFileName.toLowerCase().contains("inw.txt")) {
                //Uploading of ACH DR
                synchronized (LOCK) {
                    result = npciFacade.npciAchDr306Upload(pojoList, getOrgnBrCode(), getTodayDate(),
                            getUserName(), "ACH-DR", "", ""); //Previous was ECS-DR
                }
            } else if (uploadedFileName.toLowerCase().contains("ach-dr") //ach dr response 306 format
                    && uploadedFileName.toLowerCase().contains("res.txt")) {
                //Uploading of ACH DR Response
                synchronized (LOCK) {
                    result = npciFacade.npciAchDrResponse306Upload(pojoList, getOrgnBrCode(), getTodayDate(), getUserName(), inwFile.getCanonicalPath(), uploadedFileName, "");
                }
            } else if (uploadedFileName.toLowerCase().contains("ecs-dr") //ecs dr response 156 format
                    && uploadedFileName.toLowerCase().contains("res.txt")) {
                //Uploading of ECS DR Response
                synchronized (LOCK) {
                    result = npciFacade.npciECSDrResponse156Upload(pojoList, getOrgnBrCode(), getTodayDate(), getUserName(), inwFile.getCanonicalPath(), uploadedFileName, "");
                }
            }
            if (!result.equals("true")) {
                this.setErrMessage(result);
                return;
            }
            this.setErrMessage("Data has been uploded successfully.");
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void refreshForm() {
        this.setErrMessage("Click on browse button to upload the file...");
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }

    //Getter And Setter
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
}
