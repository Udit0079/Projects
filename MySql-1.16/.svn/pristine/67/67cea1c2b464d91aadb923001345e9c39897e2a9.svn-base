/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.npci;

import com.cbs.dto.other.AutoMandateTo;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.misc.CustomerDetailsReportFacadeRemote;
import com.cbs.facade.other.NpciMandateFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.txn.TxnAuthorizationManagementFacadeRemote;
import com.cbs.utils.Base64;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.model.SelectItem;

public class MmsVerifierBean extends BaseBean {

    private String errMessage;
    private String mmsMode;
    private String mmsType;
    private String fileUpDt;
    private String seqNo;
    private String umrn;
    private String acno;
    private String newAcno;
    private String acNoLen;
    private String name;
    //add by rahul
    private String opMode;
    //end
    private String status;
    private String reason;
    private String imageName;
    private String sigData;
    private String category;
    //Joint Instance Variable
    private String jtMsg;
    private String jtName1;
    private String dob1;
    private String panNo1;
    private String fatherName1;
    private String occupation1;
    private String address1;
    private String jtName2;
    private String dob2;
    private String panNo2;
    private String fatherName2;
    private String occupation2;
    private String address2;
    private String jtName3;
    private String dob3;
    private String panNo3;
    private String fatherName3;
    private String occupation3;
    private String address3;
    private String jtName4;
    private String dob4;
    private String panNo4;
    private String fatherName4;
    private String occupation4;
    private String address4;
    private String nomineeName;
    private String nomineeAddress;
    private String nomineeRelation;
    //End Here
    private boolean jtDetailPopUp = false;
    private boolean reasonDisable = true;
    private boolean mandateViewFlag = false;
    private AutoMandateTo currentItem;
    private List<SelectItem> mmsModeList;
    private List<SelectItem> mmsTypeList;
    private List<SelectItem> seqNoList;
    private List<SelectItem> statusList;
    private List<SelectItem> reasonList;
    private List<AutoMandateTo> gridDetail;
    private List<AutoMandateTo> preMandateList;
    private NpciMandateFacadeRemote npciMandateRemote;
    private CommonReportMethodsRemote commonReport;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private TxnAuthorizationManagementFacadeRemote txnAuthRemote;
    private CustomerDetailsReportFacadeRemote remoteObject;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    Map map;

    public MmsVerifierBean() {
        try {
            npciMandateRemote = (NpciMandateFacadeRemote) ServiceLocator.getInstance().lookup("NpciMandateFacade");
            commonReport = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            txnAuthRemote = (TxnAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup("TxnAuthorizationManagementFacade");
            remoteObject = (CustomerDetailsReportFacadeRemote) ServiceLocator.getInstance().lookup("CustomerDetailsReportFacade");
            this.setAcNoLen(getAcNoLength());
            initData();
            refreshForm();
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void initData() {
        mmsModeList = new ArrayList<>();
        //mmsTypeList = new ArrayList<SelectItem>();
        seqNoList = new ArrayList<>();
        statusList = new ArrayList<>();
        reasonList = new ArrayList<>();
        try {

            mmsModeList.add(new SelectItem("0", "--Select--"));
            mmsModeList.add(new SelectItem("N", "NEW"));
            mmsModeList.add(new SelectItem("L", "LEGACY"));
            mmsModeList.add(new SelectItem("E", "E-SIGN"));

            statusList.add(new SelectItem("0", "--Select--"));
            statusList.add(new SelectItem("A", "ACCEPT"));
            statusList.add(new SelectItem("R", "REJECT"));

            setMap(); //For MMS Category
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void modeTypeOption() {
        mmsTypeList = new ArrayList<>();
        mmsTypeList.add(new SelectItem("0", "--Select--"));
        if (mmsMode.equalsIgnoreCase("N")) {
            mmsTypeList.add(new SelectItem("CREATE"));
            mmsTypeList.add(new SelectItem("AMEND"));
        } else if (mmsMode.equalsIgnoreCase("L")) {
            mmsTypeList.add(new SelectItem("CREATE"));
            mmsTypeList.add(new SelectItem("AMEND"));
        } else {
            mmsTypeList.add(new SelectItem("CREATE"));
            mmsTypeList.add(new SelectItem("AMEND"));
        }
    }

    public void setMap() {
        try {
            map = new HashMap();
            List list = commonReport.getRefRecList("326");
            if (list.isEmpty()) {
                this.setErrMessage("Please fill MMS category.");
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                map.put(ele.get(0).toString().trim(), ele.get(1).toString().trim());
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void getAllReason() {
        reasonList = new ArrayList<>();
        reasonList.add(new SelectItem("0", "--Select--"));
        try {
            String refRecNo = "";
            if (this.mmsType.equalsIgnoreCase("CREATE")) {
                refRecNo = "320";
            } else if (this.mmsType.equalsIgnoreCase("AMEND")) {
                refRecNo = "321";
            }
            List list = commonReport.getRefRecList(refRecNo);
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                reasonList.add(new SelectItem(ele.get(0).toString().trim(), ele.get(1).toString().trim()));
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void fileUpDtProcess() {
        try {
            if (this.mmsType == null || this.mmsType.equals("0") || this.mmsType.equals("")) {
                this.setErrMessage("Please select mandate type.");
                return;
            }
            if (this.fileUpDt == null || this.fileUpDt.equals("")) {
                this.setErrMessage("Please fill file upload date.");
                return;
            }
            seqNoList = new ArrayList<>();
            List list = npciMandateRemote.getAllUploadedFileNo(ymd.format(dmy.parse(this.fileUpDt)), this.mmsType, this.mmsMode);
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

    public void retrieveAllMandate() {
        try {
            if (this.mmsType == null || this.mmsType.equals("0") || this.mmsType.equals("")) {
                this.setErrMessage("Please select mandate type.");
                return;
            }
            if (this.fileUpDt == null || this.fileUpDt.equals("")) {
                this.setErrMessage("Please fill file upload date.");
                return;
            }
            if (this.seqNo == null || this.seqNo.equals("")) {
                this.setErrMessage("Please select file no.");
                return;
            }
            gridDetail = new ArrayList<>();
            List list = npciMandateRemote.getMandateDetails(ymd.format(dmy.parse(this.fileUpDt)),
                    Integer.parseInt(this.seqNo), this.mmsType, getOrgnBrCode(), this.mmsMode);
            if (list.isEmpty()) {
                this.setErrMessage("There is no data.");
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                AutoMandateTo obj = new AutoMandateTo();

                obj.setMsgId(ele.get(0).toString().trim());
                obj.setCreDtTm(ele.get(1).toString().trim());
                obj.setInitgPtyIdOrgIdOthrId(ele.get(2).toString().trim());
                obj.setInstgAgtFinInstnIdClrSysMmbIdMmbId(ele.get(3).toString().trim());
                obj.setInstgAgtFinInstnIdNm(ele.get(4).toString().trim());
                obj.setInstdAgtFinInstnIdClrSysMmbIdMmbId(ele.get(5).toString().trim());
                obj.setInstdAgtFinInstnIdNm(ele.get(6).toString().trim());
                obj.setMndtId(ele.get(7).toString().trim());
                obj.setMndtReqId(ele.get(8).toString().trim());

                obj.setTpSvcLvlPrtry(ele.get(9).toString().trim());
                obj.setTpLclInstrmPrtry(ele.get(10).toString().trim());
                obj.setOcrncsSeqTp(ele.get(11).toString().trim());
                obj.setOcrncsFrqcy(ele.get(12).toString().trim());
                obj.setOcrncsFrstColltnDt(ele.get(13).toString().trim());
                obj.setOcrncsFnlColltnDt(ele.get(14).toString().trim());
                obj.setColltnAmt(new BigDecimal(ele.get(15).toString().trim()));
                obj.setMaxAmt(new BigDecimal(ele.get(16).toString().trim()));

                obj.setCdtrNm(ele.get(17).toString().trim());
                obj.setCdtrAcctIdOthrId(ele.get(18).toString().trim());
                obj.setCdtrAgtFinInstnIdClrSysMmbIdMmbId(ele.get(19).toString().trim());
                obj.setCdtrAgtFinInstnIdNm(ele.get(20).toString().trim());
                obj.setDbtrNm(ele.get(21).toString().trim());
                obj.setDbtrIdPrvtIdOthrId(ele.get(22).toString().trim());
                obj.setDbtrIdPrvtIdOthrSchmeNmPrtry(ele.get(23).toString().trim());
                obj.setDbtrCtctDtlsPhneNb(ele.get(24).toString().trim());
                obj.setDbtrCtctDtlsMobNb(ele.get(25).toString().trim());
                obj.setDbtrCtctDtlsEmailAdr(ele.get(26).toString().trim());
                obj.setDbtrCtctDtlsOthr(ele.get(27).toString().trim());

                obj.setDbtrAcctIdOthrId(ele.get(28).toString().trim());
                obj.setDbtrAcctTpPrtry(ele.get(29).toString().trim());
                obj.setDbtrAgtFinInstnIdClrSysMmbIdMmbId(ele.get(30).toString().trim());
                obj.setDbtrAgtFinInstnIdNm(ele.get(31).toString().trim());
                obj.setMandateType(ele.get(32).toString().trim());
                String mandateStatusValue = ele.get(33).toString().trim();
                if (mandateStatusValue.equalsIgnoreCase("V")) {
                    obj.setMandateStatus("Verified");
                } else if (mandateStatusValue.equalsIgnoreCase("F")) {
                    obj.setMandateStatus("File Generated");
                } else {
                    obj.setMandateStatus("");
                }
                String acceptValue = ele.get(34).toString().trim();
                if (acceptValue.equalsIgnoreCase("A")) {
                    obj.setAccept("Accept");
                } else if (acceptValue.equalsIgnoreCase("R")) {
                    obj.setAccept("Reject");
                } else {
                    obj.setAccept("");
                }
                obj.setRejectCode(ele.get(35).toString().trim());
                obj.setImageName(ele.get(36).toString().trim());
                obj.setVerifyBy(ele.get(37).toString().trim());

                gridDetail.add(obj);
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void setTableDataToForm() {
        try {
            this.setErrMessage("");
            this.setUmrn("");
            this.setAcno("");
            this.setNewAcno("");
            this.setName("");
            this.setCategory("");
            if (currentItem == null) {
                this.setErrMessage("Please select a row from table.");
                return;
            }
            if (mmsMode.equalsIgnoreCase("L") || mmsMode.equalsIgnoreCase("E")) {
                this.sigData = null;
            }
            this.setUmrn(currentItem.getMndtId());
            this.setCategory(map.get(currentItem.getTpSvcLvlPrtry().trim()).toString());
            this.setAcno(currentItem.getDbtrAcctIdOthrId());
            //Image Setting
            this.imageName = currentItem.getImageName() + "_detailfront.jpg";
            //Setting signature
            if (mmsMode.equalsIgnoreCase("N")) {
                System.out.println("New MMS Signature A/c-->" + this.acno);
                String cbsAccountNo = this.acno;
                try {
                    cbsAccountNo = ftsRemote.getNewAccountNumber(this.acno);
                } catch (Exception ex) {
                    throw new Exception("New Account Not Found For Acno-->" + this.acno);
                }
                getSignatureDetail(cbsAccountNo);
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void retrieveAcnoDetail() {
        try {
            this.setName("");
            this.setNewAcno("");
            List getacopMode;

            if (this.acno == null || this.acno.equals("")) {
                this.setErrMessage("A/c No can not be blank.");
                return;
            }
            if (!(this.acno.length() == 12 || (this.acno.length() == Integer.parseInt(getAcNoLen())))) {
                this.setErrMessage("Please fill proper Account Number.");
                return;
            }
            this.newAcno = ftsRemote.getNewAccountNumber(acno);
            String alphacode = commonReport.getAlphacodeByBrncode(getOrgnBrCode());
            if (!alphacode.equalsIgnoreCase("HO")) {
                if (!ftsRemote.getCurrentBrnCode(this.newAcno).equalsIgnoreCase(getOrgnBrCode())) {
                    this.setErrMessage("Please fill self branch A/c Number.");
                    return;
                }
            }
            String result = ftsRemote.ftsAcnoValidate(this.newAcno, 1, "");
            if (!result.equalsIgnoreCase("true")) {
                this.setErrMessage(result);
                return;
            }
            this.setName(ftsRemote.getCustName(this.newAcno));
            if (mmsMode.equalsIgnoreCase("L") || mmsMode.equalsIgnoreCase("E")) {
//                getSignatureDetail(this.acno);
                System.out.println("Legacy/E-Sign MMS Signature A/c-->" + this.newAcno);
                getSignatureDetail(this.newAcno);
            }
            //add by rahul
            getacopMode = commonReport.getAcopMode(this.newAcno);
            Vector opmode = (Vector) getacopMode.get(0);
            opMode = opmode.get(0).toString();
            this.setOpMode(commonReport.selectForOperationMode(this.opMode));
            //end

        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void makeReasonEnable() {
        try {
            this.setReasonDisable(true);
            if (this.status.equalsIgnoreCase("R")) {
                this.setReasonDisable(false);
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void getWhiteImage() {
        try {
            if (currentItem == null) {
                this.setErrMessage("Please select a row from table.");
                return;
            }
            this.imageName = currentItem.getImageName() + "_front.png";
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void getGrayImage() {
        try {
            if (currentItem == null) {
                this.setErrMessage("Please select a row from table.");
                return;
            }
            this.imageName = currentItem.getImageName() + "_detailfront.jpg";
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void createSignature(OutputStream out, Object data) throws IOException {
        if (null == data) {
            return;
        }
        byte[] sigBytes = null;
        if (sigData != null) {
//            byte[] sigBytes = Base64.decode(sigData);
            sigBytes = Base64.decode(sigData);
            out.write(sigBytes);
        } else {
            out.write(sigBytes);
        }
    }

    public void getSignatureDetail(String acNo) {
        try {
            String signature;
            sigData = "wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==";
            signature = txnAuthRemote.getSignatureDetails(acNo);
            if (!signature.equalsIgnoreCase("Signature not found")) {
                String imageCode = signature.trim();
                String directoryPath = CbsUtil.getSigFilePath(imageCode.substring(4), imageCode.substring(0, 4));
                String encryptAcno = CbsUtil.encryptText(acNo);
                System.out.println("encryptAcno" + encryptAcno);
                String filePath = directoryPath + encryptAcno + ".xml";
                sigData = CbsUtil.readImageFromXMLFile(new File(filePath));
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void processAction() {
        try {
            if (this.mmsType == null || this.mmsType.equals("0") || this.mmsType.equals("")) {
                this.setErrMessage("Please select mandate type.");
                return;
            }
            if (this.fileUpDt == null || this.fileUpDt.equals("")) {
                this.setErrMessage("Please fill file upload date.");
                return;
            }
            if (this.seqNo == null || this.seqNo.equals("")) {
                this.setErrMessage("Please select file no.");
                return;
            }
            if (currentItem == null) {
                this.setErrMessage("Please select a row from table.");
                return;
            }
            if (this.status == null || this.status.equals("0") || this.status.equals("")) {
                this.setErrMessage("Please select status.");
                return;
            }
            if (this.status.equalsIgnoreCase("R") && (this.reason == null || this.reason.equals("0") || this.reason.equals(""))) {
                this.setErrMessage("In case of reject, reason is mandatory.");
                return;
            }
            if (this.status.equalsIgnoreCase("A")) {
                this.reason = "";
            }
            if (mmsMode.equalsIgnoreCase("N")) {
                this.acno = "";
            } else if (mmsMode.equalsIgnoreCase("L") || mmsMode.equalsIgnoreCase("E")) {
                //In case of CCBL Legacy Verification is stopped from branches.
                int code = ftsRemote.getCodeForReportName("LEG-MANDATE-VERIFY-MODE");
                String alphaCode = commonReport.getAlphacodeByBrncode(getOrgnBrCode());
                if (!alphaCode.equalsIgnoreCase("HO") && code == 0) {
                    this.setErrMessage("Legacy verification can not be process from branches.");
                    return;
                }

                if (status.equalsIgnoreCase("A")) {
                    if (!(this.acno.length() == 12 || (this.acno.length() == Integer.parseInt(getAcNoLen())))) {
                        this.setErrMessage("Please fill proper Account Number.");
                        return;
                    }
                    if (newAcno == null || newAcno.equalsIgnoreCase("")) {
                        this.setErrMessage("There should be actual a/c no.");
                        return;
                    }
                    String result = ftsRemote.ftsAcnoValidate(this.newAcno, 1, "");
                    if (!result.equalsIgnoreCase("true")) {
                        this.setErrMessage(result);
                        return;
                    }
                }
            }
//            String msg = npciMandateRemote.verifyMms(currentItem, this.status, this.reason,
//                    ymd.format(dmy.parse(this.fileUpDt)), this.mmsType, Integer.parseInt(this.seqNo),
//                    getUserName(), getTodayDate(), this.mmsMode, this.acno);

            String msg = npciMandateRemote.verifyMms(currentItem, this.status, this.reason,
                    ymd.format(dmy.parse(this.fileUpDt)), this.mmsType, Integer.parseInt(this.seqNo),
                    getUserName(), getTodayDate(), this.mmsMode, this.newAcno);
            if (!msg.equalsIgnoreCase("true")) {
                this.setErrMessage(msg);
                return;
            }
            this.setUmrn("");
            this.setCategory("");
            this.setAcno("");
            this.setNewAcno("");
            this.setName("");
            retrieveAllMandate();
            this.setErrMessage("Entry has been verified successfully.");
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void getPrevMandateDetail() {
        this.setErrMessage("");
        try {
            preMandateList = new ArrayList<AutoMandateTo>();
            this.setMandateViewFlag(false);
            if (currentItem == null) {
                this.setErrMessage("Please select a row from table to view the mandate detail.");
                return;
            }
            if (this.umrn == null || this.umrn.equalsIgnoreCase("")) {
                this.setErrMessage("There should be UMRN for previous mandate detail.");
                return;
            }
            if (!this.mmsType.equalsIgnoreCase("AMEND")) {
                this.setErrMessage("You can see previous mandate detail in case of amendment only.");
                return;
            }
            preMandateList = npciMandateRemote.getPreviousMandateDetail(this.umrn);
            this.setMandateViewFlag(true);
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    //Account Joint Detail
    public void jtDetails() {
        resetjtHHolderDetails();
        if (currentItem == null) {
            this.setErrMessage("Please select a row from table to view the joint detail.");
            jtDetailPopUp = false;
            return;
        }
        String jtDetailToAccount = "";
        if (mmsMode.equalsIgnoreCase("N")) {
            if (!(currentItem.getDbtrAcctIdOthrId().trim().length() == 12
                    || currentItem.getDbtrAcctIdOthrId().trim().length() == 15)) {
                this.setErrMessage("A/c number is not proper to show the joint detail.");
                jtDetailPopUp = false;
                return;
            }
            jtDetailToAccount = currentItem.getDbtrAcctIdOthrId().trim();
        } else if (mmsMode.equalsIgnoreCase("L") || mmsMode.equalsIgnoreCase("E")) {
            if (newAcno == null || newAcno.equals("")) {
                this.setErrMessage("There is no proper a/c to show the joint detail.");
                jtDetailPopUp = false;
                return;
            }
            jtDetailToAccount = this.newAcno;
        }
        try {
            System.out.println("Joint Detail A/c Is-->" + jtDetailToAccount);
            List resultList = remoteObject.jtDetails(ftsRemote.getNewAccountNumber(jtDetailToAccount), getOrgnBrCode());
            if (resultList.size() <= 0) {
                jtDetailPopUp = false;
            } else {
                jtDetailPopUp = true;
                if (resultList.get(0).toString().equalsIgnoreCase("")) {
                    setJtMsg("Sorry Nominee Details Are Not Present");
                } else {
                    setNomineeName(resultList.get(0).toString());
                    setNomineeAddress(resultList.get(1).toString());
                    setNomineeRelation(resultList.get(2).toString());
                }
                if (resultList.size() == 3 || resultList.get(3).toString().equalsIgnoreCase("")) {
                    setJtMsg("Sorry Joint Holder Details Are Not Present");
                    return;
                }
                if (resultList.size() > 3) {
                    setJtName1(resultList.get(3).toString());
                    setDob1(resultList.get(4).toString());
                    setAddress1(resultList.get(5).toString());
                    setFatherName1(resultList.get(6).toString());
                    setPanNo1(resultList.get(7).toString());
                    setOccupation1(resultList.get(8).toString());
                }
                if (resultList.size() > 8) {
                    setJtName2(resultList.get(9).toString());
                    setDob2(resultList.get(10).toString());
                    setAddress2(resultList.get(11).toString());
                    setFatherName2(resultList.get(12).toString());
                    setPanNo2(resultList.get(13).toString());
                    setOccupation2(resultList.get(14).toString());
                }
                if (resultList.size() > 14) {
                    setJtName3(resultList.get(15).toString());
                    setDob3(resultList.get(16).toString());
                    setAddress3(resultList.get(17).toString());
                    setFatherName3(resultList.get(1).toString());
                    setPanNo3(resultList.get(19).toString());
                    setOccupation3(resultList.get(20).toString());
                }
                if (resultList.size() > 20) {
                    setJtName4(resultList.get(21).toString());
                    setDob4(resultList.get(22).toString());
                    setAddress4(resultList.get(23).toString());
                    setFatherName4(resultList.get(24).toString());
                    setPanNo4(resultList.get(25).toString());
                    setOccupation4(resultList.get(26).toString());
                }
            }
        } catch (Exception ex) {
            this.setErrMessage(ex.getMessage());
        }
    }

    public void resetjtHHolderDetails() {
        this.setJtMsg("");
        this.setJtName1("");
        this.setDob1("");
        this.setAddress1("");
        this.setFatherName1("");
        this.setPanNo1("");
        this.setOccupation1("");
        this.setJtName2("");
        this.setDob2("");
        this.setAddress2("");
        this.setFatherName2("");
        this.setPanNo2("");
        this.setOccupation2("");
        this.setJtName3("");
        this.setDob3("");
        this.setAddress3("");
        this.setFatherName3("");
        this.setPanNo3("");
        this.setOccupation3("");
        this.setJtName4("");
        this.setDob4("");
        this.setAddress4("");
        this.setFatherName4("");
        this.setPanNo4("");
        this.setOccupation4("");
        this.setNomineeName("");
        this.setNomineeAddress("");
        this.setNomineeRelation("");
        this.jtDetailPopUp = false;
    }

    public void refreshForm() {
        this.setErrMessage("");
        this.setMmsType("0");
        this.setFileUpDt(getTodayDate());
        seqNoList = new ArrayList<SelectItem>();
        this.setUmrn("");
        this.setCategory("");
        this.setAcno("");
        this.setName("");
        this.setStatus("0");
        this.setOpMode("");
        reasonList = new ArrayList<SelectItem>();
        this.gridDetail = new ArrayList<AutoMandateTo>();
        this.currentItem = null;
        this.setReasonDisable(true);
        this.setMandateViewFlag(false);
        this.newAcno = "";
        this.setMmsMode("0");
        this.sigData = null;
        resetjtHHolderDetails();
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isReasonDisable() {
        return reasonDisable;
    }

    public void setReasonDisable(boolean reasonDisable) {
        this.reasonDisable = reasonDisable;
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

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public List<SelectItem> getReasonList() {
        return reasonList;
    }

    public void setReasonList(List<SelectItem> reasonList) {
        this.reasonList = reasonList;
    }

    public AutoMandateTo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(AutoMandateTo currentItem) {
        this.currentItem = currentItem;
    }

    public List<AutoMandateTo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<AutoMandateTo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getUmrn() {
        return umrn;
    }

    public void setUmrn(String umrn) {
        this.umrn = umrn;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getSigData() {
        return sigData;
    }

    public void setSigData(String sigData) {
        this.sigData = sigData;
    }

    public List<AutoMandateTo> getPreMandateList() {
        return preMandateList;
    }

    public void setPreMandateList(List<AutoMandateTo> preMandateList) {
        this.preMandateList = preMandateList;
    }

    public boolean isMandateViewFlag() {
        return mandateViewFlag;
    }

    public void setMandateViewFlag(boolean mandateViewFlag) {
        this.mandateViewFlag = mandateViewFlag;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMmsMode() {
        return mmsMode;
    }

    public void setMmsMode(String mmsMode) {
        this.mmsMode = mmsMode;
    }

    public String getNewAcno() {
        return newAcno;
    }

    public void setNewAcno(String newAcno) {
        this.newAcno = newAcno;
    }

    public List<SelectItem> getMmsModeList() {
        return mmsModeList;
    }

    public void setMmsModeList(List<SelectItem> mmsModeList) {
        this.mmsModeList = mmsModeList;
    }

    //Joint Detail
    public String getJtName1() {
        return jtName1;
    }

    public void setJtName1(String jtName1) {
        this.jtName1 = jtName1;
    }

    public String getDob1() {
        return dob1;
    }

    public void setDob1(String dob1) {
        this.dob1 = dob1;
    }

    public String getPanNo1() {
        return panNo1;
    }

    public void setPanNo1(String panNo1) {
        this.panNo1 = panNo1;
    }

    public String getFatherName1() {
        return fatherName1;
    }

    public void setFatherName1(String fatherName1) {
        this.fatherName1 = fatherName1;
    }

    public String getOccupation1() {
        return occupation1;
    }

    public void setOccupation1(String occupation1) {
        this.occupation1 = occupation1;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getJtName2() {
        return jtName2;
    }

    public void setJtName2(String jtName2) {
        this.jtName2 = jtName2;
    }

    public String getDob2() {
        return dob2;
    }

    public void setDob2(String dob2) {
        this.dob2 = dob2;
    }

    public String getPanNo2() {
        return panNo2;
    }

    public void setPanNo2(String panNo2) {
        this.panNo2 = panNo2;
    }

    public String getFatherName2() {
        return fatherName2;
    }

    public void setFatherName2(String fatherName2) {
        this.fatherName2 = fatherName2;
    }

    public String getOccupation2() {
        return occupation2;
    }

    public void setOccupation2(String occupation2) {
        this.occupation2 = occupation2;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getJtName3() {
        return jtName3;
    }

    public void setJtName3(String jtName3) {
        this.jtName3 = jtName3;
    }

    public String getDob3() {
        return dob3;
    }

    public void setDob3(String dob3) {
        this.dob3 = dob3;
    }

    public String getPanNo3() {
        return panNo3;
    }

    public void setPanNo3(String panNo3) {
        this.panNo3 = panNo3;
    }

    public String getFatherName3() {
        return fatherName3;
    }

    public void setFatherName3(String fatherName3) {
        this.fatherName3 = fatherName3;
    }

    public String getOccupation3() {
        return occupation3;
    }

    public void setOccupation3(String occupation3) {
        this.occupation3 = occupation3;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getJtName4() {
        return jtName4;
    }

    public void setJtName4(String jtName4) {
        this.jtName4 = jtName4;
    }

    public String getDob4() {
        return dob4;
    }

    public void setDob4(String dob4) {
        this.dob4 = dob4;
    }

    public String getPanNo4() {
        return panNo4;
    }

    public void setPanNo4(String panNo4) {
        this.panNo4 = panNo4;
    }

    public String getFatherName4() {
        return fatherName4;
    }

    public void setFatherName4(String fatherName4) {
        this.fatherName4 = fatherName4;
    }

    public String getOccupation4() {
        return occupation4;
    }

    public void setOccupation4(String occupation4) {
        this.occupation4 = occupation4;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getNomineeName() {
        return nomineeName;
    }

    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
    }

    public String getNomineeAddress() {
        return nomineeAddress;
    }

    public void setNomineeAddress(String nomineeAddress) {
        this.nomineeAddress = nomineeAddress;
    }

    public String getNomineeRelation() {
        return nomineeRelation;
    }

    public void setNomineeRelation(String nomineeRelation) {
        this.nomineeRelation = nomineeRelation;
    }

    public boolean isJtDetailPopUp() {
        return jtDetailPopUp;
    }

    public void setJtDetailPopUp(boolean jtDetailPopUp) {
        this.jtDetailPopUp = jtDetailPopUp;
    }

    public String getJtMsg() {
        return jtMsg;
    }

    public void setJtMsg(String jtMsg) {
        this.jtMsg = jtMsg;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getOpMode() {
        return opMode;
    }

    public void setOpMode(String opMode) {
        this.opMode = opMode;
    }
}
