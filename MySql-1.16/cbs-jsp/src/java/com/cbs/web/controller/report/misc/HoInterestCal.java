/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.dto.report.HoIntCalPojo;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author ANKIT VERMA
 */
public class HoInterestCal extends BaseBean {

    private String msg;
    private Date date = new Date();
    private Date fromDate;
    private Date toDate;
    private String oldacno;
    private String acno,acNoLen;
    private String roi;
    private MiscReportFacadeRemote beanRemote = null;
    FtsPostingMgmtFacadeRemote fts;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    Validator val;
    private String accountName;
    private boolean saveDisable;

    public HoInterestCal() {
        try {
            fromDate = date;
            toDate = date;
            roi = "0.0";
            saveDisable = true;
            beanRemote = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            this.setAcNoLen(getAcNoLength());
            val = new Validator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getAccountName() {
        return accountName;
    }

    public String getOldacno() {
        return oldacno;
    }

    public void setOldacno(String oldacno) {
        this.oldacno = oldacno;
    }

    public boolean isSaveDisable() {
        return saveDisable;
    }

    public void setSaveDisable(boolean saveDisable) {
        this.saveDisable = saveDisable;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MiscReportFacadeRemote getBeanRemote() {
        return beanRemote;
    }

    public void setBeanRemote(MiscReportFacadeRemote beanRemote) {
        this.beanRemote = beanRemote;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    public void onBlurOfAcno() {
        try {
            msg="";
            accountName="";
            if (oldacno.equalsIgnoreCase("")) {
                msg = "Please insert account no. !";
                return;
            //} else if (oldacno.length() < 12) {
            } else if (!this.oldacno.equalsIgnoreCase("") && ((this.oldacno.length() != 12) && (this.oldacno.length() != (Integer.parseInt(this.getAcNoLen()))))) {   
                msg = "Please insert valid account no. !";
                return;
            } else if (!val.validateStringAllNoSpace(oldacno)) {
                msg = "Please insert valid account no. !";
                return;
            }
            acno=fts.getNewAccountNumber(oldacno);
            if (!getOrgnBrCode().equalsIgnoreCase("90")) {
                if (!fts.getCurrentBrnCode(acno).equalsIgnoreCase(getOrgnBrCode())) {
                    msg = "This is not your branch's account no. !";
                    return;
                }
            } 
            if (!fts.getAccountCode(acno).equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
                msg="Pleae insert 'GL' account only !";
                return;
            }
            accountName = beanRemote.checkAcno(acno);
            saveDisable = false;
        } catch (Exception e) {
            e.printStackTrace();
            setMsg(e.getLocalizedMessage());
        }
    }

    public void viewReport() {
        try {
            msg = validation();
            if (msg.equalsIgnoreCase("ok")) {
                List<HoIntCalPojo> hoInterst = beanRemote.getHoInterst(acno, ymd.format(fromDate), ymd.format(toDate), Double.parseDouble(roi));
                if (!hoInterst.isEmpty()) {
                    CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                    List branchNameandAddress = common.getBranchNameandAddress(getOrgnBrCode());
                    String report = "Int. Calculation (HO)";
                    Map fillParams = new HashMap();
                    fillParams.put("pReportDate", sdf.format(fromDate) + " to " + sdf.format(toDate));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", report);
                    fillParams.put("pBankName", branchNameandAddress.get(0));
                    fillParams.put("pBankAdd", branchNameandAddress.get(1));
                    fillParams.put("accountName", accountName);
                    new ReportBean().jasperReport("HoInterestCal", "text/html",
                            new JRBeanCollectionDataSource(hoInterst), fillParams, report);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    refreshForm();
                  } else {
                    setMsg("No detail exists !");
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            setMsg(e.getLocalizedMessage());
        }
    }

    public void refreshForm() {
        fromDate = date;
        toDate = date;
        roi = "0.0";
        acno = "";
        oldacno="";
        msg = "";
        saveDisable = true;
        accountName="";
    }

    public String exitAction() {
        refreshForm();
        return "case1";
    }

    public String validation() {
        if (acno.equalsIgnoreCase("")) {
            return "Please insert account no. !";
        } else if (acno.length() < 12) {
            return "Please insert valid account no. !";
        } else if (!val.validateStringAllNoSpace(acno)) {
            return "Please insert valid account no. !";
        } else if (roi.equalsIgnoreCase("")) {
            return "Please insert roi !";
        } else if (!val.validateDoublePositive(roi)) {
            return "Please insert valid roi !";
        } else if (fromDate == null) {
            return "Please enter from date !";
        } else if (toDate == null) {
            return "Please enter to date !";
        } else if (!Validator.validateDate(sdf.format(fromDate))) {
            return "Please enter valid from date !";
        } else if (!Validator.validateDate(sdf.format(toDate))) {
            return "Please enter valid to date !";
        }
        else if(fromDate.compareTo(toDate)>0)
        {
            return "'From date' should be less than 'To date' !";
        }
        return "ok";
    }
}
