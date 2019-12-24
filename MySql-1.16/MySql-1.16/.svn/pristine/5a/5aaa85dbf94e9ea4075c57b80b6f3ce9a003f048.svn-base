/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ho.share;

import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Zeeshan Waris
 */
@Remote
public interface ShareAuthorizationFacadeRemote {

    public List gridDetail() throws ApplicationException;

    public String authorizeAction(List list, String enterBy, String brcode) throws ApplicationException;

    public String deleteDividendAction(String txnId) throws ApplicationException;

    public List shareIssuegridDetail() throws ApplicationException;

    public String shareCertificateAuthorization(int certNo, String enterBy, String brcode) throws ApplicationException;
    
    public List getPendingShares(String orgBrCode) throws ApplicationException;
    
    public List shareIssuedDetails(long txnNo, String issueDt) throws ApplicationException;

    public String shareIssueAuthorizeAction(long shareNoFrom, long shareNoTo, String enterBy, String brcode) throws ApplicationException;

    public List shareTransferGridDetail() throws ApplicationException;

    public String shareTransferAuthorizeAction(List list, String enterBy, String brcode) throws ApplicationException;
    
    public String deleteUnAuthShares(long shareNoFrom, long shareNoTo) throws ApplicationException;
}
