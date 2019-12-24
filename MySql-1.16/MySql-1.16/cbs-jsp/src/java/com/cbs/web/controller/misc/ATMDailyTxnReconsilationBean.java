/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.misc;

import com.cbs.dto.misc.ATMReconsilationDTO;
import com.cbs.facade.misc.ATMMgmtFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.utils.Validator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ATMDailyTxnReconsilationBean extends BaseBean {
    
    private String errorMessage;
    private String txnDt;
    private boolean btnDisable;
    private List<ATMReconsilationDTO> gridDetail;
    private ATMMgmtFacadeRemote atmRemote;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    
    public ATMDailyTxnReconsilationBean() {
        try {
            atmRemote = (ATMMgmtFacadeRemote) ServiceLocator.getInstance().lookup("ATMMgmtFacade");
            refreshForm();
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }
    
    public void populateReconData() {
        this.setErrorMessage("");
        this.setBtnDisable(true);
        try {
            if (this.txnDt == null || this.txnDt.equals("")) {
                this.setErrorMessage(errorMessage);
                return;
            }
            boolean result = new Validator().validateDate_dd_mm_yyyy(this.txnDt);
            if (result == false) {
                this.setErrorMessage("Please fill proper date.");
                return;
            }
            gridDetail = new ArrayList<>();
            gridDetail = atmRemote.getAllTxnsToReconsile(ymd.format(dmy.parse(txnDt)));
            if (gridDetail.isEmpty()) {
                this.setErrorMessage("There is no data to reconsile.");
                return;
            }
            this.setErrorMessage("Please check transaction data to post.");
            this.setBtnDisable(false);
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }
    
    public void processAction() {
        this.setErrorMessage("");
        try {
            if (this.gridDetail.isEmpty()) {
                this.setErrorMessage("There is no data to post.");
                return;
            }
            String result = atmRemote.postATMReconsilationData(gridDetail, ymd.format(dmy.parse(txnDt)),
                    ymd.format(dmy.parse(getTodayDate())), getUserName());
            if (!result.trim().substring(0, 4).equalsIgnoreCase("true")) {
                this.setErrorMessage(result);
                return;
            }
            refreshForm();
            this.setErrorMessage("Data has been posted successfully. Batch No Is: " + result.substring(5));
        } catch (Exception ex) {
            this.setErrorMessage(ex.getMessage());
        }
    }
    
    public void refreshForm() {
        this.setErrorMessage("");
        this.setTxnDt(getTodayDate());
        this.setBtnDisable(true);
        gridDetail = new ArrayList<>();
    }
    
    public String exitForm() {
        return "case1";
    }

    //Getter And Setter
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public String getTxnDt() {
        return txnDt;
    }
    
    public void setTxnDt(String txnDt) {
        this.txnDt = txnDt;
    }
    
    public List<ATMReconsilationDTO> getGridDetail() {
        return gridDetail;
    }
    
    public void setGridDetail(List<ATMReconsilationDTO> gridDetail) {
        this.gridDetail = gridDetail;
    }
    
    public boolean isBtnDisable() {
        return btnDisable;
    }
    
    public void setBtnDisable(boolean btnDisable) {
        this.btnDisable = btnDisable;
    }
}
