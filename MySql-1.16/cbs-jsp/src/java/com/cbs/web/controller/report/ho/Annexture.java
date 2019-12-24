/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.dto.report.RbiSossPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.HoReportFacadeRemote;
import com.cbs.facade.report.RbiMonthlyReportFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.utils.Util;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Sudhir
 */
public class Annexture extends BaseBean {

    private String message;
    private String fromDate;
    private String toDate;
    private String repOpt;
    private String flag;
    private List<SelectItem> repOptionList;
    private final String jndiHomeName = "HoReportFacade";
    private HoReportFacadeRemote hoRemote = null;
    private final String jndiName = "CommonReportMethods";
    private CommonReportMethodsRemote common = null;
    private final String jndiFtsPostingMgmtFacade = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostingMgmt = null;
    private final String monthlyJndiName = "RbiMonthlyReportFacade";
    private RbiMonthlyReportFacadeRemote monthlyRemote = null;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    NumberFormat formatter = new DecimalFormat("#.##");
    private String reportFormat;
    private List<SelectItem> reportFormatIn;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRepOpt() {
        return repOpt;
    }

    public void setRepOpt(String repOpt) {
        this.repOpt = repOpt;
    }

    public List<SelectItem> getRepOptionList() {
        return repOptionList;
    }

    public void setRepOptionList(List<SelectItem> repOptionList) {
        this.repOptionList = repOptionList;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
    public String getReportFormat() {
        return reportFormat;
    }
    public void setReportFormat(String reportFormat) {
        this.reportFormat = reportFormat;
    }
    public List<SelectItem> getReportFormatIn() {
        return reportFormatIn;
    }
    public void setReportFormatIn(List<SelectItem> reportFormatIn) {
        this.reportFormatIn = reportFormatIn;
    }

    public Annexture() {
        this.setMessage("");
        try {
            hoRemote = (HoReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiName);
            ftsPostingMgmt = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiFtsPostingMgmtFacade);
            monthlyRemote = (RbiMonthlyReportFacadeRemote) ServiceLocator.getInstance().lookup(monthlyJndiName);

            repOptionList = new ArrayList<SelectItem>();
            repOptionList.add(new SelectItem("", "--Select--"));
            repOptionList.add(new SelectItem("R", "Rs"));
            repOptionList.add(new SelectItem("T", "Thousand"));
            repOptionList.add(new SelectItem("L", "Lacs"));
            repOptionList.add(new SelectItem("C", "Crore"));
            
            reportFormatIn = new ArrayList<SelectItem>();
            reportFormatIn.add(new SelectItem("N", "In Detail"));
            reportFormatIn.add(new SelectItem("Y", "In Summary"));
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }

    public List<String> makeDateList(String frDt, String toDt) {
        List<String> dates = new ArrayList<String>();
        try {
            String tmpDt = "";
            dates.add(ymd.format(dmy.parse(frDt)));
            tmpDt = CbsUtil.dateAdd(ymd.format(dmy.parse(frDt)), 1);
            while (ymd.parse(tmpDt).compareTo(dmy.parse(toDt)) < 0) {
                dates.add(tmpDt);
                tmpDt = CbsUtil.dateAdd(tmpDt, 1);
            }
            if (dmy.parse(frDt).compareTo(dmy.parse(toDt)) != 0) {
                dates.add(ymd.format(dmy.parse(toDt)));
            }
        } catch (Exception ex) {
            flag = "false";
            this.setMessage(ex.getMessage());
        }
        return dates;
    }

    public String[] setDateParameter(List<String> dates) {
        String[] arr = new String[7];
        try {
            String p1Date = "", p2Date = "", p3Date = "", p4Date = "", p5Date = "", p6Date = "", p7Date = "";
            for (int i = 0; i < dates.size(); i++) {
                if (i == 0) {
                    p1Date = dates.get(i);
                } else if (i == 1) {
                    p2Date = dates.get(i);
                } else if (i == 2) {
                    p3Date = dates.get(i);
                } else if (i == 3) {
                    p4Date = dates.get(i);
                } else if (i == 4) {
                    p5Date = dates.get(i);
                } else if (i == 5) {
                    p6Date = dates.get(i);
                } else if (i == 6) {
                    p7Date = dates.get(i);
                }
            }
            arr[0] = p1Date;
            arr[1] = p2Date;
            arr[2] = p3Date;
            arr[3] = p4Date;
            arr[4] = p5Date;
            arr[5] = p6Date;
            arr[6] = p7Date;
        } catch (Exception ex) {
            flag = "false";
            this.setMessage(ex.getMessage());
        }
        return arr;
    }

    public void downloadPdf() {
        this.setMessage("");
        try {
            String msg = dateValidate();
            if (!msg.equalsIgnoreCase("true")) {
                this.setMessage(msg);
                return;
            }
            flag = "true";
            List<String> dates = makeDateList(this.fromDate, this.toDate);

            String report = "Annexture Report";
            List bankDetails = common.getBranchNameandAddress(getOrgnBrCode());
            String[] arr = Util.getReportOptionAndDescription(this.repOpt);

            Map fillParams = new HashMap();
            fillParams.put("pBankName", bankDetails.get(0).toString());
            fillParams.put("pBankAddress", bankDetails.get(1).toString());
            fillParams.put("pAmtIn", arr[1]);
            fillParams.put("report", report);
            fillParams.put("pPrintedDate", getTodayDate());

            String[] dtArr = setDateParameter(dates);
            fillParams.put("p1Date", dtArr[0]);
            fillParams.put("p2Date", dtArr[1]);
            fillParams.put("p3Date", dtArr[2]);
            fillParams.put("p4Date", dtArr[3]);
            fillParams.put("p5Date", dtArr[4]);
            fillParams.put("p6Date", dtArr[5]);
            fillParams.put("p7Date", dtArr[6]);

            List<RbiSossPojo> resultList = monthlyRemote.getForm1DetailsAsPerGlb(dates, new BigDecimal(arr[0]), "FORM1", getOrgnBrCode().equalsIgnoreCase("90")?"0A":getOrgnBrCode(), this.repOpt,this.reportFormat);
            if (!resultList.isEmpty()) {
//                for (RbiSossPojo pojo : resultList) {
//                    System.out.println("Desc::" + pojo.getDescription() + "::FAmount::" + pojo.getAmt() + "::SAmount::"
//                            + pojo.getSecondAmount() + "::TAmount::" + pojo.getThirdAmount() + "\n");
//                }
                new ReportBean().downloadPdf("Annexture_" + ymd.format(dmy.parse(this.fromDate)) + "_" + ymd.format(dmy.parse(this.toDate)), "all_rbi_report_master_annexture1", new JRBeanCollectionDataSource(resultList), fillParams);
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }

    public void printReport() {
        this.setMessage("");
        try {
            String msg = dateValidate();
            if (!msg.equalsIgnoreCase("true")) {
                this.setMessage(msg);
                return;
            }
            flag = "true";
            List<String> dates = makeDateList(this.fromDate, this.toDate);

            String report = "Annexture Report";
            List bankDetails = common.getBranchNameandAddress(getOrgnBrCode());
            String[] arr = Util.getReportOptionAndDescription(this.repOpt);

            Map fillParams = new HashMap();
            fillParams.put("pBankName", bankDetails.get(0).toString());
            fillParams.put("pBankAddress", bankDetails.get(1).toString());
            fillParams.put("pAmtIn", arr[1]);
            fillParams.put("report", report);
            fillParams.put("pPrintedDate", getTodayDate());

            String[] dtArr = setDateParameter(dates);
            fillParams.put("p1Date", dtArr[0]);
            fillParams.put("p2Date", dtArr[1]);
            fillParams.put("p3Date", dtArr[2]);
            fillParams.put("p4Date", dtArr[3]);
            fillParams.put("p5Date", dtArr[4]);
            fillParams.put("p6Date", dtArr[5]);
            fillParams.put("p7Date", dtArr[6]);

            List<RbiSossPojo> resultList = monthlyRemote.getForm1DetailsAsPerGlb(dates, new BigDecimal(arr[0]), "FORM1", getOrgnBrCode().equalsIgnoreCase("90")?"0A":getOrgnBrCode(), this.repOpt,this.reportFormat);
            if (!resultList.isEmpty()) {
//                for (RbiSossPojo pojo : resultList) {
//                    System.out.println("Desc::" + pojo.getDescription() + "::FAmount::" + pojo.getAmt() + "::SAmount::"
//                            + pojo.getSecondAmount() + "::TAmount::" + pojo.getThirdAmount() + "\n");
//                }
                new ReportBean().jasperReport("all_rbi_report_master_annexture1", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, report);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
            flag = "false";
        }
    }

    public String dateValidate() {
        this.setMessage("");
        try {
            if (this.fromDate == null
                    || this.toDate == null
                    || this.fromDate.equals("")
                    || this.toDate.equals("")) {
                flag = "false";
                return "Please fill from and to date.";
            }
            boolean result = new Validator().validateDate_dd_mm_yyyy(this.fromDate);
            if (result == false) {
                flag = "false";
                return "Please fill correct from date.";
            }
            result = new Validator().validateDate_dd_mm_yyyy(this.toDate);
            if (result == false) {
                flag = "false";
                return "Please fill correct to date.";
            }
            if (dmy.parse(this.fromDate).compareTo(dmy.parse(toDate)) > 0) {
                flag = "false";
                return "From date can not be greater than to date.";
            }
            if (dmy.parse(this.toDate).compareTo(dmy.parse(dmy.format(new Date()))) > 0) {
                flag = "false";
                return "To date can not be greater than current date.";
            }
            long dayDiff = CbsUtil.dayDiff(dmy.parse(this.fromDate), dmy.parse(this.toDate));
            if (dayDiff > 6) {
                flag = "false";
                return "Date difference should be of only 6 days.";
            }
            if (this.repOpt == null || this.repOpt.equalsIgnoreCase("")) {
                flag = "false";
                return "Please Select the Report Option";
            }
        } catch (Exception ex) {
            flag = "false";
            this.setMessage(ex.getMessage());
        }
        return "true";
    }

    public void refresh() {
        this.setMessage("");
        this.setFromDate("");
        this.setToDate("");
        this.setRepOpt("");
        this.setReportFormat("N");
    }

    public String exitFrm() {
        return "case1";
    }
}
