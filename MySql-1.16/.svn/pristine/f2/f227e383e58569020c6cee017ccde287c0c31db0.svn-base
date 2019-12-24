/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.facade;

import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrMstBusTO;
import com.hrms.common.to.HrMstCompLoanTO;
import com.hrms.common.to.HrMstDeptSubdeptTO;
import com.hrms.common.to.HrMstRouteTO;
import com.hrms.common.to.HrMstShiftTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrMstZoneLocationTO;
import com.hrms.common.to.PayrollCalendarTO;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Zeeshan Waris
 */
@Remote
public interface DefinitionFacadeRemote {

    public List<PayrollCalendarTO> getFinYear(int compCode) throws ApplicationException;

    public List<HrMstBusTO> getAllByBusNo(int compCode) throws ApplicationException;

    public String saveBusMasterDetail(HrMstBusTO hrMstBusTO, String mode) throws ApplicationException;

    public List<HrMstRouteTO> getAllByRouteNo(int compCode) throws ApplicationException;

    public String saveRouteDetail(HrMstRouteTO hrMstRouteTO, String mode) throws ApplicationException;

    public List<HrMstStructTO> getIntialData(int compCode, String structCode) throws ApplicationException;

    public List<HrMstDeptSubdeptTO> getAllHrMstDeptSubdeptByDeptcodeSubdeptcode(int compCode, String deptCode, String subDeptCode) throws ApplicationException;

    public List<HrMstDeptSubdeptTO> getAllHrMstDeptSubdeptByCompcode(int compCode) throws ApplicationException;

    public String saveDepartmentSubDepartmentMasterDetail(HrMstDeptSubdeptTO hrMstDeptSubdeptTO, String mode) throws ApplicationException;

    public List getSubDepartment(int compCode) throws ApplicationException;

    public List<HrMstDeptSubdeptTO> getAllHrMstDeptSubdeptByDeptcode(int compCode, String deptCode) throws ApplicationException;

    public List getSubDepartmentsAssigned(int compCode, String deptCode) throws ApplicationException;

    public String saveLoanBudgetMasterDetail(HrMstCompLoanTO hrMstCompLoanTO, String mode) throws ApplicationException;

    public List<HrMstCompLoanTO> getHrMstCompLoanData(int compCode) throws ApplicationException;

    public List<HrMstShiftTO> getShiftMasterByCompCode(int compCode) throws ApplicationException;

    public String saveShiftMasterDetail(HrMstShiftTO hrMstShiftTO, String mode) throws ApplicationException;

    public List getAssignedLocations(int compCode, String zoneValue) throws ApplicationException;

    public List getAvailableLocations(int compCode) throws ApplicationException;

    public String saveZoneLocationMasterDetail(HrMstZoneLocationTO hrMstZoneLocationTO, String mode) throws ApplicationException;
}
