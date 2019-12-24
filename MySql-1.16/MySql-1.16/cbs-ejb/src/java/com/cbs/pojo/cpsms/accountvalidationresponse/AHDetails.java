package com.cbs.pojo.cpsms.accountvalidationresponse;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.util.List;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author root
 */
public class AHDetails {
    private List<AH> ahDtl;

    public List<AH>  getAhDtl() {
        return ahDtl;
    }
    @XmlElement(namespace = "",name="AH")
    public void setAhDtl(List<AH>  ahDtl) {
        this.ahDtl = ahDtl;
    }
    
}
