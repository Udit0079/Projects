/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.facade.hrd;

import com.hrms.common.to.HrDatabankTO;
import com.hrms.common.to.HrInterviewHdTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
import javax.ejb.Remote;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrAdvertDtTO;
import com.hrms.common.to.HrAdvertHdTO;
import com.hrms.common.to.HrInterviewDtSalTO;
import com.hrms.common.to.HrInterviewDtTO;
import com.hrms.common.to.HrMstStructTO;
import java.util.List;

/**
 *
 * @author Zeeshan Waris
 */
@Remote
public interface InterviewActionPlanFacadeRemote {

    public List<HrMstStructTO> getIntialData(int compCode, String code) throws ApplicationException;

    public List<HrDatabankTO> getViewDetails(int compCode, char calint) throws ApplicationException;

    public String generateAdvtCode(int compCode) throws ApplicationException;

    public List preintInterviewersName(int companyCode, String nameConsultant) throws ApplicationException;

    public List preintInterviewersUpdateDetails(int companyCode, String intPreCode) throws ApplicationException;

    public List preintInterviewersCodeDetails(int companyCode, String intPreCode) throws ApplicationException;

    public String savePreIntActionPlan(HrInterviewHdTO hrinterviewObj, List<HrInterviewDtTO> interviewDtTOs,List<HrDatabankTO> hrDatabankTOs) throws ApplicationException;

    public String updatePreIntActionPlan(HrInterviewHdTO hrinterviewObj) throws ApplicationException;

    public List<HrMstStructTO> getInterviewerNameList(int compCode, String code) throws ApplicationException;

    public List<HrPersonnelDetailsTO> getInterviewerSecondNameList(int compCode) throws ApplicationException;

    public List postIntInterviewersDetails(int companyCode, String advtcode) throws ApplicationException;

    public String savePostIntActionPlan(HrInterviewDtTO hrinterviewDtObj) throws ApplicationException;

    public String updatePostIntActionPlan(HrInterviewDtTO hrInterviewDtObj) throws ApplicationException;

    public List postIntIntViewDetails(int companyCode, String preCode) throws ApplicationException;

    public List postIntIntEditDetails(int compCode, String intCode, String advtCode, String jobCode, String candSrno) throws ApplicationException;

    public List<HrMstStructTO> getInterviewerNameListExtension(int compCode, String code) throws ApplicationException;

    public List extensionOfAppointmentSearch(int compCode, String candidateCode) throws ApplicationException;

    public List extensionOfAppointmentAdviceEditList(int compCode, String intCode) throws ApplicationException;

    public String extensionOfAppointmentAdviceSaveAction(HrInterviewDtTO hrInterviewDtObj) throws ApplicationException;

    public String extensionOfAppointmentAdviceUpdateAction(HrInterviewDtTO hrInterviewDtObj) throws ApplicationException;

    public List<HrMstStructTO> getdesigDepartLocationList(String desc, int compCode) throws ApplicationException;

    public List<HrAdvertHdTO> getAdvtHeaderDetails(int compCode, String advtCode) throws ApplicationException;

    public List getAdvertisementCodedetails(int compCode, String advtCode) throws ApplicationException;

    public String saveHrAdvertisementHeader(HrAdvertHdTO hrAdvertHdTO, String mod) throws ApplicationException;

    public void saveHrAdvertisementDetail(HrAdvertDtTO hrAdvertDtTO, String mod) throws ApplicationException;

    public List<HrAdvertHdTO> findAdvCode(int compCode, String advCode) throws ApplicationException;

    public String generateAdvtAdverisementCode(int compCode) throws ApplicationException;

    public List<HrDatabankTO> searchAppointmentLetterData(int compCode, String candidateCode) throws ApplicationException;

    public List<HrDatabankTO> viewEditAppointmentLetterData(int compCode, String interviewCode) throws ApplicationException;

    public String saveAppointmentLetter(HrInterviewDtTO hrInterviewDtTO,HrInterviewDtSalTO interviewDtSalTO) throws ApplicationException;

    public String updateAppointmentLetter(HrInterviewDtSalTO hrInterviewDtSalTO) throws ApplicationException;

    public List<HrDatabankTO> searchCencelAppointmentData(int compCode, String candidateCode) throws ApplicationException;

    public List<HrDatabankTO> viewCencelAppointmentData(int compCode, String interviewCode) throws ApplicationException;

    public String updateCencellationOfAppointment(HrInterviewDtTO hrInterviewDtTO) throws ApplicationException;

    public String deleteAdvertisementAction(int compCode, String consCode) throws ApplicationException;
}
