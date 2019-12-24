/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.report.ProfitAndLossPojo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface SecondaryGlReportFacadeRemote {

    public List<ProfitAndLossPojo> getComperativePLDetails(String brncode, String firstDt, String secondDt,
            String option) throws ApplicationException;
}
