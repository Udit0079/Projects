/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.other.MandateRecordTo;
import com.cbs.dto.other.NpciOacDto;
import com.cbs.facade.cdci.IBSWSFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.OtherNpciMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.report.other.JointHolderBean;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.model.SelectItem;

public class NpciEcsMandate extends BaseBean {

    private String errMessage;
    private String fileUpDt;
    private String seqNo;
    private List<SelectItem> seqNoList;
    private String oldAcno;
    private String oldAcName;
    private String micr;
    private String acType;
    private String stRrn;
    private String statusFlag;
    private String acno, accountNo, acNoLen;
    private String name;
    private String status;
    private String confirmationMsg;
    private String balance;
    private String ecsAmount;
    private boolean odBalFlag;
    private String branch;
    private String txnType;
    private String viewID = "/pages/master/sm/test.jsp";
    private List<SelectItem> branchList;
    private List<SelectItem> statusList;
    private String reason;
    private List<SelectItem> reasonList;
    private List<SelectItem> txnTypeList;
    private boolean reasonDisable = true;
    private boolean jtDetailPopUp;
    private List<NpciOacDto> gridDetail;
    private NpciOacDto currentItem;
    private OtherNpciMgmtFacadeRemote otherNpciRemote;
    private CommonReportMethodsRemote commonReport;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private IBSWSFacadeRemote ibswsRemote;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("#.##");
    private String mandateViewFlag;
    private String mandatePanelAc;
    private String mandatePanelName;
    private List<MandateRecordTo> mandateGridDetail;
    private JointHolderBean jointBean;
    private Map ActypeConversionMap;

    public NpciEcsMandate() {
        try {
            otherNpciRemote = (OtherNpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("OtherNpciMgmtFacade");
            commonReport = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            ibswsRemote = (IBSWSFacadeRemote) ServiceLocator.getInstance().lookup("IBSWSFacade");
            this.setAcNoLen(getAcNoLength());
            seqNoList = new ArrayList<SelectItem>();
            seqNoList.add(new SelectItem("0", "--Select--"));

            initData();
            refreshForm();
            this.setErrMessage("Please fill File Settlement Date.");
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void initData() {
        try {
            txnTypeList = new ArrayList<SelectItem>();
            txnTypeList.add(new SelectItem("0", "--Select--"));
            txnTypeList.add(new SelectItem("ECS-DR"));
            txnTypeList.add(new SelectItem("ACH-DR"));

            statusList = new ArrayList<SelectItem>();
            statusList.add(new SelectItem("0", "--Select--"));
            statusList.add(new SelectItem("P", "PASS"));
            statusList.add(new SelectItem("R", "RETURN"));

            reasonList = new ArrayList<SelectItem>();
            branchList = new ArrayList<SelectItem>();
            branchList.add(new SelectItem("0", "--Select--"));
            List list = commonReport.getAlphacodeAllAndBranch(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                String brncode = ele.get(1).toString().trim().length() < 2 ? "0" + ele.get(1).toString().trim()
                        : ele.get(1).toString().trim();
                branchList.add(new SelectItem(brncode, ele.get(0).toString().trim()));
            }

            ActypeConversionMap = commonReport.getConvertedAccTypeByMapping();
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void getAllRejectReason() {
        this.setErrMessage("");
        try {
            reasonList = new ArrayList<SelectItem>();
            reasonList.add(new SelectItem("0", "--Select--"));
            if (this.txnType.equals("ECS-DR")) {    //ECS-DR Return code
                List list = commonReport.getRefRecList("315");
                for (int i = 0; i < list.size(); i++) {
                    Vector ele = (Vector) list.get(i);
                    reasonList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            } else if (this.txnType.equals("ACH-DR")) {  //ACH-DR Return code
                List list = commonReport.getRefRecList("319");
                for (int i = 0; i < list.size(); i++) {
                    Vector ele = (Vector) list.get(i);
                    reasonList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void fileUpDtProcess() {
        this.setErrMessage("");
        try {
            if (validateTxnType() && validateFileUpDate()) {
                seqNoList = new ArrayList<SelectItem>();
                seqNoList.add(new SelectItem("0", "--Select--"));

                List list = otherNpciRemote.findAllFileSeqNo(ymd.format(dmy.parse(fileUpDt)), this.txnType);
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
            if (validateTxnType() && validateBranch() && validateFileUpDate() && validateSeqNo()) {
                fieldRefresh();
                gridDetail = otherNpciRemote.getDataForInComingDateAndSeqNo(ymd.format(dmy.parse(fileUpDt)),
                        this.seqNo, this.txnType, getOrgnBrCode(), this.branch);
                for (NpciOacDto dto : gridDetail) {
                    if (dto.getAcValFlag().equalsIgnoreCase("P")) {
                        dto.setAcValFlag("PASS");
                    } else if (dto.getAcValFlag().equalsIgnoreCase("R")) {
                        dto.setAcValFlag("RETURN");
                    }

                    if (!(dto.getReturnCode() == null || dto.getReturnCode().equals(""))) {
                        if (this.txnType.equals("ECS-DR")) {
                            dto.setReturnCode(commonReport.getRefRecDesc("315", dto.getReturnCode()));
                        } else if (this.txnType.equals("ACH-DR")) {
                            dto.setReturnCode(commonReport.getRefRecDesc("319", dto.getReturnCode()));
                        }
                    }

                    BigDecimal amt = new BigDecimal(dto.getAmount()).divide(new BigDecimal("100"));
                    dto.setAmount(formatter.format(amt.doubleValue()));
                }
                this.setErrMessage("Now you can verify an entry. Please select a row from table.");
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
            this.setStRrn(currentItem.getTranRef());
            this.setStatusFlag(currentItem.getAcValFlag());
            this.setEcsAmount(currentItem.getAmount());
            this.setErrMessage("");

            this.accountNo = "";
            this.name = "";
            this.balance = "";

            int autoFillFlag = commonReport.getCodeByReportName("ACNO-AUTOFILL-ECSACH-MND");
            if (autoFillFlag == 1) {

                String acTypeFinal = acType;
                if ((!ActypeConversionMap.isEmpty()) && (ActypeConversionMap.containsKey(acType))) {
                    acTypeFinal = ActypeConversionMap.get(acType).toString();
                }

                if ((oldAcno.length() > 0 && oldAcno.length() <= 6) && txnType.equalsIgnoreCase("ECS-DR")) {
                    acno = commonReport.getcbsFormatAcno(oldAcno, micr, acTypeFinal);
                }

                if (this.oldAcno.length() == 12 || this.oldAcno.length() == 15) {
                    acno = ftsRemote.getNewAccountNumber(this.oldAcno);
                }
                accountNo = acno;

                String alphacode = commonReport.getAlphacodeByBrncode(getOrgnBrCode());
                if (!alphacode.equalsIgnoreCase("HO")) {
                    if (!ftsRemote.getCurrentBrnCode(this.acno).equalsIgnoreCase(getOrgnBrCode())) {
                        this.setErrMessage("This entry not related to your branch .");
                        return;
                    }
                }

                String result = ftsRemote.ftsAcnoValidate(this.acno, 1, "");
                if (!result.equalsIgnoreCase("true")) {
                    this.setErrMessage(result);
                    return;
                }
                this.setName(ftsRemote.getCustName(this.acno));
                this.setBalance(ibswsRemote.getBalanceByAccountNumber(this.acno, ymd.format(new Date())).toString());

            }

        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void retrieveValidAcno() {
        this.setErrMessage("");
        try {
            this.setName("");
            this.setBalance("");
            //New Addition
            this.setMandatePanelAc("");
            this.setMandatePanelName("");
            this.setMandateViewFlag("false");
            this.mandateGridDetail = new ArrayList<MandateRecordTo>();
            //New Addition End
            if (this.accountNo == null || this.accountNo.equals("")) {
                this.setErrMessage("A/c No can not be blank.");
                return;
            }
            //if (this.acno.trim().length() != 12) {
            if (!this.accountNo.equalsIgnoreCase("") && ((this.accountNo.length() != 12) && (this.accountNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setErrMessage("Please fill proper A/c No.");
                return;
            }
            acno = ftsRemote.getNewAccountNumber(accountNo);

            String alphacode = commonReport.getAlphacodeByBrncode(getOrgnBrCode());
            if (!alphacode.equalsIgnoreCase("HO")) {
                if (!ftsRemote.getCurrentBrnCode(this.acno).equalsIgnoreCase(getOrgnBrCode())) {
                    this.setErrMessage("Please fill self branch A/c Number.");
                    return;
                }
            }
            String result = ftsRemote.ftsAcnoValidate(this.acno, 1, "");
            if (!result.equalsIgnoreCase("true")) {
                this.setErrMessage(result);
                return;
            }
            this.setName(ftsRemote.getCustName(this.acno));
            this.setBalance(ibswsRemote.getBalanceByAccountNumber(this.acno, ymd.format(new Date())).toString());
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void statusSelectProcess() {
        this.setErrMessage("");
        this.setReasonDisable(true);
        try {
            this.reasonDisable = this.status.equals("R") ? false : true;
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void processAction(String odBalChq) {
        this.setErrMessage("");
        try {
            if (this.status == null || this.status.equals("0") || this.status.equals("")) {
                this.setErrMessage("Please select Status.");
                return;
            }
            if (this.status.equals("P")) { //Pass
                if ((this.acno == null || this.acno.equals("") || this.acno.trim().length() != 12)
                        || (this.name == null || this.name.equals(""))) {
                    this.setErrMessage("In case of pass A/c No and Name will required.");
                    return;
                }
                this.reason = "";
            }
            if (this.status.equals("R")) { //Return
                if (this.reason == null || this.reason.equals("0") || this.reason.equals("")) {
                    this.setErrMessage("In case of return Reason will required.");
                    return;
                }
//                this.acno = "";
//                this.name = "";
            }
            if (currentItem == null) {
                this.setErrMessage("Please select a row from table to verify.");
                return;
            }
            if (validateBranch() && validateFileUpDate() && validateSeqNo()) {
                //Stop Payment Checking
                if (this.txnType.equalsIgnoreCase("ACH-DR") && status.equalsIgnoreCase("P")) {
                    List umrnList = otherNpciRemote.getMandateDetail(currentItem.getUmrn().trim());
                    if (!umrnList.isEmpty()) {
                        Vector umrnVec = (Vector) umrnList.get(0);
                        if (umrnVec.get(0).toString().equalsIgnoreCase("S")) {
                            this.setErrMessage("UMRN for this entry is stop payment.");
                            return;
                        }
                    }
                }
                //End Here
                //Balance Checking
                Double amount = Double.parseDouble(currentItem.getAmount());
                if (!odBalChq.equalsIgnoreCase("odcheck") && status.equalsIgnoreCase("P")) { //Pass Case
                    String actNature = ftsRemote.getAccountNature(this.acno);
                    String chkBalResult = ftsRemote.chkBal(this.acno, 1, 0, actNature);
                    if (!chkBalResult.equalsIgnoreCase("true")) {
                        if (actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            String chkBalance = ftsRemote.checkBalForOdLimit(this.acno, amount, getUserName());
                            if (chkBalance.equalsIgnoreCase("99")) {
                                confirmationMsg = "Limit Exceede for : " + this.acno + ". \n To Proceed press Yes, \n To Cancel press No";
                                this.odBalFlag = true;
                                return;
                            } else if (!chkBalance.equalsIgnoreCase("1")) {
                                this.odBalFlag = false;
                                this.confirmationMsg = "";
                                this.setErrMessage("Balance Exceeds.");
                                return;
                            }
                        } else if (actNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                            String balResult = ftsRemote.checkBalance(this.acno, amount, getUserName());
                            if (!balResult.equalsIgnoreCase("true")) {
                                this.odBalFlag = false;
                                this.confirmationMsg = "";
                                this.setErrMessage("Balance Exceeds.");
                                return;
                            }
                        }
                    }
                }

                String result = otherNpciRemote.ecsMandate(currentItem, this.acno.trim(), this.name.trim(),
                        this.status, this.reason, getUserName(), ymd.format(dmy.parse(fileUpDt)),
                        this.seqNo, this.txnType, amount, getTodayDate(), getOrgnBrCode(), this.branch);

                String resultLastUpdation = ftsRemote.lastTxnDateUpdation(acno);
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

    public void odBalExceedProcess() {
        try {
            processAction("odcheck");
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public boolean validateTxnType() {
        this.setErrMessage("");
        try {
            if (this.txnType == null || this.txnType.equals("0") || this.txnType.equals("")) {
                this.setErrMessage("Please select Txn. Type");
                return false;
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean validateFileUpDate() {
        this.setErrMessage("");
        try {
            if (this.fileUpDt == null || this.fileUpDt.equals("")) {
                this.setErrMessage("Please fill proper File Settlement Date.");
                return false;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(this.fileUpDt)) {
                this.setErrMessage("Please fill proper File Settlement Date.");
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

    public void mandateViewAuthProcess() {
        try {
            this.mandateViewFlag = "false";
            if (this.acno == null || this.acno.equals("") || this.acno.trim().length() != 12) {
                this.setErrMessage("A/c No can not be blank.");
                return;
            }
            if (this.name == null || this.name.equals("")) {
                this.setErrMessage("Name can not be blank.");
                return;
            }

            mandateGridDetail = otherNpciRemote.retrieveMandateData(this.acno);
            if (mandateGridDetail.isEmpty()) {
                this.setErrMessage("There is no mandate detail.");
                this.mandateViewFlag = "false";
                return;
            }
            this.mandateViewFlag = "true";
            this.mandatePanelAc = this.acno;
            this.mandatePanelName = this.name;
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void validateJointAccount() {
        this.setErrMessage("");
        this.jtDetailPopUp = false;
        try {
            if (this.acno == null || this.acno.equals("")) {
                this.setErrMessage("Account no can not be blank.");
                return;
            }
            System.out.println("Form Account No Is->" + acno);
            ftsRemote.getNewAccountNumber(this.acno);
            this.jtDetailPopUp = true;
            this.setViewID("/pages/other/jointholderdetail.jsp");
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void jtDetails() {
        this.setErrMessage("");
        try {
            jointBean.jtDetails(ftsRemote.getNewAccountNumber(this.acno));
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void selectFieldRefresh() {
        this.setOldAcno("");
        this.setOldAcName("");
        this.setMicr("");
        this.setAcType("");
        this.setStRrn("");
        this.setStatusFlag("");
        this.setAcno("");
        this.setName("");
        this.setBalance("");
        this.setStatus("0");
        this.setReason("0");
        this.setAccountNo("");
        this.setReasonDisable(true);
        this.odBalFlag = false;
        this.setEcsAmount("");
        this.setMandatePanelAc("");
        this.setMandatePanelName("");
        this.setMandateViewFlag("false");
        this.mandateGridDetail = new ArrayList<MandateRecordTo>();
    }

    public void fieldRefresh() {
        this.setErrMessage("");
        this.setOldAcno("");
        this.setOldAcName("");
        this.setMicr("");
        this.setAcType("");
        this.setStRrn("");
        this.setStatusFlag("");
        this.setAcno("");
        this.setName("");
        this.setBalance("");
        this.setStatus("0");
        this.setReason("0");
        this.setAccountNo("");
        this.setReasonDisable(true);
        gridDetail = new ArrayList<NpciOacDto>();
        this.currentItem = null;
        this.odBalFlag = false;
        this.setEcsAmount("");
        this.setMandatePanelAc("");
        this.setMandatePanelName("");
        this.setMandateViewFlag("false");
        this.mandateGridDetail = new ArrayList<MandateRecordTo>();
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
        this.setStatusFlag("");
        this.setAcno("");
        this.setName("");
        this.setBalance("");
        this.setStatus("0");
        this.setReason("0");
        this.setEcsAmount("");
        this.setAccountNo("");
        this.setReasonDisable(true);
        gridDetail = new ArrayList<NpciOacDto>();
        this.currentItem = null;
        this.odBalFlag = false;
        this.setErrMessage("Please select Txn. Type.");
        this.setMandatePanelAc("");
        this.setMandatePanelName("");
        this.setMandateViewFlag("false");
        this.mandateGridDetail = new ArrayList<MandateRecordTo>();
        this.setBranch("0");
        this.setTxnType("0");
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

    public String getStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(String statusFlag) {
        this.statusFlag = statusFlag;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
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

    public boolean isOdBalFlag() {
        return odBalFlag;
    }

    public void setOdBalFlag(boolean odBalFlag) {
        this.odBalFlag = odBalFlag;
    }

    public String getConfirmationMsg() {
        return confirmationMsg;
    }

    public void setConfirmationMsg(String confirmationMsg) {
        this.confirmationMsg = confirmationMsg;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getEcsAmount() {
        return ecsAmount;
    }

    public void setEcsAmount(String ecsAmount) {
        this.ecsAmount = ecsAmount;
    }

    public String getMandateViewFlag() {
        return mandateViewFlag;
    }

    public void setMandateViewFlag(String mandateViewFlag) {
        this.mandateViewFlag = mandateViewFlag;
    }

    public String getMandatePanelAc() {
        return mandatePanelAc;
    }

    public void setMandatePanelAc(String mandatePanelAc) {
        this.mandatePanelAc = mandatePanelAc;
    }

    public String getMandatePanelName() {
        return mandatePanelName;
    }

    public void setMandatePanelName(String mandatePanelName) {
        this.mandatePanelName = mandatePanelName;
    }

    public List<MandateRecordTo> getMandateGridDetail() {
        return mandateGridDetail;
    }

    public void setMandateGridDetail(List<MandateRecordTo> mandateGridDetail) {
        this.mandateGridDetail = mandateGridDetail;
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

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public boolean isJtDetailPopUp() {
        return jtDetailPopUp;
    }

    public void setJtDetailPopUp(boolean jtDetailPopUp) {
        this.jtDetailPopUp = jtDetailPopUp;
    }

    public JointHolderBean getJointBean() {
        return jointBean;
    }

    public void setJointBean(JointHolderBean jointBean) {
        this.jointBean = jointBean;
    }
}
