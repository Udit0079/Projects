/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.txn;

import com.cbs.dto.LoanIntCalcList;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.intcal.LoanIntProductTestFacadeRemote;
import com.cbs.facade.other.PostalDetailFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author sipl
 */
public class FidelityAdjustment extends BaseBean {
    private String message;
    private String desgOption;
    private List<SelectItem> desgOptionList;
    private Date tillDate;
    private String debitAcc;
    private String creditAcc;
    private String totalCredit;
    private String totalDebit;
    private String panelMsg;
    private boolean reportFlag;
    private boolean disablePost;
    private boolean disDt;
    private boolean disDesg;
    private String viewID = "/pages/master/sm/test.jsp";    

    private final String jndiName = "PostalDetailFacade";
    private PostalDetailFacadeRemote remote = null;
    private final String LoanIntTestCalcJndiName = "LoanIntProductTestFacade";
    private LoanIntProductTestFacadeRemote loanIntTestCalcRemote = null;
    
    public String getCreditAcc() {
        return creditAcc;
    }

    public void setCreditAcc(String creditAcc) {
        this.creditAcc = creditAcc;
    }

    public String getDebitAcc() {
        return debitAcc;
    }

    public void setDebitAcc(String debitAcc) {
        this.debitAcc = debitAcc;
    }

    public String getDesgOption() {
        return desgOption;
    }

    public void setDesgOption(String desgOption) {
        this.desgOption = desgOption;
    }

    public List<SelectItem> getDesgOptionList() {
        return desgOptionList;
    }

    public void setDesgOptionList(List<SelectItem> desgOptionList) {
        this.desgOptionList = desgOptionList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPanelMsg() {
        return panelMsg;
    }

    public void setPanelMsg(String panelMsg) {
        this.panelMsg = panelMsg;
    }

    public boolean isReportFlag() {
        return reportFlag;
    }

    public void setReportFlag(boolean reportFlag) {
        this.reportFlag = reportFlag;
    }

    public Date getTillDate() {
        return tillDate;
    }

    public void setTillDate(Date tillDate) {
        this.tillDate = tillDate;
    }

    public String getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(String totalCredit) {
        this.totalCredit = totalCredit;
    }

    public String getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(String totalDebit) {
        this.totalDebit = totalDebit;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public boolean isDisablePost() {
        return disablePost;
    }

    public void setDisablePost(boolean disablePost) {
        this.disablePost = disablePost;
    }

    public boolean isDisDesg() {
        return disDesg;
    }

    public void setDisDesg(boolean disDesg) {
        this.disDesg = disDesg;
    }

    public boolean isDisDt() {
        return disDt;
    }

    public void setDisDt(boolean disDt) {
        this.disDt = disDt;
    }
    
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    
    public FidelityAdjustment() {
        try{
            remote = (PostalDetailFacadeRemote) ServiceLocator.getInstance().lookup(jndiName);            
            loanIntTestCalcRemote = (LoanIntProductTestFacadeRemote) ServiceLocator.getInstance().lookup(LoanIntTestCalcJndiName);
            setDisablePost(true);
            setDisDesg(true);
            setDisDt(false);
            setTillDate(sdf.parse(getTodayDate()));
        }catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }        
    }
    
    public void getValues(String dt, String brncode) {
        try {
            setReportFlag(false);
            List resultList = remote.getDesgType(dt,brncode);
            if (!resultList.isEmpty()) {
                desgOptionList = new ArrayList<SelectItem>();
                desgOptionList.add(new SelectItem("0", " "));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    desgOptionList.add(new SelectItem(ele.get(0).toString(), ele.get(0).toString()));
                }
            }else{
                this.setMessage("Data Not Exist To Adjust For Any Designation");
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }
    
    public void setDesg(){
        getValues(ymd.format(this.getTillDate()),this.getOrgnBrCode());
        setDisablePost(true);
        setDisDesg(false);
    }
    
    public void setAcAmt(){
        this.setMessage("");
        setDebitAcc("");
        setCreditAcc("");
        setTotalCredit("");
        setTotalDebit("");
        try {
            if(!this.getDesgOption().equalsIgnoreCase("0")){
                List resultList = remote.getHeadFidility(this.getDesgOption());
                if (!resultList.isEmpty()) {
                    List resultList1 = remote.getSumFidility(ymd.format(this.getTillDate()),this.getDesgOption(),this.getOrgnBrCode());
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        this.setCreditAcc(ele.get(1).toString());
                        this.setDebitAcc(ele.get(0).toString());
                    }
                    Vector ele1 = (Vector) resultList1.get(0);
                    this.setTotalDebit(ele1.get(0).toString());
                    this.setTotalCredit(ele1.get(0).toString());
                }else{
                    this.setMessage("Head Not Defined For This Designation ");
                }
            } else {
                this.setMessage("Please Select Designation ");
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }
    
    public void calculate() {
        setMessage("");
        setReportFlag(false);
        try {
            if(this.tillDate == null) {
                setMessage("Please enter the Date.");
                return;
            }
            
            if(this.desgOption.equalsIgnoreCase("0")){
                setMessage("Please Select Designation");
                return;
            }
            
            if (debitAcc == null || debitAcc.equalsIgnoreCase("")) {
                setMessage("Debit Account is not set.");
                return;
            }
            
            if (totalDebit == null || totalDebit.equalsIgnoreCase("")) {
                setMessage("Debit Amount is not set.");
                return;
            }
            
            if (creditAcc == null || creditAcc.equalsIgnoreCase("")) {
                setMessage("Credit Account is not set.");
                return;
            }
            
            if (totalCredit == null || totalCredit.equalsIgnoreCase("")) {
                setMessage("Debit Account is not set.");
                return;
            }
            
            List<LoanIntCalcList> resultList = remote.fidilityListRep(ymd.format(this.getTillDate()),this.getDesgOption(),this.getOrgnBrCode());
            
            if (resultList.isEmpty()) {
                throw new ApplicationException("Data does not exist.");
            }
            
            String toDt = sdf.format(this.getTillDate());

            String report = "Fidelity Adjustment Report";
            Vector vec = loanIntTestCalcRemote.getBranchNameandAddress(getOrgnBrCode());
            Map fillParams = new HashMap();

            fillParams.put("pReportName", report);
            fillParams.put("pPrintedBy", getUserName());

            fillParams.put("pToDt", toDt);
            fillParams.put("pBankName", vec.get(0).toString());
            fillParams.put("pBankAdd", vec.get(1).toString());
            new ReportBean().jasperReport("FidelityAdjReport", "text/html", new JRBeanCollectionDataSource(resultList), fillParams, report);
            this.setViewID("/report/ReportPagePopUp.jsp");
            setReportFlag(true);
            setDisDt(true);
            setDisDesg(true);
            setDisablePost(false);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }
    
    public void Post() {
        try {
            if(this.tillDate == null) {
                setMessage("Please enter the Date.");
                return;
            }
            
            if(this.desgOption.equalsIgnoreCase("0")){
                setMessage("Please Select Designation");
                return;
            }
            
            if (debitAcc == null || debitAcc.equalsIgnoreCase("")) {
                setMessage("Debit Account is not set.");
                return;
            }
            
            if (totalDebit == null || totalDebit.equalsIgnoreCase("")) {
                setMessage("Debit Amount is not set.");
                return;
            }
            
            if (creditAcc == null || creditAcc.equalsIgnoreCase("")) {
                setMessage("Credit Account is not set.");
                return;
            }
            
            if (totalCredit == null || totalCredit.equalsIgnoreCase("")) {
                setMessage("Debit Account is not set.");
                return;
            }
            
            String result = remote.fidilityPost(ymd.format(this.getTillDate()),this.getDesgOption(),this.getOrgnBrCode(),this.getCreditAcc(),this.getDebitAcc(),
                    Double.parseDouble(this.getTotalCredit()),getUserName());
            setReportFlag(false);
            setMessage(result);
            setTillDate(sdf.parse(getTodayDate()));
            setDesgOption("0");
            setDebitAcc("");
            setCreditAcc("");
            setTotalCredit("");
            setTotalDebit("");
            setDisablePost(true);
            setDisDt(false);
            setDisDesg(false);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }
    
    public void refresh() {
        try {
            setReportFlag(false);
            setMessage("");
            setDesgOption("0");
            setTillDate(sdf.parse(getTodayDate()));
            this.setDebitAcc("");
            this.setTotalDebit("");
            this.setCreditAcc("");
            this.setTotalCredit("");
            setDisablePost(true);
            setDisDt(false);
            setDisDesg(true);
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