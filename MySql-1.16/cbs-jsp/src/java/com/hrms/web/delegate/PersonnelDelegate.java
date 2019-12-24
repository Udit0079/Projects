package com.hrms.web.delegate;

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
import com.hrms.utils.HrServiceLocator;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.facade.personnel.PersonnelFacadeRemote;
import com.hrms.web.exception.WebException;
import java.util.ArrayList;
import java.util.List;

public class PersonnelDelegate {

    private final String jndiHomeName = "PersonnelFacade";
    private PersonnelFacadeRemote beanRemote = null;

    /**
     *
     * @throws ServiceLocatorException
     */
    public PersonnelDelegate() throws ServiceLocatorException {
        try{
            Object lookup = HrServiceLocator.getInstance().lookup(jndiHomeName);
            beanRemote = (PersonnelFacadeRemote) lookup;
        }catch (Exception e) {
            throw new ServiceLocatorException(e);            
        }    
    }

    /**
     *
     * @param authorizedUser
     * @param authorizedPassword
     * @param formName
     * @return
     */
    public String getAuthorization(String authorizedUser, String authorizedPassword, String formName) throws WebException {
        String result = null;
        try {
            result = beanRemote.getAuthorization(authorizedUser, authorizedPassword, formName);
            System.out.println(result);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    /**
     *
     * @param compCode
     * @param structCode
     * @return
     * @throws WebException
     */
    public List<HrMstStructTO> getInitialData(int compCode, String structCode) throws WebException {
        List<HrMstStructTO> HrMstStructTOs = new ArrayList<HrMstStructTO>();
        try {
            HrMstStructTOs = beanRemote.getIntialData(compCode, structCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return HrMstStructTOs;
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
     * @param compcode
     * @param type
     * @param value
     * @return
     * @throws WebException
     */
    public List<HrPersonnelDetailsTO> getHrPersonnelData(int compcode, String type, String value) throws WebException {
        List<HrPersonnelDetailsTO> hrPersonnelDetailsTOs = null;
        try {
            hrPersonnelDetailsTOs = beanRemote.getPersonnalData(compcode, type, value);
            return hrPersonnelDetailsTOs;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    public String authorizeEmpAdvanceDetail(HrEmpAdvanceHdTO hrEmpAdvanceHdTO) throws WebException {
        String result = null;
        try {
            result = beanRemote.authorizeEmpAdvanceDetail(hrEmpAdvanceHdTO);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public String authorizeEmpLoanDetail(HrEmpLoanHdTO hrEmpLoanHdTO) throws WebException {
        String result = null;
        try {
            result = beanRemote.authorizeEmpLoanDetail(hrEmpLoanHdTO);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    /**
     *
     * @param compCode
     * @return
     * @throws WebException
     */
    public List getAdvanceTableData(int compCode) throws WebException {
        List list = null;
        try {
            list = beanRemote.getAdvanceTableData(compCode);
            return list;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    /**
     *
     * @param compCode
     * @return
     * @throws WebException
     */
    public List getLoanTableData(int compCode) throws WebException {
        List list = null;
        try {
            list = beanRemote.getLoanTableData(compCode);
            return list;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    /**
     *
     * @param compCode
     * @return
     * @throws WebException
     */
    public long getMaxAdvNoFromHrEmpAdvanceDt(int compCode) throws WebException {
        long max = 0;
        try {
            max = beanRemote.getMaxAdvNoFromHrEmpAdvanceDt(compCode);
            return max;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    /**
     *
     * @param compCode
     * @return
     * @throws WebException
     */
    public long getMaxLoanNoFromHrEmpLoanDt(int compCode) throws WebException {
        long max = 0;
        try {
            max = beanRemote.getMaxLoanNoFromHrEmpLoanDt(compCode);
            return max;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    /**
     *
     * @param compCode
     * @return
     * @throws WebException
     */
    public List getGradeAnddesignationList(int compCode) throws WebException {
        List list = null;
        try {
            list = beanRemote.getGradeAnddesignationList(compCode);
            return list;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    /**
     *
     * @param compCode
     * @return
     * @throws WebException
     */
    public String getLastEmployeeId(int compCode) throws WebException {
        String result = "";
        try {
            result = beanRemote.getLastEmployeeId(compCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    /**
     *
     * @param compCode
     * @return
     * @throws WebException
     */
    public long getMaxEmpCode() throws WebException {
        long max = 0;
        try {
            max = beanRemote.getMaxEmpCode();
            return max;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    /**
     *
     * @param compCode
     * @param empLoanNo
     * @return
     * @throws WebException
     */
    public List<HrEmpLoanHdTO> getLoanDetails(int compCode, long empLoanNo) throws WebException {
        List<HrEmpLoanHdTO> hrEmpLoanHdTOs = null;
        try {
            hrEmpLoanHdTOs = beanRemote.getLoanDetails(compCode, empLoanNo);
            return hrEmpLoanHdTOs;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    /**
     *
     * @param compCode
     * @param advNo
     * @return
     * @throws WebException
     */
    public List<HrEmpAdvanceHdTO> getAdvanceDetails(int compCode, long advNo) throws WebException {
        List<HrEmpAdvanceHdTO> hrEmpAdvanceHdTOs = null;
        try {
            hrEmpAdvanceHdTOs = beanRemote.getAdvanceDetails(compCode, advNo);
            return hrEmpAdvanceHdTOs;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    /**
     *
     * @param compCode
     * @param empCode
     * @param empId
     * @return
     */
    public HrPersonnelDetailsTO getAllByCompCodeAndEmpCodeOrEMPID(int compCode, long empCode, String empId) throws WebException {
        HrPersonnelDetailsTO hrPersonnelDetailsTOs = null;
        try {
            hrPersonnelDetailsTOs = beanRemote.getAllByCompCodeAndEmpCodeOrEMPID(compCode, empCode, empId);
            return hrPersonnelDetailsTOs;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    public long getMaxRefCode() throws WebException {
        long max = 0;
        try {
            max = beanRemote.getMaxRefCode();
            return max;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    public List<HrPersonnelReferenceTO> getReferenceTableData(long empCode) throws WebException {
        List<HrPersonnelReferenceTO> hrPersonnelReferenceTOs = null;
        try {
            hrPersonnelReferenceTOs = beanRemote.getReferenceTableData(empCode);
            return hrPersonnelReferenceTOs;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    public String saveHrPersonnelReference(HrPersonnelReferenceTO hrPersonnelReferenceTO, String mode) throws WebException {
        String result = null;
        try {
            result = beanRemote.saveHrPersonnelReference(hrPersonnelReferenceTO, mode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public String saveHrPersonnelQualification(HrPersonnelQualificationTO hrPersonnelQualificationTO, String mode) throws WebException {
        String result = null;
        try {
            result = beanRemote.saveHrPersonnelQualification(hrPersonnelQualificationTO, mode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public List<HrPersonnelQualificationTO> getQualificationTableData(long empCode) throws WebException {
        List<HrPersonnelQualificationTO> hrPersonnelQualificationTOs = null;
        try {
            hrPersonnelQualificationTOs = beanRemote.getQualificationTableData(empCode);
            return hrPersonnelQualificationTOs;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    public List<HrPersonnelDependentTO> getDependentTableData(long empCode) throws WebException {
        List<HrPersonnelDependentTO> hrPersonnelDependentTOs = null;
        try {
            hrPersonnelDependentTOs = beanRemote.getDependentTableData(empCode);
            return hrPersonnelDependentTOs;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    public String saveHrPersonnelDependent(HrPersonnelDependentTO hrPersonnelDependentTO, String mode) throws WebException {
        String result = null;
        try {
            result = beanRemote.saveHrPersonnelDependent(hrPersonnelDependentTO, mode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public long getMaxDependentCode() throws WebException {
        long max = 0;
        try {
            max = beanRemote.getMaxDependentCode();
            return max;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    public List<HrPersonnelPreviousCompanyTO> getExperienceData(long empCode) throws WebException {
        try {
            List<HrPersonnelPreviousCompanyTO> hrPersonnelPreviousCompanyTOs = beanRemote.getExperienceData(empCode);
            return hrPersonnelPreviousCompanyTOs;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    public long getMaxPrevCompCode() throws WebException {
        long max = 0;
        try {
            max = beanRemote.getMaxPrevCompCode();
            return max;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    public String saveHrPersonnelPreviousCompany(HrPersonnelPreviousCompanyTO hrPersonnelPreviousCompanyTO, String mode) throws WebException {
        String result = null;
        try {
            result = beanRemote.saveHrPersonnelPreviousCompany(hrPersonnelPreviousCompanyTO, mode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public String saveHrPersonnelTransport(HrPersonnelTransportTO hrPersonnelTransportTO, String mode) throws WebException {
        String result = null;
        try {
            result = beanRemote.saveHrPersonnelTransport(hrPersonnelTransportTO, mode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public List<HrPersonnelTransportTO> getTransportTableData(long empCode) throws WebException {
        try {
            List<HrPersonnelTransportTO> hrPersonnelTransportTOs = beanRemote.getTransportData(empCode);
            return hrPersonnelTransportTOs;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    public int getMaxMemNo() throws WebException {
        int max = 0;
        try {
            max = beanRemote.getMaxMemNo();
            return max;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    public String saveHrMembershipDetail(HrMembershipDetailTO hrMembershipDetailTO, String mode) throws WebException {
        String result = null;
        try {
            result = beanRemote.saveHrMembershipDetail(hrMembershipDetailTO, mode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public List<HrMembershipDetailTO> getMembershipTableData(long empCode) throws WebException {
        try {
            List<HrMembershipDetailTO> hrMembershipDetailTOs = beanRemote.getMembershipData(empCode);
            return hrMembershipDetailTOs;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    public String saveHrPersonnelEmployeeDetails(HrPersonnelDetailsTO personnelDetailsTO, String mode) throws WebException {
        String result = null;
        try {
            result = beanRemote.saveHrPersonnelEmployeeDetails(personnelDetailsTO, mode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public String saveHrPersonnelJobDetails(HrPersonnelDetailsTO personnelDetailsTO, String mode) throws WebException {
        String result = null;
        try {
            result = beanRemote.saveHrPersonnelJobDetails(personnelDetailsTO, mode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public List<HrPersonnelHobbyTO> getHobbyTableData(long empCode) throws WebException {
        try {
            List<HrPersonnelHobbyTO> hrPersonnelHobbyTOs = beanRemote.getHobbyData(empCode);
            return hrPersonnelHobbyTOs;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    public List<HrPersonnelLangTO> getLanguageTableData(long empCode) throws WebException {
        try {
            List<HrPersonnelLangTO> hrPersonnelLanguageTOs = beanRemote.getLanguageData(empCode);
            return hrPersonnelLanguageTOs;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    public String saveHrPersonnelHobby(HrPersonnelHobbyTO hrPersonnelHobbyTO, String mode) throws WebException {
        String result = null;
        try {
            result = beanRemote.saveHrPersonnelHobby(hrPersonnelHobbyTO, mode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public String saveHrPersonnelLang(HrPersonnelLangTO hrPersonnelLangTO, String mode) throws WebException {
        String result = null;
        try {
            result = beanRemote.saveHrPersonnelLang(hrPersonnelLangTO, mode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public List getAdvanceTableDataForAuthorization(int compCode) throws WebException {
        List list = null;
        try {
            list = beanRemote.getAdvanceTableDataForAuthorization(compCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return list;
    }

    public List getLoanTableDataForAuthorization(int compCode) throws WebException {
        List list = null;
        try {
            list = beanRemote.getLoanTableDataForAuthorization(compCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return list;
    }

    public String saveEmpAdvanceDetail(HrEmpAdvanceHdTO hrEmpAdvanceHdTO, List<HrEmpAdvanceDtTO> hrEmpAdvanceDtTOList, String mode) throws WebException {
        String result = null;
        try {
            result = beanRemote.saveEmpAdvanceDetail(hrEmpAdvanceHdTO, hrEmpAdvanceDtTOList, mode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public String saveEmpLoanDetail(HrEmpLoanHdTO hrEmpLoanHdTO, List<HrEmpLoanDtTO> hrEmpLoanDtTOList, String mode) throws WebException {
        String result = null;
        try {
            result = beanRemote.saveEmpLoanDetail(hrEmpLoanHdTO, hrEmpLoanDtTOList, mode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }
}
