/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.dto.NpciFileDto;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.NpciMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

public class NpciNonAadhaarRegistration extends BaseBean {

    private String errorMessage;
    private String fileType;
    private String lpgType;
    private String genDt;
    private String seqNo;
    private String seqEnable = "none";
    private String btnValue;
    private String confirmText;
    private NpciFileDto currentItem;
    private List<NpciFileDto> gridDetail;
    private List<SelectItem> fileTypeList;
    private List<SelectItem> lpgTypeList;
    private List<SelectItem> seqNoList;
    private NpciMgmtFacadeRemote npciFacade = null;
    private CommonReportMethodsRemote reportRemote = null;
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public NpciNonAadhaarRegistration() {
        try {
            npciFacade = (NpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciMgmtFacade");
            reportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            initData();
            btnRefreshAction();
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void initData() {
        fileTypeList = new ArrayList<SelectItem>();
        lpgTypeList = new ArrayList<SelectItem>();
        seqNoList = new ArrayList<SelectItem>();
        try {
            fileTypeList.add(new SelectItem("GI", "Generate Input File"));
            fileTypeList.add(new SelectItem("SI", "Show Input File"));
            fileTypeList.add(new SelectItem("GR", "Generate Return File"));
            fileTypeList.add(new SelectItem("SR", "Show Return File"));
            fileTypeList.add(new SelectItem("GC", "Generate CBDT File"));
            fileTypeList.add(new SelectItem("SC", "Show CBDT File"));
            

            List lpgList = npciFacade.getLPGTypeList("008");
            if (lpgList.isEmpty()) {
                this.setErrorMessage("Please define values in Cbs Ref Rec Type for 008");
                return;
            }
            lpgTypeList.add(new SelectItem("", "--Select--"));
            for (int i = 0; i < lpgList.size(); i++) {
                Vector vtr = (Vector) lpgList.get(i);
                lpgTypeList.add(new SelectItem(vtr.get(0).toString(), vtr.get(1).toString()));
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void onChangeType() {
        this.setErrorMessage("");
        try {
            if (this.fileType == null || this.fileType.equals("")) {
                this.setErrorMessage("Please select File Type.");
                return;
            }
            if (this.fileType.equals("GI")) {
                this.setBtnValue("Generate Input File");
            } else if (this.fileType.equals("SI")) {
                this.setBtnValue("Show Input File");
            } else if (this.fileType.equals("GR")) {
                this.setBtnValue("Generate Return File");
            } else if (this.fileType.equals("SR")) {
                this.setBtnValue("Show Return File");
            } else if(this.fileType.equals("GC")){
                this.setBtnValue("Generate CBDT File");
            } else if(this.fileType.equals("SC")){
                this.setBtnValue("Show CBDT File");
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void dateAction() {
        this.setErrorMessage("");
        try {
            seqNoList = new ArrayList<SelectItem>();
            this.seqEnable = "none";
            if (validateField()) {
                if (this.fileType.equals("GR") || this.fileType.equals("SR")
                        || this.fileType.equals("GC")||this.fileType.equals("SC")) {
                    this.seqEnable = "";
                    seqNoList.add(new SelectItem("--Select--"));
                    List list = npciFacade.getSeqNoForNonAadhaar(this.lpgType, this.genDt);
                    for (int i = 0; i < list.size(); i++) {
                        Vector ele = (Vector) list.get(i);
                        if (ele.get(0) == null) {
                            this.setErrorMessage("Seq no can not be null.");
                            return;
                        }
                        seqNoList.add(new SelectItem(ele.get(0).toString()));
                    }
                }
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public boolean validateField() {
        try {
            if (this.fileType == null || this.fileType.equals("")) {
                this.setErrorMessage("Please select File Type.");
                return false;
            }
            if (this.lpgType == null || this.lpgType.equals("")) {
                this.setErrorMessage("Please select LPG Type.");
                return false;
            }
            if (new Validator().validateDate_dd_mm_yyyy(this.genDt) == false) {
                this.setErrorMessage("Please select proper File Generation Date.");
                return false;
            }
            if (dmy.parse(this.genDt).after(dmy.parse(getTodayDate()))) {
                this.setErrorMessage("File Generation Date can not be greater than Current Date.");
                return false;
            }
            String alphaCode = reportRemote.getAlphacodeByBrncode(getOrgnBrCode());
            if (!alphaCode.equalsIgnoreCase("HO")) {
                this.setErrorMessage("Aadhaar related any work will be perform from HO.");
                return false;
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
        return true;
    }

    public void createConfirmTxt() {
        this.setErrorMessage("");
        this.setConfirmText("");
        try {
            if (this.fileType.equals("GI") && this.btnValue.equalsIgnoreCase("Generate Input File")) {
                this.setConfirmText("Are you sure to generate the Input files ?");
            } else if (this.fileType.equals("SI") && this.btnValue.equalsIgnoreCase("Show Input File")) {
                this.setConfirmText("Are you sure to show the Input files ?");
            } else if (this.fileType.equals("GR") && this.btnValue.equalsIgnoreCase("Generate Return File")) {
                this.setConfirmText("Are you sure to generate the Return files ?");
            } else if (this.fileType.equals("SR") && this.btnValue.equalsIgnoreCase("Show Return File")) {
                this.setConfirmText("Are you sure to show the Return files ?");
            } else if(this.fileType.equals("GC") && this.btnValue.equalsIgnoreCase("Generate CBDT File")){
                this.setConfirmText("Are you sure to generate the CBDT files ?");
            } else if(this.fileType.equals("SC")&& this.btnValue.equalsIgnoreCase("Show CBDT File")){
                this.setConfirmText("Are you sure to show the CBDT files ?");
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void processAction() {
        this.setErrorMessage("");
        try {
            if (validateField()) {
                if (this.fileType.equals("GI") && this.btnValue.equalsIgnoreCase("Generate Input File")) {
                    String result = npciFacade.generateNonAadhaarInput(this.lpgType, this.genDt, getOrgnBrCode(),
                            getUserName(), getTodayDate());
                    if (!result.equals("true")) {
                        this.setErrorMessage(result);
                        return;
                    }
                    btnRefreshAction();
                    this.setErrorMessage("Input file has been generated successfully.");
                } else if (this.fileType.equals("SI") && this.btnValue.equalsIgnoreCase("Show Input File")) {
                    gridDetail = npciFacade.showNonAadhaarGeneratedFiles(this.fileType, this.lpgType, this.genDt, 0d);
                    this.setErrorMessage("Now you can download a file from table.");
                } else if (this.fileType.equals("GR") && this.btnValue.equalsIgnoreCase("Generate Return File")) {
                    if (this.seqNo == null || this.seqNo.equals("")) {
                        this.setErrorMessage("Seq no can not be null or blank.");
                        return;
                    }
                    //fileGenerationDt is FILE_COMING_DT in cbs_npci_inward_non_aadhaar.
                    String result = npciFacade.generateNonAadhaarReturn(this.lpgType, this.genDt, getOrgnBrCode(), getUserName(),
                            getTodayDate(), this.seqNo,"","");
                    if (!result.equals("true")) {
                        this.setErrorMessage(result);
                        return;
                    }
                    btnRefreshAction();
                    this.setErrorMessage("Return file has been generated successfully.");
                } else if (this.fileType.equals("SR") && this.btnValue.equalsIgnoreCase("Show Return File")) {
                    if (this.seqNo == null || this.seqNo.equals("")) {
                        this.setErrorMessage("Seq no can not be null or blank.");
                        return;
                    }
                    gridDetail = npciFacade.showNonAadhaarGeneratedFiles(this.fileType, this.lpgType, this.genDt, Double.parseDouble(this.seqNo));
                    this.setErrorMessage("Now you can download a file from table.");
                
                } else if(this.fileType.equals("GC") && this.btnValue.equalsIgnoreCase("Generate CBDT File")){
                    if (this.seqNo == null || this.seqNo.equals("")) {
                        this.setErrorMessage("Seq no can not be null or blank.");
                        return;
                    }
                    String result =npciFacade.generateNonAadhaarCBDT(this.lpgType,this.genDt,getOrgnBrCode(), getUserName(),
                            getTodayDate(), this.seqNo);
                            
                    if (!result.equals("true")) {
                        this.setErrorMessage(result);
                        return;
                    }
                    btnRefreshAction();
                    this.setErrorMessage("CBDT file has been generated successfully.");
                }else if (this.fileType.equals("SC") && this.btnValue.equalsIgnoreCase("Show CBDT File")){
                       if (this.seqNo == null || this.seqNo.equals("")) {
                        this.setErrorMessage("Seq no can not be null or blank.");
                        return;
                    }
                    gridDetail = npciFacade.showNonAadhaarGeneratedFiles(this.fileType, this.lpgType, this.genDt, Double.parseDouble(this.seqNo));
                    this.setErrorMessage("Now you can download a file from table.");
                }
                
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void downloadFile() {
        try {
            if (currentItem == null) {
                this.setErrorMessage("Please download one file from table.");
                return;
            }
            HttpServletResponse res = (HttpServletResponse) getHttpResponse();
            String ctxPath = getFacesContext().getExternalContext().getRequestContextPath();

            List list = npciFacade.getBankDetails();
            Vector ele = (Vector) list.get(0);
            if (ele.get(8) == null) {
                this.setErrorMessage("Please define Aadhar Location in bank detail.");
                return;
            }
            String dirName = ele.get(8).toString().trim();
            String keyPwd = ftsRemote.getKeyPwd();
            
            if(!keyPwd.equals("")){
               res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirName + "&fileName=" + currentItem.getFileName()+ "&pwd=" + keyPwd);
            }else{
                res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirName + "&fileName=" + currentItem.getFileName());
            }
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setErrorMessage("");
        this.setFileType("GI");
        this.setLpgType("");
        this.setGenDt(getTodayDate());
        this.setBtnValue("Generate Input File");
        this.setConfirmText("");
        currentItem = null;
        gridDetail = new ArrayList<NpciFileDto>();
        seqEnable = "none";
        seqNoList = new ArrayList<SelectItem>();
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }
    //Getter And Setter

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public List<SelectItem> getFileTypeList() {
        return fileTypeList;
    }

    public void setFileTypeList(List<SelectItem> fileTypeList) {
        this.fileTypeList = fileTypeList;
    }

    public String getGenDt() {
        return genDt;
    }

    public void setGenDt(String genDt) {
        this.genDt = genDt;
    }

    public String getLpgType() {
        return lpgType;
    }

    public void setLpgType(String lpgType) {
        this.lpgType = lpgType;
    }

    public List<SelectItem> getLpgTypeList() {
        return lpgTypeList;
    }

    public void setLpgTypeList(List<SelectItem> lpgTypeList) {
        this.lpgTypeList = lpgTypeList;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public List<SelectItem> getSeqNoList() {
        return seqNoList;
    }

    public void setSeqNoList(List<SelectItem> seqNoList) {
        this.seqNoList = seqNoList;
    }

    public String getSeqEnable() {
        return seqEnable;
    }

    public void setSeqEnable(String seqEnable) {
        this.seqEnable = seqEnable;
    }

    public String getBtnValue() {
        return btnValue;
    }

    public void setBtnValue(String btnValue) {
        this.btnValue = btnValue;
    }

    public String getConfirmText() {
        return confirmText;
    }

    public void setConfirmText(String confirmText) {
        this.confirmText = confirmText;
    }

    public List<NpciFileDto> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<NpciFileDto> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public NpciFileDto getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(NpciFileDto currentItem) {
        this.currentItem = currentItem;
    }
}
