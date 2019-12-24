/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.email;

import com.cbs.dto.report.AccontDetailList;
import com.cbs.dto.report.MailingDetailsPojo;
import com.cbs.dto.report.ServiceDetailPojo;
import com.cbs.email.service.MailCustomerInfo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface EmailMgmtFacadeRemote {

    public void emailProcess();

    public MailCustomerInfo getAccForEnableServices(String function, String serviceType,
            String custId) throws ApplicationException;

    public String enableModifyAccountServeces(MailCustomerInfo accList, String function,
            String enterBy, String currentDate) throws ApplicationException;

    public List gridDetailForVerifyEnableService(String function, String acNo, String custId) throws ApplicationException;

    public List gridDetailForEnableService(String Date) throws ApplicationException;

    public String verifyEnableServicesDetail(AccontDetailList obj, String user, String Date) throws ApplicationException;

    public List<ServiceDetailPojo> getServiceRegistrationData(String brnCode, String frdt, String todt,String serviceType, String mode) throws ApplicationException;

    public List<ServiceDetailPojo> getIndividualServiceDetail(String brnCode, String acno, String custid, String serviceType) throws ApplicationException;

    public List<MailingDetailsPojo> getIndividualMailingDetail(String brnCode,String custid, String serviceType, String frDt, String toDt,String mode) throws ApplicationException;
}
