/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.AtmMasterGrid;
import com.cbs.dto.report.RbiSossPojo;
import com.cbs.dto.report.ho.BankProfilePojo;
import com.cbs.dto.report.ho.OrganisationalPojo;
import com.cbs.dto.report.ho.Oss7BusinessPojo;
import com.cbs.dto.report.ho.Oss8BusinessPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.AdditionalStmtPojo;
import com.cbs.pojo.ManagementDetailsPojo;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface RbiQuarterlyReportFacadeRemote {

    public List<AtmMasterGrid> getAtmProfileData() throws ApplicationException;

    public List<BankProfilePojo> getBankProfileData(Integer brnCode, BigDecimal repOpt, String reportDt) throws ApplicationException;

    public List<OrganisationalPojo> getOrganisationalProfile(String dt, BigDecimal repOpt) throws ApplicationException;

    public List<ManagementDetailsPojo> getManagementDetails(String reportDt) throws ApplicationException;

    public List<Oss8BusinessPojo> getOss8Details(String reportName, String reportDt, BigDecimal repOpt,
            String orgnBrCode, int noOfEmp, double dividend, String reportOption) throws ApplicationException;

    public List<Oss8BusinessPojo> getXBRL8Details(String reportName, String reportDt,
            BigDecimal repOpt, String orgnBrCode, int noOfEmp, double dividend, String reportOption) throws ApplicationException;
    
    public List<RbiSossPojo> getOss7PartBAndPartCSec2(String repName, String reportDt, String orgnCode,
            BigDecimal repOpt, String reportFormat, List osBlancelist) throws ApplicationException;

    public List<RbiSossPojo> getOss7PartCSection1(String repName, String reportDt, String orgnCode,
            BigDecimal repOpt, List osBlancelist) throws ApplicationException;

    public List<RbiSossPojo> getOss7PartCSection2i(String repName, String reportDt, String orgnCode,
            BigDecimal repOpt,List osBlancelist) throws ApplicationException;

    public List<RbiSossPojo> getOss7PartA(String repName, String reportDt, String orgnCode,
            BigDecimal repOpt, String reportFormat,List osBlancelist,String partBCParam) throws ApplicationException;

    public List<Oss7BusinessPojo> getOss7Details(String reportName, String reportDt,
            BigDecimal repOpt, String orgnBrCode, String reportFormat) throws ApplicationException;

    public List<Oss7BusinessPojo> getXBRLOss7Details(String reportName, String reportDt,
            BigDecimal repOpt, String orgnBrCode, String reportFormat) throws ApplicationException;
    
    public BigDecimal getAcCodeAmount(String accode, String dt) throws ApplicationException;
    
    public BigDecimal getFortnightlyAvgBal(AdditionalStmtPojo params) throws ApplicationException;
    
    public String getMinFinYear(String dt) throws ApplicationException;
    
    public BigDecimal getSectorWiseBal(AdditionalStmtPojo params, String fgno, String fsgno) throws ApplicationException;
    
    public BigDecimal getOsBal(AdditionalStmtPojo params) throws ApplicationException ;
    
    public String getFinYear(String dt) throws ApplicationException ;
}
