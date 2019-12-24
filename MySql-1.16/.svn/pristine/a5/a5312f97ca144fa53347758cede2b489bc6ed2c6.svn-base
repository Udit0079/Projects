/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.txn;

import com.cbs.dto.ChargesObject;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.other.PostalDetailFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
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
 * @author Admin
 */
public class loanInsurancePosting extends BaseBean  {
    private String message;
    private String branch;
    private List<SelectItem> branchList;
    private String acctType;
    private List<SelectItem> acctTypeList;
    private Date tillDate;
    private String debitAcc;
    private String creditAcc;
    private String totalCredit;
    private String totalDebit;
    private boolean disablePost;
    private boolean disabCalc;
    private String viewID = "/pages/master/sm/test.jsp";
    List<ChargesObject> lipInsAcnoLst;
    private String glHeadCr;
    private boolean calcFlag;
    
    private final String jndiHomeNameCommon = "CommonReportMethods";
    private CommonReportMethodsRemote commonBeanRemote = null;
    private final String jndiHomeNamePostal = "PostalDetailFacade";
    private PostalDetailFacadeRemote postalBeanRemote = null;

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

    public Date getTillDate() {
        return tillDate;
    }

    public void setTillDate(Date tillDate) {
        this.tillDate = tillDate;
    }

    public String getDebitAcc() {
        return debitAcc;
    }

    public void setDebitAcc(String debitAcc) {
        this.debitAcc = debitAcc;
    }

    public String getCreditAcc() {
        return creditAcc;
    }

    public void setCreditAcc(String creditAcc) {
        this.creditAcc = creditAcc;
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

    public boolean isDisablePost() {
        return disablePost;
    }

    public void setDisablePost(boolean disablePost) {
        this.disablePost = disablePost;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public boolean isDisabCalc() {
        return disabCalc;
    }

    public void setDisabCalc(boolean disabCalc) {
        this.disabCalc = disabCalc;
    }    

    public boolean isCalcFlag() {
        return calcFlag;
    }

    public void setCalcFlag(boolean calcFlag) {
        this.calcFlag = calcFlag;
    }
    
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    
    public loanInsurancePosting() {
        try {
            postalBeanRemote = (PostalDetailFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNamePostal);            
            commonBeanRemote = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup(jndiHomeNameCommon);            
            List brncode = new ArrayList();
            brncode = commonBeanRemote.getBranchCodeList(this.getOrgnBrCode());
            branchList = new ArrayList<SelectItem>();
            branchList.add(new SelectItem("","-Select-"));
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            
            List actCode = new ArrayList();
            actCode = commonBeanRemote.getLipAcctCodeList("LIP_PREMIUM");
            acctTypeList = new ArrayList<SelectItem>();
            acctTypeList.add(new SelectItem("","-Select-"));
            if (!actCode.isEmpty()) {
                for (int i = 0; i < actCode.size(); i++) {
                    Vector actCodeVector = (Vector) actCode.get(i);
                    acctTypeList.add(new SelectItem(actCodeVector.get(0).toString(), actCodeVector.get(2).toString()));
                    glHeadCr = actCodeVector.get(3).toString();
                }
            }
            this.setDisablePost(true);
            setTillDate(date);
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }        
    }
    
    public void calculationData(){
        try{
            calcFlag = true;
            if(this.getBranch().equalsIgnoreCase("")){
                this.setMessage("Please Select Branch");
                calcFlag = false;
                return;
            }
            
            if(this.acctType.equalsIgnoreCase("")){
                this.setMessage("Please Select Account Desc");
                calcFlag = false;
                return;
            }
            
            if (this.tillDate == null) {
                this.setMessage("Please Enter As On Date.");
                calcFlag = false;
                return;
            }

            lipInsAcnoLst = postalBeanRemote.calculateInsPremium(this.getBranch(), this.getAcctType(), ymd.format(this.tillDate));
            if (lipInsAcnoLst.size() > 0) {
                if (lipInsAcnoLst.get(0).getMsg().equalsIgnoreCase("TRUE")) {
                    double amt = 0f;
                    for (ChargesObject tmpBean : lipInsAcnoLst) {
                        amt = amt + tmpBean.getLimit();
                    }
                    this.setDebitAcc("As Per List");
                    this.setCreditAcc(this.getBranch() + glHeadCr + "01");
                    this.setTotalDebit(String.valueOf(amt));
                    this.setTotalCredit(String.valueOf(amt));
                    this.setDisabCalc(true);
                    this.setDisablePost(false);                    
                } else {
                    setMessage(lipInsAcnoLst.get(0).getMsg());
                    calcFlag = false;
                    return;
                }
            } else {
                setMessage("Data does not exist for Insurance Premium.");
                calcFlag = false;
                return;
            }
            String bankName = "", bankAddress = "";
            List dataList1 = commonBeanRemote.getBranchNameandAddress(this.branch);
            if (dataList1.size() > 0) {
                bankName = (String) dataList1.get(0);
                bankAddress = (String) dataList1.get(1);
            }
            
            String report = "Insurance Premium Report";
            Map fillParams = new HashMap();
            fillParams.put("pReportDate", sdf.format(this.tillDate));
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportName", report);
            fillParams.put("pBankName", bankName);
            fillParams.put("pBankAdd", bankAddress);
            
            List<ChargesObject> intList = (List<ChargesObject>) lipInsAcnoLst;
            new ReportBean().jasperReport("Insurance_Premium", "text/html", new JRBeanCollectionDataSource(intList), fillParams, report);
            this.setViewID("/report/ReportPagePopUp.jsp");            
        }catch (ApplicationException e) {
            setMessage(e.getMessage());
            calcFlag = false;
        } catch (Exception e) {
            setMessage(e.getMessage());
            calcFlag = false;
        }
    }   
    
    public void postAction(){
        try{
            String msg = postalBeanRemote.postPremium(lipInsAcnoLst, this.getCreditAcc(), Double.parseDouble(this.getTotalCredit()), ymd.format(this.tillDate), this.getUserName(), this.getOrgnBrCode());
            if(msg.substring(0,4).equalsIgnoreCase("True")){
                refresh();
                this.setMessage("Primum Transferred Successfully and batch No. is " + msg.substring(4));
            }else{
                this.setMessage(msg);
            }
        }catch (ApplicationException e) {
            setMessage(e.getMessage());            
        } catch (Exception e) {
            setMessage(e.getMessage());            
        }
    }
    
    public void refresh(){
        this.setBranch("");
        this.setAcctType("");
        this.setTillDate(date);
        this.setDebitAcc("");
        this.setCreditAcc("");
        this.setTotalDebit("");
        this.setTotalCredit("");
        this.setDisabCalc(false);
        this.setDisablePost(true);
        this.setMessage("");
    } 
    
    public String exitBtnAction(){
        return "case1";
    }
}
