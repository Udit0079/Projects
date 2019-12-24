/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.ws;

import com.cbs.facade.dds.PigMyFacadeRemote;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author root
 */
@WebService(serviceName = "PIGMEWS")
@Stateless()
public class PIGMEWS {

    @EJB
    private PigMyFacadeRemote ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "pigMeProcess")
    public String pigMeProcess(@WebParam(name = "custId") String custId, @WebParam(name = "payLoad") String payLoad) {
        return ejbRef.pigMeProcess(custId, payLoad);
    }
}
