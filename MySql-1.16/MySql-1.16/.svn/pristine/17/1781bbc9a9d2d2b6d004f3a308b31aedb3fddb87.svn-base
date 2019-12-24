/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.share;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.dto.report.AreaWiseSharePojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.other.PostalDetailFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.ShareReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
 * @author Athar Reza
 */
public class ShareAreaWise extends BaseBean {

    private String message;
    private String area;
    private String date;
    private List<SelectItem> areaList;
    private CommonReportMethodsRemote common;
    private ShareReportFacadeRemote horfr;
    private PostalDetailFacadeRemote postalRemote;
    List<AreaWiseSharePojo> reportList = new ArrayList<AreaWiseSharePojo>();
    private SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private String branch;
    private List<SelectItem> brnList;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<SelectItem> getBrnList() {
        return brnList;
    }

    public void setBrnList(List<SelectItem> brnList) {
        this.brnList = brnList;
    }

    public ShareAreaWise() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            horfr = (ShareReportFacadeRemote) ServiceLocator.getInstance().lookup("ShareReportFacade");
            postalRemote = (PostalDetailFacadeRemote) ServiceLocator.getInstance().lookup("PostalDetailFacade");
            List brnLst = new ArrayList();
            brnLst = common.getBranchCodeList(getOrgnBrCode());
            brnList = new ArrayList<SelectItem>();
            for (int i = 0; i < brnLst.size(); i++) {
                Vector ele7 = (Vector) brnLst.get(i);
                brnList.add(new SelectItem(ele7.get(0).toString(), ele7.get(1).toString()));
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void onBlurArea() {
        try {
            areaList = new ArrayList<SelectItem>();
            String banckCode = postalRemote.getBankCode();
            if (banckCode.equalsIgnoreCase("ccbl")) {
                areaList.add(new SelectItem("ALL", "ALL"));
            }
            List result = horfr.getAreaWiseList(this.getBranch());
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector ele = (Vector) result.get(i);
                    areaList.add(new SelectItem(ele.get(0).toString()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void viewReport() {
        setMessage("");
        String branchName = "";
        String address = "";
        String report = "Area Wise Share Report";
        try {
            if ((this.getArea() == null) || (this.getArea().equalsIgnoreCase(""))) {
                throw new Exception("Please Select Area !!!");
            }

            List brnAddrList = new ArrayList();
            brnAddrList = common.getBranchNameandAddress(getOrgnBrCode());
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }

            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAddr", address);
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pArea", this.area);

            reportList = horfr.getAreaWiseShareData(branch, area, ymd.format(dmy.parse(date)));
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !!!");
            }

            new ReportBean().jasperReport("AreaWiseShare", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Area Wise Share Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void pdfDownLoad() {
        setMessage("");
        String branchName = "";
        String address = "";
        String report = "Area Wise Share Report";
        try {
            if ((this.getArea() == null) || (this.getArea().equalsIgnoreCase(""))) {
                throw new Exception("Please Select Area !!!");
            }

            List brnAddrList = new ArrayList();
            brnAddrList = common.getBranchNameandAddress(getOrgnBrCode());
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }

            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAddr", address);
            fillParams.put("pPrintedBy", this.getUserName());
            fillParams.put("pArea", this.area);

            reportList = horfr.getAreaWiseShareData(branch, area, ymd.format(dmy.parse(date)));
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !!!");
            }

            new ReportBean().openPdf("Area Wise Share Report", "AreaWiseShare", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
    public FastReportBuilder excelPrint()  {
        setMessage("");
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
           reportList = horfr.getAreaWiseShareData(branch, area, ymd.format(dmy.parse(date)));

            int width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);
            AbstractColumn folioNo = ReportBean.getColumn("folioNo", String.class, "Folio No", 100);
            AbstractColumn custName= ReportBean.getColumn("custName", String.class, "Cust Name", 200);
            AbstractColumn fatherName = ReportBean.getColumn("fatherName", String.class, "Father Name", 150);
            AbstractColumn dob = ReportBean.getColumn("dob", String.class, "Dob", 90);
            AbstractColumn permAdd = ReportBean.getColumn("permAdd", String.class, "Perm Add", 150);
            AbstractColumn corresspndAdd = ReportBean.getColumn("corresspndAdd", String.class, "Corresspnd Add", 150);
            AbstractColumn area = ReportBean.getColumn("area", String.class, "Area", 150);
           

            fastReport.addColumn(folioNo);
            width = width + folioNo.getWidth();

            fastReport.addColumn(custName);
            width = width + custName.getWidth();

            fastReport.addColumn(fatherName);
            width = width + fatherName.getWidth();

            fastReport.addColumn(dob);
            width = width + dob.getWidth();

            fastReport.addColumn(permAdd);
            width = width + permAdd.getWidth();

            fastReport.addColumn(corresspndAdd);
            width = width + corresspndAdd.getWidth();
  
            fastReport.addColumn(area);
             width = width + area.getWidth();
            
             Page page = new Page(842, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("Area Wise Share Report");

             new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(reportList), fastReport, "AreaWiseShareData");

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return fastReport;
    
    
        
    
    }

    public void refreshPage() {
        try {
            setMessage("");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
}
