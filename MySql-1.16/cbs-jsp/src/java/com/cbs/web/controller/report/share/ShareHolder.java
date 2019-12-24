/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.share;

import ar.com.fdvs.dj.domain.Style;
import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.dto.report.ShareHoldersPojo;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.ShareReportFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
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
 * @author Athar Reza
 */
public class ShareHolder extends BaseBean {

    private String branchCode;
    private String alphacode;
    List<SelectItem> alphacodeList;
    private String frAcNo, frFolionoShow;
    private String toAcNo, toFolionoShow;
    private String tillDt;
    private String curDt;
    private String orderBy;
    private String frDt;
    List<SelectItem> orderByList;
    private String message;
    Date dt = new Date();
    private String display = "";
    private boolean optall;
    private boolean optbet;
    private String accountCat;
    private List<SelectItem> accountCatList;
    private String focusId;
    private String status;
    private List<SelectItem> statusList;
    private String repType;
    private List<SelectItem> repTypeList;
    private boolean disableFolio;
    private boolean disableBtn;
    private boolean disableBtnExcel;
    private boolean disableStatus;
    private ShareReportFacadeRemote shareHolderFacade;
    FtsPostingMgmtFacadeRemote ftsPostRemote;
    private CommonReportMethodsRemote common;
    List<ShareHoldersPojo> shareHoldersList = new ArrayList<ShareHoldersPojo>();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public String getCurDt() {
        return curDt;
    }

    public void setCurDt(String curDt) {
        this.curDt = curDt;
    }

    public String getFrFolionoShow() {
        return frFolionoShow;
    }

    public void setFrFolionoShow(String frFolionoShow) {
        this.frFolionoShow = frFolionoShow;
    }

    public String getToFolionoShow() {
        return toFolionoShow;
    }

    public void setToFolionoShow(String toFolionoShow) {
        this.toFolionoShow = toFolionoShow;
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

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getFrAcNo() {
        return frAcNo;
    }

    public void setFrAcNo(String frAcNo) {
        this.frAcNo = frAcNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isOptall() {
        return optall;
    }

    public void setOptall(boolean optall) {
        this.optall = optall;
    }

    public boolean isOptbet() {
        return optbet;
    }

    public void setOptbet(boolean optbet) {
        this.optbet = optbet;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public List<SelectItem> getOrderByList() {
        return orderByList;
    }

    public void setOrderByList(List<SelectItem> orderByList) {
        this.orderByList = orderByList;
    }

    public String getTillDt() {
        return tillDt;
    }

    public void setTillDt(String tillDt) {
        this.tillDt = tillDt;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getToAcNo() {
        return toAcNo;
    }

    public void setToAcNo(String toAcNo) {
        this.toAcNo = toAcNo;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
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

    public String getFrDt() {
        return frDt;
    }

    public void setFrDt(String frDt) {
        this.frDt = frDt;
    }

    public boolean isDisableFolio() {
        return disableFolio;
    }

    public void setDisableFolio(boolean disableFolio) {
        this.disableFolio = disableFolio;
    }

    public boolean isDisableBtn() {
        return disableBtn;
    }

    public void setDisableBtn(boolean disableBtn) {
        this.disableBtn = disableBtn;
    }

    public boolean isDisableBtnExcel() {
        return disableBtnExcel;
    }

    public void setDisableBtnExcel(boolean disableBtnExcel) {
        this.disableBtnExcel = disableBtnExcel;
    }

    public boolean isDisableStatus() {
        return disableStatus;
    }

    public void setDisableStatus(boolean disableStatus) {
        this.disableStatus = disableStatus;
    }

    public ShareHolder() {
        try {
            this.setTillDt(getTodayDate());
            this.setFrDt(getTodayDate());
            shareHolderFacade = (ShareReportFacadeRemote) ServiceLocator.getInstance().lookup("ShareReportFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List brnCode = new ArrayList();
            brnCode = shareHolderFacade.getAlphacodeList();
            alphacodeList = new ArrayList<SelectItem>();
            alphacodeList.add(new SelectItem("--Select--"));
            alphacodeList.add(new SelectItem("ALL", "ALL"));
            if (!brnCode.isEmpty()) {
                for (int i = 0; i < brnCode.size(); i++) {
                    Vector brV = (Vector) brnCode.get(i);
                    alphacodeList.add(new SelectItem(brV.get(0).toString()));
                }
            }

            statusList = new ArrayList<SelectItem>();
            statusList.add(new SelectItem("ALL", "ALL"));
            statusList.add(new SelectItem("A", "Active"));
            statusList.add(new SelectItem("C", "Close"));

            repTypeList = new ArrayList<>();
            repTypeList.add(new SelectItem("0", "--Select--"));
            repTypeList.add(new SelectItem("1", "ALL Folio"));
            repTypeList.add(new SelectItem("2", "Between Folio"));
            repTypeList.add(new SelectItem("4", "Folio Opening"));
            repTypeList.add(new SelectItem("3", "Excel"));

            accountCatList = new ArrayList<SelectItem>();
            accountCatList.add(new SelectItem("ALL", "ALL"));
            accountCatList.add(new SelectItem("01", "Individual"));
            accountCatList.add(new SelectItem("02", "Associate"));

            orderByList = new ArrayList<SelectItem>();
            orderByList.add(new SelectItem("S", "--Select--"));
            orderByList.add(new SelectItem("1", "Folio No"));
            //orderByList.add(new SelectItem("2","Certificate No"));
            //orderByList.add(new SelectItem("3", "Customer Name"));
            orderByList.add(new SelectItem("4", "Issue Date"));
            refreshPage();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void viewReport() {
        String branchName = "";
        String address = "";

        if (alphacode.equalsIgnoreCase("--Select--")) {
            setMessage("Please select Branch Name");
            return;
        }

        if (repType.equalsIgnoreCase("0")) {
            setMessage("Please select Report Option");
            return;
        }

        if (frDt == null || frDt.equalsIgnoreCase("")) {
            setMessage("Please fill from date");
            return;
        }

        if (tillDt == null || tillDt.equalsIgnoreCase("")) {
            setMessage("Please fill to date");
            return;
        }

        if (orderBy == null || orderBy.equalsIgnoreCase("S")) {
            setMessage("Please select Order By");
            return;
        }

        try {
            List brNameAdd = new ArrayList();
            if (this.alphacode.equalsIgnoreCase("All")) {
                brNameAdd = shareHolderFacade.getBrnNameandBrnAddress(common.getAlphacodeByBrncode(getOrgnBrCode()));
            } else {
                brNameAdd = shareHolderFacade.getBrnNameandBrnAddress(this.alphacode);
            }
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }
            String report = "Share Holders Report";
            String jrxml = "ShareHoldersReport";
            if (repType.equalsIgnoreCase("4")) {
                jrxml = "FolioOpeningReport";
                report = "Folio Opening Report";
            }
            Map fillmap = new HashMap();
            fillmap.put("pRepName", report);
            fillmap.put("pPrintedBy", this.getUserName());
            fillmap.put("pReportDt", this.tillDt);
            fillmap.put("pPrintDt", this.getTodayDate());
            fillmap.put("pBrnName", branchName);
            fillmap.put("pBrnAddress", address);
            fillmap.put("pCurdt", this.curDt);
            if (accountCat.equalsIgnoreCase("ALL")) {
                fillmap.put("pAcCatg", "ALL");
            } else if (accountCat.equalsIgnoreCase("01")) {
                fillmap.put("pAcCatg", "Individual");
            } else if (accountCat.equalsIgnoreCase("02")) {
                fillmap.put("pAcCatg", "Associate");
            }

            shareHoldersList = shareHolderFacade.getShareHolders(alphacode, frAcNo, toAcNo, ymd.format(dmy.parse(frDt)), ymd.format(dmy.parse(tillDt)), orderBy, accountCat, status, repType);
            if (shareHoldersList.isEmpty()) {
                message = "No value found in databse";
                return;
            }
            new ReportBean().jasperReport(jrxml, "text/html",
                    new JRBeanCollectionDataSource(shareHoldersList), fillmap, report);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public FastReportBuilder excelPrint() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            if (alphacode.equalsIgnoreCase("--Select--")) {
                setMessage("Please select Branch Name");
                return null;
            }

            if (repType.equalsIgnoreCase("0")) {
                setMessage("Please select Report Option");
                return null;
            }

            if (frDt == null || frDt.equalsIgnoreCase("")) {
                setMessage("Please fill from date");
                return null;
            }

            if (tillDt == null || tillDt.equalsIgnoreCase("")) {
                setMessage("Please fill to date");
                return null;
            }

            if (orderBy == null || orderBy.equalsIgnoreCase("S")) {
                setMessage("Please select Order By");
                return null;
            }

            shareHoldersList = shareHolderFacade.getShareHolders(alphacode, frAcNo, toAcNo, ymd.format(dmy.parse(frDt)), ymd.format(dmy.parse(tillDt)), orderBy, accountCat, status, repType);
            if (shareHoldersList.isEmpty()) {
                throw new ApplicationException("Data does not exit");
            }
            int Width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn folioNo = ReportBean.getColumn("folioNo", String.class, "Folio . Number", 100);
            AbstractColumn shName = ReportBean.getColumn("shName", String.class, "Share Holder Name ", 200);
            AbstractColumn fhName = ReportBean.getColumn("fhName", String.class, "Father Husband.Name", 200);
            AbstractColumn address = ReportBean.getColumn("address", String.class, "Address", 500);
            //AbstractColumn totalSr = ReportBean.getColumn("totalSr", Integer.class, "TotalShare", 80);
            //AbstractColumn shareValue = ReportBean.getColumn("shareValue", Double.class, "Share.Value", 200);
            // AbstractColumn shareAmt = ReportBean.getColumn("shareAmt", Double.class, "ShareAmt", 90);
            //AbstractColumn branch = ReportBean.getColumn("branch", String.class, "BranchName", 150);
            AbstractColumn city = ReportBean.getColumn("city", String.class, "State", 150);
            AbstractColumn pinCode = ReportBean.getColumn("pinCode", String.class, "Pin Code", 150);
            //AbstractColumn purpose = ReportBean.getColumn("purpose", String.class, "Purpose", 150);


            fastReport.addColumn(folioNo);
            Width = Width + folioNo.getWidth();

            fastReport.addColumn(shName);
            Width = Width + shName.getWidth();

            fastReport.addColumn(fhName);
            Width = Width + fhName.getWidth();

            fastReport.addColumn(address);
            Width = Width + address.getWidth();

//            fastReport.addColumn(totalSr);
//            Width = Width + totalSr.getWidth();
//
//            fastReport.addColumn(shareValue);
//            Width = Width + shareValue.getWidth();
//
//            fastReport.addColumn(shareAmt);
//            Width = Width + shareAmt.getWidth();
//
//            fastReport.addColumn(branch);
//            Width = Width + branch.getWidth();

            fastReport.addColumn(city);
            Width = Width + city.getWidth();

            fastReport.addColumn(pinCode);
            Width = Width + pinCode.getWidth();

//            fastReport.addColumn(purpose);
//            Width = Width + purpose.getWidth();

            Page page = new Page(842, Width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("Share Holder Detail Report");

            new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(shareHoldersList), fastReport, "Share Holder Detail Report");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return fastReport;
    }

    public void pdfDownLoad() {
        String branchName = "";
        String address = "";

        if (alphacode.equalsIgnoreCase("--Select--")) {
            setMessage("Please select Branch Name");
            return;
        }
        if (repType.equalsIgnoreCase("0")) {
            setMessage("Please select Report Option");
            return;
        }

        if (frDt == null || frDt.equalsIgnoreCase("")) {
            setMessage("Please fill from date");
            return;
        }

        if (tillDt == null || tillDt.equalsIgnoreCase("")) {
            setMessage("Please fill to date");
            return;
        }
        if (orderBy == null || orderBy.equalsIgnoreCase("S")) {
            setMessage("Please select Order By");
            return;
        }

        try {
            List brNameAdd = new ArrayList();
            if (this.alphacode.equalsIgnoreCase("All")) {
                brNameAdd = shareHolderFacade.getBrnNameandBrnAddress(common.getAlphacodeByBrncode(getOrgnBrCode()));
            } else {
                brNameAdd = shareHolderFacade.getBrnNameandBrnAddress(this.alphacode);
            }
            if (brNameAdd.size() > 0) {
                branchName = (String) brNameAdd.get(0);
                address = (String) brNameAdd.get(1);
            }
            String report = "Share Holders Report";
             String jrxml = "ShareHoldersReport";
            if (repType.equalsIgnoreCase("4")) {
                jrxml = "FolioOpeningReport";
                report = "Folio Opening Report";
            }
            Map fillmap = new HashMap();
            fillmap.put("pRepName", report);
            fillmap.put("pPrintedBy", this.getUserName());
            fillmap.put("pReportDt", this.tillDt);
            fillmap.put("pPrintDt", this.getTodayDate());
            fillmap.put("pBrnName", branchName);
            fillmap.put("pBrnAddress", address);
            fillmap.put("pCurdt", this.curDt);
            if (accountCat.equalsIgnoreCase("ALL")) {
                fillmap.put("pAcCatg", "ALL");
            } else if (accountCat.equalsIgnoreCase("01")) {
                fillmap.put("pAcCatg", "Individual");
            } else if (accountCat.equalsIgnoreCase("02")) {
                fillmap.put("pAcCatg", "Associate");
            }

            shareHoldersList = shareHolderFacade.getShareHolders(alphacode, frAcNo, toAcNo, ymd.format(dmy.parse(frDt)), ymd.format(dmy.parse(tillDt)), orderBy, accountCat, status, repType);
            if (shareHoldersList.isEmpty()) {
                message = "No value found in databse";
                return;
            }
            new ReportBean().openPdf(report, jrxml, new JRBeanCollectionDataSource(shareHoldersList), fillmap);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void folioFromMethod() {
        try {
            setMessage("");
            if (frFolionoShow == null || frFolionoShow.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill From Folio Number");
            }
            frAcNo = getOrgnBrCode() + CbsAcCodeConstant.SF_AC.getAcctCode() + CbsUtil.lPadding(6, Integer.parseInt(frFolionoShow)) + "01";
            //frAcNo = ftsPostRemote.getNewAccountNumber(frAcNo);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void folioToMethod() {
        try {
            setMessage("");
            if (toFolionoShow == null || toFolionoShow.equalsIgnoreCase("")) {
                throw new ApplicationException("Please fill To Folio Number");
            }
            toAcNo = getOrgnBrCode() + CbsAcCodeConstant.SF_AC.getAcctCode() + CbsUtil.lPadding(6, Integer.parseInt(toFolionoShow)) + "01";
            //    toAcNo = ftsPostRemote.getNewAccountNumber(toAcNo);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void setFocus() {

        if (repType.equalsIgnoreCase("1")) {
            this.focusId = "ddmember";
            this.disableFolio = true;
            this.disableBtn = false;
            this.disableBtnExcel = true;
            this.disableStatus = false;
        } else if (repType.equalsIgnoreCase("3")) {
            this.focusId = "ddmember";
            this.disableFolio = true;
            this.disableBtn = true;
            this.disableBtnExcel = false;
            this.disableStatus = false;
        } else if (repType.equalsIgnoreCase("4")) {
            this.disableFolio = true;
            this.disableBtn = false;
            this.disableStatus = true;
        } else {
            this.focusId = "txtfrmacno";
            this.disableFolio = false;
            this.disableBtn = false;
            this.disableBtnExcel = true;
            this.disableStatus = false;
        }

//        if (optall) {
//            this.focusId = "frDt";
//        } else {
//            this.focusId = "";
//        }
    }

    public void refreshPage() {
        this.setAlphacode("--select--");
        this.setMessage("");
        this.setFrAcNo("");
        this.setToAcNo("");
        this.setFrFolionoShow("");
        this.setToFolionoShow("");
        optall = false;
        optbet = false;
        this.setOrderBy("Select");
        this.focusId = "";
        this.repType = "0";
        this.status = "ALL";
        this.accountCat = "ALL";
        this.setTillDt(getTodayDate());
        this.setFrDt(getTodayDate());
        this.disableBtnExcel = true;
    }
}
