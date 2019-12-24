/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.ws.sms;

import com.cbs.exception.ApplicationException;
import com.cbs.facade.sms.MessageDetailBeanRemote;
import com.cbs.pojo.sms.MessageDetailPojo;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ejb.Stateless;

/**
 *
 * @author root
 */
@WebService(serviceName = "DeliveredMessages")
@Stateless()
public class DeliveredMessages {

    @EJB
    private MessageDetailBeanRemote ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "getMessageDetail")
    public List<MessageDetailPojo> getMessageDetail(@WebParam(name = "fromDate") String fromDate, @WebParam(name = "toDate") String toDate) throws ApplicationException {
        return ejbRef.getMessageDetail(fromDate, toDate);
    }
}
