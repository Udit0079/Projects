/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.admin.customer;

import com.cbs.dto.RelatedPersonsInfoTable;
import com.cbs.dto.customer.CBSCustMISInfoHisTO;
import com.cbs.dto.customer.CBSCustMinorInfoHisTO;
import com.cbs.dto.customer.CBSCustNREInfoHisTO;
import com.cbs.dto.customer.CBSCustomerMasterDetailHisTO;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface CustomerMgmtFacadeRemote {

    public String getNextRelatedPerson() throws ApplicationException;

    public List<RelatedPersonsInfoTable> getAllRelatedPersons(String customerId) throws ApplicationException;

    public String getBankCode() throws ApplicationException;

    public String isCustIdExistBasedOnPan(String function, String custId, String panNo) throws ApplicationException;

    public String isCustIdExistBasedOnAadhar(String function, String custId, String aadharNo) throws ApplicationException;

    public String isCustIdExistBasedOnNameFatherAndDob(String function, String custId, String custName,
            String fatherName, String dob) throws ApplicationException;

    public String retrieveCurrentCustomerStatus(String custId) throws ApplicationException;

    public boolean isActiveAcOnCustomer(String custId) throws ApplicationException;

    public CBSCustomerMasterDetailHisTO getCustomerLastChangeDetail(String customerid) throws ApplicationException;

    public CBSCustMinorInfoHisTO getCustomerLastChangeDetailForMinor(String customerid) throws ApplicationException;

    public CBSCustMISInfoHisTO getCustomerLastChangeDetailForMis(String customerid) throws ApplicationException;
    
    public CBSCustNREInfoHisTO getCustomerLastChangeDetailForNre(String customerid) throws ApplicationException;
}
