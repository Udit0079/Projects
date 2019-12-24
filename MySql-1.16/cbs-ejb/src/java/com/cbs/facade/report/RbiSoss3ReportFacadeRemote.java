/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.report.Soss3PortFolioPojo;
import com.cbs.exception.ApplicationException;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface RbiSoss3ReportFacadeRemote {
    
    public List<Soss3PortFolioPojo> getSOSS3(String reptName, String dt, String orgnCode, BigDecimal repOpt) throws ApplicationException;

    public BigDecimal getSoss3RefernceBal(String reptName, String dt, String orgnCode, BigDecimal repOpt) throws ApplicationException ;
    
    
    
}
