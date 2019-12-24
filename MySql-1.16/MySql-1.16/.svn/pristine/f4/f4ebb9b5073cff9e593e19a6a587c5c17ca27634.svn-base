package com.cbs.web.controller.report.other;

import com.cbs.dto.report.AccountOpenIntroducerPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Zeeshan Waris
 */
public final class IntroducerCertificate extends BaseBean {

    private String message;
    Date calFromDate = new Date();
    Date caltoDate = new Date();
    OtherReportFacadeRemote beanRemote;
    private String accountNo, owner, selTp, acTp;
    private boolean fomDtDisable, toDtDisable, checkbox, acnoDisable, acTpDisable;
    private List<SelectItem> introducerList, selTpList, acTpList;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    private String newAcno,acNoLen;
    private final String FtsPostingMgmtFacadeJndiName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote fts = null;

    public String getNewAcno() {
        return newAcno;
    }

    public void setNewAcno(String newAcno) {
        this.newAcno = newAcno;
    }

    public boolean isAcnoDisable() {
        return acnoDisable;
    }

    public void setAcnoDisable(boolean acnoDisable) {
        this.acnoDisable = acnoDisable;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public boolean isFomDtDisable() {
        return fomDtDisable;
    }

    public void setFomDtDisable(boolean fomDtDisable) {
        this.fomDtDisable = fomDtDisable;
    }

    public List<SelectItem> getIntroducerList() {
        return introducerList;
    }

    public void setIntroducerList(List<SelectItem> introducerList) {
        this.introducerList = introducerList;
    }

    public boolean isToDtDisable() {
        return toDtDisable;
    }

    public void setToDtDisable(boolean toDtDisable) {
        this.toDtDisable = toDtDisable;
    }

    public Date getCalFromDate() {
        return calFromDate;
    }

    public void setCalFromDate(Date calFromDate) {
        this.calFromDate = calFromDate;
    }

    public Date getCaltoDate() {
        return caltoDate;
    }

    public void setCaltoDate(Date caltoDate) {
        this.caltoDate = caltoDate;
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

    public String getAcTp() {
        return acTp;
    }

    public void setAcTp(String acTp) {
        this.acTp = acTp;
    }

    public boolean isAcTpDisable() {
        return acTpDisable;
    }

    public void setAcTpDisable(boolean acTpDisable) {
        this.acTpDisable = acTpDisable;
    }

    public List<SelectItem> getAcTpList() {
        return acTpList;
    }

    public void setAcTpList(List<SelectItem> acTpList) {
        this.acTpList = acTpList;
    }

    public String getSelTp() {
        return selTp;
    }

    public void setSelTp(String selTp) {
        this.selTp = selTp;
    }

    public List<SelectItem> getSelTpList() {
        return selTpList;
    }

    public void setSelTpList(List<SelectItem> selTpList) {
        this.selTpList = selTpList;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    public IntroducerCertificate() {
        try {
            beanRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(FtsPostingMgmtFacadeJndiName);
            fomDtDisable = true;
            toDtDisable = true;
            introducerList = new ArrayList<SelectItem>();
            introducerList.add(new SelectItem("Owner of Account", "Owner of Account"));
            introducerList.add(new SelectItem("Introducer Of Account", "Introducer Of Account"));

            selTpList = new ArrayList<SelectItem>();
            selTpList.add(new SelectItem("AC", "Account No"));
            selTpList.add(new SelectItem("AT", "Account Type"));

            acTpList = new ArrayList<SelectItem>();
            acTpList.add(new SelectItem("S", "--Select--"));
            acTpList.add(new SelectItem("A", "ALL"));
            List acTypeList = beanRemote.getAcctTypeExceptGL();
            if (!acTypeList.isEmpty()) {
                for (int i = 0; i < acTypeList.size(); i++) {
                    Vector vec = (Vector) acTypeList.get(i);
                    acTpList.add(new SelectItem(vec.get(0).toString(), vec.get(1).toString()));
                }
            }

            this.setAcNoLen(getAcNoLength());
            this.setSelTp("AC");
            this.setAcTp("S");
            this.acTpDisable = true;

            setMessage("");
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String printAction() {
        setMessage("");
        try {
            if (calFromDate == null) {
                message = "Please Fill From Date";
                return null;
            }
            if (caltoDate == null) {
                message = "Please Fill From Date";
                return null;
            }
            if (calFromDate.after(caltoDate)) {
                this.setMessage("From Date can not be greater than To Date.");
                return null;
            }

            String actype = "";
            String acctNo = "";
            String brncode = "";
            String agCode = "";
            String dateFrom = ymdFormat.format(calFromDate);
            String dateTo = ymdFormat.format(caltoDate);
            String chbox1 = "";
            if (selTp.equalsIgnoreCase("AT")) {
                if (acTp.equalsIgnoreCase("S")) {
                    message = "Please Select Account Type";
                    return null;
                }
                chbox1 = this.getSelTp();
                actype = this.getAcTp();
                acctNo = "";
                brncode = getOrgnBrCode();
            } else {
                if (newAcno == null || newAcno.equalsIgnoreCase("")) {
                    message = "Please Fill The Account Number";
                    return null;

                }
                if (newAcno.length() < 12) {
                    message = "Account no should Be 12 digit Long";
                    return null;
                }
                actype = newAcno.substring(2, 4);
                acctNo = newAcno.substring(4, 10);
                brncode = fts.getCurrentBrnCode(newAcno);
                agCode = newAcno.substring(10, 12);
                chbox1 = this.getSelTp();
                dateFrom = "";
                dateTo = "";
            }
            List<AccountOpenIntroducerPojo> accountOpenIntroducer = beanRemote.accountOpenIntroducer(chbox1, owner, actype, acctNo, dateFrom, dateTo, brncode, agCode);
            System.out.println("accountOpenIntroducer" + accountOpenIntroducer.size());
            System.out.println("accountOpenIntroducer+" + accountOpenIntroducer);
            if (!accountOpenIntroducer.isEmpty()) {
                String owners = "";
                if (owner.equalsIgnoreCase("Owner of Account")) {
                    owners = "O";
                } else if (owner.equalsIgnoreCase("Introducer Of Account")) {
                    owners = "I";
                }
                String repName = "A/C opening Thanks Report";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", repName);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pAcType", owners);
                fillParams.put("pPrintedDate", dmyFormat.format(calFromDate) + " to " + dmyFormat.format(caltoDate));
                new ReportBean().jasperReport("ACCOUNT_OPEN_INTRODUCER_CERTIFICATE", "text/html", new JRBeanCollectionDataSource(accountOpenIntroducer), fillParams, repName);
                return "report";
            } else {
                setMessage("No data to print");
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
//        catch (Exception e) {
//            setMessage(e.getLocalizedMessage());
//            return null;
//        }
    }

    public void pdfDownLoad() {
        setMessage("");
        try {
            if (calFromDate == null) {
                message = "Please Fill From Date";
                return;
            }
            if (caltoDate == null) {
                message = "Please Fill From Date";
                return;
            }
            if (calFromDate.after(caltoDate)) {
                this.setMessage("From Date can not be greater than To Date.");
                return;
            }

            String actype = "";
            String acctNo = "";
            String brncode = "";
            String agCode = "";
            String dateFrom = ymdFormat.format(calFromDate);
            String dateTo = ymdFormat.format(caltoDate);
            String chbox1 = "";
            if (selTp.equalsIgnoreCase("AT")) {
                if (acTp.equalsIgnoreCase("S")) {
                    message = "Please Select Account Type";
                    return;
                }
                chbox1 = this.getSelTp();
                actype = this.getAcTp();
                acctNo = "";
                brncode = getOrgnBrCode();
            } else {
                if (newAcno == null || newAcno.equalsIgnoreCase("")) {
                    message = "Please Fill The Account Number";
                    return;

                }
                if (newAcno.length() < 12) {
                    message = "Account no should Be 12 digit Long";
                    return;
                }
                actype = newAcno.substring(2, 4);
                acctNo = newAcno.substring(4, 10);
                brncode = fts.getCurrentBrnCode(newAcno);
                agCode = newAcno.substring(10, 12);
                chbox1 = this.getSelTp();
                dateFrom = "";
                dateTo = "";
            }
            List<AccountOpenIntroducerPojo> accountOpenIntroducer = beanRemote.accountOpenIntroducer(chbox1, owner, actype, acctNo, dateFrom, dateTo, brncode, agCode);
            System.out.println("accountOpenIntroducer" + accountOpenIntroducer.size());
            System.out.println("accountOpenIntroducer+" + accountOpenIntroducer);
            if (!accountOpenIntroducer.isEmpty()) {
                String owners = "";
                if (owner.equalsIgnoreCase("Owner of Account")) {
                    owners = "O";
                } else if (owner.equalsIgnoreCase("Introducer Of Account")) {
                    owners = "I";
                }
                String repName = "A/C opening Thanks Report";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", repName);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pAcType", owners);
                fillParams.put("pPrintedDate", dmyFormat.format(calFromDate) + " to " + dmyFormat.format(caltoDate));

                new ReportBean().openPdf("Account opening Thanks Report", "ACCOUNT_OPEN_INTRODUCER_CERTIFICATE", new JRBeanCollectionDataSource(accountOpenIntroducer), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", repName);
            } else {
                setMessage("No data to print");
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void checkboxprocessValueChange() {
        try {
            if (selTp.equalsIgnoreCase("AC")) {
                this.setAcTp("S");
                fomDtDisable = true;
                toDtDisable = true;
                acnoDisable = false;
                acTpDisable = true;
            } else {
                fomDtDisable = false;
                toDtDisable = false;
                acnoDisable = true;
                acTpDisable = false;
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void refreshAction() {
        try {
            setMessage("");
            setAccountNo("");
            calFromDate = new Date();
            caltoDate = new Date();
            setNewAcno("");
            this.setSelTp("AC");
            this.setAcTp("S");
            acTpDisable = true;
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }

    public void getNewAccountNo() {
        try {
            if (accountNo == null || accountNo.equalsIgnoreCase("")) {
                message = "Please Fill The Account Number";
                return;
            }
            //if (accountNo.length() < 12) {
            if ((this.accountNo.length() != 12) && (this.accountNo.length() != (Integer.parseInt(this.getAcNoLen())))) {
                message = "Account no should Be Proper";
                return;
            }
            newAcno = fts.getNewAccountNumber(accountNo);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
}
