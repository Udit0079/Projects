/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.other;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.dto.report.InterestFdReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.DDSReportFacadeRemote;
import com.cbs.facade.report.OtherReportFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author sipl
 */
public class InterestFdReport extends BaseBean {

    private String message;
    private String ddAcType;
    private String lblAcType;
    private String branch;
    private List<SelectItem> branchList;
    List<SelectItem> ddAcTypeList = new ArrayList<SelectItem>();
    private String frAmt;
    private String toAmt;
    private String calFromDate;
    private String caltoDate;
    private String tdsFlag;
    private List<SelectItem> tdsflagList;
    private String acNature;
    private List<SelectItem> acNatureList;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    private OtherReportFacadeRemote facadeRemote;
    private CommonReportMethodsRemote commonBeanRemote;
    private TdReceiptManagementFacadeRemote tdFacade;
     private DDSReportFacadeRemote ddsRemote;
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
     

    public InterestFdReport() {
        try {
            facadeRemote = (OtherReportFacadeRemote) ServiceLocator.getInstance().lookup("OtherReportFacade");
            commonBeanRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ddsRemote = (DDSReportFacadeRemote) ServiceLocator.getInstance().lookup("DDSReportFacade");
            setCalFromDate(dmyFormat.format(dt));
            setCaltoDate(dmyFormat.format(dt));
            setMessage("");

            List brnLst = new ArrayList();
            brnLst = commonBeanRemote.getBranchCodeList(getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            if (!brnLst.isEmpty()) {
                for (int i = 0; i < brnLst.size(); i++) {
                    Vector ele7 = (Vector) brnLst.get(i);
                    branchList.add(new SelectItem(ele7.get(0).toString().length() < 2 ? "0" + ele7.get(0).toString() : ele7.get(0).toString(), ele7.get(1).toString()));
                }
            }
            
            tdsflagList = new ArrayList<SelectItem>();
            tdsflagList.add(new SelectItem("ALL","ALL"));
            tdsflagList.add(new SelectItem("Y","Yes"));
            tdsflagList.add(new SelectItem("N","No"));

            List acCodeList = commonBeanRemote.getFDMSAcTypeList();
            ddAcTypeList = new ArrayList<SelectItem>();
            ddAcTypeList.add(new SelectItem("ALL", "ALL"));
            for (Object element : acCodeList) {
                Vector vector = (Vector) element;
                ddAcTypeList.add(new SelectItem(vector.get(0).toString(), vector.get(0).toString()));
            } 
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
      public void getacNature() {
        try {
            acNatureList = new ArrayList<SelectItem>();
            acNatureList.add(new SelectItem("0", "--Select--"));
            //List acNtureList = ddsRemote.getAccountNatureClassification("'C','B'");
            List acNtureList = commonBeanRemote.getAcNatureIncludeRdFdMsDs();
            if (!acNtureList.isEmpty()) {
                for (int i = 0; i < acNtureList.size(); i++) {
                    Vector vec = (Vector) acNtureList.get(i);
                    acNatureList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
       public void getAcTypeByAcNature() {
        try {
            ddAcTypeList= new ArrayList<SelectItem>();
            ddAcTypeList.add(new SelectItem("ALL", "ALL"));
            List actCodeList = ddsRemote.getAcctCodeByNature(this.acNature);
            if (!actCodeList.isEmpty()) {
                for (int i = 0; i < actCodeList.size(); i++) {
                    Vector vec = (Vector) actCodeList.get(i);
                    ddAcTypeList.add(new SelectItem(vec.get(0)));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public boolean validate() {
        try {
            
            Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
            
            if(this.getDdAcType().equalsIgnoreCase("0")){
                this.setMessage("Please Select Account Type");
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
                String bankName="",bankAddress="";
                String acCode = this.getDdAcType();
                List<InterestFdReportPojo> interestRepList = facadeRemote.interestFdReport(acNature,acCode,this.getFrAmt(),this.toAmt,
                        ymdFormat.format(dmyFormat.parse(this.getCalFromDate())),ymdFormat.format(dmyFormat.parse(this.getCaltoDate())),this.branch,tdsFlag);
                
                
                FastReportBuilder fastReportBuilder = genrateDaynamicReport();
                new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(interestRepList), fastReportBuilder, "INTEREST FD REPORT");             
            }
        } catch (ApplicationException e) {
            setMessage(e.getMessage());            
        } catch (Exception e) {
            setMessage(e.getMessage());            
        }        
    }
    
    public FastReportBuilder genrateDaynamicReport() {
        FastReportBuilder fastReport = new FastReportBuilder();
        try {
            int width = 0;
            
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);            
            
            AbstractColumn custId = ReportBean.getColumn("custId", String.class, "Customer Id", 60);
            AbstractColumn custName = ReportBean.getColumn("custName", String.class, "Customer Name", 200);
            AbstractColumn fatherName = ReportBean.getColumn("fatherName", String.class, "Father's Name", 200);
            AbstractColumn dob = ReportBean.getColumn("dob", String.class, "Date of Birth", 60);            
            AbstractColumn panNo = ReportBean.getColumn("panNo", String.class, "Pan No", 80);
            AbstractColumn perAddr = ReportBean.getColumn("perAddr", String.class, "Permanent Address", 400);
            AbstractColumn acNo = ReportBean.getColumn("acNo", String.class, "Account No", 100);
            AbstractColumn recptNo = ReportBean.getColumn("recptNo", String.class, "Receipt No", 100);
            AbstractColumn prnAmt = ReportBean.getColumn("prnAmt", BigDecimal.class, "Principal Amount", 150);
            AbstractColumn depDate = ReportBean.getColumn("depDate", String.class, "Deposit Date", 60);
            AbstractColumn intAmt = ReportBean.getColumn("intAmt", BigDecimal.class, "Interest Amount", 150);
            AbstractColumn intPayDate = ReportBean.getColumn("intPayDate", String.class, "Interest Pay Date", 60);
            AbstractColumn tdsAmt = ReportBean.getColumn("tdsAmt", BigDecimal.class, "Tds Amount", 150);
            AbstractColumn tdsDate = ReportBean.getColumn("tdsDate", String.class, "Tds Date", 60);
            
            fastReport.addColumn(custId);
            width = width + custId.getWidth();
            
            fastReport.addColumn(custName);
            width = width + custName.getWidth();
            
            fastReport.addColumn(fatherName);
            width = width + fatherName.getWidth();
            
            fastReport.addColumn(dob);
            width = width + dob.getWidth();
            
            fastReport.addColumn(panNo);
            width = width + panNo.getWidth();
            
            fastReport.addColumn(perAddr);
            width = width + perAddr.getWidth();
            
            fastReport.addColumn(acNo);
            width = width + acNo.getWidth();
            
            fastReport.addColumn(recptNo);
            width = width + recptNo.getWidth();
            
            fastReport.addColumn(prnAmt);
            prnAmt.setStyle(style);
            width = width + 2 * prnAmt.getWidth();
            
            fastReport.addColumn(depDate);
            width = width + depDate.getWidth();
            
            fastReport.addColumn(intAmt);
            intAmt.setStyle(style);
            //width = width + 2 * intAmt.getWidth();
            
            fastReport.addColumn(intPayDate);
            width = width + intPayDate.getWidth();
            
             fastReport.addColumn(tdsAmt);
             tdsAmt.setStyle(style);
             
             fastReport.addColumn(tdsDate);
             width = width + tdsDate.getWidth();
            
            Page page = new Page(842, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page);
            fastReport.setTitle("INTEREST FD REPORT");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fastReport;
    }
    
    public void refreshAction(){
        this.setDdAcType("0");
        setCalFromDate(dmyFormat.format(dt));
        setCaltoDate(dmyFormat.format(dt));
        this.setFrAmt("0");
        this.setToAmt("0"); 
        this.setMessage("");
    }
    
    public String exitAction() {
        refreshAction();
        return "case1";
    }    
}
