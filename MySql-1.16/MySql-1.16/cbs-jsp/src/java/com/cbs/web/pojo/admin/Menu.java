/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.admin;

import java.util.List;

/**
 *
 * @author root
 */
public class Menu {
    private int menuId;
    private String menuCaption;
    private boolean enabled;
    private List<MenuItem> menuItems;
    private List<MenuGroup> menuGroupList;

    public Menu(int id, String menuCaption, List<MenuGroup> menuGroupList, List<MenuItem> menuItems, boolean enabled){
        this.menuId = id;
        this.menuCaption = menuCaption;
        this.menuGroupList = menuGroupList;
        this.menuItems = menuItems;
        this.enabled = enabled;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getMenuCaption() {
        return menuCaption;
    }

    public void setMenuCaption(String menuCaption) {
        this.menuCaption = menuCaption;
    }

    public List<MenuGroup> getMenuGroupList() {
        return menuGroupList;
    }

    public void setMenuGroupList(List<MenuGroup> menuGroupList) {
        this.menuGroupList = menuGroupList;
    }

}
