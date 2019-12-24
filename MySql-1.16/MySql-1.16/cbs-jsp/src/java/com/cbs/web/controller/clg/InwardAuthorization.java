/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.clg;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.TxnDetailBean;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.clg.CtsManagementFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.facade.txn.TxnAuthorizationManagementFacadeRemote;
import com.cbs.utils.Base64;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.clg.UnAuthItem;
import java.io.File;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author root
 */
public class InwardAuthorization extends BaseBean {

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
    private boolean completeValue;
    private boolean batchValue;
    private String msg;
    private String gridAcno;
    private String sigData;
    private boolean accViewFlag = false;
    private List<TxnDetailBean> txnDetailList;
    private List<TxnDetailBean> txnDetailUnAuthList;
    private BigDecimal presentBalance;
    private BigDecimal openBalance;
    private TxnDetailBean txnDetailGrid;
    private TxnDetailBean txnDetailUnAuthGrid;
    private List<SelectItem> branchNameList;
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
    private String compAmountDetail = "none";
    private String compPassedAmount;
    private String compReturnedAmount;
    private boolean odBalFlag;
    private String confirmationMsg;
    private FtsPostingMgmtFacadeRemote ftsRemote;

    public InwardAuthorization() {
        try {
            ctsTxnRemote = (CtsManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameCts);
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            txnAuthRemote = (TxnAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiTxnHomeName);
            reportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiCommonReportHome);
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");

            getOnLoadData();
            getCompletionDetail();
            postValue = true;
            reasonEnable = true;
            accViewFlag = false;
            this.setGridAcno("");
            setOdBalFlag(false);
            setConfirmationMsg("");
        } catch (ApplicationException e) {
            this.setMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsg(e.getMessage());
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
            //Setting the Branch Name
            branchNameList = new ArrayList<SelectItem>();
            // String loginAlphaCode = reportRemote.getAlphacodeByBrncode(getOrgnBrCode());


            List alphaList = reportRemote.getAlphacodeBasedOnOrgnBranch(getOrgnBrCode());
            for (int i = 0; i < alphaList.size(); i++) {
                Vector element = (Vector) alphaList.get(i);
                branchNameList.add(new SelectItem(element.get(1).toString(), element.get(0).toString()));
            }


//            if (loginAlphaCode.equalsIgnoreCase("HO") || loginAlphaCode.equalsIgnoreCase("CELL")) {
//                List alphaList = reportRemote.getAlphacodeExcludingHo();
//                for (int i = 0; i < alphaList.size(); i++) {
//                    Vector element = (Vector) alphaList.get(i);
//                    branchNameList.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
//                }
//            }
//            else {
//                loginAlphaCode = reportRemote.getAlphacodeByBrncode(getOrgnBrCode());
//                branchNameList.add(new SelectItem(getOrgnBrCode(), loginAlphaCode));
//            }

            //Setting the Return Reason
            List reasonList = new ArrayList();
            reasonList = ctsTxnRemote.getReason();

            reasonOption = new ArrayList<SelectItem>();
            reasonOption.add(new SelectItem("--Select--"));
            for (int i = 0; i < reasonList.size(); i++) {
                Vector element = (Vector) reasonList.get(i);
                reasonOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));

//                for (Object o : element) {
//                    reasonOption.add(new SelectItem(o));
//                }
            }
        } catch (ApplicationException e) {
            this.setMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsg(e.getMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void dataInGrid() {
        List unAuthList = null;
        try {
            dataItemList = new ArrayList<UnAuthItem>();
            unAuthList = ctsTxnRemote.getGridDetails(Integer.parseInt(getBranchName()));
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
                currentItem.setCustName(element.get(31).toString());
                currentItem.setExceedFlag("");
                currentItem.setCustState(element.get(32).toString());
                currentItem.setBranchState(element.get(33).toString());
                dataItemList.add(currentItem);
            }
        } catch (ApplicationException e) {
            this.setMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public void authorizeAction() {
        try {
            for (int i = 0; i < dataItemList.size(); i++) {
                UnAuthItem item = dataItemList.get(i);
                if (item.getVarStatus().equals("PASS") || item.getVarStatus().equals("RETURN") || item.getVarStatus().equals("DELETE")) {
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
                    } else if (item.getVarStatus().equals("DELETE")) {
                        authStatus = 5;
                    }
                    System.out.println("Execced Flag Is-->" + item.getExceedFlag());
                    String finalMsg = ctsTxnRemote.chequesAuthorization(item.getAcNo(), item.getEnterBy(),
                            item.getOrgnCode(), item.getDestCode(), item.getDetails(), item.getSecondAuthBy(),
                            getUserName(), item.getInstNo(), item.getScreenFlag(), item.getPayee(), item.getBankName(),
                            item.getBankAddress(), authStatus, item.getSeqNo(), item.getPayBy(), item.getReasonForCancel(),
                            item.getVchNo(), item.getBatchNo(), item.getTxnId(), item.getInstAmt(), item.getDt(),
                            item.getInstDt(), item.getTranType(), item.getSubStatus(), item.getRequestType(),
                            item.getCircleType(), item.getTy(), item.getDetails(), userDetails, item.getExceedFlag(), 0, otherReasonText, item.getCustState(), item.getBranchState());
                    if (finalMsg.equalsIgnoreCase("The cheque has been passed successfuly")
                            || finalMsg.equalsIgnoreCase("The cheque has been returned successfuly")
                            || finalMsg.equalsIgnoreCase("The cheque has been deleted successfuly")
                            || finalMsg.equalsIgnoreCase("Charges Entered in PendingCharges due to Insufficient Fund!")) {
                        dataItemList.remove(currentRow);
                        this.setReasonType("--Select--");
                        postValue = true;
                        reasonEnable = true;
                        this.setMsg(finalMsg);
                        getCompletionDetail();
                    } else {
                        dataInGrid();
                        this.setMsg(finalMsg);
                        this.setReasonType("--Select--");
                        postValue = true;
                        reasonEnable = true;
                    }
                }
            }
        } catch (ApplicationException e) {
            this.setMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public void actionToCompleteCTS() {
        try {
            String total = ctsTxnRemote.getTotalChequeDetail(getOrgnBrCode());
            if (total.equalsIgnoreCase("true")) {
                String compMsg = ctsTxnRemote.completion(getOrgnBrCode(), getUserName());
                if (compMsg.equalsIgnoreCase("true")) {
                    this.setMsg("Inward process has been completed");
                    completeValue = true;
                } else {
                    this.setMsg(compMsg);
                }
            } else if (total.equalsIgnoreCase("false")) {
                this.setMsg("There are some pending entries for this branch");
            } else {
                this.setMsg(total);
            }
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public void getCompletionDetail() {
        try {
            String total = ctsTxnRemote.getTotalChequeDetail(getOrgnBrCode());
            if (total.equalsIgnoreCase("true")) {
                completeValue = false;
                //New Changes on 13/11/2014
                this.compAmountDetail = "";
                this.compPassedAmount = "";
                this.compReturnedAmount = "";
                List<String> list = ctsTxnRemote.getIwAmountOnCurrentDt(getOrgnBrCode());
                this.compPassedAmount = list.get(0);
                this.compReturnedAmount = list.get(1);
                //End
                this.setReasonType("--Select--");
                postValue = true;
                reasonEnable = true;
            } else if (total.equalsIgnoreCase("You have completed your CTS/Inward")) {
                completeValue = true;
                this.setReasonType("--Select--");
                postValue = true;
                reasonEnable = true;
                this.setMsg("You have completed your CTS/Inward");
            } else {
                completeValue = true;
            }

            String loginAlphaCode = reportRemote.getAlphacodeByBrncode(getOrgnBrCode());
            if (loginAlphaCode.equalsIgnoreCase("CELL")) {
                completeValue = true;
            }

//            if (loginAlphaCode.equalsIgnoreCase("HO") || loginAlphaCode.equalsIgnoreCase("CELL")) {
//                completeValue = true;
//            }
        } catch (ApplicationException e) {
            this.setMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public String changeStatus() {
        try {
            UnAuthItem item = dataItemList.get(currentRow);
            item.setExceedFlag("");
            if (item.getVarStatus().equalsIgnoreCase("ENTERED")) {
                if (item.getEnterBy().equalsIgnoreCase(getUserName())) {
                    this.setMsg("You are not authorized person");
                    return null;
                }
                boolean found = false;
                for (UnAuthItem item1 : dataItemList) {
                    if (item1.getVarStatus().equalsIgnoreCase("PASS") || item1.getVarStatus().equalsIgnoreCase("RETURN") || item1.getVarStatus().equalsIgnoreCase("DELETE")) {
                        found = true;
                    }
                }
                if (found) {
                    this.setMsg("Only one transction can be authorize at one time.");
                } else {
                    this.setGridAcno(item.getAcNo());
                    getSignatureDetail(item.getAcNo());
                    item.setVarStatus("PASS");
                    dataItemList.remove(currentRow);
                    dataItemList.add(currentRow, item);
                    this.setMsg("");
                    postValue = false;
                    reasonEnable = true;
                    accViewFlag = true;

                    this.odBalFlag = false;
                    this.confirmationMsg = "";
                    String chkBalResult = ftsRemote.chkBal(item.getAcNo(), 1, 0, ftsRemote.getAccountNature(item.getAcNo()));
                    if (!chkBalResult.equalsIgnoreCase("true")) {
                        if (ftsRemote.getAccountNature(item.getAcNo()).equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            String chkBalance = ftsRemote.checkBalForOdLimit(item.getAcNo(), item.getInstAmt(), getUserName());
                            if (chkBalance.equalsIgnoreCase("99")) {
                                confirmationMsg = "Limit Exceed for : " + item.getAcNo() + ". \n To Proceed press Yes, \n To Cancel press No";
                                this.odBalFlag = true;
                                return null;
                            } else if (!chkBalance.equalsIgnoreCase("1")) {
                                this.odBalFlag = false;
                                this.confirmationMsg = "";
                                this.setMsg("Balance Exceeds.");

                                this.setReasonType("--Select--");
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
                                postValue = true;
                                reasonEnable = true;

                                ctsTxnRemote.odBalStopUpdation(item.getTxnId(), getUserName(), "Balance Exceeds");
                                dataInGrid();
                                return null;
                            }
                        }
                    }
                }
            } else if (item.getVarStatus().equalsIgnoreCase("PASS")) {
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
                this.setGridAcno(item.getAcNo());
                getSignatureDetail(item.getAcNo());
                item.setVarStatus("DELETE");
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
//                this.setGridAcno(item.getAcNo());
//                getSignatureDetail(item.getAcNo());
//                item.setVarStatus("ENTERED");
//                dataItemList.remove(currentRow);
//                dataItemList.add(currentRow, item);
//                this.setMsg("");
//                postValue = true;
//                reasonEnable = true;
//                accViewFlag = false;

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
        if (data == null) {
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
            signature = txnAuthRemote.getSignatureDetails(gridAcno);
            if (!signature.equalsIgnoreCase("Signature not found")) {
                String imageCode = signature.trim();
                String directoryPath = CbsUtil.getSigFilePath(imageCode.substring(4), imageCode.substring(0, 4));
                String encryptAcno = CbsUtil.encryptText(gridAcno);
//                System.out.println("Encrypt A/c is:" + encryptAcno);
                String filePath = directoryPath + encryptAcno + ".xml";
                sigData = CbsUtil.readImageFromXMLFile(new File(filePath));
            } else {
                sigData = null;
            }
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public String refreshForm() {
        this.setMsg("");
        this.setReasonType("--Select--");
        getOnLoadData();
        reasonEnable = true;
        postValue = true;
        accViewFlag = false;
        this.setGridAcno("");
        dataItemList = new ArrayList<UnAuthItem>();
        this.compAmountDetail = "none";
        this.compPassedAmount = "";
        this.compReturnedAmount = "";
        setOdBalFlag(false);
        setConfirmationMsg("");
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
        } catch (ApplicationException e) {
            this.setMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public void getSignatureDetail() {
        try {
//            this.imageData = null;
            int Toper;
            String signature;
            accNo = currentItem.getAcNo();
            accountName = "";
            List list = txnAuthRemote.getDataForSignature(accNo);
//            System.out.println("list    " + list);
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
//                this.setErrorMsg("");
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
    /*Getter And Setter*/

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

//    public String getFolderName() {
//        return folderName;
//    }
//
//    public void setFolderName(String folderName) {
//        this.folderName = folderName;
//    }
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

//    public String getImageData() {
//        return imageData;
//    }
//
//    public void setImageData(String imageData) {
//        this.imageData = imageData;
//    }
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

    public String getCompAmountDetail() {
        return compAmountDetail;
    }

    public void setCompAmountDetail(String compAmountDetail) {
        this.compAmountDetail = compAmountDetail;
    }

    public String getCompPassedAmount() {
        return compPassedAmount;
    }

    public void setCompPassedAmount(String compPassedAmount) {
        this.compPassedAmount = compPassedAmount;
    }

    public String getCompReturnedAmount() {
        return compReturnedAmount;
    }

    public void setCompReturnedAmount(String compReturnedAmount) {
        this.compReturnedAmount = compReturnedAmount;
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
}
