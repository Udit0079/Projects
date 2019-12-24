/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.dto.other.NpciOacDto;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.OtherNpciMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

public class NpciOacVerification extends BaseBean {

    private String errMessage;
    private String fileUpDt;
    private String seqNo;
    private List<SelectItem> seqNoList;
    private String oldAcno;
    private String oldAcName;
    private String micr;
    private String acType;
    private String stRrn;
    private String acValidFlag;
    private String acno;
    private String name;
    private String acValid;
    private String branch;
    private List<SelectItem> acValidList;
    private List<SelectItem> branchList;
    private String reason;
    private List<SelectItem> reasonList;
    private boolean reasonDisable = true;
    private List<NpciOacDto> gridDetail;
    private NpciOacDto currentItem;
//    private NpciMgmtFacadeRemote npciRemote;
    private OtherNpciMgmtFacadeRemote otherNpciRemote;
    private CommonReportMethodsRemote commonReport;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public NpciOacVerification() {
        try {
//            npciRemote = (NpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciMgmtFacade");
            otherNpciRemote = (OtherNpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("OtherNpciMgmtFacade");
            commonReport = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");

            seqNoList = new ArrayList<SelectItem>();
            seqNoList.add(new SelectItem("0", "--Select--"));

            initData();
            refreshForm();
            this.setErrMessage("Please fill File Upload Date.");
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void initData() {
        try {
            acValidList = new ArrayList<SelectItem>();
            acValidList.add(new SelectItem("0", "--Select--"));
            acValidList.add(new SelectItem("Y", "Valid"));
            acValidList.add(new SelectItem("N", "In-Valid"));
            acValidList.add(new SelectItem("Z", "As Per CBS"));

            reasonList = new ArrayList<SelectItem>();
            reasonList.add(new SelectItem("0", "--Select--"));
            List list = commonReport.getRefRecList("314");
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                reasonList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }
            branchList = new ArrayList<SelectItem>();
            branchList.add(new SelectItem("0", "--Select--"));
            list = commonReport.getAlphacodeAllAndBranch(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                String brncode = ele.get(1).toString().trim().length() < 2 ? "0" + ele.get(1).toString().trim()
                        : ele.get(1).toString().trim();
                branchList.add(new SelectItem(brncode, ele.get(0).toString().trim()));
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void fileUpDtProcess() {
        this.setErrMessage("");
        try {
            if (validateFileUpDate()) {
                seqNoList = new ArrayList<SelectItem>();
                seqNoList.add(new SelectItem("0", "--Select--"));
                List list = otherNpciRemote.findAllFileSeqNo(ymd.format(dmy.parse(fileUpDt)), "NPCI-OAC");
                for (int i = 0; i < list.size(); i++) {
                    Vector ele = (Vector) list.get(i);
                    seqNoList.add(new SelectItem(ele.get(0).toString()));
                }
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void retrieveProcess() {
        this.setErrMessage("");
        try {
            if (validateBranch() && validateFileUpDate() && validateSeqNo()) {
                fieldRefresh();
                gridDetail = otherNpciRemote.getDataForInComingDateAndSeqNo(ymd.format(dmy.parse(fileUpDt)),
                        this.seqNo, "NPCI-OAC", getOrgnBrCode(), this.branch);
                this.setErrMessage("Now you can verify an entry.");
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void setTableDataToForm() {
        try {
            if (currentItem == null) {
                this.setErrMessage("Please select a row from table to verify.");
                return;
            }
            selectFieldRefresh();
            this.setOldAcno(currentItem.getOldAcno());
            this.setOldAcName(currentItem.getOldAcName());
            this.setMicr(currentItem.getMicr());
            this.setAcType(currentItem.getAcType());
            this.setStRrn(currentItem.getRrn());
            this.setAcValidFlag(currentItem.getAcValFlag());
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void retrieveValidAcno() {
        this.setErrMessage("");
        try {
            this.setName("");
            if (this.acno == null || this.acno.equals("")) {
                this.setErrMessage("A/c No can not be blank.");
                return;
            }
            if (this.acno.trim().length() != 12) {
                this.setErrMessage("Please fill 12 digit A/c No.");
                return;
            }
            String result = ftsRemote.ftsAcnoValidate(this.acno, 1, "");
            if (!result.equalsIgnoreCase("true")) {
                this.setErrMessage(result);
                return;
            }
            this.setName(ftsRemote.getCustName(this.acno));
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void acValSelectProcess() {
        this.setErrMessage("");
        this.setReasonDisable(true);
        try {
            if (this.acValid.equals("N")) {
                this.setReasonDisable(false);
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void processAction() {
        this.setErrMessage("");
        try {
            if (this.acValid == null || this.acValid.equals("0") || this.acValid.equals("")) {
                this.setErrMessage("Please select A/c Valid.");
                return;
            }
            if (this.acValid.equals("Y")) {
                if ((this.acno == null || this.acno.equals("") || this.acno.trim().length() != 12)
                        || (this.name == null || this.name.equals(""))) {
                    this.setErrMessage("If A/c is valid then A/c No and Name will required.");
                    return;
                }
                this.reason = "";
            }
            if (this.acValid.equals("N")) {
                if (this.reason == null || this.reason.equals("0") || this.reason.equals("")) {
                    this.setErrMessage("If A/c is In-Valid then Reason will required.");
                    return;
                }
                this.acno = "";
                this.name = "";
            }
            if (this.acValid.equals("Z")) {
                this.acno = "";
                this.name = "";
                this.reason = "99";
            }
            if (currentItem == null) {
                this.setErrMessage("Please select a row from table to verify.");
                return;
            }
            if (validateBranch() && validateFileUpDate() && validateSeqNo()) {
                String result = otherNpciRemote.verifyEntry(currentItem, this.acno.trim(), this.name.trim(),
                        this.acValid, this.reason, getUserName(), ymd.format(dmy.parse(fileUpDt)), this.seqNo, "NPCI-OAC");
                if (!result.equalsIgnoreCase("true")) {
                    this.setErrMessage(result);
                    return;
                }

                retrieveProcess();
                this.setErrMessage("Entry has been verfied successfully.");
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public boolean validateFileUpDate() {
        this.setErrMessage("");
        try {
            if (this.fileUpDt == null || this.fileUpDt.equals("")) {
                this.setErrMessage("Please fill proper File Upload Date.");
                return false;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(this.fileUpDt)) {
                this.setErrMessage("Please fill proper File Upload Date.");
                return false;
            }
            if (dmy.parse(this.fileUpDt).compareTo(dmy.parse(dmy.format(new Date()))) > 0) {
                this.setErrMessage("File Upload Date can not be greater than current date.");
                return false;
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean validateSeqNo() {
        this.setErrMessage("");
        try {
            if (this.seqNo == null || this.seqNo.equals("0") || this.seqNo.equals("")) {
                this.setErrMessage("Please select Seq No.");
                return false;
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean validateBranch() {
        this.setErrMessage("");
        try {
            if (this.branch == null || this.branch.equals("0") || this.branch.equals("")) {
                this.setErrMessage("Please select branch.");
                return false;
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
            return false;
        }
        return true;
    }

    public void selectFieldRefresh() {
        this.setOldAcno("");
        this.setOldAcName("");
        this.setMicr("");
        this.setAcType("");
        this.setStRrn("");
        this.setAcValidFlag("");
        this.setAcno("");
        this.setName("");
        this.setAcValid("0");
        this.setReason("0");
        this.setReasonDisable(true);
//        this.currentItem = null;
    }

    public void fieldRefresh() {
        this.setErrMessage("");
        this.setOldAcno("");
        this.setOldAcName("");
        this.setMicr("");
        this.setAcType("");
        this.setStRrn("");
        this.setAcValidFlag("");
        this.setAcno("");
        this.setName("");
        this.setAcValid("0");
        this.setReason("0");
        this.setReasonDisable(true);
        gridDetail = new ArrayList<NpciOacDto>();
        this.currentItem = null;
    }

    public void refreshForm() {
        this.setErrMessage("");
        this.setFileUpDt("");
        seqNoList = new ArrayList<SelectItem>();
        seqNoList.add(new SelectItem("0", "--Select--"));
        this.setOldAcno("");
        this.setOldAcName("");
        this.setMicr("");
        this.setAcType("");
        this.setStRrn("");
        this.setAcValidFlag("");
        this.setAcno("");
        this.setName("");
        this.setAcValid("0");
        this.setReason("0");
        this.setReasonDisable(true);
        gridDetail = new ArrayList<NpciOacDto>();
        this.currentItem = null;
        this.setBranch("--Select--");
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

    public List<SelectItem> getSeqNoList() {
        return seqNoList;
    }

    public void setSeqNoList(List<SelectItem> seqNoList) {
        this.seqNoList = seqNoList;
    }

    public String getOldAcno() {
        return oldAcno;
    }

    public void setOldAcno(String oldAcno) {
        this.oldAcno = oldAcno;
    }

    public String getOldAcName() {
        return oldAcName;
    }

    public void setOldAcName(String oldAcName) {
        this.oldAcName = oldAcName;
    }

    public String getMicr() {
        return micr;
    }

    public void setMicr(String micr) {
        this.micr = micr;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getAcValidFlag() {
        return acValidFlag;
    }

    public void setAcValidFlag(String acValidFlag) {
        this.acValidFlag = acValidFlag;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcValid() {
        return acValid;
    }

    public void setAcValid(String acValid) {
        this.acValid = acValid;
    }

    public List<SelectItem> getAcValidList() {
        return acValidList;
    }

    public void setAcValidList(List<SelectItem> acValidList) {
        this.acValidList = acValidList;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<SelectItem> getReasonList() {
        return reasonList;
    }

    public void setReasonList(List<SelectItem> reasonList) {
        this.reasonList = reasonList;
    }

    public boolean isReasonDisable() {
        return reasonDisable;
    }

    public void setReasonDisable(boolean reasonDisable) {
        this.reasonDisable = reasonDisable;
    }

    public List<NpciOacDto> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<NpciOacDto> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public NpciOacDto getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(NpciOacDto currentItem) {
        this.currentItem = currentItem;
    }

    public String getStRrn() {
        return stRrn;
    }

    public void setStRrn(String stRrn) {
        this.stRrn = stRrn;
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
}
