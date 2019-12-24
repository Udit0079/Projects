/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.dto.report.StatemenrtOfRecoveriesPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.report.ShareReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author Athar Reza
 */
public class StatementOfRecoveries extends BaseBean {

    private String message;
    private String area;
    private List<SelectItem> areaList;
    private String reportOption;
    private List<SelectItem> reportOptionList;
    private String tillDate;
    private Date date = new Date();
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String month;
    private List<SelectItem> monthList;
    private String year;
    private ShareReportFacadeRemote horfr;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private LoanReportFacadeRemote RemoteLocal;
    private CommonReportMethodsRemote common;
    TdReceiptManagementFacadeRemote RemoteCode;
    private List<StatemenrtOfRecoveriesPojo> reportList = new ArrayList<StatemenrtOfRecoveriesPojo>();

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<SelectItem> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<SelectItem> areaList) {
        this.areaList = areaList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTillDate() {
        return tillDate;
    }

    public void setTillDate(String tillDate) {
        this.tillDate = tillDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<SelectItem> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<SelectItem> monthList) {
        this.monthList = monthList;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getReportOption() {
        return reportOption;
    }

    public void setReportOption(String reportOption) {
        this.reportOption = reportOption;
    }

    public List<SelectItem> getReportOptionList() {
        return reportOptionList;
    }

    public void setReportOptionList(List<SelectItem> reportOptionList) {
        this.reportOptionList = reportOptionList;
    }

    public StatementOfRecoveries() {
        try {
            tillDate = dmy.format(date);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            RemoteLocal = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            horfr = (ShareReportFacadeRemote) ServiceLocator.getInstance().lookup("ShareReportFacade");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

            areaList = new ArrayList<SelectItem>();
            areaList.add(new SelectItem("S", "--Select--"));
            List result = horfr.getAreaWiseList(this.getOrgnBrCode());
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector ele = (Vector) result.get(i);
                    areaList.add(new SelectItem(ele.get(0).toString()));
                }
            }

            monthList = new ArrayList<SelectItem>();
            //Set all months
            monthList.add(new SelectItem("0", "--Select--"));
            Map<Integer, String> monthMap = CbsUtil.getAllMonths();
            System.out.println("Month List Size-->" + monthMap.size());
            Set<Map.Entry<Integer, String>> mapSet = monthMap.entrySet();
            Iterator<Map.Entry<Integer, String>> mapIt = mapSet.iterator();
            while (mapIt.hasNext()) {
                Map.Entry<Integer, String> mapEntry = mapIt.next();
                monthList.add(new SelectItem(mapEntry.getKey(), mapEntry.getValue()));
            }

            reportOptionList = new ArrayList<>();
            reportOptionList.add(new SelectItem("0", "--Select--"));
            reportOptionList.add(new SelectItem("D", "Detail"));
            reportOptionList.add(new SelectItem("S", "Summary"));
            reportOptionList.add(new SelectItem("E", "Excel"));

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void viewReport() {
        setMessage("");
        String branchName = "", address = "";
        String report = "Statement of Recoveries Report";

        try {
            if (area.equalsIgnoreCase("S")) {
                setMessage("Please select Area !");
                return;
            }
            if (this.month == null || this.month.equals("0")) {
                this.setMessage("Please select Month.");
                return;
            }
            if (this.year == null || year.equals("") || year.trim().length() != 4) {
                this.setMessage("Please fill proper Year.");
                return;
            }
            Pattern p = Pattern.compile("[0-9]*");
            Matcher m = p.matcher(this.year);
            if (m.matches() != true) {
                this.setMessage("Please fill proper Year in numeric");
                return;
            }
            if (reportOption == null || reportOption.equalsIgnoreCase("0")) {
                setMessage("Please select Report Option !");
                return;
            }
            tillDate = dmy.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.month), Integer.parseInt(this.year)));
            List brnAddrList = new ArrayList();
            brnAddrList = common.getBranchNameandAddress(getOrgnBrCode());
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }
            String tillDt = ymd.format(dmy.parse(tillDate));
            String month = CbsUtil.getMonthName(Integer.parseInt(tillDt.substring(4, 6)));
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAddr", address);
            fillParams.put("pReportDt", this.tillDate);
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pArea", this.area);
            fillParams.put("pMonth", month);
            reportList = RemoteLocal.getStatementRecoveriesData(area, tillDt, getOrgnBrCode(), reportOption);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does exist !!!");
            }
            String jrxml = "Recoeries_stmt";
            if (reportOption.equalsIgnoreCase("S")) {
                jrxml = "Recoeries_stmt_Summary";
            }
//            ComparatorChain chObj = new ComparatorChain();
//            chObj.addComparator(new SortedByCustIdRecStmt());
//            Collections.sort(reportList, chObj);

//            for (int i = 0; i < reportList.size(); i++) {
//                StatemenrtOfRecoveriesPojo val = reportList.get(i);
//                System.out.println("Cust Id-->" + val.getCustId() + "Acno ->" + val.getLoanAcNo() + "name ->" + val.getCustName() + "father Name ->" + val.getFatherName() + "Out Amount->" + val.getOutStdBal());
//            }
            new ReportBean().jasperReport(jrxml, "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Statement of Recoveries Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void printPdf() {
        setMessage("");
        String branchName = "", address = "";
        String report = "Statement of Recoveries Report";
        try {
            if (area.equalsIgnoreCase("S")) {
                setMessage("Please select Area !");
                return;
            }
            if (this.month == null || this.month.equals("0")) {
                this.setMessage("Please select Month.");
                return;
            }
            if (this.year == null || year.equals("") || year.trim().length() != 4) {
                this.setMessage("Please fill proper Year.");
                return;
            }
            Pattern p = Pattern.compile("[0-9]*");
            Matcher m = p.matcher(this.year);
            if (m.matches() != true) {
                this.setMessage("Please fill proper Year in numeric");
                return;
            }
            if (reportOption == null || reportOption.equalsIgnoreCase("0")) {
                setMessage("Please select Report Option !");
                return;
            }
            tillDate = dmy.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.month), Integer.parseInt(this.year)));
            List brnAddrList = new ArrayList();
            brnAddrList = common.getBranchNameandAddress(getOrgnBrCode());
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }
            String tillDt = ymd.format(dmy.parse(tillDate));
            String month = CbsUtil.getMonthName(Integer.parseInt(tillDt.substring(4, 6)));
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAddr", address);
            fillParams.put("pReportDt", this.tillDate);
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pArea", this.area);
            fillParams.put("pMonth", month);
            reportList = RemoteLocal.getStatementRecoveriesData(area, tillDt, getOrgnBrCode(), reportOption);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does exist !!!");
            }
            String jrxml = "Recoeries_stmt";
            if (reportOption.equalsIgnoreCase("S")) {
                jrxml = "Recoeries_stmt_Summary";
            }
            new ReportBean().openPdf("Statement of Recoveries Report", jrxml, new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void downloadExcel() {

        try {
            if (area.equalsIgnoreCase("S")) {
                setMessage("Please select Area !");
                return;
            }
            if (this.month == null || this.month.equals("0")) {
                this.setMessage("Please select Month.");
                return;
            }
            if (this.year == null || year.equals("") || year.trim().length() != 4) {
                this.setMessage("Please fill proper Year.");
                return;
            }
            Pattern p = Pattern.compile("[0-9]*");
            Matcher m = p.matcher(this.year);
            if (m.matches() != true) {
                this.setMessage("Please fill proper Year in numeric");
                return;
            }
            if (reportOption == null || reportOption.equalsIgnoreCase("0")) {
                setMessage("Please select Report Option !");
                return;
            }
            tillDate = dmy.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.month), Integer.parseInt(this.year)));
            String tillDt = ymd.format(dmy.parse(tillDate));
            reportList = RemoteLocal.getStatementRecoveriesData(area, tillDt, getOrgnBrCode(), reportOption);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does exist !!!");
            }

            FastReportBuilder gstrFormat = generateExcelFormat();
            new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(reportList), gstrFormat, "Recoeries_stmt_Summary" + "_" + tillDt);


        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public FastReportBuilder generateExcelFormat() {
        FastReportBuilder fastReportBuilder = new FastReportBuilder();
        try {
            int width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn custId = ReportBean.getColumn("custId", String.class, "Bank CID", 60);
            AbstractColumn empId = ReportBean.getColumn("empId", String.class, "CSI EMP ID", 70);
            AbstractColumn custName = ReportBean.getColumn("custName", String.class, "Name of Member", 180);
            AbstractColumn fatherName = ReportBean.getColumn("fatherName", String.class, "Father Name", 180);
            AbstractColumn outStdBal = ReportBean.getColumn("outStdBal", Double.class, "GRAND TOTAL BANK ALL LOAN BALANCE", 80);
            AbstractColumn totalAmt = ReportBean.getColumn("totalAmt", Double.class, "TOTAL MONTHLY INSTALLMENT AMOUNT", 90);

            fastReportBuilder.addColumn(custId);
            width = width + custId.getWidth();

            fastReportBuilder.addColumn(empId);
            width = width + empId.getWidth();

            fastReportBuilder.addColumn(custName);
            width = width + custName.getWidth();

            fastReportBuilder.addColumn(fatherName);
            width = width + fatherName.getWidth();

            fastReportBuilder.addColumn(outStdBal);
            outStdBal.setStyle(style);
            width = width + outStdBal.getWidth();

            fastReportBuilder.addColumn(totalAmt);
            totalAmt.setStyle(style);
            width = width + totalAmt.getWidth();

            Page page = new Page(1300, width, false);
            fastReportBuilder.setMargins(0, 0, 0, 0);
            fastReportBuilder.setPageSizeAndOrientation(page);
            fastReportBuilder.setTitle("Statement of Recoveries");

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return fastReportBuilder;
    }

    public void refreshPage() {
        try {
            setMessage("");
            tillDate = dmy.format(date);
            this.setArea("S");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
}
