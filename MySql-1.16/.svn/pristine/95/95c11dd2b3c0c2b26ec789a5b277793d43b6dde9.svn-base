package com.cbs.web.controller.clg;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.other.TxnDetailsBean;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.clg.CtsManagementFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.txn.TxnAuthorizationManagementFacadeRemote;
import com.cbs.utils.Base64;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.txn.AccountTypeDescPojo;
import com.hrms.web.utils.WebUtil;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;

public class CtsTransaction extends BaseBean {

    private String batchNo;
    private String errorMsg;
    private String message;
    private boolean flag1;
    private String branch;
    private List<SelectItem> branchList;
    private String accType;
    private List<SelectItem> accTypeList;
    private List<TxnDetailsBean> txnDetailBeanList;
    private TxnDetailsBean txnDetailBean;
    private boolean flag2;
    private String acctCode;
    private String agentCode;
    private String accNo;
    private String acNoLen;
    private String name;
    private String jtName;
    private BigDecimal clBal;
    private BigDecimal unclBal;
    private String instDate;
    private String instNo;
    private BigDecimal instAmount;
    String dtls;
    private String inFavourOf;
    private String bankName;
    private String bankAddress;
    private int serialNo;
    private String imageData;
    private boolean flag3;
    private boolean flag5 = false;
    private String flag4 = "none";
    private String instNoGL;
    private BigDecimal amountTxnGL;
    private String seqNo;
    private String opMode;
    private String txnId;
    private String rbiRefNo;
    private int emFlag;
    private String signData;
    private ServletContext serContext;
    private String folderName;
    private String msgForDataVerification;
    private String alertMsg;
    private String alertMsgChq;
    private BigDecimal instHiddenAmt;
    private String alertMsgAmtSummary;
    private boolean flag55 = false;
    private boolean disableDataVerification;
    private final String jndiHomeNameCts = "CtsManagementFacade";
    private CtsManagementFacadeRemote ctsTxnRemote = null;
    private final String jndiTxnHomeName = "TxnAuthorizationManagementFacade";
    private TxnAuthorizationManagementFacadeRemote txnAuthRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    String acctNature;
    List<AccountTypeDescPojo> accTypeDescList;
    private String scheduleNo;
    private List<SelectItem> scheduleNoList;
    private CommonReportMethodsRemote commonReportRemote;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private int ctsSponsor = 0;

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

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
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

    public String getAcctNature() {
        return acctNature;
    }

    public void setAcctNature(String acctNature) {
        this.acctNature = acctNature;
    }

    public List<AccountTypeDescPojo> getAccTypeDescList() {
        return accTypeDescList;
    }

    public void setAccTypeDescList(List<AccountTypeDescPojo> accTypeDescList) {
        this.accTypeDescList = accTypeDescList;
    }

    public boolean isDisableDataVerification() {
        return disableDataVerification;
    }

    public void setDisableDataVerification(boolean disableDataVerification) {
        this.disableDataVerification = disableDataVerification;
    }

    public int getCtsSponsor() {
        return ctsSponsor;
    }

    public void setCtsSponsor(int ctsSponsor) {
        this.ctsSponsor = ctsSponsor;
    }

    public int getEmFlag() {
        return emFlag;
    }

    public void setEmFlag(int emFlag) {
        this.emFlag = emFlag;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public CtsTransaction() {
        try {
            ctsTxnRemote = (CtsManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameCts);
            txnAuthRemote = (TxnAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiTxnHomeName);
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            commonReportRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            this.setAcNoLen(getAcNoLength());
            scheduleNoList = new ArrayList<SelectItem>();

//            getCtsFolderName();
            this.ctsSponsor = ftsPostRemote.getCodeForReportName("CTS-SPONSOR");//1-AMMCO,0-CCBL,3-RAMGARIYA
            getDataOnLoad();

            if (ctsSponsor == 1 || ctsSponsor == 3) {
                disableDataVerification = true;
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void getDataOnLoad() {

        try {

            if (ctsSponsor == 1 || ctsSponsor == 3) {
                this.setBranch("--Select--");
                branchList = new ArrayList<SelectItem>();
                List list = commonReportRemote.getAlphacodeAllAndBranch(getOrgnBrCode());
                for (int i = 0; i < list.size(); i++) {
                    Vector vtr = (Vector) list.get(i);
                    if (CbsUtil.lPadding(2, Integer.parseInt(vtr.get(1).toString())).equalsIgnoreCase(getOrgnBrCode())) {
                        branchList.add(new SelectItem(vtr.get(0).toString(), vtr.get(0).toString()));
                    }
                }
            } else {
                List list1 = ctsTxnRemote.getBranch();
                branchList = new ArrayList<SelectItem>();
                branchList.add(new SelectItem(" ", "--Select--"));
                for (int i = 0; i < list1.size(); i++) {
                    Vector element1 = (Vector) list1.get(i);
                    branchList.add(new SelectItem(element1.get(0).toString(), element1.get(0).toString()));
                }
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
            this.setMessage(e.getMessage());
        }
    }

//    public void retrieveScheduleNo() {
//        this.setErrorMsg("");
//        scheduleNoList = new ArrayList<SelectItem>();
//        try {
//            if (this.branch.equals(" ")) {
//                this.setErrorMsg("Please Select Branch");
//                flag2 = true;
//                return;
//            }
//            List list = ctsTxnRemote.getScheduleNoList(this.branch, ymd.format(new Date()));
//            if (list == null || list.isEmpty()) {
//                this.setErrorMsg("There is no schedule no for this branch.");
//                return;
//            }
//            scheduleNoList.add(new SelectItem("--Select--"));
//            for (int i = 0; i < list.size(); i++) {
//                Vector element = (Vector) list.get(i);
//                scheduleNoList.add(new SelectItem(element.get(0)));
//            }
//            
//        } catch (Exception e) {
//            this.setErrorMsg(e.getMessage());
//        }
//    }
    public void retrieveScheduleNo() {
        this.setErrorMsg("");
        scheduleNoList = new ArrayList<SelectItem>();
        try {

            if (this.branch.equals(" ")) {
                this.setErrorMsg("Please Select Branch");
                flag2 = true;
                return;
            }
            if (ctsSponsor == 3) {
                List list1 = ctsTxnRemote.getScheduleNoList1(this.branch, ymd.format(new Date()));
                if (list1 == null || list1.isEmpty()) {
                    this.setErrorMsg("There is no schedule no for this branch.");
                    return;
                }
                scheduleNoList.add(new SelectItem("--Select--"));
                for (int i = 0; i < list1.size(); i++) {
                    Vector element = (Vector) list1.get(i);
                    scheduleNoList.add(new SelectItem(element.get(0)));
                }
            } else {
                List list = ctsTxnRemote.getScheduleNoList(this.branch, ymd.format(new Date()));
                if (list == null || list.isEmpty()) {
                    this.setErrorMsg("There is no schedule no for this branch.");
                    return;
                }
                scheduleNoList.add(new SelectItem("--Select--"));
                for (int i = 0; i < list.size(); i++) {
                    Vector element = (Vector) list.get(i);
                    scheduleNoList.add(new SelectItem(element.get(0)));
                }
            }

        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void branchLostFocus() {
        String array[] = null;
        int i, j, k, l, m, n, o, p, q, r, s = 0, serlNo;
        try {
            NumberFormat formatter = new DecimalFormat("#0.00");
            txnDetailBeanList = new ArrayList<TxnDetailsBean>();
            if (this.branch.equals(" ")) {
                this.setErrorMsg("Please Select Branch.");
                flag2 = true;
                return;
            }
            if (this.scheduleNo == null || this.scheduleNo.equals("") || this.scheduleNo.equalsIgnoreCase("--Select--")) {
                this.setErrorMsg("Please Select Schedule No.");
                flag2 = true;
                return;
            }
            if (ctsSponsor == 3) {
                //For Ramgariya
                txnDetailBeanList = ctsTxnRemote.fnABBGetCtsDetails1(ymd.format(dmy.parse(getTodayDate())), this.branch,
                        Integer.parseInt(this.scheduleNo));
                flag2 = false;
            } else {
                String str = ctsTxnRemote.fnABBGetCtsDetails(ymd.format(dmy.parse(getTodayDate())),
                        this.branch, Integer.parseInt(this.scheduleNo));
                if (str.contains("[")) {
                    str = str.replace("[", "");
                    str = str.replace("]", "");
                    if (str.length() == 0) {
                        this.setErrorMsg("No data found");
                        flag2 = true;
                        return;
                    } else {
                        if (!str.contains(",")) {
                            //this.setErrorMsg("");
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
            }
        } catch (ApplicationException e) {
            this.setErrorMsg(e.getMessage());
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void setRowValues() {
        try {
            if (txnDetailBean == null) {
                this.setMessage("Please select a row from table.");
                return;
            }
            if (ctsSponsor == 3) {
                this.setBankName(txnDetailBean.getPrBankName());
                this.setBankAddress(txnDetailBean.getPrBankAddress());
                this.setInFavourOf(txnDetailBean.getInFavourOf());
                this.setInstDate(txnDetailBean.getInstDate());
                this.setInstNo(txnDetailBean.getChqNo());
                this.setInstAmount(txnDetailBean.getAmount());
                getNpciImageFolderName(txnDetailBean.getEmflag(), txnDetailBean.getScheduleNo(), commonReportRemote.getBrncodeByAlphacode(this.branch));
                this.setImageData(txnDetailBean.getImage() + ".JPEG");
                this.setInstHiddenAmt(txnDetailBean.getAmount());
                this.setRbiRefNo(txnDetailBean.getRbiReferenceNo());
            } else {
                String micr = txnDetailBean.getPrBankCode().toString().substring(0, 3);
                String code = txnDetailBean.getPrBankCode().toString().substring(3, 6);
                String codeNo = txnDetailBean.getPrBankCode().toString().substring(6);
                List list = ctsTxnRemote.getMicrDetails(micr, code, codeNo);
                Vector v = (Vector) list.get(0);
                this.setBankName(v.get(0).toString());
                this.setBankAddress(v.get(1).toString());
                this.setInstDate(txnDetailBean.getInstDate());
                this.setInstNo(txnDetailBean.getChqNo());
                this.setInstAmount(txnDetailBean.getAmount());
                getImageFolderName(Integer.parseInt(this.scheduleNo), commonReportRemote.getBrncodeByAlphacode(this.branch));
                if (ctsSponsor == 1) {
                    this.setImageData(txnDetailBean.getImage() + "_Fb.jpg");
                } else {
                    this.setImageData(txnDetailBean.getImage() + ".GF");
                }
                this.setInstHiddenAmt(txnDetailBean.getAmount());
                this.setRbiRefNo(txnDetailBean.getRbiReferenceNo());
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void getWhiteImage() {
        try {
            if (this.imageData == null) {
                this.setErrorMsg("No data found to show");
                return;
            }
            if (ctsSponsor == 3) {
                getNpciImageFolderName(txnDetailBean.getEmflag(), txnDetailBean.getScheduleNo(), commonReportRemote.getBrncodeByAlphacode(this.branch));
                this.setImageData(txnDetailBean.getImage() + ".PNG");
            } else {
                getImageFolderName(Integer.parseInt(this.scheduleNo), commonReportRemote.getBrncodeByAlphacode(this.branch));
                if (ctsSponsor == 1 || ctsSponsor == 3) {
                    this.setImageData(txnDetailBean.getImage() + "_Fb.jpg");
                } else {
                    this.setImageData(txnDetailBean.getImage() + ".jpg");
                }
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void getBackImage() {
        try {
            if (this.imageData == null) {
                this.setErrorMsg("No data found to show");
                return;
            }
            if (ctsSponsor == 3) {
                getNpciImageFolderName(txnDetailBean.getEmflag(), txnDetailBean.getScheduleNo(), commonReportRemote.getBrncodeByAlphacode(this.branch));
                this.setImageData(txnDetailBean.getImage() + ".JPG");
            } else {
                getImageFolderName(Integer.parseInt(this.scheduleNo), commonReportRemote.getBrncodeByAlphacode(this.branch));
                if (ctsSponsor == 1 || ctsSponsor == 3) {
                    this.setImageData(txnDetailBean.getImage() + "_Ba.jpg");
                } else {
                    this.setImageData(txnDetailBean.getImage() + ".png");
                }
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void getGrayImage() {
        try {
            if (this.imageData == null) {
                this.setErrorMsg("No data found to show");
                return;
            }
            if (ctsSponsor == 3) {
                getNpciImageFolderName(txnDetailBean.getEmflag(), txnDetailBean.getScheduleNo(), commonReportRemote.getBrncodeByAlphacode(this.branch));
                this.setImageData(txnDetailBean.getImage() + ".JPEG");
            } else {
                getImageFolderName(Integer.parseInt(this.scheduleNo), commonReportRemote.getBrncodeByAlphacode(this.branch));
                if (ctsSponsor == 1 || ctsSponsor == 3) {
                    this.setImageData(txnDetailBean.getImage() + "_Fg.jpg");
                } else {
                    this.setImageData(txnDetailBean.getImage() + ".GF");
                }
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void getImageFolderName(Integer scheduleCount, String orgnBrCode) {
        try {
            this.setFolderName(scheduleCount.toString() + "_" + orgnBrCode);
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void getNpciImageFolderName(Integer clgType, Integer scheduleNo, String branchCode) {
        try {
            this.setFolderName(clgType.toString() + "_" + scheduleNo.toString() + "_" + branchCode);
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }

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
            signature = txnAuthRemote.getSignatureDetails(accNo);
            if (!signature.equalsIgnoreCase("Signature not found")) {
                String imageCode = signature.trim();
                String directoryPath = CbsUtil.getSigFilePath(imageCode.substring(4), imageCode.substring(0, 4));
                String encryptAcno = CbsUtil.encryptText(accNo);
                System.out.println("encryptAcno is-->" + encryptAcno);
                String filePath = directoryPath + encryptAcno + ".xml";
                signData = CbsUtil.readImageFromXMLFile(new File(filePath));
            } else {
                signData = null;
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public boolean onblurAccountCode() {
        flag3 = false;
        String array[] = null;
        int j, k, l, m, n;
        try {
            if (this.acctCode == null || this.acctCode.equals("")) {
                this.setErrorMsg("Please Fill Account Number");
                flag3 = false;
                return false;
            }
            this.setErrorMsg("");
            flag3 = false;
            String loggedInAlphaCode = ctsTxnRemote.getBranchAplhaCode(Integer.parseInt(getOrgnBrCode()));
            if (!(loggedInAlphaCode.equalsIgnoreCase("CELL") || loggedInAlphaCode.equalsIgnoreCase("HO"))) {
                if (ctsSponsor == 0) {
                    if (!getOrgnBrCode().equalsIgnoreCase(acctCode.substring(0, 2))) {
                        this.setErrorMsg("Please fill only your branch A/c Number");
                        return false;
                    }
                }
            }
            accNo = ftsPostRemote.getNewAccountNumber(acctCode);
            acctNature = ftsPostRemote.getAccountNature(accNo);
            if (acctNature.equals(CbsConstant.PAY_ORDER)) {
                flag4 = "";
                this.setClBal(new BigDecimal("0"));
                this.setUnclBal(new BigDecimal("0"));
                this.setName("");
                this.setJtName("");
                this.setOpMode("");

            } else {
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
                    this.setAlertMsg(result);
                    flag3 = true;
                    return false;
                }
            }
            getSignatureDetail();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return true;
    }

    public void getCtsSeqNo() {
        this.setErrorMsg("");
        this.flag55 = false;
        try {
            if (!this.instAmount.equals(this.instHiddenAmt)) {
                this.flag55 = true;
                this.setAlertMsgAmtSummary("Are you satisfy with this amount");
            }
            String alphaCode = ctsTxnRemote.getBranchAplhaCode(Integer.parseInt(this.acctCode.substring(0, 2)));
            if (acctNature.equals(CbsConstant.PAY_ORDER)) {
                String result = ctsTxnRemote.valBillHead(accNo, this.instNo, instAmount.toString(), instDate.substring(6) + instDate.substring(3, 5) + instDate.substring(0, 2), alphaCode);
                if (!result.equalsIgnoreCase("true")) {
                    this.setErrorMsg("Please fill proper A/c or seq no does not exists.");
                    return;
                }
                result = ctsTxnRemote.getSeqNo(instNo, instAmount.toString(), instDate.substring(6) + instDate.substring(3, 5) + instDate.substring(0, 2), alphaCode);
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
            if (ctsSponsor == 3) {
                String sequenceNo;
                if (acctNature.equals(CbsConstant.PAY_ORDER)) {
                    sequenceNo = this.seqNo;
                } else {
                    sequenceNo = "0";
                }

                String partyName = this.name == null ? "" : this.name;
                String partyOpMode = this.opMode == null ? "" : this.opMode;

                String result = ctsTxnRemote.npciSaveClgInwardDetail(txnDetailBean.getTxnId().trim(), accNo,
                        instNo, instAmount, instDate, this.getDtls(), inFavourOf, bankName, bankAddress,
                        getUserName(), sequenceNo, partyName, partyOpMode);
                if (result.equalsIgnoreCase("true")) {
                    resetForm();
                    this.setErrorMsg("Data has been modified successfully.");
                    txnDetailBeanList.remove(txnDetailBean);
                }
            } else {
                txnId = WebUtil.getClientIP(this.getHttpRequest()) + sdf.format(new Date());
                if (validateForm() == false) {
                    return;
                }
                if (imageData == null) {
                    this.setErrorMsg("Please select cheque to save.");
                    return;
                }
                this.setErrorMsg("");
                /**
                 * New Addition*
                 */
                List paramList = ftsPostRemote.getBaseParameter(ftsPostRemote.getAccountCode(accNo));
                if (!paramList.isEmpty()) {
                    Vector paramVector = (Vector) paramList.get(0);
                    String chqFlag = paramVector.get(4).toString();
                    if (chqFlag.equalsIgnoreCase("N")) {
                        this.setErrorMsg("CTS is not allowed for this type of account.");
                        return;
                    }
                }
                /**
                 * New Addition End*
                 */
                String sequenceNo;
                if (acctNature.equals(CbsConstant.PAY_ORDER)) {
                    sequenceNo = this.seqNo;
                } else {
                    sequenceNo = "";
                }
                String image = "";
                if (ctsSponsor == 1) {
                    image = rbiRefNo;
                } else {
                    image = imageData.substring(imageData.lastIndexOf("/") + 1, imageData.indexOf("."));
                }

                String saveResult = ctsTxnRemote.saveTxnDetails(txnId, "0", accNo, instNo, instAmount.toString(), instDate.substring(6) + instDate.substring(3, 5) + instDate.substring(0, 2), inFavourOf,
                        bankName, bankAddress, getUserName(), sequenceNo, getOrgnBrCode(), this.acctCode.substring(0, 2),
                        ymd.format(new Date()), image, name, opMode, txnDetailBean.getPrBankCode().toString(), rbiRefNo, this.getDtls(), Integer.parseInt(this.scheduleNo));

                if (!saveResult.substring(0, 4).equalsIgnoreCase("TRUE")) {
                    this.setErrorMsg(saveResult);
                    return;
                } else {
                    resetForm();
                    this.setErrorMsg("Generated Voucher No. is:-   " + saveResult.substring(4));
                    txnDetailBeanList.remove(txnDetailBean);
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public boolean validateForm() {
        if (this.getUserName() == null || this.getUserName().equalsIgnoreCase("")) {
            this.setErrorMsg("Please exit and again open this form");
            return false;
        }
        if (this.branch.equals(" ")) {
            this.setErrorMsg("Please Select Branch");
            return false;
        }
        if (this.acctCode.equals("") || this.acctCode == null) {
            this.setErrorMsg("Please Fill Account Number");
            return false;
        }
        if (this.instDate == null || this.instDate.equalsIgnoreCase("")) {
            this.setErrorMsg("Please Select Instrument Date");
            return false;
        }
        if (this.instDate.length() < 10) {
            this.setErrorMsg("Please fill instrument date in dd/MM/yyyy format");
            return false;
        }
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
        if (acctNature.equals(CbsConstant.PAY_ORDER) && (this.seqNo == null || this.seqNo.equals(""))) {
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

        this.setInstDate("");
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
        scheduleNoList = new ArrayList<SelectItem>();
        txnDetailBeanList = new ArrayList<TxnDetailsBean>();
    }

    public String exitForm() {
        resetForm();
        this.setFlag1(false);
        this.setBranch(" ");
        scheduleNoList = new ArrayList<SelectItem>();
        txnDetailBeanList = new ArrayList<TxnDetailsBean>();
        return "case1";
    }

    public void checkData() {
        if (this.getBranch().equalsIgnoreCase(" ")) {
            this.setMsgForDataVerification("Please select your branch");
            return;
        }
        if (this.scheduleNo == null || this.scheduleNo.equals("") || this.scheduleNo.equalsIgnoreCase("--Select--")) {
            this.setMsgForDataVerification("Please select your branch schedule no.");
            return;
        }
        try {
            String brCode = commonReportRemote.getBrncodeByAlphacode(this.branch);
            String msg = ctsTxnRemote.dataVerification(Integer.parseInt(brCode), Integer.parseInt(scheduleNo));
            if (msg.equalsIgnoreCase("true")) {
                this.setMsgForDataVerification("Data is OK");
            } else {
                this.setMsgForDataVerification(msg);
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
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
            this.setMessage(e.getMessage());
        }
    }

    public void accTypeNatureDesc() {
        try {
            accTypeDescList = new ArrayList<AccountTypeDescPojo>();
            AccountTypeDescPojo pojo;
            List resultList = ftsPostRemote.getAcctTypeDesc();
            if (!resultList.isEmpty()) {
                for (Object object : resultList) {
                    Vector element = (Vector) object;
                    pojo = new AccountTypeDescPojo();
                    pojo.setAcctCode((String) element.get(0));
                    pojo.setAccType((String) element.get(1));
                    pojo.setAccNature((String) element.get(2));
                    pojo.setAccDesc((String) element.get(3));
                    accTypeDescList.add(pojo);
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
}
