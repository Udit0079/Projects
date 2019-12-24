/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.ho.share;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.share.ShareTransferFacadeRemote;
import com.cbs.web.pojo.ho.CategoryMaintenanceTable;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Zeeshan Waris
 */
public class CategoryAdd {

    Context ctx;
    private HttpServletRequest req;
    ShareTransferFacadeRemote remoteObject;
    private String orgnBrCode;
    private String todayDate;
    private String userName;
    private String message;
    private String category;
    Date date = new Date();
    private List<CategoryMaintenanceTable> maintenance;
    private CategoryMaintenanceTable currentItem = new CategoryMaintenanceTable();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

    public CategoryMaintenanceTable getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(CategoryMaintenanceTable currentItem) {
        this.currentItem = currentItem;
    }

    public List<CategoryMaintenanceTable> getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(List<CategoryMaintenanceTable> maintenance) {
        this.maintenance = maintenance;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public CategoryAdd() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            //setUserName("manager1");
            remoteObject = (ShareTransferFacadeRemote) ServiceLocator.getInstance().lookup("ShareTransferFacade");
            setTodayDate(sdf.format(date));
            this.setMessage("");
            //onloadCheckAction();
            onloadCategoryDetails();
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public void onloadCategoryDetails() {
        try {
            maintenance = new ArrayList<CategoryMaintenanceTable>();
            List resultList = new ArrayList();
            resultList = remoteObject.CategoryMaintenanceDetail();
            if (resultList.size() > 0) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    CategoryMaintenanceTable rd = new CategoryMaintenanceTable();
                    if (ele.get(0) != null) {
                        rd.setCategory(ele.get(0).toString());
                    }
                    maintenance.add(rd);
                }
            }
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public String btnExit() {
        btnRefresh();
        return "case1";
    }

    public void btnRefresh() {
        this.setMessage("");
        setCategory("");
    }

    public void saveBtnAction() {
        if (!category.matches("[a-zA-Z0-9,_/ ]*")) {
            setMessage("Numeric value not allowed in category");
            return;
        }
        
        if (category == null || category.equalsIgnoreCase("")) {
            this.setMessage("Please Enter Category");
            return;
        }
        try {
            String result = remoteObject.saveCategoryMaintenanceAction(category, userName);
            this.setMessage(result);
            onloadCategoryDetails();
            setCategory("");
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

    public void deleteBtnAction() {
        if (currentItem.getCategory() == null || currentItem.getCategory().equalsIgnoreCase("")) {
            this.setMessage("Please Select Category From the Table");
            return;
        }
        try {
            String result = remoteObject.CategoryMaintenanceDeleteAction(currentItem.getCategory());
            this.setMessage(result);
            onloadCategoryDetails();
            setCategory("");
        } catch (ApplicationException e) {
            setMessage(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
        }
    }

//    public void onloadCheckAction() {
//        try {
//            remoteObject.CategoryMaintenanceOnloadAction();
//        } catch (ApplicationException e) {
//            setMessage(e.getLocalizedMessage());
//        } catch (Exception e) {
//            setMessage(e.getLocalizedMessage());
//        }
//    }
}
