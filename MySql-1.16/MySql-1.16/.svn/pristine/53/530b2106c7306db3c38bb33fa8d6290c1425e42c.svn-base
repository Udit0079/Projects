package com.cbs.web.controller.ho.deadstock;

import com.cbs.exception.ApplicationException;
import com.cbs.web.pojo.ho.DeadstockAuthorizationTable;
import com.cbs.facade.ho.deadstock.DeadstockAuthorizationFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

/**
 *
 * @author Ankit Verma
 */
public final class DeadstockAuthorization extends BaseBean {

    private String msg;
    private double crAmount;
    private double drAmount;
    private String authModeCombo;
    private int flag;
    private int ty;
    private String batchNo;
    String itemName;
    
    private List<DeadstockAuthorizationTable> datagrid1;
    private List<DeadstockAuthorizationTable> selectedItemList;
    DeadstockAuthorizationFacadeRemote daRemoteObj;
    private List<SelectItem> authModeList;
    private String authMode;
    private List resultList;
    private boolean disableAuthorizeBtn;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public DeadstockAuthorization() {
        try {
            daRemoteObj = (DeadstockAuthorizationFacadeRemote) ServiceLocator.getInstance().lookup("DeadstockAuthorizationFacade");
            authModeList = new ArrayList<SelectItem>();
            authModeList.add(new SelectItem("Transaction"));
            authModeList.add(new SelectItem("Deadstock"));
            authModeList.add(new SelectItem("Stationary"));
            disableAuthorizeBtn = true;
            flag = 2;
            authMode = "TRANSACTION";
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public int getTy() {
        return ty;
    }

    public void setTy(int ty) {
        this.ty = ty;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public List<DeadstockAuthorizationTable> getDatagrid1() {
        return datagrid1;
    }

    public void setDatagrid1(List<DeadstockAuthorizationTable> datagrid1) {
        this.datagrid1 = datagrid1;
    }

    public String getAuthMode() {
        return authMode;
    }

    public void setAuthMode(String authMode) {
        this.authMode = authMode;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getAuthModeCombo() {
        return authModeCombo;
    }

    public void setAuthModeCombo(String authModeCombo) {
        this.authModeCombo = authModeCombo;
    }

    public List<SelectItem> getAuthModeList() {
        return authModeList;
    }

    public void setAuthModeList(List<SelectItem> authModeList) {
        this.authModeList = authModeList;
    }

    public double getCrAmount() {
        return crAmount;
    }

    public void setCrAmount(double crAmount) {
        this.crAmount = crAmount;
    }

    public double getDrAmount() {
        return drAmount;
    }

    public void setDrAmount(double drAmount) {
        this.drAmount = drAmount;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public boolean isDisableAuthorizeBtn() {
        return disableAuthorizeBtn;
    }

    public void setDisableAuthorizeBtn(boolean disableAuthorizeBtn) {
        this.disableAuthorizeBtn = disableAuthorizeBtn;
    }

    public void chooseTable() {
        try {
            if (authModeCombo.equalsIgnoreCase("Deadstock")) {
                flag = 1;
                authMode = "DEADSTOCK MAINTENANCE (ISSUE /SELL /SCRAP)";
            } else if (authModeCombo.equalsIgnoreCase("Transaction")) {
                flag = 2;
                authMode = "TRANSACTION";
                viewDataInGrid();
            } else if (authModeCombo.equalsIgnoreCase("Stationary")) {
                flag = 3;
                authMode = "STATIONARY";
            }
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void viewDataInGrid() {
        try {
            datagrid1 = new ArrayList<DeadstockAuthorizationTable>();
            resultList = daRemoteObj.viewDataInGrid();
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    DeadstockAuthorizationTable grid = new DeadstockAuthorizationTable();
                    Vector ele = (Vector) resultList.get(i);
                    if (ele.get(0) != null) {
                        grid.setTrsno(Integer.parseInt(ele.get(0).toString()));
                    }
                    if (ele.get(1) != null) {
                        grid.setAcno(ele.get(1).toString());
                    }
                    if (ele.get(2) != null) {
                        grid.setCustName(ele.get(2).toString());
                    }
                    if (ele.get(3) != null) {
                        grid.setCrAmt(Float.parseFloat(ele.get(3).toString()));
                    }
                    if (ele.get(4) != null) {
                        grid.setDrAmt(Float.parseFloat(ele.get(4).toString()));
                    }
                    if (ele.get(5) != null) {
                        grid.setInstNo(ele.get(5).toString());
                    }
                    if (ele.get(6) != null) {
                        grid.setEnterBy(ele.get(6).toString());
                    }
                    if (ele.get(7) != null) {
                        grid.setAuth(ele.get(7).toString());
                    }
                    if (ele.get(8) != null) {
                        grid.setDetails(ele.get(8).toString());
                    }
                    if (ele.get(9) != null) {
                        grid.setRecNo(Float.parseFloat(ele.get(9).toString()));
                    }
                    if (ele.get(10) != null) {
                        grid.setDt(ele.get(10).toString().substring(0, 10));
                    }
                    if (ele.get(11) != null) {
                        grid.setDestBrnId(ele.get(11).toString());
                    }
                    if (ele.get(12) != null) {
                        grid.setOrgBrnId(ele.get(12).toString());
                    }
                    if (ele.get(13) != null) {
                        grid.setTy(Integer.parseInt(ele.get(13).toString()));
                    }
                    if (ele.get(14) != null) {
                        itemName = ele.get(14).toString();
                    }
                    datagrid1.add(grid);
                }
            }
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void setSelectedBatch() {
        try {
            msg = "";
            if (this.batchNo.equals("") || this.batchNo == null) {
                this.setMsg("Please enter a batch number for selecting that batch for authorization.");
                this.setCrAmount(0.0f);
                this.setDrAmount(0.0f);
                return;
            }
            if (!this.batchNo.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                this.setMsg("Please enter numeric value in Batch No.");
                this.setCrAmount(0.0f);
                this.setDrAmount(0.0f);
                return;
            }
            if (checkDrCrBalance() == false) {
                this.setMsg("Batch No. " + this.getBatchNo() + " Is Incomplete");
                this.setCrAmount(0.0f);
                this.setDrAmount(0.0f);
                return;
            }
            this.setMsg("");
            int count = 0;
            selectedItemList = new ArrayList<DeadstockAuthorizationTable>();
            for (DeadstockAuthorizationTable authorizationTableItem : datagrid1) {
                if (authorizationTableItem.getTrsno() == Integer.parseInt(batchNo)) {
                    selectedItemList.add(authorizationTableItem);
                    msg = "";
                    disableAuthorizeBtn = false;
                    ++count;
                }
            }
            if (count == 0) {
                msg = "Batch No. does not exist in above table";
            }
            if (selectedItemList.isEmpty()) {
                this.setCrAmount(0.0);
                this.setDrAmount(0.0);
            }
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public boolean checkDrCrBalance() {
        try {
            double crAmt = 0.0;
            double drAmt = 0.0;
            selectedItemList = new ArrayList<DeadstockAuthorizationTable>();
            for (DeadstockAuthorizationTable authorizationTableItem : datagrid1) {
                if (authorizationTableItem.getTrsno() == Integer.parseInt(batchNo)) {
                    selectedItemList.add(authorizationTableItem);
                }
            }
            if (!selectedItemList.isEmpty()) {
                for (int i = 0; i < selectedItemList.size(); i++) {
                    ty = selectedItemList.get(i).getTy();
                    if (ty == 0) {
                        crAmt = crAmt + selectedItemList.get(i).getCrAmt();
                    } else if (ty == 1) {
                        drAmt = drAmt + selectedItemList.get(i).getDrAmt();
                    }
                }
                this.setCrAmount(crAmt);
                this.setDrAmount(drAmt);
            }
            return true;
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
            return false;
        }
    }

    public void authorizeRecords() {
        if (selectedItemList.size() > 0) {
            try {
                String dt = ymd.format(dmy.parse(getTodayDate()));
                int trSNo = Integer.parseInt(this.batchNo);
                String AuthBy = getUserName();
                String result = daRemoteObj.authorizeRecords(trSNo, AuthBy, dt, getOrgnBrCode(), itemName);
                System.out.println("result" + result);
                if (result.substring(0, 4).equalsIgnoreCase("true")) {
                    this.setMsg("Authorizaton Over");
                    if (selectedItemList.size() > 0) {
                        selectedItemList.clear();
                    }
                    this.setBatchNo("");
                } else {
                    this.setMsg(result);
                }
                viewDataInGrid();
                refresh();
                batchNo = "";
            } catch (ApplicationException e) {
                setMsg(e.getLocalizedMessage());
            } catch (Exception e) {
                setMsg(e.getLocalizedMessage());
            }
        } else {
            this.setMsg("Please select at least one batch for authorization");
        }
    }

    public void refreshPage() {
        msg = "";
        if(datagrid1!=null)
        {
            datagrid1.clear();
        }
        refresh();
    }

    public void refresh() {
        authMode = "TRANSACTION";
        batchNo = "";
        crAmount = 0.00;
        drAmount = 0.00;
        disableAuthorizeBtn = true;
    }

    public String exitAction() {
        refresh();
        return "case1";
    }
}
