/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.pojo.admin;

import java.io.Serializable;

/**
 *
 * @author root
 */
public class KccLandHoldingTable implements Serializable{

    private String seqNo;
    private String village;
    private String blockNo;
    private String ownedLand;
    private String leased;
    private String cropper;
    private String areaAcres;
    private String irrigated;
    private String sourceOfIrrigation;
    private String incumbrance;

    public String getAreaAcres() {
        return areaAcres;
    }

    public void setAreaAcres(String areaAcres) {
        this.areaAcres = areaAcres;
    }

    public String getBlockNo() {
        return blockNo;
    }

    public void setBlockNo(String blockNo) {
        this.blockNo = blockNo;
    }

    public String getCropper() {
        return cropper;
    }

    public void setCropper(String cropper) {
        this.cropper = cropper;
    }

    public String getIncumbrance() {
        return incumbrance;
    }

    public void setIncumbrance(String incumbrance) {
        this.incumbrance = incumbrance;
    }

    public String getIrrigated() {
        return irrigated;
    }

    public void setIrrigated(String irrigated) {
        this.irrigated = irrigated;
    }

    public String getLeased() {
        return leased;
    }

    public void setLeased(String leased) {
        this.leased = leased;
    }

    public String getOwnedLand() {
        return ownedLand;
    }

    public void setOwnedLand(String ownedLand) {
        this.ownedLand = ownedLand;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getSourceOfIrrigation() {
        return sourceOfIrrigation;
    }

    public void setSourceOfIrrigation(String sourceOfIrrigation) {
        this.sourceOfIrrigation = sourceOfIrrigation;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }
    


}
