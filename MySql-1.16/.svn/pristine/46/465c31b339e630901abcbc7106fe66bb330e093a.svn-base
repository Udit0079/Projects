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
public final class ItemGroupDepreciation extends BaseBean {

    private String userName = getUserName();
    private String msg;
    private String month;
    private String time;
    private String year;
    private float depPercent;
    private String selectMethod;
    private String selectRound;
    private String selectRoundBase;
    private String selectRoundRange;
    private String selectGroup;
    private boolean saveDisable;
    private boolean updateDisable;
    private boolean itemCodeDisable;
    private boolean disableRoundRange;
    private boolean disableRoundBase;
    private boolean disableGroup;
    private List<SelectItem> selectGroupList;
    private List<DsItemMasterTable> datagrid;
    DsItemMasterTable currentData;
    DeadstockFacadeRemote deadstockFacadeRemote;
    Date date;
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

    public float getDepPercent() {
        return depPercent;
    }

    public void setDepPercent(float depPercent) {
        this.depPercent = depPercent;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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

    public String getSelectMethod() {
        return selectMethod;
    }

    public void setSelectMethod(String selectMethod) {
        this.selectMethod = selectMethod;
    }

    public String getSelectRound() {
        return selectRound;
    }

    public void setSelectRound(String selectRound) {
        this.selectRound = selectRound;
    }

    public String getSelectRoundBase() {
        return selectRoundBase;
    }

    public void setSelectRoundBase(String selectRoundBase) {
        this.selectRoundBase = selectRoundBase;
    }

    public String getSelectRoundRange() {
        return selectRoundRange;
    }

    public void setSelectRoundRange(String selectRoundRange) {
        this.selectRoundRange = selectRoundRange;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public boolean isDisableRoundBase() {
        return disableRoundBase;
    }

    public void setDisableRoundBase(boolean disableRoundBase) {
        this.disableRoundBase = disableRoundBase;
    }

    public boolean isDisableRoundRange() {
        return disableRoundRange;
    }

    public void setDisableRoundRange(boolean disableRoundRange) {
        this.disableRoundRange = disableRoundRange;
    }

    public boolean isDisableGroup() {
        return disableGroup;
    }

    public void setDisableGroup(boolean disableGroup) {
        this.disableGroup = disableGroup;
    }

    public ItemGroupDepreciation() {
        try {
            deadstockFacadeRemote = (DeadstockFacadeRemote) ServiceLocator.getInstance().lookup("DeadstockFacade");
            selectGroupList = new ArrayList<SelectItem>();
            selectGroupList.add(new SelectItem("--Select--"));
            getGroupList();
            viewData();
            updateDisable = true;
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
        String method = "";
        String round = "";
        String roundBase = "0";
        String range = "";
        msg = validation();
        if (msg.equalsIgnoreCase("ok")) {
            try {
                String wefPeriod = month + "-" + year;
                if (selectMethod.equalsIgnoreCase("Straight-Line")) {
                    method = "S";
                } else if (selectMethod.equalsIgnoreCase("Declining Balance")) {
                    method = "D";
                }
                if (selectRound.equalsIgnoreCase("Yes")) {
                    round = "Y";
                } else if (selectRound.equalsIgnoreCase("No")) {
                    round = "N";
                }
                if (selectRoundRange.equalsIgnoreCase("Higher")) {
                    range = "H";
                } else if (selectRoundRange.equalsIgnoreCase("Lower")) {
                    range = "L";
                } else {
                    range = "N";
                }
                roundBase = selectRoundBase;
                msg = deadstockFacadeRemote.saveRecord(selectGroup, wefPeriod, depPercent, method, round, roundBase, range, userName);
                viewData();
                refresh();
            } catch (ApplicationException e) {
                setMsg(e.getLocalizedMessage());
            } catch (Exception e) {
                setMsg(e.getLocalizedMessage());
            }
        }
    }

    public void viewData() {
        try {
            datagrid = new ArrayList<DsItemMasterTable>();
            List resultList = deadstockFacadeRemote.viewDepreciationData();
            int count = 0;
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    DsItemMasterTable grid = new DsItemMasterTable();
                    Vector ele = (Vector) resultList.get(i);
                    if (ele.get(0) != null) {
                        grid.setGroupCode(ele.get(0).toString());
                    }
                    if (ele.get(1) != null) {
                        grid.setItemCode(Long.parseLong(ele.get(1).toString()));
                    }
                    if (ele.get(2) != null) {
                        grid.setItemName(ele.get(2).toString());
                    }
                    if (ele.get(3) != null) {
                        String mon = "";
                        String wefPer = "";
                        String period = ele.get(3).toString();
                        grid.setTempWefPeriod(period);
                        String per[] = period.split("-");
                        if (per[0].equalsIgnoreCase("01")) {
                            mon = "JANUARY";
                        } else if (per[0].equalsIgnoreCase("02")) {
                            mon = "FEBUARY";
                        } else if (per[0].equalsIgnoreCase("03")) {
                            mon = "MARCH";
                        } else if (per[0].equalsIgnoreCase("04")) {
                            mon = "APRIL";
                        } else if (per[0].equalsIgnoreCase("05")) {
                            mon = "MAY";
                        } else if (per[0].equalsIgnoreCase("06")) {
                            mon = "JUNE";
                        } else if (per[0].equalsIgnoreCase("07")) {
                            mon = "JULY";
                        } else if (per[0].equalsIgnoreCase("08")) {
                            mon = "AUGUST";
                        } else if (per[0].equalsIgnoreCase("09")) {
                            mon = "SEPTEMBER";
                        } else if (per[0].equalsIgnoreCase("10")) {
                            mon = "OCTOBER";
                        } else if (per[0].equalsIgnoreCase("11")) {
                            mon = "NOVEMBER";
                        } else if (per[0].equalsIgnoreCase("12")) {
                            mon = "DECEMBER";
                        } else {
                            mon = "blank";
                        }
                        if (!mon.equalsIgnoreCase("blank")) {
                            wefPer = mon + " ," + per[1];
                        } else {
                            wefPer = per[1];
                        }
                        grid.setWefPeriod(wefPer);
                    }
                    if (ele.get(4) != null) {
                        grid.setDepPercent(Float.parseFloat(ele.get(4).toString()));
                    }
                    if (ele.get(5) != null) {
                        String method = ele.get(5).toString();
                        if (method.equalsIgnoreCase("S")) {
                            method = "Straight-Line";
                        } else if (method.equalsIgnoreCase("D")) {
                            method = "Declining Balance";
                        }
                        grid.setDepMethod(method);
                    }
                    if (ele.get(6) != null) {
                        grid.setDepRound(ele.get(6).toString());
                    }
                    if (ele.get(7) != null) {
                        grid.setRoundBase(ele.get(7).toString());
                    }
                    if (ele.get(8) != null) {
                        grid.setRoundRange(ele.get(8).toString());
                    }
                    if (ele.get(9) != null) {
                        grid.setEnterBy(ele.get(9).toString());
                    }
                    grid.setSno(++count);
                    datagrid.add(grid);
                }
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
            disableGroup = true;
            itemCodeDisable = true;
            setDepPercent(currentData.getDepPercent());
            String wefPer = currentData.getTempWefPeriod();
            String monthYear[] = wefPer.split("-");
            setMonth(monthYear[0]);
            setYear(monthYear[1]);
            Vector groupName = deadstockFacadeRemote.getGroupCode(currentData.getGroupCode());
            if (groupName.get(0) != null || groupName != null) {
                setSelectGroup(groupName.get(0).toString());
            }
            String method = currentData.getDepMethod();
            setSelectMethod(method);
            setSelectRound(currentData.getDepRound());
            setSelectRoundBase(currentData.getRoundBase());
            String range = currentData.getRoundRange();
            if (range.equalsIgnoreCase("H")) {
                range = "Higher";
            } else if (range.equalsIgnoreCase("L")) {
                range = "Lower";
            } else {
                range = "Normal";
            }
            setSelectRoundRange(range);
            msg = "";
        } catch (ApplicationException e) {
            setMsg(e.getLocalizedMessage());
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }

    public void updateData() {
        msg = validation();
        if (msg.equalsIgnoreCase("ok")) {
            String method = "";
            String round = "";
            String range = "";
            String roundBase = "";
            try {
                String wefPeriod = month + "-" + year;
                if (selectMethod.equalsIgnoreCase("Straight-Line")) {
                    method = "S";
                } else if (selectMethod.equalsIgnoreCase("Declining Balance")) {
                    method = "D";
                }
                if (selectRound.equalsIgnoreCase("Yes")) {
                    round = "Y";
                } else if (selectRound.equalsIgnoreCase("No")) {
                    round = "N";
                }
                if (selectRoundRange.equalsIgnoreCase("Higher")) {
                    range = "H";
                } else if (selectRoundRange.equalsIgnoreCase("Lower")) {
                    range = "L";
                } else {
                    range = "N";
                }
                roundBase = selectRoundBase;
                msg = deadstockFacadeRemote.updateRecord(selectGroup, wefPeriod, depPercent, method, round, roundBase, range, userName);
                itemCodeDisable = false;
                viewData();
                refresh();
            } catch (ApplicationException e) {
                setMsg(e.getLocalizedMessage());
            } catch (Exception e) {
                setMsg(e.getLocalizedMessage());
            }
        }
    }

    public void refresh() {
        selectGroup = "--Select--";
        selectMethod = "--Select--";
        month = "";
        year = "";
        depPercent = 0.0f;
        selectRound = "Yes";
        selectRoundBase = "0";
        selectRoundRange = "Normal";
        itemCodeDisable = false;
        updateDisable = true;
        saveDisable = false;
        disableRoundBase = false;
        disableRoundRange = false;
        disableGroup = false;
    }

    public void refreshForm() {
        refresh();
        msg = "";
    }

    public String exitAction() {
        refreshForm();
        return "case1";
    }

    public String validation() {
        try {
            if (year.equalsIgnoreCase("")) {
                return "Please Specify Wef-Period";
            }
            if (selectGroup.equalsIgnoreCase("--Select--")) {
                return "Please select a group first";
            }
            if (selectMethod.equalsIgnoreCase("--Select--")) {
                return "Please select a Depreciation method first";
            }
            return "ok";
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
            return "";
        }
    }

    public void disableSomeFields() {
        try {
            if (selectRound.equalsIgnoreCase("No")) {
                selectRoundBase = "0";
                selectRoundRange = "Normal";
                disableRoundBase = true;
                disableRoundRange = true;
            } else {
                disableRoundBase = false;
                disableRoundRange = false;
            }
        } catch (Exception e) {
            setMsg(e.getLocalizedMessage());
        }
    }
}
