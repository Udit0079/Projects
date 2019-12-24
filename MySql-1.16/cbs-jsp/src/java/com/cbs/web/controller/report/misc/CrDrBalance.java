/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.dto.report.CrDrBalancePojo;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
 * @author Athar Reza
 */
public class CrDrBalance extends BaseBean {

    private String message;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String acctType;
    private List<SelectItem> acctTypeList;
    private String balType;
    private List<SelectItem> balTypeList;
    private String frmDt;
    private String toDt;
    private Date date = new Date();
    private String orderBy;
    private List<SelectItem> orderByList;
    private CommonReportMethodsRemote common;
    private TdReceiptManagementFacadeRemote beanFacade;
    private MiscReportFacadeRemote remoteFacade;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<CrDrBalancePojo> repList = new ArrayList<CrDrBalancePojo>();

    public List<SelectItem> getAcctTypeList() {
        return acctTypeList;
    }

    public void setAcctTypeList(List<SelectItem> acctTypeList) {
        this.acctTypeList = acctTypeList;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getBalType() {
        return balType;
    }

    public void setBalType(String balType) {
        this.balType = balType;
    }

    public List<SelectItem> getBalTypeList() {
        return balTypeList;
    }

    public void setBalTypeList(List<SelectItem> balTypeList) {
        this.balTypeList = balTypeList;
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

    public String getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(String frmDt) {
        this.frmDt = frmDt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public List<SelectItem> getOrderByList() {
        return orderByList;
    }

    public void setOrderByList(List<SelectItem> orderByList) {
        this.orderByList = orderByList;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public CrDrBalance() {
        try {
            frmDt = dmy.format(date);
            toDt = dmy.format(date);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            beanFacade = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            remoteFacade = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");

            List brncode = new ArrayList();
            brncode = beanFacade.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }

            balTypeList = new ArrayList<SelectItem>();
            balTypeList.add(new SelectItem("1", "Cr Balance"));
            balTypeList.add(new SelectItem("-1", "Dr Balance"));

            orderByList = new ArrayList<SelectItem>();
            orderByList.add(new SelectItem("0", "A/c.No"));
            orderByList.add(new SelectItem("1", "Date"));
            getCAAcType();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
     public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry.Got a null request from faces context");
        }
        return request;
    }

    public void getCAAcType() {
        List acType = new ArrayList();
        try {
            acType = common.getCAAcTypeList();
            acctTypeList = new ArrayList<SelectItem>();
            if (!acType.isEmpty()) {
                for (int i = 0; i < acType.size(); i++) {
                    Vector acctVector = (Vector) acType.get(i);
                    acctTypeList.add(new SelectItem(acctVector.get(0).toString()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

//    public void getToDate() throws ParseException, java.text.ParseException {
//        String fDt = ymd.format(dmy.parse(this.frmDt));
//        String month = fDt.substring(4, 6);
//        String year = fDt.substring(0, 4);
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.MONTH, Integer.parseInt(month));
//        cal.set(Calendar.YEAR, Integer.parseInt(year));
//        int td = cal.getActualMaximum(Calendar.DATE);
//        cal.set(Calendar.DATE, td);
//        String toDt = dmy.format(cal.getTime());
//    }
    public void viewReport() {
        String branchName = "";
        String address = "";
        String report = "Cr/Dr Balance Report";

        try {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dmy.parse(frmDt));
            int fDate = calendar.get(Calendar.MONTH)+1;
            calendar.setTime(dmy.parse(toDt));
            int tDate = calendar.get(Calendar.MONTH)+1;
            if (fDate != tDate) {
                setMessage("To Month Should Be Same as From Month");
                return;
            }
            if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                setMessage("Please fill from Date");
                return;
            }
            if (!Validator.validateDate(frmDt)) {
                setMessage("Please select Proper from date ");
                return;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setMessage("Please fill to Date");
                return;
            }
            if (!Validator.validateDate(toDt)) {
                setMessage("Please select Proper to Date");
                return;
            }

            List brnAddrList = new ArrayList();
            if(getBranchCode().equalsIgnoreCase("0A")){
                 brnAddrList = common.getBranchNameandAddress("90");
            }else{
                 brnAddrList = common.getBranchNameandAddress(getBranchCode());
            }
           
            if (!brnAddrList.isEmpty()) {
                branchName = (String) brnAddrList.get(0);
                address = (String) brnAddrList.get(1);
            }

            Map fillParams = new HashMap();
            fillParams.put("pBnkName", branchName);
            fillParams.put("pBnkAddr", address);
            fillParams.put("pReprtName", report);
            fillParams.put("pReportDate", frmDt + " To " + toDt);
            fillParams.put("pPrintedBy", getUserName());
            
            String fDt = ymd.format(dmy.parse(frmDt));
            String tDt = ymd.format(dmy.parse(toDt));
            
            repList = remoteFacade.getMonthWiseCrDrBal(branchCode, acctType, balType, fDt, tDt, orderBy);
            if(repList.isEmpty()){
                setMessage("Data does not exist");
                return;
            }
            new ReportBean().jasperReport("CrDrBalanceReport", "text/html", new JRBeanCollectionDataSource(repList), fillParams, report);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setFrmDt(getTodayDate());
        this.setToDt(getTodayDate());
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }
}
