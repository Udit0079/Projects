/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.txn;
import java.io.Serializable;


/**
 *
 * @author root
 */
public class OdfdrAccountsGrid implements Serializable{
    String sNo;
    String odfdrAccNo;
    String recptNo;

    public String getOdfdrAccNo() {
        return odfdrAccNo;
    }

    public void setOdfdrAccNo(String odfdrAccNo) {
        this.odfdrAccNo = odfdrAccNo;
    }

    public String getRecptNo() {
        return recptNo;
    }

    public void setRecptNo(String recptNo) {
        this.recptNo = recptNo;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }
    
}
