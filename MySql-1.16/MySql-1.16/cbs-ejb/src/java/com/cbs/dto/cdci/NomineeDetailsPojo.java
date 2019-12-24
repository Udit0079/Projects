/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.dto.cdci;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NomineeDetailsPojo", propOrder = {
    "nomname", "nomadd", "relation", "dob"})
public class NomineeDetailsPojo {
    private String nomname;
    private String nomadd   ;
    private String relation ;
    private Date dob;

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }


    public String getNomadd() {
        return nomadd;
    }

    public void setNomadd(String nomadd) {
        this.nomadd = nomadd;
    }

    public String getNomname() {
        return nomname;
    }

    public void setNomname(String nomname) {
        this.nomname = nomname;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }


}
