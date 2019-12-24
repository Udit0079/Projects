
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.deadstock;

import com.cbs.dto.ItemStatusReportTable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.deadstock.DeadstockFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
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

public class DepreciationPostingReport extends BaseBean {

    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String message;
    private String itemGroup;
    private String itemCodeString;
    private String itemDistinctNo;
    private List<SelectItem> itemGroupList;
    private List<SelectItem> itemList;
    private List<SelectItem> itemDistinctNoList;
    private String itemOption;
    private List<SelectItem> itemOptionList;
    private boolean disableItemDisNo;
    private HttpServletRequest req;
    private DeadstockFacadeRemote deadstockFacadeRemote;
    List<ItemStatusReportTable> reportList;
    DeadstockFacadeRemote remote;
    private CommonReportMethodsRemote common;
    private FtsPostingMgmtFacadeRemote fts;
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private String viewID = "/pages/master/sm/test.jsp";
    Date dt = new Date();
    Date calDate;
    private Date calFromDate;
    private Date caltoDate;
    private String branch;
    private boolean disableBtn;

    public DepreciationPostingReport() {
        req = getRequest();
        try {
            remote = (DeadstockFacadeRemote) ServiceLocator.getInstance().lookup("DeadstockFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            this.setUserName(req.getRemoteUser());
            this.setTodayDate(sdf.format(date));
            itemOptionList = new ArrayList<>();
            itemOptionList.add(new SelectItem("0", "--Select--"));
            itemOptionList.add(new SelectItem("A", "All Item"));
            itemOptionList.add(new SelectItem("I", "Individul Item"));
            List l = fts.getCurrentFinYear(getOrgnBrCode());
            Vector vtr = (Vector) l.get(0);
            String frdt = vtr.get(0).toString();
            String todt = vtr.get(1).toString();
            this.setCalFromDate(ymd.parse(frdt));
            this.setCaltoDate(ymd.parse(todt));
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
        initData();
    }

    public void initData() {
        itemGroupList = new ArrayList<SelectItem>();
        itemList = new ArrayList<SelectItem>();
        itemDistinctNoList = new ArrayList<SelectItem>();
        itemGroupList.add(new SelectItem("00", "--Select--"));
        try {
            List resultGroup = remote.getDepreciationGroupCodde();
            if (resultGroup != null) {
                for (int i = 0; i < resultGroup.size(); i++) {
                    Vector vtr = (Vector) resultGroup.get(i);
                    itemGroupList.add(new SelectItem(vtr.get(1).toString(), vtr.get(0).toString()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void itemOptionAction() {
        setMessage("");
        try {
            if (itemOption == null || itemOption.equalsIgnoreCase("0")) {
                setMessage("Please select Item Option !");
                return;
            }
            if (itemOption.equalsIgnoreCase("A")) {
                itemDistinctNoList.clear();
                itemDistinctNoList.add(new SelectItem("00", "ALL"));
                this.disableItemDisNo = true;
            } else {
                this.disableItemDisNo = false;
            }
            itemList.clear();
            itemList.add(new SelectItem("00", "--Select--"));
            if (!itemOption.equalsIgnoreCase("A")) {
                List result = remote.getItemCodeListReport(this.itemGroup);
                if (result != null) {
                    for (int i = 0; i < result.size(); i++) {
                        Vector vtr = (Vector) result.get(i);
                        itemList.add(new SelectItem(vtr.get(0), vtr.get(1).toString()));
                    }
                }
            }

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void distinctNo() {
        try {
            message = "";
            itemDistinctNoList.clear();
            itemDistinctNoList.add(new SelectItem("00", "ALL"));
            List result = remote.getDistinctNoReport(itemGroup, Integer.parseInt(itemCodeString));
            if (result != null) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    itemDistinctNoList.add(new SelectItem(vtr.get(0), vtr.get(0).toString()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void btnPdfAction() {
        setMessage("");
        String report = "Depreciation Posting Report";
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportDt", sdf.format(this.date));
            fillParams.put("pReportName", report);

            if (itemOption.equalsIgnoreCase("A")) {
                this.setItemCodeString("");
                this.setItemDistinctNo("0");
            } else {
                this.setItemDistinctNo(this.itemDistinctNo);
            }

            reportList = remote.getDepreciationReportData(itemGroup, itemCodeString,
                    Integer.parseInt(itemDistinctNo), ymdFormat.format(calFromDate), ymdFormat.format(caltoDate), itemOption);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            new ReportBean().openPdf("Depreciation Posting Report" + ymd.format(dt), "DepreciationPostingReport", new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnHtmlAction() {
        setMessage("");
        String report = "Depreciation Posting Report";

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
        String FromDt = sdf.format(calFromDate);
        String ToDate = sdf.format(caltoDate);

        try {
            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportDt", sdf.format(this.date));
            fillParams.put("pReportName", report);

            if (itemOption.equalsIgnoreCase("A")) {
                this.setItemCodeString("");
                this.setItemDistinctNo("0");
            } else {
                this.setItemDistinctNo(this.itemDistinctNo);
            }

            reportList = remote.getDepreciationReportData(itemGroup, itemCodeString,
                    Integer.parseInt(itemDistinctNo), ymdFormat.format(calFromDate), ymdFormat.format(caltoDate), itemOption);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            new ReportBean().jasperReport("DepreciationPostingReport", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "Depreciation Posting Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refresh() {
        itemGroup = "00";
        itemDistinctNo = "00";
        itemCodeString = "00";
        this.disableItemDisNo = false;
        this.itemOption = "0";
        this.disableBtn = false;
    }

    public void refreshData() {
        refresh();
        message = "";
    }

    public String exitAction() {
        refreshData();
        return "case1";
    }

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(String itemGroup) {
        this.itemGroup = itemGroup;
    }

    public String getItemCodeString() {
        return itemCodeString;
    }

    public void setItemCodeString(String itemCodeString) {
        this.itemCodeString = itemCodeString;
    }

    public String getItemDistinctNo() {
        return itemDistinctNo;
    }

    public void setItemDistinctNo(String itemDistinctNo) {
        this.itemDistinctNo = itemDistinctNo;
    }

    public List<SelectItem> getItemGroupList() {
        return itemGroupList;
    }

    public void setItemGroupList(List<SelectItem> itemGroupList) {
        this.itemGroupList = itemGroupList;
    }

    public List<SelectItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SelectItem> itemList) {
        this.itemList = itemList;
    }

    public List<SelectItem> getItemDistinctNoList() {
        return itemDistinctNoList;
    }

    public List<ItemStatusReportTable> getReportList() {
        return reportList;
    }

    public void setReportList(List<ItemStatusReportTable> reportList) {
        this.reportList = reportList;
    }

    public SimpleDateFormat getYmd() {
        return ymd;
    }

    public void setYmd(SimpleDateFormat ymd) {
        this.ymd = ymd;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public Date getCalDate() {
        return calDate;
    }

    public void setCalDate(Date calDate) {
        this.calDate = calDate;
    }

    public Date getCalFromDate() {
        return calFromDate;
    }

    public void setCalFromDate(Date calFromDate) {
        this.calFromDate = calFromDate;
    }

    public Date getCaltoDate() {
        return caltoDate;
    }

    public void setCaltoDate(Date caltoDate) {
        this.caltoDate = caltoDate;
    }

    public void setItemDistinctNoList(List<SelectItem> itemDistinctNoList) {
        this.itemDistinctNoList = itemDistinctNoList;
    }

    public String getItemOption() {
        return itemOption;
    }

    public void setItemOption(String itemOption) {
        this.itemOption = itemOption;
    }

    public List<SelectItem> getItemOptionList() {
        return itemOptionList;
    }

    public void setItemOptionList(List<SelectItem> itemOptionList) {
        this.itemOptionList = itemOptionList;
    }

    public boolean isDisableItemDisNo() {
        return disableItemDisNo;
    }

    public void setDisableItemDisNo(boolean disableItemDisNo) {
        this.disableItemDisNo = disableItemDisNo;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public DeadstockFacadeRemote getRemote() {
        return remote;
    }

    public void setRemote(DeadstockFacadeRemote remote) {
        this.remote = remote;
    }

    public CommonReportMethodsRemote getCommon() {
        return common;
    }

    public void setCommon(CommonReportMethodsRemote common) {
        this.common = common;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public boolean isDisableBtn() {
        return disableBtn;
    }

    public void setDisableBtn(boolean disableBtn) {
        this.disableBtn = disableBtn;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }
}
