/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.dto.report.TdActiveReportPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
public class TdActive extends BaseBean {

    private String acType;
    private String reportType;
    private String errorMsg;
    private String brnCode;
    private String orderBy;
    private String rpName = null;
    private float frmRoi;
    private float toRoi;
    private int dd;
    private int mm;
    private int mm1;
    private int yyyy;
    private int yyyy1;
    private boolean flagPanel4;
    private boolean flagPanel2;
    private boolean flagPanel3;
    private boolean flagPanel5;
    private boolean flagPanel6;
    private Date frmyear = null;
    private Date toyear = null;
    private Date date = new Date();
    private String repDate = null;
    private Date asOnDate;
    private OtherReportFacadeRemote local;
    private CommonReportMethodsRemote common;
    private List<SelectItem> acTypeList;
    private SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private String branch;
    private List<SelectItem> branchList;

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isFlagPanel5() {
        return flagPanel5;
    }

    public void setFlagPanel5(boolean flagPanel5) {
        this.flagPanel5 = flagPanel5;
    }

    public Date getFrmyear() {
        return frmyear;
    }

    public void setFrmyear(Date frmyear) {
        this.frmyear = frmyear;
    }

    public Date getToyear() {
        return toyear;
    }

    public void setToyear(Date toyear) {
        this.toyear = toyear;
    }

    public int getYyyy1() {
        return yyyy1;
    }

    public void setYyyy1(int yyyy1) {
        this.yyyy1 = yyyy1;
    }

    public boolean isFlagPanel2() {
        return flagPanel2;
    }

    public void setFlagPanel2(boolean flagPanel2) {
        this.flagPanel2 = flagPanel2;
    }

    public boolean isFlagPanel3() {
        return flagPanel3;
    }

    public void setFlagPanel3(boolean flagPanel3) {
        this.flagPanel3 = flagPanel3;
    }

    public boolean isFlagPanel4() {
        return flagPanel4;
    }

    public void setFlagPanel4(boolean flagPanel4) {
        this.flagPanel4 = flagPanel4;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getMm1() {
        return mm1;
    }

    public void setMm1(int mm1) {
        this.mm1 = mm1;
    }

    public float getFrmRoi() {
        return frmRoi;
    }

    public void setFrmRoi(float frmRoi) {
        this.frmRoi = frmRoi;
    }

    public float getToRoi() {
        return toRoi;
    }

    public void setToRoi(float toRoi) {
        this.toRoi = toRoi;
    }

    public int getDd() {
        return dd;
    }

    public void setDd(int dd) {
        this.dd = dd;
    }

    public int getMm() {
        return mm;
    }

    public void setMm(int mm) {
        this.mm = mm;
    }

    public int getYyyy() {
        return yyyy;
    }

    public void setYyyy(int yyyy) {
        this.yyyy = yyyy;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public Date getAsOnDate() {
        return asOnDate;
    }

    public void setAsOnDate(Date asOnDate) {
        this.asOnDate = asOnDate;
    }

    public boolean isFlagPanel6() {
        return flagPanel6;
    }

    public void setFlagPanel6(boolean flagPanel6) {
        this.flagPanel6 = flagPanel6;
    }

    /**
     * Creates a new instance of TdActive
     */
    public TdActive() {
        try {
            //asOnDate = date;
            setAsOnDate(date);

            local = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            acTypeList = new ArrayList<SelectItem>();
            //List listAccTy = local.getAcctType();
            List listAccTy = common.getFDMSAcTypeList();
            Vector vtr = new Vector();
            if (!listAccTy.isEmpty()) {
                acTypeList.add(new SelectItem("All"));
                for (int i = 0; i < listAccTy.size(); i++) {
                    vtr = (Vector) listAccTy.get(i);
                    acTypeList.add(new SelectItem(vtr.get(0).toString()));
                }

            }

            List brnLst = new ArrayList();
            brnLst = common.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }

        } catch (Exception e) {
        }
        financialYear();
    }

    public void onblurReportType() {
        if (!reportType.equalsIgnoreCase("--Select--")) {
            switch (Integer.parseInt(reportType)) {
                case 0:
                    flagPanel2 = false;
                    flagPanel3 = false;
                    flagPanel4 = false;
                    flagPanel5 = false;
                    flagPanel6 = false;
                    break;
                case 1:
                    flagPanel2 = true;
                    flagPanel3 = false;
                    flagPanel4 = false;
                    flagPanel5 = false;
                    flagPanel6 = false;
                    break;
                case 2:
                    flagPanel2 = false;
                    flagPanel3 = true;
                    flagPanel4 = false;
                    flagPanel5 = false;
                    flagPanel6 = false;
                    break;
                case 3:
                    flagPanel2 = false;
                    flagPanel3 = false;
                    flagPanel4 = true;
                    flagPanel5 = false;
                    flagPanel6 = false;
                    break;
                case 4:
                    flagPanel2 = false;
                    flagPanel3 = false;
                    flagPanel4 = false;
                    flagPanel5 = true;
                    flagPanel6 = false;
                    break;
                case 5:
                    flagPanel2 = false;
                    flagPanel3 = false;
                    flagPanel4 = false;
                    flagPanel5 = false;
                    flagPanel6 = true;
                    break;
            }
        }
    }

    public String viewReport() {
        String bankName = "ALL", bankAddress = "ALL";
        try {
            rpName = "Active TD Report ";
            repDate = dmy.format(asOnDate);
            switch (Integer.parseInt(reportType)) {
                case 0:
                    rpName += "(All)";
                    break;
                case 1:
                    rpName += "(Duration Wise)";
                    break;
                case 2:
                    rpName += "(Maturity Wise)";
                    break;
                case 3:
                    rpName = rpName + "(Financial Year wise)";
                    repDate = dmy.format(frmyear) + " to " + dmy.format(toyear);
                    break;
                case 4:
                    rpName = rpName + "(Roi  Wise)";
                    break;
                case 5:
                    rpName = rpName + "(As On Date Wise)";
                    toyear = asOnDate;
                    break;
            }

            //public List<TdActiveReportPojo> getTdActiveReport(int option, String actype, int month, String brncode, String frmdt, String todt, int tempDays, float frmroi, float toroi, String orderby)
            List<TdActiveReportPojo> resultList = local.getTdActiveReport(Integer.parseInt(reportType), acType, mm1, yyyy1, this.getBranch(), ymd.format(frmyear), ymd.format(toyear), yyyy * 365 + mm * 30 + dd, frmRoi, toRoi, orderBy);
            if (!resultList.isEmpty()) {

                String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");

                if (!this.branch.equalsIgnoreCase("0A")) {
                    List dataList1 = common.getBranchNameandAddress(this.branch);
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                } else {
                    List dataList1 = common.getBranchNameandAddress("90");
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                }

                Map fillParams = new HashMap();
                fillParams.put("pReportName", rpName);
                fillParams.put("pPrintedDate", repDate);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pbankName", bankName);
                fillParams.put("pbankAddress", bankAddress);
                new ReportBean().jasperReport("TdActiveReport", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, "TD Active report");
                return "report";
            } else {
                errorMsg = "Data does not exists ";
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
        return null;
    }

    public void excelPrint() {
        try {
            List<TdActiveReportPojo> details = new ArrayList<TdActiveReportPojo>();

            details = local.getTdActiveReport(Integer.parseInt(reportType), acType, mm1, yyyy1, this.getBranch(), ymd.format(frmyear), ymd.format(toyear), yyyy * 365 + mm * 30 + dd, frmRoi, toRoi, orderBy);;
            if (!details.isEmpty()) {
                FastReportBuilder fastReportBuilder = generateDynamicReport();
                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(details), fastReportBuilder, "Branch Performance");
            } else {
                errorMsg = "Data does not exists ";
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public FastReportBuilder generateDynamicReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);
            AbstractColumn custId = ReportBean.getColumn("custId", String.class, "Cust Id", 70);
            AbstractColumn acNo = ReportBean.getColumn("acNo", String.class, "AcNo", 100);
            AbstractColumn custName = ReportBean.getColumn("custName", String.class, "Customer Name ", 250);
            AbstractColumn vouchNo = ReportBean.getColumn("vouchNo", Float.class, "R.T. No", 60);
            AbstractColumn receiptNo = ReportBean.getColumn("receiptNo", Float.class, "Receipt No", 60);
            AbstractColumn seqno = ReportBean.getColumn("seqno", Float.class, "Control no", 50);
            AbstractColumn prinAmt = ReportBean.getColumn("prinAmt", Float.class, "Print Amt", 80);
            AbstractColumn cumPrinAmt = ReportBean.getColumn("cumPrinAmt", Double.class, "Cum Print Amt", 80);
            AbstractColumn intAmt = ReportBean.getColumn("intAmt", Double.class, "Interest Amount", 80);
            AbstractColumn tds = ReportBean.getColumn("tds", Double.class, "Tds Amount", 80);
            AbstractColumn intOpt = ReportBean.getColumn("intOpt", String.class, "Int Opt", 80);
            AbstractColumn fddtexcel = ReportBean.getColumn("fddtexcel", String.class, "TD Dt(wef)", 75);
            AbstractColumn roi = ReportBean.getColumn("roi", Float.class, "ROI", 50);
            AbstractColumn matDtexcel = ReportBean.getColumn("matDtexcel", String.class, "Mat dt", 75);
            AbstractColumn autoRenew = ReportBean.getColumn("autoRenew", String.class, "Auto Renew", 40);
            AbstractColumn nextIntpDt = ReportBean.getColumn("nextIntpDt", String.class, "Next Int Pay Dt", 75);

            fastReport.addColumn(custId);
            width = width + custId.getWidth();

            fastReport.addColumn(acNo);
            width = width + acNo.getWidth();

            fastReport.addColumn(custName);
            width = width + custName.getWidth();

            fastReport.addColumn(vouchNo);
            width = width + vouchNo.getWidth();

            fastReport.addColumn(receiptNo);
            width = width + receiptNo.getWidth();

            fastReport.addColumn(seqno);
            width = width + seqno.getWidth();

            fastReport.addColumn(prinAmt);
            width = width + prinAmt.getWidth();

            fastReport.addColumn(cumPrinAmt);
            width = width + cumPrinAmt.getWidth();

            fastReport.addColumn(intAmt);
            width = width + intAmt.getWidth();

            fastReport.addColumn(tds);
            width = width + tds.getWidth();

            fastReport.addColumn(intOpt);
            width = width + intOpt.getWidth();

            fastReport.addColumn(fddtexcel);
            width = width + fddtexcel.getWidth();

            fastReport.addColumn(roi);
            width = width + roi.getWidth();

            fastReport.addColumn(matDtexcel);
            width = width + matDtexcel.getWidth();

            fastReport.addColumn(autoRenew);
            width = width + autoRenew.getWidth();

            fastReport.addColumn(nextIntpDt);
            width = width + nextIntpDt.getWidth();

            Page page = new Page(842, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("Td Active Report");

        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
        return fastReport;
    }

    public String pdfDownLoad() {
        String bankName = "ALL", bankAddress = "ALL";
        try {
            rpName = "Active TD Report ";
            repDate = dmy.format(asOnDate);
            switch (Integer.parseInt(reportType)) {
                case 0:
                    rpName += "(All)";
                    break;
                case 1:

                    rpName += "(Duration Wise)";
                    break;
                case 2:
                    rpName += "(Maturity Wise)";
                    break;
                case 3:
                    rpName = rpName + "(Financial Year wise)";
                    repDate = dmy.format(frmyear) + " to " + dmy.format(toyear);
                    break;
                case 4:
                    rpName = rpName + "(Roi  Wise)";
                    break;
                case 5:
                    rpName = rpName + "(As On Date Wise)";
                    toyear = asOnDate;
                    break;
            }

            //public List<TdActiveReportPojo> getTdActiveReport(int option, String actype, int month, String brncode, String frmdt, String todt, int tempDays, float frmroi, float toroi, String orderby)
            List<TdActiveReportPojo> resultList = local.getTdActiveReport(Integer.parseInt(reportType), acType, mm1, yyyy1, this.getBranch(), ymd.format(frmyear), ymd.format(toyear), yyyy * 365 + mm * 30 + dd, frmRoi, toRoi, orderBy);
            if (!resultList.isEmpty()) {

                String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");

                if (!this.branch.equalsIgnoreCase("0A")) {
                    List dataList1 = common.getBranchNameandAddress(this.branch);
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                } else {
                    List dataList1 = common.getBranchNameandAddress("90");
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }
                }

                Map fillParams = new HashMap();
                fillParams.put("pReportName", rpName);
                fillParams.put("pPrintedDate", repDate);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pbankName", bankName);
                fillParams.put("pbankAddress", bankAddress);

                new ReportBean().openPdf("TD Active report_" + ymd.format(dmy.parse(getTodayDate())), "TdActiveReport", new JRBeanCollectionDataSource(resultList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", "TD Active report");

                return "report";
            } else {
                errorMsg = "Data does not exists ";
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
        return null;
    }

    private boolean valiadte() {
        switch (Integer.parseInt(reportType)) {
            case 0:
                break;
            case 1:
                if (yyyy < 1900 || yyyy > 2337) {
                    errorMsg = "Please enter right year";
                    return false;
                } else if (mm < 1 || mm > 13) {
                    errorMsg = "Please enter right month";
                    return false;
                } else if (dd < 1 || dd > 31) {
                    errorMsg = "Please enter right date";
                    return false;
                }
                break;
            case 2:
                break;
            case 4:
                if (frmRoi < 0) {
                    errorMsg = "Please enter right from roi";
                    return false;
                }
                if (toRoi > 99) {
                    errorMsg = "Please enter right to roi";
                    return false;
                }
                break;
        }
        return true;
    }

    private void financialYear() {
        Calendar cal = Calendar.getInstance();
        Calendar calfrm = Calendar.getInstance();
        Calendar calto = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        if (month < 3) {
            calfrm.set(Calendar.MONTH, 3);
            calfrm.set(Calendar.DAY_OF_MONTH, 1);
            calfrm.set(Calendar.YEAR, (cal.get(Calendar.YEAR) - 1));
            calto.set(Calendar.MONTH, 2);
            calto.set(Calendar.DAY_OF_MONTH, 31);
            calto.set(Calendar.YEAR, cal.get(Calendar.YEAR));
            frmyear = calfrm.getTime();
            toyear = calto.getTime();
        } else {
            calfrm.set(Calendar.MONTH, 3);
            calfrm.set(Calendar.DAY_OF_MONTH, 1);
            calfrm.set(Calendar.YEAR, cal.get(Calendar.YEAR));
            calto.set(Calendar.MONTH, 2);
            calto.set(Calendar.DAY_OF_MONTH, 31);
            calto.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);
            frmyear = calfrm.getTime();
            toyear = calto.getTime();
        }

    }

    public void refreshForm() {
        reportType = "--Select--";
        flagPanel2 = false;
        flagPanel3 = false;
        flagPanel4 = false;
        flagPanel5 = false;
        flagPanel6 = false;
        errorMsg = "";
    }

    public String exitAction() {
        refreshForm();
        return "case1";
    }
}