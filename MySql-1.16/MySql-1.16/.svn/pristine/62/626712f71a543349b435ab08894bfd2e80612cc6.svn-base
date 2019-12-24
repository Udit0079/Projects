/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.HoReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.pojo.ExpenditureBalDayWisePojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.ho.DepositLoanAnnexturePojo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class DepositLoanAnnexure extends BaseBean {

    private String msg;
    private String reportType;
    private List<SelectItem> reportTypeList;
    private String calDate;
    private Date dt = new Date();
    private String branch;
    private List<SelectItem> branchList;
    private HoReportFacadeRemote hoRemote = null;
    private TdReceiptManagementFacadeRemote RemoteCode;
    private CommonReportMethodsRemote common = null;
    private SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");

    public DepositLoanAnnexure() {
        try {
            setCalDate(dmyFormat.format(dt));
            hoRemote = (HoReportFacadeRemote) ServiceLocator.getInstance().lookup("HoReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            RemoteCode = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            List brncode = new ArrayList();
            brncode = RemoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("0", "--Select--"));
            reportTypeList.add(new SelectItem("D", "DEPOSIT"));
            reportTypeList.add(new SelectItem("L", "LOAN"));
            reportTypeList.add(new SelectItem("N", "NO OF ACCOUNTS"));
            reportTypeList.add(new SelectItem("SO", "SUMMARY OF ACCOUNT OPENED"));
            reportTypeList.add(new SelectItem("SC", "SUMMARY OF ACCOUNT CLOSED"));
            reportTypeList.add(new SelectItem("NP", "NET ACCOUNT OPENED"));


        } catch (Exception ex) {
            setMsg(ex.getMessage());
        }
    }

    public void downloadPdf() {
        setMsg("");
        try {
            List<ExpenditureBalDayWisePojo> repList = hoRemote.getLoanDepositAnnextureData(branch, reportType, ymdFormat.format(dmyFormat.parse(calDate)));
            if (repList.isEmpty()) {
                throw new ApplicationException("Data does not exist!");
            }
            String headerValue = "", valueIn = "";
            if (reportType.equalsIgnoreCase("D")) {
                valueIn = "Rs. in Lacs";
                headerValue = "DEPOSIT ANALYSIS " + CbsUtil.getMonthName(Integer.parseInt(ymdFormat.format(dmyFormat.parse(calDate)).substring(4, 6))).toUpperCase() + "-" + ymdFormat.format(dmyFormat.parse(calDate)).substring(0, 4);
            } else if (reportType.equalsIgnoreCase("L")) {
                valueIn = "Rs. in Lacs";
                headerValue = "ADVANCE ANALYSIS " + CbsUtil.getMonthName(Integer.parseInt(ymdFormat.format(dmyFormat.parse(calDate)).substring(4, 6))).toUpperCase() + "-" + ymdFormat.format(dmyFormat.parse(calDate)).substring(0, 4);
            } else if (reportType.equalsIgnoreCase("N")) {
                headerValue = "NO OF ACCOUNTS " + CbsUtil.getMonthName(Integer.parseInt(ymdFormat.format(dmyFormat.parse(calDate)).substring(4, 6))).toUpperCase() + "-" + ymdFormat.format(dmyFormat.parse(calDate)).substring(0, 4);
            } else if (reportType.equalsIgnoreCase("SO")) {
                headerValue = "SUMMARY OF ACCOUNT OPENED IN " + CbsUtil.getMonthName(Integer.parseInt(ymdFormat.format(dmyFormat.parse(calDate)).substring(4, 6))).toUpperCase() + "-" + ymdFormat.format(dmyFormat.parse(calDate)).substring(0, 4);
            } else if (reportType.equalsIgnoreCase("SC")) {
                headerValue = "SUMMARY OF ACCOUNT CLOSED IN " + CbsUtil.getMonthName(Integer.parseInt(ymdFormat.format(dmyFormat.parse(calDate)).substring(4, 6))).toUpperCase() + "-" + ymdFormat.format(dmyFormat.parse(calDate)).substring(0, 4);
            } else if (reportType.equalsIgnoreCase("NP")) {
                headerValue = "ACCOUNT SUMMARY OF " + CbsUtil.getMonthName(Integer.parseInt(ymdFormat.format(dmyFormat.parse(calDate)).substring(4, 6))).toUpperCase() + "-" + ymdFormat.format(dmyFormat.parse(calDate)).substring(0, 4) + " NET OPENED";
            }
            String bankName = "";
            String bankAddress = "";
            List dataList1 = common.getBranchNameandAddress(getOrgnBrCode());
            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }
            Map pamMap = new HashMap();
            pamMap.put("pValueIn", valueIn);
            pamMap.put("pHeader", headerValue);
            pamMap.put("pBankName", bankName);
            pamMap.put("pBankAddress", bankAddress);
            pamMap.put("pPrintedDate", dmyFormat.parse(calDate));
            pamMap.put("pPrintedBy", this.getUserName());
            pamMap.put("pReportType", this.reportType);

            pamMap.put("SUBREPORT_DIR", getHttpSession().getServletContext().getRealPath("/WEB-INF/reports") + "/");

            List<DepositLoanAnnexturePojo> jrxmlList = new ArrayList<DepositLoanAnnexturePojo>();     //Actual List To Print
            if (reportType.equalsIgnoreCase("D") || reportType.equalsIgnoreCase("L") || reportType.equalsIgnoreCase("N")) {
                DepositLoanAnnexturePojo jrxmlPojo = new DepositLoanAnnexturePojo();
                jrxmlPojo.setDepositAnnexureList(new JRBeanCollectionDataSource(repList));
                jrxmlPojo.setDepositAnnexureListSummary(new JRBeanCollectionDataSource(repList));
                List<ExpenditureBalDayWisePojo> repListCasa = hoRemote.getLoanDepositAnnextureDataSummary(branch, reportType, ymdFormat.format(dmyFormat.parse(calDate)));
//                if (repList.isEmpty()) {
//                    throw new ApplicationException("Data does not exist!");
//                }
                jrxmlPojo.setDepositAnnexureListCasa(new JRBeanCollectionDataSource(repListCasa));
                jrxmlList.add(jrxmlPojo);
            }
            // CbsUtil.getMonthName(Integer.parseInt(ymdFormat.format(dmyFormat.parse(calDate)).substring(4, 6)));
//            if (reportType.equalsIgnoreCase("D")) {
//                pamMap.put("pHeader", "DEPOSIT " + CbsUtil.getMonthName(Integer.parseInt(ymdFormat.format(dmyFormat.parse(calDate)).substring(4, 6))).toUpperCase() + "-" + ymdFormat.format(dmyFormat.parse(calDate)).substring(0, 4));
//            } else if (reportType.equalsIgnoreCase("L")) {
//                pamMap.put("pHeader", "ADVANCE " + CbsUtil.getMonthName(Integer.parseInt(ymdFormat.format(dmyFormat.parse(calDate)).substring(4, 6))).toUpperCase() + "-" + ymdFormat.format(dmyFormat.parse(calDate)).substring(0, 4));
//            } else if (reportType.equalsIgnoreCase("N")) {
//                pamMap.put("pHeader", "NO OF ACCOUNTS " + CbsUtil.getMonthName(Integer.parseInt(ymdFormat.format(dmyFormat.parse(calDate)).substring(4, 6))).toUpperCase() + "-" + ymdFormat.format(dmyFormat.parse(calDate)).substring(0, 4));
//            } else if (reportType.equalsIgnoreCase("SO")) {
//                pamMap.put("pHeader", "SUMMARY OF ACCOUNT OPENED IN " + CbsUtil.getMonthName(Integer.parseInt(ymdFormat.format(dmyFormat.parse(calDate)).substring(4, 6))).toUpperCase() + "-" + ymdFormat.format(dmyFormat.parse(calDate)).substring(0, 4));
//            } else if (reportType.equalsIgnoreCase("SC")) {
//                pamMap.put("pHeader", "SUMMARY OF ACCOUNT CLOSED IN " + CbsUtil.getMonthName(Integer.parseInt(ymdFormat.format(dmyFormat.parse(calDate)).substring(4, 6))).toUpperCase() + "-" + ymdFormat.format(dmyFormat.parse(calDate)).substring(0, 4));
//            } else if (reportType.equalsIgnoreCase("NP")) {
//                pamMap.put("pHeader", "ACCOUNT SUMMARY OF " + CbsUtil.getMonthName(Integer.parseInt(ymdFormat.format(dmyFormat.parse(calDate)).substring(4, 6))).toUpperCase() + "-" + ymdFormat.format(dmyFormat.parse(calDate)).substring(0, 4) + " NET OPENED");
//            }
            if (reportType.equalsIgnoreCase("SO") || reportType.equalsIgnoreCase("SC")) {
                new ReportBean().downloadPdf(headerValue, "SummaryOfAccountOpenClose", new JRBeanCollectionDataSource(repList), pamMap);
            } else if (reportType.equalsIgnoreCase("NP")) {
                new ReportBean().downloadPdf(headerValue, "SummaryOfNetAcno", new JRBeanCollectionDataSource(repList), pamMap);
            } else if (reportType.equalsIgnoreCase("N")) {
                new ReportBean().downloadPdf(headerValue, "depositAnnexure2", new JRBeanCollectionDataSource(jrxmlList), pamMap);
            } else {
                new ReportBean().downloadPdf(headerValue, "depositAnnexure2", new JRBeanCollectionDataSource(jrxmlList), pamMap);
            }
            repList = new ArrayList<>();
        } catch (Exception ex) {
            setMsg(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        setMsg("");
    }

    public String btnExitAction() {
        return "case1";
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    public String getCalDate() {
        return calDate;
    }

    public void setCalDate(String calDate) {
        this.calDate = calDate;
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

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }
}
