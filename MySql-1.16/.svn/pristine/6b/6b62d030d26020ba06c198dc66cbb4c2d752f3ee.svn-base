/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.npci;

import com.cbs.dto.NpciFileDto;
import com.cbs.dto.other.CbsMandateDetailPojo;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.NpciMgmtFacadeRemote;
import com.cbs.facade.other.OtherNpciMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

public class NpciInputController extends BaseBean {

    private String errorMessage;
    private String function;
    private String fileType;
    private String displayOngenerate;
    private String displayOnShow;
    private String displayOngenerateECS;
    private String displayOngenerateACH;
    private String displayOnShowECS;
    private String displayOnShowACH;
    private String dt;
    private String btnValue;
    private String createConfirmTxt;
    private String confirmText;
    private String txnType;
    private CbsMandateDetailPojo currentItem;
    private List<SelectItem> fileTypeList;
    private List<SelectItem> functionList;
    private List<SelectItem> tranTypeList;
    private List<SelectItem> txnTypeList;
    private String tranType;
    private List<SelectItem> forgetFlagList;
    private String forgateFlag;
    private String forgetDay;
    private List<SelectItem> seqTypeList;
    private String seqType;
    private List<SelectItem> freqnTypeList;
    private String freqnType;
    private boolean checkAllBox;
    private boolean disableForgrtDay;
    private String fromDate;
    private String toDate;
    private String dateFlag = "";
    private String settlementDt;
    private String txnTypeFlag = "none";
    private List<CbsMandateDetailPojo> gridDetail;
    private List<NpciFileDto> gridDetailECS;
    private NpciFileDto currentItemECS;
    private List<String[]> gridDetailToShowData;
    private NpciMgmtFacadeRemote npciFacade = null;
    private CommonReportMethodsRemote reportRemote = null;
    private OtherNpciMgmtFacadeRemote otherNpciRemote;
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public NpciInputController() {
        try {
            npciFacade = (NpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciMgmtFacade");
            reportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            otherNpciRemote = (OtherNpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("OtherNpciMgmtFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            btnRefreshAction();
            initData();
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void initData() {
        try {
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("0", "--Select--"));
            functionList.add(new SelectItem("G", "Generate"));
            functionList.add(new SelectItem("S", "Show"));

            fileTypeList = new ArrayList<SelectItem>();
            fileTypeList.add(new SelectItem("0", "--Select--"));
            List list = reportRemote.getRefRecList("384");
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                fileTypeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }

            tranTypeList = new ArrayList<SelectItem>();
            tranTypeList.add(new SelectItem("0", "--Select--"));
            tranTypeList.add(new SelectItem("CREDIT", "Credit"));
            tranTypeList.add(new SelectItem("DEBIT", "Debit"));

            forgetFlagList = new ArrayList<SelectItem>();
            forgetFlagList.add(new SelectItem("0", "--Select--"));
            forgetFlagList.add(new SelectItem("Y", "YES"));
            forgetFlagList.add(new SelectItem("N", "NO"));

            txnTypeList = new ArrayList<SelectItem>();
            txnTypeList.add(new SelectItem("0", "--Select--"));
            txnTypeList.add(new SelectItem("Legacy"));
            txnTypeList.add(new SelectItem("New"));

            forgateFlag = "N";
            disableForgrtDay = true;

            seqTypeList = new ArrayList<SelectItem>();
            seqTypeList.add(new SelectItem("0", "--Select--"));
            list = reportRemote.getRefRecList("369");
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                seqTypeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }

            freqnTypeList = new ArrayList<SelectItem>();
            freqnTypeList.add(new SelectItem("0", "--Select--"));
            freqnTypeList.add(new SelectItem("WTOASWP", "WITH  OUT ASWP"));
            list = reportRemote.getRefRecList("365");
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                freqnTypeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }
            displayOngenerate = "";
            displayOngenerateECS = "none";
            displayOngenerateACH = "none";
            displayOnShow = "none";
            displayOnShowECS = "none";
            displayOnShowACH = "none";
            dateFlag = "";
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void functionAction() {
        try {
            this.setConfirmText("");
            this.setBtnValue("");
            if (this.function == null || this.function.equals("0")) {
                this.setErrorMessage("Please select function.");
                return;
            }
            if (this.function.equals("G")) {
                this.setBtnValue("Generate");
                this.displayOngenerate = "";
                this.displayOngenerateECS = "none";
                this.displayOngenerateACH = "none";
                this.displayOnShow = "none";
                this.displayOnShowACH = "none";
                this.displayOnShowECS = "none";
            } else if (this.function.equals("S")) {
                this.setBtnValue("Show");
                this.displayOngenerate = "none";
                this.displayOngenerateECS = "none";
                this.displayOngenerateACH = "none";
                this.displayOnShow = "";
                this.displayOnShowACH = "none";
                this.displayOnShowECS = "none";
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void onblurFileType() {
        if (this.function.equals("G")) {
            this.setBtnValue("Generate");
            if (this.fileType.equalsIgnoreCase("ACH") || this.fileType.equalsIgnoreCase("ECSNEW")) {
                this.displayOngenerate = "";
                this.displayOngenerateECS = "none";
                this.displayOngenerateACH = "";
                this.displayOnShow = "none";
                this.displayOnShowACH = "none";
                this.displayOnShowECS = "none";
                if (this.fileType.equalsIgnoreCase("ACH")) {
                    this.txnTypeFlag = "";
                } else if (this.fileType.equalsIgnoreCase("ECSNEW")) {
                    this.txnTypeFlag = "none";
                }
            } else if (this.fileType.equalsIgnoreCase("ECS")) {
                this.displayOngenerate = "";
                this.displayOngenerateECS = "";
                this.displayOngenerateACH = "none";
                this.displayOnShow = "none";
                this.displayOnShowACH = "none";
                this.displayOnShowECS = "none";
                this.txnTypeFlag = "none";
            }
        } else if (this.function.equals("S")) {
            this.setBtnValue("Show");
            if (this.fileType.equalsIgnoreCase("ACH") || this.fileType.equalsIgnoreCase("ECSNEW")) {
                this.displayOngenerate = "none";
                this.displayOngenerateECS = "none";
                this.displayOngenerateACH = "none";
                this.displayOnShow = "";
                this.displayOnShowACH = "";
                this.displayOnShowECS = "none";
                this.txnTypeFlag = "none";
            } else if (this.fileType.equalsIgnoreCase("ECS")) {
                this.displayOngenerate = "none";
                this.displayOngenerateECS = "none";
                this.displayOngenerateACH = "none";
                this.displayOnShow = "";
                this.displayOnShowACH = "none";
                this.displayOnShowECS = "";
                this.txnTypeFlag = "none";
            }
        }
    }

    public void onBlurForgrtFlag() {
        if (forgateFlag.equalsIgnoreCase("Y")) {
            disableForgrtDay = false;
            dateFlag = "none";
            gridDetail = new ArrayList<CbsMandateDetailPojo>();
        } else {
            disableForgrtDay = true;
            dateFlag = "";
        }
    }

    public void createConfirmTxt() {
        this.setErrorMessage("");
        if (this.function == null || this.function.equals("0")) {
            this.setErrorMessage("Please select function.");
            return;
        }
        this.setConfirmText("");
        if (this.function.equals("G")) {
            this.setConfirmText("Are you sure to generate the file ?");
        } else if (this.function.equals("S")) {
            this.setConfirmText("Are you sure to show the file ?");
        }
    }

    public void downloadFile(String fileName) {
        try {
            if (fileName == null) {
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

            if (!keyPwd.equals("")) {
                res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirName + "&fileName=" + fileName + "&pwd=" + keyPwd);
            } else {
                res.sendRedirect(ctxPath + "/downloadFile/?dirName=" + dirName + "&fileName=" + fileName);
            }
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void getUmrnDataForGrid() {
        this.setErrorMessage("");
        try {
            if (fileType.equalsIgnoreCase("ACH") || this.fileType.equalsIgnoreCase("ECSNEW")) {
                if (validateField()) {
                    gridDetail = new ArrayList<>();
                    int forgetDayInt = forgetDay == null || forgetDay.equalsIgnoreCase("") ? 0 : Integer.parseInt(forgetDay);
                    String npciFileType = "";
                    if (fileType.equalsIgnoreCase("ECSNEW")) {
                        npciFileType = "ECS";
                    } else {
                        npciFileType = fileType;
                    }
                    String fileGenStartDt = "";
                    if (forgateFlag.equalsIgnoreCase("Y")) {
                        if (forgetDayInt == 0) {
                            forgetDayInt = 1;
                            fileGenStartDt = dt;
                        } else {
                            fileGenStartDt = dmy.format(ymd.parse(CbsUtil.dateAdd(ymd.format(dmy.parse(dt)), -forgetDayInt)));
                            forgetDayInt = forgetDayInt + 1;
                        }
                    } else if (forgateFlag.equalsIgnoreCase("N")) {
                        fileGenStartDt = dt;
                    }

                    gridDetail = otherNpciRemote.getUmrDetailsOnmandateDate(npciFileType, tranType, fileGenStartDt, forgateFlag,
                            forgetDayInt, seqType, freqnType, getOrgnBrCode(), getUserName(), this.txnType);
                    if (gridDetail.isEmpty()) {
                        this.setErrorMessage("Mandate Data Not Exist for Corresponding Date ");
                    } else {
                        this.setErrorMessage("Selected rows get proccessed to generate input file");
                    }
                }
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public void processAction() {
        this.setErrorMessage("");
        try {
            if (validateField() && !(this.btnValue == null || this.btnValue.equals(""))) {
                String fileGenType = "";
                if (tranType.equalsIgnoreCase("CREDIT") && fileType.equalsIgnoreCase("ECS")) {
                    fileGenType = "ECT";
                } else if (tranType.equalsIgnoreCase("DEBIT") && fileType.equalsIgnoreCase("ECS")) {
                    fileGenType = "EDT";
                }
                if (fileType.equalsIgnoreCase("ACH")) {
                    if (tranType.equalsIgnoreCase("DEBIT")) {
                        fileGenType = "AINPSD";
                    } else if (tranType.equalsIgnoreCase("CREDIT")) {
                        fileGenType = "AINPSC";
                    }
                } else if (fileType.equalsIgnoreCase("ECSNEW")) {
                    if (tranType.equalsIgnoreCase("DEBIT")) {
                        fileGenType = "EINPSD";
                    } else if (tranType.equalsIgnoreCase("CREDIT")) {
                        fileGenType = "EINPSC";
                    }
                }
                if ((this.function.equals("G")) && (fileType.equalsIgnoreCase("ACH"))) {
                    String result = otherNpciRemote.generateMandateIputFile(gridDetail, tranType, dt, getTodayDate(),
                            getUserName(), getOrgnBrCode(), this.settlementDt, this.txnType);
                    if (!result.equals("true")) {
                        this.setErrorMessage(result);
                        return;
                    }
                    btnRefreshAction();
                    this.setErrorMessage("File has been generated successfully.");
                } else if ((this.function.equals("G")) && fileType.equalsIgnoreCase("ECSNEW")) {
                    String result = otherNpciRemote.generateECSNewIputFile(gridDetail, fileGenType, dt, getTodayDate(),
                            getUserName(), getOrgnBrCode(), this.settlementDt);
                    if (!result.equals("true")) {
                        this.setErrorMessage(result);
                        return;
                    }
                    btnRefreshAction();
                    this.setErrorMessage("File has been generated successfully.");
                } else if ((this.function.equals("G")) && fileType.equalsIgnoreCase("ECS")) {

                    String result = otherNpciRemote.generateEcsIputFile(fileGenType, this.dt, getOrgnBrCode(), getUserName(), getTodayDate());
                    if (!result.equals("true")) {
                        this.setErrorMessage(result);
                        return;
                    }
                    btnRefreshAction();
                    this.setErrorMessage("File has been generated successfully.");

                } else if (this.function.equals("S")) {
                    if (fileType.equalsIgnoreCase("ACH") || this.fileType.equalsIgnoreCase("ECSNEW")) {

                        gridDetailToShowData = otherNpciRemote.getACHOrECSInputGeneratedFile(fileGenType, fromDate, toDate, getOrgnBrCode());
                        if (gridDetailToShowData.isEmpty()) {
                            this.setErrorMessage("No File To Download.");
                        }
                    } else if (fileType.equalsIgnoreCase("ECS")) {
                        gridDetailECS = otherNpciRemote.showGeneratedFiles(fileGenType, this.fromDate, this.toDate);
                        this.setErrorMessage("Now you can download a file from table.");
                    }
                } else {
                    this.setErrorMessage("Now you can download a file from table.");
                }
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

    public boolean validateField() {
        try {
            if (this.function == null || this.function.equals("0")) {
                this.setErrorMessage("Please select function.");
                return false;
            }
            if (function.equalsIgnoreCase("G")) {
                if (this.fileType == null || this.fileType.equals("0")) {
                    this.setErrorMessage("Please select file type.");
                    return false;
                }
                if (this.tranType == null || this.tranType.equals("0")) {
                    this.setErrorMessage("Please select transaction type.");
                    return false;
                }
                if (this.settlementDt == null || this.settlementDt.equals("")) {
                    this.setErrorMessage("Please fill settlement date.");
                    return false;
                }
                if (new Validator().validateDate_dd_mm_yyyy(this.settlementDt) == false) {
                    this.setErrorMessage("Please select proper date.");
                    return false;
                }

                if (this.fileType.equalsIgnoreCase("ACH")) {
                    if (dmy.parse(this.settlementDt).compareTo(dmy.parse(getTodayDate())) < 0) {
                        this.setErrorMessage("Settlement date must be greater than or equal to current date.");
                        return false;
                    }
                } else if (this.fileType.equalsIgnoreCase("ECSNEW")) {
                    if (this.seqType == null || this.seqType.equals("0")) {
                        this.setErrorMessage("Please select sequence type.");
                        return false;
                    }
                    if (this.freqnType == null || this.freqnType.equals("0")) {
                        this.setErrorMessage("Please select frequency type.");
                        return false;
                    }
                    if (dmy.parse(this.settlementDt).compareTo(dmy.parse(getTodayDate())) <= 0) {
                        this.setErrorMessage("Settlement date must be greater than current date.");
                        return false;
                    }
                }
                if (this.fileType.equalsIgnoreCase("ACH")) {
                    if (this.seqType == null || this.seqType.equals("0")) {
                        this.setErrorMessage("Please select sequence type.");
                        return false;
                    }
                    if (this.freqnType == null || this.freqnType.equals("0")) {
                        this.setErrorMessage("Please select frequency type.");
                        return false;
                    }
                    if (this.forgateFlag == null || this.forgateFlag.equals("0")) {
                        this.setErrorMessage("Please select forget flag.");
                        return false;
                    }
                    if ((forgateFlag.equalsIgnoreCase("Y")) && (this.forgetDay == null || this.forgetDay.equals(""))) {
                        this.setErrorMessage("Please fill forget day.");
                        return false;
                    }
                    if (forgateFlag.equalsIgnoreCase("Y")) {
                        if (!((!(this.forgetDay == null || this.forgetDay.equals(""))) && (Pattern.matches("-?[0-9]+", this.forgetDay)))) {
                            this.setErrorMessage("Please fill forget day as number.");
                            return false;
                        }
                    }
                    if (this.dt == null || this.dt.equals("")) {
                        this.setErrorMessage("Please fill proper date.");
                        return false;
                    }
                    if (new Validator().validateDate_dd_mm_yyyy(this.dt) == false) {
                        this.setErrorMessage("Please fill proper date.");
                        return false;
                    }
                    int day = Integer.parseInt(new SimpleDateFormat("dd").format(dmy.parse(dt)));
                    if (!(day > 0 && day <= 28)) {
                        this.setErrorMessage("Date Must be less than or equal 28th. !");
                        return false;
                    }
                    if (this.txnType == null || this.txnType.equals("0") || this.txnType.equals("")) {
                        this.setErrorMessage("Please select txn type");
                        return false;
                    }
                } else if (this.fileType.equalsIgnoreCase("ECS")) {
                    if (this.dt == null || this.dt.equals("")) {
                        this.setErrorMessage("Please select proper date.");
                        return false;
                    }
                    if (new Validator().validateDate_dd_mm_yyyy(this.dt) == false) {
                        this.setErrorMessage("Please select proper date.");
                        return false;
                    }
                    if (dmy.parse(this.dt).after(dmy.parse(getTodayDate()))) {
                        this.setErrorMessage("Date can not be greater than Current Date.");
                        return false;
                    }
                    String alphaCode = reportRemote.getAlphacodeByBrncode(getOrgnBrCode());
                    if (!alphaCode.equalsIgnoreCase("HO")) {
                        this.setErrorMessage("Any work will be perform from HO.");
                        return false;
                    }
                }
            } else if (function.equalsIgnoreCase("S")) {
                if (this.fromDate == null || this.fromDate.equals("")) {
                    this.setErrorMessage("Please fill proper from date.");
                    return false;
                }
                if (this.toDate == null || this.toDate.equals("")) {
                    this.setErrorMessage("Please fill proper to date.");
                    return false;
                }
                if (dmy.parse(fromDate).after(dmy.parse(toDate))) {
                    this.setErrorMessage("To Date must be gerater than From Date. ");
                    return false;
                }
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
            return false;
        }
        return true;
    }

    public void checkAll() {
        if (this.checkAllBox == true) {
            for (int i = 0; i < gridDetail.size(); i++) {
                gridDetail.get(i).setCheckBox(true);
            }
        } else if (this.checkAllBox == false) {
            for (int i = 0; i < gridDetail.size(); i++) {
                gridDetail.get(i).setCheckBox(false);
            }
        }
    }

    public void btnRefreshAction() {
        errorMessage = "";
        function = "0";
        fileType = "0";
        displayOngenerate = "";
        displayOnShow = "none";
        currentItem = new CbsMandateDetailPojo();
        currentItemECS = null;
        tranType = "0";
        forgateFlag = "N";
        disableForgrtDay = true;
        forgetDay = "";
        seqType = "0";
        freqnType = "0";
        this.setDt(getTodayDate());
        this.setBtnValue("");
        this.setConfirmText("");
        gridDetail = new ArrayList<CbsMandateDetailPojo>();
        gridDetailECS = new ArrayList<NpciFileDto>();
        fromDate = "";
        toDate = "";
        gridDetailToShowData = new ArrayList<String[]>();
        dateFlag = "";
        this.setSettlementDt(getTodayDate());
        this.setTxnType("0");
        this.setTxnTypeFlag("none");
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    public void checkUnCheck() {
        // This method is required for grid check box. 
    }
    //Getter And Setter

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getDisplayOngenerate() {
        return displayOngenerate;
    }

    public void setDisplayOngenerate(String displayOngenerate) {
        this.displayOngenerate = displayOngenerate;
    }

    public String getDisplayOnShow() {
        return displayOnShow;
    }

    public void setDisplayOnShow(String displayOnShow) {
        this.displayOnShow = displayOnShow;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getBtnValue() {
        return btnValue;
    }

    public void setBtnValue(String btnValue) {
        this.btnValue = btnValue;
    }

    public String getCreateConfirmTxt() {
        return createConfirmTxt;
    }

    public void setCreateConfirmTxt(String createConfirmTxt) {
        this.createConfirmTxt = createConfirmTxt;
    }

    public String getConfirmText() {
        return confirmText;
    }

    public void setConfirmText(String confirmText) {
        this.confirmText = confirmText;
    }

    public CbsMandateDetailPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(CbsMandateDetailPojo currentItem) {
        this.currentItem = currentItem;
    }

    public List<SelectItem> getFileTypeList() {
        return fileTypeList;
    }

    public void setFileTypeList(List<SelectItem> fileTypeList) {
        this.fileTypeList = fileTypeList;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public List<CbsMandateDetailPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<CbsMandateDetailPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public List<SelectItem> getTranTypeList() {
        return tranTypeList;
    }

    public void setTranTypeList(List<SelectItem> tranTypeList) {
        this.tranTypeList = tranTypeList;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public List<SelectItem> getForgetFlagList() {
        return forgetFlagList;
    }

    public void setForgetFlagList(List<SelectItem> forgetFlagList) {
        this.forgetFlagList = forgetFlagList;
    }

    public String getForgateFlag() {
        return forgateFlag;
    }

    public void setForgateFlag(String forgateFlag) {
        this.forgateFlag = forgateFlag;
    }

    public List<SelectItem> getSeqTypeList() {
        return seqTypeList;
    }

    public void setSeqTypeList(List<SelectItem> seqTypeList) {
        this.seqTypeList = seqTypeList;
    }

    public String getSeqType() {
        return seqType;
    }

    public void setSeqType(String seqType) {
        this.seqType = seqType;
    }

    public List<SelectItem> getFreqnTypeList() {
        return freqnTypeList;
    }

    public void setFreqnTypeList(List<SelectItem> freqnTypeList) {
        this.freqnTypeList = freqnTypeList;
    }

    public String getFreqnType() {
        return freqnType;
    }

    public void setFreqnType(String freqnType) {
        this.freqnType = freqnType;
    }

    public String getForgetDay() {
        return forgetDay;
    }

    public void setForgetDay(String forgetDay) {
        this.forgetDay = forgetDay;
    }

    public boolean isCheckAllBox() {
        return checkAllBox;
    }

    public void setCheckAllBox(boolean checkAllBox) {
        this.checkAllBox = checkAllBox;
    }

    public boolean isDisableForgrtDay() {
        return disableForgrtDay;
    }

    public void setDisableForgrtDay(boolean disableForgrtDay) {
        this.disableForgrtDay = disableForgrtDay;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public List<String[]> getGridDetailToShowData() {
        return gridDetailToShowData;
    }

    public void setGridDetailToShowData(List<String[]> gridDetailToShowData) {
        this.gridDetailToShowData = gridDetailToShowData;
    }

    public List<NpciFileDto> getGridDetailECS() {
        return gridDetailECS;
    }

    public void setGridDetailECS(List<NpciFileDto> gridDetailECS) {
        this.gridDetailECS = gridDetailECS;
    }

    public NpciFileDto getCurrentItemECS() {
        return currentItemECS;
    }

    public void setCurrentItemECS(NpciFileDto currentItemECS) {
        this.currentItemECS = currentItemECS;
    }

    public String getDisplayOngenerateECS() {
        return displayOngenerateECS;
    }

    public void setDisplayOngenerateECS(String displayOngenerateECS) {
        this.displayOngenerateECS = displayOngenerateECS;
    }

    public String getDisplayOnShowECS() {
        return displayOnShowECS;
    }

    public void setDisplayOnShowECS(String displayOnShowECS) {
        this.displayOnShowECS = displayOnShowECS;
    }

    public String getDisplayOngenerateACH() {
        return displayOngenerateACH;
    }

    public void setDisplayOngenerateACH(String displayOngenerateACH) {
        this.displayOngenerateACH = displayOngenerateACH;
    }

    public String getDisplayOnShowACH() {
        return displayOnShowACH;
    }

    public void setDisplayOnShowACH(String displayOnShowACH) {
        this.displayOnShowACH = displayOnShowACH;
    }

    public String getDateFlag() {
        return dateFlag;
    }

    public void setDateFlag(String dateFlag) {
        this.dateFlag = dateFlag;
    }

    public String getSettlementDt() {
        return settlementDt;
    }

    public void setSettlementDt(String settlementDt) {
        this.settlementDt = settlementDt;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public List<SelectItem> getTxnTypeList() {
        return txnTypeList;
    }

    public void setTxnTypeList(List<SelectItem> txnTypeList) {
        this.txnTypeList = txnTypeList;
    }

    public String getTxnTypeFlag() {
        return txnTypeFlag;
    }

    public void setTxnTypeFlag(String txnTypeFlag) {
        this.txnTypeFlag = txnTypeFlag;
    }
}
