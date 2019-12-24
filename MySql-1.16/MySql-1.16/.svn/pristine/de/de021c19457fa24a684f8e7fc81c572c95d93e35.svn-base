/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.report.ShareReportFacadeRemote;
import com.cbs.pojo.RecoveryDetailPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

/**
 *
 * @author Admin
 */
public class RecoveryDetail extends BaseBean {

    private String message;
    private String custId;
    private String reportType;
    private List<SelectItem> reportTypeList;
    private String area;
    private String disPlayPanalCustId;
    private String disPlayPanalArea;
    private String lableButton;
    private List<SelectItem> areaList;
    private String folioNo;
    Date calDate;
    private String month;
    private List<SelectItem> monthList;
    private String year;
    private LoanReportFacadeRemote local;
    private CommonReportMethodsRemote common;
    private ShareReportFacadeRemote horfr;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private List<RecoveryDetailPojo> repList = new ArrayList<RecoveryDetailPojo>();
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");

    public RecoveryDetail() {
        //setCalDate(dmyFormat.format(new Date()));
        try {
            local = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            horfr = (ShareReportFacadeRemote) ServiceLocator.getInstance().lookup("ShareReportFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");

            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("S", "--Select--"));
            reportTypeList.add(new SelectItem("A", "Area Wise"));
            reportTypeList.add(new SelectItem("C", "Folio No. Wise"));
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
            this.setLableButton("Folio No.");
            this.setDisPlayPanalArea("none");
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void reportAction() {
        if (reportType.equalsIgnoreCase("A")) {
            this.setLableButton("Area Wise");
            this.setDisPlayPanalArea("");
            this.setDisPlayPanalCustId("none");
            areaAction();
        } else {
            this.setLableButton("Folio No.");
            this.setDisPlayPanalArea("none");
            this.setDisPlayPanalCustId("");
        }
    }

    public void areaAction() {
        try {
            areaList = new ArrayList<SelectItem>();
            areaList.add(new SelectItem("S", "--Select--"));
            List result = common.getRefRecList("019");
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector ele = (Vector) result.get(i);
                    areaList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void onBlurFolio() {
        setMessage("");
        try {
            if (reportType.equalsIgnoreCase("S")) {
                setMessage("Please select Report Type !");
                return;
            }

            if (reportType.equalsIgnoreCase("A")) {
                if (area.equalsIgnoreCase("S")) {
                    setMessage("Please select Area Wise !");
                    return;
                }
            } else {
                if (custId.equalsIgnoreCase("") || custId == null) {
                    setMessage("Please fill Folio No !");
                    return;
                }
                if (custId.length() != 12) {
                    setMessage("Please fill 12 digits Folio No !");
                    return;
                }
            }
            String chkAcno = ftsRemote.getNewAccountNumber(this.custId);
            if (!chkAcno.equalsIgnoreCase(this.custId)) {
                throw new ApplicationException("Acount No. Invalid");
            }
            if (this.reportType.equalsIgnoreCase("C")) {
                this.folioNo = chkAcno;
            } else {
                this.folioNo = this.area;
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
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

    public String getDisPlayPanalCustId() {
        return disPlayPanalCustId;
    }

    public void setDisPlayPanalCustId(String disPlayPanalCustId) {
        this.disPlayPanalCustId = disPlayPanalCustId;
    }

    public String getDisPlayPanalArea() {
        return disPlayPanalArea;
    }

    public void setDisPlayPanalArea(String disPlayPanalArea) {
        this.disPlayPanalArea = disPlayPanalArea;
    }

    public String getLableButton() {
        return lableButton;
    }

    public void setLableButton(String lableButton) {
        this.lableButton = lableButton;
    }

    public String getFolioNo() {
        return folioNo;
    }

    public void setFolioNo(String folioNo) {
        this.folioNo = folioNo;
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

    public void btnHtmlAction() {
        setMessage("");
        String report = "Recovery Detail";
        try {
            if (reportType.equalsIgnoreCase("S")) {
                setMessage("Please select Report Type !");
                return;
            }

            if (reportType.equalsIgnoreCase("A")) {
                if (area.equalsIgnoreCase("S")) {
                    setMessage("Please select Area Wise !");
                    return;
                }
            } else {
                if (custId.equalsIgnoreCase("") || custId == null) {
                    setMessage("Please fill Folio No !");
                    return;
                }
                if (custId.length() != 12) {
                    setMessage("Please fill 12 digits Folio No !");
                    return;
                }
            }
//            if (custId.equalsIgnoreCase("")) {
//                setMessage("Please fill customer id !");
//                return;
//            }
            if (calDate == null) {
                setMessage("Please fill date !");
                return;
            }
            String dt = ymdFormat.format(calDate);
            String month = CbsUtil.getMonthName(Integer.parseInt(dt.substring(4, 6)));
            String yy = dt.substring(0, 4);
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pMonth", month);
            fillParams.put("pYY", yy);

            repList = local.getLoanRecoveryDetail(custId, dt, area, reportType, getOrgnBrCode());

            if (repList.isEmpty()) {
                throw new ApplicationException("Data does not exist  !!!");
            }
            new ReportBean().jasperReport("RecoveryDetailNew", "text/html",
                    new JRBeanCollectionDataSource(repList), fillParams, "Recovery Detail");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (Exception ex) {
            ex.printStackTrace();
            setMessage(ex.getMessage());
        }
    }

    public void pdfDownLoad() {
        setMessage("");
        String report = "Loan Slip";
        try {
            if (reportType.equalsIgnoreCase("S")) {
                setMessage("Please select Report Type !");
                return;
            }

            if (reportType.equalsIgnoreCase("A")) {
                if (area.equalsIgnoreCase("S")) {
                    setMessage("Please select Area Wise !");
                    return;
                }
            } else {
                if (custId.equalsIgnoreCase("") || custId == null) {
                    setMessage("Please fill Folio No. !");
                    return;
                }
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
            //Get month End Date
            ftsRemote.getCodeFromCbsParameterInfo("MWF_ONOF_BOARD");
            String tillDate = dmyFormat.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(this.month), Integer.parseInt(this.year)));
            String dt = ymdFormat.format(dmyFormat.parse(tillDate));
            String month = CbsUtil.getMonthName(Integer.parseInt(dt.substring(4, 6)));
            String yy = dt.substring(0, 4);
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pMonth", month);
            fillParams.put("pYY", yy);
            fillParams.put("pMwf_Onof_Board", ftsRemote.getCodeFromCbsParameterInfo("MWF_ONOF_BOARD"));

            repList = local.getLoanRecoveryDetail(custId, dt, area, reportType, getOrgnBrCode());

            if (repList.isEmpty()) {
                throw new ApplicationException("Data does not exist  !!!");
            }
//            new ReportBean().jasperReport("RecoveryDetailNew", "text/html",
//                    new JRBeanCollectionDataSource(repList), fillParams, "Recovery Detail");
//            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            String pdfName = "";
            if (reportType.equalsIgnoreCase("A")) {
                pdfName = area;
            } else {
                pdfName = custId;
            }
            new ReportBean().openPdf(pdfName + "_Loan Slip_" + ymdFormat.format(dmyFormat.parse(this.getTodayDate())), "RecoveryDetailNew", new JRBeanCollectionDataSource(repList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception ex) {
            ex.printStackTrace();
            setMessage(ex.getMessage());
        }
    }

    public void refreshAction() {
        setMessage("");
        setCustId("");
        setReportType("S");
        setArea("S");
        setFolioNo("");
        this.setMonth("0");
        this.setYear("");

    }

    public String btnExitAction() {
        return "case1";
    }
}
