/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.share;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.share.ShareTransferFacadeRemote;
import com.cbs.facade.other.PrintFacadeRemote;
import com.cbs.facade.report.ShareReportFacadeRemote;
import com.cbs.pojo.ShareCertificatePojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.controller.other.PrinterClass;
import com.cbs.web.print.SiplExporter;
import com.cbs.web.print.SiplPage;
import com.cbs.web.print.SiplText;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class ShareCertificate extends BaseBean {

    private String errorMsg;
    private String folioNo;
    private String folioNoShow;
    private String certificateNo;
    private String certificateNoShow;
    private String custName;
    private String fatherName;
    private String custAdd;
    private String custCity;
    private String custState;
    private String noOfshare;
    private String formShareNo;
    private String toShareNo;
    private String issueDt;
    private String shareAmt;
    private String reportType;
    private String frmDt;
    private String toDt;
    private Date date = new Date();
    private String frmFolioNo;
    private String toFolioNo, frmFolioNoShow, toFolioNoShow;
    private String frmCertNo, toCertNo, frmCertNoShow, toCertNoShow;
    private String styleDate, styleFolio, styleCertNo;
    private String lableButton1, lableButton2, acNoLen;
    private List<SelectItem> reportTypeList;
    private List<SelectItem> certificateNoList;
    private boolean disableSingleAcno;
    private FtsPostingMgmtFacadeRemote ftsPostRemote;
    private ShareReportFacadeRemote horfr = null;
    private final String jndiHomeName = "PrintManagementFacade";
    private PrintFacadeRemote beanRemote = null;
    ShareTransferFacadeRemote remoteObject;
    private SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    List<String> shareDetailList;
    List<ShareCertificatePojo> reportList = new ArrayList<ShareCertificatePojo>();

    public ShareCertificate() {
        try {
            frmDt = dmy.format(date);
            toDt = dmy.format(date);
            horfr = (ShareReportFacadeRemote) ServiceLocator.getInstance().lookup("ShareReportFacade");
            remoteObject = (ShareTransferFacadeRemote) ServiceLocator.getInstance().lookup("ShareTransferFacade");
            beanRemote = (PrintFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");

            this.setAcNoLen(getAcNoLength());
            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("S", "--Select--"));
            if (ftsPostRemote.getCodeFromCbsParameterInfo("SHARE-PRINT").equalsIgnoreCase("Y")) {
                reportTypeList.add(new SelectItem("Single Folio", "Single Folio"));
            } else {
                reportTypeList.add(new SelectItem("Certificate No. Wise", "Certificate No. Wise"));
                reportTypeList.add(new SelectItem("Date Wise", "Date Wise"));
                reportTypeList.add(new SelectItem("Folio Wise", "Folio Wise"));
                reportTypeList.add(new SelectItem("Single Folio", "Single Folio"));
            }

            this.reportType = "Date Wise";
            this.lableButton1 = "From Date";
            this.lableButton2 = "To Date";
            this.styleDate = "";
            this.styleFolio = "none";
            refreshPage();
        } catch (Exception ex) {
            setErrorMsg(ex.getMessage());
        }
    }

    public void reprtOption() {
        setErrorMsg("");
        try {
            if (reportType == null || reportType.equalsIgnoreCase("S")) {
                errorMsg = "Please select report type !";
                return;
            }
            if (reportType.equalsIgnoreCase("Date Wise")) {
                this.lableButton1 = "From Date";
                this.lableButton2 = "To Date";
                frmDt = dmy.format(date);
                toDt = dmy.format(date);
                this.styleDate = "";
                this.styleFolio = "none";
                this.styleCertNo = "none";
                this.disableSingleAcno = true;
                refreshfield();
            } else if (reportType.equalsIgnoreCase("Folio Wise")) {
                this.lableButton1 = "From Folio Number";
                this.lableButton2 = "To Folio Number";
                this.styleDate = "none";
                this.styleCertNo = "none";
                this.styleFolio = "";
                this.disableSingleAcno = true;
                refreshfield();
            } else if (reportType.equalsIgnoreCase("Single Folio")) {
                this.disableSingleAcno = false;
                this.styleDate = "none";
                this.styleFolio = "none";
                this.styleCertNo = "none";
                this.lableButton1 = "";
                this.lableButton2 = "";
            } else if (reportType.equalsIgnoreCase("Certificate No. Wise")) {
                this.lableButton1 = "From Certificate No.";
                this.lableButton2 = "To Certificate No.";
                this.styleDate = "none";
                this.styleFolio = "none";
                this.styleCertNo = "";
                this.disableSingleAcno = true;
                refreshfield();
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void folioToMethod() {
        setErrorMsg("");
        certificateNoList = new ArrayList<SelectItem>();
        if (folioNo == null || folioNo.equalsIgnoreCase("")) {
            setErrorMsg("Please fill Folio Number");
            return;
        }
        //if (folioNo.length() < 12) {
        if ((this.folioNo.length() != 12) && (this.folioNo.length() != (Integer.parseInt(this.getAcNoLen())))) {
            this.setErrorMsg("Please Fill Proper Folio Number");
            return;
        }
        setErrorMsg("Please Select Certicate No. !");
        try {
            folioNoShow = "[" + ftsPostRemote.getNewAccountNumber(folioNo) + "]";
            certificateNoList.add(new SelectItem("--Select--"));
            List certificateList = horfr.getCertificateNoList(folioNo);
            if (!certificateList.isEmpty()) {
                for (int i = 0; i < certificateList.size(); i++) {
                    Vector vtr = (Vector) certificateList.get(i);
                    certificateNoList.add(new SelectItem(vtr.get(0).toString()));
                }
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }

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
    }

    public void folioTo1Method() {
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

    public void setOnfromCertNo() {
        setErrorMsg("");
        if (frmCertNoShow == null || frmCertNoShow.equalsIgnoreCase("")) {
            setErrorMsg("Please fill From Certificate Number");
            return;
        }
        frmCertNo = frmCertNoShow;
    }

    public void setOntoCertNo() {
        setErrorMsg("");
        if (toCertNoShow == null || toCertNoShow.equalsIgnoreCase("")) {
            setErrorMsg("Please fill To Certificate Number");
            return;
        }
        toCertNo = toCertNoShow;
    }

    public void retriveCustData() {
        setErrorMsg("");

        if (certificateNo == null || certificateNo.equalsIgnoreCase("") || certificateNo.equalsIgnoreCase("--Select--")) {
            setErrorMsg("Please Select Certicate No. !");
            return;
        }

        certificateNoShow = "[" + certificateNo + "]";
        try {
            shareDetailList = horfr.getShareCertificateData(folioNo, certificateNo, getOrgnBrCode());
            this.setCustName(shareDetailList.get(2));
            this.setFatherName(shareDetailList.get(3));
            this.setCustAdd(shareDetailList.get(4));
            this.setCustCity(shareDetailList.get(5));
            this.setCustState(shareDetailList.get(6));

            this.setFormShareNo(shareDetailList.get(7));
            this.setToShareNo(shareDetailList.get(8));
            this.setNoOfshare(shareDetailList.get(9));
            this.setCertificateNo(shareDetailList.get(10));
            this.setIssueDt(shareDetailList.get(11));
            this.setShareAmt(shareDetailList.get(12));

        } catch (Exception ex) {
            setErrorMsg(ex.getMessage());
        }
    }

    public void printButton() {
        setErrorMsg("");
        try {
            if (ftsPostRemote.getCodeFromCbsParameterInfo("SHARE-PRINT").equalsIgnoreCase("Y")) {
                printReport();
            } else {
                printAction();
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void printReport() {
        setErrorMsg("");
        try {
            if (reportType == null || reportType.equalsIgnoreCase("S")) {
                errorMsg = "Please select report type !";
                return;
            }

            if (folioNo == null || folioNo.equalsIgnoreCase("")) {
                errorMsg = "Please fill Folio Number";
                return;
            }
            //if (folioNo.length() < 12) {
            if ((this.folioNo.length() != 12) && (this.folioNo.length() != (Integer.parseInt(this.getAcNoLen())))) {
                errorMsg = "Please Fill Proper Folio Number";
                return;
            }

            if (certificateNo == null || certificateNo.equalsIgnoreCase("") || certificateNo.equalsIgnoreCase("--Select--")) {
                setErrorMsg("Please Select Certicate No. !");
                return;
            }

            List paramHeader = beanRemote.getPrintingHeader("SC");
            if (paramHeader.isEmpty()) {
                throw new ApplicationException("Please fill the Header parameters");
            }
            Vector ele = (Vector) paramHeader.get(0);
            int width = Integer.parseInt(ele.get(0).toString());
            int height = Integer.parseInt(ele.get(1).toString());

            int tmpBreak = Integer.parseInt(ele.get(2).toString());
            int xOrd = Integer.parseInt(ele.get(3).toString());
            boolean lineBeak = tmpBreak == 1 ? true : false;

            float pdfWidth = Float.parseFloat(ele.get(4).toString()) * 72;
            float pdfHeight = Float.parseFloat(ele.get(5).toString()) * 72;
            float topMargin = Float.parseFloat(ele.get(6).toString());

            float leftMargin = Float.parseFloat(ele.get(7).toString());
            float fontSize = Float.parseFloat(ele.get(8).toString());

            shareDetailList = horfr.getShareCertificateData(folioNo, certificateNo, "90");

            List billLabelList = beanRemote.getPrintingParameters("SCL", "L");
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
                text.setWidth(Integer.parseInt(element.get(3).toString()));
                text.setText(element.get(0).toString());
                page.addElement(text);
            }
            List fdValueList = beanRemote.getPrintingParameters("SC", "V");
            for (int i = 0; i < fdValueList.size(); i++) {
                Vector element = (Vector) fdValueList.get(i);

                text = new SiplText();
                text.setX(Integer.parseInt(element.get(1).toString()));
                text.setY(Integer.parseInt(element.get(2).toString()));

                text.setHeight(8);
                text.setWidth(Integer.parseInt(element.get(3).toString()));
                text.setText(shareDetailList.get(i));
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
            String msg = "";
            if (beanRemote.isNewPrinting("PDF PRINTING")) {
                byte[] byteArr = prinObj.generatePDFStream(pdfWidth, pdfHeight, topMargin, leftMargin, fontSize, os.toString());
                msg = prinObj.printReport(pdfWidth, pdfHeight, byteArr);
            } else {
                if (beanRemote.isNewPrinting("NEW PRINTER")) {
                    option = "10";
                } else {
                    option = "TXT";
                }
                msg = prinObj.printReport(option, os.toByteArray());
            }
            if (msg.equalsIgnoreCase("true")) {
                this.setErrorMsg("Print Successfull.");
            } else {
                this.setErrorMsg("Error in Printing.");
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void printAction() {
        setErrorMsg("");
        try {
            if (reportType == null || reportType.equalsIgnoreCase("S")) {
                errorMsg = "Please select report type !";
                return;
            }
            if (reportType.equalsIgnoreCase("Date Wise")) {
                if (!Validator.validateDate(frmDt)) {
                    errorMsg = "Please select Proper from date ";
                    return;
                }
                if (dmy.parse(frmDt).after(getDate())) {
                    errorMsg = "From date should be less than current date !";
                    return;
                }
                if (toDt == null || toDt.equalsIgnoreCase("")) {
                    errorMsg = "Please enter to date";
                    return;
                }
                if (!Validator.validateDate(toDt)) {
                    errorMsg = "Please select Proper to date ";
                    return;
                }
                if (dmy.parse(toDt).after(getDate())) {
                    errorMsg = "To date should be less than current date !";
                    return;
                }
                if (dmy.parse(frmDt).after(dmy.parse(toDt))) {
                    errorMsg = "Please from date should be less than to date";
                    return;
                }
            } else if (reportType.equalsIgnoreCase("Folio Wise")) {

                if (frmFolioNo == null || frmFolioNo.equalsIgnoreCase("")) {
                    errorMsg = "Please fill from folio no.";
                    return;
                }
                //  ftsPostRemote.getNewAccountNumber(frmFolioNo);
                if (toFolioNo == null || toFolioNo.equalsIgnoreCase("")) {
                    errorMsg = "Please fill to folio no.";
                    return;
                }
            } else if (reportType.equalsIgnoreCase("Single Folio")) {
                if (folioNo == null || folioNo.equalsIgnoreCase("")) {
                    errorMsg = "Please fill Folio Number";
                    return;
                }
                //if (folioNo.length() < 12) {
                if ((this.folioNo.length() != 12) && (this.folioNo.length() != (Integer.parseInt(this.getAcNoLen())))) {
                    errorMsg = "Please Fill Proper Folio Number";
                    return;
                }
            } else if (reportType.equalsIgnoreCase("Certificate No. Wise")) {
                if (frmCertNo == null || frmCertNo.equalsIgnoreCase("")) {
                    errorMsg = "Please fill from certificate no.";
                    return;
                }
                if (toCertNo == null || toCertNo.equalsIgnoreCase("")) {
                    errorMsg = "Please fill to certificate no.";
                    return;
                }
            }
            String report = "Share Certificate";
            reportList = horfr.getShareCertificateData1(reportType, frmFolioNo, toFolioNo, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)), folioNo, certificateNo, getOrgnBrCode(), frmCertNo, toCertNo);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist");
            }
            Map fillParam = new HashMap();

            if (ftsPostRemote.getCodeFromCbsParameterInfo("BNK_IDENTIFIER").equalsIgnoreCase("K")) {
                new ReportBean().openPdf("Share Certificate", "Koylanchal_Share_Certificate", new JRBeanCollectionDataSource(reportList), fillParam);
            } else {
                new ReportBean().openPdf("Share Certificate", "ShareCertificate1", new JRBeanCollectionDataSource(reportList), fillParam);
            }
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void refreshPage() {
        setErrorMsg("");
        setCustName("");
        setCustAdd("");
        setCustCity("");
        setCustState("");
        setCertificateNo("");
        setCertificateNoShow("");
        setFatherName("");
        setFolioNo("");
        setFolioNoShow("");
        setNoOfshare("");
        setFormShareNo("");
        setToShareNo("");
        setIssueDt("");
        setShareAmt("");
        setFrmFolioNo("");
        setFolioNoShow("");
        setFrmFolioNoShow("");
        setToFolioNo("");
        setToFolioNoShow("");
        this.styleDate = "none";
        this.styleFolio = "none";
        this.styleCertNo = "none";
        this.lableButton1 = "";
        this.lableButton2 = "";
        this.reportType = "S";
        setFrmCertNoShow("");
        setFrmCertNo("");
        setToCertNo("");
        setToCertNoShow("");
        setToCertNoShow("");
    }

    public void refreshfield() {
        setErrorMsg("");
        setCustName("");
        setCustAdd("");
        setCustCity("");
        setCustState("");
        setCertificateNo("");
        setCertificateNoShow("");
        setFatherName("");
        setFolioNo("");
        setFolioNoShow("");
        setNoOfshare("");
        setFormShareNo("");
        setToShareNo("");
        setIssueDt("");
        setShareAmt("");
        setFrmFolioNo("");
        setFolioNoShow("");
        setFrmFolioNoShow("");
        setToFolioNo("");
        setToFolioNoShow("");
        setFrmCertNoShow("");
        setFrmCertNo("");
        setToCertNo("");
        setToCertNoShow("");
        setToCertNoShow("");
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getFolioNoShow() {
        return folioNoShow;
    }

    public void setFolioNoShow(String folioNoShow) {
        this.folioNoShow = folioNoShow;
    }

    public String getCertificateNoShow() {
        return certificateNoShow;
    }

    public void setCertificateNoShow(String certificateNoShow) {
        this.certificateNoShow = certificateNoShow;
    }

    public String getFolioNo() {
        return folioNo;
    }

    public void setFolioNo(String folioNo) {
        this.folioNo = folioNo;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public List<SelectItem> getCertificateNoList() {
        return certificateNoList;
    }

    public void setCertificateNoList(List<SelectItem> certificateNoList) {
        this.certificateNoList = certificateNoList;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustAdd() {
        return custAdd;
    }

    public void setCustAdd(String custAdd) {
        this.custAdd = custAdd;
    }

    public String getCustCity() {
        return custCity;
    }

    public void setCustCity(String custCity) {
        this.custCity = custCity;
    }

    public String getCustState() {
        return custState;
    }

    public void setCustState(String custState) {
        this.custState = custState;
    }

    public String getNoOfshare() {
        return noOfshare;
    }

    public void setNoOfshare(String noOfshare) {
        this.noOfshare = noOfshare;
    }

    public String getFormShareNo() {
        return formShareNo;
    }

    public void setFormShareNo(String formShareNo) {
        this.formShareNo = formShareNo;
    }

    public String getToShareNo() {
        return toShareNo;
    }

    public void setToShareNo(String toShareNo) {
        this.toShareNo = toShareNo;
    }

    public String getIssueDt() {
        return issueDt;
    }

    public void setIssueDt(String issueDt) {
        this.issueDt = issueDt;
    }

    public String getShareAmt() {
        return shareAmt;
    }

    public void setShareAmt(String shareAmt) {
        this.shareAmt = shareAmt;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
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

    public String getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(String frmDt) {
        this.frmDt = frmDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
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

    public String getStyleDate() {
        return styleDate;
    }

    public void setStyleDate(String styleDate) {
        this.styleDate = styleDate;
    }

    public String getStyleFolio() {
        return styleFolio;
    }

    public void setStyleFolio(String styleFolio) {
        this.styleFolio = styleFolio;
    }

    public String getLableButton1() {
        return lableButton1;
    }

    public void setLableButton1(String lableButton1) {
        this.lableButton1 = lableButton1;
    }

    public String getLableButton2() {
        return lableButton2;
    }

    public void setLableButton2(String lableButton2) {
        this.lableButton2 = lableButton2;
    }

    public boolean isDisableSingleAcno() {
        return disableSingleAcno;
    }

    public void setDisableSingleAcno(boolean disableSingleAcno) {
        this.disableSingleAcno = disableSingleAcno;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFrmCertNo() {
        return frmCertNo;
    }

    public void setFrmCertNo(String frmCertNo) {
        this.frmCertNo = frmCertNo;
    }

    public String getToCertNo() {
        return toCertNo;
    }

    public void setToCertNo(String toCertNo) {
        this.toCertNo = toCertNo;
    }

    public String getFrmCertNoShow() {
        return frmCertNoShow;
    }

    public void setFrmCertNoShow(String frmCertNoShow) {
        this.frmCertNoShow = frmCertNoShow;
    }

    public String getToCertNoShow() {
        return toCertNoShow;
    }

    public void setToCertNoShow(String toCertNoShow) {
        this.toCertNoShow = toCertNoShow;
    }

    public String getStyleCertNo() {
        return styleCertNo;
    }

    public void setStyleCertNo(String styleCertNo) {
        this.styleCertNo = styleCertNo;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
}
