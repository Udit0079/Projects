/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountStatusSecurityFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.web.pojo.admin.AcStatus;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

public class AccountStatus extends BaseBean {

    private String message;
    private List<SelectItem> statusList;
    private String acno;
    private String remarks;
    private String name;
    private String pStatus;
    private String pstatusCode;
    private String nStatus;
    private Date wefDate;
    private Date reportDate;
    private List<AcStatus> incis;
    private String lienAmt;
    private String liencode;
    private String oldLiencode;
    private String flag;
    private String fflag = "false";
    private String oldAcno, acNoLen;
    Validator validator;
    private String openingDt;
    SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    AccountStatusSecurityFacadeRemote statusMaintenanceFacade;
    FtsPostingMgmtFacadeRemote ftsPostRemote;

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }

    public String getOldLiencode() {
        return oldLiencode;
    }

    public void setOldLiencode(String oldLiencode) {
        this.oldLiencode = oldLiencode;
    }

    public String getFflag() {
        return fflag;
    }

    public void setFflag(String fflag) {
        this.fflag = fflag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getLiencode() {
        return liencode;
    }

    public void setLiencode(String liencode) {
        this.liencode = liencode;
    }

    public String getLienAmt() {
        return lienAmt;
    }

    public void setLienAmt(String lienAmt) {
        this.lienAmt = lienAmt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpStatus() {
        return pStatus;
    }

    public void setpStatus(String pStatus) {
        this.pStatus = pStatus;
    }

    public String getnStatus() {
        return nStatus;
    }

    public void setnStatus(String nStatus) {
        this.nStatus = nStatus;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Date getWefDate() {
        return wefDate;
    }

    public void setWefDate(Date wefDate) {
        this.wefDate = wefDate;
    }

    public List<AcStatus> getIncis() {
        return incis;
    }

    public void setIncis(List<AcStatus> incis) {
        this.incis = incis;
    }

    public String getOldAcno() {
        return oldAcno;
    }

    public void setOldAcno(String oldAcno) {
        this.oldAcno = oldAcno;
    }

    public String getOpeningDt() {
        return openingDt;
    }

    public void setOpeningDt(String openingDt) {
        this.openingDt = openingDt;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    /**
     * Creates a new instance of AccountStatus
     */
    public AccountStatus() {
        try {
            statusMaintenanceFacade = (AccountStatusSecurityFacadeRemote) ServiceLocator.getInstance().lookup("AccountStatusSecurityFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            this.setAcNoLen(getAcNoLength());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            getAllStatusList();
            setReportDate(sdf.parse(getTodayDate()));
            setWefDate(sdf.parse(getTodayDate()));
            validator = new Validator();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void getAllStatusList() {
        try {
            List resultList = statusMaintenanceFacade.getAllStatusList();
            statusList = new ArrayList<SelectItem>();
            statusList.add(new SelectItem(0, "--SELECT--"));
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                statusList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void custName() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            incis = new ArrayList<AcStatus>();
            setName("");
            setpStatus("");
            setRemarks("");
            setnStatus("0");
            pstatusCode = "0";
            acno = "";;
            setReportDate(sdf.parse(getTodayDate()));
            setWefDate(sdf.parse(getTodayDate()));
            lienAmt = "0";
            setMessage("");
            if ((oldAcno.equals("")) || (oldAcno == null)) {
                this.setMessage("Please Enter Account Number");
                setName("");
                setpStatus("");
                pstatusCode = "0";
                incis.clear();
                return;
            }
            if (!validator.validateStringAllNoSpace(oldAcno) || (((this.oldAcno.length() != 12) && (this.oldAcno.length() != (Integer.parseInt(this.getAcNoLen())))))) {
                this.setMessage("Please Enter Valid Account Number.");
                this.setOldAcno("");
                setName("");
                setpStatus("");
                pstatusCode = "0";
                incis.clear();
                return;
            }

            acno = ftsPostRemote.getNewAccountNumber(oldAcno);
            String acctype = ftsPostRemote.getAccountCode(acno);
            String acNat = ftsPostRemote.getAcNatureByCode(acctype);
            List list = statusMaintenanceFacade.getCustNameAndStatus(acno, acctype);
            if (list.isEmpty()) {
                reFresh1();
                throw new ApplicationException("Data does not exists");
            }
            fflag = "true";
            Vector values = (Vector) list.get(0);
            this.setName(values.get(0).toString());
            pstatusCode = values.get(1).toString();
            this.setpStatus(values.get(2).toString());
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                openingDt = ymd.format(ymdhms.parse(values.get(3).toString()));
            } else {
                openingDt = values.get(3).toString();
            }

            setMessage("");
            griddata(acno);
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void griddata(String acno) {
        incis = new ArrayList<AcStatus>();
        try {
            List list = statusMaintenanceFacade.getStatusHistory(acno);
            for (int i = 0; i < list.size(); i++) {
                Vector v = (Vector) list.get(i);
                AcStatus tab = new AcStatus();
                tab.setAcctNo(v.get(0).toString());
                tab.setRemark(v.get(1).toString());
                tab.setSpFlag(v.get(2).toString());
                tab.setDate(v.get(3).toString());
                tab.setAmount(v.get(4).toString());
                tab.setAuth(v.get(5).toString());
                tab.setEnterBy(v.get(6).toString());
                tab.setEfftDate(v.get(7).toString());
                incis.add(tab);
            }
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void isLienMarked() {
        if (nStatus.equals("10")) {
            flag = "true";
            return;
        } else {
            flag = "false";
            lienAmt = "0";
        }
    }

    public void save() {
        try {
            setMessage("");
            if (wefDate == null) {
                setMessage("Please Enter The W.E.F.Date ");
                return;
            }
            if ((oldAcno.equals("")) || (oldAcno == null)) {
                this.setMessage("Please Enter Account Number");
                setName("");
                setpStatus("");
                incis.clear();
                return;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            if (sdf.parse(getTodayDate()).compareTo(wefDate) < 0) {
                this.setMessage("WEF Date Cannot Be Greater than Today Date.");
                setWefDate(sdf.parse(getTodayDate()));
                return;
            }
            if (sdf.parse(getTodayDate()).compareTo(reportDate) < 0) {
                this.setMessage("Report Date Cannot Be Greater than Today Date.");
                setReportDate(sdf.parse(getTodayDate()));
                return;
            }
            //if (!validator.validateStringAllNoSpace(acno) || acno.length() < 12) {
            if (!validator.validateStringAllNoSpace(oldAcno) || (((this.oldAcno.length() != 12) && (this.oldAcno.length() != (Integer.parseInt(this.getAcNoLen())))))) {
                this.setMessage("Please Enter Valid Account Number.");
                this.setOldAcno(oldAcno);
                setName("");
                setpStatus("");
                incis.clear();
                return;
            }
            if (this.nStatus.equalsIgnoreCase("0")) {
                this.setMessage("Please Select New Status");
                setName("");
                setpStatus("");
                incis.clear();
                return;
            }
            if (nStatus.equals("10")) {
                if (oldLiencode.equalsIgnoreCase("")) {
                    setMessage("Please insert Lien Account No.");
                    return;
                //} else if (!validator.validateStringAllNoSpace(oldLiencode) || oldLiencode.length() < 12) {
                } else if (!validator.validateStringAllNoSpace(oldLiencode) || (((this.oldLiencode.length() != 12) && (this.oldLiencode.length() != (Integer.parseInt(this.getAcNoLen())))))) {
                    setMessage("Please insert a valid Lien Account No.");
                    return;
                }
                if (liencode.equalsIgnoreCase("")) {
                    setMessage("Please insert Lien Account No.");
                    return;
                }
                if (this.remarks == null || this.remarks.equalsIgnoreCase("")) {
                    this.remarks = "Lien Marked Against : " + liencode;
                }
                if ((this.getLienAmt().equals("0") || this.getLienAmt().equals("0.0"))) {
                    setMessage("Please insert valid Lien Amount");
                    return;
                }

                String actNat = ftsPostRemote.getAccountNature(liencode);
                if (actNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                    if (!acno.equalsIgnoreCase(oldLiencode)) {
                        setMessage("Please Enter Same Account Numer In Case Of Saving");
                        return;
                    }
                } else {
                    if (acno.equalsIgnoreCase(oldLiencode)) {
                        setMessage("Please Enter Different Account");
                        return;
                    }
                }
            }

            String s1 = onblurDate(wefDate);
            if (!s1.equalsIgnoreCase("true")) {
                this.setMessage(s1);
                return;
            } else {
                this.setMessage("");
            }
            String s2 = onblurDate(reportDate);
            if (!s2.equalsIgnoreCase("true")) {
                this.setMessage(s2);
                return;
            } else {
                this.setMessage("");
            }

            if (pstatusCode.equals(nStatus)) {
                setMessage("Current Status and New Status Cannot be Same!");
                return;
            } else if (!ftsPostRemote.getCurrentBrnCode(acno).equalsIgnoreCase(getOrgnBrCode())) {
                setMessage("This is not your branch's account no.");
                return;
            } else {
                if (this.pstatusCode.equals("15") || this.nStatus.equals("15")) {
                    setMessage("Deaf processing is not allowed from here.");
                    return;
                }
                if (this.nStatus.equalsIgnoreCase("15")) {
                    this.setMessage("Deaf Marking is not allowed");
                    setName("");
                    setpStatus("");
                    incis.clear();
                    return;
                }

                if (ymd.parse(openingDt).after(wefDate)) {
                    this.setMessage("W.E.F Date should not be less than A/c Opening Date !");
                    return;
                }

                String tempAcno = acno;
                String rs = statusMaintenanceFacade.cbsSaveAcctStatus(acno, remarks, getUserName(), nStatus, ymd.format(wefDate),
                        Float.parseFloat(lienAmt), liencode);
                if (rs.equalsIgnoreCase("Status Changed Successfully")) {
                    setMessage(rs);
                    reFresh1();
                } else {
                    setMessage(rs);
                }
                griddata(tempAcno);
            }
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void reFresh() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            incis = new ArrayList<AcStatus>();
            setMessage("");
            setName("");
            setpStatus("");
            pstatusCode = "0";
            setOldAcno("");
            setRemarks("");
            setnStatus("0");
            liencode = "";
            acno = "";
            setLienAmt("");
            setOldLiencode("");
            fflag = "false";
            flag = "false";
            setAcno("");
            setReportDate(sdf.parse(getTodayDate()));
            setWefDate(sdf.parse(getTodayDate()));
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void reFresh1() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            incis = new ArrayList<AcStatus>();
            setName("");
            setpStatus("");
            setOldAcno("");
            setRemarks("");
            setnStatus("0");
            pstatusCode = "0";
            liencode = "";
            acno = "";
            setLienAmt("");
            setOldLiencode("");
            fflag = "false";
            flag = "false";
            setReportDate(sdf.parse(getTodayDate()));
            setWefDate(sdf.parse(getTodayDate()));
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void onblurWefDate() {
        try {
            if (wefDate == null) {
                setMessage("Please Enter The W.E.F.Date ");
                return;
            } else {
                onblurDate(this.wefDate);
            }
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
    }

    public void onblurReportDate() {
        try {
            if (reportDate == null) {
                setMessage("Please Enter The Report Date ");
                return;
            } else {
                onblurDate(this.reportDate);
            }
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }

    }

    public String onblurDate(Date dt) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String msg1 = "";
        try {
            List dateDif = statusMaintenanceFacade.dateDiffWefDate(sdf.format(dt));
            Vector vecDateDiff = (Vector) dateDif.get(0);
            String strDateDiff = vecDateDiff.get(0).toString();
            if (Integer.parseInt(strDateDiff) < 0) {
                this.setMessage("Date Must Be Less Than Current Date Or Equal To Current Date.");
                return msg1 = "Date Must Be Less Than Current Date Or Equal To Current Date.";
            } else {
                this.setMessage("");
                return msg1 = "true";
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return msg1;
    }

    public String exitBtnAction() {
        reFresh();
        return "case1";
    }

    public void getNewLineAcNo() {
        try {
            setMessage("");
            if (oldLiencode.equalsIgnoreCase("") || oldLiencode == null) {
                setMessage("Please insert lien account no.");
                return;
            //} else if (!validator.validateString(oldLiencode) || oldLiencode.length() < 12) {
            } else if (!validator.validateStringAllNoSpace(oldLiencode) || (((this.oldLiencode.length() != 12) && (this.oldLiencode.length() != (Integer.parseInt(this.getAcNoLen())))))) {
                setMessage("Please insert a valid line account no.");
                return;
            }
            liencode = ftsPostRemote.getNewAccountNumber(oldLiencode);
            String actNat = ftsPostRemote.getAccountNature(liencode);
            String[] actNatArr = {CbsConstant.CURRENT_AC, CbsConstant.DEMAND_LOAN, CbsConstant.TERM_LOAN, CbsConstant.SAVING_AC};
            int flag = 0;
            for (int i = 0; i < actNatArr.length; i++) {
                if ((!actNatArr[i].equalsIgnoreCase(actNat))) {
                    flag = 1;
                } else {
                    flag = 0;
                    break;
                }
            }
            if (flag == 0) {
                if (actNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                    if (!acno.equalsIgnoreCase(oldLiencode)) {
                        setMessage("Please Enter Same Account Numer In Case Of Saving");
                        return;
                    }
                } else {
                    if (acno.equalsIgnoreCase(oldLiencode)) {
                        setMessage("Please Enter Different Account");
                        return;
                    }
                }
            }
            if (flag == 1) {
                setMessage("Please insert CA,CC,OD,DL,TL account only");
                return;
            }

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
}
