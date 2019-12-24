/*
 Document   : AccountClosingAuthorization
 Created on : 30 Oct, 2010, 3:25:00 PM
 Author     : Zeeshan Waris[zeeshan.glorious@gmail.com]
 */
package com.cbs.web.controller.txn;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.txn.AccountAuthorizationManagementFacadeRemote;
import com.cbs.web.pojo.txn.AccountStatus;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author root
 */
public class AccountStatusAuthorization extends BaseBean {

    private String message;
    private List<AccountStatus> statusTable;
    private int currentRow;
    boolean btndisable;
    private AccountStatus currentItem = new AccountStatus();
    private final String jndiHomeName = "AccountAuthorizationManagementFacade";
    private AccountAuthorizationManagementFacadeRemote accountAuthRemote = null;

    public boolean isBtndisable() {
        return btndisable;
    }

    public void setBtndisable(boolean btndisable) {
        this.btndisable = btndisable;
    }

    public AccountStatus getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(AccountStatus currentItem) {
        this.currentItem = currentItem;
    }

    public List<AccountStatus> getStatusTable() {
        return statusTable;
    }

    public void setStatusTable(List<AccountStatus> statusTable) {
        this.statusTable = statusTable;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AccountStatusAuthorization() {
        try {
            accountAuthRemote = (AccountAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            getTableStock();
            this.setMessage("");
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void getTableStock() {
        statusTable = new ArrayList<AccountStatus>();
        try {
            List resultLt = new ArrayList();
            resultLt = accountAuthRemote.tableAuthorize(getOrgnBrCode());
            int j = 1;
            if (!resultLt.isEmpty()) {
                for (int i = 0; i < resultLt.size(); i++) {
                    Vector ele = (Vector) resultLt.get(i);
                    AccountStatus rd = new AccountStatus();
                    rd.setAcno(ele.get(0).toString());
                    rd.setRemark(ele.get(1).toString());
                    rd.setDate(ele.get(2).toString());
                    rd.setSpflag(ele.get(3).toString());
                    rd.setAuth(ele.get(4).toString());
                    if (ele.get(5) == null) {
                        rd.setAuthBy("");
                    } else {
                        rd.setAuthBy(ele.get(5).toString());
                    }
                    rd.setEnterBy(ele.get(6).toString());
                    rd.setBaseAcno(ele.get(7).toString());
//                    String g = ele.get(8).toString().substring(8, 10) + "/" + ele.get(8).toString().substring(5, 7) + "/" + ele.get(8).toString().substring(0, 4);
                    rd.setEffDate(ele.get(8).toString());
                    rd.setSpFlagCode(ele.get(9).toString());
                    rd.setSpNo(Long.parseLong(ele.get(10).toString()));
                    rd.setOldStatus(ele.get(11).toString());
                    rd.setCustName(ele.get(12).toString());
                    rd.setOldStatusCode(ele.get(13).toString());
                    rd.setSno(j++);
                    statusTable.add(rd);
                }

            } else {
                if (message != null) {
                    if (message.equalsIgnoreCase("Authorization Completed Successfully")) {
                        this.setMessage("Authorization Completed Successfully");
                    }
                } else {
                    this.setMessage("Records does not Exist. ");
                }
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void changeAuth() {
        this.setMessage("");
        AccountStatus item = statusTable.get(currentRow);
        if (item.getAuth().equalsIgnoreCase("N")) {
            if (item.getEnterBy().equalsIgnoreCase(getUserName())) {
                this.setMessage("You Cant Authorize YourSelf");
                btndisable = true;
                return;
            } else {
                boolean found = false;
                for (AccountStatus item1 : statusTable) {
                    if (item1.getAuth().equalsIgnoreCase("Y")) {
                        found = true;
                    }
                }
                if (found) {
                    this.setMessage("Only one transction can be authorize at one time.");
                } else {
                    item.setAuth("Y");
                    btndisable = false;
                    statusTable.remove(currentRow);
                    statusTable.add(currentRow, item);
                }
            }
        } else if (item.getAuth().equalsIgnoreCase("Y")) {
            item.setAuth("N");
            btndisable = true;
            statusTable.remove(currentRow);
            statusTable.add(currentRow, item);
            this.setMessage("");
        }
    }

    public void authorizeBtnAction() {
        try {
            if (statusTable.size() <= 0) {
                this.setMessage("There is no record in the table to authorize.");
                return;
            }
            for (AccountStatus ele : statusTable) {
                if (ele.getAuth().equalsIgnoreCase("Y")) {
                    String result = accountAuthRemote.authorizeActionAccountStatus(ele.getSpNo(), ele.getAcno(), 
                            ele.getEffDate(), ele.getSpFlagCode(), getUserName(), getOrgnBrCode(), ele.getDate(), 
                            ele.getOldStatusCode());
                    if (result.equalsIgnoreCase("True")) {
                        this.setMessage("Authorization Completed Successfully");
                    } else {
                        this.setMessage("System error occured during Authorization");
                        return;
                    }
                }
            }
            getTableStock();
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void deleteDetails() {
        try {
            AccountStatus item = statusTable.get(currentRow);
            String result = accountAuthRemote.acctStatusDeletion(item.getSpNo(), item.getAcno());
            if (result.equalsIgnoreCase("True")) {
                this.setMessage("Entry successfully deleted");
            } else {
                this.setMessage("There is an issue in entry deletion");
            }
            getTableStock();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }

    }

    public String btnExit() {
        return "case1";
    }

    public void btnRefresh() {
        this.setMessage("");
        getTableStock();
    }
}
