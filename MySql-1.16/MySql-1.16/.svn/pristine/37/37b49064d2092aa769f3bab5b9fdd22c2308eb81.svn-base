package com.cbs.web.controller.dds;

import com.cbs.dto.DDSDenominationGrid;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.dds.DDSManagementFacadeRemote;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.dds.DDSEntryGrid;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;
import org.apache.commons.collections.comparators.ComparatorChain;

public class CashReceiving extends BaseBean {

    private String agentName,
            pendingAgent,
            agent,
            denoValue,
            denoNo,
            errorMessage,
            denoPanel,
            accountType;
    private boolean disableDenoNo,
            disableDenoValue,
            disableReveiveButton,
            disableActype,
            disableAgentCode;
    DDSManagementFacadeRemote cashReceivingRemote;
    private List<DDSEntryGrid> entryTable;
    private List<SelectItem> accountTypeList;
    private List<DDSDenominationGrid> denominationTable;
    private DDSDenominationGrid currentDenominationItem;
    private int currentIDenominationRow;
    private List<SelectItem> acctType,
            agents,
            pendingAgents;
    private double totalAmount,
            currencyAmount,
            amount;
    public String gCashMod;
    NumberFormat formatter = new DecimalFormat("#.##");
    private String selectOldNewFlg;
    //private List<SelectItem> oldNewFlgList;
    private final String jndiHomeName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsRemote = null;
    private Boolean[] ar = null;
    private String denoPanelValue;
    private String tyFlg;
    private List<SelectItem> tyList;
    private List oldLst;
    private List newLst;
    AdvancesInformationTrackingRemote aitr;

    public CashReceiving() {
        try {
            onLoad();
            cashReceivingRemote = (DDSManagementFacadeRemote) ServiceLocator.getInstance().lookup("DDSManagementFacade");
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            aitr = (AdvancesInformationTrackingRemote) ServiceLocator.getInstance().lookup("AdvancesInformationTrackingFacade");

            ar = ftsRemote.ddsCashReceivedFlag(getOrgnBrCode());

            if (ar[1] == true) {
                setDenoPanelValue("");
                gCashMod = "Y";
            } else {
                setDenoPanelValue("none");
                gCashMod = "N";
            }
            //gCashMod = cashReceivingRemote.getCashMode(getOrgnBrCode());
            disableReveiveButton = true;
            getInitialData();
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    private void onLoad() {
        entryTable = new LinkedList<DDSEntryGrid>();
        denominationTable = null;
        setDisableDenoValue(true);
        setDisableDenoNo(true);
        setDisableReveiveButton(true);

        tyList = new ArrayList<SelectItem>();
        tyList.add(new SelectItem("0", "Recieved"));
        tyList.add(new SelectItem("3", "Return"));
    }

    private boolean validate() {
        Validator validator = new Validator();
        if (!validator.validateDoublePositive(denoValue)) {
            setErrorMessage("Denomination Can Have Numeric Value Only !!");
            return false;
        }
        if (!validator.validateInteger(denoNo)) {
            setErrorMessage("No. of Notes / Coins Can Have Numeric Value Only !!");
            return false;
        }

//        if (this.getSelectOldNewFlg().equalsIgnoreCase("O")) {
//            for (int i = 0; i < oldLst.size(); i++) {
//                Vector element = (Vector) oldLst.get(i);
//                if (Double.parseDouble(element.get(0).toString()) == Double.parseDouble(denoValue)) {
//                    return true;
//                }
//            }
//            this.setErrorMessage("This denomination in old can't be accepted");
//            return false;
//        }

//        if (this.getSelectOldNewFlg().equalsIgnoreCase("N")) {
            for (int i = 0; i < newLst.size(); i++) {
                Vector element = (Vector) newLst.get(i);
                if (Double.parseDouble(element.get(0).toString()) == Double.parseDouble(denoValue)) {
                    return true;
                }
            }
            this.setErrorMessage("This denomination can't be accepted");
            return false;
//        }


//        if((denoValue.equalsIgnoreCase("2000") || denoValue.equalsIgnoreCase("2000.00") || denoValue.equalsIgnoreCase("2000.0")) 
//                && this.getSelectOldNewFlg().equalsIgnoreCase("O")){
//            this.setErrorMessage("2000 notes can't be old !!!");
//            return false;
//        }
//            
//        if(!(denoValue.equalsIgnoreCase("2000") || denoValue.equalsIgnoreCase("2000.00") || denoValue.equalsIgnoreCase("2000.0") 
//                || denoValue.equalsIgnoreCase("500") || denoValue.equalsIgnoreCase("500.00") || denoValue.equalsIgnoreCase("500.0")) 
//                && this.getSelectOldNewFlg().equalsIgnoreCase("N")){
//            this.setErrorMessage(denoValue + " notes can't be New !!!");
//            return false;
//        }
        //return true;
    }

    public boolean checkCurrency() {
        try {
            Double value = Double.parseDouble(denoValue);
            if (value == 0.5) {
                return true;
            }
            if (value.intValue() != 0 && CbsUtil.round(value - value.intValue(), 2) > 0.0) {
                return false;
            }

//            if (this.getSelectOldNewFlg().equalsIgnoreCase("O")) {
//                for (int i = 0; i < oldLst.size(); i++) {
//                    Vector element = (Vector) oldLst.get(i);
//                    if (Double.parseDouble(element.get(0).toString()) == Double.parseDouble(denoValue)) {
//                        return true;
//                    }
//                }
//                return false;
//            }

//            if (this.getSelectOldNewFlg().equalsIgnoreCase("N")) {
                for (int i = 0; i < newLst.size(); i++) {
                    Vector element = (Vector) newLst.get(i);
                    if (Double.parseDouble(element.get(0).toString()) == Double.parseDouble(denoValue)) {
                        return true;
                    }
                }
                //return false;
//            }

//        switch (value.intValue()) {
//            case 1:
//                return true;
//            case 2:
//                return true;
//            case 5:
//                return true;
//            case 10:
//                return true;
//            case 20:
//                return true;
//            case 50:
//                return true;
//            case 100:
//                return true;
//            case 500:
//                return true;
//            case 1000:
//                return true;
//            case 2000:
//                return true;    
//            default:
//                return false;
//        }
        } catch (Exception e) {
            setErrorMessage(e.getLocalizedMessage());
        }
        return false;
    }

    public void receiveBtnAction() {
        try {
            //String acNo = getOrgnBrCode() + accountType + "000000" + pendingAgent;
            //DenominitionTable denominitionList = getDenominitionTable(acNo);
            this.setErrorMessage("");
            if (gCashMod.equalsIgnoreCase("Y")) {
                if (denominationTable == null || denominationTable.isEmpty()) {
                    this.setErrorMessage("Please enter denomination detail !!!");
                    return;
                }

                if (currencyAmount > totalAmount) {
                    setErrorMessage("Currency amount is exceeding total receivable amount !!");
                    return;
                } else if (currencyAmount < totalAmount) {
                    setErrorMessage("Currency amount is less total receivable amount !!");
                    return;
                }
            }

            String enterDt = getTodayDate().substring(6) + getTodayDate().substring(3, 5) + getTodayDate().substring(0, 2);
            String result = cashReceivingRemote.updateAgentTransactions(pendingAgent, getOrgnBrCode(), getUserName(), denominationTable, gCashMod, enterDt);
            cancelBtnAction();
            setErrorMessage(result);

        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void onBlurAcType() {
        try {
            if (this.accountType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select a/c type !");
                return;
            }
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }

//    public DenominitionTable getDenominitionTable(String acNo) throws ParseException {
//        SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
//        Date d = new Date();
//        DenominitionTable denominition = new DenominitionTable();
//        denominition.setAcno(acNo);
//        denominition.setAmount(totalAmount);
//        denominition.setDt(ymdFormat.format(d));
//        denominition.setEnterBy(getUserName());
//        denominition.setRecno(totalAmount);
//        denominition.setRs1(0);
//        denominition.setRs2(0);
//        denominition.setRs5(0);
//        denominition.setRs10(0);
//        denominition.setRs20(0);
//        denominition.setRs50(0);
//        denominition.setRs100(0);
//        denominition.setRs500(0);
//        denominition.setRs1000(0);
//        denominition.setRs00001(0);
//        denominition.setRs00002(0);
//        denominition.setRs001(0);
//        denominition.setRs002(0);
//        denominition.setRs005(0);
//        denominition.setRs010(0);
//        denominition.setRs020(0);
//        denominition.setRs025(0);
//        denominition.setRs05(0);
//        denominition.setRsc001(0);
//        denominition.setTrantime(ymdFormat.format(d));
//        denominition.setTy(0);
//        try {
//            for (DDSDenominationGrid dSDenominationGrid : denominationTable) {
//                if (dSDenominationGrid.getDenoValue() == 0.5) {
//                    denominition.setRs05(dSDenominationGrid.getDenoNo().intValue());
//                    break;
//                }
//                switch (dSDenominationGrid.getDenoValue().intValue()) {
//                    case 1:
//                        denominition.setRs1(dSDenominationGrid.getDenoNo().intValue());
//                        break;
//                    case 2:
//                        denominition.setRs2(dSDenominationGrid.getDenoNo().intValue());
//                        break;
//                    case 5:
//                        denominition.setRs5(dSDenominationGrid.getDenoNo().intValue());
//                        break;
//                    case 10:
//                        denominition.setRs10(dSDenominationGrid.getDenoNo().intValue());
//                        break;
//                    case 20:
//                        denominition.setRs20(dSDenominationGrid.getDenoNo().intValue());
//                        break;
//                    case 50:
//                        denominition.setRs50(dSDenominationGrid.getDenoNo().intValue());
//                        break;
//                    case 100:
//                        denominition.setRs100(dSDenominationGrid.getDenoNo().intValue());
//                        break;
//                    case 500:
//                        denominition.setRs500(dSDenominationGrid.getDenoNo().intValue());
//                        break;
//                    case 1000:
//                        denominition.setRs1000(dSDenominationGrid.getDenoNo().intValue());
//                        break;
//                }
//            }
//        } catch (Exception e) {
//            this.setErrorMessage(e.getMessage());
//        }
//        return denominition;
//    }
    public void cancelBtnAction() {
        try {
            entryTable = new ArrayList<DDSEntryGrid>();
            setAgentName("");
            setErrorMessage("");
            setDenoNo("");
            setDenoValue("");
            setTotalAmount(0);
            setCurrencyAmount(0);
            this.setAccountType("--Select--");
            this.setAmount(0);
            this.setSelectOldNewFlg("N");
            this.setTyFlg("0");
            onLoad();
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public String exitBtnAction() {
        try {
            cancelBtnAction();
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
        return "case1";
    }

    public void onBlurAgentCode() {
        this.setErrorMessage("");
        try {
            if (this.accountType.equalsIgnoreCase("--Select--")) {
                this.setErrorMessage("Please select a/c type !");
                return;
            }
            List denoCheckList = cashReceivingRemote.getCodeFromParameterInfoReportTable();
            if (denoCheckList.isEmpty()) {
                this.denoPanel = "false";
                this.disableReveiveButton = false;
            } else {
                Vector element = (Vector) denoCheckList.get(0);
                String denoCheckFlag = element.get(0).toString();
                if (denoCheckFlag.equalsIgnoreCase("0")) {
                    this.denoPanel = "false";
                    this.disableReveiveButton = false;
                } else {
                    this.denoPanel = "true";
                    this.disableReveiveButton = true;
                }
            }

            agentName = cashReceivingRemote.getAgentName(pendingAgent, getOrgnBrCode());
            entryTable = new LinkedList<DDSEntryGrid>();
            List data = cashReceivingRemote.getAgentTransactions(accountType, pendingAgent, getOrgnBrCode());
            if (!data.isEmpty()) {
                setDisableDenoNo(false);
                setDisableDenoValue(false);
                totalAmount = 0;
                amount = 0;
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
            } else {
                setTotalAmount(0.0);
                setErrorMessage("No transaction data available !");
                setDisableDenoNo(true);
                setDisableDenoValue(true);
                disableReveiveButton = true;
                entryTable = new LinkedList<DDSEntryGrid>();
            }
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void onBlurDenominationNo() {
        try {
            if (validate()) {
                if (checkCurrency()) {
                    setErrorMessage("");
                    //DenominationComparatorDesc denominationComparator = new DenominationComparatorDesc();
                    DDSDenominationGrid currentDenominationGrid = new DDSDenominationGrid();
                    if (denominationTable != null) {
                        currentDenominationGrid.setSno(denominationTable.size() + 1);
                        currentDenominationGrid.setDenoValue(Double.parseDouble(denoValue));
                        currentDenominationGrid.setDenoNo(Integer.parseInt(denoNo));
                        currentDenominationGrid.setDenoAmount(Double.parseDouble(denoValue) * Integer.parseInt(denoNo));
//                        currentDenominationGrid.setFlag(this.getSelectOldNewFlg());
                        currentDenominationGrid.setFlag("N");

                        currentDenominationGrid.setTy(this.getTyFlg());
                        if (this.getTyFlg().equalsIgnoreCase("0")) {
                            currentDenominationGrid.setTySh("Receive");
                        } else {
                            currentDenominationGrid.setTySh("Return");
                        }
                        ComparatorChain chain = new ComparatorChain();
                        chain.addComparator(new DenoValueCompare());
                        chain.addComparator(new DenoValueFlag());

                        if (Collections.binarySearch(denominationTable, currentDenominationGrid, chain) >= 0) {
//                        if (Collections.binarySearch(denominationTable, currentDenominationGrid, chain) != 0) {
                            this.setErrorMessage("You have already entered denomination for " + denoValue + ", So you can only update it by selecting from grid !!!");

                        } else {
                            denominationTable.add(currentDenominationGrid);
                        }

//                        if (Collections.binarySearch(denominationTable, currentDenominationGrid, denominationComparator) != 0) {                            
//                            denominationTable.add(currentDenominationGrid);
//                        } else {
//                            this.setErrorMessage("You have already entered denomination for " + denoValue + " with " + this.getSelectOldNewFlg() + ", So you can only update it by selecting from grid !!!");                                                        
//                        }
                    } else {
                        denominationTable = new LinkedList<DDSDenominationGrid>();
                        currentDenominationGrid.setSno(1);
                        currentDenominationGrid.setDenoValue(Double.parseDouble(denoValue));
                        currentDenominationGrid.setDenoNo(Integer.parseInt(denoNo));
                        currentDenominationGrid.setDenoAmount(Double.parseDouble(denoValue) * Integer.parseInt(denoNo));
//                        currentDenominationGrid.setFlag(this.getSelectOldNewFlg());
                        currentDenominationGrid.setFlag("N");
                        currentDenominationGrid.setTy(this.getTyFlg());
                        if (this.getTyFlg().equalsIgnoreCase("0")) {
                            currentDenominationGrid.setTySh("Receive");
                        } else {
                            currentDenominationGrid.setTySh("Return");
                        }
                        denominationTable.add(currentDenominationGrid);
                    }
                    //Collections.sort(denominationTable, denominationComparator);
                    calculateCurrencyAmount();
                    if (currencyAmount == totalAmount) {
                        setDisableReveiveButton(false);
                        setDisableDenoNo(true);
                        setDisableDenoValue(true);
                    }
//                    else if (currencyAmount > totalAmount) {
//                        denominationTable.remove(currentDenominationGrid);
//                        calculateCurrencyAmount();
//                        setErrorMessage("Currency Amount Is Exceeding Total Receivable Amount !!");
//                    }
                    setDenoValue("");
                    setDenoNo("");
                } else {
                    setErrorMessage("Currency " + denoValue + " Is Not Allowed !!");
                    setDenoNo("");
                    setDenoValue("");
                }
            } else {
                setErrorMessage("Please Fill Proper Denomination And  No of Notes / Coins !!");
            }
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    private void calculateCurrencyAmount() {
        double i = 0;
        if (denominationTable != null) {
            for (DDSDenominationGrid dDSDenominationGrid : denominationTable) {
                //i += dDSDenominationGrid.getDenoAmount();
                if (dDSDenominationGrid.getTy().equalsIgnoreCase("0")) {
                    i += dDSDenominationGrid.getDenoAmount();
                } else {
                    i -= dDSDenominationGrid.getDenoAmount();
                }
            }
            setCurrencyAmount(i);
        }
    }

//    class DenominationComparatorDesc implements Comparator<DDSDenominationGrid> {
//        public int compare(DDSDenominationGrid one, DDSDenominationGrid two) {
//            if(two.getDenoValue().equals(one.getDenoValue()) && two.getFlag().equalsIgnoreCase(one.getFlag())){
//                return 0;
//            }else{
//                return 1;
//            }
//        }
//    }
    class DenoValueCompare implements Comparator<DDSDenominationGrid> {

        @Override
        public int compare(DDSDenominationGrid o1, DDSDenominationGrid o2) {
            if (o1.denoValue.compareTo(o2.denoValue) == 0) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    class DenoValueFlag implements Comparator<DDSDenominationGrid> {

        @Override
        public int compare(DDSDenominationGrid o1, DDSDenominationGrid o2) {
            if (o1.getFlag().equals(o2.getFlag())) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    public void setDenominationRowValues() {
        try {
            denoValue = String.valueOf(currentDenominationItem.getDenoValue());
            denoNo = String.valueOf(currentDenominationItem.getDenoNo());
            //setSelectOldNewFlg(currentDenominationItem.getFlag());
            setTyFlg(currentDenominationItem.getTy());
            denominationTable.remove(currentDenominationItem);
            calculateCurrencyAmount();
            if (currencyAmount == totalAmount) {
                setDisableReveiveButton(false);
                setDisableDenoNo(true);
                setDisableDenoValue(true);
            } else {
                setDisableReveiveButton(true);
                setDisableDenoNo(false);
                setDisableDenoValue(false);
            }
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void getInitialData() {
        try {
            accountTypeList = new ArrayList<SelectItem>();
            pendingAgents = new ArrayList<SelectItem>();
            if (ar[0] == true) {
                List lis1 = cashReceivingRemote.getAcctType();
                if (!lis1.isEmpty()) {
                    accountTypeList.add(new SelectItem("--Select--"));
                    for (int i = 0; i < lis1.size(); i++) {
                        Vector ele = (Vector) lis1.get(i);
                        accountTypeList.add(new SelectItem(ele.get(0).toString()));
                    }
                } else {
                    this.setErrorMessage("There is no account code !");
                    return;
                }

                List lis3 = cashReceivingRemote.getPendingAgents(getOrgnBrCode(), ar[0]);
                if (!lis3.isEmpty()) {
                    for (int i = 0; i < lis3.size(); i++) {
                        Vector ele = (Vector) lis3.get(i);
                        for (Object ee : ele) {
                            pendingAgents.add(new SelectItem(ee.toString()));
                        }
                    }
                } else {
                    pendingAgents.add(new SelectItem("No Pending Agents !"));
                }
            } else {
                pendingAgents.add(new SelectItem("0", "--Select--"));
                this.setErrorMessage("Cashier module is not active, so proceed to the DDS Authorization form !");
            }
//            oldNewFlgList = new ArrayList<SelectItem>();
//            oldNewFlgList.add(new SelectItem("O", "OLD"));
//            oldNewFlgList.add(new SelectItem("N", "NEW"));

            oldLst = aitr.getReferenceCode("500");
            newLst = aitr.getReferenceCode("501");
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
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

    public boolean isDisableDenoNo() {
        return disableDenoNo;
    }

    public void setDisableDenoNo(boolean disableDenoNo) {
        this.disableDenoNo = disableDenoNo;
    }

    public boolean isDisableReveiveButton() {
        return disableReveiveButton;
    }

    public void setDisableReveiveButton(boolean disableReveiveButton) {
        this.disableReveiveButton = disableReveiveButton;
    }

    public String getDenoNo() {
        return denoNo;
    }

    public void setDenoNo(String denoNo) {
        this.denoNo = denoNo;
    }

    public String getDenoValue() {
        return denoValue;
    }

    public void setDenoValue(String denoValue) {
        this.denoValue = denoValue;
    }

    public List<DDSDenominationGrid> getDenominationTable() {
        return denominationTable;
    }

    public void setDenominationTable(List<DDSDenominationGrid> denominationTable) {
        this.denominationTable = denominationTable;
    }

    public DDSDenominationGrid getCurrentDenominationItem() {
        return currentDenominationItem;
    }

    public void setCurrentDenominationItem(DDSDenominationGrid currentDenominationItem) {
        this.currentDenominationItem = currentDenominationItem;
    }

    public int getCurrentIDenominationRow() {
        return currentIDenominationRow;
    }

    public void setCurrentIDenominationRow(int currentIDenominationRow) {
        this.currentIDenominationRow = currentIDenominationRow;
    }

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

    public boolean isDisableDenoValue() {
        return disableDenoValue;
    }

    public void setDisableDenoValue(boolean disableDenoValue) {
        this.disableDenoValue = disableDenoValue;
    }

    public boolean isDisableActype() {
        return disableActype;
    }

    public void setDisableActype(boolean disableActype) {
        this.disableActype = disableActype;
    }

    public boolean isDisableAgentCode() {
        return disableAgentCode;
    }

    public void setDisableAgentCode(boolean disableAgentCode) {
        this.disableAgentCode = disableAgentCode;
    }

    public String getDenoPanel() {
        return denoPanel;
    }

    public void setDenoPanel(String denoPanel) {
        this.denoPanel = denoPanel;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public List<SelectItem> getAccountTypeList() {
        return accountTypeList;
    }

    public void setAccountTypeList(List<SelectItem> accountTypeList) {
        this.accountTypeList = accountTypeList;
    }

    public String getSelectOldNewFlg() {
        return selectOldNewFlg;
    }

    public void setSelectOldNewFlg(String selectOldNewFlg) {
        this.selectOldNewFlg = selectOldNewFlg;
    }

//    public List<SelectItem> getOldNewFlgList() {
//        return oldNewFlgList;
//    }
//
//    public void setOldNewFlgList(List<SelectItem> oldNewFlgList) {
//        this.oldNewFlgList = oldNewFlgList;
//    }

    public String getDenoPanelValue() {
        return denoPanelValue;
    }

    public void setDenoPanelValue(String denoPanelValue) {
        this.denoPanelValue = denoPanelValue;
    }

    public String getTyFlg() {
        return tyFlg;
    }

    public void setTyFlg(String tyFlg) {
        this.tyFlg = tyFlg;
    }

    public List<SelectItem> getTyList() {
        return tyList;
    }

    public void setTyList(List<SelectItem> tyList) {
        this.tyList = tyList;
    }

    public List getOldLst() {
        return oldLst;
    }

    public void setOldLst(List oldLst) {
        this.oldLst = oldLst;
    }

    public List getNewLst() {
        return newLst;
    }

    public void setNewLst(List newLst) {
        this.newLst = newLst;
    }
}
