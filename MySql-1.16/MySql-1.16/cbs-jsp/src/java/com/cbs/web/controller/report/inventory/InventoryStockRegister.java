/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.inventory;

import com.cbs.dto.report.InventoryReportPojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.InventoryReportFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
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
 * @author Ankit Verma
 */
public class InventoryStockRegister extends BaseBean {

    private String userName;
    private String brnCode;
    private String todayDate;
    private String msg;
    private String date;
    private String type;
    private List<SelectItem> typeList;
    private String branch;
    private List<SelectItem> branchList;
    private InventoryReportFacadeRemote facadeRemote;
    private CommonReportMethodsRemote common;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private HttpServletRequest req;

    public InventoryStockRegister() {
        try {
            req = getRequest();
            setUserName(req.getRemoteUser());
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            brnCode = Init.IP2BR.getBranch(localhost);
            todayDate = sdf.format(new Date());
            facadeRemote = (InventoryReportFacadeRemote) ServiceLocator.getInstance().lookup("InventoryReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            typeList = new ArrayList<SelectItem>();
            typeList.add(new SelectItem("0", "--Select--"));
            typeList.add(new SelectItem("U", "User Label"));
            typeList.add(new SelectItem("B", "Branch Label"));

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
            setMsg(e.getLocalizedMessage());
        }
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SelectItem> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<SelectItem> typeList) {
        this.typeList = typeList;
    }

    public String getMsg() {
        return msg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void viewReport() {
        msg = validation();
        if (msg.equalsIgnoreCase("ok")) {
            try {
                String dt1[] = date.split("/");
                String dd3 = dt1[2] + dt1[1] + dt1[0];
                List<InventoryReportPojo> reportList = facadeRemote.getInvtStockRegister(type, dd3, branch);
                Map fillParams = new HashMap();
                CommonReportMethodsRemote beanLocal = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List vec = beanLocal.getBranchNameandAddress(brnCode);
                if (vec.size() > 0) {
                    fillParams.put("pReportName", "User/Branch Stock Register");
                    fillParams.put("pPrintedBy", userName);
                    fillParams.put("pBankName", vec.get(0).toString());
                    fillParams.put("pBankAdd", vec.get(1).toString());
                    fillParams.put("pReportDate", date);
                }
                ReportBean rb = new ReportBean();
                if (reportList.size() > 0) {
                    rb.jasperReport("InventoryStockRegister", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, "User/Branch Stock Register");
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                } else {
                    setMsg("No detail exists");
                }
            } catch (Exception e) {
                setMsg(e.getLocalizedMessage());
            }

        }
    }

    public void pdfDownLoad() {
        msg = validation();
        if (msg.equalsIgnoreCase("ok")) {
            try {
                String dt1[] = date.split("/");
                String dd3 = dt1[2] + dt1[1] + dt1[0];
                List<InventoryReportPojo> reportList = facadeRemote.getInvtStockRegister(type, dd3, branch);
                
                Map fillParams = new HashMap();
                CommonReportMethodsRemote beanLocal = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
                List vec = beanLocal.getBranchNameandAddress(brnCode);
                if (vec.size() > 0) {
                    fillParams.put("pReportName", "User/Branch Stock Register");
                    fillParams.put("pPrintedBy", userName);
                    fillParams.put("pBankName", vec.get(0).toString());
                    fillParams.put("pBankAdd", vec.get(1).toString());
                    fillParams.put("pReportDate", date);
                }
                ReportBean rb = new ReportBean();
                if (reportList.size() > 0) {
                    rb.openPdf("User Branch Stock Register_" + ymd.format(sdf.parse(getTodayDate())), "InventoryStockRegister", new JRBeanCollectionDataSource(reportList), fillParams);
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", "User/Branch Stock Register");
                } else {
                    setMsg("No detail exists");
                }
            } catch (Exception e) {
                setMsg(e.getLocalizedMessage());
            }

        }
    }

    public void refreshForm() {
        date = "";
        type = "0";
        msg = "";
    }

    public String exitAction() {
        return "case1";
    }

    public String validation() {
        if (date.equalsIgnoreCase("")) {
            return "Please Enter Date.";
        } else if (type.equalsIgnoreCase("0")) {
            return "Please select inventory type";
        }
        return "ok";
    }
}
