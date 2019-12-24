/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.ho;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class PartB4List { 

    JRBeanCollectionDataSource impList;
    JRBeanCollectionDataSource sanList;

    public JRBeanCollectionDataSource getImpList() {
        return impList;
    }

    public void setImpList(JRBeanCollectionDataSource impList) {
        this.impList = impList;
    }

    public JRBeanCollectionDataSource getSanList() {
        return sanList;
    }

    public void setSanList(JRBeanCollectionDataSource sanList) {
        this.sanList = sanList;
    }
}
