/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.clg;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.clg.OutwardClearingManagementFacadeRemote;
import com.cbs.facade.txn.OtherTransactionManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.clg.ReturnGridLoad;
import com.cbs.pojo.ChqGridDetails;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

public class LocalHighChkClrRegisManaged extends BaseBean {

    boolean flag = true;
    boolean flag2 = true;
    List<ChqGridDetails> chqGridDetails;
    ChqGridDetails chqDetails;
    List<ReturnGridLoad> returnGridList = new ArrayList<ReturnGridLoad>();
    ReturnGridLoad returnGridDetails;
    int currentRow;
    String acountNo;
    String instrNo;
    String instrDate;
    BigDecimal instrAmt;
    String bankName;
    String bankAddress;
    String name;
    List<SelectItem> clearModeOption;
    String selectclearModeOption;
    List<SelectItem> reasonForReturn;
    String reasonForReturnOption;
    String clearModeResult;
    List<SelectItem> loadPendingDateList;
    String errorMessage;
    String loadPendingDateListOption;
    private String gridName;
    private final String jndiHomeNameOutward = "OutwardClearingManagementFacade";
    private OutwardClearingManagementFacadeRemote outwardClgRemote = null;
    private final String jndiHomeNameOther = "OtherTransactionManagementFacade";
    private OtherTransactionManagementFacadeRemote otherRemote = null;

    public ChqGridDetails getChqDetails() {
        return chqDetails;
    }

    public void setChqDetails(ChqGridDetails chqDetails) {
        this.chqDetails = chqDetails;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getGridName() {
        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }

    public BigDecimal getInstrAmt() {
        return instrAmt;
    }

    public void setInstrAmt(BigDecimal instrAmt) {
        this.instrAmt = instrAmt;
    }

    public String getInstrDate() {
        return instrDate;
    }

    public void setInstrDate(String instrDate) {
        this.instrDate = instrDate;
    }

    public String getInstrNo() {
        return instrNo;
    }

    public void setInstrNo(String instrNo) {
        this.instrNo = instrNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcountNo() {
        return acountNo;
    }

    public void setAcountNo(String acountNo) {
        this.acountNo = acountNo;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public List<ChqGridDetails> getChqGridDetails() {
        return chqGridDetails;
    }

    public void setChqGridDetails(List<ChqGridDetails> chqGridDetails) {
        this.chqGridDetails = chqGridDetails;
    }

    public boolean isFlag2() {
        return flag2;
    }

    public void setFlag2(boolean flag2) {
        this.flag2 = flag2;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<SelectItem> getLoadPendingDateList() {
        return loadPendingDateList;
    }

    public void setLoadPendingDateList(List<SelectItem> loadPendingDateList) {
        this.loadPendingDateList = loadPendingDateList;
    }

    public String getLoadPendingDateListOption() {
        return loadPendingDateListOption;
    }

    public void setLoadPendingDateListOption(String loadPendingDateListOption) {
        this.loadPendingDateListOption = loadPendingDateListOption;
    }

    public String getClearModeResult() {
        return clearModeResult;
    }

    public void setClearModeResult(String clearModeResult) {
        this.clearModeResult = clearModeResult;
    }

    public List<SelectItem> getReasonForReturn() {
        return reasonForReturn;
    }

    public void setReasonForReturn(List<SelectItem> reasonForReturn) {
        this.reasonForReturn = reasonForReturn;
    }

    public String getReasonForReturnOption() {
        return reasonForReturnOption;
    }

    public void setReasonForReturnOption(String reasonForReturnOption) {
        this.reasonForReturnOption = reasonForReturnOption;
    }

    public String getSelectclearModeOption() {
        return selectclearModeOption;
    }

    public void setSelectclearModeOption(String selectclearModeOption) {
        this.selectclearModeOption = selectclearModeOption;
    }

    public List<SelectItem> getClearModeOption() {
        return clearModeOption;
    }

    public void setClearModeOption(List<SelectItem> clearModeOption) {
        this.clearModeOption = clearModeOption;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public ReturnGridLoad getReturnGridDetails() {
        return returnGridDetails;
    }

    public void setReturnGridDetails(ReturnGridLoad returnGridDetails) {
        this.returnGridDetails = returnGridDetails;
    }

    public List<ReturnGridLoad> getReturnGridList() {
        return returnGridList;
    }

    public void setReturnGridList(List<ReturnGridLoad> returnGridList) {
        this.returnGridList = returnGridList;
    }

    public LocalHighChkClrRegisManaged() {
        try {
            outwardClgRemote = (OutwardClearingManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameOutward);
            otherRemote = (OtherTransactionManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameOther);
            chqGridDetails = new ArrayList<ChqGridDetails>();
            returnGridList = new ArrayList<ReturnGridLoad>();
            loadClrngModes();
            loadReasonForReturn();
            clickOnClearingMode();
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());

        }
    }

    public void loadClrngModes() {
        try {
            List circleTyp = otherRemote.getClearingOption();
            clearModeOption = new ArrayList<SelectItem>();
            clearModeOption.add(new SelectItem("0", "--Select--"));
            for (int i = 0; i < circleTyp.size(); i++) {
                Vector vecForcircleTyp = (Vector) circleTyp.get(i);
                clearModeOption.add(new SelectItem(vecForcircleTyp.get(0).toString(), vecForcircleTyp.get(1).toString()));
            }
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
            Logger.getLogger(Authorization.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void loadReasonForReturn() {
        reasonForReturn = new ArrayList<SelectItem>();
        reasonForReturn.add(new SelectItem("Clear The Instrument"));
        List reasonReturn = new ArrayList();
        try {
            reasonReturn = outwardClgRemote.loadReasonForReturnInBean();
            for (int i = 0; i < reasonReturn.size(); i++) {
                Vector vectorForReturn = (Vector) reasonReturn.get(i);
                reasonForReturn.add(new SelectItem(vectorForReturn.get(0).toString(),vectorForReturn.get(1).toString()));
                
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void clickOnClearingMode() {
        loadPendingDateList = new ArrayList<SelectItem>();
        List listloadPendingDate = new ArrayList();
        if (this.selectclearModeOption != null) {
            if (this.selectclearModeOption.equals("C") || this.selectclearModeOption.equals("D")) {
                clearModeResult = "HIGH VALUE";
            } else if (this.selectclearModeOption.equals("E") || this.selectclearModeOption.equals("F")) {
                clearModeResult = "LOCAL CLEARING";
            }
            try {
                listloadPendingDate = outwardClgRemote.loadPendingDate(this.selectclearModeOption, getOrgnBrCode());
                if (listloadPendingDate.isEmpty()) {
                    this.setErrorMessage("No Register is active for this circle Type.");
                } else {
                    this.setErrorMessage("");
                    for (int i = 0; i < listloadPendingDate.size(); i++) {
                        Vector vectorForPendingDate = (Vector) listloadPendingDate.get(i);
                        for (Object o : vectorForPendingDate) {
                            loadPendingDateList.add(new SelectItem(o.toString()));
                        }
                    }
                }
            } catch (ApplicationException e) {
                this.setErrorMessage(e.getMessage());
            } catch (Exception e) {
                this.setErrorMessage(e.getLocalizedMessage());
            }
        }
    }

    public void comboReasonForReturn() {
        if (this.reasonForReturnOption.equals("Clear The Instrument")) {
            flag = false;
            flag2 = true;
        } else {
            flag = true;
            flag2 = false;
        }
    }

    public void loadCkqGrid() {
        try {
            List listForLoadChqGrid = new ArrayList();
            List listForChqGridReturn = new ArrayList();
            String pendingDate = null;
            if (this.loadPendingDateListOption != null) {
                pendingDate = this.loadPendingDateListOption;
                pendingDate = pendingDate.substring(6) + pendingDate.substring(3, 5) + pendingDate.substring(0, 2);
                List chkStatus = outwardClgRemote.checkStatus(this.loadPendingDateListOption, this.getSelectclearModeOption(), getOrgnBrCode());
                if (chkStatus.isEmpty()) {
                    return;
                }
                if (!chkStatus.isEmpty()) {
                    Vector vecChkStatus = (Vector) chkStatus.get(0);
                    if (!vecChkStatus.get(0).toString().equalsIgnoreCase("CLOSE") && !vecChkStatus.get(0).toString().equalsIgnoreCase("UNCLEARED")) {
                        this.setErrorMessage("This Register is not yet closed.");
                        return;
                    }
                }
                this.setErrorMessage("");
                listForLoadChqGrid = outwardClgRemote.loadCkqGridInBean(this.selectclearModeOption, pendingDate, getOrgnBrCode());
                if (!listForLoadChqGrid.isEmpty()) {
                    chqGridDetails = new ArrayList<ChqGridDetails>();
                    for (int i = 0; i < listForLoadChqGrid.size(); i++) {
                        Vector vectorForChqGridDetails = (Vector) listForLoadChqGrid.get(i);
                        chqDetails = new ChqGridDetails();
                        chqDetails.setStatus("Uncleared");
                        chqDetails.setAccNo(vectorForChqGridDetails.get(0).toString());
                        String strForInstrDt = vectorForChqGridDetails.get(7).toString();
                        strForInstrDt = strForInstrDt.substring(8, 10) + "/" + strForInstrDt.substring(5, 7) + "/" + strForInstrDt.substring(0, 4);

                        chqDetails.setInstrDt(strForInstrDt);
                        chqDetails.setInstrNo(vectorForChqGridDetails.get(4).toString());
                        chqDetails.setAmount(new BigDecimal(vectorForChqGridDetails.get(5).toString()));
                        chqDetails.setEmFlag(vectorForChqGridDetails.get(16).toString());


                        String acNoFromGrid = vectorForChqGridDetails.get(0).toString();
                        if (acNoFromGrid.substring(2, 4).equals(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
                            listForChqGridReturn = outwardClgRemote.getDetailsFromChqGrid1(acNoFromGrid, vectorForChqGridDetails.get(4).toString(), getOrgnBrCode());
                        } else {
                            listForChqGridReturn = outwardClgRemote.getDetailsFromChqGrid2(acNoFromGrid, vectorForChqGridDetails.get(4).toString(), getOrgnBrCode());
                        }
                        if (!(listForChqGridReturn.isEmpty())) {
                            Vector vectorForGl = (Vector) listForChqGridReturn.get(0);
                            chqDetails.setName(vectorForGl.get(6).toString());
                        }
                        gridName = vectorForChqGridDetails.get(17).toString();
                        chqGridDetails.add(chqDetails);
                    }
                } else {
                    chqGridDetails = new ArrayList<ChqGridDetails>();
                    chqDetails = new ChqGridDetails();
                    chqDetails.setStatus("");
                    chqDetails.setAccNo("");
                    chqDetails.setInstrDt("");
                    chqDetails.setInstrNo("");
                    chqDetails.setAmount(new BigDecimal(""));
                    chqDetails.setEmFlag("");
                    chqDetails.setName("");
                    chqGridDetails.add(chqDetails);
                }
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void fetchCurrentRow(ActionEvent event) {

        String instrNo1 = (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("instrNo"));
        currentRow = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("row"));
        for (ChqGridDetails item : chqGridDetails) {
            if (item.getAccNo().equals(instrNo1)) {
                chqDetails = item;
            }
        }
    }

    public void setRowValues() {

        String acNoFromGrid2;
        String acNoFromGrid = chqDetails.getAccNo();
        this.setAcountNo(acNoFromGrid);
        acNoFromGrid2 = acNoFromGrid.substring(2, 4);
        List listForChqGridReturn = new ArrayList();
        String strForInstrDt;
        try {
            if (acNoFromGrid2.equals("GL")) {
                listForChqGridReturn = outwardClgRemote.getDetailsFromChqGrid1(acNoFromGrid, chqDetails.getInstrNo(), getOrgnBrCode());
            } else {
                listForChqGridReturn = outwardClgRemote.getDetailsFromChqGrid2(acNoFromGrid, chqDetails.getInstrNo(), getOrgnBrCode());
            }
            if (!(listForChqGridReturn.isEmpty())) {
                Vector vectorForGl = (Vector) listForChqGridReturn.get(0);
                this.setInstrNo(vectorForGl.get(0).toString());
                strForInstrDt = vectorForGl.get(1).toString();
                strForInstrDt = strForInstrDt.substring(8, 10) + "/" + strForInstrDt.substring(5, 7) + "/" + strForInstrDt.substring(0, 4);

                this.setInstrDate(strForInstrDt);
                this.setInstrAmt(new BigDecimal(vectorForGl.get(2).toString()));
                this.setBankName(vectorForGl.get(3).toString());
                this.setBankAddress(vectorForGl.get(4).toString());
                this.setAcountNo(vectorForGl.get(5).toString());
                if (!acNoFromGrid2.equals("GL")) {
                    setName(vectorForGl.get(6).toString());
                } else {
                    setName(vectorForGl.get(6).toString());
                }
            }
        } catch (Exception e) {
            this.setErrorMessage(e.getLocalizedMessage());
        }
    }

    public void clickOnClearButton() {
        try {
            this.setErrorMessage("");
            if (this.clearModeResult.equalsIgnoreCase("---Select---")) {
                this.setErrorMessage("Please,Select Clearing Mode.");
                return;
            }
            this.setErrorMessage("");
            if (chqGridDetails.isEmpty()) {
                this.setErrorMessage("No data in grid to clear.");
                return;
            }
            if (this.loadPendingDateListOption != null && acountNo != null && instrAmt != null && instrNo != null) {
                String resultOfCoverOfClearButton = outwardClgRemote.coverOfClearButton(this.getSelectclearModeOption(),
                        this.loadPendingDateListOption, getUserName(), acountNo, instrAmt.toString(), instrNo, getOrgnBrCode(),
                        getUserName(), gridName);
                if (resultOfCoverOfClearButton.equalsIgnoreCase("Transaction Inserted Successfully")) {
                    this.setErrorMessage("Transaction Inserted Successfully");
                    this.setInstrNo("");
                    this.setInstrDate("");

                    this.setInstrAmt(new BigDecimal("0"));
                    this.setBankName("");
                    this.setBankAddress("");
                    this.setAcountNo("");
                    this.setName("");
                    chqGridDetails.remove(currentRow);
                } else {
                    this.setErrorMessage(resultOfCoverOfClearButton);
                }
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void clickOnAllClearButton() {
        try {
            this.setErrorMessage("");
            if (this.clearModeResult.equalsIgnoreCase("---Select---")) {
                this.setErrorMessage("Please,Select Clearing Mode.");
                return;
            }
            this.setErrorMessage("");
            if (chqGridDetails.isEmpty()) {
                this.setErrorMessage("No data in grid to clear.");
                return;
            }
            String resultOfCoverOfAllClearButton = outwardClgRemote.coverOfAllClearButton(this.getSelectclearModeOption(),
                    this.loadPendingDateListOption, getUserName(), getOrgnBrCode(), getUserName(), chqGridDetails);
            if (resultOfCoverOfAllClearButton.equalsIgnoreCase("Transaction Inserted Successfully")) {
                this.setErrorMessage("Transaction Inserted Successfully");
                this.setInstrNo("");
                this.setInstrDate("");
                this.setInstrAmt(new BigDecimal("0"));
                this.setBankName("");
                this.setBankAddress("");
                this.setAcountNo("");
                this.setName("");
                chqGridDetails = new ArrayList<ChqGridDetails>();
            } else {
                this.setErrorMessage(resultOfCoverOfAllClearButton);
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void returnResult() {
        if (this.instrNo == null || this.instrNo.equalsIgnoreCase("0")) {
            this.setErrorMessage("Instrument no. can not be blank.");
            flag2 = true;
            return;
        }
        this.setErrorMessage("");
        try {
            if (this.loadPendingDateListOption != null && acountNo != null && instrAmt != null && instrNo != null) {
                String returnresult = outwardClgRemote.returnClickResult(selectclearModeOption, acountNo, instrDate, instrNo,
                        Double.parseDouble(instrAmt.toString()), getUserName(), getOrgnBrCode(), reasonForReturnOption, loadPendingDateListOption, getUserName());
                if (!returnresult.equalsIgnoreCase("true")) {
                    this.setErrorMessage(returnresult);
                    return;
                } else {
                    returnGridDetails = new ReturnGridLoad();
                    returnGridDetails.setAccNoReturn(this.acountNo);
                    returnGridDetails.setInstAmount(this.instrAmt.toString());
                    returnGridDetails.setInstNumber(this.instrNo);
                    returnGridDetails.setStatuAfterReturn("Returned");
                    if (returnGridList.isEmpty()) {
                        returnGridDetails.setsNo("1");
                    } else {
                        returnGridDetails.setsNo(String.valueOf(returnGridList.size() + 1));
                    }

                    returnGridList.add(returnGridDetails);
                    chqGridDetails.remove(currentRow);
                    this.setAcountNo("");
                    this.setInstrAmt(new BigDecimal("0"));
                    this.setInstrDate("");
                    this.setInstrNo("");
                    this.setBankAddress("");
                    this.setBankName("");
                    this.setName("");
                    this.setReasonForReturnOption("Clear The Instrument");
                    flag = true;
                    flag2 = true;

                    this.setErrorMessage("Data has been returned successfully.");
                }
            }
        } catch (ApplicationException e) {
            this.setErrorMessage(e.getMessage());
        } catch (Exception e) {
            this.setErrorMessage(e.getMessage());
        }
    }

    public void refreshForm() {
        this.setAcountNo("");
        this.setInstrAmt(new BigDecimal("0"));
        this.setInstrDate("");
        this.setInstrNo("");
        this.setBankAddress("");
        this.setBankName("");
        this.setName("");
        this.setSelectclearModeOption("---Select---");
        returnGridList.clear();
        chqGridDetails.clear();
        this.setLoadPendingDateListOption("");
        this.setErrorMessage("");
    }

    public String exitBtnAction() {
        refreshForm();
        this.setErrorMessage("");
        this.setLoadPendingDateListOption(null);
        this.setReasonForReturnOption("Clear The Instrument");
        return "case1";
    }
}
