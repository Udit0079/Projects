/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.admin;

import com.cbs.dto.CbsVersionDetails;
import com.cbs.web.pojo.admin.Menu;
import com.cbs.web.pojo.admin.MenuGroup;
import com.cbs.web.pojo.admin.MenuItem;
import com.cbs.dto.UrlItem;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AdminManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.utils.CbsUtil;
import com.cbs.web.controller.BaseBean;
import com.hrms.web.utils.WebUtil;
import java.net.InetAddress;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * <p>
 * Page bean that corresponds to a similarly named JSP page. This class contains
 * component definitions (and initialization code) for all components that you
 * have defined on this page, as well as lifecycle methods and event handlers
 * where you may add behavior to respond to incoming events.</p>
 *
 * @version Welcome.java
 * @version Created on Apr 30, 2010, 4:20:26 PM
 * @author Dhirendra Singh
 */
public class Welcome extends BaseBean {

    private String branchName;
    private String bnkName;
    private String ivrBrCode;
    private String fullUserName;
    private boolean isCashier;
    private String message;
    private List<Menu> menuList;
    private MenuItem currentItem;
    private String pymtAlertMsg;
    private boolean displayflag;
    private String manualUrl;
    private String urlDisplay;
    private boolean showReleaseNote;
    private List<CbsVersionDetails> versionList;
    private final String jndiHomeName = "AdminManagementFacade";
    private AdminManagementFacadeRemote beanRemote = null;

    public boolean isShowReleaseNote() {
        return showReleaseNote;
    }

    public void setShowReleaseNote(boolean showReleaseNote) {
        this.showReleaseNote = showReleaseNote;
    }

    public List<CbsVersionDetails> getVersionList() {
        return versionList;
    }

    public void setVersionList(List<CbsVersionDetails> versionList) {
        this.versionList = versionList;
    }

    public String getUrlDisplay() {
        return urlDisplay;
    }

    public void setUrlDisplay(String urlDisplay) {
        this.urlDisplay = urlDisplay;
    }

    public String getManualUrl() {
        return manualUrl;
    }

    public void setManualUrl(String manualUrl) {
        this.manualUrl = manualUrl;
    }

    public boolean isDisplayflag() {
        return displayflag;
    }

    public void setDisplayflag(boolean displayflag) {
        this.displayflag = displayflag;
    }

    public String getPymtAlertMsg() {
        return pymtAlertMsg;
    }

    public void setPymtAlertMsg(String pymtAlertMsg) {
        this.pymtAlertMsg = pymtAlertMsg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isIsCashier() {
        return isCashier;
    }

    public void setIsCashier(boolean isCashier) {
        this.isCashier = isCashier;
    }

    public MenuItem getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(MenuItem item) {
        this.currentItem = item;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getIvrBrCode() {
        return ivrBrCode;
    }

    public void setIvrBrCode(String ivrBrCode) {
        this.ivrBrCode = ivrBrCode;
    }
    
    public String getFullUserName() {
        return fullUserName;
    }

    public void setFullUserName(String fullUserName) {
        this.fullUserName = fullUserName;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public String getBnkName() {
        return bnkName;
    }

    public void setBnkName(String bnkName) {
        this.bnkName = bnkName;
    }

    public Welcome() {
        long begin = System.nanoTime();
        try {
            beanRemote = (AdminManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            try {
                setPymtAlertMsg("Your Monthly Rent of CBS Services has been overdue. Please make the Payment ASAP for uninterrupted Services.");
                setDisplayflag(beanRemote.isCbsPymtMade());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            setShowReleaseNote(false);
            
            List<String> verList = (List) getHttpSession().getAttribute("verNos");
            if (!verList.isEmpty()) {
                versionList = beanRemote.getCbsVersionDetails(verList);
                setShowReleaseNote(true);
                
            }
            setManualUrl(beanRemote.getUserManualUrl());
            urlDisplay = "none";
            if (!manualUrl.equals("")) {
                urlDisplay = "inline";
            }
            isCashier = beanRemote.isCashier(getOrgnBrCode(), getUserName());
            menuList = new ArrayList<Menu>();
            if (getOrgnBrCode() != null && getUserName() != null) {
                List bankResultList = beanRemote.bankAddress(getOrgnBrCode());
                if (bankResultList.size() > 0) {
                    for (int i = 0; i < bankResultList.size(); i++) {
                        Vector ele = (Vector) bankResultList.get(i);
                        this.setBranchName(CbsUtil.toProperCase((ele.get(1).toString() + " " + ele.get(2).toString()).toLowerCase()));
                        this.setBnkName(CbsUtil.toProperCase((ele.get(0).toString()).toLowerCase()));
                        this.setIvrBrCode(ele.get(3).toString());
                    }
                }
                setFullUserName(CbsUtil.toProperCase(beanRemote.getUserName(getUserName()).toLowerCase()));
                List<UrlItem> urlItemList = beanRemote.getMenu(getUserName(), getOrgnBrCode());
                Menu menu;
                for (UrlItem urlItem : urlItemList) {
                    if (urlItem.getParentCode() == 0) {
                        List<MenuGroup> menuGroupList = getMenuGroups(urlItemList, urlItem.getIdNo());
                        List<MenuItem> menuItemList = getMenuItems(urlItemList, urlItem.getIdNo(), 0);
                        menu = new Menu(urlItem.getIdNo(), urlItem.getDisplayName(), menuGroupList, menuItemList, true);
                        menuList.add(menu);
                    }
                }
            }
        } catch (ServiceLocatorException ex) {
            setMessage(ex.getMessage());
        } catch (ApplicationException ex) {
            setMessage(ex.getMessage());
        }

        System.out.println(
                "Execution time for menu load is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
    }

    private List<MenuGroup> getMenuGroups(List<UrlItem> urlItemList, int menuId) {
        List<MenuGroup> menuGroupList = new ArrayList<MenuGroup>();
        for (UrlItem urlItem : urlItemList) {
            if (urlItem.getParentCode() == menuId && urlItem.getSubParentCode() == 0 && urlItem.getUrl().equals("")) {
                List<MenuItem> menuGroupItem = getMenuItems(urlItemList, menuId, urlItem.getIdNo());
                MenuGroup menuGroup = new MenuGroup(urlItem.getIdNo(), urlItem.getDisplayName(), menuGroupItem, true);
                menuGroupList.add(menuGroup);
            }
        }
        return menuGroupList;
    }

    private List<MenuItem> getMenuItems(List<UrlItem> urlItemList, int menuId, int menuGroupId) {
        List<MenuItem> localMenuList = new ArrayList<MenuItem>();
        for (UrlItem urlItem : urlItemList) {
            if (urlItem.getParentCode() == menuId && urlItem.getSubParentCode() == menuGroupId && !urlItem.getUrl().equals("")) {
                MenuItem menuItem = new MenuItem(urlItem.getIdNo(), urlItem.getDisplayName(), urlItem.getUrl(), true);
                localMenuList.add(menuItem);
            }
        }
        return localMenuList;
    }

    public void urlNevigation() {
        try {
            if (this.getCurrentItem().getMenuItemAction().contains("CashierCashRecieved.jsp")
                    || this.getCurrentItem().getMenuItemAction().contains("CashierTokenBook.jsp")) {
                if (isCashier) {
                    setMessage("");
                    getHttpResponse().sendRedirect(this.getCurrentItem().getMenuItemAction());
                } else {
                    setMessage("You are not authorized person for accessing this page.");
                }
            } else {
                setMessage("");
                getHttpResponse().sendRedirect(this.getCurrentItem().getMenuItemAction());
            }
        } catch (Exception ex) {
            setMessage(ex.getMessage());
        }
    }

    public String logout() {
        try {
            beanRemote = (AdminManagementFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeName);
            System.out.println(new Date() + ": The User " + getUserName() + " is logged out from IP Address " + WebUtil.getClientIP(getHttpRequest()));
            beanRemote.logOut(getUserName());

            getHttpResponse().sendRedirect("/cbs-jsp/j_spring_security_logout");

        } catch (Exception ex) {
            Logger.getLogger(Welcome.class
                    .getName()).log(Level.ERROR, null, ex);
        }
        return null;
    }

    public void hidePymtAlertPanel() {
        try {
            setDisplayflag(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideReleaseNoteAlert() {
        try {
            setShowReleaseNote(false);
            getHttpSession().setAttribute("verNos", new ArrayList<String>());
        } catch (Exception e) {
        }
    }
}
