/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.txn;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.txn.OtherTransactionManagementFacadeRemote;
import com.cbs.web.pojo.txn.ShadowBalClearForReturn;
import com.cbs.web.pojo.txn.ShadowBalClearanceTable;
import com.cbs.web.pojo.txn.ShadowBalCleBankNameTable;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.faces.model.SelectItem;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShadowBalanceClearance {

    private String branch;
    private String orgnBrCode;
    private String todayDate;
    private HttpServletRequest req;
    private String userName;
    private String message;
    private String ddHoldOpt;
    private String emFlags;
    private String registerDate;
    private String orderByRegisterDt;
    private List<SelectItem> clearingModeOption;
    private List<SelectItem> reasonForCancel;
    private List<SelectItem> bankNameOption;
    private List<SelectItem> registerDtOption;
    private List<SelectItem> ddHoldOption;
    private List<SelectItem> branchList;
    private List<ShadowBalClearanceTable> balClearanceTable;
    int currentRow;
    ShadowBalClearanceTable currentItem;
    private String acno;
    private String instrNo;
    Date instDate;
    private String bankName;
    private String totalAmount;
    private int totalCheque;
    private String clearingDate;
    private String rtTotalAmount;
    private int rtTotalCheque;
    private String bankClearingHold;
    private String bankAdd;
    private String returnCheques;
    private List<ShadowBalCleBankNameTable> bankNameTable;
    ShadowBalCleBankNameTable currentItem1;
    private String bankHoldName;
    private String returnBankName;
    private String flag;
    private String reasonPenalDisplay;
    private List<ShadowBalClearForReturn> balReturnTable;
    ShadowBalClearForReturn currentItem2;
    //private float txtInstAmount;
    public String txtInstAmount;
    private String reasonsReturn;
    String orderRegisterDt;
    private String vtot;
    private String bankClearingHoldRel;
    private String returnCode;
    private final String jndiHomeName = "OtherTransactionManagementFacade";
    private OtherTransactionManagementFacadeRemote otherTxnRemote = null;
    private CommonReportMethodsRemote commonRemote;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private String bnkHoldLen = "3";
    private int ctsSponsor = 0;
    private boolean disableReleaseBtn;

    public boolean isDisableReleaseBtn() {
        return disableReleaseBtn;
    }

    public void setDisableReleaseBtn(boolean disableReleaseBtn) {
        this.disableReleaseBtn = disableReleaseBtn;
    }

    public int getCtsSponsor() {
        return ctsSponsor;
    }

    public void setCtsSponsor(int ctsSponsor) {
        this.ctsSponsor = ctsSponsor;
    }

    public List<SelectItem> getReasonForCancel() {
        return reasonForCancel;
    }

    public void setReasonForCancel(List<SelectItem> reasonForCancel) {
        this.reasonForCancel = reasonForCancel;
    }

    public String getBankClearingHoldRel() {
        return bankClearingHoldRel;
    }

    public void setBankClearingHoldRel(String bankClearingHoldRel) {
        this.bankClearingHoldRel = bankClearingHoldRel;
    }

    public String getVtot() {
        return vtot;
    }

    public void setVtot(String vtot) {
        this.vtot = vtot;
    }

    public String getReasonsReturn() {
        return reasonsReturn;
    }

    public void setReasonsReturn(String reasonsReturn) {
        this.reasonsReturn = reasonsReturn;
    }

    public String getTxtInstAmount() {
        return txtInstAmount;
    }

    public void setTxtInstAmount(String txtInstAmount) {
        this.txtInstAmount = txtInstAmount;
    }

    public ShadowBalClearForReturn getCurrentItem2() {
        return currentItem2;
    }

    public void setCurrentItem2(ShadowBalClearForReturn currentItem2) {
        this.currentItem2 = currentItem2;
    }

    public List<ShadowBalClearForReturn> getBalReturnTable() {
        return balReturnTable;
    }

    public void setBalReturnTable(List<ShadowBalClearForReturn> balReturnTable) {
        this.balReturnTable = balReturnTable;
    }

    public String getReasonPenalDisplay() {
        return reasonPenalDisplay;
    }

    public void setReasonPenalDisplay(String reasonPenalDisplay) {
        this.reasonPenalDisplay = reasonPenalDisplay;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getReturnBankName() {
        return returnBankName;
    }

    public void setReturnBankName(String returnBankName) {
        this.returnBankName = returnBankName;
    }

    public String getBankHoldName() {
        return bankHoldName;
    }

    public void setBankHoldName(String bankHoldName) {
        this.bankHoldName = bankHoldName;
    }

    public ShadowBalCleBankNameTable getCurrentItem1() {
        return currentItem1;
    }

    public void setCurrentItem1(ShadowBalCleBankNameTable currentItem1) {
        this.currentItem1 = currentItem1;
    }

    public List<ShadowBalCleBankNameTable> getBankNameTable() {
        return bankNameTable;
    }

    public void setBankNameTable(List<ShadowBalCleBankNameTable> bankNameTable) {
        this.bankNameTable = bankNameTable;
    }

    public String getReturnCheques() {
        return returnCheques;
    }

    public void setReturnCheques(String returnCheques) {
        this.returnCheques = returnCheques;
    }

    public String getBankAdd() {
        return bankAdd;
    }

    public void setBankAdd(String bankAdd) {
        this.bankAdd = bankAdd;
    }

    public List<SelectItem> getBankNameOption() {
        return bankNameOption;
    }

    public void setBankNameOption(List<SelectItem> bankNameOption) {
        this.bankNameOption = bankNameOption;
    }

    public String getBankClearingHold() {
        return bankClearingHold;
    }

    public void setBankClearingHold(String bankClearingHold) {
        this.bankClearingHold = bankClearingHold;
    }

    public String getRtTotalAmount() {
        return rtTotalAmount;
    }

    public void setRtTotalAmount(String rtTotalAmount) {
        this.rtTotalAmount = rtTotalAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getRtTotalCheque() {
        return rtTotalCheque;
    }

    public void setRtTotalCheque(int rtTotalCheque) {
        this.rtTotalCheque = rtTotalCheque;
    }

    public String getClearingDate() {
        return clearingDate;
    }

    public void setClearingDate(String clearingDate) {
        this.clearingDate = clearingDate;
    }

    public int getTotalCheque() {
        return totalCheque;
    }

    public void setTotalCheque(int totalCheque) {
        this.totalCheque = totalCheque;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Date getInstDate() {
        return instDate;
    }

    public void setInstDate(Date instDate) {
        this.instDate = instDate;
    }

    public String getInstrNo() {
        return instrNo;
    }

    public void setInstrNo(String instrNo) {
        this.instrNo = instrNo;
    }

    public ShadowBalClearanceTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(ShadowBalClearanceTable currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<ShadowBalClearanceTable> getBalClearanceTable() {
        return balClearanceTable;
    }

    public void setBalClearanceTable(List<ShadowBalClearanceTable> balClearanceTable) {
        this.balClearanceTable = balClearanceTable;
    }

    public String getOrderByRegisterDt() {
        return orderByRegisterDt;
    }

    public void setOrderByRegisterDt(String orderByRegisterDt) {
        this.orderByRegisterDt = orderByRegisterDt;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getEmFlags() {
        return emFlags;
    }

    public void setEmFlags(String emFlags) {
        this.emFlags = emFlags;
    }

    public List<SelectItem> getRegisterDtOption() {
        return registerDtOption;
    }

    public void setRegisterDtOption(List<SelectItem> registerDtOption) {
        this.registerDtOption = registerDtOption;
    }

    public List<SelectItem> getClearingModeOption() {
        return clearingModeOption;
    }

    public void setClearingModeOption(List<SelectItem> clearingModeOption) {
        this.clearingModeOption = clearingModeOption;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public List<SelectItem> getDdHoldOption() {
        return ddHoldOption;
    }

    public void setDdHoldOption(List<SelectItem> ddHoldOption) {
        this.ddHoldOption = ddHoldOption;
    }

    public String getDdHoldOpt() {
        return ddHoldOpt;
    }

    public void setDdHoldOpt(String ddHoldOpt) {
        this.ddHoldOpt = ddHoldOpt;
    }

    public String getBnkHoldLen() {
        return bnkHoldLen;
    }

    public void setBnkHoldLen(String bnkHoldLen) {
        this.bnkHoldLen = bnkHoldLen;
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

    /**
     * Creates a new instance of ShadowBalanceClearance
     */
    public ShadowBalanceClearance() {
        try {
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            req = getRequest();
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            otherTxnRemote = (OtherTransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            this.setMessage("");
            registerDtOption = new ArrayList<SelectItem>();
            registerDtOption.add(new SelectItem(""));
            getClearingMode();
            getReasonsForCancel();
            getBankNames();
            registerBranches();
            flag = "true";
            ctsSponsor = ftsRemote.getCodeForReportName("CTS-SPONSOR");
            if (ftsRemote.getCodeForReportName("RELEASE-BNT") == 1) {
                disableReleaseBtn = true;
            } else {
                disableReleaseBtn = false;
            }

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void getReasonsForCancel() {
        reasonForCancel = new ArrayList<SelectItem>();
        try {
            List resultList = otherTxnRemote.getReasonForCancel();
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector vecForcircleTyp = (Vector) resultList.get(i);
//                    reasonForCancel.add(new SelectItem(vecForcircleTyp.get(0).toString(), vecForcircleTyp.get(0).toString()));
                    reasonForCancel.add(new SelectItem(vecForcircleTyp.get(0).toString(), vecForcircleTyp.get(1).toString()));
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void getClearingMode() {
        clearingModeOption = new ArrayList<SelectItem>();
        try {
            List resultList = otherTxnRemote.getClearingOption();
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector vecForcircleTyp = (Vector) resultList.get(i);
                    clearingModeOption.add(new SelectItem(vecForcircleTyp.get(0).toString(), vecForcircleTyp.get(1).toString()));
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void getBankNames() {
        bankNameOption = new ArrayList<SelectItem>();
        try {
            List bankNameList = new ArrayList();
            bankNameList = otherTxnRemote.getBankName();
            for (int i = 0; i < bankNameList.size(); i++) {
                Vector ele = (Vector) bankNameList.get(i);
                for (Object ee : ele) {
                    bankNameOption.add(new SelectItem(ee.toString(), ee.toString()));
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void registerDtClearrance() {
        registerDtOption = new ArrayList<SelectItem>();
        this.setMessage("");
        if (this.branch == null || this.branch.equals("")) {
            this.setMessage("Please Select Branch");
            return;
        }
        if (this.emFlags.equalsIgnoreCase("--SELECT--")) {
            this.setMessage("Please Select Circle Mode");
            return;
        }
        try {
//            String registerDtList = otherTxnRemote.loadDate(emFlags, orgnBrCode);
            String registerDtList = otherTxnRemote.loadDate(emFlags, this.branch);
            if (registerDtList.equals("Register Date has not been set for this Clearing Mode.")) {
                this.setMessage(registerDtList);
            } else {
                if (registerDtList.contains("[")) {
                    //String[] values = null;
                    registerDtList = registerDtList.replace("]", "");
                    registerDtList = registerDtList.replace("[", "");
                    String[] temp = registerDtList.split(",");
                    for (String str : temp) {
                        if (!str.equals("") || str != null) {
                            registerDtOption.add(new SelectItem(str));
                        }
                    }
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void registerBranches() {
        try {
            branchList = new ArrayList<SelectItem>();
            List list = commonRemote.getAlphacodeBasedOnOrgnBranch(orgnBrCode);
            if (list.isEmpty()) {
                this.setMessage("Please define branchmaster detail.");
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                branchList.add(new SelectItem(ele.get(1).toString().trim().length() < 2 ? "0"
                        + ele.get(1).toString().trim() : ele.get(1).toString().trim(), ele.get(0).toString()));
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void registerDateLostFocus() {
        bankClearingHoldRel = "NO";
        Double amt = 0.0d;
        int totalCheq = 0;
        Double amtReturn = 0.0d;
        int totalCheqReturn = 0;
        setTotalAmount("0.00");
        setTotalCheque(0);
        balClearanceTable = new ArrayList<ShadowBalClearanceTable>();
        balReturnTable = new ArrayList<ShadowBalClearForReturn>();
        this.setMessage("");
        setClearingDate("");
        if (branch == null || this.branch.equals("")) {
            this.setMessage("Please select Branch");
            return;
        }
        if (this.emFlags.equalsIgnoreCase("--SELECT--")) {
            this.setMessage("Please select circle mode");
            return;
        }
        registerDate = registerDate.trim().toString();
        String dd = registerDate.substring(0, 2);
        String mm = registerDate.substring(3, 5);
        String yy = registerDate.substring(6, 10);
        String rDate = yy + mm + dd;
        if ((rDate == null) || (rDate.equalsIgnoreCase(""))) {
            return;
        } else if (registerDate.equals("--SELECT--")) {
            return;
        }
        try {
//            orderRegisterDt = otherTxnRemote.registerCboDateLostFocus(emFlags, rDate, orderByRegisterDt, orgnBrCode);
            orderRegisterDt = otherTxnRemote.registerCboDateLostFocus(emFlags, rDate, orderByRegisterDt, this.branch);
            List tableDetail = new ArrayList();
            List txnTable = new ArrayList();
            if (orderRegisterDt.contains(":")) {
                String[] values1 = null;
                String spliter1 = ": ";
                values1 = orderRegisterDt.split(spliter1);
                String orderBy = values1[0];
                String cleardDate = values1[1];
                if ((orderBy.equals("Acno")) || (orderBy.equals("txninstno,txnbankname")) || (orderBy.equals("vtot,acno"))) {
//                    tableDetail = otherTxnRemote.getTableData(emFlags, rDate, orderBy, orgnBrCode);
//                    txnTable = otherTxnRemote.getTxnTableData(emFlags, rDate, orgnBrCode);
                    tableDetail = otherTxnRemote.getTableData(emFlags, rDate, orderBy, this.branch);
                    txnTable = otherTxnRemote.getTxnTableData(emFlags, rDate, this.branch);
                    setRtTotalAmount("0.00");
                    setRtTotalCheque(0);
                }
                setClearingDate(cleardDate.substring(6, 8) + "/" + cleardDate.substring(4, 6) + "/" + cleardDate.substring(0, 4));
            } else {
                this.setMessage(orderRegisterDt);
                setClearingDate("Clearing date not set in this date");
                return;
            }
            for (int i = 0; i < tableDetail.size(); i++) {
                Vector ele = (Vector) tableDetail.get(i);
                ShadowBalClearanceTable clear = new ShadowBalClearanceTable();
                String sts = (ele.get(0).toString());
                if (sts.equals("P")) {
                    clear.setTxnStatus(sts);
                } else if (sts.equals("H")) {
                    clear.setTxnStatus(sts);
                } else {
                    String statu = otherTxnRemote.status();
                    clear.setTxnStatus(statu);
                }
                clear.setAcno(ele.get(1).toString());
                clear.setTxnInstDate(ele.get(2).toString());
                clear.setTxnInstNo(ele.get(3).toString());
                String abc = (ele.get(4).toString());
                if (tableDetail.size() < 1) {
                    setTotalAmount("0.00");
                    setTotalCheque(0);
                }
                Double amt1 = Double.parseDouble(abc);
                amt = amt + amt1;
                totalCheq = totalCheq + 1;

                clear.setTxnInstAmt(Double.parseDouble(amt1.toString()));
                clear.setTxnBankName(ele.get(5).toString());
                clear.setRemarks(ele.get(6).toString());
                clear.setvTot(Integer.parseInt(ele.get(7).toString()));
                clear.setTxnBankAddress(ele.get(8).toString());
                //Added by Manish kumar
                clear.setReturnCode(ele.get(9).toString());
                clear.setReturnDesc(ele.get(10).toString());
                //-----
                balClearanceTable.add(clear);
                String abcAmt = amt.toString();
                setTotalAmount(formatter.format(Double.parseDouble(abcAmt)));
                setTotalCheque(totalCheq);
            }
            for (int j = 0; j < txnTable.size(); j++) {
                Vector ele = (Vector) txnTable.get(j);
                ShadowBalClearForReturn returnDet = new ShadowBalClearForReturn();

                returnDet.setAcno(ele.get(0).toString());
                returnDet.setTxnInstNo(ele.get(1).toString());
                returnDet.setTxnInstDate(ele.get(2).toString());
                Double amt2 = Double.parseDouble(ele.get(3).toString());
                amtReturn = amtReturn + amt2;
                totalCheqReturn = totalCheqReturn + 1;
                returnDet.setTxnInstAmt(Double.parseDouble(amt2.toString()));
                returnDet.setReasonForCancel(ele.get(4).toString());
                String emFlg = ele.get(5).toString();
                returnDet.setEnFlag(emFlg);
                returnDet.setTxnBankName(ele.get(6).toString());
                returnDet.setTxnBankAddress(ele.get(7).toString());
                returnDet.setVtot(Integer.parseInt(ele.get(8).toString()));
                balReturnTable.add(returnDet);
                String abc = amtReturn.toString();
                setRtTotalAmount(formatter.format(Double.parseDouble(abc)));
                setRtTotalCheque(totalCheqReturn);
            }

            bankClearingHoldRel = "NO";
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }
    NumberFormat formatter = new DecimalFormat("#0.00");

    public void setRowValues() {
        flag = "true";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        try {
            String stats = (currentItem.getTxnStatus());
            String instNo = currentItem.getTxnInstNo();
            String instDt = currentItem.getTxnInstDate();
            registerDate = registerDate.trim().toString();
            String dd = registerDate.substring(0, 2);
            String mm = registerDate.substring(3, 5);
            String yy = registerDate.substring(6, 10);
            String rDate = yy + "" + mm + "" + dd;
            String acnos = currentItem.getAcno();
            vtot = String.valueOf(currentItem.getvTot());
            if (stats.equals("H")) {
                this.setMessage("This Instrument is marked held. First release the held instruments and then return them.");
                resetAllValueAfterSelect();
                return;
            }
            if (ctsSponsor == 2 && !currentItem.getReturnDesc().equals("")) {
                returnBasedOnCtsSponsor(currentItem.getAcno(), currentItem.getTxnInstNo(),
                        ymd.format(sdf.parse(currentItem.getTxnInstDate())), currentItem.getTxnBankName(),
                        currentItem.getReturnCode(), String.valueOf(new BigDecimal(formatter.format(currentItem.getTxnInstAmt()))),
                        vtot);
                return;
            }

//            String maskMsg = otherTxnRemote.maskEdBox1KeyDown(instNo, instDt, emFlags, rDate, acnos, orgnBrCode);
            String maskMsg = otherTxnRemote.maskEdBox1KeyDown(instNo, instDt, emFlags, rDate, acnos, this.branch);
            if (!maskMsg.equals("true")) {
                this.setMessage(maskMsg);
                resetAllValueAfterSelect();
                return;
            }
            setAcno(acnos);
            setBankName(currentItem.getTxnBankName());
            setInstrNo(instNo);
            setInstDate(sdf.parse(instDt));
            setBankAdd(currentItem.getTxnBankAddress());
            setTxtInstAmount(String.valueOf(new BigDecimal(formatter.format(currentItem.getTxnInstAmt()))));
            setReasonsReturn("--SELECT--");
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void returnBasedOnCtsSponsor(String acno, String instrNo, String rDate, String bankName, String reasonDesc,
            String txtInstAmount, String vtot) {
        this.setMessage("");
        if (balClearanceTable.isEmpty()) {
            registerDateLostFocus();
            this.setMessage("Data does not exist for return");
            return;
        }
        try {
            float seqNo = 0;
            int txtYear = 0;
            String returnInfo = otherTxnRemote.getTableDataReasonForReturn(this.emFlags, acno, instrNo, rDate, bankName,
                    reasonDesc, seqNo, Float.parseFloat(txtInstAmount), txtYear, this.branch, Integer.parseInt(vtot));
            if (returnInfo.equals("Cheque Returned Successfully")) {
                loadDataAferReturnedCheque();
                this.setMessage(returnInfo);
                setInstrNo("");
                setTxtInstAmount("0.00");
                setAcno("");
                setBankName("");
                setBankAdd("");
                setReasonsReturn("--SELECT--");
                flag = "false";
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void resetAllValueAfterSelect() {
        setClearingDate("");
        setInstrNo("");
        setTxtInstAmount("0.00");
        setAcno("");
        setEmFlags("--SELECT--");
        setRegisterDate("--SELECT--");
        setBankName("");
        setBankAdd("");
        setReasonsReturn("--SELECT--");
    }

    public void bankNameTableUpdation() {
        if (ddHoldOpt.equalsIgnoreCase("0")) {
            this.setMessage("Please Select Proper Holding Option.");
            return;
        }
        registerDate = registerDate.trim().toString();
        String dd = registerDate.substring(0, 2);
        String mm = registerDate.substring(3, 5);
        String yy = registerDate.substring(6, 10);
        String rDate = yy + "" + mm + "" + dd;
        try {
//            String upDate = otherTxnRemote.gridbankDblClick(emFlags, rDate, currentItem1.getBankName(), orgnBrCode, ddHoldOpt);
            String upDate = otherTxnRemote.gridbankDblClick(emFlags, rDate, currentItem1.getBankName(), this.branch, ddHoldOpt);
            if (upDate.equals("Release Successfully")) {
                this.setMessage(upDate);
                btnReleaseClearance();
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void grdTxnDblClickUpdation() {
        this.setMessage("");
        try {
            float seq = 0;
            String insDate = currentItem2.getTxnInstDate();
            String dd = insDate.substring(0, 2);
            String mm = insDate.substring(3, 5);
            String yy = insDate.substring(6, 10);
            String fullInstDt = yy + "" + mm + "" + dd;
            String txnInsAmt = String.valueOf(currentItem2.getTxnInstAmt());
//            String upDateTxnDblClick = otherTxnRemote.grdTxnDblClick(currentItem2.getTxnInstNo(), fullInstDt, currentItem2.getAcno(), currentItem2.getVtot(), Float.parseFloat(txnInsAmt), seq, orgnBrCode);
            String upDateTxnDblClick = otherTxnRemote.grdTxnDblClick(currentItem2.getTxnInstNo(), fullInstDt, currentItem2.getAcno(), currentItem2.getVtot(), Float.parseFloat(txnInsAmt), seq, this.branch);
            if (upDateTxnDblClick.equals("1")) {
                loadDataAferReturnedCheque();
                setInstrNo("");
                setTxtInstAmount("0.00");
                setAcno("");
                setBankName("");
                setBankAdd("");
            }

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void close() {
        try {
            setBankClearingHoldRel("NO");
            resetAllValue1();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void btnReleaseClearance() {
        setBankHoldName("");
        if (registerDate.equalsIgnoreCase("--SELECT--")) {
            this.setMessage("Please select the register date");
            return;
        }
        if (ddHoldOpt.equalsIgnoreCase("0")) {
            this.setMessage("Please select Proper Holding Option");
            return;
        }
        registerDate = registerDate.trim().toString();
        bankNameTable = new ArrayList<ShadowBalCleBankNameTable>();
        String dd = registerDate.substring(0, 2);
        String mm = registerDate.substring(3, 5);
        String yy = registerDate.substring(6, 10);
        String rDate = yy + "" + mm + "" + dd;
        if ((rDate == null) || (rDate.equalsIgnoreCase(""))) {
            this.setMessage("Please select the register date");
            return;
        } else if (registerDate.equals("--SELECT--")) {
            this.setMessage("Please select the register date");
            return;
        }
        try {
            bankClearingHoldRel = "YES";
            List bnkNametableDetail = new ArrayList();
//            bnkNametableDetail = otherTxnRemote.getBankNameTableData(rDate, orgnBrCode, ddHoldOpt);
            bnkNametableDetail = otherTxnRemote.getBankNameTableData(rDate, this.branch, ddHoldOpt);
            for (int i = 0; i < bnkNametableDetail.size(); i++) {
                Vector ele = (Vector) bnkNametableDetail.get(i);
                ShadowBalCleBankNameTable bnkName = new ShadowBalCleBankNameTable();

                bnkName.setBankName(ele.get(0).toString());
                bankNameTable.add(bnkName);
            }

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void processValueChange() {
        this.setMessage("");
        try {
            if (returnCheques.equals("--SELECT--")) {
                this.setMessage("Please Select Return Cheque");
                return;
            }
            if (returnCheques.equals("YES")) {
                flag = "false";
            } else {
                flag = "true";
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void onBlurDoYouWantToHold() {
        try {
            this.setMessage("");
            setBankHoldName("");
            if (bankClearingHold.equals("NO")) {
                bankClearingHoldRel = "NO";
            }
            if (bankClearingHold.equals("YES")) {
                bankClearingHoldRel = "YES";
                ddHoldOption = new ArrayList<SelectItem>();
                ddHoldOption.add(new SelectItem("0", "-- Select --"));
                ddHoldOption.add(new SelectItem("HC", "Hold City"));
                ddHoldOption.add(new SelectItem("HB", "Hold Bank"));
                ddHoldOption.add(new SelectItem("BR", "Hold Branch"));
                ddHoldOption.add(new SelectItem("CB", "Particular Bank of City"));
                //btnReleaseClearance();
            }

        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void bankClearingHoldValueChange() {
        this.setMessage("");
        if (this.branch == null || this.branch.equals("")) {
            this.setMessage("Please select Branch");
            return;
        }
        if (this.emFlags.equalsIgnoreCase("--SELECT--")) {
            this.setMessage("Please select circle mode");
            return;
        }
        if (registerDate.equalsIgnoreCase("--SELECT--")) {
            this.setMessage("Please select register date");
            return;
        }
        if (ddHoldOpt.equalsIgnoreCase("0")) {
            this.setMessage("Please select Proper Holding Option");
            return;
        }

        if (bankHoldName.equals("")) {
            this.setMessage("Please enter proper code");
            return;
        }

        if (ddHoldOpt.equalsIgnoreCase("BR")) {
            if (bankHoldName.length() != 9) {
                this.setMessage("Please enter 9 digit Bank Micr Code");
                return;
            }
        } else if (ddHoldOpt.equalsIgnoreCase("CB")) {
            if (bankHoldName.length() != 6) {
                this.setMessage("Please enter 6 digit of City and Bank Code");
                return;
            }
        } else {
            if (bankHoldName.length() != 3) {
                this.setMessage("Please enter 3 digit Bank Micr Code");
                return;
            }
        }

        registerDate = registerDate.trim().toString();
        String dd = registerDate.substring(0, 2);
        String mm = registerDate.substring(3, 5);
        String yy = registerDate.substring(6, 10);
        String rDate = yy + "" + mm + "" + dd;
        try {
            returnBankName = otherTxnRemote.cmbBankNameValueChangeClearance(emFlags, rDate, bankHoldName, this.branch, ddHoldOpt);
            if (returnBankName.equals("")) {
                this.setMessage(returnBankName);
                close();
            } else {
                btnReleaseClearance();
                this.setMessage(returnBankName);
            }

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void reasonForReturnProcessValueChange() {
        this.setMessage("");
        if ((instrNo == null) || (instrNo.equalsIgnoreCase(""))) {
            this.setMessage("Please enter inst. no");
            return;
        }
        if (instDate == null) {
            this.setMessage("Please enter inst. date");
            return;
        }
        if ((txtInstAmount == null) || (txtInstAmount.equalsIgnoreCase(""))) {
            this.setMessage("Please enter inst. amount");
            return;
        }
        if (txtInstAmount.equals("0.00")) {
            this.setMessage("Please enter inst. amount");
            return;
        }
        if (reasonsReturn.equals("--SELECT--")) {
            this.setMessage("Please select Reason For Return");
            return;
        }
        if ((acno == null) || (acno.equalsIgnoreCase(""))) {
            reasonPenalDisplay = "false";
        } else {
            reasonPenalDisplay = "True";
        }
        registerDate = registerDate.trim().toString();
        String dd = registerDate.substring(0, 2);
        String mm = registerDate.substring(3, 5);
        String yy = registerDate.substring(6, 10);
        String rDate = yy + "" + mm + "" + dd;
        try {
            float seq = 0;
//            String reason = otherTxnRemote.cboReasonKeyDown(acno, instrNo, seq, bankName, rDate, orgnBrCode);
            String reason = otherTxnRemote.cboReasonKeyDown(acno, instrNo, seq, bankName, rDate, this.branch);
            if ((reason.equalsIgnoreCase("There are more instance exist of this instrument. Please mark these instruments as returned (if required)")) || (reason.equals("No"))) {
                this.setMessage(reason);
            } else {
                this.setMessage(reason);
            }

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void reasonForReturnInformation() {
        this.setMessage("");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String rDate = sdf.format(instDate);
        if (balClearanceTable.size() < 1) {
            registerDateLostFocus();
            this.setMessage("Data does not exist for return");
            return;
        }
        try {
            float seqNo = 0;
            int txtYear = 0;
//            String returnInfo = otherTxnRemote.getTableDataReasonForReturn(emFlags, acno, instrNo, rDate, bankName, reasonsReturn, seqNo, Float.parseFloat(txtInstAmount), txtYear, orgnBrCode, Integer.parseInt(vtot));
            String returnInfo = otherTxnRemote.getTableDataReasonForReturn(emFlags, acno, instrNo, rDate, bankName, reasonsReturn, seqNo, Float.parseFloat(txtInstAmount), txtYear, this.branch, Integer.parseInt(vtot));
            if (returnInfo.equals("Cheque Returned Successfully")) {
                loadDataAferReturnedCheque();
                this.setMessage(returnInfo);
                setInstrNo("");
                setTxtInstAmount("0.00");
                setAcno("");
                setBankName("");
                setBankAdd("");
                setReasonsReturn("--SELECT--");
                flag = "false";
            }

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    private void loadDataAferReturnedCheque() {
        bankClearingHoldRel = "NO";
        Double amt = 0.0d;
        int totalCheq = 0;
        Double amtReturn = 0.0d;
        int totalCheqReturn = 0;
        setTotalAmount("0.00");
        setTotalCheque(0);
        balClearanceTable = new ArrayList<ShadowBalClearanceTable>();
        balReturnTable = new ArrayList<ShadowBalClearForReturn>();
        this.setMessage("");
        setClearingDate("");
        if (this.emFlags.equalsIgnoreCase("--SELECT--")) {
            this.setMessage("Please Select Circle Mode");
            return;
        }
        registerDate = registerDate.trim().toString();
        String dd = registerDate.substring(0, 2);
        String mm = registerDate.substring(3, 5);
        String yy = registerDate.substring(6, 10);
        String rDate = yy + mm + dd;
        if ((rDate == null) || (rDate.equalsIgnoreCase(""))) {
            return;
        } else if (registerDate.equals("--SELECT--")) {
            return;
        }
        try {
//            orderRegisterDt = otherTxnRemote.registerCboDateLostFocus(emFlags, rDate, orderByRegisterDt, orgnBrCode);
            orderRegisterDt = otherTxnRemote.registerCboDateLostFocus(emFlags, rDate, orderByRegisterDt, this.branch);
            List tableDetail = new ArrayList();
            List txnTable = new ArrayList();
            if (orderRegisterDt.contains(":")) {
                String[] values1 = null;
                String spliter1 = ": ";
                values1 = orderRegisterDt.split(spliter1);
                String orderBy = values1[0];
                String cleardDate = values1[1];
                if ((orderBy.equals("Acno")) || (orderBy.equals("txninstno,txnbankname")) || (orderBy.equals("vtot,acno"))) {
//                    tableDetail = otherTxnRemote.getTableData(emFlags, rDate, orderBy, orgnBrCode);
//                    txnTable = otherTxnRemote.getTxnTableData(emFlags, rDate, orgnBrCode);
                    tableDetail = otherTxnRemote.getTableData(emFlags, rDate, orderBy, this.branch);
                    txnTable = otherTxnRemote.getTxnTableData(emFlags, rDate, this.branch);
                    setRtTotalAmount("0.00");
                    setRtTotalCheque(0);
                }
                setClearingDate(cleardDate.substring(6, 8) + "/" + cleardDate.substring(4, 6) + "/" + cleardDate.substring(0, 4));
            } else {
                this.setMessage(orderRegisterDt);
                setClearingDate("Clearing date not set in this date");
                return;
            }
            for (int i = 0; i < tableDetail.size(); i++) {
                Vector ele = (Vector) tableDetail.get(i);
                ShadowBalClearanceTable clear = new ShadowBalClearanceTable();
                String sts = (ele.get(0).toString());
                if (sts.equals("P")) {
                    clear.setTxnStatus(sts);
                } else if (sts.equals("H")) {
                    clear.setTxnStatus(sts);
                } else {
                    String statu = otherTxnRemote.status();
                    clear.setTxnStatus(statu);
                }
                clear.setAcno(ele.get(1).toString());
                clear.setTxnInstDate(ele.get(2).toString());
                clear.setTxnInstNo(ele.get(3).toString());
                String abc = (ele.get(4).toString());
                if (tableDetail.size() < 1) {
                    setTotalAmount("0.00");
                    setTotalCheque(0);
                }
                Double amt1 = Double.parseDouble(abc);
                amt = amt + amt1;
                totalCheq = totalCheq + 1;

                clear.setTxnInstAmt(Double.parseDouble(amt1.toString()));
                clear.setTxnBankName(ele.get(5).toString());
                clear.setRemarks(ele.get(6).toString());
                clear.setvTot(Integer.parseInt(ele.get(7).toString()));
                clear.setTxnBankAddress(ele.get(8).toString());
                //Added by Manish kumar
                clear.setReturnCode(ele.get(9).toString());
                clear.setReturnDesc(ele.get(10).toString());
                //-----
                balClearanceTable.add(clear);
                String abcAmt = amt.toString();
                setTotalAmount(formatter.format(Double.parseDouble(abcAmt)));
                setTotalCheque(totalCheq);
            }
            for (int j = 0; j < txnTable.size(); j++) {
                Vector ele = (Vector) txnTable.get(j);
                ShadowBalClearForReturn returnDet = new ShadowBalClearForReturn();

                returnDet.setAcno(ele.get(0).toString());
                returnDet.setTxnInstNo(ele.get(1).toString());
                returnDet.setTxnInstDate(ele.get(2).toString());
                Double amt2 = Double.parseDouble(ele.get(3).toString());
                amtReturn = amtReturn + amt2;
                totalCheqReturn = totalCheqReturn + 1;

                returnDet.setTxnInstAmt(Double.parseDouble(amt2.toString()));
                returnDet.setReasonForCancel(ele.get(4).toString());
                String emFlg = ele.get(5).toString();

                returnDet.setEnFlag(emFlg);
                returnDet.setTxnBankName(ele.get(6).toString());
                returnDet.setTxnBankAddress(ele.get(7).toString());
                returnDet.setVtot(Integer.parseInt(ele.get(8).toString()));

                balReturnTable.add(returnDet);
                String abc = amtReturn.toString();
                setRtTotalAmount(formatter.format(Double.parseDouble(abc)));
                setRtTotalCheque(totalCheqReturn);
            }

            bankClearingHoldRel = "NO";
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void clearButton() {
        this.setMessage("");
        registerDate = registerDate.trim().toString();
        String dd = registerDate.substring(0, 2);
        String mm = registerDate.substring(3, 5);
        String yy = registerDate.substring(6, 10);
        String rDate = yy + "" + mm + "" + dd;
        if ((rDate == null) || (rDate.equalsIgnoreCase(""))) {
            this.setMessage("Please Select The Register Date");
            return;
        } else if (registerDate.equals("--SELECT--")) {
            this.setMessage("Please Select The Register Date");
            return;
        }
        if (this.branch == null || this.branch.equals("")) {
            this.setMessage("Please Select Branch");
            return;
        }
        if (emFlags.equals("--SELECT--")) {
            this.setMessage("Please Select The Clearing Mode");
            return;
        }
        for (int i = 0; i < balClearanceTable.size(); i++) {
            if (!balClearanceTable.get(i).getReturnCode().equalsIgnoreCase("")) {
                this.setMessage("Sorry, not allow to clear. First of all Return, those have 'Return Reason' !");
                return;
            }
        }
//        if(!currentItem.getReturnCode().equalsIgnoreCase("")){
//            this.setMessage("Sorry, This Ac No. not allow to Clear couse for Return Reason !");
//            return;
//        }
        try {
//            orderRegisterDt = otherTxnRemote.registerCboDateLostFocus(emFlags, rDate, orderByRegisterDt, orgnBrCode);
            orderRegisterDt = otherTxnRemote.registerCboDateLostFocus(emFlags, rDate, orderByRegisterDt, this.branch);
            if (!orderRegisterDt.contains(":")) {
                this.setMessage(orderRegisterDt);
                return;
            }

//            String clear = otherTxnRemote.clgOutward3day(userName, rDate, emFlags, orgnBrCode);
            String clear = otherTxnRemote.clgOutward3day(userName, rDate, emFlags, this.branch);
            if (clear.equalsIgnoreCase("TRUE")) {
                this.setMessage("Transaction Inserted Successfully");
                balClearanceTable.clear();
                balReturnTable.clear();
                resetAllValue();
            } else {
                this.setMessage(clear);
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void resetAllValue() {
        setClearingDate("");
        setInstrNo("");
        setTxtInstAmount("0.00");
        setAcno("");
        setEmFlags("--SELECT--");
        setRegisterDate("--SELECT--");
        setBankName("");
        setBankAdd("");
        setReasonsReturn("--SELECT--");
        setTotalAmount("0.00");
        setTotalCheque(0);
        setRtTotalAmount("0.00");
        setRtTotalCheque(0);
        balClearanceTable = new ArrayList<ShadowBalClearanceTable>();
        balReturnTable = new ArrayList<ShadowBalClearForReturn>();
    }

    public void resetAllValue1() {
        this.setMessage("");
        setClearingDate("");
        setInstrNo("");
        setTxtInstAmount("0.00");
        setAcno("");
        setEmFlags("--SELECT--");
        setRegisterDate("--SELECT--");
        setBankName("");
        setBankAdd("");
        setReasonsReturn("--SELECT--");
        setTotalAmount("0.00");
        setTotalCheque(0);
        setRtTotalAmount("0.00");
        setRtTotalCheque(0);
        setBankClearingHold("NO");
        balClearanceTable = new ArrayList<ShadowBalClearanceTable>();
        balReturnTable = new ArrayList<ShadowBalClearForReturn>();
    }

    public String exitFrm() {
        resetAllValue1();
        return "case1";
    }

    public void getCurrentTableData() {
        this.setMessage("");
        if (this.branch == null || this.branch.equals("")) {
            this.setMessage("Please Select Branch");
            return;
        }
        if (this.emFlags.equalsIgnoreCase("--SELECT--")) {
            this.setMessage("Please Select Circle Mode");
            return;
        }
        registerDate = registerDate.trim().toString();
        String dd = registerDate.substring(0, 2);
        String mm = registerDate.substring(3, 5);
        String yy = registerDate.substring(6, 10);
        String rDate = yy + "" + mm + "" + dd;
        if ((rDate == null) || (rDate.equalsIgnoreCase(""))) {
            this.setMessage("Please Select Register Date");
            return;
        } else if (registerDate.equals("--SELECT--")) {
            this.setMessage("Please Select Register Date");
            return;
        }
        if ((instrNo == null) || (instrNo.equalsIgnoreCase(""))) {
            this.setMessage("Please Enter Inst. No");
            return;
        }
        if (instDate == null) {
            this.setMessage("Please Enter Inst. Date");
            return;
        }
        if ((txtInstAmount == null) || (txtInstAmount.equalsIgnoreCase(""))) {
            this.setMessage("Please Enter Inst. Amt");
            return;
        }
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        Matcher txtInstAmountCheck = p.matcher(this.txtInstAmount);
        if (!txtInstAmountCheck.matches()) {
            this.setMessage("Please Enter Valid Inst. Amt");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String instDt = sdf.format(this.instDate);
        try {
//            List tableDetail = otherTxnRemote.getCurrentTableData(emFlags, rDate, instrNo, instDt, txtInstAmount, orgnBrCode);
            List tableDetail = otherTxnRemote.getCurrentTableData(emFlags, rDate, instrNo, instDt, txtInstAmount, this.branch);
            if (tableDetail.isEmpty()) {
                this.setMessage("Entry does not exist");
                return;
            } else {
                for (int i = 0; i < tableDetail.size(); i++) {
                    Vector ele = (Vector) tableDetail.get(i);
                    if (ele.get(0).toString().equals("H")) {
                        this.setMessage("This Instrument is marked held. First release the held instruments and then return them.");
                        resetAllValueAfterSelect();
                        return;
                    }
                    setAcno(ele.get(1).toString());
                    setBankName(ele.get(5).toString());
                    setBankAdd(ele.get(8).toString());
                    vtot = (ele.get(7).toString());
                    setReasonsReturn("--SELECT--");
                }
            }

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void setLength() {
        if (ddHoldOpt.equalsIgnoreCase("BR")) {
            bnkHoldLen = "9";
        } else if (ddHoldOpt.equalsIgnoreCase("CB")) {
            bnkHoldLen = "6";
        } else {
            bnkHoldLen = "3";
        }
    }
}
