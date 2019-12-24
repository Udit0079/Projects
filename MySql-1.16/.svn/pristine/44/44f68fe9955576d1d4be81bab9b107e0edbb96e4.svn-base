/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.clg;

import com.cbs.dto.master.BillTypeMasterTO;
import com.cbs.dto.report.CTSUploadReportPojo;
import com.cbs.web.controller.BaseBean;
import java.util.List;
import javax.faces.model.SelectItem;
import com.cbs.dto.report.CtsChequeStatusReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.clg.NpciClearingMgmtFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class NPCIctsModification extends BaseBean {

    private String errMessage;
    private List<SelectItem> functionList;
    private List<SelectItem> chequeTypeList;
    private List<SelectItem> scheduleNoList;
    private List<SelectItem> sanNatureList;
    private String function;
    private String btnValue;
    private String date;
    private String chequeType;
    private String scheduleNo;
    private String instNo;
    private String instNoFilter;
    private String instDate;
    private String favorOf;
    private String acno;
    private String custName;
    private String operMode;
    private String destBranch;
    private String destBranchCode;
    private String seqNo;
    //SAN or MICR
    private String sanNo;
    private String sanLabel;
    private String serialNo;
    private String displayModifyUploadDate;
    private String displayUploadReport;
    private String displayUpdateBtn;
    private String sanNature;
    private boolean disableInstDt = false;
    private int ctsSponsor = 0;
    private int ctsByMicr = 0;
    private String folderName;
    private String imageData;
    private List<CtsChequeStatusReportPojo> gridDetail;
    private CtsChequeStatusReportPojo currentItem;
    private CommonReportMethodsRemote commonReport;
    private NpciClearingMgmtFacadeRemote npciClrRemot;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymdOne = new SimpleDateFormat("ddMMyyyy");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public NPCIctsModification() {
        try {
            commonReport = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            npciClrRemot = (NpciClearingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciClearingMgmtFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            functionList = new ArrayList<>();
            functionList.add(new SelectItem("0", "--Select--"));
            functionList.add(new SelectItem("EDIT", "Modify Upload Data"));
            functionList.add(new SelectItem("REPORT", "Upload Report"));

            sanNatureList = new ArrayList<>();
            sanNatureList.add(new SelectItem("0", "--Select--"));
            sanNatureList.add(new SelectItem("NON-PO"));
            sanNatureList.add(new SelectItem("PO"));

            chequeTypeList = new ArrayList<>();
            chequeTypeList.add(new SelectItem("0", "--Select--"));
            List list = commonReport.getRefRecList("016");
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                chequeTypeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }

            displayModifyUploadDate = "";
            displayUploadReport = "none";
            disableInstDt = false;
            ctsSponsor = ftsRemote.getCodeForReportName("CTS-SPONSOR");
            ctsByMicr = ftsRemote.getCodeForReportName("CTS-BY-MICR");
            if (ctsByMicr == 1) {
                setSanLabel("MICR CODE : ");
            } else {
                setSanLabel("SAN NO. : ");
            }
            refreshForm();
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void onBlurFunction() {
        if (function.equalsIgnoreCase("EDIT")) {
            displayModifyUploadDate = "";
            displayUploadReport = "none";
        } else if (function.equalsIgnoreCase("REPORT")) {
            displayModifyUploadDate = "none";
            displayUploadReport = "";
        } else {
            displayModifyUploadDate = "";
            displayUploadReport = "none";
        }
        refreshForm();
    }

    public void onBlurSerialNo() {
        try {
            if (this.sanNature == null || this.sanNature.equals("0") || this.sanNature.equals("")) {
                this.setErrMessage("Please select nature.");
                return;
            }
            if (this.sanNo == null || this.sanNo.trim().equals("")) {
                this.setErrMessage("Please fill SAN No.");
                return;
            }
//            String itemTranCode = "";
//            if (this.sanNo.equalsIgnoreCase("000000")) {
//                itemTranCode = currentItem.getItemTransCode();
//            } else {
//                itemTranCode = sanNo.substring(1, 3);
//            }

            String itemTranCode = "";
            if (this.sanNature.equalsIgnoreCase("PO")) {
                itemTranCode = currentItem.getItemTransCode();
            } else {
                itemTranCode = sanNo.substring(1, 3);
            }

            String nature = npciClrRemot.getTransCodeMap().get(itemTranCode);
            BillTypeMasterTO billTo = npciClrRemot.getBillTypeDetail("PO");
            String[] accDetail = {};
            if (ctsByMicr == 1) {
                accDetail = npciClrRemot.getMICRDetail(currentItem.getItemTransCode(), serialNo, billTo, sanNo);
            } else {
                accDetail = npciClrRemot.getSANDetail(itemTranCode, sanNo, serialNo, billTo, currentItem.getItemPayorBnkRoutNo());
            }
            this.acno = accDetail[0];
            this.custName = accDetail[1];
            this.operMode = accDetail[2];
            this.seqNo = accDetail[3];
            if (nature.equalsIgnoreCase("PO")) {
                if (!accDetail[4].equalsIgnoreCase("")) {
                    this.instDate = dmy.format(ymd.parse(accDetail[4]));
                } else {
                    this.instDate = accDetail[4];
                }
                disableInstDt = false;
            } else {
                disableInstDt = true;
                this.instDate = currentItem.getInstDt();
            }
            if (!(acno == null || acno.equalsIgnoreCase(""))) {
                this.destBranch = commonReport.getBranchNameByBrncode(acno.substring(0, 2));
                destBranchCode = acno.substring(0, 2);
            } else {
                this.destBranch = "";
                destBranchCode = "";
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }

    }

    public void getScheduleNoOnBlur() {
        try {
            if (date == null || date.equalsIgnoreCase("")) {
                this.setErrMessage("Please Fill Date.!");
                return;
            }
            scheduleNoList = new ArrayList<SelectItem>();
            scheduleNoList.add(new SelectItem("0", "--Select--"));
            List list = npciClrRemot.getAllScheduleNos(ymd.format(dmy.parse(date)), getOrgnBrCode(), chequeType);
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                scheduleNoList.add(new SelectItem(ele.get(0).toString(), ele.get(0).toString()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            this.setErrMessage(ex.getMessage());
        }
    }

    public void getDataToEditInGrid() {
        try {
            if (date == null || date.equalsIgnoreCase("")) {
                this.setErrMessage("Please Fill Date.!");
                return;
            }
            if (function.equalsIgnoreCase("EDIT")) {
                gridDetail = npciClrRemot.getctsUploadDataToEdit(date, chequeType, scheduleNo);
            }
            if (!gridDetail.isEmpty()) {
                this.setErrMessage("Select a Row to Modify.");
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void btnAction() {
        try {
//            if ((!(this.acno == null || this.acno.equalsIgnoreCase("")))
//                    && (((currentItem.getAcno() == null || currentItem.getAcno().equalsIgnoreCase("")) && (currentItem.getModifyFlag().equalsIgnoreCase("")))
//                    || (currentItem.getModifyFlag().equalsIgnoreCase("Y")))) {
            int n = npciClrRemot.getInstrumentStatus(ymd.format(dmy.parse(date)), chequeType,
                    Integer.parseInt(scheduleNo), currentItem.getTxnId(), currentItem.getItemSeqNo());
            if (n == 2 || n == 4 || n == 5) {
                this.setErrMessage("Process for this instrument is already completed.");
                return;
            }
            CtsChequeStatusReportPojo data = new CtsChequeStatusReportPojo();
            String itemTranCode = "";
            if (ctsByMicr == 1) {
                data.setItemSAN(currentItem.getItemSAN());
                itemTranCode = currentItem.getItemTransCode();
            } else {
                data.setItemSAN(this.sanNo);
                if (this.sanNo.equalsIgnoreCase("000000")) {
                    itemTranCode = currentItem.getItemTransCode();
                } else {
                    itemTranCode = sanNo.substring(1, 3);
                }
            }
            data.setInstNo(this.serialNo);
            data.setInstDt(this.instDate);
            data.setFavourOf(this.favorOf);
            data.setEnterBy(getUserName());
            data.setTxnId(currentItem.getTxnId());
            data.setBatchNo(currentItem.getBatchNo());
            data.setAcno(this.acno);
            data.setCustName(this.custName);
            data.setOperMode(operMode);
            data.setDestBranch(destBranchCode);
            data.setSeqNo(seqNo);

            data.setItemTransCode(itemTranCode);
            if (ctsByMicr == 1) {
                data.setItemPayorBnkRoutNo(this.sanNo);
            }else{
                data.setItemPayorBnkRoutNo(currentItem.getItemPayorBnkRoutNo());
            }
            String result = npciClrRemot.updateCTSUploadData(data);
            if (result.equalsIgnoreCase("Data Successfully Updated")) {
                getDataToEditInGrid();
                this.setErrMessage(result);
                //*****reset the form Data
                this.sanNo = "";
                this.serialNo = "";
                this.acno = "";
                this.custName = "";
                this.operMode = "";
                this.seqNo = "";
                this.instDate = "";
                this.instNo = "";
                this.favorOf = "";
                this.destBranchCode = "";
                this.destBranch = "";
                this.imageData = "";
                this.folderName = "";
                this.currentItem = null;
                //****
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public boolean validateField() {
        if (function == null || function.equalsIgnoreCase("0") || function.equalsIgnoreCase("")) {
            this.setErrMessage("Please Select Function.!");
            return false;
        }
        if (date.equalsIgnoreCase("")) {
            this.setErrMessage("Please Fill Date.!");
            return false;
        }
        if (chequeType == null || chequeType.equalsIgnoreCase("") || chequeType.equalsIgnoreCase("0")) {
            this.setErrMessage("Please Select Transaction Type!");
            return false;
        }
        if (scheduleNo == null || scheduleNo.equalsIgnoreCase("") || scheduleNo.equalsIgnoreCase("0")) {
            this.setErrMessage("Please Fill Schedule No.!");
            return false;
        }
        return true;
    }

    public void setTableDataToForm() {
        try {
            if (currentItem == null) {
                this.setErrMessage("Please select row from table.");
                return;
            }
            //*****reset the form Data
            this.sanNo = "";
            this.serialNo = "";
            this.acno = "";
            this.custName = "";
            this.operMode = "";
            this.seqNo = "";
            this.instDate = "";
            this.instNo = "";
            this.favorOf = "";
            this.destBranchCode = "";
            this.destBranch = "";
            //****
            this.sanNo = currentItem.getItemSAN();
            this.serialNo = currentItem.getInstNo();
            String itemTranCode = "";
            if (this.sanNo.equalsIgnoreCase("000000")) {
                itemTranCode = currentItem.getItemTransCode();
            } else {
                itemTranCode = sanNo.substring(1, 3);
            }
            String nature = npciClrRemot.getTransCodeMap().get(itemTranCode);

            BillTypeMasterTO billTo = npciClrRemot.getBillTypeDetail("PO");
            String[] accDetail = {};

            if (ctsByMicr == 1) {
                this.sanNo = currentItem.getItemPayorBnkRoutNo();
                accDetail = npciClrRemot.getMICRDetail(currentItem.getItemTransCode(), serialNo, billTo, currentItem.getItemPayorBnkRoutNo());
            } else {
                accDetail = npciClrRemot.getSANDetail(itemTranCode, sanNo, serialNo, billTo, currentItem.getItemPayorBnkRoutNo());
            }

            this.acno = accDetail[0];
            this.custName = accDetail[1];
            this.operMode = accDetail[2];
            this.seqNo = accDetail[3];
            if (nature != null && nature.equalsIgnoreCase("PO")) {
                if (!accDetail[4].equalsIgnoreCase("")) {
                    this.instDate = dmy.format(ymd.parse(accDetail[4]));
                } else {
                    this.instDate = accDetail[4];
                }
                disableInstDt = false;
            } else {
                disableInstDt = true;
                this.instDate = currentItem.getInstDt();
            }
            this.chequeType = currentItem.getEmFlag();
            this.instNo = currentItem.getInstNo();
            this.favorOf = currentItem.getFavourOf();
            if (!(acno == null || acno.equalsIgnoreCase(""))) {
                this.destBranch = commonReport.getBranchNameByBrncode(acno.substring(0, 2));
                destBranchCode = acno.substring(0, 2);
            } else {
                this.destBranch = "";
                destBranchCode = "";
            }

            if (ctsSponsor == 2) { //For Khatri- NPCI CTS
                this.setFolderName(String.valueOf(Integer.parseInt(this.chequeType)) + "_" + String.valueOf(Integer.parseInt(this.scheduleNo)) + "_" + getOrgnBrCode());
            } else {
                getImageFolderName(Integer.parseInt(this.scheduleNo), getOrgnBrCode().length() == 1 ? "0" + getOrgnBrCode() : getOrgnBrCode());
            }

            if (ctsSponsor == 1) {
                this.setImageData(currentItem.getImageCode() + "_Fb.jpg");
            } else if (ctsSponsor == 2) {
                this.setImageData(currentItem.getImageCode() + ".JPEG");
            } else {
                this.setImageData(currentItem.getImageCode() + ".GF");
            }

        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public String viewReport() {
        try {
            if (!validateField()) {
                return "";
            }
            List<CTSUploadReportPojo> resultList = npciClrRemot.getctsUploadDataToReport(date, chequeType, scheduleNo, getOrgnBrCode());
            if (!resultList.isEmpty()) {
                Map fillParams = new HashMap();
                fillParams.put("pReportName", "CTS UPLOAD REPORT");
                fillParams.put("pReportDate", date);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pbankName", commonReport.getBankName());
                new ReportBean().jasperReport("cts_upload_report", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, "CTS UPLOAD REPORT");
            } else {
                errMessage = "Data does not found";
                return null;
            }
        } catch (ApplicationException e) {
            this.setErrMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrMessage(e.getMessage());
        }
        return "report";
    }

    public void viewPdfReport() {
        try {
            if (!validateField()) {
                return;
            }
            List<CTSUploadReportPojo> resultList = npciClrRemot.getctsUploadDataToReport(date, chequeType, scheduleNo, getOrgnBrCode());
            if (!resultList.isEmpty()) {
                Map fillParams = new HashMap();
                String report = "CTS UPLOAD REPORT";
                fillParams.put("pReportName", report);
                fillParams.put("pReportDate", date);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pbankName", commonReport.getBankName());

                new ReportBean().openPdf("CTS Upload Report_" + ymd.format(dmy.parse(getTodayDate())), "cts_upload_report", new JRBeanCollectionDataSource(resultList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            } else {
                errMessage = "Data not found";
                return;
            }
        } catch (ApplicationException e) {
            this.setErrMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrMessage(e.getMessage());
        }
    }

    public void getWhiteImage() {
        try {
//            UnAuthItem item = dataItemList.get(currentRow);
            if (ctsSponsor == 2) { //For Khatri- NPCI CTS
                this.setFolderName(String.valueOf(Integer.parseInt(this.chequeType)) + "_" + String.valueOf(Integer.parseInt(this.scheduleNo)) + "_" + getOrgnBrCode());
            } else {
                getImageFolderName(Integer.parseInt(this.scheduleNo), getOrgnBrCode().length() == 1 ? "0" + getOrgnBrCode() : getOrgnBrCode());
            }
            if (ctsSponsor == 1) {
                this.setImageData(currentItem.getImageCode() + "_Fb.jpg");
            } else if (ctsSponsor == 2) {
                this.setImageData(currentItem.getImageCode() + ".JPEG");
            } else {
                this.setImageData(currentItem.getImageCode() + ".jpg");
            }
        } catch (Exception e) {
            this.setErrMessage(e.getLocalizedMessage());

        }
    }

    public void getBackImage() {
        try {
            if (ctsSponsor == 2) { //For Khatri- NPCI CTS
                this.setFolderName(String.valueOf(Integer.parseInt(this.chequeType)) + "_" + String.valueOf(Integer.parseInt(this.scheduleNo)) + "_" + getOrgnBrCode());
            } else {
                getImageFolderName(Integer.parseInt(this.scheduleNo), getOrgnBrCode().length() == 1 ? "0" + getOrgnBrCode() : getOrgnBrCode());
            }
            if (ctsSponsor == 1) {
                this.setImageData(currentItem.getImageCode() + "_Ba.jpg");
            } else if (ctsSponsor == 2) {
                this.setImageData(currentItem.getImageCode() + ".JPG");
            } else {
                this.setImageData(currentItem.getImageCode() + ".png");
            }

        } catch (Exception e) {
            this.setErrMessage(e.getLocalizedMessage());

        }
    }

    public void getGrayImage() {
        try {
            if (ctsSponsor == 2) { //For Khatri- NPCI CTS
                this.setFolderName(String.valueOf(Integer.parseInt(this.chequeType)) + "_" + String.valueOf(Integer.parseInt(this.scheduleNo)) + "_" + getOrgnBrCode());
            } else {
                getImageFolderName(Integer.parseInt(this.scheduleNo), getOrgnBrCode().length() == 1 ? "0" + getOrgnBrCode() : getOrgnBrCode());
            }
            if (ctsSponsor == 1) {
                this.setImageData(currentItem.getImageCode() + "_Fg.jpg");
            } else if (ctsSponsor == 2) {
                this.setImageData(currentItem.getImageCode() + ".PNG");
            } else {
                this.setImageData(currentItem.getImageCode() + ".GF");
            }
        } catch (Exception e) {
            this.setErrMessage(e.getMessage());
        }
    }

    public void getImageFolderName(Integer scheduleCount, String orgnBrCode) {
        try {
            this.setFolderName(scheduleCount.toString() + "_" + orgnBrCode);
        } catch (Exception e) {
            this.setErrMessage(e.getMessage());
        }
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }

    public void refreshForm() {
        this.errMessage = "";
        this.btnValue = "";
        this.date = "";
        this.chequeType = "0";
        this.scheduleNo = "0";
        this.instNo = "";
        this.instDate = "";
        this.favorOf = "";
        this.acno = "";
        this.custName = "";
        this.operMode = "";
        this.destBranch = "";
        this.seqNo = "";
        this.sanNo = "";
        this.serialNo = "";
        this.gridDetail = new ArrayList<>();
        this.currentItem = new CtsChequeStatusReportPojo();
        disableInstDt = false;
        this.imageData = "";
        this.folderName = "";
        this.instNoFilter = "";
        this.setSanNature("0");
        if (ctsByMicr == 1) {
            setSanLabel("MICR CODE : ");
        } else {
            setSanLabel("SAN NO. : ");
        }
    }

    public boolean filterInstrumentNoList(Object current) {
        CtsChequeStatusReportPojo currentCapital = (CtsChequeStatusReportPojo) current;
        String inst = getInstNoFilter();
        if (inst.length() == 0) {
            return true;
        }
        if (currentCapital.getInstNo().toLowerCase().startsWith(getInstNoFilter().toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

//***************************************Getter And Setter***********************************************//
    public String getSanLabel() {
        return sanLabel;
    }

    public void setSanLabel(String sanLabel) {
        this.sanLabel = sanLabel;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public List<SelectItem> getChequeTypeList() {
        return chequeTypeList;
    }

    public void setChequeTypeList(List<SelectItem> chequeTypeList) {
        this.chequeTypeList = chequeTypeList;
    }

    public String getFunction() {
        return function;
    }

    public String getBtnValue() {
        return btnValue;
    }

    public void setBtnValue(String btnValue) {
        this.btnValue = btnValue;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<CtsChequeStatusReportPojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<CtsChequeStatusReportPojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public CtsChequeStatusReportPojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(CtsChequeStatusReportPojo currentItem) {
        this.currentItem = currentItem;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getChequeType() {
        return chequeType;
    }

    public void setChequeType(String chequeType) {
        this.chequeType = chequeType;
    }

    public String getScheduleNo() {
        return scheduleNo;
    }

    public void setScheduleNo(String scheduleNo) {
        this.scheduleNo = scheduleNo;
    }

    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public String getInstDate() {
        return instDate;
    }

    public void setInstDate(String instDate) {
        this.instDate = instDate;
    }

    public String getFavorOf() {
        return favorOf;
    }

    public void setFavorOf(String favorOf) {
        this.favorOf = favorOf;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getOperMode() {
        return operMode;
    }

    public void setOperMode(String operMode) {
        this.operMode = operMode;
    }

    public String getDestBranch() {
        return destBranch;
    }

    public void setDestBranch(String destBranch) {
        this.destBranch = destBranch;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getSanNo() {
        return sanNo;
    }

    public void setSanNo(String sanNo) {
        this.sanNo = sanNo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getDisplayModifyUploadDate() {
        return displayModifyUploadDate;
    }

    public void setDisplayModifyUploadDate(String displayModifyUploadDate) {
        this.displayModifyUploadDate = displayModifyUploadDate;
    }

    public String getDisplayUploadReport() {
        return displayUploadReport;
    }

    public void setDisplayUploadReport(String displayUploadReport) {
        this.displayUploadReport = displayUploadReport;
    }

    public String getDisplayUpdateBtn() {
        return displayUpdateBtn;
    }

    public void setDisplayUpdateBtn(String displayUpdateBtn) {
        this.displayUpdateBtn = displayUpdateBtn;
    }

    public List<SelectItem> getScheduleNoList() {
        return scheduleNoList;
    }

    public void setScheduleNoList(List<SelectItem> scheduleNoList) {
        this.scheduleNoList = scheduleNoList;
    }

    public boolean isDisableInstDt() {
        return disableInstDt;
    }

    public void setDisableInstDt(boolean disableInstDt) {
        this.disableInstDt = disableInstDt;
    }

    public int getCtsSponsor() {
        return ctsSponsor;
    }

    public void setCtsSponsor(int ctsSponsor) {
        this.ctsSponsor = ctsSponsor;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public String getInstNoFilter() {
        return instNoFilter;
    }

    public void setInstNoFilter(String instNoFilter) {
        this.instNoFilter = instNoFilter;
    }

    public List<SelectItem> getSanNatureList() {
        return sanNatureList;
    }

    public void setSanNatureList(List<SelectItem> sanNatureList) {
        this.sanNatureList = sanNatureList;
    }

    public String getSanNature() {
        return sanNature;
    }

    public void setSanNature(String sanNature) {
        this.sanNature = sanNature;
    }
}
