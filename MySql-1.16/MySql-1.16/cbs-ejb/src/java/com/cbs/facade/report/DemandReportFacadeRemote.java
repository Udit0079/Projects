/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.report.OverdueEmiReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.DemandOefPojo;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Admin
 */
@Remote
public interface DemandReportFacadeRemote {

    public List<DemandOefPojo> getOefDemandRecoveriesData(String area, String tillDate, String brCode, String range) throws ApplicationException;

    public String[] getInstallmentAmt(String acNo, String tillDt, double outSt, String acNature) throws ApplicationException;

    public String generateExcelPersonalNoWise(String placeOfWorking, String brncode, String firstDt, String generationDt, String enterBy, String range) throws ApplicationException;
    
    public List<OverdueEmiReportPojo> getDueLoanRecoveryData(String branch,String frDt,String days) throws ApplicationException;
}
