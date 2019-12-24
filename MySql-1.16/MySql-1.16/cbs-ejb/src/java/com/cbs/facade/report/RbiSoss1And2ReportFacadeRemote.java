/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.report.RbiSoss1AnnexI2;
import com.cbs.dto.report.RbiSoss1AnnexI3And4;
import com.cbs.dto.report.RbiSoss1AnnexI5;
import com.cbs.dto.report.RbiSoss1AnnexII;
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
public interface RbiSoss1And2ReportFacadeRemote {
    
    public List<RbiSossPojo> getSOSS1(String reptName, String dt, String orgnCode, BigDecimal repOpt, String summaryOpt) throws ApplicationException;

    public List<RbiSossPojo> getSOSS2(String reptName, String dt, String orgnCode, BigDecimal repOpt, String summaryOpt, List osBlancelist,String numberOfEmp) throws ApplicationException;
            
    public List<RbiSoss1AnnexI2> getSOSS1AnnexI2(String reptName, String dt, String orgnCode, BigDecimal repOpt) throws ApplicationException;

    public List<RbiSoss1AnnexI3And4> getSOSS1AnnexI3And4(String reptName, String dt, String orgnCode, BigDecimal repOpt) throws ApplicationException;

    public List<RbiSoss1AnnexI5> getSOSS1AnnexI5(String reptName, String dt, String orgnCode, BigDecimal repOpt) throws ApplicationException;

    public List<RbiSoss1AnnexII> getSOSS1AnnexII(String reptName, String dt, String orgnCode, BigDecimal repOpt) throws ApplicationException;

}
