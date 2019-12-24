package com.cbs.facade.report;

import com.cbs.dto.report.EquifaxComercialPoJo;
import com.cbs.dto.report.ExperionPojo;
import javax.ejb.Remote;
import com.cbs.exception.ApplicationException;
import java.util.List;
/**
 *
 * @author root
 */
@Remote
public interface ExperionLoanFacadeRemote {
    public List<ExperionPojo> experionReport(String membeerCode,String fromDate, String toDate,String todaydate, String cibilParameter) throws ApplicationException;
    
//    public List<ExperionPojo> cibilReport(String memberCode, String fromDate, String toDate, String todaydate,String reportType, String cibilParameter) throws ApplicationException ;
    
//    public List<ExperionPojo> cicReport(String memberCode, String fromDate, String toDate, String todaydate) throws ApplicationException ;
//    
//    public List<EquifaxComercialPoJo> comercialEquifaxReport(String memberCode, String fromDate, String toDate, String todaydate,String reportType) throws ApplicationException ;
//    
    public List<EquifaxComercialPoJo> comercialExperianReport(String memberCode, String fromDate, String toDate, String todaydate,String reportType) throws ApplicationException ;
}