/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.misc;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.dto.report.Form60ReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class Form16Annexture114B extends BaseBean {

    private String message;
    private String branch;
    private List<SelectItem> branchList;
    //private String dateType;
    private boolean dateType;
    private String flag;
    private String calFromDate;
    private String caltoDate;
    Date dt = new Date();
    private Boolean disableDateType;
    private TdReceiptManagementFacadeRemote tdFacade;
    private MiscReportFacadeRemote remoteFacade;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    List<Form60ReportPojo> reportList = new ArrayList<Form60ReportPojo>();

    public Form16Annexture114B() {

        try {
            this.dateType = false;
            setCalFromDate(dmyFormat.format(dt));
            setCaltoDate(dmyFormat.format(dt));
            tdFacade = (TdReceiptManagementFacadeRemote) ServiceLocator.getInstance().lookup("TdReceiptManagementFacade");
            remoteFacade = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            List brnLst = new ArrayList();
            brnLst = tdFacade.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }

            this.flag = "N";

        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void onDateType() {
        setMessage("");
        if (dateType == true) {
            this.flag = "Y";
        } else {
            this.flag = "N";
        }
    }

    public void printAction() {
        setMessage("");

        try {
            String repName = "";
            if (flag.equalsIgnoreCase("Y")) {
                repName = "Including  Transactions";
            } else {
                repName = "Excluding  Transactions";
            }
            if (!Validator.validateDate(calFromDate)) {
                this.setMessage("Please check from date");
                return;
            }
            if (!Validator.validateDate(caltoDate)) {
                this.setMessage("Please check to date");
                return;
            }

            if (dmyFormat.parse(calFromDate).after(dmyFormat.parse(caltoDate))) {
                this.setMessage("From date should be less then to date");
                return;
            }

            if (dmyFormat.parse(calFromDate).after(dt)) {
                this.setMessage("To date should be less or Equal to Current date");
                return;
            }

            if (dmyFormat.parse(caltoDate).after(dt)) {
                this.setMessage("To date should be less or Equal to Current date");
                return;
            }

            if (flag.equalsIgnoreCase("Y")) {
                if (dmyFormat.parse(calFromDate).after(dmyFormat.parse("09/11/2016"))) {
                    this.setMessage("From date should be less then 09/11/2016");
                    return;
                }

                if (dmyFormat.parse("30/12/2016").after(dmyFormat.parse(caltoDate))) {
                    this.setMessage("To date should be greater then 30/12/2016");
                    return;
                }
            }

            reportList = remoteFacade.getFormSixtyDetail(branch, ymdFormat.format(dmyFormat.parse(this.getCalFromDate())), ymdFormat.format(dmyFormat.parse(this.getCaltoDate())), flag);
            if (reportList.isEmpty()) {
                throw new ApplicationException("Data does not exist !");
            }

            /* Open After Discussion 
             ComparatorChain chObj = new ComparatorChain();
             chObj.addComparator(new SortByCustId());            
             Collections.sort(reportList, chObj);
             */
            FastReportBuilder form60Reprot = genrateForm16Report();
            new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(reportList), form60Reprot, "FORM 60 (Annexture F)_" + repName);

        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public FastReportBuilder genrateForm16Report() {

        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;

            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn repSrNo = ReportBean.getColumn("repSrNo", Integer.class, "Report Serial Number", 70);
            AbstractColumn orgRepSrNo = ReportBean.getColumn("orgRepSrNo", Integer.class, "Original Report Serial", 70);
            AbstractColumn custId = ReportBean.getColumn("custId", String.class, "Customer ID", 70);
            AbstractColumn custName = ReportBean.getColumn("custName", String.class, "Person Name", 120);
            AbstractColumn dob = ReportBean.getColumn("dob", String.class, "Date of birth/Incorporation", 70);
            AbstractColumn fatherName = ReportBean.getColumn("fatherName", String.class, "Fathers`s Name(for individuals)", 120);
            AbstractColumn panAck = ReportBean.getColumn("panAck", String.class, "PAN Acknowledgement", 100);
            AbstractColumn aadharNo = ReportBean.getColumn("aadharNo", String.class, "Aadhar Number", 100);
            AbstractColumn idenType = ReportBean.getColumn("idenType", String.class, "Identification Type", 50);
            AbstractColumn idenNo = ReportBean.getColumn("idenNo", String.class, "Identification Number", 100);
            AbstractColumn flatNo = ReportBean.getColumn("flatNo", String.class, "Flat/Door/Building", 220);
            AbstractColumn nameOfPremises = ReportBean.getColumn("nameOfPremises", String.class, "Name of Premises/Building/Village", 100);
            AbstractColumn streetName = ReportBean.getColumn("streetName", String.class, "Road/Street", 100);
            AbstractColumn area = ReportBean.getColumn("area", String.class, "Area/Locality", 100);
            AbstractColumn city = ReportBean.getColumn("city", String.class, "City/Town", 100);
            AbstractColumn postalCode = ReportBean.getColumn("postalCode", String.class, "Postal Code", 100);
            AbstractColumn stateCode = ReportBean.getColumn("stateCode", String.class, "State Code", 100);
            AbstractColumn countryCode = ReportBean.getColumn("countryCode", String.class, "Country Code", 100);
            AbstractColumn mobile = ReportBean.getColumn("mobile", String.class, "Mobile/Telephone Number", 100);
            AbstractColumn estAgrIncom = ReportBean.getColumn("estAgrIncom", String.class, "Estimated agricultural income", 100);
            AbstractColumn estNonAgrIncom = ReportBean.getColumn("estNonAgrIncom", String.class, "Estimated non-agricultural income", 100);
            AbstractColumn remarks = ReportBean.getColumn("remarks", String.class, "Remarks", 100);
            AbstractColumn form60Ack = ReportBean.getColumn("form60Ack", String.class, "Form 60 Acknowledgement Number", 100);
            AbstractColumn txnDate = ReportBean.getColumn("txnDate", String.class, "Transaction Date", 100);
            AbstractColumn txnId = ReportBean.getColumn("txnId", String.class, "Transaction ID", 100);
            AbstractColumn txnType = ReportBean.getColumn("txnType", String.class, "Transaction Type", 100);
            AbstractColumn txnAmount = ReportBean.getColumn("txnAmount", String.class, "Transaction Account", 100);
            AbstractColumn txnMode = ReportBean.getColumn("txnMode", String.class, "Transaction Mode", 100);

            fastReport.addColumn(repSrNo);
            width = width + repSrNo.getWidth();

            fastReport.addColumn(orgRepSrNo);
            width = width + orgRepSrNo.getWidth();

            fastReport.addColumn(custId);
            width = width + custId.getWidth();

            fastReport.addColumn(custName);
            width = width + custName.getWidth();

            fastReport.addColumn(dob);
            width = width + dob.getWidth();

            fastReport.addColumn(fatherName);
            width = width + fatherName.getWidth();

            fastReport.addColumn(panAck);
            width = width + panAck.getWidth();

            fastReport.addColumn(aadharNo);
            width = width + aadharNo.getWidth();

            fastReport.addColumn(idenType);
            width = width + idenType.getWidth();

            fastReport.addColumn(idenNo);
            width = width + idenNo.getWidth();

            fastReport.addColumn(flatNo);
            width = width + flatNo.getWidth();

            fastReport.addColumn(nameOfPremises);
            width = width + nameOfPremises.getWidth();

            fastReport.addColumn(streetName);
            width = width + streetName.getWidth();

            fastReport.addColumn(area);
            width = width + area.getWidth();

            fastReport.addColumn(city);
            width = width + city.getWidth();

            fastReport.addColumn(postalCode);
            width = width + postalCode.getWidth();

            fastReport.addColumn(stateCode);
            width = width + stateCode.getWidth();

            fastReport.addColumn(countryCode);
            width = width + countryCode.getWidth();

            fastReport.addColumn(mobile);
            width = width + mobile.getWidth();

            fastReport.addColumn(estAgrIncom);
            width = width + estAgrIncom.getWidth();

            fastReport.addColumn(estNonAgrIncom);
            width = width + estNonAgrIncom.getWidth();

            fastReport.addColumn(remarks);
            width = width + remarks.getWidth();

            fastReport.addColumn(form60Ack);
            width = width + form60Ack.getWidth();

            fastReport.addColumn(txnDate);
            width = width + txnDate.getWidth();

            fastReport.addColumn(txnId);
            width = width + txnId.getWidth();

            fastReport.addColumn(txnType);
            width = width + txnType.getWidth();

            fastReport.addColumn(txnAmount);
            txnAmount.setStyle(style);
            width = width + txnAmount.getWidth();

            fastReport.addColumn(txnMode);
            width = width + txnMode.getWidth();


            Page page = new Page(1410, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("FORM 60 (Annexture F)");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }

    public void refreshAction() {
        this.setMessage("");
        this.flag = "N";
        setCalFromDate(dmyFormat.format(dt));
        setCaltoDate(dmyFormat.format(dt));
        this.disableDateType = false;
        this.dateType = false;

    }

    public String exitAction() {
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

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public Boolean getDisableDateType() {
        return disableDateType;
    }

    public void setDisableDateType(Boolean disableDateType) {
        this.disableDateType = disableDateType;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public boolean isDateType() {
        return dateType;
    }

    public void setDateType(boolean dateType) {
        this.dateType = dateType;
    }
}
