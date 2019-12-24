/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.delegate;

import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.LeaveMasterTO;
import com.hrms.common.to.PayrollCalendarTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.utils.HrServiceLocator;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.facade.MasterMgmtFacadeRemote;
import com.hrms.web.exception.WebException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dhirendra Singh
 */
public class MasterManagementDelegate {

    private final String jndiHomeName = "MasterMgmtFacade";
    private MasterMgmtFacadeRemote beanRemote = null;

    /**
     * No argument constructor
     * @throws ServiceLocatorException
     */
    public MasterManagementDelegate() throws ServiceLocatorException {
        try{
            Object lookup = HrServiceLocator.getInstance().lookup(jndiHomeName);
            beanRemote = (MasterMgmtFacadeRemote) lookup;
        }catch (Exception e) {
            throw new ServiceLocatorException(e);            
       }    
    }

    /**
     *
     * @param compCode
     * @return
     * @throws WebException
     */
    public List<HrMstStructTO> getInitialData(int compCode) throws WebException {
        List<HrMstStructTO> structMasterTOs = new ArrayList<HrMstStructTO>();
        try {
            structMasterTOs = beanRemote.getIntialData(compCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return structMasterTOs;
    }

    /**
     *
     * @param compCode
     * @return
     * @throws WebException
     */
    public List<PayrollCalendarTO> getFinancialYear(int compCode) throws WebException {
        List<PayrollCalendarTO> payrollCalendarTOs = new ArrayList<PayrollCalendarTO>();
        try {
            payrollCalendarTOs = beanRemote.getFinYear(compCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return payrollCalendarTOs;
    }

    /**
     *
     * @param leavmstObj
     * @return
     * @throws WebException
     */
    public String saveLeaveDetail(LeaveMasterTO leavmstObj) throws WebException {
        String result = null;
        try {
            result = beanRemote.saveLeaveDetail(leavmstObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
            
        }
        return result;
    }
}
