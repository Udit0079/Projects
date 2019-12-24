/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.exception.ApplicationException;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface SavingAccountProductFacadeRemote {

    public String updateDailySavingProductAtCentral(String processFlag) throws ApplicationException;

    public String updateDailySavingProductAtMonthEnd(int brncode, String processFlag) throws ApplicationException;
    
    public String updateDailySavingProductAtCentralDayBegin(String processFlag, String todayDt) throws ApplicationException;
}
