/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.clg;

import com.cbs.constant.CbsConstant;
import com.cbs.constant.NpciReturnConstant;
import com.cbs.dto.TxnDetailBean;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.clg.CtsManagementFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.facade.txn.TxnAuthorizationManagementFacadeRemote;
import com.cbs.web.pojo.clg.UnAuthItem;
import com.cbs.utils.Base64;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ParseFileUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Authorization extends BaseBean {

    Date dt = new Date();
    ServletContext serContext;
    private List<SelectItem> batchOption;
    private String reasonType;
    private List<SelectItem> reasonOption;
    private String branchName;
    private List<UnAuthItem> dataItemList;
    private UnAuthItem currentItem;
    private int currentRow;
    private Integer batchNo;
    private String returnReason;
    private boolean postValue;
    private boolean reasonEnable;
    private boolean otherReasonEnable;
    private boolean completeValue;
    private boolean batchValue;
    private boolean othReasonflag;
    private String msg;
    private String imageData;
    private String gridAcno;
    private String sigData;
    private String folderName;
    private String txnType;
    private boolean accViewFlag = false;
    private List<TxnDetailBean> txnDetailList;
    private List<TxnDetailBean> txnDetailUnAuthList;
    private BigDecimal presentBalance;
    private BigDecimal openBalance;
    private TxnDetailBean txnDetailGrid;
    private TxnDetailBean txnDetailUnAuthGrid;
    private List<SelectItem> branchNameList;
    private List<SelectItem> txnTypeList;
    private final String jndiHomeName = "TransactionManagementFacade";
    private TransactionManagementFacadeRemote txnRemote = null;
    private final String jndiHomeNameCts = "CtsManagementFacade";
    private CtsManagementFacadeRemote ctsTxnRemote = null;
    private final String jndiTxnHomeName = "TxnAuthorizationManagementFacade";
    private TxnAuthorizationManagementFacadeRemote txnAuthRemote = null;
    private final String jndiCommonReportHome = "CommonReportMethods";
    private CommonReportMethodsRemote reportRemote = null;
    String accNo;
    String accountName;
    String opMode;
    String openDate;
    String jtName;
    String accInstruction;
    private String custId;
    private String riskCategorization;
    private String annualTurnover;
    private String custPanNo;
    private String profession;
    private String dpLimit;
    private String scheduleNo;
    private List<SelectItem> scheduleNoList;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private boolean odBalFlag;
    private String confirmationMsg;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private int ctsSponsor = 0;
    private String clgType;
    private String otherReason;
    private List<SelectItem> clgTypeList;
    private String npciCtsDisplayFlag = "none";
    private String normalCtsDisplayFlag = "none";
    private String displayStatus;
    private List<SelectItem> displayStatusList;
    private boolean docTypeFlag;
    private static final Object LOCK = new Object();

    public Authorization() {
        try {
            ctsTxnRemote = (CtsManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameCts);
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            txnAuthRemote = (TxnAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiTxnHomeName);
            reportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiCommonReportHome);
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            ctsSponsor = ftsRemote.getCodeForReportName("CTS-SPONSOR");
            getOnLoadData();
            postValue = true;
            reasonEnable = true;
            otherReasonEnable = true;
            accViewFlag = false;
            this.setGridAcno("");
            this.completeValue = true;
            scheduleNoList = new ArrayList<SelectItem>();
            setOdBalFlag(false);
            setConfirmationMsg("");
        } catch (Exception e) {
            this.setMsg(e.getLocalizedMessage());
        }
    }

    public HttpServletRequest getRquest() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return req;
    }

    public ServletContext getWebSerContext() {
        ServletContext ser = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        if (ser == null) {
            throw new RuntimeException("Sorry. Got a null context from faces context");
        }
        return ser;
    }

    public void fetchCurrentRow(ActionEvent event) {
        String varStatus = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("status"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (UnAuthItem item : dataItemList) {
            if (item.getVarStatus().equalsIgnoreCase(varStatus)) {
                currentItem = item;
            }
        }
    }

    public void getOnLoadData() {
        try {
            //Setting Bank Code
            clgTypeList = new ArrayList<SelectItem>();
            //Setting the Branch Name
            branchNameList = new ArrayList<SelectItem>();
            String loginAlphaCode = reportRemote.getAlphacodeByBrncode(getOrgnBrCode());

            if (loginAlphaCode.equalsIgnoreCase("HO") || loginAlphaCode.equalsIgnoreCase("CELL")) {
                List alphaList = reportRemote.getAlphacodeExcludingHo();
                for (int i = 0; i < alphaList.size(); i++) {
                    Vector element = (Vector) alphaList.get(i);
                    branchNameList.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
                }
                if (ctsSponsor == 2 || ctsSponsor == 3) {
                    branchNameList.add(new SelectItem(reportRemote.getBrncodeByAlphacode("HO"), "HO"));
                }
            } else {
//                loginAlphaCode = reportRemote.getAlphacodeByBrncode(getOrgnBrCode());
                branchNameList.add(new SelectItem(getOrgnBrCode(), loginAlphaCode));
            }

            //Setting the Return Reason
            List reasonList = new ArrayList();
            reasonList = ctsTxnRemote.getReason();

            reasonOption = new ArrayList<SelectItem>();
            reasonOption.add(new SelectItem("--Select--"));
            for (int i = 0; i < reasonList.size(); i++) {
                Vector element = (Vector) reasonList.get(i);
                reasonOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }

            txnTypeList = new ArrayList<SelectItem>();
            txnTypeList.add(new SelectItem("A", "Authorization"));
            txnTypeList.add(new SelectItem("C", "Completion"));

            displayStatusList = new ArrayList<SelectItem>();
            displayStatusList.add(new SelectItem("1", "ENTERED"));
            displayStatusList.add(new SelectItem("3", "HOLD"));
            displayStatusList.add(new SelectItem("9", "ALL")); //Just For ALL

            npciCtsDisplayFlag = "none";
            normalCtsDisplayFlag = "";
            //For Khatri
            if (ctsSponsor == 2 || ctsSponsor == 3) {
                clgTypeList = new ArrayList<SelectItem>();
                List clgList = reportRemote.getRefRecList("016");
                if (clgList.isEmpty()) {
                    this.setMsg("Please define clearing type in Cbs Ref Rec Type.");
                    return;
                }
                for (int x = 0; x < clgList.size(); x++) {
                    Vector clgVec = (Vector) clgList.get(x);
                    clgTypeList.add(new SelectItem(clgVec.get(0).toString(), clgVec.get(1).toString()));
                }
                npciCtsDisplayFlag = "";
                normalCtsDisplayFlag = "none";
            }
            this.setDocTypeFlag(false);
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public void dataInGrid() {
        try {
            if (this.branchName == null || this.branchName.equals("")) {
                this.setMsg("Please Select Branch Name.");
                return;
            }
            if (ctsSponsor == 2 || ctsSponsor == 3) {
                if (this.clgType == null || this.clgType.equals("")) {
                    this.setMsg("Please Select Clearing Type");
                    return;
                }
                if (this.displayStatus == null || this.displayStatus.equals("")) {
                    this.setMsg("Please Select Status Filter");
                    return;
                }
            }
            if (this.txnType == null || this.txnType.equals("")) {
                this.setMsg("Please Select Txn. Type.");
                return;
            }
            if (this.scheduleNo == null || this.scheduleNo.equals("")) {
                this.setMsg("Please Select Schedule No.");
                return;
            }
            if (this.txnType.equals("C")) {
                String loginAlphaCode = reportRemote.getAlphacodeByBrncode(getOrgnBrCode());
                if (loginAlphaCode.equalsIgnoreCase("HO") || loginAlphaCode.equalsIgnoreCase("CELL")) {
                    completeValue = true;
                    return;
                }
                getCompletionDetail();
            } else if (this.txnType.equals("A")) {
                dataItemList = new ArrayList<UnAuthItem>();
                String emFlag = "", dataStatus = "9";
                if (ctsSponsor == 2 || ctsSponsor == 3) {
                    emFlag = clgType;
                    dataStatus = displayStatus;
                }

                List unAuthList = ctsTxnRemote.getGridDetailsCts(Integer.parseInt(getBranchName()), Integer.parseInt(this.scheduleNo), ctsSponsor, emFlag, Integer.parseInt(dataStatus));
                for (int i = 0; i < unAuthList.size(); i++) {
                    Vector element = (Vector) unAuthList.get(i);
                    currentItem = new UnAuthItem();
                    currentItem.setsNo(i + 1);
                    currentItem.setBatchNo(Integer.parseInt(element.get(0).toString()));
                    currentItem.setVchNo(Integer.parseInt(element.get(1).toString()));
                    currentItem.setAcNo(element.get(2).toString());
                    currentItem.setInstAmt(Double.parseDouble(element.get(3).toString()));
                    currentItem.setInstNo(element.get(4).toString());
                    currentItem.setInstDt(element.get(5).toString());
                    currentItem.setPrBankCode(element.get(6).toString());
                    currentItem.setStatus(Integer.parseInt(element.get(7).toString()));
                    if (currentItem.getStatus() == 1) {
                        currentItem.setVarStatus("ENTERED");
                    } else if (currentItem.getStatus() == 2) {
                        currentItem.setVarStatus("PASS");
                    } else if (currentItem.getStatus() == 3) {
                        currentItem.setVarStatus("HOLD");
                    } else if (currentItem.getStatus() == 4) {
                        currentItem.setVarStatus("RETURN");
                    } else if (currentItem.getStatus() == 5) {
                        currentItem.setVarStatus("DELETE");
                    }
                    currentItem.setOperMode(element.get(8).toString());
                    currentItem.setEnterBy(element.get(9).toString());
                    currentItem.setDetails(element.get(10).toString());
                    currentItem.setPayee(element.get(11).toString());
                    currentItem.setBankName(element.get(12).toString());
                    currentItem.setBankAddress(element.get(13).toString());
                    currentItem.setTxnId(element.get(14).toString());
                    currentItem.setOrgnCode(element.get(15).toString());
                    currentItem.setDestCode(element.get(16).toString());
                    currentItem.setSeqNo(Float.parseFloat(element.get(17).toString()));
                    currentItem.setAuthBy(element.get(18).toString());
                    currentItem.setPayBy(Integer.parseInt(element.get(19).toString()));
                    currentItem.setTy(Integer.parseInt(element.get(20).toString()));
                    currentItem.setScreenFlag(element.get(21).toString());
                    currentItem.setSecondAuthBy(element.get(22).toString());
                    currentItem.setCircleType(element.get(23).toString());
                    currentItem.setReasonForCancel(Integer.parseInt(element.get(24).toString()));
                    currentItem.setTranTime(element.get(25).toString());
                    currentItem.setDt(element.get(26).toString());
                    currentItem.setTranType(Integer.parseInt(element.get(27).toString()));
                    currentItem.setSubStatus(element.get(28).toString());
                    currentItem.setRequestType(Integer.parseInt(element.get(29).toString()));
                    currentItem.setImageCode(element.get(30).toString());
                    currentItem.setExceedFlag("");
                    currentItem.setCustState(element.get(31).toString());
                    currentItem.setBranchState(element.get(32).toString());
                    currentItem.setDocType(element.get(33).toString());
                    dataItemList.add(currentItem);
                }
            }
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public void authorizeAction(String docAlert) {
        try {
            for (int i = 0; i < dataItemList.size(); i++) {
                UnAuthItem item = dataItemList.get(i);
                if (item.getVarStatus().equals("PASS") || item.getVarStatus().equals("RETURN") || item.getVarStatus().equals("DELETE")) {
                    this.docTypeFlag = false;
                    if ((ctsSponsor == 2 || ctsSponsor == 3) && item.getDocType().equalsIgnoreCase("C") && docAlert.equals("")) { //Only for khattri - 2, Ramgariya - 3
                        this.docTypeFlag = true;
                        return;
                    }

                    String userDetails = null, otherReasonText = "";
                    Integer authStatus = null;
                    if (item.getVarStatus().equals("PASS")) {
                        authStatus = 2;
                        userDetails = "CLEARED";
                    } else if (item.getVarStatus().equals("RETURN")) {
                        if (this.reasonType == null || this.reasonType.equalsIgnoreCase("--Select--")) {
                            this.setMsg("Please select reason");
                            return;
                        }
                        authStatus = 4;
                        userDetails = this.reasonType;
                        if (this.reasonType.equalsIgnoreCase(NpciReturnConstant.NACH_CLEARING_RETURN_88.getCode())) { //For other reason
                            String valOthReasonResult = validateOtherReasonText(this.otherReason);
                            if (!valOthReasonResult.equalsIgnoreCase("true")) {
                                this.setMsg(valOthReasonResult);
                                return;
                            }
                            otherReasonText = this.otherReason;
                        }
                    } else if (item.getVarStatus().equals("DELETE")) {
                        authStatus = 5;
                    }
                    System.out.println("Execced Flag Is-->" + item.getExceedFlag());
                    String finalMsg = "";
                    synchronized (LOCK) {
                        finalMsg = ctsTxnRemote.chequesAuthorization(item.getAcNo(), item.getEnterBy(), item.getOrgnCode(),
                                item.getDestCode(), item.getDetails(), item.getSecondAuthBy(), getUserName(), item.getInstNo(),
                                item.getScreenFlag(), item.getPayee(), item.getBankName(), item.getBankAddress(), authStatus,
                                item.getSeqNo(), item.getPayBy(), item.getReasonForCancel(), item.getVchNo(), item.getBatchNo(),
                                item.getTxnId(), item.getInstAmt(), item.getDt(), item.getInstDt(), item.getTranType(),
                                item.getSubStatus(), item.getRequestType(), item.getCircleType(), item.getTy(),
                                item.getDetails(), userDetails, item.getExceedFlag(), ctsSponsor, otherReasonText, item.getCustState(), item.getBranchState());
                    }

                    if (finalMsg.equalsIgnoreCase("The cheque has been passed successfuly")
                            || finalMsg.equalsIgnoreCase("The cheque has been returned successfuly")
                            || finalMsg.equalsIgnoreCase("The cheque has been deleted successfuly")
                            || finalMsg.equalsIgnoreCase("Charges Entered in PendingCharges due to Insufficient Fund!")) {
//                        dataItemList.remove(currentRow);
                        dataItemList.remove(item);
                        this.setReasonType("--Select--");
                        this.setOtherReason("");
                        postValue = true;
                        reasonEnable = true;
                        otherReasonEnable = true;
                        this.setMsg(finalMsg);
                    } else {
                        dataInGrid();
                        this.setMsg(finalMsg);
                        this.setReasonType("--Select--");
                        this.setOtherReason("");
                        postValue = true;
                        reasonEnable = true;
                        otherReasonEnable = true;
                    }
                }
            }
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

//    public void authorizeAction(String docAlert) {
//        try {
//            UnAuthItem item = dataItemList.get(currentRow);
//            if (item.getVarStatus().equals("PASS") || item.getVarStatus().equals("RETURN") || item.getVarStatus().equals("DELETE")) {
//                this.docTypeFlag = false;
//                if (ctsSponsor == 2 && item.getDocType().equalsIgnoreCase("C") && docAlert.equals("")) { //Only for khattri
//                    this.docTypeFlag = true;
//                    return;
//                }
//
//                String userDetails = null, otherReasonText = "";
//                Integer authStatus = null;
//                if (item.getVarStatus().equals("PASS")) {
//                    authStatus = 2;
//                    userDetails = "CLEARED";
//                } else if (item.getVarStatus().equals("RETURN")) {
//                    if (this.reasonType.equalsIgnoreCase("--Select--")) {
//                        this.setMsg("Please select reason");
//                        return;
//                    }
//                    authStatus = 4;
//                    userDetails = this.reasonType;
//                    if (this.reasonType.equalsIgnoreCase(NpciReturnConstant.NACH_CLEARING_RETURN_88.getCode())) { //For other reason
//                        String valOthReasonResult = validateOtherReasonText(this.otherReason);
//                        if (!valOthReasonResult.equalsIgnoreCase("true")) {
//                            this.setMsg(valOthReasonResult);
//                            return;
//                        }
//                        otherReasonText = this.otherReason;
//                    }
//                } else if (item.getVarStatus().equals("DELETE")) {
//                    authStatus = 5;
//                }
//                System.out.println("Execced Flag Is-->" + item.getExceedFlag());
//                String finalMsg = "";
//                synchronized (LOCK) {
//                    finalMsg = ctsTxnRemote.chequesAuthorization(item.getAcNo(), item.getEnterBy(), item.getOrgnCode(),
//                            item.getDestCode(), item.getDetails(), item.getSecondAuthBy(), getUserName(), item.getInstNo(),
//                            item.getScreenFlag(), item.getPayee(), item.getBankName(), item.getBankAddress(), authStatus,
//                            item.getSeqNo(), item.getPayBy(), item.getReasonForCancel(), item.getVchNo(), item.getBatchNo(),
//                            item.getTxnId(), item.getInstAmt(), item.getDt(), item.getInstDt(), item.getTranType(),
//                            item.getSubStatus(), item.getRequestType(), item.getCircleType(), item.getTy(),
//                            item.getDetails(), userDetails, item.getExceedFlag(), ctsSponsor, otherReasonText, item.getCustState(), item.getBranchState());
//                }
//
//                if (finalMsg.equalsIgnoreCase("The cheque has been passed successfuly")
//                        || finalMsg.equalsIgnoreCase("The cheque has been returned successfuly")
//                        || finalMsg.equalsIgnoreCase("The cheque has been deleted successfuly")
//                        || finalMsg.equalsIgnoreCase("Charges Entered in PendingCharges due to Insufficient Fund!")) {
//                    dataItemList.remove(currentRow);
//                    this.setReasonType("--Select--");
//                    this.setOtherReason("");
//                    postValue = true;
//                    reasonEnable = true;
//                    otherReasonEnable = true;
//                    this.setMsg(finalMsg);
//                } else {
//                    dataInGrid();
//                    this.setMsg(finalMsg);
//                    this.setReasonType("--Select--");
//                    this.setOtherReason("");
//                    postValue = true;
//                    reasonEnable = true;
//                    otherReasonEnable = true;
//                }
//            }
//        } catch (Exception e) {
//            this.setMsg(e.getMessage());
//        }
//    }
    public void retrieveScheduleNo() {
        this.setMsg("");
        scheduleNoList = new ArrayList<SelectItem>();
        try {
            if (!(ctsSponsor == 2 || ctsSponsor == 3)) { //Not for NPCI CTS (It is for CCBL/AMMCO)
                if (this.branchName == null || this.branchName.equals("")) {
                    this.setMsg("Please Select Branch Name");
                    return;
                }
                List list = ctsTxnRemote.getTransactionScheduleNos(ymd.format(dmy.parse(getTodayDate())),
                        Integer.parseInt(this.branchName), "", ctsSponsor);
                if (list == null || list.isEmpty()) {
                    this.setMsg("There is no schedule no for this branch.");
                    return;
                }
                for (int i = 0; i < list.size(); i++) {
                    Vector element = (Vector) list.get(i);
                    scheduleNoList.add(new SelectItem(element.get(0)));
                }
            }
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public void retrieveCtsScheduleNo() {
        this.setMsg("");
        scheduleNoList = new ArrayList<SelectItem>();
        try {
            if (this.branchName == null || this.branchName.equals("")) {
                this.setMsg("Please Select Branch Name");
                return;
            }
            if (clgType == null || clgType.equals("")) {
                this.setMsg("Please Select Clearing Type");
                return;
            }
            List list = ctsTxnRemote.getTransactionScheduleNos(ymd.format(dmy.parse(getTodayDate())),
                    Integer.parseInt(this.branchName), this.clgType, ctsSponsor);
            if (list == null || list.isEmpty()) {
                this.setMsg("There is no schedule no for this branch.");
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                Vector element = (Vector) list.get(i);
                scheduleNoList.add(new SelectItem(element.get(0)));
            }
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public void actionToCompleteCTS() {
        FileInputStream fin = null;
        try {
            if (this.txnType.equalsIgnoreCase("C")) {
                String total = ctsTxnRemote.getTotalChequeDetailCts(Integer.parseInt(this.branchName),
                        Integer.parseInt(this.scheduleNo));
                if (total.equalsIgnoreCase("true")) {
                    String compMsg = ctsTxnRemote.completionCts(Integer.parseInt(this.branchName),
                            Integer.parseInt(this.scheduleNo), getUserName());
                    if (compMsg.equalsIgnoreCase("true")) {
                        String moduleName = ctsTxnRemote.getModuleActiveCode("CTS");
                        if (moduleName.equalsIgnoreCase("1")) {
                            String brFolderName = String.valueOf(Integer.parseInt(this.scheduleNo)) + "_" + (this.branchName.length() == 1 ? "0" + this.branchName : this.branchName);
                            serContext = getWebSerContext();
                            String directory = serContext.getInitParameter("cts");
                            //Addition of code to save image with details in database.
                            try {
                                File file = new File(directory + "/" + brFolderName + "/");
                                String[] files = file.list();

                                for (int i = 0; i < files.length; i++) {
                                    if (!(files[i].endsWith("Txt") || (files[i].endsWith("Pxf")))) {
                                        File imgFilePath = new File(directory + "/" + brFolderName + "/" + files[i]);
                                        fin = new FileInputStream(imgFilePath);
                                        byte[] bytes = new byte[(int) imgFilePath.length()];
                                        fin.read(bytes);
                                        String encyImgFile = Base64.encodeBytes(bytes);
                                        int dotIndex = files[i].indexOf(".");
                                        String imageCode = files[i].substring(0, dotIndex);
                                        String imageType = files[i].substring(dotIndex + 1);

                                        //Fetching details against that imagecode
                                        List imgDetails = ctsTxnRemote.getImageDetailsCts(imageCode, Integer.parseInt(this.branchName),
                                                Integer.parseInt(this.scheduleNo));
                                        if (imgDetails.size() > 0) {
                                            Vector element = (Vector) imgDetails.get(0);
                                            String acno = element.get(0).toString();
                                            String instNo = element.get(1).toString();
                                            Float instAmt = Float.parseFloat(element.get(2).toString());
                                            String instDt = element.get(3).toString();
                                            String favourOf = element.get(4).toString();
                                            String bankName = element.get(5).toString();
                                            String enterBy = element.get(6).toString();
                                            String authBy = element.get(7).toString();
                                            int status = Integer.parseInt(element.get(8).toString());
                                            Float seqNo = Float.parseFloat(element.get(9).toString());
                                            String orgBranch = element.get(10).toString();
                                            String destBranch = element.get(11).toString();
                                            String clgDt = element.get(12).toString();
                                            String details = element.get(13).toString();
                                            String auth = element.get(14).toString();
                                            String substatus = element.get(15).toString();
                                            String custname = element.get(17).toString();
                                            String opermode = element.get(18).toString();
                                            String userdetails = element.get(19).toString();
                                            String prbankcode = element.get(20).toString();
                                            String rbirefno = element.get(21).toString();
                                            Integer scheduleNo = Integer.parseInt(element.get(22).toString());

                                            //Code to save the data
                                            ctsTxnRemote.makeImageBackup(imageType, imageCode, encyImgFile, acno, instNo, instAmt,
                                                    instDt, favourOf, bankName, enterBy, authBy, status, seqNo, orgBranch,
                                                    destBranch, clgDt, details, auth, substatus, custname, opermode, userdetails,
                                                    prbankcode, rbirefno, scheduleNo);
                                        }
                                    }
                                }
                            } catch (ApplicationException e) {
                                this.setMsg(e.getMessage());
                            } catch (Exception e) {
                                this.setMsg(e.getMessage());
                            } finally {
                                fin.close();
                            }
                            this.setMsg("Branch CTS has been completed");
                            completeValue = true;
                        } else {
                            this.setMsg("Inward process has been completed");
                            completeValue = true;
                        }
                    } else {
                        this.setMsg(compMsg);
                    }
                } else if (total.equalsIgnoreCase("false")) {
                    this.setMsg("There are some pending entries for this branch");
                } else {
                    this.setMsg(total);
                }
            } else {
                this.setMsg("Please select proper Txn.Type.");
                return;
            }
        } catch (ApplicationException e) {
            this.setMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public void getCompletionDetail() {
        try {
            String total = ctsTxnRemote.getTotalChequeDetailCts(Integer.parseInt(this.branchName), Integer.parseInt(this.scheduleNo));
            if (total.equalsIgnoreCase("true")) {
                completeValue = false;
                this.setReasonType("--Select--");
                this.setOtherReason("");
                postValue = true;
                reasonEnable = true;
            } else if (total.equalsIgnoreCase("You have completed your CTS/Inward")) {
                completeValue = true;
                this.setReasonType("--Select--");
                this.setOtherReason("");
                postValue = true;
                reasonEnable = true;
                this.setMsg("You have completed your CTS/Inward");
            } else {
                completeValue = true;
            }
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public String changeStatus() {
        try {
            UnAuthItem item = dataItemList.get(currentRow);
            item.setExceedFlag("");

            boolean found = false;
            for (UnAuthItem item1 : dataItemList) {
                if ((item1.getVarStatus().equalsIgnoreCase("PASS")
                        || item1.getVarStatus().equalsIgnoreCase("RETURN")
                        || item1.getVarStatus().equalsIgnoreCase("DELETE"))
                        && !item.getTxnId().equalsIgnoreCase(item1.getTxnId())) {
                    found = true;
                }
            }
            if (found) {
                this.setMsg("Only one transction can be authorize at one time.");
                return null;
            }

            if (item.getVarStatus().equalsIgnoreCase("ENTERED")) {
                if (item.getEnterBy().equalsIgnoreCase(getUserName())) {
                    this.setMsg("You are not authorized person");
                    return null;
                }
//                boolean found = false;
//                for (UnAuthItem item1 : dataItemList) {
//                    if (item1.getVarStatus().equalsIgnoreCase("PASS") || item1.getVarStatus().equalsIgnoreCase("RETURN") || item1.getVarStatus().equalsIgnoreCase("DELETE")) {
//                        found = true;
//                    }
//                }
//                if (found) {
//                    this.setMsg("Only one transction can be authorize at one time.");
//                } else {
                if (ctsSponsor == 2 || ctsSponsor == 3) { //For Khatri- NPCI CTS
                    this.setFolderName(String.valueOf(Integer.parseInt(this.clgType)) + "_" + String.valueOf(Integer.parseInt(this.scheduleNo)) + "_" + getOrgnBrCode());
                } else {
                    getImageFolderName(Integer.parseInt(this.scheduleNo), this.branchName.length() == 1 ? "0" + this.branchName : this.branchName);
                }

                if (ctsSponsor == 1) {
                    this.setImageData(item.getImageCode() + "_Fb.jpg");
                } else if (ctsSponsor == 2 || ctsSponsor == 3) {
                    this.setImageData(item.getImageCode() + ".JPEG");
                } else {
                    this.setImageData(item.getImageCode() + ".GF");
                }

                this.setGridAcno(item.getAcNo());
                getSignatureDetail(item.getAcNo());
                item.setVarStatus("PASS");
                dataItemList.remove(currentRow);
                dataItemList.add(currentRow, item);
                this.setMsg("");
                postValue = false;
                reasonEnable = true;
                accViewFlag = true;

                //A/c And cheque validation
                if (item.getAcNo() != null && !item.getAcNo().trim().equals("")) {
                    String chqResult = "";
                    chqResult = ftsRemote.ftsAcnoValidate(item.getAcNo(), 1, "");
                    if (!chqResult.trim().equalsIgnoreCase("true")) {
                        this.setMsg(chqResult);
                        this.setReasonType("--Select--");
                        this.setOtherReason("");
                        postValue = true;
                        reasonEnable = true;
                        ctsTxnRemote.odBalStopUpdation(item.getTxnId(), getUserName(), chqResult);
                        dataInGrid();
                        return null;
                    } else {
                        chqResult = ftsRemote.chequeStatus(item.getAcNo(), item.getInstNo());
                        if (!chqResult.trim().equalsIgnoreCase("true")) {
                            this.setMsg(chqResult);
                            this.setReasonType("--Select--");
                            this.setOtherReason("");
                            postValue = true;
                            reasonEnable = true;
                            ctsTxnRemote.odBalStopUpdation(item.getTxnId(), getUserName(), chqResult);
                            dataInGrid();
                            return null;
                        }
                    }
                }
                //End Here

                this.odBalFlag = false;
                this.confirmationMsg = "";
                String chkBalResult = ftsRemote.chkBal(item.getAcNo(), 1, 0, ftsRemote.getAccountNature(item.getAcNo()));
                if (!chkBalResult.equalsIgnoreCase("true")) {
                    if (ftsRemote.getAccountNature(item.getAcNo()).equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        String chkBalance = ftsRemote.checkBalForOdLimit(item.getAcNo(), item.getInstAmt(), getUserName());
                        if (chkBalance.equalsIgnoreCase("99")) {
                            confirmationMsg = "Limit Exceede for : " + item.getAcNo() + ". \n To Proceed press Yes, \n To Cancel press No";
                            this.odBalFlag = true;
                            return null;
                        } else if (!chkBalance.equalsIgnoreCase("1")) {
                            this.odBalFlag = false;
                            this.confirmationMsg = "";
                            this.setMsg("Balance Exceeds.");

                            this.setReasonType("--Select--");
                            this.setOtherReason("");
                            postValue = true;
                            reasonEnable = true;

                            ctsTxnRemote.odBalStopUpdation(item.getTxnId(), getUserName(), "Balance Exceeds");
                            dataInGrid();
                            return null;
                        }
                    } else if (ftsRemote.getAccountNature(item.getAcNo()).equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                        String balResult = ftsRemote.checkBalance(item.getAcNo(), item.getInstAmt(), getUserName());
                        if (!balResult.equalsIgnoreCase("true")) {
                            this.odBalFlag = false;
                            this.confirmationMsg = "";
                            this.setMsg("Balance Exceeds.");

                            this.setReasonType("--Select--");
                            this.setOtherReason("");
                            postValue = true;
                            reasonEnable = true;

                            ctsTxnRemote.odBalStopUpdation(item.getTxnId(), getUserName(), "Balance Exceeds");
                            dataInGrid();
                            return null;
                        }
                    }
                }
//                }
            } else if (item.getVarStatus().equalsIgnoreCase("PASS")) {
                if (ctsSponsor == 2 || ctsSponsor == 3) { //For Khatri- NPCI CTS
                    this.setFolderName(String.valueOf(Integer.parseInt(this.clgType)) + "_" + String.valueOf(Integer.parseInt(this.scheduleNo)) + "_" + getOrgnBrCode());
                } else {
                    getImageFolderName(Integer.parseInt(this.scheduleNo), this.branchName.length() == 1 ? "0" + this.branchName : this.branchName);
                }
                if (ctsSponsor == 1) {
                    this.setImageData(item.getImageCode() + "_Fb.jpg");
                } else if (ctsSponsor == 2 || ctsSponsor == 3) {
                    this.setImageData(item.getImageCode() + ".JPEG");
                } else {
                    this.setImageData(item.getImageCode() + ".GF");
                }

                this.setGridAcno(item.getAcNo());
                getSignatureDetail(item.getAcNo());
                item.setVarStatus("RETURN");
                dataItemList.remove(currentRow);
                dataItemList.add(currentRow, item);
                this.setMsg("");
                postValue = false;
                reasonEnable = false;
                accViewFlag = false;
            } else if (item.getVarStatus().equalsIgnoreCase("RETURN")) {
                if (ctsSponsor == 2 || ctsSponsor == 3) { //For Khatri- NPCI CTS
                    this.setFolderName(String.valueOf(Integer.parseInt(this.clgType)) + "_" + String.valueOf(Integer.parseInt(this.scheduleNo)) + "_" + getOrgnBrCode());
                } else {
                    getImageFolderName(Integer.parseInt(this.scheduleNo), this.branchName.length() == 1 ? "0" + this.branchName : this.branchName);
                }
                if (ctsSponsor == 1) {
                    item.setVarStatus("DELETE");
                    this.setImageData(item.getImageCode() + "_Fb.jpg");
                } else if (ctsSponsor == 2) {
                    item.setVarStatus("ENTERED");
                    this.setImageData(item.getImageCode() + ".JPEG");
                } else if (ctsSponsor == 3) {
                    item.setVarStatus("DELETE");
                    this.setImageData(item.getImageCode() + ".JPEG");
                } else {
                    item.setVarStatus("DELETE");
                    this.setImageData(item.getImageCode() + ".GF");
                }
                this.setGridAcno(item.getAcNo());
                getSignatureDetail(item.getAcNo());
                dataItemList.remove(currentRow);
                dataItemList.add(currentRow, item);
                this.setMsg("");
                postValue = false;
                reasonEnable = true;
                accViewFlag = false;
            } else if (item.getVarStatus().equalsIgnoreCase("DELETE")) {
                item.setVarStatus("ENTERED");
                dataItemList.remove(currentRow);
                dataItemList.add(currentRow, item);
                this.setMsg("");
                postValue = true;
                reasonEnable = true;
                accViewFlag = false;
            } else if (item.getVarStatus().equalsIgnoreCase("HOLD")) {
                if (ctsSponsor == 2 || ctsSponsor == 3) { //For Khatri- NPCI CTS
                    this.setFolderName(String.valueOf(Integer.parseInt(this.clgType)) + "_" + String.valueOf(Integer.parseInt(this.scheduleNo)) + "_" + getOrgnBrCode());
                } else {
                    getImageFolderName(Integer.parseInt(this.scheduleNo), this.branchName.length() == 1 ? "0" + this.branchName : this.branchName);
                }
                if (ctsSponsor == 1) {
                    this.setImageData(item.getImageCode() + "_Fb.jpg");
                } else if (ctsSponsor == 2 || ctsSponsor == 3) {
                    this.setImageData(item.getImageCode() + ".JPEG");
                } else {
                    this.setImageData(item.getImageCode() + ".GF");
                }
                this.setGridAcno(item.getAcNo());
                getSignatureDetail(item.getAcNo());
                item.setVarStatus("RETURN");
                dataItemList.remove(currentRow);
                dataItemList.add(currentRow, item);
                this.setMsg("");
                postValue = false;
                reasonEnable = false;
                accViewFlag = false;
            }
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
        return null;
    }

    public void createSignature(OutputStream out, Object data) throws IOException {
        if (null == data) {
            return;
        }
        if (sigData != null) {
            byte[] sigBytes = Base64.decode(sigData);
            out.write(sigBytes);
        }
    }

    public void getSignatureDetail(String gridAcno) {
        try {
            String signature;
            sigData = "wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==";
            signature = txnAuthRemote.getSignatureDetails(gridAcno);
            if (!signature.equalsIgnoreCase("Signature not found")) {
                String imageCode = signature.trim();
                String directoryPath = CbsUtil.getSigFilePath(imageCode.substring(4), imageCode.substring(0, 4));
                String encryptAcno = CbsUtil.encryptText(gridAcno);
                String filePath = directoryPath + encryptAcno + ".xml";
                sigData = CbsUtil.readImageFromXMLFile(new File(filePath));
            }
        } catch (Exception e) {
            this.setMsg(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void getWhiteImage() {
        try {
            UnAuthItem item = dataItemList.get(currentRow);
            if (ctsSponsor == 2 || ctsSponsor == 3) { //For Khatri- NPCI CTS
                this.setFolderName(String.valueOf(Integer.parseInt(this.clgType)) + "_" + String.valueOf(Integer.parseInt(this.scheduleNo)) + "_" + getOrgnBrCode());
            } else {
                getImageFolderName(Integer.parseInt(this.scheduleNo), this.branchName.length() == 1 ? "0" + this.branchName : this.branchName);
            }
            if (ctsSponsor == 1) {
                this.setImageData(item.getImageCode() + "_Fb.jpg");
            } else if (ctsSponsor == 2 || ctsSponsor == 3) {
                this.setImageData(item.getImageCode() + ".JPEG");
            } else {
                this.setImageData(item.getImageCode() + ".jpg");
            }
            accViewFlag = false;
        } catch (Exception e) {
            this.setMsg(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void getBackImage() {
        try {
            UnAuthItem item = dataItemList.get(currentRow);
            if (ctsSponsor == 2 || ctsSponsor == 3) { //For Khatri- NPCI CTS
                this.setFolderName(String.valueOf(Integer.parseInt(this.clgType)) + "_" + String.valueOf(Integer.parseInt(this.scheduleNo)) + "_" + getOrgnBrCode());
            } else {
                getImageFolderName(Integer.parseInt(this.scheduleNo), this.branchName.length() == 1 ? "0" + this.branchName : this.branchName);
            }
            if (ctsSponsor == 1) {
                this.setImageData(item.getImageCode() + "_Ba.jpg");
            } else if (ctsSponsor == 2 || ctsSponsor == 3) {
                this.setImageData(item.getImageCode() + ".JPG");
            } else {
                this.setImageData(item.getImageCode() + ".png");
            }
            accViewFlag = false;
        } catch (Exception e) {
            this.setMsg(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void getGrayImage() {
        try {
            UnAuthItem item = dataItemList.get(currentRow);
            if (ctsSponsor == 2 || ctsSponsor == 3) { //For Khatri- NPCI CTS
                this.setFolderName(String.valueOf(Integer.parseInt(this.clgType)) + "_" + String.valueOf(Integer.parseInt(this.scheduleNo)) + "_" + getOrgnBrCode());
            } else {
                getImageFolderName(Integer.parseInt(this.scheduleNo), this.branchName.length() == 1 ? "0" + this.branchName : this.branchName);
            }
            if (ctsSponsor == 1) {
                this.setImageData(item.getImageCode() + "_Fg.jpg");
            } else if (ctsSponsor == 2 || ctsSponsor == 3) {
                this.setImageData(item.getImageCode() + ".PNG");
            } else {
                this.setImageData(item.getImageCode() + ".GF");
            }
            accViewFlag = false;
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public void getImageFolderName(Integer scheduleCount, String orgnBrCode) {
        try {
            this.setFolderName(scheduleCount.toString() + "_" + orgnBrCode);
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public String refreshForm() {
        this.setMsg("");
        this.setReasonType("--Select--");
        this.setOtherReason("");
        this.otherReasonEnable = true;
        getOnLoadData();
        reasonEnable = true;
        postValue = true;
        accViewFlag = false;
        this.setGridAcno("");
        dataItemList = new ArrayList<UnAuthItem>();
        scheduleNoList = new ArrayList<SelectItem>();
        this.setTxnType("A");
        setOdBalFlag(false);
        setConfirmationMsg("");
        this.setDocTypeFlag(false);
        return null;
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }

    public void acctViewAuthUnAuth() {
        try {
            txnDetailList = new ArrayList<TxnDetailBean>();
            txnDetailUnAuthList = new ArrayList<TxnDetailBean>();
            if ((gridAcno == null) || (gridAcno.equals(""))) {
                this.setMsg("Please double click on the row to view Account Detail");
                return;
            }

            this.setMsg("");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar now = Calendar.getInstance();
            now.roll(Calendar.MONTH, false);
            String oneMonthBeforeDate = sdf.format(now.getTime());

            String openBal = txnRemote.getOpeningBalF4(gridAcno, oneMonthBeforeDate);
            if (!openBal.equals("FALSE")) {
                this.setOpenBalance(new BigDecimal(openBal));
            }

            String prsntBal = txnRemote.getOpeningBalF4(gridAcno, getTodayDate());
            if (!prsntBal.equals("FALSE")) {

                this.setPresentBalance(new BigDecimal(prsntBal));
            }

            txnDetailList = txnRemote.accViewAuth(gridAcno, getTodayDate(), getOrgnBrCode());
            txnDetailUnAuthList = txnRemote.accViewUnauth(gridAcno, getTodayDate(), getOrgnBrCode());
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public void getSignatureDetail() {
        try {
            this.imageData = null;
            int Toper;
            String signature;
            accNo = currentItem.getAcNo();
            accountName = "";
            List list = txnAuthRemote.getDataForSignature(accNo);
            System.out.println("list    " + list);
            if (!list.isEmpty()) {
                Vector vec = (Vector) list.get(0);
                jtName = vec.get(0).toString() + " " + vec.get(1).toString() + " " + vec.get(2).toString() + " " + vec.get(3).toString();
                opMode = vec.get(5).toString();
                Toper = Integer.parseInt(opMode);
                if (Toper == 1) {
                    if ((Toper == 1 && !accNo.equalsIgnoreCase("CA")) && (Toper == 1 && !accNo.equalsIgnoreCase("CC"))) {
                        opMode = "Self";
                    } else {
                        opMode = "Self";
                    }
                } else {
                    List list2 = txnAuthRemote.selectForOperationMode(opMode);
                    Vector v2 = (Vector) list2.get(0);
                    this.setOpMode(v2.get(0).toString());

                }

                accInstruction = vec.get(4).toString();
                String accOpenDate = vec.get(6).toString().substring(6) + "/" + vec.get(6).toString().substring(4, 6) + "/" + vec.get(6).toString().substring(0, 4);
                openDate = accOpenDate;
                this.setDpLimit(vec.get(8).toString());
                this.setAccountName(vec.get(9).toString());
                List<String> custIdList = txnRemote.getCustIdByAccount(accNo);
                if (!custIdList.isEmpty()) {
                    this.setCustId(custIdList.get(0));
                    this.setAnnualTurnover(custIdList.get(1));
                    this.setRiskCategorization(custIdList.get(2));
                    this.setCustPanNo(custIdList.get(3));
                    this.setProfession(custIdList.get(4));
                } else {
                    this.setCustId("");
                    this.setAnnualTurnover("");
                    this.setRiskCategorization("");
                    this.setCustPanNo("");
                    this.setProfession("");
                }
            }
            signature = txnAuthRemote.getSignatureDetails(accNo);
            if (!signature.equalsIgnoreCase("Signature not found")) {
                String imageCode = signature.trim();
                String directoryPath = CbsUtil.getSigFilePath(imageCode.substring(4), imageCode.substring(0, 4));
                String encryptAcno = CbsUtil.encryptText(accNo);
                String filePath = directoryPath + encryptAcno + ".xml";
                sigData = CbsUtil.readImageFromXMLFile(new File(filePath));
            }
        } catch (ApplicationException e) {
            this.setMsg(e.getMessage());
        } catch (FileNotFoundException e) {
            this.setMsg("");
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public void odBalExceedStopProcess() {
        try {
            this.odBalFlag = false;
            this.confirmationMsg = "";
            this.setMsg("Balance Exceeds.");

            this.setReasonType("--Select--");
            this.setOtherReason("");
            postValue = true;
            reasonEnable = true;
            UnAuthItem item = dataItemList.get(currentRow);
            ctsTxnRemote.odBalStopUpdation(item.getTxnId(), getUserName(), "Balance Exceeds");
            dataInGrid();
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void setExceedTransaction() {
        try {
            for (UnAuthItem item : dataItemList) {
                if (item.getVarStatus().equalsIgnoreCase("PASS")) {
                    item.setExceedFlag("exceed");
                } else {
                    item.setExceedFlag("");
                }
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public String validateOtherReasonText(String otherReasonText) {
        try {
            if (otherReasonText.trim() == null || otherReasonText.trim().equals("")
                    || otherReasonText.trim().toLowerCase().contains("other reason")) {
                return "There must be value in Other Reason field.";
            }
            if (otherReasonText.trim().length() < 6 || otherReasonText.trim().length() > 25) {
                return "Other reason field must be in between 6-25 digit length.";
            }
            boolean valResult = ParseFileUtil.containsSpecialCharacter(otherReasonText);
            if (valResult == true) {
                return "Special characters are not allowed in other reason field.";
            }
            if (ParseFileUtil.isNumeric(otherReasonText)) {
                return "Only numeric values are not allowed in other reason field.";
            }
            if (ParseFileUtil.isNumeric(otherReasonText.trim().substring(0, 1))) {
                return "First digit in other reason field must not be numeric.";
            }
        } catch (Exception ex) {
            return "false " + ex.getMessage();
        }
        return "true";
    }

    public void blurOnReason() {
        this.otherReasonEnable = true;
        this.othReasonflag = false;
        try {
            this.setOtherReason("");
            if (this.reasonType.equalsIgnoreCase(NpciReturnConstant.NACH_CLEARING_RETURN_88.getCode())) {
                this.otherReasonEnable = false;
                this.othReasonflag = true;
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    public void docAlertProcess() {
        try {
            authorizeAction("docAlert");
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

    //Getter And Setter
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getOpMode() {
        return opMode;
    }

    public void setOpMode(String opMode) {
        this.opMode = opMode;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getJtName() {
        return jtName;
    }

    public void setJtName(String jtName) {
        this.jtName = jtName;
    }

    public String getAccInstruction() {
        return accInstruction;
    }

    public void setAccInstruction(String accInstruction) {
        this.accInstruction = accInstruction;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getRiskCategorization() {
        return riskCategorization;
    }

    public void setRiskCategorization(String riskCategorization) {
        this.riskCategorization = riskCategorization;
    }

    public String getAnnualTurnover() {
        return annualTurnover;
    }

    public void setAnnualTurnover(String annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    public String getCustPanNo() {
        return custPanNo;
    }

    public void setCustPanNo(String custPanNo) {
        this.custPanNo = custPanNo;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getDpLimit() {
        return dpLimit;
    }

    public void setDpLimit(String dpLimit) {
        this.dpLimit = dpLimit;
    }

    public TxnDetailBean getTxnDetailUnAuthGrid() {
        return txnDetailUnAuthGrid;
    }

    public void setTxnDetailUnAuthGrid(TxnDetailBean txnDetailUnAuthGrid) {
        this.txnDetailUnAuthGrid = txnDetailUnAuthGrid;
    }

    public TxnDetailBean getTxnDetailGrid() {
        return txnDetailGrid;
    }

    public void setTxnDetailGrid(TxnDetailBean txnDetailGrid) {
        this.txnDetailGrid = txnDetailGrid;
    }

    public BigDecimal getOpenBalance() {
        return openBalance;
    }

    public void setOpenBalance(BigDecimal openBalance) {
        this.openBalance = openBalance;
    }

    public List<TxnDetailBean> getTxnDetailList() {
        return txnDetailList;
    }

    public void setTxnDetailList(List<TxnDetailBean> txnDetailList) {
        this.txnDetailList = txnDetailList;
    }

    public List<TxnDetailBean> getTxnDetailUnAuthList() {
        return txnDetailUnAuthList;
    }

    public void setTxnDetailUnAuthList(List<TxnDetailBean> txnDetailUnAuthList) {
        this.txnDetailUnAuthList = txnDetailUnAuthList;
    }

    public BigDecimal getPresentBalance() {
        return presentBalance;
    }

    public void setPresentBalance(BigDecimal presentBalance) {
        this.presentBalance = presentBalance;
    }

    public boolean isAccViewFlag() {
        return accViewFlag;
    }

    public void setAccViewFlag(boolean accViewFlag) {
        this.accViewFlag = accViewFlag;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public boolean isBatchValue() {
        return batchValue;
    }

    public void setBatchValue(boolean batchValue) {
        this.batchValue = batchValue;
    }

    public boolean isCompleteValue() {
        return completeValue;
    }

    public void setCompleteValue(boolean completeValue) {
        this.completeValue = completeValue;
    }

    public String getSigData() {
        return sigData;
    }

    public void setSigData(String sigData) {
        this.sigData = sigData;
    }

    public String getGridAcno() {
        return gridAcno;
    }

    public void setGridAcno(String gridAcno) {
        this.gridAcno = gridAcno;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public boolean isReasonEnable() {
        return reasonEnable;
    }

    public void setReasonEnable(boolean reasonEnable) {
        this.reasonEnable = reasonEnable;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isPostValue() {
        return postValue;
    }

    public void setPostValue(boolean postValue) {
        this.postValue = postValue;
    }

    public Integer getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(Integer batchNo) {
        this.batchNo = batchNo;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public UnAuthItem getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(UnAuthItem currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<UnAuthItem> getDataItemList() {
        return dataItemList;
    }

    public void setDataItemList(List<UnAuthItem> dataItemList) {
        this.dataItemList = dataItemList;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public List<SelectItem> getBatchOption() {
        return batchOption;
    }

    public void setBatchOption(List<SelectItem> batchOption) {
        this.batchOption = batchOption;
    }

    public List<SelectItem> getReasonOption() {
        return reasonOption;
    }

    public void setReasonOption(List<SelectItem> reasonOption) {
        this.reasonOption = reasonOption;
    }

    public String getReasonType() {
        return reasonType;
    }

    public void setReasonType(String reasonType) {
        this.reasonType = reasonType;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public List<SelectItem> getBranchNameList() {
        return branchNameList;
    }

    public void setBranchNameList(List<SelectItem> branchNameList) {
        this.branchNameList = branchNameList;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getScheduleNo() {
        return scheduleNo;
    }

    public void setScheduleNo(String scheduleNo) {
        this.scheduleNo = scheduleNo;
    }

    public List<SelectItem> getScheduleNoList() {
        return scheduleNoList;
    }

    public void setScheduleNoList(List<SelectItem> scheduleNoList) {
        this.scheduleNoList = scheduleNoList;
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

    public int getCtsSponsor() {
        return ctsSponsor;
    }

    public void setCtsSponsor(int ctsSponsor) {
        this.ctsSponsor = ctsSponsor;
    }

    public String getClgType() {
        return clgType;
    }

    public void setClgType(String clgType) {
        this.clgType = clgType;
    }

    public List<SelectItem> getClgTypeList() {
        return clgTypeList;
    }

    public void setClgTypeList(List<SelectItem> clgTypeList) {
        this.clgTypeList = clgTypeList;
    }

    public String getNpciCtsDisplayFlag() {
        return npciCtsDisplayFlag;
    }

    public void setNpciCtsDisplayFlag(String npciCtsDisplayFlag) {
        this.npciCtsDisplayFlag = npciCtsDisplayFlag;
    }

    public String getNormalCtsDisplayFlag() {
        return normalCtsDisplayFlag;
    }

    public void setNormalCtsDisplayFlag(String normalCtsDisplayFlag) {
        this.normalCtsDisplayFlag = normalCtsDisplayFlag;
    }

    public String getOtherReason() {
        return otherReason;
    }

    public void setOtherReason(String otherReason) {
        this.otherReason = otherReason;
    }

    public boolean isOtherReasonEnable() {
        return otherReasonEnable;
    }

    public void setOtherReasonEnable(boolean otherReasonEnable) {
        this.otherReasonEnable = otherReasonEnable;
    }

    public boolean isOthReasonflag() {
        return othReasonflag;
    }

    public void setOthReasonflag(boolean othReasonflag) {
        this.othReasonflag = othReasonflag;
    }

    public String getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(String displayStatus) {
        this.displayStatus = displayStatus;
    }

    public List<SelectItem> getDisplayStatusList() {
        return displayStatusList;
    }

    public void setDisplayStatusList(List<SelectItem> displayStatusList) {
        this.displayStatusList = displayStatusList;
    }

    public boolean isDocTypeFlag() {
        return docTypeFlag;
    }

    public void setDocTypeFlag(boolean docTypeFlag) {
        this.docTypeFlag = docTypeFlag;
    }
}
