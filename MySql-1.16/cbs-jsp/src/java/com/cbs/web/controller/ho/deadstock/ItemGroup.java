package com.cbs.web.controller.ho.deadstock;

import com.cbs.exception.ApplicationException;
import com.cbs.web.pojo.ho.ItemGroupTable;
import com.cbs.facade.ho.deadstock.DeadstockFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Ankit Verma
 */
public final class ItemGroup extends BaseBean {

    private String userName = getUserName();
    String msg;
    String groupCode;
    String groupName;
    private boolean saveDisable;
    private boolean updateDisable;
    private boolean disableGroupCode;
    List<ItemGroupTable> datagrid;
    ItemGroupTable currentData;
    String code11;
    DeadstockFacadeRemote deadstockFacadeRemote;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    private Date currentDate = new Date();

    public ItemGroup() {
        try {
            deadstockFacadeRemote = (DeadstockFacadeRemote) ServiceLocator.getInstance().lookup("DeadstockFacade");
            updateDisable = true;
            saveDisable = false;
            getMaxGroupCode();
            viewData();
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public boolean isSaveDisable() {
        return saveDisable;
    }

    public boolean isDisableGroupCode() {
        return disableGroupCode;
    }

    public void setDisableGroupCode(boolean disableGroupCode) {
        this.disableGroupCode = disableGroupCode;
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

    public ItemGroupTable getCurrentData() {
        return currentData;
    }

    public void setCurrentData(ItemGroupTable currentData) {
        this.currentData = currentData;
    }

    public List<ItemGroupTable> getDatagrid() {
        return datagrid;
    }

    public void setDatagrid(List<ItemGroupTable> datagrid) {
        this.datagrid = datagrid;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void getMaxGroupCode() {
        try {
            long maxCode = deadstockFacadeRemote.getMaxGroupCode();
            if (maxCode == 0) {
                code11 = "00001";
            } else {
                long a = maxCode + 1;
                code11 = Long.toString(a);
                int length = code11.length();
                int addedZero = 5 - length;
                for (int i = 1; i <= addedZero; i++) {
                    code11 = "0" + code11;
                }
            }
            setGroupCode(code11);
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void viewData() {
            try {
            int sno = 0;
            datagrid = new ArrayList<ItemGroupTable>();
            List resultList = deadstockFacadeRemote.viewItemGroupData();
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    ItemGroupTable grid = new ItemGroupTable();
                    Vector ele = (Vector) resultList.get(i);
                    if (ele.get(0) != null) {
                        grid.setGroupCode(ele.get(0).toString());
                    }
                    if (ele.get(1) != null) {
                        grid.setGroupName(ele.get(1).toString());
                    }
                    if (ele.get(2) != null) {
                        grid.setEnterBy(ele.get(2).toString());
                    }
                    grid.setSno(++sno);
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

    public void saveData() {
        try {
            msg = validation();
            if (msg.equalsIgnoreCase("ok")) {
                String code = "ITMGR" + groupCode;
                String name = groupName;
                String dt = ymd.format(currentDate);
                msg = deadstockFacadeRemote.saveData(code, name, dt, userName);
                viewData();
                refresh();
            }
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void updateData() {
        try {
            String code = "ITMGR" + groupCode;
            String name = groupName;
            String dt = ymd.format(currentDate);
            msg = validation();
            if (msg.equalsIgnoreCase("ok")) {
                msg = deadstockFacadeRemote.updateItemGroupRecord(code, name, dt, userName);
                viewData();
                refresh();
            }
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void refreshForm() {
        refresh();
        msg = "";
    }

    public void refresh() {
        getMaxGroupCode();
        groupName = "";
        disableGroupCode = false;
        updateDisable = true;
        saveDisable = false;
    }

    public String validation() {
        try {
            if (groupName.equalsIgnoreCase("")) {
                return "Please Insert Group Name";
            }
            if (groupCode.equalsIgnoreCase("")) {
                return "Please Specify Group Code";
            }
            return "ok";
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
            return "";
        }
    }

    public String exitAction() {
        refresh();
        return "case1";
    }

    public void selectData() {
        try {
            updateDisable = false;
            saveDisable = true;
            msg = "";
            setGroupCode(currentData.getGroupCode().substring(5));
            setGroupName(currentData.getGroupName());
            disableGroupCode = true;
            saveDisable = true;
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }
}
