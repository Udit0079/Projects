/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.dto.report.ho;

/**
 *
 * @author root
 */
public class HoReconPojo {
    private String acno;
    
    private String orgn;
    
    private String dest;
    
    private String modeOfTran;
    
    private double amt;

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }
    
    public String getOrgn() {
        return orgn;
    }

    public void setOrgn(String orgn) {
        this.orgn = orgn;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getModeOfTran() {
        return modeOfTran;
    }

    public void setModeOfTran(String modeOfTran) {
        this.modeOfTran = modeOfTran;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }
    
    
    
}
