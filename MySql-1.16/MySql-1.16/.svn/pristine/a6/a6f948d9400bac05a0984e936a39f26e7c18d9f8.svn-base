/*
Document   : EditAccIntRate
Created on : 17 Mar, 2011, 4:29:07 PM
Author     : Zeeshan Waris
 */
package com.cbs.web.controller.loan;

/**
 *
 * @author root
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;

import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.web.pojo.master.IntMasterTable;
import com.cbs.facade.loan.LoanGenralFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.hrms.constant.CbsConstant;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author root
 */
public class EditAccIntRate extends BaseBean {

    //  LoanGenralFacadeRemote loanGenralFacade;
    // FtsPostingMgmtFacade    fts;
    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String message;
    private String oldacno, acNoLen;
    private String acno;
    private String name;
    private String inttabcode;
    private String accPrefCr;
    private String minIntRateCr;
    private String maxIntRateCr;
    private String accPrefDr;
    private String minIntRateDr;
    private String maxIntRateDr;
    private String intPegflag;
    private String pegFreqMon;
    private String pegFreqDays;
    private String effNoOfDays;
    private int versionNo;
    private int recordModificationCount;
    private String createdBy;
    private String creationDate;
    private String changedBy;
    private String changedDate;
    //private String status;
    private String startDate;
    private String endDate;
    private String currentDate;
    private String sysDate;
    private String rdCreateDate;
    private String customerName;
    private boolean flag;
    private String lastUpdateDate;
    private List<SelectItem> recrdStatus;
    private Date fromDate = new Date();
    private Date toDate = new Date();
    private HttpServletRequest req;
    private List<IntMasterTable> intMasterData;
    private int currentRow;
    private IntMasterTable currentItem = new IntMasterTable();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private List<SelectItem> intCodeList;
    private String frmDate;
    private String toDt;
    String mDate1;
    String mDate;
    String createDt;
    //   private String acctType;
    private String appRoi;
    private String roi;
    private List<SelectItem> acctTypeList;
    private boolean disFlag;
    private boolean disFlag1;
    private final String LoanInterestCalculationJndiName = "LoanInterestCalculationFacade";
    private LoanInterestCalculationFacadeRemote loanInterestCalculationFacadeRemote = null;
    private final String FtsPostingMgmtFacadeJndiName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote fts = null;
    private final String LoanGenralFacadeJndiName = "LoanGenralFacade";
    private LoanGenralFacadeRemote loanGenralFacade = null;
    FtsPostingMgmtFacadeRemote facadeRemote;
    AdvancesInformationTrackingRemote aitr;
    boolean acnoFlag = false;
    private String function;
    private List<SelectItem> functionList;
    private String penalApply;
    private List<SelectItem> penalApplyList;
    private String verifyAcNo;
    private List<SelectItem> verifyAcNoList;
    private String acNoComboDisFlag = "none";

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }

    public boolean isAcnoFlag() {
        return acnoFlag;
    }

    public void setAcnoFlag(boolean acnoFlag) {
        this.acnoFlag = acnoFlag;
    }

    public String getVerifyAcNo() {
        return verifyAcNo;
    }

    public void setVerifyAcNo(String verifyAcNo) {
        this.verifyAcNo = verifyAcNo;
    }

    public List<SelectItem> getVerifyAcNoList() {
        return verifyAcNoList;
    }

    public void setVerifyAcNoList(List<SelectItem> verifyAcNoList) {
        this.verifyAcNoList = verifyAcNoList;
    }

    public String getAcNoComboDisFlag() {
        return acNoComboDisFlag;
    }

    public void setAcNoComboDisFlag(String acNoComboDisFlag) {
        this.acNoComboDisFlag = acNoComboDisFlag;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public boolean isDisFlag1() {
        return disFlag1;
    }

    public void setDisFlag1(boolean disFlag1) {
        this.disFlag1 = disFlag1;
    }

    public boolean isDisFlag() {
        return disFlag;
    }

    public void setDisFlag(boolean disFlag) {
        this.disFlag = disFlag;
    }

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }

    public String getAppRoi() {
        return appRoi;
    }

    public void setAppRoi(String appRoi) {
        this.appRoi = appRoi;
    }

    public List<SelectItem> getAcctTypeList() {
        return acctTypeList;
    }

    public void setAcctTypeList(List<SelectItem> acctTypeList) {
        this.acctTypeList = acctTypeList;
    }

    public String getFrmDate() {
        return frmDate;
    }

    public void setFrmDate(String frmDate) {
        this.frmDate = frmDate;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public List<SelectItem> getIntCodeList() {
        return intCodeList;
    }

    public void setIntCodeList(List<SelectItem> intCodeList) {
        this.intCodeList = intCodeList;
    }

    public String getAccPrefCr() {
        return accPrefCr;
    }

    public void setAccPrefCr(String accPrefCr) {
        this.accPrefCr = accPrefCr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccPrefDr() {
        return accPrefDr;
    }

    public void setAccPrefDr(String accPrefDr) {
        this.accPrefDr = accPrefDr;
    }

    public String getOldacno() {
        return oldacno;
    }

    public void setOldacno(String oldacno) {
        this.oldacno = oldacno;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getEffNoOfDays() {
        return effNoOfDays;
    }

    public void setEffNoOfDays(String effNoOfDays) {
        this.effNoOfDays = effNoOfDays;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getIntPegflag() {
        return intPegflag;
    }

    public void setIntPegflag(String intPegflag) {
        this.intPegflag = intPegflag;
    }

    public String getInttabcode() {
        return inttabcode;
    }

    public void setInttabcode(String inttabcode) {
        this.inttabcode = inttabcode;
    }

    public String getMaxIntRateCr() {
        return maxIntRateCr;
    }

    public void setMaxIntRateCr(String maxIntRateCr) {
        this.maxIntRateCr = maxIntRateCr;
    }

    public String getMaxIntRateDr() {
        return maxIntRateDr;
    }

    public void setMaxIntRateDr(String maxIntRateDr) {
        this.maxIntRateDr = maxIntRateDr;
    }

    public String getMinIntRateCr() {
        return minIntRateCr;
    }

    public void setMinIntRateCr(String minIntRateCr) {
        this.minIntRateCr = minIntRateCr;
    }

    public String getMinIntRateDr() {
        return minIntRateDr;
    }

    public void setMinIntRateDr(String minIntRateDr) {
        this.minIntRateDr = minIntRateDr;
    }

    public String getPegFreqDays() {
        return pegFreqDays;
    }

    public void setPegFreqDays(String pegFreqDays) {
        this.pegFreqDays = pegFreqDays;
    }

    public String getPegFreqMon() {
        return pegFreqMon;
    }

    public void setPegFreqMon(String pegFreqMon) {
        this.pegFreqMon = pegFreqMon;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public SimpleDateFormat getYmd() {
        return ymd;
    }

    public void setYmd(SimpleDateFormat ymd) {
        this.ymd = ymd;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getRdCreateDate() {
        return rdCreateDate;
    }

    public void setRdCreateDate(String rdCreateDate) {
        this.rdCreateDate = rdCreateDate;
    }

    public String getSysDate() {
        return sysDate;
    }

    public void setSysDate(String sysDate) {
        this.sysDate = sysDate;
    }

    public int getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(int versionNo) {
        this.versionNo = versionNo;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public String getChangedDate() {
        return changedDate;
    }

    public void setChangedDate(String changedDate) {
        this.changedDate = changedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getRecordModificationCount() {
        return recordModificationCount;
    }

    public void setRecordModificationCount(int recordModificationCount) {
        this.recordModificationCount = recordModificationCount;
    }

    public IntMasterTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(IntMasterTable currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<IntMasterTable> getIntMasterData() {
        return intMasterData;
    }

    public void setIntMasterData(List<IntMasterTable> intMasterData) {
        this.intMasterData = intMasterData;
    }

    public List<SelectItem> getRecrdStatus() {
        return recrdStatus;
    }

    public void setRecrdStatus(List<SelectItem> recrdStatus) {
        this.recrdStatus = recrdStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
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

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getPenalApply() {
        return penalApply;
    }

    public void setPenalApply(String penalApply) {
        this.penalApply = penalApply;
    }

    public List<SelectItem> getPenalApplyList() {
        return penalApplyList;
    }

    public void setPenalApplyList(List<SelectItem> penalApplyList) {
        this.penalApplyList = penalApplyList;
    }
    
    

    public EditAccIntRate() {
        req = getRequest();
        clear();
        try {
            loanInterestCalculationFacadeRemote = (LoanInterestCalculationFacadeRemote) ServiceLocator.getInstance().lookup(LoanInterestCalculationJndiName);
            facadeRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            aitr = (AdvancesInformationTrackingRemote) ServiceLocator.getInstance().lookup("AdvancesInformationTrackingFacade");
            this.setAcNoLen(getAcNoLength());
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            //    loanGenralFacade = (LoanGenralFacadeRemote) ServiceLocator.getInstance().lookup("LoanGenralFacade");
            loanGenralFacade = (LoanGenralFacadeRemote) ServiceLocator.getInstance().lookup(LoanGenralFacadeJndiName);
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(FtsPostingMgmtFacadeJndiName);
            //setUserName("manager1");
            Date date = new Date();
            setTodayDate(sdf.format(date));
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
            setSysDate(sdf1.format(date));
            setLastUpdateDate(sdf1.format(date));
            String toDt = "31" + "/" + "12" + "/" + 2099;
            // setToDate(sdf.parse(toDt));
            recrdStatus = new ArrayList<SelectItem>();
            recrdStatus.add(new SelectItem("0", "--SELECT--"));
            recrdStatus.add(new SelectItem("Y", "Yes"));
            recrdStatus.add(new SelectItem("N", "No"));
            functionList = new ArrayList<>();
            functionList.add(new SelectItem("0", "--SELECT--"));
            functionList.add(new SelectItem("M", "MODIFY"));
            functionList.add(new SelectItem("V", "VERIFY"));
            penalApplyList = new ArrayList<>();
            penalApplyList.add(new SelectItem("Y","Yes"));
            penalApplyList.add(new SelectItem("N","No"));
            setAccPrefCr("0.0");
            setMinIntRateCr("0.0");
            setMaxIntRateCr("0.0");
            setAccPrefDr("0.0");
            setMinIntRateDr("0.0");
            setMaxIntRateDr("0.0");
            this.setMessage("");
            //getDropDownList();
            intCodeList = new ArrayList<SelectItem>();
            intCodeList.add(new SelectItem("0", "--SELECT--"));
            this.setFlag(false);
            this.setDisFlag(false);
            this.setDisFlag1(false);
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    /************************** Update Button functionality************************/
    public void updateBtnAction() {
        this.setMessage("");
        try {
            if (this.function.equalsIgnoreCase("0")) {
                this.setMessage("Please select the Function Type!!! ");
                return;
            }
            String fullAcNo = acno;
            String auth = "";
            String curentDate;
            Date date = new Date();
            curentDate = ymd.format(date);
            startDate = ymd.format(this.fromDate);
            endDate = ymd.format(this.toDate);
            String result = "";
            if (this.function.equalsIgnoreCase("M")) {
                auth = "N";
                if (this.acno == null || this.acno.length() == 0) {
                    this.setMessage("Please Enter the Account No.");
                    return;
                }
                fullAcNo = acno;

                if (fullAcNo.equals("")) {
                    this.setMessage("Please Enter the Account No.");
                    return;
                }
            if(inttabcode.equals("0")){
                    this.setMessage("Please select the Int Table Code ");
                    return;
                }
                if (onblurChecking().equalsIgnoreCase("false")) {
                    return;
                }
                if (fromDate == null) {
                    this.setMessage("Please fill the Start Date");
                    return;
                }
                if (toDate == null) {
                    this.setMessage("Please fill the End Date");
                    return;
                }
                result = loanGenralFacade.interestSaveUpdate(fullAcNo, inttabcode, Double.parseDouble(accPrefCr), Double.parseDouble(minIntRateCr), Double.parseDouble(maxIntRateCr), Double.parseDouble(accPrefDr), Double.parseDouble(minIntRateDr), Double.parseDouble(maxIntRateDr), intPegflag, Integer.parseInt(pegFreqMon), Integer.parseInt(pegFreqDays), startDate, endDate, Integer.parseInt(effNoOfDays), userName, curentDate, createdBy, createDt, auth,penalApply);
                setMessage(result);
            } else if (this.function.equalsIgnoreCase("V")) {
                auth = "Y";
                fullAcNo = verifyAcNo;
                result = loanGenralFacade.interestVerify(fullAcNo, auth,this.userName);
                setMessage(result);
            }
            clear();

        } catch (ApplicationException e) {
            setMessage(getMessage());
        } catch (Exception e) {
            setMessage(getMessage());
        }
    }

    public void disableAcPrefField() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher limitCheck = p.matcher(this.accPrefCr);
            if (!limitCheck.matches()) {
                this.setAccPrefCr("0");
                this.setMessage("Please enter valid Acc Pref (Cr).");
                return;
            }
            if (this.accPrefCr.contains(".")) {
                if (this.accPrefCr.indexOf(".") != this.accPrefCr.lastIndexOf(".")) {
                    this.setAccPrefCr("0");
                    this.setMessage("Please enter valid Acc Pref (Cr).");
                    return;
                } else if (this.accPrefCr.substring(accPrefCr.indexOf(".") + 1).length() > 2) {
                    this.setAccPrefCr("0");
                    this.setMessage("Please enter Acc Pref (Cr) upto two decimal places.");
                    return;
                }
            }
            if (Float.parseFloat(this.accPrefCr) < 0) {
                this.setAccPrefCr("0");
                this.setMessage("Acc Pref (Cr) cannot be less than zero !!!");
                return;
            }
            if (this.accPrefCr == null || this.accPrefCr.equalsIgnoreCase("") || this.accPrefCr.length() == 0 || Float.parseFloat(this.accPrefCr) == 0) {
                this.setDisFlag(false);
            } else {
                this.setDisFlag(true);
                this.setAccPrefDr("0");
            }
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void disableAcPrefCrField() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            Matcher limitCheck = p.matcher(this.accPrefDr);
            if (!limitCheck.matches()) {
                this.setAccPrefDr("0");
                this.setMessage("Please enter valid Acc Pref (Dr).");
                return;
            }
            if (this.accPrefDr.contains(".")) {
                if (this.accPrefDr.indexOf(".") != this.accPrefDr.lastIndexOf(".")) {
                    this.setAccPrefDr("0");
                    this.setMessage("Please enter valid Acc Pref (Dr).");
                    return;
                } else if (this.accPrefDr.substring(accPrefDr.indexOf(".") + 1).length() > 2) {
                    this.setAccPrefDr("0");
                    this.setMessage("Please enter Acc Pref (Dr) upto two decimal places.");
                    return;
                }
            }
            if (Float.parseFloat(this.accPrefDr) < 0) {
                this.setAccPrefDr("0");
                this.setMessage("Acc Pref (Dr) cannot be less than zero !!!");
                return;
            }
            if (this.accPrefDr == null || this.accPrefDr.equalsIgnoreCase("") || this.accPrefDr.length() == 0 || Float.parseFloat(this.accPrefDr) == 0) {
                this.setDisFlag1(false);
            } else {
                this.setDisFlag1(true);
                this.setAccPrefCr("0");
            }
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void setAppRoiValue() {
        try {
            if (this.roi == null || this.roi.equalsIgnoreCase("") || this.roi.length() == 0) {
                this.setRoi("0");
            }
            if (this.accPrefCr == null || this.accPrefCr.equalsIgnoreCase("") || this.accPrefCr.length() == 0) {
                this.setAccPrefCr("0");
            }
            if (this.accPrefDr == null || this.accPrefDr.equalsIgnoreCase("") || this.accPrefDr.length() == 0) {
                this.setAccPrefDr("0");
            }
            this.setAppRoi(String.valueOf(Double.parseDouble(roi) - Double.parseDouble(accPrefCr) + Double.parseDouble(accPrefDr)));
            
            if(Double.parseDouble(this.getAppRoi())==0){
              setPenalApply("N");
            }else{
              setPenalApply("Y");  
            }
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void getDetailsOnblurFunction() {
        try {
            if (this.function.equalsIgnoreCase("0")) {
                this.setMessage("Please Select Function.");
                return;
            }
            acNoComboDisFlag = "none";
            if (this.function.equalsIgnoreCase("A")) {
                this.acnoFlag = false;
            } else if (this.function.equalsIgnoreCase("M")) {
                this.acnoFlag = false;
            } else if (this.function.equalsIgnoreCase("V")) {
                this.acnoFlag = true;
                acNoComboDisFlag = "";
                getUnVerifyRecords();
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = e.getMessage();
        }
    }

    public void getUnVerifyRecords() {
        try {
            verifyAcNoList = new ArrayList<SelectItem>();            
            List acnoList = loanGenralFacade.getUnVerifiedAcNo(this.orgnBrCode);
            verifyAcNoList.add(new SelectItem("0", "--Select--"));
            if (!acnoList.isEmpty()) {
                for (int j = 0; j < acnoList.size(); j++) {
                    Vector vect = (Vector) acnoList.get(j);
                    verifyAcNoList.add(new SelectItem(vect.get(0).toString()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDetailsVerifyAcno() {
        try {
            Integer loanPdInMonth = 0, loanPdInDays = 0;
            acno = fts.getNewAccountNumber(verifyAcNo);
            setAcno(acno);
            List resultLt = new ArrayList();
            resultLt = loanGenralFacade.tableDataUnverified(acno);
            /*
             * Select a.ACNO,a.AC_INT_VER_NO,a.INT_TABLE_CODE,a.ACC_PREF_CR,a.MIN_INT_RATE_CR,a.MAX_INT_RATE_CR,a.AC_PREF_DR,a.MIN_INT_RATE_DR,a.MAX_INT_RATE_DR,a.INT_PEG_FLG,a.PEG_FREQ_MON,a.PEG_FREQ_DAYS,a.EFF_FRM_DT,ifnull(a.EFF_TO_DT,''),ifnull(a.EFF_NO_OF_DAYS,0),a.CREATED_BY,a.CREATION_DT,a.MOD_CNT,a.UPDATED_BY,a.UPDATED_DT,b.custname from cbs_acc_int_rate_details a ,accountmaster b   where a.ACNO='" + acno + "' and a.ACNO=b.acno  and AC_INT_VER_NO=(select max(AC_INT_VER_NO) from cbs_acc_int_rate_details where ACNO='" + acno + "' and auth ='N')
             */
            if (resultLt.size() > 0) {
                for (int i = 0; i < resultLt.size(); i++) {
                    Vector ele = (Vector) resultLt.get(i);
                    setOldacno(ele.get(0).toString());
                    setAcno(acno);
                    if (ele.get(1).toString().equalsIgnoreCase("")) {
                        setVersionNo(0);
                    } else {
                        setVersionNo(Integer.parseInt(ele.get(1).toString()));
                    }                    
                    if (ele.get(2).toString().equalsIgnoreCase("")) {
                        setInttabcode("");
                    } else {
                        setInttabcode(ele.get(2).toString());
                    }
                    if (ele.get(3).toString().equalsIgnoreCase("")) {
                        setAccPrefCr("");
                    } else {
                        setAccPrefCr(ele.get(3).toString());
                    }

                    if (ele.get(4).toString().equalsIgnoreCase("")) {
                        setMinIntRateCr("");
                    } else {
                        setMinIntRateCr(ele.get(4).toString());
                    }

                    if (ele.get(5).toString().equalsIgnoreCase("")) {
                        setMaxIntRateCr("");
                    } else {
                        setMaxIntRateCr(ele.get(5).toString());
                    }

                    if (ele.get(6).toString().equalsIgnoreCase("")) {
                        setAccPrefDr("");
                    } else {
                        setAccPrefDr(ele.get(6).toString());
                    }
                    if (ele.get(7).toString().equalsIgnoreCase("")) {
                        setMinIntRateDr("");
                    } else {
                        setMinIntRateDr(ele.get(7).toString());
                    }

                    if (ele.get(8).toString().equalsIgnoreCase("")) {
                        setMaxIntRateDr("");
                    } else {
                        setMaxIntRateDr(ele.get(8).toString());
                    }

                    if (ele.get(9).toString().equalsIgnoreCase("")) {
                        setIntPegflag("");
                    } else {
                        setIntPegflag(ele.get(9).toString());
                    }
                    if (ele.get(10).toString().equalsIgnoreCase("")) {
                        setPegFreqMon("");
                    } else {
                        setPegFreqMon(ele.get(10).toString());
                    }
                    if (ele.get(11).toString().equalsIgnoreCase("")) {
                        setPegFreqDays("");
                    } else {
                        setPegFreqDays(ele.get(11).toString());
                    }

                    if (ele.get(12).toString().equalsIgnoreCase("")) {
                        setFrmDate("");
                    } else {
                        String createdate = ele.get(12).toString();
                        String year = createdate.substring(0, 4);
                        String month = createdate.substring(5, 7);
                        String day = createdate.substring(8, 10);
                        mDate = year + month + day;
                    }

                    if (ele.get(13).toString().equalsIgnoreCase("")) {
                        setToDt("");
                        mDate1 = CbsUtil.monthAdd(CbsUtil.dateAdd(mDate, loanPdInDays), loanPdInMonth);
                    } else {
                        String createdate1 = ele.get(13).toString();
                        String year1 = createdate1.substring(0, 4);
                        String month1 = createdate1.substring(5, 7);
                        String day1 = createdate1.substring(8, 10);
                        mDate1 = year1 + month1 + day1;

                    }
                    try {
                        this.setFromDate(sdf.parse(sdf.format(ymd.parse(mDate))));
                        this.setToDate(sdf.parse(sdf.format(ymd.parse(mDate1))));
                    } catch (ParseException ex) {
                        Logger.getLogger(EditAccIntRate.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    if (ele.get(14).toString().equalsIgnoreCase("0")) {
                        setEffNoOfDays(String.valueOf(CbsUtil.dayDiff(ymd.parse(mDate), ymd.parse(mDate1))));
                    } else {
                        setEffNoOfDays(ele.get(14).toString());
                    }

                    if (ele.get(15).toString().equalsIgnoreCase("")) {
                        setCreatedBy("");
                    } else {
                        setCreatedBy(ele.get(15).toString());
                    }
                    if (ele.get(16).toString().equalsIgnoreCase("")) {
                        setCreationDate("");
                    } else {
                        String createdt = ele.get(16).toString();
                        String year = createdt.substring(0, 4);
                        String month = createdt.substring(5, 7);
                        String day = createdt.substring(8, 10);
                        String time = createdt.substring(10);
                        String modDate = day + "-" + month + "-" + year;
                        setCreationDate(modDate);
                        createDt = year + month + day;
                    }

                    if (ele.get(17).toString().equalsIgnoreCase("")) {
                        setRecordModificationCount(1);
                    } else {
                        setRecordModificationCount(Integer.parseInt(ele.get(17).toString()));
                    }


                    if (ele.get(18) == null || ele.get(18).toString().equalsIgnoreCase("")) {
                        setChangedBy("");
                    } else {
                        setChangedBy(ele.get(18).toString());
                    }

                    if (ele.get(19) == null || ele.get(19).toString().equalsIgnoreCase("")) {
                        setChangedDate("");
                    } else {
                        String dt = ele.get(19).toString();
                        String yearModified = dt.substring(0, 4);
                        String monthModified = dt.substring(5, 7);
                        String dayModified = dt.substring(8, 10);
                        String timeModified = dt.substring(10);
                        String modifiedDate = dayModified + "-" + monthModified + "-" + yearModified;
                        // String modifiedDate = dayModified + "-" + monthModified + "-" + yearModified + timeModified;
                        setChangedDate(modifiedDate);
                    }
                    setCustomerName(ele.get(20).toString());
                    setPenalApply(ele.get(21).toString());
                }
                String curDate = this.todayDate.substring(6) + this.todayDate.substring(3, 5) + this.todayDate.substring(0, 2);
                String result = loanInterestCalculationFacadeRemote.getRoiLoanAccount(0, curDate, acno);
                if (result == null || result.equalsIgnoreCase("")) {
                    this.setAppRoi("0.00");
                } else if (result.substring(0, 1).equalsIgnoreCase("f")) {
                    this.setAppRoi("0.00");
                } else {
                    this.setAppRoi(result);
                }
                String result1 = loanInterestCalculationFacadeRemote.getROI(inttabcode, 0, curDate);
                if (result1 == null || result1.equalsIgnoreCase("")) {
                    this.setRoi("0.00");
                } else if (result1.substring(0, 1).equalsIgnoreCase("f")) {
                    this.setRoi("0.00");
                } else {
                    this.setRoi(result1);
                }
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
            message = e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            message = e.getMessage();
        }
    }
    public void getDetails() {
        try {
            this.setMessage("");
            /**Added by Rohit Krishna Gupta On 20 May 2011**/
            this.setRoi("0.00");
            this.setAppRoi("0.00");
            this.setDisFlag(false);
            this.setDisFlag1(false);
            flag = false;
            setVersionNo(0);
            setInttabcode("0");
            setAccPrefCr("");
            setMinIntRateCr("");
            setMaxIntRateCr("");
            setAccPrefDr("");
            setMinIntRateDr("");
            setMaxIntRateDr("");
            setIntPegflag("0");
            setPegFreqMon("");
            setPegFreqDays("");
            setVersionNo(0);
            setRecordModificationCount(0);
            fromDate = new Date();
            setFromDate(fromDate);
            toDate = new Date();
            setToDate(toDate);
            setEffNoOfDays("");
            setCreatedBy("");
            setCreationDate("");
            setChangedDate("");
            setChangedBy("");
            Integer loanPdInMonth = 0 , loanPdInDays=0;
            if (this.oldacno == null || this.oldacno.equalsIgnoreCase("") || this.oldacno.length() == 0) {
                this.setMessage("Please Enter the Account No.");
                setCustomerName("");
                return;
            }

            if (!oldacno.matches("[0-9a-zA-z]*")) {
                setMessage("Please Enter Valid  Account No.");
                return;
            }
            //if (oldacno.length() != 12) {
            if (!this.oldacno.equalsIgnoreCase("") && ((this.oldacno.length() != 12) && (this.oldacno.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                setMessage("Account number is not valid.");
                return;
            }
//            if(!oldacno.substring(0, 2).equals(orgnBrCode)){
//                setMessage("Account Number does not exist.");
//                return;
//            }

            String acNat = fts.getAccountNature(oldacno);
            if(acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)){
                setMessage("Saving Account Rate Can't Be Changed From This Form.");
                return;
            }

            acno = fts.getNewAccountNumber(oldacno);
            setAcno(acno);
            //acNature();
            if (message.equalsIgnoreCase("ACCOUNT NATURE NOT FOUND FOR THIS ACCOUNT")) {
                setMessage("Account Nature Not Found For This Account");
                return;
            } else {
                List resultLt = new ArrayList();
                resultLt = loanGenralFacade.tableData(acno);
                if (resultLt.size() > 0) {
                    for (int i = 0; i < resultLt.size(); i++) {
                        Vector ele = (Vector) resultLt.get(i);
                        if (ele.get(1).toString().equalsIgnoreCase("")) {
                            setVersionNo(0);
                        } else {
                            setVersionNo(Integer.parseInt(ele.get(1).toString()));
                        }
                        List resultList1 = aitr.getIntCodeIntTypeSchmCode(acno);
                        if (!resultList1.isEmpty()) {
                            Vector element = (Vector) resultList1.get(0);
                            loanPdInMonth = Integer.parseInt(element.get(3).toString());
                            loanPdInDays = Integer.parseInt(element.get(4).toString());
                            List resultList = aitr.interestTableCode(element.get(0).toString());
                            if (!resultList.isEmpty()) {
                                intCodeList = new ArrayList<SelectItem>();
                                intCodeList.add(new SelectItem("0", "--SELECT--"));
                                for (int k = 0; k < resultList.size(); k++) {
                                    Vector ele1 = (Vector) resultList.get(k);
                                    intCodeList.add(new SelectItem(ele1.get(0).toString(), ele1.get(1).toString()));
                                }
                            } else {
                                intCodeList = new ArrayList<SelectItem>();
                                intCodeList.add(new SelectItem("0", "--SELECT--"));
                            }
                        }
                        if (ele.get(2).toString().equalsIgnoreCase("")) {
                            setInttabcode("");
                        } else {
                            setInttabcode(ele.get(2).toString());
                        }
                        if (ele.get(3).toString().equalsIgnoreCase("")) {
                            setAccPrefCr("");
                        } else {
                            setAccPrefCr(ele.get(3).toString());
                        }

                        if (ele.get(4).toString().equalsIgnoreCase("")) {
                            setMinIntRateCr("");
                        } else {
                            setMinIntRateCr(ele.get(4).toString());
                        }

                        if (ele.get(5).toString().equalsIgnoreCase("")) {
                            setMaxIntRateCr("");
                        } else {
                            setMaxIntRateCr(ele.get(5).toString());
                        }

                        if (ele.get(6).toString().equalsIgnoreCase("")) {
                            setAccPrefDr("");
                        } else {
                            setAccPrefDr(ele.get(6).toString());
                        }
                        if (ele.get(7).toString().equalsIgnoreCase("")) {
                            setMinIntRateDr("");
                        } else {
                            setMinIntRateDr(ele.get(7).toString());
                        }

                        if (ele.get(8).toString().equalsIgnoreCase("")) {
                            setMaxIntRateDr("");
                        } else {
                            setMaxIntRateDr(ele.get(8).toString());
                        }

                        if (ele.get(9).toString().equalsIgnoreCase("")) {
                            setIntPegflag("");
                        } else {
                            setIntPegflag(ele.get(9).toString());
                        }
                        if (ele.get(10).toString().equalsIgnoreCase("")) {
                            setPegFreqMon("");
                        } else {
                            setPegFreqMon(ele.get(10).toString());
                        }
                        if (ele.get(11).toString().equalsIgnoreCase("")) {
                            setPegFreqDays("");
                        } else {
                            setPegFreqDays(ele.get(11).toString());
                        }

                        if (ele.get(12).toString().equalsIgnoreCase("")) {
                            setFrmDate("");
                        } else {
                            String createdate = ele.get(12).toString();
                            String year = createdate.substring(0, 4);
                            String month = createdate.substring(5, 7);
                            String day = createdate.substring(8, 10);
                            mDate = year + month + day;
                        }

                        if (ele.get(13).toString().equalsIgnoreCase("")) {
                            setToDt("");
                            mDate1 = CbsUtil.monthAdd(CbsUtil.dateAdd(mDate,loanPdInDays),loanPdInMonth);
                        } else {
                            String createdate1 = ele.get(13).toString();
                            String year1 = createdate1.substring(0, 4);
                            String month1 = createdate1.substring(5, 7);
                            String day1 = createdate1.substring(8, 10);
                            mDate1 = year1 + month1 + day1;

                        }

                        flag = true;
                        try {
                            this.setFromDate(sdf.parse(sdf.format(ymd.parse(mDate))));
                            this.setToDate(sdf.parse(sdf.format(ymd.parse(mDate1))));
                        } catch (ParseException ex) {
                            Logger.getLogger(EditAccIntRate.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        if (ele.get(14).toString().equalsIgnoreCase("0")) {
                            setEffNoOfDays(String.valueOf(CbsUtil.dayDiff(ymd.parse(mDate), ymd.parse(mDate1))));
                        } else {
                            setEffNoOfDays(ele.get(14).toString());
                        }

                        if (ele.get(15).toString().equalsIgnoreCase("")) {
                            setCreatedBy("");
                        } else {
                            setCreatedBy(ele.get(15).toString());
                        }
                        if (ele.get(16).toString().equalsIgnoreCase("")) {
                            setCreationDate("");
                        } else {
                            String createdt = ele.get(16).toString();
                            String year = createdt.substring(0, 4);
                            String month = createdt.substring(5, 7);
                            String day = createdt.substring(8, 10);
                            String time = createdt.substring(10);
                            String modDate = day + "-" + month + "-" + year;
                            //String modDate = day + "-" + month + "-" + year + time;
                            setCreationDate(modDate);
                            createDt = year + month + day;
                        }

                        if (ele.get(17).toString().equalsIgnoreCase("")) {
                            setRecordModificationCount(1);
                        } else {
                            setRecordModificationCount(Integer.parseInt(ele.get(17).toString()));
                        }


                        if (ele.get(18) == null || ele.get(18).toString().equalsIgnoreCase("")) {
                            setChangedBy("");
                        } else {
                            setChangedBy(ele.get(18).toString());
                        }

                        if (ele.get(19) == null || ele.get(19).toString().equalsIgnoreCase("")) {
                            setChangedDate("");
                        } else {
                            String dt = ele.get(19).toString();
                            String yearModified = dt.substring(0, 4);
                            String monthModified = dt.substring(5, 7);
                            String dayModified = dt.substring(8, 10);
                            String timeModified = dt.substring(10);
                            String modifiedDate = dayModified + "-" + monthModified + "-" + yearModified;
                            // String modifiedDate = dayModified + "-" + monthModified + "-" + yearModified + timeModified;
                            setChangedDate(modifiedDate);
                        }
                        setCustomerName(ele.get(20).toString());
                        setPenalApply(ele.get(21).toString());
                    }

                    String curDate = this.todayDate.substring(6) + this.todayDate.substring(3, 5) + this.todayDate.substring(0, 2);
                    String result = loanInterestCalculationFacadeRemote.getRoiLoanAccount(0, curDate, acno);
                    if (result == null || result.equalsIgnoreCase("")) {
                        this.setAppRoi("0.00");
                    } else if (result.substring(0, 1).equalsIgnoreCase("f")) {
                        this.setAppRoi("0.00");
                    } else {
                        this.setAppRoi(result);
                    }
                    String result1 = loanInterestCalculationFacadeRemote.getROI(inttabcode, 0, curDate);
                    if (result1 == null || result1.equalsIgnoreCase("")) {
                        this.setRoi("0.00");
                    } else if (result1.substring(0, 1).equalsIgnoreCase("f")) {
                        this.setRoi("0.00");
                    } else {
                        this.setRoi(result1);
                    }
                } else {
                    setMessage("No Record Found");
                    setCustomerName("");
                    this.setRoi("0.00");
                    this.setAppRoi("0.00");
                    this.setDisFlag(false);
                    this.setDisFlag1(false);
                    flag = false;
                    setVersionNo(0);
                    setInttabcode("0");
                    setAccPrefCr("");
                    setMinIntRateCr("");
                    setMaxIntRateCr("");
                    setAccPrefDr("");
                    setMinIntRateDr("");
                    setMaxIntRateDr("");
                    setIntPegflag("0");
                    setPegFreqMon("");
                    setPegFreqDays("");
                    setVersionNo(0);
                    setRecordModificationCount(0);
                    fromDate = new Date();
                    setFromDate(fromDate);
                    toDate = new Date();
                    setToDate(toDate);
                    setEffNoOfDays("");
                    setCreatedBy("");
                    setCreationDate("");
                    setChangedDate("");
                    setChangedBy("");
                    return;
                }
            }
        } catch (ApplicationException e) {
            e.printStackTrace();
            message = e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            message = e.getMessage();
        }
    }

    public void getRoiForIntTableCode() {
        this.setMessage("");
        try {
            if (this.inttabcode.equalsIgnoreCase("--SELECT--") || inttabcode.equalsIgnoreCase("")) {
                this.setMessage("Please select Int Table Code.");
                return;
            }
            String curDate = this.todayDate.substring(6) + this.todayDate.substring(3, 5) + this.todayDate.substring(0, 2);
            String result = loanInterestCalculationFacadeRemote.getROI(inttabcode, 0, curDate);
            if (result == null || result.equalsIgnoreCase("")) {
                this.setRoi("0.00");
            } else if (result.substring(0, 1).equalsIgnoreCase("f")) {
                this.setRoi("0.00");
            } else {
                this.setRoi(result);
            }
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void dateDiffDetails() {
        try {
            if (fromDate == null) {
                this.setMessage("Please fill the Start Date");
                return;
            }
            if (toDate == null) {
                this.setMessage("Please fill the End Date");
                return;
            }
            startDate = ymd.format(this.fromDate);
            endDate = ymd.format(this.toDate);
            List resultLt = new ArrayList();
            resultLt = loanGenralFacade.dateDiff(startDate, endDate);
            if (resultLt.size() > 0) {
                for (int i = 0; i < resultLt.size(); i++) {
                    Vector ele = (Vector) resultLt.get(i);
                    if (ele.get(0).toString().equalsIgnoreCase("")) {
                        setEffNoOfDays("");
                    } else {
                        setEffNoOfDays(ele.get(0).toString());
                    }
                }
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void clear() {
        try {
            setCustomerName("");
            this.setRoi("0.00");
            this.setAppRoi("0.00");
            flag = false;
            setAcno("");
            setVersionNo(0);
            setInttabcode("0");
            setAccPrefCr("");
            setMinIntRateCr("");
            setMaxIntRateCr("");
            setAccPrefDr("");
            setMinIntRateDr("");
            setMaxIntRateDr("");
            setIntPegflag("0");
            setPegFreqMon("");
            setPegFreqDays("");
            setVersionNo(0);
            setRecordModificationCount(0);
            fromDate = new Date();
            setFromDate(fromDate);
            toDate = new Date();
            setToDate(toDate);
            setEffNoOfDays("");
            setCreatedBy("");
            setCreationDate("");
            setChangedDate("");
            setChangedBy("");
            setOldacno("");
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void refreshButton() {
        try {
            this.setRoi("0.00");
            this.setAppRoi("0.00");
            this.setDisFlag(false);
            this.setDisFlag1(false);
            flag = false;
            this.setMessage("");
            setAcno("");
            setVersionNo(0);
            setInttabcode("0");
            setAccPrefCr("");
            setMinIntRateCr("");
            setMaxIntRateCr("");
            setAccPrefDr("");
            setMinIntRateDr("");
            setMaxIntRateDr("");
            setIntPegflag("0");
            setPegFreqMon("");
            setPegFreqDays("");
            setVersionNo(0);
            setRecordModificationCount(0);
            fromDate = new Date();
            setFromDate(fromDate);
            toDate = new Date();
            setToDate(toDate);
            setEffNoOfDays("");
            setCreatedBy("");
            setCreationDate("");
            setChangedDate("");
            setChangedBy("");
            setCustomerName("");
            setOldacno("");
        } catch (Exception ex) {
        }
    }

    public void acNature() {
        try {
            String resultList;
            String accountCode = facadeRemote.getAccountCode(acno);
            resultList = loanGenralFacade.acNature(accountCode);
            this.setMessage(resultList);
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }

    }

    public String onblurChecking() {
        try {
            if (accPrefCr == null || accPrefCr.equalsIgnoreCase("")) {
            } else {
                if (!accPrefCr.matches("[0-9.]*")) {
                    this.setMessage("Please Enter Numeric Value in  Acc Pref (Cr)");
                    return "false";
                }
            }
            if (minIntRateCr == null || minIntRateCr.equalsIgnoreCase("")) {
            } else {
                if (!minIntRateCr.matches("[0-9.]*")) {
                    this.setMessage("Please Enter Numeric Value in Min Int Rate (Cr)");
                    return "false";
                }
            }
            if (maxIntRateCr == null || maxIntRateCr.equalsIgnoreCase("")) {
            } else {
                if (!maxIntRateCr.matches("[0-9.]*")) {
                    this.setMessage("Please Enter Numeric Value in Max Int Rate (Cr)");
                    return "false";
                }
            }
            if (accPrefDr == null || accPrefDr.equalsIgnoreCase("")) {
            } else {
                if (!accPrefDr.matches("[0-9.]*")) {
                    this.setMessage("Please Enter Numeric Value in Acc Pref (Dr)");
                    return "false";
                }
            }
            if (minIntRateDr == null || minIntRateDr.equalsIgnoreCase("")) {
            } else {
                if (!minIntRateDr.matches("[0-9.]*")) {
                    this.setMessage("Please Enter Numeric Value in Min Int Rate (Dr)");
                    return "false";
                }
            }

            if (maxIntRateDr == null || maxIntRateDr.equalsIgnoreCase("")) {
            } else {
                if (!maxIntRateDr.matches("[0-9.]*")) {
                    this.setMessage("Please Enter Numeric Value in Max Int Rate (Dr)");
                    return "false";
                }
            }
            return "true";
        } catch (Exception e) {
            message = e.getMessage();
        }
        return "true";
    }

    public void resetButton() {
        this.setMessage("");
    }

    public String exitForm() {
        clear();
        this.setMessage("");
        return "case1";
    }
}
