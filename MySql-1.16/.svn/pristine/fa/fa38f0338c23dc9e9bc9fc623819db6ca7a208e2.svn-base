/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.report.VillageWiseEMIDetailPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.pojo.SortedByAcType;
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
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author Athar Reza
 */
public class VillWiseEMIDetail extends BaseBean {

    private String message;
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String acctType;
    private List<SelectItem> acctTypeList;
    private String accountMang;
    private List<SelectItem> accountMangList;
    private String groupID;
    private List<SelectItem> groupIDList;
    Date frDt;
    Date toDt;
    Date date = new Date();
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private CommonReportMethodsRemote common;
    private LoanInterestCalculationFacadeRemote beanRemote;
    private LoanReportFacadeRemote loanBeanRemote;
    private FtsPostingMgmtFacadeRemote fts;
    List<VillageWiseEMIDetailPojo> reportList = new ArrayList<VillageWiseEMIDetailPojo>();

    public String getAccountMang() {
        return accountMang;
    }

    public void setAccountMang(String accountMang) {
        this.accountMang = accountMang;
    }

    public List<SelectItem> getAccountMangList() {
        return accountMangList;
    }

    public void setAccountMangList(List<SelectItem> accountMangList) {
        this.accountMangList = accountMangList;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public List<SelectItem> getGroupIDList() {
        return groupIDList;
    }

    public void setGroupIDList(List<SelectItem> groupIDList) {
        this.groupIDList = groupIDList;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public List<SelectItem> getAcctTypeList() {
        return acctTypeList;
    }

    public void setAcctTypeList(List<SelectItem> acctTypeList) {
        this.acctTypeList = acctTypeList;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getFrDt() {
        return frDt;
    }

    public void setFrDt(Date frDt) {
        this.frDt = frDt;
    }

    public Date getToDt() {
        return toDt;
    }

    public void setToDt(Date toDt) {
        this.toDt = toDt;
    }

    public VillWiseEMIDetail() {
        try {
            setFrDt(date);
            setToDt(date);

            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            beanRemote = (LoanInterestCalculationFacadeRemote) ServiceLocator.getInstance().lookup("LoanInterestCalculationFacade");
            loanBeanRemote = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");

            List brnCode = new ArrayList();
            brnCode = common.getAlphacodeBasedOnBranch(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brnCode.isEmpty()) {
                for (int i = 0; i < brnCode.size(); i++) {
                    Vector brnVec = (Vector) brnCode.get(i);
                    branchCodeList.add(new SelectItem(CbsUtil.lPadding(2, Integer.parseInt(brnVec.get(1).toString())), brnVec.get(0).toString()));
                }
            }
            getAccountManagerData();
            getActType();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getActType() {
        try {
            List acTypeList = new ArrayList();
            acTypeList = beanRemote.getAcctType();
            if (!acTypeList.isEmpty()) {
                acctTypeList = new ArrayList<SelectItem>();
                acctTypeList.add(new SelectItem("ALL", "ALL"));
                for (int i = 0; i < acTypeList.size(); i++) {
                    Vector ele = (Vector) acTypeList.get(i);
                    acctTypeList.add(new SelectItem(ele.get(0).toString()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getAccountManagerData() {
        setMessage("");
        try {
            List mangList = new ArrayList();
            mangList = common.getAccountManager();
            accountMangList = new ArrayList<SelectItem>();
            if (!mangList.isEmpty()) {
                accountMangList.add(new SelectItem("S", "--Select--"));
                for (int i = 0; i < mangList.size(); i++) {
                    Vector ele = (Vector) mangList.get(i);
                    accountMangList.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getGroupIDdata() {
        setMessage("");
        try {

            if (this.accountMang == null || this.accountMang.equalsIgnoreCase("S")) {
                setMessage("Please select Agent Manager/Officer!!!");
                return;
            }

            List groupList = new ArrayList();
            groupList = common.getGroupId(accountMang);
            groupIDList = new ArrayList<SelectItem>();
            groupIDList.add(new SelectItem("ALL", "ALL"));
            if (!groupList.isEmpty()) {
                for (int i = 0; i < groupList.size(); i++) {
                    Vector eleId = (Vector) groupList.get(i);
                    groupIDList.add(new SelectItem(eleId.get(0).toString(), eleId.get(1).toString()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnHtmlAction() {
        setMessage("");
        String report = "Village Wise EMI Detail Report";
        String acType = "ALL";
        String bankName = "", bankAddress = "";
        try {

            if (accountMang == null || accountMang.equalsIgnoreCase("S")) {
                setMessage("Please select Agent Manager/Officer!!!");
                return;
            }

            if (toDt == null) {
                setMessage("Please fill to Date!!!");
                return;
            }
            if (!this.branchCode.equalsIgnoreCase("0A")) {
                List dataList1 = common.getBranchNameandAddress(this.branchCode);
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
            } else {
                List dataList1 = common.getBranchNameandAddress("90");
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
            }
            String bnkCode = fts.getBankCode();
            Map fillParams = new HashMap();
            if (!acctType.equalsIgnoreCase("ALL")) {
                acType = common.getAcctDecription(acctType);
                fillParams.put("pActDesc", acType);
            } else {
                fillParams.put("pActDesc", acType);
            }
            List phoneList = common.getPhoneNo(branchCode);
            Vector vtr = (Vector) phoneList.get(0);
            List officeIdList = common.getOfficeDeptHead(this.accountMang, this.groupID);
            Vector vtr1 = (Vector) officeIdList.get(0);
            String yyyy = ymd.format(toDt).substring(0, 4);
            String mm = ymd.format(toDt).substring(4, 6);
            fillParams.put("pMonthYear", CbsUtil.getMonthName(Integer.parseInt(mm)) + " " + yyyy);
            fillParams.put("pReportName", common.getEmiReportName() + " Emi Detail Report");
            fillParams.put("pBankName", bankName);
            fillParams.put("pBankAddress", bankAddress);
            fillParams.put("pReportDt", dmy.format(toDt));
            fillParams.put("pfrDt", dmy.format(frDt));
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pPhoneNo", vtr.get(0).toString());
            fillParams.put("pOfficeHead", vtr1.get(0).toString());
            fillParams.put("pOfficeName", vtr1.get(1).toString());
            fillParams.put("pOfficeHeadAdd", vtr1.get(2).toString());
            fillParams.put("pDeptId", "Dept Id : [" + this.groupID + "] " + common.getDeptDescByGroupId(groupID));
            if (!acctType.equalsIgnoreCase("ALL")) {
                fillParams.put("pAcctType", this.acctType);
            } else {
                fillParams.put("pAcctType", this.acctType);
            }
            fillParams.put("pAccountManager", this.accountMang);
            fillParams.put("pGroupId", this.groupID);
            if (bnkCode.equalsIgnoreCase("NABU")) {
                fillParams.put("pHeader", "Over Due Prin/Interest");
            } else {
                fillParams.put("pHeader", "Premium Amount");
            }
            reportList = loanBeanRemote.getVillWeseEmiDetail("", branchCode, acctType, "", ymd.format(frDt), ymd.format(toDt), accountMang, groupID);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist!!!");
            }

            ComparatorChain chObj = new ComparatorChain();
            chObj.addComparator(new SortedByAcType());
            Collections.sort(reportList, chObj);

            new ReportBean().jasperReport("VillageWiseEmiDetail_Letter_AllAcno_Text", "text/html",
                    new JRBeanCollectionDataSource(reportList), fillParams, "OfficeId/Village Wise EMI Detail Report");
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void btnPdfAction() {
        setMessage("");
        String report = "OfficeId/Village Wise EMI Detail Report";
        String acType = "ALL";
        String bankName = "", bankAddress = "";
        try {

            if (accountMang == null || accountMang.equalsIgnoreCase("S")) {
                setMessage("Please select Agent Manager/Officer!!!");
                return;
            }

            if (toDt == null) {
                setMessage("Please fill to Date!!!");
                return;
            }
            if (!this.branchCode.equalsIgnoreCase("0A")) {
                List dataList1 = common.getBranchNameandAddress(this.branchCode);
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
            } else {
                List dataList1 = common.getBranchNameandAddress("90");
                if (dataList1.size() > 0) {
                    bankName = (String) dataList1.get(0);
                    bankAddress = (String) dataList1.get(1);
                }
            }
            String bnkCode = fts.getBankCode();
            Map fillParams = new HashMap();
            if (!acctType.equalsIgnoreCase("ALL")) {
                acType = common.getAcctDecription(acctType);
                fillParams.put("pActDesc", acType);
            } else {
                fillParams.put("pActDesc", acType);
            }
            List phoneList = common.getPhoneNo(branchCode);
            Vector vtr = (Vector) phoneList.get(0);
            List officeIdList = common.getOfficeDeptHead(this.accountMang, this.groupID);
            Vector vtr1 = (Vector) officeIdList.get(0);
            String yyyy = ymd.format(toDt).substring(0, 4);
            String mm = ymd.format(toDt).substring(4, 6);
            fillParams.put("pMonthYear", CbsUtil.getMonthName(Integer.parseInt(mm)) + " " + yyyy);
            fillParams.put("pReportName", common.getEmiReportName() + " Emi Detail Report");
            fillParams.put("pBankName", bankName);
            fillParams.put("pBankAddress", bankAddress);
            fillParams.put("pReportDt", dmy.format(toDt));
            fillParams.put("pfrDt", dmy.format(frDt));
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pPhoneNo", vtr.get(0).toString());
            fillParams.put("pOfficeHead", vtr1.get(0).toString());
            fillParams.put("pOfficeName", vtr1.get(1).toString());
            fillParams.put("pOfficeHeadAdd", vtr1.get(2).toString());
            fillParams.put("pDeptId", "Dept Id : [" + this.groupID + "] " + common.getDeptDescByGroupId(groupID));
            if (!acctType.equalsIgnoreCase("ALL")) {
                fillParams.put("pAcctType", this.acctType);
            } else {
                fillParams.put("pAcctType", this.acctType);
            }
            fillParams.put("pAccountManager", this.accountMang);
            fillParams.put("pGroupId", this.groupID);
            if (bnkCode.equalsIgnoreCase("NABU")) {
                fillParams.put("pHeader", "OverDue Interest");
            } else {
                fillParams.put("pHeader", "Premium Amount");
            }
            reportList = loanBeanRemote.getVillWeseEmiDetail("", branchCode, acctType, "", ymd.format(frDt), ymd.format(toDt), accountMang, groupID);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist!!!");
            }

            ComparatorChain chObj = new ComparatorChain();
            chObj.addComparator(new SortedByAcType());
            Collections.sort(reportList, chObj);

            new ReportBean().openPdf("Office Id Wise EMI detail_" + ymd.format(frDt) + " to " + ymd.format(toDt), "VillageWiseEmiDetail_Letter_AllAcno",
                    new JRBeanCollectionDataSource(reportList), fillParams);
            ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
            HttpSession httpSession = getHttpSession();
            httpSession.setAttribute("ReportName", report);

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void refresh() {
        this.setMessage("");
        setFrDt(date);
        setToDt(date);
        setAccountMang("S");
        setGroupID("ALL");

    }

    public String exitAction() {
        return "case1";
    }
}
