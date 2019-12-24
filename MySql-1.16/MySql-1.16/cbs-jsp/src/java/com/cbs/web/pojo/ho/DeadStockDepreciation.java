/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cbs.ho.deadstock.tables;

import java.io.Serializable;

/**
 *
 * @author admin
 */
public class DeadStockDepreciation implements Serializable{
 private double depAmt;
 private double depPre;
 private double orignalCost;
 private double bookValue;
 private String itemdstncno;
 private String itemCode;
 private String itemName;
 private double depAmtPrevious;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
 
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
 

    public String getItemdstncno() {
        return itemdstncno;
    }

    public void setItemdstncno(String itemdstncno) {
        this.itemdstncno = itemdstncno;
    }
  
    public double getBookValue() {
        return bookValue;
    }

    public void setBookValue(double bookValue) {
        this.bookValue = bookValue;
    }

    public double getDepAmt() {
        return depAmt;
    }

    public void setDepAmt(double depAmt) {
        this.depAmt = depAmt;
    }

    public double getDepPre() {
        return depPre;
    }

    public void setDepPre(double depPre) {
        this.depPre = depPre;
    }

    public double getOrignalCost() {
        return orignalCost;
    }

    public void setOrignalCost(double orignalCost) {
        this.orignalCost = orignalCost;
    }

    public double getDepAmtPrevious() {
        return depAmtPrevious;
    }

    public void setDepAmtPrevious(double depAmtPrevious) {
        this.depAmtPrevious = depAmtPrevious;
    }
    
}
