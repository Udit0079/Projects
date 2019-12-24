/**
 *
 * @author Navneet Goyal
 */
package com.cbs.web.controller.dds;

import com.cbs.dto.ReceiptMasterTable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.dds.DDSManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class DDSReceiptMaster extends BaseBean{
    
    private String message;
    private DDSManagementFacadeRemote receiptRemote;
    private String receiptFrom;
    private String receiptTo;
    private boolean disableSaveButton;
    private boolean disableDeleteButton;
    private boolean disableReceiptFrom;
    private boolean disableReceiptTo;
    List<ReceiptMasterTable> receiptMasterTable;
    ReceiptMasterTable currentRmt;
    
    public DDSReceiptMaster() {
        try {
            receiptRemote = (DDSManagementFacadeRemote) ServiceLocator.getInstance().lookup("DDSManagementFacade");
            onLoad();
        } catch (ApplicationException e) {
            setMessage(e.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
    public void saveAction() {
        try {
            setMessage("");
            String validation = validation();
            if (validation.equalsIgnoreCase("Validations successful")) {
                String insertSeries = receiptRemote.insertSeries(Float.parseFloat(receiptFrom), Float.parseFloat(receiptTo));
                cancelAction();
                setMessage(insertSeries);
            } else {
                setMessage(validation);
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
    public void viewAction() {
        try {
            setMessage("");
            receiptMasterTable = receiptRemote.getAvailableReceipts();
            if (receiptMasterTable.isEmpty()) {
                setMessage("No data exists !");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
    public void deleteAction() {
        try {
            setMessage("");
            String validation = validation();
            if (validation.equalsIgnoreCase("Validations successful")) {
                String insertSeries = receiptRemote.deleteSeries(Float.parseFloat(receiptFrom), Float.parseFloat(receiptTo));
                cancelAction();
                setMessage(insertSeries);
            } else {
                setMessage(validation);
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
    public void cancelAction() {
        try {
            setMessage("");
            setReceiptFrom("");
            setReceiptTo("");
            onLoad();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
    public String exitAction() {
        try {
            cancelAction();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
        return "case1";
    }
    
    public void setRowValues() {
        try {
            receiptFrom = String.valueOf(currentRmt.getReceiptFrom());
            receiptTo = String.valueOf(currentRmt.getReceiptTo());
            disableSaveButton = true;
            disableDeleteButton = false;
            disableValues();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
    private void onLoad() {
        try {
            viewAction();
            disableSaveButton = false;
            disableDeleteButton = true;
            disableReceiptFrom = false;
            disableReceiptTo = false;
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }
    
    private String validation() {
        try {
            if (!isFloat(receiptFrom)) {
                return "Please enter numeric value in field 'Receipt From'";
            }
            if (!isFloat(receiptTo)) {
                return "Please enter numeric value in field 'Receipt To'";
            }
            return "Validations successful";
        } catch (Exception e) {
            return "System error occurred";
        }
    }
    
    private boolean isFloat(String value) {
        try {
            Float.parseFloat(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private void disableValues() {
        disableReceiptFrom = true;
        disableReceiptTo = true;
    }
    
   
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
   
    public String getReceiptFrom() {
        return receiptFrom;
    }
    
    public void setReceiptFrom(String receiptFrom) {
        this.receiptFrom = receiptFrom;
    }
    
    public String getReceiptTo() {
        return receiptTo;
    }
    
    public void setReceiptTo(String receiptTo) {
        this.receiptTo = receiptTo;
    }
    
    public boolean isDisableDeleteButton() {
        return disableDeleteButton;
    }
    
    public void setDisableDeleteButton(boolean disableDeleteButton) {
        this.disableDeleteButton = disableDeleteButton;
    }
    
    public boolean isDisableSaveButton() {
        return disableSaveButton;
    }
    
    public void setDisableSaveButton(boolean disableSaveButton) {
        this.disableSaveButton = disableSaveButton;
    }
    
    public ReceiptMasterTable getCurrentRmt() {
        return currentRmt;
    }
    
    public void setCurrentRmt(ReceiptMasterTable currentRmt) {
        this.currentRmt = currentRmt;
    }
    
    public List<ReceiptMasterTable> getReceiptMasterTable() {
        return receiptMasterTable;
    }
    
    public void setReceiptMasterTable(List<ReceiptMasterTable> receiptMasterTable) {
        this.receiptMasterTable = receiptMasterTable;
    }
    
    public boolean isDisableReceiptFrom() {
        return disableReceiptFrom;
    }
    
    public void setDisableReceiptFrom(boolean disableReceiptFrom) {
        this.disableReceiptFrom = disableReceiptFrom;
    }
    
    public boolean isDisableReceiptTo() {
        return disableReceiptTo;
    }
    
    public void setDisableReceiptTo(boolean disableReceiptTo) {
        this.disableReceiptTo = disableReceiptTo;
    }
}
