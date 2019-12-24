/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import com.cbs.dto.report.FdMaturityPojo;
import com.cbs.facade.ho.HoReportBeanRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
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
 * @author Athar Reza
 */
public class FdGenderWise extends BaseBean {

    private String branchCode;
    private double amount;
    Date dt = new Date();
    private String asOnDate;
    private Integer fromYear;
    private Integer toYear;
    private String outputType;
    private HttpServletRequest req;
    private String message;
    private String display = "";
    private List<SelectItem> branchCodeList;
    private List<SelectItem> outputTypeList;
    private CommonReportMethodsRemote comman;
    private TdReceiptManagementFacadeRemote tdmFacade;
    private HoReportBeanRemote genderWise;
    List<FdMaturityPojo> genderList = new ArrayList<FdMaturityPojo>();

    public List<FdMaturityPojo> getGenderList() {
        return genderList;
    }

    public void setGenderList(List<FdMaturityPojo> genderList) {
        this.genderList = genderList;
    }

    public Integer getFromYear() {
        return fromYear;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public void setFromYear(Integer fromYear) {
        this.fromYear = fromYear;
    }

    public Integer getToYear() {
        return toYear;
    }

    public void setToYear(Integer toYear) {
        this.toYear = toYear;
    }

    public String getAsOnDate() {
        return asOnDate;
    }

    public void setAsOnDate(String asOnDate) {
        this.asOnDate = asOnDate;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public SimpleDateFormat getYmd() {
        return ymd;
    }

    public void setYmd(SimpleDateFormat ymd) {
        this.ymd = ymd;
    }

    public List<SelectItem> getOutputTypeList() {
        return outputTypeList;
    }

    public void setOutputTypeList(List<SelectItem> outputTypeList) {
        this.outputTypeList = outputTypeList;
    }

    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }

    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public FdGenderWise() {
        req = getRequest();
        this.setAsOnDate(getTodayDate());
        try {
            genderWise = (HoReportBeanRemote) ServiceLocator.getInstance().lookup("HoReportBean");
            comman = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            tdmFacade = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");

            List brnCode = new ArrayList();
            brnCode = tdmFacade.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brnCode.isEmpty()) {
                for (int i = 0; i < brnCode.size(); i++) {
                    Vector ele = (Vector) brnCode.get(i);
                    branchCodeList.add(new SelectItem(ele.get(0).toString().length() < 2 ? "0" + ele.get(0).toString() : ele.get(0).toString(), ele.get(1).toString()));
                }
            }
            outputTypeList = new ArrayList<SelectItem>();
            outputTypeList.add(new SelectItem("Select", "--Select--"));
            outputTypeList.add(new SelectItem("0", "PDF"));
            outputTypeList.add(new SelectItem("1", "HTML"));

        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void PrintViwe() {
        String branchName = "";
        String address = "";
        try {
            List brnAdd = new ArrayList();
            if (branchCode.equalsIgnoreCase("0A")) {
                brnAdd = comman.getBranchNameandAddress("90");
            } else {
                brnAdd = comman.getBranchNameandAddress(this.branchCode);
            }
            if (brnAdd.size() > 0) {
                branchName = (String) brnAdd.get(0);
                address = (String) brnAdd.get(1);
            }

            if (outputType.equalsIgnoreCase("Select")) {
                setMessage("Please Select Output Type.");
                return;
            }
            if (this.asOnDate == null) {
                setMessage("Please enter the As On Date.");
                return;
            }
            if (this.fromYear == null) {
                setMessage("Please enter the FromYear.");
                return;
            }
            if (this.toYear == null) {
                setMessage("Please enter the ToYear.");
                return;
            }
            if (this.branchCode.equalsIgnoreCase("--Select--")) {
                setMessage("Please enter the Branch Code.");
                return;
            }
            if (this.outputType.equalsIgnoreCase("--Select--")) {
                setMessage("Please enter the Output Type.");
                return;
            }
//            if (amount == 0) {
//                setMessage("Please enter amount ");
//                return;
//            }
            String report = "Fd Gender wise Report";
            Map fillMap = new HashMap();
            fillMap.put("SysDate", sdf.format(new Date()));
            fillMap.put("asOnDate", this.asOnDate);
            fillMap.put("pPrintedBy", this.getUserName());
            fillMap.put("branchName", branchName);
            fillMap.put("address", address);
            fillMap.put("pAmt", amount);

            String dd = this.asOnDate.substring(0, 2);
            String mm = this.asOnDate.substring(3, 5);
            String yy = this.asOnDate.substring(6, 10);
            String asOnDt = yy + mm + dd;

            genderList = genderWise.getFdMaturityData(this.branchCode, this.amount, this.fromYear, this.toYear, asOnDt);
            new ReportBean().jasperReport("Fd Genderwise Report", "text/html",
                    new JRBeanCollectionDataSource(genderList), fillMap, "Fd Gender wise Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }

    }

    public void refresh() {
        try {

            this.setMessage("");
            this.setAmount(0.00);
            this.setAsOnDate(getTodayDate());
            this.setFromYear(0);
            this.setToYear(0);
            this.setBranchCode("--select--");
            this.setOutputType("Select");

        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String closeAction() {
        return "case1";
    }
}
