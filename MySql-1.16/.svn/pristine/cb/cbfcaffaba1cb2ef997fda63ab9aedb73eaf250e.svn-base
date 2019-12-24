/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.master;

import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.ho.master.CrrDailyEntryFacadeRemote;
import com.cbs.facade.ho.master.DailyMasterFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.ho.CrrDailyUpdationMasterGrid;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

public class CrrDailyUpdationMaster extends BaseBean {

    private String message;
    private String accDescType;
    private String function;
    private String glHeadFrom;
    private String glHeadTO;
    private String newGlheadFrom;
    private String newGlheadTo;
    private String gridAcDesc;
    private String gridFacno;
    private String gridTacno;
    private String btnValue;
    private boolean enableProcessBtn;
    private boolean frTxtEnable;
    private boolean toTxtEnable;
    private List<SelectItem> acctDescOption;
    private List<SelectItem> functionList;
    private CrrDailyUpdationMasterGrid authorized;
    private CrrDailyUpdationMasterGrid table;
    private List<CrrDailyUpdationMasterGrid> tableList;
    private CrrDailyEntryFacadeRemote rfr = null;
    private DailyMasterFacadeRemote dailyBean = null;

    public boolean isFrTxtEnable() {
        return frTxtEnable;
    }

    public void setFrTxtEnable(boolean frTxtEnable) {
        this.frTxtEnable = frTxtEnable;
    }

    public boolean isToTxtEnable() {
        return toTxtEnable;
    }

    public void setToTxtEnable(boolean toTxtEnable) {
        this.toTxtEnable = toTxtEnable;
    }

    public String getBtnValue() {
        return btnValue;
    }

    public void setBtnValue(String btnValue) {
        this.btnValue = btnValue;
    }

    public boolean isEnableProcessBtn() {
        return enableProcessBtn;
    }

    public void setEnableProcessBtn(boolean enableProcessBtn) {
        this.enableProcessBtn = enableProcessBtn;
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

    public String getNewGlheadFrom() {
        return newGlheadFrom;
    }

    public void setNewGlheadFrom(String newGlheadFrom) {
        this.newGlheadFrom = newGlheadFrom;
    }

    public String getNewGlheadTo() {
        return newGlheadTo;
    }

    public void setNewGlheadTo(String newGlheadTo) {
        this.newGlheadTo = newGlheadTo;
    }

    public CrrDailyUpdationMasterGrid getAuthorized() {
        return authorized;
    }

    public void setAuthorized(CrrDailyUpdationMasterGrid authorized) {
        this.authorized = authorized;
    }

    public CrrDailyUpdationMasterGrid getTable() {
        return table;
    }

    public void setTable(CrrDailyUpdationMasterGrid table) {
        this.table = table;
    }

    public List<CrrDailyUpdationMasterGrid> getTableList() {
        return tableList;
    }

    public void setTableList(List<CrrDailyUpdationMasterGrid> tableList) {
        this.tableList = tableList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGlHeadFrom() {
        return glHeadFrom;
    }

    public void setGlHeadFrom(String glHeadFrom) {
        this.glHeadFrom = glHeadFrom;
    }

    public String getGlHeadTO() {
        return glHeadTO;
    }

    public void setGlHeadTO(String glHeadTO) {
        this.glHeadTO = glHeadTO;
    }

    public String getAccDescType() {
        return accDescType;
    }

    public void setAccDescType(String accDescType) {
        this.accDescType = accDescType;
    }

    public List<SelectItem> getAcctDescOption() {
        return acctDescOption;
    }

    public void setAcctDescOption(List<SelectItem> acctDescOption) {
        this.acctDescOption = acctDescOption;
    }

    public CrrDailyUpdationMaster() {
        try {
            rfr = (CrrDailyEntryFacadeRemote) ServiceLocator.getInstance().lookup("CrrDailyEntryFacade");
            dailyBean = (DailyMasterFacadeRemote) ServiceLocator.getInstance().lookup("DailyMasterFacade");
            tableList = new ArrayList<CrrDailyUpdationMasterGrid>();
            acctDescOption = new ArrayList<SelectItem>();
            fillFunctionList();
            getAcDesc();
            refresh();
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void fillFunctionList() {
        try {
            functionList = new ArrayList<SelectItem>();
            functionList.add(new SelectItem("0", "--Select--"));
            functionList.add(new SelectItem("A", "ADD"));
            functionList.add(new SelectItem("M", "MODIFY"));
            functionList.add(new SelectItem("D", "DELETE"));
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void getAcDesc() {
        try {
            List listForAcDesc = rfr.getTableDetails();
            acctDescOption = new ArrayList<SelectItem>();
            acctDescOption.add(new SelectItem("--SELECT--"));
            if (!listForAcDesc.isEmpty()) {
                for (int i = 0; i < listForAcDesc.size(); i++) {
                    Vector element = (Vector) listForAcDesc.get(i);
                    acctDescOption.add(new SelectItem(element.get(0).toString()));
                }
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void functionProcess() {
        this.setMessage("");
        try {
            if (this.function.equals("0")) {
                this.setMessage("Please select function !");
                return;
            }
            if (this.function.equalsIgnoreCase("A")) {
                this.frTxtEnable = false;
                this.toTxtEnable = false;
                this.btnValue = "Save";
                this.setMessage("Fill 8 digit account number in From and To GL Head fields which contains :2 digit account code plus 6 digit account number !");
            } else if (this.function.equalsIgnoreCase("M")) {
                this.frTxtEnable = true;
                this.toTxtEnable = true;
                this.btnValue = "Modify";
                this.setMessage("Please select a row from table to modify the entry !");
            } else if (this.function.equalsIgnoreCase("D")) {
                this.frTxtEnable = true;
                this.toTxtEnable = true;
                this.btnValue = "Delete";
                this.setMessage("Please select a row from table to delete the entry !");
            }
            this.setGlHeadFrom("");
            this.setNewGlheadFrom("");
            this.setGlHeadTO("");
            this.setNewGlheadTo("");
            this.enableProcessBtn = true;
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void setRowValues() {
        try {
            this.gridAcDesc = authorized.getAcDesc();
            this.gridFacno = authorized.getFomGlHead();
            this.gridTacno = authorized.getToGlHead();
            this.setGlHeadFrom(gridFacno);
            this.setGlHeadTO(gridTacno);
            this.setNewGlheadFrom(gridFacno);
            this.setNewGlheadTo(gridTacno);
            this.frTxtEnable = false;
            this.toTxtEnable = false;
            this.btnValue = "Modify";
            this.setFunction("M");
            this.setMessage("Now modify the entry and then press Modify button !");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void gridLoad() {
        this.setMessage("");
        try {
            if (this.accDescType.equalsIgnoreCase("--Select--")) {
                this.setMessage("Please select account description !");
                return;
            }
            this.setFunction("A");
            this.setGlHeadFrom("");
            this.setNewGlheadFrom("");
            this.setGlHeadTO("");
            this.setNewGlheadTo("");
            this.enableProcessBtn = true;
            this.btnValue = "Save";
            datLoadInTable();
            this.setMessage("Now you can perform either Add, Modify or Delete operation ! Please select a function.");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void datLoadInTable() {
        try {
            tableList = new ArrayList<CrrDailyUpdationMasterGrid>();
            List list = rfr.gridLoad(accDescType);
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    table = new CrrDailyUpdationMasterGrid();
                    Vector vector = (Vector) list.get(i);
                    table.setAcDesc(vector.get(1).toString());
                    table.setFomGlHead(vector.get(2).toString());
                    table.setToGlHead(vector.get(3).toString());
                    tableList.add(table);
                }
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void saveData() {
        try {
            if (accDescType.equals("--SELECT--")) {
                this.setMessage("Please select account description !");
                return;
            }
            if (!this.function.equalsIgnoreCase("A")) {
                this.setMessage("Please select appropriate function !");
                return;
            }
            if (this.glHeadFrom == null || this.glHeadTO == null || this.glHeadFrom.equals("") || this.glHeadTO.equals("")) {
                this.setMessage("Please fill From and To GL Head !");
                return;
            }
            if (this.glHeadFrom.length() < 8) {
                setMessage("Fill 8 digit account number in From GL Head which contains :2 digit account code plus 6 digit account number !");
                return;
            }
            if (this.glHeadTO.length() < 8) {
                setMessage("Fill 8 digit account number in To GL Head which contains :2 digit account code plus 6 digit account number !");
                return;
            }

            String result = rfr.saveRecordCrrDaily(this.accDescType, this.newGlheadFrom, this.newGlheadTo);
            this.setMessage(result);
            this.setGlHeadFrom("");
            this.setGlHeadTO("");
            this.setNewGlheadFrom("");
            this.setNewGlheadTo("");
            this.enableProcessBtn = true;
            datLoadInTable();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void deleteData() {
        this.setMessage("");
        try {
            String result = rfr.deleteRecord(authorized.getAcDesc(), authorized.getFomGlHead(), authorized.getToGlHead());
            this.setMessage(result);
            datLoadInTable();
            this.function = "D";
            this.btnValue = "DELETE";
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void fromGlHead() {
        this.setMessage("");
        try {
            if (this.glHeadFrom == null || this.glHeadFrom.equalsIgnoreCase("")) {
                this.setMessage("Please enter From GL Head !");
                return;
            }
            if (this.glHeadFrom.length() < 8) {
                setMessage("Fill 8 digit account number in From GL Head which contains :2 digit account code plus 6 digit account number !");
                return;
            }
            FtsPostingMgmtFacadeRemote ftsBeanRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            String result = ftsBeanRemote.getNewAccountNumberForHo(getGlHeadFrom());
            if (result.startsWith("A/c")) {
                this.setMessage(result);
                return;
            } else {
                this.newGlheadFrom = result.substring(2, 10);
            }
            List resultSet = dailyBean.acNameForBankPurpose(this.newGlheadFrom);
            if (resultSet.isEmpty()) {
                this.setMessage("Entered From GL Head does not exist !");
                return;
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void toGLHead() {
        this.setMessage("");
        try {
            if (this.glHeadTO == null || this.glHeadTO.equalsIgnoreCase("")) {
                this.setMessage("Please enter To GL Head !");
                return;
            }
            if (this.glHeadTO.length() < 8) {
                setMessage("Fill 8 digit account number in To GL Head which contains :2 digit account code plus 6 digit account number !");
                return;
            }
            FtsPostingMgmtFacadeRemote ftsBeanRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup("FtsPostingMgmtFacade");
            String result = ftsBeanRemote.getNewAccountNumberForHo(getGlHeadTO());
            if (result.startsWith("A/c")) {
                this.setMessage(result);
                return;
            } else {
                this.newGlheadTo = result.substring(2, 10);
            }
            List resultList = dailyBean.acNameForBankPurpose(this.newGlheadTo);
            if (resultList.isEmpty()) {
                this.setMessage("Entered To GL Head does not exist !");
                return;
            }
            if (Long.parseLong(this.getGlHeadFrom().substring(2, 8)) >= Long.parseLong(this.getGlHeadTO().substring(2, 8))) {
                this.setMessage("To GL Head cannot be less than or equal to From GL Head !");
                return;
            }
            this.enableProcessBtn = false;
            if (this.function.equals("A")) {
                this.btnValue = "Save";
                this.setMessage("Now you can save your entry !");
            } else if (this.function.equals("M")) {
                this.btnValue = "Modify";
                this.setMessage("Now you can modify your entry !");
            }
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void updateClick() {
        try {
            if (accDescType.equals("--SELECT--")) {
                this.setMessage("Please select account description !");
                return;
            }
            if (!this.function.equalsIgnoreCase("M")) {
                this.setMessage("Please select appropriate function !");
                return;
            }
            if (this.glHeadFrom == null || this.glHeadTO == null || this.glHeadFrom.equals("") || this.glHeadTO.equals("")) {
                this.setMessage("Please fill From and To GL Head !");
                return;
            }
            if (this.glHeadFrom.length() < 8) {
                setMessage("Fill 8 digit account number in From GL Head which contains :2 digit account code plus 6 digit account number !");
                return;
            }
            if (this.glHeadTO.length() < 8) {
                setMessage("Fill 8 digit account number in To GL Head which contains :2 digit account code plus 6 digit account number !");
                return;
            }
            if (this.gridFacno.equalsIgnoreCase("") || this.gridTacno.equalsIgnoreCase("")) {
                setMessage("Previous GL Heads are not found for update !");
                return;
            }
            String msg = rfr.update(gridAcDesc, newGlheadFrom, newGlheadTo, gridFacno, gridTacno);
            this.setMessage(msg);
            this.setGlHeadFrom("");
            this.setGlHeadTO("");
            this.setNewGlheadFrom("");
            this.setNewGlheadTo("");
            this.btnValue = "Modify";
            this.enableProcessBtn = true;
            datLoadInTable();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void processAction() {
        this.setMessage("");
        try {
            if (this.function.equalsIgnoreCase("A")) {
                saveData();
            } else if (this.function.equalsIgnoreCase("M")) {
                updateClick();
            }
        } catch (Exception ex) {
            this.setMessage(ex.getMessage());
        }
    }

    public void refresh() {
        try {
            this.setMessage("");
            this.setAccDescType("--Select--");
            this.setFunction("--Select--");
            this.frTxtEnable = false;
            this.setGlHeadFrom("");
            this.setNewGlheadFrom("");
            this.toTxtEnable = false;
            this.setGlHeadTO("");
            this.setNewGlheadTo("");
            this.enableProcessBtn = true;
            this.btnValue = "Save";
            tableList.clear();
            this.setMessage("Please select account description !");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public String exitForm() {
        try {
            refresh();
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
        return "case1";
    }
}
