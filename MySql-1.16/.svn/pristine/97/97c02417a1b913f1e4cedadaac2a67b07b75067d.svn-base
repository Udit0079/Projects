/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.investment;

import com.cbs.facade.ho.investment.InvestmentReportMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.HoReportFacadeRemote;
import com.cbs.pojo.DailyCallMoneyPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class DailyCallAndNoticeMoneyOperations extends BaseBean {

    private String errorMsg;
    public String firstAltDt;
    private String secondAltDt;
    private String thirdAltDt;
    private Integer fridayListSize;
    private String reportIn;
    private List<SelectItem> reportInList;
    private Date date = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private final String jndiHomeName = "HoReportFacade";
    private HoReportFacadeRemote hoRemote = null;
    private InvestmentReportMgmtFacadeRemote remoteObj = null;
    private final String jndiHomeNameInv = "InvestmentReportMgmtFacade";
    private CommonReportMethodsRemote common;
    List<DailyCallMoneyPojo> reportList = new ArrayList<DailyCallMoneyPojo>();

    public DailyCallAndNoticeMoneyOperations() {
        reportInList = new ArrayList<SelectItem>();
        try {
            hoRemote = (HoReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            remoteObj = (InvestmentReportMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameInv);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            reportInList.add(new SelectItem("R", "Rs."));
            reportInList.add(new SelectItem("T", "THOUSANDS"));
            reportInList.add(new SelectItem("L", "LACS"));
            reportInList.add(new SelectItem("C", "CRORES"));
            refresh();
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public String validateFirstAltFridayDt(String fAltDt) {
        String msg = "true";
        try {
            if (this.firstAltDt == null || this.firstAltDt.equals("")) {
                return "Please fill First Alternate Friday.";
            }
            boolean result = new Validator().validateDate_dd_mm_yyyy(this.firstAltDt);
            if (result == false) {
                return "Please fill correct First Alternate Friday.";
            }
            boolean isFriday = hoRemote.isFridayDate(ymd.format(dmy.parse(this.firstAltDt)));
            if (!isFriday) {
                return "Please fill correct First Alternate Friday.";
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
        return msg;
    }

    public void generateFridayDate() {
        this.setErrorMsg("");
        secondAltDt = "";
        thirdAltDt = "";
        try {
            fridayListSize = 1;
            Date lastMonthDtOfFriday = CbsUtil.getLastDateOfMonth(dmy.parse(this.firstAltDt));
            List tempFridayList = hoRemote.getAllAlternateFriday(ymd.format(dmy.parse(this.firstAltDt)), ymd.format(lastMonthDtOfFriday));
            if (!tempFridayList.isEmpty()) {
                Integer tempListSize = tempFridayList.size();
                fridayListSize = fridayListSize + tempListSize;
                if (tempListSize == 1) {
                    Vector element = (Vector) tempFridayList.get(0);
                    secondAltDt = element.get(0).toString();
                } else if (tempListSize == 2) {
                    for (int i = 0; i < tempFridayList.size(); i++) {
                        Vector element = (Vector) tempFridayList.get(i);
                        if (i == 0) {
                            secondAltDt = element.get(0).toString();
                        } else if (i == 1) {
                            thirdAltDt = element.get(0).toString();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void downloadPdf() {
        setErrorMsg("");
        String bnkName = "", bnkAddress = "";
        try {
            String report = "Daily call and notice operations";
            //generateFridayDate();
            String msg = validateFirstAltFridayDt(this.firstAltDt);
            if (!msg.equalsIgnoreCase("true")) {
                this.setErrorMsg(msg);
                return;
            }
            String preFirstAltDt = CbsUtil.dateAdd(ymd.format(dmy.parse(firstAltDt)), -13);

            reportList = remoteObj.getDailyCallMoneydata(preFirstAltDt, ymd.format(dmy.parse(firstAltDt)), reportIn);
            List bnkList = common.getBranchNameandAddress(getOrgnBrCode());
            if (bnkList.size() > 0) {
                bnkName = (String) bnkList.get(0);
                bnkAddress = (String) bnkList.get(1);
            }
            String repIns = "";
            if (reportIn.equalsIgnoreCase("R")) {
                repIns = "Rs. in rupees";
            } else if (reportIn.equalsIgnoreCase("T")) {
                repIns = "Rs. in thousand";
            } else if (reportIn.equalsIgnoreCase("L")) {
                repIns = "Rs. in lakh";
            } else if (reportIn.equalsIgnoreCase("C")) {
                repIns = "Rs. in crores";
            }

            List<DailyCallMoneyPojo> paramList = remoteObj.getDailyCallMoneyAvgData(preFirstAltDt, ymd.format(dmy.parse(firstAltDt)), reportIn);
            DailyCallMoneyPojo pVal = paramList.get(0);

            Map fillParams = new HashMap();
            fillParams.put("pbnkName", bnkName);
            fillParams.put("pbnkAddress", bnkAddress);
            fillParams.put("pReportName", report);
            fillParams.put("pFirstAltDt", firstAltDt);
            fillParams.put("pReportIns", repIns);

            fillParams.put("pLentAvRate", pVal.getLentAvRate());
            fillParams.put("pLentMaxRate", pVal.getLentMaxRate());
            fillParams.put("pLentMinRate", pVal.getLentMinRate());
            fillParams.put("pLentDaily", pVal.getLentDaily());

            fillParams.put("pB1", pVal.getB1());
            fillParams.put("pB2", pVal.getB2());
            fillParams.put("pB3", pVal.getB3());
            fillParams.put("pB4", pVal.getB4());

            new ReportBean().openPdf("Daily call and notice operations_" + ymd.format(dmy.parse(firstAltDt)), "DailyCallMoney_Operations", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void refresh() {
        setErrorMsg("");
        setFirstAltDt("");
        setReportIn("R");
    }

    public String exitAction() {
        return "case1";
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getFirstAltDt() {
        return firstAltDt;
    }

    public void setFirstAltDt(String firstAltDt) {
        this.firstAltDt = firstAltDt;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReportIn() {
        return reportIn;
    }

    public void setReportIn(String reportIn) {
        this.reportIn = reportIn;
    }

    public List<SelectItem> getReportInList() {
        return reportInList;
    }

    public void setReportInList(List<SelectItem> reportInList) {
        this.reportInList = reportInList;
    }

    public String getSecondAltDt() {
        return secondAltDt;
    }

    public void setSecondAltDt(String secondAltDt) {
        this.secondAltDt = secondAltDt;
    }

    public String getThirdAltDt() {
        return thirdAltDt;
    }

    public void setThirdAltDt(String thirdAltDt) {
        this.thirdAltDt = thirdAltDt;
    }

    public Integer getFridayListSize() {
        return fridayListSize;
    }

    public void setFridayListSize(Integer fridayListSize) {
        this.fridayListSize = fridayListSize;
    }
}
