/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.report.LoanIntDetailsMonthWisePojo;
import com.cbs.dto.report.NpaAccountDetailPojo;
import com.cbs.dto.report.OverdueEmiReportPojo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

/**
 *
 * @author SAMY
 */
@Remote
public interface OverDueReportFacadeRemote {
    
    public List<OverdueEmiReportPojo> getOverdueEmiReport(int repType, int codeType, String sector, String acCode, int emiPendingfrom, int emiPendingto, String tDate, String brCode, String status,Boolean chkBox,String acNo,String withOutNpa) throws ApplicationException;
       
    public List<OverdueEmiReportPojo> getOverdueEmiList(String sector, String accountNumber, int emiPendingfrom, int emiPendingto, String tDate, String brCode, String overdueParameter, int overDueFlag,int isExcessEmi,Map<String, String> mapMor,int morOnOf) throws ApplicationException;
    
    public List getOverDueNonEmi(String acNature, String acType, String transDt, String brCode,String withOutNpa) throws ApplicationException;
    
    public List<NpaAccountDetailPojo> getNpaDetail(String acctNature, String acctType, String tillDate, String brCode,String parameter) throws ApplicationException;

    public List<NpaAccountDetailPojo> getProbableNpaDetail(String acctNature, String acctType1, String tillDate, String brCode) throws ApplicationException;
    
    public List<LoanIntDetailsMonthWisePojo>  getAccountDetailsMonthWise(String brnCode,String acType,String frDt,String toDt,String schemeType) throws ApplicationException;
    
    public List<NpaAccountDetailPojo> getNpaDetailOptimized(String acctNature, String acctType1, String tillDate, String brCode, String parameter,String excelParameter) throws ApplicationException;
}
