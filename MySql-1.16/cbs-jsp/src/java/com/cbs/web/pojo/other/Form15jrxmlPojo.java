/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.other;

import java.io.Serializable;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Admin
 */
public class Form15jrxmlPojo implements Serializable{
    
    private JRBeanCollectionDataSource partI;
    private JRBeanCollectionDataSource partII;

    public JRBeanCollectionDataSource getPartI() {
        return partI;
    }

    public void setPartI(JRBeanCollectionDataSource partI) {
        this.partI = partI;
    }

    public JRBeanCollectionDataSource getPartII() {
        return partII;
    }

    public void setPartII(JRBeanCollectionDataSource partII) {
        this.partII = partII;
    }
    
}
