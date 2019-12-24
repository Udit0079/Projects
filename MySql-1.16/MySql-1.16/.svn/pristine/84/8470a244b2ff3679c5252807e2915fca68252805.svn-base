/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.delegate;

import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrMstBusTO;
import com.hrms.common.to.HrMstCompLoanTO;
import com.hrms.common.to.HrMstDeptSubdeptTO;
import com.hrms.common.to.HrMstRouteTO;
import com.hrms.common.to.HrMstShiftTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrMstZoneLocationTO;
import com.hrms.common.to.PayrollCalendarTO;
import com.hrms.utils.HrServiceLocator;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.facade.DefinitionFacadeRemote;
import com.hrms.web.exception.WebException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class DefinitionsDelegate {

    private final String jndiHomeName = "DefinitionFacade";
    private DefinitionFacadeRemote beanRemote = null;

    public DefinitionsDelegate() throws ServiceLocatorException {
        try{
            Object lookup = HrServiceLocator.getInstance().lookup(jndiHomeName);
            beanRemote = (DefinitionFacadeRemote) lookup;
        }catch (Exception e) {
            throw new ServiceLocatorException(e);            
       }
        
    }

    public List<PayrollCalendarTO> getFinancialYear(int compCode) throws WebException {
        List<PayrollCalendarTO> payrollCalendarTOs = new ArrayList<PayrollCalendarTO>();
        try {
            payrollCalendarTOs = beanRemote.getFinYear(compCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return payrollCalendarTOs;
    }

    public List<HrMstBusTO> getAllByBusNo(int compCode) throws WebException {
        try {
            List<HrMstBusTO> hrMstBusTOs = beanRemote.getAllByBusNo(compCode);
            return hrMstBusTOs;
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
    }

    public String saveBusMasterDetail(HrMstBusTO hrMstBusTO, String mode) throws WebException {
        String result = null;
        try {
            result = beanRemote.saveBusMasterDetail(hrMstBusTO, mode);
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public List<HrMstRouteTO> getAllByRouteNo(int compCode) throws WebException {
        try {
            List<HrMstRouteTO> hrMstRouteTO = beanRemote.getAllByRouteNo(compCode);
            return hrMstRouteTO;
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
    }

    public String saveRouteDetail(HrMstRouteTO hrMstRouteTO, String mode) throws WebException {
        String result = null;
        try {
            result = beanRemote.saveRouteDetail(hrMstRouteTO, mode);
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public List<HrMstStructTO> getInitialData(int compCode, String structCode) throws WebException {
        List<HrMstStructTO> HrMstStructTOs = new ArrayList<HrMstStructTO>();
        try {
            HrMstStructTOs = beanRemote.getIntialData(compCode, structCode);
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
        return HrMstStructTOs;
    }

    public List<HrMstDeptSubdeptTO> getAllHrMstDeptSubdeptByDeptcodeSubdeptcode(int compCode, String deptCode, String subDeptCode) throws WebException {
        List<HrMstDeptSubdeptTO> hrMstDeptSubdeptTOs = new ArrayList<HrMstDeptSubdeptTO>();
        try {
            hrMstDeptSubdeptTOs = beanRemote.getAllHrMstDeptSubdeptByDeptcodeSubdeptcode(compCode, deptCode, subDeptCode);
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
        return hrMstDeptSubdeptTOs;
    }

    public List<HrMstDeptSubdeptTO> getAllHrMstDeptSubdeptByCompcode(int compCode) throws WebException {
        List<HrMstDeptSubdeptTO> hrMstDeptSubdeptTOs = new ArrayList<HrMstDeptSubdeptTO>();
        try {
            hrMstDeptSubdeptTOs = beanRemote.getAllHrMstDeptSubdeptByCompcode(compCode);
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
        return hrMstDeptSubdeptTOs;
    }

    public String saveDepartmentSubDepartmentMasterDetail(HrMstDeptSubdeptTO hrMstDeptSubdeptTO, String mode) throws WebException {
        String result = null;
        try {
            result = beanRemote.saveDepartmentSubDepartmentMasterDetail(hrMstDeptSubdeptTO, mode);
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public List getSubDepartment(int compCode) throws WebException {
        List list = null;
        try {
            list = beanRemote.getSubDepartment(compCode);
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
        return list;
    }

    public List getSubDepartmentsAssigned(int compCode, String deptCode) throws WebException {
        List list = null;
        try {
            list = beanRemote.getSubDepartmentsAssigned(compCode, deptCode);
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
        return list;
    }

    public String saveLoanBudgetMasterDetail(HrMstCompLoanTO hrMstCompLoanTO, String mode) throws WebException {
        String result = null;
        try {
            result = beanRemote.saveLoanBudgetMasterDetail(hrMstCompLoanTO, mode);
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public List<HrMstCompLoanTO> getHrMstCompLoanData(int compCode) throws WebException {
        List<HrMstCompLoanTO> hrMstCompLoanTOs = new ArrayList<HrMstCompLoanTO>();
        try {
            hrMstCompLoanTOs = beanRemote.getHrMstCompLoanData(compCode);
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
        return hrMstCompLoanTOs;
    }

    public List<HrMstShiftTO> getShiftMasterByCompCode(int compCode) throws WebException {
        List<HrMstShiftTO> hrMstShiftTOs = new ArrayList<HrMstShiftTO>();
        try {
            hrMstShiftTOs = beanRemote.getShiftMasterByCompCode(compCode);
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
        return hrMstShiftTOs;
    }

    public String saveShiftMasterDetail(HrMstShiftTO hrMstShiftTO, String mode) throws WebException {
        String result = null;
        try {
            result = beanRemote.saveShiftMasterDetail(hrMstShiftTO, mode);
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public List getAvailableLocations(int compCode) throws WebException {
        List list = null;
        try {
            list = beanRemote.getAvailableLocations(compCode);
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
        return list;
    }

    public List getAssignedLocations(int compCode, String zoneValue) throws WebException {
        List list = null;
        try {
            list = beanRemote.getAssignedLocations(compCode, zoneValue);
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
        return list;
    }

    public String saveZoneLocationMasterDetail(HrMstZoneLocationTO hrMstZoneLocationTO, String mode) throws WebException {
        String result = null;
        try {
            result = beanRemote.saveZoneLocationMasterDetail(hrMstZoneLocationTO, mode);
        } catch (ApplicationException ex) {
            throw new WebException(ex.getExceptionCode().getMessageKey());
        }
        return result;
    }
}
