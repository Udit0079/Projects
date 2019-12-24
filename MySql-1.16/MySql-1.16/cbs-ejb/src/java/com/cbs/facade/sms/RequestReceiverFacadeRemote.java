/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.sms;

import com.cbs.exception.ApplicationException;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface RequestReceiverFacadeRemote {

    public void processOnRequestMessage(String mobileNo, String message) throws ApplicationException;

    public void missedCallHandler(String mobileNo, String channelId, String circle, String operator, String dateTime) throws ApplicationException;

    public void onlineAadhaarRegistration(String mobileNo, String message) throws Exception;
}
