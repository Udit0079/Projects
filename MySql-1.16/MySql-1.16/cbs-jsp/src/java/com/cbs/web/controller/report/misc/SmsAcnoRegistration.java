/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.pojo.SmsAcnoRegistrationPojo;
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
 * @author Admin
 */
public class SmsAcnoRegistration extends BaseBean {

    private String message;
    Date calFromDate = new Date();
    Date caltoDate = new Date();
    MiscReportFacadeRemote beanRemote;
    private String acType;
    private List<SelectItem> acTypeList;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String repType;
    private List<SelectItem> repTypeList;
    private CommonReportMethodsRemote RemoteCode;

    public SmsAcnoRegistration() {
        try {
            beanRemote = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            RemoteCode = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            acTypeList = new ArrayList<SelectItem>();
            List listAccTy = beanRemote.getAllAccounType();
            Vector vtr = new Vector();
            if (!listAccTy.isEmpty()) {
                for (int i = 0; i < listAccTy.size(); i++) {
                    vtr = (Vector) listAccTy.get(i);
                    acTypeList.add(new SelectItem(vtr.get(0).toString(), vtr.get(0).toString()));
                }
                acTypeList.add(new SelectItem("ALL", "ALL"));
            }

            List brncode = new ArrayList();
            brncode = RemoteCode.getAlphacodeAllAndBranch(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(1).toString().length() < 2 ? "0" + brnVector.get(1).toString() : brnVector.get(1).toString(), brnVector.get(0).toString()));
                }
            }

            repTypeList = new ArrayList<SelectItem>();
            repTypeList.add(new SelectItem("S", "--Select--"));
            repTypeList.add(new SelectItem("R", "Registered"));
            repTypeList.add(new SelectItem("U", "Unregistered"));

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public String printAction() {
        setMessage("");
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            if (calFromDate == null) {
                message = "Please Fill From Date";
                return null;
            }
            if (caltoDate == null) {
                message = "Please Fill From Date";
                return null;
            }

            if (acType.equalsIgnoreCase("0")) {
                message = "Please select A/c Type";
                return null;
            }

            if (repType.equalsIgnoreCase("S")) {
                message = "Please select Report Type";
                return null;

            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String FromDt = sdf.format(calFromDate);
            String ToDate = sdf.format(caltoDate);
            String duration = FromDt + "  TO  " + ToDate;
            List<SmsAcnoRegistrationPojo> repportList = beanRemote.smsAcnoRegister(branchCode, acType, ymdFormat.format(calFromDate), ymdFormat.format(caltoDate), repType);
            if (!repportList.isEmpty()) {
                List list = RemoteCode.getBranchNameandAddress(branchCode);
                String repName = "";
                if (repName.equalsIgnoreCase("R")) {
                    repName = "SMS ACCOUNT REGISTER";
                } else {
                    repName = "SMS ACCOUNT UNREGISTER";
                }
                Map fillParams = new HashMap();
                fillParams.put("pReportName", repName);
                fillParams.put("pPrintedDate", duration);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pBranch", list.get(0).toString());
                fillParams.put("pName", list.get(0).toString());
                fillParams.put("pReptype", repType);


                new ReportBean().jasperReport("sms_registration_drtail", "text/html", new JRBeanCollectionDataSource(repportList), fillParams, "SMS ACCOUNT REGISTER");
                return "report";
            } else {
                setMessage("No data to print");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public String pdfDownLoad() {
        setMessage("");
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            if (calFromDate == null) {
                message = "Please Fill From Date";
                return null;
            }
            if (caltoDate == null) {
                message = "Please Fill From Date";
                return null;
            }

            if (acType.equalsIgnoreCase("0")) {
                message = "Please select A/c Type";
                return null;
            }

            if (repType.equalsIgnoreCase("S")) {
                message = "Please select Report Type";
                return null;

            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String FromDt = sdf.format(calFromDate);
            String ToDate = sdf.format(caltoDate);
            String duration = FromDt + "  TO  " + ToDate;
            List<SmsAcnoRegistrationPojo> repportList = beanRemote.smsAcnoRegister(branchCode, acType, ymdFormat.format(calFromDate), ymdFormat.format(caltoDate), repType);
            if (!repportList.isEmpty()) {
                List list = RemoteCode.getBranchNameandAddress(branchCode);
                String repName = "";
                if (repName.equalsIgnoreCase("R")) {
                    repName = "SMS ACCOUNT REGISTER";
                } else {
                    repName = "SMS ACCOUNT UNREGISTER";
                }
                Map fillParams = new HashMap();
                fillParams.put("pReportName", repName);
                fillParams.put("pPrintedDate", duration);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pBranch", list.get(0).toString());
                fillParams.put("pName", list.get(0).toString());
                fillParams.put("pReptype", repType);

                new ReportBean().openPdf(repName + "_" + ymdFormat.format(calFromDate) + " to " + ymdFormat.format(caltoDate), "sms_registration_drtail", new JRBeanCollectionDataSource(repportList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", "SMS ACCOUNT REGISTER");

            } else {
                setMessage("No data to print");
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return null;
    }

    public void refreshAction() {
        try {
            setMessage("");
            setAcType("0");
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitAction() {
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }

    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
    }

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public List<SelectItem> getRepTypeList() {
        return repTypeList;
    }

    public void setRepTypeList(List<SelectItem> repTypeList) {
        this.repTypeList = repTypeList;
    }
}
