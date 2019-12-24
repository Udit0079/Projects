/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.share;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.dto.report.ShareFinalBalanceReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.ShareReportFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.io.IOException;
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
 * @author admin
 */
public class FinalBalanceReport extends BaseBean {

    private String errorMsg;
    private String frmFolioNo;
    private String toFolioNo, frmFolioNoShow, toFolioNoShow;
    private Date date;
    private String status;
    private String alphacode;
    List<SelectItem> alphacodeList;
    private String accountCat;
    private List<SelectItem> accountCatList;
    private SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private ShareReportFacadeRemote horfr = null;
    private CommonReportMethodsRemote commonRemote = null;
    FtsPostingMgmtFacadeRemote ftsPostRemote;

    /**
     * Creates a new instance of FinalBalanceReport
     */
    public FinalBalanceReport() {
        try {
            horfr = (ShareReportFacadeRemote) ServiceLocator.getInstance().lookup("ShareReportFacade");
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");

            List brnCode = new ArrayList();
            brnCode = horfr.getAlphacodeList();
            alphacodeList = new ArrayList<SelectItem>();
            //alphacodeList.add(new SelectItem("--Select--"));
            alphacodeList.add(new SelectItem("ALL", "ALL"));
            if (!brnCode.isEmpty()) {
                for (int i = 0; i < brnCode.size(); i++) {
                    Vector brV = (Vector) brnCode.get(i);
                    alphacodeList.add(new SelectItem(brV.get(0).toString()));
                }
            }

            accountCatList = new ArrayList<SelectItem>();
            accountCatList.add(new SelectItem("ALL", "ALL"));
            accountCatList.add(new SelectItem("01", "Individual"));
            accountCatList.add(new SelectItem("02", "Associate"));


        } catch (Exception e) {
        }
    }

    public String getFrmFolioNoShow() {
        return frmFolioNoShow;
    }

    public void setFrmFolioNoShow(String frmFolioNoShow) {
        this.frmFolioNoShow = frmFolioNoShow;
    }

    public String getToFolioNoShow() {
        return toFolioNoShow;
    }

    public void setToFolioNoShow(String toFolioNoShow) {
        this.toFolioNoShow = toFolioNoShow;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getFrmFolioNo() {
        return frmFolioNo;
    }

    public void setFrmFolioNo(String frmFolioNo) {
        this.frmFolioNo = frmFolioNo;
    }

    public String getToFolioNo() {
        return toFolioNo;
    }

    public void setToFolioNo(String toFolioNo) {
        this.toFolioNo = toFolioNo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAlphacode() {
        return alphacode;
    }

    public void setAlphacode(String alphacode) {
        this.alphacode = alphacode;
    }

    public List<SelectItem> getAlphacodeList() {
        return alphacodeList;
    }

    public void setAlphacodeList(List<SelectItem> alphacodeList) {
        this.alphacodeList = alphacodeList;
    }

    public String getAccountCat() {
        return accountCat;
    }

    public void setAccountCat(String accountCat) {
        this.accountCat = accountCat;
    }

    public List<SelectItem> getAccountCatList() {
        return accountCatList;
    }

    public void setAccountCatList(List<SelectItem> accountCatList) {
        this.accountCatList = accountCatList;
    }

    public String viewReport() {
        String status = "", chkStatus = "";
        try {
            List resultList8 = commonRemote.dividendParameterCode();
            if (!resultList8.isEmpty()) {
                for (int i = 0; i < resultList8.size(); i++) {
                    Vector ele8 = (Vector) resultList8.get(i);
                    chkStatus = ele8.get(0).toString();
                }
            } else {
                chkStatus = "0";
            }

            if (chkStatus.equalsIgnoreCase("0")) {
                status = "N";
            } else {
                status = "Y";
            }

            if (validate()) {
                List<ShareFinalBalanceReportPojo> resultList = horfr.getFinalBalanceReport(frmFolioNo, toFolioNo, ymd.format(date), alphacode, accountCat);
                if (!resultList.isEmpty()) {
                    String aCategory = "ALL";
                    if (accountCat.equalsIgnoreCase("01")) {
                        aCategory = "Individual";
                    } else if (accountCat.equalsIgnoreCase("02")) {
                        aCategory = "Associate";
                    } 
                    if(alphacode.equalsIgnoreCase("All")){
                        alphacode = "HO";
                    }
                    List brnList = commonRemote.getBranchInfoByAlphaCode(alphacode);
                    Vector brnCode =(Vector) brnList.get(0);
                    String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    Map fillParams = new HashMap();
                    fillParams.put("pReportDate", dmy.format(date));
                    fillParams.put("pPrintDate", dmy.format(new Date()));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", "Final Balance Report");
                    fillParams.put("pBankName", s[0]);
                    fillParams.put("pBankAddress",brnCode.get(1).toString());
                    fillParams.put("status", status);
                    fillParams.put("pAcategory", aCategory);
                    new ReportBean().jasperReport("ShareFinalBalanceReport", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, "Final Balance Report");
                    return "report";
                } else {
                    errorMsg = "Data does not exist";
                }
            }
        } catch (ApplicationException ex) {
            setErrorMsg(ex.getMessage());
        }
        return null;
    }

    public String pdfDownLoad() {
        String status = "", chkStatus = "";
        try {
            List resultList8 = commonRemote.dividendParameterCode();
            if (!resultList8.isEmpty()) {
                for (int i = 0; i < resultList8.size(); i++) {
                    Vector ele8 = (Vector) resultList8.get(i);
                    chkStatus = ele8.get(0).toString();
                }
            } else {
                chkStatus = "0";
            }

            if (chkStatus.equalsIgnoreCase("0")) {
                status = "N";
            } else {
                status = "Y";
            }

            if (validate()) {
                List<ShareFinalBalanceReportPojo> resultList = horfr.getFinalBalanceReport(frmFolioNo, toFolioNo, ymd.format(date), alphacode, accountCat);
                if (!resultList.isEmpty()) {
                    String aCategory = "ALL";
                    if (accountCat.equalsIgnoreCase("01")) {
                        aCategory = "Individual";
                    } else if (accountCat.equalsIgnoreCase("02")) {
                        aCategory = "Associate";
                    }
                    if(alphacode.equalsIgnoreCase("All")){
                         alphacode = "HO";
                    }
                    List brnList = commonRemote.getBranchInfoByAlphaCode(alphacode);
                    Vector brnCode =(Vector) brnList.get(0);
                    String s[] = commonRemote.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                    Map fillParams = new HashMap();
                    fillParams.put("pReportDate", dmy.format(date));
                    fillParams.put("pPrintDate", dmy.format(new Date()));
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pReportName", "Final Balance Report");
                    fillParams.put("pBankName", s[0]);
                    fillParams.put("pBankAddress", brnCode.get(1).toString());
                    fillParams.put("status", status);
                    fillParams.put("pAcategory", aCategory);
                    new ReportBean().openPdf("ShareFinalBalanceReport", "ShareFinalBalanceReport", new JRBeanCollectionDataSource(resultList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", "Final Balance Report");
                } else {
                    errorMsg = "Data does not exist";
                }
            }
        } catch (Exception ex) {
            setErrorMsg(ex.getMessage());
        }
        return null;
    }

    public void refreshPage() {
        setFrmFolioNo("");
        setFrmFolioNoShow("");
        setToFolioNo("");
        setToFolioNoShow("");
        setErrorMsg("");
    }

    public boolean validate() {
        if (frmFolioNo == null || frmFolioNo.trim().length() < 12) {
            errorMsg = "Please enter valid from foliono";
            return false;
        } else if (toFolioNo == null || toFolioNo.trim().length() < 12) {
            errorMsg = "Please enter valid to foliono";
            return false;
        } else if (date == null) {
            errorMsg = "Please enter valid date";
            return false;
        }

        return true;
    }

    public void folioFromMethod() {
        try {
            setErrorMsg("");
            if (frmFolioNoShow == null || frmFolioNoShow.equalsIgnoreCase("")) {
                setErrorMsg("Please fill From Folio Number");
                return;
            }
            frmFolioNo = getOrgnBrCode() + CbsAcCodeConstant.SF_AC.getAcctCode() + CbsUtil.lPadding(6, Integer.parseInt(frmFolioNoShow)) + "01";
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }


//        if (frmFolioNoShow.length() < 12) {
//            setErrorMsg("Please enter 12 digit From Folio Number");
//            return;
//        }
//        try {
//            frmFolioNo = ftsPostRemote.getNewAccountNumber(frmFolioNoShow);
//        } catch (Exception e) {
//            setErrorMsg(e.getLocalizedMessage());
//        }
    }

    public void folioToMethod() {
        try {
            setErrorMsg("");
            if (toFolioNoShow == null || toFolioNoShow.equalsIgnoreCase("")) {
                setErrorMsg("Please fill To Folio Number");
                return;
            }
            toFolioNo = getOrgnBrCode() + CbsAcCodeConstant.SF_AC.getAcctCode() + CbsUtil.lPadding(6, Integer.parseInt(toFolioNoShow)) + "01";
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }

//        if (toFolioNoShow.length() < 12) {
//            setErrorMsg("Please enter 12 digit To Folio Number");
//            return;
//        }
//        try {
//            toFolioNo = ftsPostRemote.getNewAccountNumber(toFolioNoShow);
//        } catch (Exception e) {
//            setErrorMsg(e.getLocalizedMessage());
//        }
    }
}
