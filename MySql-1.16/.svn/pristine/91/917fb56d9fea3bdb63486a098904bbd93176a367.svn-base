/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.facade.hrd;

import com.hrms.common.to.HrDatabankTO;
import com.hrms.common.to.HrInterviewHdTO;
import com.hrms.entity.BaseEntity;
import com.hrms.entity.hr.HrDatabank;
import com.hrms.entity.hr.HrInterviewHd;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.dao.HrInterviewHdDAO;
import com.hrms.dao.HrPersonnelDetailsDAO;
import com.hrms.entity.hr.HrPersonnelDetails;
import java.util.Iterator;
import javax.ejb.Stateless;
import com.hrms.adaptor.ObjectAdaptorHr;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrAdvertDtTO;
import com.hrms.common.to.HrAdvertHdTO;
import com.hrms.common.to.HrInterviewDtSalTO;
import com.hrms.common.to.HrInterviewDtTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.utils.Validator;
import com.hrms.dao.HrAdvertDtDAO;
import com.hrms.dao.HrAdvertHdDAO;
import com.hrms.dao.HrDatabankDAO;
import com.hrms.dao.HrInterviewDtDAO;
import com.hrms.dao.HrInterviewDtSalDAO;
import com.hrms.dao.HrMstStructDAO;
import com.hrms.dao.exception.DAOException;
import com.hrms.entity.hr.HrAdvertDt;
import com.hrms.entity.hr.HrAdvertHd;
import com.hrms.entity.hr.HrDatabankPK;
import com.hrms.entity.hr.HrInterviewDt;
import com.hrms.entity.hr.HrInterviewDtSal;
import com.hrms.entity.hr.HrMstStruct;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author Zeeshan Waris
 */
@Stateless(mappedName = "InterviewActionPlanFacade")
@Remote({InterviewActionPlanFacadeRemote.class})
public class InterviewActionPlanFacade implements InterviewActionPlanFacadeRemote {
    
    private static final Logger logger = Logger.getLogger(InterviewActionPlanFacade.class);
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public List<HrMstStructTO> getIntialData(int compCode, String code) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstStructDAO structMasterDao = new HrMstStructDAO(em);
        List<HrMstStructTO> structMasterTOs = new ArrayList<HrMstStructTO>();
        try {
            List<HrMstStruct> structMasterList = structMasterDao.findByStructCode(compCode, code);
            for (HrMstStruct hrMstStruct : structMasterList) {
                structMasterTOs.add(ObjectAdaptorHr.adaptToStructMasterTO(hrMstStruct));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getIntialData()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getIntialData()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getIntialData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return structMasterTOs;
    }
    
    @Override
    public List<HrDatabankTO> getViewDetails(int compCode, char calint) throws ApplicationException {
        long begin = System.nanoTime();
        HrDatabankDAO hrDatabankDAO = new HrDatabankDAO(em);
        List<HrDatabankTO> hrDatabankTOs = new ArrayList<HrDatabankTO>();
        try {
            List<HrDatabank> databankList = hrDatabankDAO.viewDetails(compCode, calint);
            for (HrDatabank databank : databankList) {
                hrDatabankTOs.add(ObjectAdaptorHr.adaptToHrDatabankTO(databank));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getViewDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getViewDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getTableDetails is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrDatabankTOs;
    }
    
    @Override
    public String generateAdvtCode(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrInterviewHdDAO hrAdvertHdDAO = new HrInterviewHdDAO(em);
        String advertisementCode;
        try {
            advertisementCode = hrAdvertHdDAO.preinterviewAdvtCode(compCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method generateAdvtCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method generateAdvtCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for generateAdvtCode is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return advertisementCode;
    }
    
    @Override
    public List preintInterviewersName(int companyCode, String nameConsultant) throws ApplicationException {
        long begin = System.nanoTime();
        HrInterviewHdDAO hrAdvertHdDAO = new HrInterviewHdDAO(em);
        List dropDownList = new ArrayList();
        try {
            dropDownList = hrAdvertHdDAO.preintInterviewerSearch(companyCode, nameConsultant);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method preintInterviewersName()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method preintInterviewersName()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for generateAdvtCode is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return dropDownList;
    }
    
    @Override
    public List preintInterviewersUpdateDetails(int companyCode, String intPreCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrInterviewHdDAO hrAdvertHdDAO = new HrInterviewHdDAO(em);
        List dropDownList = new ArrayList();
        try {
            dropDownList = hrAdvertHdDAO.preintInterviewerUpdateView(companyCode, intPreCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method preintInterviewersUpdateDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method preintInterviewersUpdateDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for generateAdvtCode is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return dropDownList;
    }
    
    @Override
    public List preintInterviewersCodeDetails(int companyCode, String intPreCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrInterviewHdDAO hrAdvertHdDAO = new HrInterviewHdDAO(em);
        List dropDownList = new ArrayList();
        try {
            dropDownList = hrAdvertHdDAO.preintInterviewersCode(companyCode, intPreCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method preintInterviewersCodeDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method preintInterviewersCodeDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for generateAdvtCode is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return dropDownList;
    }
    
    @Override
    public String savePreIntActionPlan(HrInterviewHdTO hrinterviewObj,List<HrInterviewDtTO> interviewDtTOs,List<HrDatabankTO> hrDatabankTOs) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrinterviewObj);
        HrInterviewHd hrInterviewHd = null;
        HrInterviewDt hrInterviewDt = null;
        HrDatabank hrDatabank = null;
        try {
            HrInterviewHdDAO hrInterviewHdDao = new HrInterviewHdDAO(em);
            hrInterviewHd = ObjectAdaptorHr.adaptToHrInterviewHdEntity(hrinterviewObj);
            hrInterviewHdDao.save(hrInterviewHd);
            
            HrInterviewDtDAO hrInterviewDtDao = new HrInterviewDtDAO(em);
            for(HrInterviewDtTO interviewDtTO:interviewDtTOs)
            {
            hrInterviewDt = ObjectAdaptorHr.adaptToHrInterviewDtEntity(interviewDtTO);
            hrInterviewDtDao.save(hrInterviewDt);
            }
            int count=0;
           for(HrDatabankTO hrDatabankTO:hrDatabankTOs)
            {
                HrDatabankPK hdpk=new HrDatabankPK();
                hdpk = ObjectAdaptorHr.adaptToHrDatabankPKEntity(hrDatabankTO.getHrDatabankPKTO());
                HrDatabankDAO databank=new HrDatabankDAO(em);
                BaseEntity findById = databank.findById(new HrDatabank(), hdpk);
                if(findById!=null)
                {
                    HrDatabank hrDatabank1=(HrDatabank)findById;
                    hrDatabank1.setCallInt(hrDatabankTO.getCallInt());
                    databank.update(hrDatabank1);
                    count+=1;
                }
            }
           
            if (count!=hrDatabankTOs.size()) {
                return "Data not saved.";
            }
        } catch (DAOException e) {
            e.printStackTrace();
            logger.error("Exception occured while executing method savePreIntActionPlan()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("ConstraintViolationException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.DUPLICATE_ENTITY_EXISTS,
                        "Duplicate entity exists."), e);
            } else if (e.getExceptionCode().getExceptionMessage().equals("EntityNotFoundException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_ENTITY_FOUND, "No Entity found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method savePreIntActionPlan()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for saveDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully saved.";
    }
    
    @Override
    public String updatePreIntActionPlan(HrInterviewHdTO hrinterviewObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrinterviewObj);
        HrInterviewHd hrInterviewHd = null;
        try {
            HrInterviewHdDAO hrInterviewHdDao = new HrInterviewHdDAO(em);
            hrInterviewHd = ObjectAdaptorHr.adaptToHrInterviewHdEntity(hrinterviewObj);
            hrInterviewHdDao.update(hrInterviewHd);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method updatePreIntActionPlan()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("ConstraintViolationException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.DUPLICATE_ENTITY_EXISTS,
                        "Duplicate entity exists."), e);
            } else if (e.getExceptionCode().getExceptionMessage().equals("EntityNotFoundException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_ENTITY_FOUND, "No Entity found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method updatePreIntActionPlan()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for updateDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully updated.";
    }
    
    @Override
    public List<HrMstStructTO> getInterviewerNameList(int compCode, String code) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstStructDAO structMasterDao = new HrMstStructDAO(em);
        List<HrMstStructTO> structMasterTOs = new ArrayList<HrMstStructTO>();
        try {
            List<HrMstStruct> structMasterList = structMasterDao.findByStructCode(compCode, code);
            for (HrMstStruct hrMstStruct : structMasterList) {
                structMasterTOs.add(ObjectAdaptorHr.adaptToStructMasterTO(hrMstStruct));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getInterviewerNameList()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getInterviewerNameList()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getInterviewerNameList is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return structMasterTOs;
    }
    
    @Override
    public List<HrPersonnelDetailsTO> getInterviewerSecondNameList(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrPersonnelDetailsDAO personnelDetailsDao = new HrPersonnelDetailsDAO(em);
        List<HrPersonnelDetailsTO> hrPersonnelDetailsTo = new ArrayList<HrPersonnelDetailsTO>();
        try {
            List<HrPersonnelDetails> personnelList = personnelDetailsDao.findByCompCode(compCode);
            for (HrPersonnelDetails hrPersonnelDetails : personnelList) {
                hrPersonnelDetailsTo.add(ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrPersonnelDetails));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getInterviewerSecondNameList()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getInterviewerSecondNameList()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getInterviewerSecondNameList is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrPersonnelDetailsTo;
    }
    
    @Override
    public List postIntInterviewersDetails(int companyCode, String advtcode) throws ApplicationException {
        long begin = System.nanoTime();
        HrInterviewHdDAO hrAdvertHdDAO = new HrInterviewHdDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrAdvertHdDAO.postintInterviewerDetail(companyCode, advtcode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method postIntInterviewersDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method postIntInterviewersDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for postIntInterviewersDetails is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }
    
    @Override
    public String savePostIntActionPlan(HrInterviewDtTO hrinterviewDtObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrinterviewDtObj);
        HrInterviewDt hrInterviewDt = null;
        try {
            HrInterviewDtDAO hrInterviewDtDao = new HrInterviewDtDAO(em);
            hrInterviewDt = ObjectAdaptorHr.adaptToHrInterviewDtEntity(hrinterviewDtObj);
            hrInterviewDtDao.update(hrInterviewDt);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method savePostIntActionPlan()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("ConstraintViolationException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.DUPLICATE_ENTITY_EXISTS,
                        "Duplicate entity exists."), e);
            } else if (e.getExceptionCode().getExceptionMessage().equals("EntityNotFoundException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_ENTITY_FOUND, "No Entity found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method savePostIntActionPlan()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for savePostIntActionPlan is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully saved.";
    }
    
    @Override
    public String updatePostIntActionPlan(HrInterviewDtTO hrInterviewDtObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrInterviewDtObj);
        HrInterviewDt hrInterviewDt = null;
        try {
            HrInterviewDtDAO hrInterviewDtDao = new HrInterviewDtDAO(em);
            hrInterviewDt = ObjectAdaptorHr.adaptToHrInterviewDtEntity(hrInterviewDtObj);
            String result = hrInterviewDtDao.postinterviewUpdate(hrInterviewDt);
            if (!result.equalsIgnoreCase("true")) {
                return "Data not saved.";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method savePreIntActionPlan()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("ConstraintViolationException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.DUPLICATE_ENTITY_EXISTS,
                        "Duplicate entity exists."), e);
            } else if (e.getExceptionCode().getExceptionMessage().equals("EntityNotFoundException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_ENTITY_FOUND, "No Entity found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method savePreIntActionPlan()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for saveDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully updated.";
    }
    
    @Override
    public List postIntIntViewDetails(int companyCode, String preCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrInterviewDtDAO hrInterviewDtDAO = new HrInterviewDtDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrInterviewDtDAO.postintIntView(companyCode, preCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method postIntIntViewDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method postIntIntViewDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for postIntIntViewDetails is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }
    
    @Override
    public List postIntIntEditDetails(int compCode, String intCode, String advtCode, String jobCode, String candSrno) throws ApplicationException {
        long begin = System.nanoTime();
        HrInterviewDtDAO hrInterviewDtDAO = new HrInterviewDtDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrInterviewDtDAO.postintIntEdit(compCode, intCode, advtCode, jobCode, candSrno);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method postIntIntEditDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method postIntIntEditDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for postIntIntEditDetails is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }
    
    @Override
    public List<HrMstStructTO> getInterviewerNameListExtension(int compCode, String code) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstStructDAO structMasterDao = new HrMstStructDAO(em);
        List<HrMstStructTO> structMasterTOs = new ArrayList<HrMstStructTO>();
        try {
            List<HrMstStruct> structMasterList = structMasterDao.findByStructCode(compCode, code);
            for (HrMstStruct hrMstStruct : structMasterList) {
                structMasterTOs.add(ObjectAdaptorHr.adaptToStructMasterTO(hrMstStruct));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getInterviewerNameList()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getInterviewerNameList()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getInterviewerNameList is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return structMasterTOs;
    }
    
    @Override
    public List extensionOfAppointmentSearch(int compCode, String candidateCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrDatabankDAO hrDatabankDAO = new HrDatabankDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrDatabankDAO.extensionOfAppointmentAdviceSearch(compCode, candidateCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method extensionOfAppointmentSearch()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method extensionOfAppointmentSearch()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for extensionOfAppointmentSearch is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }
    
    @Override
    public List extensionOfAppointmentAdviceEditList(int compCode, String intCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrDatabankDAO hrDatabankDAO = new HrDatabankDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrDatabankDAO.extensionOfAppointmentAdviceEditList(compCode, intCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method extensionOfAppointmentAdviceEditList()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method extensionOfAppointmentAdviceEditList()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for extensionOfAppointmentAdviceEditList is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }
    
    @Override
    public String extensionOfAppointmentAdviceSaveAction(HrInterviewDtTO hrInterviewDtObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrInterviewDtObj);
        HrInterviewDt hrInterviewDt = null;
        try {
            HrInterviewDtDAO hrInterviewDtDao = new HrInterviewDtDAO(em);
            hrInterviewDt = ObjectAdaptorHr.adaptToHrInterviewDtEntity(hrInterviewDtObj);
            String result = hrInterviewDtDao.extensionOfAppointmentAdviceSave(hrInterviewDt);
            if (!result.equalsIgnoreCase("true")) {
                return "Data not saved.";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method extensionOfAppointmentAdviceSaveAction()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("ConstraintViolationException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.DUPLICATE_ENTITY_EXISTS,
                        "Duplicate entity exists."), e);
            } else if (e.getExceptionCode().getExceptionMessage().equals("EntityNotFoundException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_ENTITY_FOUND, "No Entity found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method extensionOfAppointmentAdviceSaveAction()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for saveDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully saved.";
    }
    
    @Override
    public String extensionOfAppointmentAdviceUpdateAction(HrInterviewDtTO hrInterviewDtObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrInterviewDtObj);
        HrInterviewDt hrInterviewDt = null;
        try {
            HrInterviewDtDAO hrInterviewDtDao = new HrInterviewDtDAO(em);
            hrInterviewDt = ObjectAdaptorHr.adaptToHrInterviewDtEntity(hrInterviewDtObj);
            String result = hrInterviewDtDao.extensionOfAppointmentAdviceUpdate(hrInterviewDt);
            if (!result.equalsIgnoreCase("true")) {
                return "Data not saved.";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method extensionOfAppointmentAdviceUpdateAction()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("ConstraintViolationException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.DUPLICATE_ENTITY_EXISTS,
                        "Duplicate entity exists."), e);
            } else if (e.getExceptionCode().getExceptionMessage().equals("EntityNotFoundException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_ENTITY_FOUND, "No Entity found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method extensionOfAppointmentAdviceUpdateAction()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for saveDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully updated.";
    }

    /********************** advertisement ************************/
    @Override
    public List<HrMstStructTO> getdesigDepartLocationList(String desc, int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstStructDAO hrMstStructDAO = new HrMstStructDAO(em);
        List<HrMstStructTO> hrMstStructTOs = new ArrayList<HrMstStructTO>();
        try {
            List<HrMstStruct> structMasterList = hrMstStructDAO.findByStructCode(compCode, desc);
            for (HrMstStruct hrMstStruct : structMasterList) {
                hrMstStructTOs.add(ObjectAdaptorHr.adaptToStructMasterTO(hrMstStruct));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getdesigDepartLocationList()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getdesigDepartLocationList()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getdesigDepartLocationList is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrMstStructTOs;
    }

    /**
     *
     * @param hrAdvertHdTO
     * @param mod
     * @return
     * @throws ApplicationException
     */
    @Override
    public String saveHrAdvertisementHeader(HrAdvertHdTO hrAdvertHdTO, String mod) throws ApplicationException {
        long begin = System.nanoTime();
        try {
            String result;
            HrAdvertHdDAO hrAdvertHdDAO = new HrAdvertHdDAO(em);
            HrAdvertHd hrAdvertHd = ObjectAdaptorHr.adaptToHrAdvertHdEntity(hrAdvertHdTO);
            
            if (mod.equalsIgnoreCase("ADD")) {
                hrAdvertHdDAO.save(hrAdvertHd);
            }
            if (mod.equalsIgnoreCase("UPDATE")) {
                hrAdvertHdDAO.update(hrAdvertHd);
            }
            
        } catch (DAOException e) {
            logger.error("Exception occured while executing method saveHrAdvertisementHeader()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("ConstraintViolationException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.DUPLICATE_ENTITY_EXISTS,
                        "Duplicate entity exists."), e);
            } else if (e.getExceptionCode().getExceptionMessage().equals("EntityNotFoundException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_ENTITY_FOUND, "No Entity found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveHrAdvertisementHeader()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for saveHrAdvertisementHeader is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "true";
        
    }

    /**
     *
     *
     * @param hrAdvertDtTO
     * @throws ApplicationException
     */
    @Override
    public void saveHrAdvertisementDetail(HrAdvertDtTO hrAdvertDtTO, String mod) throws ApplicationException {
        long begin = System.nanoTime();
        try {
            HrAdvertDt hrAdvertDt = ObjectAdaptorHr.adaptToHrAdvertDtEntity(hrAdvertDtTO);
            HrAdvertDtDAO hrAdvertDtDAO = new HrAdvertDtDAO(em);
            
            if (mod.equalsIgnoreCase("UPDATE")) {
//            hrAdvertDtDAO.delete(hrAdvertDt, hrAdvertDt.getHrAdvertDtPK());
//            hrAdvertDtDAO.save(hrAdvertDt);
                hrAdvertDtDAO.update(hrAdvertDt);
            } else {
                hrAdvertDtDAO.save(hrAdvertDt);
            }
            
        } catch (DAOException e) {
            logger.error("Exception occured while executing method saveHrAdvertisementDetail()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("ConstraintViolationException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.DUPLICATE_ENTITY_EXISTS,
                        "Duplicate entity exists."), e);
            } else if (e.getExceptionCode().getExceptionMessage().equals("EntityNotFoundException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_ENTITY_FOUND, "No Entity found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveHrAdvertisementDetail()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for saveHrAdvertisementDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
    }

    /**
     *
     * @param compCode
     * @param advtCode
     * @return
     * @throws ApplicationException
     */
    @Override
    public List<HrAdvertHdTO> getAdvtHeaderDetails(int compCode, String advtCode) throws ApplicationException {
        long begin = System.nanoTime();
        List<HrAdvertHdTO> hrAdvertCodeTOs = new ArrayList<HrAdvertHdTO>();
        HrAdvertHdDAO hrAdvertHdDAO = new HrAdvertHdDAO(em);
        try {
            List<HrAdvertHd> hrAdvertCodeDetails = hrAdvertHdDAO.getAdvtCodeHeaderDetails(compCode, advtCode);
            for (HrAdvertHd hrAdvertHdCodeDetails : hrAdvertCodeDetails) {
                hrAdvertCodeTOs.add(ObjectAdaptorHr.adaptToHrAdvertHdTO(hrAdvertHdCodeDetails));
            }
            
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getAdvtHeaderDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("ConstraintViolationException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.DUPLICATE_ENTITY_EXISTS,
                        "Duplicate entity exists."), e);
            } else if (e.getExceptionCode().getExceptionMessage().equals("EntityNotFoundException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_ENTITY_FOUND, "No Entity found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAdvtHeaderDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getdesigDepartLocationList is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrAdvertCodeTOs;
    }
//

    /**
     *
     * @param compCode
     * @param advtCode
     * @return
     * @throws ApplicationException
     */
    @Override
    public List getAdvertisementCodedetails(int compCode, String advtCode) throws ApplicationException {
        long begin = System.nanoTime();
        List l1 = new ArrayList();
        try {
            HrAdvertHdDAO hrAdvertHdDAO = new HrAdvertHdDAO(em);
            l1 = hrAdvertHdDAO.getAdvertisementCodeDetails(compCode, advtCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getAdvertisementCodedetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("ConstraintViolationException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.DUPLICATE_ENTITY_EXISTS,
                        "Duplicate entity exists."), e);
            } else if (e.getExceptionCode().getExceptionMessage().equals("EntityNotFoundException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_ENTITY_FOUND, "No Entity found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getAdvertisementCodedetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getdesigDepartLocationList is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return l1;
    }
    
    @Override
    public String deleteAdvertisementAction(int compCode, String consCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrAdvertDt hrAdvertDt = new HrAdvertDt();
        HrAdvertHd hrAdvertHd = new HrAdvertHd();
        try {
            HrAdvertHdDAO hrAdvertHdDAO = new HrAdvertHdDAO(em);
            hrAdvertHd = hrAdvertHdDAO.deleteAdvertisementHd(compCode, consCode);
            hrAdvertHdDAO.delete(hrAdvertHd);
            
            HrAdvertDtDAO hrAdvertDtDAO = new HrAdvertDtDAO(em);
            List checkData = hrAdvertDtDAO.deleteAdvertisementDtCheck(compCode, consCode);
            if (checkData.size() > 0) {
                Iterator iterator = checkData.iterator();
                while (iterator.hasNext()) {
                    HrAdvertDt entity = (HrAdvertDt) iterator.next();
                    hrAdvertDtDAO.delete(entity);
                }
//            hrAdvertDt = hrAdvertDtDAO.deleteAdvertisementDt(compCode, consCode);
//            hrAdvertDtDAO.delete(hrAdvertDt);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteAdvertisementAction()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for deleteAdvertisementAction is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "true";
    }

    /**
     *
     * @param compCode
     * @param advCode
     * @return
     * @throws ApplicationException
     */
    @Override
    public List<HrAdvertHdTO> findAdvCode(int compCode, String advCode) throws ApplicationException {
        long begin = System.nanoTime();
        try {
            HrAdvertHdDAO hrAdvertHdDAO = new HrAdvertHdDAO(em);
            List<HrAdvertHdTO> hrAdvertHdListTOs = new ArrayList<HrAdvertHdTO>();
            if (advCode == null) {
                advCode = "%";
            } else {
                advCode = advCode + "%";
            }
            List<HrAdvertHd> hrAdvertHdList = hrAdvertHdDAO.getAdvertisementCodeList(compCode, advCode);
            for (HrAdvertHd hrAdvertHd : hrAdvertHdList) {
                hrAdvertHdListTOs.add(ObjectAdaptorHr.adaptToHrAdvertHdTO(hrAdvertHd));
            }
            return hrAdvertHdListTOs;
            
        } catch (DAOException e) {
            logger.error("Exception occured while executing method findAdvCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("ConstraintViolationException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.DUPLICATE_ENTITY_EXISTS,
                        "Duplicate entity exists."), e);
            } else if (e.getExceptionCode().getExceptionMessage().equals("EntityNotFoundException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_ENTITY_FOUND, "No Entity found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method findAdvCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        
    }

    /**
     *
     * @param compCode
     * @return
     * @throws ApplicationException
     */
    @Override
    public String generateAdvtAdverisementCode(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrAdvertHdDAO hrAdvertHdDAO = new HrAdvertHdDAO(em);
        String advertisementCode;
        try {
            advertisementCode = hrAdvertHdDAO.generateAdvtCode(compCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method generateAdvtCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method generateAdvtCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for generateAdvtCode is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return advertisementCode;
    }

    ///////////////////////////////////////// Appointment Letter //////////////////////////////////////////
    @Override
    public List searchAppointmentLetterData(int compCode, String candidateCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrDatabankDAO hrDatabankDao = new HrDatabankDAO(em);
        //   List<HrDatabankTO> hrDatabankTOs = new ArrayList<HrDatabankTO>();
        List searchApptLetterList = new ArrayList();
        try {
            searchApptLetterList = hrDatabankDao.searchListAppointmentLetter(compCode, candidateCode);
//            for (HrDatabank hrDatabank : hrDatabankList) {
//                hrDatabankTOs.add(ObjectAdaptorHr.adaptToHrDatabankTO(hrDatabank));
//            }

        } catch (DAOException e) {
            logger.error("Exception occured while executing method searchData()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getFinYear()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getFinYear is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return searchApptLetterList;
        
    }
    
    @Override
    public List viewEditAppointmentLetterData(int compCode, String interviewCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrDatabankDAO hrDatabankDao = new HrDatabankDAO(em);
        List editApptLetterList = new ArrayList();
        try {
            
            editApptLetterList = hrDatabankDao.viewListCanAppAdvice(compCode, interviewCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method searchData()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getFinYear()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getFinYear is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return editApptLetterList;
    }
    
    @Override
    public String saveAppointmentLetter(HrInterviewDtTO hrInterviewDtTO,HrInterviewDtSalTO interviewDtSalTO) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrInterviewDtTO);
        
        HrInterviewDt hrInterviewDt = null;
        try {
            HrInterviewDtDAO hrInterviewDtDao = new HrInterviewDtDAO(em);
            hrInterviewDt = ObjectAdaptorHr.adaptToHrInterviewDtEntity(hrInterviewDtTO);
            hrInterviewDtDao.updateTabForSaveAppLetter(hrInterviewDt);
            
            HrInterviewDtSalDAO hrInterviewDtSalDAO=new HrInterviewDtSalDAO(em);
            HrInterviewDtSal hrInterviewDtSalEntity = ObjectAdaptorHr.adaptToHrInterviewDtSalEntity(interviewDtSalTO);
            hrInterviewDtSalDAO.save(hrInterviewDtSalEntity);
            
        } catch (DAOException e) {
            
            logger.error("Exception occured while executing method saveLeaveDetail()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("ConstraintViolationException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.DUPLICATE_ENTITY_EXISTS,
                        "Duplicate entity exists."), e);
            } else if (e.getExceptionCode().getExceptionMessage().equals("EntityNotFoundException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_ENTITY_FOUND, "No Entity found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveLeaveDetail()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for saveLeaveDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully saved.";
    }
    
    @Override
    public String updateAppointmentLetter(HrInterviewDtSalTO hrInterviewDtSalTO) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrInterviewDtSalTO);
        HrInterviewDtSal hrInterviewDtSal = null;
        try {
            HrInterviewDtSalDAO hrInterviewDtSalDao = new HrInterviewDtSalDAO(em);
            hrInterviewDtSal = ObjectAdaptorHr.adaptToHrInterviewDtSalEntity(hrInterviewDtSalTO);
            hrInterviewDtSalDao.updateAppointmentLetter(hrInterviewDtSal);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method updateAppointmentLetter()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("ConstraintViolationException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.DUPLICATE_ENTITY_EXISTS,
                        "Duplicate entity exists."), e);
            } else if (e.getExceptionCode().getExceptionMessage().equals("EntityNotFoundException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_ENTITY_FOUND, "No Entity found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method saveLeaveDetail()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for updateAppointmentLetter is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully updated.";
    }

    ///////////////////////////////////////// Cencellation of Appointment Letter //////////////////////////////////////////
    @Override
    public List searchCencelAppointmentData(int compCode, String candidateCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrDatabankDAO hrDatabankDao = new HrDatabankDAO(em);
        List searchCencelAppointmentList = new ArrayList();
        try {
            searchCencelAppointmentList = hrDatabankDao.searchListCanAppAdvice(compCode, candidateCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method searchData()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getFinYear()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getFinYear is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return searchCencelAppointmentList;
        
    }
    
    @Override
    public List viewCencelAppointmentData(int compCode, String interviewCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrDatabankDAO hrDatabankDao = new HrDatabankDAO(em);
        List viewCencelList = new ArrayList();
        
        try {
            viewCencelList = hrDatabankDao.viewListCanAppAdvice(compCode, interviewCode);
            
        } catch (DAOException e) {
            logger.error("Exception occured while executing method searchData()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getFinYear()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getFinYear is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return viewCencelList;
        
    }
    
    @Override
    public String updateCencellationOfAppointment(HrInterviewDtTO hrInterviewDtTO) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrInterviewDtTO);
        HrInterviewDt hrInterviewDt = null;
        try {
            HrInterviewDtDAO hrInterviewDtDao = new HrInterviewDtDAO(em);
            hrInterviewDt = ObjectAdaptorHr.adaptToHrInterviewDtEntity(hrInterviewDtTO);
            String result = hrInterviewDtDao.updateAppointmentCencel(hrInterviewDt);
            if (!result.equalsIgnoreCase("true")) {
                return "Data not saved.";
            }
            
        } catch (DAOException e) {
            logger.error("Exception occured while executing method updateAppointmentCencel()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("ConstraintViolationException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.DUPLICATE_ENTITY_EXISTS,
                        "Duplicate entity exists."), e);
            } else if (e.getExceptionCode().getExceptionMessage().equals("EntityNotFoundException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_ENTITY_FOUND, "No Entity found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method updateAppointmentCencel()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for updateAppointment is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully saved.";
    }
}
