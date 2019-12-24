/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.ho.investment;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "codebook")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Codebook.findAll", query = "SELECT c FROM Codebook c"),
    @NamedQuery(name = "Codebook.findByGroupCode", query = "SELECT c FROM Codebook c WHERE c.codebookPK.groupCode = :groupCode"),
    @NamedQuery(name = "Codebook.findByCode", query = "SELECT c FROM Codebook c WHERE c.codebookPK.code = :code"),
    @NamedQuery(name = "Codebook.findByDescription", query = "SELECT c FROM Codebook c WHERE c.description = :description"),
    @NamedQuery(name = "Codebook.findByFlag", query = "SELECT c FROM Codebook c WHERE c.flag = :flag")})
public class Codebook extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CodebookPK codebookPK;
    @Size(max = 60)
    @Column(name = "Description")
    private String description;
    @Size(max = 1)
    @Column(name = "FLAG")
    private String flag;

    public Codebook() {
    }

    public Codebook(CodebookPK codebookPK) {
        this.codebookPK = codebookPK;
    }

    public Codebook(short groupCode, short code) {
        this.codebookPK = new CodebookPK(groupCode, code);
    }

    public CodebookPK getCodebookPK() {
        return codebookPK;
    }

    public void setCodebookPK(CodebookPK codebookPK) {
        this.codebookPK = codebookPK;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codebookPK != null ? codebookPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Codebook)) {
            return false;
        }
        Codebook other = (Codebook) object;
        if ((this.codebookPK == null && other.codebookPK != null) || (this.codebookPK != null && !this.codebookPK.equals(other.codebookPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.ho.investment.Codebook[ codebookPK=" + codebookPK + " ]";
    }
}
