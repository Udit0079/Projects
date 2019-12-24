/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.share;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import java.util.List;
import com.cbs.facade.ho.share.CertIssueFacadeRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.pojo.ho.CertIssueTable;
import com.cbs.dto.HoTransactionTable;
import com.cbs.dto.TxnDetailBean;
import com.cbs.facade.txn.TxnAuthorizationManagementFacadeRemote;
import com.cbs.utils.Base64;
import com.cbs.utils.CbsUtil;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.txn.GLHeadGrid;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.faces.model.SelectItem;
//import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author admin
 */
public class CertIssue extends BaseBean {

    private String datetoday;
    private String msg;
    private String shareMsg;
    private String acno;
    private String oldAcno;
    private List<CertIssueTable> datagrid;
    private List<HoTransactionTable> bacthtable;
    private HoTransactionTable currentData;
    private List<SelectItem> actypelist;
    private List<SelectItem> trantype;
    private List<SelectItem> relatedto;
    private List<SelectItem> transby;
    private List<SelectItem> transtype;
    private List<SelectItem> adviselist;
    private List<SelectItem> branchlist;
    private CertIssueTable currentdata;
    private List<SelectItem> statuslist;
    private List<CertIssueTable> datagridcert;
    private CertIssueTable cuurentdata;
    private CertIssueFacadeRemote certIssueRemote;
    private TransactionManagementFacadeRemote transRemote;
    private List<GLHeadGrid> listForF6;
    private GLHeadGrid glHeadGrid;
    private boolean flag;
    private boolean dividendFlag;
    private String dividendAmount;
    private boolean disableAmt;
    private double dividendBal;
    private boolean sharePaid;
    private boolean refundbool;
    private boolean shareCheck;
    private boolean adviceBoolean;
    private String foliono;
    private String folioname;
    private String foliofathername;
    private String instumentNo;
    private String city;
    private double sharevalue;
    private int noofshareAvailable;
    private Date issueDate = new Date();
    private Date valueDt;
    private Date refundDate;
    private String advise;
    private String remark;
    private double shareamt;
    private int noshare;
    private String certificateno;
    private String status;
    private String accountname;
    private String accountopening;
    private String orignbranch = "";
    // @NotBlank(message = "Date is not blank")
    private Date accountdate;
    private Date originDate;
    private String destbranch;
    private String strtrantype;
    private String type;
    private String tranby;
    private String hodetails;
    private double hoamount;
    private String strRelate;
    private String adviceNo = "";
    private String instmentno, folionoShow;
    private double drAmt;
    private double crAmt;
    private boolean boolsavebatch;
    private String sharesToBePaid;
    private boolean boolshareno;
    private String balance;
    private String advNo;
    private String poNo;
    private String folioBalance;
    private List<TxnDetailBean> txnDetailList;
    private List<TxnDetailBean> txnDetailUnAuthList;
    // private String accNo;
    private BigDecimal openBalance;
    private BigDecimal presentBalance;
    private TxnDetailBean txnDetailGrid;
    private TxnDetailBean txnDetailUnAuthGrid;
    private FtsPostingMgmtFacadeRemote ftsPostingRemote;
    private TransactionManagementFacadeRemote txnRemote = null;
    private final String jndiHomeNameTxn = "TransactionManagementFacade";
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private String imageData;
    private TxnAuthorizationManagementFacadeRemote txnAuthRemote;

    /**
     * Creates a new instance of CertIssue
     */
    public CertIssue() {
        try {
            System.out.println("CertIssue");
            datetoday = sdf.format(date);
            originDate = date;
            accountdate = date;
            valueDt = date;
            shareCheck = false;
            poNo = "";
            advNo = "";
            certIssueRemote = (CertIssueFacadeRemote) ServiceLocator.getInstance().lookup("CertIssueFacade");
            ftsPostingRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTxn);
            txnAuthRemote = (TxnAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup("TxnAuthorizationManagementFacade");
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }

        initData();
        refundbool = true;
        boolsavebatch = true;
        bacthtable = new ArrayList<HoTransactionTable>();
        crAmt = 0;
        drAmt = 0;
    }

    public String getDividendAmount() {
        return dividendAmount;
    }

    public void setDividendAmount(String dividendAmount) {
        this.dividendAmount = dividendAmount;
    }

    public double getDividendBal() {
        return dividendBal;
    }

    public void setDividendBal(double dividendBal) {
        this.dividendBal = dividendBal;
    }

    public boolean isDisableAmt() {
        return disableAmt;
    }

    public void setDisableAmt(boolean disableAmt) {
        this.disableAmt = disableAmt;
    }
    
    
    public String getSharesToBePaid() {
        return sharesToBePaid;
    }

    public void setSharesToBePaid(String sharesToBePaid) {
        this.sharesToBePaid = sharesToBePaid;
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

    public String getShareMsg() {
        return shareMsg;
    }

    public void setShareMsg(String shareMsg) {
        this.shareMsg = shareMsg;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Date getValueDt() {
        return valueDt;
    }

    public void setValueDt(Date valueDt) {
        this.valueDt = valueDt;
    }

    public String getFolionoShow() {
        return folionoShow;
    }

    public void setFolionoShow(String folionoShow) {
        this.folionoShow = folionoShow;
    }

    public String getInstumentNo() {
        return instumentNo;
    }

    public void setInstumentNo(String instumentNo) {
        this.instumentNo = instumentNo;
    }

    public boolean isAdviceBoolean() {
        return adviceBoolean;
    }

    public void setAdviceBoolean(boolean adviceBoolean) {
        this.adviceBoolean = adviceBoolean;
    }

    public int getNoshare() {
        return noshare;
    }

    public void setNoshare(int noshare) {
        this.noshare = noshare;
    }

    public Date getOriginDate() {
        return originDate;
    }

    public void setOriginDate(Date originDate) {
        this.originDate = originDate;
    }

    public boolean isBoolsavebatch() {
        return boolsavebatch;
    }

    public void setBoolsavebatch(boolean boolsavebatch) {
        this.boolsavebatch = boolsavebatch;
    }

    public double getCrAmt() {
        return crAmt;
    }

    public void setCrAmt(double crAmt) {
        this.crAmt = crAmt;
    }

    public double getDrAmt() {
        return drAmt;
    }

    public void setDrAmt(double drAmt) {
        this.drAmt = drAmt;
    }

    public List<HoTransactionTable> getBacthtable() {
        return bacthtable;
    }

    public void setBacthtable(List<HoTransactionTable> bacthtable) {
        this.bacthtable = bacthtable;
    }

    public String getInstmentno() {
        return instmentno;
    }

    public void setInstmentno(String instmentno) {
        this.instmentno = instmentno;
    }

    public String getAdviceNo() {
        return adviceNo;
    }

    public void setAdviceNo(String adviceNo) {
        this.adviceNo = adviceNo;
    }

    public String getStrRelate() {
        return strRelate;
    }

    public void setStrRelate(String strRelate) {
        this.strRelate = strRelate;
    }

    public String getDestbranch() {
        return destbranch;
    }

    public void setDestbranch(String destbranch) {
        this.destbranch = destbranch;
    }

    public double getHoamount() {
        return hoamount;
    }

    public void setHoamount(double hoamount) {
        this.hoamount = hoamount;
    }

    public String getHodetails() {
        return hodetails;
    }

    public void setHodetails(String hodetails) {
        this.hodetails = hodetails;
    }

    public String getOrignbranch() {
        return orignbranch;
    }

    public void setOrignbranch(String orignbranch) {
        this.orignbranch = orignbranch;
    }

    public String getStrtrantype() {
        return strtrantype;
    }

    public void setStrtrantype(String strtrantype) {
        this.strtrantype = strtrantype;
    }

    public String getTranby() {
        return tranby;
    }

    public void setTranby(String tranby) {
        this.tranby = tranby;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getAccountdate() {
        return accountdate;
    }

    public void setAccountdate(Date accountdate) {
        this.accountdate = accountdate;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public String getAccountopening() {
        return accountopening;
    }

    public void setAccountopening(String accountopening) {
        this.accountopening = accountopening;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCertificateno() {
        return certificateno;
    }

    public void setCertificateno(String certificateno) {
        this.certificateno = certificateno;
    }

    public double getShareamt() {
        return shareamt;
    }

    public void setShareamt(double shareamt) {
        this.shareamt = shareamt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAdvise() {
        return advise;
    }

    public void setAdvise(String advise) {
        this.advise = advise;
    }

    public boolean isRefundbool() {
        return refundbool;
    }

    public void setRefundbool(boolean refundbool) {
        this.refundbool = refundbool;
    }

    public Date getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(Date refundDate) {
        this.refundDate = refundDate;
    }

    public List<CertIssueTable> getDatagridcert() {
        return datagridcert;
    }

    public CertIssueTable getCuurentdata() {
        return cuurentdata;
    }

    public void setCuurentdata(CertIssueTable cuurentdata) {
        this.cuurentdata = cuurentdata;
    }

    public void setDatagridcert(List<CertIssueTable> datagridcert) {
        this.datagridcert = datagridcert;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public int getNoofshareAvailable() {
        return noofshareAvailable;
    }

    public void setNoofshareAvailable(int noofshareAvailable) {
        this.noofshareAvailable = noofshareAvailable;
    }

    public double getSharevalue() {
        return sharevalue;
    }

    public void setSharevalue(double sharevalue) {
        this.sharevalue = sharevalue;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFoliofathername() {
        return foliofathername;
    }

    public void setFoliofathername(String foliofathername) {
        this.foliofathername = foliofathername;
    }

    public String getFolioname() {
        return folioname;
    }

    public void setFolioname(String folioname) {
        this.folioname = folioname;
    }

    public String getFoliono() {
        return foliono;
    }

    public void setFoliono(String foliono) {
        this.foliono = foliono;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isDividendFlag() {
        return dividendFlag;
    }

    public void setDividendFlag(boolean dividendFlag) {
        this.dividendFlag = dividendFlag;
    }

    public GLHeadGrid getGlHeadGrid() {
        return glHeadGrid;
    }

    public void setGlHeadGrid(GLHeadGrid glHeadGrid) {
        this.glHeadGrid = glHeadGrid;
    }

    public List<GLHeadGrid> getListForF6() {
        return listForF6;
    }

    public void setListForF6(List<GLHeadGrid> listForF6) {
        this.listForF6 = listForF6;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getOldAcno() {
        return oldAcno;
    }

    public void setOldAcno(String oldAcno) {
        this.oldAcno = oldAcno;
    }

    public List<SelectItem> getAdviselist() {
        return adviselist;
    }

    public void setAdviselist(List<SelectItem> adviselist) {
        this.adviselist = adviselist;
    }

    public List<SelectItem> getBranchlist() {
        return branchlist;
    }

    public void setBranchlist(List<SelectItem> branchlist) {
        this.branchlist = branchlist;
    }

    public CertIssueTable getCurrentdata() {
        return currentdata;
    }

    public void setCurrentdata(CertIssueTable currentdata) {
        this.currentdata = currentdata;
    }

    public List<SelectItem> getTransby() {
        return transby;
    }

    public void setTransby(List<SelectItem> transby) {
        this.transby = transby;
    }

    public List<SelectItem> getTranstype() {
        return transtype;
    }

    public void setTranstype(List<SelectItem> transtype) {
        this.transtype = transtype;
    }

    public List<SelectItem> getRelatedto() {
        return relatedto;
    }

    public void setRelatedto(List<SelectItem> relatedto) {
        this.relatedto = relatedto;
    }

    public List<SelectItem> getTrantype() {
        return trantype;
    }

    public void setTrantype(List<SelectItem> trantype) {
        this.trantype = trantype;
    }

    public List<SelectItem> getActypelist() {
        return actypelist;
    }

    public void setActypelist(List<SelectItem> actypelist) {
        this.actypelist = actypelist;
    }

    public String getDatetoday() {
        return datetoday;
    }

    public void setDatetoday(String datetoday) {
        this.datetoday = datetoday;
    }

    public List<CertIssueTable> getDatagrid() {
        return datagrid;
    }

    public void setDatagrid(List<CertIssueTable> datagrid) {
        this.datagrid = datagrid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<SelectItem> getStatuslist() {
        return statuslist;
    }

    public void setStatuslist(List<SelectItem> statuslist) {
        this.statuslist = statuslist;
    }

    public HoTransactionTable getCurrentData() {
        return currentData;
    }

    public void setCurrentData(HoTransactionTable currentData) {
        this.currentData = currentData;
    }

    public boolean isBoolshareno() {
        return boolshareno;
    }

    public void setBoolshareno(boolean boolshareno) {
        this.boolshareno = boolshareno;
    }

    public boolean isShareCheck() {
        return shareCheck;
    }

    public void setShareCheck(boolean shareCheck) {
        this.shareCheck = shareCheck;
    }

    public boolean isSharePaid() {
        return sharePaid;
    }

    public void setSharePaid(boolean sharePaid) {
        this.sharePaid = sharePaid;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public String getFolioBalance() {
        return folioBalance;
    }

    public void setFolioBalance(String folioBalance) {
        this.folioBalance = folioBalance;
    }
    
    private void initData() {
        try {
            actypelist = new ArrayList<SelectItem>();
            trantype = new ArrayList<SelectItem>();
            relatedto = new ArrayList<SelectItem>();
            transby = new ArrayList<SelectItem>();
            transtype = new ArrayList<SelectItem>();
            adviselist = new ArrayList<SelectItem>();
            branchlist = new ArrayList<SelectItem>();
            statuslist = new ArrayList<SelectItem>();
            trantype.add(new SelectItem("2", "Transfer"));
            relatedto.add(new SelectItem("0", "Others"));
            transby.add(new SelectItem("3", "Voucher"));
            transtype.add(new SelectItem("0", "Cr"));
            transtype.add(new SelectItem("1", "Dr"));
            this.setType("0");
            statuslist.add(new SelectItem("Active", "Active"));
            statuslist.add(new SelectItem("Inactive", "Inactive"));
            statuslist.add(new SelectItem("Partial", "Partial"));
            setStatus("Active");
            setBoolshareno(true);
            setFolioBalance("0.00");
            destbranch = "";
            List data = certIssueRemote.accType();
            Vector vtr;
            if (!data.isEmpty()) {
                int i = 0;
                while (data.size() > i) {
                    vtr = (Vector) data.get(i);
                    actypelist.add(new SelectItem(vtr.get(0).toString(), vtr.get(0).toString()));
                    i++;
                }
            }
            data = certIssueRemote.getRelatedTo();
            if (!data.isEmpty()) {
                int i = 0;
                while (data.size() > i) {

                    vtr = (Vector) data.get(i);
                    relatedto.add(new SelectItem(vtr.get(0).toString(), vtr.get(1).toString()));
                    i++;
                }
            }
            data = certIssueRemote.getAdviseNo();
            if (!data.isEmpty()) {
                int i = 0;
                adviselist.add(new SelectItem("Automatic", "Automatic"));
                while (data.size() > i) {

                    vtr = (Vector) data.get(i);
                    adviselist.add(new SelectItem(vtr.get(0).toString(), vtr.get(0).toString()));
                    i++;
                }
            }
            data = certIssueRemote.getBranchCode();
            if (!data.isEmpty()) {
                int i = 0;
                while (data.size() > i) {
                    vtr = (Vector) data.get(i);
                    String brCode = CbsUtil.lPadding(2, Integer.parseInt(vtr.get(1).toString()));
                    branchlist.add(new SelectItem(brCode, vtr.get(0).toString()));
                    i++;
                }
            }
            noofshareAvailable = certIssueRemote.getAvailableShare(getOrgnBrCode() + CbsAcCodeConstant.SF_AC.getAcctCode() + "00000001");
            sharevalue = certIssueRemote.getperShareValue();
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }
    String acNature = "";

    public void onBlurAcno() {
        DecimalFormat formatter = new DecimalFormat("#.##");
        flag = false;
        dividendFlag = false;
        disableAmt = false;
        List acctdetails = null;
        msg = "";
        if (oldAcno == null || oldAcno.equalsIgnoreCase("") || oldAcno.length() != 12) {
            msg = "Please enter 12 digit account number.";
            return;
        }
        try {
            acno = ftsPostingRemote.getNewAccountNumber(oldAcno);
            acNature = ftsPostingRemote.getAccountNature(acno);

            if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                acctdetails = certIssueRemote.getAccountDetailsGlhead(acno);
                Vector vtr = (Vector) acctdetails.get(0);
                if (vtr.get(3).toString().equalsIgnoreCase("40")) {
                    btn_refresh();
                }

//                if (acno.substring(0, 2).equals(getOrgnBrCode())) {
//                    acctdetails = certIssueRemote.getAccountDetailsGlhead(acno);
//                } else {
                if (!(ftsPostingRemote.getCodeForReportName("REMOTE_GL_TXN").equals(1) && !vtr.get(3).toString().equalsIgnoreCase("4"))) {
                    if (!(acno.substring(0, 2).equals(getOrgnBrCode())) && (ftsPostingRemote.getOfficeAccountNo(acno).equalsIgnoreCase("false"))) {
                        setAcno("");
                        setOldAcno("");
                        msg = "Txn is not allowed for other branch GL Heads.";
                        return;
                    }
                }

                // }
            } else {
                acctdetails = certIssueRemote.getAccountDetailsNormal(acno);
            }
            if (acctdetails.isEmpty()) {
                setAcno("");
                setOldAcno("");
                msg = "Account No Does Not exist";
                return;
            }
            Vector vtr = (Vector) acctdetails.get(0);
            if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                if (vtr.get(3).toString().equalsIgnoreCase("50")) {
                    flag = true;
                    shareCheck = true;
                } else if (vtr.get(3).toString().equalsIgnoreCase("40")) {
                    dividendFlag = true;
                    type = "1";
                    shareCheck = true;
                } else {
                    shareCheck = false;
                }
                balance = formatter.format(certIssueRemote.getBalanceAccountNo(acno, acNature));
                accountname = vtr.get(0).toString();
                accountopening = "";
            } else {
                shareCheck = false;
                flag = false;
                accountname = vtr.get(0).toString();
                accountopening = vtr.get(1).toString().substring(6, 8) + "/" + vtr.get(1).toString().substring(4, 6) + "/" + vtr.get(1).toString().substring(0, 4);
                String acctStatus = vtr.get(2).toString();
                if (Integer.parseInt(acctStatus) == 2) {
                    this.setMsg("Account Has been marked Inoperative");
                    return;
                } else if (Integer.parseInt(acctStatus) == 3) {
                    this.setMsg("Account Has been marked Suit Filed");
                    return;
                } else if (Integer.parseInt(acctStatus) == 4) {
                    this.setMsg("Account Has been marked Frozen");
                    return;
                } else if (Integer.parseInt(acctStatus) == 5) {
                    this.setMsg("Account Has been marked Recalled");
                    return;
                } else if (Integer.parseInt(acctStatus) == 6) {
                    this.setMsg("Account Has been marked Decreed");
                    return;
                } else if (Integer.parseInt(acctStatus) == 7) {
                    this.setMsg("Withdrawal is not Allowed in this Account");
                    return;
                } else if (Integer.parseInt(acctStatus) == 8) {
                    this.setMsg("Account Has been marked Operation Stopped");
                    return;
                } else if (Integer.parseInt(acctStatus) == 9) {
                    this.setMsg("Account Has been Closed");
                    return;
                } else if (Integer.parseInt(acctStatus) == 11) {
                    this.setMsg("This Account is SUB STANDARD Account");
                    return;
                } else if (Integer.parseInt(acctStatus) == 12) {
                    this.setMsg("This Account is DOUBT FUL Account");
                    return;
                } else if (Integer.parseInt(acctStatus) == 13) {
                    this.setMsg("This Account is LOSS Account");
                    return;
                } else if (Integer.parseInt(acctStatus) == 14) {
                    this.setMsg("This Account is PROTESTED BILL Account");
                    return;
                } else {
                    this.setMsg("");
                }
                balance = formatter.format(certIssueRemote.getBalanceAccountNo(acno, acNature));
            }
            sharevalue = certIssueRemote.getperShareValue();
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }

    }

    public void selectGlHeadOnPressF6() {
        try {
            transRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup("TransactionManagementFacade");
            String query = "select AcName,acno from gltable where substring(acno,1,2)='" + getOrgnBrCode() + "' order by AcName";
            List listF6 = transRemote.getDataForF6(query);
            if (!listF6.isEmpty()) {
                this.setMsg("");
                listForF6 = new ArrayList<GLHeadGrid>();
                for (int i = 0; i < listF6.size(); i++) {
                    glHeadGrid = new GLHeadGrid();
                    Vector vecF6 = (Vector) listF6.get(i);
                    glHeadGrid.setAccName(vecF6.get(0).toString());
                    glHeadGrid.setGlhead(vecF6.get(1).toString());
                    listForF6.add(glHeadGrid);
                }
            } else {
                this.setMsg("No Data For GL Head.");
            }
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void sharefoliodetails() {
        shareMsg = "";
        datagridcert = new ArrayList<CertIssueTable>();

        try {
            if (folionoShow == null || folionoShow.equalsIgnoreCase("")) {
                setShareMsg("Please fill Folio No.");
                return;
            }
            if (folionoShow.length() < 12) {
                setShareMsg("Please fill 12 digit Folio No.");
                return;
            }
            foliono = ftsPostingRemote.getNewAccountNumber(folionoShow);
            if (foliono.length() != 12) {
                setShareMsg("Please Check Folio No");
                return;
            }
            List foliodt = certIssueRemote.getShareFolioDetails(foliono);
            if (foliodt.isEmpty()) {
                setShareMsg("Please Check Folio No");
                return;
            } else {
                Vector vtr = (Vector) foliodt.get(0);
                String auth = vtr.get(3) != null ? vtr.get(3).toString() : "";
                if (auth.equalsIgnoreCase("Y")) {
                    folioname = vtr.get(0).toString();
                    foliofathername = vtr.get(1).toString();
                    city = vtr.get(2).toString();
                    statuslist = new ArrayList<SelectItem>();
                    statuslist.add(new SelectItem("Active", "Active"));
                    setStatus("Active");
                    double folioBal = 0;
                    List details = certIssueRemote.getShareDetails(foliono);
                    for (int i = 0; i < details.size(); i++) {
                        Vector vtr1 = (Vector) details.get(i);
                        CertIssueTable grid = new CertIssueTable();

                        grid.setCertNo(vtr1.get(0).toString());
                        grid.setNoofshares(vtr1.get(1).toString());
                        grid.setFromNo(vtr1.get(2).toString());
                        grid.setToNo(vtr1.get(3).toString());

                        grid.setAmount(Integer.parseInt(vtr1.get(1).toString()) * Float.parseFloat(vtr1.get(12).toString()) + "");
                        grid.setIssuedate(sdf.format((Date) (vtr1.get(4) != null ? vtr1.get(4) : new Date())));
                        grid.setIssueBy(vtr1.get(5) != null ? vtr1.get(5).toString() : "");
                        grid.setStatus(vtr1.get(11) != null ? vtr1.get(11).toString() : "");

                        grid.setCertIssueDt(sdf.format((Date) vtr1.get(9) != null ? vtr1.get(9) : new Date()));
                        grid.setRemarks(vtr1.get(10).toString().equals("null") ? "" : vtr1.get(10).toString());
                        grid.setAdviceNo(vtr1.get(6) != null ? vtr1.get(6).toString() : "");
                        grid.setPoNo(vtr1.get(7) != null ? vtr1.get(7).toString() : "");
                        grid.setShareValue(vtr1.get(12).toString());
                        if(grid.getStatus().equalsIgnoreCase("ACTIVE"))
                            folioBal = folioBal + Double.parseDouble(grid.getAmount());
                        datagridcert.add(grid);
                    }
                    setFolioBalance(formatter.format(folioBal));
                } else {
                    setShareMsg("Account verification is pending for this account.");
                }
                getSignatureDetail();
            }
        } catch (ApplicationException e) {
            setMsg(e.getMessage());
        } catch (Exception e) {
            setMsg(e.getMessage());
        }
    }

    public void sharefolioInformation() {
        setShareMsg("");
        try {
            if (folionoShow == null || folionoShow.equalsIgnoreCase("")) {
                setShareMsg("Please fill Folio No.");
                return;
            }
            if (folionoShow.length() < 12) {
                setShareMsg("Please fill 12 digit Folio No.");
                return;
            }
            foliono = ftsPostingRemote.getNewAccountNumber(folionoShow);
            if (foliono.length() != 12) {
                setShareMsg("Please Check Folio No");
                return;
            }

            List isUnathorizeFolioList = certIssueRemote.isUnathorizeFolio(foliono, ymd.format(date));
            if (!isUnathorizeFolioList.isEmpty()) {
                setShareMsg("This Folio No Payment Already exist,Please Authorize the Batch.");
                return;
            }

            List foliodt = certIssueRemote.getShareFolioDetails(foliono);
            if (foliodt.isEmpty()) {
                setShareMsg("Please Check Folio No");
                return;
            } else {
                Vector vtr = (Vector) foliodt.get(0);
                String auth = vtr.get(3) != null ? vtr.get(3).toString() : "";
                if (auth.equalsIgnoreCase("Y")) {
                    folioname = vtr.get(0).toString();
                    foliofathername = vtr.get(1).toString();
                    city = vtr.get(2).toString();
                    dividendBal = certIssueRemote.getDividendBal(foliono, ymd.format(date));
                }
            }
        } catch (Exception ex) {
            setShareMsg(ex.getMessage());
        }
    }

    public void onbularDividend() {
        setMsg("");
        try {
            if (Double.parseDouble(dividendAmount) > dividendBal) {
                setMsg("Amount should not be greater than  dividend balance.");
                return;
            }
        } catch (Exception ex) {
            setMsg(ex.getMessage());
        }
    }

    public void btn_update() throws Exception {
        setShareMsg("");
        if (noshare < 0) {
            setShareMsg("No of share is not less than zero");
            return;
        }

        if (!sharePaid) {
            if (issueDate.after(date)) {
                setShareMsg("Issue date less than today date");
                return;
            }
            if (noofshareAvailable < noshare) {
                setShareMsg("No of share is less than total no of share");
                return;
            }
            hoamount = noshare * sharevalue;
            String shareMaxLimit = ftsPostingRemote.getValueFromCbsparameterInfo("SHARE-MAX-LIMIT");
            if(!shareMaxLimit.equals("") && !shareMaxLimit.equals("0")){
                if(Double.parseDouble(folioBalance) + hoamount > Double.parseDouble(shareMaxLimit)){
                    setShareMsg("You can not issue the share above the maximum limit("+ shareMaxLimit +"). ");
                    return;
                }
            }
            this.setType("0");
        } else {
            if (ftsPostingRemote.getCodeForReportName("SHARE-LOAN-CHECK") == 1 && certIssueRemote.isLoanOutStanding(foliono)) {
                setShareMsg("Please sattle the Loan Accounts before the Share Payment");
                return;
            }
            int paidShare = Integer.parseInt(sharesToBePaid);
            if (paidShare <= 0) {
                setShareMsg("No of share to be paid is greater than 0");
                return;
            }
            if (noshare < paidShare) {
                setShareMsg("No of share to be paid is less than total No of Share");
                return;
            }
            hoamount = paidShare * sharevalue;
        }
        this.disableAmt = true;
    }

    public void dividendBnt() throws Exception {
        setShareMsg("");
        try {
            if (Double.parseDouble(dividendAmount) > dividendBal) {
                setMsg("Amount should not be greater than  dividend balance.");
                return;
            }
            hoamount = Double.parseDouble(dividendAmount);
            this.disableAmt = true;
        } catch (Exception ex) {
            setMsg(ex.getMessage());
        }
    }

    public void btn_refresh() {
        if (datagridcert != null) {
            datagridcert.clear();
        }
        folionoShow = "";
        foliono = "";
        foliofathername = "";
        folioname = "";
        city = "";
        remark = "";
        shareamt = 0;
        issueDate = new Date();
        certificateno = "0";
        refundDate = null;
        noshare = 0;
        setShareMsg("");
        balance = "";
        sharesToBePaid = "";
        statuslist = new ArrayList<SelectItem>();
        statuslist.add(new SelectItem("Active", "Active"));
        setStatus("Active");
        dividendAmount = "";
        dividendBal = 0;
        this.disableAmt = true;
    }

    public void sharePopupClose() {
        if (datagridcert != null) {
            datagridcert.clear();
        }
        folionoShow = "";
        foliono = "";
        foliofathername = "";
        folioname = "";
        city = "";
        remark = "";
        shareamt = 0;
        issueDate = new Date();
        certificateno = "0";
        refundDate = null;
        noshare = 0;
        setShareMsg("");
        balance = "";
        sharesToBePaid = "";
        statuslist = new ArrayList<SelectItem>();
        statuslist.add(new SelectItem("Active", "Active"));
        setStatus("Active");
        shareCheck = false;
    }

    public void selectData() throws Exception {
        if (!currentdata.getStatus().equalsIgnoreCase("Inactive")) {
            statuslist = new ArrayList<SelectItem>();
            statuslist.add(new SelectItem("Inactive", "Inactive"));
            statuslist.add(new SelectItem("Partial", "Partial"));
            sharePaid = true;
            setStatus("Inactive");
            noshare = Integer.parseInt(currentdata.getNoofshares());
            refundbool = true;
            refundDate = new Date();
            remark = currentdata.getRemarks();
            shareamt = Double.parseDouble(currentdata.getAmount());
            issueDate = sdf.parse(currentdata.getIssuedate());
            certificateno = currentdata.getCertNo();
            this.sharevalue = Double.parseDouble(currentdata.getShareValue());
            this.setType("1");
        }
    }

    public void saveBatch() {
        try {
            msg = certIssueRemote.saveData(getUserName(), getOrgnBrCode(), bacthtable);
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
        cancelBatch();
    }
    DecimalFormat formatter = new DecimalFormat("#.##");

    public void saveBtn() {

        try {
            setMsg("");
            if (oldAcno == null || oldAcno.equalsIgnoreCase("") || oldAcno.length() != 12) {
                return;
            }
            if (acno == null || acno.equalsIgnoreCase("") || acno.length() != 12) {
                msg = "Please enter proper account number";
                return;
            }
            if (valueDt == null) {
                msg = "Please enter value date";
                return;
            }
            if (valueDt.after(date)) {
                msg = "Value date less than or equal to today date";
                return;
            }
            for (HoTransactionTable item : bacthtable) {
                if (!valueDt.equals(item.getValDt())) {
                    msg = "Value date of this batch is same";
                    return;
                }
            }
            if (hoamount <= 0) {
                msg = "Please enter valid amount ";
                return;
            }
            /**
             * Small Account Checking.
             */
            if ((this.strtrantype.equals("0") || this.strtrantype.equals("2")) && this.type.equals("0")) {
                List paramList = ftsPostingRemote.getBaseParameter(ftsPostingRemote.getAccountCode(acno));
                if (!paramList.isEmpty()) {
                    Vector paramVector = (Vector) paramList.get(0);
                    BigDecimal maxDepInFinYear = new BigDecimal(paramVector.get(1).toString());
                    BigDecimal balAtAnyTime = new BigDecimal(paramVector.get(3).toString());

                    paramList = ftsPostingRemote.getCurrentFinYear(getOrgnBrCode());
                    paramVector = (Vector) paramList.get(0);
                    String minFinDt = paramVector.get(0).toString();
                    BigDecimal totalFinYearDeposit = txnRemote.getFinYearDepositAsWellAsMonthWithdrawalTillDate(acno, minFinDt);
                    BigDecimal totalFinYearPlusCrAmt = totalFinYearDeposit.add(new BigDecimal(this.hoamount));
                    if (totalFinYearPlusCrAmt.compareTo(maxDepInFinYear) == 1) {
                        msg = "You can not deposit more than financial year limit. Remaining deposit limit is " + maxDepInFinYear.subtract(totalFinYearDeposit) + " only";
                        return;
                    }
                    BigDecimal actualBalance = new BigDecimal(ftsPostingRemote.ftsGetBal(acno));
                    BigDecimal actualBalPlusCrAmt = actualBalance.add(new BigDecimal(this.hoamount));
                    if (actualBalPlusCrAmt.compareTo(balAtAnyTime) == 1) {
                        msg = "You can not deposit more than balance limit. Remaining deposit balance limit is " + balAtAnyTime.subtract(actualBalance) + " only";
                        return;
                    }
                }
            }
            if ((this.strtrantype.equals("0") || this.strtrantype.equals("2")) && this.type.equals("1")) {
                List paramList = ftsPostingRemote.getBaseParameter(ftsPostingRemote.getAccountCode(acno));
                if (!paramList.isEmpty()) {
                    Vector paramVector = (Vector) paramList.get(0);
                    BigDecimal cashTrfDrInMonth = new BigDecimal(paramVector.get(2).toString());

                    BigDecimal totalMonthWithdrawal = txnRemote.getFinYearDepositAsWellAsMonthWithdrawalTillDate(acno, "");
                    BigDecimal totalMonthWithPlusDrAmt = totalMonthWithdrawal.add(new BigDecimal(this.hoamount));
                    if (totalMonthWithPlusDrAmt.compareTo(cashTrfDrInMonth) == 1) {
                        msg = "You can not withdrawal more than month limit. Remaining withdrawal limit is " + cashTrfDrInMonth.subtract(totalMonthWithdrawal) + " only";
                        return;
                    }
                }
            }
            /**
             * Small Account Checking End.
             */
            String chkBalResult = ftsPostingRemote.chkBal(this.getAcno(), Integer.parseInt(this.type), Integer.parseInt(this.strRelate), acNature);
            if (!chkBalResult.equalsIgnoreCase("TRUE")) {
                String chkBalance = ftsPostingRemote.checkBalance(this.getAcno(), this.getHoamount(), getUserName());
                if (!chkBalance.equalsIgnoreCase("TRUE")) {
                    this.setMsg(chkBalance);
                    return;
                }
            }
            String details = "";
            String msgFlag = "";

            if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                List list = certIssueRemote.getAccountDetailsGlhead(acno);
                Vector vtr = (Vector) list.get(0);
                msgFlag = vtr.get(3).toString();
            }

            if (!msgFlag.equalsIgnoreCase("40")) {
                if (shareCheck && !foliono.equals("")) {
                    if (sharePaid) {
                        details = " Share " + "~!" + false + "~@" + foliono + "~@" + (certificateno == null ? "0" : certificateno) + "~@" + ymd.format(issueDate) + "~@" + status + "~@" + sharesToBePaid;
                    } else {
                        if (!valueDt.equals(issueDate)) {
                            msg = "Value date must be equal to Issue date.";
                            return;
                        }
                        if (issueDate.after(date)) {
                            msg = "Issue date less than today date.";
                            return;
                        }
                        if (noofshareAvailable < noshare) {
                            msg = "No of share is less than total no of share.";
                            return;
                        }
                        details = " Share " + "~!" + true + "~@" + foliono + "~@" + poNo + "~@" + advNo + "~@" + remark + "~@" + noshare + "~@" + ymd.format(issueDate);
                    }
                }
            } else {
                if (hoamount > dividendBal) {
                    setMsg("Amount should not be greater than  dividend balance.");
                    return;
                }
                details = "Dividend Payment of: " + foliono;
            }

            HoTransactionTable grid = new HoTransactionTable();
            grid.setAcno(acno);
            grid.setAmount(hoamount);
            grid.setTranType(strtrantype);
            grid.setDt(accountdate);
            grid.setEnteredby(getUserName());
            grid.setTranDesc(strRelate);
            if(msgFlag.equalsIgnoreCase("40")){
                 grid.setDetails(details);  
            }else{
               grid.setDetails(hodetails.trim() + details);  
            }
            grid.setViewDetails(hodetails);
            grid.setDestBrId(ftsPostingRemote.getCurrentBrnCode(acno));
            grid.setOrgBrId(getOrgnBrCode());
            grid.setTy(type);
            float rec = ftsPostingRemote.getRecNo();
            grid.setValDt(valueDt);
            grid.setRecNo(rec);
            grid.setAuth("N");
            grid.setPayBy(tranby);
            grid.setInstDt(null);
            grid.setVoucherNo(0);
            grid.setIntFlag("");
            grid.setCxsxFlag("");
            grid.setScreenFlag("");
            grid.setCloseFlag("");
            grid.setTxnStatus("");
            grid.setTermId("");
            grid.setAuthBy("");
            grid.setSubTokeNo("");
            grid.setInstNo("");
            grid.setInstDt("");
            if (msgFlag.equalsIgnoreCase("40")) {
                grid.setTdAcnoDt(foliono);
            } else {
                grid.setTdAcnoDt("");
            }

            if (type.equalsIgnoreCase("0")) {
                grid.setCrAmtHo(hoamount);
            } else {
                grid.setDrAmtHo(hoamount);
            }
            if (type.equalsIgnoreCase("0")) {
                crAmt = Double.parseDouble(formatter.format(crAmt).toString()) + hoamount;
            } else {
                drAmt = Double.parseDouble(formatter.format(drAmt).toString()) + hoamount;
            }
            grid.setAdviseNo(adviceNo);
            grid.setAdviseBranch(orignbranch);
            bacthtable.add(grid);
            if (Double.parseDouble(formatter.format(drAmt).toString()) == Double.parseDouble(formatter.format(crAmt).toString()) && Double.parseDouble(formatter.format(drAmt).toString()) != 0) {
                boolsavebatch = false;
            }
            details = "";
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
        btn_refresh();
        accountname = "";
        accountopening = "";
        orignbranch = "";
        accountdate = date;
        strtrantype = "";
        destbranch = "";
        originDate = date;
        valueDt = date;
        type = "";
        tranby = "";
        adviceNo = "";
        strRelate = "";
        hoamount = 0.0;
        acno = "";
        oldAcno = "";
        balance = "";
    }

    public void onchangAdvice() {
        if (advise.equalsIgnoreCase("Automatic")) {
            adviceBoolean = false;
        } else {
            adviceBoolean = true;
            if (advise.equalsIgnoreCase("AD")) {
                advNo = adviceNo;
            } else {
                poNo = adviceNo;
            }
        }
    }

    public void cancelBatch() {
        if (bacthtable != null) {
            bacthtable.clear();
        }
        crAmt = 0;
        drAmt = 0;
        shareCheck = false;
        boolsavebatch = true;
        sharePaid = false;
    }

    public void cancelBtn() {
        msg = "";
        accountname = "";
        accountopening = "";
        orignbranch = "";
        accountdate = date;
        strtrantype = "";
        destbranch = "";
        originDate = date;
        valueDt = date;
        type = "";
        tranby = "";
        adviceNo = "";
        advise = "";
        strRelate = "";
        hoamount = 0.0;
        acno = "";
        oldAcno = "";
        bacthtable.clear();
        balance = "";
        boolsavebatch = true;
        sharePaid = false;
    }

    public void deleteRow() {
        bacthtable.remove(currentData);
        crAmt = crAmt - currentData.getCrAmtHo();
        drAmt = drAmt - currentData.getDrAmtHo();
        if (Double.parseDouble(formatter.format(drAmt).toString()) != Double.parseDouble(formatter.format(crAmt).toString())) {
            boolsavebatch = true;
        }
    }

    public void enableShareNoToBePaid() {
        if (getStatus().equalsIgnoreCase("Partial")) {
            sharesToBePaid = "";
            setBoolshareno(false);
        } else {
            sharesToBePaid = String.valueOf(noshare);
            setBoolshareno(true);
        }
    }

    public void acctViewAuthUnAuth() {
        try {
            txnDetailList = new ArrayList<TxnDetailBean>();
            txnDetailUnAuthList = new ArrayList<TxnDetailBean>();

            if (this.acno == null) {
                this.setMsg("Please Double Click On Row To View A/C Details");
                return;
            }
            if (this.acno.equals("")) {
                this.setMsg("Please Double Click On Row To View A/C Details");
                return;
            }
            this.setMsg("");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar now = Calendar.getInstance();
            now.roll(Calendar.MONTH, false);
            String oneMonthBeforeDate = sdf.format(now.getTime());

            String openBal = txnRemote.getOpeningBalF4(acno, oneMonthBeforeDate);
            if (!openBal.equals("FALSE")) {

                this.setOpenBalance(new BigDecimal(openBal));
            }

            String prsntBal = txnRemote.getOpeningBalF4(acno, getTodayDate());
            if (!prsntBal.equals("FALSE")) {

                this.setPresentBalance(new BigDecimal(prsntBal));
            }

            txnDetailList = txnRemote.accViewAuth(acno, getTodayDate(), getOrgnBrCode());
            txnDetailUnAuthList = txnRemote.accViewUnauth(acno, getTodayDate(), getOrgnBrCode());

        } catch (ApplicationException e) {
            this.setMsg(e.getMessage());
        } catch (Exception e) {
            this.setMsg(e.getLocalizedMessage());
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
            String sign;
            sign = txnAuthRemote.getSignatureDetails(this.getFolionoShow());
            if (!sign.equalsIgnoreCase("Signature not found")) {
                String imageCode = sign.trim();
                String directoryPath = CbsUtil.getSigFilePath(imageCode.substring(4), imageCode.substring(0, 4));
                String encryptAcno = CbsUtil.encryptText(this.getFolionoShow());
                String filePath = directoryPath + encryptAcno + ".xml";
                imageData = CbsUtil.readImageFromXMLFile(new File(filePath));
            }
        } catch (FileNotFoundException e) {
            this.setMsg("");
        } catch (Exception e) {
            this.setMsg(e.getMessage());
        }
    }
}
