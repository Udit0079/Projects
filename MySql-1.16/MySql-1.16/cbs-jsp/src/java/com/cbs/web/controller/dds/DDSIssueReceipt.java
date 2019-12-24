/**
 *
 * @author Navneet Goyal
 */
package com.cbs.web.controller.dds;

import com.cbs.dto.ReceiptMasterTable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.dds.DDSManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

public class DDSIssueReceipt {

    private HttpServletRequest request;
    private String todayDate;
    private String userName;
    private String message;
    private String message1;
    private String message2;
    private String branchCode;
    private List<SelectItem> branchCodeList = new ArrayList<SelectItem>();
    private String agentCode;
    private List<SelectItem> agentCodeList = new ArrayList<SelectItem>();
    private DDSManagementFacadeRemote receiptRemote;
    List<ReceiptMasterTable> receiptMasterTable1;
    ReceiptMasterTable currentRmt1;
    List<ReceiptMasterTable> receiptMasterTable2;
    ReceiptMasterTable currentRmt2;
    private String receiptFrom;
    private String receiptTo;
    private boolean disableBranchName;
    private boolean disableAgentName;
    private boolean saveDis;

    public DDSIssueReceipt() {
        try {
            receiptRemote = (DDSManagementFacadeRemote) ServiceLocator.getInstance().lookup("DDSManagementFacade");
            request = getRequest();
            Date dt = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(dt));
            setUserName(request.getRemoteUser());
            List resultList = receiptRemote.getBranchList();
            branchCodeList.add(new SelectItem("Select", "--Select--"));
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    SelectItem item = new SelectItem();
                    Vector ele = (Vector) resultList.get(i);
                    if (null != ele.get(0)) {
                        item.setValue(ele.get(0).toString());
                    }
                    if (null != ele.get(1)) {
                        item.setLabel(ele.get(1).toString());
                    }
                    branchCodeList.add(item);
                }
            }
            agentCodeList.add(new SelectItem("0", "--Select--"));
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
                String issueSeries = receiptRemote.issueSeries(agentCode, userName, Float.parseFloat(receiptFrom), Float.parseFloat(receiptTo));
                cancelAction();
                setMessage(issueSeries);
            } else {
                setMessage(validation);
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void deleteAction() {
        try {
            setMessage("");
            String validation = deleteValidation();
            if (validation.equalsIgnoreCase("Validations successful")) {
                String insertSeries = receiptRemote.revertSeries(Float.parseFloat(receiptFrom), Float.parseFloat(receiptTo));
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
            setMessage1("");
            setMessage2("");
            receiptMasterTable1 = receiptRemote.getAvailableReceipts();
            if (receiptMasterTable1.isEmpty()) {
                setMessage1("No receipts available for allocation !!");
            }
            receiptMasterTable2 = receiptRemote.getAllocatedReceipts();
            if (receiptMasterTable2.isEmpty()) {
                setMessage2("No receipts series are allocated !!");
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void cancelAction() {
        try {
            setMessage("");
            setMessage1("");
            setMessage2("");
            setBranchCode("Select");
            agentCodeList.clear();
            agentCodeList.add(new SelectItem("0", "--Select--"));
            setReceiptFrom("");
            setReceiptTo("");
            onLoad();
            setSaveDis(false);
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

    private String validation() {
        try {
            if (branchCode.equalsIgnoreCase("Select")) {
                return "Please select field 'Branch'";
            }
            if (agentCode.equalsIgnoreCase("0")) {
                return "Please select field 'Agent Name'";
            }
            return "Validations successful";
        } catch (Exception e) {
            return "System error occurred";
        }
    }

    private String deleteValidation() {
        try {
            if (agentCode.equalsIgnoreCase("0")) {
                return "Please select field 'Agent Name'";
            }
            return "Validations successful";
        } catch (Exception e) {
            return "System error occurred";
        }
    }

    public void onChangeBranch() {
        try {
            setMessage("");
            agentCodeList.clear();
            List resultList = receiptRemote.getAgentList(branchCode);
            agentCodeList.add(new SelectItem("0", "--Select--"));
            for (int i = 0; i < resultList.size(); i++) {
                SelectItem item = new SelectItem();
                Vector ele = (Vector) resultList.get(i);
                if (null != ele.get(0)) {
                    item.setValue(ele.get(0).toString());
                }
                if (null != ele.get(1)) {
                    item.setLabel(ele.get(1).toString());
                }
                agentCodeList.add(item);
            }
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    private void onLoad() {
        try {
            disableBranchName = false;
            disableAgentName = false;
            viewAction();
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void setRowValues() {
        try {
            setMessage("");
            NumberFormat format = new DecimalFormat("0");
            receiptFrom = format.format(currentRmt1.getReceiptFrom());
            receiptTo = format.format(currentRmt1.getReceiptTo());
            setSaveDis(false);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void setDeleteRowValues() {
        try {
            setMessage("");
            NumberFormat format = new DecimalFormat("0");
            setBranchCode("Select");
            agentCodeList.clear();
            agentCodeList.add(new SelectItem(currentRmt2.getAgCode(), currentRmt2.getAgName()));
            setAgentCode(currentRmt2.getAgCode());
            receiptFrom = format.format(currentRmt2.getReceiptFrom());
            receiptTo = format.format(currentRmt2.getReceiptTo());
            disableBranchName = true;
            disableAgentName = true;
            setSaveDis(true);
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return req;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ReceiptMasterTable getCurrentRmt1() {
        return currentRmt1;
    }

    public void setCurrentRmt1(ReceiptMasterTable currentRmt1) {
        this.currentRmt1 = currentRmt1;
    }

    public List<ReceiptMasterTable> getReceiptMasterTable1() {
        return receiptMasterTable1;
    }

    public void setReceiptMasterTable1(List<ReceiptMasterTable> receiptMasterTable1) {
        this.receiptMasterTable1 = receiptMasterTable1;
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

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public List<SelectItem> getAgentCodeList() {
        return agentCodeList;
    }

    public void setAgentCodeList(List<SelectItem> agentCodeList) {
        this.agentCodeList = agentCodeList;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }

    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
    }

    public ReceiptMasterTable getCurrentRmt2() {
        return currentRmt2;
    }

    public void setCurrentRmt2(ReceiptMasterTable currentRmt2) {
        this.currentRmt2 = currentRmt2;
    }

    public List<ReceiptMasterTable> getReceiptMasterTable2() {
        return receiptMasterTable2;
    }

    public void setReceiptMasterTable2(List<ReceiptMasterTable> receiptMasterTable2) {
        this.receiptMasterTable2 = receiptMasterTable2;
    }

    public boolean isDisableAgentName() {
        return disableAgentName;
    }

    public void setDisableAgentName(boolean disableAgentName) {
        this.disableAgentName = disableAgentName;
    }

    public boolean isDisableBranchName() {
        return disableBranchName;
    }

    public void setDisableBranchName(boolean disableBranchName) {
        this.disableBranchName = disableBranchName;
    }

    public String getMessage1() {
        return message1;
    }

    public void setMessage1(String message1) {
        this.message1 = message1;
    }

    public String getMessage2() {
        return message2;
    }

    public void setMessage2(String message2) {
        this.message2 = message2;
    }

    public boolean isSaveDis() {
        return saveDis;
    }

    public void setSaveDis(boolean saveDis) {
        this.saveDis = saveDis;
    }
}
