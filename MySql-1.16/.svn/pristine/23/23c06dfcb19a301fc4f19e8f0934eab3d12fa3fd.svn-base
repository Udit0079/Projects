/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.txn;

import com.cbs.dto.DDSDenominationGrid;
import com.cbs.web.pojo.txn.CashDrItem;
import com.cbs.dto.TxnDetailBean;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.facade.txn.TxnAuthorizationManagementFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.Base64;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Vector;

/**
 * <p>
 * Page bean that corresponds to a similarly named JSP page. This class contains
 * component definitions (and initialization code) for all components that you
 * have defined on this page, as well as lifecycle methods and event handlers
 * where you may add behavior to respond to incoming events.</p>
 *
 * @version CashWithdrawal.java
 * @version Created on May 26, 2010, 10:32:27 AM
 * @author Administrator
 */
public class CashWithdrawal {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    private String orgnBrCode;
    private String todayDate;
    private String userName;
    private List<CashDrItem> cashDrItemList;
    CashDrItem currentItem;
    private HttpServletRequest req;
    private static String REGEX;
    private static String INPUT;
    private static String REPLACE;
    int currentRow;
    StringBuffer sb = new StringBuffer();
    private String message;
    boolean authorizeValue;
    boolean deleteValue;
    String imageData;
    int currItem;
    String accNo;
//    String errorMsg;
    private List<TxnDetailBean> txnDetailList;
    private List<TxnDetailBean> txnDetailUnAuthList;
    BigDecimal openBalance;
    private BigDecimal presentBalance;
    private TxnDetailBean txnDetailGrid;
    private TxnDetailBean txnDetailUnAuthGrid;
    String accountNo;
    String accountName;
    String opMode;
    String openDate;
    String jtName;
    String accInstruction;
    String authFlag;
    private final String jndiHomeName = "TxnAuthorizationManagementFacade";
    private TxnAuthorizationManagementFacadeRemote txnAuthRemote = null;
    private final String jndiHomeNameTxn = "TransactionManagementFacade";
    private TransactionManagementFacadeRemote txnRemote = null;
    private final String jndiHomeNameFts = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    private final String jndiHomeNameIbt = "InterBranchTxnFacade";
    private InterBranchTxnFacadeRemote ibtFacade = null;

    private String custId;
    private String riskCategorization;
    private String annualTurnover;
    private String custPanNo;
    private String profession;
    private String dpLimit;
    private List<DDSDenominationGrid> denominationTable;
    private double pRecNo;

    public String getDpLimit() {
        return dpLimit;
    }

    public void setDpLimit(String dpLimit) {
        this.dpLimit = dpLimit;
    }

    public boolean isAuthorizeValue() {
        return authorizeValue;
    }

    public void setAuthorizeValue(boolean authorizeValue) {
        this.authorizeValue = authorizeValue;
    }

    public boolean isDeleteValue() {
        return deleteValue;
    }

    public void setDeleteValue(boolean deleteValue) {
        this.deleteValue = deleteValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CashDrItem getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(CashDrItem currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<CashDrItem> getCashDrItemList() {
        return cashDrItemList;
    }

    public void setCashDrItemList(List<CashDrItem> cashDrItemList) {
        this.cashDrItemList = cashDrItemList;
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

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public int getCurrItem() {
        return currItem;
    }

    public void setCurrItem(int currItem) {
        this.currItem = currItem;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public BigDecimal getOpenBalance() {
        return openBalance;
    }

    public void setOpenBalance(BigDecimal openBalance) {
        this.openBalance = openBalance;
    }

    public BigDecimal getPresentBalance() {
        return presentBalance;
    }

    public void setPresentBalance(BigDecimal presentBalance) {
        this.presentBalance = presentBalance;
    }

    public TxnDetailBean getTxnDetailGrid() {
        return txnDetailGrid;
    }

    public void setTxnDetailGrid(TxnDetailBean txnDetailGrid) {
        this.txnDetailGrid = txnDetailGrid;
    }

    public List<TxnDetailBean> getTxnDetailList() {
        return txnDetailList;
    }

    public void setTxnDetailList(List<TxnDetailBean> txnDetailList) {
        this.txnDetailList = txnDetailList;
    }

    public TxnDetailBean getTxnDetailUnAuthGrid() {
        return txnDetailUnAuthGrid;
    }

    public void setTxnDetailUnAuthGrid(TxnDetailBean txnDetailUnAuthGrid) {
        this.txnDetailUnAuthGrid = txnDetailUnAuthGrid;
    }

    public List<TxnDetailBean> getTxnDetailUnAuthList() {
        return txnDetailUnAuthList;
    }

    public void setTxnDetailUnAuthList(List<TxnDetailBean> txnDetailUnAuthList) {
        this.txnDetailUnAuthList = txnDetailUnAuthList;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }

    public String getAccInstruction() {
        return accInstruction;
    }

    public void setAccInstruction(String accInstruction) {
        this.accInstruction = accInstruction;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getJtName() {
        return jtName;
    }

    public void setJtName(String jtName) {
        this.jtName = jtName;
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

    public String getAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(String authFlag) {
        this.authFlag = authFlag;
    }

    public String getAnnualTurnover() {
        return annualTurnover;
    }

    public void setAnnualTurnover(String annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
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

    public String getRiskCategorization() {
        return riskCategorization;
    }

    public void setRiskCategorization(String riskCategorization) {
        this.riskCategorization = riskCategorization;
    }

    public double getpRecNo() {
        return pRecNo;
    }

    public void setpRecNo(double pRecNo) {
        this.pRecNo = pRecNo;
    }

    public List<DDSDenominationGrid> getDenominationTable() {
        return denominationTable;
    }

    public void setDenominationTable(List<DDSDenominationGrid> denominationTable) {
        this.denominationTable = denominationTable;
    }

    // </editor-fold>
    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    /**
     * <p>
     * Construct a new Page bean instance.</p>
     */
    public CashWithdrawal() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            setUserName(req.getRemoteUser());
            txnAuthRemote = (TxnAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTxn);
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFts);
            ibtFacade = (InterBranchTxnFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameIbt);
            getCashDr();
            deleteValue = true;
            authorizeValue = true;

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }

    }

    private void getCashDr() {
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat dmyOne = new SimpleDateFormat("dd/MM/yyyy");
            cashDrItemList = new ArrayList<CashDrItem>();
            List resultList = new ArrayList();
            resultList = txnAuthRemote.getUnAuthCashdr(orgnBrCode);
            if (resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector element = (Vector) resultList.get(i);
                    CashDrItem cashDrItem = new CashDrItem();

                    cashDrItem.setsNo(String.valueOf(i + 1));
                    cashDrItem.setTokenNo(Float.parseFloat(element.elementAt(0).toString()));
                    cashDrItem.setSubTokenNo(element.elementAt(1).toString());

                    cashDrItem.setAcNo(element.elementAt(2).toString());
                    cashDrItem.setCustomerName(element.elementAt(3).toString());
                    cashDrItem.setInstAmt(Double.parseDouble(element.elementAt(4).toString()));

                    cashDrItem.setBalAfterInst(Double.parseDouble(element.elementAt(5).toString()));
                    cashDrItem.setInstNo(element.elementAt(6).toString());
                    cashDrItem.setEnterBy(element.elementAt(7).toString());

                    cashDrItem.setAuthStatus(element.elementAt(8).toString());
                    cashDrItem.setRecNo(Float.parseFloat(element.elementAt(9).toString()));
                    cashDrItem.setDetails(element.elementAt(10).toString());

                    int index = element.elementAt(10).toString().indexOf("~`");
                    if (index > -1) {
                        cashDrItem.setViewDetails(element.elementAt(10).toString().substring(0, index));
                    } else {
                        cashDrItem.setViewDetails(element.elementAt(10).toString());
                    }
                    
                    index = element.elementAt(10).toString().indexOf("$#");
                    if (index > -1) {
                        cashDrItem.setViewDetails(element.elementAt(10).toString().substring(0, index));
                    } else {
                        cashDrItem.setViewDetails(element.elementAt(10).toString());
                    }
                    cashDrItem.setIy(element.elementAt(11).toString());
                    cashDrItem.setOrgnCode(element.elementAt(12).toString());
                    cashDrItem.setDestCode(element.elementAt(13).toString());
                    if (!element.elementAt(6).toString().equals("")) {
                        cashDrItem.setInstDt(element.elementAt(14).toString());
                    }

                    cashDrItem.setValueDt(element.elementAt(15).toString());
                    
                    cashDrItem.setRelatedTo(txnAuthRemote.getTranDescription(Integer.parseInt(element.elementAt(16).toString())));
                    
                    cashDrItem.setTdsAmt(new BigDecimal(element.elementAt(18).toString()));
                    
                    cashDrItemList.add(cashDrItem);
                }
            }

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void changeAuth() {
        this.setMessage("");
        accountNo = currentItem.getAcNo();
        pRecNo = currentItem.getRecNo();
        CashDrItem item = cashDrItemList.get(currentRow);

        if (item.getAuthStatus().equalsIgnoreCase("N")) {
            if (item.getEnterBy().equalsIgnoreCase(this.getUserName())) {
                this.setMessage("You cannot authorize your own entry");
                //item.setAuthStatus("Y");
                authorizeValue = true;
                deleteValue = false;
            } else {
                boolean found1 = false;
                for (CashDrItem item1 : cashDrItemList) {
                    if (item1.getAuthStatus().equalsIgnoreCase("Y")) {
                        found1 = true;
                    }
                }
                if (found1) {
                    this.setMessage("Only one transction can be authorize at one time.");
                } else {
                    item.setAuthStatus("Y");
                    cashDrItemList.remove(currentRow);
                    cashDrItemList.add(currentRow, item);
                    authorizeValue = false;
                    deleteValue = false;
                    authFlag = item.getAuthStatus();
                    System.out.println("authFlag     " + authFlag);
                }
            }
        } else if (item.getAuthStatus().equalsIgnoreCase("Y")) {
            item.setAuthStatus("N");
            cashDrItemList.remove(currentRow);
            cashDrItemList.add(currentRow, item);
            authorizeValue = true;
            deleteValue = true;
            authFlag = item.getAuthStatus();
            System.out.println("authFlag     " + authFlag);

        }
    }

    public void btnAuthAction() {
        int msgNo = 0;
        int msg_Bill_Start = 0;
        String status = null;
        int msg_Bill_Po = 0;
        int msg_Bill_end = 0;
        String date = null;
        float seqNo = 0.0f;
        String instNo = null;
        String billType = null;
        String acno = null;
        String custName = null;
        String payableAt = null;
        double amount = 0.0d;
        String dt1 = null, valueDate = null, InstrDt = null;
        String originDt = null;
        int timeLimit = 0;
        double comm = 0.0d;
        int tranType = 0;
        int ty = 0;
        String inFavourOf = null;
        String lastUpdateBy = null;
        this.setMessage("");
        try {
            for (int j = 0; j < cashDrItemList.size(); j++) {
                CashDrItem items = cashDrItemList.get(j);
                if (items.getAuthStatus().equals("Y")) {
                    /**
                     * **Checking of user limit of authorization Added by Dinesh****
                     */
                    List userAuthLimit = txnAuthRemote.getUserAuthorizationLimitCash(userName, orgnBrCode);
                    if (userAuthLimit.size() > 0) {
                        for (int t = 0; t < userAuthLimit.size(); t++) {
                            Vector element = (Vector) userAuthLimit.get(t);
                            double userLimit = Double.parseDouble(element.get(0).toString());
                            if (userLimit == 0.0) {
                                this.setMessage("Your passing limit is less than withdrawal amount. You can not authorize this transaction");
                                return;
                            } else if (items.getInstAmt() > userLimit) {
                                this.setMessage("Your passing limit is less than withdrawal amount. You can not authorize this transaction");
                                return;
                            }
                        }
                    } else {
                        this.setMessage("Your passing limit is less than withdrawal amount. You can not authorize this transaction");
                        return;
                    }
                    /**
                     * **Addition end here*****
                     */
                    if (items.getOrgnCode().equals(items.getDestCode())) {
                        //Single branch handling.
                        acno = items.getAcNo();
                        String enterBy = items.getEnterBy();
                        valueDate = items.getValueDt().substring(6, 10) + items.getValueDt().substring(3, 5) + items.getValueDt().substring(0, 2);
                        if (items.getInstDt() != null) {
                            InstrDt = items.getInstDt().substring(6, 10) + items.getInstDt().substring(3, 5) + items.getInstDt().substring(0, 2);
                        }
                        if (items.getDetails().indexOf("~`") > -1) {
                            String[] elements = items.getDetails().split("~`");

                            msgNo = Integer.parseInt(elements[1]);
                            msg_Bill_Start = 110;
                            status = elements[11];
                            msg_Bill_Po = Integer.parseInt(elements[1]);

                            msg_Bill_end = 140;
                            date = elements[9];
                            seqNo = Float.parseFloat(elements[2]);
                            instNo = elements[3];
                            billType = elements[4];

                            acno = elements[5];
                            custName = elements[6];
                            payableAt = elements[7];
                            amount = Double.parseDouble(elements[8]);
                            dt1 = elements[9];

                            originDt = elements[10];
                            timeLimit = Integer.parseInt(elements[12]);
                            comm = Double.parseDouble(elements[13]);
                            tranType = Integer.parseInt(elements[14]);

                            ty = Integer.parseInt(elements[15]);
                            inFavourOf = elements[16];
                            enterBy = elements[17];
                            lastUpdateBy = elements[18];

                        }
//                        ctx = new InitialContext();
//                        cashWithdrawlAuthRemote = (CashWithdrawlAuthRemote) ctx.lookup(CashWithdrawlAuthRemote.class.getName());
                        String authFinalMsg = txnAuthRemote.cbsAuthCashDr(items.getRecNo(), userName, msgNo,
                                msg_Bill_Start, status, msg_Bill_Po, msg_Bill_end, date, seqNo, instNo, billType, acno,
                                custName, payableAt, items.getInstAmt(), dt1, originDt, timeLimit, comm, tranType, ty,
                                inFavourOf, enterBy, lastUpdateBy, items.getOrgnCode(), valueDate, InstrDt,items.getTdsAmt());

                        if (authFinalMsg.equalsIgnoreCase("Authorization over.") || authFinalMsg.equalsIgnoreCase("TRUE")) {
                            getCashDr();
                            this.setMessage("Authorization over.");
                        } else {
                            System.out.println("authFinalMsg.substring(0,13) -->" + authFinalMsg.substring(0, 13));
                            if (authFinalMsg.substring(0, 13).equalsIgnoreCase("Authorization")) {
                                getCashDr();
                                this.setMessage(authFinalMsg);
                            } else {
                                this.setMessage(authFinalMsg);
                            }
                        }

                        deleteValue = true;
                        authorizeValue = true;

                    } else {
                        //  Multiple branch handling coding
                        String msg = txnAuthRemote.cbsCashDrDestAuth(items.getRecNo(), this.userName, items.getEnterBy(),
                                items.getOrgnCode(),items.getTdsAmt());
                        if (msg.equalsIgnoreCase("Authorization over.") || msg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                            getCashDr();
                            this.setMessage("Autorization over.");
                        } else {
                            this.setMessage(msg);
                        }
                        deleteValue = true;
                        authorizeValue = true;
                    }
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void btnDeleteAction() {
        try {
            this.setMessage("");
            Date d1 = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dt = sdf.format(d1);

            String s1 = txnAuthRemote.CbsAuthCashDrDeletion(currentItem.getRecNo(), currentItem.getInstNo(), currentItem.getAcNo(),
                    currentItem.getInstAmt(), dt, currentItem.getCustomerName(), currentItem.getSubTokenNo(), currentItem.getTokenNo(), dt,
                    currentItem.getEnterBy(), this.userName, currentItem.getOrgnCode());
            this.setMessage(s1);
            getCashDr();
            deleteValue = true;
            authorizeValue = true;
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String btnRefreshAction() {
        this.setMessage("");
        deleteValue = true;
        authorizeValue = true;
        getCashDr();
        this.imageData = null;
        this.setAccountNo("");
        this.setOpenBalance(new BigDecimal("0"));
        this.setPresentBalance(new BigDecimal("0"));
        this.setpRecNo(0);
        return null;
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    public void createSignature(OutputStream out, Object data) throws IOException {

        if (null == data) {
            return;
        }
        if (imageData != null) {
            byte[] sigBytes = Base64.decode(imageData);
            out.write(sigBytes);
        }
    }

    public void getSignatureDetail() {
        try {
            this.imageData = null;
            int Toper;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String signature;
            accNo = currentItem.getAcNo();

            accountName = currentItem.getCustomerName();
            List list = txnAuthRemote.getDataForSignature(accNo);

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
                imageData = CbsUtil.readImageFromXMLFile(new File(filePath));
//                this.setErrorMsg("");
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (FileNotFoundException e) {
            this.setMessage("");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void acctViewAuthUnAuth() {
//        String array[] = null;
//        String array1[] = null;
//        int i, j, k, l, m, n, o, p, q;

        try {
            txnDetailList = new ArrayList<TxnDetailBean>();
            txnDetailUnAuthList = new ArrayList<TxnDetailBean>();
            if (this.accountNo == null) {
                this.setMessage("Please Double Click On Row To View A/C Details");
                return;
            }
            if (this.accountNo.equals("")) {
                this.setMessage("Please Double Click On Row To View A/C Details");
                return;
            }
            this.setMessage("");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar now = Calendar.getInstance();
            now.roll(Calendar.MONTH, false);
            String oneMonthBeforeDate = sdf.format(now.getTime());

            String openBal = txnRemote.getOpeningBalF4(accountNo, oneMonthBeforeDate);
            if (!openBal.equals("FALSE")) {

                this.setOpenBalance(new BigDecimal(openBal));
            }

            String prsntBal = txnRemote.getOpeningBalF4(accountNo, todayDate);
            if (!prsntBal.equals("FALSE")) {

                this.setPresentBalance(new BigDecimal(prsntBal));
            }

           // BigDecimal netAmount = this.getPresentBalance();
            txnDetailList = txnRemote.accViewAuth(accountNo, todayDate, orgnBrCode);
            txnDetailUnAuthList = txnRemote.accViewUnauth(accountNo, todayDate, orgnBrCode);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void acctDenoView() {
        try {
            int txnCashParam = 0;
            String cashMode;
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

            if (this.accountNo == null) {
                this.setMessage("Please Double Click On Row To View A/C Details");
                return;
            }
            if (this.accountNo.equals("")) {
                this.setMessage("Please Double Click On Row To View A/C Details");
                return;
            }

            txnCashParam = Integer.parseInt(ftsRemote.getCodeByReportName("TELLER_DENOMINITION"));
            cashMode = ftsRemote.getCashMode(getOrgnBrCode());

            denominationTable = new ArrayList<DDSDenominationGrid>();
            if (cashMode.equalsIgnoreCase("N") && (txnCashParam == 1)) {
                for (int j = 0; j < cashDrItemList.size(); j++) {
                    CashDrItem items = cashDrItemList.get(j);
                    if (items.getAuthStatus().equalsIgnoreCase("Y")) {
                        List denoData = txnAuthRemote.getDenoDateToShow(accountNo, pRecNo, ymd.format(new Date()));
                        for (int i = 0; i < denoData.size(); i++) {
                            Vector element = (Vector) denoData.get(i);
                            DDSDenominationGrid denoGrid = new DDSDenominationGrid();
                            denoGrid.setDenoValue(Double.parseDouble(element.get(1).toString()));
                            denoGrid.setDenoNo(Integer.parseInt(element.get(0).toString()));
                            denoGrid.setDenoAmount(Double.parseDouble(element.get(1).toString()) * Integer.parseInt(element.get(0).toString()));
                            denoGrid.setFlag(element.get(2).toString());
                            if (element.get(3).toString().equalsIgnoreCase("1")) {
                                if (element.get(2).toString().equalsIgnoreCase("0")) {
                                    denoGrid.setTySh("Return");
                                } else {
                                    denoGrid.setTySh("Receive");
                                }
                            } else {
                                if (element.get(2).toString().equalsIgnoreCase("1")) {
                                    denoGrid.setTySh("Receive");
                                } else {
                                    denoGrid.setTySh("Return");
                                }
                            }
                            denominationTable.add(denoGrid);
                        }
                    }
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
}
