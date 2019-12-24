/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.report.ExperionPojo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author SAMY
 */
@Remote
public interface CicReportFacadeRemote {

    public List<ExperionPojo> cicReport(String memberCode, String fromDate, String toDate, String todaydate) throws ApplicationException;
}
