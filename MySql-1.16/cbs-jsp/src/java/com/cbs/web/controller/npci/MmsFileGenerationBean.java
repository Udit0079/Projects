/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.npci;

import com.cbs.dto.NpciFileDto;
import com.cbs.facade.other.NpciMandateFacadeRemote;
import com.cbs.facade.other.NpciMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

public class MmsFileGenerationBean extends BaseBean {

    private String errMessage;
    private String operation;
    private String mmsType;
    private String fileUpDt;
    private String seqNo;
    private String btnValue;
    private String fileType;
    private String returnFlag = "none";
    private String initiationFlag = "none";
    private String tillDt;
    private String option;
    private NpciFileDto currentItem;
    private List<SelectItem> operationList;
    private List<SelectItem> mmsTypeList;
    private List<SelectItem> seqNoList;
    private List<SelectItem> fileTypeList;
    private List<SelectItem> optionList;
    private List<NpciFileDto> gridDetail;
    private NpciMandateFacadeRemote npciMandateRemote;
    private NpciMgmtFacadeRemote npciFacade;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public MmsFileGenerationBean() {
        try {
            npciMandateRemote = (NpciMandateFacadeRemote) ServiceLocator.getInstance().lookup("NpciMandateFacade");
            npciFacade = (NpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciMgmtFacade");
            initData();
            refreshForm();
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void initData() {
        operationList = new ArrayList<SelectItem>();
        optionList = new ArrayList<SelectItem>();
        mmsTypeList = new ArrayList<SelectItem>();
        seqNoList = new ArrayList<SelectItem>();
        try {
            operationList.add(new SelectItem("0", "--Select--"));
            operationList.add(new SelectItem("G", "Generate"));
            operationList.add(new SelectItem("S", "Show"));


            optionList.add(new SelectItem("0", "--Select--"));
            optionList.add(new SelectItem("New"));
            optionList.add(new SelectItem("Esign"));
            optionList.add(new SelectItem("Input"));
            optionList.add(new SelectItem("Legacy"));

            mmsTypeList.add(new SelectItem("0", "--Select--"));
            mmsTypeList.add(new SelectItem("CREATE"));
            mmsTypeList.add(new SelectItem("AMEND"));



        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void optionOpration() {
        try {
            if (option.equalsIgnoreCase("New") || option.equalsIgnoreCase("Esign") || option.equalsIgnoreCase("Legacy")) {
                fileTypeList = new ArrayList<SelectItem>();
                fileTypeList.add(new SelectItem("0", "--Select--"));
                fileTypeList.add(new SelectItem("RETURN"));
            } else if (option.equalsIgnoreCase("Input")) {
                fileTypeList = new ArrayList<SelectItem>();
                fileTypeList.add(new SelectItem("0", "--Select--"));
                fileTypeList.add(new SelectItem("INITIATION"));
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void btnChangeProcess() {
        try {
            this.btnValue = "";
            if (this.operation.equalsIgnoreCase("G")) {
                this.btnValue = "Generate";
            } else if (this.operation.equalsIgnoreCase("S")) {
                this.btnValue = "Show";
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void btnFileTypeProcess() {
        this.setReturnFlag("none");
        this.setInitiationFlag("none");
        try {
            if (this.getFileType().equals("RETURN")) {
                this.setReturnFlag("");
                this.setInitiationFlag("none");
            } else if (this.getFileType().equals("INITIATION")) {
                this.setReturnFlag("none");
                this.setInitiationFlag("");
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void fileUpDtProcess() {
        try {
            if (this.operation == null || this.operation.equals("0") || this.operation.equals("")) {
                this.setErrMessage("Please select operation.");
                return;
            }
            if (this.mmsType == null || this.mmsType.equals("0") || this.mmsType.equals("")) {
                this.setErrMessage("Please select mandate type.");
                return;
            }
            if (this.fileType == null || this.fileType.equals("0") || this.fileType.equals("")) {
                this.setErrMessage("Please select file type.");
                return;
            }
            if (this.fileUpDt == null || this.fileUpDt.equals("")) {
                this.setErrMessage("Please fill file upload date.");
                return;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(this.fileUpDt)) {
                this.setErrMessage("Please fill proper upload date.");
                return;
            }
            String mandateMode = "";
            if (option.equalsIgnoreCase("New")) {
                mandateMode = "N";
            } else if (option.equalsIgnoreCase("Esign")) {
                mandateMode = "E";
            } else if (option.equalsIgnoreCase("Legacy")) {
                mandateMode = "L";
            }
            seqNoList = new ArrayList<>();
            List list = npciMandateRemote.getAllUploadedFileNoAtGenerationTime(ymd.format(dmy.parse(this.fileUpDt)), this.mmsType, mandateMode);
            if (list.isEmpty()) {
                this.setErrMessage("There are no uploaded files.");
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                seqNoList.add(new SelectItem(ele.get(0).toString().trim()));
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void processAction() {
        try {
            String mandateMode = "";
            if (option.equalsIgnoreCase("New")) {
                mandateMode = "N";
            } else if (option.equalsIgnoreCase("Esign")) {
                mandateMode = "E";
            } else if (option.equalsIgnoreCase("Legacy")) {
                mandateMode = "L";
            }
            if (this.operation == null || this.operation.equals("0") || this.operation.equals("")) {
                this.setErrMessage("Please select operation.");
                return;
            }
            if (this.option.equalsIgnoreCase("0") || this.option.equals("") || this.option == null) {
                this.setErrMessage("Please select option.");
                return;
            }
            if (this.mmsType == null || this.mmsType.equals("0") || this.mmsType.equals("")) {
                this.setErrMessage("Please select mandate type.");
                return;
            }
            if (this.fileType == null || this.fileType.equals("0") || this.fileType.equals("")) {
                this.setErrMessage("Please select file type.");
                return;
            }
            if (this.fileType.equals("RETURN")) {
                if (this.fileUpDt == null || this.fileUpDt.equals("")) {
                    this.setErrMessage("Please fill file upload date.");
                    return;
                }
                if (!new Validator().validateDate_dd_mm_yyyy(this.fileUpDt)) {
                    this.setErrMessage("Please fill proper upload date.");
                    return;
                }
                if (this.seqNo == null || this.seqNo.equals("")) {
                    this.setErrMessage("Please select file no.");
                    return;
                }
            } else if (this.fileType.equals("INITIATION")) {
                if (this.tillDt == null || this.tillDt.equals("")) {
                    this.setErrMessage("Please fill date.");
                    return;
                }
                if (!new Validator().validateDate_dd_mm_yyyy(this.tillDt)) {
                    this.setErrMessage("Please fill proper date.");
                    return;
                }
            }

            if (this.operation.equals("G")) {   //File Generation
                String result = "";
                if (this.option.equalsIgnoreCase("New") || this.option.equalsIgnoreCase("Legacy")) {
                    if (this.fileType.equals("RETURN")) { //As destination mms
                        result = npciMandateRemote.generateMmsCreateReturn(this.mmsType,
                                ymd.format(dmy.parse(this.fileUpDt)), this.seqNo,
                                getTodayDate(), getUserName(), getOrgnBrCode(), "", "", mandateMode);
                    }
                } else if (this.option.equalsIgnoreCase("Esign")) {
                    if (this.fileType.equals("RETURN")) {
                        result = npciMandateRemote.generateEsignMmsReturn(this.mmsType,
                                ymd.format(dmy.parse(this.fileUpDt)), this.seqNo,
                                getTodayDate(), getUserName(), getOrgnBrCode(), "", "", mandateMode);
                    }
                } else if (this.option.equalsIgnoreCase("Input")) {
                    if (this.fileType.equals("INITIATION")) {
                        result = npciMandateRemote.generateMmsInitiationFile(this.mmsType, ymd.format(dmy.parse(this.tillDt)),
                                getTodayDate(), getUserName(), getOrgnBrCode());
                    }
                }
                if (!result.equalsIgnoreCase("true")) {
                    this.setErrMessage(result);
                    return;
                }
                this.setErrMessage("File has been generated successfully.");
            } else if (this.operation.equals("S")) { //Show process
                if (this.fileType.equals("RETURN")) {
                    gridDetail = npciMandateRemote.showMmsFiles(this.fileType, this.mmsType, this.fileUpDt, Double.parseDouble(this.seqNo));
                } else if (this.fileType.equals("INITIATION")) {
                    gridDetail = npciMandateRemote.showMmsFiles(this.fileType, this.mmsType, this.fileUpDt, 0.0);
                }
                this.setErrMessage("Now you can download a file from table.");
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void downloadFile() {
        try {
            if (currentItem == null) {
                this.setErrMessage("Please download one file from table.");
                return;
            }
            HttpServletResponse res = (HttpServletResponse) getHttpResponse();
            String ctxPath = getFacesContext().getExternalContext().getRequestContextPath();

            List list = npciFacade.getBankDetails();
            Vector ele = (Vector) list.get(0);
            if (ele.get(8) == null) {
                this.setErrMessage("Please define Aadhar Location in bank detail.");
                return;
            }
            String dirName = ele.get(8).toString().trim();
            if (this.fileType.equals("RETURN")) {
                dirName = dirName + ymd.format(dmy.parse(this.fileUpDt)) + "/" + this.seqNo + "_" + this.mmsType + "/";
            }
            res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirName + "&fileName=" + currentItem.getFileName());
        } catch (Exception e) {
            this.setErrMessage(e.getMessage());
        }
    }

    public void refreshForm() {
        this.setErrMessage("");
        this.setOperation("0");
        this.setMmsType("0");
        this.setFileType("0");
        this.setFileUpDt(getTodayDate());
        this.setSeqNo("");
        this.setTillDt(getTodayDate());
        this.setReturnFlag("none");
        this.setInitiationFlag("none");
        currentItem = null;
        seqNoList = new ArrayList<SelectItem>();
        gridDetail = new ArrayList<NpciFileDto>();
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

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getMmsType() {
        return mmsType;
    }

    public void setMmsType(String mmsType) {
        this.mmsType = mmsType;
    }

    public String getFileUpDt() {
        return fileUpDt;
    }

    public void setFileUpDt(String fileUpDt) {
        this.fileUpDt = fileUpDt;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public List<SelectItem> getOperationList() {
        return operationList;
    }

    public void setOperationList(List<SelectItem> operationList) {
        this.operationList = operationList;
    }

    public List<SelectItem> getMmsTypeList() {
        return mmsTypeList;
    }

    public void setMmsTypeList(List<SelectItem> mmsTypeList) {
        this.mmsTypeList = mmsTypeList;
    }

    public List<SelectItem> getSeqNoList() {
        return seqNoList;
    }

    public void setSeqNoList(List<SelectItem> seqNoList) {
        this.seqNoList = seqNoList;
    }

    public NpciFileDto getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(NpciFileDto currentItem) {
        this.currentItem = currentItem;
    }

    public List<NpciFileDto> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<NpciFileDto> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getBtnValue() {
        return btnValue;
    }

    public void setBtnValue(String btnValue) {
        this.btnValue = btnValue;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getReturnFlag() {
        return returnFlag;
    }

    public void setReturnFlag(String returnFlag) {
        this.returnFlag = returnFlag;
    }

    public String getInitiationFlag() {
        return initiationFlag;
    }

    public void setInitiationFlag(String initiationFlag) {
        this.initiationFlag = initiationFlag;
    }

    public String getTillDt() {
        return tillDt;
    }

    public void setTillDt(String tillDt) {
        this.tillDt = tillDt;
    }

    public List<SelectItem> getFileTypeList() {
        return fileTypeList;
    }

    public void setFileTypeList(List<SelectItem> fileTypeList) {
        this.fileTypeList = fileTypeList;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public List<SelectItem> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<SelectItem> optionList) {
        this.optionList = optionList;
    }
}
