/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.dto.LoanIntCalcList;
import com.cbs.dto.report.PostingReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.facade.intcal.SbIntCalcFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
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
public class SbInterestReport extends BaseBean {

    private String message;
    private String intOpt;
    private String acctStatus;
    private String accountType;
    // private String trsNo;
    //  private String postedBy;
    private Date postingDate;
    private String focusEle;
    private boolean reportFlag;
    private boolean postReportFlag;
    private String viewID = "/pages/master/sm/test.jsp";
    private List<SelectItem> acctStatusList;
    private List<SelectItem> accountTypeList;
    // private List<SelectItem> trsNoList;
    // private List<SelectItem> postedByList;
    private List<LoanIntCalcList> intCalcList;
    private List<SelectItem> intOptionList;
    private String sbIntCalcJndiName = "SbIntCalcFacade";
    private CommonReportMethodsRemote common = null;
    private SbIntCalcFacadeRemote sbIntCalcRemote = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");

    //    public String getTrsNo() {
    //        return trsNo;
    //    }
    //
    //    public void setTrsNo(String trsNo) {
    //        this.trsNo = trsNo;
    //    }
    //
    //    public String getPostedBy() {
    //        return postedBy;
    //    }
    //
    //    public void setPostedBy(String postedBy) {
    //        this.postedBy = postedBy;
    //    }
    //
    //    public List<SelectItem> getTrsNoList() {
    //        return trsNoList;
    //    }
    //
    //    public void setTrsNoList(List<SelectItem> trsNoList) {
    //        this.trsNoList = trsNoList;
    //    }
    //
    //    public List<SelectItem> getPostedByList() {
    //        return postedByList;
    //    }
    //
    //    public void setPostedByList(List<SelectItem> postedByList) {
    //        this.postedByList = postedByList;
    //    }
    public boolean isPostReportFlag() {
        return postReportFlag;
    }

    public void setPostReportFlag(boolean postReportFlag) {
        this.postReportFlag = postReportFlag;
    }

    public boolean isReportFlag() {
        return reportFlag;
    }

    public void setReportFlag(boolean reportFlag) {
        this.reportFlag = reportFlag;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public String getFocusEle() {
        return focusEle;
    }

    public void setFocusEle(String focusEle) {
        this.focusEle = focusEle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIntOpt() {
        return intOpt;
    }

    public void setIntOpt(String intOpt) {
        this.intOpt = intOpt;
    }

    public String getAcctStatus() {
        return acctStatus;
    }

    public void setAcctStatus(String acctStatus) {
        this.acctStatus = acctStatus;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Date getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(Date postingDate) {
        this.postingDate = postingDate;
    }

    public List<SelectItem> getAcctStatusList() {
        return acctStatusList;
    }

    public void setAcctStatusList(List<SelectItem> acctStatusList) {
        this.acctStatusList = acctStatusList;
    }

    public List<SelectItem> getAccountTypeList() {
        return accountTypeList;
    }

    public void setAccountTypeList(List<SelectItem> accountTypeList) {
        this.accountTypeList = accountTypeList;
    }

    public List<LoanIntCalcList> getIntCalcList() {
        return intCalcList;
    }

    public void setIntCalcList(List<LoanIntCalcList> intCalcList) {
        this.intCalcList = intCalcList;
    }

    public List<SelectItem> getIntOptionList() {
        return intOptionList;
    }

    public void setIntOptionList(List<SelectItem> intOptionList) {
        this.intOptionList = intOptionList;
    }

    /**
     * Creates a new instance of SbInterestReport
     */
    public SbInterestReport() {
        try {
            sbIntCalcRemote = (SbIntCalcFacadeRemote) ServiceLocator.getInstance().lookup(sbIntCalcJndiName);
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            intOptionList = new ArrayList<SelectItem>();
            intOptionList.add(new SelectItem("C", "Calculation Report"));
            intOptionList.add(new SelectItem("P", "Posting Report"));
            getValues();
            acctStatusList = new ArrayList<SelectItem>();
            acctStatusList.add(new SelectItem("1", "Operative"));
            acctStatusList.add(new SelectItem("2", "In-Operative"));
        } catch (ServiceLocatorException ex) {
            setMessage(ex.getMessage());
        }
    }

    public void getValues() {
        try {
            acctStatusList = new ArrayList<SelectItem>();
            List resultList = sbIntCalcRemote.getAcctType();
            if (!resultList.isEmpty()) {
                accountTypeList = new ArrayList<SelectItem>();
                accountTypeList.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    accountTypeList.add(new SelectItem(ele.get(0).toString(), ele.get(0).toString()));
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void setIntOptions() {
        try {
            intCalcList = new ArrayList<LoanIntCalcList>();
            setAccountType("0");
            setFocusEle("ddAccountType");
            setMessage("");
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void getIntPostingDate() {
        try {
            setMessage("");
            if (accountType == null || accountType.equals("0")) {
                throw new ApplicationException("Please select Account type");
            }
            String dt = sbIntCalcRemote.getIntPostingDt(getOrgnBrCode(), getAccountType(), getAcctStatus());
            setPostingDate(ymd.parse(dt));
            setFocusEle("calToDate");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void interestReport() {
        try {
            setMessage("");
            setReportFlag(false);
            if (getAccountType().equals("0")) {
                throw new ApplicationException("Please select account type");
            }

            if (getPostingDate() == null || getPostingDate().equals("")) {
                throw new ApplicationException("Please fill Posting Date");
            }

            List list = sbIntCalcRemote.getIntPostingPd(getAccountType(), getOrgnBrCode(), ymd.format(getPostingDate()), getAcctStatus());
            Vector dtVect = (Vector) list.get(0);
            if (dtVect.get(0) == null) {
                throw new ApplicationException("Data does not exist in cbs_loan_acctype_interest_parameter");
            }
            String fromDt = dtVect.get(0).toString();
            String toDt = dtVect.get(1).toString();

            if (intOpt.equals("C")) {
                intCalcList = sbIntCalcRemote.cbsSbIntCalc("A", getAcctStatus(), getAccountType(), "", sdf.format(ymd.parse(fromDt)),
                        sdf.format(ymd.parse(toDt)), getOrgnBrCode());

                long mills = System.currentTimeMillis();

                String frmDate = sdf.format(ymd.parse(fromDt));
                String toDate = sdf.format(ymd.parse(toDt));

                String repName = "Interest Calculation";
                String report = "Interest Calculation Report";
                List vec = common.getBranchNameandAddress(getOrgnBrCode());
                Map fillParams = new HashMap();

                fillParams.put("pReportName", repName);
                fillParams.put("pReportDate", getTodayDate());
                fillParams.put("pPrintedBy", getUserName());

                fillParams.put("pFromDt", frmDate);
                fillParams.put("pToDt", toDate);
                fillParams.put("pBankName", vec.get(0).toString());
                fillParams.put("pBankAdd", vec.get(1).toString());
                new ReportBean().jasperReport("SbIntCalAllBranch", "text/html", new JRBeanCollectionDataSource(intCalcList), fillParams, report);
                this.setViewID("/report/ReportPagePopUp.jsp");
                setReportFlag(true);
                long diff = System.currentTimeMillis() - mills;
                System.out.println("Total report printing time is = " + diff);

            } else {
                List<PostingReportPojo> postingReport = common.sbIntPostingReport(getAccountType(), getAcctStatus(), ymd.format(getPostingDate()), getOrgnBrCode());
                if (postingReport.isEmpty()) {
                    throw new ApplicationException("Data does not exist");
                }
                String report = "SB Interest Posting Report";
                Map fillParams = new HashMap();
                String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                fillParams.put("pReportName", report);
                fillParams.put("pReportDate", fromDt + " to " + toDt);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pbankName", s[0]);
                fillParams.put("pbankAddress", s[1]);
                new ReportBean().jasperReport("PostingReport", "text/html", new JRBeanCollectionDataSource(postingReport), fillParams, report);
                this.setViewID("/report/ReportPagePopUp.jsp");
                setReportFlag(true);
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }
    
    public void btnPdfAction(){
        try {
            setMessage("");
            setReportFlag(false);
            if (getAccountType().equals("0")) {
                throw new ApplicationException("Please select account type");
            }

            if (getPostingDate() == null || getPostingDate().equals("")) {
                throw new ApplicationException("Please fill Posting Date");
            }

            List list = sbIntCalcRemote.getIntPostingPd(getAccountType(), getOrgnBrCode(), ymd.format(getPostingDate()), getAcctStatus());
            Vector dtVect = (Vector) list.get(0);
            if (dtVect.get(0) == null) {
                throw new ApplicationException("Data does not exist in cbs_loan_acctype_interest_parameter");
            }
            String fromDt = dtVect.get(0).toString();
            String toDt = dtVect.get(1).toString();

            if (intOpt.equals("C")) {
                intCalcList = sbIntCalcRemote.cbsSbIntCalc("A", getAcctStatus(), getAccountType(), "", sdf.format(ymd.parse(fromDt)),
                        sdf.format(ymd.parse(toDt)), getOrgnBrCode());

                long mills = System.currentTimeMillis();

                String frmDate = sdf.format(ymd.parse(fromDt));
                String toDate = sdf.format(ymd.parse(toDt));

                String repName = "Interest Calculation";
                String report = "Interest Calculation Report";
                List vec = common.getBranchNameandAddress(getOrgnBrCode());
                Map fillParams = new HashMap();

                fillParams.put("pReportName", repName);
                fillParams.put("pReportDate", getTodayDate());
                fillParams.put("pPrintedBy", getUserName());

                fillParams.put("pFromDt", frmDate);
                fillParams.put("pToDt", toDate);
                fillParams.put("pBankName", vec.get(0).toString());
                fillParams.put("pBankAdd", vec.get(1).toString());
                //new ReportBean().jasperReport("SbIntCalAllBranch", "text/html", new JRBeanCollectionDataSource(intCalcList), fillParams, report);
                new ReportBean().openPdf("Interest Calculation Report", "SbIntCalAllBranch", new JRBeanCollectionDataSource(intCalcList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                 HttpSession httpSession = getHttpSession();
                 httpSession.setAttribute("ReportName", report);
                 //this.setViewID("/report/ReportPagePopUp.jsp");
                // setReportFlag(true);
                long diff = System.currentTimeMillis() - mills;
                System.out.println("Total report printing time is = " + diff);

            } else {
                List<PostingReportPojo> postingReport = common.sbIntPostingReport(getAccountType(), getAcctStatus(), ymd.format(getPostingDate()), getOrgnBrCode());
                if (postingReport.isEmpty()) {
                    throw new ApplicationException("Data does not exist");
                }
                String report = "SB Interest Posting Report";
                Map fillParams = new HashMap();
                String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                fillParams.put("pReportName", report);
                fillParams.put("pReportDate", fromDt + " to " + toDt);
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pbankName", s[0]);
                fillParams.put("pbankAddress", s[1]);
               // new ReportBean().jasperReport("PostingReport", "text/html", new JRBeanCollectionDataSource(postingReport), fillParams, report);
                new ReportBean().openPdf("SB Interest Posting Report", "PostingReport", new JRBeanCollectionDataSource(postingReport), fillParams);
                
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                 HttpSession httpSession = getHttpSession();
                 httpSession.setAttribute("ReportName", report);
                 
                //this.setViewID("/report/ReportPagePopUp.jsp");
                //setReportFlag(true);
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        
    }

    public void refresh() {
        try {
            setMessage("");
            setAccountType("0");
            setAcctStatus("1");
            setIntOpt("C");
            setPostingDate(sdf.parse(getTodayDate()));
            if (intCalcList != null) {
                intCalcList.clear();
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        this.setMessage("");
        refresh();
        return "case1";
    }
}
