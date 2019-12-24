/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.master;

import com.cbs.dao.GenericDAO;
import com.cbs.entity.ho.investment.ParameterinfoReport;
import com.cbs.exception.ApplicationException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author root
 */
public class ParameterinfoReportDAO extends GenericDAO {

    public ParameterinfoReportDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public ParameterinfoReport getCodeByReportName(String reportName) throws ApplicationException {
        ParameterinfoReport obj = new ParameterinfoReport();
        try {
            Query createNamedQuery = entityManager.createNamedQuery("ParameterinfoReport.findByReportName");
            createNamedQuery.setParameter("reportName", reportName);
            obj = (ParameterinfoReport) createNamedQuery.getSingleResult();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return obj;
    }
}
