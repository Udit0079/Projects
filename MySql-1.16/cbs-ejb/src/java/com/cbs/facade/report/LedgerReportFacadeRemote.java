package com.cbs.facade.report;

import com.cbs.dto.report.CashierCashPojo;
import com.cbs.dto.report.CbsCashClgTimewisePojo;
import com.cbs.dto.report.CbsCashTrfClgScrollTimewisePojo;
import com.cbs.dto.report.DayBookPojo;
import com.cbs.dto.report.IncomeExpenditurePojo;
import com.cbs.dto.report.IncomeExpenditureStTimePojo;
import com.cbs.dto.report.LedgerReportPojo;
import com.cbs.dto.report.SubDailyReportPojo;
import com.cbs.dto.report.LongBookReportPojo;
import com.cbs.dto.report.MiscLongBookReportPojo;
import com.cbs.dto.report.MiscLongBookSubTotalReportPojo;
import com.cbs.dto.report.MonthlyDepositProgressReportPojo;
import com.cbs.dto.report.TokenBookPojo;
import com.cbs.dto.report.TransactionCountPojo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface LedgerReportFacadeRemote {

    public List<IncomeExpenditurePojo> incomeExpenditure(String dt, String brCode) throws ApplicationException;

    public List<IncomeExpenditureStTimePojo> incomeExpenditureStTime(String date, String brnCode, String fromTime, String toTime, boolean timeAllowed) throws ApplicationException;

    public List<SubDailyReportPojo> subDailyReport(String brnCode, String date, String optType, String optDType) throws ApplicationException;

    public List<LongBookReportPojo> getLongBookReport(String brCode) throws ApplicationException;

    public List<MonthlyDepositProgressReportPojo> getMonthlyDepositProgressReport(String fDate, String brCode, String repType) throws ApplicationException;

    public List<MiscLongBookSubTotalReportPojo> getMiscLongBookSubTotalReport(String date, String brCode, String fromTime, String toTime, boolean timeAllowed) throws ApplicationException;

    public List<MiscLongBookReportPojo> getMiscLongBookReport(String date, String brCode, String orderBy);

    public List<LedgerReportPojo> getLedgerReport(String acType, String fromAccount, String toAccount, String fromDate, String toDate, String brCode) throws ApplicationException;

    public List<CbsCashClgTimewisePojo> getCbsCashClgScrollData(String userDate, String scrollType,
            String orgBrnCode, String fromTime, String toTime, String timeAllowed, String orderBy) throws ApplicationException;

    public List<CbsCashTrfClgScrollTimewisePojo> getCbsCashTrfClgScrollData(String userDate, String scrollType,
            String orgBrnCode, String fromTime, String toTime, String timeAllowed, String orderby, String optRep) throws ApplicationException;

    public List<DayBookPojo> getDayBookReportData(String userDate, String orgBrnCode, String exCounterInclude) throws ApplicationException;

    public java.util.List getAllAccounType() throws com.cbs.exception.ApplicationException;

    public List<CashierCashPojo> getReceivePaymentData(String enterBy, String asOnDt, String type, String brCode) throws ApplicationException;

    public List<TokenBookPojo> getTokenBookData(String tokenPaidBy, String asOnDt, String type, String brCode) throws ApplicationException;

    public List<TransactionCountPojo> getTransactionCountData(String frDate, String toDate,
            String orgBrnCode, String fromTime, String toTime, String timeAllowed) throws ApplicationException;

    public List<CbsCashTrfClgScrollTimewisePojo> getMismatchTransferScrollData(String orgBrnCode,String dt,String auth) throws ApplicationException;
}
