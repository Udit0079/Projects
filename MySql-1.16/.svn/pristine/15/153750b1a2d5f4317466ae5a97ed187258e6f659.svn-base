/*
 * Created By: ROHIT KRISHNA GUPTA
 * Creation Date: 25 Sep 2010
 */
package com.cbs.web.controller.txn;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.txn.LoanAuthorizationManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.txn.LlockerIssuedAuthGrid;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Admin
 */
public class LockerIssueAuthorization extends BaseBean {

    private String errorMessage;
    private String message;
    int currentRow;
    LlockerIssuedAuthGrid currentItem;
    private List<LlockerIssuedAuthGrid> gridDetail;
    private final String jndiHomeName = "LoanAuthorizationManagementFacade";
    private LoanAuthorizationManagementFacadeRemote loanAuthRemote = null;
    FtsPostingMgmtFacadeRemote ftsPosting;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LlockerIssuedAuthGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(LlockerIssuedAuthGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<LlockerIssuedAuthGrid> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<LlockerIssuedAuthGrid> gridDetail) {
        this.gridDetail = gridDetail;
    }

    /** Creates a new instance of LockerIssueAuthorization */
    public LockerIssueAuthorization() {

        try {
            loanAuthRemote = (LoanAuthorizationManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            ftsPosting = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            gridLoad();
            this.setErrorMessage("");
            this.setMessage("");

        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void gridLoad() {
        setCurrentRow(0);
        gridDetail = new ArrayList<LlockerIssuedAuthGrid>();
        try {
            List resultList = new ArrayList();
            resultList = loanAuthRemote.gridLoad(this.getOrgnBrCode());
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    LlockerIssuedAuthGrid detail = new LlockerIssuedAuthGrid();
                    detail.setCabNo(ele.get(0).toString());
                    detail.setLockerTy(ele.get(1).toString());
                    detail.setLockerNo(ele.get(2).toString());
                    detail.setKeyNo(ele.get(3).toString());
                    detail.setAcNo(ele.get(4).toString());
                    detail.setCustName(ftsPosting.getCustName(ele.get(4).toString()));
                    detail.setCustCat(ele.get(5).toString());
                    detail.setSecurity(ele.get(6).toString());
                    detail.setRent(ele.get(7).toString());
                    detail.setMode(ele.get(8).toString());
                    detail.setNomination(ele.get(9).toString());
                    detail.setRemarks(ele.get(10).toString());
                    detail.setEnterBy(ele.get(11).toString());
                    detail.setAuth(ele.get(12).toString());
                    gridDetail.add(detail);
                }
            } else {
                return;
            }
        } catch (ApplicationException e) {
            this.setMessage(e.getMessage());
        } catch (Exception e) {
            this.setMessage(e.getLocalizedMessage());
        }
    }

    public void changeAuth() {
        this.setErrorMessage("");
        this.setMessage("");
        LlockerIssuedAuthGrid item = gridDetail.get(currentRow);
        System.out.println("Auth=" + item.getAuth());
        System.out.println("Curent row = " + currentRow);
        if (item.getAuth().equalsIgnoreCase("N")) {
            if (item.getEnterBy().equalsIgnoreCase(this.getUserName())) {
                this.setMessage("You cannot authorize your own entry");
                return;
            } else {
                boolean found1 = false;
                for (LlockerIssuedAuthGrid item1 : gridDetail) {
                    if (item1.getAuth().equalsIgnoreCase("Y")) {
                        found1 = true;
                        break;
                    }
                }
                if (found1) {
                    this.setMessage("Only one transction can be authorize at one time.");
                } else {
                    item.setAuth("Y");
                    gridDetail.remove(currentRow);
                    gridDetail.add(currentRow, item);
                }
            }
        } else if (item.getAuth().equalsIgnoreCase("Y")) {
            item.setAuth("N");
            gridDetail.remove(currentRow);
            gridDetail.add(currentRow, item);
        }
    }

    public void authorizeBtn() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            for (LlockerIssuedAuthGrid item : gridDetail) {
                if (item.getAuth().equals("Y")) {
                    String result = loanAuthRemote.lockerIssueAuthorization(item.getLockerTy(), item.getCabNo(), item.getLockerNo(), getUserName(), getOrgnBrCode());
                    this.setMessage(result);
                    gridLoad();
                }
            }

        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void refreshForm() {
        gridLoad();
        this.setErrorMessage("");
        this.setMessage("");
        
    }

    public String exitForm() {
        refreshForm();
        return "case1";
    }

    public void deleteForm() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            String result = loanAuthRemote.lockerIssueDeletion(currentItem.getLockerTy(), currentItem.getCabNo(), currentItem.getLockerNo(), getUserName(), getOrgnBrCode());
            this.setMessage(result);
            gridLoad();
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }
}
