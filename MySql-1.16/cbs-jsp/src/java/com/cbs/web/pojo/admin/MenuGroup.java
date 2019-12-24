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
public class MenuGroup {
    private int id;
    private String menuGroupCaption;
    private boolean enabled;
    private List<MenuItem> menuItems;

    public MenuGroup(int id, String menuGpCaption, List<MenuItem> menuItems, boolean enabled){
        this.id = id;
        this.menuGroupCaption = menuGpCaption;
        this.menuItems = menuItems;
        this.enabled = enabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getMenuGroupCaption() {
        return menuGroupCaption;
    }

    public void setMenuGroupCaption(String menuGroupCaption) {
        this.menuGroupCaption = menuGroupCaption;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }


}
