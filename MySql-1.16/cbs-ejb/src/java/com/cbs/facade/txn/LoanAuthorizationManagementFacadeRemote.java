/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.txn;

import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface LoanAuthorizationManagementFacadeRemote {

    public List getAccountNoInformation(String BRCODE) throws ApplicationException;

    public List getAccountNoAuthData(String acno, String todayDate) throws ApplicationException;

    public String authorizeAction(String acno, String enterBy, String user) throws ApplicationException;

    public List getListAccountsStock(String BRCODE) throws ApplicationException;

    public List getListAccountAuth(String acno, int seqNo) throws ApplicationException;

    public String authorizeAction(String acno, String seqNo, String enterBy, String user, String brCode) throws ApplicationException;

    public List gridLoad(String brCode) throws ApplicationException;

    public String lockerIssueAuthorization(String lockerType, String cabNo, String lockerNo, String enterBy, String brcode) throws ApplicationException;
    
    public String lockerIssueDeletion(String lockerType, String cabNo, String lockerNo, String enterBy, String brcode) throws ApplicationException ;
    
    public List getTotLienOfAccount (String acno) throws ApplicationException;
}
