/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo.mis;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class MarketingMisCombine {
    
    private JRBeanCollectionDataSource entryDetails;
    private JRBeanCollectionDataSource updateDetails;

    public JRBeanCollectionDataSource getEntryDetails() {
        return entryDetails;
    }

    public void setEntryDetails(JRBeanCollectionDataSource entryDetails) {
        this.entryDetails = entryDetails;
    }

    public JRBeanCollectionDataSource getUpdateDetails() {
        return updateDetails;
    }

    public void setUpdateDetails(JRBeanCollectionDataSource updateDetails) {
        this.updateDetails = updateDetails;
    }    
    
}
