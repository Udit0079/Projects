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
public class DepositLoanAnnexturePojo {
    private JRBeanCollectionDataSource depositAnnexureList;
    private JRBeanCollectionDataSource depositAnnexureListSummary;
    private JRBeanCollectionDataSource depositAnnexureListCasa;

    public JRBeanCollectionDataSource getDepositAnnexureList() {
        return depositAnnexureList;
    }

    public void setDepositAnnexureList(JRBeanCollectionDataSource depositAnnexureList) {
        this.depositAnnexureList = depositAnnexureList;
    }

    public JRBeanCollectionDataSource getDepositAnnexureListSummary() {
        return depositAnnexureListSummary;
    }

    public void setDepositAnnexureListSummary(JRBeanCollectionDataSource depositAnnexureListSummary) {
        this.depositAnnexureListSummary = depositAnnexureListSummary;
    }

    public JRBeanCollectionDataSource getDepositAnnexureListCasa() {
        return depositAnnexureListCasa;
    }

    public void setDepositAnnexureListCasa(JRBeanCollectionDataSource depositAnnexureListCasa) {
        this.depositAnnexureListCasa = depositAnnexureListCasa;
    }
    
}
