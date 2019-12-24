/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master;

import com.cbs.dto.AtmCardMappGrid;
import com.cbs.dto.AtmSecondryAccountDetail;
import com.cbs.dto.CustomerDetail;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.share.ShareTransferFacadeRemote;
import com.cbs.facade.master.GeneralMasterFacadeRemote;
import com.cbs.facade.misc.MiscMgmtFacadeS1Remote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ParseFileUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.model.SelectItem;

public class ATMCardMaster extends BaseBean {

    private String message;
    private String function;
    private String acNo;
    private String cardNo;
    private String minLmt;
    private String stFlg;
    private String btnValue = "Save";
    private Date issueDt = new Date();
    private boolean acFlag;
    private boolean cardFlag;
    private boolean dtFlag;
    private boolean lmtFlag;
    private boolean statusFlag;
    private boolean btnFlag;
    private String enter;
    private String oldCard;
    private List<SelectItem> functionList;
    private List<SelectItem> statusList;
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymdd = new SimpleDateFormat("yyyy-MM-dd");
    ShareTransferFacadeRemote remoteObject;
    private GeneralMasterFacadeRemote generalFacadeRemote;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private AtmCardMappGrid currentItem;
    private List<AtmCardMappGrid> gridDetail;
    //New Addition Here
    private int isSeqMode = 0;
    private String normalDisplay = "none";
    private String seqFileDisplay = "none";
    private String chnPanelDisplay = "none";
    private String firstDisplay = "none";
    private String secondaryPanelDisplay = "none";
    private String secondFileDataDisplay = "none";
    private String fourthFileDataDisplay = "none";
    private String fifthPanelDisplay = "none";
    private String sixthPanelDisplay = "none";
    private String acdisplay = "";
    private String fileMode;
    private String operation;
    private String acNoLen;
    private String accountNo;
    private String newAccountNumber;
    private String chnNo;
    private String customerId;
    private String gender;
    private String name;
    private String dob;
    private String mail;
    private String encodingName;
    private String embossingName;
    private String cardType;
    private String txnLimitCardType;
    private String cardRelationship;
    private String serviceCode;
    private String wdrlLimitAmount;
    private String wdrlLimitCount;
    private String purLimitAmount;
    private String purLimitCount;
    private String minLimit;
    private String secondaryWdrlLimitAmount;
    private String secondaryWdrlLimitCount;
    private String secondaryPurLimitAmount;
    private String secondaryPurLimitCount;
    private String txnLimitType;
    private String limitAmount;
    private String limitCount;
    private String kccEmvType;
    private String san;
    private String newSan;
    private String secondaryTxnLimitType;
    private String secondaryLimitAmount;
    private String secondaryLimitCount;
    private String presentCardStatus;
    private String cardStatus;
    private String prEncodingName;
    private String prEmbossingName;
    private String chgEncodingName;
    private String chgEmbossingName;
    private boolean secondaryChkBox;
    private boolean selectRender;
    private boolean deactiveRender;
    private boolean selectUpdateRender = false;
    private List<SelectItem> fileModeList;
    private List<SelectItem> operationList;
    private List<SelectItem> cardTypeList;
    private List<SelectItem> txnLimitCardTypeList;
    private List<SelectItem> cardRelationshipList;
    private List<SelectItem> serviceCodeList;
    private List<SelectItem> txnLimitTypeList;
    private List<SelectItem> kccEmvTypeList;
    private List<SelectItem> secondaryTxnLimitTypeList;
    private List<SelectItem> cardStatusList;
    private AtmSecondryAccountDetail secondaryCurrentItem;
    private List<AtmSecondryAccountDetail> secondaryTable;
    private List<AtmSecondryAccountDetail> secondaryViewTable;
    private List<AtmCardMappGrid> verifyTable;
    private List<AtmCardMappGrid> deactivateTable;
    private AtmCardMappGrid verifyCurrentItem;
    private AtmCardMappGrid deactiveCurrentItem;
    private CommonReportMethodsRemote commonReportRemote;
    private MiscMgmtFacadeS1Remote miscRemoteS1;
    //verifyPanel field
    private boolean verifyPanelViewFlag = false;
    private String acno1;
    private String name1;
    private String card1;
    private String date1;
    private String encoding1;
    private String minlmt1;
    private String widlmtamt1;
    private String widlmtcount1;
    private String purlmtcount1;
    private String purlmtamt1;
    private String cardStatus1;
    private String txnLimitType1;
    private String custid1;
    private String gender1;
    private String embossing1;

    public ATMCardMaster() {
        try {
            remoteObject = (ShareTransferFacadeRemote) ServiceLocator.getInstance().lookup("ShareTransferFacade");
            generalFacadeRemote = (GeneralMasterFacadeRemote) ServiceLocator.getInstance().lookup("GeneralMasterFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            commonReportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            miscRemoteS1 = (MiscMgmtFacadeS1Remote) ServiceLocator.getInstance().lookup("MiscMgmtFacadeS1");

            isSeqMode = ftsRemote.getCodeForReportName("IS-ATM-SEQ-FILE");
            functionList = new ArrayList<>();
            statusList = new ArrayList<>();

            fileModeList = new ArrayList<>();
            operationList = new ArrayList<>();
//            cardTypeList = new ArrayList<>();
//            txnLimitCardTypeList = new ArrayList<>();
//            cardRelationshipList = new ArrayList<>();
//            serviceCodeList = new ArrayList<>();
            txnLimitTypeList = new ArrayList<>();
            kccEmvTypeList = new ArrayList<>();
            secondaryTxnLimitTypeList = new ArrayList<>();
            cardStatusList = new ArrayList<>();

            if (isSeqMode != 1) { //Normal
                this.normalDisplay = "";
                this.seqFileDisplay = "none";
                functionList.add(new SelectItem("", "-Select-"));
                functionList.add(new SelectItem("1", "Add"));
                functionList.add(new SelectItem("2", "Modify"));
                String levelId = remoteObject.getLevelId(getUserName(), getOrgnBrCode());
                if (levelId.equals("1") || levelId.equals("2")) {
                    functionList.add(new SelectItem("3", "Verify"));
                }
                statusList.add(new SelectItem("", "-Select-"));
                statusList.add(new SelectItem("A", "Active"));
                statusList.add(new SelectItem("I", "Inactive"));
                this.setBtnFlag(false);
            } else { //ATM Sequence File
                this.normalDisplay = "none";
                this.seqFileDisplay = "";
                this.chnPanelDisplay = "none";
                this.secondFileDataDisplay = "none";
                this.fourthFileDataDisplay = "none";
                this.fifthPanelDisplay = "none";
                this.acdisplay = "";
                this.setAcNoLen(getAcNoLength());

                fileModeList.add(new SelectItem("0", "--Select--"));
//                cardTypeList.add(new SelectItem("0", "--Select--"));
//                txnLimitCardTypeList.add(new SelectItem("0", "--Select--"));
//                cardRelationshipList.add(new SelectItem("0", "--Select--"));
//                serviceCodeList.add(new SelectItem("0", "--Select--"));
                txnLimitTypeList.add(new SelectItem("0", "--Select--"));
//                kccEmvTypeList.add(new SelectItem("0", "--Select--"));
                secondaryTxnLimitTypeList.add(new SelectItem("0", "--Select--"));
                cardStatusList.add(new SelectItem("0", "--Select--"));

                List referenceList = commonReportRemote.getRefRecList("405");
                if (referenceList.isEmpty()) {
                    this.setMessage("Please define initial data for code 405");
                    return;
                }
                for (int i = 0; i < referenceList.size(); i++) {
                    Vector ele = (Vector) referenceList.get(i);
                    fileModeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
                referenceList = commonReportRemote.getRefRecList("406");
                if (referenceList.isEmpty()) {
                    this.setMessage("Please define initial data for code 406");
                    return;
                }
                for (int i = 0; i < referenceList.size(); i++) {
                    Vector ele = (Vector) referenceList.get(i);
                    cardStatusList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
                referenceList = commonReportRemote.getRefRecList("407");
                if (referenceList.isEmpty()) {
                    this.setMessage("Please define initial data for code 407");
                    return;
                }
                for (int i = 0; i < referenceList.size(); i++) {
                    Vector ele = (Vector) referenceList.get(i);
                    txnLimitTypeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                    secondaryTxnLimitTypeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
//                referenceList = commonReportRemote.getRefRecList("408");
//                if (referenceList.isEmpty()) {
//                    this.setMessage("Please define initial data for code 408");
//                    return;
//                }
//                for (int i = 0; i < referenceList.size(); i++) {
//                    Vector ele = (Vector) referenceList.get(i);
//                    cardTypeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
//                }
//                referenceList = commonReportRemote.getRefRecList("409");
//                if (referenceList.isEmpty()) {
//                    this.setMessage("Please define initial data for code 409");
//                    return;
//                }
//                for (int i = 0; i < referenceList.size(); i++) {
//                    Vector ele = (Vector) referenceList.get(i);
//                    txnLimitCardTypeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
//                }
//                referenceList = commonReportRemote.getRefRecList("410");
//                if (referenceList.isEmpty()) {
//                    this.setMessage("Please define initial data for code 410");
//                    return;
//                }
//                for (int i = 0; i < referenceList.size(); i++) {
//                    Vector ele = (Vector) referenceList.get(i);
//                    cardRelationshipList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
//                }
//                referenceList = commonReportRemote.getRefRecList("411");
//                if (referenceList.isEmpty()) {
//                    this.setMessage("Please define initial data for code 411");
//                    return;
//                }
//                for (int i = 0; i < referenceList.size(); i++) {
//                    Vector ele = (Vector) referenceList.get(i);
//                    serviceCodeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
//                }
                referenceList = commonReportRemote.getRefRecList("412");
                if (referenceList.isEmpty()) {
                    this.setMessage("Please define initial data for code 412");
                    return;
                }
                for (int i = 0; i < referenceList.size(); i++) {
                    Vector ele = (Vector) referenceList.get(i);
                    kccEmvTypeList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
                this.cardType = "";
                this.txnLimitCardType = "";
                this.cardRelationship = "";
                this.serviceCode = "";
                this.kccEmvType = "N";
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void chgFunction() {
        try {
            setMessage("");
            if (function.equals("")) {
            } else if (function.equals("1")) {
                gridDetail = new ArrayList<AtmCardMappGrid>();
                this.setMessage("");
                this.setAcNo("");
                this.setCardNo("");
                this.setIssueDt(date);
                this.setMinLmt("0.0");
                this.setStFlg("");
                this.setFunction("");
                this.setWdrlLimitAmount("");
                this.setWdrlLimitCount("");
                this.setPurLimitAmount("");
                this.setPurLimitCount("");
                this.setMinLimit("");
                this.btnValue = "Save";
                this.setBtnFlag(false);
            } else if (function.equals("2")) {
                gridDetail = new ArrayList<AtmCardMappGrid>();
                this.setMessage("");
                this.setAcNo("");
                this.setCardNo("");
                this.setIssueDt(date);
                this.setMinLmt("0.0");
                this.setStFlg("");
                this.setFunction("");
                this.setWdrlLimitAmount("");
                this.setWdrlLimitCount("");
                this.setPurLimitAmount("");
                this.setPurLimitCount("");
                this.setMinLimit("");
                this.setBtnFlag(true);
            } else if (function.equals("3")) {
                gridDetail = new ArrayList<AtmCardMappGrid>();
                this.setMessage("");
                this.setAcNo("");
                this.setCardNo("");
                this.setIssueDt(date);
                this.setMinLmt("");
                this.setStFlg("");
                this.setFunction("");
                this.setBtnFlag(true);
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void chenageOperation() {
        try {
            setMessage("");
            if (function.equals("")) {
                setMessage("Please select Function.");
                return;
            } else if (function.equals("1")) {
                gridDetail = new ArrayList<AtmCardMappGrid>();
                setBtnValue("Save");
            } else if (function.equals("2")) {
                if (acNo.equalsIgnoreCase("")) {
                    setMessage("Please Fill Account no.");
                    return;
                }
                gridLoad(this.getFunction(), this.getAcNo());
                setBtnValue("Update");
            } else if (function.equals("3")) {
                gridLoad(this.getFunction(), this.getAcNo());
                setBtnValue("Verify");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void gridLoad(String opt, String accNo) {
        this.setMessage("");
        gridDetail = new ArrayList<AtmCardMappGrid>();
        try {
            List resultList = generalFacadeRemote.gridDetailATMCard(opt, accNo);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    AtmCardMappGrid atm = new AtmCardMappGrid();
                    Vector ele = (Vector) resultList.get(i);

                    atm.setAcNo(ele.get(0).toString());
                    atm.setCardNo(ele.get(1).toString());
                    atm.setIssDt(ele.get(2).toString());
                    atm.setMinLmt(ele.get(3).toString());
                    atm.setStatus(ele.get(4).toString());
                    atm.setEnterBy(ele.get(5).toString());

                    gridDetail.add(atm);
                }
            } else {
                this.setMessage("There is no entry.");
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void select() {
        this.setMessage("");
        try {
            this.setAcNo(currentItem.getAcNo());
            this.setCardNo(currentItem.getCardNo());
            this.setIssueDt(sdf.parse(currentItem.getIssDt()));
            this.setMinLmt(currentItem.getMinLmt());
            this.setStFlg(currentItem.getStatus());
            enter = currentItem.getEnterBy();
            oldCard = currentItem.getCardNo();
            fieldDisEnable();
            this.setBtnFlag(false);
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void fieldDisEnable() {
        if (this.getFunction().equalsIgnoreCase("3")) {
            this.setAcFlag(true);
            this.setCardFlag(true);
            this.setDtFlag(true);
            this.setLmtFlag(true);
            this.setStatusFlag(true);
        } else {
            this.setAcFlag(false);
            this.setCardFlag(false);
            this.setDtFlag(false);
            this.setLmtFlag(false);
            this.setStatusFlag(false);
        }
    }

    public String validateField() {
        this.setMessage("");
        Pattern p = Pattern.compile("[0-9]*");
//      Pattern pm = Pattern.compile("[a-zA-z0-9,]+([ '-][a-zA-Z0-9,]+)*");
        Pattern pAmt = Pattern.compile("([0-9]+(\\.[0-9]+)?)+");

        try {

            if ((this.function == null) || (this.getFunction().equalsIgnoreCase(""))) {
                return "Please Select Function";
            }

            if (this.acNo.equalsIgnoreCase("")) {
                return "Please fill Account Number";
            }

            if (this.acNo.length() != 12) {
                return "Account Number Should be 12 Digit";
            }

            Matcher matcher = p.matcher(this.acNo);
            if (!matcher.matches()) {
                return "Account Number should be in numeric digits.";
            }

            if (this.minLmt == null || this.minLmt.toString().equalsIgnoreCase("")) {
                return "Please Fill Min Limiit";
            } else {
                matcher = pAmt.matcher(this.minLmt);
                if (!matcher.matches()) {
                    return "Min Limit should be in numeric digits.";
                }
            }

            if ((this.stFlg == null) || (this.stFlg.equalsIgnoreCase(""))) {
                return "Please Select Status.";
            }

            String msg = ftsRemote.ftsAcnoValidate(this.acNo, 1, "");
            if (!msg.equalsIgnoreCase("true")) {
                return msg;
            }

            if (function.equalsIgnoreCase("3")) {
                if (this.enter.equalsIgnoreCase(getUserName())) {
                    return "You can not Verify your own transaction";
                }
            }

        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "true";
    }

    public void saveMasterDetail() {
        this.setMessage("");
        try {
            String result = validateField();
            if (!result.equals("true")) {
                this.setMessage(result);
                return;
            }
            result = generalFacadeRemote.SaveUpdateVerifyATMCard(this.getBtnValue(), this.getAcNo(), this.getCardNo(), ymd.format(this.getIssueDt()), this.getMinLmt(),
                    this.getStFlg(), this.getUserName(), this.oldCard);
            if (!result.equalsIgnoreCase("true")) {
                this.setMessage(result);
                return;
            }
            if (function.equalsIgnoreCase("1")) {
                refreshForm();
                this.setMessage("Data has been saved successfully.");
            } else if (function.equalsIgnoreCase("2")) {
                refreshForm();
                this.setMessage("Data has been Updated successfully.");
            } else if (function.equalsIgnoreCase("3")) {
                refreshForm();
                this.setMessage("Data has been verified successfully.");
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void refreshForm() {
        this.setMessage("");
        this.setAcNo("");
        this.setCardNo("");
        this.setIssueDt(date);
        this.setMinLmt("");
        this.setStFlg("");
        this.setFunction("");
        this.btnValue = "Save";
        gridDetail = new ArrayList<>();
        fieldDisEnable();
        this.setBtnFlag(false);
    }

    public String exitBtnAction() {
        refreshForm();
        return "case1";
    }

    //New Method Addition
    public void fileModeProcess() {
        try {
            if (this.fileMode == null || this.fileMode.equals("0") || this.fileMode.equals("")) {
                this.setMessage("Please select file mode.");
                return;
            }
            operationList = new ArrayList<>();
            operationList.add(new SelectItem("0", "--Select--"));
            if (this.fileMode.equalsIgnoreCase("N")) {
                operationList.add(new SelectItem("A", "ADD"));
                operationList.add(new SelectItem("M", "MODIFY"));
                operationList.add(new SelectItem("D", "DELETE"));
                operationList.add(new SelectItem("Y", "VERIFY"));
            } else {
                operationList.add(new SelectItem("U", "UPDATE"));
                operationList.add(new SelectItem("Y", "VERIFY"));
            }
            if (this.fileMode.equalsIgnoreCase("D")) {
                operationList.add(new SelectItem("I", "DEACTIVATE"));
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void operationProcess() {
        this.setMessage("");
        try {
            if (this.fileMode == null || this.fileMode.equals("0") || this.fileMode.equals("")) {
                this.setMessage("Please select file mode.");
                return;
            }
            if (this.operation == null || this.operation.equals("0") || this.operation.equals("")) {
                this.setMessage("Please select operation.");
                return;
            }
            if (this.fileMode.equalsIgnoreCase("N") && (this.operation.equals("A")
                    || this.operation.equals("M") || this.operation.equals("D"))) {
                this.setAccountNo("");
                refreshFirstFileField();
            } else if (this.fileMode.equalsIgnoreCase("S") && this.operation.equals("U")) {
                this.setAccountNo("");
                this.setNewAccountNumber("");
                this.firstDisplay = "none";
                this.secondaryPanelDisplay = "none";
                this.fourthFileDataDisplay = "none";
                this.chnPanelDisplay = "";
                this.secondFileDataDisplay = "";
                this.fifthPanelDisplay = "none";
                this.sixthPanelDisplay = "none";
                this.acdisplay = "";
                this.setMessage("Please fill required details.");
            } else if (this.fileMode.equalsIgnoreCase("C") && this.operation.equals("U")) {
                this.setAccountNo("");
                this.setNewAccountNumber("");

                this.firstDisplay = "none";
                this.secondaryPanelDisplay = "none";
                this.secondFileDataDisplay = "none";
                this.chnPanelDisplay = "";
                this.fourthFileDataDisplay = "";
                this.fifthPanelDisplay = "none";
                this.sixthPanelDisplay = "none";
                this.acdisplay = "";
                this.setMessage("Please fill required details.");
            } else if (this.fileMode.equalsIgnoreCase("D") && this.operation.equals("U")) {
                this.setAccountNo("");
                this.setNewAccountNumber("");
                this.setChnNo("");
                this.setCustomerId("");
                this.setGender("");
                this.setName("");
                this.setDob("");
                this.setMail("");

                this.firstDisplay = "none";
                this.secondaryPanelDisplay = "none";
                this.secondFileDataDisplay = "none";
                this.fourthFileDataDisplay = "none";
                this.chnPanelDisplay = "";
                this.fifthPanelDisplay = "none";
                this.sixthPanelDisplay = "";
                this.acdisplay = "";
                this.selectUpdateRender = true;
                this.deactiveRender = false;
                this.setMessage("Please fill required details.");
            } else if (this.operation.equalsIgnoreCase("Y")) {
                this.firstDisplay = "none";
                this.secondFileDataDisplay = "none";
                this.secondaryPanelDisplay = "none";
                this.fourthFileDataDisplay = "none";
                this.chnPanelDisplay = "none";
                this.fifthPanelDisplay = "";
                this.sixthPanelDisplay = "none";
                this.acdisplay = "none";
                this.selectRender = true;

                this.setMessage("");
                verifyTable = new ArrayList<AtmCardMappGrid>();
                List resultList = generalFacadeRemote.gridDetailForVerifyAtmCard(getTodayDate(), getOrgnBrCode());
                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        AtmCardMappGrid atm = new AtmCardMappGrid();
                        Vector ele = (Vector) resultList.get(i);
                        atm.setAcNo(ele.get(0).toString());
                        atm.setCardNo(ele.get(1).toString());
                        atm.setMinLmt(ele.get(3).toString());
                        atm.setTxnLimitType(ele.get(10).toString());
                        atm.setWithdrawalLimitAmount(new BigDecimal(ele.get(11).toString()));
                        atm.setWithdrawalLimitCount(Integer.parseInt(ele.get(12).toString()));
                        atm.setPurchaseLimitAmount(new BigDecimal(ele.get(13).toString()));
                        atm.setPurchaseLimitCount(Integer.parseInt(ele.get(14).toString()));
                        atm.setRegistrationDt(ele.get(19).toString());
                        atm.setCardStatus(ele.get(9).toString());
                        atm.setEncodingName(ele.get(17).toString());
                        atm.setEmbossingName(ele.get(18).toString());

                        verifyTable.add(atm);

                    }
                    this.verifyPanelViewFlag = true;
                }
                this.setMessage("Please select the verify link for verify the entry.");
            }
            if (fileMode.equalsIgnoreCase("D") && operation.equalsIgnoreCase("I")) {
                this.verifyCurrentItem = null;
                this.deactiveCurrentItem = null;
                this.firstDisplay = "none";
                this.secondFileDataDisplay = "none";
                this.secondaryPanelDisplay = "none";
                this.fourthFileDataDisplay = "none";
                this.chnPanelDisplay = "none";
                this.fifthPanelDisplay = "none";
                this.sixthPanelDisplay = "none";
                this.acdisplay = "";
                this.deactiveRender = true;
                this.selectUpdateRender = false;
                this.setMessage("");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void accountProcess() {
        this.setMessage("");
        try {
            if (this.fileMode.equalsIgnoreCase("N")) {
                refreshFirstFileField();
                if (this.operation.equals("A") || this.operation.equals("M") || this.operation.equals("D")) {
                    List list = commonReportRemote.getAmountLimitList();
                    for (int i = 0; i < list.size(); i++) {
                        Vector v = (Vector) list.get(i);
                        if (v.get(0).toString().equalsIgnoreCase("MIN-LIMIT")) {
                            setMinLimit(v.get(1).toString().trim());
                        }
                        if (v.get(0).toString().equalsIgnoreCase("PURCHASE-LIMIT")) {
                            setPurLimitAmount(v.get(1).toString().trim());
                        }
                        if (v.get(0).toString().equalsIgnoreCase("PURCHASE-COUNT")) {
                            setPurLimitCount(v.get(1).toString().trim());
                        }
                        if (v.get(0).toString().equalsIgnoreCase("WITHDRAWAL-LIMIT")) {
                            setWdrlLimitAmount(v.get(1).toString().trim());
                        }
                        if (v.get(0).toString().equalsIgnoreCase("WITHDRAWAL-COUNT")) {
                            setWdrlLimitCount(v.get(1).toString().trim());
                        }
                    }
                }

                if (validateAccountField(this.accountNo) == false) {
                    return;
                }
                CustomerDetail customerObj = miscRemoteS1.getCustomerDetailByAccountNo(newAccountNumber);
                this.setCustomerId(customerObj.getCustomerId());
                this.setGender(customerObj.getGender());
                this.setName(customerObj.getCustomerName());
                this.setDob(customerObj.getDateOfBirth());
                this.setMail(customerObj.getMailAddress());

                if (this.operation.equals("M") || this.operation.equals("D")) {
                    List<AtmCardMappGrid> dataList = miscRemoteS1.getAtmAccountDetail(newAccountNumber, "");
                    if (dataList.isEmpty()) {
                        this.setMessage("There is no data");
                        return;
                    }

                    this.setEncodingName(dataList.get(0).getEncodingName());
                    this.setEmbossingName(dataList.get(0).getEmbossingName());
//                    this.setCardType(dataList.get(0).getCardType());
//                    this.setTxnLimitCardType(dataList.get(0).getTxnLimitCardType());
//                    this.setCardRelationship(dataList.get(0).getCardRelationship());
//                    this.setServiceCode(dataList.get(0).getServiceCode());
                    this.setWdrlLimitAmount(String.valueOf(dataList.get(0).getWithdrawalLimitAmount().intValueExact()));
                    this.setWdrlLimitCount(dataList.get(0).getWithdrawalLimitCount().toString());
                    this.setPurLimitAmount(String.valueOf(dataList.get(0).getPurchaseLimitAmount().intValueExact()));
                    this.setPurLimitCount(dataList.get(0).getPurchaseLimitCount().toString());
                    this.setTxnLimitType(dataList.get(0).getTxnLimitType());
                    this.setMinLimit(String.valueOf(dataList.get(0).getMinLmt()).split("\\.")[0]);

                    this.setKccEmvType(dataList.get(0).getKccEmvType());
                    selectRender = true;
                    secondaryTable = dataList.get(0).getSecondryAccounts();
                    if (secondaryTable != null && !secondaryTable.isEmpty()) {
                        this.setSecondaryChkBox(true);
                        this.secondaryPanelDisplay = "";
                    }
//                  secondaryTable = new ArrayList<>();
//                  secondaryTable = miscRemoteS1.getAtmSecondaryAccountDetail(this.accountNo,this.cardNo);
//                   if (secondaryTable.isEmpty()) {
//                        this.setMessage("There is no data");
//                        return;
//                    }

                }
            } else if (this.fileMode.equalsIgnoreCase("D")) {
                validateAccountField(this.accountNo);
            } else if (this.fileMode.equalsIgnoreCase("S")) {
                validateAccountField(this.accountNo);
            } else if (this.fileMode.equalsIgnoreCase("C")) {
                validateAccountField(this.accountNo);
            }
            if (this.fileMode.equalsIgnoreCase("D") && this.operation.equalsIgnoreCase("I")) {
                this.sixthPanelDisplay = "";
                if (this.accountNo.equalsIgnoreCase(null) || this.accountNo.equalsIgnoreCase("")) {
                    this.setMessage("Please fill the account no.");
                    return;
                }
                validateAccountField(this.accountNo);
                List getAccountList = generalFacadeRemote.getAccountInAtmCardMaster(this.accountNo);
                if (getAccountList.isEmpty()) {
                    this.setMessage("Detail is not available for this account. Please check your account no.");
                } else {
//                    String exitList = generalFacadeRemote.getdeactivatedAccount(this.accountNo);
//                    if (exitList.equalsIgnoreCase("This account already has been deactivated.")) {
//                        this.setMessage(exitList);
//                        return;
                    //                 } else
                    {
                        deactivateTable = new ArrayList<AtmCardMappGrid>();
                        List dataList = generalFacadeRemote.gridDetailForDeactivateCard(getOrgnBrCode(), this.accountNo);
                        if (!dataList.isEmpty()) {
                            for (int i = 0; i < dataList.size(); i++) {
                                AtmCardMappGrid atm = new AtmCardMappGrid();
                                Vector ele = (Vector) dataList.get(i);
                                atm.setAcNo(ele.get(0).toString());
                                atm.setCardNo(ele.get(1).toString());
                                atm.setMinLmt(ele.get(3).toString());
                                atm.setTxnLimitType(ele.get(10).toString());
                                atm.setWithdrawalLimitAmount(new BigDecimal(ele.get(11).toString()));
                                atm.setWithdrawalLimitCount(Integer.parseInt(ele.get(12).toString()));
                                atm.setPurchaseLimitAmount(new BigDecimal(ele.get(13).toString()));
                                atm.setPurchaseLimitCount(Integer.parseInt(ele.get(14).toString()));
                                atm.setRegistrationDt(sdf.format(ymdd.parse(ele.get(19).toString())));

                                deactivateTable.add(atm);

                                this.setMessage("Please select the deactivate link for deactivate the entry.");
                            }
                        } else {
                            this.setMessage("There is no data for this account no.");
                        }
                    }
                }
            } else if (this.fileMode.equalsIgnoreCase("D") && this.operation.equalsIgnoreCase("U")) {
                if (this.accountNo.equalsIgnoreCase(null) || this.accountNo.equalsIgnoreCase("")) {
                    this.setMessage("Please fill the account no.");
                    return;
                }
                validateAccountField(this.accountNo);
                List getAccountList = generalFacadeRemote.getAccountInAtmCardMaster(this.accountNo);
                if (getAccountList.isEmpty()) {
                    this.setMessage("Detail is not available for this account. Please check your account no.");
                }
                deactivateTable = new ArrayList<AtmCardMappGrid>();
                List dataList = generalFacadeRemote.gridDetailForDeactivateCard(getOrgnBrCode(), this.accountNo);
                if (!dataList.isEmpty()) {
                    for (int i = 0; i < dataList.size(); i++) {
                        AtmCardMappGrid atm = new AtmCardMappGrid();
                        Vector ele = (Vector) dataList.get(i);
                        atm.setAcNo(ele.get(0).toString());
                        atm.setCardNo(ele.get(1).toString());
                        atm.setMinLmt(ele.get(3).toString());
                        atm.setTxnLimitType(ele.get(10).toString());
                        atm.setWithdrawalLimitAmount(new BigDecimal(ele.get(11).toString()));
                        atm.setWithdrawalLimitCount(Integer.parseInt(ele.get(12).toString()));
                        atm.setPurchaseLimitAmount(new BigDecimal(ele.get(13).toString()));
                        atm.setPurchaseLimitCount(Integer.parseInt(ele.get(14).toString()));
                        atm.setRegistrationDt(sdf.format(ymdd.parse(ele.get(19).toString())));

                        deactivateTable.add(atm);
                        this.setMessage("Please select the link for update card no.");
                    }
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void selectDetailUpdateOpration() {
        this.setMessage("");
        try {
            if (!(deactiveCurrentItem == null)) {
                this.setChnNo(deactiveCurrentItem.getCardNo());
            }

        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void checkUncheckProcess() {
        this.setMessage("");
        try {
            this.setSan("");
            this.setNewSan("");
            this.setSecondaryTxnLimitType("0");
//            this.setSecondaryLimitAmount("");
//            this.setSecondaryLimitCount("");
            this.setSecondaryWdrlLimitAmount("");
            this.setSecondaryWdrlLimitCount("");
            this.setSecondaryPurLimitAmount("");
            this.setSecondaryPurLimitCount("");
            this.selectRender = false;
            this.deactiveRender = false;
            if (this.secondaryChkBox == true) {
                this.secondaryPanelDisplay = "";
                if (this.fileMode.equalsIgnoreCase("N") && (this.operation.equalsIgnoreCase("A") || this.operation.equalsIgnoreCase("M"))) {
                    this.selectRender = true;
                    //Default set value 
                    List list = commonReportRemote.getAmountLimitList();
                    for (int i = 0; i < list.size(); i++) {
                        Vector v = (Vector) list.get(i);
                        if (v.get(0).toString().equalsIgnoreCase("MIN-LIMIT")) {
                            setSecondaryLimitAmount(v.get(1).toString().trim());
                        }
                        if (v.get(0).toString().equalsIgnoreCase("PURCHASE-LIMIT")) {
                            setSecondaryPurLimitAmount(v.get(1).toString().trim());
                        }
                        if (v.get(0).toString().equalsIgnoreCase("PURCHASE-COUNT")) {
                            setSecondaryPurLimitCount(v.get(1).toString().trim());
                        }
                        if (v.get(0).toString().equalsIgnoreCase("WITHDRAWAL-LIMIT")) {
                            setSecondaryWdrlLimitAmount(v.get(1).toString().trim());
                        }
                        if (v.get(0).toString().equalsIgnoreCase("WITHDRAWAL-COUNT")) {
                            setSecondaryWdrlLimitCount(v.get(1).toString().trim());
                        }
                    }

                } else if (this.fileMode.equalsIgnoreCase("D") && this.operation.equalsIgnoreCase("I")) {
                    this.selectRender = false;
                    this.deactiveRender = true;
                    this.selectUpdateRender = false;
                }

            } else if (this.secondaryChkBox == false) {
                this.secondaryPanelDisplay = "none";
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void chnProcess() {
        this.setMessage("");
        try {
            if (this.fileMode == null
                    || this.fileMode.equals("0") || this.fileMode.equals("")) {
                this.setMessage("Please select file mode.");
                return;
            }
            if (this.operation == null || this.operation.equals("0") || this.operation.equals("")) {
                this.setMessage("Please select operation.");
                return;
            }
            if (this.accountNo == null || accountNo.equalsIgnoreCase("")) {
                this.setMessage("Account number should not be blank.");
                return;
            }
            if (this.newAccountNumber == null || newAccountNumber.equalsIgnoreCase("")) {
                this.setMessage("New account number can not be blank.");
                return;
            }
            if (this.chnNo == null || this.chnNo.equals("")) {
                this.setMessage("CHN(Card No) can not be blank.");
                return;
            }
            if (fileMode.equalsIgnoreCase("S") && this.operation.equalsIgnoreCase("U")) {
                this.presentCardStatus = "";
                this.setCardStatus("0");
                List<AtmCardMappGrid> dataList = miscRemoteS1.getAtmAccountDetail(this.newAccountNumber.trim(), this.chnNo.trim());
                if (dataList.isEmpty()) {
                    this.setMessage("There is no data");
                    return;
                }
                this.setPresentCardStatus(commonReportRemote.getRefRecDesc("406", dataList.get(0).getCardStatus().trim()));
            } else if (fileMode.equalsIgnoreCase("C") && this.operation.equalsIgnoreCase("U")) {
                this.prEncodingName = "";
                this.prEmbossingName = "";
                this.chgEncodingName = "";
                this.chgEmbossingName = "";
                List<AtmCardMappGrid> dataList = miscRemoteS1.getAtmAccountDetail(this.newAccountNumber.trim(), this.chnNo.trim());
                if (dataList.isEmpty()) {
                    this.setMessage("There is no data");
                    return;
                }
                this.setPrEncodingName(dataList.get(0).getEncodingName().trim());
                this.setPrEmbossingName(dataList.get(0).getEmbossingName().trim());
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void setTableDataToForm() {
        try {
            if (this.fileMode.equalsIgnoreCase("N") && this.operation.equals("A")
                    || this.fileMode.equalsIgnoreCase("N") && this.operation.equals("M")) {
                if (secondaryCurrentItem == null) {
                    this.setMessage("Please select a row from table.");
                    return;
                }
                this.setSan(secondaryCurrentItem.getSecondaryAccount());
                this.setSecondaryTxnLimitType(secondaryCurrentItem.getTxnLimitType());
//                this.setSecondaryLimitAmount(secondaryCurrentItem.getCommonLimitAmount().toString());
//                this.setSecondaryLimitCount(secondaryCurrentItem.getCommonLimitCount().toString());
                this.setSecondaryWdrlLimitAmount(String.valueOf(secondaryCurrentItem.getWithdrawalLimitAmount().intValueExact()));
                this.setSecondaryWdrlLimitCount(secondaryCurrentItem.getWithdrawalLimitCount().toString());
                this.setSecondaryPurLimitAmount(String.valueOf(secondaryCurrentItem.getPurchaseLimitAmount().intValueExact()));
                this.setSecondaryPurLimitCount(secondaryCurrentItem.getPurchaseLimitCount().toString());

                secondaryTable.remove(secondaryCurrentItem);

                this.setMessage("Now you can modify the secondary account detail in the field.");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    //changes by Rahul 

    public void getviewDetails() {
        this.setVerifyPanelViewFlag(true);
        try {
            if (this.operation.equalsIgnoreCase("Y")) {
                this.setAcno1(verifyCurrentItem.getAcNo());
                this.setCard1(verifyCurrentItem.getCardNo());
                this.setDate1(verifyCurrentItem.getRegistrationDt());
                this.setMinlmt1(verifyCurrentItem.getMinLmt());
                this.setEncoding1(verifyCurrentItem.getEncodingName());
                this.setWidlmtamt1(verifyCurrentItem.getWithdrawalLimitAmount().toString());
                this.setWidlmtcount1(verifyCurrentItem.getWithdrawalLimitCount().toString());
                List referenceList = commonReportRemote.getRefCardStatuslist("406", verifyCurrentItem.getCardStatus());
                this.setCardStatus1(referenceList.get(0).toString());
                this.setTxnLimitType1(verifyCurrentItem.getTxnLimitType());
                this.setPurlmtamt1(verifyCurrentItem.getPurchaseLimitAmount().toString());
                this.setPurlmtcount1(verifyCurrentItem.getPurchaseLimitCount().toString());
                this.setEmbossing1(verifyCurrentItem.getEmbossingName().toString());
                CustomerDetail customerObj = miscRemoteS1.getCustomerDetailByAccountNo(acno1);
                this.setName1(customerObj.getCustomerName());
                this.setGender1(customerObj.getGender());
                this.setCustid1(customerObj.getCustomerId());

                secondaryViewTable = new ArrayList<AtmSecondryAccountDetail>();
                List secondaryAcList = generalFacadeRemote.getSecondaryAccountData(this.acno1, this.card1);
                if (!secondaryAcList.isEmpty()) {
                    for (int i = 0; i < secondaryAcList.size(); i++) {
                        AtmSecondryAccountDetail Obj = new AtmSecondryAccountDetail();
                        Vector ele = (Vector) secondaryAcList.get(i);
                        Obj.setSecondaryAccount(ele.get(2).toString());
                        Obj.setCardNo(ele.get(1).toString());
                        Obj.setTxnLimitType(ele.get(3).toString());
                        Obj.setWithdrawalLimitAmount(new BigDecimal(ele.get(4).toString()));
                        Obj.setWithdrawalLimitCount(Integer.parseInt(ele.get(5).toString()));
                        Obj.setPurchaseLimitAmount(new BigDecimal(ele.get(6).toString()));
                        Obj.setPurchaseLimitCount(Integer.parseInt(ele.get(7).toString()));

                        secondaryViewTable.add(Obj);
                    }
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    //changes by Rahul

    public void accountVerification() {
        try {
            if (this.operation.equalsIgnoreCase("Y")) {
                String verify = generalFacadeRemote.verifyAtmAccountDetail(verifyCurrentItem, getUserName(), getTodayDate());
                if (!verify.equalsIgnoreCase("true")) {
                    this.setMessage(verify);
                    return;
                }
                verifyTable.remove(verifyCurrentItem);
                this.setMessage("This entry has been verified successfully.");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    //Changes by Rahul

    public void cardDeactivation() {
        try {
            if (this.operation.equalsIgnoreCase("I")) {
                if (deactiveCurrentItem.getCardNo().equals("")) {
                    this.setMessage("Card Number is Blank. Please update Card number then deactivate the Card");
                } else {
                    String deactivate = generalFacadeRemote.deactivateAtmAccountDetail(deactiveCurrentItem, getUserName(), getTodayDate());
                    if (!deactivate.equalsIgnoreCase("true")) {
                        this.setMessage(deactivate);
                        return;
                    }
                    deactivateTable.remove(deactiveCurrentItem);
                    this.setMessage("This entry has been deactivated successfully.");
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }

    }

    public void sanProcess() {
        try {
            if (this.san == null || this.san.trim().equals("") || this.san.trim().length() == 0) {
                this.setMessage("SAN should not be blank.");
                return;
            }
            if (!(this.san.length() == 12 || (this.san.length() == Integer.parseInt(getAcNoLen())))) {
                this.setMessage("Please fill proper SAN.");
                return;
            }
            this.newSan = ftsRemote.getNewAccountNumber(this.san);
            if (!miscRemoteS1.isPrimaryTypeAccount(newSan)) {
                this.setMessage("Please fill primary type account number !");
                return;
            }
            if (this.newSan.trim().equalsIgnoreCase(this.newAccountNumber)) {
                this.setMessage("Secondary account will be different from your primary account number !");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void addSecondaryAccountDetail() {
        try {
            if (this.fileMode.equalsIgnoreCase("N") && (this.operation.equals("A") || this.operation.equals("M"))) {
                if (this.san == null || this.san.trim().equals("") || this.san.trim().length() == 0) {
                    this.setMessage("SAN should not be blank.");
                    return;
                }
                if (!(this.san.length() == 12 || (this.san.length() == Integer.parseInt(getAcNoLen())))) {
                    this.setMessage("Please fill proper SAN.");
                    return;
                }
                this.newSan = ftsRemote.getNewAccountNumber(this.san);
                if (!miscRemoteS1.isPrimaryTypeAccount(newSan)) {
                    this.setMessage("Please fill primary type account number !");
                    return;
                }
                if (this.newSan.trim().equalsIgnoreCase(this.newAccountNumber)) {
                    this.setMessage("Secondary account will be different from your primary account number !");
                    return;
                }
                if (this.secondaryTxnLimitType == null || this.secondaryTxnLimitType.equals("0") || this.secondaryTxnLimitType.equals("")) {
                    this.setMessage("Please select txn limit type.");
                    return;
                }
//                if (this.secondaryLimitAmount == null || this.secondaryLimitAmount.equals("0") || this.secondaryLimitAmount.equals("")) {
//                    this.setMessage("Please fill limit amount.");
//                    return;
//                }

//                if (ParseFileUtil.isValidAmount(this.secondaryLimitAmount, 0) == false) {
//                    this.setMessage("Please fill proper amount without decimal point.");
//                    return;
//                }
//                if (this.secondaryLimitCount == null || this.secondaryLimitCount.equals("")) {
//                    this.setMessage("Please fill limit count.");
//                    return;
//                }
                if (this.secondaryWdrlLimitAmount == null || this.secondaryWdrlLimitAmount.equals("")) {
                    this.setMessage("Please fill the widrawal Amount.");
                    return;
                }
                if (ParseFileUtil.isValidAmount(this.secondaryWdrlLimitAmount, 0) == false) {
                    this.setMessage("Please fill proper amount without decimal point.");
                    return;
                }
                Pattern wd = Pattern.compile("^([1-9][0-9]+|[1-9])$");
                Matcher matcher = wd.matcher(this.secondaryWdrlLimitAmount);
                if (!matcher.matches()) {
                    this.setMessage("Widrawal limit amount should be greater than zero.");
                    return;
                }
                if (this.secondaryWdrlLimitCount == null || this.secondaryWdrlLimitCount.equals("")) {
                    this.setMessage("Please fill the widrawal limit count.");
                    return;
                }
                if (Double.parseDouble(this.secondaryWdrlLimitCount) < 0) {
                    this.setMessage("Withdrawal limit count can not be less than zero.");
                    return;
                }
                if (this.secondaryPurLimitAmount == null || this.secondaryPurLimitAmount.equals("")) {
                    this.setMessage("Please fill the purchase amount .");
                    return;
                }
                if (ParseFileUtil.isValidAmount(this.secondaryPurLimitAmount, 0) == false) {
                    this.setMessage("Please fill proper amount without decimal point.");
                    return;
                }

                Matcher matcher1 = wd.matcher(this.secondaryPurLimitAmount);
                if (!matcher1.matches()) {
                    this.setMessage("Purchase limit amount should be greater than zero.");
                    return;
                }
                if (this.secondaryPurLimitCount == null || this.secondaryPurLimitCount.equals("")) {
                    this.setMessage("Please fill the purchase limit count.");
                    return;
                }
                if (Double.parseDouble(this.secondaryPurLimitCount) < 0) {
                    this.setMessage("purchase limit count can not be less than zero.");
                    return;
                }
                boolean flag = false;
                for (AtmSecondryAccountDetail obj : secondaryTable) { //We are not checking the card no because card no will be blank
                    if (obj.getPrimaryAccount().equalsIgnoreCase(newAccountNumber) && obj.getSecondaryAccount().equalsIgnoreCase(newSan)) {
                        flag = true;
                        break;
                    }
                }
                if (flag == true) {
                    this.setMessage("This particular entry is already exist."
                            + " Please delete the same account entry in the grid then modify.");
                } else {
                    AtmSecondryAccountDetail addedObj = new AtmSecondryAccountDetail();
                    addedObj.setPrimaryAccount(this.newAccountNumber);
                    addedObj.setCardNo("");
                    addedObj.setSecondaryAccount(this.newSan);
                    addedObj.setTxnLimitType(this.secondaryTxnLimitType);
//                    addedObj.setCommonLimitAmount(new BigDecimal(this.secondaryLimitAmount));
//                    addedObj.setCommonLimitCount(Integer.parseInt(this.secondaryLimitCount));
                    addedObj.setWithdrawalLimitAmount(new BigDecimal(this.secondaryWdrlLimitAmount));
                    addedObj.setWithdrawalLimitCount(Integer.parseInt(this.secondaryWdrlLimitCount));
                    addedObj.setPurchaseLimitAmount(new BigDecimal(this.secondaryPurLimitAmount));
                    addedObj.setPurchaseLimitCount(Integer.parseInt(this.secondaryPurLimitCount));

                    secondaryTable.add(addedObj);
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processAction() {
        try {
            if (this.fileMode == null || this.fileMode.equals("0") || this.fileMode.equals("")) {
                this.setMessage("Please select file mode.");
                return;
            }
            if (this.operation == null || this.operation.equals("0") || this.operation.equals("")) {
                this.setMessage("Please select operation.");
                return;
            }
            String result = "";
            if (this.fileMode.equalsIgnoreCase("N") && (this.operation.equals("A") || this.operation.equals("M"))) {
                if (validateAccountField(this.accountNo) == false) {
                    return;
                }
                if (validateFirstSeqFileField() == false) {
                    return;
                }
                addSecondaryAccountDetail();

                result = miscRemoteS1.processAddAndModifyFirstSeqFile(this.fileMode.trim(), this.operation.trim(),
                        this.newAccountNumber.trim(), this.txnLimitType.trim(), this.encodingName.trim(), this.embossingName.trim(), this.cardType.trim(), this.txnLimitCardType.trim(),
                        this.cardRelationship.trim(), this.serviceCode.trim(), this.kccEmvType.trim(), secondaryTable,
                        getUserName(), this.wdrlLimitAmount.trim(), this.wdrlLimitCount.trim(), this.purLimitAmount.trim(),
                        this.purLimitCount.trim(), this.minLimit.trim(), this.san.trim());

            } else if (this.fileMode.equalsIgnoreCase("N") && this.operation.equals("D")) {
                if (validateAccountField(this.accountNo) == false) {
                    return;
                }
                result = miscRemoteS1.processDeleteFirstSeqFile(this.fileMode.trim(), this.operation.trim(),
                        this.newAccountNumber.trim(), secondaryTable, getUserName());
            } else if (fileMode.equalsIgnoreCase("S") && this.operation.equalsIgnoreCase("U")) { //Second File
                if (validateAccountField(this.accountNo) == false) {
                    return;
                }
                if (this.chnNo == null || this.chnNo.equals("")) {
                    this.setMessage("CHN(Card No) can not be blank.");
                    return;
                }
                if (this.presentCardStatus == null || this.presentCardStatus.trim().equals("")) {
                    this.setMessage("There should be present card status.");
                    return;
                }
                if (this.cardStatus == null || this.cardStatus.equals("0") || this.cardStatus.equals("")) {
                    this.setMessage("Please select card status that will change.");
                    return;
                }
                result = miscRemoteS1.changeCardStatus(this.fileMode.trim(), this.operation.trim(), this.newAccountNumber.trim(),
                        this.chnNo.trim(), this.cardStatus.trim(), getUserName());
            } else if (fileMode.equalsIgnoreCase("C") && this.operation.equalsIgnoreCase("U")) { //Fourth File
                if (validateAccountField(this.accountNo) == false) {
                    return;
                }
                if (this.chnNo == null || this.chnNo.equals("")) {
                    this.setMessage("CHN(Card No) can not be blank.");
                    return;
                }
                if (this.prEncodingName == null || this.prEncodingName.trim().equals("")) {
                    this.setMessage("There should be present encoding name.");
                    return;
                }
                if (this.prEmbossingName == null || this.prEmbossingName.trim().equals("")) {
                    this.setMessage("There should be present embossing name.");
                    return;
                }
                if (this.chgEncodingName == null || this.chgEncodingName.trim().equals("")) {
                    this.setMessage("Please fill encoding name that will change.");
                    return;
                }
                if (ParseFileUtil.containsSpecialCharacter(chgEncodingName)) {
                    this.setMessage("Please fill encoding name without special character.");
                    return;
                }
                if (this.chgEmbossingName == null || this.chgEmbossingName.trim().equals("")) {
                    this.setMessage("Please fill embossing name that will change.");
                    return;
                }
                if (ParseFileUtil.containsSpecialCharacter(chgEmbossingName)) {
                    this.setMessage("Please fill embossing name without special character.");
                    return;
                }
                result = miscRemoteS1.changeNames(this.fileMode.trim(), this.operation.trim(), this.newAccountNumber.trim(),
                        this.chnNo.trim(), this.chgEncodingName.trim(), this.chgEmbossingName.trim(), getUserName());
            } else if (fileMode.equalsIgnoreCase("D") && this.operation.equalsIgnoreCase("U")) { //Detail Updation
                if (validateAccountField(this.accountNo) == false) {
                    return;
                }
                if (this.chnNo == null || this.chnNo.equals("")) {
                    this.setMessage("CHN(Card No) can not be blank.");
                    return;
                }
                if (this.chnNo.trim().length() < 16 || this.chnNo.trim().length() > 19) {
                    this.setMessage("CHN(Card No) will be 16 to 19 digit length.");
                    return;
                }
                result = miscRemoteS1.detailModification(this.newAccountNumber.trim(), this.chnNo.trim(), deactiveCurrentItem.getCardNo(), getUserName());
            }
            if (!result.equalsIgnoreCase("true")) {
                this.setMessage(result);
                return;
            }
            refreshSeqForm();
            this.setMessage("Process has been completed successfully.");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public boolean validateAccountField(String accNo) {
        try {
            if (accNo == null || accNo.equalsIgnoreCase("")) {
                this.setMessage("Account number should not be blank.");
                return false;
            }
            if (!(accNo.length() == 12 || (accNo.length() == Integer.parseInt(getAcNoLen())))) {
                this.setMessage("Please fill proper Account Number.");
                return false;
            }
            this.newAccountNumber = ftsRemote.getNewAccountNumber(accNo);
            if (!(newAccountNumber.substring(0, 2).equalsIgnoreCase(getOrgnBrCode()))) {
                this.setMessage("Please fill your branch account number !");
                return false;
            }
            if (!miscRemoteS1.isPrimaryTypeAccount(newAccountNumber)) {
                this.setMessage("Please fill your branch primary account number !");
                return false;
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean validateFirstSeqFileField() {
        try {
            if (this.fileMode.equalsIgnoreCase("N") && (this.operation.equals("A") || this.operation.equals("M"))) {
                if (this.customerId == null || this.customerId.equals("")) {
                    this.setMessage("There should be customer Id.");
                    return false;
                }
                if (this.gender == null || this.gender.equals("")) {
                    this.setMessage("There should be gender.");
                    return false;
                }
                if (this.name == null || this.name.equals("")) {
                    this.setMessage("There should be name.");
                    return false;
                }
                if (this.dob == null || this.dob.equals("")) {
                    this.setMessage("There should be date of birth.");
                    return false;
                }
                if (this.mail == null || this.mail.equals("")) {
                    this.setMessage("There should be mail address.");
                    return false;
                }
                if (this.encodingName == null || this.encodingName.equals("")) {
                    this.setMessage("Please fill encoding name.");
                    return false;
                }
                if (this.embossingName == null || this.embossingName.equals("")) {
                    this.setMessage("Please fill embossing name.");
                    return false;
                }
//                if (this.cardType == null || this.cardType.equals("0") || this.cardType.equals("")) {
//                    this.setMessage("Please select card type.");
//                    return false;
//                }
////                if (this.txnLimitCardType == null || this.txnLimitCardType.equals("0") || this.txnLimitCardType.equals("")) {
////                    this.setMessage("Please select txn limit card type.");
////                    return false;
////                } 
//                if (this.txnLimitCardType == null || this.txnLimitCardType.equals("0") || this.txnLimitCardType.equals("")) {
//                    this.setTxnLimitCardType("CD");
//                 }
//                if (this.cardRelationship == null || this.cardRelationship.equals("0") || this.cardRelationship.equals("")) {
//                    this.setMessage("Please select card relationship.");
//                    return false;
//                }
////                if (this.serviceCode == null || this.serviceCode.equals("0") || this.serviceCode.equals("")) {
////                    this.setMessage("Please select service code.");
////                    return false;
////                } 
//                if (this.serviceCode == null || this.serviceCode.equals("0") || this.serviceCode.equals("")) {
//                    this.setServiceCode("03");
//                }
                if (this.txnLimitType == null || this.txnLimitType.equals("0") || this.txnLimitType.equals("")) {
                    this.setMessage("Please select txn limit type.");
                    return false;
                }
                if (this.txnLimitType.equalsIgnoreCase("AC")) {
//                    if (this.limitAmount == null || this.limitAmount.equals("0") || this.limitAmount.equals("")) {
//                        this.setMessage("Please fill limit amount.");
//                        return false;
//                    }
//                    if (ParseFileUtil.isValidAmount(this.limitAmount, 0) == false) {
//                        this.setMessage("Please fill proper amount without decimal point.");
//                        return false;
//                    }
//                    if (this.limitCount == null || this.limitCount.equals("")) {
//                        this.setMessage("Please fill limit count.");
//                        return false;
//                    }
                    if (this.wdrlLimitAmount == null || this.wdrlLimitAmount.equals("")) {
                        this.setMessage("Please fill the withdrawal amount.");
                        return false;
                    }
                    if (ParseFileUtil.isValidAmount(this.wdrlLimitAmount, 0) == false) {
                        this.setMessage("Please fill proper amount without decimal point.");
                        return false;
                    }
                    Pattern wd = Pattern.compile("^([1-9][0-9]+|[1-9])$");
                    Matcher matcher = wd.matcher(this.wdrlLimitAmount);
                    if (!matcher.matches()) {
                        this.setMessage("Withdrawal limit amount should be greater than zero.");
                        return false;
                    }
                    if (this.wdrlLimitCount == null || this.wdrlLimitCount.equals("")) {
                        this.setMessage("Please fill the withdrawal limit count.");
                        return false;
                    }
                    if (Double.parseDouble(this.wdrlLimitCount) < 0) {
                        this.setMessage("Withdrawal limit count can not be less than zero.");
                        return false;
                    }
                    if (this.purLimitAmount == null || this.purLimitAmount.equals("")) {
                        this.setMessage("Please fill the purchase amount .");
                        return false;
                    }
                    if (ParseFileUtil.isValidAmount(this.purLimitAmount, 0) == false) {
                        this.setMessage("Please fill proper amount without decimal point.");
                        return false;
                    }

                    Matcher matcher1 = wd.matcher(this.purLimitAmount);
                    if (!matcher1.matches()) {
                        this.setMessage("Purchase limit amount should be greater than zero.");
                        return false;
                    }
                    if (this.purLimitCount == null || this.purLimitCount.equals("")) {
                        this.setMessage("Please fill the purchase limit count.");
                        return false;
                    }
                    if (Double.parseDouble(this.purLimitCount) < 0) {
                        this.setMessage("Purchase limit count can not be less than zero.");
                        return false;
                    }
                    if (this.minLimit == null || this.minLimit.equals("")) {
                        this.setMessage("Please fill the minimum limit.");
                        return false;
                    }
                    if (ParseFileUtil.isValidAmount(this.minLimit, 0) == false) {
                        this.setMessage("Please fill proper minimum limit without decimal point.");
                        return false;
                    }
                }
//                  if (this.kccEmvType == null || this.kccEmvType.equals("0") || this.kccEmvType.equals("")) {
//                    this.setMessage("Please select KCC/EMV Type.");
//                    return false;
//                 }
//                if (this.secondaryChkBox == true) {
//                    this.setMessage("There should be some secondary account detail.");
//                    return false;
//                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
            return false;
        }
        return true;
    }

    public void refreshFirstFileField() {
        this.chnPanelDisplay = "none";
        this.firstDisplay = "";
        this.secondaryPanelDisplay = "none";
        this.acdisplay = "";
        this.fifthPanelDisplay = "none";
        this.setNewAccountNumber("");
        this.setChnNo("");
        this.setCustomerId("");
        this.setGender("");
        this.setName("");
        this.setDob("");
        this.setMail("");
        this.setEncodingName("");
        this.setEmbossingName("");
//        this.setCardType("0");
//        this.setTxnLimitCardType("0");
//        this.setCardRelationship("0");
//        this.setServiceCode("0");
        this.setTxnLimitType("0");
        this.setWdrlLimitAmount("");
        this.setWdrlLimitCount("");
        this.setPurLimitAmount("");
        this.setPurLimitCount("");
        this.setMinLimit("");
//        this.setLimitAmount("");
//        this.setLimitCount("");
//        this.setKccEmvType("0");
        this.setSecondaryChkBox(false);
        this.setSan("");
        this.setNewSan("");
        this.setSecondaryTxnLimitType("0");
//        this.setSecondaryLimitAmount("");
//        this.setSecondaryLimitCount("");
        this.setSecondaryWdrlLimitAmount("");
        this.setSecondaryWdrlLimitCount("");
        this.setSecondaryPurLimitAmount("");
        this.setSecondaryPurLimitCount("");
        secondaryTable = new ArrayList<>();
        secondaryCurrentItem = null;
        //Second File
        this.secondFileDataDisplay = "none";
        this.setPresentCardStatus("");
        this.setCardStatus("0");
        //Fourth File
        this.fourthFileDataDisplay = "none";
        this.setPrEncodingName("");
        this.setPrEmbossingName("");
        this.setChgEncodingName("");
        this.setChgEmbossingName("");
//        verifyTable = new ArrayLIst<>();
        verifyCurrentItem = null;
        deactiveCurrentItem = null;
    }

    public void refreshSeqForm() {
        this.fifthPanelDisplay = "none";
        this.acdisplay = "";
        this.setMessage("");
        this.setFileMode("0");
        this.setOperation("0");
        this.setAccountNo("");
        this.setNewAccountNumber("");
        this.chnPanelDisplay = "none";
        this.setChnNo("");
        this.firstDisplay = "none";
        this.setCustomerId("");
        this.setGender("");
        this.setName("");
        this.setDob("");
        this.setMail("");
        this.setEncodingName("");
        this.setEmbossingName("");
//        this.setCardType("0");
//        this.setTxnLimitCardType("0");
//        this.setCardRelationship("0");
//        this.setServiceCode("0");
        this.setTxnLimitType("0");
        this.setWdrlLimitAmount("");
        this.setWdrlLimitCount("");
        this.setPurLimitAmount("");
        this.setPurLimitCount("");
        this.setMinLimit("");
//        this.setLimitAmount("");
//        this.setLimitCount("");
//        this.setKccEmvType("0");
        this.setSecondaryChkBox(false);
        this.secondaryPanelDisplay = "none";
        this.setSan("");
        this.setNewSan("");
        this.setSecondaryTxnLimitType("0");
//        this.setSecondaryLimitAmount("");
//        this.setSecondaryLimitCount("");
        this.setSecondaryWdrlLimitAmount("");
        this.setSecondaryWdrlLimitCount("");
        this.setSecondaryPurLimitAmount("");
        this.setSecondaryPurLimitCount("");
        secondaryTable = new ArrayList<>();
        secondaryCurrentItem = null;
        //Second File Related
        this.secondFileDataDisplay = "none";
        this.setPresentCardStatus("");
        this.setCardStatus("0");
        this.selectRender = false;
        //Fourth File
        this.fourthFileDataDisplay = "none";
        this.setPrEncodingName("");
        this.setPrEmbossingName("");
        this.setChgEncodingName("");
        this.setChgEmbossingName("");
        this.deactivateTable = null;
        verifyCurrentItem = null;
        deactiveCurrentItem = null;
        sixthPanelDisplay = "none";
        fifthPanelDisplay = "none";
    }

    public String exitSeqBtnAction() {
        refreshSeqForm();
        return "case1";
    }

    //Getter And Setter
    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getBtnValue() {
        return btnValue;
    }

    public void setBtnValue(String btnValue) {
        this.btnValue = btnValue;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public Date getIssueDt() {
        return issueDt;
    }

    public void setIssueDt(Date issueDt) {
        this.issueDt = issueDt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMinLmt() {
        return minLmt;
    }

    public void setMinLmt(String minLmt) {
        this.minLmt = minLmt;
    }

    public String getStFlg() {
        return stFlg;
    }

    public void setStFlg(String stFlg) {
        this.stFlg = stFlg;
    }

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public boolean isAcFlag() {
        return acFlag;
    }

    public void setAcFlag(boolean acFlag) {
        this.acFlag = acFlag;
    }

    public boolean isCardFlag() {
        return cardFlag;
    }

    public void setCardFlag(boolean cardFlag) {
        this.cardFlag = cardFlag;
    }

    public boolean isDtFlag() {
        return dtFlag;
    }

    public void setDtFlag(boolean dtFlag) {
        this.dtFlag = dtFlag;
    }

    public boolean isLmtFlag() {
        return lmtFlag;
    }

    public void setLmtFlag(boolean lmtFlag) {
        this.lmtFlag = lmtFlag;
    }

    public boolean isStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(boolean statusFlag) {
        this.statusFlag = statusFlag;
    }

    public AtmCardMappGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(AtmCardMappGrid currentItem) {
        this.currentItem = currentItem;
    }

    public String getEnter() {
        return enter;
    }

    public void setEnter(String enter) {
        this.enter = enter;
    }

    public List<AtmCardMappGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<AtmCardMappGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public String getOldCard() {
        return oldCard;
    }

    public void setOldCard(String oldCard) {
        this.oldCard = oldCard;
    }

    public boolean isBtnFlag() {
        return btnFlag;
    }

    public void setBtnFlag(boolean btnFlag) {
        this.btnFlag = btnFlag;
    }

    public int getIsSeqMode() {
        return isSeqMode;
    }

    public void setIsSeqMode(int isSeqMode) {
        this.isSeqMode = isSeqMode;
    }

    public String getSeqFileDisplay() {
        return seqFileDisplay;
    }

    public void setSeqFileDisplay(String seqFileDisplay) {
        this.seqFileDisplay = seqFileDisplay;
    }

    public String getNormalDisplay() {
        return normalDisplay;
    }

    public void setNormalDisplay(String normalDisplay) {
        this.normalDisplay = normalDisplay;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getChnPanelDisplay() {
        return chnPanelDisplay;
    }

    public void setChnPanelDisplay(String chnPanelDisplay) {
        this.chnPanelDisplay = chnPanelDisplay;
    }

    public String getFirstDisplay() {
        return firstDisplay;
    }

    public void setFirstDisplay(String firstDisplay) {
        this.firstDisplay = firstDisplay;
    }

    public String getSecondaryPanelDisplay() {
        return secondaryPanelDisplay;
    }

    public void setSecondaryPanelDisplay(String secondaryPanelDisplay) {
        this.secondaryPanelDisplay = secondaryPanelDisplay;
    }

    public String getFileMode() {
        return fileMode;
    }

    public void setFileMode(String fileMode) {
        this.fileMode = fileMode;
    }

    public List<SelectItem> getFileModeList() {
        return fileModeList;
    }

    public void setFileModeList(List<SelectItem> fileModeList) {
        this.fileModeList = fileModeList;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public List<SelectItem> getOperationList() {
        return operationList;
    }

    public void setOperationList(List<SelectItem> operationList) {
        this.operationList = operationList;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getNewAccountNumber() {
        return newAccountNumber;
    }

    public void setNewAccountNumber(String newAccountNumber) {
        this.newAccountNumber = newAccountNumber;
    }

    public String getChnNo() {
        return chnNo;
    }

    public void setChnNo(String chnNo) {
        this.chnNo = chnNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getEncodingName() {
        return encodingName;
    }

    public void setEncodingName(String encodingName) {
        this.encodingName = encodingName;
    }

    public String getEmbossingName() {
        return embossingName;
    }

    public void setEmbossingName(String embossingName) {
        this.embossingName = embossingName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public List<SelectItem> getCardTypeList() {
        return cardTypeList;
    }

    public void setCardTypeList(List<SelectItem> cardTypeList) {
        this.cardTypeList = cardTypeList;
    }

    public String getTxnLimitCardType() {
        return txnLimitCardType;
    }

    public void setTxnLimitCardType(String txnLimitCardType) {
        this.txnLimitCardType = txnLimitCardType;
    }

    public List<SelectItem> getTxnLimitCardTypeList() {
        return txnLimitCardTypeList;
    }

    public void setTxnLimitCardTypeList(List<SelectItem> txnLimitCardTypeList) {
        this.txnLimitCardTypeList = txnLimitCardTypeList;
    }
//

    public String getCardRelationship() {
        return cardRelationship;
    }

    public void setCardRelationship(String cardRelationship) {
        this.cardRelationship = cardRelationship;
    }

    public List<SelectItem> getCardRelationshipList() {
        return cardRelationshipList;
    }

    public void setCardRelationshipList(List<SelectItem> cardRelationshipList) {
        this.cardRelationshipList = cardRelationshipList;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public List<SelectItem> getServiceCodeList() {
        return serviceCodeList;
    }

    public void setServiceCodeList(List<SelectItem> serviceCodeList) {
        this.serviceCodeList = serviceCodeList;
    }

    public String getTxnLimitType() {
        return txnLimitType;
    }

    public void setTxnLimitType(String txnLimitType) {
        this.txnLimitType = txnLimitType;
    }

    public List<SelectItem> getTxnLimitTypeList() {
        return txnLimitTypeList;
    }

    public void setTxnLimitTypeList(List<SelectItem> txnLimitTypeList) {
        this.txnLimitTypeList = txnLimitTypeList;
    }

    public String getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(String limitAmount) {
        this.limitAmount = limitAmount;
    }

    public String getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(String limitCount) {
        this.limitCount = limitCount;
    }

    public String getKccEmvType() {
        return kccEmvType;
    }

    public void setKccEmvType(String kccEmvType) {
        this.kccEmvType = kccEmvType;
    }

    public List<SelectItem> getKccEmvTypeList() {
        return kccEmvTypeList;
    }

    public void setKccEmvTypeList(List<SelectItem> kccEmvTypeList) {
        this.kccEmvTypeList = kccEmvTypeList;
    }

    public boolean isSecondaryChkBox() {
        return secondaryChkBox;
    }

    public void setSecondaryChkBox(boolean secondaryChkBox) {
        this.secondaryChkBox = secondaryChkBox;
    }

    public String getSan() {
        return san;
    }

    public void setSan(String san) {
        this.san = san;
    }

    public String getSecondaryTxnLimitType() {
        return secondaryTxnLimitType;
    }

    public void setSecondaryTxnLimitType(String secondaryTxnLimitType) {
        this.secondaryTxnLimitType = secondaryTxnLimitType;
    }

    public List<SelectItem> getSecondaryTxnLimitTypeList() {
        return secondaryTxnLimitTypeList;
    }

    public void setSecondaryTxnLimitTypeList(List<SelectItem> secondaryTxnLimitTypeList) {
        this.secondaryTxnLimitTypeList = secondaryTxnLimitTypeList;
    }

    public String getSecondaryLimitAmount() {
        return secondaryLimitAmount;
    }

    public void setSecondaryLimitAmount(String secondaryLimitAmount) {
        this.secondaryLimitAmount = secondaryLimitAmount;
    }

    public String getSecondaryLimitCount() {
        return secondaryLimitCount;
    }

    public void setSecondaryLimitCount(String secondaryLimitCount) {
        this.secondaryLimitCount = secondaryLimitCount;
    }

    public List<AtmSecondryAccountDetail> getSecondaryTable() {
        return secondaryTable;
    }

    public void setSecondaryTable(List<AtmSecondryAccountDetail> secondaryTable) {
        this.secondaryTable = secondaryTable;
    }

    public AtmSecondryAccountDetail getSecondaryCurrentItem() {
        return secondaryCurrentItem;
    }

    public void setSecondaryCurrentItem(AtmSecondryAccountDetail secondaryCurrentItem) {
        this.secondaryCurrentItem = secondaryCurrentItem;
    }

    public List<SelectItem> getCardStatusList() {
        return cardStatusList;
    }

    public void setCardStatusList(List<SelectItem> cardStatusList) {
        this.cardStatusList = cardStatusList;
    }

    public String getNewSan() {
        return newSan;
    }

    public void setNewSan(String newSan) {
        this.newSan = newSan;
    }

    public String getSecondFileDataDisplay() {
        return secondFileDataDisplay;
    }

    public void setSecondFileDataDisplay(String secondFileDataDisplay) {
        this.secondFileDataDisplay = secondFileDataDisplay;
    }

    public String getPresentCardStatus() {
        return presentCardStatus;
    }

    public void setPresentCardStatus(String presentCardStatus) {
        this.presentCardStatus = presentCardStatus;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getPrEncodingName() {
        return prEncodingName;
    }

    public void setPrEncodingName(String prEncodingName) {
        this.prEncodingName = prEncodingName;
    }

    public String getPrEmbossingName() {
        return prEmbossingName;
    }

    public void setPrEmbossingName(String prEmbossingName) {
        this.prEmbossingName = prEmbossingName;
    }

    public String getChgEncodingName() {
        return chgEncodingName;
    }

    public void setChgEncodingName(String chgEncodingName) {
        this.chgEncodingName = chgEncodingName;
    }

    public String getChgEmbossingName() {
        return chgEmbossingName;
    }

    public void setChgEmbossingName(String chgEmbossingName) {
        this.chgEmbossingName = chgEmbossingName;
    }

    public String getFourthFileDataDisplay() {
        return fourthFileDataDisplay;
    }

    public void setFourthFileDataDisplay(String fourthFileDataDisplay) {
        this.fourthFileDataDisplay = fourthFileDataDisplay;
    }

    public boolean isSelectRender() {
        return selectRender;
    }

    public void setSelectRender(boolean selectRender) {
        this.selectRender = selectRender;
    }

    public String getWdrlLimitAmount() {
        return wdrlLimitAmount;
    }

    public void setWdrlLimitAmount(String wdrlLimitAmount) {
        this.wdrlLimitAmount = wdrlLimitAmount;
    }

    public String getWdrlLimitCount() {
        return wdrlLimitCount;
    }

    public void setWdrlLimitCount(String wdrlLimitCount) {
        this.wdrlLimitCount = wdrlLimitCount;
    }

    public String getPurLimitAmount() {
        return purLimitAmount;
    }

    public void setPurLimitAmount(String purLimitAmount) {
        this.purLimitAmount = purLimitAmount;
    }

    public String getPurLimitCount() {
        return purLimitCount;
    }

    public void setPurLimitCount(String purLimitCount) {
        this.purLimitCount = purLimitCount;
    }

    public String getSecondaryWdrlLimitAmount() {
        return secondaryWdrlLimitAmount;
    }

    public void setSecondaryWdrlLimitAmount(String secondaryWdrlLimitAmount) {
        this.secondaryWdrlLimitAmount = secondaryWdrlLimitAmount;
    }

    public String getSecondaryWdrlLimitCount() {
        return secondaryWdrlLimitCount;
    }

    public void setSecondaryWdrlLimitCount(String secondaryWdrlLimitCount) {
        this.secondaryWdrlLimitCount = secondaryWdrlLimitCount;
    }

    public String getSecondaryPurLimitAmount() {
        return secondaryPurLimitAmount;
    }

    public void setSecondaryPurLimitAmount(String secondaryPurLimitAmount) {
        this.secondaryPurLimitAmount = secondaryPurLimitAmount;
    }

    public String getSecondaryPurLimitCount() {
        return secondaryPurLimitCount;
    }

    public void setSecondaryPurLimitCount(String secondaryPurLimitCount) {
        this.secondaryPurLimitCount = secondaryPurLimitCount;
    }

    public String getMinLimit() {
        return minLimit;
    }

    public void setMinLimit(String minLimit) {
        this.minLimit = minLimit;
    }

    public AtmCardMappGrid getVerifyCurrentItem() {
        return verifyCurrentItem;
    }

    public void setVerifyCurrentItem(AtmCardMappGrid verifyCurrentItem) {
        this.verifyCurrentItem = verifyCurrentItem;
    }

    public List<AtmCardMappGrid> getVerifyTable() {
        return verifyTable;
    }

    public void setVerifyTable(List<AtmCardMappGrid> verifyTable) {
        this.verifyTable = verifyTable;
    }

    public String getFifthPanelDisplay() {
        return fifthPanelDisplay;
    }

    public void setFifthPanelDisplay(String fifthPanelDisplay) {
        this.fifthPanelDisplay = fifthPanelDisplay;
    }

    public String getAcdisplay() {
        return acdisplay;
    }

    public void setAcdisplay(String acdisplay) {
        this.acdisplay = acdisplay;
    }

    public boolean isDeactiveRender() {
        return deactiveRender;
    }

    public void setDeactiveRender(boolean deactiveRender) {
        this.deactiveRender = deactiveRender;
    }

    public AtmCardMappGrid getDeactiveCurrentItem() {
        return deactiveCurrentItem;
    }

    public void setDeactiveCurrentItem(AtmCardMappGrid deactiveCurrentItem) {
        this.deactiveCurrentItem = deactiveCurrentItem;
    }

    public List<AtmCardMappGrid> getDeactivateTable() {
        return deactivateTable;
    }

    public void setDeactivateTable(List<AtmCardMappGrid> deactivateTable) {
        this.deactivateTable = deactivateTable;
    }

    public String getSixthPanelDisplay() {
        return sixthPanelDisplay;
    }

    public void setSixthPanelDisplay(String sixthPanelDisplay) {
        this.sixthPanelDisplay = sixthPanelDisplay;
    }

    public String getAcno1() {
        return acno1;
    }

    public void setAcno1(String acno1) {
        this.acno1 = acno1;
    }

    public String getCard1() {
        return card1;
    }

    public void setCard1(String card1) {
        this.card1 = card1;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getEncoding1() {
        return encoding1;
    }

    public void setEncoding1(String encoding1) {
        this.encoding1 = encoding1;
    }

    public String getMinlmt1() {
        return minlmt1;
    }

    public void setMinlmt1(String minlmt1) {
        this.minlmt1 = minlmt1;
    }

    public String getWidlmtamt1() {
        return widlmtamt1;
    }

    public void setWidlmtamt1(String widlmtamt1) {
        this.widlmtamt1 = widlmtamt1;
    }

    public String getWidlmtcount1() {
        return widlmtcount1;
    }

    public void setWidlmtcount1(String widlmtcount1) {
        this.widlmtcount1 = widlmtcount1;
    }

    public String getPurlmtcount1() {
        return purlmtcount1;
    }

    public void setPurlmtcount1(String purlmtcount1) {
        this.purlmtcount1 = purlmtcount1;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public boolean isVerifyPanelViewFlag() {
        return verifyPanelViewFlag;
    }

    public void setVerifyPanelViewFlag(boolean verifyPanelViewFlag) {
        this.verifyPanelViewFlag = verifyPanelViewFlag;
    }

    public String getPurlmtamt1() {
        return purlmtamt1;
    }

    public void setPurlmtamt1(String purlmtamt1) {
        this.purlmtamt1 = purlmtamt1;
    }

    public String getCardStatus1() {
        return cardStatus1;
    }

    public void setCardStatus1(String cardStatus1) {
        this.cardStatus1 = cardStatus1;
    }

    public String getTxnLimitType1() {
        return txnLimitType1;
    }

    public void setTxnLimitType1(String txnLimitType1) {
        this.txnLimitType1 = txnLimitType1;
    }

    public List<AtmSecondryAccountDetail> getSecondaryViewTable() {
        return secondaryViewTable;
    }

    public void setSecondaryViewTable(List<AtmSecondryAccountDetail> secondaryViewTable) {
        this.secondaryViewTable = secondaryViewTable;
    }

    public String getCustid1() {
        return custid1;
    }

    public void setCustid1(String custid1) {
        this.custid1 = custid1;
    }

    public String getGender1() {
        return gender1;
    }

    public void setGender1(String gender1) {
        this.gender1 = gender1;
    }

    public String getEmbossing1() {
        return embossing1;
    }

    public void setEmbossing1(String embossing1) {
        this.embossing1 = embossing1;
    }

    public boolean isSelectUpdateRender() {
        return selectUpdateRender;
    }

    public void setSelectUpdateRender(boolean selectUpdateRender) {
        this.selectUpdateRender = selectUpdateRender;
    }

}
