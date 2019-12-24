/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import com.cbs.dto.report.UltilityReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.ParseException;
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

public class UtilityReport extends BaseBean {

    private String errorMsg;
    private String branch;
    private String remarks;
    private String frmDt;
    private String toDt;
    private String mode;
    private String branchDisplay = "";
    private String acdisplay = "none";
    private String acno;
    private String acNoLen;
    private String newAccountNo;
    private String searchBy ="";
    private String searchByOption= "";
    private List<SelectItem> modeList;
    private List<SelectItem> branchList;
    private List<SelectItem> searchByList;
    private List<SelectItem> searchByOptionList;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private OtherReportFacadeRemote otherFacade;
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    private final String jndiFtsName = "FtsPostingMgmtFacade";
    private AdvancesInformationTrackingRemote aitr;
    List<UltilityReportPojo> reportList = new ArrayList<>();

    public UtilityReport() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            otherFacade = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiFtsName);
             aitr = (AdvancesInformationTrackingRemote) ServiceLocator.getInstance().lookup("AdvancesInformationTrackingFacade");

            modeList = new ArrayList<SelectItem>();
            modeList.add(new SelectItem("0", "--Select--"));
            modeList.add(new SelectItem("A", "All"));
            modeList.add(new SelectItem("I", "Individual"));

            branchList = new ArrayList<>();
            branchList.add(new SelectItem("0", "--Select--"));
            List list = common.getAlphacodeBasedOnBranch(getOrgnBrCode());
            if (list.isEmpty()) {
                this.setErrorMsg("Please define branches.");
                return;
            }

            if (common.getAlphacodeByBrncode(getOrgnBrCode()).equalsIgnoreCase("HO")) {
                branchList.add(new SelectItem("0A", "All"));
            }
            for (int i = 0; i < list.size(); i++) {
                Vector vec = (Vector) list.get(i);
                branchList.add(new SelectItem(vec.get(1).toString().length() < 2 ? "0" + vec.get(1).toString()
                        : vec.get(1).toString(), vec.get(0).toString()));
            }

            List searchList = this.common.getRefRecList("429");
            searchByList = new ArrayList<SelectItem>();
            searchByList.add(new SelectItem("", "--Select--"));
            for (int i = 0; i < searchList.size(); i++) {
                Vector ele = (Vector) searchList.get(i);
                searchByList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }
        } catch (Exception e) {
            setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void searchByAction() {
        searchByOptionList = new ArrayList<SelectItem>();
        try{
        List serchByOptionList = aitr.getListAsPerRequirement("430",this.searchBy, "0", "0", "0", "0", ymd.format(new Date()), 0);
        
        searchByOptionList.add(new SelectItem("","--Select--"));
            if (!serchByOptionList.isEmpty()) {
                for (int i = 0; i < serchByOptionList.size(); i++) {
                    Vector v = (Vector) serchByOptionList.get(i);
                    searchByOptionList.add(new SelectItem(v.get(1).toString(), v.get(1).toString()));
                }
            }  
        }catch(Exception ex){
           setErrorMsg(ex.getLocalizedMessage());
        }
     }

    public void onBlurmode() {
        this.setErrorMsg("");
        try {
            if (mode.equalsIgnoreCase("0")) {
                this.setErrorMsg("Please select the mode .");
                return;
            }
            if (mode.equalsIgnoreCase("A")) {
                this.branchDisplay = "";
                this.acdisplay = "none";
                this.acno = "";
                this.newAccountNo = "";
            } else if (mode.equalsIgnoreCase("I")) {
                this.branchDisplay = "none";
                this.acdisplay = "";
            }

        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void accountAction() {
        try {
            if (acno.equalsIgnoreCase("") || acno.equalsIgnoreCase(null)) {
                this.setErrorMsg("Account should not be blank.");
                return;
            }
            if (!getOrgnBrCode().equalsIgnoreCase("90")) {
                if (!acno.substring(0, 2).equals(getOrgnBrCode())) {
                    this.setErrorMsg("Please fill yuor branch account no.");
                    return;
                } else {
                    this.newAccountNo = ftsRemote.getNewAccountNumber(acno);
                }
            } else {
                this.newAccountNo = ftsRemote.getNewAccountNumber(acno);
            }
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }

    public void viewReport() {
        this.setErrorMsg("");
        try {
            if (mode.equalsIgnoreCase("A")) {
                if (branch == null || branch.equals("0")) {
                    setErrorMsg("Please select branch !");
                    return;
                }
            }
            if (remarks == null || remarks.equals("")) {
                setErrorMsg("Please fill remarks !");
                return;
            }
            if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill From Date!");
                return;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(frmDt)) {
                setErrorMsg("Please fill Proper From Date");
                return;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill To Date!");
                return;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(toDt)) {
                setErrorMsg("Please fill Proper To Date!");
                return;
            }
            if (dmy.parse(frmDt).compareTo(dmy.parse(toDt)) > 0) {
                setErrorMsg("From date can not be greater than to date!");
                return;
            }

            String report = "Utility Report";
            String pdate = getTodayDate();
            List brList = null;
            String brnName = "", brnAddress = "";
            brList = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brList.size() > 0) {
                brnName = (String) brList.get(0);
                brnAddress = (String) brList.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("pBnkName", brnName);
            fillParams.put("pBnkAddr", brnAddress);
            fillParams.put("pRepName", report);
            fillParams.put("pRepDate", this.frmDt + " to " + this.toDt);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pPrintDate", pdate);
            reportList = otherFacade.getUtilityReportDetails(this.branch, this.remarks, ymd.format(dmy.parse(this.frmDt)), ymd.format(dmy.parse(this.toDt)),
                    this.mode, this.newAccountNo,this.searchBy,this.searchByOption==null?"":searchByOption);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exits.");
            } else {
                new ReportBean().jasperReport("Utility_Report", "text/html",
                        new JRBeanCollectionDataSource(reportList), fillParams, "Utility_Report");
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
            }
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

    public void btnPdfAction() {
        this.setErrorMsg("");
        try {
            if (mode.equalsIgnoreCase("A")) {
                if (branch == null || branch.equals("0")) {
                    setErrorMsg("Please select branch !");
                    return;
                }
            }
            if (remarks == null || remarks.equals("")) {
                setErrorMsg("Please fill remarks !");
                return;
            }
            if (frmDt == null || frmDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill From Date!");
                return;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(frmDt)) {
                setErrorMsg("Please fill Proper From Date");
                return;
            }
            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setErrorMsg("Please fill To Date!");
                return;
            }
            if (!new Validator().validateDate_dd_mm_yyyy(toDt)) {
                setErrorMsg("Please fill Proper To Date!");
                return;
            }
            if (dmy.parse(frmDt).compareTo(dmy.parse(toDt)) > 0) {
                setErrorMsg("From date can not be greater than to date!");
                return;
            }

            String report = "Utility Report";
            String pdate = getTodayDate();
            List brList = null;
            String brnName = "", brnAddress = "";
            brList = common.getBranchNameandAddress(this.getOrgnBrCode());
            if (brList.size() > 0) {
                brnName = (String) brList.get(0);
                brnAddress = (String) brList.get(1);
            }
            Map fillParams = new HashMap();
            fillParams.put("pBnkName", brnName);
            fillParams.put("pBnkAddr", brnAddress);
            fillParams.put("pRepName", report);
            fillParams.put("pRepDate", this.frmDt + " to " + this.toDt);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pPrintDate", pdate);
            reportList = otherFacade.getUtilityReportDetails(this.branch, this.remarks, ymd.format(dmy.parse(this.frmDt)), ymd.format(dmy.parse(this.toDt)),
                    this.mode, this.newAccountNo,this.searchBy,this.searchByOption==null?"":searchByOption);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exits.");
            } else {
                new ReportBean().openPdf("Utility_Report" + ymd.format(dmy.parse(this.frmDt)) + " to " + ymd.format(dmy.parse(this.toDt)), "Utility_Report", new JRBeanCollectionDataSource(reportList), fillParams);
            }

            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getRequest().getSession();
            httpSession.setAttribute("ReportName", report);
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }

    public void refresh() {
        this.setErrorMsg("");
        this.setAcno("");
        this.setNewAccountNo("");
        this.setMode("0");
        this.setBranch("0");
        this.setRemarks("");
        this.setFrmDt(getTodayDate());
        this.setToDt(getTodayDate());
    }

    public String exitAction() {
        return "case1";
    }
    //Getter And Setter

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(String frmDt) {
        this.frmDt = frmDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public List<SelectItem> getModeList() {
        return modeList;
    }

    public void setModeList(List<SelectItem> modeList) {
        this.modeList = modeList;
    }

    public String getBranchDisplay() {
        return branchDisplay;
    }

    public void setBranchDisplay(String branchDisplay) {
        this.branchDisplay = branchDisplay;
    }

    public String getAcdisplay() {
        return acdisplay;
    }

    public void setAcdisplay(String acdisplay) {
        this.acdisplay = acdisplay;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    public String getNewAccountNo() {
        return newAccountNo;
    }

    public void setNewAccountNo(String newAccountNo) {
        this.newAccountNo = newAccountNo;
    }

    public String getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(String searchBy) {
        this.searchBy = searchBy;
    }

    public String getSearchByOption() {
        return searchByOption;
    }

    public void setSearchByOption(String searchByOption) {
        this.searchByOption = searchByOption;
    }

    public List<SelectItem> getSearchByList() {
        return searchByList;
    }

    public void setSearchByList(List<SelectItem> searchByList) {
        this.searchByList = searchByList;
    }

    public List<SelectItem> getSearchByOptionList() {
        return searchByOptionList;
    }

    public void setSearchByOptionList(List<SelectItem> searchByOptionList) {
        this.searchByOptionList = searchByOptionList;
    }
}
