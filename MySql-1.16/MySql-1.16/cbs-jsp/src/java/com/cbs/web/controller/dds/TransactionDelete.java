/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.dds;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.dds.DDSManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.dds.TransactionDeleteTable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

/**
 *
 * @author Sudhir Kr Bisht
 */
public class TransactionDelete extends BaseBean{

    private String message,actype, acCode,name,
            flag = "true";
    private float totalAmount;
    private List<SelectItem> acTypeList, acCodeList;
    private List<TransactionDeleteTable> transGrid = new ArrayList<TransactionDeleteTable>();
    private TransactionDeleteTable currentGridItem;
    private TransactionDeleteTable currentTranDeleteTable;
    private boolean deleteAll;
    DDSManagementFacadeRemote transactionsDeleteRemote;
    private boolean parameterSet = true;

    public TransactionDelete() {
        try {
            transactionsDeleteRemote = (DDSManagementFacadeRemote) ServiceLocator.getInstance().lookup("DDSManagementFacade");
            getInitialData();
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void getInitialData() {
        try {
            setMessage("");
            acTypeList = new ArrayList<SelectItem>();
            acTypeList.add(new SelectItem("--SELECT--"));
            acCodeList = new ArrayList<SelectItem>();
            acCodeList.add(new SelectItem("--SELECT--"));
            List agentAcTypeList = transactionsDeleteRemote.getAcctType();
            if (!agentAcTypeList.isEmpty()) {
                for (int i = 0; i < agentAcTypeList.size(); i++) {
                    Vector vec = (Vector) agentAcTypeList.get(i);
                    acTypeList.add(new SelectItem(vec.get(0).toString(),"("+vec.get(0).toString()+")"+vec.get(1).toString()));
                }
            } else {
                this.setMessage("There is no account code !");
                return;
            }
            List agentCodeList = transactionsDeleteRemote.getAgCode(getOrgnBrCode());
            if (!agentCodeList.isEmpty()) {
                for (int i = 0; i < agentCodeList.size(); i++) {
                    Vector vec = (Vector) agentCodeList.get(i);
                    acCodeList.add(new SelectItem(vec.get(0).toString(),"("+vec.get(0).toString()+")"+vec.get(1).toString()));
                }
            } else {
                this.setMessage("There is no agent for this branch !");
                return;
            }
            List cashDenoModuleList = transactionsDeleteRemote.getCodeFromParameterInfoReportTable();
            if (cashDenoModuleList.isEmpty()) {
                setMessage("Cash denomination module parameter is not set!!!");
                parameterSet = false;
                flag = "false";
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void cmbAgcodeKeyDown() {
        setMessage("");
        try {
            if (parameterSet) {
                totalAmount = 0;
                String agentName = transactionsDeleteRemote.getAgentName(acCode, getOrgnBrCode());
                if (agentName.equalsIgnoreCase("")) {
                    flag = "false";
                    setMessage("Agent name does not exist !");
                    return;
                }
                setName(agentName);
                transGrid.clear();
                List unauthorizelist = transactionsDeleteRemote.getUnauthorizedDDSEntries(acCode, getOrgnBrCode());
                if (unauthorizelist.isEmpty()) {
                    flag = "false";
                    setMessage("No records exist for this agent code !");
                    return;
                } else {
                    flag = "true";
                    for (int i = 0; i < unauthorizelist.size(); i++) {
                        TransactionDeleteTable deleteGrid = new TransactionDeleteTable();
                        Vector vec = (Vector) unauthorizelist.get(i);
                        deleteGrid.setReceiptNo(vec.get(6).toString());
                        deleteGrid.setAccountNo(vec.get(0).toString());
                        deleteGrid.setCrAmt(vec.get(4).toString());
                        deleteGrid.setEnterBy(vec.get(7).toString());
                        deleteGrid.setRecno(vec.get(8).toString());
                        deleteGrid.setTokenPaid(vec.get(11).toString());
                        try {
                            deleteGrid.setTokenNo(vec.get(10).toString());
                        } catch (NullPointerException e) {
                            deleteGrid.setTokenNo(String.valueOf(0));
                        }
                        deleteGrid.setSno(String.valueOf(transGrid.size() + 1));
                        transGrid.add(deleteGrid);
                        totalAmount = totalAmount + Float.parseFloat(vec.get(4).toString());
                    }
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
            return;
        }
    }

    public void deleteRows() {
        try {
            String rowReceiptNo = currentTranDeleteTable.getReceiptNo();
            String rowRecno = currentTranDeleteTable.getRecno();
            String rowTempAcNo = currentTranDeleteTable.getAccountNo();
            String rowTokenNo = currentTranDeleteTable.getTokenNo();
            String rowCreditAmt = currentTranDeleteTable.getCrAmt();
            String newLabelAmount = String.valueOf(totalAmount - Float.parseFloat(rowCreditAmt));
            String rowTokenPaid = currentTranDeleteTable.getTokenPaid();
            String dt = formatter.format(new Date());
            String currentDate = dt.substring(6, 10) + dt.substring(3, 5) + dt.substring(0, 2);
            String result = transactionsDeleteRemote.deleteDDSEntries(rowTempAcNo, Float.parseFloat(rowRecno), rowReceiptNo, rowTokenPaid, Float.parseFloat(newLabelAmount), Integer.parseInt(rowTokenNo), totalAmount, currentDate);
            if (result.equalsIgnoreCase("true")) {
                transGrid.remove(currentTranDeleteTable);
                setTotalAmount(totalAmount - Float.parseFloat(rowCreditAmt));
                setMessage("Row deleted successfully");
            } else {
                setMessage("problem in deletion!!!");
            }
            if (transGrid.size() <= 0) {
                setAcCode("0");
                setActype("0");
                setName("");
                setTotalAmount(0f);
                flag = "false";
            } else {
                flag = "true";
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
            return;
        }
    }

    public void deleteAllRows() {
        try {
            if (transGrid.size() <= 0) {
                flag = "false";
                setMessage("No record for deletion purposes!!!");
                setName("");
                setAcCode("0");
                setActype("0");
                return;
            }
            TransactionDeleteTable tranDeleteTable1 = transGrid.get(0);
            String agentCode = tranDeleteTable1.getAccountNo().substring(10, 12);
            String tokenPaid = tranDeleteTable1.getTokenPaid();
            int tokenNo = Integer.parseInt(tranDeleteTable1.getTokenNo());
            String dt = formatter.format(new Date());
            String currentDate = dt.substring(6, 10) + dt.substring(3, 5) + dt.substring(0, 2);
            String result = transactionsDeleteRemote.deleteAllDdsEntries(agentCode, tokenPaid, tokenNo, currentDate, totalAmount,getOrgnBrCode());
            if (result.equalsIgnoreCase("true")) {
                transGrid.clear();
                setName("");
                setAcCode("0");
                setActype("0");
                setTotalAmount(0f);
                setMessage("All records delete successfully");
            } else {
                setMessage("Problem in deletion");
            }
            if (transGrid.size() <= 0) {
                setAcCode("0");
                setActype("0");
                setName("");
                setTotalAmount(0f);
                flag = "false";
            } else {
                flag = "true";
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
            return;
        }
    }

    public void refresh() {
        try {
            setMessage("");
            setAcCode("0");
            setActype("0");
            setName("");
            setTotalAmount(0f);
            flag = "true";
            transGrid.clear();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public String exitBtnAction() {
        refresh();
        return "case1";
    }


    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public boolean isDeleteAll() {
        return deleteAll;
    }

    public void setDeleteAll(boolean deleteAll) {
        this.deleteAll = deleteAll;
    }
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public TransactionDeleteTable getCurrentTranDeleteTable() {
        return currentTranDeleteTable;
    }

    public void setCurrentTranDeleteTable(TransactionDeleteTable currentTranDeleteTable) {
        this.currentTranDeleteTable = currentTranDeleteTable;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public TransactionDeleteTable getCurrentGridItem() {
        return currentGridItem;
    }

    public void setCurrentGridItem(TransactionDeleteTable currentGridItem) {
        this.currentGridItem = currentGridItem;
    }

    public List<TransactionDeleteTable> getTransGrid() {
        return transGrid;
    }

    public void setTransGrid(List<TransactionDeleteTable> transGrid) {
        this.transGrid = transGrid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcCode() {
        return acCode;
    }

    public void setAcCode(String acCode) {
        this.acCode = acCode;
    }

    public List<SelectItem> getAcCodeList() {
        return acCodeList;
    }

    public void setAcCodeList(List<SelectItem> acCodeList) {
        this.acCodeList = acCodeList;
    }

    public List<SelectItem> getAcTypeList() {
        return acTypeList;
    }

    public void setAcTypeList(List<SelectItem> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public String getActype() {
        return actype;
    }

    public void setActype(String actype) {
        this.actype = actype;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    
}
