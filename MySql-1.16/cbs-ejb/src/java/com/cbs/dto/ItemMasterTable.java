/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.dto;

import java.io.Serializable;


/**
 *
 * @author Ankit Verma
 */
public class ItemMasterTable implements Serializable {
    private int sno;
    private int itemCode;
    private String itemName;
    private float depPercent;
    private String wefDt;
    private String enterBy;
    private String depOption;
    private double amtPerUnit;
    private int totalItems;
    private double totalAmount;
    private long itemDistinctiveSno;
    private String tranFlag;
    private  double itemSaleValue;
    private String originBranchId;
    private String destBrnid;
    private String depApplyUpto;
    private double originalCost;
    private double itemDepAmount;
    private String flag;

    public String getDepOption() {
        return depOption;
    }

    public void setDepOption(String depOption) {
        this.depOption = depOption;
    }

    public float getDepPercent() {
        return depPercent;
    }

    public void setDepPercent(float depPercent) {
        this.depPercent = depPercent;
    }
    public String getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(String enterBy) {
        this.enterBy = enterBy;
    }

    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getWefDt() {
        return wefDt;
    }

    public void setWefDt(String wefDt) {
        this.wefDt = wefDt;
    }

    public double getAmtPerUnit() {
        return amtPerUnit;
    }

    public void setAmtPerUnit(double amtPerUnit) {
        this.amtPerUnit = amtPerUnit;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public long getItemDistinctiveSno() {
        return itemDistinctiveSno;
    }

    public void setItemDistinctiveSno(long itemDistinctiveSno) {
        this.itemDistinctiveSno = itemDistinctiveSno;
    }

    public String getTranFlag() {
        return tranFlag;
    }

    public void setTranFlag(String tranFlag) {
        this.tranFlag = tranFlag;
    }

    public double getItemSaleValue() {
        return itemSaleValue;
    }

    public void setItemSaleValue(double itemSaleValue) {
        this.itemSaleValue = itemSaleValue;
    }

    public String getDepApplyUpto() {
        return depApplyUpto;
    }

    public void setDepApplyUpto(String depApplyUpto) {
        this.depApplyUpto = depApplyUpto;
    }

    public String getDestBrnid() {
        return destBrnid;
    }

    public void setDestBrnid(String destBrnid) {
        this.destBrnid = destBrnid;
    }

    public double getItemDepAmount() {
        return itemDepAmount;
    }

    public void setItemDepAmount(double itemDepAmount) {
        this.itemDepAmount = itemDepAmount;
    }

    public String getOriginBranchId() {
        return originBranchId;
    }

    public void setOriginBranchId(String originBranchId) {
        this.originBranchId = originBranchId;
    }

    public double getOriginalCost() {
        return originalCost;
    }

    public void setOriginalCost(double originalCost) {
        this.originalCost = originalCost;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
    
 
}
