package com.cbs.web.controller.td;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.web.pojo.other.TdDuplicateReceipt;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import javax.faces.model.SelectItem;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TdDuplicateReceiptIssue extends BaseBean {

    int currentRow;
    TdDuplicateReceipt currentItem;
    private List<SelectItem> acctTypeOption;
    private String acType;
    String accountNo;
    private String fullAccNo, acNoLen;
    private List<TdDuplicateReceipt> tdDuplicate;
    private String message;
    private String custName;
    private float balanceInTd;
    private float totalInt;
    private float receiptNo;
    private double prinAmt;
    private float tdsDeducted;
    private String rtNo;
    private String accStatus;
    private final String jndiHomeNameTdRcpt = "TdReceiptManagementFacade";
    private TdReceiptManagementFacadeRemote tdRcptMgmtRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;

    public double getPrinAmt() {
        return prinAmt;
    }

    public void setPrinAmt(double prinAmt) {
        this.prinAmt = prinAmt;
    }

    public String getRtNo() {
        return rtNo;
    }

    public void setRtNo(String rtNo) {
        this.rtNo = rtNo;
    }

    public float getTdsDeducted() {
        return tdsDeducted;
    }

    public void setTdsDeducted(float tdsDeducted) {
        this.tdsDeducted = tdsDeducted;
    }

    public float getBalanceInTd() {
        return balanceInTd;
    }

    public void setBalanceInTd(float balanceInTd) {
        this.balanceInTd = balanceInTd;
    }

    public float getTotalInt() {
        return totalInt;
    }

    public void setTotalInt(float totalInt) {
        this.totalInt = totalInt;
    }

    public float getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(float receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(String accStatus) {
        this.accStatus = accStatus;
    }

    public TdDuplicateReceipt getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(TdDuplicateReceipt currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<TdDuplicateReceipt> getTdDuplicate() {
        return tdDuplicate;
    }

    public void setTdDuplicate(List<TdDuplicateReceipt> tdDuplicate) {
        this.tdDuplicate = tdDuplicate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getFullAccNo() {
        return fullAccNo;
    }

    public void setFullAccNo(String fullAccNo) {
        this.fullAccNo = fullAccNo;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcctTypeOption() {
        return acctTypeOption;
    }

    public void setAcctTypeOption(List<SelectItem> acctTypeOption) {
        this.acctTypeOption = acctTypeOption;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    public TdDuplicateReceiptIssue() {
        try {
            this.setAcNoLen(getAcNoLength());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            tdRcptMgmtRemote = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTdRcpt);
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            getdata();
            tdDuplicate = new ArrayList<>();

        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getdata() {
        try {
            List acnoDataList = new ArrayList();
            acnoDataList = tdRcptMgmtRemote.getData();
            acctTypeOption = new ArrayList<SelectItem>();
            for (int i = 0; i < acnoDataList.size(); i++) {
                Vector ele = (Vector) acnoDataList.get(i);
                for (Object ee : ele) {
                    acctTypeOption.add(new SelectItem(ee.toString(), ee.toString()));
                }
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getAccountDetails() {
        clearText();
        this.setMessage("");
        String accType = this.acType;
        String code = this.accountNo;
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        if (this.accountNo == null || this.accountNo.equalsIgnoreCase("")) {
            this.setMessage("Please enter account no.");
            return;
        }
        //if (this.accountNo.length() < 12) {
        if (!this.accountNo.equalsIgnoreCase("") && ((this.accountNo.length() != 12) && (this.accountNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
            this.setMessage("Please fill proper account no.");
            return;
        }
        try {
            fullAccNo = ftsPostRemote.getNewAccountNumber(accountNo);
            String checkBrnCode = ftsPostRemote.getCurrentBrnCode(fullAccNo);
            if (!checkBrnCode.equals(getOrgnBrCode())) {
                this.setMessage("Sorry! Other branch account no. is not allowed to proceed.");
                return;
            }
            List accountDetails = new ArrayList();
            accountDetails = tdRcptMgmtRemote.accountStatus(fullAccNo);
            for (int i = 0; i < accountDetails.size(); i++) {
                Vector ele = (Vector) accountDetails.get(i);
                this.setAccStatus(ele.get(1).toString());
            }
            if (accountDetails.isEmpty()) {
                this.setMessage("A/c no. does not exist. ");
            }
            if (!accountDetails.isEmpty()) {
                for (int i = 0; i < accountDetails.size(); i++) {
                    Vector ele = (Vector) accountDetails.get(i);
                    this.setAccStatus(ele.get(1).toString());
                    this.setCustName(ele.get(2).toString());
                    loadGrid();
                }
            }
            if (accStatus.equals("9")) {
                tdDuplicate.clear();
                this.setMessage("This account has been closed ");
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void loadGrid() {
        tdDuplicate = new ArrayList<>();
        try {
            List tableList = new ArrayList();
            tableList = tdRcptMgmtRemote.tableData(fullAccNo);
            for (int i = 0; i < tableList.size(); i++) {
                Vector ele = (Vector) tableList.get(i);
                TdDuplicateReceipt duplicate = new TdDuplicateReceipt();
                duplicate.setAccountNumber(ele.get(0).toString());
                duplicate.setSeqNo(Float.parseFloat(ele.get(1).toString()));
                duplicate.setVoucherNo(Float.parseFloat(ele.get(2).toString()));
                String s = String.valueOf(Float.parseFloat(ele.get(3).toString()));
                s = s.substring(0, s.indexOf("."));
                duplicate.setReceiptNo(s);
                duplicate.setRoi(Float.parseFloat(ele.get(4).toString()));
                duplicate.setTdMadeDt(ele.get(5).toString());
                duplicate.setFdDt(ele.get(6).toString());
                duplicate.setMatDt(ele.get(7).toString());
                duplicate.setPrinAmt(Double.parseDouble(ele.get(8).toString()));
                duplicate.setIntOpt(ele.get(9).toString());
                duplicate.setIntToAcno(ele.get(10).toString());
                duplicate.setPeriod(ele.get(11).toString());
                String st = ele.get(12).toString();
                String fdDate = ele.get(6).toString();
                String dd = fdDate.substring(0, 2);
                String mm = fdDate.substring(3, 5);
                String yy = fdDate.substring(6, 10);
                String fdDates = yy + "" + mm + "" + dd;
                String matDate = ele.get(7).toString();
                String p1 = matDate.substring(0, 2);
                String p2 = matDate.substring(3, 5);
                String p3 = matDate.substring(6, 10);
                String matDates = p3 + "" + p2 + "" + p1;
                String interest = tdRcptMgmtRemote.orgFDInterest(ele.get(9).toString(), Float.parseFloat(ele.get(4).toString()), fdDates, matDates, Double.parseDouble(ele.get(8).toString()), ele.get(11).toString(),ele.get(0).toString().substring(0,2));
                duplicate.setIntAtMat(Double.parseDouble(interest));
                if (ele.get(9).toString().equals("C") || ele.get(9).toString().equals("S")) {
                    duplicate.setTotTdAmt(Double.parseDouble(ele.get(8).toString()) + (Double.parseDouble(interest)));
                } else if (ele.get(9).toString().equals("M") || ele.get(9).toString().equals("Q")) {
                    duplicate.setTotTdAmt(Double.parseDouble(ele.get(8).toString()));
                }
                if (st.equals("C")) {
                    st = "Closed";
                } else {
                    st = "Active";
                }
                duplicate.setStatus(st);
                tdDuplicate.add(duplicate);
            }
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void receiptIssues() {
        if (receiptNo <= 0.0) {
            this.setMessage("Please Select Receipt No From The Table");
            return;
        }
        try {
            String checkBrnCode = ftsPostRemote.getCurrentBrnCode(fullAccNo);
            if (!checkBrnCode.equals(getOrgnBrCode())) {
                this.setMessage("Sorry! Other branch account no. is not allowed to proceed.");
                return;
            }
            String receiptIssue = tdRcptMgmtRemote.receiptIssue(fullAccNo, receiptNo, Float.parseFloat(rtNo), getUserName(), getOrgnBrCode());
            this.setMessage(receiptIssue);
            loadGrid();
            clearText1();
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void delete() {
        if (receiptNo <= 0.0) {
            this.setMessage("Please Select Receipt No From The Table");
            return;
        }
        tdDuplicate = new ArrayList<>();
        try {
            String checkBrnCode = ftsPostRemote.getCurrentBrnCode(fullAccNo);
            if (!checkBrnCode.equals(getOrgnBrCode())) {
                this.setMessage("Sorry! Other branch account no. is not allowed to proceed.");
                return;
            }
            String delete = tdRcptMgmtRemote.delete(fullAccNo, receiptNo, Float.parseFloat(rtNo));
            this.setMessage(delete);
            loadGrid();
            clearText1();
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void clearText() {
        setCustName("");
        setFullAccNo("");
        setTotalInt(0);
        setBalanceInTd(0);
        setPrinAmt(0.0d);
        setTdsDeducted(0);
        setMessage("");
        setRtNo("");
        setReceiptNo(0);
        tdDuplicate = new ArrayList<>();
    }

    public void clearText1() {
        setCustName("");
        //setFullAccNo("");
        setTotalInt(0);
        setBalanceInTd(0);
        setPrinAmt(0.0d);
        setTdsDeducted(0);
        setRtNo("");
        setReceiptNo(0);
    }

     public void setRowValues() {
        this.setMessage("");
        clearText1();
        try {
            setFullAccNo(currentItem.getAccountNumber());
            setRtNo(String.valueOf(currentItem.getVoucherNo()));
            setReceiptNo(Float.parseFloat(currentItem.getReceiptNo()));
            setPrinAmt(currentItem.getPrinAmt());
            String balance = tdRcptMgmtRemote.tdDupReceiptIssueClickGrid(fullAccNo);
            String[] values = null;
            String spliter = ": ";
            values = balance.split(spliter);
            this.setTdsDeducted(Float.parseFloat(values[0]));
            this.setBalanceInTd(Float.parseFloat(values[1]));
            this.setTotalInt(Float.parseFloat(values[2]));
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void setRtNumberValues() {
        this.setMessage("");
        setTotalInt(0);
        setBalanceInTd(0);
        setPrinAmt(0.0d);
        setTdsDeducted(0);
        setReceiptNo(0);
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        if (this.accountNo == null || this.accountNo.equalsIgnoreCase("")) {
            this.setMessage("Please enter account no.");
            return;
        }
        //if (this.accountNo.length() < 12) {
        if (!this.accountNo.equalsIgnoreCase("") && ((this.accountNo.length() != 12) && (this.accountNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
            this.setMessage("Please fill proper account no.");
            return;
        }
        if (this.rtNo.equalsIgnoreCase("") || this.rtNo.length() == 0) {
            this.setMessage("Please Enter Rt No");
            return;
        }
        Matcher rtNoCheck = p.matcher(rtNo);
        if (!rtNoCheck.matches()) {
            this.setMessage("Please Enter Valid Rt No");
            return;
        }
        try {
            String rtNos = tdRcptMgmtRemote.getRtNumberInformation(fullAccNo, rtNo);
            String[] values = null;
            String spliter = ": ";
            values = rtNos.split(spliter);
            if (values[0].equals("null")) {
            } else {
                setFullAccNo(values[0]);
            }
            if (values[2].equals("null")) {
            } else {
                setReceiptNo(Float.parseFloat(values[2]));
            }
            if (values[3].equals("null")) {
            } else {
                setPrinAmt(Double.parseDouble(values[3]));
            }
            String balance = tdRcptMgmtRemote.tdDupReceiptIssueClickGrid(fullAccNo);
            String[] values1 = null;
            String spliter1 = ": ";
            values1 = balance.split(spliter1);
            this.setTdsDeducted(Float.parseFloat(values1[0]));
            this.setBalanceInTd(Float.parseFloat(values1[1]));
            this.setTotalInt(Float.parseFloat(values1[2]));
        } catch (ApplicationException ex) {
            this.setMessage(ex.getMessage());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public String exitFrm() {
        setAccountNo("");
        clearText();
        return "case1";
    }
}
