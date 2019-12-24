/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.neftrtgs;

/**
 *
 * @author root
 */

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.neftrtgs.UploadNeftRtgsMgmtFacadeRemote;
import com.cbs.pojo.neftrtgs.HDFCInwardNEFTReturnPojo;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.ReportBean;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import com.cbs.web.controller.BaseBean;
import java.util.Date;

/**
 *
 * @author root
 */
public class HDFCInwardNEFTReturn extends BaseBean{

    private String message;
    private String toDt;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    List<HDFCInwardNEFTReturnPojo> repList = new ArrayList<HDFCInwardNEFTReturnPojo>();
    private UploadNeftRtgsMgmtFacadeRemote remote;
        
    public HDFCInwardNEFTReturn() {
        this.setMessage("");
        setToDt(getTodayDate());
        try {
            remote = (UploadNeftRtgsMgmtFacadeRemote) ServiceLocator.getInstance().lookup("UploadNeftRtgsMgmtFacade");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }
    public void btnRefreshAction() {
        setToDt(getTodayDate());
        this.setMessage("");
    }

    public String btnExitAction() {
        btnRefreshAction();
        return "case1";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    
    public FastReportBuilder excelPrint() {
        this.setMessage("");
        FastReportBuilder fastReport = new FastReportBuilder();
        String toDate = "";
        try {

            if (toDt == null || toDt.equalsIgnoreCase("")) {
                setMessage("Please fill to Date");
                return null;
            }
            if (!Validator.validateDate(toDt)) {
                setMessage("Please select Proper to Date");
                return null;
            }
            System.err.println("Date : "+dmy.parse(toDt)+" "+new Date());
            if (dmy.parse(toDt).compareTo(new Date()) > 0) {
                setMessage("Date can not be greater than Current Date.");
                return null;
            }
            toDate = ymd.format(dmy.parse(toDt));
            
             

            repList = remote.getHDFCInwardNEFTReturn(toDate);
            if (repList.isEmpty()) {
                throw new ApplicationException("Data does not exit");
            }

            int width = 0;
            Style style = new Style();
            style.setHorizontalAlign(HorizontalAlign.RIGHT);
            style.setBorder(Border.THIN);

            AbstractColumn transactionRefNo = ReportBean.getColumn("transactionRefNo", String.class, "Transaction_Ref_No", 150);
            AbstractColumn amount = ReportBean.getColumn("amount", BigDecimal.class, "Amount", 200);
            AbstractColumn valueDate = ReportBean.getColumn("valueDate", String.class, "Value Date", 100);
            AbstractColumn branchCode = ReportBean.getColumn("branchCode", String.class, "Branch Code", 150);
            AbstractColumn senderAccountType = ReportBean.getColumn("senderAccountType", String.class, "Sender Account Type", 90);
            AbstractColumn remitterAccountNo = ReportBean.getColumn("remitterAccountNo", String.class, "Remitter Account No", 150);
            AbstractColumn remitterName = ReportBean.getColumn("remitterName", String.class, "Remitter Name", 200);
            AbstractColumn iFSCCode = ReportBean.getColumn("iFSCCode", String.class, "IFSC Code", 100);
            AbstractColumn debitAccount = ReportBean.getColumn("debitAccount", String.class, "Debit Account", 200);
            AbstractColumn beneficiaryAccounttype = ReportBean.getColumn("beneficiaryAccounttype", String.class, "Beneficiary Account type", 150);
            AbstractColumn bankAccountNumber = ReportBean.getColumn("bankAccountNumber", String.class, "Bank Account Number", 150);
            AbstractColumn beneficiaryName = ReportBean.getColumn("beneficiaryName", String.class, "Beneficiary Name", 250);
            AbstractColumn remittanceDetails = ReportBean.getColumn("remittanceDetails", String.class, "Remittance Details", 100);
            AbstractColumn debitAccountSystem = ReportBean.getColumn("debitAccountSystem", String.class, "Debit Account System", 200);
            AbstractColumn originatorOfRemmittance = ReportBean.getColumn("originatorOfRemmittance", String.class, "Originator Of Remmittance", 200);
            AbstractColumn emailIDMobnumber = ReportBean.getColumn("emailIDMobnumber", String.class, "Email ID / Mobile numbe", 200);

            fastReport.addColumn(transactionRefNo);
            width = width + transactionRefNo.getWidth();

            fastReport.addColumn(amount);
            amount.setStyle(style);
            width = width + amount.getWidth();

            fastReport.addColumn(valueDate);
            width = width + valueDate.getWidth();

            fastReport.addColumn(branchCode);
            width = width + branchCode.getWidth();

            fastReport.addColumn(senderAccountType);
            width = width + senderAccountType.getWidth();
            
            fastReport.addColumn(remitterAccountNo);
            width = width + remitterAccountNo.getWidth();
            
            fastReport.addColumn(remitterName);
            width = width + remitterName.getWidth();
            
            fastReport.addColumn(iFSCCode);
            width = width + iFSCCode.getWidth();
            
            fastReport.addColumn(debitAccount);
            width = width + debitAccount.getWidth();
            
            fastReport.addColumn(beneficiaryAccounttype);
            width = width + beneficiaryAccounttype.getWidth();
            
            fastReport.addColumn(bankAccountNumber);
            width = width + bankAccountNumber.getWidth();
            
            fastReport.addColumn(beneficiaryName);
            width = width + beneficiaryName.getWidth();
            
            fastReport.addColumn(remittanceDetails);
            width = width + remittanceDetails.getWidth();
            
            fastReport.addColumn(debitAccountSystem);
            width = width + debitAccountSystem.getWidth();
            
            fastReport.addColumn(originatorOfRemmittance);
            width = width + originatorOfRemmittance.getWidth();
            
            fastReport.addColumn(emailIDMobnumber);
            width = width + emailIDMobnumber.getWidth();
            
            

            Page page = new Page(842, width, false);
            fastReport.setMargins(0, 0, 0, 0);
            fastReport.setPageSizeAndOrientation(page); 
            fastReport.setTitle("HDFC INWARD NEFT RETURN Report");

            new ReportBean().dynamicJasper(new JRBeanCollectionDataSource(repList), fastReport, "HDFC-NEFT-RETURN-"+sdf.format(dmy.parse(toDt)));
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return fastReport;
    }
    
}
