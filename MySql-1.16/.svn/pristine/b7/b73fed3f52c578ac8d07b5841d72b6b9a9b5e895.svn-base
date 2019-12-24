/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ib;

import com.cbs.dto.ib.IbRequestTo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface IbRequestMgmtFacadeRemote {

    public List<IbRequestTo> getIbRequestForBranch(String reqType, String reqStatus,
            String brCode, String todayDt) throws ApplicationException;

    public String processChequeBookIssue(String acno, long chqFrom, long chqTo,
            String chqSeries, long noOfLeaves, String invtClass, String invtType,
            String userName, String todayDt, String orgnCode, long requestNo,
            long requestBrNo) throws ApplicationException;

    public String reScheduleProcess(String brBusinessDt, String reScheduleReason,
            String reScheduleBy, long requestNo, long requestBrNo,
            String orgnBrCode) throws ApplicationException;
}
