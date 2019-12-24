/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.sms;

import com.cbs.exception.ApplicationException;
import javax.ejb.Remote;

@Remote
public interface TimerServiceFacadeRemote {

    public boolean isSMSModuleOn() throws ApplicationException;

    public boolean isNewGl() throws ApplicationException;

    public void sendMessage() throws ApplicationException;

    public boolean isAtmSmsModuleOn() throws ApplicationException;
    
    public void sendEODMessage() throws ApplicationException;
}
