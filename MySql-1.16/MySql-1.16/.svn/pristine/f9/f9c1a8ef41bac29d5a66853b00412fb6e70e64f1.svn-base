/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.facade.hrd;



import com.hrms.common.to.HrConsultantTO;
import com.hrms.dao.HrConsultantDAO;
import com.hrms.entity.hr.HrConsultant;
import javax.ejb.Stateless;
import com.hrms.adaptor.ObjectAdaptorHr;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrDirectRecTO;
import com.hrms.common.to.HrManpowerTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.utils.Validator;
import com.hrms.dao.HrDirectRecDAO;
import com.hrms.dao.HrManpowerDAO;
import com.hrms.dao.HrMstStructDAO;
import com.hrms.dao.HrPersonnelDetailsDAO;
import com.hrms.dao.exception.DAOException;
import com.hrms.entity.hr.HrDirectRec;
import com.hrms.entity.hr.HrManpower;
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
@Stateless(mappedName = "RecruitmentFacade")
@Remote({RecruitmentFacadeRemote.class})
public class RecruitmentFacade implements RecruitmentFacadeRemote {

    private static final Logger logger = Logger.getLogger(RecruitmentFacade.class);
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<HrMstStructTO> getDirectRecruitmentList(int compCode, String code) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstStructDAO structMasterDao = new HrMstStructDAO(em);
        List<HrMstStructTO> structMasterTOs = new ArrayList<HrMstStructTO>();
        try {
            List<HrMstStruct> structMasterList = structMasterDao.findByStructCode(compCode, code);
            for (HrMstStruct hrMstStruct : structMasterList) {
                structMasterTOs.add(ObjectAdaptorHr.adaptToStructMasterTO(hrMstStruct));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getDirectRecruitmentList()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getDirectRecruitmentList()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getDirectRecruitmentList is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return structMasterTOs;
    }

    @Override
    public List directRecruitmentZoneCode(int compCode, String zone) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstStructDAO hrMstStructDao = new HrMstStructDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrMstStructDao.directRecruitmentZoneList(compCode, zone);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method directRecruitmentZoneCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method directRecruitmentZoneCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for directRecruitmentZoneCode is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }

    @Override
    public List directRecruitmentInterviewerDetails(int compCode, String empId) throws ApplicationException {
        long begin = System.nanoTime();
        HrPersonnelDetailsDAO HrPersonnelDetailsDao = new HrPersonnelDetailsDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = HrPersonnelDetailsDao.directRecruitmentInterviewerDetail(compCode, empId);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method directRecruitmentInterviewerDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method directRecruitmentInterviewerDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for directRecruitmentInterviewerDetails is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }

    @Override
    public List directRecruitmentInterviewerByName(int compCode, String empName) throws ApplicationException {
        long begin = System.nanoTime();
        HrPersonnelDetailsDAO HrPersonnelDetailsDao = new HrPersonnelDetailsDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = HrPersonnelDetailsDao.directRecruitmentInterviewerDetailByName(compCode, empName);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method directRecruitmentInterviewerByName()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method directRecruitmentInterviewerByName()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for directRecruitmentInterviewerByName is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }

    @Override
    public List directRecruitmentViewDetail(int compCode, String candName) throws ApplicationException {
        long begin = System.nanoTime();
        HrDirectRecDAO hrDirectRecDAO = new HrDirectRecDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrDirectRecDAO.directRecruitmentInterviewerViewDetail(compCode, candName);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method directRecruitmentViewDetail()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method directRecruitmentViewDetail()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for directRecruitmentViewDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }

    @Override
    public List directRecruitmentUpdateDetail(int compCode, String arNo) throws ApplicationException {
        long begin = System.nanoTime();
        HrDirectRecDAO hrDirectRecDAO = new HrDirectRecDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrDirectRecDAO.directRecruitmentUpdateDetail(compCode, arNo);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method directRecruitmentUpdateDetail()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method directRecruitmentUpdateDetail()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for directRecruitmentUpdateDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }

    @Override
    public List directRecruitmentSaveCheckAction(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrDirectRecDAO hrDirectRecDAO = new HrDirectRecDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrDirectRecDAO.directRecruitmentSaveCheck(compCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method directRecruitmentSaveCheckAction()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method directRecruitmentSaveCheckAction()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for directRecruitmentSaveCheckAction is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }

    @Override
    public List directRecruitmentSaveValidAction() throws ApplicationException {
        long begin = System.nanoTime();
        HrDirectRecDAO hrDirectRecDAO = new HrDirectRecDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrDirectRecDAO.directRecruitmentSaveValid();
        } catch (DAOException e) {
            logger.error("Exception occured while executing method directRecruitmentSaveValidAction()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method directRecruitmentSaveValidAction()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for directRecruitmentSaveValidAction is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }

    @Override
    public List directRecruitmentSuperCodeForSave(int compcode, String superId) throws ApplicationException {
        long begin = System.nanoTime();
        HrDirectRecDAO hrDirectRecDAO = new HrDirectRecDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrDirectRecDAO.directRecruitmentSuperCode(compcode, superId);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method directRecruitmentSuperCodeForSave()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method directRecruitmentSuperCodeForSave()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for directRecruitmentSuperCodeForSave is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }

    @Override
    public String directRecruitmentSave(HrDirectRecTO hrDirectObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrDirectObj);
        HrDirectRec hrDirectRec = null;
        try {
            HrDirectRecDAO hrDirectRecDao = new HrDirectRecDAO(em);
            hrDirectRec = ObjectAdaptorHr.adaptToHrDirectRecEntity(hrDirectObj);
            hrDirectRecDao.save(hrDirectRec);

        } catch (DAOException e) {
            logger.error("Exception occured while executing method directRecruitmentSave()", e);
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
            logger.error("Exception occured while executing method directRecruitmentSave()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for directRecruitmentSave is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully saved.";
    }

    @Override
    public String directRecruitmentUpdate(HrDirectRecTO hrDirectObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrDirectObj);
        HrDirectRec hrDirect = new HrDirectRec();
        try {
            HrDirectRecDAO hrDirectRecDao = new HrDirectRecDAO(em);
            hrDirect = hrDirectRecDao.directRecruitmentUpdateCheck(hrDirectObj.getHrDirectRecPKTO().getCompCode(), hrDirectObj.getHrDirectRecPKTO().getArno());
            hrDirect.setArdate(hrDirectObj.getArdate());
            hrDirect.setZoneCode(hrDirectObj.getZoneCode());
            hrDirect.setDesigCode(hrDirectObj.getDesigCode());
            hrDirect.setLocationCode(hrDirectObj.getLocationCode());
            hrDirect.setCandName(hrDirectObj.getCandName());
            hrDirect.setFatherName(hrDirectObj.getFatherName());
            hrDirect.setAppointmentDate(hrDirectObj.getAppointmentDate());
            hrDirect.setContactNo(hrDirectObj.getContactNo());
            hrDirect.setBasicSalary(hrDirectObj.getBasicSalary());
            hrDirect.setHra(hrDirectObj.getHra());
            hrDirect.setTa(hrDirectObj.getTa());
            hrDirect.setMedicalAllw(hrDirectObj.getMedicalAllw());
            hrDirect.setTotal(hrDirectObj.getTotal());
            hrDirect.setAddress(hrDirectObj.getAddress());
            hrDirect.setCity(hrDirectObj.getCity());
            hrDirect.setState(hrDirectObj.getState());
            hrDirect.setPin(hrDirectObj.getPin());
            hrDirect.setEmailId(hrDirectObj.getEmailId());
            hrDirect.setJobStatus(hrDirectObj.getJobStatus());
            hrDirect.setRemarks(hrDirectObj.getRemarks());
            hrDirect.setEffectiveDate(hrDirectObj.getEffectiveDate());
            hrDirect.setQualCode(hrDirectObj.getQualCode());
            hrDirect.setSuperId(hrDirectObj.getSuperId());
            hrDirect.setInitiatorId(hrDirectObj.getInitiatorId());
            hrDirect.setDeptHeadId(hrDirectObj.getDeptHeadId());
            hrDirect.setHrdApproval(hrDirectObj.getHrdApproval());
            hrDirect.setMdApproval(hrDirectObj.getMdApproval());
            hrDirect.setStatFlag(hrDirectObj.getStatFlag());
            hrDirect.setStatUpFlag(hrDirectObj.getStatUpFlag());
            hrDirect.setModDate(hrDirectObj.getModDate());
            hrDirect.setDefaultComp(hrDirectObj.getDefaultComp());
            hrDirect.setAuthBy(hrDirectObj.getAuthBy());
            hrDirect.setEnteredBy(hrDirectObj.getEnteredBy());
            hrDirectRecDao.update(hrDirect);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method directRecruitmentUpdate()", e);
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
            logger.error("Exception occured while executing method directRecruitmentUpdate()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for directRecruitmentUpdate is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully updated.";
    }

    @Override
    public String directRecruitmentDeleteRecord(int compCode, String arNo) throws ApplicationException {
        long begin = System.nanoTime();
        HrDirectRec hrDirect = new HrDirectRec();
        try {
            HrDirectRecDAO hrDirectRecDAO = new HrDirectRecDAO(em);
            hrDirect = hrDirectRecDAO.directRecruitmentUpdateCheck(compCode, arNo);
            hrDirectRecDAO.delete(hrDirect);
        } catch (Exception e) {
            logger.error("Exception occured while executing method directRecruitmentDeleteRecord()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for directRecruitmentDeleteRecord is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "true";
    }

    @Override
    public List<HrMstStructTO> getIntialData(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstStructDAO structMasterDao = new HrMstStructDAO(em);
        List<HrMstStructTO> structMasterTOs = new ArrayList<HrMstStructTO>();
        try {
            List<HrMstStruct> structMasterList = structMasterDao.findByStructCode(compCode, "DES%");
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
    public List<HrConsultantTO> getTableDetails(int compCode, String consultantName) throws ApplicationException {
        long begin = System.nanoTime();
        HrConsultantDAO hrConsultantDAO = new HrConsultantDAO(em);
        List<HrConsultantTO> hrConsultantTOs = new ArrayList<HrConsultantTO>();
        try {
            List<HrConsultant> consultantList = hrConsultantDAO.findByConsultantName(compCode, consultantName);
            for (HrConsultant consultant : consultantList) {
                hrConsultantTOs.add(ObjectAdaptorHr.adaptToHrConsultantTO(consultant));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getTableDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getTableDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getTableDetails is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrConsultantTOs;
    }

    @Override
    public List<HrConsultantTO> getConsultantDetails(int compCode, String consName, String consultantName) throws ApplicationException {
        long begin = System.nanoTime();
        HrConsultantDAO hrConsultantDAO = new HrConsultantDAO(em);
        List<HrConsultantTO> hrConsultantTOs = new ArrayList<HrConsultantTO>();
        try {
            List<HrConsultant> consultantList = hrConsultantDAO.consultantDetails(compCode, consName, consultantName);
            for (HrConsultant consultant : consultantList) {
                hrConsultantTOs.add(ObjectAdaptorHr.adaptToHrConsultantTO(consultant));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getTableDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getTableDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getTableDetails is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrConsultantTOs;
    }

    @Override
    public String saveConsultant(HrConsultantTO consultantObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(consultantObj);
        HrConsultant hrConsultant = null;
        try {
            HrConsultantDAO consultantDao = new HrConsultantDAO(em);
            hrConsultant = ObjectAdaptorHr.adaptToHrConsultantEntity(consultantObj);
            List checkData = consultantDao.consultantPrimrycheck(consultantObj.getHrConsultantPKTO().getCompCode(), consultantObj.getHrConsultantPKTO().getConsCode());
            if (checkData.size() == 0) {
                consultantDao.save(hrConsultant);
            } else {
                return "Duplicate data exist please fill another Code";
            }

        } catch (DAOException e) {
            logger.error("Exception occured while executing method saveDetail()", e);
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
            logger.error("Exception occured while executing method saveDetail()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for saveDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully saved.";
    }

    @Override
    public String updateConsultant(HrConsultantTO consultantObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(consultantObj);
        HrConsultant hrConsultant = null;
        try {
            HrConsultantDAO consultantDao = new HrConsultantDAO(em);
            hrConsultant = ObjectAdaptorHr.adaptToHrConsultantEntity(consultantObj);
            consultantDao.update(hrConsultant);

        } catch (DAOException e) {
            logger.error("Exception occured while executing method updateDetail()", e);
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
            logger.error("Exception occured while executing method updateDetail()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for updateDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully updated.";
    }

    @Override
    public String deleteConsultantAction(int compCode, String consCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrConsultant hrConsultant = new HrConsultant();
        try {
            HrConsultantDAO consultantDao = new HrConsultantDAO(em);
            hrConsultant = consultantDao.deleteConsultant(compCode, consCode);
            consultantDao.delete(hrConsultant);
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteConsultant()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for deleteConsultant is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "true";
    }

    @Override
    public List<HrMstStructTO> getManPowerList(int compCode, String code) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstStructDAO structMasterDao = new HrMstStructDAO(em);
        List<HrMstStructTO> structMasterTOs = new ArrayList<HrMstStructTO>();
        try {
            List<HrMstStruct> structMasterList = structMasterDao.findByStructCode(compCode, code);
            for (HrMstStruct hrMstStruct : structMasterList) {
                structMasterTOs.add(ObjectAdaptorHr.adaptToStructMasterTO(hrMstStruct));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method getDirectRecruitmentList()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method getDirectRecruitmentList()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getDirectRecruitmentList is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return structMasterTOs;
    }

    @Override
    public List manpowerDetails(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrManpowerDAO hrManpowerDao = new HrManpowerDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrManpowerDao.manpowerDetail(compCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method manpowerDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method manpowerDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for manpowerDetails is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }

    @Override
    public String deleteManPowerPlanAction(int compCode, int year, String month, String zone, String deptCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrManpower hrManpower = new HrManpower();
        try {
            HrManpowerDAO hrManpowerDAO = new HrManpowerDAO(em);
            hrManpower = hrManpowerDAO.manpowerDeleteAction(compCode, year, month, zone, deptCode);
            hrManpowerDAO.delete(hrManpower);
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteConsultant()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for deleteConsultant is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "true";
    }

    @Override
    public List manpowerPlanningGradeCode(int compCode, String desgCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrManpowerDAO hrManpowerDao = new HrManpowerDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrManpowerDao.manpowerGradeCode(compCode, desgCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method manpowerPlanningGradeCode()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method manpowerPlanningGradeCode()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for manpowerPlanningGradeCode is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }

    @Override
    public List manpowerPlanningEmployeeNo(int compCode, String zones, String deptCode, String desgCode, String gradeCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrManpowerDAO hrManpowerDao = new HrManpowerDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrManpowerDao.manpowerEmployeeNo(compCode, zones, deptCode, desgCode, gradeCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method manpowerPlanningEmployeeNo()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method manpowerPlanningEmployeeNo()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for manpowerPlanningEmployeeNo is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }

    @Override
    public String manpowerSave(HrManpowerTO hrDirectObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrDirectObj);
        HrManpower hrManpower = null;
        try {
            HrManpowerDAO hrManpowerDao = new HrManpowerDAO(em);
            hrManpower = ObjectAdaptorHr.adaptToHrManpowerEntity(hrDirectObj);
            hrManpowerDao.save(hrManpower);

        } catch (DAOException e) {
            logger.error("Exception occured while executing method manpowerSave()", e);
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
            logger.error("Exception occured while executing method manpowerSave()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for manpowerSave is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully saved.";
    }

    @Override
    public String manpowerupdate(HrManpowerTO hrmanpowerObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrmanpowerObj);
        HrManpower hrmanpower = null;
        try {
            HrManpowerDAO hrManpowerDao = new HrManpowerDAO(em);
            hrmanpower = ObjectAdaptorHr.adaptToHrManpowerEntity(hrmanpowerObj);
            String result = hrManpowerDao.manpowerUpdate(hrmanpower);
            if (!result.equalsIgnoreCase("true")) {
                return "Data not updated";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method manpowerupdate()", e);
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
            logger.error("Exception occured while executing method manpowerupdate()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for manpowerupdate is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully updated.";
    }
}
