/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.ho;

import java.io.Serializable;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author saurabhsipl
 */
public class AnnualNPAStmtJrxmlPojo implements Serializable {
    private JRBeanCollectionDataSource pojoList;
    private JRBeanCollectionDataSource partBList;

    public JRBeanCollectionDataSource getPartBList() {
        return partBList;
    }

    public void setPartBList(JRBeanCollectionDataSource partBList) {
        this.partBList = partBList;
    }

    public JRBeanCollectionDataSource getPojoList() {
        return pojoList;
    }

    public void setPojoList(JRBeanCollectionDataSource pojoList) {
        this.pojoList = pojoList;
    }
    
}
