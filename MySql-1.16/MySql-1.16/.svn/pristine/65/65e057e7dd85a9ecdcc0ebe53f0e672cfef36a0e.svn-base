/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.sms;

import com.cbs.dto.sms.SmsDetailsDto;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.sms.MessageDetailBeanRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.delegate.SmsManagementDelegate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class AcWiseSmsDetails extends BaseBean {

    private String errMsg;
    private String reportType;
    private String acno, acNoLen;
    private String willShow = "";
    private String willShow1 = "";
    private String frDt;
    private String toDt;
    private List<SelectItem> reportTypeList;
    private CommonReportMethodsRemote commonReport;
    private MessageDetailBeanRemote msgDetailRemote;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private Validator validator;

    public AcWiseSmsDetails() {
        reportTypeList = new ArrayList<SelectItem>();
        try {
            commonReport = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            msgDetailRemote = (MessageDetailBeanRemote) ServiceLocator.getInstance().lookup("MessageDetailBean");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            this.setAcNoLen(getAcNoLength());
            initData();
            refresh();
        } catch (Exception ex) {
            this.setErrMsg(ex.getMessage());
        }
    }

    public void initData() {
        try {
            reportTypeList.add(new SelectItem("--Select--"));
            reportTypeList.add(new SelectItem("AR", "A/c Registration"));
            //Do not delete it.
            reportTypeList.add(new SelectItem("Dt", "Date b/w sms"));
            reportTypeList.add(new SelectItem("TSA", "Total Send SMS To A/c"));
            reportTypeList.add(new SelectItem("ASA", "All SMS To A/c"));
        } catch (Exception ex) {
            this.setErrMsg(ex.getMessage());
        }
    }

    public void reportTypeAction() {
        this.setErrMsg("");
        try {
            if (this.reportType == null || this.reportType.equals("--Select--")) {
                this.setErrMsg("Please Select Report Type.");
                return;
            }
            this.willShow = "";
            if (this.reportType.equals("AR") || this.reportType.equals("AS")) {
                this.willShow = "none";
            }
            this.willShow1 = "";
            if (this.reportType.equalsIgnoreCase("Dt")) {
                this.willShow1 = "none";
            }
        } catch (Exception ex) {
            this.setErrMsg(ex.getMessage());
        }
    }

    public void accountAction() {
        this.setErrMsg("");
        try {
            if (this.acno == null || this.acno.equalsIgnoreCase("")) {
                this.setErrMsg("Account number should not be blank.");
                return;
            }
            //if (this.acno.length() < 12) {
            if (!this.acno.equalsIgnoreCase("") && ((this.acno.length() != 12) && (this.acno.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setErrMsg("Please fill 12 digits valid acoount number.");
                return;
            }
            if (!validator.validateStringAll(this.acno)) {
                this.setErrMsg("Please fill 12 digits valid account number.");
                return;
            }
            String alphacode = commonReport.getAlphacodeByBrncode(getOrgnBrCode());
            if (!alphacode.equalsIgnoreCase("HO")) {
                if (!(ftsRemote.getCurrentBrnCode(this.acno).equalsIgnoreCase(getOrgnBrCode()))) {
                    this.setErrMsg("Please fill your branch account number.");
                    return;
                }
            }
            String result = ftsRemote.ftsAcnoValidate(this.acno, 1, "");
            if (!result.equalsIgnoreCase("true")) {
                this.setErrMsg(result);
                return;
            }
            SmsManagementDelegate delegate = new SmsManagementDelegate();
            result = delegate.checkSubscriberDetails(acno);
            if (!result.equalsIgnoreCase("true")) {
                this.setErrMsg("This account number is not registered for sms.");
                return;
            }
        } catch (Exception ex) {
            this.setErrMsg(ex.getMessage());
        }
    }

    public void btnHtmlAction() {
        this.setErrMsg("");
        String report = "SMS Detail Report";
        String repType = "";
        try {
            if (validateField()) {
                Map fillParams = new HashMap();
                List brNameAndAddList = commonReport.getBranchNameandAddress(getOrgnBrCode());
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
                fillParams.put("pReportDt", frDt + " to " + toDt);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", report);
                if (reportType.equalsIgnoreCase("AR")) {
                    repType = "A/c Registration";
                } else if (reportType.equalsIgnoreCase("AS")) {
                    repType = "A/c Service";
                } else if (reportType.equalsIgnoreCase("TSA")) {
                    repType = "Total Send SMS To A/c";
                } else if (reportType.equalsIgnoreCase("ASA")) {
                    repType = "All SMS To A/c";
                } else if (reportType.equalsIgnoreCase("Dt")) {
                    repType = "Date b/w sms";
                }
                fillParams.put("pReportType", repType);
                List<SmsDetailsDto> list = msgDetailRemote.getAcWiseSmsDetails(this.reportType, this.acno, this.frDt, this.toDt);
                if (list.isEmpty()) {
                    throw new ApplicationException("Data does not exist !");
                }
                if (reportType.equalsIgnoreCase("AR")) {
                    new ReportBean().jasperReport("SmsRegistration", "text/html", new JRBeanCollectionDataSource(list), fillParams, "SMS Detail Report");
                } else if (reportType.equalsIgnoreCase("AS")) {
                    new ReportBean().jasperReport("SmsService", "text/html", new JRBeanCollectionDataSource(list), fillParams, "SMS Detail Report");
                } else if (reportType.equalsIgnoreCase("TSA")) {
                    new ReportBean().jasperReport("SmsSendToAccount", "text/html", new JRBeanCollectionDataSource(list), fillParams, "SMS Detail Report");
                } else if (reportType.equalsIgnoreCase("ASA") || reportType.equalsIgnoreCase("Dt")) {
                    new ReportBean().jasperReport("SmsSendAllAccount", "text/html", new JRBeanCollectionDataSource(list), fillParams, "SMS Detail Report");
                }
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            }
        } catch (Exception ex) {
            this.setErrMsg(ex.getMessage());
        }
    }

    public void pdfDownLoad() {
        this.setErrMsg("");
        String report = "SMS Detail Report";
        String repType = "";
        try {
            if (validateField()) {
                Map fillParams = new HashMap();
                List brNameAndAddList = commonReport.getBranchNameandAddress(getOrgnBrCode());
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
                fillParams.put("pReportDt", frDt + " to " + toDt);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", report);
                if (reportType.equalsIgnoreCase("AR")) {
                    repType = "A/c Registration";
                } else if (reportType.equalsIgnoreCase("AS")) {
                    repType = "A/c Service";
                } else if (reportType.equalsIgnoreCase("TSA")) {
                    repType = "Total Send SMS To A/c";
                } else if (reportType.equalsIgnoreCase("ASA")) {
                    repType = "All SMS To A/c";
                } else if (reportType.equalsIgnoreCase("Dt")) {
                    repType = "Date b/w sms";
                }
                fillParams.put("pReportType", repType);
                List<SmsDetailsDto> list = msgDetailRemote.getAcWiseSmsDetails(this.reportType, this.acno, this.frDt, this.toDt);
                if (list.isEmpty()) {
                    throw new ApplicationException("Data does not exist !");
                }
                if (reportType.equalsIgnoreCase("AR")) {
                    new ReportBean().openPdf("SMS Detail Report", "SmsRegistration", new JRBeanCollectionDataSource(list), fillParams);
                } else if (reportType.equalsIgnoreCase("AS")) {
                    new ReportBean().openPdf("SMS Detail Report", "SmsService", new JRBeanCollectionDataSource(list), fillParams);
                } else if (reportType.equalsIgnoreCase("TSA")) {
                    new ReportBean().openPdf("SMS Detail Report", "SmsSendToAccount", new JRBeanCollectionDataSource(list), fillParams);
                } else if (reportType.equalsIgnoreCase("ASA") || reportType.equalsIgnoreCase("Dt")) {
                    new ReportBean().openPdf("SMS Detail Report", "SmsSendAllAccount", new JRBeanCollectionDataSource(list), fillParams);
                }
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            }
        } catch (Exception ex) {
            this.setErrMsg(ex.getMessage());
        }
    }

    private boolean validateField() {
        try {
            if (this.reportType == null || this.reportType.equals("--Select--")) {
                this.setErrMsg("Please Select Report Type.");
                return false;
            }

            if (!this.reportType.equalsIgnoreCase("Dt")) {
                if (this.acno == null || this.acno.equalsIgnoreCase("")) {
                    this.setErrMsg("Account number should not be blank.");
                    return false;
                }
                //if (this.acno.length() < 12) {
                if (!this.acno.equalsIgnoreCase("") && ((this.acno.length() != 12) && (this.acno.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                    this.setErrMsg("Please fill 12 digits valid acoount number.");
                    return false;
                }
                String alphacode = commonReport.getAlphacodeByBrncode(getOrgnBrCode());
                if (!alphacode.equalsIgnoreCase("HO")) {
                    if (!(ftsRemote.getCurrentBrnCode(this.acno).equalsIgnoreCase(getOrgnBrCode()))) {
                        this.setErrMsg("Please fill your branch account number.");
                        return false;
                    }
                }
                String result = ftsRemote.ftsAcnoValidate(this.acno, 1, "");
                if (!result.equalsIgnoreCase("true")) {
                    this.setErrMsg(result);
                    return false;
                }
                SmsManagementDelegate delegate = new SmsManagementDelegate();
                result = delegate.checkSubscriberDetails(acno);
                if (!result.equalsIgnoreCase("true")) {
                    this.setErrMsg("This account number is not registered for sms.");
                    return false;
                }
            }
            if (this.reportType.equals("TSA") || this.reportType.equals("ASA") || this.reportType.equals("Dt")) {
                if (this.frDt == null || this.frDt.equals("")) {
                    this.setErrMsg("Please Fill From Date.");
                    return false;
                }
                if (!new Validator().validateDate_dd_mm_yyyy(this.frDt)) {
                    this.setErrMsg("Please Fill Proper From Date.");
                    return false;
                }
                if (this.toDt == null || this.toDt.equals("")) {
                    this.setErrMsg("Please Fill To Date.");
                    return false;
                }
                if (!new Validator().validateDate_dd_mm_yyyy(this.toDt)) {
                    this.setErrMsg("Please Fill Proper To Date.");
                    return false;
                }
            }
        } catch (Exception ex) {
            this.setErrMsg(ex.getMessage());
        }
        return true;
    }

    public void refresh() {
        this.setErrMsg("");
        this.setReportType("--Select--");
        this.setAcno("");
        this.willShow = "";
        this.willShow1 = "";
        this.setFrDt(getTodayDate());
        this.setToDt(getTodayDate());
    }

    public String exit() {
        refresh();
        return "case1";
    }

    //Getter And Setter
    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public List<SelectItem> getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List<SelectItem> reportTypeList) {
        this.reportTypeList = reportTypeList;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getWillShow() {
        return willShow;
    }

    public void setWillShow(String willShow) {
        this.willShow = willShow;
    }

    public String getFrDt() {
        return frDt;
    }

    public void setFrDt(String frDt) {
        this.frDt = frDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getWillShow1() {
        return willShow1;
    }

    public void setWillShow1(String willShow1) {
        this.willShow1 = willShow1;
    }
}
