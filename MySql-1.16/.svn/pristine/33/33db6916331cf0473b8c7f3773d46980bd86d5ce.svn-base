/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.exception.ApplicationException;
import javax.ejb.Remote;

@Remote
public interface ReconcilationManagementFacadRemote {

//    public String autoReconcilation(String currentDate) throws ApplicationException;
    public String checkForIntersoleAccounts(String currentDate) throws ApplicationException;

//    public String hoConsolidateEntry(String enterBy) throws ApplicationException;
    public String adviceReconsilation(String currentDate) throws ApplicationException;

    public String neftInwardOutwardReconsilation(String currentDate) throws ApplicationException;

    public String isoTxnReconsilationExceptIntersole(String todayDt) throws ApplicationException;
    
    public String neftInwardReturnReconsilation(String sponsorBankName, String currentDate) throws ApplicationException;
}
