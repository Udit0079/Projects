/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import com.cbs.dto.other.SortedByCustId;
import com.cbs.dto.report.InterestReportPojo;
import com.cbs.dto.report.RbiSossPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author sipl
 */
public class InterestReport extends BaseBean {

    private String message;
    private String ddAcType;
    private String lblAcType;
    private String branch;
    private List<SelectItem> branchList;
    private String tdsFlag;
    private List<SelectItem> tdsflagList;
    private String repType;
    private List<SelectItem> repTypeList;
    List<SelectItem> ddAcTypeList = new ArrayList<SelectItem>();
    private String frAmt;
    private String toAmt;
    private String calFromDate;
    private String caltoDate;
    private Boolean disableField;
    private String focusId = "";
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    private OtherReportFacadeRemote facadeRemote;
    private CommonReportMethodsRemote commonBeanRemote;
    Date dt = new Date();

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

    public String getCalFromDate() {
        return calFromDate;
    }

    public void setCalFromDate(String calFromDate) {
        this.calFromDate = calFromDate;
    }

    public String getCaltoDate() {
        return caltoDate;
    }

    public void setCaltoDate(String caltoDate) {
        this.caltoDate = caltoDate;
    }

    public String getDdAcType() {
        return ddAcType;
    }

    public void setDdAcType(String ddAcType) {
        this.ddAcType = ddAcType;
    }

    public List<SelectItem> getDdAcTypeList() {
        return ddAcTypeList;
    }

    public void setDdAcTypeList(List<SelectItem> ddAcTypeList) {
        this.ddAcTypeList = ddAcTypeList;
    }

    public String getLblAcType() {
        return lblAcType;
    }

    public void setLblAcType(String lblAcType) {
        this.lblAcType = lblAcType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToAmt() {
        return toAmt;
    }

    public void setToAmt(String toAmt) {
        this.toAmt = toAmt;
    }

    public String getFrAmt() {
        return frAmt;
    }

    public void setFrAmt(String frAmt) {
        this.frAmt = frAmt;
    }

    public String getTdsFlag() {
        return tdsFlag;
    }

    public void setTdsFlag(String tdsFlag) {
        this.tdsFlag = tdsFlag;
    }

    public List<SelectItem> getTdsflagList() {
        return tdsflagList;
    }

    public void setTdsflagList(List<SelectItem> tdsflagList) {
        this.tdsflagList = tdsflagList;
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

    public Boolean getDisableField() {
        return disableField;
    }

    public void setDisableField(Boolean disableField) {
        this.disableField = disableField;
    }

    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public InterestReport() {
        try {
            facadeRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            commonBeanRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            setCalFromDate(dmyFormat.format(dt));
            setCaltoDate(dmyFormat.format(dt));
            setMessage("");

            tdsflagList = new ArrayList<SelectItem>();
            tdsflagList.add(new SelectItem("ALL", "ALL"));
            tdsflagList.add(new SelectItem("Y", "Yes"));
            tdsflagList.add(new SelectItem("N", "No"));

            repTypeList = new ArrayList<SelectItem>();
            repTypeList.add(new SelectItem("0", "--Select--"));
            repTypeList.add(new SelectItem("D", "Detail"));
            repTypeList.add(new SelectItem("S", "Summary"));

            List brnLst = new ArrayList();
            brnLst = commonBeanRemote.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }

            List acCodeList = commonBeanRemote.getAccTypeIncludeSBRDFD();
            ddAcTypeList = new ArrayList<SelectItem>();
            ddAcTypeList.add(new SelectItem("ALL", "ALL"));
            for (Object element : acCodeList) {
                Vector vector = (Vector) element;
                ddAcTypeList.add(new SelectItem(vector.get(0).toString(), vector.get(1).toString()));
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void reportOption() {
        setMessage("");
        try {
            if (repType.equalsIgnoreCase("IC")) {
                this.setFrAmt("0");
                this.setToAmt("0");
                this.setDdAcType("ALL");
                this.setTdsFlag("ALL");
                this.disableField = true;
                this.setFocusId("calfrm");
            } else {
                this.disableField = false;
                this.setFocusId("");
            }

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public boolean validate() {
        try {

            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");

            if (this.getDdAcType().equalsIgnoreCase("0")) {
                this.setMessage("Please Select Account Type");
                return false;
            }
            if (repType.equalsIgnoreCase("0")) {
                this.setMessage("Please Select Report Type");
                return false;
            }

            Matcher frAmtCheck = p.matcher(this.frAmt);
            if (!frAmtCheck.matches()) {
                this.setMessage("Invalid From Amount Entry.");
                return false;
            }

            Matcher toAmtCheck = p.matcher(this.toAmt);
            if (!toAmtCheck.matches()) {
                this.setMessage("Invalid To Amount Entry.");
                return false;
            }

            if (!Validator.validateDate(calFromDate)) {
                this.setMessage("Please check from date");
                return false;
            }
            if (!Validator.validateDate(caltoDate)) {
                this.setMessage("Please check to date");
                return false;
            }
            if (dmyFormat.parse(calFromDate).after(dmyFormat.parse(caltoDate))) {
                this.setMessage("From date should be less then to date");
                return false;
            }
            if (dmyFormat.parse(caltoDate).after(dt)) {
                this.setMessage("To date should be less or Equal to Current date");
                return false;
            }

        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return true;
    }

    public void printAction() {
        try {
            if (validate()) {
                String bankName = "", bankAddress = "";
                String acCode = this.getDdAcType();
                List<InterestReportPojo> interestRepList = facadeRemote.interestReport(acCode, this.getFrAmt(), this.toAmt,
                        ymdFormat.format(dmyFormat.parse(this.getCalFromDate())), ymdFormat.format(dmyFormat.parse(this.getCaltoDate())), this.branch, this.tdsFlag, this.repType);

                if (!interestRepList.isEmpty()) {

                    List dataList1 = commonBeanRemote.getBranchNameandAddress(getOrgnBrCode());
                    if (dataList1.size() > 0) {
                        bankName = (String) dataList1.get(0);
                        bankAddress = (String) dataList1.get(1);
                    }

                    String repName = "INTEREST REPORT";
                    Map fillParams = new HashMap();
                    fillParams.put("pReportName", repName);
                    fillParams.put("pPrintedBy", getUserName());
                    fillParams.put("pFrDate", this.getCalFromDate());
                    fillParams.put("pToDate", this.getCaltoDate());
                    fillParams.put("pFrAmt", this.getFrAmt());
                    fillParams.put("pToAmt", this.getToAmt());
                    fillParams.put("pBankName", bankName);
                    fillParams.put("pBranchAddress", bankAddress);
                    fillParams.put("pAcType", this.getDdAcType());

                    ComparatorChain chObj = new ComparatorChain();
                    chObj.addComparator(new SortedByCustId());
                    Collections.sort(interestRepList, chObj);

                    if (repType.equalsIgnoreCase("D")) {
                        new ReportBean().jasperReport("interest_rep", "text/html", new JRBeanCollectionDataSource(interestRepList), fillParams, "INTEREST REPORT");
                    } else {
                        new ReportBean().jasperReport("interest_rep_summary", "text/html", new JRBeanCollectionDataSource(interestRepList), fillParams, "INTEREST REPORT");
                    }
                    ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                    HttpSession httpSession = getHttpSession();
                    httpSession.setAttribute("ReportName", repName);
                } else {
                    setMessage("No data to print");
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void pdfDownLoad() {
        try {
            List<InterestReportPojo> interestRepList = new ArrayList<InterestReportPojo>();
            List<RbiSossPojo> resultList = new ArrayList<RbiSossPojo>();
            if (validate()) {
                String bankName = "", bankAddress = "";
                String acCode = this.getDdAcType();
                interestRepList = facadeRemote.interestReport(acCode, this.getFrAmt(), this.toAmt, ymdFormat.format(dmyFormat.parse(this.getCalFromDate())), ymdFormat.format(dmyFormat.parse(this.getCaltoDate())), this.branch, this.tdsFlag, this.repType);
                if (interestRepList.isEmpty()) {
                    throw new ApplicationException("Data does not exist !");
                }
                List dataList1 = commonBeanRemote.getBranchNameandAddress(getOrgnBrCode());
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }

                String repName = "INTEREST REPORT";
                Map fillParams = new HashMap();
                fillParams.put("pReportName", repName);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pFrDate", this.getCalFromDate());
                fillParams.put("pToDate", this.getCaltoDate());
                fillParams.put("pFrAmt", this.getFrAmt());
                fillParams.put("pToAmt", this.getToAmt());
                fillParams.put("pBankName", bankName);
                fillParams.put("pBranchAddress", bankAddress);
                fillParams.put("pAcType", this.getDdAcType());
                
                ComparatorChain chObj = new ComparatorChain();
                chObj.addComparator(new SortedByCustId());
                Collections.sort(interestRepList, chObj);
                
                if (repType.equalsIgnoreCase("D")) {
                    new ReportBean().openPdf("INTEREST REPORT_" + ymdFormat.format(dmyFormat.parse(getTodayDate())), "interest_rep", new JRBeanCollectionDataSource(interestRepList), fillParams);
                } else {
                    new ReportBean().openPdf("INTEREST REPORT_" + ymdFormat.format(dmyFormat.parse(getTodayDate())), "interest_rep_summary", new JRBeanCollectionDataSource(interestRepList), fillParams);
                }
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", repName);
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refreshAction() {
        this.setDdAcType("A");
        setCalFromDate(dmyFormat.format(dt));
        setCaltoDate(dmyFormat.format(dt));
        this.setFrAmt("0");
        this.setToAmt("0");
        this.setMessage("");
        this.disableField = false;
        this.setTdsFlag("ALL");
        this.setRepType("0");
        this.setFocusId("");
    }

    public String exitAction() {
        refreshAction();
        return "case1";
    }
}