/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.neftrtgs;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.other.NpciMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.AdharRegistrationDetailPojo;
import com.cbs.pojo.SortedByStatus;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author Ajeet
 */
public class AdharRegistraionDetail extends BaseBean {

    private String message;
    private String branch;
    private List<SelectItem> branchList;
    private String adhartype;
    private List<SelectItem> adharList;

    public boolean isBtnflag() {
        return btnflag;
    }

    public void setBtnflag(boolean btnflag) {
        this.btnflag = btnflag;
    }
    private String filter;
    private List<SelectItem> filterList;
    private String fromdate;
    private String todate;
    private String displayDate = "none";
    private Date date = new Date();
    private boolean btnflag;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private NpciMgmtFacadeRemote RemoteFacade;
    List<AdharRegistrationDetailPojo> reportList = new ArrayList<AdharRegistrationDetailPojo>();

    public AdharRegistraionDetail() {
        try {
            fromdate = dmy.format(date);
            todate = dmy.format(date);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            RemoteFacade = (NpciMgmtFacadeRemote) ServiceLocator.getInstance().lookup("NpciMgmtFacade");
            List brnLst = new ArrayList();
            brnLst = common.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();

            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }
            adharList = new ArrayList<SelectItem>();
            adharList.add(new SelectItem("S", "--Select--"));
            adharList.add(new SelectItem("AD", "AAdhar"));
            adharList.add(new SelectItem("NA", "Non AAdhar"));

            filterList = new ArrayList<SelectItem>();
            filterList.add(new SelectItem("S", "--Select--"));
            filterList.add(new SelectItem("All", "All Date"));
            filterList.add(new SelectItem("Dt", "Date Wise"));
            btnRefreshAction();
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void hideDate() {
        try {
            if (filter.equalsIgnoreCase("All")) {
                this.displayDate = "none";
            } else {
                this.displayDate = "";
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public boolean validateField() {
        this.setMessage("");
        try {

            if (this.adhartype == null || this.getAdhartype().equalsIgnoreCase("S")) {
                setMessage("Please select Aadhaar Type !");
                return false;
            }

            if (this.filter == null || this.getFilter().equalsIgnoreCase("S")) {
                setMessage("Please select Filter Type !");
                return false;
            }

            if (this.fromdate == null || this.getFromdate().equalsIgnoreCase("")) {
                setMessage("Please fill from Date");
                return false;
            }

            if (!Validator.validateDate(fromdate)) {
                setMessage("Please select Proper from date");
                return false;
            }
            if (this.todate == null || this.getTodate().equalsIgnoreCase("")) {
                setMessage("Please fill to Date");
                return false;
            }
            if (!Validator.validateDate(todate)) {
                setMessage("Please select Proper to Date");
                return false;
            }
            if (dmy.parse(fromdate).after(date)) {
                setMessage("From date should be less than current date !");
                return false;
            }

            if (dmy.parse(todate).after(date)) {
                setMessage("To date should be less than current date !");
                return false;
            }

            if (dmy.parse(fromdate).after(dmy.parse(todate))) {
                setMessage("From Date should be less than To date !");
                return false;
            }

        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public void viewReport() {
        setMessage("");
        //String report = "AAdhar Registration Detail Report";
        try {
            if (validateField()) {
                String reportName = "AAdhar Registration Detail Report";
                List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
                Map fillParams = new HashMap();
                fillParams.put("printedBy", getUserName());
                fillParams.put("reportName", reportName);
                fillParams.put("pBankName", brNameAndAddList.get(0).toString());
                fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
                fillParams.put("frDt", fromdate);
                fillParams.put("toDt", todate);

                reportList = RemoteFacade.getAdharRegistraionDetail(branch, adhartype, filter, ymd.format(dmy.parse(fromdate)), ymd.format(dmy.parse(todate)));
                if (reportList.isEmpty()) {
                    throw new ApplicationException("Data does not exist !");
                }
                ComparatorChain chainOb = new ComparatorChain();
                chainOb.addComparator(new SortedByStatus());
                Collections.sort(reportList, chainOb);

                new ReportBean().jasperReport("AadharRegistrationDetail", "text/html", new JRBeanCollectionDataSource(reportList), fillParams, reportName);
                getHttpResponse().sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                getHttpSession().setAttribute("ReportName", reportName);
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setAdhartype("S");
        this.setFilter("S");
        this.displayDate = "none";
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
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

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getAdhartype() {
        return adhartype;
    }

    public void setAdhartype(String adhartype) {
        this.adhartype = adhartype;
    }

    public List<SelectItem> getAdharList() {
        return adharList;
    }

    public void setAdharList(List<SelectItem> adharList) {
        this.adharList = adharList;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public List<SelectItem> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<SelectItem> filterList) {
        this.filterList = filterList;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public String getDisplayDate() {
        return displayDate;
    }

    public void setDisplayDate(String displayDate) {
        this.displayDate = displayDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
