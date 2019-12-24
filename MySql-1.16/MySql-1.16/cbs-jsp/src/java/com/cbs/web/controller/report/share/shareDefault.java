/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.share;

import com.cbs.dto.report.AreaWiseSharePojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.share.ShareTransferFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author mayank
 */
public class shareDefault extends BaseBean implements Serializable  {
    private String errorMsg, status;
    private String area;
    private List<SelectItem> areaList;
    private String date;
    private ShareTransferFacadeRemote horfr;
    private CommonReportMethodsRemote common;
    List<AreaWiseSharePojo> reportList = new ArrayList<AreaWiseSharePojo>();
    private SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    public shareDefault(){
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            horfr = (ShareTransferFacadeRemote) ServiceLocator.getInstance().lookup("ShareTransferFacade");
            List areaLst = horfr.areaList();
            areaList = new ArrayList<SelectItem>();
            areaList.add(new SelectItem("", "--Select--"));
            if (!areaLst.isEmpty()) {
                areaList.add(new SelectItem("ALL", "ALL"));
                for (int i = 0; i < areaLst.size(); i++) {
                    Vector ele = (Vector) areaLst.get(i);
                    areaList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }
    
    public void viewReport() {
        setErrorMsg("");
        String branchName = "";
        String address = "";
        String report = "";
        if(status.equalsIgnoreCase("A")){
            report = "Active Holder Report";
        }else{
            report = "Defaulter Holder Report";
        }
        
        if((this.getArea() == null) || (this.getArea().equalsIgnoreCase(""))){
            this.setErrorMsg("Please Select Area");
            return;
        }
        
        if((this.getDate() == null) || (this.getDate().equalsIgnoreCase(""))){        
            this.setErrorMsg("Please Fill Date");
            return;
        }
        
        try {

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

            reportList = horfr.getDefaulterShareData(this.getStatus(),this.getArea(),ymd.format(dmy.parse(date)));
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !!!");
            }

            new ReportBean().jasperReport("AreaWiseShare", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, report);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }
    
    public void refreshPage() {
        try {
            setErrorMsg("");
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }
}
