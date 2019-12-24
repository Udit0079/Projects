/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.report.LoanMisCellaniousPojo;
import com.cbs.dto.report.PrioritySectorPojo;
import com.cbs.dto.report.ho.Form1UnencumAppSecPojo;
import com.cbs.dto.report.ho.Form2StmtUnSecAdvToDirFirmPojo;
import com.cbs.exception.ApplicationException;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface RbiReportFacadeHalfYearlyRemote {
    
    public List<Form2StmtUnSecAdvToDirFirmPojo> getForm2StmtUnSecAdvToDirFirm(String reportDt, String orgnCode, double repOpt) throws ApplicationException ;
    
    public List<PrioritySectorPojo> getPrioritySector(String reportName, String reportDt, String orgnCode, BigDecimal repOpt) throws ApplicationException;
    
    public List<PrioritySectorPojo> getPrioritySectorOSS(String reportName, String reportDt, String orgnCode, BigDecimal repOpt) throws ApplicationException;
    
    public List<PrioritySectorPojo> getMinorityCommCreditFlow(String reportName, String fromDt, String reportDt, String orgnCode, BigDecimal repOpt) throws ApplicationException ;
    
    public List<PrioritySectorPojo> getValueFromFormula1(List<PrioritySectorPojo> dataList, String formula) throws ApplicationException ;
    
    public List<PrioritySectorPojo> getOperandBalance1(List<PrioritySectorPojo> dataList, String csvExp) throws ApplicationException ;
    
    public List<LoanMisCellaniousPojo> getAdvToDirRelative(String branchCode, String dt,BigDecimal rptOpt) throws ApplicationException;
    
    public List<Form1UnencumAppSecPojo> getUnencumberedApprovedSecurities(String dt, BigDecimal repOpt) throws ApplicationException;
}
