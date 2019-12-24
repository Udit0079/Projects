package com.cbs.facade.report;

import com.cbs.dto.report.LockerInventoryReportPojo;
import com.cbs.dto.report.LockerRentReportPojo;
import com.cbs.dto.report.LockerStatementReportPojo;
import com.cbs.dto.report.LockerSurrenderReportPojo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface LockerReportFacadeRemote {

    public List<LockerStatementReportPojo> getLockerStatementReport(String lockerType, double cabNo, double lockerNo, String brCode)throws ApplicationException;

    public List<LockerSurrenderReportPojo> getLockerSurrenderReport(String option, String cabNo, String brCode,String frDt,String toDt)throws ApplicationException;

    public List<LockerRentReportPojo> getLockerRentReport(String reportType, String brCode, String cabNo, int ordBy,String status,String frDt,String toDt)throws ApplicationException;

    public List<LockerInventoryReportPojo> getLockerInventoryReport(String option, String cabNo, String brCode,String frDt,String toDt,String status)throws ApplicationException;

    public List getLockerCabinetNumbers(String brnCode)throws ApplicationException;

    public List getLockerCabinetNumbersByLockerType(String lockerType, String brnCode)throws ApplicationException;
    
    public List getLockerNoByLockerTypeAndCabNo(String lockerType,String cabNo,String brCode)throws ApplicationException;
    
}
