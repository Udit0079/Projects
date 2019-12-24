/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.dto.report;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class CbsGeneralLedgerBookDebitBalPojo implements Serializable{

    double bal;
    double tot;
    double acBal;

    public double getAcBal() {
        return acBal;
    }

    public void setAcBal(double acBal) {
        this.acBal = acBal;
    }

    public double getBal() {
        return bal;
    }

    public void setBal(double bal) {
        this.bal = bal;
    }

    public double getTot() {
        return tot;
    }

    public void setTot(double tot) {
        this.tot = tot;
    }
    
}
