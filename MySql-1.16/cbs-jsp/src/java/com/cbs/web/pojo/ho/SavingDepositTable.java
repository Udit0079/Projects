/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.pojo.ho;

public class SavingDepositTable {

    private String applicableDate;
    private float addIntAmount;
    private String addIntEnter;

    public float getAddIntAmount() {
        return addIntAmount;
    }

    public void setAddIntAmount(float addIntAmount) {
        this.addIntAmount = addIntAmount;
    }

    public String getAddIntEnter() {
        return addIntEnter;
    }

    public void setAddIntEnter(String addIntEnter) {
        this.addIntEnter = addIntEnter;
    }

    public String getApplicableDate() {
        return applicableDate;
    }

    public void setApplicableDate(String applicableDate) {
        this.applicableDate = applicableDate;
    }

    void setacdesc(String toString) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void setacno(float parseFloat) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void setbaltype(String toString) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

