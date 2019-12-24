/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.exception.ApplicationException;
import com.cbs.pojo.AllSmsPojo;
import com.cbs.pojo.HoReconsilePojo;
import com.cbs.pojo.IndividualSmsPojo;
import com.cbs.pojo.NpaCategoryReport;
import com.cbs.pojo.NpaRecoveryPojo;
import com.cbs.pojo.NpaSummaryPojo;
import com.cbs.pojo.NpaReportPojo;
import com.cbs.pojo.NpaStatusReportPojo;
import com.cbs.pojo.OfficeAccountPojo;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface NpaReportFacadeRemote {

    public List getAccountType() throws ApplicationException;

    public List<NpaRecoveryPojo> getNpaRecoveryData(String acType, String fromDt, String toDt, String orgnBrCode) throws ApplicationException;

    public List<HoReconsilePojo> getReconsiledEntry(String fromDt, String toDt, String orgnBrCode) throws ApplicationException;

    public List<NpaSummaryPojo> getNpaSummary(String brnCode,String toDt) throws ApplicationException;

    /**
     * Addition For Npa Status Report
     */
    public List getAcStatus() throws ApplicationException;

    public List getNpaReportAccountType() throws ApplicationException;

    public List<NpaStatusReportPojo> getNpaStatusReportData(String status, String acType, String asOnDt, String acStatus, String orgBrnCode) throws ApplicationException;

    public List<NpaStatusReportPojo> getNpaStatusReportDataOptimized(String status, String acType, String fromDt, String toDt, String acStatus, String orgBrnCode, String rbiParameter) throws ApplicationException;

    public List<NpaStatusReportPojo> getNpaStatusReportData1(String status, String acType, String fromDt, String toDt, String acStatus, String orgBrnCode, String rbiParameter) throws ApplicationException;

    public List<NpaStatusReportPojo> getNpaStatusReportData2(String status, String acType, String fromDt, String toDt, String acStatus, String orgBrnCode, String rbiParameter) throws ApplicationException;

    public List<NpaStatusReportPojo> getNpaStatusReportDataNew(String status, String acType, String fromDt, String toDt, String acStatus, String orgBrnCode) throws ApplicationException;

    public List<NpaReportPojo> getNpaReportData(String acType, String tillDt, String orgBrnCode) throws ApplicationException;

    public List<NpaRecoveryPojo> getNpaRecoveryReportData(String acType, String fromDt, String toDt, String orgnBrCode) throws ApplicationException;

    public List<NpaCategoryReport> getNpaCategoryReportData(String forQuarter, String year, String orgBrnCode) throws ApplicationException;

    public List<OfficeAccountPojo> getOfficeAccountTransaction(String officeAcno, String reportDt, String orgBrnCode) throws ApplicationException;

    public List<AllSmsPojo> getAllMessageAtHo(String fromdt, String todt,String reportType) throws ApplicationException;

    public List<IndividualSmsPojo> getIndividualMessageAtHoByAcno(String acno, String fromdt, String todt) throws ApplicationException;

    public List<AllSmsPojo> getIndividualMessageAtHoByBranch(String brncode, String fromdt, String todt) throws ApplicationException;

    public Integer getTotalSendMessageByAcno(String acno, String messageType, String fromdt, String todt) throws ApplicationException;

    public List<NpaRecoveryPojo> getNpaAutoMarkingData(String acType, String fromDt, String toDt, String orgnBrCode) throws ApplicationException;
}
