/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.txn;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.txn.AccountAuthorizationManagementFacadeRemote;
import com.cbs.web.pojo.txn.AccountLoanLimitAuthorizationGrid;
import com.cbs.web.pojo.txn.AccountLoanLimitAuthorizationGridDetails;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author root
 */
public class AccountLoanLimitAuthorization {

    private String orgBrnCode;
    HttpServletRequest request;
    private String todayDate;
    private String userName;
    List<SelectItem> acList;
    String list;
    private int currentRow;
    ArrayList<AccountLoanLimitAuthorizationGrid> gridData;
    AccountLoanLimitAuthorizationGrid rd;
    AccountLoanLimitAuthorizationGrid authorized;
    String message;
    String accountName;
    String accountNumber;
    ArrayList<AccountLoanLimitAuthorizationGridDetails> gridData2;
    AccountLoanLimitAuthorizationGridDetails rd2;
    boolean authorize;
    int k = 0;
    private final String jndiHomeName = "AccountAuthorizationManagementFacade";
    private AccountAuthorizationManagementFacadeRemote accountAuthRemote = null;

    public boolean isAuthorize() {
        return authorize;
    }

    public void setAuthorize(boolean authorize) {
        this.authorize = authorize;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountLoanLimitAuthorizationGridDetails getRd2() {
        return rd2;
    }

    public void setRd2(AccountLoanLimitAuthorizationGridDetails rd2) {
        this.rd2 = rd2;
    }

    public ArrayList<AccountLoanLimitAuthorizationGridDetails> getGridData2() {
        return gridData2;
    }

    public void setGridData2(ArrayList<AccountLoanLimitAuthorizationGridDetails> gridData2) {
        this.gridData2 = gridData2;
    }

    public AccountLoanLimitAuthorizationGrid getAuthorized() {
        return authorized;
    }

    public void setAuthorized(AccountLoanLimitAuthorizationGrid authorized) {
        this.authorized = authorized;
    }

    public AccountLoanLimitAuthorizationGrid getRd() {
        return rd;
    }

    public void setRd(AccountLoanLimitAuthorizationGrid rd) {
        this.rd = rd;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public ArrayList<AccountLoanLimitAuthorizationGrid> getGridData() {
        return gridData;
    }

    public void setGridData(ArrayList<AccountLoanLimitAuthorizationGrid> gridData) {
        this.gridData = gridData;
    }

    public List<SelectItem> getAcList() {
        return acList;
    }

    public void setAcList(List<SelectItem> acList) {
        this.acList = acList;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public String getOrgBrnCode() {
        return orgBrnCode;
    }

    public void setOrgBrnCode(String orgBrnCode) {
        this.orgBrnCode = orgBrnCode;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** Creates a new instance of AccountLoanLimitAuth */
    public AccountLoanLimitAuthorization() {

        try {
            request = getRquest();
            String orgnBrIp = WebUtil.getClientIP(request);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgBrnCode = Init.IP2BR.getBranch(localhost);
            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(dt));
            setUserName(request.getRemoteUser());
            gridData = new ArrayList<AccountLoanLimitAuthorizationGrid>();
            accountAuthRemote = (AccountAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            this.setMessage("");
            authorize = true;
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HttpServletRequest getRquest() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return req;
    }

    public void getListData() {
        try {
            this.setMessage("");
            if (!gridData.isEmpty()) {
                this.gridData.clear();
            }
            if (!gridData.isEmpty()) {
                this.gridData2.clear();
            }

            List getAcNo = new ArrayList();
            getAcNo = accountAuthRemote.getListDetails(orgBrnCode);
            if (getAcNo.isEmpty()) {
                this.setMessage("No Data Find..");
                return;
            }
            for (int i = 0; i < getAcNo.size(); i++) {
                Vector ele = (Vector) getAcNo.get(i);
                rd = new AccountLoanLimitAuthorizationGrid();
                rd.setAccountNo(ele.get(0).toString());
                rd.setTxnId(ele.get(1).toString());
                gridData.add(rd);
            }
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void fetchCurrentRow(ActionEvent event) {

        String ctrlNumber = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ctrlNo"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (AccountLoanLimitAuthorizationGrid item4 : this.gridData) {
            if (item4.getTxnId().equals(ctrlNumber)) {
                authorized = item4;
            }
        }
    }

    public void setRowValues() {
        this.setMessage("");
        String acNo = authorized.getAccountNo();
        String txnId = authorized.getTxnId();
        List list2 = accountAuthRemote.getTableData(acNo, txnId);
        if (list2.isEmpty()) {
            this.setMessage("Please Check The Entries May Be invalid..");
            return;
        }

        Vector v1 = (Vector) list2.get(0);
        this.setAccountName(v1.get(0).toString());
        this.setAccountNumber(v1.get(1).toString());

        for (int i = 0; i < list2.size(); i++) {

            gridData2 = new ArrayList<AccountLoanLimitAuthorizationGridDetails>();
            k = 1;
            rd2 = new AccountLoanLimitAuthorizationGridDetails();
            rd2.setInterestDepositSet(v1.get(2).toString());

            rd2.setPenaltyRoiSet(v1.get(4).toString());

            rd2.setLimitSet(v1.get(6).toString());

            rd2.setAdhocIntSet(v1.get(8).toString());

            rd2.setAdhocLimitoldSet(v1.get(10).toString());

            rd2.setAdhocApplicableDateSet(v1.get(12).toString());


            rd2.setAdhocTillDtOldSet(v1.get(14).toString());

            rd2.setSubsidyAmtOldSet(v1.get(16).toString());

            rd2.setSubsidyExpDtOldSet(v1.get(18).toString());



            // rd2.setMaxLimitOldSet(v1.get(20).toString());

            Double d2 = Double.parseDouble(v1.get(20).toString());

            rd2.setMaxLimitOldSet(new java.text.DecimalFormat("#,############0.00").format(d2));

            rd2.setEnterByEditedBySet(v1.get(22).toString());



            rd2.setIntDepositSet(v1.get(3).toString());

            rd2.setPenaltySet(v1.get(5).toString());

            rd2.setOdLimitSet(v1.get(7).toString());

            rd2.setAdhocInterestSet(v1.get(9).toString());

            rd2.setAdhocLimitSet(v1.get(11).toString());

            rd2.setAdhocApplicableDtSet(v1.get(13).toString());

            rd2.setAdhocTillDtSet(v1.get(15).toString());

            rd2.setSubsidyAmtSet(v1.get(17).toString());

            rd2.setSubsidyExpSet(v1.get(19).toString());

            Double d1 = Double.parseDouble(v1.get(21).toString());

            rd2.setMaxLimitSet(new java.text.DecimalFormat("#,############0.00").format(d1));


            rd2.setEnterBySet(v1.get(23).toString());

            gridData2.add(rd2);

            authorize = false;

            /* /*
            deciFormat.format(dTotal) and got the String value of dTotal=50377172.35*/
            //  new java.text.DecimalFormat("#,##0.00").format(1222556456.000);*/
        }
    }

    public void btnAuthorize() {
        try {
            if (this.getUserName().toUpperCase().equalsIgnoreCase(rd2.getEnterBySet().toUpperCase())) {
                this.setMessage("Sorry You can not Authorize Your Own Entry!!!");
                return;
            }
            if (Double.parseDouble(rd2.getOdLimitSet()) > 0 ) {
                String chk =  accountAuthRemote.borrowerChecking(authorized.getAccountNo());
                if (!chk.equalsIgnoreCase("")) {
                    this.setMessage(chk);
                    return;
                }
            }
            String st1 = accountAuthRemote.btnAuthorize(authorized.getAccountNo(), authorized.getTxnId(), this.getUserName());
            if (st1.equals("transaction successful")) {
                setAccountName("");
                setAccountNumber("");
                gridData2.clear();
                gridData.remove(authorized);
                this.setMessage("Entries Authorized Successfully");
                authorize = true;

            } else {
                this.setMessage("Entries Could Not Authorized..");

            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }


    }

    public String exitBtnAction() {

        if (!gridData.isEmpty()) {
            gridData.clear();
        }


        if (k == 1) {
            if (!gridData2.isEmpty()) {
                gridData2.clear();
            }

        }

        this.setAccountName("");
        setAccountNumber("");
        authorize = true;

        this.setMessage("");
        return "case1";
    }
}
