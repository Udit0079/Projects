/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.txn;

import com.cbs.dto.DDSDenominationGrid;
import com.cbs.web.pojo.txn.CashCrItem;
import com.cbs.dto.TxnDetailBean;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.misc.CashierCashRecievedFacadeRemote;
import com.cbs.facade.other.PostalDetailFacadeRemote;
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
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author root
 */
public class CashDeposit {

    Date dt = new Date();
    HttpServletRequest request;
    private String orgBrnCode;
    private String todayDate;
    private String userName;
    private String msg;
    private List<CashCrItem> cashCrItemList;
    private CashCrItem currentItem;
    private int currentRow;
    private boolean authorizeValue;
    private boolean deleteValue;
    private List<TxnDetailBean> txnDetailList;
    private List<TxnDetailBean> txnDetailUnAuthList;
    private String accNo;
    BigDecimal openBalance;
    private BigDecimal presentBalance;
    private TxnDetailBean txnDetailGrid;
    private TxnDetailBean txnDetailUnAuthGrid;
    private final String jndiHomeName = "TxnAuthorizationManagementFacade";
    private TxnAuthorizationManagementFacadeRemote txnAuthRemote = null;
    private final String jndiHomeNameTxn = "TransactionManagementFacade";
    private TransactionManagementFacadeRemote txnRemote = null;
    private final String jndiHomeNameFts = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    private final String jndiHomeNameCsh = "CashierCashRecievedFacade";
    private CashierCashRecievedFacadeRemote cshRemote = null;
    private String cashMode;
    private String accountName;
    private String opMode;
    private String openDate;
    private String jtName;
    private String accInstruction;
    private String custId;
    private String riskCategorization;
    private String annualTurnover;
    private String custPanNo;
    private String profession;
    private String dpLimit;
//    private String errorMsg;
    private String imageData;
    private PostalDetailFacadeRemote postalRemote;
    private List<DDSDenominationGrid> denominationTable;
    private double pRecNo;

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
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

    public String getCashMode() {
        return cashMode;
    }

    public void setCashMode(String cashMode) {
        this.cashMode = cashMode;
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

    public List<CashCrItem> getCashCrItemList() {
        return cashCrItemList;
    }

    public void setCashCrItemList(List<CashCrItem> cashCrItemList) {
        this.cashCrItemList = cashCrItemList;
    }

    public CashCrItem getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(CashCrItem currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getOrgBrnCode() {
        return orgBrnCode;
    }

    public void setOrgBrnCode(String orgBrnCode) {
        this.orgBrnCode = orgBrnCode;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
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

    public List<TxnDetailBean> getTxnDetailList() {
        return txnDetailList;
    }

    public void setTxnDetailList(List<TxnDetailBean> txnDetailList) {
        this.txnDetailList = txnDetailList;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public List<TxnDetailBean> getTxnDetailUnAuthList() {
        return txnDetailUnAuthList;
    }

    public void setTxnDetailUnAuthList(List<TxnDetailBean> txnDetailUnAuthList) {
        this.txnDetailUnAuthList = txnDetailUnAuthList;
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

    public TxnDetailBean getTxnDetailUnAuthGrid() {
        return txnDetailUnAuthGrid;
    }

    public void setTxnDetailUnAuthGrid(TxnDetailBean txnDetailUnAuthGrid) {
        this.txnDetailUnAuthGrid = txnDetailUnAuthGrid;
    }

    public String getDpLimit() {
        return dpLimit;
    }

    public void setDpLimit(String dpLimit) {
        this.dpLimit = dpLimit;
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
    
    public CashDeposit() {
        try {
            request = getRquest();
            String orgnBrIp = WebUtil.getClientIP(request);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgBrnCode = Init.IP2BR.getBranch(localhost);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(dt));
            setUserName(request.getRemoteUser());
            txnAuthRemote = (TxnAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTxn);
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFts);
            postalRemote = (PostalDetailFacadeRemote) ServiceLocator.getInstance().lookup("PostalDetailFacade");
            cshRemote = (CashierCashRecievedFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameCsh);
            getCashModeFromDb();
            getDataToAuthorize();
            authorizeValue = true;
            deleteValue = true;

        } catch (ApplicationException e) {
            this.setMsg(e.getMessage());
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

    public void getCashModeFromDb() {
        try {
            cashMode = ftsRemote.getCashMode(orgBrnCode);
            cashMode = "N";
            if (cshRemote.cashAndDenominitionModChk()) {
                cashMode = "Y";
            }
        } catch (ApplicationException ex) {
            setMsg(ex.getMessage());
        }
    }

    public List<CashCrItem> getDataToAuthorize() {
        List dataToAuthorize = null;
        String date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            date = sdf.format(dt);
            cashCrItemList = new ArrayList<CashCrItem>();
            dataToAuthorize = txnAuthRemote.getCashCrDataToAuthorize(date, orgBrnCode);

            for (int i = 0; i < dataToAuthorize.size(); i++) {
                Vector element = (Vector) dataToAuthorize.get(i);
                CashCrItem cashCrItem = new CashCrItem();
                cashCrItem.setsNo(i + 1);
                cashCrItem.setVchNo(Integer.parseInt(element.get(0).toString()));
                cashCrItem.setAcNo(element.get(1).toString());
                cashCrItem.setCustName(element.get(2).toString());

                cashCrItem.setInstAmt(Double.parseDouble(element.elementAt(3).toString()));
                cashCrItem.setBalBeforeInst(Double.parseDouble(element.elementAt(4).toString()));
                cashCrItem.setInstNo(element.get(5).toString());
                cashCrItem.setEnterBy(element.get(6).toString());

                cashCrItem.setAuthStatus(element.get(7).toString());
                cashCrItem.setRecNo(Float.parseFloat(element.get(8).toString()));

                cashCrItem.setDetails(element.get(9).toString());

                int index = element.get(9).toString().indexOf("~`");
                if (index > -1) {
                    cashCrItem.setViewDetails(element.get(9).toString().substring(0, index));
                } else {
                    cashCrItem.setViewDetails(element.get(9).toString().toString());
                }
                cashCrItem.setOrgnCode(element.get(10).toString());
                cashCrItem.setDestCode(element.get(11).toString());
                cashCrItem.setValueDt(element.get(12).toString());
                String InstrDt;
                if ((element.get(13) == null)) {
                    InstrDt = null;
                } else {
                    if ((element.get(13).toString().equals("")) || (element.get(13).toString().equals("19000101"))) {
                        InstrDt = null;
                    } else {
                        InstrDt = element.get(13).toString();
                    }
                }
                cashCrItem.setInstDT(InstrDt);
                cashCrItem.setRelatedTo(txnAuthRemote.getTranDescription(Integer.parseInt(element.elementAt(14).toString())));
                cashCrItemList.add(cashCrItem);
            }
        } catch (ApplicationException e) {
            this.setMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsg(e.getLocalizedMessage());
        }
        return dataToAuthorize;
    }

    public void changeStatus() {
        CashCrItem item = cashCrItemList.get(currentRow);
        accNo = currentItem.getAcNo();
        pRecNo = currentItem.getRecNo();
        if (item.getAuthStatus().equalsIgnoreCase("N")) {
            if (item.getEnterBy().equalsIgnoreCase(this.getUserName())) {
                this.setMsg("You cannot authorize your own entry");
                // item.setAuthStatus("Y");
                authorizeValue = true;
                deleteValue = false;
            } else {
                boolean found = false;
                for (CashCrItem item1 : cashCrItemList) {
                    if (item1.getAuthStatus().equalsIgnoreCase("Y")) {
                        found = true;
                    }
                }
                if (found) {
                    this.setMsg("Only one transction can be authorize at one time.");
                } else {

                    item.setAuthStatus("Y");
                    cashCrItemList.remove(currentRow);
                    cashCrItemList.add(currentRow, item);
                    authorizeValue = false;
                    deleteValue = false;
                }
            }
        } else if (item.getAuthStatus().equalsIgnoreCase("Y")) {
            item.setAuthStatus("N");
            cashCrItemList.remove(currentRow);
            cashCrItemList.add(currentRow, item);
            authorizeValue = true;
            deleteValue = true;
            this.setMsg("");
        }
    }

    public void authorizeAction() {
        String status = null, date = null, instNo = null, billType = null, acno = null, custName = null,
                payableAt = null, dt1 = null, originDt = null, inFavourOf = null, lastUpdateBy = null, valueDate = null;
        float seqNo = 0.0f;
        double amount = 0.0d, comm = 0.0d;
        int msgNo = 0, msgBillStart = 0, msgBillPo = 0, msgBillEnd = 0, timeLimit = 0, tranType = 0, ty = 0;
//        SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
//        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        try {
            for (int j = 0; j < cashCrItemList.size(); j++) {
                CashCrItem items = cashCrItemList.get(j);
                if (items.getAuthStatus().equalsIgnoreCase("Y")) {
                    /*   String nreFlag = postalRemote.isAccountofPostal(items.getAcNo());
                     String acctNature = ftsRemote.getAccountNature(items.getAcNo());
                     if (nreFlag.equalsIgnoreCase("Y") && acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                     //Postal Case.
                     String authMsg = postalRemote.processSingleRecovery(0f, items.getRecNo(), orgBrnCode, todayDate, 0, userName);
                     if (authMsg.equalsIgnoreCase("true")) {
                     getDataToAuthorize();
                     this.setMsg("Authorization is over");
                     deleteValue = true;
                     authorizeValue = true;
                     }
                     } else {*/
                    if (items.getOrgnCode().equals(items.getDestCode())) {
                        acno = items.getAcNo();
                        amount = items.getInstAmt();
                        date = items.getValueDt();
                        //Single branch handling coding.
                        String enterBy = items.getEnterBy();
                        valueDate = items.getValueDt().substring(6, 10) + items.getValueDt().substring(3, 5) + items.getValueDt().substring(0, 2);
                        if (items.getDetails().indexOf("~`") > -1) {
                            String[] elements = items.getDetails().split("~`");
                            msgNo = Integer.parseInt(elements[1]);
                            msgBillStart = 110;
                            status = elements[11];

                            msgBillPo = Integer.parseInt(elements[1]);
                            msgBillEnd = 140;
                            date = elements[9];
                            seqNo = Float.parseFloat(elements[2]);
                            instNo = elements[3];

                            billType = elements[4];
                            acno = elements[5];
                            custName = elements[6];
                            payableAt = elements[7];

                            amount = Double.parseDouble(elements[8]);
                            dt1 = elements[9];
                            originDt = elements[9];
                            timeLimit = Integer.parseInt(elements[12]);

                            comm = Double.parseDouble(elements[13]);
                            tranType = Integer.parseInt(elements[14]);
                            ty = Integer.parseInt(elements[15]);

                            inFavourOf = elements[16];
                            enterBy = elements[17];
                            lastUpdateBy = elements[18];

                        }
                        String authMsg = txnAuthRemote.cbsAuthCashCr(items.getRecNo(), this.userName, msgNo, msgBillStart,
                                status, msgBillPo, msgBillEnd, date, seqNo, instNo, billType, acno, custName, payableAt,
                                amount, dt1, originDt, timeLimit, comm, tranType, ty, inFavourOf, enterBy, lastUpdateBy,
                                items.getOrgnCode(), items.getDestCode(), valueDate);
                        if (authMsg.equalsIgnoreCase("Authorization is over")) {
                            getDataToAuthorize();
                            this.setMsg(authMsg);
                            deleteValue = true;
                            authorizeValue = true;
                        } else {
                            if (authMsg.substring(0, 13).equalsIgnoreCase("Authorization")) {
                                getDataToAuthorize();
                                this.setMsg(authMsg);
                                deleteValue = true;
                                authorizeValue = true;
                            } else {
                                this.setMsg(authMsg);
                            }
                        }
                    } else {
                        //Multiple branch handling coding
                        String authMsg = txnAuthRemote.cbsCashCrDestAuth(items.getRecNo(), this.userName, items.getEnterBy(), items.getOrgnCode());
                        if (authMsg.equalsIgnoreCase("Authorization is over")
                                || authMsg.substring(0, 4).equalsIgnoreCase("TRUE")
                                || authMsg.trim().toLowerCase().contains("cash-handling")) {
                            getDataToAuthorize();
                            if (authMsg.trim().toLowerCase().contains("cash-handling")) {
                                if (authMsg.trim().substring(13).equalsIgnoreCase("pending")) {
                                    this.setMsg("Authorization is over. Cash handling charge has been entered into pending charges.");
                                } else {
                                    this.setMsg("Authorization is over. Batch no of cash handling charges is:" + authMsg.trim().substring(13));
                                }
                            } else {
                                this.setMsg("Authorization is over");
                            }
                            deleteValue = true;
                            authorizeValue = true;
                        } else {
                            this.setMsg(authMsg);
                        }
                    }
                }
            }
            // }
        } catch (ApplicationException e) {
            e.printStackTrace();
            this.setMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }

    public void deleteForm() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String curDate = sdf.format(date);
        float tokenNo = 0.0f;
        try {
            double amount = currentItem.getInstAmt();
            String delMsg = txnAuthRemote.cbsAuthCashCrDeletion(currentItem.getRecNo(), currentItem.getInstNo(), currentItem.getAcNo(),
                    amount, curDate, currentItem.getCustName(), "A", tokenNo, curDate, currentItem.getEnterBy(), userName,
                    currentItem.getOrgnCode());

            if (delMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                getDataToAuthorize();
                this.setMsg("Entry deleted successfuly");
                deleteValue = true;
                authorizeValue = true;
            } else {
                this.setMsg(delMsg);
            }
        } catch (ApplicationException e) {
            this.setMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsg(e.getLocalizedMessage());
        }
    }

    public String refreshForm() {
        getDataToAuthorize();
        this.setMsg("");
        deleteValue = true;
        authorizeValue = true;
        this.imageData = null;
        this.setAccNo("");
        this.setpRecNo(0);
        this.setOpenBalance(new BigDecimal("0"));
        this.setPresentBalance(new BigDecimal("0"));
        return null;
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }

    public void acctViewAuthUnAuth() {
//        String array[] = null;
//        String array1[] = null;
//        int i, j, k, l, m, n, o, p, q;

        try {
            txnDetailList = new ArrayList<TxnDetailBean>();
            txnDetailUnAuthList = new ArrayList<TxnDetailBean>();
            if (this.accNo == null) {
                this.setMsg("Please Double Click On Row To View A/C Details");
                return;
            }
            if (this.accNo.equals("")) {
                this.setMsg("Please Double Click On Row To View A/C Details");
                return;
            }
            this.setMsg("");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar now = Calendar.getInstance();
            now.roll(Calendar.MONTH, false);
            String oneMonthBeforeDate = sdf.format(now.getTime());


            String openBal = txnRemote.getOpeningBalF4(accNo, oneMonthBeforeDate);
            if (!openBal.equals("FALSE")) {

                this.setOpenBalance(new BigDecimal(openBal));
            }

            String prsntBal = txnRemote.getOpeningBalF4(accNo, todayDate);
            if (!prsntBal.equals("FALSE")) {

                this.setPresentBalance(new BigDecimal(prsntBal));
            }

            // BigDecimal netAmount = this.getPresentBalance();

            txnDetailList = txnRemote.accViewAuth(accNo, todayDate, orgBrnCode);
            txnDetailUnAuthList = txnRemote.accViewUnauth(accNo, todayDate, orgBrnCode);
        } catch (ApplicationException e) {
            this.setMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }
    
    public void acctDenoView(){
        try {
            int txnCashParam = 0;
            String cashMode;
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            
            if (this.accNo == null) {
                this.setMsg("Please Double Click On Row To View A/C Details");
                return;
            }
            if (this.accNo.equals("")) {
                this.setMsg("Please Double Click On Row To View A/C Details");
                return;
            }
            
            txnCashParam = Integer.parseInt(ftsRemote.getCodeByReportName("TELLER_DENOMINITION"));
            cashMode = ftsRemote.getCashMode(orgBrnCode);
            
            denominationTable = new ArrayList<DDSDenominationGrid>();
            if ((cashMode.equalsIgnoreCase("N") && (txnCashParam == 1)) || (cashMode.equalsIgnoreCase("Y") && (cshRemote.cashAndDenominitionModChk()))) {
                for (int j = 0; j < cashCrItemList.size(); j++) {
                    CashCrItem items = cashCrItemList.get(j);
                    if (items.getAuthStatus().equalsIgnoreCase("Y")) {
                        List denoData = txnAuthRemote.getDenoDateToShow(accNo,pRecNo,ymd.format(dt));
                        for (int i = 0; i < denoData.size(); i++) {
                            Vector element = (Vector) denoData.get(i);
                            DDSDenominationGrid denoGrid = new DDSDenominationGrid();
                            denoGrid.setDenoValue(Double.parseDouble(element.get(1).toString()));
                            denoGrid.setDenoNo(Integer.parseInt(element.get(0).toString()));
                            denoGrid.setDenoAmount(Double.parseDouble(element.get(1).toString()) * Integer.parseInt(element.get(0).toString()));
                            denoGrid.setFlag(element.get(2).toString());
                            if(element.get(3).toString().equalsIgnoreCase("0")){
                                if (element.get(2).toString().equalsIgnoreCase("0")) {
                                    denoGrid.setTySh("Return");
                                } else {
                                    denoGrid.setTySh("Receive");
                                }
                            } else{
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
        }catch (ApplicationException e) {
            this.setMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }  
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
            String signature;
            accNo = currentItem.getAcNo();

            accountName = currentItem.getCustName();
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
            this.setMsg(e.getMessage());
        } catch (FileNotFoundException e) {
            this.setMsg("");
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }
}
