/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.delegate;

import com.hrms.facade.hrd.InterviewActionPlanFacadeRemote;
import com.hrms.common.to.HrDatabankTO;
import com.hrms.common.to.HrInterviewHdTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrAdvertDtTO;
import com.hrms.common.to.HrAdvertHdTO;
import com.hrms.common.to.HrInterviewDtSalTO;
import com.hrms.common.to.HrInterviewDtTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.utils.HrServiceLocator;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.exception.WebException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zeeshan Waris
 */
public class InterviewActionPlanDelegate {

    private final String jndiHomeName = "InterviewActionPlanFacade";
    private InterviewActionPlanFacadeRemote beanRemote = null;

    /**
     *
     * @throws ServiceLocatorException
     */
    public InterviewActionPlanDelegate() throws ServiceLocatorException {
        try{
            Object lookup = HrServiceLocator.getInstance().lookup(jndiHomeName);
            beanRemote = (InterviewActionPlanFacadeRemote) lookup;
        }catch (Exception e) {
            throw new ServiceLocatorException(e);            
       }
    }

    /**
     *
     * @param compCode
     * @param code
     * @return
     * @throws WebException
     */
    public List<HrMstStructTO> getPositionList(int compCode, String code) throws WebException {
        List<HrMstStructTO> structMasterTOs = new ArrayList<HrMstStructTO>();
        try {
            structMasterTOs = beanRemote.getIntialData(compCode, code);
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
    public List<HrDatabankTO> getIntTableDetails(int compCode) throws WebException {
        List<HrDatabankTO> hrDatabankTOs = new ArrayList<HrDatabankTO>();
        try {
            hrDatabankTOs = beanRemote.getViewDetails(compCode, 'C');
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return hrDatabankTOs;
    }

    /**
     *
     * @param compCode
     * @return
     * @throws WebException
     */
    public String getAdvtCode(int compCode) throws WebException {
        String advtCode = "";
        try {
            advtCode = beanRemote.generateAdvtCode(compCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return advtCode;
    }

    /**
     *
     * @param companyCode
     * @param nameConsultant
     * @return
     * @throws WebException
     */
    public List preIntInterviewersName(int companyCode, String nameConsultant) throws WebException {
        List dropDownList = new ArrayList();
        try {
            dropDownList = beanRemote.preintInterviewersName(companyCode, nameConsultant);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return dropDownList;
    }

    /**
     *
     * @param companyCode
     * @param nameConsultant
     * @return
     * @throws WebException
     */
    public List preIntInterviewersUdateViewDetail(int companyCode, String nameConsultant) throws WebException {
        List preintlist = new ArrayList();
        try {
            preintlist = beanRemote.preintInterviewersUpdateDetails(companyCode, nameConsultant);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return preintlist;
    }

    /**
     *
     * @param companyCode
     * @param nameConsultant
     * @return
     * @throws WebException
     */
    public List preIntInterviewerCodeDetail(int companyCode, String nameConsultant) throws WebException {
        List preintlist = new ArrayList();
        try {
            preintlist = beanRemote.preintInterviewersCodeDetails(companyCode, nameConsultant);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return preintlist;
    }

    /**
     *
     * @param hrInterviewHdObj
     * @param hrInterviewDtObj
     * @param hrDatabanakObj
     * @return
     * @throws WebException
     */
    public String savePreInterviewActionPlan(HrInterviewHdTO hrInterviewHdObj,List<HrInterviewDtTO> interviewDtTOs,  List<HrDatabankTO> hrDatabankTOs) throws WebException {
        String result = null;
        try {
            result = beanRemote.savePreIntActionPlan(hrInterviewHdObj, interviewDtTOs, hrDatabankTOs);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    /**
     *
     * @param hrInterviewObj
     * @return
     * @throws WebException
     */
    public String updatePreInterviewActionPlan(HrInterviewHdTO hrInterviewObj) throws WebException {
        String result = null;
        try {
            result = beanRemote.updatePreIntActionPlan(hrInterviewObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    /**
     *
     * @param compCode
     * @param code
     * @return
     * @throws WebException
     */
    public List<HrMstStructTO> getPositionPostList(int compCode, String code) throws WebException {
        List<HrMstStructTO> structMasterTOs = new ArrayList<HrMstStructTO>();
        try {
            structMasterTOs = beanRemote.getInterviewerNameList(compCode, code);
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
    public List<HrPersonnelDetailsTO> getintrviewersNameList(int compCode) throws WebException {
        List<HrPersonnelDetailsTO> hrPersonnelDetailsTOs = new ArrayList<HrPersonnelDetailsTO>();
        try {
            hrPersonnelDetailsTOs = beanRemote.getInterviewerSecondNameList(compCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return hrPersonnelDetailsTOs;
    }

    /**
     *
     * @param companyCode
     * @param advtCode
     * @return
     * @throws WebException
     */
    public List postIntInterviewersDetail(int companyCode, String advtCode) throws WebException {
        List interviererList = new ArrayList();
        try {
            interviererList = beanRemote.postIntInterviewersDetails(companyCode, advtCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return interviererList;
    }

    /**
     *
     * @param hrInterviewDtObj
     * @return
     * @throws WebException
     */
    public String savePostInterviewActionPlan(HrInterviewDtTO hrInterviewDtObj) throws WebException {
        String result = null;
        try {
            result = beanRemote.savePostIntActionPlan(hrInterviewDtObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    /**
     *
     * @param hrInterviewDtObj
     * @return
     * @throws WebException
     */
    public String updatePostInterviewActionPlan(HrInterviewDtTO hrInterviewDtObj) throws WebException {
        String result = null;
        try {
            result = beanRemote.updatePostIntActionPlan(hrInterviewDtObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    /**
     *
     * @param companyCode
     * @param interviewerName
     * @return
     * @throws WebException
     */
    public List postIntViewDetail(int companyCode, String interviewerName) throws WebException {
        List interviererList = new ArrayList();
        try {
            interviererList = beanRemote.postIntIntViewDetails(companyCode, interviewerName);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return interviererList;
    }

    /**
     *
     * @param compCode
     * @param intCode
     * @param advtCode
     * @param jobCode
     * @param candSrno
     * @return
     * @throws WebException
     */
    public List postIntEditDetail(int compCode, String intCode, String advtCode, String jobCode, String candSrno) throws WebException {
        List interviererList = new ArrayList();
        try {
            interviererList = beanRemote.postIntIntEditDetails(compCode, intCode, advtCode, jobCode, candSrno);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return interviererList;
    }

    /**
     *
     * @param compCode
     * @param code
     * @return
     * @throws WebException
     */
    public List<HrMstStructTO> getPositionExtensionList(int compCode, String code) throws WebException {
        List<HrMstStructTO> structMasterTOs = new ArrayList<HrMstStructTO>();
        try {
            structMasterTOs = beanRemote.getInterviewerNameListExtension(compCode, code);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return structMasterTOs;
    }

    /**
     *
     * @param compCode
     * @param candidateSrno
     * @return
     * @throws WebException
     */
    public List extensionOfAppointmentSearchList(int compCode, String candidateSrno) throws WebException {
        List searchList = new ArrayList();
        try {
            searchList = beanRemote.extensionOfAppointmentSearch(compCode, candidateSrno);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return searchList;
    }

    /**
     *
     * @param compCode
     * @param intCode
     * @return
     * @throws WebException
     */
    public List extensionOfAppointmentEditList(int compCode, String intCode) throws WebException {
        List searchList = new ArrayList();
        try {
            searchList = beanRemote.extensionOfAppointmentAdviceEditList(compCode, intCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return searchList;
    }

    /**
     *
     * @param hrInterviewDtObj
     * @return
     * @throws WebException
     */
    public String extensionOfAppointmentSaveAction(HrInterviewDtTO hrInterviewDtObj) throws WebException {
        String result = null;
        try {
            result = beanRemote.extensionOfAppointmentAdviceSaveAction(hrInterviewDtObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    /**
     *
     * @param hrInterviewDtObj
     * @return
     * @throws WebException
     */
    public String extensionOfAppointmentUpdateAction(HrInterviewDtTO hrInterviewDtObj) throws WebException {
        String result = null;
        try {
            result = beanRemote.extensionOfAppointmentAdviceUpdateAction(hrInterviewDtObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    /******************** advertisement ****************************/
    public List<HrMstStructTO> getLocationList(String desc, int compCode) throws WebException {
        List<HrMstStructTO> HrMstStructTOs = new ArrayList<HrMstStructTO>();
        try {
            HrMstStructTOs = beanRemote.getdesigDepartLocationList(desc, compCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return HrMstStructTOs;
    }

    /**
     *
     * @param desc
     * @param compCode
     * @return
     * @throws WebException
     */
    public List<HrMstStructTO> getDesignationList(String desc, int compCode) throws WebException {
        List<HrMstStructTO> HrMstStructTOs = new ArrayList<HrMstStructTO>();
        try {
            HrMstStructTOs = beanRemote.getdesigDepartLocationList(desc, compCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return HrMstStructTOs;
    }

    /**
     *
     * @param desc
     * @param compCode
     * @return
     * @throws WebException
     */
    public List<HrMstStructTO> getDepartmentList(String desc, int compCode) throws WebException {
        List<HrMstStructTO> HrMstStructTOs = new ArrayList<HrMstStructTO>();
        try {
            HrMstStructTOs = beanRemote.getdesigDepartLocationList(desc, compCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return HrMstStructTOs;
    }

    /**
     *
     * @param compCode
     * @param advtCode
     * @return
     * @throws WebException
     */
    public List<HrAdvertHdTO> findAdvertisementCodeList(int compCode, String advtCode) throws WebException {
        try {
            List<HrAdvertHdTO> hrAdvertHdTO = beanRemote.findAdvCode(compCode, advtCode);
            return hrAdvertHdTO;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }

    }

    /**
     *
     * @param compCode
     * @param advtCode
     * @return
     * @throws WebException
     */
    public List<HrAdvertHdTO> getAdvtCodeHeaderDetails(int compCode, String advtCode) throws WebException {
        try {
            List<HrAdvertHdTO> hrAdvertHdTO = beanRemote.getAdvtHeaderDetails(compCode, advtCode);
            return hrAdvertHdTO;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    /**
     *
     * @param compCode
     * @param advtCode
     * @return
     * @throws WebException
     */
    public List getAdvertisementCodeDetails(int compCode, String advtCode) throws WebException {
        try {
            List l1 = beanRemote.getAdvertisementCodedetails(compCode, advtCode);
            return l1;
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
    public String getAdvetisementCode(int compCode) throws WebException {
        String advtCode = "";
        try {
            advtCode = beanRemote.generateAdvtAdverisementCode(compCode);

        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return advtCode;
    }

    /**
     *
     * @param hrAdvertHdTO
     * @param mod
     * @return
     * @throws WebException
     */
    public String saveAdvtCodeHeader(HrAdvertHdTO hrAdvertHdTO, String mod) throws WebException {
        String result = "";
        try {
            result = beanRemote.saveHrAdvertisementHeader(hrAdvertHdTO, mod);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    /**
     *
     * @param hrAdvertDtTO
     * @throws WebException
     */
    public void saveAdvtCodeDetails(HrAdvertDtTO hrAdvertDtTO, String mod) throws WebException {
        try {
            String result = "";
            beanRemote.saveHrAdvertisementDetail(hrAdvertDtTO, mod);
        } catch (ApplicationException e) {
            e.printStackTrace();
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

    /**
     *
     * @param compCode
     * @param advtCode
     * @throws WebException
     */
    public String deleteAdvertisementCode(int compcode, String advtCode) throws WebException {
        String result = null;
        try {
            result = beanRemote.deleteAdvertisementAction(compcode, advtCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    /**
     *
     * @param compCode
     * @param description
     * @return
     * @throws WebException
     * @throws ApplicationException
     */
    public List<HrMstStructTO> getInitialData(int compCode, String description) throws WebException, ApplicationException {
        List<HrMstStructTO> structMasterTOs = new ArrayList<HrMstStructTO>();
        try {
            structMasterTOs = beanRemote.getIntialData(compCode, description);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return structMasterTOs;
    }

    /**
     *
     * @param compCode
     * @param candidateCode
     * @return
     * @throws WebException
     */
    public List getSearchAppointmentLetterData(int compCode, String candidateCode) throws WebException {
        List searchAppLetterList = new ArrayList();
        try {
            searchAppLetterList = beanRemote.searchAppointmentLetterData(compCode, candidateCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return searchAppLetterList;
    }

    /**
     *
     * @param compCode
     * @param candidateIdView
     * @return
     * @throws WebException
     */
    public List getViewEditAppointmentLetterData(int compCode, String candidateIdView) throws WebException {
        List editAppLetterList = new ArrayList();
        try {
            editAppLetterList = beanRemote.viewEditAppointmentLetterData(compCode, candidateIdView);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return editAppLetterList;
    }

    /**
     *
     * @param hrInterviewDtTO
     * @return
     * @throws WebException
     */
    public String saveAppointmentLetterData(HrInterviewDtTO hrInterviewDtTO,HrInterviewDtSalTO interviewDtSalTO) throws WebException {
        String result = null;
        try {
            result = beanRemote.saveAppointmentLetter(hrInterviewDtTO,interviewDtSalTO);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    /**
     *
     * @param hrInterviewDtSalTO
     * @return
     * @throws WebException
     */
    public String updateAppointmentLetterData(HrInterviewDtSalTO hrInterviewDtSalTO) throws WebException {
        String result = null;
        try {
            result = beanRemote.updateAppointmentLetter(hrInterviewDtSalTO);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    /**
     *
     * @param compCode
     * @param candidateCode
     * @return
     * @throws WebException
     */
    public List searchCencelAppointmentData(int compCode, String candidateCode) throws WebException {
        List searchCencelList = new ArrayList();
        try {
            searchCencelList = beanRemote.searchCencelAppointmentData(compCode, candidateCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return searchCencelList;
    }

    /**
     *
     * @param compCode
     * @param interviewCode
     * @return
     * @throws WebException
     */
    public List viewCencelAppointmentData(int compCode, String interviewCode) throws WebException {
        List viewCencelList = new ArrayList();
        try {
            viewCencelList = beanRemote.viewCencelAppointmentData(compCode, interviewCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return viewCencelList;
    }

    /**
     *
     * @param hrInterviewDtTO
     * @return
     * @throws WebException
     */
    public String updateCencellationOfAppointment(HrInterviewDtTO hrInterviewDtTO) throws WebException {
        String result = null;
        try {
            result = beanRemote.updateCencellationOfAppointment(hrInterviewDtTO);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());

        }
        return result;
    }
}
