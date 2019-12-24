/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.facade.hrd;

import com.hrms.common.to.HrConsultantTO;
import javax.ejb.Remote;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrDirectRecTO;
import com.hrms.common.to.HrManpowerTO;
import com.hrms.common.to.HrMstStructTO;
import java.util.List;

/**
 *
 * @author Zeeshan Waris
 */
@Remote
public interface RecruitmentFacadeRemote {

    public List<HrMstStructTO> getDirectRecruitmentList(int compCode, String code) throws ApplicationException;

    public List directRecruitmentZoneCode(int compCode, String zone) throws ApplicationException;

    public List directRecruitmentInterviewerDetails(int compCode, String empId) throws ApplicationException;

    public List directRecruitmentInterviewerByName(int compCode, String empName) throws ApplicationException;

    public List directRecruitmentViewDetail(int compCode, String candName) throws ApplicationException;

    public List directRecruitmentUpdateDetail(int compCode, String arNo) throws ApplicationException;

    public List directRecruitmentSaveCheckAction(int compCode) throws ApplicationException;

    public List directRecruitmentSaveValidAction() throws ApplicationException;

    public List directRecruitmentSuperCodeForSave(int compcode, String superId) throws ApplicationException;

    public String directRecruitmentSave(HrDirectRecTO hrDirectObj) throws ApplicationException;

    public String directRecruitmentUpdate(HrDirectRecTO hrDirectObj) throws ApplicationException;
    //public HrDirectRecTO directRecruitmentUpdateValidation(int compCode, String arNo) throws ApplicationException;

    public String directRecruitmentDeleteRecord(int compCode, String arNo) throws ApplicationException;

    public List<HrMstStructTO> getIntialData(int compCode) throws ApplicationException;

    public List<HrConsultantTO> getTableDetails(int compCode, String consultantName) throws ApplicationException;

    public List<HrConsultantTO> getConsultantDetails(int compCode, String consName, String consultantName) throws ApplicationException;

    public String saveConsultant(HrConsultantTO consultantObj) throws ApplicationException;

    public String updateConsultant(HrConsultantTO consultantObj) throws ApplicationException;

    public String deleteConsultantAction(int compCode, String consCode) throws ApplicationException;

    public List<HrMstStructTO> getManPowerList(int compCode, String code) throws ApplicationException;

    public List manpowerDetails(int compCode) throws ApplicationException;

    public String deleteManPowerPlanAction(int compCode, int year, String month, String zone, String deptCode) throws ApplicationException;

    public List manpowerPlanningGradeCode(int compCode, String desgCode) throws ApplicationException;

    public List manpowerPlanningEmployeeNo(int compCode, String zones, String deptCode, String desgCode, String gradeCode) throws ApplicationException;

    public String manpowerSave(HrManpowerTO hrDirectObj) throws ApplicationException;

    public String manpowerupdate(HrManpowerTO hrmanpowerObj) throws ApplicationException;
}
