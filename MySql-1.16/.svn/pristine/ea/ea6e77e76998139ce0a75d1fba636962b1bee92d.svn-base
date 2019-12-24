/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.hr;

import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.pojo.BonusChecklistPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.hrms.facade.payroll.PayrollOtherMgmFacadeRemote;
import com.hrms.facade.payroll.PayrollTransactionsFacadeRemote;
import com.hrms.utils.HrServiceLocator;
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
public class BonusChecklist extends BaseBean {

    private Date frDate;
    private Date toDate;
    private String yearFromDate;
    private String yearToDate;
    private String category;
    private List<SelectItem> categoryList;
    private String branch;
    private String message;
    public boolean disableBankBranch;
    private List<SelectItem> selectBranchList;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private CommonReportMethodsRemote commomRemote;
    private PayrollTransactionsFacadeRemote payrollRemote;
    List<BonusChecklistPojo> bonusCheckListdata;
    private PayrollOtherMgmFacadeRemote payrollOthrMgm;
    private String branchName;
    private String branchCode;

    public BonusChecklist() {
        try {
            commomRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            payrollRemote = (PayrollTransactionsFacadeRemote) HrServiceLocator.getInstance().lookup("PayrollTransactionsFacade");
            payrollOthrMgm = (PayrollOtherMgmFacadeRemote) HrServiceLocator.getInstance().lookup("PayrollOtherMgmFacade");
            categoryList = new ArrayList<SelectItem>();
            categoryList.add(new SelectItem("0", "--Select--"));
            categoryList.add(new SelectItem("BRN", "BRANCH WISE"));

            getFinancialYear();
            branchwiseCategory();
            disableBankBranch = true;
            bonusCheckListdata = new ArrayList();

        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void branchwiseCategory() {
        try {
            selectBranchList = new ArrayList<SelectItem>();
            selectBranchList.add(new SelectItem("0", "--SELECT--"));
            if (category.equalsIgnoreCase("0")) {
                disableBankBranch = true;
                this.setMessage("Please select category");
            } else if (category.equalsIgnoreCase("BRN")) {
                disableBankBranch = false;
                this.setMessage("");
                List list = commomRemote.getAlphacodeAllAndBranch(getOrgnBrCode());
                for (int i = 0; i < list.size(); i++) {
                    Vector ele = (Vector) list.get(i);
                    branchName = ele.get(0).toString();
                    branchCode = ele.get(1).toString();
                    selectBranchList.add(new SelectItem(ele.get(1).toString().length() < 2
                            ? "0" + ele.get(1).toString() : ele.get(1).toString(), ele.get(0).toString()));
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void getFinancialYear() {
        try {
            List list = payrollRemote.getFinYears(getOrgnBrCode());
            for (int i = 0; i < list.size(); i++) {
                Object[] ele = (Object[]) list.get(0);
                yearFromDate = ele[0].toString().trim();
                yearToDate = ele[1].toString().trim();
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String validations() {
        this.setMessage("");
        if (category.equalsIgnoreCase("0")) {
            disableBankBranch = true;
            return "Please select category";
        }
        if (branch.equalsIgnoreCase("0")) {
            return "Please select the branch";
        }
        if (frDate == null) {
            return "Please select From Date!!";
        }

        if (toDate == null) {
            return "Please select To Date!!";
        }

        if (toDate.before(frDate) || toDate.equals(frDate)) {
            return "todate should be greater than fromdate !!";
        }
        return "true";
    }

    public void bonusChecklistReport() {
        try {
            String errorResult = validations();
            if (!errorResult.equalsIgnoreCase("true")) {
                this.setMessage(errorResult);
                return;
            }

            bonusCheckListdata = payrollOthrMgm.getBonusCheckListData(branch, ymd.format(frDate), ymd.format(toDate));

            String repName = "Bonus Checklist Report";
            Map fillParams = new HashMap();
            String BankName = commomRemote.getBankName();
            fillParams.put("pBankName", BankName);
            fillParams.put("pBranchName", branchName);
            fillParams.put("pBranchCode", branchCode);
            fillParams.put("pFromDate", dmy.format(frDate));
            fillParams.put("pToDate", dmy.format(toDate));
            new ReportBean().openPdf(repName, "bonus_checklist_report", new JRBeanCollectionDataSource(bonusCheckListdata), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", repName);
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }

    }

    public void btnRefreshAction() {
        this.setMessage("");
        this.setFrDate(null);
        this.setToDate(null);
        this.setBranch("0");
        this.setCategory("0");
        disableBankBranch = true;

        selectBranchList.clear();

    }

    public String btnExitAction() {
        return "case1";
    }

    public Date getFrDate() {
        return frDate;
    }

    public void setFrDate(Date frDate) {
        this.frDate = frDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<SelectItem> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<SelectItem> categoryList) {
        this.categoryList = categoryList;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<SelectItem> getSelectBranchList() {
        return selectBranchList;
    }

    public void setSelectBranchList(List<SelectItem> selectBranch) {
        this.selectBranchList = selectBranchList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDisableBankBranch() {
        return disableBankBranch;
    }

    public void setDisableBankBranch(boolean disableBankBranch) {
        this.disableBankBranch = disableBankBranch;
    }
}
