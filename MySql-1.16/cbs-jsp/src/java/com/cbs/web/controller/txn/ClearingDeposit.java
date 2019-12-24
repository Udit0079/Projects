/*
 Document   : AccountClosingAuthorization
 Created on : 31 jan, 2011, 5:25:00 PM
 Author     : Zeeshan Waris
 */
package com.cbs.web.controller.txn;

/**
 *
 * @author root
 */
import com.cbs.web.pojo.txn.ClearingCr;
import com.cbs.dto.TxnDetailBean;
import com.cbs.exception.ApplicationException;
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
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author root
 */
public class ClearingDeposit {

    private HttpServletRequest req;
    private String orgnBrCode;
    private String todayDate;
    private String userName;
    private String message;
    private List<ClearingCr> statusTable;
    private int currentRow;
    boolean authorizeValue;
    private List<SelectItem> ddModeOfAuth;
    private String authSelection;
    private ClearingCr currentItem = new ClearingCr();
    String imageData;
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
    private String custId;
    private String riskCategorization;
    private String annualTurnover;
    private String custPanNo;
    private String profession;
    private String dpLimit;

    public String getDpLimit() {
        return dpLimit;
    }

    public void setDpLimit(String dpLimit) {
        this.dpLimit = dpLimit;
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

    public String getAuthSelection() {
        return authSelection;
    }

    public void setAuthSelection(String authSelection) {
        this.authSelection = authSelection;
    }

    public List<SelectItem> getDdModeOfAuth() {
        return ddModeOfAuth;
    }

    public void setDdModeOfAuth(List<SelectItem> ddModeOfAuth) {
        this.ddModeOfAuth = ddModeOfAuth;
    }

    public ClearingCr getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(ClearingCr currentItem) {
        this.currentItem = currentItem;
    }

    public List<ClearingCr> getStatusTable() {
        return statusTable;
    }

    public void setStatusTable(List<ClearingCr> statusTable) {
        this.statusTable = statusTable;
    }

    public boolean isAuthorizeValue() {
        return authorizeValue;
    }

    public void setAuthorizeValue(boolean authorizeValue) {
        this.authorizeValue = authorizeValue;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
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

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
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

    public ClearingDeposit() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            txnAuthRemote = (TxnAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTxn);
            this.setMessage("");
            ddInquery();
            authorizeValue = true;

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

    public void ddInquery() {
        ddModeOfAuth = new ArrayList<SelectItem>();
        ddModeOfAuth.add(new SelectItem("2", "--Select--"));
        ddModeOfAuth.add(new SelectItem("0", "Clearing Deposit"));
        ddModeOfAuth.add(new SelectItem("1", "Clearing Withdrawal"));
    }

    public void getTableStock() {
        statusTable = new ArrayList<ClearingCr>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String curDate = sdf.format(date);
        try {
            List resultLt = new ArrayList();
            resultLt = txnAuthRemote.tableAuthorize(curDate, Integer.parseInt(authSelection), orgnBrCode);
            int j = 1;
            if (!resultLt.isEmpty()) {
                for (int i = 0; i < resultLt.size(); i++) {
                    Vector ele = (Vector) resultLt.get(i);
                    ClearingCr clgDetail = new ClearingCr();
                    clgDetail.setAcNo(ele.get(0).toString());
                    clgDetail.setCustName(ele.get(1).toString());

                    clgDetail.setCrAmt(Double.parseDouble(ele.get(2).toString()));
                    clgDetail.setDrAmt(Double.parseDouble(ele.get(3).toString()));
                    clgDetail.setBalance(Double.parseDouble(ele.get(4).toString()));

                    clgDetail.setInstNo(ele.get(5).toString());
                    clgDetail.setEnterBy(ele.get(6).toString());
                    clgDetail.setAuth(ele.get(7).toString());
                    clgDetail.setTokenNo(ele.get(8).toString());

                    clgDetail.setSubTokenNo(ele.get(9).toString());
                    clgDetail.setRecNo(ele.get(10).toString());
                    clgDetail.setDetails(ele.elementAt(11).toString());

                    int index = ele.elementAt(11).toString().indexOf("~`");
                    if (index > -1) {
                        clgDetail.setViewDetails(ele.elementAt(11).toString().substring(0, index));
                    } else {
                        clgDetail.setViewDetails(ele.elementAt(11).toString());
                    }
                    clgDetail.setSno(j++);
                    if (!ele.get(5).toString().equals("")) {
                        clgDetail.setInstDt(ele.get(14).toString());
                    }

                    clgDetail.setValueDt(ele.elementAt(13).toString());
                    clgDetail.setIy(ele.elementAt(15).toString());
                    clgDetail.setRelatedTo(txnAuthRemote.getTranDescription(Integer.parseInt(ele.elementAt(16).toString())));
                    statusTable.add(clgDetail);
                }
                this.setMessage("");
            } else {
                this.setMessage("Records does not Exist. ");
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void changeAuth() {
        this.setMessage("");
        ClearingCr item = statusTable.get(currentRow);
        accountNo = item.getAcNo();
        if (item.getAuth().equalsIgnoreCase("N")) {
            if (item.getEnterBy().equalsIgnoreCase(this.getUserName())) {
                this.setMessage("You cannot authorize your own entry");
                //item.setAuthStatus("Y");
                authorizeValue = true;
            } else {
                boolean found1 = false;
                for (ClearingCr item1 : statusTable) {
                    if (item1.getAuth().equalsIgnoreCase("Y")) {
                        found1 = true;
                    }
                }
                if (found1) {
                    this.setMessage("Only one transction can be authorize at one time.");
                } else {
                    item.setAuth("Y");
                    statusTable.remove(currentRow);
                    statusTable.add(currentRow, item);
                    authorizeValue = false;
                    authFlag = item.getAuth();
                    System.out.println("authFlag     " + authFlag);
                }
            }
        } else if (item.getAuth().equalsIgnoreCase("Y")) {
            item.setAuth("N");
            statusTable.remove(currentRow);
            statusTable.add(currentRow, item);
            authorizeValue = true;
            authFlag = item.getAuth();
            System.out.println("authFlag     " + authFlag);
        }
    }

    public void saveBtnAction() {
        String MsgNo = "0";
        String Msg_Bill_Start = "0";
        String Status = "";
        String Msg_Bill_PO = "0";

        String Msg_Bill_End = "0";
        String date = "";
        String SeqNo = "0";
        String instno = "";

        String BillType = "";
        String Acno = "";
        String custname = "";
        String PAYABLEAT = "";

        String Amount = "0";
        String dt = "";
        String ORIGINDT = "";
        String TimeLimit = "0";
        String comm = "0";

        String TranType = "0";
        String ty = "0";
        String InFavourOf = "";
        String enterby = "";
        String LastUpdateBy = "";
        String option = "";
        for (int j = 0; j < statusTable.size(); j++) {
            ClearingCr items = statusTable.get(j);
            if (items.getAuth().equals("Y")) {
                try {
                    if (authSelection.equalsIgnoreCase("1")) {
                        List userAuthLimit = txnAuthRemote.getUserAuthorizationLimitClearing(userName, orgnBrCode);
                        if (userAuthLimit.size() <= 0) {
                            this.setMessage("UserId does not exists:: " + userName);
                            return;
                        }
                        Vector element = (Vector) userAuthLimit.get(0);
                        double userLimit = Double.parseDouble(element.get(0).toString());
                        if (userLimit == 0.0 || items.getDrAmt() > userLimit) {
                            this.setMessage("Your passing limit is less than withdrawal amount. You "
                                    + "can not authorize this transaction");
                            return;
                        }
                    }
                    Acno = items.getAcNo();
                    Amount = String.valueOf(items.getDrAmt());
                    dt = items.getValueDt();
                    
                    if (items.getDetails().indexOf("~`") > -1) {
                        String[] elements = items.getDetails().split("~`");
                        MsgNo = elements[1];
                        Msg_Bill_Start = "110";
                        Status = elements[11];

                        Msg_Bill_PO = elements[1];
                        Msg_Bill_End = "140";
                        date = elements[9];
                        SeqNo = elements[2];

                        instno = elements[3];
                        BillType = elements[4];
                        Acno = elements[5];
                        custname = elements[6];

                        PAYABLEAT = elements[7];
                        Amount = elements[8];
                        dt = elements[9];
                        ORIGINDT = elements[10];

                        TimeLimit = elements[12];
                        comm = elements[13];
                        TranType = elements[14];
                        ty = elements[15];

                        InFavourOf = elements[16];
                        enterby = elements[17];
                        LastUpdateBy = elements[18];
                    }
                    if (authSelection.equalsIgnoreCase("0")) {
                        option = "OUTWARD";
                    } else if (authSelection.equalsIgnoreCase("1")) {
                        option = "INWARD";
                    }
                    String result = txnAuthRemote.cbsAuthCleringDrCr(Float.parseFloat(items.getRecNo()), userName, Integer.parseInt(MsgNo),
                            Integer.parseInt(Msg_Bill_Start), Status, Integer.parseInt(Msg_Bill_PO), Integer.parseInt(Msg_Bill_End),
                            date, Float.parseFloat(SeqNo), instno, BillType, Acno, custname, PAYABLEAT, Double.parseDouble(Amount),
                            dt, ORIGINDT, Integer.parseInt(TimeLimit), Double.parseDouble(comm), Integer.parseInt(TranType),
                            Integer.parseInt(ty), InFavourOf, enterby, LastUpdateBy, option, orgnBrCode);
                    System.out.println("result = " + result);
                    getTableStock();
                    this.setMessage(result);
                    authorizeValue = true;
                } catch (ApplicationException e) {
                    this.setMessage(e.getMessage());
                } catch (Exception e) {
                    this.setMessage(e.getMessage());
                }
            }
        }
    }

    public void select() {
        if (this.authSelection.equalsIgnoreCase("1")) {
            getSignatureDetail();
        }
    }

    public void getSignatureDetail() {
        try {
            this.imageData = null;
            int Toper;
            String signature;
            accNo = currentItem.getAcNo();
            System.out.println("accNo    " + accNo);
            accountName = currentItem.getCustName();
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

    public void createSignature(OutputStream out, Object data) throws IOException {
        if (null == data) {
            return;
        }
        if (imageData != null) {
            byte[] sigBytes = Base64.decode(imageData);
            out.write(sigBytes);
        }
    }

    public void delete() {
        this.setMessage("");
        try {
            String dt = todayDate;
            String subTokenNo = "A";
            String tokenNo = "0";
            String originDt = todayDate;
            String authBy = userName;
            String tranType = null;
            String option = "";
            if (authSelection.equalsIgnoreCase("0")) {
                tranType = "0";
            } else if (authSelection.equalsIgnoreCase("1")) {
                tranType = "1";
            }
            if (authSelection.equalsIgnoreCase("0")) {
                option = "OUTWARD";
            } else if (authSelection.equalsIgnoreCase("1")) {
                option = "INWARD";
            }

            String deleteEntry = new String();
            deleteEntry = txnAuthRemote.cbsAuthDeletion(Float.parseFloat(currentItem.getRecNo()), currentItem.getInstNo(),
                    currentItem.getAcNo(), currentItem.getCrAmt(), currentItem.getDrAmt(), currentItem.getCrAmt(), dt,
                    currentItem.getCustName(), subTokenNo, Float.parseFloat(tokenNo), originDt, currentItem.getEnterBy(),
                    authBy, Integer.parseInt(tranType), orgnBrCode, option);

            if (deleteEntry.equalsIgnoreCase("True")) {
                getTableStock();
                this.setMessage("Data Deleted Successfully");
            } else {
                this.setMessage(deleteEntry);
            }

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String btnExit() {
        return "case1";
    }

    public void btnRefresh() {
        this.setMessage("");
        getTableStock();
        this.setAccountNo("");
        this.setOpenBalance(new BigDecimal("0"));
        this.setPresentBalance(new BigDecimal("0"));
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
            txnDetailList = txnRemote.accViewUnauth(accountNo, todayDate, orgnBrCode);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }
}
