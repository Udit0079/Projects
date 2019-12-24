/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.report.CibilComPoJo;
import com.cbs.dto.report.ExperionPojo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author SAMY
 */
@Remote
public interface CibilReportFacadeRemote {

    public List<ExperionPojo> cibilReport(String memberCode, String fromDate, String toDate, String todaydate, String reportType, String cibilParameter) throws ApplicationException;

    public CibilComPoJo cibilComercial(String memberCode, String fromDate, String toDate, String todaydate, String reportType, String cibilParameter) throws ApplicationException;

    public String cibilReportConsumerText(String memberCode, String fromDate, String toDate, String todaydate, String reportType, String path, String fileName, String cibilParameter) throws ApplicationException;

    public String cibilComercialDelimitedFormat(String memberCode, String fromDate, String toDate, String todaydate, String reportType, String cibilParameter, String path, String cibilfileName,String repType) throws ApplicationException;
}
