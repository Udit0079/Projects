/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.report.RbiSossPojo;
import com.cbs.exception.ApplicationException;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface RbiOss8ReportFacadeRemote {
    
    public List<RbiSossPojo> getOss8Detail(String reportName,List<String> dates, String orgnCode, BigDecimal repOpt, int noOfEmp, double dividend, String summaryOpt) throws ApplicationException;
    
}
