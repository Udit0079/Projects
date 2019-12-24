package com.hrms.facade.personnel;

import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrEmpAdvanceDtTO;
import com.hrms.common.to.HrEmpAdvanceHdTO;
import com.hrms.common.to.HrEmpLoanDtTO;
import com.hrms.common.to.HrEmpLoanHdTO;
import com.hrms.common.to.HrMembershipDetailTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrPersonnelDependentTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.common.to.HrPersonnelHobbyTO;
import com.hrms.common.to.HrPersonnelLangTO;
import com.hrms.common.to.HrPersonnelPreviousCompanyTO;
import com.hrms.common.to.HrPersonnelQualificationTO;
import com.hrms.common.to.HrPersonnelReferenceTO;
import com.hrms.common.to.HrPersonnelTransportTO;
import com.hrms.common.to.PayrollCalendarTO;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Administrator
 */
@Remote
public interface PersonnelFacadeRemote {

    public String getAuthorization(String authorizedUser, String authorizedPassword, String formName) throws ApplicationException;

    public List<PayrollCalendarTO> getFinYear(int compCode) throws ApplicationException;

    public List<HrPersonnelDetailsTO> getPersonnalData(int compCode, String type, String value) throws ApplicationException;

    public List<HrMstStructTO> getIntialData(int compCode, String structCode) throws ApplicationException;

    public long getMaxAdvNoFromHrEmpAdvanceDt(int compCode) throws ApplicationException;

    public List getAdvanceTableData(int compCode) throws ApplicationException;

    public List getLoanTableData(int compCode) throws ApplicationException;

    public List getAdvanceTableDataForAuthorization(int compCode) throws ApplicationException;

    public List getLoanTableDataForAuthorization(int compCode) throws ApplicationException;

    public long getMaxLoanNoFromHrEmpLoanDt(int compCode) throws ApplicationException;

    public List getGradeAnddesignationList(int compCode) throws ApplicationException;

    public String getLastEmployeeId(int compCode) throws ApplicationException;

    public long getMaxEmpCode() throws ApplicationException;

    public List<HrEmpLoanHdTO> getLoanDetails(int compCode, long empLoanNo) throws ApplicationException;

    public List<HrEmpAdvanceHdTO> getAdvanceDetails(int compCode, long advNo) throws ApplicationException;

    public HrPersonnelDetailsTO getAllByCompCodeAndEmpCodeOrEMPID(int compCode, long empCode, String empId) throws ApplicationException;

    public long getMaxRefCode() throws ApplicationException;

    public List<HrPersonnelReferenceTO> getReferenceTableData(long empCode) throws ApplicationException;

    public String saveHrPersonnelReference(HrPersonnelReferenceTO hrPersonnelReferenceTO, String mode) throws ApplicationException;

    public String saveHrPersonnelQualification(HrPersonnelQualificationTO hrPersonnelQualificationTO, String mode) throws ApplicationException;

    public List<HrPersonnelQualificationTO> getQualificationTableData(long empCode) throws ApplicationException;

    public List<HrPersonnelDependentTO> getDependentTableData(long empCode) throws ApplicationException;

    public String saveHrPersonnelDependent(HrPersonnelDependentTO hrPersonnelDependentTO, String mode) throws ApplicationException;

    public long getMaxDependentCode() throws ApplicationException;

    public List<HrPersonnelPreviousCompanyTO> getExperienceData(long empCode) throws ApplicationException;

    public long getMaxPrevCompCode() throws ApplicationException;

    public String saveHrPersonnelPreviousCompany(HrPersonnelPreviousCompanyTO hrPersonnelPreviousCompanyTO, String mode) throws ApplicationException;

    public List<HrPersonnelTransportTO> getTransportData(long empCode) throws ApplicationException;

    public String saveHrPersonnelTransport(HrPersonnelTransportTO hrPersonnelTransportTO, String mode) throws ApplicationException;

    public int getMaxMemNo() throws ApplicationException;

    public String saveHrMembershipDetail(HrMembershipDetailTO hrMembershipDetailTO, String mode) throws ApplicationException;

    public List<HrMembershipDetailTO> getMembershipData(long empCode) throws ApplicationException;

    public String saveHrPersonnelEmployeeDetails(HrPersonnelDetailsTO personnelDetailsTO, String mode) throws ApplicationException;

    public String saveHrPersonnelJobDetails(HrPersonnelDetailsTO personnelDetailsTO, String mode) throws ApplicationException;

    public List<HrPersonnelHobbyTO> getHobbyData(long empCode) throws ApplicationException;

    public List<HrPersonnelLangTO> getLanguageData(long empCode) throws ApplicationException;

    public String saveHrPersonnelHobby(HrPersonnelHobbyTO hrPersonnelHobbyTO, String mode) throws ApplicationException;

    public String saveHrPersonnelLang(HrPersonnelLangTO hrPersonnelLangTO, String mode) throws ApplicationException;

    public String saveEmpAdvanceDetail(HrEmpAdvanceHdTO hrEmpAdvanceHdTO, List<HrEmpAdvanceDtTO> hrEmpAdvanceDtTOList, String mode) throws ApplicationException;

    public String authorizeEmpAdvanceDetail(HrEmpAdvanceHdTO hrEmpAdvanceHdTO) throws ApplicationException;

    public String authorizeEmpLoanDetail(HrEmpLoanHdTO hrEmpLoanHdTO) throws ApplicationException;

    public String saveEmpLoanDetail(HrEmpLoanHdTO hrEmpLoanHdTO, List<HrEmpLoanDtTO> hrEmpLoanDtTOList, String mode) throws ApplicationException;
}
