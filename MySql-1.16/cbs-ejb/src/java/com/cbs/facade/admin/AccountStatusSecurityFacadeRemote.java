/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.admin;

import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Ankit Verma
 */
@Remote
public interface AccountStatusSecurityFacadeRemote {

    public List lienAcNo() throws ApplicationException;

    public List getCustNameAndStatus(String Acno, String acctType) throws ApplicationException;

    public List getStatusHistory(String Acno) throws ApplicationException;

    public List returnTableValues(String acno) throws ApplicationException;

    public List getAllStatusList() throws ApplicationException;

    public String cbsSaveAcctStatus(String acno, String strRemarks, String authby, String newStatus, String dt, float lienAmt, String lienAcNo) throws ApplicationException;

    public List dateDiffWefDate(String wefDate) throws ApplicationException;

    public String saveSecurityDetail(String acno, String SecurityNature, String securityType, String status, String securityDesc1, String securityDesc2, String securityDesc3, String particulars, String otherAc, Float matValue, String matDate, String estimationDt, Float lienValue, String enteredBy, String Remarks, String entryDate, Float Margin, String Auth, String groupCode, String secODRoi,String secODScheme) throws ApplicationException;

    public List getSecurityTableValues(String acno) throws ApplicationException;

    public String deleteSecurityTable(String EntryDate, String todayDate, String acno, Integer sno) throws ApplicationException;

    public String acctNature(String acno) throws ApplicationException;

    public List toUpdate(String acno, Integer sno) throws ApplicationException;

    public String UpdateSecurity(String acno, String SecurityNature, String securityType, String status, String securityDesc1, String securityDesc2, String securityDesc3, String particulars, String otherAc, Float matValue, String matDate, String estimationDt, Float lienValue, String enteredBy, String Remarks, String entryDate, Float Margin, Integer sno, String groupCode) throws ApplicationException;

    public String UpdateSecurityTable(String expiredBy, String ExpiryDate, String acno, Integer sno) throws ApplicationException;

    public String SecurityCode(String secType, String secDesc2, String secDesc3) throws ApplicationException;

    public List getStatus() throws ApplicationException;
    
    public String lienMarked(String acno, String strRemarks, String authby, String newStatus, String dt,
            float lienAmt, String lienAcNo, String secODRoi, String secODScheme, String margin) throws ApplicationException;

   
    
}
