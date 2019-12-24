/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.txn;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.txn.LoanAuthorizationManagementFacadeRemote;
import com.cbs.web.pojo.txn.LoanSecurityAuthTable;
import com.cbs.web.pojo.txn.LoanSecurityTableList;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.event.ActionEvent;

/**
 *
 * @author Administrator
 */
public class LoanSecurityAuth extends BaseBean{

    private String message;
    private List<LoanSecurityAuthTable> loanSeq;
    int currentRow;
    LoanSecurityAuthTable currentItem;
    private List<LoanSecurityTableList> loanSeqAuth;
    private String customerName;
    private String acNumbers;
    int currentRow1;
    private String flag;
    private String enterBy;
    private String seqNumb;
    private final String jndiHomeName = "LoanAuthorizationManagementFacade";
    private LoanAuthorizationManagementFacadeRemote loanAuthRemote = null;
    private String totLien;

    public String getSeqNumb() {
        return seqNumb;
    }

    public void setSeqNumb(String seqNumb) {
        this.seqNumb = seqNumb;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getCurrentRow1() {
        return currentRow1;
    }

    public void setCurrentRow1(int currentRow1) {
        this.currentRow1 = currentRow1;
    }

    public String getAcNumbers() {
        return acNumbers;
    }

    public void setAcNumbers(String acNumbers) {
        this.acNumbers = acNumbers;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<LoanSecurityTableList> getLoanSeqAuth() {
        return loanSeqAuth;
    }

    public void setLoanSeqAuth(List<LoanSecurityTableList> loanSeqAuth) {
        this.loanSeqAuth = loanSeqAuth;
    }

    public LoanSecurityAuthTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(LoanSecurityAuthTable currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<LoanSecurityAuthTable> getLoanSeq() {
        return loanSeq;
    }

    public void setLoanSeq(List<LoanSecurityAuthTable> loanSeq) {
        this.loanSeq = loanSeq;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTotLien() {
        return totLien;
    }

    public void setTotLien(String totLien) {
        this.totLien = totLien;
    }

    /** Creates a new instance of LoanSecurityAuth */
    public LoanSecurityAuth() {
        try {
            this.setMessage("");
            flag = "true";
            loanAuthRemote = (LoanAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void getList() {
        this.setMessage("");
        loanSeq = new ArrayList<LoanSecurityAuthTable>();
        try {
            List tableList = new ArrayList();
            tableList = loanAuthRemote.getListAccountsStock(getOrgnBrCode());
            if (!tableList.isEmpty()) {
                for (int i = 0; i < tableList.size(); i++) {
                    Vector ele = (Vector) tableList.get(i);
                    LoanSecurityAuthTable securityList = new LoanSecurityAuthTable();

                    securityList.setAcno(ele.get(0).toString());
                    securityList.setSeqNumber(ele.get(1).toString());
                    loanSeq.add(securityList);
                }
            } else {
                this.setMessage("No Data Exists");
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

//    public void fetchCurrentRow(ActionEvent event) {
//        String acNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("acNo"));
//        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
//        for (LoanSecurityAuthTable item : loanSeq) {
//            if (item.getAcno().equalsIgnoreCase(acNo)) {
//                currentItem = item;
//            }
//        }
//    }

    public void getAuthorizeValue() {
        this.setMessage("");
        try {
            String acnos = (currentItem.getAcno());
            int seqNumber = Integer.parseInt(currentItem.getSeqNumber());
            loanSeqAuth = new ArrayList<LoanSecurityTableList>();

            List authList = new ArrayList();
            authList = loanAuthRemote.getListAccountAuth(acnos, seqNumber);
            if (!authList.isEmpty()) {
                for (int i = 0; i < authList.size(); i++) {
                    Vector ele = (Vector) authList.get(i);
                    LoanSecurityTableList securityAuthList = new LoanSecurityTableList();
                    securityAuthList.setName(ele.get(0).toString());
                    setCustomerName(ele.get(0).toString());
                    setAcNumbers(ele.get(1).toString());
                    securityAuthList.setSeqNumber(ele.get(2).toString());
                    setSeqNumb(ele.get(2).toString());
                    if(ele.get(17).toString().equals("")){
                        securityAuthList.setDetails(ele.get(3).toString());
                    }else{
                        securityAuthList.setDetails(ele.get(17).toString());
                    }
                    securityAuthList.setSecurityType(ele.get(4).toString());
                    securityAuthList.setSecurityOption(ele.get(5).toString());
                    securityAuthList.setSecurityCharge(ele.get(6).toString());
                    securityAuthList.setMaturityValue(new BigDecimal(Float.parseFloat(ele.get(7).toString())));
                    securityAuthList.setLienValue(new BigDecimal(Float.parseFloat(ele.get(8).toString())));
                    securityAuthList.setPrimaryColl(ele.get(9).toString());
                    securityAuthList.setMargin(new BigDecimal(Float.parseFloat(ele.get(10).toString())));
                    securityAuthList.setIssueDate(ele.get(13).toString().equalsIgnoreCase("")?ele.get(11).toString():ele.get(13).toString());
                    securityAuthList.setMaturityDate(ele.get(14).toString().equalsIgnoreCase("")?ele.get(12).toString():ele.get(14).toString());
                    securityAuthList.setEnterBy(ele.get(15).toString());
                    setEnterBy(ele.get(15).toString());
                    securityAuthList.setAuth(ele.get(16).toString());

                    loanSeqAuth.add(securityAuthList);
                }
                
                List lienLst = loanAuthRemote.getTotLienOfAccount(acnos);
                if(!lienLst.isEmpty()) {
                    Vector eleLien = (Vector) lienLst.get(0);
                    totLien = eleLien.get(0).toString();
                }else{
                    totLien = "0";
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void changeValue() {
        flag = "false";
        LoanSecurityTableList item = loanSeqAuth.get(currentRow1);
        if (item.getAuth().equalsIgnoreCase("N")) {
            item.setAuth("Y");
            loanSeqAuth.remove(currentRow1);
            loanSeqAuth.add(currentRow1, item);
            flag = "false";
        } else if (item.getAuth().equalsIgnoreCase("Y")) {
            item.setAuth("N");
            loanSeqAuth.remove(currentRow1);
            loanSeqAuth.add(currentRow1, item);
            flag = "true";
        }
    }

    public void accountAuthorization() {
        this.setMessage("");
        if (this.enterBy.equalsIgnoreCase("") || this.enterBy.length() == 0) {
            this.setMessage("Enter By Name Is Not Present So This A/C. Cannot Be Authorized.");
            return;
        }
        if (enterBy.equalsIgnoreCase(getUserName())) {
            this.setMessage("SORRY,YOU CAN NOT PASS YOUR OWN ENTRY !!!");
            return;
        }
        try {
            String result = loanAuthRemote.authorizeAction(acNumbers, seqNumb, enterBy, getUserName(), getOrgnBrCode());
            if (result.equalsIgnoreCase("AUTHORIZATION SUCCESSFUL")) {
                this.setMessage(result);
                loanSeqAuth = new ArrayList<LoanSecurityTableList>();
                loanSeq = new ArrayList<LoanSecurityAuthTable>();
                List tableList = loanAuthRemote.getListAccountsStock(getOrgnBrCode());
                if (!tableList.isEmpty()) {
                    for (int i = 0; i < tableList.size(); i++) {
                        Vector ele = (Vector) tableList.get(i);
                        LoanSecurityAuthTable securityList = new LoanSecurityAuthTable();

                        securityList.setAcno(ele.get(0).toString());
                        securityList.setSeqNumber(ele.get(1).toString());
                        loanSeq.add(securityList);
                    }
                }
                this.setAcNumbers("");
                this.setCustomerName("");
                this.setTotLien("0");
            } else {
                this.setMessage(result);
            }
            this.setFlag("true");

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public String exitFrm() {
        this.setMessage("");
        this.setAcNumbers("");
        this.setCustomerName("");
        this.setTotLien("0");
        loanSeqAuth = new ArrayList<LoanSecurityTableList>();
        loanSeq = new ArrayList<LoanSecurityAuthTable>();
        return "case1";
    }
}
