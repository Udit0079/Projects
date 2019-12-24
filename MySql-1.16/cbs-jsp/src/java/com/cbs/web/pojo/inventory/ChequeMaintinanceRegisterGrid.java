/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.inventory;

/**
 *
 * @author Admin
 */
public class ChequeMaintinanceRegisterGrid {
    
    /**THESE 2 VARIABLES FOR UNUSED INST. GRID**/
    public String chqNoUnused;
    public String issueDtUnused;
    /**THESE 3 VARIABLES FOR STOP PAY MARK INST. GRID**/
    public String chqNoStop;
    public String issueDtStop;
    public String enterBy;

    public String getChqNoStop() {
        return chqNoStop;
    }

    public void setChqNoStop(String chqNoStop) {
        this.chqNoStop = chqNoStop;
    }

    public String getChqNoUnused() {
        return chqNoUnused;
    }

    public void setChqNoUnused(String chqNoUnused) {
        this.chqNoUnused = chqNoUnused;
    }

    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public String getIssueDtStop() {
        return issueDtStop;
    }

    public void setIssueDtStop(String issueDtStop) {
        this.issueDtStop = issueDtStop;
    }

    public String getIssueDtUnused() {
        return issueDtUnused;
    }

    public void setIssueDtUnused(String issueDtUnused) {
        this.issueDtUnused = issueDtUnused;
    }

    

}
