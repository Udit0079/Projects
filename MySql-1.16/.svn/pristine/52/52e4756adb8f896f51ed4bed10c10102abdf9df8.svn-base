/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.report.EquifaxComercialPoJo;
import com.cbs.exception.ApplicationException;
import javax.ejb.Remote;
import java.util.List;

/**
 *
 * @author SAMY
 */
@Remote
public interface EquifaxReportFacadeRemote {

    public List<EquifaxComercialPoJo> comercialEquifaxReport(String memberCode, String fromDate, String toDate, String todaydate, String reportType) throws ApplicationException;
}
