/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.report.OverdueEmiReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.report.OverDueReportFacadeRemote;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Sudhir
 */
public class LoanOverdueEMI extends BaseBean {

    private String message;
    private String acCode;
    private List<SelectItem> acCodeList = new ArrayList<SelectItem>();
    private Date asOnDate;
    private final String jndiHomeName = "LoanReportFacade";
    private LoanReportFacadeRemote beanRemote = null;
    private SimpleDateFormat ymdFormatter = new SimpleDateFormat("yyyyMMdd"),
            dmyFormatter = new SimpleDateFormat("dd/MM/yyyy");
    private String flag;
    private String sectorWise,
            sector, groupBy;
    private List<SelectItem> sectorWiseList = new ArrayList<SelectItem>(),
            sectorList = new ArrayList<SelectItem>(),
            groupByList = new ArrayList<SelectItem>();
    private boolean checkBox, disableSector, disableGroupBy, disableAccountCode, disableFromText, disableTotext;
    private String fromText, toText;
    private String branch;
    private List<SelectItem> branchList;
    private String status;
    private List<SelectItem> statusList;
    private CommonReportMethodsRemote common;
    private FtsPostingMgmtFacadeRemote ftsPosting;
    private OverDueReportFacadeRemote overDueReport;

    public boolean isDisableGroupBy() {
        return disableGroupBy;
    }

    public void setDisableGroupBy(boolean disableGroupBy) {
        this.disableGroupBy = disableGroupBy;
    }

    public boolean isDisableAccountCode() {
        return disableAccountCode;
    }

    public void setDisableAccountCode(boolean disableAccountCode) {
        this.disableAccountCode = disableAccountCode;
    }

    public boolean isDisableFromText() {
        return disableFromText;
    }

    public void setDisableFromText(boolean disableFromText) {
        this.disableFromText = disableFromText;
    }

    public boolean isDisableSector() {
        return disableSector;
    }

    public void setDisableSector(boolean disableSector) {
        this.disableSector = disableSector;
    }

    public boolean isDisableTotext() {
        return disableTotext;
    }

    public void setDisableTotext(boolean disableTotext) {
        this.disableTotext = disableTotext;
    }

    public String getFromText() {
        return fromText;
    }

    public void setFromText(String fromText) {
        this.fromText = fromText;
    }

    public String getToText() {
        return toText;
    }

    public void setToText(String toText) {
        this.toText = toText;
    }

    public boolean isCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAcCode() {
        return acCode;
    }

    public void setAcCode(String acCode) {
        this.acCode = acCode;
    }

    public List<SelectItem> getAcCodeList() {
        return acCodeList;
    }

    public void setAcCodeList(List<SelectItem> acCodeList) {
        this.acCodeList = acCodeList;
    }

    public Date getAsOnDate() {
        return asOnDate;
    }

    public void setAsOnDate(Date asOnDate) {
        this.asOnDate = asOnDate;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public List<SelectItem> getGroupByList() {
        return groupByList;
    }

    public void setGroupByList(List<SelectItem> groupByList) {
        this.groupByList = groupByList;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public List<SelectItem> getSectorList() {
        return sectorList;
    }

    public void setSectorList(List<SelectItem> sectorList) {
        this.sectorList = sectorList;
    }

    public String getSectorWise() {
        return sectorWise;
    }

    public void setSectorWise(String sectorWise) {
        this.sectorWise = sectorWise;
    }

    public List<SelectItem> getSectorWiseList() {
        return sectorWiseList;
    }

    public void setSectorWiseList(List<SelectItem> sectorWiseList) {
        this.sectorWiseList = sectorWiseList;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SelectItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<SelectItem> statusList) {
        this.statusList = statusList;
    }

    /**
     * Creates a new instance of AccountCloseRegister
     */
    public LoanOverdueEMI() {
        try {
            beanRemote = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ftsPosting = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            overDueReport = (OverDueReportFacadeRemote) ServiceLocator.getInstance().lookup("OverDueReportFacade");
            this.setMessage("");
            statusList = new ArrayList<SelectItem>();
            statusList.add(new SelectItem("A", "ALL"));
            statusList.add(new SelectItem("N", "NPA"));
            statusList.add(new SelectItem("S", "STANDARD"));
            onLoadDataFill();
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
            flag = "false";
        } catch (Exception e) {
            setMessage(e.getMessage());
            flag = "false";
        }
    }

    public void onLoadDataFill() {
        try {
            sectorWiseList = new ArrayList<SelectItem>();
            sectorWiseList.add(new SelectItem("0", "SECTOR WISE REPORT FOR OVERDUE OF LOANS"));
            sectorWiseList.add(new SelectItem("1", "DETAILED REPORT FOR OVERDUE OF LOANS"));

            groupByList = new ArrayList<SelectItem>();
            groupByList.add(new SelectItem("0", "ACCOUNT CODE"));
            groupByList.add(new SelectItem("1", "LOAN SECTOR"));

            sectorList = new ArrayList<SelectItem>();

            acCodeList = new ArrayList<SelectItem>();
            List acCodelist = beanRemote.getTLCode();
            if (!acCodelist.isEmpty()) {
                acCodeList.add(new SelectItem("ALL", "ALL"));
                for (int i = 0; i < acCodelist.size(); i++) {
                    Vector vec = (Vector) acCodelist.get(i);
                    acCodeList.add(new SelectItem(vec.get(0).toString(), vec.get(0).toString()));
                }
            }

            List brnLst = new ArrayList();
            brnLst = common.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }

            List sectorDetails = common.getRefRecList("183");
            if (!sectorDetails.isEmpty()) {
                for (int i = 0; i < sectorDetails.size(); i++) {
                    Vector vec = (Vector) sectorDetails.get(i);
                    sectorList.add(new SelectItem(vec.get(0).toString(), vec.get(1).toString()));
                }
            }
            disableFromText = true;
            disableTotext = true;
            disableGroupBy = true;
            disableAccountCode = true;
            setFromText(String.valueOf(1));
            setToText(String.valueOf(100));
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
            flag = "false";
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            flag = "false";
        }
    }

    public void onchangeSectorWise() {
        try {
            setMessage("");
            if (sectorWise.equalsIgnoreCase("0")) {
                disableSector = false;
                disableGroupBy = true;
                disableAccountCode = true;
                disableFromText = true;
                disableTotext = true;
                setFromText(String.valueOf(1));
                setToText(String.valueOf(100));
            } else {
                disableSector = true;
                disableGroupBy = false;
                disableAccountCode = false;
                disableFromText = true;
                disableTotext = true;
                setFromText(String.valueOf(1));
                setToText(String.valueOf(100));
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            flag = "false";
        }
    }

    public void onChangeGroupType() {
        try {
            if (this.groupBy.equalsIgnoreCase("0")) {
                disableAccountCode = false;
            } else {
                disableAccountCode = true;
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            flag = "false";
        }
    }

    public void onClickcheckBox() {
        try {
            setMessage("");
            if (checkBox == true) {
                disableFromText = false;
                disableTotext = false;
                setFromText("");
                setToText("");
            } else {
                disableFromText = true;
                disableTotext = true;
                setFromText(String.valueOf(1));
                setToText(String.valueOf(100));
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            flag = "false";
        }
    }

    public void printReport() {
        try {
            String valResult = validations();
            if (!valResult.equalsIgnoreCase("true")) {
                setMessage(valResult);
                flag = "false";
                return;
            }
            flag = "true";

            this.setMessage("");
            int groupBy;
            String accountCode = "";
            String sector1 = "";
            String bnkCode = ftsPosting.getBankCode();

            if (this.sectorWise.equalsIgnoreCase("0")) {
                sector1 = sector;
                groupBy = -1;
                accountCode = "";
            } else {
                sector1 = "";
                if (this.groupBy.equalsIgnoreCase("0")) {
                    accountCode = acCode;
                } else {
                    accountCode = "";
                }
                groupBy = Integer.parseInt(this.groupBy);
            }

            List<OverdueEmiReportPojo> overDueDetails = overDueReport.getOverdueEmiReport(Integer.parseInt(sectorWise), groupBy, sector1, accountCode, Integer.parseInt(fromText), Integer.parseInt(toText), ymdFormatter.format(asOnDate), this.getBranch(), status,checkBox,"","N");
            if (overDueDetails.isEmpty()) {
                setMessage("No proper data available!!!");
                flag = "false";
                return;
            }
            String report = "Overdue Report";
            Map fillParams = new HashMap();
            List branchNameandAddress = new ArrayList();
            if (this.getBranch().equalsIgnoreCase("0A")) {
                branchNameandAddress = common.getBranchNameandAddress("90");
            } else {
                branchNameandAddress = common.getBranchNameandAddress(this.getBranch());
            }
            String bnkIdenty = ftsPosting.getCodeFromCbsParameterInfo("GRACE_PD_APP_IN_OVERDUE");
            fillParams.put("pReportDate", dmyFormatter.format(asOnDate));
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pIsPrint", bnkIdenty);
            fillParams.put("pBnkName", branchNameandAddress.get(0).toString());
            fillParams.put("pBnkAdd", branchNameandAddress.get(1).toString());
            String jrxmlName = "OverDue";
            if (bnkCode.equalsIgnoreCase("KHAT")) {
                jrxmlName = "OverDue_Surety";
            }
            new ReportBean().jasperReport(jrxmlName, "text/html", new JRBeanCollectionDataSource(overDueDetails), fillParams, "Overdue Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
            flag = "false";
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            flag = "false";
        }
    }
    
    public void printExcelReport() {
        try {
            String valResult = validations();
            if (!valResult.equalsIgnoreCase("true")) {
                setMessage(valResult);
                flag = "false";
                return;
            }
            flag = "true";

            this.setMessage("");
            int groupBy;
            String accountCode = "";
            String sector1 = "";
            String bnkCode = ftsPosting.getBankCode();

            if (this.sectorWise.equalsIgnoreCase("0")) {
                sector1 = sector;
                groupBy = -1;
                accountCode = "";
            } else {
                sector1 = "";
                if (this.groupBy.equalsIgnoreCase("0")) {
                    accountCode = acCode;
                } else {
                    accountCode = "";
                }
                groupBy = Integer.parseInt(this.groupBy);
            }

            List<OverdueEmiReportPojo> overDueDetails = overDueReport.getOverdueEmiReport(Integer.parseInt(sectorWise), groupBy, sector1, accountCode, Integer.parseInt(fromText), Integer.parseInt(toText), ymdFormatter.format(asOnDate), this.getBranch(), status, checkBox, "", "N");
            if (overDueDetails.isEmpty()) {
                setMessage("No proper data available!!!");
                flag = "false";
                return;
            }
            String report = "Overdue Report";
            Map fillParams = new HashMap();
            List branchNameandAddress = new ArrayList();
            if (this.getBranch().equalsIgnoreCase("0A")) {
                branchNameandAddress = common.getBranchNameandAddress("90");
            } else {
                branchNameandAddress = common.getBranchNameandAddress(this.getBranch());
            }
            String bnkIdenty = ftsPosting.getCodeFromCbsParameterInfo("GRACE_PD_APP_IN_OVERDUE");
            fillParams.put("pReportDate", dmyFormatter.format(asOnDate));
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pIsPrint", bnkIdenty);
            fillParams.put("pBnkName", branchNameandAddress.get(0).toString());
            fillParams.put("pBnkAdd", branchNameandAddress.get(1).toString());
            String jrxmlName = "OverDue";
            if (bnkCode.equalsIgnoreCase("KHAT")) {
                jrxmlName = "OverDue_Surety";
            }
            new ReportBean().dynamicExcelJasper(jrxmlName, "excel", new JRBeanCollectionDataSource(overDueDetails), fillParams, "Overdue Report");
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
            flag = "false";
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            flag = "false";
        }
    }

    public String validations() {
        try {
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            if (sectorWise.equalsIgnoreCase("")) {
                return "Error!!! Sector wise is not set!!!";
            }
            if (disableSector == false) {
                if (sector.equalsIgnoreCase("")) {
                    return "Error!!! Sector type listbox is not selected";
                }
            }
            if (disableGroupBy == false) {
                if (groupBy.equalsIgnoreCase("")) {
                    return "Error!!! Group by listbox is not selected";
                }
            }
            if (disableAccountCode == false) {
                if (acCode.equalsIgnoreCase("")) {
                    return "Error!!! Account code is not selected";
                }
            }
            if (asOnDate == null) {
                return "Error!!! As on date is not set";
            }
            if (fromText.equalsIgnoreCase("")) {
                return "Error!!! From text is empty";
            }
            Matcher fromTextCheck = p.matcher(fromText);
            if (!fromTextCheck.matches()) {
                return "Error!!! Enter Valid numeric field for from text.";
            }
            if (Double.parseDouble(fromText) < 0) {
                return "Error!!! Enter numeric cannot be negative";
            }

            if (toText.equalsIgnoreCase("")) {
                return "Error!!! To text is empty";
            }
            Matcher toTextCheck = p.matcher(fromText);
            if (!toTextCheck.matches()) {
                return "Error!!! Enter Valid numeric field for to text.";
            }
            if (Double.parseDouble(toText) < 0) {
                return "Error!!! Enter numeric field for to text cannot be negative";
            }
        } catch (Exception e) {
            return "Exception occurred whiile validating fields !!!";
        }
        return "true";
    }

    public void printPDF() {
        try {
            String valResult = validations();
            if (!valResult.equalsIgnoreCase("true")) {
                setMessage(valResult);
                flag = "false";
                return;
            }
            flag = "true";

            this.setMessage("");
            int groupBy;
            String accountCode = "";
            String sector1 = "";
            String bnkCode = ftsPosting.getBankCode();

            if (this.sectorWise.equalsIgnoreCase("0")) {
                sector1 = sector;
                groupBy = -1;
                accountCode = "";
            } else {
                sector1 = "";
                if (this.groupBy.equalsIgnoreCase("0")) {
                    accountCode = acCode;
                } else {
                    accountCode = "";
                }
                groupBy = Integer.parseInt(this.groupBy);
            }

            List<OverdueEmiReportPojo> overDueDetails = overDueReport.getOverdueEmiReport(Integer.parseInt(sectorWise), groupBy, sector1, accountCode, Integer.parseInt(fromText), Integer.parseInt(toText), ymdFormatter.format(asOnDate), this.getBranch(), status,checkBox,"","N");
            if (overDueDetails.isEmpty()) {
                setMessage("No proper data available!!!");
                flag = "false";
                return;
            }
            List branchNameandAddress = new ArrayList();
            if (this.getBranch().equalsIgnoreCase("0A")) {
                branchNameandAddress = common.getBranchNameandAddress("90");
            } else {
                branchNameandAddress = common.getBranchNameandAddress(this.getBranch());
            }
            String report = "Overdue Report";
            Map fillParams = new HashMap();
            String bnkIdenty = ftsPosting.getCodeFromCbsParameterInfo("GRACE_PD_APP_IN_OVERDUE");
            fillParams.put("pReportDate", dmyFormatter.format(asOnDate));
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pIsPrint", bnkIdenty);
            fillParams.put("pBnkName", branchNameandAddress.get(0).toString());
            fillParams.put("pBnkAdd", branchNameandAddress.get(1).toString());
            String jrxmlName = "OverDue";
            if (bnkCode.equalsIgnoreCase("KHAT")) {
                jrxmlName = "OverDue_Surety";
            }
            new ReportBean().openPdf("OverDue_", jrxmlName, new JRBeanCollectionDataSource(overDueDetails), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
            flag = "false";
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            flag = "false";
        }
    }

    public String exitFrm() {
        return "case1";
    }
}