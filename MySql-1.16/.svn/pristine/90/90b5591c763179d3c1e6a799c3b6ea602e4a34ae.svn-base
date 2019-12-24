/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.ho;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.RbiInspectionPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.FdRdFacadeRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class TermLoanDetail extends BaseBean {

    private String msg;
    private String date;
    private String selectAcType;
    private String acNature;
    private List<SelectItem> selectAcTypeList;
    private List<SelectItem> acNatureList;
    private LoanReportFacadeRemote beanLocal;
    private FdRdFacadeRemote remote;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private String branchCode;
    private List<SelectItem> branchCodeList;
    private CommonReportMethodsRemote remoteCode;
    private List<RbiInspectionPojo> reportList;

    public TermLoanDetail() {
        try {
            beanLocal = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            remoteCode = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            remote = (FdRdFacadeRemote) ServiceLocator.getInstance().lookup("FdRdFacade");

            List brnCode = new ArrayList();
            brnCode = remoteCode.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brnCode.isEmpty()) {
                for (int i = 0; i < brnCode.size(); i++) {
                    Vector brnVector = (Vector) brnCode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            List natureList = remoteCode.getAcNaturetldlcaList();
            acNatureList = new ArrayList<SelectItem>();
            acNatureList.add(new SelectItem("0", "--Select--"));
            if (natureList.size() > 0) {
                for (int i = 0; i < natureList.size(); i++) {
                    Vector vec = (Vector) natureList.get(i);
                    acNatureList.add(new SelectItem(vec.get(0).toString()));
                }
            }

            List codeList = beanLocal.getDistinctAcCodeByAcNature();
            selectAcTypeList = new ArrayList<SelectItem>();
            selectAcTypeList.add(new SelectItem("0", "--Select--"));

            this.setDate(getTodayDate());
        } catch (Exception ex) {
            this.setMsg(ex.getLocalizedMessage());
        }
    }

    public void getAcTypeForNature() {
        selectAcTypeList = new ArrayList<SelectItem>();
        try {
            selectAcTypeList.add(new SelectItem("All", "All"));

            List list = remoteCode.getAcctTypeAsAcNatureOnlyDB(acNature);
            for (int i = 0; i < list.size(); i++) {
                Vector element = (Vector) list.get(i);
                selectAcTypeList.add(new SelectItem(element.get(0).toString()));
            }
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
    }

//     public void viewReport() {
//         try{
//             
//         }catch(Exception ex){
//             this.setMsg(ex.getLocalizedMessage());
//         }
//     }
    public boolean validation() {
        if (acNature.equals(null) || acNature.equalsIgnoreCase("") || acNature.equalsIgnoreCase("0")) {
            this.setMsg("Please select the account nature.");
            return false;
        }
        if (selectAcType.equalsIgnoreCase("0") || selectAcType.equalsIgnoreCase("") || selectAcType.equalsIgnoreCase(null)) {
            this.setMsg("Please select the account type .");
            return false;
        }
        if (date.equals(null)) {
            this.setMsg("Please fill the date .");
            return false;
        }
        return true;
    }

    public void excelPrint() {
        try {
            reportList = new ArrayList<RbiInspectionPojo>();
            if (validation() == true) {
                reportList = remote.rbiInspectionData(this.branchCode, this.acNature, this.selectAcType, ymd.format(sdf.parse(date)));
                if (reportList.isEmpty()) {
                    throw new ApplicationException("Data does not exist !");
                }
                FastReportBuilder fastReportBuilder = generateDynamicReport();
                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(reportList), fastReportBuilder, "RBI_INSPECTION_DETAIL_FOR_" + acNature);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public FastReportBuilder generateDynamicReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);
            AbstractColumn srno = ReportBean.getColumn("srno", Integer.class, "Sr no.", 100);
            
            AbstractColumn custId = ReportBean.getColumn("custId", String.class, "ID Number", 90);
            AbstractColumn folioNo = ReportBean.getColumn("folioNo", String.class, "Membership No.", 90);
            AbstractColumn membershipDate = ReportBean.getColumn("membershipDate", String.class, "Date of Membership", 90);
            AbstractColumn shareBal = ReportBean.getColumn("shareBal", BigDecimal.class, "Share Balance", 80);
            AbstractColumn thriftBal = ReportBean.getColumn("thriftBal", BigDecimal.class, "Thrift Balance", 80);
            
            AbstractColumn acNo = ReportBean.getColumn("acNo", String.class, "A/c .Number", 90);
            AbstractColumn name = ReportBean.getColumn("name", String.class, "Borrower's Name", 200);
            AbstractColumn sancDt = ReportBean.getColumn("sancDt", String.class, "Date of Sanction", 150);
            AbstractColumn lastDateReview = ReportBean.getColumn("lastDateReview", String.class, "Last review Date", 0);
            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                lastDateReview = ReportBean.getColumn("lastDateReview", String.class, "Last review Date", 100);
            }
            AbstractColumn loanType = ReportBean.getColumn("loanType", String.class, "Type of Term Loan", 0);
            if (!(acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                loanType = ReportBean.getColumn("loanType", String.class, "Type of Term Loan", 100);
            }
            AbstractColumn sancAmt = ReportBean.getColumn("sancAmt", BigDecimal.class, "Sanction Amount", 200);
            AbstractColumn dp = ReportBean.getColumn("dp", String.class, "Drawing Power", 0);
            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                dp = ReportBean.getColumn("dp", String.class, "Drawing Power", 100);
            }
            AbstractColumn disbAmt = ReportBean.getColumn("disbAmt", BigDecimal.class, "Amount Disbursed", 0);
            AbstractColumn disbDt = ReportBean.getColumn("disbDt", String.class, "Date of Disbursal", 0);
            AbstractColumn emiAmt = ReportBean.getColumn("emiAmt", BigDecimal.class, "Emi Amount", 0);
            AbstractColumn emiStrtDt = ReportBean.getColumn("emiStrtDt", String.class, "Date of start of Emi", 0);
            if (!acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                disbAmt = ReportBean.getColumn("disbAmt", BigDecimal.class, "Amount Disbursed", 200);
                disbDt = ReportBean.getColumn("disbDt", String.class, "Date of Disbursal", 150);
                emiAmt = ReportBean.getColumn("emiAmt", BigDecimal.class, "Emi Amount", 200);
                emiStrtDt = ReportBean.getColumn("emiStrtDt", String.class, "Date of start of Emi", 150);
            }

            AbstractColumn outsAmt1 = ReportBean.getColumn("outsAmt1", BigDecimal.class, "O/s amount 1", 200);
            AbstractColumn outsAmt2 = ReportBean.getColumn("outsAmt2", BigDecimal.class, "O/s amount 2", 0);
            AbstractColumn outsAmt3 = ReportBean.getColumn("outsAmt3", BigDecimal.class, "O/s amount 3", 0);
            AbstractColumn outsAmt4 = ReportBean.getColumn("outsAmt4", BigDecimal.class, "O/s amount 4", 0);
            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                outsAmt2 = ReportBean.getColumn("outsAmt2", BigDecimal.class, "O/s amount 2", 200);
                outsAmt3 = ReportBean.getColumn("outsAmt3", BigDecimal.class, "O/s amount 3", 200);
                outsAmt4 = ReportBean.getColumn("outsAmt4", BigDecimal.class, "O/s amount 4", 200);
            }
            AbstractColumn dueEmi = ReportBean.getColumn("dueEmi", BigDecimal.class, "Total Due Emi Amount", 0);
            AbstractColumn noOfemiDue = ReportBean.getColumn("noOfemiDue", String.class, "Total no. fo Emi", 0);
            AbstractColumn totEmiPaid = ReportBean.getColumn("totEmiPaid", BigDecimal.class, "Total paid Emi Amount", 0);
            AbstractColumn noOfEmiPaid = ReportBean.getColumn("noOfEmiPaid", String.class, "Total no. of paid Emi", 0);
            if (!acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                dueEmi = ReportBean.getColumn("dueEmi", BigDecimal.class, "Total Due Emi Amount", 200);
                noOfemiDue = ReportBean.getColumn("noOfemiDue", String.class, "Total no. of Emi unpaid", 100);
                totEmiPaid = ReportBean.getColumn("totEmiPaid", BigDecimal.class, "Total paid Emi Amount", 200);
                noOfEmiPaid = ReportBean.getColumn("noOfEmiPaid", String.class, "Total no. of paid Emi", 100);
            }
            AbstractColumn security = ReportBean.getColumn("security", String.class, "Security Code", 250);
            AbstractColumn totalSecurityValue = ReportBean.getColumn("totalSecurityValue", BigDecimal.class, "Total Security Value", 150);
            AbstractColumn accountStatus = ReportBean.getColumn("accountStatus", String.class, "Account Status", 100);

            fastReport.addColumn(srno);
            width = width + srno.getWidth();
            
            fastReport.addColumn(custId);
            width = width + custId.getWidth();
            
            fastReport.addColumn(folioNo);
            width = width + folioNo.getWidth();
            
            fastReport.addColumn(membershipDate);
            width = width + membershipDate.getWidth();
            
            fastReport.addColumn(shareBal);
            width = width + shareBal.getWidth();
            
            fastReport.addColumn(thriftBal);
            width = width + thriftBal.getWidth();

            fastReport.addColumn(acNo);
            width = width + acNo.getWidth();

            fastReport.addColumn(name);
            width = width + name.getWidth();

            fastReport.addColumn(sancDt);
            width = width + sancDt.getWidth();

            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                fastReport.addColumn(lastDateReview);
                width = width + lastDateReview.getWidth();
            }

            if (!(acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                fastReport.addColumn(loanType);
                width = width + loanType.getWidth();
            }

            fastReport.addColumn(sancAmt);
            width = width + sancAmt.getWidth();

            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                fastReport.addColumn(dp);
                width = width + dp.getWidth();
            }
            if (!acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {

                fastReport.addColumn(disbAmt);
                width = width + disbAmt.getWidth();

                fastReport.addColumn(disbDt);
                width = width + disbDt.getWidth();

                fastReport.addColumn(emiAmt);
                width = width + emiAmt.getWidth();

                fastReport.addColumn(emiStrtDt);
                width = width + emiStrtDt.getWidth();

            }


            fastReport.addColumn(outsAmt1);
            width = width + outsAmt1.getWidth();
            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                fastReport.addColumn(outsAmt2);
                width = width + outsAmt2.getWidth();
                fastReport.addColumn(outsAmt3);
                width = width + outsAmt3.getWidth();
                fastReport.addColumn(outsAmt4);
                width = width + outsAmt4.getWidth();
            }

            if (!acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                fastReport.addColumn(dueEmi);
                width = width + dueEmi.getWidth();

                fastReport.addColumn(noOfemiDue);
                width = width + noOfemiDue.getWidth();

                fastReport.addColumn(totEmiPaid);
                width = width + totEmiPaid.getWidth();

                fastReport.addColumn(noOfEmiPaid);
                width = width + noOfEmiPaid.getWidth();
            }
            fastReport.addColumn(security);
            width = width + security.getWidth();
            fastReport.addColumn(totalSecurityValue);
            width = width + totalSecurityValue.getWidth();
            fastReport.addColumn(accountStatus);
            width = width + accountStatus.getWidth();

            Page page = new Page(842, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("Rbi Inspection Report");
        } catch (Exception ex) {
            this.setMsg(ex.getMessage());
        }
        return fastReport;
    }

    public void refreshForm() {
        this.setSelectAcType("0");
        this.setAcNature("0");
        date = getTodayDate();
        msg = "";
    }

    public String exitAction() {
        return "case1";
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSelectAcType() {
        return selectAcType;
    }

    public void setSelectAcType(String selectAcType) {
        this.selectAcType = selectAcType;
    }

    public List<SelectItem> getSelectAcTypeList() {
        return selectAcTypeList;
    }

    public void setSelectAcTypeList(List<SelectItem> selectAcTypeList) {
        this.selectAcTypeList = selectAcTypeList;
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

    public List<RbiInspectionPojo> getReportList() {
        return reportList;
    }

    public void setReportList(List<RbiInspectionPojo> reportList) {
        this.reportList = reportList;
    }
}
