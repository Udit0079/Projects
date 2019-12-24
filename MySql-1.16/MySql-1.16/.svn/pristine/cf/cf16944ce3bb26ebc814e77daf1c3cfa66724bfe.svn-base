    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.td;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.TdReceiptIntDetailPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.facade.report.RbiReportFacadeRemote;
import com.cbs.facade.td.TermDepositeCalculationManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.pojo.GridData;
import com.cbs.web.pojo.other.AcDetailGrid;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author stellar
 */
public class TdsEntryForSingle extends BaseBean{

    String accNo, acNoLen;
    String custName;
    String jointName;
    String opMode;
    String erMsg;
    String rtNo;
    String recpNo;
    String tdsAmt;
    float totAmt;
    int currentRow;
    GridData currentItem;
    List<AcDetailGrid> acd;
    List<GridData> gdl;
    AcDetailGrid actblDetail;
    GridData gd;
    int currentRow1;
    AcDetailGrid currentItem1;
    private String fullAcno;
    private String tmpCumuAmt;
    private String intOpt;
    private String accountFlag;
    private final String jndiHomeNameTdCal = "TermDepositeCalculationManagementFacade";
    private TermDepositeCalculationManagementFacadeRemote tdCalMgmtRemote = null;
   // private final String jndiHomeNameTdRcpt = "TdReceiptManagementFacade";
    //private TdReceiptManagementFacadeRemote tdRcptMgmtRemote = null;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private final String jndiHomeNameOtherReport = "OtherReportFacade";
    private OtherReportFacadeRemote otherReport  = null;
    private final String jndiHomeNameRbiReport = "RbiReportFacade";
    private RbiReportFacadeRemote rbiReport  = null;
    String oldAcNo;
    String rowAcno , rowVchNo, rowTdsUnRec;
    
    private String rtFlag;
    private String rtDisab;

    public String getRowAcno() {
        return rowAcno;
    }

    public void setRowAcno(String rowAcno) {
        this.rowAcno = rowAcno;
    }

    public String getRowVchNo() {
        return rowVchNo;
    }

    public void setRowVchNo(String rowVchNo) {
        this.rowVchNo = rowVchNo;
    }

    public String getRowTdsUnRec() {
        return rowTdsUnRec;
    }

    public void setRowTdsUnRec(String rowTdsUnRec) {
        this.rowTdsUnRec = rowTdsUnRec;
    }

    public String getAccountFlag() {
        return accountFlag;
    }

    public void setAccountFlag(String accountFlag) {
        this.accountFlag = accountFlag;
    }

    public String getIntOpt() {
        return intOpt;
    }

    public void setIntOpt(String intOpt) {
        this.intOpt = intOpt;
    }

    public String getTmpCumuAmt() {
        return tmpCumuAmt;
    }

    public void setTmpCumuAmt(String tmpCumuAmt) {
        this.tmpCumuAmt = tmpCumuAmt;
    }

    public String getFullAcno() {
        return fullAcno;
    }

    public void setFullAcno(String fullAcno) {
        this.fullAcno = fullAcno;
    }

    public AcDetailGrid getCurrentItem1() {
        return currentItem1;
    }

    public void setCurrentItem1(AcDetailGrid currentItem1) {
        this.currentItem1 = currentItem1;
    }

    public int getCurrentRow1() {
        return currentRow1;
    }

    public void setCurrentRow1(int currentRow1) {
        this.currentRow1 = currentRow1;
    }

    public GridData getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(GridData currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getTdsAmt() {
        return tdsAmt;
    }

    public void setTdsAmt(String tdsAmt) {
        this.tdsAmt = tdsAmt;
    }

    public float getTotAmt() {
        return totAmt;
    }

    public void setTotAmt(float totAmt) {
        this.totAmt = totAmt;
    }

    public String getRecpNo() {
        return recpNo;
    }

    public void setRecpNo(String recpNo) {
        this.recpNo = recpNo;
    }

    public String getRtNo() {
        return rtNo;
    }

    public void setRtNo(String rtNo) {
        this.rtNo = rtNo;
    }

    public String getErMsg() {
        return erMsg;
    }

    public void setErMsg(String erMsg) {
        this.erMsg = erMsg;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getJointName() {
        return jointName;
    }

    public void setJointName(String jointName) {
        this.jointName = jointName;
    }

    public String getOpMode() {
        return opMode;
    }

    public void setOpMode(String opMode) {
        this.opMode = opMode;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public List<GridData> getGdl() {
        return gdl;
    }

    public void setGdl(List<GridData> gdl) {
        this.gdl = gdl;
    }

    public List<AcDetailGrid> getAcd() {
        return acd;
    }

    public void setAcd(List<AcDetailGrid> acd) {
        this.acd = acd;
    }

    
    public String getOldAcNo() {
        return oldAcNo;
    }

    public void setOldAcNo(String oldAcNo) {
        this.oldAcNo = oldAcNo;
    }

    public String getRtFlag() {
        return rtFlag;
    }

    public void setRtFlag(String rtFlag) {
        this.rtFlag = rtFlag;
    }

    public String getRtDisab() {
        return rtDisab;
    }

    public void setRtDisab(String rtDisab) {
        this.rtDisab = rtDisab;
    }    

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    public TdsEntryForSingle() {
        try {
            tdCalMgmtRemote = (TermDepositeCalculationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTdCal);
           // tdRcptMgmtRemote = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameTdRcpt);
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            otherReport = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameOtherReport);
            rbiReport = (RbiReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameRbiReport);            
            this.setAcNoLen(getAcNoLength());
            //getAcType();
            gdl = new ArrayList<GridData>();
            this.setAccountFlag("0");
            this.setRtFlag("0");
            this.setRtDisab("true");
            rowAcno = "R.T.No.";
            rowVchNo = "Receipt No";
            rowTdsUnRec = "Prin Amt";
        } catch (ApplicationException ex) {
            this.setErMsg(ex.getMessage());
        } catch (Exception ex) {
            this.setErMsg(ex.getMessage());
        }
    }

    public void getAccDetail() {
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        if (this.oldAcNo.equalsIgnoreCase("") || this.oldAcNo.length() == 0) {
            this.setAccountFlag("1");
            this.setErMsg("Please Enter Account Number.");
            return;
        }
        if (!this.oldAcNo.equalsIgnoreCase("") && ((this.oldAcNo.length() != 12) && (this.oldAcNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
            this.setAccountFlag("1");
            this.setErMsg("Please Enter Account Number.");
            return;
        }
        
        this.setAccountFlag("0");
        this.setRtFlag("0");
        this.setRtDisab("true");
        try {
            rowAcno = "R.T.No.";
            rowVchNo = "Receipt No";
            rowTdsUnRec = "Prin Amt";
            fullAcno = ftsPostRemote.getNewAccountNumber(oldAcNo);
            String checkBrnCode = ftsPostRemote.getCurrentBrnCode(fullAcno);
            if (!checkBrnCode.equals(getOrgnBrCode())) {
                this.setErMsg("Sorry! Other branch account no. is not allowed to proceed.");
                return;
            }
            List result = tdCalMgmtRemote.getAcctDetail(fullAcno, getOrgnBrCode());
            if (result.isEmpty()) {
                this.setErMsg("Please check the Account Status");
                reSetForm();
                return;
            } else {
                for (int i = 0; i < result.size(); i++) {
                    Vector element = (Vector) result.get(i);
                    setCustName(element.get(0).toString());
                    String jtName1 = (element.get(1).toString());
                    String jtName2 = (element.get(2).toString());
                    String totalJointName = jtName1 + jtName2;
                    setJointName(totalJointName);
                    setOpMode(element.get(3).toString());
                    this.setErMsg("");
                }
            }
            acd = new ArrayList<AcDetailGrid>();
            String acNat = ftsPostRemote.getAccountNature(fullAcno);
            if(acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)){
                this.setRtFlag("1");
                this.setRtDisab("false");
                result = tdCalMgmtRemote.getTableDetailForTdSingleEntry(fullAcno, getOrgnBrCode());
                if (!result.isEmpty()) {
                    for (int j = 0; j < result.size(); j++) {
                        actblDetail = new AcDetailGrid();
                        Vector element = (Vector) result.get(j);
                        actblDetail.setRtNo(element.get(1).toString());
                        actblDetail.setReceiptNo(element.get(2).toString());
                        actblDetail.setCtrlNo(element.get(10).toString());
                        actblDetail.setPrintAmt(Double.parseDouble(element.get(3).toString()));
                        actblDetail.setTdMDate(element.get(6).toString());
                        actblDetail.setTdDate(element.get(4).toString());
                        actblDetail.setModuDate(element.get(5).toString());
                        actblDetail.setIntOpt(element.get(7).toString());
                        actblDetail.setRoi(element.get(8).toString());
                        actblDetail.setStatus(element.get(9).toString());
                        acd.add(actblDetail);
                    }
                } else {
                    this.setErMsg("No Voucher Exist In This Account No");
                    this.setAccountFlag("1");
                    this.setRtFlag("0");
                    this.setRtDisab("true");
                }
            }
        } catch (ApplicationException ex) {
            this.setErMsg(ex.getMessage());
        } catch (Exception ex) {
            this.setErMsg(ex.getMessage());
        }
    }
    NumberFormat formatter = new DecimalFormat("#0.00");
    public void loadGrid() {
        this.setErMsg("");
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        if (this.tdsAmt.equalsIgnoreCase("") || this.tdsAmt.length() == 0) {
            this.setErMsg("Please Enter Tds Amount.");
            return;
        }
        Matcher tdsAmtCheck = p.matcher(tdsAmt);
        if (!tdsAmtCheck.matches()) {
            this.setErMsg("Please Enter Valid  Tds Amount.");
            return;
        }
        
        try {
            if(gdl.size()>0){
                for(int l = 0; l < gdl.size(); l++) {
                    if(gdl.get(l).getAcNo().equalsIgnoreCase(fullAcno) && gdl.get(l).getRecptNo().equalsIgnoreCase(recpNo.equalsIgnoreCase("0")?"":recpNo) ){
                        this.setErMsg("account is already selected.");
                        return;
                    }
                }
            }
            String acNat = ftsPostRemote.getAccountNature(fullAcno);
            if(acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)){
                if (this.rtNo.equalsIgnoreCase("") || this.rtNo.length() == 0) {
                    this.setErMsg("Please Enter Rt Number.");
                    return;
                }
                Matcher rtNoCheck = p.matcher(rtNo);
                if (!rtNoCheck.matches()) {
                    this.setErMsg("Please Enter Valid  Rt Number.");
                    return;
                }
                
                String fieldDisplay = tdCalMgmtRemote.displayField(fullAcno, rtNo, getOrgnBrCode());
                if (fieldDisplay.equals("This Receipt No. Does Not Exist")) {
                    this.setErMsg("Please Enter Correct R.T. No.");
                    return;
                } else if (fieldDisplay.equals("This Receipt Is Closed And Amount Is Paid")) {
                    this.setErMsg(fieldDisplay);
                    return;
                } else {
                    String[] values = null;
                    String spliter = ": ";
                    values = fieldDisplay.split(spliter);
                    String receiptNo = values[0];
                    String cumuAmount = values[1];
                    String intOt = values[2];
                    setRecpNo(receiptNo);
                    setTmpCumuAmt(cumuAmount);
                    setIntOpt(intOt);
                }
                rowAcno = "R.T.No.";
                rowVchNo = "Receipt No";
                rowTdsUnRec = "Prin Amt";
                List resultData = new ArrayList();
                String check = tdCalMgmtRemote.cmdLoadClick(fullAcno, intOpt, Float.parseFloat(tdsAmt), getUserName(), getOrgnBrCode());
                if (check.equals("True")) {
                    resultData = tdCalMgmtRemote.getReceiptNo(fullAcno, rtNo, getOrgnBrCode());
                    for (int j = 0; j < resultData.size(); j++) {
                        gd = new GridData();
                        Vector element = (Vector) resultData.get(j);
                        String s = element.get(2).toString();
                        double l1 = Double.parseDouble(this.tdsAmt);
                        double l2 = Double.parseDouble(element.get(25).toString());
                        if (l1 > l2) {
                            this.setErMsg("Insufficient Fund");
                            return;
                        } else {
                            gd.setRecptNo(element.get(8).toString());
                            gd.setAcNo(element.get(0).toString());
                            gd.setIntOpt(element.get(4).toString());
                            gd.setTdsAmt(formatter.format(Double.parseDouble(tdsAmt)));
                            gd.setRtNo(rtNo);
                            gdl.add(gd);
                            this.totAmt = this.totAmt + Float.parseFloat(gd.getTdsAmt());
                            this.setErMsg("");
                            acd = new ArrayList<AcDetailGrid>();
                            this.setCustName("");
                            this.setJointName("");
                            this.setOpMode("");
                            this.setRecpNo("");
                            this.setRtNo("");
                            this.setTdsAmt("");
                        }
                    }
                } else {
                    this.setErMsg(check);
                }
            }else if(acNat.equalsIgnoreCase(CbsConstant.DEPOSIT_SC) || acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)){
                gd = new GridData();
                String chkBalance = ftsPostRemote.checkBalance(fullAcno, Double.parseDouble(this.tdsAmt), getUserName());
                if (!(chkBalance.equalsIgnoreCase("True"))) {
                    this.setErMsg("For " + chkBalance);
                    return;
                }
                gd.setRecptNo("");
                gd.setAcNo(fullAcno);
                gd.setIntOpt("");
                gd.setTdsAmt(formatter.format(Double.parseDouble(tdsAmt)));
                gd.setRtNo("");
                gdl.add(gd);
                this.totAmt = this.totAmt + Float.parseFloat(gd.getTdsAmt());
                this.setErMsg("");
                acd = new ArrayList<AcDetailGrid>();
                this.setCustName("");
                this.setJointName("");
                this.setOpMode("");
                this.setRecpNo("");
                this.setRtNo("");
                this.setTdsAmt("");                
            }
        } catch (ApplicationException ex) {
            this.setErMsg(ex.getMessage());
        } catch (Exception ex) {
            this.setErMsg(ex.getMessage());
        }
    }

    public void loadGridUnSec() {
        this.setErMsg("");
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
//        if (this.tdsAmt.equalsIgnoreCase("") || this.tdsAmt.length() == 0) {
//            this.setErMsg("Please Enter Tds Amount.");
//            return;
//        }
//        Matcher tdsAmtCheck = p.matcher(tdsAmt);
//        if (!tdsAmtCheck.matches()) {
//            this.setErMsg("Please Enter Valid  Tds Amount.");
//            return;
//        }
        rowAcno = "Account";
        rowVchNo = "Receipt No";
        rowTdsUnRec = "Tds Amount";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        List<TdReceiptIntDetailPojo> reportList = new ArrayList<TdReceiptIntDetailPojo>();
        acd = new ArrayList<AcDetailGrid>();
        try {
//            getTdReceiptIntData(String allAcNo, String custId, String frDt, String toDt, String receiptNo, String brCode, String acType, String tdsOption, String summaryOpt, String recFrDt, String recToDt, String actCatgory)
            String finStartDt = rbiReport.getMinFinYear(sdf.format(new Date()));
            reportList = otherReport.getTdReceiptIntData("C","",finStartDt,sdf.format(new Date()), "ALL", getOrgnBrCode(), "S", "UR", "D", finStartDt,sdf.format(new Date()), "A",-99999999d,9999999999d);
            if(!reportList.isEmpty()){
                for (int j = 0; j < reportList.size(); j++) {
                    actblDetail = new AcDetailGrid();
                    actblDetail.setRtNo(reportList.get(j).getAcNo());
                    actblDetail.setReceiptNo(reportList.get(j).getReceiptNo());
                    actblDetail.setCtrlNo("0");
                    actblDetail.setPrintAmt(reportList.get(j).getTds());
                    actblDetail.setTdMDate("");
                    actblDetail.setTdDate("");
                    actblDetail.setModuDate("");
                    actblDetail.setIntOpt("");
                    actblDetail.setRoi("0");
                    actblDetail.setStatus(reportList.get(j).getRecoveredFlag());
                    if(reportList.get(j).getRecoveredFlag().equals("UnRecover")){
                        acd.add(actblDetail);
                    }
                }
            } else {
                this.setErMsg("Un-Recovered TDS doesn't exist.");
                return;
            }
        } catch (ApplicationException ex) {
            ex.printStackTrace();
            this.setErMsg(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            this.setErMsg(ex.getMessage());
        }
    }

    public void setRowValues() {
        this.setErMsg("");
        try {
            String fieldDisplay = "";
            if(currentItem1.getRtNo().length()==12){
                    setOldAcNo(currentItem1.getRtNo());
                    getAccDetail();
//                    setFullAcno(currentItem1.getRtNo());
//                    setRtNo("");
                    setRecpNo(currentItem1.getReceiptNo());
//                    String[] values = null;
//                    String spliter = ": ";
//                    values = fieldDisplay.split(spliter);
//                    String cumuAmount = values[1];
//                    String intOt = values[2];
                    setTdsAmt(String.valueOf(currentItem1.getPrintAmt()));
//                    setIntOpt(intOt);
            } else {
                fieldDisplay = tdCalMgmtRemote.displayField(fullAcno, currentItem1.getRtNo(), getOrgnBrCode());
                if (fieldDisplay.equals("This Receipt No. Does Not Exist")) {
                    this.setErMsg("Please Enter Correct R.T. No.");
                    reSetForm();
                } else if (fieldDisplay.equals("This Receipt Is Closed And Amount Is Paid")) {
                    this.setErMsg(fieldDisplay);
                    reSetForm();
                } else {
                    setRtNo(currentItem1.getRtNo());
                    setRecpNo(currentItem1.getReceiptNo());
                    String[] values = null;
                    String spliter = ": ";
                    values = fieldDisplay.split(spliter);
                    String cumuAmount = values[1];
                    String intOt = values[2];
                    setTmpCumuAmt(cumuAmount);
                    setIntOpt(intOt);
                }
            }
        } catch (ApplicationException ex) {
            this.setErMsg(ex.getMessage());
        } catch (Exception ex) {
            this.setErMsg(ex.getMessage());
        }
    }

    public void checkTdsAmount() {
        this.setErMsg("");
        Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
        if (this.tdsAmt.equalsIgnoreCase("") || this.tdsAmt.length() == 0) {
            this.setErMsg("Please Enter Tds Amount.");
            return;
        }
        Matcher tdsAmtCheck = p.matcher(tdsAmt);
        if (!tdsAmtCheck.matches()) {
            this.setErMsg("Please Enter Valid  Tds Amount.");
            return;
        }
        try {
           String acNat = ftsPostRemote.getAccountNature(fullAcno);
           if(acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)){
               if(rtNo.equalsIgnoreCase("")){
                   this.setErMsg("Please Select R.T.No From Grid.");
                   return;
               }
               String fieldDisplay = tdCalMgmtRemote.displayField(fullAcno, rtNo, getOrgnBrCode());
                if (fieldDisplay.equals("This Receipt No. Does Not Exist")) {
                    this.setErMsg("Please Enter Correct R.T. No.");
                    return;
                } else if (fieldDisplay.equals("This Receipt Is Closed And Amount Is Paid")) {
                    this.setErMsg(fieldDisplay);
                    return;
                } else {
                    String[] values = null;
                    String spliter = ": ";
                    values = fieldDisplay.split(spliter);
                    String receiptNo = values[0];
                    String cumuAmount = values[1];
                    String intOt = values[2];
                    setRecpNo(receiptNo);
                    setTmpCumuAmt(cumuAmount);
                    setIntOpt(intOt);
                }
                if (Float.parseFloat(tdsAmt) > Float.parseFloat(tmpCumuAmt)) {
                    this.setErMsg("Insufficient Fund");
                    return;
                }
           } else if(acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC) || acNat.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)){
               String chkBalance = ftsPostRemote.checkBalance(fullAcno, Double.parseDouble(this.tdsAmt), getUserName());
               if (!(chkBalance.equalsIgnoreCase("True"))) {
                    this.setErMsg("For " + chkBalance);
                    return;
               }
           }        
        } catch (ApplicationException ex) {
            this.setErMsg(ex.getMessage());
        } catch (Exception ex) {
            this.setErMsg(ex.getMessage());
        }
    }

    public void toPost() {
        this.setErMsg("");
        try {
            String checkBrnCode = ftsPostRemote.getCurrentBrnCode(fullAcno);
            if (!checkBrnCode.equals(getOrgnBrCode())) {
                this.setErMsg("Sorry! Other branch account no. is not allowed to proceed.");
                return;
            }
            String saveData = tdCalMgmtRemote.save(gdl, totAmt, getOrgnBrCode(), getUserName());
            this.setErMsg(saveData);
            reSetForm();
        } catch (ApplicationException ex) {
            this.setErMsg(ex.getMessage());
        } catch (Exception ex) {
            this.setErMsg(ex.getMessage());
        }
    }

    public void delete() {
        gdl.remove(currentRow);
        this.setErMsg("");
        List resultList = new ArrayList();
        String t[][] = new String[gdl.size()][5];
        for (int i = 0; i < gdl.size(); i++) {
            t[i][0] = gdl.get(i).getAcNo().toString();
            t[i][1] = gdl.get(i).getRecptNo().toString();
            t[i][2] = gdl.get(i).getTdsAmt().toString();
            t[i][3] = gdl.get(i).getRtNo().toString();
            t[i][4] = gdl.get(i).getIntOpt().toString();
        }
        for (int i = 0; i < gdl.size(); i++) {
            for (int j = 0; j < 5; j++) {
                Object combinedArray = t[i][j];
                resultList.add(combinedArray);
            }
        }
        float tdsA = 0;
        for (int a = 0, b = 1, c = 2, d = 3, e = 4; a < resultList.size(); a = a + 5, b = b + 5, c = c + 5, d = d + 5, e = e + 5) {
            float tdsAmount = Float.parseFloat(resultList.get(c).toString());
            tdsA = tdsA + tdsAmount;
        }
        setTotAmt(tdsA);
    }

    public void reSetForm() {
        this.setAccNo("");
        this.setOldAcNo("");
        this.setFullAcno("");
        this.setCustName("");
        this.setJointName("");
        this.setOpMode("");
        this.setRecpNo("");
        this.setRtNo("");
        this.setTdsAmt("");
        this.setTotAmt(0);
        this.setRtFlag("0");
        this.setRtDisab("true");
        gdl = new ArrayList<GridData>();
        acd = new ArrayList<AcDetailGrid>();
        rowAcno = "R.T.No.";
        rowVchNo = "Receipt No";
        rowTdsUnRec = "Prin Amt";
    }

    public void reSetForm1() {
        this.setErMsg("");
        this.setAccNo("");
        this.setOldAcNo("");
        this.setFullAcno("");
        this.setCustName("");
        this.setJointName("");
        this.setOpMode("");
        this.setRecpNo("");
        this.setRtNo("");
        this.setTdsAmt("");
        this.setTotAmt(0);
        this.setRtFlag("0");
        this.setRtDisab("true");
        gdl = new ArrayList<GridData>();
        acd = new ArrayList<AcDetailGrid>();
        rowAcno = "R.T.No.";
        rowVchNo = "Receipt No";
        rowTdsUnRec = "Prin Amt";
    }

    public String exitForm() {
        this.setErMsg("");
        reSetForm();
        return "case1";
    }
}
