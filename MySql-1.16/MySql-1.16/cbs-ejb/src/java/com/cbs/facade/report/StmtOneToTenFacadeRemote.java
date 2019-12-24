/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.report.LoanMisCellaniousPojo;
import javax.ejb.Remote;
import com.cbs.exception.ApplicationException;
import java.math.BigDecimal;
import com.cbs.dto.report.RbiSos4Pojo;
import com.cbs.dto.report.RbiSossPojo;
import com.cbs.dto.report.Statement8PoJo;
import com.cbs.dto.report.Statement9PoJo;
import com.cbs.dto.report.ho.InterOfycPojo;
import com.cbs.dto.report.ho.Oss7BusinessPojo;
import com.cbs.pojo.Oss4PartACombinedPojo;
import java.util.List;
/**
 *
 * @author saurabhsipl
 */
@Remote
public interface StmtOneToTenFacadeRemote {    
    
    public List<RbiSossPojo> getStaementTwo(List<String> dates, BigDecimal repOpt, String reportName, String orgnCode,String reportIn, String summaryOption) throws ApplicationException ;   
    
    public List<Oss4PartACombinedPojo> getStaementFourPartAAndB(List<String> dates, BigDecimal repOpt, String reportName, String orgnCode,String reportIn, String reportFormat) throws ApplicationException ;
    
    public List<RbiSos4Pojo> getStaementFivePartA(List<String> dates, BigDecimal repOpt, String reportName, String orgnCode, String reportIn, String reportFormat) throws ApplicationException; 
    
    public List<RbiSos4Pojo> getStaementFive(List<String> dates, BigDecimal repOpt, String reportName, String orgnCode,String reportIn, String reportFormat) throws ApplicationException ;
    
    public List<RbiSos4Pojo> getStaementSix(List<String> dates, BigDecimal repOpt, String reportName, String orgnCode,String reportIn, String reportFormat) throws ApplicationException ;
    
    public List<LoanMisCellaniousPojo> getStaementSeven(List<String> dates, BigDecimal repOpt, String reportName, String orgnCode,String reportIn, String reportFormat) throws ApplicationException ;
    
    public List<Statement8PoJo> getStaementEight(List<String> dates, BigDecimal repOpt, String reportName, String orgnCode,String reportIn, String reportFormat) throws ApplicationException ;
    
    public List<Statement9PoJo> getStaementNine(List<String> dates, BigDecimal repOpt, String reportName, String orgnCode,String reportIn, String reportFormat) throws ApplicationException ;
    
    public List<Oss7BusinessPojo> getStaementTen(List<String> dates, BigDecimal repOpt, String reportName, String orgnCode,String reportIn, String reportFormat) throws ApplicationException ;
    
    public List<InterOfycPojo> getInterOfycMemoDetail(String acno, String fromDate, String ToDate) throws ApplicationException ;
    
    List misSheetPart5Data (List osBlancelist,String reportName,BigDecimal repOpt,List<String> dates, String orgnCode) throws Exception ;
    
    List misSheetPart6Data (List osBlancelist,String reportName,BigDecimal repOpt,List<String> dates, String orgnCode) throws Exception ;
    
    public List misSheetPart1Data (String fromDt,String toDt,BigDecimal repOpt) throws Exception ;
    
}