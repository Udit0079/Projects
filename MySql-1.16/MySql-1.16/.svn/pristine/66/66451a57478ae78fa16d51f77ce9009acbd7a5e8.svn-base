/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin;

import com.cbs.dto.other.UserInfo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AdminManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Admin
 */
public class ActivateUser extends BaseBean {

    private final String jndiHomeName = "AdminManagementFacade";
    private AdminManagementFacadeRemote beanLocal = null;

    private String errorMessage;

    private String message;

    int currentRow;

    UserInfo currentItem;

    private List<UserInfo> gridDetail;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserInfo> getGridDetail() {
        return gridDetail;
    }

    public void setGridDetail(List<UserInfo> gridDetail) {
        this.gridDetail = gridDetail;
    }

    public UserInfo getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(UserInfo currentItem) {
        this.currentItem = currentItem;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    /**
     * Creates a new instance of ActivateUser
     */
    public ActivateUser() {
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            setTodayDate(sdf.format(date));
            gridLoad();
            this.setErrorMessage("");
            this.setMessage("");
        } catch (Exception e) {
            this.setMessage(e.getMessage());
        }
    }

    public void gridLoad() {
        gridDetail = new ArrayList<UserInfo>();
        try {
            List resultList = new ArrayList();
            beanLocal = (AdminManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            resultList = beanLocal.gridLoad(this.getOrgnBrCode(), this.getUserName());
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    UserInfo detail = new UserInfo();
                    detail.setSerialNo(i + 1);
                    detail.setUserId(ele.get(0).toString());
                    detail.setUserName(ele.get(1).toString());
                    String status;
                    if (ele.get(2).toString().equalsIgnoreCase("A")) {
                        status = "Active";
                    } else {
                        status = "Inactive";
                    }
                    detail.setStatus(status);
                    detail.setAllowLogin(ele.get(3).toString());
                    String cashAccess;
                    if (ele.get(4).toString().equalsIgnoreCase("Y")) {
                        cashAccess = "Allowed";
                    } else {
                        cashAccess = "N";
                    }
                    detail.setCashierAccess(cashAccess);
                    String headCashierAccesss = "";
                    if (ele.get(5).toString().equalsIgnoreCase("Y")) {
                        headCashierAccesss = "Allowed";
                    } else {
                        headCashierAccesss = "N";
                    }
                    detail.setHeadCashierAccess(headCashierAccesss);
                    gridDetail.add(detail);
                }
            } else {
                return;
            }
        } catch (ApplicationException ex) {
            setMessage(ex.getMessage());
        } catch (Exception e) {
            setMessage(e.getMessage());
        }
    }

    public void changeStatus() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            UserInfo item = gridDetail.get(currentRow);
            if (item.getStatus().equalsIgnoreCase("Active")) {
                if (item.getAllowLogin().equalsIgnoreCase("N")) {
                    this.setErrorMessage("User is Not Allow to Login,So You Can't Change Status.");
                } else {
                    item.setStatus("Inactive");
                    gridDetail.remove(currentRow);
                    gridDetail.add(currentRow, item);
                }
            } else if (item.getStatus().equalsIgnoreCase("Inactive")) {
                if (item.getAllowLogin().equalsIgnoreCase("N")) {
                    this.setErrorMessage("User is Not Allow to Login,So You Can't Change Status.");
                } else {
                    item.setStatus("Active");
                    gridDetail.remove(currentRow);
                    gridDetail.add(currentRow, item);
                }
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void changeLogin() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            UserInfo item = gridDetail.get(currentRow);
            if (item.getAllowLogin().equalsIgnoreCase("Y")) {
                item.setAllowLogin("N");
                gridDetail.remove(currentRow);
                gridDetail.add(currentRow, item);
            } else if (item.getAllowLogin().equalsIgnoreCase("N")) {
                item.setAllowLogin("Y");
                gridDetail.remove(currentRow);
                gridDetail.add(currentRow, item);
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void changeAccess() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            UserInfo item = gridDetail.get(currentRow);
            if (item.getAllowLogin().equalsIgnoreCase("N")) {
                throw new ApplicationException("User is Not Allow to Login,So You Can't Change Status.");
            }
            //else {
            if (item.getCashierAccess().equalsIgnoreCase("Allowed")) {
                item.setCashierAccess("N");
                gridDetail.remove(currentRow);
                gridDetail.add(currentRow, item);
            } else if (item.getCashierAccess().equalsIgnoreCase("N")) {
                item.setCashierAccess("Allowed");
                gridDetail.remove(currentRow);
                gridDetail.add(currentRow, item);
            }
            //}
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void changeHeadCashierAccess() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            UserInfo item = gridDetail.get(currentRow);
            if (item.getAllowLogin().equalsIgnoreCase("N")) {
                throw new ApplicationException("User does not allow to Login, So You can't change the Status.");
            }
            //else {
            if (item.getHeadCashierAccess().equalsIgnoreCase("Allowed")) {
                item.setHeadCashierAccess("N");
                gridDetail.remove(currentRow);
                gridDetail.add(currentRow, item);
            } else if (item.getHeadCashierAccess().equalsIgnoreCase("N")) {
                boolean found1 = false;
                for (UserInfo item1 : gridDetail) {
                    if (item1.getHeadCashierAccess().equalsIgnoreCase("Allowed")) {
                        found1 = true;
                    }
                }
                if (found1) {
                    this.setErrorMessage("Only one person casn be Head Cashier in one branch !!!");
                } else {
                    item.setHeadCashierAccess("Allowed");
                    gridDetail.remove(currentRow);
                    gridDetail.add(currentRow, item);
                }
            }
            // }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void saveUserInfo() {
        this.setErrorMessage("");
        this.setMessage("");
        try {
            String result = beanLocal.saveActiveUserDetail(gridDetail, this.getUserName(), this.getOrgnBrCode());
            if (result.equals("true")) {
                this.setMessage("Data Saved Succesfully.");
                gridLoad();
            }
        } catch (ApplicationException ex) {
            setMessage(ex.getMessage());
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public void refreshForm() {
        try {
            gridLoad();
            this.setErrorMessage("");
            this.setMessage("");
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public String ExitForm() {
        try {

            this.setErrorMessage("");
            this.setMessage("");
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
        return "case1";
    }
}
