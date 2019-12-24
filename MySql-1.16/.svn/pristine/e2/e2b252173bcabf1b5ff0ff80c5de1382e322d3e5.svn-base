/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.report.RbiAlmPojo;
import com.cbs.dto.report.RbiSossPojo;
import com.cbs.dto.report.RevenueStatementPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.BucketWiseInvestmentPojo;
import com.cbs.pojo.GlHeadPojo;
import java.math.BigDecimal;
import javax.ejb.Remote;
import java.util.List;

/**
 *
 * @author root
 */
@Remote
public interface ALMQtrReportFacadeRemote {
    public List hoAlm(String date, String brCode, String repOpt) throws ApplicationException;
    
    public List getAlmBucketMaster(String profileParameter, String dt, String bktNo)  throws ApplicationException;
    
    public List getAlmAccMaster(String profileParameter, String headNo, String dt)  throws ApplicationException;
    
    public List<BucketWiseInvestmentPojo> getBucketWiseInvestmentdataForALM(String report, String toDt, String profileParameter) throws ApplicationException;
    
    public List<RbiAlmPojo> hoAlmDetailsSummary(String reptName, String dt, String orgnCode, Double repOpt, String summaryOpt, String profileParameter) throws ApplicationException;
    
    public List hoAlmInterestSensitivty(String date, String brCode, String repOpt) throws ApplicationException;    
}
