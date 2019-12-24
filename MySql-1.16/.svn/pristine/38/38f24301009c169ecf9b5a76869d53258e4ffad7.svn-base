/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ib;

import com.cbs.dto.ib.IbRequestTo;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ib.IbRequestMgmtFacadeRemote;
import com.cbs.facade.inventory.InventorySplitAndMergeFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.inventory.IssueCheqbook;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

public class IbRequestProcess extends BaseBean {

    private String message;
    private String requestType;
    private String action;
    private String chqFrom;
    private String chqTo;
    private String noOfLeaves;
    private String deliveryAgency;
    private String deliveryMode;
    private String rescheduleReason;
    private String stAccountNo;
    private String stInvtType;
    private String stPartyName;
    private String stRequestStatus;
    private String stCbsRequestDt;
    private String chequeSeries;
    private String rescheduleDisplay = "none";
    private String chqBookDisplay = "none";
    private IssueCheqbook currentItem;
    private IbRequestTo rqCurrentItem;
    private List<IssueCheqbook> stocktable;
    private List<IbRequestTo> requestTable;
    private List<SelectItem> requestTypeList;
    private List<SelectItem> actionList;
    private List<SelectItem> deliveryModeList;
    private List<SelectItem> chequeSeriesList;
    private FtsPostingMgmtFacadeRemote ftsRemote;
    private CommonReportMethodsRemote commonReport;
    private IbRequestMgmtFacadeRemote ibRemote;
    private InventorySplitAndMergeFacadeRemote invtRemote;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public IbRequestProcess() {
        try {
            ftsRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            commonReport = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            ibRemote = (IbRequestMgmtFacadeRemote) ServiceLocator.getInstance().lookup("IbRequestMgmtFacade");
            invtRemote = (InventorySplitAndMergeFacadeRemote) ServiceLocator.getInstance().lookup("InventorySplitAndMergeFacade");
            initData();
            this.setMessage("Please select Request Type.");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void initData() {
        try {
            requestTypeList = new ArrayList<SelectItem>();
            requestTypeList.add(new SelectItem("0", "--Select--"));
            actionList = new ArrayList<SelectItem>();
            deliveryModeList = new ArrayList<SelectItem>();
            deliveryModeList.add(new SelectItem("0", "--Select--"));
            chequeSeriesList = new ArrayList<SelectItem>();
            chequeSeriesList.add(new SelectItem("0", "--Select--"));

            List list = commonReport.getRefRecList("316");
            if (list.isEmpty()) {
                this.setMessage("Please define data for reference no:316");
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                requestTypeList.add(new SelectItem(ele.get(0).toString().trim(), ele.get(1).toString().trim()));
            }

            list = commonReport.getRefRecList("317");
            if (list.isEmpty()) {
                this.setMessage("Please define data for reference no:317");
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                deliveryModeList.add(new SelectItem(ele.get(0).toString().trim(), ele.get(1).toString().trim()));
            }

            actionList.add(new SelectItem("0", "--Select--"));
            actionList.add(new SelectItem("P", "Process"));
            actionList.add(new SelectItem("R", "Reschedule"));
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void requestTypeAction() {
        try {
            if (this.requestType == null || this.requestType.equals("0")) {
                this.setMessage("Please select request type.");
                return;
            }
            requestTable = ibRemote.getIbRequestForBranch(requestType, "NEW",
                    getOrgnBrCode(), ymd.format(dmy.parse(getTodayDate())));
            if (requestTable.isEmpty()) {
                this.setMessage("There is no data.");
            } else {
                this.setMessage("Please select a row from table to process.");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getTableStock() {
        this.setMessage("");
        try {
            if (this.requestType == null || this.requestType.equals("0")) {
                this.setMessage("Please select request type.");
                return;
            }
            if (this.action == null || this.action.equals("0")) {
                this.setMessage("Please select action.");
                return;
            }
            if (this.chequeSeries == null || this.chequeSeries.equals("0")) {
                this.setMessage("There should be proper cheque series for inventory type.");
                return;
            }
            List list = invtRemote.gridLoadForStockDetail(getOrgnBrCode(), rqCurrentItem.getInvtClass(),
                    this.stInvtType, this.chequeSeries);
            if (list.isEmpty()) {
                this.setMessage("Inventory does not exist to issue.");
                return;
            }
            stocktable = new ArrayList<IssueCheqbook>();
            int j = 1;
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                IssueCheqbook rd = new IssueCheqbook();
                rd.setChNofrom(ele.get(0).toString());
                rd.setChNoTo(ele.get(1).toString());
                rd.setStockDt(ele.get(2).toString());
                rd.setSno(j++);
                stocktable.add(rd);
            }
            this.setMessage("Now you can select a row from detail of inventory table to issue.");
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void selectInventory() {
        this.setChqFrom(currentItem.getChNofrom());
        this.setChqTo(currentItem.getChNoTo());
        int result = (Integer.parseInt(chqTo) - Integer.parseInt(chqFrom)) + 1;
        this.setNoOfLeaves(Integer.toString(result));
    }

    public void selectRequestFromTable() {
        this.setMessage("");
        try {
            if (rqCurrentItem == null) {
                this.setMessage("Please select a row from table to process.");
                return;
            }
            fieldRefreshOnRqObj();
            this.setStAccountNo(rqCurrentItem.getAcno());
            this.setStInvtType(rqCurrentItem.getInvtType());
            this.setStPartyName(ftsRemote.getCustName(rqCurrentItem.getAcno()));
            this.setStRequestStatus(rqCurrentItem.getRequestStatus());
            this.setStCbsRequestDt(rqCurrentItem.getCbsRequestDt());
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void processAction() {
        this.setMessage("");
        try {
            if (this.requestType == null || this.requestType.equals("0")) {
                this.setMessage("Please select request type.");
                return;
            }
            if (this.action == null || this.action.equals("0")) {
                this.setMessage("Please select action.");
                return;
            }
            this.chqBookDisplay = "none";
            this.rescheduleDisplay = "none";
            fieldRefreshOnProcess();
            if (this.action.equals("P") && this.requestType.equalsIgnoreCase("CHEQUE BOOK")) {
                this.chqBookDisplay = "";
                if (this.stInvtType == null || this.stInvtType.equals("")
                        || !this.stInvtType.equalsIgnoreCase(rqCurrentItem.getInvtType())) {
                    this.setMessage("There should be proper inventory type.");
                    return;
                }
                List list = invtRemote.chequeSeriesCombo(getOrgnBrCode(), rqCurrentItem.getInvtClass(), this.stInvtType);
                if (list.isEmpty()) {
                    this.setMessage("No cheque series available corresponding to this inventory type.");
                    return;
                }
                chequeSeriesList = new ArrayList<SelectItem>();
                chequeSeriesList.add(new SelectItem("0", "--Select--"));
                for (int i = 0; i < list.size(); i++) {
                    Vector ele = (Vector) list.get(i);
                    chequeSeriesList.add(new SelectItem(ele.get(0).toString()));
                }
            } else if (this.action.equals("R") && this.requestType.equalsIgnoreCase("CHEQUE BOOK")) {
                this.rescheduleDisplay = "";
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void formProcessing() {
        try {
            String prRequsetType = "";
            if (this.action.equals("P") && this.requestType.equalsIgnoreCase("CHEQUE BOOK")) {
                if (chqIssueValidation()) {
                    prRequsetType = this.requestType;
                    String msg = ibRemote.processChequeBookIssue(stAccountNo, Long.parseLong(this.chqFrom),
                            Long.parseLong(this.chqTo), this.chequeSeries, Long.parseLong(this.noOfLeaves),
                            "CHQ", this.stInvtType, getUserName(), ymd.format(dmy.parse(getTodayDate())),
                            getOrgnBrCode(), rqCurrentItem.getRequestNo(), rqCurrentItem.getRequestBreakNo());
                    if (!msg.equalsIgnoreCase("true")) {
                        this.setMessage(msg);
                        return;
                    }
                    btnRefresh();
                    this.setRequestType(prRequsetType);
                    this.setMessage("Cheque has been issued successfully.");
                }
            } else if (this.action.equals("R") && this.requestType.equalsIgnoreCase("CHEQUE BOOK")) {
                this.rescheduleDisplay = "";
                if (this.rescheduleReason == null || this.rescheduleReason.equals("")) {
                    this.setMessage("Please fill Reason.");
                    return;
                }
                prRequsetType = this.requestType;
                String msg = ibRemote.reScheduleProcess(ymd.format(dmy.parse(getTodayDate())), this.rescheduleReason,
                        getUserName(), rqCurrentItem.getRequestNo(), rqCurrentItem.getRequestBreakNo(), getOrgnBrCode());
                if (!msg.equalsIgnoreCase("true")) {
                    this.setMessage(msg);
                    return;
                }
                btnRefresh();
                this.setRequestType(prRequsetType);
                this.setMessage("Reschedule process has been done.");
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public boolean chqIssueValidation() {
        if (this.requestType == null || this.requestType.equals("0")) {
            this.setMessage("Please select request type.");
            return false;
        }
        if (this.action == null || this.action.equals("0")) {
            this.setMessage("Please select action.");
            return false;
        }
        if (this.chequeSeries == null || this.chequeSeries.equals("0")) {
            this.setMessage("There should be proper cheque series for inventory type.");
            return false;
        }
        if (this.chqFrom == null || this.chqFrom.equals("")) {
            this.setMessage("There should be proper Chq. No. From.");
            return false;
        }
        if (this.chqTo == null || this.chqTo.equals("")) {
            this.setMessage("There should be proper Chq. No. To.");
            return false;
        }
        if (this.noOfLeaves == null || this.noOfLeaves.equals("")) {
            this.setMessage("There should be proper No.Of Leaves.");
            return false;
        }
        if (this.stAccountNo == null || this.stAccountNo.equals("")) {
            this.setMessage("There should be proper a/c no.");
            return false;
        }
        if (this.stInvtType == null || this.stInvtType.equals("")) {
            this.setMessage("There should be proper inventory type.");
            return false;
        }
        return true;
    }

    public void fieldRefreshOnProcess() {
        this.setChequeSeries("0");
        this.setChqFrom("");
        this.setChqTo("");
        this.setNoOfLeaves("");
        this.setDeliveryAgency("");
        this.setDeliveryMode("0");
        this.rescheduleReason = "";
        this.currentItem = null;
        this.stocktable = new ArrayList<IssueCheqbook>();
    }

    public void fieldRefreshOnRqObj() {
        this.setMessage("");
        this.setAction("0");
        this.stAccountNo = "";
        this.stInvtType = "";
        this.stPartyName = "";
        this.stRequestStatus = "";
        this.stCbsRequestDt = "";
    }

    public void btnRefresh() {
        this.setMessage("");
        this.setRequestType("0");
        this.setAction("0");
        this.setChequeSeries("0");
        this.setChqFrom("");
        this.setChqTo("");
        this.setNoOfLeaves("");
        this.setDeliveryAgency("");
        this.setDeliveryMode("0");
        this.rescheduleReason = "";
        this.stAccountNo = "";
        this.stInvtType = "";
        this.stPartyName = "";
        this.stRequestStatus = "";
        this.stCbsRequestDt = "";
        this.chqBookDisplay = "none";
        this.rescheduleDisplay = "none";
        this.currentItem = null;
        this.rqCurrentItem = null;
        this.stocktable = new ArrayList<IssueCheqbook>();
        this.requestTable = new ArrayList<IbRequestTo>();
        this.setMessage("Please select Request Type.");
//        this.requestTypeList = new ArrayList<SelectItem>();
//        this.actionList = new ArrayList<SelectItem>();
//        this.deliveryModeList = new ArrayList<SelectItem>();
    }

    public String btnExit() {
        btnRefresh();
        return "case1";
    }

    //Getter And Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getChqFrom() {
        return chqFrom;
    }

    public void setChqFrom(String chqFrom) {
        this.chqFrom = chqFrom;
    }

    public String getChqTo() {
        return chqTo;
    }

    public void setChqTo(String chqTo) {
        this.chqTo = chqTo;
    }

    public String getNoOfLeaves() {
        return noOfLeaves;
    }

    public void setNoOfLeaves(String noOfLeaves) {
        this.noOfLeaves = noOfLeaves;
    }

    public String getDeliveryAgency() {
        return deliveryAgency;
    }

    public void setDeliveryAgency(String deliveryAgency) {
        this.deliveryAgency = deliveryAgency;
    }

    public String getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(String deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public String getRescheduleReason() {
        return rescheduleReason;
    }

    public void setRescheduleReason(String rescheduleReason) {
        this.rescheduleReason = rescheduleReason;
    }

    public String getStAccountNo() {
        return stAccountNo;
    }

    public void setStAccountNo(String stAccountNo) {
        this.stAccountNo = stAccountNo;
    }

    public String getStInvtType() {
        return stInvtType;
    }

    public void setStInvtType(String stInvtType) {
        this.stInvtType = stInvtType;
    }

    public String getStPartyName() {
        return stPartyName;
    }

    public void setStPartyName(String stPartyName) {
        this.stPartyName = stPartyName;
    }

    public String getStRequestStatus() {
        return stRequestStatus;
    }

    public void setStRequestStatus(String stRequestStatus) {
        this.stRequestStatus = stRequestStatus;
    }

    public String getStCbsRequestDt() {
        return stCbsRequestDt;
    }

    public void setStCbsRequestDt(String stCbsRequestDt) {
        this.stCbsRequestDt = stCbsRequestDt;
    }

    public String getRescheduleDisplay() {
        return rescheduleDisplay;
    }

    public void setRescheduleDisplay(String rescheduleDisplay) {
        this.rescheduleDisplay = rescheduleDisplay;
    }

    public IssueCheqbook getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(IssueCheqbook currentItem) {
        this.currentItem = currentItem;
    }

    public IbRequestTo getRqCurrentItem() {
        return rqCurrentItem;
    }

    public void setRqCurrentItem(IbRequestTo rqCurrentItem) {
        this.rqCurrentItem = rqCurrentItem;
    }

    public List<IssueCheqbook> getStocktable() {
        return stocktable;
    }

    public void setStocktable(List<IssueCheqbook> stocktable) {
        this.stocktable = stocktable;
    }

    public List<IbRequestTo> getRequestTable() {
        return requestTable;
    }

    public void setRequestTable(List<IbRequestTo> requestTable) {
        this.requestTable = requestTable;
    }

    public List<SelectItem> getRequestTypeList() {
        return requestTypeList;
    }

    public void setRequestTypeList(List<SelectItem> requestTypeList) {
        this.requestTypeList = requestTypeList;
    }

    public List<SelectItem> getActionList() {
        return actionList;
    }

    public void setActionList(List<SelectItem> actionList) {
        this.actionList = actionList;
    }

    public List<SelectItem> getDeliveryModeList() {
        return deliveryModeList;
    }

    public void setDeliveryModeList(List<SelectItem> deliveryModeList) {
        this.deliveryModeList = deliveryModeList;
    }

    public String getChequeSeries() {
        return chequeSeries;
    }

    public void setChequeSeries(String chequeSeries) {
        this.chequeSeries = chequeSeries;
    }

    public String getChqBookDisplay() {
        return chqBookDisplay;
    }

    public void setChqBookDisplay(String chqBookDisplay) {
        this.chqBookDisplay = chqBookDisplay;
    }

    public List<SelectItem> getChequeSeriesList() {
        return chequeSeriesList;
    }

    public void setChequeSeriesList(List<SelectItem> chequeSeriesList) {
        this.chequeSeriesList = chequeSeriesList;
    }
}
