/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.deaf;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.facade.report.DeafMgmtFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.pojo.deaf.DeafMarkPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class UnclaimedAccountInfo extends BaseBean {

    private String message;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String acNature;
    private List<SelectItem> acNatureList;
    private String month;
    private List<SelectItem> monthList;
    private String year;
    TdReceiptManagementFacadeRemote RemoteCode;
    private DeafMgmtFacadeRemote deafRemote;
    private CommonReportMethodsRemote common = null;
    private DDSReportFacadeRemote ddsRemote;
    List<DeafMarkPojo> repoprtList = new ArrayList<DeafMarkPojo>();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public UnclaimedAccountInfo() {
        try {
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            deafRemote = (DeafMgmtFacadeRemote) ServiceLocator.getInstance().lookup("DeafMgmtFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");

            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

            monthList = new ArrayList<SelectItem>();
            monthList.add(new SelectItem("0", "--Select--"));
            Map<Integer, String> monthMap = CbsUtil.getAllMonths();
            System.out.println("Month List Size-->" + monthMap.size());
            Set<Map.Entry<Integer, String>> mapSet = monthMap.entrySet();
            Iterator<Map.Entry<Integer, String>> mapIt = mapSet.iterator();
            while (mapIt.hasNext()) {
                Map.Entry<Integer, String> mapEntry = mapIt.next();
                monthList.add(new SelectItem(mapEntry.getKey(), mapEntry.getValue()));
            }

            getacNature();

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getacNature() {
        try {
            acNatureList = new ArrayList<SelectItem>();
            acNatureList.add(new SelectItem("0", "--Select--"));
            List acNtureList = ddsRemote.getAccountNatureClassification("'C','B'");
            if (!acNtureList.isEmpty()) {
                for (int i = 0; i < acNtureList.size(); i++) {
                    Vector vec = (Vector) acNtureList.get(i);
                    acNatureList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String validateField(String month, String year) {
        String result = "true";
        Pattern p = Pattern.compile("[0-9]*");
        try {
            if (month == null || month.equals("0")) {
                return result = "Please select month !";
            }
            if (year == null || year.equals("")) {
                return result = "Year can not be blank.";
            }
            Matcher m = p.matcher(year);
            if (m.matches() != true) {
                return result = "Please fill proper Year.";
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
        return result;
    }

    public void printAction() {
        try {
            if (validateField(this.month, this.year).equals("true")) {

                String generatedDt = dmy.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.month),
                        Integer.parseInt(this.year)));
                if (!new Validator().validateDate_dd_mm_yyyy(generatedDt)) {
                    this.setMessage("Please fill proper month and year.");
                    return;
                }

                repoprtList = deafRemote.getUnAcnoInfoData(branchCode, acNature, ymd.format(dmy.parse(generatedDt)));;
                if (repoprtList.isEmpty()) {
                    throw new ApplicationException("Data does not exit");
                }

                String reportName = "Unclaimed Account Information";
                Map fillParams = new HashMap();
                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBranchAddress", brNameAndAddList.get(1).toString());
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportDt", "");
                fillParams.put("pReportName", reportName);
                new ReportBean().jasperReport("UnclaimedAccountInfo", "text/html",
                        new JRBeanCollectionDataSource(repoprtList), fillParams, "Unclaimed Account Information");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).
                        sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            } else {
                this.setMessage(validateField(this.month, this.year));
                return;
            }

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public FastReportBuilder excelPrint() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {

            if (validateField(this.month, this.year).equals("true")) {

                String generatedDt = dmy.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.month),
                        Integer.parseInt(this.year)));
                if (!new Validator().validateDate_dd_mm_yyyy(generatedDt)) {
                    this.setMessage("Please fill proper month and year.");
                    return null;
                }

                repoprtList = deafRemote.getUnAcnoInfoData(branchCode, acNature, ymd.format(dmy.parse(generatedDt)));;
                if (repoprtList.isEmpty()) {
                    throw new ApplicationException("Data does not exit");
                }

                int width = 0;
                Style style = new Style();
                style.setHorizontalAlign(HorizontalAlign.RIGHT);
                style.setBorder(Border.THIN);

                AbstractColumn acNo = ReportBean.getColumn("acNo", String.class, "A/c . Number", 100);
                AbstractColumn custName = ReportBean.getColumn("custName", String.class, "Customer Name", 200);
                AbstractColumn address = ReportBean.getColumn("address", String.class, "Address", 250);
                //AbstractColumn deafDate = ReportBean.getColumn("deafDate", String.class, "Last txn Date", 90);
                // AbstractColumn deafAmt = ReportBean.getColumn("deafAmt", BigDecimal.class, "Amount", 150);

                fastReport.addColumn(acNo);
                width = width + acNo.getWidth();

                fastReport.addColumn(custName);
                width = width + custName.getWidth();

                fastReport.addColumn(address);
                width = width + address.getWidth();

//                fastReport.addColumn(deafDate);
//                width = width + deafDate.getWidth();

//            fastReport.addColumn(bal);
//            bal.setStyle(style);
//            width = width + 2 * bal.getWidth();

//                fastReport.addColumn(deafAmt);
//                deafAmt.setStyle(style);
//                width = width + deafAmt.getWidth();

                Page page = new Page(842, width, false);
                fastReport.setMargins(0, 0, 0, 0);
                fastReport.setPageSizeAndOrientation(page);
                fastReport.setTitle("Unclaimed Account Information");

                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(repoprtList), fastReport, "Unclaimed Account Information");
            } else {
                this.setMessage(validateField(this.month, this.year));
                return null;
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return fastReport;
    }

    public void refreshAction() {
        this.setMessage("");
        this.setMonth("0");
        this.setYear("");
    }

    public String exitAction() {
        refreshAction();
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public List<SelectItem> getAcNatureList() {
        return acNatureList;
    }

    public void setAcNatureList(List<SelectItem> acNatureList) {
        this.acNatureList = acNatureList;
    }
}
