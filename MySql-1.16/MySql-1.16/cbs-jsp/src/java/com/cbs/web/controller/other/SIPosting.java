/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.other;

import com.cbs.facade.other.StandingInstructionManagementRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.other.SIPostingGeneralGrid;
import com.cbs.web.pojo.other.SIPostingTxnGrid;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SIPosting extends BaseBean {

    private String message;
    private String instructionType;
    private String errorMsg;
    private List<SIPostingTxnGrid> siTransTables;
    private List<SIPostingGeneralGrid> siTransGeneTbs;
    private int currentRow;
    private SIPostingGeneralGrid currentItem;
    private List<SIPostingTxnGrid> reportTables;
    private final String jndiHomeName = "StandingInstructionManagementFacade";
    private StandingInstructionManagementRemote beanRemote = null;

    public SIPosting() {
        try {
            beanRemote = (StandingInstructionManagementRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            reportTables = new ArrayList<SIPostingTxnGrid>();
            this.setMessage("");
            this.setErrorMsg("");
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void getPostGridDetails() {
        this.setErrorMsg("");
        if (this.instructionType.equalsIgnoreCase("--SELECT--")) {
            this.setErrorMsg("Please select instruction type.");
            return;
        }
        siTransTables = new ArrayList<SIPostingTxnGrid>();
        siTransGeneTbs = new ArrayList<SIPostingGeneralGrid>();
        try {
            List tableList = new ArrayList();
            tableList = beanRemote.tableData(instructionType, getOrgnBrCode());
            if (instructionType.equals("TRANSACTION")) {
                if (!tableList.isEmpty()) {
                    for (int i = 0; i < tableList.size(); i++) {
                        Vector ele = (Vector) tableList.get(i);
                        SIPostingTxnGrid transaction = new SIPostingTxnGrid();

                        transaction.setFromAcno(ele.get(0).toString());
                        transaction.setFromCustName(beanRemote.getCustomerName(ele.get(0).toString()));
                        transaction.setToAcno(ele.get(1).toString());
                        transaction.setToCustName(beanRemote.getCustomerName(ele.get(1).toString()));
                        transaction.setsNo(Integer.parseInt(ele.get(2).toString()));
                        transaction.setInstrNo(Float.parseFloat(ele.get(3).toString()));
                        transaction.setAmount(Float.parseFloat(ele.get(4).toString()));
                        transaction.setEffDate(ele.get(5).toString());
                        transaction.setStatus(ele.get(6).toString());
                        transaction.setRemarks(ele.get(7).toString());
                        transaction.setEnterBy(ele.get(8).toString());
                        transaction.setEntryDate(ele.get(9).toString());
                        transaction.setChargeFlag(ele.get(10).toString());
                        transaction.setExpiryDt(ele.get(11).toString());

                        siTransTables.add(transaction);
                    }
                } else {
                    this.setErrorMsg("Records does not exist.");
                }
            }
            if (instructionType.equals("GENERAL")) {
                if (!tableList.isEmpty()) {
                    for (int i = 0; i < tableList.size(); i++) {
                        Vector ele = (Vector) tableList.get(i);
                        SIPostingGeneralGrid general = new SIPostingGeneralGrid();

                        general.setAcno(ele.get(0).toString());
                        general.setsNo(Integer.parseInt(ele.get(1).toString()));
                        general.setInstrNo(Float.parseFloat(ele.get(2).toString()));
                        general.setEffDate(ele.get(3).toString());
                        general.setStatus(ele.get(4).toString());
                        general.setRemarks(ele.get(5).toString());
                        general.setEnterBy(ele.get(6).toString());
                        general.setEntryDate(ele.get(7).toString());
                        siTransGeneTbs.add(general);
                    }
                } else {
                    this.setErrorMsg("Records does not exist.");
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void getPostGridDetails1() {
        if (this.instructionType.equalsIgnoreCase("--SELECT--")) {
            this.setErrorMsg("Please select instruction type.");
            return;
        }
        siTransTables = new ArrayList<SIPostingTxnGrid>();
        siTransGeneTbs = new ArrayList<SIPostingGeneralGrid>();
        try {
            List tableList = new ArrayList();
            tableList = beanRemote.tableData(instructionType, getOrgnBrCode());
            if (instructionType.equals("TRANSACTION")) {
                if (!tableList.isEmpty()) {
                    for (int i = 0; i < tableList.size(); i++) {
                        Vector ele = (Vector) tableList.get(i);
                        SIPostingTxnGrid transaction = new SIPostingTxnGrid();

                        transaction.setFromAcno(ele.get(0).toString());
                        transaction.setFromCustName(beanRemote.getCustomerName(ele.get(0).toString()));
                        transaction.setToAcno(ele.get(1).toString());
                        transaction.setToCustName(beanRemote.getCustomerName(ele.get(1).toString()));
                        transaction.setsNo(Integer.parseInt(ele.get(2).toString()));
                        transaction.setInstrNo(Float.parseFloat(ele.get(3).toString()));
                        transaction.setAmount(Float.parseFloat(ele.get(4).toString()));
                        transaction.setEffDate(ele.get(5).toString());
                        transaction.setStatus(ele.get(6).toString());
                        transaction.setRemarks(ele.get(7).toString());
                        transaction.setEnterBy(ele.get(8).toString());
                        transaction.setEntryDate(ele.get(9).toString());
                        transaction.setChargeFlag(ele.get(10).toString());
                        transaction.setExpiryDt(ele.get(11).toString());

                        siTransTables.add(transaction);
                    }
                } else {
                    this.setErrorMsg("Records does not exist.");
                }
            }
            if (instructionType.equals("GENERAL")) {
                if (!tableList.isEmpty()) {
                    for (int i = 0; i < tableList.size(); i++) {
                        Vector ele = (Vector) tableList.get(i);
                        SIPostingGeneralGrid general = new SIPostingGeneralGrid();

                        general.setAcno(ele.get(0).toString());
                        general.setsNo(Integer.parseInt(ele.get(1).toString()));
                        general.setInstrNo(Float.parseFloat(ele.get(2).toString()));
                        general.setEffDate(ele.get(3).toString());
                        general.setStatus(ele.get(4).toString());
                        general.setRemarks(ele.get(5).toString());
                        general.setEnterBy(ele.get(6).toString());
                        general.setEntryDate(ele.get(7).toString());
                        siTransGeneTbs.add(general);
                    }
                } else {
                    this.setErrorMsg("Records does not exist.");
                }
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void postingData() {
        if (instructionType.equals("--SELECT--")) {
            this.setErrorMsg("Please select instruction type");
            return;
        }
        reportTables = new ArrayList<SIPostingTxnGrid>();
        this.setMessage("");
        this.setErrorMsg("");
        try {
            List resultList = new ArrayList();
            String a[][] = new String[siTransGeneTbs.size()][8];
            for (int i = 0; i < siTransGeneTbs.size(); i++) {
                a[i][0] = siTransGeneTbs.get(i).getAcno().toString();
                a[i][1] = siTransGeneTbs.get(i).getsNo().toString();
                a[i][2] = siTransGeneTbs.get(i).getInstrNo().toString();
                a[i][3] = siTransGeneTbs.get(i).getEffDate().toString();
                a[i][4] = siTransGeneTbs.get(i).getStatus().toString();
                a[i][5] = siTransGeneTbs.get(i).getRemarks().toString();
                a[i][6] = siTransGeneTbs.get(i).getEnterBy().toString();
                a[i][7] = siTransGeneTbs.get(i).getEntryDate().toString();
            }
            for (int i = 0; i < siTransGeneTbs.size(); i++) {
                for (int j = 0; j < 8; j++) {
                    Object combinedArray = a[i][j];
                    resultList.add(combinedArray);
                }
            }
            String postingResult = beanRemote.postButton(this.getUserName(), getOrgnBrCode(), instructionType, resultList);
            if (instructionType.equals("TRANSACTION")) {
                String[] values = null;
                String spliter = ": ";
                values = postingResult.split(spliter);
                String msg = (values[0]);
                String tableListValue = (values[1]);
                this.setMessage(msg);
                if (tableListValue.contains("[")) {
                    String[] values1 = null;
                    tableListValue = tableListValue.replace("]", "");
                    tableListValue = tableListValue.replace("[", "");
                    String spliter1 = ", ";
                    values1 = tableListValue.split(spliter1);
                    for (int i = 0, j = 1, k = 2, l = 3, m = 4, n = 5, o = 6, p = 7, q = 8, r = 9, s = 10, t = 11; t <= (values1.length); i = i + 12, j = j + 12, k = k + 12, l = l + 12, m = m + 12, n = n + 12, o = o + 12, p = p + 12, q = q + 12, r = r + 12, s = s + 12, t = t + 12) {
                        SIPostingTxnGrid trans = new SIPostingTxnGrid();

                        trans.setFromAcno(values1[i]);
                        trans.setFromCustName(beanRemote.getCustomerName(values1[i]));
                        trans.setToAcno(values1[j]);
                        trans.setToCustName(beanRemote.getCustomerName(values1[j]));
                        trans.setsNo(Integer.parseInt(values1[k]));
                        trans.setInstrNo(Float.parseFloat(values1[l]));
                        trans.setAmount(Float.parseFloat(values1[m]));
                        trans.setEffDate(values1[n]);
                        trans.setStatus(values1[o]);
                        trans.setRemarks(values1[p]);
                        trans.setEnterBy(values1[q]);
                        trans.setEntryDate(values1[r]);
                        trans.setChargeFlag(values1[s]);
                        trans.setExpiryDt(values1[t]);

                        reportTables.add(trans);
                    }
                }
            } else {
                this.setErrorMsg("Instructions posted successfully");
            }
            getPostGridDetails1();
        } catch (Exception e) {
            this.setErrorMsg(e.getMessage());
        }
    }

    public void changeValue() {
        try {
            SIPostingGeneralGrid item = siTransGeneTbs.get(currentRow);
            if (item.getStatus().equalsIgnoreCase("UNEXECUTED")) {
                item.setStatus("EXECUTED");
                siTransGeneTbs.remove(currentRow);
                siTransGeneTbs.add(currentRow, item);
            } else if (item.getStatus().equalsIgnoreCase("EXECUTED")) {
                item.setStatus("UNEXECUTED");
                siTransGeneTbs.remove(currentRow);
                siTransGeneTbs.add(currentRow, item);
            }
        } catch (Exception e) {
            this.setErrorMsg(e.getLocalizedMessage());
        }
    }

    public void resetForm() {
        try {
            this.setMessage("");
            this.setErrorMsg("");
            this.setInstructionType("--SELECT--");
            siTransTables = new ArrayList<SIPostingTxnGrid>();
            siTransGeneTbs = new ArrayList<SIPostingGeneralGrid>();
        } catch (Exception e) {
            this.setErrorMsg(e.getLocalizedMessage());
        }
    }

    public String exitForm() {
        try {
            resetForm();
        } catch (Exception e) {
            this.setErrorMsg(e.getLocalizedMessage());
        }
        return "case1";
    }

    //Getter And Setter
    public List<SIPostingTxnGrid> getReportTables() {
        return reportTables;
    }

    public void setReportTables(List<SIPostingTxnGrid> reportTables) {
        this.reportTables = reportTables;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public SIPostingGeneralGrid getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(SIPostingGeneralGrid currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<SIPostingGeneralGrid> getSiTransGeneTbs() {
        return siTransGeneTbs;
    }

    public void setSiTransGeneTbs(List<SIPostingGeneralGrid> siTransGeneTbs) {
        this.siTransGeneTbs = siTransGeneTbs;
    }

    public List<SIPostingTxnGrid> getSiTransTables() {
        return siTransTables;
    }

    public void setSiTransTables(List<SIPostingTxnGrid> siTransTables) {
        this.siTransTables = siTransTables;
    }

    public String getInstructionType() {
        return instructionType;
    }

    public void setInstructionType(String instructionType) {
        this.instructionType = instructionType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
