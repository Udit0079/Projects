/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.facade;

import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.LeaveMasterTO;
import com.hrms.common.to.PayrollCalendarTO;
import com.hrms.common.to.HrMstStructTO;
import java.util.List;

/**
 *
 * @author root
 */
public interface MasterMgmtFacadeRemote {

    public List<PayrollCalendarTO> getFinYear(int compCode) throws ApplicationException;

    public List<HrMstStructTO> getIntialData(int compCode) throws ApplicationException;

    public String saveLeaveDetail(LeaveMasterTO leavmstObj) throws ApplicationException;
}
