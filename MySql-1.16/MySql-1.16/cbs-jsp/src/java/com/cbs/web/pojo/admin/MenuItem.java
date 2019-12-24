/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.admin;

import org.richfaces.component.html.HtmlMenuItem;

/**
 *
 * @author root
 */
public class MenuItem {
    private int itemId;
    private String menuItemCaption;
    private String menuItemAction;
    private boolean enabled;

    HtmlMenuItem htmlMenuItem = new HtmlMenuItem();
    
 
    public MenuItem(int itemId, String menuItemCaption, String menuItemAction, boolean enabled){
        this.itemId = itemId;
	this.menuItemCaption = menuItemCaption;
	this.menuItemAction = menuItemAction;
	this.enabled = enabled;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getMenuItemAction() {
        return menuItemAction;
    }

    public void setMenuItemAction(String menuItemAction) {
        this.menuItemAction = menuItemAction;
    }

    public String getMenuItemCaption() {
        return menuItemCaption;
    }

    public void setMenuItemCaption(String menuItemCaption) {
        this.menuItemCaption = menuItemCaption;
    }

    public HtmlMenuItem getHtmlMenuItem() {
        return htmlMenuItem;
    }

    public void setHtmlMenuItem(HtmlMenuItem htmlMenuItem) {
        this.htmlMenuItem = htmlMenuItem;
    }
    
}
