package com.cbs.web.controller.dds;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.dds.DDSManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.dds.DDSEntryGrid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

public class TransactionAuthorization extends BaseBean {

    //private HttpServletRequest request;
    private String agentName, pendingAgent, agent, errorMessage, repTp;
    private boolean disableAuthorizeButton;
    DDSManagementFacadeRemote txnRemote;
    private List<DDSEntryGrid> entryTable;
    //////  private List<DDSDenominationGrid> denominationTable;
    //  private DDSDenominationGrid currentDenominationItem;
    //  private int currentIDenominationRow;

    private List<SelectItem> acctType,
            agents,
            pendingAgents, repLst;
    private double totalAmount,
            currencyAmount, amount;

   // public String gsreport = "DDS_CASH_REC";

    public TransactionAuthorization() {
        try {

            txnRemote = (DDSManagementFacadeRemote) ServiceLocator.getInstance().lookup("DDSManagementFacade");
            repLst = new ArrayList<SelectItem>();
            repLst.add(new SelectItem("receiptno", "By Receipt No."));
            repLst.add(new SelectItem("acno", "By Account No."));

            List agentList = txnRemote.getAgentsForAuth(getOrgnBrCode());
            agents = new ArrayList<>();
            for (int i = 0; i < agentList.size(); i++) {
                Vector ele = (Vector) agentList.get(i);
                agents.add(new SelectItem(ele.get(0).toString(), ele.get(1).toString()));
            }

            getInitialData();
        } catch (ApplicationException e) {
            setErrorMessage(e.getMessage());
        } catch (Exception e) {
            setErrorMessage(e.getMessage());
        }
    }

    public void authorizeBtnAction() {
        try {
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
            Date d = new Date();
            if (entryTable != null || !entryTable.isEmpty()) {
                String enteredBy = entryTable.get(0).getEnteredBy();
                if (enteredBy.equalsIgnoreCase(getUserName())) {
                    setErrorMessage("You Cannot Authorize your Own Entry !!");
                } else {
                    String result = txnRemote.authorizeAgentTransactions(agentName, totalAmount, agent, getOrgnBrCode(), getUserName(), ymdFormat.format(d));
                    refresh();
                    setErrorMessage(result);
                }
            }
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }

    public String exitBtnAction() {
        return "case1";
    }

    public void refresh() {
        try {
            setAgent("--Select--");
            setAgentName("");
            entryTable = new ArrayList<DDSEntryGrid>();
            this.setAmount(0);
            setRepTp("receiptno");
            getInitialData();
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }

    private String getAgName(String agCode) {
        for (SelectItem item : agents) {
            if (agCode.equalsIgnoreCase(item.getValue().toString())) {
                return item.getLabel() + " (" + agCode + ")";
            }
        }
        return "";
    }

    public void onBlurAgentCode() {
        try {
            setErrorMessage("");
            setAgentName(getAgName(agent));

            entryTable = new LinkedList<DDSEntryGrid>();
            List data = txnRemote.getAgentTransactionsBy(agent, getOrgnBrCode(), this.getRepTp());
            if (!data.isEmpty()) {
                setDisableAuthorizeButton(false);
                totalAmount = 0;
                for (int i = 0; i < data.size(); i++) {
                    Vector ele = (Vector) data.get(i);
                    DDSEntryGrid currentEntryGrid = new DDSEntryGrid();
                    currentEntryGrid.setSno(i + 1);
                    currentEntryGrid.setAccountNo(ele.get(0).toString());
                    currentEntryGrid.setAmount(ele.get(4).toString());
                    currentEntryGrid.setReceiptNo(ele.get(6).toString());
                    currentEntryGrid.setEnteredBy(ele.get(7).toString());
                    currentEntryGrid.setRecNo(ele.get(8).toString());
                    if (ele.get(10) != null) {
                        currentEntryGrid.setTokenNo(ele.get(10).toString());
                    }
                    currentEntryGrid.setTokenpaid(ele.get(11).toString());
                    entryTable.add(currentEntryGrid);
                    totalAmount += Double.parseDouble(ele.get(4).toString());
                    amount += Double.parseDouble(ele.get(4).toString());
                }
            }
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void getInitialData() {
        try {
            entryTable = new LinkedList<DDSEntryGrid>();
            //    denominationTable = null;
            setDisableAuthorizeButton(true);

            // List lis1 = txnRemote.getAcctType();
            //acctType = new ArrayList<SelectItem>();
            //  agents = new ArrayList<SelectItem>();
            pendingAgents = new ArrayList<SelectItem>();

//            if (!lis1.isEmpty()) {
//                for (int i = 0; i < lis1.size(); i++) {
//                    Vector ele = (Vector) lis1.get(i);
//                    for (Object ee : ele) {
//                        acctType.add(new SelectItem(ee.toString()));
//                    }
//                }
//            } else {
//                acctType.add(new SelectItem("N", "No Account Type"));
//            }
            // List agentList = txnRemote.getAgentsForAuth(getOrgnBrCode());
//            if (!resultList.isEmpty()) {
//                agents.add(new SelectItem("--Select--"));
//                for (int i = 0; i < resultList.size(); i++) {
//                    Vector ele = (Vector) resultList.get(i);
//                    for (Object ee : ele) {
//                        agents.add(new SelectItem(ee.toString()));
//                    }
//                }
//            } else {
//                agents.add(new SelectItem("No Agents"));
//            }
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
            List resultList = txnRemote.getPendingAgentsForAuth(getOrgnBrCode());
            if (resultList.isEmpty()) {
                throw new ApplicationException("There is no pending transaction for any Agent");
            }
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                if (ele.get(0) != null && ele.get(1) != null && ymdFormat.format(new Date()).equalsIgnoreCase(ymdFormat.format(ele.get(1)))) {
                    pendingAgents.add(new SelectItem(ele.get(0).toString(), getAgName(ele.get(0).toString())));
                }
            }
        } catch (Exception e) {
            setErrorMessage(e.getMessage());
        }
    }

    public List<DDSEntryGrid> getEntryTable() {
        return entryTable;
    }

    public void setEntryTable(List<DDSEntryGrid> entryTable) {
        this.entryTable = entryTable;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

//    public String getDenoNo() {
//        return denoNo;
//    }
//
//    public void setDenoNo(String denoNo) {
//        this.denoNo = denoNo;
//    }
//
//    public String getDenoValue() {
//        return denoValue;
//    }
//
//    public void setDenoValue(String denoValue) {
//        this.denoValue = denoValue;
//    }
//
//    public List<DDSDenominationGrid> getDenominationTable() {
//        return denominationTable;
//    }
//
//    public void setDenominationTable(List<DDSDenominationGrid> denominationTable) {
//        this.denominationTable = denominationTable;
//    }
//
//    public DDSDenominationGrid getCurrentDenominationItem() {
//        return currentDenominationItem;
//    }
//
//    public void setCurrentDenominationItem(DDSDenominationGrid currentDenominationItem) {
//        this.currentDenominationItem = currentDenominationItem;
//    }
//
//    public int getCurrentIDenominationRow() {
//        return currentIDenominationRow;
//    }
//
//    public void setCurrentIDenominationRow(int currentIDenominationRow) {
//        this.currentIDenominationRow = currentIDenominationRow;
//    }
    public double getCurrencyAmount() {
        return currencyAmount;
    }

    public void setCurrencyAmount(double currencyAmount) {
        this.currencyAmount = currencyAmount;
    }

    public List<SelectItem> getAcctType() {
        return acctType;
    }

    public void setAcctType(List<SelectItem> acctType) {
        this.acctType = acctType;
    }

    public List<SelectItem> getAgents() {
        return agents;
    }

    public void setAgents(List<SelectItem> agents) {
        this.agents = agents;
    }

    public List<SelectItem> getPendingAgents() {
        return pendingAgents;
    }

    public void setPendingAgents(List<SelectItem> pendingAgents) {
        this.pendingAgents = pendingAgents;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getPendingAgent() {
        return pendingAgent;
    }

    public void setPendingAgent(String pendingAgent) {
        this.pendingAgent = pendingAgent;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isDisableAuthorizeButton() {
        return disableAuthorizeButton;
    }

    public void setDisableAuthorizeButton(boolean disableAuthorizeButton) {
        this.disableAuthorizeButton = disableAuthorizeButton;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRepTp() {
        return repTp;
    }

    public void setRepTp(String repTp) {
        this.repTp = repTp;
    }

    public List<SelectItem> getRepLst() {
        return repLst;
    }

    public void setRepLst(List<SelectItem> repLst) {
        this.repLst = repLst;
    }
}
