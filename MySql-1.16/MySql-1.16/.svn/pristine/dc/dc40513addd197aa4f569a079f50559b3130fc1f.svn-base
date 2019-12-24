/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.share;

import com.cbs.dto.report.ShareDividendDetailPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.ShareReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.util.ArrayList;
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
 * @author Admin
 */
public class ShareDividendDetail extends BaseBean {

    private String errorMsg;
    private String finYear;
    private List<SelectItem> finYearList;
    private String reportType;
    private List<SelectItem> reportTypeList;
    private String area;
    private List<SelectItem> areaList;
    private ShareReportFacadeRemote horfr = null;
    private CommonReportMethodsRemote commonRemote = null;

    public ShareDividendDetail() {
        try {
            horfr = (ShareReportFacadeRemote) ServiceLocator.getInstance().lookup("ShareReportFacade");
            commonRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");

            areaList = new ArrayList<SelectItem>();
            areaList.add(new SelectItem("0", "--Select--"));
            areaList.add(new SelectItem("ALL", " All"));
            List result = horfr.getAreaTypeList();
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector ele = (Vector) result.get(i);
                    areaList.add(new SelectItem(ele.get(0).toString()));
                }
            }
            finYearList = new ArrayList<SelectItem>();
            finYearList.add(new SelectItem("0", "--Select--"));
            List finList = horfr.fYearList();
            if (!finList.isEmpty()) {
                for (int i = 0; i < finList.size(); i++) {
                    Vector vec = (Vector) finList.get(i);
                    finYearList.add(new SelectItem(vec.get(0)));
                }
            }

            reportTypeList = new ArrayList<SelectItem>();
            reportTypeList.add(new SelectItem("Select", "--Select--"));
            reportTypeList.add(new SelectItem("B", "Outstanding Dividend"));
            reportTypeList.add(new SelectItem("P", "Paid Dividend"));

        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void viewReport() {
        setErrorMsg("");
        String report = "Share Dividend Detail Report";
        try {
            if (finYear.equalsIgnoreCase("0")) {
                setErrorMsg("Please select Financial Year");
                return;
            }

            if (reportType.equalsIgnoreCase("Select")) {
                setErrorMsg("Please select Report Type");
                return;
            }

            Map fillParams = new HashMap();
            List bnkList = commonRemote.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", (String) bnkList.get(0));
            fillParams.put("pBankAddress", (String) bnkList.get(1));
            fillParams.put("pArea", area);
            
            String repType = "";
            if (reportType.equalsIgnoreCase("P")) {
                repType = "Paid Dividend";
            } else {
                repType = "OutStanding Dividend";
            }
            fillParams.put("pRepType", repType);

            List<ShareDividendDetailPojo> repList = horfr.getShareDividendDetail(finYear, reportType,area);
            if (repList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            new ReportBean().jasperReport("Share_Dividend_Detail", "text/html", new JRBeanCollectionDataSource(repList), fillParams, report);
            getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            getHttpSession().setAttribute("ReportName", report);

        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void pdfDownLoad() {
        setErrorMsg("");
        String report = "Share Dividend Detail Report";
        try {
            if (finYear.equalsIgnoreCase("0")) {
                setErrorMsg("Please select Financial Year");
                return;
            }

            if (reportType.equalsIgnoreCase("Select")) {
                setErrorMsg("Please select Report Type");
                return;
            }

            Map fillParams = new HashMap();
            List bnkList = commonRemote.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", (String) bnkList.get(0));
            fillParams.put("pBankAddress", (String) bnkList.get(1));
            fillParams.put("pArea", area);
            String repType = "";
            if (reportType.equalsIgnoreCase("P")) {
                repType = "Paid Dividend";
            } else {
                repType = "OutStanding Dividend";
            }
            fillParams.put("pRepType", repType);

            List<ShareDividendDetailPojo> repList = horfr.getShareDividendDetail(finYear, reportType,area);
            if (repList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            new ReportBean().openPdf("Share Dividend Detail Report", "Share_Dividend_Detail", new JRBeanCollectionDataSource(repList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void refreshPage() {
        setErrorMsg("");
        setFinYear("0");
        setReportType("Select");
        setArea("");
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getFinYear() {
        return finYear;
    }

    public void setFinYear(String finYear) {
        this.finYear = finYear;
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

    public List<SelectItem> getFinYearList() {
        return finYearList;
    }

    public void setFinYearList(List<SelectItem> finYearList) {
        this.finYearList = finYearList;
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
}
