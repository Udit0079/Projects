/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.dto.other.NpciOacDto;
import com.cbs.facade.cdci.IBSWSFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.OtherNpciMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.report.other.JointHolderBean;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.model.SelectItem;

public class EcsInwController extends BaseBean {

    private String errMessage;
    private String branch;
    private String type;
    private String fileUpDt;
    private String seqNo;
    private String stxtDestAcno;
    private String stxtAcType;
    private String stxtBenName;
    private String stxtAmount;
    private String stxtMicr;
    private String stxtUserNarration;
    private String stxtUniqueRefNo;
    private String stxtStatus;
    private String stxtReason;
    private String acno, accountNo, acNoLen;
    private String balance;
    private String name;
    private String outputVisible = "none";
    private String typeVisible = "none";
    private String viewID = "/pages/master/sm/test.jsp";
    private String verificationstatus;
    private Boolean verificationstatusdisable;
    private boolean jtDetailPopUp;
    private String verificationstatusdisplay;
    private List<SelectItem> verificationstatusList;
    private NpciOacDto currentItem;
    private List<SelectItem> branchList;
    private List<SelectItem> typeList;
    private List<SelectItem> seqNoList;
    private List<NpciOacDto> gridDetail;
    private int verifiACNameMismatchModule;
    private OtherNpciMgmtFacadeRemote otherNpciRemote;
    private CommonReportMethodsRemote commonReport;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private IBSWSFacadeRemote ibswsRemote;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("#.##");
    private JointHolderBean jointBean;
    private Map ActypeConversionMap;
    private int achCrUnsuccessVerificationCode;

    public EcsInwController() {
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
            this.setErrMessage("Please select Branch.");
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void initData() {
        branchList = new ArrayList<SelectItem>();
        branchList.add(new SelectItem("0", "--Select--"));
        try {
            List list = commonReport.getAlphacodeAllAndBranch(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                String brncode = ele.get(1).toString().trim().length() < 2 ? "0" + ele.get(1).toString().trim()
                        : ele.get(1).toString().trim();
                branchList.add(new SelectItem(brncode, ele.get(0).toString().trim()));
            }

            int varifiACNameMismatchModule = commonReport.getCodeByReportName("ECSACH-CR-VERF-NAMEMISMCH");
            if (varifiACNameMismatchModule == 1) {
                verificationstatusdisplay = "";
                verificationstatusList = new ArrayList<SelectItem>();
                verificationstatusList.add(new SelectItem("0", "--Select--"));
                verificationstatusList.add(new SelectItem("P", "PASS"));
                verificationstatusList.add(new SelectItem("R", "RETURN"));
            } else {
                verificationstatusdisplay = "none";
            }
            verificationstatusdisable = true;
            verifiACNameMismatchModule = commonReport.getCodeByReportName("ECSACH-CR-VERF-NAMEMISMCH");
            ActypeConversionMap = commonReport.getConvertedAccTypeByMapping();
            //New Addition
            achCrUnsuccessVerificationCode = commonReport.getCodeByReportName("ACH-CR-UNSUCCESS-VERIFY");
            typeList = new ArrayList<>();
            list = commonReport.getRefRecList("456");
            if (list.isEmpty()) {
                this.setErrMessage("Please define 456 code values.");
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                Vector elem = (Vector) list.get(i);
                typeList.add(new SelectItem(elem.get(0).toString(), elem.get(1).toString()));
            }
            if (achCrUnsuccessVerificationCode == 1) {
                typeVisible = "";
                outputVisible = "none";
            } else {
                typeVisible = "none";
                outputVisible = "";
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void fileUpDtProcess() {
        this.setErrMessage("");
        try {
            if (validateBranch() && validateType() && validateFileUpDate()) {
                seqNoList = new ArrayList<>();
                seqNoList.add(new SelectItem("0", "--Select--"));
//                List list = otherNpciRemote.findAllFileSeqNoForCreditInward(ymd.format(dmy.parse(fileUpDt)), "ECS");
                List list = otherNpciRemote.findAllFileSeqNoForCreditInward(ymd.format(dmy.parse(fileUpDt)), this.type);
                for (int i = 0; i < list.size(); i++) {
                    Vector ele = (Vector) list.get(i);
                    seqNoList.add(new SelectItem(this.type.equalsIgnoreCase("ACH")
                            ? ele.get(0).toString().split("-")[4] : ele.get(0).toString()));
                }
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void retrieveProcess() {
        this.setErrMessage("");
        try {
            if (validateBranch() && validateType() && validateFileUpDate() && validateSeqNo()) {
                fieldRefresh();
//                gridDetail = otherNpciRemote.getDataForEcsCrInComingDateAndSeqNo(ymd.format(dmy.parse(fileUpDt)),
//                        this.seqNo, "ECS", getOrgnBrCode(), this.branch);
                gridDetail = otherNpciRemote.getDataForEcsCrInComingDateAndSeqNo(ymd.format(dmy.parse(fileUpDt)),
                        this.seqNo, this.type, getOrgnBrCode(), this.branch);
                if (gridDetail.isEmpty()) {
                    this.setErrMessage("There is no data to verify.");
                    return;
                }
                this.setErrMessage("Now you can verify an entry. Please select a row from table.");
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
//            if (this.acno == null || this.acno.equals("")) {
//                this.setErrMessage("A/c No can not be blank.");
//                return;
//            }
            if (!(this.accountNo == null || this.accountNo.equals(""))) {
                if (!this.accountNo.equalsIgnoreCase("") && ((this.accountNo.length() != 12) && (this.accountNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                    //if (this.acno.trim().length() != 12) {
                    this.setErrMessage("Please fill proper A/c No.");
                    return;
                }
                acno = ftsRemote.getNewAccountNumber(accountNo);

                String alphaCode = commonReport.getAlphacodeByBrncode(getOrgnBrCode());
                if (!alphaCode.equalsIgnoreCase("HO") && !ftsRemote.getCurrentBrnCode(this.acno).equalsIgnoreCase(getOrgnBrCode())) {
                    this.setErrMessage("Please fill self branch A/c Number.");
                    return;
                }
                String result = ftsRemote.ftsAcnoValidate(this.acno, 0, "");
                if (!result.equalsIgnoreCase("true")) {
                    this.setErrMessage(result);
                    return;
                }
                this.setName(ftsRemote.getCustName(this.acno));
                this.setBalance(ibswsRemote.getBalanceByAccountNumber(this.acno,
                        ymd.format(new Date())).toString());
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
            this.setStxtDestAcno(currentItem.getOldAcno());
            this.setStxtAcType(currentItem.getAcType());
            this.setStxtBenName(currentItem.getOldAcName());
            this.setStxtAmount(currentItem.getAmount());
            this.setStxtMicr(currentItem.getMicr());
            this.setStxtUserNarration(currentItem.getUserName());

            this.setStxtUniqueRefNo(currentItem.getTranRef());
            this.setStxtStatus(currentItem.getStatus());
            this.setStxtReason(currentItem.getReason());
            this.setAcno("");
            this.setAccountNo("");
            this.setBalance("");
            this.setName("");
            this.setErrMessage("");

            if (verifiACNameMismatchModule == 1 && (stxtReason.equalsIgnoreCase("account holder name invalid"))) {
                verificationstatusdisable = false;
            }

            int autoFillFlag = commonReport.getCodeByReportName("ACNO-AUTOFILL-ECSACH-VRF");
            if (autoFillFlag == 1) {
                String acTypeFinal = stxtAcType;
                if ((!ActypeConversionMap.isEmpty()) && (ActypeConversionMap.containsKey(stxtAcType))) {
                    acTypeFinal = ActypeConversionMap.get(stxtAcType).toString();
                }

                if ((stxtDestAcno.length() > 0 && stxtDestAcno.length() <= 6)) {
                    acno = commonReport.getcbsFormatAcno(stxtDestAcno, stxtMicr, acTypeFinal);
                }

                if (this.stxtDestAcno.length() == 12 || this.stxtDestAcno.length() == 15) {
                    acno = ftsRemote.getNewAccountNumber(this.stxtDestAcno);
                }

                accountNo = acno;
                String alphacode = commonReport.getAlphacodeByBrncode(getOrgnBrCode());
                if (!alphacode.equalsIgnoreCase("HO")) {
                    if (!ftsRemote.getCurrentBrnCode(this.acno).equalsIgnoreCase(getOrgnBrCode())) {
                        this.setErrMessage("This entry not related to your branch .");
                        return;
                    }
                }
                String result = ftsRemote.ftsAcnoValidate(this.acno, 0, "");
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

    public void processAction() {
        this.setErrMessage("");
        try {
            if (currentItem == null) {
                this.setErrMessage("Please select a row from table to verify.");
                return;
            }
            String processAcNo = currentItem.getOldAcno();
            String alphaCode = commonReport.getAlphacodeByBrncode(getOrgnBrCode());
            if (!(this.accountNo == null || this.accountNo.equals(""))) {
                if (((this.accountNo.length() != 12) && (this.accountNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                    //if (this.acno.trim().length() != 12) {
                    this.setErrMessage("Please fill proper A/c No.");
                    return;
                }


                if (!alphaCode.equalsIgnoreCase("HO") && !ftsRemote.getCurrentBrnCode(this.acno).equalsIgnoreCase(getOrgnBrCode())) {
                    this.setErrMessage("Please fill self branch A/c Number.");
                    return;
                }
                String result = ftsRemote.ftsAcnoValidate(this.acno, 0, "");
//                if (!result.equalsIgnoreCase("true")) {
//                    this.setErrMessage(result);
//                    return;
//                }
                
                if (!(result.equalsIgnoreCase("true")
                        || result.equalsIgnoreCase("Account is Marked as Inoperative")
                        || result.equalsIgnoreCase("Sorry,Invalid Account Status")
                        || result.equalsIgnoreCase("Account is Closed"))) {
                    this.setErrMessage(result);
                    return;
                }
                processAcNo = this.acno;
            }
            if (!alphaCode.equalsIgnoreCase("HO") && processAcNo.trim().length() == 12
                    && !ftsRemote.getCurrentBrnCode(processAcNo).equalsIgnoreCase(getOrgnBrCode())) {
                this.setErrMessage("You can not process this entry. It will be processed at HO.");
                return;
            }

            if (verifiACNameMismatchModule == 1 && (stxtReason.equalsIgnoreCase("account holder name invalid"))) {
                if (this.verificationstatus == null || verificationstatus.equalsIgnoreCase("0")) {
                    this.setErrMessage("Please Select Status");
                    return;
                }
            }

//            String result = otherNpciRemote.processCecsInwCredit(currentItem, getUserName(),
//                    getTodayDate(), getOrgnBrCode(), this.type, ymd.format(dmy.parse(this.fileUpDt)),
//                    this.seqNo, processAcNo, this.stxtBenName, this.branch, this.verificationstatus);

            String result = otherNpciRemote.processCecsInwCredit(currentItem, getUserName(),
                    getTodayDate(), getOrgnBrCode(), this.type, ymd.format(dmy.parse(this.fileUpDt)),
                    this.seqNo, processAcNo, this.stxtBenName, this.branch, this.verificationstatus, "");
            if (!result.equalsIgnoreCase("true")) {
                this.setErrMessage(result);
                return;
            }
            // last date Updation in accountMaster 
            String resultLastUpdation = ftsRemote.lastTxnDateUpdation(processAcNo);

            if (validateBranch() && validateType() && validateFileUpDate() && validateSeqNo()) {
                fieldRefresh();
//                gridDetail = otherNpciRemote.getDataForEcsCrInComingDateAndSeqNo(ymd.format(dmy.parse(fileUpDt)),
//                        this.seqNo, "ECS", getOrgnBrCode(), this.branch);

                gridDetail = otherNpciRemote.getDataForEcsCrInComingDateAndSeqNo(ymd.format(dmy.parse(fileUpDt)),
                        this.seqNo, this.type, getOrgnBrCode(), this.branch);
                this.setErrMessage("Entry has been verified successfully. Now you can select if any other !");
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public boolean validateBranch() {
        this.setErrMessage("");
        try {
            if (this.branch == null || this.branch.equals("0") || this.branch.equals("")) {
                this.setErrMessage("Please fill Branch.");
                return false;
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean validateType() {
        this.setErrMessage("");
        try {
            if (this.type == null || this.type.equals("")) {
                this.setErrMessage("There should be type selected.");
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

    public void validateJointAccount() {
        this.setErrMessage("");
        this.jtDetailPopUp = false;
        try {
            if (this.acno == null || this.acno.equals("")) {
                this.setErrMessage("Account can not be blank.");
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

    public void fieldRefresh() {
        this.setErrMessage("");
        this.setStxtDestAcno("");
        this.setStxtAcType("");
        this.setStxtBenName("");
        this.setStxtAmount("");
        this.setStxtMicr("");
        this.setStxtUserNarration("");
        this.setStxtUniqueRefNo("");
        this.setStxtStatus("");
        this.setStxtReason("");
        this.setAcno("");
        this.setBalance("");
        this.setName("");
        this.setAccountNo("");
        this.currentItem = null;
        verificationstatusdisable = true;
        this.verificationstatus = "0";
    }

    public void refreshForm() {
        this.setErrMessage("");
        this.setFileUpDt("");
        this.setBranch("0");
        seqNoList = new ArrayList<SelectItem>();
        seqNoList.add(new SelectItem("0", "--Select--"));
        this.setStxtDestAcno("");
        this.setStxtAcType("");
        this.setStxtBenName("");
        this.setStxtAmount("");
        this.setStxtMicr("");
        this.setStxtUserNarration("");
        this.setStxtUniqueRefNo("");
        this.setStxtStatus("");
        this.setStxtReason("");
        this.setAcno("");
        this.setBalance("");
        this.setName("");
        this.setAccountNo("");
        this.currentItem = null;
        gridDetail = new ArrayList<NpciOacDto>();
        this.setErrMessage("Please select Branch.");
        verificationstatusdisable = true;
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

    public String getStxtDestAcno() {
        return stxtDestAcno;
    }

    public void setStxtDestAcno(String stxtDestAcno) {
        this.stxtDestAcno = stxtDestAcno;
    }

    public String getStxtAcType() {
        return stxtAcType;
    }

    public void setStxtAcType(String stxtAcType) {
        this.stxtAcType = stxtAcType;
    }

    public String getStxtBenName() {
        return stxtBenName;
    }

    public void setStxtBenName(String stxtBenName) {
        this.stxtBenName = stxtBenName;
    }

    public String getStxtAmount() {
        return stxtAmount;
    }

    public void setStxtAmount(String stxtAmount) {
        this.stxtAmount = stxtAmount;
    }

    public String getStxtMicr() {
        return stxtMicr;
    }

    public void setStxtMicr(String stxtMicr) {
        this.stxtMicr = stxtMicr;
    }

    public String getStxtUserNarration() {
        return stxtUserNarration;
    }

    public void setStxtUserNarration(String stxtUserNarration) {
        this.stxtUserNarration = stxtUserNarration;
    }

    public String getStxtUniqueRefNo() {
        return stxtUniqueRefNo;
    }

    public void setStxtUniqueRefNo(String stxtUniqueRefNo) {
        this.stxtUniqueRefNo = stxtUniqueRefNo;
    }

    public String getStxtStatus() {
        return stxtStatus;
    }

    public void setStxtStatus(String stxtStatus) {
        this.stxtStatus = stxtStatus;
    }

    public String getStxtReason() {
        return stxtReason;
    }

    public void setStxtReason(String stxtReason) {
        this.stxtReason = stxtReason;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NpciOacDto getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(NpciOacDto currentItem) {
        this.currentItem = currentItem;
    }

    public List<SelectItem> getSeqNoList() {
        return seqNoList;
    }

    public void setSeqNoList(List<SelectItem> seqNoList) {
        this.seqNoList = seqNoList;
    }

    public List<NpciOacDto> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<NpciOacDto> gridDetail) {
        this.gridDetail = gridDetail;
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

    public String getVerificationstatus() {
        return verificationstatus;
    }

    public void setVerificationstatus(String verificationstatus) {
        this.verificationstatus = verificationstatus;
    }

    public List<SelectItem> getVerificationstatusList() {
        return verificationstatusList;
    }

    public void setVerificationstatusList(List<SelectItem> verificationstatusList) {
        this.verificationstatusList = verificationstatusList;
    }

    public Boolean getVerificationstatusdisable() {
        return verificationstatusdisable;
    }

    public void setVerificationstatusdisable(Boolean verificationstatusdisable) {
        this.verificationstatusdisable = verificationstatusdisable;
    }

    public String getVerificationstatusdisplay() {
        return verificationstatusdisplay;
    }

    public void setVerificationstatusdisplay(String verificationstatusdisplay) {
        this.verificationstatusdisplay = verificationstatusdisplay;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SelectItem> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<SelectItem> typeList) {
        this.typeList = typeList;
    }

    public String getOutputVisible() {
        return outputVisible;
    }

    public void setOutputVisible(String outputVisible) {
        this.outputVisible = outputVisible;
    }

    public String getTypeVisible() {
        return typeVisible;
    }

    public void setTypeVisible(String typeVisible) {
        this.typeVisible = typeVisible;
    }

    public int getAchCrUnsuccessVerificationCode() {
        return achCrUnsuccessVerificationCode;
    }

    public void setAchCrUnsuccessVerificationCode(int achCrUnsuccessVerificationCode) {
        this.achCrUnsuccessVerificationCode = achCrUnsuccessVerificationCode;
    }
}
