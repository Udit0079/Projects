package com.cbs.facade.mis;

import com.cbs.exception.ApplicationException;
import com.cbs.pojo.mis.MarketingMisPojo;
import com.cbs.pojo.mis.TaskLeadTargetPojo;
import java.util.List;
import com.cbs.pojo.mis.TaskManagementPojo;
import com.cbs.pojo.mis.TaskUpdationPojo;
import java.math.BigInteger;

public interface TaskManagementRemoteFacade {

    public List getCustomerDetailsbyAccountno(String accountNo) throws ApplicationException;

    public List getUtilityReportDetailsforTaskManagement(String brnCode, String remarks, String frDate, String toDate, String searchBy, String searchByOption) throws ApplicationException;

    public String insertDataTaskManagement(List<TaskManagementPojo> taskManagement, String sourceOfData, String sourceOfMarketing,
            String origin, String branchcode, String remarks, String from_date, String to_date, String purpose, String purpose_desc,
            String status, String userToBeAssigned, String EnquiryType, String followUpDate, String customerRemark, String user, String destBrncode, String originbyFilter) throws ApplicationException;

    public List<TaskManagementPojo> getTaskManagementReport(String dataSource, String sourceOfMarketing, String frmdt, String toDt, String orgnBrnCode, String branch) throws Exception;

    public List getDataByUserName(String user, String orgnBrcode, String branch, String userAssigned, String levelId) throws ApplicationException;

    public List getTaskInformationByDetail(String detailId) throws ApplicationException;

    public String updateDataTaskUpdation(List<TaskUpdationPojo> dataList, String purpose, String purpose_desc, String status,
            String userToBeAssigned, String EnquiryType, String followupDate, String followUptime, String customerRemarks,
            String user, String referredAccountNo, String accountOpeningDate, String personalVisitStaus, String personalVisitTime, String personalVisitDate, boolean checkBox) throws ApplicationException;

    public String insertDataLeadTarget(String orgn_brncode, String branch, BigInteger finacialYear, String purpose, String purposeDesc, String fromDate,
            String todate, BigInteger target, String assignedUser, String entryType, String username) throws ApplicationException;

    public List getTargetDetails(String branchCode, String branch, String enterType) throws ApplicationException;

    public List getTaskUpdationHistoryDetails(String detailId, String user) throws ApplicationException;

    public List getRefferedAccountNoDetail(String refferedAcno) throws Exception;

    public List<TaskUpdationPojo> getUpdationReportDataList(String frmdt, String todt, String branchCode, String userName, String levelId, String branch, String userassigend) throws Exception;

    public List<MarketingMisPojo> getMarketingMisReportData(String branch, String frmdt, String todt, String entryType, String followFrmdt, String followTodt, String reportType, String misType) throws Exception;

    public List<MarketingMisPojo> getStaffPerformanceMisReport(String branch, String frmdt, String todt, String entryType, String followFrmdt, String followTodt, String reportType, String misType) throws Exception;
    
    public List<MarketingMisPojo> getMktDataAsPerRequirement(String branch, String frmdt, String todt, String entryType, String followFrmdt, String followTodt, String reportType) throws Exception;

    public List getTargetCountbyPurpose(String branch, String frmdt, String todt) throws Exception;

    public String getLevelIdOfBranch(String orgnCode, String userId) throws Exception;

    public List getYearlyTargetHistoryDetails(String origncode) throws Exception;

    public List getCbsLeadMagmtDetails(String contactNo) throws Exception;

    public List<TaskLeadTargetPojo> getTargetReportData(String branch, String frmdt, String todt, String orignBrcode, String entryType) throws Exception;

    public String updateTaskLeadTarget(TaskLeadTargetPojo pojo, int target) throws Exception;

    public String updateFollowUpDateByBranchManager(String followUpId, String detailId, String followUpDate, String followUptime, String userName) throws Exception;

    public List getTotalTargetForTaskUpdation(String frmdt, String todt, String userToAssigned, String branch) throws Exception;
}
