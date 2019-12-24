/*
 * Nishant Kansal
 */
package com.cbs.web.controller.clg;

import com.cbs.dto.other.TxnDetailsBean;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.clg.CtsManagementFacadeRemote;
import com.cbs.facade.txn.OtherAuthorizationManagementFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.Base64;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
//import java.math.BigInteger;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class Transaction {

    /*                                   Variables Declarations          */
    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private HttpServletRequest req;
    private String batchNo;
    private String errorMsg;
    private String message;
    private boolean flag1;
    private String branch;
    private List<SelectItem> branchList;
    private String destBranch;
    private List<SelectItem> destBranchList;
    private String accType;
    private List<SelectItem> accTypeList;
    private List<TxnDetailsBean> txnDetailBeanList;
    private TxnDetailsBean txnDetailBean;
    private boolean flag2;
    private String acctCode;
    private String agentCode;
    private String accNo;
    private String name;
    private String jtName;
    private BigDecimal clBal;
    private BigDecimal unclBal;
    /**
     * *Changed by dinesh**
     */
    //private Date instDate;
    private String instDate;
    /**
     * *end here**
     */
    private String instNo;
    private BigDecimal instAmount;
    String dtls;
    private String inFavourOf;
    private String bankName;
    private String bankAddress;
    private int serialNo;
    private int currentRow;
    private String imageData;
    private String brnCode;
    private boolean flag3;
    private boolean flag5 = false;
    private String flag4 = "none";
    private String instNoGL;
    private BigDecimal amountTxnGL;
    private String seqNo;
    private String opMode;
    private String txnId;
    private String rbiRefNo;
    private String signData;
    private ServletContext serContext;
    private String folderName;
    private String msgForDataVerification;
    private String alertMsg;
    private String alertMsgChq;
    /**
     * *Added by dinesh**
     */
    private BigDecimal instHiddenAmt;
    private String alertMsgAmtSummary;
    private boolean flag55 = false;
    private final String jndiHomeNameCts = "CtsManagementFacade";
    private CtsManagementFacadeRemote ctsTxnRemote = null;
    private final String jndiHomeNameOther = "OtherAuthorizationManagementFacade";
    private OtherAuthorizationManagementFacadeRemote otherRemote = null;

    /**
     * *end here**
     */

    /*                                       End of  Variables Declarations  */
    /*                                       Start of Getters and Setters    */
    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isFlag1() {
        return flag1;
    }

    public void setFlag1(boolean flag1) {
        this.flag1 = flag1;
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

    public String getDestBranch() {
        return destBranch;
    }

    public void setDestBranch(String destBranch) {
        this.destBranch = destBranch;
    }

    public List<SelectItem> getDestBranchList() {
        return destBranchList;
    }

    public void setDestBranchList(List<SelectItem> destBranchList) {
        this.destBranchList = destBranchList;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public List<SelectItem> getAccTypeList() {
        return accTypeList;
    }

    public void setAccTypeList(List<SelectItem> accTypeList) {
        this.accTypeList = accTypeList;
    }

    public TxnDetailsBean getTxnDetailBean() {
        return txnDetailBean;
    }

    public void setTxnDetailBean(TxnDetailsBean txnDetailBean) {
        this.txnDetailBean = txnDetailBean;
    }

    public List<TxnDetailsBean> getTxnDetailBeanList() {
        return txnDetailBeanList;
    }

    public void setTxnDetailBeanList(List<TxnDetailsBean> txnDetailBeanList) {
        this.txnDetailBeanList = txnDetailBeanList;
    }

    public boolean isFlag2() {
        return flag2;
    }

    public void setFlag2(boolean flag2) {
        this.flag2 = flag2;
    }

    public String getAcctCode() {
        return acctCode;
    }

    public void setAcctCode(String acctCode) {
        this.acctCode = acctCode;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public BigDecimal getClBal() {
        return clBal;
    }

    public void setClBal(BigDecimal clBal) {
        this.clBal = clBal;
    }

    public String getDtls() {
        return dtls;
    }

    public void setDtls(String dtls) {
        this.dtls = dtls;
    }

    public String getInFavourOf() {
        return inFavourOf;
    }

    public void setInFavourOf(String inFavourOf) {
        this.inFavourOf = inFavourOf;
    }

    public BigDecimal getInstAmount() {
        return instAmount;
    }

    public void setInstAmount(BigDecimal instAmount) {
        this.instAmount = instAmount;
    }

    /**
     * *Changed by dinesh**
     */
    public String getInstDate() {
        return instDate;
    }

    public void setInstDate(String instDate) {
        this.instDate = instDate;
    }

    public BigDecimal getInstHiddenAmt() {
        return instHiddenAmt;
    }

    public void setInstHiddenAmt(BigDecimal instHiddenAmt) {
        this.instHiddenAmt = instHiddenAmt;
    }

    public String getAlertMsgAmtSummary() {
        return alertMsgAmtSummary;
    }

    public void setAlertMsgAmtSummary(String alertMsgAmtSummary) {
        this.alertMsgAmtSummary = alertMsgAmtSummary;
    }

    public boolean isFlag55() {
        return flag55;
    }

    public void setFlag55(boolean flag55) {
        this.flag55 = flag55;
    }

//    public Date getInstDate() {
//        return instDate;
//    }
//
//    public void setInstDate(Date instDate) {
//        this.instDate = instDate;
//    }
    /**
     * *end here**
     */
    public String getInstNo() {
        return instNo;
    }

    public void setInstNo(String instNo) {
        this.instNo = instNo;
    }

    public String getJtName() {
        return jtName;
    }

    public void setJtName(String jtName) {
        this.jtName = jtName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getUnclBal() {
        return unclBal;
    }

    public void setUnclBal(BigDecimal unclBal) {
        this.unclBal = unclBal;
    }

    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public String getBrnCode() {
        return brnCode;
    }

    public void setBrnCode(String brnCode) {
        this.brnCode = brnCode;
    }

    public boolean isFlag3() {
        return flag3;
    }

    public void setFlag3(boolean flag3) {
        this.flag3 = flag3;
    }

    public String getFlag4() {
        return flag4;
    }

    public void setFlag4(String flag4) {
        this.flag4 = flag4;
    }

    public BigDecimal getAmountTxnGL() {
        return amountTxnGL;
    }

    public void setAmountTxnGL(BigDecimal amountTxnGL) {
        this.amountTxnGL = amountTxnGL;
    }

    public String getInstNoGL() {
        return instNoGL;
    }

    public void setInstNoGL(String instNoGL) {
        this.instNoGL = instNoGL;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getOpMode() {
        return opMode;
    }

    public void setOpMode(String opMode) {
        this.opMode = opMode;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getRbiRefNo() {
        return rbiRefNo;
    }

    public void setRbiRefNo(String rbiRefNo) {
        this.rbiRefNo = rbiRefNo;
    }

    public String getSignData() {
        return signData;
    }

    public void setSignData(String signData) {
        this.signData = signData;
    }

    public ServletContext getSerContext() {
        return serContext;
    }

    public void setSerContext(ServletContext serContext) {
        this.serContext = serContext;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getMsgForDataVerification() {
        return msgForDataVerification;
    }

    public void setMsgForDataVerification(String msgForDataVerification) {
        this.msgForDataVerification = msgForDataVerification;
    }

    public String getAlertMsg() {
        return alertMsg;
    }

    public void setAlertMsg(String alertMsg) {
        this.alertMsg = alertMsg;
    }

    public String getAlertMsgChq() {
        return alertMsgChq;
    }

    public void setAlertMsgChq(String alertMsgChq) {
        this.alertMsgChq = alertMsgChq;
    }

    public boolean isFlag5() {
        return flag5;
    }

    public void setFlag5(boolean flag5) {
        this.flag5 = flag5;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    /*                                              End of Getters and Setters                   */
    public Transaction() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            ctsTxnRemote = (CtsManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameCts);
            otherRemote = (OtherAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameOther);
            getDataOnLoad();
            getCtsFolderName();

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public ServletContext getWebSerContext() {
        ServletContext ser = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        if (ser == null) {
            throw new RuntimeException("Sorry. Got a null context from faces context");
        }
        return ser;
    }
    /*                                Data On Load                                  */

    public void getDataOnLoad() {
        try {
            List list1 = ctsTxnRemote.getBranch();
            branchList = new ArrayList<SelectItem>();
            branchList.add(new SelectItem(" ", "--Select--"));
            for (int i = 0; i < list1.size(); i++) {
                Vector element1 = (Vector) list1.get(i);
                branchList.add(new SelectItem(element1.get(0).toString(), element1.get(0).toString()));
            }

            destBranchList = new ArrayList<SelectItem>();
            destBranchList.add(new SelectItem(" ", "--Select--"));
            for (int i = 0; i < list1.size(); i++) {
                Vector element2 = (Vector) list1.get(i);
                destBranchList.add(new SelectItem(element2.get(1).toString(), element2.get(0).toString()));
            }

            List list2 = ctsTxnRemote.getAcctCode();
            accTypeList = new ArrayList<SelectItem>();
            accTypeList.add(new SelectItem("0", "--Select--"));
            for (int i = 0; i < list2.size(); i++) {
                Vector element3 = (Vector) list2.get(i);
                accTypeList.add(new SelectItem(element3.get(0).toString(), element3.get(0).toString()));
            }

            this.setAgentCode("01");

            this.setClBal(new BigDecimal("0"));
            this.setUnclBal(new BigDecimal("0"));
            this.setInstAmount(new BigDecimal("0"));

        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /*                                Start Session                                 */
//    public void clickOnStartSession() {
////        String array[];
//        try {
//            ctx = new InitialContext();
//            ftsPost43CBSRemote = (FtsPostingCbsRemote) ctx.lookup(FtsPostingCbsRemote.class.getName());
//            float batch = ftsPost43CBSRemote.getTrsNo();
//            //this.setBatchNo("0");
//            this.setFlag1(true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    /*
     *
     */
    public void branchLostFocus() {
        String array[] = null;
        int i, j, k, l, m, n, o, p, q, r, s = 0, serlNo;
        try {
            NumberFormat formatter = new DecimalFormat("#0.00");
            txnDetailBeanList = new ArrayList<TxnDetailsBean>();
            if (this.branch.equals(" ")) {
                this.setErrorMsg("Please Select Branch");
                flag2 = true;
                return;
            }
            String str = "";
//            String str = ctsTxnRemote.fnABBGetCtsDetails(todayDate, branch);
            if (str.contains("[")) {
                str = str.replace("[", "");
                str = str.replace("]", "");
                if (str.length() == 0) {
                    this.setErrorMsg("No data found");
                    flag2 = true;
                    return;
                } else {
                    if (!str.contains(",")) {
                        this.setErrorMsg(str);
                        return;
                    } else {
                        this.setErrorMsg("");
                        String spliter = ", ";
                        array = str.split(spliter);
                        flag2 = false;
                    }

                }
            }

            for (i = 0, j = 1, k = 2, l = 3, m = 4, n = 5, o = 6, p = 7, q = 8, r = 9; r < (array.length); i = i + 10, j = j + 10, k = k + 10, l = l + 10, m = m + 10, n = n + 10, o = o + 10, p = p + 10, q = q + 10, r = r + 10) {
                txnDetailBean = new TxnDetailsBean();
                String instDt = array[i];
                serlNo = s + 1;
                s++;
                txnDetailBean.setSrNo(serlNo);
                txnDetailBean.setInstDate(instDt.substring(6) + "/" + instDt.substring(4, 6) + "/" + instDt.substring(0, 4));
                txnDetailBean.setAmount(new BigDecimal(formatter.format(Double.parseDouble(array[k]))));
                txnDetailBean.setChqNo(array[l]);
                txnDetailBean.setPrBankCode(array[m]);
                txnDetailBean.setPrBankName(array[n]);
                txnDetailBean.setImage(array[q]);
                txnDetailBean.setRbiReferenceNo(array[r]);
                txnDetailBeanList.add(txnDetailBean);

            }

        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    /*
     *
     */

    public void fetchCurrentRow(ActionEvent event) {
        try {
            String chequeNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("chqNo"));
            currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
            for (TxnDetailsBean item : txnDetailBeanList) {
                if (item.getChqNo().equals(chequeNo)) {
                    txnDetailBean = item;
                    break;
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    /*
     *
     */

    public void setRowValues() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String micr, code, codeNo;
        NumberFormat formatter = new DecimalFormat("#0.00");
        try {
            micr = txnDetailBean.getPrBankCode().toString().substring(0, 3);
            code = txnDetailBean.getPrBankCode().toString().substring(3, 6);
            codeNo = txnDetailBean.getPrBankCode().toString().substring(6);
            List list = ctsTxnRemote.getMicrDetails(micr, code, codeNo);
            Vector v = (Vector) list.get(0);
            this.setBankName(v.get(0).toString());
            this.setBankAddress(v.get(1).toString());
            /**
             * *Changed by dinesh**
             */
            //this.setInstDate(sdf.parse(txnDetailBean.getInstDate()));
            this.setInstDate(txnDetailBean.getInstDate());
            /**
             * *end here**
             */
            this.setInstNo(txnDetailBean.getChqNo());
            this.setInstAmount(txnDetailBean.getAmount());
            this.setImageData(txnDetailBean.getImage() + ".GF");
            /**
             * *Added by dinesh**
             */
            this.setInstHiddenAmt(txnDetailBean.getAmount());
            /**
             * *end here**
             */
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void getWhiteImage() {
        if (this.imageData == null) {
            this.setErrorMsg("No data found to show");
            return;
        }

        this.setImageData(txnDetailBean.getImage() + ".jpg");

    }

    public void getCtsFolderName() {
        try {
            this.setFolderName(ctsTxnRemote.getBranchCtsFolder(orgnBrCode));
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void getBackImage() {
        if (this.imageData == null) {
            this.setErrorMsg("No data found to show");
            return;
        }

        this.setImageData(txnDetailBean.getImage() + ".png");

    }

    public void getGrayImage() {
        if (this.imageData == null) {
            this.setErrorMsg("No data found to show");
            return;
        }

        this.setImageData(txnDetailBean.getImage() + ".GF");

    }

    public void createSignature(OutputStream out, Object data) throws IOException {

        if (null == data) {
            return;
        }

        if (signData != null) {
            byte[] sigBytes = Base64.decode(signData);
            out.write(sigBytes);
        }
    }

    public void getSignatureDetail() {
        try {
            if (this.acctCode.equals("") || this.acctCode == null) {
                signData = null;

                return;
            }
            String signature;
            signature = otherRemote.getSignatureDetails(accNo);
            if (!signature.equalsIgnoreCase("Signature not found")) {
                signData = signature;
            } else {
                signData = null;
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void onblurBaseBranch() {
        try {
            if (this.destBranch.equals(" ")) {
                this.setErrorMsg("Please select base branch");
                return;
            } else {
                this.setErrorMsg("");
                if (this.destBranch.length() == 1) {
                    brnCode = "0" + this.destBranch;
                } else {
                    brnCode = this.destBranch;
                }

            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public boolean onblurAccountCode() {
        flag3 = false;
        int lenAcc, addZero;
        String array[] = null;
        int j, k, l, m, n;
        try {
            if (this.destBranch.equals(" ")) {
                this.setErrorMsg("Please select base branch");
                flag3 = true;
                return false;
            }
            if (this.accType.equals("0")) {
                this.setErrorMsg("Please select A/C type");
                flag3 = true;
                return false;
            }
            if (this.acctCode.equals("") || this.acctCode == null) {
                this.setErrorMsg("Please Fill Account Number");
                flag3 = true;
                return false;
            }
            if (!this.acctCode.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                this.setErrorMsg("Only Numeric value is allowed in A/C No.");
                flag3 = true;
                return false;
            }
            this.setErrorMsg("");
            flag3 = false;
            if (this.accType.equals("GL")) {

                flag4 = "";
                lenAcc = this.acctCode.length();
                addZero = 6 - lenAcc;
                for (int i = 0; i < addZero; i++) {
                    acctCode = "0" + acctCode;
                }
                accNo = brnCode + this.accType + acctCode + this.agentCode;
                this.setClBal(new BigDecimal("0"));
                this.setUnclBal(new BigDecimal("0"));
                this.setName("");
                this.setJtName("");
                this.setOpMode("");

            } else {
                lenAcc = this.acctCode.length();
                addZero = 6 - lenAcc;
                for (int i = 0; i < addZero; i++) {
                    acctCode = "0" + acctCode;
                }
                accNo = brnCode + this.accType + acctCode + this.agentCode;
                flag4 = "none";
                String result = ctsTxnRemote.getClngDetails(accNo);

                if (result.contains("[")) {
                    result = result.replace("[", "");
                    result = result.replace("]", "");
                    String spliter = ", ";
                    array = result.split(spliter);

                    for (j = 0, k = 1, l = 2, m = 3, n = 4; n < (array.length); j = j + 5, k = k + 5, l = l + 5, m = m + 5, n = n + 5) {
                        this.setClBal(new BigDecimal(Double.parseDouble(array[l])));
                        this.setUnclBal(new BigDecimal(Double.parseDouble(array[m])));
                        this.setName(array[j]);
                        this.setJtName(array[k]);
                        this.setOpMode(array[n]);

                    }
                    this.setAlertMsg("true");
                } else {
                    //this.setErrorMsg(result);
                    this.setAlertMsg(result);
                    flag3 = true;
                    return false;
                }
            }
            getSignatureDetail();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }

        return true;
    }

    public void getCtsSeqNo() {
        this.setErrorMsg("");
        /**
         * *Added by dinesh**
         */
        this.flag55 = false;
        /**
         * *end here**
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            /**
             * *Added by dinesh**
             */
            if (!this.instAmount.equals(this.instHiddenAmt)) {
                this.flag55 = true;
                this.setAlertMsgAmtSummary("Are you satisfy with this amount");
            }
            /**
             * *end here**
             */
            String alphaCode = ctsTxnRemote.getBranchAplhaCode(Integer.parseInt(this.getDestBranch()));
            if (this.accType.equals("GL")) {
                /**
                 * *Changed by dinesh***
                 */
                //String result = ctsTxnRemote.getSeqNo(instNo, instAmount.toString(), sdf.format(instDate), alphaCode);
                String result = ctsTxnRemote.getSeqNo(instNo, instAmount.toString(), instDate.substring(6) + instDate.substring(3, 5) + instDate.substring(0, 2), alphaCode);
                /**
                 * *end here***
                 */
                this.setErrorMsg("");
                String[] array = result.split("~`");
                this.setSeqNo(array[0]);
                this.setInFavourOf(array[1]);
                this.setName(array[2]);

            }
        } catch (ApplicationException e) {
            this.setSeqNo("");
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setSeqNo("");
            this.setErrorMsg(e.getMessage());
        }
    }

    public void saveInwardClgDetails() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssms");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        try {
            req = getRequest();
            txnId = WebUtil.getClientIP(req) + sdf.format(new Date());
            if (validateForm() == false) {
                return;
            }
            if (imageData == null) {
                this.setErrorMsg("Please select cheque to save.");
                return;
            }
            this.setErrorMsg("");
            rbiRefNo = txnDetailBean.getRbiReferenceNo().toString();
            String sequenceNo;
            if (this.accType.equals("GL")) {
                sequenceNo = this.seqNo;
            } else {
                sequenceNo = "";
            }

            String image = imageData.substring(imageData.lastIndexOf("/") + 1, imageData.indexOf("."));

            String saveResult = ctsTxnRemote.saveTxnDetails(txnId, "0", accNo, instNo, instAmount.toString(), instDate.substring(6) + instDate.substring(3, 5) + instDate.substring(0, 2), inFavourOf,
                    bankName, bankAddress, userName, sequenceNo, getOrgBrnCode(), destBranch,
                    ymd.format(new Date()), image, name,
                    opMode, txnDetailBean.getPrBankCode().toString(), rbiRefNo, this.getDtls(), 0);

            /**
             * *end here**
             */
            if (!saveResult.substring(0, 4).equalsIgnoreCase("TRUE")) {
                this.setErrorMsg(saveResult);
                return;
            } else {
                resetForm();
                this.setErrorMsg("Generated Voucher No. is:-   " + saveResult.substring(4));
                txnDetailBeanList.remove(currentRow);
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public String getOrgBrnCode() {
        String orgCode = new String();
        try {
            orgCode = ctsTxnRemote.getOrgnBranch(this.branch);
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
        return orgCode;
    }

    public boolean validateForm() {
        /**
         * *Changed by dinesh**
         */
        if (this.userName == null || this.userName.equalsIgnoreCase("")) {
            this.setErrorMsg("Please exit and again open this form");
            return false;
        }
        /**
         * *end here**
         */
        if (this.branch.equals(" ")) {
            this.setErrorMsg("Please Select Branch");
            return false;
        }
        if (this.destBranch.equals(" ")) {
            this.setErrorMsg("Please Select Base Branch");
            return false;
        }

        if (this.accType.equals("0")) {
            this.setErrorMsg("Please Select A/C Type");
            return false;
        }
        if (this.acctCode.equals("") || this.acctCode == null) {
            this.setErrorMsg("Please Fill Account Number");
            return false;
        }
        /**
         * **Changed by dinesh***
         */
//        if (this.instDate == null) {
//            this.setErrorMsg("Please Select Instrument Date");
//            return false;
//        }
        if (this.instDate == null || this.instDate.equalsIgnoreCase("")) {
            this.setErrorMsg("Please Select Instrument Date");
            return false;
        }
        if (this.instDate.length() < 10) {
            this.setErrorMsg("Please fill instrument date in dd/MM/yyyy format");
            return false;
        }

        /**
         * **end here***
         */
        if (this.instNo.equals("") || this.instNo == null) {
            this.setErrorMsg("Please Fill Instrument No.");
            return false;
        }
        if (!this.instNo.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
            this.setErrorMsg("Only Numeric value is allowed in Instrument No.");
            return false;
        }

        if (this.instAmount.toString().equals("") || this.instAmount == null) {
            this.setErrorMsg("Please Fill Instrument Amount");
            return false;
        }

        if (!this.instAmount.toString().matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
            this.setErrorMsg("Only Numeric value is allowed in Instrument Amount");
            return false;
        }
        if (this.accType.equals("GL") && (this.seqNo == null || this.seqNo.equals(""))) {
            this.setErrorMsg("There is no seq.no. Please check your details");
            return false;
        }
        if (this.dtls == null) {
            this.setErrorMsg("Please Fill Details for Inst. no.");
            return false;
        }
        return true;
    }

    public void resetForm() {
        this.setFlag1(true);

        this.setAccType("0");
        this.setAcctCode("");
        this.setAccNo("");
        this.setName("");
        this.setJtName("");
        this.setClBal(new BigDecimal("0"));
        this.setUnclBal(new BigDecimal("0"));
        /**
         * *Changed by dinesh**
         */
        //this.setInstDate(null);
        this.setInstDate("");
        /**
         * *end here**
         */
        this.setInstNo("");
        this.setInstAmount(new BigDecimal("0"));
        this.setDtls("");
        this.setInFavourOf("");
        this.setBankName("");
        this.setBankAddress("");

        this.setErrorMsg("");
        this.setImageData(null);
        this.setFlag2(false);
        this.setSignData(null);
        this.setOpMode("");
        this.setSeqNo("");
    }

    public void cancelTxnProcess() {
        resetForm();
        this.setBranch(" ");
        this.setDestBranch(" ");

        txnDetailBeanList = new ArrayList<TxnDetailsBean>();
    }

    public String exitForm() {
        resetForm();
        this.setFlag1(false);
        this.setBranch(" ");
        this.setDestBranch(" ");

        txnDetailBeanList = new ArrayList<TxnDetailsBean>();
        return "case1";
    }

    public void checkData() {
        String alphaCode = this.getBranch();
        if (alphaCode.equalsIgnoreCase(" ")) {
            this.setMsgForDataVerification("Please select your branch");
            return;
        }
        try {
            int brCode = ctsTxnRemote.getDestBrnCode(alphaCode);
            String branchCtsFolder = ctsTxnRemote.getBranchCtsFolder(Integer.toString(brCode));
            String msg = ctsTxnRemote.dataVerification(brCode, Integer.parseInt(branchCtsFolder));
            if (msg.equalsIgnoreCase("true")) {
                this.setMsgForDataVerification("Data is OK");
            } else {
                this.setMsgForDataVerification(msg);
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void checkInstNo() {
        String msg = "";
        flag5 = false;
        try {
            if (this.getAlertMsg() != null) {
                String acMsg = this.getAlertMsg();
                if (acMsg.equalsIgnoreCase("true")) {
                    flag5 = false;
                    msg = ctsTxnRemote.chequeValidate(accNo, this.getInstNo());
                    if (!msg.equalsIgnoreCase("true")) {
                        this.setAlertMsgChq(msg);
                        flag5 = true;
                    }
                }
            }

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
