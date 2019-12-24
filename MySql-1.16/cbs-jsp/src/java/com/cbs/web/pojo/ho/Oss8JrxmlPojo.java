/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.ho;

import java.io.Serializable;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author root
 */
public class Oss8JrxmlPojo implements Serializable {

    private JRBeanCollectionDataSource atmProfile;
    private JRBeanCollectionDataSource bankProfile;
    private JRBeanCollectionDataSource organisationalProfile;
    private JRBeanCollectionDataSource managementProfile;
    private JRBeanCollectionDataSource finOneProfile;
    private JRBeanCollectionDataSource finTwoProfile;
    private JRBeanCollectionDataSource npaProfile;
    private JRBeanCollectionDataSource partBList;
    private JRBeanCollectionDataSource mainList;

    public JRBeanCollectionDataSource getAtmProfile() {
        return atmProfile;
    }

    public void setAtmProfile(JRBeanCollectionDataSource atmProfile) {
        this.atmProfile = atmProfile;
    }

    public JRBeanCollectionDataSource getBankProfile() {
        return bankProfile;
    }

    public void setBankProfile(JRBeanCollectionDataSource bankProfile) {
        this.bankProfile = bankProfile;
    }

    public JRBeanCollectionDataSource getOrganisationalProfile() {
        return organisationalProfile;
    }

    public void setOrganisationalProfile(JRBeanCollectionDataSource organisationalProfile) {
        this.organisationalProfile = organisationalProfile;
    }

    public JRBeanCollectionDataSource getManagementProfile() {
        return managementProfile;
    }

    public void setManagementProfile(JRBeanCollectionDataSource managementProfile) {
        this.managementProfile = managementProfile;
    }

    public JRBeanCollectionDataSource getFinOneProfile() {
        return finOneProfile;
    }

    public void setFinOneProfile(JRBeanCollectionDataSource finOneProfile) {
        this.finOneProfile = finOneProfile;
    }

    public JRBeanCollectionDataSource getFinTwoProfile() {
        return finTwoProfile;
    }

    public void setFinTwoProfile(JRBeanCollectionDataSource finTwoProfile) {
        this.finTwoProfile = finTwoProfile;
    }
    public JRBeanCollectionDataSource getNpaProfile() {
        return npaProfile;
    }
    public void setNpaProfile(JRBeanCollectionDataSource npaProfile) {
        this.npaProfile = npaProfile;
    }
    public JRBeanCollectionDataSource getMainList() {
        return mainList;
    }
    public void setMainList(JRBeanCollectionDataSource mainList) {
        this.mainList = mainList;
    }
    public JRBeanCollectionDataSource getPartBList() {
        return partBList;
    }
    public void setPartBList(JRBeanCollectionDataSource partBList) {
        this.partBList = partBList;
    }
}
