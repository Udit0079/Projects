/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.txn;

import com.cbs.facade.txn.TransactionManagementFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.pojo.SuspiciousVerifyPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
public class SuspiciosTranVerify extends BaseBean {

    private String message;
    private List<SuspiciousVerifyPojo> tableList;
    private boolean checkBoxAll;
    private boolean checkBoxAllDisable;
    private FtsPostingMgmtFacadeRemote fts;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SuspiciousVerifyPojo> getTableList() {
        return tableList;
    }

    public void setTableList(List<SuspiciousVerifyPojo> tableList) {
        this.tableList = tableList;
    }

    public boolean isCheckBoxAll() {
        return checkBoxAll;
    }

    public void setCheckBoxAll(boolean checkBoxAll) {
        this.checkBoxAll = checkBoxAll;
    }

    public boolean isCheckBoxAllDisable() {
        return checkBoxAllDisable;
    }

    public void setCheckBoxAllDisable(boolean checkBoxAllDisable) {
        this.checkBoxAllDisable = checkBoxAllDisable;
    }
    private final String jndiHomeName = "TransactionManagementFacade";
    private TransactionManagementFacadeRemote txnRemote = null;
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public SuspiciosTranVerify() {
        try {
            txnRemote = (TransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            onLoadGrid();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    private void onLoadGrid() {
        tableList = new ArrayList<SuspiciousVerifyPojo>();
        try {
            int noOfDays = fts.getCodeForReportName("STR-VERIFY-DT");
            if(noOfDays!=0){
                noOfDays = -noOfDays;
            }
            String Dt = "";
            Dt = CbsUtil.dateAdd(ymd.format(new Date()), noOfDays);
            List<SuspiciousVerifyPojo> retLst = txnRemote.getStrVerifyDetails(Dt, this.getOrgnBrCode());
            if (retLst.isEmpty()) {
                this.setMessage("Data does not exist");
            } else {
                for (int i = 0; i < retLst.size(); i++) {
                    SuspiciousVerifyPojo verifyTable = new SuspiciousVerifyPojo();

                    verifyTable.setAcNo(retLst.get(i).getAcNo().toString());
                    verifyTable.setCustName(retLst.get(i).getCustName().toString());
                    verifyTable.setTrsNo(retLst.get(i).getTrsNo());
                    verifyTable.setRecNo(retLst.get(i).getRecNo());
                    verifyTable.setDt(retLst.get(i).getDt());
                    verifyTable.setAlertCode(retLst.get(i).getAlertCode());
                    verifyTable.setEnterBy(retLst.get(i).getEnterBy());
                    verifyTable.setsNo(retLst.get(i).getsNo());
                    verifyTable.setCheckBox(true);
                    tableList.add(verifyTable);
                }
                this.setCheckBoxAll(true);
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void Post() {
        try {
            String msg = txnRemote.markSuspiciousTran(tableList, this.getUserName());
            this.setMessage(msg);
            tableList = new ArrayList<>();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void checkAll() {
        if (this.checkBoxAll == true) {
            for (int i = 0; i < tableList.size(); i++) {
                tableList.get(i).setCheckBox(true);
            }
        } else {
            for (int i = 0; i < tableList.size(); i++) {
                tableList.get(i).setCheckBox(false);
            }
        }
    }

    public void refresh() {
        onLoadGrid();
        this.setMessage("");
    }

    public String exitForm() {
        try {
            refresh();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return "case1";
    }
}
