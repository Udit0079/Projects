/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.cdci;

import com.cbs.dto.cdci.FiMiniStatementPojo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface PacsWSFacadeRemote {

    public List<FiMiniStatementPojo> getMiniStatement(String acno, String opt) throws ApplicationException;

    public String fiPostingTxnProcess(String acno, String txnDt, String amt, int ty, String macId, String bcId, String remark, String mAcNo, String chqNo, String chqDt) throws ApplicationException;

    public Double balanceeEnquiry(String acNo) throws ApplicationException;
}
