/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.intcal;

import com.cbs.dto.RdInterestDTO;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.intcal.RDIntCalFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import com.cbs.web.pojo.intcal.RdProvisionInterestTable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class RdIntProvision extends BaseBean{
    private List<SelectItem> acctTypeOption;
    
    private Date toDate;    
    private String acctCode;

    private String calFlag;
    
    private String message;
    private boolean reportFlag;
    private String viewID="/pages/master/sm/test.jsp";
    NumberFormat formatter = new DecimalFormat("#0.00");
    private List<RdProvisionInterestTable> rdProvision;
    
    private final String provisionInterestCalRdJndiName = "RDIntCalFacade";
    private RDIntCalFacadeRemote provisionInterestCalRdRemote = null;

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    
    public boolean isReportFlag() {
        return reportFlag;
    }

    public void setReportFlag(boolean reportFlag) {
        this.reportFlag = reportFlag;
    }

    
    public String getAcctCode() {
        return acctCode;
    }

    public void setAcctCode(String acctCode) {
        this.acctCode = acctCode;
    }

    public List<RdProvisionInterestTable> getRdProvision() {
        return rdProvision;
    }

    public void setRdProvision(List<RdProvisionInterestTable> rdProvision) {
        this.rdProvision = rdProvision;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCalFlag() {
        return calFlag;
    }

    public void setCalFlag(String calFlag) {
        this.calFlag = calFlag;
    }


    public List<SelectItem> getAcctTypeOption() {
        return acctTypeOption;
    }

    public void setAcctTypeOption(List<SelectItem> acctTypeOption) {
        this.acctTypeOption = acctTypeOption;
    }


    public RdIntProvision() {
        try {
            provisionInterestCalRdRemote = (RDIntCalFacadeRemote) ServiceLocator.getInstance().lookup(provisionInterestCalRdJndiName);
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            getAccountTypes();
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void getAccountTypes() {
        acctTypeOption = new ArrayList<SelectItem>();
        try {
            List accountTypeList = new ArrayList();
            accountTypeList = provisionInterestCalRdRemote.getAccountTypes();
            for (int i = 0; i < accountTypeList.size(); i++) {
                Vector ele = (Vector) accountTypeList.get(i);
                acctTypeOption.add(new SelectItem(ele.get(0).toString()));
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }


    public void calculate() {
        setMessage("");
        SimpleDateFormat ymd=new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
        rdProvision = new ArrayList<RdProvisionInterestTable>();

        try {
            if (toDate == null) {
                setMessage("Please select to date.");
                return;
            } else if (toDate.after(sdf.parse(getTodayDate()))) {
                setMessage(" To date can not be greater than current Date");
                this.setToDate(sdf.parse(getTodayDate()));
                return;
            }
            List<RdInterestDTO> rdInterestDTOs = provisionInterestCalRdRemote.rdIntProvision(acctCode, ymd.format(toDate), getOrgnBrCode());
            RdProvisionInterestTable rdProvisionInterestTable;
            int seqNo = 1;
            double totalInt = 0d;
            for (RdInterestDTO rdInterestDTO : rdInterestDTOs) {
                rdProvisionInterestTable = new RdProvisionInterestTable();
                rdProvisionInterestTable.setSeqNumber(seqNo);
                rdProvisionInterestTable.setAccountNumber(rdInterestDTO.getAcNo());
                rdProvisionInterestTable.setCustomerName(rdInterestDTO.getCustName());

                rdProvisionInterestTable.setStartingDate(sdf.format(ymd.parse(rdInterestDTO.getOpeningDt())));
                rdProvisionInterestTable.setInstallment(String.valueOf(rdInterestDTO.getInstallment()));
                rdProvisionInterestTable.setRoi(String.valueOf(rdInterestDTO.getRoi()));

                rdProvisionInterestTable.setProduct(rdInterestDTO.getBalance());
                rdProvisionInterestTable.setInterest(rdInterestDTO.getInterest());
                System.out.println("Account No = " + rdInterestDTO.getAcNo() + "   Interest = " + rdInterestDTO.getInterest());
                totalInt = totalInt + rdInterestDTO.getInterest();
                rdProvision.add(rdProvisionInterestTable);
                seqNo++;
            }
           // this.setCrAmount(formatter.format(totalInt));
            this.setMessage("Interest successfully calculated.");
            
            String repName = "RD Interest Provision";
            CommonReportMethodsRemote reportFacade = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            List branchList = reportFacade.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();
            fillParams.put("RpName", repName);
            fillParams.put("PntBy", getUserName());
            fillParams.put("RpDate",  sdf.format(toDate));
            fillParams.put("pBankName", branchList.get(0));
            fillParams.put("pBankAdd", branchList.get(1));
            new ReportBean().jasperReport("RdIntCal", "text/html", new JRBeanCollectionDataSource(rdProvision), fillParams, repName);
            reportFlag=true;
            this.setViewID("/report/ReportPagePopUp.jsp"); 
            
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

   

    public void clearText() {
        try {
            setToDate(null);
            setMessage("");
            rdProvision = new ArrayList<RdProvisionInterestTable>();
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String exitFrm() {
        try {
            clearText();
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }

}
