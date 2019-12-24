/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.master;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.RdReschedulePojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.report.MiscReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nishant Srivastava
 */
public class RdReschedule extends BaseBean {

    private String message;
    private String accountNo, acNoLen;
    private String newAcNo;
    private String custName;
    private String roi;
    private String openingDate;
    private String installment;    
    private String period;
    private String sno;
    private String dueDt;
    private String status;
    private String paymentDt;
    private String enterBy;
    private boolean roiDisb;
    private boolean installmentDisb;
    private boolean periodDisb;
    private boolean saveDisb;
    int currentRow;
    private ArrayList<RdReschedulePojo> gridDetail;
    private RdReschedulePojo currentItem = new RdReschedulePojo();
    
    MiscReportFacadeRemote beanRemote;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    public String getDueDt() {
        return dueDt;
    }

    public RdReschedulePojo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(RdReschedulePojo currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public void setDueDt(String dueDt) {
        this.dueDt = dueDt;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }    

    public String getPaymentDt() {
        return paymentDt;
    }

    public void setPaymentDt(String paymentDt) {
        this.paymentDt = paymentDt;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getInstallment() {
        return installment;
    }

    public void setInstallment(String installment) {
        this.installment = installment;
    }

    public String getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(String openingDate) {
        this.openingDate = openingDate;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public MiscReportFacadeRemote getBeanRemote() {
        return beanRemote;
    }

    public void setBeanRemote(MiscReportFacadeRemote beanRemote) {
        this.beanRemote = beanRemote;
    }

    public SimpleDateFormat getDmyFormat() {
        return dmyFormat;
    }

    public void setDmyFormat(SimpleDateFormat dmyFormat) {
        this.dmyFormat = dmyFormat;
    }

    public FtsPostingMgmtFacadeRemote getFtsPostRemote() {
        return ftsPostRemote;
    }

    public void setFtsPostRemote(FtsPostingMgmtFacadeRemote ftsPostRemote) {
        this.ftsPostRemote = ftsPostRemote;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNewAcNo() {
        return newAcNo;
    }

    public void setNewAcNo(String newAcNo) {
        this.newAcNo = newAcNo;
    }

    public ArrayList<RdReschedulePojo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(ArrayList<RdReschedulePojo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public boolean isRoiDisb() {
        return roiDisb;
    }

    public void setRoiDisb(boolean roiDisb) {
        this.roiDisb = roiDisb;
    }

    public boolean isInstallmentDisb() {
        return installmentDisb;
    }

    public void setInstallmentDisb(boolean installmentDisb) {
        this.installmentDisb = installmentDisb;
    }

    public boolean isPeriodDisb() {
        return periodDisb;
    }

    public void setPeriodDisb(boolean periodDisb) {
        this.periodDisb = periodDisb;
    }

    public boolean isSaveDisb() {
        return saveDisb;
    }

    public void setSaveDisb(boolean saveDisb) {
        this.saveDisb = saveDisb;
    }   

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }

    
    public RdReschedule() {
        try {
            beanRemote = (MiscReportFacadeRemote) ServiceLocator.getInstance().lookup("MiscReportFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost); 
            this.setAcNoLen(getAcNoLength());
            this.setRoiDisb(true);
            this.setInstallmentDisb(true);
            this.setPeriodDisb(true);
            this.setSaveDisb(true);
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void onBlurAccountNumber() {
        try {
            setMessage("");
            setInstallment("");
            setRoi("");
            setOpeningDate("");
            setPeriod("");
            setCustName("");
            setPaymentDt(null);
            //if (accountNo.length() != 12) {
            if ((this.accountNo.length() != 12) && (this.accountNo.length() != (Integer.parseInt(this.getAcNoLen())))) {
                setMessage("Please Enter Proper Account No");
                return;
            }

            this.newAcNo = ftsPostRemote.getNewAccountNumber(accountNo);
            String acctype = ftsPostRemote.getAccountNature(newAcNo);
            if(!acctype.equalsIgnoreCase(CbsConstant.RECURRING_AC)){
                setMessage("Account No Must Be Of RD");
                return;
            }
            
            List<RdReschedulePojo> returnList = beanRemote.getAccountDetailsStatement(newAcNo);
            if (!returnList.isEmpty()) {
                custName = returnList.get(0).getCustname();
                openingDate = returnList.get(0).getOpeningDate();
                installment = returnList.get(0).getInstallment();
                roi = returnList.get(0).getRoi();
                period = returnList.get(0).getPeriod();
            }
            gridLoad();
            
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void gridLoad() {
        try {
            gridDetail = new ArrayList<RdReschedulePojo>();
            List<RdReschedulePojo> resultList = new ArrayList<RdReschedulePojo>();
            resultList = beanRemote.gridDetailAccountDetails(newAcNo);
            if (!resultList.isEmpty()) {
                gridDetail = (ArrayList<RdReschedulePojo>)resultList;
                this.setRoiDisb(true);
                this.setInstallmentDisb(true);
                this.setPeriodDisb(true);
                this.setSaveDisb(true);
            }else{
                this.setRoiDisb(false);
                this.setInstallmentDisb(false);
                this.setPeriodDisb(false);
                this.setSaveDisb(false);
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void saveMasterDetail() {
        this.setMessage("");
        try {
            if (this.accountNo == null || this.accountNo.equalsIgnoreCase("") || this.accountNo.length() == 0) {
                this.setMessage("Account No. Is Blank !!!");
                return;
            }
            //if (this.accountNo.length() != 12) {
            if (!this.accountNo.equalsIgnoreCase("") && ((this.accountNo.length() != 12) && (this.accountNo.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                this.setMessage("Please Enter Proper Account No.");
                return;
            }
            if(Integer.parseInt(this.period)<0){
                this.setMessage("Period Can't be Negative, Please Check Maturity Date");
                return;
            }
            String result = beanRemote.SaveRecordRD(newAcNo, openingDate, installment, Integer.parseInt(period), getUserName());
            if(result.equalsIgnoreCase("true")){
                this.setMessage("Data Saved Successfully !!!");                
            }else if (result == null || result.equalsIgnoreCase("")) {
                this.setMessage("System error occured !!!");
                return;
            } else{
                this.setMessage("System error occured !!!");
                return;
            }
            gridLoad();
        } catch (Exception e) {
            e.printStackTrace();
            setMessage(e.getLocalizedMessage());
        }
    }

    public void refreshForm() {
        try {
            this.setMessage("");
            this.setAccountNo("");
            this.setCustName("");
            this.setRoi("");
            this.setOpeningDate("");
            this.setInstallment("");
            this.setPeriod("");
            this.setNewAcNo("");
            gridDetail = new ArrayList<RdReschedulePojo>();
            this.setRoiDisb(true);
            this.setInstallmentDisb(true);
            this.setPeriodDisb(true);
            this.setSaveDisb(true);
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        try {
            refreshForm();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
