/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.report.TdReceiptIntDetailPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.FdRdFacadeRemote;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class ProjectedInterestTdsCalReport extends BaseBean {

    private String message;
    private String branch;
    private List<SelectItem> branchList;
    private Date dt = new Date();
    private String frmDt;
    private String toDt;
    private String intFrmAmt;
    private String intToAmt;
    private String repType;
    private List<SelectItem> repTypeList;
    private String repOption;
    private List<SelectItem> repOptionList;
    private String custType;
    private List<SelectItem> custTypeList;
    private String custId;
    private boolean disableId;
    private String focusId;
    private CommonReportMethodsRemote common;
    private FtsPostingMgmtFacadeRemote ftsPostRemote;
    private FdRdFacadeRemote fdRdRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<TdReceiptIntDetailPojo> repList = new ArrayList<TdReceiptIntDetailPojo>();

    public ProjectedInterestTdsCalReport() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            fdRdRemote = (FdRdFacadeRemote) ServiceLocator.getInstance().lookup("FdRdFacade");

            List brnLst = new ArrayList();
            brnLst = common.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }
            List frList = ftsPostRemote.getCurrentFinYear(getOrgnBrCode());
            Vector frVector = (Vector) frList.get(0);
            setFrmDt(dmy.format(ymd.parse(frVector.get(0).toString())));
            setToDt(dmy.format(ymd.parse(frVector.get(1).toString())));
            setIntFrmAmt("0");
            setIntToAmt("9999999999");

            repTypeList = new ArrayList<>();
            repTypeList.add(new SelectItem("Detail", "Detail"));
            repTypeList.add(new SelectItem("Summary", "Summary"));

            repOptionList = new ArrayList<>();
            repOptionList.add(new SelectItem("0", "--Select--"));
            repOptionList.add(new SelectItem("ALL Id", "ALL Id"));
            repOptionList.add(new SelectItem("Individual Id", "Individual Id"));

            custTypeList = new ArrayList<>();
            custTypeList.add(new SelectItem("0", "--Select--"));
            custTypeList.add(new SelectItem("Y", "with Senior Citizen"));
            custTypeList.add(new SelectItem("N", "Without Senior Citizen"));

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void repOptAction() {
        setMessage("");
        if (repOption.equalsIgnoreCase("Individual Id")) {
            this.focusId = "txtcid";
            this.disableId = false;
        } else {
            this.focusId = "branchddl";
            this.disableId = true;
        }
    }

    public void setSeniorCitizenFrAmt() {
        setMessage("");
        try {
            if (custType.equalsIgnoreCase("Y")) {
                setIntFrmAmt(String.valueOf(fdRdRemote.getSeniorCitizenAmt(ymd.format(dmy.parse(toDt)))));
            } else {
                setIntFrmAmt("0");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void viewReport() {
        setMessage("");
        try {

            if (repOption.equalsIgnoreCase("0")) {
                setMessage("Please Select Report Option.");
                return;
            }
            if (repOption.equalsIgnoreCase("Individual Id")) {
                if (custId == null || custId.equalsIgnoreCase("")) {
                    setMessage("Please fill Customer Id.");
                    return;
                }
            }

            if (custType.equalsIgnoreCase("0")) {
                setMessage("Please Select Cust Type.");
                return;
            }

            repList = fdRdRemote.getProjectedInterestTdsCalData(branch, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)), Double.parseDouble(intFrmAmt), Double.parseDouble(intToAmt), repOption, custId, custType);
            if (repList.isEmpty()) {
                throw new ApplicationException("Data does not exist!");
            }

            String bankName = "";
            String branchAddress = "";
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List ele = common.getBranchNameandAddress(getOrgnBrCode());
            if (ele.get(0) != null) {
                bankName = ele.get(0).toString();
            }
            if (ele.get(1) != null) {
                branchAddress = ele.get(1).toString();
            }
            String repName = "Projected Interest Tds Cal Report";
            Map fillParams = new HashMap();
            fillParams.put("pBankName", bankName);
            fillParams.put("pBranchAddress", branchAddress);
            fillParams.put("pStmtPeriod", frmDt);
            fillParams.put("pPrintedDate", toDt);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", repName);
            String jrxml = "ProjectedIntTdsCalReport";
            if (custType.equalsIgnoreCase("Y")) {
                fillParams.put("pCustType", "with Senior Citizen");
            } else {
                fillParams.put("pCustType", "Without Senior Citizen");
            }
            if (repType.equalsIgnoreCase("Summary")) {
                jrxml = "ProjectedIntTdsCalReport_Summary";
            }
            new ReportBean().jasperReport(jrxml, "text/html",
                    new JRBeanCollectionDataSource(repList), fillParams, "Projected Interest Tds Cal Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void pdfDownLoad() {
        setMessage("");
        try {

            if (repOption.equalsIgnoreCase("0")) {
                setMessage("Please Select Report Option.");
                return;
            }
            if (repOption.equalsIgnoreCase("Individual Id")) {
                if (custId == null || custId.equalsIgnoreCase("")) {
                    setMessage("Please fill Customer Id.");
                    return;
                }
            }

            if (custType.equalsIgnoreCase("0")) {
                setMessage("Please Select Cust Type.");
                return;
            }
            repList = fdRdRemote.getProjectedInterestTdsCalData(branch, ymd.format(dmy.parse(frmDt)), ymd.format(dmy.parse(toDt)), Double.parseDouble(intFrmAmt), Double.parseDouble(intToAmt), repOption, custId, custType);
            if (repList.isEmpty()) {
                throw new ApplicationException("Data does not exist!");
            }
            String bankName = "";
            String branchAddress = "";
            CommonReportMethodsRemote common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List ele = common.getBranchNameandAddress(getOrgnBrCode());
            if (ele.get(0) != null) {
                bankName = ele.get(0).toString();
            }
            if (ele.get(1) != null) {
                branchAddress = ele.get(1).toString();
            }
            String repName = "Projected Interest Tds Cal Report";
            Map fillParams = new HashMap();
            fillParams.put("pBankName", bankName);
            fillParams.put("pBranchAddress", branchAddress);
            fillParams.put("pStmtPeriod", frmDt);
            fillParams.put("pPrintedDate", toDt);
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", repName);
            if (custType.equalsIgnoreCase("Y")) {
                fillParams.put("pCustType", "with Senior Citizen");
            } else {
                fillParams.put("pCustType", "Without Senior Citizen");
            }
            String jrxml = "ProjectedIntTdsCalReport";
            if (repType.equalsIgnoreCase("Summary")) {
                jrxml = "ProjectedIntTdsCalReport_Summary";
            }

            new ReportBean().openPdf("Projected Interest Tds Cal Report", jrxml, new JRBeanCollectionDataSource(repList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", "Projected Interest Tds Cal Report");
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void btnRefreshAction() {
        setMessage("");
        setIntFrmAmt("0");
        setIntToAmt("9999999999");
        setRepOption("0");
        setCustType("0");
    }

    public String btnExitAction() {
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

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
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

    public String getIntFrmAmt() {
        return intFrmAmt;
    }

    public void setIntFrmAmt(String intFrmAmt) {
        this.intFrmAmt = intFrmAmt;
    }

    public String getIntToAmt() {
        return intToAmt;
    }

    public void setIntToAmt(String intToAmt) {
        this.intToAmt = intToAmt;
    }

    public String getRepType() {
        return repType;
    }

    public void setRepType(String repType) {
        this.repType = repType;
    }

    public List<SelectItem> getRepTypeList() {
        return repTypeList;
    }

    public void setRepTypeList(List<SelectItem> repTypeList) {
        this.repTypeList = repTypeList;
    }

    public String getRepOption() {
        return repOption;
    }

    public void setRepOption(String repOption) {
        this.repOption = repOption;
    }

    public List<SelectItem> getRepOptionList() {
        return repOptionList;
    }

    public void setRepOptionList(List<SelectItem> repOptionList) {
        this.repOptionList = repOptionList;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public boolean isDisableId() {
        return disableId;
    }

    public void setDisableId(boolean disableId) {
        this.disableId = disableId;
    }

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public List<SelectItem> getCustTypeList() {
        return custTypeList;
    }

    public void setCustTypeList(List<SelectItem> custTypeList) {
        this.custTypeList = custTypeList;
    }
}
