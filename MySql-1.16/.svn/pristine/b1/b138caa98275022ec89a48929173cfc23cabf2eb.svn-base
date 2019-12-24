package com.cbs.web.controller.ho.deadstock;

import com.cbs.exception.ApplicationException;
import com.cbs.web.pojo.ho.DsItemMasterTable;
import com.cbs.facade.ho.deadstock.DeadstockFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.model.SelectItem;

/**
 *
 * @author Ankit Verma
 */
public final class DsItemMaster extends BaseBean {

    private String userName = getUserName();
    private String todayDate = getTodayDate();
    private String msg;
    private long itemCode;
    private String itemName;
    private String selectGroup;
    private boolean saveDisable;
    private boolean updateDisable;
    private boolean itemCodeDisable;
    private List<SelectItem> selectGroupList;
    private List<DsItemMasterTable> datagrid;
    DsItemMasterTable currentData;
    DeadstockFacadeRemote deadstockFacadeRemote;
    Date currentDate;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public boolean isItemCodeDisable() {
        return itemCodeDisable;
    }

    public void setItemCodeDisable(boolean itemCodeDisable) {
        this.itemCodeDisable = itemCodeDisable;
    }

    public String getSelectGroup() {
        return selectGroup;
    }

    public void setSelectGroup(String selectGroup) {
        this.selectGroup = selectGroup;
    }

    public long getItemCode() {
        return itemCode;
    }

    public void setItemCode(long itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<SelectItem> getSelectGroupList() {
        return selectGroupList;
    }

    public void setSelectGroupList(List<SelectItem> selectGroupList) {
        this.selectGroupList = selectGroupList;
    }

    public DsItemMasterTable getCurrentData() {
        return currentData;
    }

    public void setCurrentData(DsItemMasterTable currentData) {
        this.currentData = currentData;
    }

    public List<DsItemMasterTable> getDatagrid() {
        return datagrid;
    }

    public void setDatagrid(List<DsItemMasterTable> datagrid) {
        this.datagrid = datagrid;
    }

    public boolean isSaveDisable() {
        return saveDisable;
    }

    public void setSaveDisable(boolean saveDisable) {
        this.saveDisable = saveDisable;
    }

    public boolean isUpdateDisable() {
        return updateDisable;
    }

    public void setUpdateDisable(boolean updateDisable) {
        this.updateDisable = updateDisable;
    }

    public DsItemMaster() {
        try {
            deadstockFacadeRemote = (DeadstockFacadeRemote) ServiceLocator.getInstance().lookup("DeadstockFacade");
            currentDate = new Date();
            selectGroupList = new ArrayList<SelectItem>();
            selectGroupList.add(new SelectItem("--Select--"));
            itemCodeDisable=true;
            getGroupList();
            getMaxItemCode();
            viewData();
            updateDisable = true;
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void getMaxItemCode() {
        try {
            String maxCode = deadstockFacadeRemote.getMaxItemCode();
            long code11 = 0;
            if (maxCode == null) {
                code11 = 1;
            } else {
                long code = Long.parseLong(maxCode) + 1;
                code11 = code;
            }
            setItemCode(code11);
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void getGroupList() {
        try {
            List groupList = deadstockFacadeRemote.getGroupList();
            if (!groupList.isEmpty() || groupList != null) {
                for (int i = 0; i < groupList.size(); i++) {
                    Vector vec = (Vector) groupList.get(i);
                    if (vec.get(0) != null) {
                        selectGroupList.add(new SelectItem(vec.get(0)));
                    }
                }
            }
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void saveData() {
        try {
            msg = validation();
            if (msg.equalsIgnoreCase("ok")) {
                msg = deadstockFacadeRemote.saveItemMasterRecord(selectGroup, itemCode, itemName, userName);
            }
            viewData();
            refresh();
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void viewData() {
        try {
            datagrid = new ArrayList<DsItemMasterTable>();
            List resultList = deadstockFacadeRemote.viewData();
            int count = 0;
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    DsItemMasterTable grid = new DsItemMasterTable();
                    Vector ele = (Vector) resultList.get(i);
                    if (ele.get(0) != null) {
                        grid.setGroupName(ele.get(0).toString());
                    }
                    if (ele.get(1) != null) {
                        grid.setItemCode(Long.parseLong(ele.get(1).toString()));
                    }
                    if (ele.get(2) != null) {
                        grid.setItemName(ele.get(2).toString());
                    }
                    if (ele.get(3) != null) {
                        grid.setEnterBy(ele.get(3).toString());
                    }
                    grid.setSno(++count);
                    datagrid.add(grid);
                }
            } else {
                this.setMsg("No Details Exist ");
            }
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void selectData() {
        try {
            updateDisable = false;
            saveDisable = true;
            msg = "";
            itemCodeDisable = true;
            setItemCode(currentData.getItemCode());
            setItemName(currentData.getItemName());
            setSelectGroup(currentData.getGroupName());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void updateData() {
        try {
            msg = validation();
            if (msg.equalsIgnoreCase("ok")) {
                msg = deadstockFacadeRemote.updateItemMasterRecord(selectGroup, itemCode, itemName, userName);
            }
            viewData();
            refresh();
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void refresh() {
        try {
            itemName = "";
            updateDisable = true;
            saveDisable = false;
            getMaxItemCode();
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void refreshForm() {
        refresh();
        selectGroup = "--Select--";
        msg = "";
    }

    public String exitAction() {
        refreshForm();
        return "case1";
    }

    public String validation() {
        try {
            if (itemName.equalsIgnoreCase("")) {
                return "Please insert item name";
            }
            if (selectGroup.equalsIgnoreCase("--Select--")) {
                return "Please select a group first";
            }
            return "ok";
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
            return "false";
        }
    }
}
