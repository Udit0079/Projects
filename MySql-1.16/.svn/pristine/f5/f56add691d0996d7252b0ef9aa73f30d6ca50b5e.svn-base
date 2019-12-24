/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.delegate;

import com.hrms.common.to.ClearSlipDetTO;
import com.hrms.common.to.ClearSlipHdTO;
import com.hrms.common.to.HrApprisalDetailsTO;
import com.hrms.common.to.HrExitInterviewDtTO;
import com.hrms.common.to.HrExitInterviewHdTO;
import com.hrms.common.to.HrPromotionDetailsTO;
import com.hrms.common.to.HrSeparationDetailsTO;
import com.hrms.facade.personnel.PersonnelMasterFacadeRemote;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.utils.HrServiceLocator;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.exception.WebException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class PersonnelMasterManagementDelegate {

    private final String jndiHomeName = "PersonnelMasterFacade";
    private PersonnelMasterFacadeRemote beanRemote = null;

    public PersonnelMasterManagementDelegate() throws ServiceLocatorException {
        try{
            Object lookup = HrServiceLocator.getInstance().lookup(jndiHomeName);
            beanRemote = (PersonnelMasterFacadeRemote) lookup;
        }catch (Exception e) {
            throw new ServiceLocatorException(e);            
        }        
    }

    public List<HrMstStructTO> getInitialData(int compCode, String strcode) throws WebException {
        List<HrMstStructTO> HrMstStructTOs = new ArrayList<HrMstStructTO>();
        try {
            HrMstStructTOs = beanRemote.getIntialData(compCode, strcode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return HrMstStructTOs;
    }

    /**
     *
     * @param compCode
     * @return
     */
    public List viewDataAcceptance(int compCode) throws WebException {
        List result = null;
        try {
            result = beanRemote.viewDataAcceptancefacade(compCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    /**
     *
     * @param compCode
     * @param search
     * @param seatchflag
     * @return
     * @throws WebException
     * @throws ApplicationException
     */
    public List<HrPersonnelDetailsTO> findDataPersonalDetailDelegat(int compCode, String search, int seatchflag) throws WebException, ApplicationException {
        List<HrPersonnelDetailsTO> data = new ArrayList<HrPersonnelDetailsTO>();
        try {
            data = beanRemote.findDataPersonalDetailFacade(compCode, search, seatchflag);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return data;
    }

    /**
     *
     * @param compCode
     * @param empid
     * @param flag
     * @param to
     * @return
     * @throws WebException
     * @throws ApplicationException
     */
    
    public String performoperationAcceptLetterDelegate(int compCode, String empid, String flag, HrSeparationDetailsTO to) throws WebException {
        String msg = "";
        try {
            msg = beanRemote.performOperationAcceptanceletter(compCode, empid, flag, to);
        }catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
 

    /**
     *
     * @param compCode
     * @param empId
     * @return
     */
   
    public List selectDataEmpidDelegate(int compCode, String empId)throws WebException, ApplicationException {
        List result = null;
        try {
            result = beanRemote.selectDataEmpidFacade(compCode, empId);
        }catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    /**
     *
     * @param compCode
     * @return
     */
    public List viewDataAppraisalDelegate(int compCode) throws WebException, ApplicationException{
        List result = null;
        try {
            result = beanRemote.viewDataAppraisalFacade(compCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }
/**
 *
 * @param flag
 * @param to
 * @return
 * @throws WebException
 * @throws ApplicationException
 */
    public String performOperationAppraisalDelegate(String flag, HrApprisalDetailsTO to) throws WebException, ApplicationException {
        try {
            String msg = beanRemote.performOperationAppraisal(flag, to);
            return msg;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }
/**
 *
 * @param compCode
 * @return
 */
    public List viewDataSettlement(int compCode) throws WebException, ApplicationException{
        try {
            List result = beanRemote.viewDataSettlementLetter(compCode);
            return result;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }
/**
 *
 * @param compCode
 * @return
 */
    public List viewSettelementSearch(int compCode) throws WebException, ApplicationException{
        try {
            List result = beanRemote.viewSetelementSearch(compCode);
            return result;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }
/**
 *
 * @param compCode
 * @param empId
 * @return
 */
    public List empDataSetelementDelegate(int compCode, String empId)throws WebException, ApplicationException {
        try {
            List result = beanRemote.empDataSetelementFacade(compCode, empId);
            return result;
        }catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }
    
/**
 *
 * @param compCode
 * @return
 */
    public List viewDataPromotionDelegate(int compCode) throws WebException, ApplicationException{
        try {
            List result = beanRemote.viewDataPromotionFacade(compCode);
            return result;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }

/**
 *
 * @param compCode
 * @return
 */
    public String generateArnCodeDelegate(int compCode)throws WebException, ApplicationException {
        String arno = "";
        try {
            arno = beanRemote.generateArnCodeFacade(compCode);

        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return arno;
    }
    
/**
 *
 * @param flaf
 * @param to
 * @param to1
 * @return
 */
    public String performoperationPromotionDelegate(String flaf, HrPromotionDetailsTO to, HrPersonnelDetailsTO to1) throws WebException, ApplicationException{
        String msg = "";
        try {
            msg = beanRemote.performoperationPromotion(flaf, to, to1);

        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
/**
 *
 * @param flag
 * @param to
 * @param to1
 * @return
 */
    public String performOprationSettelement(String flag, ClearSlipHdTO to, ClearSlipDetTO to1)throws WebException, ApplicationException {
        String msg = "";
        try {
            msg = beanRemote.performOperationSettlement(flag, to, to1);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
    //addviewData
/**
 *
 * @param flag
 * @param compCode
 * @return
 * @throws WebException
 * @throws ApplicationException
 */
    public List<HrPersonnelDetailsTO> addviewDatafacade(String flag, int compCode) throws WebException, ApplicationException {
        List<HrPersonnelDetailsTO> data = new ArrayList<HrPersonnelDetailsTO>();
        try {
            data = beanRemote.addviewData(flag, compCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return data;
    }
/**
 *
 * @param flag
 * @param hrPersonnelDetailsTO
 * @param exitInterviewDtTO
 * @param exitInterviewHdTO
 * @return
 */
    public String performOperationExitInterview(String flag, HrPersonnelDetailsTO hrPersonnelDetailsTO, HrExitInterviewDtTO exitInterviewDtTO, HrExitInterviewHdTO exitInterviewHdTO) throws WebException, ApplicationException{
        String msg = "";
        try {
            msg = beanRemote.performOperationExitInterview(flag, hrPersonnelDetailsTO, exitInterviewDtTO, exitInterviewHdTO);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return msg;
    }
/**
 *
 * @param comCode
 * @param empCode
 * @return
 */
    public List viewDataExitInterviewDelegate(int comCode, long empCode)throws WebException, ApplicationException {
        List result = null;
        try {
            result = beanRemote.viewDataExitInterviewFacade(comCode, empCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }
    
    public List viewEmployeeForTaxSearch() throws WebException, ApplicationException{
        try {
            List result = beanRemote.viewEmployeeForTaxSearch();
            return result;
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
    }
}
