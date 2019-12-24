/*
 * CREATED BY    :  ROHIT KRISHNA GUPTA
 * CREATION DATE :  11 AUGUST 2011
 */
package com.cbs.facade.misc;

import com.cbs.dto.CashTokenBookGridFile;
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
public interface CashTokenBookFacadeRemote {

    public List gridLoad(String brCode) throws ApplicationException;

//    public String tokenPaidDebitWithCashier(List<CashTokenBookGridFile> gridDetail, String tokenpaidby, String enterDt, DenominitionTable denominitionObj, boolean denominationRender, String brCode) throws ApplicationException;
    
    public String tokenPaidDebitWithCashier(List<CashTokenBookGridFile> gridDetail, String tokenpaidby, String enterDt, List<DDSDenominationGrid> denominationTable, boolean denominationRender, String brCode) throws ApplicationException;

    public boolean cashAndDenominitionModChk() throws ApplicationException;

    public List activeCashierCombo(String brCode) throws ApplicationException;

    public String cashRecievedByCashier(DenominitionTable denominitionObj, String userId, double openingBal, String enterBy, String enterDt, String brCode) throws ApplicationException;
}
