package com.cbs.facade.report;

import com.cbs.dto.report.AverageVoucherReportDailyPojo;
import com.cbs.dto.report.SearchReportPojo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface AuditReportFacadeRemote {

    public List<SearchReportPojo> searchReport(String cboEnterBy, String checkBoxEnterBy, String cboAuthBy, String chBoxAuth1, String cmbTranType, String checkBoxTranType1, String cmbAmtType, String checkBoxAMType1, String cmbInstrumentNo, String chekboxInstrumentNo1, double FromAmount, double ToAmount, String FromDate, String ToDate, String acCode) throws ApplicationException;

    public List<AverageVoucherReportDailyPojo> getAverageVoucherDailyReport(String fromDate, String toDate, String brCode) throws ApplicationException;

    public List<AverageVoucherReportDailyPojo> getAverageVoucherQuarterlyReport(int quarter, int year, String brCode) throws ApplicationException;

    public List getLevelId(String brnCode) throws ApplicationException;
}
