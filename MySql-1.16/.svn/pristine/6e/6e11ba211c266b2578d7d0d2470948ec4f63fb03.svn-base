/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.other.BankProcessManagementFacadeRemote;
import com.cbs.facade.other.PrintFacadeRemote;
import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.web.pojo.other.IssuePassBookDtl;
import com.cbs.servlets.Init;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.print.SiplExporter;
import com.cbs.web.print.SiplPage;
import com.cbs.web.print.SiplText;
import com.hrms.web.utils.WebUtil;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.Socket;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author sipl
 */
public class PassBookPrint extends BaseBean {

    TransactionManagementFacadeRemote tranRemote;
    FtsPostingMgmtFacadeRemote ftsremote;
    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String acNo, acNoLen;
    private String acNoMsg;
    private String acName;
    private BigDecimal balance;
    private String passBookNo;
    private String prnFrmDate;
    private String prnAcNo;
    private String prnAcNoMsg;
    private String prnAcName;
    private BigDecimal prnBalance;
    private String canAcNo;
    private String canAcNoMsg;
    private String canAcName;
    private BigDecimal canBalance;
    private String reason;
    private int msgFlag;
    private String found;
    int gDateAlign;
    int gDateWidth;
    int gCheqNoAlign;
    int gCheqNoWidth;
    int gParticlrAlign;
    int gParticlrWidth;
    int gDrAmtAlign;
    int gDrAmtWidth;
    int gCrAmtAlign;
    int gCrAmtWidth;
    int gBalAlign;
    int gBalWidth;
    int HalfLine;
    int SkipLine;
    int PassLine;
    int BeginLines;
    String flag1 = "none";
    boolean FirstUpDate;
    String Pstat;
    String DateFrom;
    double recmax;
    BigDecimal bal;
    int rwcnt;
    int VelFwdLine;
    int prow;
    double pno;
    int PrtLine;
    int rnum;
    long cnum;
    int rwcount;
    String flag2;
    String flag3;
    boolean status;
    Date printFrom;
    double Curbal;
    String s;
    String s1;
    String filename;
    String reply;
    String flag4 = "false";
    String flag5;
    String flag6;
    String flag7;
    String firstPrintMsg;
    String fileNameFirstPrnt;
    IssuePassBookDtl issPDtl;
    List<IssuePassBookDtl> issPassList;
    private HttpServletRequest req;
    String flagForReprint = "false";
    private Socket socketTCP;
    public static int PORT = 2001;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private final String jndiHomeName = "BankProcessManagementFacade";
    private final String jndiHomeNameTxn = "TransactionManagementFacade";
    private BankProcessManagementFacadeRemote beanRemote = null;
    //private InitialContext ctx;
    private String printNewAcno;
    private String issueNewAcno;
    private String cancelNewAcno;
    String checkdate;
    String maxDate;
    private PrintFacadeRemote printRemote = null;
    private List<String> passbookDetailList;

    public String getCancelNewAcno() {
        return cancelNewAcno;
    }

    public void setCancelNewAcno(String cancelNewAcno) {
        this.cancelNewAcno = cancelNewAcno;
    }

    public String getIssueNewAcno() {
        return issueNewAcno;
    }

    public void setIssueNewAcno(String issueNewAcno) {
        this.issueNewAcno = issueNewAcno;
    }

    public String getPrintNewAcno() {
        return printNewAcno;
    }

    public void setPrintNewAcno(String printNewAcno) {
        this.printNewAcno = printNewAcno;
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

    public IssuePassBookDtl getIssPDtl() {
        return issPDtl;
    }

    public void setIssPDtl(IssuePassBookDtl issPDtl) {
        this.issPDtl = issPDtl;
    }

    public List<IssuePassBookDtl> getIssPassList() {
        return issPassList;
    }

    public void setIssPassList(List<IssuePassBookDtl> issPassList) {
        this.issPassList = issPassList;
    }

    public String getCanAcNo() {
        return canAcNo;
    }

    public void setCanAcNo(String canAcNo) {
        this.canAcNo = canAcNo;
    }

    public String getCanAcNoMsg() {
        return canAcNoMsg;
    }

    public void setCanAcNoMsg(String canAcNoMsg) {
        this.canAcNoMsg = canAcNoMsg;
    }

    public String getPrnAcName() {
        return prnAcName;
    }

    public void setPrnAcName(String prnAcName) {
        this.prnAcName = prnAcName;
    }

    public String getPrnAcNo() {
        return prnAcNo;
    }

    public void setPrnAcNo(String prnAcNo) {
        this.prnAcNo = prnAcNo;
    }

    public String getPrnAcNoMsg() {
        return prnAcNoMsg;
    }

    public void setPrnAcNoMsg(String prnAcNoMsg) {
        this.prnAcNoMsg = prnAcNoMsg;
    }

    public BigDecimal getPrnBalance() {
        return prnBalance;
    }

    public void setPrnBalance(BigDecimal prnBalance) {
        this.prnBalance = prnBalance;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }

    public String getAcNoMsg() {
        return acNoMsg;
    }

    public void setAcNoMsg(String acNoMsg) {
        this.acNoMsg = acNoMsg;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getPassBookNo() {
        return passBookNo;
    }

    public void setPassBookNo(String passBookNo) {
        this.passBookNo = passBookNo;
    }

    public String getPrnFrmDate() {
        return prnFrmDate;
    }

    public void setPrnFrmDate(String prnFrmDate) {
        this.prnFrmDate = prnFrmDate;
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

    public int getMsgFlag() {
        return msgFlag;
    }

    public void setMsgFlag(int msgFlag) {
        this.msgFlag = msgFlag;
    }

    public String getCanAcName() {
        return canAcName;
    }

    public void setCanAcName(String canAcName) {
        this.canAcName = canAcName;
    }

    public BigDecimal getCanBalance() {
        return canBalance;
    }

    public void setCanBalance(BigDecimal canBalance) {
        this.canBalance = canBalance;
    }

    public String getFound() {
        return found;
    }

    public void setFound(String found) {
        this.found = found;
    }

    public int getgBalAlign() {
        return gBalAlign;
    }

    public void setgBalAlign(int gBalAlign) {
        this.gBalAlign = gBalAlign;
    }

    public int getgBalWidth() {
        return gBalWidth;
    }

    public void setgBalWidth(int gBalWidth) {
        this.gBalWidth = gBalWidth;
    }

    public int getgCheqNoAlign() {
        return gCheqNoAlign;
    }

    public void setgCheqNoAlign(int gCheqNoAlign) {
        this.gCheqNoAlign = gCheqNoAlign;
    }

    public int getgCheqNoWidth() {
        return gCheqNoWidth;
    }

    public void setgCheqNoWidth(int gCheqNoWidth) {
        this.gCheqNoWidth = gCheqNoWidth;
    }

    public int getgCrAmtAlign() {
        return gCrAmtAlign;
    }

    public void setgCrAmtAlign(int gCrAmtAlign) {
        this.gCrAmtAlign = gCrAmtAlign;
    }

    public int getgCrAmtWidth() {
        return gCrAmtWidth;
    }

    public void setgCrAmtWidth(int gCrAmtWidth) {
        this.gCrAmtWidth = gCrAmtWidth;
    }

    public int getgDateAlign() {
        return gDateAlign;
    }

    public void setgDateAlign(int gDateAlign) {
        this.gDateAlign = gDateAlign;
    }

    public int getgDateWidth() {
        return gDateWidth;
    }

    public void setgDateWidth(int gDateWidth) {
        this.gDateWidth = gDateWidth;
    }

    public int getgDrAmtAlign() {
        return gDrAmtAlign;
    }

    public void setgDrAmtAlign(int gDrAmtAlign) {
        this.gDrAmtAlign = gDrAmtAlign;
    }

    public int getgDrAmtWidth() {
        return gDrAmtWidth;
    }

    public void setgDrAmtWidth(int gDrAmtWidth) {
        this.gDrAmtWidth = gDrAmtWidth;
    }

    public int getgParticlrAlign() {
        return gParticlrAlign;
    }

    public void setgParticlrAlign(int gParticlrAlign) {
        this.gParticlrAlign = gParticlrAlign;
    }

    public int getgParticlrWidth() {
        return gParticlrWidth;
    }

    public void setgParticlrWidth(int gParticlrWidth) {
        this.gParticlrWidth = gParticlrWidth;
    }

    public int getBeginLines() {
        return BeginLines;
    }

    public void setBeginLines(int BeginLines) {
        this.BeginLines = BeginLines;
    }

    public int getHalfLine() {
        return HalfLine;
    }

    public void setHalfLine(int HalfLine) {
        this.HalfLine = HalfLine;
    }

    public int getPassLine() {
        return PassLine;
    }

    public void setPassLine(int PassLine) {
        this.PassLine = PassLine;
    }

    public int getSkipLine() {
        return SkipLine;
    }

    public void setSkipLine(int SkipLine) {
        this.SkipLine = SkipLine;
    }

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public boolean isFirstUpDate() {
        return FirstUpDate;
    }

    public void setFirstUpDate(boolean FirstUpDate) {
        this.FirstUpDate = FirstUpDate;
    }

    public String getPstat() {
        return Pstat;
    }

    public void setPstat(String Pstat) {
        this.Pstat = Pstat;
    }

    public String getDateFrom() {
        return DateFrom;
    }

    public void setDateFrom(String DateFrom) {
        this.DateFrom = DateFrom;
    }

    public BigDecimal getBal() {
        return bal;
    }

    public void setBal(BigDecimal bal) {
        this.bal = bal;
    }

    public double getRecmax() {
        return recmax;
    }

    public void setRecmax(double recmax) {
        this.recmax = recmax;
    }

    public int getRwcnt() {
        return rwcnt;
    }

    public void setRwcnt(int rwcnt) {
        this.rwcnt = rwcnt;
    }

    public int getVelFwdLine() {
        return VelFwdLine;
    }

    public void setVelFwdLine(int VelFwdLine) {
        this.VelFwdLine = VelFwdLine;
    }

    public String getFlag2() {
        return flag2;
    }

    public void setFlag2(String flag2) {
        this.flag2 = flag2;
    }

    public String getFlag3() {
        return flag3;
    }

    public void setFlag3(String flag3) {
        this.flag3 = flag3;
    }

    public int getProw() {
        return prow;
    }

    public void setProw(int prow) {
        this.prow = prow;
    }

    public double getPno() {
        return pno;
    }

    public void setPno(double pno) {
        this.pno = pno;
    }

    public int getPrtLine() {
        return PrtLine;
    }

    public void setPrtLine(int PrtLine) {
        this.PrtLine = PrtLine;
    }

    public long getCnum() {
        return cnum;
    }

    public void setCnum(long cnum) {
        this.cnum = cnum;
    }

    public int getRnum() {
        return rnum;
    }

    public void setRnum(int rnum) {
        this.rnum = rnum;
    }

    public int getRwcount() {
        return rwcount;
    }

    public void setRwcount(int rwcount) {
        this.rwcount = rwcount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getPrintFrom() {
        return printFrom;
    }

    public void setPrintFrom(Date printFrom) {
        this.printFrom = printFrom;
    }

    public double getCurbal() {
        return Curbal;
    }

    public void setCurbal(double Curbal) {
        this.Curbal = Curbal;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getFlag4() {
        return flag4;
    }

    public void setFlag4(String flag4) {
        this.flag4 = flag4;
    }

    public String getFlag5() {
        return flag5;
    }

    public void setFlag5(String flag5) {
        this.flag5 = flag5;
    }

    public String getFlag6() {
        return flag6;
    }

    public void setFlag6(String flag6) {
        this.flag6 = flag6;
    }

    public String getFirstPrintMsg() {
        return firstPrintMsg;
    }

    public void setFirstPrintMsg(String firstPrintMsg) {
        this.firstPrintMsg = firstPrintMsg;
    }

    public String getFlag7() {
        return flag7;
    }

    public void setFlag7(String flag7) {
        this.flag7 = flag7;
    }

    public String getFileNameFirstPrnt() {
        return fileNameFirstPrnt;
    }

    public void setFileNameFirstPrnt(String fileNameFirstPrnt) {
        this.fileNameFirstPrnt = fileNameFirstPrnt;
    }

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public String getFlagForReprint() {
        return flagForReprint;
    }

    public void setFlagForReprint(String flagForReprint) {
        this.flagForReprint = flagForReprint;
    }

    public String getCheckdate() {
        return checkdate;
    }

    public void setCheckdate(String checkdate) {
        this.checkdate = checkdate;
    }

    public String getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(String maxDate) {
        this.maxDate = maxDate;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    /*
     * Constructor of the class that Initializes the value viz. Branch Code, Login User And Login Date
     */
    public PassBookPrint() {
        try {
            beanRemote = (BankProcessManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            ftsremote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            tranRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTxn);
            printRemote = (PrintFacadeRemote) ServiceLocator.getInstance().lookup("PrintManagementFacade");
            this.setAcNoLen(getAcNoLength());
            req = getRequest();
            getDetailsOnLoad();
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            issPassList = new ArrayList<IssuePassBookDtl>();
            issPassList = new ArrayList<IssuePassBookDtl>();
        } catch (ApplicationException e) {
            this.setPrnAcNoMsg(e.getMessage());
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    /**
     * ****************************************************************************************************
     */
    public void getDetailsOnLoad() {
        try {
            List l = beanRemote.getDetailsOnLoad1();
            for (int j = 0; j < l.size(); j++) {
                Vector vec = (Vector) l.get(j);
                if (vec.get(0).toString().equalsIgnoreCase("date")) {
                    gDateAlign = Integer.parseInt(vec.get(2).toString());
                    gDateWidth = Integer.parseInt(vec.get(3).toString());
                }
                if (vec.get(0).toString().equalsIgnoreCase("chequeno")) {
                    gCheqNoAlign = Integer.parseInt(vec.get(2).toString());
                    gCheqNoWidth = Integer.parseInt(vec.get(3).toString());
                }
                if (vec.get(0).toString().equalsIgnoreCase("particular")) {
                    gParticlrAlign = Integer.parseInt(vec.get(2).toString());
                    gParticlrWidth = Integer.parseInt(vec.get(3).toString());
                }
                if (vec.get(0).toString().equalsIgnoreCase("dramt")) {
                    gDrAmtAlign = Integer.parseInt(vec.get(2).toString());
                    gDrAmtWidth = Integer.parseInt(vec.get(3).toString());
                }
                if (vec.get(0).toString().equalsIgnoreCase("cramt")) {
                    gCrAmtAlign = Integer.parseInt(vec.get(2).toString());
                    gCrAmtWidth = Integer.parseInt(vec.get(3).toString());
                }
                if (vec.get(0).toString().equalsIgnoreCase("bal")) {
                    gBalAlign = Integer.parseInt(vec.get(2).toString());
                    gBalWidth = Integer.parseInt(vec.get(3).toString());
                }
            }
            List l2 = beanRemote.getDetailsOnLoad2();
            for (int j = 0; j < l2.size(); j++) {
                Vector vec2 = (Vector) l2.get(j);
                if (vec2.get(0).toString().equalsIgnoreCase("PassLine")) {
                    PassLine = Integer.parseInt(vec2.get(1).toString());
                }
                if (vec2.get(0).toString().equalsIgnoreCase("HalfLine")) {
                    HalfLine = Integer.parseInt(vec2.get(1).toString());
                }
                if (vec2.get(0).toString().equalsIgnoreCase("SkipLine")) {
                    SkipLine = Integer.parseInt(vec2.get(1).toString());
                }
                if (vec2.get(0).toString().equalsIgnoreCase("BeginLines")) {
                    BeginLines = Integer.parseInt(vec2.get(1).toString());
                }
            }
        } catch (ApplicationException e) {
            this.setPrnAcNoMsg(e.getMessage());
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
    }

    /**
     * ****************************************************************************************************
     */

    /*
     * Method That Clear All The Fields of The Form
     */
    public void clearAll() {
        try {
            this.setAcName("");
            this.setAcNo("");
            this.setAcNoMsg("");
            this.setBalance(new BigDecimal("0"));
            this.setCanAcName("");
            this.setCanAcNo("");
            this.setCanAcNoMsg("");
            this.setCanBalance(new BigDecimal("0"));
            this.setPassBookNo("");
            this.setPrnAcName("");
            this.setPrnAcNo("");
            this.setPrnAcNoMsg("");
            this.setPrnBalance(new BigDecimal("0"));
            this.setPrintFrom(null);
            this.setReason("");
            issPassList.clear();
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
    }

    /*
     * Get The Details of the Account whose Pass Book Want to be Cancel
     */
    public void getAccountDetail() {
        try {
            //if (this.canAcNo.length() != 12) {
            if ((this.canAcNo.length() != 12) && (this.canAcNo.length() != (Integer.parseInt(this.getAcNoLen())))) {
                this.setCanAcNoMsg("Please enter 12 digit account number.");
                this.setCanAcName("");
                this.setCanBalance(new BigDecimal("0"));
                this.setMsgFlag(4);
                setCancelNewAcno("");
                return;
            //} else if (this.canAcNo.length() == 12) {
            } else if ((this.canAcNo.length() == 12) && (this.canAcNo.length() == (Integer.parseInt(this.getAcNoLen())))) {
                this.setCanAcNoMsg("");
                this.setMsgFlag(1);
            }
            this.setFound("False");
            List resultList = new ArrayList();

            FtsPostingMgmtFacadeRemote ftsBeanRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            String result = ftsBeanRemote.getNewAccountNumber(canAcNo);
            if (result.startsWith("A/c")) {
                this.setCanAcName("");
                this.setCanBalance(new BigDecimal("0"));
                this.setMsgFlag(4);
                setCanAcNoMsg(result);
                return;
            } else {
                cancelNewAcno = result;
            }
            String valRes = beanRemote.valAccDetail(cancelNewAcno);
            if (valRes.equals("True")) {
                resultList = beanRemote.getAccDetail(cancelNewAcno);
                Vector ele = (Vector) resultList.get(0);
                this.setCanAcName(ele.get(1).toString());
                if (ele.get(2).toString().contains(".")) {
                    if (ele.get(2).toString().substring(ele.get(2).toString().indexOf(".") + 1).length() > 2) {
                        this.setCanBalance(new BigDecimal(ele.get(2).toString().substring(0, ele.get(2).toString().indexOf(".") + 3)));
                    } else if (ele.get(2).toString().substring(ele.get(2).toString().indexOf(".") + 1).length() < 2) {
                        this.setCanBalance(new BigDecimal(ele.get(2).toString() + "0"));
                    } else {
                        this.setCanBalance(new BigDecimal(ele.get(2).toString()));
                    }
                } else {
                    this.setCanBalance(new BigDecimal(ele.get(2).toString() + ".00"));
                }
                this.setMsgFlag(1);
                this.setFound("True");
            } else {
                this.setCanAcName("");
                this.setCanBalance(new BigDecimal("0"));
                this.setCanAcNoMsg(valRes);
                this.setMsgFlag(4);
            }
        } catch (ApplicationException e) {
            this.setPrnAcNoMsg(e.getMessage());
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
    }

    /*
     * Cancel The Pass Book 
     */
    public void canPassBook() {
        try {
            if (!this.cancelNewAcno.equals("")) {
                String acnoBrCode = ftsremote.getCurrentBrnCode(this.cancelNewAcno);
                if (!getOrgnBrCode().equalsIgnoreCase(acnoBrCode)) {
                    this.setCanAcNoMsg("Account number should be of home branch !");
                    return;
                }
                if (this.found.equals("True")) {
                    String result = beanRemote.canPassBook(this.cancelNewAcno, this.reason);
                    if (result.equals("True")) {
                        this.setFound("False");
                        this.setCanAcName("");
                        this.setCanAcNoMsg("Passbook of " + this.cancelNewAcno + " Cancelled!");
                        this.setCanBalance(new BigDecimal("0"));
                        this.setCanAcNo("");
                        this.setReason("");
                    }
                }
            }
        } catch (ApplicationException e) {
            this.setPrnAcNoMsg(e.getMessage());
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }

    }

    /*
     * Get The Details of the Account whose Pass Book Want to be Print
     */
    public void getPrnAccountDetail() {
        try {
            //if (this.prnAcNo.length() != 12) {
            if ((this.prnAcNo.length() != 12) && (this.prnAcNo.length() != (Integer.parseInt(this.getAcNoLen())))) {
                this.setPrnAcNoMsg("Please enter 12 digit account number.");
                this.setPrnAcName("");
                this.setPrnBalance(new BigDecimal("0"));
                this.setMsgFlag(4);
                this.setPrintNewAcno("");
                return;
            //} else if (this.prnAcNo.length() == 12) {
            } else if ((this.prnAcNo.length() == 12) && (this.prnAcNo.length() == (Integer.parseInt(this.getAcNoLen())))) {
                this.setPrnAcNoMsg("");
                this.setMsgFlag(1);
            }

            flag1 = "none";
            this.setFound("False");
            List resultList = new ArrayList();

            /**
             * * added ***********
             */
            FtsPostingMgmtFacadeRemote ftsBeanRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            String result = ftsBeanRemote.getNewAccountNumber(prnAcNo);
            if (result.startsWith("A/c")) {
                this.setPrintNewAcno("");
                this.setPrnAcName("");
                this.setPrnBalance(new BigDecimal("0"));
                this.setMsgFlag(4);
                setPrnAcNoMsg(result);
                return;
            } else {
                printNewAcno = result;
            }
            /**
             * * added ***********
             */
            String valRes = beanRemote.valAccDetail(printNewAcno);
            if (valRes.equals("True")) {
                resultList = beanRemote.getAccDetail(printNewAcno);
                Vector ele = (Vector) resultList.get(0);
                this.setPrnAcName(ele.get(1).toString());
                if (ele.get(2).toString().contains(".")) {
                    if (ele.get(2).toString().substring(ele.get(2).toString().indexOf(".") + 1).length() > 2) {
                        this.setPrnBalance(new BigDecimal(ele.get(2).toString().substring(0, ele.get(2).toString().indexOf(".") + 3)));
                    } else if (ele.get(2).toString().substring(ele.get(2).toString().indexOf(".") + 1).length() < 2) {
                        this.setPrnBalance(new BigDecimal(ele.get(2).toString() + "0"));
                    } else {
                        this.setPrnBalance(new BigDecimal(ele.get(2).toString()));
                    }
                } else {
                    this.setPrnBalance(new BigDecimal(ele.get(2).toString() + ".00"));
                }

                this.setMsgFlag(1);
                this.setFound("True");
                String acResult = beanRemote.passBookIss(printNewAcno);
                if (!acResult.equals("True")) {
                    this.setPrnAcNoMsg(acResult);
                }
            } else {
                this.setPrnAcName("");
                this.setPrnBalance(new BigDecimal("0"));
                this.setPrnAcNoMsg(valRes);
                this.setMsgFlag(4);
            }
        } catch (ApplicationException e) {
            this.setPrnAcNoMsg(e.getMessage());
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
    }

    /*
     * Get The Details of the Account whose Pass Book Want to be Issued 
     */
    public void getIssAccountDetail() {
        try {
            //if (this.acNo.length() != 12) {
            if ((this.acNo.length() != 12) && (this.acNo.length() != (Integer.parseInt(this.getAcNoLen())))) {
                this.setAcNoMsg("Please enter 12 digit account number.");
                setIssueNewAcno("");
                this.setAcName("");
                this.setBalance(new BigDecimal("0"));
                this.setMsgFlag(4);
                return;
            //} else if (this.acNo.length() == 12) {
            } else if((this.acNo.length() == 12) && (this.acNo.length() == (Integer.parseInt(this.getAcNoLen())))){
                this.setAcNoMsg("");
                this.setMsgFlag(1);
            }
            this.setFound("False");
            List resultList = new ArrayList();

            FtsPostingMgmtFacadeRemote ftsBeanRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            String result = ftsBeanRemote.getNewAccountNumber(acNo);
            if (result.startsWith("A/c")) {
                this.setIssueNewAcno("");
                this.setAcName("");
                this.setBalance(new BigDecimal("0"));
                this.setMsgFlag(4);
                setAcNoMsg(result);
                return;
            } else {
                issueNewAcno = result;
            }
            String valRes = beanRemote.valAccFstPrnDtl(issueNewAcno);
            if (valRes.equals("True")) {
                resultList = beanRemote.getAccFstPrnDtl(issueNewAcno);
                Vector ele = (Vector) resultList.get(0);
                this.setAcName(ele.get(1).toString());
                if (ele.get(2).toString().contains(".")) {
                    if (ele.get(2).toString().substring(ele.get(2).toString().indexOf(".") + 1).length() > 2) {
                        this.setBalance(new BigDecimal(ele.get(2).toString().substring(0, ele.get(2).toString().indexOf(".") + 3)));
                    } else if (ele.get(2).toString().substring(ele.get(2).toString().indexOf(".") + 1).length() < 2) {
                        this.setBalance(new BigDecimal(ele.get(2).toString() + "0"));
                    } else {
                        this.setBalance(new BigDecimal(ele.get(2).toString()));
                    }
                } else {
                    this.setBalance(new BigDecimal(ele.get(2).toString() + ".00"));
                }
                this.setMsgFlag(1);
                this.setFound("True");
                resultList = beanRemote.getIssuedDetail(issueNewAcno);
                issPassList = new ArrayList<IssuePassBookDtl>();
                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector vecForIssPassBook = (Vector) resultList.get(i);
                        issPDtl = new IssuePassBookDtl();
                        issPDtl.setAccountNo(vecForIssPassBook.get(0).toString());
                        issPDtl.setCustName(ele.get(1).toString());
                        issPDtl.setPassBkNo(vecForIssPassBook.get(1).toString());
                        String date = vecForIssPassBook.get(2).toString();
                        String issDt = date.substring(8, 10) + "/" + date.substring(5, 7) + "/" + date.substring(0, 4);
                        issPDtl.setIssDt(issDt);
                        issPDtl.setStatus(vecForIssPassBook.get(3).toString());
                        issPassList.add(issPDtl);
                    }
                }
            } else {
                this.setAcName("");
                this.setBalance(new BigDecimal("0"));
                this.setAcNoMsg(valRes);
                this.issPassList.clear();
                this.setMsgFlag(4);
            }
        } catch (ApplicationException e) {
            this.setPrnAcNoMsg(e.getMessage());
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
    }

    public void onblurPassbookNumber() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher passbookNoCheck = p.matcher(this.passBookNo);
            if (!passbookNoCheck.matches()) {
                this.setAcNoMsg("Please Enter Numeric Passbook No.");
                return;
            } else {
                this.setAcNoMsg("");
            }
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
    }

    public void onblurReason() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher reasonCheck = p.matcher(this.reason);
            if (reasonCheck.matches()) {
                this.setCanAcNoMsg("Please Enter Some Non Numeric Data.");
                return;
            } else {
                this.setCanAcNoMsg("");
            }
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
    }
    /*
     * PassBook Printing 
     */

    public void prnPassBook() {
        // int i = 0, m, k, blankLines;
        // String DtIssue = "", balance1, s, Table;
        String Table;
        //FileOutputStream fos;
        //DataOutputStream dos;
        try {
            if (this.printNewAcno.equalsIgnoreCase("")) {
                this.setPrnAcNoMsg("Please Enter Valid Account No.");
                return;
            }
            
            List dataList = ftsremote.getUnAuthorizedTranListForAcno(this.printNewAcno);
            if (!dataList.isEmpty()) {
                this.setPrnAcNoMsg("There are some pending authorization !");
                flag2="";
                return;
            }
            
            List list1 = beanRemote.getPassBookValues();
            Vector vec1 = (Vector) list1.get(0);
            if (list1.isEmpty()) {
                this.setCanAcNoMsg("Check for the PASSBOOK code in Parameterinfo_Report");
                return;
            }
            if (Integer.parseInt(vec1.get(0).toString()) == 0) {
                this.setCanAcNoMsg("Check for the PASSBOOK code in Parameterinfo_Report");
                return;
            }
            if (Integer.parseInt(vec1.get(0).toString()) == 40) {
                List list2 = beanRemote.setPassBookValues(this.printNewAcno);
                for (int j = 0; j < list2.size(); j++) {
                    Vector vec2 = (Vector) list2.get(j);
                    if (vec2.get(0).toString().equalsIgnoreCase("date")) {
                        gDateAlign = Integer.parseInt(vec2.get(2).toString());
                        gDateWidth = Integer.parseInt(vec2.get(3).toString());
                    }
                    if (vec2.get(0).toString().equalsIgnoreCase("chequeno")) {
                        gCheqNoAlign = Integer.parseInt(vec2.get(2).toString());
                        gCheqNoWidth = Integer.parseInt(vec2.get(3).toString());
                    }
                    if (vec2.get(0).toString().equalsIgnoreCase("particular")) {
                        gParticlrAlign = Integer.parseInt(vec2.get(2).toString());
                        gParticlrWidth = Integer.parseInt(vec2.get(3).toString());
                    }
                    if (vec2.get(0).toString().equalsIgnoreCase("dramt")) {
                        gDrAmtAlign = Integer.parseInt(vec2.get(2).toString());
                        gDrAmtWidth = Integer.parseInt(vec2.get(3).toString());
                    }
                    if (vec2.get(0).toString().equalsIgnoreCase("cramt")) {
                        gCrAmtAlign = Integer.parseInt(vec2.get(2).toString());
                        gCrAmtWidth = Integer.parseInt(vec2.get(3).toString());
                    }
                    if (vec2.get(0).toString().equalsIgnoreCase("bal")) {
                        gBalAlign = Integer.parseInt(vec2.get(2).toString());
                        gBalWidth = Integer.parseInt(vec2.get(3).toString());
                    }
                }
                List list3 = beanRemote.selectParameterValue(this.printNewAcno);
                for (int j = 0; j < list3.size(); j++) {
                    Vector vec3 = (Vector) list3.get(j);
                    if (vec3.get(0).toString().equalsIgnoreCase("PassLine")) {
                        PassLine = Integer.parseInt(vec3.get(1).toString());
                    }
                    if (vec3.get(0).toString().equalsIgnoreCase("HalfLine")) {
                        HalfLine = Integer.parseInt(vec3.get(1).toString());
                    }
                    if (vec3.get(0).toString().equalsIgnoreCase("SkipLine")) {
                        SkipLine = Integer.parseInt(vec3.get(1).toString());
                    }
                    if (vec3.get(0).toString().equalsIgnoreCase("BeginLines")) {
                        BeginLines = Integer.parseInt(vec3.get(1).toString());
                    }
                }
            }

            if (!this.printNewAcno.equalsIgnoreCase("")) {
                if (this.found.equalsIgnoreCase("true")) {
                    flag1 = "";
                    Pstat = "True";
                    String deleteResult = beanRemote.deleteFromPassbookPrint(this.printNewAcno);
                    if (!deleteResult.equalsIgnoreCase("Success in deletion.")) {
//                        this.setPrnAcNoMsg(deleteResult);
                    }
                    FirstUpDate = false;
                    List list4 = beanRemote.selectFromPassbookRecon(this.printNewAcno);
                    if (list4.size() > 0) {
                        Vector vec4 = (Vector) list4.get(0);
                        DateFrom = vec4.get(1).toString();
                        recmax = Double.parseDouble(vec4.get(0).toString());
                        bal = new BigDecimal(vec4.get(2).toString());
                        if (vec4.get(3).toString().equalsIgnoreCase("")) {
                            rwcnt = 0;
                        } else {
                            rwcnt = Integer.parseInt(vec4.get(3).toString());
                        }

                        VelFwdLine = 0;
                        checkPrint(this.printNewAcno);
                        if (rwcnt == PassLine) {
                            rwcnt = 0;
                        }
                        Table = CbsUtil.getReconTableName(ftsremote.getAccountNature(printNewAcno));
                        String selectAcnoCramtDramtDtResult = beanRemote.selectAcnoCramtDramtDt(this.printNewAcno, Table, DateFrom, recmax);
                        prow = Integer.parseInt(selectAcnoCramtDramtDtResult);
                        if ((prow + rwcnt) > PassLine) {
                            prow = PassLine - (rwcnt);
                        }
                        pno = 0;
                        if (pno == 0) {
                            printPass(this.printNewAcno, pno, prow);
                            if (Pstat.equalsIgnoreCase("true")) {
                                printStatus();
                            }
                        }
                    }

                } else {
                    flag1 = "none";
                }
            } else {
                this.setPrnAcNoMsg("Please Enter Valid Account No.");
                return;
            }
        } catch (ApplicationException e) {
            this.setPrnAcNoMsg(e.getMessage());
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
    }

    public void printStatus() {
        try {
            if (!filename.equalsIgnoreCase("")) {
                pno = pno + 1;
            }
            flag2 = "true";
            flag3 = "Printing Successful?";
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
    }

    public void clickWhenPrintAgain() {
        try {
            printPass(this.printNewAcno, pno, prow);
            if (Pstat.equalsIgnoreCase("true")) {
                printStatus();
            }
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
    }

    /**
     * ************************************************************************************************************************************
     */
    public void passUpdate() {
        String maxdt1;
        double maxrecno;
        long Totprint;
        String Table;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date tmpBd = new Date();
        int balanceRows = 3;
        try {
            List selectForPassUpdateResult = beanRemote.selectForPassUpdate(prow, printNewAcno);
            if (!selectForPassUpdateResult.isEmpty()) {
                Vector vec1 = (Vector) selectForPassUpdateResult.get(selectForPassUpdateResult.size() - 1);
                if (vec1.get(0).toString().equalsIgnoreCase("")) {
                    maxdt1 = sdf.format(tmpBd);
                } else {
                    maxdt1 = vec1.get(0).toString();
                }
                maxrecno = Double.parseDouble(vec1.get(1).toString());
                Table = CbsUtil.getReconTableName(ftsremote.getAccountNature(printNewAcno));
                String deleteInsertPassbookreconResult = beanRemote.deleteInsertPassbookrecon(Table, printNewAcno, maxrecno, maxdt1);
                if (deleteInsertPassbookreconResult.equalsIgnoreCase("False1")) {
                    this.setPrnAcNoMsg("Error in deletion from Passbook_Recon.");
                    return;
                }
                if (deleteInsertPassbookreconResult.equalsIgnoreCase("False2")) {
                    this.setPrnAcNoMsg("Error in insertion into Passbook_Recon.");
                    return;
                }
//                Totprint = prow + rwcnt + VelFwdLine;
                String listCPA = beanRemote.getPassCPABalValues();
                if (listCPA.equalsIgnoreCase("1")) {
                    Long maxDt = Long.parseLong(maxDate);
                    Long fileLastDate = Long.parseLong(checkdate);
                    if (maxDt.compareTo(fileLastDate) == 0) {
                        Totprint = rnum + rwcnt + VelFwdLine + balanceRows;
                    } else {
                        Totprint = rnum + rwcnt + VelFwdLine;
                    }
                } else {
                    Totprint = rnum + rwcnt + VelFwdLine;
                }

                if (Totprint > PassLine) {
                    Totprint = PassLine;
                }
                String updatePassbookReconresult = beanRemote.updatepassbookRecon2(bal.toString(), Totprint, printNewAcno);
                if (!updatePassbookReconresult.equalsIgnoreCase("Success")) {
                    this.setPrnAcNoMsg(updatePassbookReconresult);
                    return;
                }
                VelFwdLine = 0;
            }
        } catch (ApplicationException e) {
            this.setPrnAcNoMsg(e.getMessage());
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
    }

    /**
     * ************************************************************************************************************************************
     */
    public void checkPrintLast(String accountNo) {
        try {
            int prnln;
            List list5 = beanRemote.checkPrint2(accountNo);
            if (list5.size() > 0) {
                Vector vec5 = (Vector) list5.get(0);
                prnln = Integer.parseInt(vec5.get(0).toString());
                if (prnln < PassLine) {
                    flag4 = "false";
                    //prnln = prnln;
                } else if (prnln == PassLine) {
                    flag4 = "true";
                    reply = "The Current Page is Full Turn to next Page.Please Ensure that you have turned the Page.";
                    return;
                }
            } else {
                return;
            }
        } catch (ApplicationException e) {
            this.setPrnAcNoMsg(e.getMessage());
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
    }

    /**
     * ************************************************************************************************************************************
     */
    public void printStatusOKClick() {
        try {
            flagForReprint = "false";
            passUpdate();
            checkPrintLast(printNewAcno);
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
    }

    public void clickWhenPrintFail() {
        flagForReprint = "true";
        flag6 = "true";
        flag5 = "Do U Want To Print again ?";
    }

    public void printPass(String accNo, double pNo, int pRow) {
        int i = 0, m, k, blankLines;
        String Balance, dramt, cramt;
        String chqNo = "";
        List list7;
        NumberFormat df = new DecimalFormat("0.00");
        try {
            String DATE_FORMAT = "yyyyMMddhhmmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar c1 = Calendar.getInstance();
            String DtTm = sdf.format(c1.getTime());
            String fileName = this.userName + orgnBrCode + DtTm + ".txt";
            String osName = System.getProperty("os.name");
            String fileCr = null;
            File folder;
            if (osName.equals("Linux")) {
                folder = new File("/install/passbookPrn");
                fileCr = "/install/passbookPrn/" + fileName;
            } else {
                folder = new File("c:\\passbookPrn");
                fileCr = "c:\\passbookPrn\\" + fileName;
            }
            if (!folder.exists()) {
                folder.mkdir();
            }
            File f;
            f = new File(fileCr);
            File[] listOfFiles = folder.listFiles();

            for (int z = 0; z < listOfFiles.length; z++) {
                if (this.userName == null) {
                    this.setPrnAcNoMsg("Please Check Your User Name.");
                    return;
                }
                if (listOfFiles[z].getName().toString().substring(0, this.userName.length()).equalsIgnoreCase(this.userName)) {
                    listOfFiles[z].delete();
                }
            }

            List list1 = beanRemote.getPassBookValues();
            Vector vec1 = (Vector) list1.get(0);
            if (list1.isEmpty()) {
                this.setPrnAcNoMsg("Check for the PASSBOOK code in Parameterinfo_Report.");
                return;
            }
            if (Integer.parseInt(vec1.get(0).toString()) == 0) {
                this.setPrnAcNoMsg("Check for the PASSBOOK code in Parameterinfo_Report");
                return;
            }
            PrtLine = 0;
            List list6 = beanRemote.selectFromPassbookPrt(pRow, accNo);
            if (list6.isEmpty()) {
                flag2 = "false";
                this.setPrnAcNoMsg("No Record Exists For Printing Passbook.");
                Pstat = "False";
                return;
            } else {
                Pstat = "True";
                List list4 = beanRemote.selectFromPassbookRecon(accNo);
                Vector vec4 = (Vector) list4.get(0);
                if (list4.size() > 0) {
                    bal = new BigDecimal(CbsUtil.round(Double.parseDouble(vec4.get(2).toString()), 2));
                }

                rnum = list6.size();
                rwcount = rwcnt + rnum;
                if (rwcount > PassLine) {
                    rwcount = 0;
                    rnum = rnum - rwcnt;
                    status = true;
                }
                if (rnum > PassLine) {
                    rnum = PassLine;
                }
                if (rnum > 0) {
                    m = i + rwcnt;
                    PrtLine = rwcnt;
                    if (PrtLine == 0) {
                        PrtLine = 1;
                    }
                    if (!f.exists()) {
                        f.createNewFile();
                        filename = f.getName().toString();
                    }
                    FileWriter fstream = new FileWriter(f);
                    BufferedWriter out = new BufferedWriter(fstream);
                    if (rwcnt > HalfLine) {
                        blankLines = BeginLines + rwcnt + SkipLine;
                        for (int j = 0; j < blankLines; j++) {
                            out.newLine();
                        }
                    } else {
                        blankLines = BeginLines + rwcnt;
                        for (int j = 0; j < blankLines; j++) {
                            out.newLine();
                        }
                    }
                    if (rwcnt == 0) {
                        String printBalFwdResult = printBalFwd();
                        out.write(printBalFwdResult);
                        out.newLine();
                        VelFwdLine = 1;
                        beanRemote.updatePassbookRecon(accNo);
                    }
                    for (int j = 0; j < list6.size(); j++) {
                        String TranType;
                        Vector vec6 = (Vector) list6.get(j);
                        if (Double.parseDouble(vec6.get(3).toString()) != 0) {

                            bal = bal.add(new BigDecimal(vec6.get(3).toString()));
                        } else {
                            bal = bal.subtract(new BigDecimal(vec6.get(2).toString()));
                        }
                        if (vec6.get(1).toString().equals("")) {
                            checkdate = "19000101";
                        } else {
                            checkdate = vec6.get(1).toString().substring(0, 4) + vec6.get(1).toString().substring(5, 7) + vec6.get(1).toString().substring(8, 10);
                        }
                        Balance = df.format(Double.parseDouble(bal.toString()));
                        if (Integer.parseInt(vec6.get(9).toString()) == 0) {
                            if (vec6.get(5).toString().equalsIgnoreCase(" ")) {
                                TranType = "CSH-";
                            } else {
                                TranType = "CSH-" + vec6.get(5).toString();
                            }
                        } else if (Integer.parseInt(vec6.get(9).toString()) == 1) {
                            if (vec6.get(5).toString().equalsIgnoreCase(" ")) {
                                TranType = "CLG-";
                            } else {
                                TranType = "CLG-" + vec6.get(5).toString();
                            }
                        } else {
                            if (vec6.get(5).toString().equalsIgnoreCase(" ")) {
                                TranType = "TRF-";
                            } else {
                                TranType = "TRF-" + vec6.get(5).toString();
                            }
                        }
                        if(Double.parseDouble(vec6.get(2).toString()) == 0) dramt = "";
                        else dramt = df.format(CbsUtil.round(Double.parseDouble(vec6.get(2).toString()), 2));
                        
                        if(Double.parseDouble(vec6.get(3).toString()) == 0) cramt = "";
                        else cramt = df.format(CbsUtil.round(Double.parseDouble(vec6.get(3).toString()), 2));

                        if (Integer.parseInt(vec1.get(0).toString()) == 40) {
                            list7 = beanRemote.selectFromPassbookValuesCA(accNo);
                        } else {
                            list7 = beanRemote.selectFromPassbookValues(accNo);
                        }
                        s = "";
                        for (int r = 0; r < list7.size(); r++) {
                            Vector vec7 = (Vector) list7.get(r);
                            if (vec7.get(0).toString().equalsIgnoreCase("date")) {
                                String Date = vec6.get(1).toString();
                                if (Date.equalsIgnoreCase("")) {
                                    Date = "";
                                } else {
                                    Date = Date.substring(8, 10) + "-" + Date.substring(5, 7) + "-" + Date.substring(2, 4);
                                }
                                s = s + space(gDateAlign) + sFormat(Date, gDateWidth, false);
                            }
                            if (vec7.get(0).toString().equalsIgnoreCase("chequeno")) {
                                if (vec6.get(6).toString().equalsIgnoreCase("")) {
                                    chqNo = "";
                                } else {
                                    if (vec6.get(6).toString().length() <= 7) {
                                        chqNo = vec6.get(6).toString();
                                    } else {
                                        chqNo = vec6.get(6).toString().substring(3);
                                    }
                                }
                                s = s + space(gCheqNoAlign) + sFormat(chqNo, gCheqNoWidth, false);
                            }
                            if (vec7.get(0).toString().equalsIgnoreCase("particular")) {
                                s = s + space(gParticlrAlign) + sFormat(TranType, gParticlrWidth, false);
                            }
                            if (vec7.get(0).toString().equalsIgnoreCase("dramt")) {
                                s = s + space(gDrAmtAlign + 1) + sFormat(dramt, gDrAmtWidth, true);
                            }
                            if (vec7.get(0).toString().equalsIgnoreCase("cramt")) {
                                s = s + space(gCrAmtAlign) + sFormat(cramt, gCrAmtWidth, true);
                            }
                            if (vec7.get(0).toString().equalsIgnoreCase("bal")) {
                                s = s + space(gBalAlign) + sFormat(Balance, gBalWidth, true);
                            }
                        }
                        dramt = "";
                        cramt = "";
                        if (PrtLine != HalfLine) {
                            out.write(s);
                            out.newLine();
                        } else {
                            for (int l = 0; l < SkipLine; l++) {
                                out.newLine();
                            }
                            out.write(s);
                            out.newLine();
                        }
                        PrtLine = PrtLine + 1;
                    }

                    String listCPA = beanRemote.getPassCPABalValues();
                    if (listCPA.equalsIgnoreCase("1")) {
                        Date datePr = new Date();
                        SimpleDateFormat sdfPr = new SimpleDateFormat("dd/MM/yyyy");
                        String prntDt = sdfPr.format(datePr);
                        // NumberFormat num = new DecimalFormat("#.##");
                        List clPendingList = new ArrayList();
                        String closeBal = tranRemote.getOpeningBalF4(accNo, prntDt);
                        String clBal;
                        clPendingList = tranRemote.selectPendingBalance(accNo);
                        Vector vecForPendingBal = (Vector) clPendingList.get(0);
                        if (vecForPendingBal.get(0).toString().equalsIgnoreCase("0.0") || vecForPendingBal.get(0).toString().equalsIgnoreCase("0")) {
                            clBal = "0";
                        } else {
                            clBal = df.format(Double.parseDouble(vecForPendingBal.get(0).toString()));
                        }

                        String avlBal = df.format(Double.parseDouble(closeBal));
                        String availBal = df.format(Double.parseDouble(avlBal) - Double.parseDouble(clBal));

                        List widthList = new ArrayList();
                        widthList = beanRemote.selectWidthPassbookValues(accNo);
                        Vector vecForWidth = (Vector) widthList.get(0);
                        int width = Integer.parseInt(vecForWidth.get(0).toString());

                        List dateList = beanRemote.getMaxTxnDate(accNo);
                        Vector vector = (Vector) dateList.get(0);
                        maxDate = vector.get(0).toString();
                        Long maxDt = Long.parseLong(maxDate);
                        Long fileLastDate = Long.parseLong(checkdate);

                        if (maxDt.compareTo(fileLastDate) == 0) {
                            List dataList = beanRemote.getRemainingDataToPrint(accNo);
                            if ((rnum + rwcnt) < PassLine) {
                                s = space(2);
                                out.write(s);
                                for (int w = 0; w <= width; w++) {
                                    out.write("-");
                                }

                                out.newLine();

                                s = space(2) + "Closing Bal:" + space(2) + df.format(Double.parseDouble(closeBal)) + space(5) + "Pending Bal:" + space(2) + clBal + space(5) + "Available Bal:" + space(2) + availBal;
                                out.write(s);
                                out.newLine();

                                s = space(2);
                                out.write(s);
                                for (int w = 0; w <= width; w++) {
                                    out.write("-");
                                }

                                out.newLine();
                            } else if ((rnum + rwcnt) == PassLine && dataList.size() <= 0) {
                                s = space(2);
                                out.write(s);
                                for (int w = 0; w <= width; w++) {
                                    out.write("-");
                                }

                                out.newLine();

                                s = space(2) + "Closing Bal:" + space(2) + df.format(Double.parseDouble(closeBal)) + space(5) + "Pending Bal:" + space(2) + clBal + space(5) + "Available Bal:" + space(2) + availBal;
                                out.write(s);
                                out.newLine();

                                s = space(2);
                                out.write(s);
                                for (int w = 0; w <= width; w++) {
                                    out.write("-");
                                }

                                out.newLine();
                            }

                        }
                    }
                    k = i;
                    m = m + 1;
                    out.close();
                    String result = printReport(filename);
                    if (!result.equalsIgnoreCase("TRUE")) {
                        this.setPrnAcNoMsg("Please check attached printer or printer cable");
                    }
                } else {
                    return;
                }
            }
        } catch (ApplicationException e) {
            this.setPrnAcNoMsg(e.getMessage());
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
    }

    public void reprintPassbook() {
        try {
            if (filename != null) {
                String result = printReport(filename);
                if (!result.equalsIgnoreCase("TRUE")) {
                    this.setPrnAcNoMsg("System Error Occurred");
                } else {
                    if (flagForReprint.equalsIgnoreCase("true")) {
                        passUpdate();
                    }
                }

            } else {
                this.setPrnAcNoMsg("No Record Exists For Printing Passbook.");
                return;
            }
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
    }

    public String printBalFwd() {
        String DtIssue = "", balance1;
        List list7;
        NumberFormat df = new DecimalFormat("0.00");
        try {
            List list4 = beanRemote.selectFromPassbookRecon(this.printNewAcno);
            Vector vec4 = (Vector) list4.get(0);
            if (list4.size() > 0) {
                DtIssue = vec4.get(1).toString();
                bal = new BigDecimal(Double.parseDouble(vec4.get(2).toString()));
            }
            List list1 = beanRemote.getPassBookValues();
            Vector vec1 = (Vector) list1.get(0);
            if (list1.isEmpty()) {
                this.setPrnAcNoMsg("Check for the PASSBOOK code in Parameterinfo_Report.");
            }
            if (Integer.parseInt(vec1.get(0).toString()) == 0) {
                this.setCanAcNoMsg("Check for the PASSBOOK code in Parameterinfo_Report");
            }

            balance1 = df.format(Double.parseDouble(bal.toString()));
            if (Integer.parseInt(vec1.get(0).toString()) == 40) {
                list7 = beanRemote.selectFromPassbookValuesCA(this.printNewAcno);
            } else {
                list7 = beanRemote.selectFromPassbookValues(this.printNewAcno);
            }
            s = "";
            for (int j = 0; j < list7.size(); j++) {
                Vector vec7 = (Vector) list7.get(j);
                if (vec7.get(0).toString().equalsIgnoreCase("date")) {
                    if (DtIssue.equalsIgnoreCase("")) {
                        DtIssue = "";
                    } else {
                        DtIssue = DtIssue.substring(8, 10) + "-" + DtIssue.substring(5, 7) + "-" + DtIssue.substring(2, 4);
                    }
                    s = s + space(gDateAlign) + sFormat(DtIssue, gDateWidth, false);
                }
                if (vec7.get(0).toString().equalsIgnoreCase("particular")) {
                    String parti = "Pass-Book Bal B/F";
                    s = s + space(gParticlrAlign) + sFormat(parti, gParticlrWidth, false);
                }
                if (vec7.get(0).toString().equalsIgnoreCase("chequeno")) {
                    s = s + space(gCheqNoAlign) + sFormat("", gCheqNoWidth, false);
                }
                if (vec7.get(0).toString().equalsIgnoreCase("dramt")) {
                    s = s + space(gDrAmtAlign) + sFormat("", gDrAmtWidth, true);
                }
                if (vec7.get(0).toString().equalsIgnoreCase("cramt")) {
                    s = s + space(gCrAmtAlign) + sFormat("", gCrAmtWidth, true);
                }
                if (vec7.get(0).toString().equalsIgnoreCase("bal")) {
                    s = s + space(gBalAlign) + sFormat(balance1, gBalWidth, true);
                }
            }
        } catch (ApplicationException e) {
            this.setPrnAcNoMsg(e.getMessage());
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
        return s;
    }

    public String space(int alignmentValue) {
        String result = "";
        try {
            for (int p = 0; p < alignmentValue; p++) {
                result = result + " ";
            }
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
        return result;
    }

    public String sFormat(String valToPrint, int gDateWidth, boolean left) throws ApplicationException {
        try {
            if (valToPrint.equalsIgnoreCase("") || valToPrint == null) {
                valToPrint = " ";
            }
            String tempStr = valToPrint;
            if (valToPrint.length() > gDateWidth) {
                tempStr = valToPrint.substring(0, gDateWidth);
            }
            if (!left) {
                tempStr = tempStr + space(gDateWidth - tempStr.length());
            } else {
                tempStr = space(gDateWidth - tempStr.length()) + tempStr;
            }
            return tempStr;
        } catch (Exception e) {
            throw new ApplicationException(e.getLocalizedMessage());
        }
    }

    public void checkPrint(String accNo) {
        try {
            int prnln;
            List list5 = beanRemote.checkPrint2(accNo);
            if (list5.size() > 0) {
                Vector vec5 = (Vector) list5.get(0);
                prnln = Integer.parseInt(vec5.get(0).toString());
                if (prnln < PassLine) {
                    // prnln = prnln;
                } else if (prnln == PassLine) {
                    flag2 = "true2";
                    reply = "The Current Page is Full Turn to next Page.Please Ensure that you have turned the Page.";
                }
            } else {
                return;
            }
        } catch (ApplicationException e) {
            this.setPrnAcNoMsg(e.getMessage());
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
    }

    public void clickIssueButton() {
        try {
            int sno;
            String array[] = null, result, Table;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date date = new Date();
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            if (this.printFrom == null) {
                this.setAcNoMsg("Selected Some Print From Date.");
                return;
            }
            this.setAcNoMsg("");
            
            int issCode = ftsremote.getCodeForReportName("REMOTE_ISSUE");
            if(issCode == 0){
                String acnoBrCode = ftsremote.getCurrentBrnCode(this.issueNewAcno);
                if (!getOrgnBrCode().equalsIgnoreCase(acnoBrCode)) {
                    this.setAcNoMsg("Account number should be of home branch !");
                    return;
                }
            }
            
//            cal1.set(Integer.parseInt(sdf.format(date).toString().substring(0, 4)), Integer.parseInt(sdf.format(date).toString().substring(4, 6)), Integer.parseInt(sdf.format(date).toString().substring(6)));
//            cal2.set(Integer.parseInt(sdf.format(this.printFrom).toString().substring(0, 4)), Integer.parseInt(sdf.format(this.printFrom).toString().substring(4, 6)), Integer.parseInt(sdf.format(this.printFrom).toString().substring(6)));
//            if (cal2.after(cal1)) {
//                this.setAcNoMsg("Selected Date should not be greater than Current date.");
//                return;
//            }
            
            if(this.printFrom.after(date)){
               this.setAcNoMsg("Selected Date should not be greater than Current date.");
                return;  
            }           
            this.setAcNoMsg("");
            if (!this.issueNewAcno.equalsIgnoreCase("")) {
                if (this.found.equalsIgnoreCase("True")) {
                    sno = 0;
                    if (this.passBookNo.equalsIgnoreCase("")) {
                        this.setAcNoMsg("Please Enter PassBook No Properly.");
                        return;
                    }
                    List selectFromPassbookHisResult = beanRemote.selectFromPassbookHis(issueNewAcno);
                    if (!selectFromPassbookHisResult.isEmpty()) {
                        Vector v1 = (Vector) selectFromPassbookHisResult.get(0);
                        if (v1.get(0).toString().equalsIgnoreCase(passBookNo)) {
                            this.setAcNoMsg("Passbook No already Issued.");
                            return;
                        }
                    }
                    List selectFromPassReconResult = beanRemote.selectFromPassRecon(issueNewAcno);
                    if (selectFromPassReconResult.size() > 0) {
                        this.setAcNoMsg("PassBook Already issued.");
                        return;
                    } else {
                        result = passBookDetails(issueNewAcno, true);
                        if (result.contains(":")) {
                            String splitter = ":";
                            array = result.split(splitter);
                        }
                        Table = CbsUtil.getReconTableName(ftsremote.getAccountNature(issueNewAcno));
                        List selectFromTableResult = beanRemote.selectFromTable(issueNewAcno, sdf.format(this.printFrom).toString(), Table);
                        Vector v2 = (Vector) selectFromTableResult.get(0);
                        Curbal = Double.parseDouble(v2.get(0).toString());
                        String insertIntoPassbookReconResult = beanRemote.insertIntoPassbookRecon(issueNewAcno, String.valueOf(Curbal), String.valueOf(0), String.valueOf(0), sdf.format(this.printFrom).toString(), passBookNo, String.valueOf(sno), sdf.format(date).toString());
                        if (insertIntoPassbookReconResult.equalsIgnoreCase("Passbook Issued")) {
                            this.setAcNoMsg("Passbook Issued to " + issueNewAcno + " " + "!");
                            this.setFound("False");
                        } else {
                            this.setAcNoMsg(insertIntoPassbookReconResult);
                            return;
                        }
                        flag7 = "true";
                        firstPrintMsg = "Do U Want to Print First Page Of PassBook ?";
                    }
                }
            } else {
                this.setAcNoMsg("Passbook not issued !");
                return;
            }
        } catch (ApplicationException e) {
            this.setPrnAcNoMsg(e.getMessage());
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
    }

    public String passBookDetails(String acNo, boolean fnd) {
        String name1 = "";
        String name2 = "";
        String acType = "";
        String Address = "";
        String Acno = "";
        String occupation = "";
        String BRANCHADD = "";
        String OPERMODE = "";
        String Fathersname = "";
        String TmpNomination = "";
        String TmpAcopDt_Pass = "";
        String ifscCode = "";
        String phoneNo = "";
        String custId = "";
        String name3 = "";
        String name4 = "";
        String name5 = "";

        String micr = "";
        String micrCd = "";
        String brCd = "";
        String roi = "";
        String prd = "";
        String instAmt = "";
        String matDt = "";
        String BrPhoneNo = "";

        try {
            if (!acNo.equalsIgnoreCase("")) {
                if (fnd == true) {
                    List selectFromCustmasterAccMasterParameterInfoResult = beanRemote.selectFromCustmasterAccMasterParameterInfo(acNo);
                    if (selectFromCustmasterAccMasterParameterInfoResult.size() > 0) {
                        Vector v1 = (Vector) selectFromCustmasterAccMasterParameterInfoResult.get(0);
                        name1 = v1.get(0).toString();
                        name2 = v1.get(6).toString();
                        acType = beanRemote.getAcctDescByAcctCode(v1.get(1).toString());
                        Address = v1.get(2).toString();
                        Acno = v1.get(3).toString();
                        occupation = v1.get(4).toString();
                        BRANCHADD = v1.get(5).toString();
                        OPERMODE = v1.get(7).toString();
                        Fathersname = v1.get(8).toString();
                        TmpNomination = v1.get(9).toString();
                        TmpAcopDt_Pass = v1.get(10).toString();
                        if (!TmpAcopDt_Pass.equalsIgnoreCase("")) {
                            TmpAcopDt_Pass = TmpAcopDt_Pass.substring(6) + "-" + TmpAcopDt_Pass.substring(4, 6) + "-" + TmpAcopDt_Pass.substring(0, 4);
                        }
//                        List selectDescription4 = beanRemote.selectDescription6(occupation);
//                        Vector v2 = (Vector) selectDescription4.get(0);
//                        occupation = v2.get(0).toString();
//                        List selectDescription6 = beanRemote.selectDescription4(OPERMODE);
//                        if (selectDescription6.size() > 0) {
//                            Vector v3 = (Vector) selectDescription6.get(0);
//                            OPERMODE = v3.get(0).toString();
//                        }
                        ifscCode = beanRemote.getIfscCodeByBrnCode(Integer.parseInt(acNo.substring(0, 2)));

                        phoneNo = v1.get(11).toString() != null ? v1.get(11).toString() : "";
                        custId = v1.get(12).toString() != null ? v1.get(12).toString() : "";
                        if (v1.get(13) == null || (v1.get(13).toString().equals(""))) {
                            name3 = "/";
                        } else {
                            name3 = v1.get(13).toString();
                        }
                        if (v1.get(14) == null || (v1.get(14).toString().equals(""))) {
                            name4 = "/";
                        } else {
                            name4 = v1.get(14).toString();
                        }
                        if (v1.get(15) == null || (v1.get(15).toString().equals(""))) {
                            name5 = "/";
                        } else {
                            name5 = v1.get(15).toString();
                        }
                        micr = v1.get(16).toString();
                        micrCd = v1.get(17).toString();
                        brCd = v1.get(18).toString();
                        if (v1.get(19) == null || (v1.get(19).toString().equals(""))) {
                            roi = "";
                        } else {
                            roi = v1.get(19).toString();
                        }
                        if (v1.get(20) == null || (v1.get(20).toString().equals(""))) {
                            prd = "";
                        } else {
                            prd = v1.get(20).toString();
                        }
                        if (v1.get(21) == null || (v1.get(21).toString().equals(""))) {
                            instAmt = "";
                        } else {
                            instAmt = v1.get(21).toString();
                        }
                        matDt = v1.get(22).toString();
                        if (v1.get(23) == null || (v1.get(23).toString().equals(""))) {
                            BrPhoneNo = "";
                        } else {
                            BrPhoneNo = v1.get(23).toString();
                        }
                    }
                }
            }
        } catch (ApplicationException e) {
            this.setPrnAcNoMsg(e.getMessage());
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
        return name1 + ":" + name2 + ":" + acType + ":" + Address + ":" + Acno + ":" + occupation + ":" + BRANCHADD + ":" + OPERMODE + ":" + Fathersname + ":" + TmpNomination + ":" + ifscCode + ":" + TmpAcopDt_Pass + ":" + phoneNo + ":" + custId + ":" + name3 + ":" + name4 + ":" + name5 + ":" + micr + ":" + micrCd + ":" + brCd + ":" + roi + ":" + prd + ":" + instAmt + ":" + matDt + ":" + BrPhoneNo;
    }

    /**
     * ********************************************Code for first
     * print************************************************************************
     */
    public void firstPrintClick() {
        try {
            if (this.passBookNo.equalsIgnoreCase("")) {
                this.setAcNoMsg("Provide Passbook No.");
                return;
            }
            if (this.issueNewAcno.equalsIgnoreCase("")) {
                this.setAcNoMsg("Provide Account no.");
                return;
            }
            
            int issCode = ftsremote.getCodeForReportName("REMOTE_ISSUE");
            if(issCode == 0){
                String acnoBrCode = ftsremote.getCurrentBrnCode(this.issueNewAcno);
                if (!getOrgnBrCode().equalsIgnoreCase(acnoBrCode)) {
                    this.setAcNoMsg("Account number should be of home branch !");
                    return;
                }
            }           
            
            flag7 = "true";
            firstPrintMsg = "Do U Want to Print First Page Of PassBook ?";
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
    }

    public void clickToPrintFirstPage() {
        try {
            if (printRemote.isNewPrinting("PASSBOOK PRINT")) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                passbookDetailList = new ArrayList<String>();
                String pAcNo = ftsremote.getNewAccountNumber(acNo);
                passbookDetailList = printRemote.getNewPassbookDetails(pAcNo);

                passbookDetailList.add(passBookNo);
                passbookDetailList.add(sdf.format(printFrom));
                printFirstPage();
            } else {
                passBookDetails(issueNewAcno, true);
                fillIssuePass();
            }
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getMessage());
        }
    }

    public void printFirstPage() throws ApplicationException {
        try {
            String accNature = ftsremote.getAccountNature(ftsremote.getNewAccountNumber(acNo));
            List paramHeader = printRemote.getPrintingHeader("PB");
            if (paramHeader.isEmpty()) {
                throw new ApplicationException("Please fill the Header parameters");
            }
            Vector ele = (Vector) paramHeader.get(0);
            int width = Integer.parseInt(ele.get(0).toString());
            int height = Integer.parseInt(ele.get(1).toString());

            int tmpBreak = Integer.parseInt(ele.get(2).toString());
            int xOrd = Integer.parseInt(ele.get(3).toString());
            boolean lineBeak = tmpBreak == 1 ? true : false;

            List billLabelList = printRemote.getPrintingParameters("PBL", "L");
            SiplExporter sipl = new SiplExporter(width, height, 5, 5);
            List<SiplPage> pages = new ArrayList<SiplPage>();
            SiplText text;
            SiplPage page = new SiplPage();
            for (int i = 0; i < billLabelList.size(); i++) {
                Vector element = (Vector) billLabelList.get(i);

                text = new SiplText();
                text.setX(Integer.parseInt(element.get(1).toString()));
                text.setY(Integer.parseInt(element.get(2).toString()));

                text.setHeight(8);
                if(accNature.equalsIgnoreCase(CbsConstant.SAVING_AC) || accNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) 
                        || accNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || accNature.equalsIgnoreCase(CbsConstant.MS_AC)){
                    if (i == 19 || i == 20 || i == 21 || i == 22 || i == 23 || i == 41 || i == 42) {
                        text.setWidth(0);
                    } else {
                        text.setWidth(Integer.parseInt(element.get(3).toString()));
                    }
                }else if(accNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)){
                    if (i == 41 || i == 42) {
                        text.setWidth(0);
                    } else {
                        text.setWidth(Integer.parseInt(element.get(3).toString()));
                    }
                } else {
                    text.setWidth(Integer.parseInt(element.get(3).toString()));
                }
                text.setText(CbsUtil.toProperCase(element.get(0).toString().toLowerCase()));
                page.addElement(text);
            }
            List fdValueList = printRemote.getPrintingParameters("PB", "V");
            for (int i = 0; i < fdValueList.size(); i++) {
                Vector element = (Vector) fdValueList.get(i);

                text = new SiplText();
                text.setX(Integer.parseInt(element.get(1).toString()));
                text.setY(Integer.parseInt(element.get(2).toString()));

                text.setHeight(8);
                text.setWidth(Integer.parseInt(element.get(3).toString()));
                text.setText(passbookDetailList.get(i));
                page.addElement(text);
            }
            pages.add(page);
            sipl.setLineBreak(lineBeak);
            sipl.setNewLineX(xOrd);
            sipl.setPages(pages);
            ByteArrayOutputStream os = sipl.exportReport();
            System.out.println(os.toString());
            PrinterClass prinObj = new PrinterClass();
            String option = "TXT";
            if (printRemote.isNewPrinting("NEW PRINTER")) {
                option = "12";
            } else {
                option = "TXT";
            }
            String msg = prinObj.printReport(option, os.toByteArray());
            if (msg.equalsIgnoreCase("true")) {
                this.setPrnAcNoMsg("Print Successfull.");
            } else {
                this.setPrnAcNoMsg("Error in Printing.");
            }
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getMessage());
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public void fillIssuePass() {
        int i;
        String array[] = null;
        String result;
        try {
            String DATE_FORMAT = "yyyyMMddhhmmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar c1 = Calendar.getInstance();
            String DtTm = sdf.format(c1.getTime());
            String fileName = this.userName + orgnBrCode + DtTm + ".txt";
            String osName = System.getProperty("os.name");
            String fileCr = null;
            if (osName.equals("Linux")) {
                fileCr = "/install/passbookPrn/" + fileName;
            } else {
                fileCr = "c:\\passbookPrn\\" + fileName;
            }
            File f;
            f = new File(fileCr);
            if (!f.exists()) {
                f.createNewFile();
            }
            FileWriter fstream = new FileWriter(f);
            BufferedWriter out = new BufferedWriter(fstream);
            fileNameFirstPrnt = f.getName().toString();
            //fileNameFirstPrnt = fileName;
            result = passBookDetails(issueNewAcno, true);
            String oldAcno = beanRemote.getoldAccountNumber(issueNewAcno);
            if (result.contains(":")) {
                String splitter = ":";
                array = result.split(splitter);
            }
            List getPassbookValuesResult = beanRemote.getPassBookValues();
            Vector vecPassbook = (Vector) getPassbookValuesResult.get(0);
            if (getPassbookValuesResult.isEmpty()) {
                this.setAcNoMsg("Check for the PASSBOOK code in Parameterinfo_Report");
                return;
            }
            if (Integer.parseInt(vecPassbook.get(0).toString()) == 0) {
                this.setAcNoMsg("Check for the PASSBOOK code in Parameterinfo_Report");
                return;
            }
            this.setAcNoMsg("");
            if (Integer.parseInt(vecPassbook.get(0).toString()) == 36) {
                out.newLine();
            }
            s1 = "";
            String arr3 = "";
            if (Integer.parseInt(vecPassbook.get(0).toString()) == 2) {
                FtsPostingMgmtFacadeRemote ftsBeanRemote1 = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
                AccountOpeningFacadeRemote acOpenFacadeRemote = (AccountOpeningFacadeRemote) ServiceLocator.getInstance().lookup("AccountOpeningFacade");
                String acNat = ftsBeanRemote1.getAccountNature(issueNewAcno);

                String matAmts = "0";
                if (acNat.equalsIgnoreCase("RD")) {
                    matAmts = acOpenFacadeRemote.cbsRdCalculation(Float.parseFloat(array[22]), Integer.parseInt(array[21]), Float.parseFloat(array[20]));
                }

                String accountCode = ftsBeanRemote1.getAccountCode(issueNewAcno);
                List paramList = ftsBeanRemote1.getBaseParameter(accountCode);
                String flag = "N";
                if (!paramList.isEmpty()) {
                    Vector paramVector = (Vector) paramList.get(0);
                    String chqFlag = paramVector.get(0).toString();
                    if (chqFlag.equalsIgnoreCase(accountCode)) {
                        flag = "Y";
                    }
                }

                for (i = 0; i < 7; i++) {
                    if (i == 0) {
                        for (int j = 0; j < 28; j++) {
                            out.newLine();
                        }
                        String arrBr = array[6].toString();
                        if (arrBr.length() > 30) {
                            arrBr = array[6].substring(0, 30);
                        } else {
                            arrBr = array[6].toString();
                        }

                        s1 = space(18) + "PASSBK NO: " + this.passBookNo + space(6) + "BRANCH :  " + arrBr;
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                    } else if (i == 1) {
                        array[0] = array[0].trim() + space(40 - array[0].trim().length());
                        s1 = space(13) + "Name:  " + array[0] + space(5) + "A/C Type: " + array[2];
                        out.write(s1);
                        out.newLine();
                        s1 = space(13) + "Father/Husband's Name:  " + array[8];
                        out.write(s1);
                        out.newLine();
                    } else if (i == 2) {
                        int lenTotM = 35;
                        arr3 = array[3].toString();
                        String remStr1 = "", remStr2 = "", remStr3 = "";
                        if (arr3.length() > lenTotM) {
                            String remStrM = arr3.substring(0, lenTotM);
                            int totPosM = remStrM.lastIndexOf(" ");
                            remStr1 = remStrM.substring(0, totPosM);
                            remStr2 = arr3.substring(totPosM + 1);
                        } else {
                            remStr1 = arr3;
                        }
                        s1 = space(13) + "Address:  " + remStr1 + space(37 - remStr1.length()) + space(5) + "A/C No:  " + array[4];
                        out.write(s1);
                        out.newLine();
                        s1 = space(24) + remStr2;
                        out.write(s1);
                        out.newLine();
                    } else if (i == 3) {
                        s1 = space(13) + "Date Of Issue: " + array[11] + space(36 - array[11].length()) + " IFSC Code: " + array[10];
                        out.write(s1);
                        out.newLine();
                    } else if (i == 4) {
                        if (array[7].equalsIgnoreCase("Under Guardianship")) {
                            s1 = space(13) + "Guardian Name: " + array[1] + space(10) + " Operation Mode: " + array[7];
                        } else {
                            s1 = space(13) + "Joint Name: " + array[1] + space(39 - array[1].length()) + " Operation Mode: " + array[7];
                        }
                        out.write(s1);
                    } else if (i == 5 && acNat.equalsIgnoreCase("RD")) {
                        out.newLine();
                        s1 = space(13) + "ROI : " + array[20] + space(10) + "PERIOD : " + array[21] + space(26 - (array[20].length() + array[21].length())) + " Maturity Amount : " + matAmts;
                        out.write(s1);
                        out.newLine();
                        s1 = space(13) + "Installment : " + array[22] + space(31) + " Maturity Date : " + array[23];
                        out.write(s1);
                    } else if (i == 6 && flag.equalsIgnoreCase("Y")) {
                        out.newLine();
                        s1 = space(13) + "Small Account valid for One Year from the date of opening the account";
                        out.write(s1);
                    }
                }
            }
            //3 means Banda urban co-op bank
            if (Integer.parseInt(vecPassbook.get(0).toString()) == 3) {
                for (i = 0; i < 7; i++) {
                    if (i == 0) {
                        for (int j = 0; j < 26; j++) {
                            out.newLine();
                        }
                        String arrBr = array[6].toString();
                        if (arrBr.length() > 30) {
                            arrBr = array[6].substring(0, 30);
                        } else {
                            arrBr = array[6].toString();
                        }

                        s1 = space(18) + "PASSBK NO: " + this.passBookNo + space(20) + arrBr;
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                        out.newLine();
                    } else if (i == 1) {
                        if ((array[1] == null) || (array[1].equalsIgnoreCase(""))) {
                            s1 = space(15) + sFormat(array[0], 50, false) + space(29) + array[2];
                        } else {
                            s1 = space(15) + sFormat(array[0] + "/" + array[1], 50, false) + space(29) + array[2];
                        }
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                    } else if (i == 2) {
                        s1 = space(56) + sFormat(array[4], 50, true);
                        out.write(s1);
                        out.newLine();
                    } else if (i == 3) {
                        arr3 = array[3].toString();
                        if (arr3.length() > 35) {
                            arr3 = array[3].substring(0, 35);
                        } else {
                            arr3 = array[3].toString();
                        }
                        s1 = space(15) + sFormat(arr3, 50, false);
                        out.write(s1);
                        out.newLine();
                    } else if (i == 4) {
                        s1 = space(54) + sFormat(array[11], 50, true);
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                    } else if (i == 5) {
                        s1 = space(15) + sFormat(array[5], 50, false);
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                    } else if (i == 6) {
                        s1 = space(15) + sFormat(array[7], 50, false);
                        out.write(s1);
                    }
                }
            }
            /**
             * For Koylanchal Urban Co-operative Bank Ltd.
             */
            if (Integer.parseInt(vecPassbook.get(0).toString()) == 4) {
                for (i = 0; i < 7; i++) {
                    if (i == 0) {
                        for (int j = 0; j < 9; j++) {
                            out.newLine();
                        }
                        s1 = space(68) + "IFSC: " + array[10];
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                    } else if (i == 1) {
                        s1 = space(87) + array[4];
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                    } else if (i == 2) {
                        s1 = space(87) + array[0];
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                    } else if (i == 3) {
                        arr3 = array[3].toString();
                        if (arr3.length() > 35) {
                            arr3 = array[3].substring(0, 35);
                        } else {
                            arr3 = array[3].toString();
                        }
                        s1 = space(87) + arr3;
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                    } else if (i == 4) {
                        s1 = space(87) + array[7];
                        out.write(s1);
                        out.newLine();
                        //out.newLine();                        
                    } else if (i == 5) {
                        if ((array[14] == null) || (array[14].equalsIgnoreCase("/"))) {
                            s1 = space(75) + "Joint Name: " + array[1];
                            out.newLine();
                            out.write(s1);
                            out.newLine();
                        } else {
                            if ((array[15] == null) || (array[15].equalsIgnoreCase("/"))) {
                                s1 = space(75) + "Joint Name: " + array[1] + "/" + array[14];
                                out.newLine();
                                out.write(s1);
                                out.newLine();
                            } else {
                                if ((array[16] == null) || (array[16].equalsIgnoreCase("/"))) {
                                    s1 = space(75) + "Joint Name: " + array[1] + "/" + array[14];
                                    out.newLine();
                                    out.write(s1);
                                    s1 = space(87) + "/" + array[15];
                                    out.newLine();
                                    out.write(s1);
                                } else {
                                    s1 = space(75) + "Joint Name: " + array[1] + "/" + array[14];
                                    out.newLine();
                                    out.write(s1);
                                    s1 = space(87) + "/" + array[15] + "/" + array[16];
                                    out.newLine();
                                    out.write(s1);
                                }
                            }
                        }
                    } else if (i == 6) {
                        s1 = space(87) + oldAcno;
                        out.newLine();
                        out.write(s1);
                        out.newLine();
                    }
                }
            }

            /**
             * For Jamshedpur Urban Co-operative Bank Ltd.
             */
            if (Integer.parseInt(vecPassbook.get(0).toString()) == 5) {
                for (i = 0; i < 8; i++) {
                    if (i == 0) {
                        for (int j = 0; j < 8; j++) {
                            out.newLine();
                        }
                        s1 = space(90) + array[4];
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                    } else if (i == 1) {
                        s1 = space(90) + array[13];
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                    } else if (i == 2) {
                        s1 = space(92) + array[0];
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                    } else if (i == 3) {
                        int lenTotM = 35;
                        arr3 = array[3].toString();
                        String remStr1 = "", remStr2 = "", remStr3 = "", remStr4 = "", remStr5 = "";
                        if (arr3.length() > lenTotM) {
                            String remStrM = arr3.substring(0, lenTotM);
                            int totPosM = remStrM.lastIndexOf(" ");
                            remStr1 = remStrM.substring(0, totPosM);
                            remStr2 = arr3.substring(totPosM + 1);
                            if (remStr2.length() > lenTotM) {
                                remStr4 = remStr2.substring(0, lenTotM);
                                int totPos1 = remStr4.lastIndexOf(" ");
                                remStr3 = remStr4.substring(0, totPos1);
                                remStr5 = arr3.substring(totPos1 + totPosM + 2);
                            } else {
                                remStr3 = remStr2;
                            }
                        } else {
                            remStr1 = arr3;
                        }
                        s1 = space(92) + remStr1;
                        out.write(s1);
                        out.newLine();
                        s1 = space(92) + remStr3;
                        out.write(s1);
                        out.newLine();
                        s1 = space(92) + remStr5;
                        out.write(s1);
                        out.newLine();
                    } else if (i == 4) {
                        if ((array[14] == null) || (array[14].equalsIgnoreCase("/"))) {
                            s1 = space(72) + "Joint Name: " + array[1];
                            out.write(s1);
                            out.newLine();
                            out.newLine();
                        } else {
                            if ((array[15] == null) || (array[15].equalsIgnoreCase("/"))) {
                                s1 = space(72) + "Joint Name: " + array[1] + "/" + array[14];
                                out.write(s1);
                                out.newLine();
                                out.newLine();
                            } else {
                                if ((array[16] == null) || (array[16].equalsIgnoreCase("/"))) {
                                    s1 = space(72) + "Joint Name: " + array[1] + "/" + array[14];
                                    out.write(s1);
                                    s1 = space(84) + "/" + array[15];
                                    out.newLine();
                                    out.write(s1);
                                    out.newLine();
                                } else {
                                    s1 = space(72) + "Joint Name: " + array[1] + "/" + array[14];
                                    out.newLine();
                                    out.write(s1);
                                    s1 = space(84) + "/" + array[15] + "/" + array[16];
                                    out.newLine();
                                    out.write(s1);
                                    out.newLine();
                                }
                            }
                        }
                    } else if (i == 5) {
                        s1 = space(90) + array[12];
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                    } else if (i == 6) {
                        s1 = space(92) + array[11];
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                    } else if (i == 7) {
                        s1 = space(72) + "IFSC: " + space(14) + array[10];
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                    }
                }
            }

            /**
             * For Tapindu Urban Co-operative Bank Ltd.
             */
            if (Integer.parseInt(vecPassbook.get(0).toString()) == 6) {
                for (i = 0; i < 4; i++) {
                    if (i == 0) {
                        for (int j = 0; j < 10; j++) {
                            out.newLine();
                        }
                        s1 = space(85) + array[4];
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                        out.newLine();
                        out.newLine();
                    } else if (i == 1) {
                        s1 = space(90) + array[0];
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                        out.newLine();
                    } else if (i == 2) {
                        s1 = space(95) + array[5];
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                    } else if (i == 3) {
                        arr3 = array[3].toString();
                        if (arr3.length() > 35) {
                            arr3 = array[3].substring(0, 35);
                        } else {
                            arr3 = array[3].toString();
                        }
                        s1 = space(92) + arr3;
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                    }
                }
            }

            /**
             * For Noida Commercial Co-operative Bank Ltd
             */
            if (Integer.parseInt(vecPassbook.get(0).toString()) == 7) {
                for (i = 0; i < 5; i++) {
                    if (i == 0) {
                        for (int j = 0; j < 24; j++) {
                            out.newLine();
                        }
                        s1 = space(105) + "IFSC: " + array[10];
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                        out.newLine();
                    } else if (i == 1) {
                        String arrBr = array[6].toString();
                        if (arrBr.length() > 30) {
                            arrBr = array[6].substring(0, 30);
                        } else {
                            arrBr = array[6].toString();
                        }
                        s1 = space(40) + arrBr + space(50) + this.passBookNo;
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                        out.newLine();
                    } else if (i == 2) {
                        s1 = space(40) + array[0];
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                    } else if (i == 3) {
                        int lenTotM = 35;
                        arr3 = array[3].toString();
                        String remStr1 = "", remStr2 = "", remStr3 = "", remStr4 = "", remStr5 = "";
                        if (arr3.length() > lenTotM) {
                            String remStrM = arr3.substring(0, lenTotM);
                            int totPosM = remStrM.lastIndexOf(" ");
                            remStr1 = remStrM.substring(0, totPosM);
                            remStr2 = arr3.substring(totPosM + 1);
                            if (remStr2.length() > lenTotM) {
                                remStr4 = remStr2.substring(0, lenTotM);
                                int totPos1 = remStr4.lastIndexOf(" ");
                                remStr3 = remStr4.substring(0, totPos1);
                                remStr5 = arr3.substring(totPos1 + totPosM + 2);
                            } else {
                                remStr3 = remStr2;
                            }
                        } else {
                            remStr1 = arr3;
                        }
                        s1 = space(40) + remStr1;
                        out.write(s1);
                        out.newLine();
                        s1 = space(40) + remStr3;
                        out.write(s1);
                        out.newLine();
                        s1 = space(40) + remStr5 + space(75) + array[4];
                        out.write(s1);
                        out.newLine();
                    } else if (i == 4) {
                        out.newLine();
                        s1 = space(45) + sFormat(array[5], 50, false);
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                    }
                }
            }

            /**
             * For Kanpur Commercial Co-operative Bank Ltd
             */
            if (Integer.parseInt(vecPassbook.get(0).toString()) == 8) {
                for (i = 0; i < 5; i++) {
                    if (i == 0) {
                        for (int j = 0; j < 28; j++) {
                            out.newLine();
                        }
                        String arrBr = array[6].toString();
                        if (arrBr.length() > 30) {
                            arrBr = array[6].substring(0, 30);
                        } else {
                            arrBr = array[6].toString();
                        }
                        s1 = space(18) + "PASSBK NO: " + this.passBookNo + space(6) + "BRANCH :  " + arrBr;
                        out.write(s1);
                        out.newLine();
                        s1 = space(18) + "MICRCODE : " + array[17] + array[18] + array[19];
                        out.write(s1);
                        out.newLine();
                    } else if (i == 1) {
                        array[0] = array[0].trim() + space(40 - array[0].trim().length());
                        s1 = space(13) + "Name:  " + array[0] + space(5) + "A/C Type: " + array[2];
                        out.write(s1);
                        out.newLine();
                        s1 = space(13) + "Father/Husband's Name:  " + array[8];
                        out.write(s1);
                        out.newLine();
                    } else if (i == 2) {
                        arr3 = array[3].toString();
                        if (arr3.length() > 35) {
                            arr3 = array[3].substring(0, 35);
                        } else {
                            arr3 = array[3].toString();
                        }
                        s1 = space(13) + "Address:   " + arr3 + space(35 - arr3.length()) + space(3) + "A/C No:  " + array[4];
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                    } else if (i == 3) {
                        s1 = space(13) + "Date Of Issue: " + array[11] + space(10) + " IFSC Code: " + array[10];
                        out.write(s1);
                        out.newLine();
                    } else if (i == 4) {
                        if (array[7].equalsIgnoreCase("Under Guardianship")) {
                            s1 = space(13) + "Guardian Name: " + array[1] + space(10) + " Operation Mode: " + array[7];
                        } else {
                            s1 = space(13) + "Joint Name: " + array[1] + space(10) + " Operation Mode: " + array[7];
                        }
                        out.write(s1);
                    }
                }
            }

            if (Integer.parseInt(vecPassbook.get(0).toString()) == 9) {
                for (i = 0; i < 7; i++) {
                    if (i == 0) {
                        for (int j = 0; j < 20; j++) {
                            out.newLine();
                        }
                        String arrBr = array[6].toString();
                        s1 = space(18) + "PASSBK NO: " + this.passBookNo + space(15) + "BRANCH :  " + arrBr;
                        out.write(s1);
                        out.newLine();
                    }
                    if (i == 1) {
                        s1 = space(18) + "Branch Phone No: " + array[24];
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                        out.newLine();
                        out.newLine();
                        out.newLine();
                    } else if (i == 2) {
                        array[0] = array[0].trim() + space(40 - array[0].trim().length());
                        s1 = space(13) + "Name:  " + array[0] + space(15) + "A/C Type: " + array[2];
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                        s1 = space(13) + "Father/Husband's Name:  " + array[8];
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                    } else if (i == 3) {
                        int lenTotM = 35;
                        arr3 = array[3].toString();
                        String remStr1 = "", remStr2 = "", remStr3 = "";
                        if (arr3.length() > lenTotM) {
                            String remStrM = arr3.substring(0, lenTotM);
                            int totPosM = remStrM.lastIndexOf(" ");
                            remStr1 = remStrM.substring(0, totPosM);
                            remStr2 = arr3.substring(totPosM + 1);
                        } else {
                            remStr1 = arr3;
                        }
                        s1 = space(13) + "Address:  " + remStr1 + space(37 - remStr1.length()) + space(15) + "A/C No:  " + array[4];
                        out.write(s1);
                        out.newLine();
                        s1 = space(24) + remStr2;
                        out.write(s1);
                        out.newLine();
                    } else if (i == 4) {
                        s1 = space(13) + "Date Of Issue: " + array[11] + space(46 - array[11].length()) + " IFSC Code: " + array[10];
                        out.newLine();
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                    } else if (i == 5) {
                        String VarNom = "";
                        if (array[9].equalsIgnoreCase("")) {
                            VarNom = "NO";
                        } else {
                            VarNom = "YES";
                        }
                        s1 = space(13) + "Operation Mode: " + array[7] + space(45 - array[7].length()) + " NOMINATION: " + VarNom;
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                    } else if (i == 6) {
                        if ((array[14] == null) || (array[14].equalsIgnoreCase("/"))) {
                            s1 = space(13) + "Joint Name: " + array[1];
                            out.write(s1);
                            out.newLine();
                            out.newLine();
                        } else {
                            if ((array[15] == null) || (array[15].equalsIgnoreCase("/"))) {
                                s1 = space(13) + "Joint Name: " + array[1] + "/" + array[14];
                                out.write(s1);
                                out.newLine();
                                out.newLine();
                            } else {
                                if ((array[16] == null) || (array[16].equalsIgnoreCase("/"))) {
                                    s1 = space(13) + "Joint Name: " + array[1] + "/" + array[14];
                                    out.write(s1);
                                    s1 = space(25) + "/" + array[15];
                                    out.newLine();
                                    out.write(s1);
                                    out.newLine();
                                } else {
                                    s1 = space(13) + "Joint Name: " + array[1] + "/" + array[14];
                                    out.newLine();
                                    out.write(s1);
                                    s1 = space(25) + "/" + array[15] + "/" + array[16];
                                    out.newLine();
                                    out.write(s1);
                                    out.newLine();
                                }
                            }
                        }
                    }
                }
            }

            /*Tapendu */
            if (Integer.parseInt(vecPassbook.get(0).toString()) == 10) {
                FtsPostingMgmtFacadeRemote ftsBeanRemote1 = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
                AccountOpeningFacadeRemote acOpenFacadeRemote = (AccountOpeningFacadeRemote) ServiceLocator.getInstance().lookup("AccountOpeningFacade");
                String acNat = ftsBeanRemote1.getAccountNature(issueNewAcno);

                String matAmts = "0";
                if (acNat.equalsIgnoreCase("RD")) {
                    matAmts = acOpenFacadeRemote.cbsRdCalculation(Float.parseFloat(array[22]), Integer.parseInt(array[21]), Float.parseFloat(array[20]));
                }

                for (i = 0; i < 6; i++) {
                    if (i == 0) {
                        for (int j = 0; j < 26; j++) {
                            out.newLine();
                        }
                        String arrBr = array[6].toString();
                        if (arrBr.length() > 30) {
                            arrBr = array[6].substring(0, 30);
                        } else {
                            arrBr = array[6].toString();
                        }

                        s1 = space(18) + "PASSBK NO: " + this.passBookNo + space(20) + "BRANCH :  " + arrBr;
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                    } else if (i == 1) {
                        array[0] = array[0].trim() + space(40 - array[0].trim().length());
                        s1 = space(13) + "Name:  " + array[0] + space(15) + "A/C Type: " + array[2];
                        out.write(s1);
                        out.newLine();
                        s1 = space(13) + "Father/Husband's Name:  " + array[8];
                        out.write(s1);
                        out.newLine();
                    } else if (i == 2) {
                        int lenTotM = 35;
                        arr3 = array[3].toString();
                        String remStr1 = "", remStr2 = "", remStr3 = "";
                        if (arr3.length() > lenTotM) {
                            String remStrM = arr3.substring(0, lenTotM);
                            int totPosM = remStrM.lastIndexOf(" ");
                            remStr1 = remStrM.substring(0, totPosM);
                            remStr2 = arr3.substring(totPosM + 1);
                        } else {
                            remStr1 = arr3;
                        }
                        s1 = space(13) + "Address:  " + remStr1 + space(37 - remStr1.length()) + space(15) + "A/C No:  " + array[4];
                        out.write(s1);
                        out.newLine();
                        s1 = space(24) + remStr2;
                        out.write(s1);
                        out.newLine();
                    } else if (i == 3) {
                        s1 = space(13) + "Date Of Issue: " + array[11] + space(46 - array[11].length()) + " IFSC Code: " + array[10];
                        out.write(s1);
                        out.newLine();
                    } else if (i == 4) {
                        if (array[7].equalsIgnoreCase("Under Guardianship")) {
                            s1 = space(13) + "Guardian Name: " + array[1] + space(15) + " Operation Mode: " + array[7];
                        } else {
                            s1 = space(13) + "Joint Name: " + array[1] + space(49 - array[1].length()) + " Operation Mode: " + array[7];
                        }
                        out.write(s1);
                    } else if (i == 5 && acNat.equalsIgnoreCase("RD")) {
                        out.newLine();
                        s1 = space(13) + "ROI : " + array[20] + space(15) + "PERIOD : " + array[21] + space(31 - (array[20].length() + array[21].length())) + " Maturity Amount : " + matAmts;
                        out.write(s1);
                        out.newLine();
                        s1 = space(13) + "Installment : " + array[22] + space(44) + " Maturity Date : " + array[23];
                        out.write(s1);
                    }
                }
            }

            /**
             * For MORENA Commercial Co-operative Bank Ltd
             */
            if (Integer.parseInt(vecPassbook.get(0).toString()) == 11) {
                for (i = 0; i < 3; i++) {
                    if (i == 0) {
                        for (int j = 0; j < 29; j++) {
                            out.newLine();
                        }
                        String arrBr = array[6].toString();
                        if (arrBr.length() > 30) {
                            arrBr = array[6].substring(0, 30);
                        } else {
                            arrBr = array[6].toString();
                        }
                        s1 = space(34) + array[0] + space(75 - array[0].length()) + arrBr;
                        out.write(s1);
                        out.newLine();
                        s1 = space(34) + array[1];
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                    } else if (i == 1) {
                        int lenTotM = 35;
                        arr3 = array[3].toString();
                        String remStr1 = "", remStr2 = "", remStr3 = "", remStr4 = "", remStr5 = "";
                        if (arr3.length() > lenTotM) {
                            String remStrM = arr3.substring(0, lenTotM);
                            int totPosM = remStrM.lastIndexOf(" ");
                            remStr1 = remStrM.substring(0, totPosM);
                            remStr2 = arr3.substring(totPosM + 1);
                            if (remStr2.length() > lenTotM) {
                                remStr4 = remStr2.substring(0, lenTotM);
                                int totPos1 = remStr4.lastIndexOf(" ");
                                remStr3 = remStr4.substring(0, totPos1);
                                remStr5 = arr3.substring(totPos1 + totPosM + 2);
                            } else {
                                remStr3 = remStr2;
                            }
                        } else {
                            remStr1 = arr3;
                        }
                        s1 = space(34) + remStr1 + space(75 - remStr1.length()) + this.passBookNo;
                        out.write(s1);
                        out.newLine();
                        s1 = space(34) + remStr3;
                        out.write(s1);
                        out.newLine();
                        s1 = space(34) + remStr5;
                        out.write(s1);
                        out.newLine();
                    } else if (i == 2) {
                        s1 = space(34) + array[5] + space(75 - array[5].length()) + array[4];
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                    }
                }
            }

            /**
             * For BUDAUN Commercial Co-operative Bank Ltd
             */
            if (Integer.parseInt(vecPassbook.get(0).toString()) == 12) {
                FtsPostingMgmtFacadeRemote ftsBeanRemote1 = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
                AccountOpeningFacadeRemote acOpenFacadeRemote = (AccountOpeningFacadeRemote) ServiceLocator.getInstance().lookup("AccountOpeningFacade");
                String acNat = ftsBeanRemote1.getAccountNature(issueNewAcno);

                String matAmts = "0";
                if (acNat.equalsIgnoreCase("RD")) {
                    matAmts = acOpenFacadeRemote.cbsRdCalculation(Float.parseFloat(array[22]), Integer.parseInt(array[21]), Float.parseFloat(array[20]));
                    for (i = 0; i < 9; i++) {
                        if (i == 0) {
                            for (int j = 0; j < 3; j++) {
                                out.newLine();
                            }
                            s1 = space(92) + array[4];
                            out.write(s1);
                            out.newLine();
                            out.newLine();
                            out.newLine();
                        } else if (i == 1) {
                            s1 = space(91) + array[0];
                            out.write(s1);
                            out.newLine();
                            out.newLine();
                            out.newLine();
                        } else if (i == 2) {
                            int lenTotM = 35;
                            arr3 = array[3].toString();
                            String remStr1 = "", remStr2 = "", remStr3 = "", remStr4 = "", remStr5 = "";
                            if (arr3.length() > lenTotM) {
                                String remStrM = arr3.substring(0, lenTotM);
                                int totPosM = remStrM.lastIndexOf(" ");
                                remStr1 = remStrM.substring(0, totPosM);
                                remStr2 = arr3.substring(totPosM + 1);
                                if (remStr2.length() > lenTotM) {
                                    remStr4 = remStr2.substring(0, lenTotM);
                                    int totPos1 = remStr4.lastIndexOf(" ");
                                    remStr3 = remStr4.substring(0, totPos1);
                                    remStr5 = arr3.substring(totPos1 + totPosM + 2);
                                } else {
                                    remStr3 = remStr2;
                                }
                            } else {
                                remStr1 = arr3;
                            }
                            s1 = space(80) + remStr1;
                            out.write(s1);
                            out.newLine();
                            s1 = space(80) + remStr3;
                            out.write(s1);
                            out.newLine();
                            s1 = space(80) + remStr5;
                            out.write(s1);
                            out.newLine();
                        } else if (i == 3) {
                            s1 = space(96) + array[22];
                            out.write(s1);
                            out.newLine();
                            out.newLine();
                            out.newLine();
                        } else if (i == 4) {
                            s1 = space(93) + array[11];
                            out.write(s1);
                            out.newLine();
                            out.newLine();
                            out.newLine();
                        } else if (i == 5) {
                            s1 = space(105) + array[21];
                            out.write(s1);
                            out.newLine();
                            out.newLine();
                            out.newLine();
                        } else if (i == 6) {
                            s1 = space(105) + array[20];
                            out.write(s1);
                            out.newLine();
                            out.newLine();
                            out.newLine();
                        } else if (i == 7) {
                            s1 = space(101) + array[23];
                            out.write(s1);
                            out.newLine();
                            out.newLine();
                            out.newLine();
                        } else if (i == 8) {
                            s1 = space(102) + matAmts;
                            out.write(s1);
                            out.newLine();
                            out.newLine();
                        }
                    }
                } else {
                    for (i = 0; i < 4; i++) {
                        if (i == 0) {
                            for (int j = 0; j < 6; j++) {
                                out.newLine();
                            }
                            s1 = space(92) + array[4] + space(15) + array[11];
                            out.write(s1);
                            out.newLine();
                            out.newLine();
                        } else if (i == 1) {
                            s1 = space(88) + array[0];
                            out.write(s1);
                            out.newLine();
                            out.newLine();
                            out.newLine();
                            out.newLine();
                            out.newLine();
                            out.newLine();
                            out.newLine();
                            out.newLine();
                        } else if (i == 2) {
                            int lenTotM = 35;
                            arr3 = array[3].toString();
                            String remStr1 = "", remStr2 = "", remStr3 = "", remStr4 = "", remStr5 = "";
                            if (arr3.length() > lenTotM) {
                                String remStrM = arr3.substring(0, lenTotM);
                                int totPosM = remStrM.lastIndexOf(" ");
                                remStr1 = remStrM.substring(0, totPosM);
                                remStr2 = arr3.substring(totPosM + 1);
                                if (remStr2.length() > lenTotM) {
                                    remStr4 = remStr2.substring(0, lenTotM);
                                    int totPos1 = remStr4.lastIndexOf(" ");
                                    remStr3 = remStr4.substring(0, totPos1);
                                    remStr5 = arr3.substring(totPos1 + totPosM + 2);
                                } else {
                                    remStr3 = remStr2;
                                }
                            } else {
                                remStr1 = arr3;
                            }
                            s1 = space(88) + remStr1;
                            out.write(s1);
                            out.newLine();
                            s1 = space(88) + remStr3;
                            out.write(s1);
                            out.newLine();
                            s1 = space(88) + remStr5;
                            out.write(s1);
                            out.newLine();
                            out.newLine();
                            out.newLine();
                            out.newLine();
                            out.newLine();
                        } else if (i == 3) {
                            s1 = space(92) + array[7];
                            out.write(s1);
                        }
                    }
                }
            }

            if (Integer.parseInt(vecPassbook.get(0).toString()) == 14) {
                for (i = 0; i < 5; i++) {
                    if (i == 0) {
                        for (int j = 0; j < 25; j++) {
                            out.newLine();
                        }
                        String arrBr = array[6].toString();
                        if (arrBr.length() > 30) {
                            arrBr = array[6].substring(0, 30);
                        } else {
                            arrBr = array[6].toString();
                        }
                        s1 = space(40) + arrBr + space(30) + "IFSC: " + array[10];
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                        out.newLine();
                        out.newLine();
                    } else if (i == 1) {
                        array[0] = array[0].trim() + space(35 - array[0].trim().length());
                        s1 = space(25) + array[0] + space(27) + this.passBookNo;
                        out.write(s1);
                        out.newLine();
                        out.newLine();

                        out.newLine();
                    } else if (i == 2) {
                        arr3 = array[3].toString();
                        if (arr3.length() > 35) {
                            arr3 = array[3].substring(0, 35);
                        } else {
                            arr3 = array[3].toString();
                        }
                        s1 = space(25) + arr3 + space(35 - arr3.length()) + space(27) + array[4];
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                        out.newLine();
                    } else if (i == 3) {
                        //s1 = space(30) + array[5] + space(8) + "Date Of Issue  : " + array[10];
                        s1 = space(25) + array[5] + space(44) + "Date Of Issue  : " + array[11];
                        out.write(s1);
                        out.newLine();
                        out.newLine();
                    } else if (i == 4) {
                        if (array[7].equalsIgnoreCase("Under Guardianship")) {
                            //s1 = space(8) + "Guardian Name : " + array[1] + space(8) + " Operation Mode : " + array[7];
                            s1 = space(13) + "Guardian Name : " + array[1] + space(48) + " Operation Mode : " + array[7];
                        } else {
                            //s1 = space(8) + "Joint Name : " + array[1] + space(8) + " Operation Mode : " + array[7];
                            s1 = space(13) + "Joint Name : " + array[1] + space(48) + " Operation Mode : " + array[7];
                        }
                        out.write(s1);
                    }
                }
            }
            out.close();
            /**
             * ********************************************************************************************
             * Code of Mayank Sir to call the Print Method to ptint the text
             * file generated.
             * /*********************************************************************************************
             */
            String printingStatusResult = printingStatus();
            if (printingStatusResult.equalsIgnoreCase("true")) {
                f.delete();
                this.setAcNoMsg("Print Successfull.");
            } else {
                this.setAcNoMsg("Error in Printing.");
            }
        } catch (ApplicationException e) {
            this.setPrnAcNoMsg(e.getMessage());
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
    }

    public String printingStatus() {
        String msg = new String();
        try {
            printReport(fileNameFirstPrnt);
            msg = "true";
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
        return msg;
    }

    public String exitBtnAction() {
        try {
            refresh();
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
        return "case1";
    }

    public void refresh() {
        try {
            issPassList.clear();
            this.setAcNo("");
            this.setAcNoMsg("");
            this.setAcNoMsg("");
            this.setAcName("");
            this.setBalance(new BigDecimal("0"));
            this.setPassBookNo("");
            this.setPrintFrom(null);
            this.setPrnAcNo("");
            this.setPrnAcNoMsg("");
            this.setPrnAcName("");
            this.setPrnBalance(new BigDecimal("0"));
            this.setCanAcNo("");
            this.setCanAcNoMsg("");
            this.setCanAcName("");
            this.setCanBalance(new BigDecimal("0"));
            this.setReason("");
            this.setFlag1("none");
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
    }

    //Code Added By Mayank For Printing
//    public String cupsAction(String nameOfFile) {
//        boolean duplex = false;
//        int copies = 1;
//        String pages = null;
//        String attributes = null;
//        String printResult = "TRUE";
//
//        try {
//            req = getRequest();
//            String remoteHost = req.getRemoteHost();
//            CupsClient cl = new CupsClient(remoteHost, 631);
//            CupsPrinter cup = cl.getDefaultPrinter();
//            String prnName = cup.getName();
//            String osName = System.getProperty("os.name");
//            String fileCr = null;
//            if (osName.equals("Linux")) {
//                fileCr = "/install/passbookPrn/";
//            } else {
//                fileCr = "c:\\passbookPrn\\";
//            }
//
//
//            String filePath = fileCr + nameOfFile;
//
//            print(remoteHost, prnName, filePath, copies, pages, duplex, attributes);
//        } catch (Exception e) {
//
//            printResult = e.getMessage();
//        }
//        return printResult;
//    }
//
//    private static void print(String host, String printerName, String fileName,
//            int copies, String pages, boolean duplex, String attributes)
//            throws Exception {
//        FileInputStream fis = new FileInputStream(fileName);
//        FileChannel fc = fis.getChannel();
//        MappedByteBuffer fileBuffer = fc.map(MapMode.READ_ONLY, 0, fc.size());
//        fileBuffer.load();
//
//        CupsPrinter printer = null;
//        CupsClient cupsClient = new CupsClient(host, CupsClient.DEFAULT_PORT);
//        if (printerName == null) {
//
//            printer = cupsClient.getDefaultPrinter();
//        } else {
//            printer = new CupsPrinter(new URL("http://" + host + ":" + CupsClient.DEFAULT_PORT + "/printers/" + printerName),
//                    printerName, false);
//        }
//
//        byte[] bytes = new byte[fileBuffer.limit()];
//        fileBuffer.get(bytes);
//
//        HashMap<String, String> attributeMap = new HashMap<String, String>();
//        if (attributes != null) {
//            attributeMap.put("job-attributes", attributes.replace("+", " "));
//        }
//
//        PrintJob printJob = new PrintJob.Builder(bytes).jobName("testJobName").userName("harald").copies(copies).pageRanges(pages).duplex(duplex).attributes(attributeMap).build();
//
//        PrintRequestResult printRequestResult = printer.print(printJob);
//        if (printRequestResult.isSuccessfulResult()) {
//            int jobID = printRequestResult.getJobId();
//            Thread.sleep(1000);
//            PrintJobAttributes job = cupsClient.getJobAttributes(host, jobID);
//        } else {
//            // you might throw an exception or try to retry printing the job
//            throw new Exception("print error! status code: " + printRequestResult.getResultCode() + " status description: " + printRequestResult.getResultDescription());
//
//        }
//    }
    public String getRequestHeader() {
        String array[] = null;
        try {
            req = getRequest();
            String userAgent = req.getHeader("User-Agent");
            String splitter = ";";
            if (userAgent.contains(";")) {
                array = userAgent.split(splitter);
            }
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getLocalizedMessage());
        }
        return array[2].toString();
    }

    public String printReport(String file) {
        try {
            String osName = System.getProperty("os.name");
            String fileCr = null;
            if (osName.equals("Linux")) {
                fileCr = "/install/passbookPrn/";
            } else {
                fileCr = "c:\\passbookPrn\\";
            }
            //String remoteHost = getRequest().getRemoteHost();
            String remoteHost = WebUtil.getClientIP(getHttpRequest());
            FileInputStream fin = new FileInputStream(fileCr + file);
            int size = fin.available();
            byte[] bytes = new byte[size];
            fin.read(bytes, 0, size);

            socketTCP = new Socket(remoteHost, PORT);

            objectOutputStream = new ObjectOutputStream(socketTCP.getOutputStream());

            objectInputStream = new ObjectInputStream(socketTCP.getInputStream());

            writeObject(bytes);

            Vector vect = (Vector) readObject();
            System.out.println("Result = " + vect.elementAt(0));
            return vect.elementAt(0).toString();
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getMessage());
            e.printStackTrace();
            return "False";
        }
    }

    private Object readObject() {
        try {
            Object readObject = objectInputStream.readObject();
            return readObject;
        } catch (Exception e) {
            e.printStackTrace();
            if (socketTCP.isClosed()) {
                //System.out.println("SOCKET CLOSED FROM READ OBJECT");
            }
            return null;
        }
    }

    public boolean writeObject(byte[] byteArr) {
        try {
            System.out.println("Inside write object >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            Vector writeVector = new Vector();
            if (printRemote.isNewPrinting("NEW PRINTER")) {
                writeVector.add("12");
            } else {
                writeVector.add("TXT");
            }
            writeVector.add(byteArr);
            objectOutputStream.writeObject(writeVector);
            objectOutputStream.flush();
            return true;
        } catch (Exception e) {
            this.setPrnAcNoMsg(e.getMessage());
            return false;
        }
    }
}
