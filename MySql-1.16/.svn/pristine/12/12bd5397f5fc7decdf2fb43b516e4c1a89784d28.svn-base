/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.report.Oss4PartGPojo;
import com.cbs.dto.report.PrioritySectorPojo;
import com.cbs.dto.report.RbiSos4Pojo;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.AcctBalPojo;
import com.cbs.pojo.AdditionalStmtPojo;
import com.cbs.pojo.NpaStatusReportPojo;
import com.cbs.pojo.Oss4PartACombinedPojo;
import com.cbs.pojo.TopLoanPojo;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface RbiReportPartDFacadeRemote {

    public List<RbiSos4Pojo> getPartD(String reportDt, String orgnCode, BigDecimal repOpt) throws ApplicationException;
    
    public List<RbiSos4Pojo> getIndustryWiseExposure(String reptName,String reportDt, String orgnCode, BigDecimal repOpt) throws ApplicationException;
    
    public List<Oss4PartACombinedPojo> getSOS4PartA(String reportDt, String orgnCode, BigDecimal repOpt,List osBlancelist) throws ApplicationException;
    
    public List<RbiSos4Pojo> getSOS4PartE(String reportDt, String orgnCode, BigDecimal repOpt) throws ApplicationException;
    
    public List<RbiSos4Pojo> getSOS4PartF(String reportDt, String orgnCode, BigDecimal repOpt, String summaryOpt) throws ApplicationException;
    
    public List<TopLoanPojo> getSoss4PartB4(String reportDt, String orgnCode, BigDecimal repOpt) throws ApplicationException;
    
    public List<TopLoanPojo> getSoss4PartC1(String reportDt, String orgnCode, BigDecimal repOpt,String borrowerType,List osBlancelist) throws ApplicationException;
    
    public AcctBalPojo getAmountBasedOnNpaClassification(AdditionalStmtPojo params) throws ApplicationException;
    
    public AcctBalPojo getAmountBasedOnNpaClassificationForOss4(AdditionalStmtPojo params, List<NpaStatusReportPojo> resultList) throws ApplicationException;
    
    public List<Oss4PartGPojo> getSOS4PartG(String reportDt, String orgnCode, BigDecimal repOpt) throws ApplicationException;
    
    public List<PrioritySectorPojo> getPrioritySector(String reportDt, String orgnCode, BigDecimal repOpt) throws ApplicationException;
}
