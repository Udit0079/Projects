/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.cdci;

import com.cbs.dto.cdci.CustInfo;
import com.cbs.dto.cdci.CustRegReturn;
import com.cbs.dto.cdci.MiniStatementPojo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface FIWSFacadeRemote {

//    public List<MiniStatementPojo> getMiniStatement(String acno) throws ApplicationException;

    public String fiPostingTxnProcess(String acno, String txnDt, String amt, int ty, String macId, String bcId, String remark) throws ApplicationException;

    public Double balanceeEnquiry(String acNo) throws ApplicationException;

    public CustRegReturn customerRegistration(CustInfo custInfo) throws ApplicationException;

    public CustRegReturn customerIdAndAccountOpen(String agentBrnId, String name, String fatherName, String gender, String address, String phone, String dob, String occupation, String panno, String userId) throws ApplicationException;
}
