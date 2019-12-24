/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.report.ho.ComparatorByStatus;
import com.cbs.dto.report.ho.LoanEmiDetailPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
 * @author Athar Raza
 */
public class LoanEmiDetail extends BaseBean {

    private String message;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String acNature;
    private List<SelectItem> acNatureList;
    private String acType;
    private List<SelectItem> acTypeList;
    private CommonReportMethodsRemote common;
    private TdReceiptManagementFacadeRemote beanFacade;
    private LoanReportFacadeRemote loanRemote;
    Date asOnDate = new Date();
    private List<LoanEmiDetailPojo> repList = new ArrayList<LoanEmiDetailPojo>();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public LoanEmiDetail() {
        try {
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            beanFacade = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            loanRemote = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");

            branchCodeList = new ArrayList<SelectItem>();
            List list = common.getAlphacodeBasedOnBranch(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                branchCodeList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(vtr.get(1).toString())), vtr.get(0).toString()));
            }
            getAccountNature();
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void getAccountNature() {
        acNatureList = new ArrayList<SelectItem>();
        acNatureList.add(new SelectItem("S", "--Select--"));
        try {
            List actNatureList = common.getacNatureTLDL();
            if (!actNatureList.isEmpty()) {
                for (int i = 0; i < actNatureList.size(); i++) {
                    Vector vtr = (Vector) actNatureList.get(i);
                    acNatureList.add(new SelectItem(vtr.get(0).toString()));
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void getAccountType() {
        acTypeList = new ArrayList<SelectItem>();
        acTypeList.add(new SelectItem("ALL", "ALL"));
        try {
            List acctType = common.getAccType(acNature);
            if (!acctType.isEmpty()) {
                for (int i = 0; i < acctType.size(); i++) {
                    Vector vtr = (Vector) acctType.get(i);
                    acTypeList.add(new SelectItem(vtr.get(0).toString()));
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void printAction() {
        setMessage("");
        String report = "Loan Emi Detail";
        try {
            if (asOnDate == null) {
                message = "Please Fill From Date";
                return;
            }

            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pReportDt", dmy.format(asOnDate));
            fillParams.put("pPrintedBy", getUserName());

            repList = loanRemote.getLoanEmiDetail(branchCode, acNature, acType, ymd.format(asOnDate));

            if (repList.isEmpty()) {
                throw new ApplicationException("Data does not exist  !!!");
            }

            ComparatorChain chObj = new ComparatorChain();
            chObj.addComparator(new ComparatorByStatus());
            Collections.sort(repList, chObj);

            new ReportBean().jasperReport("LoanEmiDetail", "text/html",
                    new JRBeanCollectionDataSource(repList), fillParams, "Loan Emi Detail");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");


        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void refreshAction() {
        setMessage("");
    }

    public String exitAction() {
        return "case1";
    }

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public List<SelectItem> getAcNatureList() {
        return acNatureList;
    }

    public void setAcNatureList(List<SelectItem> acNatureList) {
        this.acNatureList = acNatureList;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public Date getAsOnDate() {
        return asOnDate;
    }

    public void setAsOnDate(Date asOnDate) {
        this.asOnDate = asOnDate;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
