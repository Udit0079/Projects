/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.misc;

import com.cbs.dto.CashierCashRecieveGridFile;
import com.cbs.dto.DDSDenominationGrid;
import com.cbs.dto.DenominitionTable;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author ROHIT KRISHNA
 */
@Remote
public interface CashierCashRecievedFacadeRemote {

    public List gridLoad(String brCode) throws ApplicationException;

    public boolean cashAndDenominitionModChk() throws ApplicationException;

//    public String cashRecievedProcess(List<CashierCashRecieveGridFile> gridDetail, String enterBy, String enterDt, DenominitionTable denominitionObj, boolean denominationRender, String brCode) throws ApplicationException;

    public String cashRecievedProcess(List<CashierCashRecieveGridFile> gridDetail, String enterBy, String enterDt, List<DDSDenominationGrid> denominationTable, boolean denominationRender, String brCode) throws ApplicationException;
    
    public List activeCashierCombo(String brCode) throws ApplicationException;

    public String loadCashierData(String userId, String brCode, String enterDt) throws ApplicationException;

    public List cashierDenominitionGridDetail(String userId, String brCode, String enterDt) throws ApplicationException;

    public String cashCloseAction(String userId, String brCode, String enterDt, double totalAmt) throws ApplicationException;

    public List gridLoadCashier(String brCode) throws ApplicationException;

    public String getUserNameMethod(String userId, String brCode) throws ApplicationException;

    public String activateCashierId(String userId, String userName, String counterNo, String enterBy, String brCode) throws ApplicationException;

    public String updateCashierCounter(String userId, String userName, String counterNo, String enterBy, String brCode) throws ApplicationException;

    public List safeDenominitionGridDetail(String brCode, String enterDt) throws ApplicationException;
}
