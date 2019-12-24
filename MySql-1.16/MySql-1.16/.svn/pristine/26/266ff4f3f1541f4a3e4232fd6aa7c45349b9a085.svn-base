/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.deadstock;

import cbs.ho.deadstock.tables.DeadStockDepreciation;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.deadstock.DeadstockFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.ReportBean;
import com.hrms.web.utils.WebUtil;
import java.io.Serializable;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Himanshu
 */
public class DepreciationApply implements Serializable {

    private String orgnBrCode;
    private String userName;
    private String todayDate;
    private String message;
    private String itemGroup;
    private String branch;
    private String itemCodeString;
    private String itemDistinctNo;
    private List<SelectItem> itemGroupList;
    private List<SelectItem> branchList;
    private List<SelectItem> itemList;
    private List<SelectItem> itemDistinctNoList;
    private String itemOption;
    private List<SelectItem> itemOptionList;
    private boolean disableItemDisNo;
    private HttpServletRequest req;
    private List<DeadStockDepreciation> dataGrid;
    DeadstockFacadeRemote remote;
    private CommonReportMethodsRemote common;
    Date date = new Date();
    private String frmDt;
    private String toDt;
    private String viewID = "/pages/master/sm/test.jsp";
    private boolean disableBtn;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private FtsPostingMgmtFacadeRemote fts;

    public DepreciationApply() {
        req = getRequest();
        try {
            remote = (DeadstockFacadeRemote) ServiceLocator.getInstance().lookup("DeadstockFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            fts = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            this.setUserName(req.getRemoteUser());
            this.setTodayDate(sdf.format(date));
            itemOptionList = new ArrayList<>();
            itemOptionList.add(new SelectItem("0", "--Select--"));
            itemOptionList.add(new SelectItem("A", "All Item"));
            itemOptionList.add(new SelectItem("I", "Individul Item"));
        } catch (Exception ex) {
            setMessage(ex.getLocalizedMessage());
        }
        initData();
    }

    public void initData() {
        itemGroupList = new ArrayList<SelectItem>();
        branchList = new ArrayList<SelectItem>();
        itemList = new ArrayList<SelectItem>();
        itemList.add(new SelectItem("00", "--Select--"));
        branchList.add(new SelectItem("sl", "--Select--"));
        itemGroupList.add(new SelectItem("00", "--Select--"));
        itemDistinctNoList = new ArrayList<SelectItem>();
        itemDistinctNoList.add(new SelectItem("00", "--Select--"));
        try {
            List resultBranch = remote.getBrachList();
            List resultGroup = remote.getDepreciationGroupCodde();
            dataGrid = new ArrayList<DeadStockDepreciation>();
            if (resultBranch != null) {
                for (int i = 0; i < resultBranch.size(); i++) {
                    Vector vtr = (Vector) resultBranch.get(i);
                    branchList.add(new SelectItem(vtr.get(1).toString().length() == 1 ? "0" + vtr.get(1).toString() : vtr.get(1).toString(), vtr.get(0).toString()));
                }
            }
            if (resultGroup != null) {
                for (int i = 0; i < resultGroup.size(); i++) {
                    Vector vtr = (Vector) resultGroup.get(i);
                    itemGroupList.add(new SelectItem(vtr.get(1).toString(), vtr.get(0).toString()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String getItemDistinctNo() {
        return itemDistinctNo;
    }

    public void setItemDistinctNo(String itemDistinctNo) {
        this.itemDistinctNo = itemDistinctNo;
    }

    public List<SelectItem> getItemDistinctNoList() {
        return itemDistinctNoList;
    }

    public void setItemDistinctNoList(List<SelectItem> itemDistinctNoList) {
        this.itemDistinctNoList = itemDistinctNoList;
    }

    public List<DeadStockDepreciation> getDataGrid() {
        return dataGrid;
    }

    public void setDataGrid(List<DeadStockDepreciation> dataGrid) {
        this.dataGrid = dataGrid;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public List<SelectItem> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<SelectItem> branchList) {
        this.branchList = branchList;
    }

    public String getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(String itemGroup) {
        this.itemGroup = itemGroup;
    }

    public List<SelectItem> getItemGroupList() {
        return itemGroupList;
    }

    public void setItemGroupList(List<SelectItem> itemGroupList) {
        this.itemGroupList = itemGroupList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
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

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public List<SelectItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SelectItem> itemList) {
        this.itemList = itemList;
    }

    public String getItemCodeString() {
        return itemCodeString;
    }

    public void setItemCodeString(String itemCodeString) {
        this.itemCodeString = itemCodeString;
    }

    public String getItemOption() {
        return itemOption;
    }

    public void setItemOption(String itemOption) {
        this.itemOption = itemOption;
    }

    public List<SelectItem> getItemOptionList() {
        return itemOptionList;
    }

    public void setItemOptionList(List<SelectItem> itemOptionList) {
        this.itemOptionList = itemOptionList;
    }

    public boolean isDisableItemDisNo() {
        return disableItemDisNo;
    }

    public void setDisableItemDisNo(boolean disableItemDisNo) {
        this.disableItemDisNo = disableItemDisNo;
    }

    public String getViewID() {
        return viewID;
    }

    public void setViewID(String viewID) {
        this.viewID = viewID;
    }

    public boolean isDisableBtn() {
        return disableBtn;
    }

    public void setDisableBtn(boolean disableBtn) {
        this.disableBtn = disableBtn;
    }

    public String getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(String frmDt) {
        this.frmDt = frmDt;
    }

    public String getToDt() {
        return toDt;
    }

    public void setToDt(String toDt) {
        this.toDt = toDt;
    }

    public FtsPostingMgmtFacadeRemote getFts() {
        return fts;
    }

    public void setFts(FtsPostingMgmtFacadeRemote fts) {
        this.fts = fts;
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;

    }

    public void calculateDepreciation() {
        dataGrid.clear();
        try {
            this.disableBtn = false;
            if (itemOption == null || itemOption.equalsIgnoreCase("0")) {
                setMessage("Please select Item Option !");
                return;
            }
            List result = remote.getDpriciation(itemGroup, itemCodeString, branch, Integer.parseInt(itemDistinctNo), itemOption, ymd.format(sdf.parse(frmDt)), ymd.format(sdf.parse(toDt)));
            if (result.isEmpty()) {
                throw new ApplicationException("There is no Data for calculation !");
            }
            for (int i = 0; i < result.size(); i++) {
                DeadStockDepreciation row = new DeadStockDepreciation();
                Vector vtr = (Vector) result.get(i);
                if (Double.parseDouble(vtr.get(2).toString()) > 0) {
                    row.setBookValue(Double.parseDouble(vtr.get(4).toString()));
                    row.setDepAmt(Double.parseDouble(vtr.get(2).toString()));
                    row.setDepPre(Double.parseDouble(vtr.get(3).toString()));
                    row.setItemdstncno(vtr.get(1).toString());
                    row.setItemCode(vtr.get(5).toString());
                    row.setOrignalCost(Double.parseDouble(vtr.get(0).toString()));
                    row.setItemName(vtr.get(6).toString());
                    row.setDepAmtPrevious(Double.parseDouble(vtr.get(7).toString()));
                    dataGrid.add(row);
                }
            }

            Map fillParams = new HashMap();
            List brNameAndAddList = common.getBranchNameandAddress(getOrgnBrCode());
            fillParams.put("pBankName", brNameAndAddList.get(0).toString());
            fillParams.put("pBankAddress", brNameAndAddList.get(1).toString());
            fillParams.put("pPrintedBy", getUserName());
            fillParams.put("pReportDt", sdf.format(this.date));
            fillParams.put("pReportName", "Depreciation Calculation Report");

            new ReportBean().jasperReport("DepreciationCalReport", "text/html", new JRBeanCollectionDataSource(dataGrid), fillParams, "Depreciation Calculation Report");
            this.disableBtn = true;
            this.setViewID("/report/ReportPagePopUp.jsp");


        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void dataofItem() {
        itemList.clear();
        message = "";
        itemList.add(new SelectItem("00", "--Select--"));
        try {
            List result = remote.getItemCodeList(branch, itemGroup);
            if (result != null) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    itemList.add(new SelectItem(vtr.get(0), vtr.get(1).toString()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }

    }

    public void setFromToDate() {
        setMessage("");
        try {
            List frDtList = remote.getFrToDateByItemGroup(branch, itemGroup);
            if (!frDtList.isEmpty()) {
                Vector vtr = (Vector) frDtList.get(0);
                frmDt = sdf.format(ymd.parse(CbsUtil.dateAdd(vtr.get(0).toString(), 1)));
            }
            List toList = fts.getCurrentFinYear(getOrgnBrCode());
            Vector vtr = (Vector) toList.get(0);
            toDt = sdf.format(ymd.parse(vtr.get(1).toString()));
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void postData() {
        try {
            List<Vector> dataTable = new ArrayList<Vector>();
            if (dataGrid.isEmpty()) {
                throw new ApplicationException("There is no Data for post !");
            }
            for (DeadStockDepreciation dd : dataGrid) {
                Vector vtr1 = new Vector();
                vtr1.add(0, dd.getItemCode());
                vtr1.add(1, dd.getItemdstncno());
                vtr1.add(2, dd.getDepAmt());
                vtr1.add(3, dd.getDepPre());
                vtr1.add(4, dd.getBookValue() - dd.getDepAmt());
                vtr1.add(5, dd.getOrignalCost());
                vtr1.add(6, itemGroup);
                vtr1.add(7, userName);
                vtr1.add(8, branch);
                vtr1.add(9, dd.getDepAmtPrevious());
                dataTable.add(vtr1);
            }
            message = remote.postDepreciation(dataTable, ymd.format(sdf.parse(frmDt)), ymd.format(sdf.parse(toDt)));
            refresh();

        } catch (Exception e) {
            setMessage(e.getMessage());
        }

    }

    public void itemOptionAction() {
        setMessage("");
        try {
            if (itemOption == null || itemOption.equalsIgnoreCase("0")) {
                setMessage("Please select Item Option !");
                return;
            }
            if (itemOption.equalsIgnoreCase("A")) {
                this.disableItemDisNo = true;
            } else {
                this.disableItemDisNo = false;
            }

        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void distinctNo() {
        try {
            message = "";
            itemDistinctNoList.clear();
            itemDistinctNoList.add(new SelectItem("00", "--Select--"));
            List result = remote.getDistinctNo(branch, itemGroup, Integer.parseInt(itemCodeString));
            if (result != null) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    itemDistinctNoList.add(new SelectItem(vtr.get(0), vtr.get(0).toString()));
                }
            }
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void refresh() {
        itemGroup = "00";
        branch = "sl";
        itemDistinctNo = "00";
        itemCodeString = "00";
        if (dataGrid != null) {
            dataGrid.clear();
        }
        this.disableItemDisNo = false;
        this.itemOption = "0";
        this.disableBtn = false;
    }

    public void refreshData() {
        refresh();
        message = "";
    }

    public String exitAction() {
        refreshData();
        return "case1";
    }
}
