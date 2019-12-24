/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.ws;

import com.cbs.dto.cdci.CustInfo;
import com.cbs.dto.cdci.CustRegReturn;
import com.cbs.dto.cdci.MiniStatementPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.cdci.FIWSFacadeRemote;
import com.cbs.facade.cdci.IBSWSFacadeRemote;
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
@WebService(serviceName = "FIWS")
@Stateless()
public class FIWS {

    @EJB
    private FIWSFacadeRemote ejbRef;
    
    @EJB
    private IBSWSFacadeRemote ibsEjbRef;

    @WebMethod(operationName = "getMiniStatement")
    public List<MiniStatementPojo> getMiniStatement(@WebParam(name = "acNo") String acno) throws ApplicationException {
        return ibsEjbRef.getMiniStatement(acno);
    }

    @WebMethod(operationName = "fiPostingTxnProcess")
    public String fiPostingTxnProcess(@WebParam(name = "acNo") String acno, @WebParam(name = "txnDt") String txnDt, @WebParam(name = "amt") String amt, @WebParam(name = "ty") int ty, @WebParam(name = "macId") String macId, @WebParam(name = "bcId") String bcId, @WebParam(name = "remark") String remark) throws ApplicationException {
        return ejbRef.fiPostingTxnProcess(acno, txnDt, amt, ty, macId, bcId, remark);
    }

    @WebMethod(operationName = "balanceeEnquiry")
    public Double balanceeEnquiry(@WebParam(name = "acNo") String acNo) throws ApplicationException {
        return ejbRef.balanceeEnquiry(acNo);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "customerRegistration")
    public CustRegReturn customerRegistration(@WebParam(name = "custInfo") CustInfo custInfo) throws ApplicationException {
        return ejbRef.customerRegistration(custInfo);
    }

    @WebMethod(operationName = "customerIdAndAccountOpen")
    public CustRegReturn customerIdAndAccountOpen(@WebParam(name = "agentBrnId") String agentBrnId, @WebParam(name = "name") String name, @WebParam(name = "fatherName") String fatherName, @WebParam(name = "gender") String gender, @WebParam(name = "address") String address, @WebParam(name = "phone") String phone, @WebParam(name = "dob") String dob, @WebParam(name = "occupation") String occupation, @WebParam(name = "panno") String panno, @WebParam(name = "userId") String userId) throws ApplicationException {
        return ejbRef.customerIdAndAccountOpen(agentBrnId, name, fatherName, gender, address, phone, dob, occupation, panno, userId);
    }
}
