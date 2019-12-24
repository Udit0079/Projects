/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.txn;

import com.cbs.dto.report.LoanAcDetailStatementPojo;
import com.cbs.dto.report.NpaAccountDetailPojo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface AccountAuthorizationManagementFacadeRemote {

    public String authorizeAction(List list, String enterBy, String brcode) throws ApplicationException;

    public List closedActGridDetail(String brCode) throws ApplicationException;

    public String closeActCustName(String acno) throws ApplicationException;

    public List getListDetails(String brncode);

    public List getTableData(String acNo, String txnId);

    public String btnAuthorize(String acNo, String txnId, String user) throws ApplicationException;

    public List tableAuthorize(String orgnCode) throws ApplicationException;

    public String authorizeActionAccountStatus(Long spNo, String acNo, String effDt, String spFlag, String authorizedBy, String brcode, String dt, String oldStatus) throws ApplicationException;

    public List accountNoList(String brCode) throws ApplicationException;

    public List accountNature(String acctType) throws ApplicationException;

    public List loadGrid(String acctNo, String txnId, String brCode) throws ApplicationException;

    public String authorizeAcEdit(String acno, String txnId, String enterBy, String authBy) throws ApplicationException;

    public List accountNoOpenList(String brCode) throws ApplicationException;

    public List loadGrid(String acctNo, String brCode) throws ApplicationException;

    public String authorizeAcOpen(String acno, String enterBy, String authBy) throws ApplicationException;

    public String npaPosting(List<NpaAccountDetailPojo> npaList, String dt, String enterBy, String orgnCode) throws ApplicationException;

    public String acctStatusDeletion(Long spNo, String acNo) throws ApplicationException;

    public List<LoanAcDetailStatementPojo> getLoanGoingToNpa(String acNo, String fromDt, String toDt, String brCode);
    
    public String borrowerChecking (String acno);
}
