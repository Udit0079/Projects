/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.npa;

import com.cbs.dto.npa.NPAChargesPojo;
import com.cbs.dto.npa.NPAReminderPojo;
import com.cbs.dto.report.OverdueEmiReportPojo;
import com.cbs.dto.report.OverdueRemainderPojo;
import com.cbs.exception.ApplicationException;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface NPAFacadeRemote {
    
   public int insertNPAChargeMaster(String acNature,String acType, String charge, String effFromDt, String effToDt, String glHead, double amount, String enterBy)throws ApplicationException;
   public List<NPAChargesPojo> getNPAChargesMaster(String branchCode)throws ApplicationException; 
   public String getGlName(String acno)throws ApplicationException;
   public boolean updateNPAChargeMaster(List<NPAChargesPojo> chargeList, int index, String effFromDt, String effToDt, String glHead, double amount, String updateBy, String updateDt)throws ApplicationException;
   public boolean isExistCharge(NPAChargesPojo pojo)throws ApplicationException;
   public BigDecimal getAmount(String acNumber, String chargeName, String date, String glHead)throws ApplicationException;
   public String getGlHead(String acNumber, String chargeName, String date) throws ApplicationException;
   public int insertNPAOverDue(List<OverdueEmiReportPojo> list, double amount,String charge, String dt, boolean allChecked, String enterBy,String branchCode)throws ApplicationException;
   public List<OverdueEmiReportPojo> getNPAOverDue(String branchCode)throws ApplicationException;
   public List<NPAReminderPojo> printOverDueReminder(List<OverdueEmiReportPojo> list,String overDueRem,String dt,boolean allChecked, String enterBy, String branchCode)throws ApplicationException;
   public boolean isExistOverDueReminder(String acNo, String rem, String monthStartDt, String monthEndDt)throws ApplicationException;
   public List getBankDistCityPin(String branchCode)throws ApplicationException;
   public List getGranterInfo(String acNo)throws ApplicationException;

    public List<OverdueRemainderPojo> getoverdueRemainderData(String acno, String dt, String brnCode,String genDate,String UserName) throws ApplicationException;

    public String insertionRefNo(String fileNo, String genDate, String refNo, String UserName, String brnCode) throws ApplicationException;
}
