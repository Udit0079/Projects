package com.cbs.web.controller.master;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.share.ShareTransferFacadeRemote;
import com.cbs.facade.master.LedgerMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
//import com.cbs.web.pojo.master.SITransTable;
//import com.cbs.web.pojo.master.SIGeneralTable;
import java.text.SimpleDateFormat;
import java.util.Date;
//import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
//import javax.faces.event.ActionEvent;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.faces.model.SelectItem;

public final class StandingInstructionMaster extends BaseBean {

    private String message;
    private String function;
    private List<SelectItem> functionList;
    private String focusId;
    private String inputDisFlag = "none"; 
    private String pendingInst;
    private List<SelectItem> pendingInstList;
    private String instructionType;
    private String oldActToBeDebited;
    private String actToBeDebited, acNoLen;
    Date dates;
    private String effPeriod;
    private String name1;
    private String name;
    private String oldActToBeCredited;
    private String actToBeCredited;
    private String periodicity;
    private String validityDays;
    private String amount;
    private String remarks;
    private String deductCharges;
    private String creditAcctName;
    private String fullAcctCredited;
    String accTo;
    private String acctToDebited;
    String tableList;
    LedgerMasterFacadeRemote testEJB;
    private final String FtsPostingMgmtFacadeJndiName = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote fts = null;
    ShareTransferFacadeRemote remoteObject;
    private String btnLabel = "Save";
    private String enterBy;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    private boolean acnoDisb;
    private boolean dtDisb;
    private boolean prdDisb;
    private boolean crAcnoDisb;
    private boolean periodDisb;
    private boolean valDisb;
    private boolean amtDisb;
    private boolean remDisb;
    private boolean chgDisb;
//    int currentRow;
//    SITransTable currentItem;
//    private List<SITransTable> siTransTables;
//    private List<SIGeneralTable> siTransGeneTbs;
//    SIGeneralTable currentItem1;
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<SelectItem> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<SelectItem> functionList) {
        this.functionList = functionList;
    }
    
    public String getFocusId() {
        return focusId;
    }

    public void setFocusId(String focusId) {
        this.focusId = focusId;
    }

    public String getInputDisFlag() {
        return inputDisFlag;
    }

    public void setInputDisFlag(String inputDisFlag) {
        this.inputDisFlag = inputDisFlag;
    }
    
    public String getPendingInst() {
        return pendingInst;
    }

    public void setPendingInst(String pendingInst) {
        this.pendingInst = pendingInst;
    }

    public List<SelectItem> getPendingInstList() {
        return pendingInstList;
    }

    public void setPendingInstList(List<SelectItem> pendingInstList) {
        this.pendingInstList = pendingInstList;
    }
    
    public String getInstructionType() {
        return instructionType;
    }

    public void setInstructionType(String instructionType) {
        this.instructionType = instructionType;
    }
    
    public String getOldActToBeDebited() {
        return oldActToBeDebited;
    }

    public void setOldActToBeDebited(String oldActToBeDebited) {
        this.oldActToBeDebited = oldActToBeDebited;
    }
    
    public String getActToBeDebited() {
        return actToBeDebited;
    }

    public void setActToBeDebited(String actToBeDebited) {
        this.actToBeDebited = actToBeDebited;
    }
    
    public Date getDates() {
        return dates;
    }

    public void setDates(Date dates) {
        this.dates = dates;
    }
    
    public String getEffPeriod() {
        return effPeriod;
    }

    public void setEffPeriod(String effPeriod) {
        this.effPeriod = effPeriod;
    }
    
    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getOldActToBeCredited() {
        return oldActToBeCredited;
    }

    public void setOldActToBeCredited(String oldActToBeCredited) {
        this.oldActToBeCredited = oldActToBeCredited;
    }
    
    public String getActToBeCredited() {
        return actToBeCredited;
    }

    public void setActToBeCredited(String actToBeCredited) {
        this.actToBeCredited = actToBeCredited;
    }
    
    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }
    
    public String getValidityDays() {
        return validityDays;
    }

    public void setValidityDays(String validityDays) {
        this.validityDays = validityDays;
    }
    
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public String getDeductCharges() {
        return deductCharges;
    }

    public void setDeductCharges(String deductCharges) {
        this.deductCharges = deductCharges;
    }
    
    public String getCreditAcctName() {
        return creditAcctName;
    }

    public void setCreditAcctName(String creditAcctName) {
        this.creditAcctName = creditAcctName;
    }

    public String getFullAcctCredited() {
        return fullAcctCredited;
    }

    public void setFullAcctCredited(String fullAcctCredited) {
        this.fullAcctCredited = fullAcctCredited;
    }
    
    public String getAccTo() {
        return accTo;
    }

    public void setAccTo(String accTo) {
        this.accTo = accTo;
    }
    
    public String getAcctToDebited() {
        return acctToDebited;
    }

    public void setAcctToDebited(String acctToDebited) {
        this.acctToDebited = acctToDebited;
    }

    public String getBtnLabel() {
        return btnLabel;
    }

    public void setBtnLabel(String btnLabel) {
        this.btnLabel = btnLabel;
    }    

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }    
    
//    public SIGeneralTable getCurrentItem1() {
//        return currentItem1;
//    }
//
//    public void setCurrentItem1(SIGeneralTable currentItem1) {
//        this.currentItem1 = currentItem1;
//    }
//
//    public List<SIGeneralTable> getSiTransGeneTbs() {
//        return siTransGeneTbs;
//    }
//
//    public void setSiTransGeneTbs(List<SIGeneralTable> siTransGeneTbs) {
//        this.siTransGeneTbs = siTransGeneTbs;
//    }   
//
//    public SITransTable getCurrentItem() {
//        return currentItem;
//    }
//
//    public void setCurrentItem(SITransTable currentItem) {
//        this.currentItem = currentItem;
//    }
//
//    public int getCurrentRow() {
//        return currentRow;
//    }
//
//    public void setCurrentRow(int currentRow) {
//        this.currentRow = currentRow;
//    }
//
//    public List<SITransTable> getSiTransTables() {
//        return siTransTables;
//    }
//
//    public void setSiTransTables(List<SITransTable> siTransTables) {
//        this.siTransTables = siTransTables;
//    }    
    
    public boolean isAcnoDisb() {
        return acnoDisb;
    }

    public void setAcnoDisb(boolean acnoDisb) {
        this.acnoDisb = acnoDisb;
    }

    public boolean isDtDisb() {
        return dtDisb;
    }

    public void setDtDisb(boolean dtDisb) {
        this.dtDisb = dtDisb;
    }

    public boolean isPrdDisb() {
        return prdDisb;
    }

    public void setPrdDisb(boolean prdDisb) {
        this.prdDisb = prdDisb;
    }

    public boolean isCrAcnoDisb() {
        return crAcnoDisb;
    }

    public void setCrAcnoDisb(boolean crAcnoDisb) {
        this.crAcnoDisb = crAcnoDisb;
    }

    public boolean isPeriodDisb() {
        return periodDisb;
    }

    public void setPeriodDisb(boolean periodDisb) {
        this.periodDisb = periodDisb;
    }

    public boolean isValDisb() {
        return valDisb;
    }

    public void setValDisb(boolean valDisb) {
        this.valDisb = valDisb;
    }

    public boolean isAmtDisb() {
        return amtDisb;
    }

    public void setAmtDisb(boolean amtDisb) {
        this.amtDisb = amtDisb;
    }

    public boolean isRemDisb() {
        return remDisb;
    }

    public void setRemDisb(boolean remDisb) {
        this.remDisb = remDisb;
    }

    public boolean isChgDisb() {
        return chgDisb;
    }

    public void setChgDisb(boolean chgDisb) {
        this.chgDisb = chgDisb;
    }

    public String getAcNoLen() {
        return acNoLen;
    }

    public void setAcNoLen(String acNoLen) {
        this.acNoLen = acNoLen;
    }
    
    public StandingInstructionMaster() {
        try {
            remoteObject = (ShareTransferFacadeRemote) ServiceLocator.getInstance().lookup("ShareTransferFacade");
            testEJB = (LedgerMasterFacadeRemote) ServiceLocator.getInstance().lookup("LedgerMasterFacade");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(FtsPostingMgmtFacadeJndiName);
            this.setAcNoLen(getAcNoLength());
            setDates(new Date());
            instructionType = "TRANSACTION";
            this.setMessage("");
            getListValues();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public void getListValues() {
        try {
            String levelId = remoteObject.getLevelId(getUserName(), getOrgnBrCode());
            
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("", "-Select-"));
            functionList.add(new SelectItem("1", "Add"));
            if (levelId.equals("1") || levelId.equals("2")) {
                functionList.add(new SelectItem("2", "Verify"));
            }
            functionList.add(new SelectItem("3", "Delete"));
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public void changeFunction() {
        if(instructionType.equalsIgnoreCase("TRANSACTION")){
            instructionType = "TRANSACTION";
        }else{
            instructionType = "GENERAL";
        }
        if (function.equals("1") ) {
            setFocusId("txtAccountToBeDebited");
            setBtnLabel("Save");
            setInputDisFlag("none");
            enableAll();
            setActToBeDebited("");
            this.setMessage("");
            setEffPeriod("");
            setActToBeCredited("");
            setRemarks("");
            setAmount("");
            setFullAcctCredited("");
            setAcctToDebited("");
            setCreditAcctName("");
            setOldActToBeDebited("");
            setOldActToBeCredited("");
            setPeriodicity("");
            setDeductCharges("--SELECT--");
            setValidityDays("");
            setName1("");
            setName("");
            setEnterBy("");  
            setAccTo("");
            tableList = "";
        } else if (function.equals("2")) {
            setFocusId("ddPInst");
            setBtnLabel("Verify");
            setInputDisFlag("");
            getUnAuthAcNo();
            disbAll();
            setActToBeDebited("");
            this.setMessage("");
            setEffPeriod("");
            setActToBeCredited("");
            setRemarks("");
            setAmount("");
            setFullAcctCredited("");
            setAcctToDebited("");
            setCreditAcctName("");
            setOldActToBeDebited("");
            setOldActToBeCredited("");
            setPeriodicity("");
            setDeductCharges("--SELECT--");
            setValidityDays("");
            setName1("");
            setName("");
            setEnterBy("");  
            setAccTo("");
            tableList = "";
        } else {
            setFocusId("ddPInst");
            setBtnLabel("Delete");
            setInputDisFlag("");
            getUnAuthAcNo();
            disbAll();
            setActToBeDebited("");
            this.setMessage("");
            setEffPeriod("");
            setActToBeCredited("");
            setRemarks("");
            setAmount("");
            setFullAcctCredited("");
            setAcctToDebited("");
            setCreditAcctName("");
            setOldActToBeDebited("");
            setOldActToBeCredited("");
            setPeriodicity("");
            setDeductCharges("--SELECT--");
            setValidityDays("");
            setName1("");
            setName("");
            setEnterBy("");  
            setAccTo("");
            tableList = "";
        }        
    }
    
    public void getUnAuthAcNo() {
        try {
            this.setMessage("");
            pendingInstList = new ArrayList<SelectItem>();
            List dataList = testEJB.getUnAuthSI(instructionType, getOrgnBrCode());
            if(instructionType.equalsIgnoreCase("TRANSACTION")){
                for (int i = 0; i < dataList.size(); i++) {
                    Vector ele = (Vector) dataList.get(i);
                    pendingInstList.add(new SelectItem(ele.get(0).toString(),ele.get(1).toString() + "-->" + ele.get(2).toString()));
                }
            }else{
                for (int i = 0; i < dataList.size(); i++) {
                    Vector ele = (Vector) dataList.get(i);
                    pendingInstList.add(new SelectItem(ele.get(0).toString(),ele.get(1).toString()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
    public void getInstDetail(){
        try {
            List dataList = testEJB.getUnAuthSILst(pendingInst, getOrgnBrCode());
            if(instructionType.equalsIgnoreCase("TRANSACTION")){
                for (int i = 0; i < dataList.size(); i++) {
                    Vector ele = (Vector) dataList.get(i);
                    setOldActToBeDebited(ele.get(2)==null ? "" : ele.get(2).toString());
                    setActToBeDebited(ele.get(2)==null ? "" : ele.get(2).toString());
                    setDates(sdf.parse(ele.get(3).toString()));
                    setEffPeriod(ele.get(4)==null ? "" : ele.get(4).toString());
                    setName1(ele.get(15)==null ? "" : ele.get(15).toString());
                    setOldActToBeCredited(ele.get(5)==null ? "" : ele.get(5).toString());
                    setActToBeCredited(ele.get(5)==null ? "" : ele.get(5).toString());
                    setPeriodicity(ele.get(6)==null ? "" : ele.get(6).toString());
                    setValidityDays(ele.get(7)==null ? "" : ele.get(7).toString());
                    setAmount(ele.get(8)==null ? "" : ele.get(8).toString());
                    setRemarks(ele.get(9)==null ? "" : ele.get(9).toString());
                    setDeductCharges(ele.get(10)==null ? "" : ele.get(10).toString());
                    setCreditAcctName(ele.get(17)==null ? "" : ele.get(17).toString());
                    enterBy = ele.get(11)==null ? "" : ele.get(11).toString();
                    accTo = ele.get(17)==null ? "" : ele.get(17).toString();
                    tableList = ele.get(15)==null ? "" : ele.get(15).toString();
                }
            }else{
                for (int i = 0; i < dataList.size(); i++) {
                    Vector ele = (Vector) dataList.get(i);
                    setOldActToBeDebited(ele.get(2)==null ? "" : ele.get(2).toString());
                    setActToBeDebited(ele.get(2)==null ? "" : ele.get(2).toString());
                    setDates(sdf.parse(ele.get(3).toString()));
                    setEffPeriod(ele.get(4)==null ? "" : ele.get(4).toString());
                    setName(ele.get(16)==null ? "" : ele.get(16).toString());
                    setPeriodicity(ele.get(6)==null ? "" : ele.get(6).toString());
                    setRemarks(ele.get(9)==null ? "" : ele.get(9).toString());
                    setDeductCharges(ele.get(10)==null ? "" : ele.get(10).toString());
                    enterBy = ele.get(11)==null ? "" : ele.get(11).toString();
                    tableList = ele.get(16)==null ? "" : ele.get(16).toString();
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public void getGrdTransactionDetails() {
        try {
            setAcctToDebited("");
            setName1("");
            setName("");
            this.setMessage("");
//            siTransTables = new ArrayList<SITransTable>();
//            siTransGeneTbs = new ArrayList<SIGeneralTable>();
            if (instructionType.equals("--SELECT--")) {
                this.setMessage("Please Select Instruction Type");
                return;
            }
//            if (this.actToBeDebited.equalsIgnoreCase("")) {
//                this.setMessage("Please Enter Acccount To Be Debited.");
//                return;
//            }
//            if (this.actToBeDebited.length() != 12) {
//                this.setMessage("Please Enter 12 Digit Acccount To Be Debited.");
//                return;
//            }
            
            if (this.oldActToBeDebited == null || this.oldActToBeDebited.equalsIgnoreCase("") || this.oldActToBeDebited.length() == 0) {
                this.setMessage("Please Enter the Account No.");             
                return;
            }
            if (!oldActToBeDebited.matches("[0-9a-zA-z]*")) {                  
                    setMessage("Please Enter Valid  Account No.");
                    return ;
                }
            //if (oldActToBeDebited.length() != 12) {
            if (!this.oldActToBeDebited.equalsIgnoreCase("") && ((this.oldActToBeDebited.length() != 12) && (this.oldActToBeDebited.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                setMessage("Account number is not valid.");
                return;
            }
            acctToDebited = fts.getNewAccountNumber(oldActToBeDebited);
            actToBeDebited = fts.getNewAccountNumber(oldActToBeDebited);
            setActToBeDebited(actToBeDebited); 
            
            tableList = testEJB.acnoLostFocus(actToBeDebited, instructionType);
            if (tableList.equals("Account Does Not Exist")) {
                this.setMessage(tableList);
            } else if (tableList.equals("This Account Is Closed")) {
                this.setMessage(tableList);
            } else if (tableList.equals("This Account Is Inoperative")) {
                this.setMessage(tableList);
            } else {
                setAcctToDebited(actToBeDebited);
                setName1(tableList);
                setName(tableList);
//                loadGridDetails();
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }
    
    public void accountNoTo() {
        try {
            setCreditAcctName("");
            this.setMessage("");
            if (this.actToBeDebited.equalsIgnoreCase("")) {
                this.setMessage("Please Enter Acccount To Be Debited.");
                return;
            }
//            if (this.actToBeCredited.equalsIgnoreCase("")) {
//                this.setMessage("Please Enter Account To Be Credited.");
//                return;
//            }
            
//            if (this.actToBeCredited.length() != 12) {
//                this.setMessage("Please Enter 12 Digit Account To Be Credited.");
//                return;
//            }
            if (this.oldActToBeCredited == null || this.oldActToBeCredited.equalsIgnoreCase("") || this.oldActToBeCredited.length() == 0) {
                this.setMessage("Please Enter Account To Be Credited.");             
                return;
            }
            if (!oldActToBeCredited.matches("[0-9a-zA-z]*")) {                  
                    setMessage("Please Enter Valid  Account To Be Credited.");
                    return ;
                }
            //if (oldActToBeCredited.length() != 12) {
            if (!this.oldActToBeCredited.equalsIgnoreCase("") && ((this.oldActToBeCredited.length() != 12) && (this.oldActToBeCredited.length() != (Integer.parseInt(this.getAcNoLen()))))) {
                setMessage("Credited Account number is not valid.");
                return;
            }
            actToBeCredited = fts.getNewAccountNumber(oldActToBeCredited);
            setActToBeCredited(actToBeCredited); 
            if (actToBeCredited != null || !(actToBeCredited.equalsIgnoreCase(""))) {
                fullAcctCredited = actToBeCredited;
            } else {
                fullAcctCredited = "";
            }
            accTo = testEJB.acctoLostFocus(actToBeDebited, actToBeCredited, instructionType);
            if (accTo.equals("Account Does Not Exist")) {
                this.setMessage(accTo);
            } else if (accTo.equals("Instruction Cannot Be Given To same Account")) {
                this.setMessage(accTo);
            } else if (accTo.equals("This Account Is Closed")) {
                this.setMessage(accTo);
            } else if (accTo.equals("This Account Is Inoperative")) {
                this.setMessage(accTo);
            } else {
                setCreditAcctName(accTo);
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

//    public void clearTextSave() {
//        try {
//            setActToBeDebited("");
//            this.setMessage("");
//            setActToBeCredited("");
//            setRemarks("");
//            setAmount("");
//            setFullAcctCredited("");
//            setAcctToDebited("");
//            setOldActToBeDebited("");
//            setOldActToBeCredited("");
//            setCreditAcctName("");
//            setEffPeriod("");
//            setPeriodicity("");
//            setDeductCharges("--SELECT--");
//            setValidityDays("");
//            setName1("");
//            setName("");
//        } catch (Exception e) {
//            setMessage(e.getLocalizedMessage());
//        }
//    }

    public void clearText() {
        try {
            setActToBeDebited("");
            this.setMessage("");
            setActToBeCredited("");
            setRemarks("");
            setAmount("");
            setFullAcctCredited("");
            setAcctToDebited("");
            setCreditAcctName("");
            setOldActToBeDebited("");
            setOldActToBeCredited("");
            setEffPeriod("");
            setPeriodicity("");
            setDeductCharges("--SELECT--");
            setValidityDays("");
//            siTransTables = new ArrayList<SITransTable>();
//            siTransGeneTbs = new ArrayList<SIGeneralTable>();
            setName1("");
            setName("");
            setEnterBy("");
            instructionType = "TRANSACTION";
            setFunction("");
            setInputDisFlag("none");
            enableAll();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String onblurDt() {
        String str = "";
        try {
            this.setMessage("");
            if (dates == null) {
                this.setMessage("Please Enter Effective Date.");
                return str = "Please Enter Effective Date.";
            } else {
                this.setMessage("");
                return str = "true";
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
        return str;
    }
    
//    public void loadGridDetails() {
//        try {
//            List tableListp = new ArrayList();
//            List tableListq = new ArrayList();
//            if (instructionType.equals("TRANSACTION")) {
//                tableListp = testEJB.loadGrdTransaction(actToBeDebited, getOrgnBrCode());
//                if (!tableListp.isEmpty()) {
//                    for (int i = 0; i < tableListp.size(); i++) {
//                        Vector ele = (Vector) tableListp.get(i);
//                        SITransTable transaction = new SITransTable();
//                        transaction.setFromAcno(ele.get(0).toString());
//                        transaction.setToAcno(ele.get(1).toString());
//                        transaction.setsNo(Integer.parseInt(ele.get(2).toString()));
//                        transaction.setInstrNo(Float.parseFloat(ele.get(3).toString()));
//                        transaction.setAmount(Float.parseFloat(ele.get(4).toString()));
//                        transaction.setEffDate(ele.get(5).toString());
//                        transaction.setStatus(ele.get(6).toString());
//                        transaction.setRemarks(ele.get(7).toString());
//                        transaction.setEnterBy(ele.get(8).toString());
//                        transaction.setEntryDate(ele.get(9).toString());
//                        transaction.setChargeFlag(ele.get(10).toString());
//                        transaction.setExpiryDt(ele.get(11).toString());
//                        siTransTables.add(transaction);
//                    }
//                }
//            }
//            if (instructionType.equals("GENERAL")) {
//                tableListq = testEJB.loadGrdGeneralData(actToBeDebited, getOrgnBrCode());
//                if (!tableListq.isEmpty()) {
//                    for (int i = 0; i < tableListq.size(); i++) {
//                        Vector ele = (Vector) tableListq.get(i);
//                        SIGeneralTable general = new SIGeneralTable();
//                        general.setAcno(ele.get(0).toString());
//                        general.setsNo(Integer.parseInt(ele.get(1).toString()));
//                        general.setInstrNo(Float.parseFloat(ele.get(2).toString()));
//                        general.setEffDate(ele.get(3).toString());
//                        general.setStatus(ele.get(4).toString());
//                        general.setRemarks(ele.get(5).toString());
//                        general.setEnterBy(ele.get(6).toString());
//                        general.setEntryDate(ele.get(7).toString());
//                        siTransGeneTbs.add(general);
//                    }
//                }
//            }
//        } catch (ApplicationException e) {
//            setMessage(e.getLocalizedMessage());
//        } catch (Exception e) {
//            setMessage(e.getLocalizedMessage());
//        }
//    }

//    public void fetchCurrentRow(ActionEvent event) {
//        try {
//            String accNo = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("acNo"));
//            currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
//            for (SITransTable item : siTransTables) {
//                if (item.getFromAcno().equals(accNo)) {
//                    currentItem = item;
//                    break;
//                }
//            }
//        } catch (Exception e) {
//            setMessage(e.getLocalizedMessage());
//        }
//    }

//    public void delete() {
//        try {
//            siTransTables = new ArrayList<SITransTable>();
//            siTransGeneTbs = new ArrayList<SIGeneralTable>();
//            this.setMessage("");
//            String deleteEntry = new String();
//            deleteEntry = testEJB.deleteTransactionData(currentItem.getFromAcno(), currentItem.getInstrNo(), getOrgnBrCode());
//            this.setMessage(deleteEntry);
//            loadGridDetails();
//        } catch (ApplicationException e) {
//            setMessage(e.getLocalizedMessage());
//        } catch (Exception e) {
//            setMessage(e.getLocalizedMessage());
//        }
//    }

//    public void fetchCurrentRow1(ActionEvent event) {
//        try {
//            String accNo1 = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("acNo1"));
//            currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
//            for (SIGeneralTable item : siTransGeneTbs) {
//                if (item.getAcno().equals(accNo1)) {
//                    currentItem1 = item;
//                    break;
//                }
//            }
//        } catch (Exception e) {
//            setMessage(e.getLocalizedMessage());
//        }
//    }

//    public void delete1() {
//        try {
//            siTransTables = new ArrayList<SITransTable>();
//            siTransGeneTbs = new ArrayList<SIGeneralTable>();
//            this.setMessage("");
//            String deleteEntry1 = new String();
//            deleteEntry1 = testEJB.deleteGeneralTableData(currentItem1.getAcno(), currentItem1.getInstrNo(), getOrgnBrCode());
//            this.setMessage(deleteEntry1);
//            loadGridDetails();
//        } catch (ApplicationException e) {
//            setMessage(e.getLocalizedMessage());
//        } catch (Exception e) {
//            setMessage(e.getLocalizedMessage());
//        }
//    }

    public void save() {
        try {
//            siTransTables = new ArrayList<SITransTable>();
//            siTransGeneTbs = new ArrayList<SIGeneralTable>();
            this.setMessage("");
            if (instructionType.equals("TRANSACTION")) {
                if (this.actToBeDebited.equalsIgnoreCase("")) {
                    this.setMessage("Please Enter Account To Be Debited As Acccount No.");
                    return;
                }
                if (this.actToBeDebited.length() != 12) {
                    this.setMessage("Please Enter 12 Digit Acccount To Be Debited.");
                    return;
                }
                String actToBeDebitedBrCode = fts.getCurrentBrnCode(actToBeDebited);
                if (!actToBeDebitedBrCode.equalsIgnoreCase(getOrgnBrCode())) {
                    this.setMessage("You Can not Debit Other Branch's Account");
                    return;
                }
                
                Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
                if (this.effPeriod.equalsIgnoreCase("") || this.effPeriod.length() == 0) {
                    this.setMessage("Please Enter Period.");
                    return;
                }
                Matcher billNoCheck = p.matcher(effPeriod);
                if (!billNoCheck.matches()) {
                    this.setMessage("Please Enter Valid  Period.");
                    return;
                }
                if (Float.parseFloat(effPeriod) < 0) {
                    this.setMessage("Please Enter Valid Period.");
                    return;
                }
                if (this.actToBeCredited.equalsIgnoreCase("")) {
                    this.setMessage("Please Enter Account To Be Credited As  Acccount No.");
                    return;
                }
                if (this.actToBeCredited.length() != 12) {
                    this.setMessage("Please Enter 12 Digit Account To Be Credited.");
                    return;
                }
                if (accTo.equals("Account Does Not Exist")) {
                    this.setMessage("Please Do Not Try Because Your Account Does Not Exist");
                    return;
                } else if (accTo.equals("Instruction Cannot Be Given To same Account")) {
                    this.setMessage("Instruction Cannot Be Given To same Account");
                    return;
                } else if (accTo.equals("This Account Is Closed")) {
                    this.setMessage("Please Do Not Try Because Your Account Has Been Closed");
                    return;
                }
                if (this.periodicity.equalsIgnoreCase("--SELECT--")) {
                    this.setMessage("Please Select Periodicity.");
                    return;
                }
                if (this.validityDays.equalsIgnoreCase("")) {
                    this.setMessage("Please Enter ValidityDays.");
                    return;
                }
                Matcher validityCheck = p.matcher(validityDays);
                if (!validityCheck.matches()) {
                    this.setMessage("Please Enter Valid ValidityDays.");
                    return;
                }
                if (Integer.parseInt(validityDays) < 0) {
                    this.setMessage("Please Enter Valid ValidityDays.");
                    return;
                }
                if (this.amount.equalsIgnoreCase("") || this.amount.equalsIgnoreCase("0") || this.amount.equalsIgnoreCase("0.0")) {
                    this.setMessage("Please Enter Amount.");
                    return;
                }
                Matcher amountCheck = p.matcher(amount);
                if (!amountCheck.matches()) {
                    this.setMessage("Please Enter Valid amount.");
                    return;
                }
                if (Float.parseFloat(amount) < 0.0) {
                    this.setMessage("Please Enter Valid amount.");
                    return;
                }
                if (this.remarks.equalsIgnoreCase("")) {
                    this.setMessage("Please Enter Remarks.");
                    return;
                }
                if (this.deductCharges.equalsIgnoreCase("--SELECT--")) {
                    this.setMessage("Please Select Deduct Charges.");
                    return;
                }
                name = "";
            }
            if (instructionType.equals("GENERAL")) {
                if (this.actToBeDebited.equalsIgnoreCase("")) {
                    this.setMessage("Please Enter Account No AS Acccount No.");
                    return;
                }
                if (this.actToBeDebited.length() != 12) {
                    this.setMessage("Please Enter 12 Digit Acccount To Be Debited.");
                    return;
                }
                Pattern p = Pattern.compile("((-|\\+)?[0-9]+(\\.[0-9]+)?)+");
                if (this.effPeriod.equalsIgnoreCase("") || this.effPeriod.length() == 0) {
                    this.setMessage("Please Enter Effective Period.");
                    return;
                }
                Matcher billNoCheck = p.matcher(effPeriod);
                if (!billNoCheck.matches()) {
                    this.setMessage("Please Enter Valid  Effective Period.");
                    return;
                }
                if (Float.parseFloat(effPeriod) < 0) {
                    this.setMessage("Please Enter Valid Period.");
                    return;
                }
                if (this.periodicity.equalsIgnoreCase("--SELECT--")) {
                    this.setMessage("Please Select Periodicity.");
                    return;
                }
                if (this.remarks.equalsIgnoreCase("")) {
                    this.setMessage("Please Enter Remarks.");
                    return;
                }
                if (this.deductCharges.equalsIgnoreCase("--SELECT--")) {
                    this.setMessage("Please Select Deduct Charges.");
                    return;
                }
                if (amount.equalsIgnoreCase("") || amount == null) {
                    amount = "0";
                }
                if (amount.contains(".")) {
                    amount = amount.substring(0, amount.indexOf("."));
                }
                if (validityDays.equalsIgnoreCase("") || validityDays == null) {
                    validityDays = "1";
                }
                name1 = "";
                creditAcctName = "";                
            }
            if (tableList.equals("Account Does Not Exist")) {
                this.setMessage("Please Do Not Try Because Your Account Does Not Exist");
                return;
            } else if (tableList.equals("This Account Is Closed")) {
                this.setMessage("Please Do Not Try Because Your Account Has Been Closed");
                return;
            }
            String s5 = onblurDt();
            if (!s5.equalsIgnoreCase("true")) {
                this.setMessage(s5);
                return;
            } else {
                this.setMessage("");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String dts = sdf.format(this.dates);
            String dd = dts.substring(0, 2);
            String mm = dts.substring(3, 5);
            String yy = dts.substring(6, 10);
            String correctDt = yy + "" + mm + "" + dd;
            if (actToBeCredited != null || !(actToBeCredited.equalsIgnoreCase(""))) {
            } else {
                actToBeCredited = "";
            }
            
            if(btnLabel.equalsIgnoreCase("Save")){
                String saveData = testEJB.saveUnAuthSIData(instructionType,actToBeDebited,correctDt,effPeriod,fullAcctCredited,periodicity,Integer.parseInt(validityDays),Float.parseFloat(amount),remarks,deductCharges,getUserName(),getOrgnBrCode(), name1, name, creditAcctName);
                if(saveData.equalsIgnoreCase("true")){
                    clearText();
                    this.setMessage("Instruction Saved Successfully");
                }
            }else if(btnLabel.equalsIgnoreCase("Verify")) {
                if (fts.isUserAuthorized(getUserName(), getOrgnBrCode())) {
                    if (this.getUserName().equals(enterBy)) {
                        this.setMessage("You can not authorize your own entry.");
                    } else {
                        if (deductCharges.equals("YES")) {
                            deductCharges = "Y";
                        } else {
                            deductCharges = "N";
                        }
                        String saveData = testEJB.saveData(instructionType, effPeriod, remarks, Float.parseFloat(amount), actToBeDebited, actToBeCredited, periodicity, correctDt, Integer.parseInt(validityDays), getUserName(), deductCharges, getOrgnBrCode(), pendingInst);
                        String[] values = null;
                        String spliter = ": ";
                        values = saveData.split(spliter);
                        clearText();
                        String msg = (values[1]);
                        this.setMessage(msg);
                    }
                } else {
                    this.setMessage("You are not authorized person for verifing this detail.");
                }
            } else if(btnLabel.equalsIgnoreCase("Delete")){
                String delData = testEJB.deleteUnAuthSIData(instructionType, getOrgnBrCode(),pendingInst);
                if(delData.equalsIgnoreCase("true")){
                    clearText();
                    this.setMessage("Instruction Deleted Successfully");
                }else{
                    clearText();
                    this.setMessage(delData);
                }
            }
            
//            float instrNo = (Float.parseFloat(values[0]));
//            if (instructionType.equals("TRANSACTION")) {
//                List currentData = testEJB.loadGrdTransaction1(actToBeDebited, instrNo, getOrgnBrCode());
//                for (int i = 0; i < currentData.size(); i++) {
//                    Vector ele = (Vector) currentData.get(i);
//                    SITransTable transaction = new SITransTable();
//                    transaction.setFromAcno(ele.get(0).toString());
//                    transaction.setToAcno(ele.get(1).toString());
//                    transaction.setsNo(Integer.parseInt(ele.get(2).toString()));
//                    transaction.setInstrNo(Float.parseFloat(ele.get(3).toString()));
//                    transaction.setAmount(Float.parseFloat(ele.get(4).toString()));
//                    transaction.setEffDate(ele.get(5).toString());
//                    transaction.setStatus(ele.get(6).toString());
//                    transaction.setRemarks(ele.get(7).toString());
//                    transaction.setEnterBy(ele.get(8).toString());
//                    transaction.setEntryDate(ele.get(9).toString());
//                    transaction.setChargeFlag(ele.get(10).toString());
//                    transaction.setExpiryDt(ele.get(11).toString());
//                    siTransTables.add(transaction);
//                }
//            }
//            if (instructionType.equals("GENERAL")) {
//                List currentData = testEJB.loadGrdGeneralData1(actToBeDebited, instrNo, getOrgnBrCode());
//                for (int i = 0; i < currentData.size(); i++) {
//                    Vector ele = (Vector) currentData.get(i);
//                    SIGeneralTable general = new SIGeneralTable();
//                    general.setAcno(ele.get(0).toString());
//                    general.setsNo(Integer.parseInt(ele.get(1).toString()));
//                    general.setInstrNo(Float.parseFloat(ele.get(2).toString()));
//                    general.setEffDate(ele.get(3).toString());
//                    general.setStatus(ele.get(4).toString());
//                    general.setRemarks(ele.get(5).toString());
//                    general.setEnterBy(ele.get(6).toString());
//                    general.setEntryDate(ele.get(7).toString());
//                    siTransGeneTbs.add(general);
//                }
//            }                    
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String exitForm() {
        clearText();
        return "case1";
    }
    
    public void disbAll(){
        setAcnoDisb(true);
        setDtDisb(true);
        setPrdDisb(true);
        setCrAcnoDisb(true);
        setPeriodDisb(true);
        setValDisb(true);
        setAmtDisb(true);
        setRemDisb(true);
        setChgDisb(true);
    }
    
    public void enableAll(){
        setAcnoDisb(false);
        setDtDisb(false);
        setPrdDisb(false);
        setCrAcnoDisb(false);
        setPeriodDisb(false);
        setValDisb(false);
        setAmtDisb(false);
        setRemDisb(false);
        setChgDisb(false);
    }
}
