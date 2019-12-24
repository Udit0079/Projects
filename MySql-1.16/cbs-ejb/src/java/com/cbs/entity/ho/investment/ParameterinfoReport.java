/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.ho.investment;

import com.cbs.entity.BaseEntity;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sipl
 */
@Entity
@Table(name = "parameterinfo_report")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParameterinfoReport.findAll", query = "SELECT p FROM ParameterinfoReport p"),
    @NamedQuery(name = "ParameterinfoReport.findByReportName", query = "SELECT p FROM ParameterinfoReport p WHERE p.reportName = :reportName"),
    @NamedQuery(name = "ParameterinfoReport.findByCode", query = "SELECT p FROM ParameterinfoReport p WHERE p.code = :code")})
public class ParameterinfoReport extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "ReportName")
    private String reportName;
    @Column(name = "Code")
    private Short code;

    public ParameterinfoReport() {
    }

    public ParameterinfoReport(String reportName) {
        this.reportName = reportName;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public Short getCode() {
        return code;
    }

    public void setCode(Short code) {
        this.code = code;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reportName != null ? reportName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParameterinfoReport)) {
            return false;
        }
        ParameterinfoReport other = (ParameterinfoReport) object;
        if ((this.reportName == null && other.reportName != null) || (this.reportName != null && !this.reportName.equals(other.reportName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.ho.investment.ParameterinfoReport[ reportName=" + reportName + " ]";
    }
}
