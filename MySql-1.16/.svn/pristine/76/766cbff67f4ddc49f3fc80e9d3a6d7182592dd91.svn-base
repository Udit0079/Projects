/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.facade.hrd;

import javax.ejb.Stateless;
import com.hrms.adaptor.ObjectAdaptorHr;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrDataPrevCompTO;
import com.hrms.common.to.HrDataQualTO;
import com.hrms.common.to.HrDataReferenceTO;
import com.hrms.common.to.HrDatabankTO;
import com.hrms.common.utils.Validator;
import com.hrms.dao.HrAdvertHdDAO;
import com.hrms.dao.HrDataPrevCompDAO;
import com.hrms.dao.HrDataQualDAO;
import com.hrms.dao.HrDataReferenceDAO;
import com.hrms.dao.HrDatabankDAO;
import com.hrms.dao.exception.DAOException;
import com.hrms.entity.hr.HrDataPrevComp;
import com.hrms.entity.hr.HrDataQual;
import com.hrms.entity.hr.HrDataReference;
import com.hrms.entity.hr.HrDatabank;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author Zeeshan Waris
 */
@Stateless(mappedName = "DatabankFacade")
@Remote({DatabankFacadeRemote.class})
public class DatabankFacade implements DatabankFacadeRemote {

    private static final Logger logger = Logger.getLogger(DatabankFacade.class);
    @PersistenceContext
    private EntityManager em;

    @Override
    public List creationOfDatabnakSearchDetail(int compCode, String advtCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrAdvertHdDAO hrAdvertHdDao = new HrAdvertHdDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrAdvertHdDao.creationOfDatabankSearch(compCode, advtCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method creationOfDatabnakSearchDetail()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method creationOfDatabnakSearchDetail()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for creationOfDatabnakSearchDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }

    @Override
    public List<HrDatabankTO> creationOfDatabankViewDetails(int compCode, String canId) throws ApplicationException {
        long begin = System.nanoTime();
        HrDatabankDAO hrDatabankDAO = new HrDatabankDAO(em);
        List<HrDatabankTO> hrDatabankTOs = new ArrayList<HrDatabankTO>();
        try {
            List<HrDatabank> databankList = hrDatabankDAO.creationOfDatabankViewDeatails(compCode, canId);
            for (HrDatabank databank : databankList) {
                hrDatabankTOs.add(ObjectAdaptorHr.adaptToHrDatabankTO(databank));
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method creationOfDatabankViewDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method creationOfDatabankViewDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for creationOfDatabankViewDetails is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrDatabankTOs;
    }

    @Override
    public List creationOfDatabnakCustomerNameList(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrDatabankDAO hrDatabankDAO = new HrDatabankDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrDatabankDAO.creationOfDatabankConsultantList(compCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method creationOfDatabnakCustomerNameList()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method creationOfDatabnakCustomerNameList()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for creationOfDatabnakCustomerNameList is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }

    @Override
    public List creationOfDatabnakReferencedetails(int compCode, String candSRno, String advtCode, String jobCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrDatabankDAO hrDatabankDAO = new HrDatabankDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrDatabankDAO.extensionOfAppointmentReferanceDetails(compCode, candSRno, advtCode, jobCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method creationOfDatabnakCustomerNameList()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method creationOfDatabnakCustomerNameList()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for creationOfDatabnakCustomerNameList is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }

    @Override
    public List creationOfDatabnakQualificationDetails(int compCode, String candSRno, String advtCode, String jobCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrDatabankDAO hrDatabankDAO = new HrDatabankDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrDatabankDAO.extensionOfAppointmentQualificationDetails(compCode, candSRno, advtCode, jobCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method creationOfDatabnakCustomerNameList()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method creationOfDatabnakCustomerNameList()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for creationOfDatabnakCustomerNameList is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }

    @Override
    public List creationOfDatabnakPreEmpDetails(int compCode, String candSRno, String advtCode, String jobCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrDatabankDAO hrDatabankDAO = new HrDatabankDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrDatabankDAO.extensionOfAppointmentPreviousEmpDetail(compCode, candSRno, advtCode, jobCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method creationOfDatabnakPreEmpDetails()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method creationOfDatabnakPreEmpDetails()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for creationOfDatabnakPreEmpDetails is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }

    @Override
    public List creationOfDatabnakSaveValidAction() throws ApplicationException {
        long begin = System.nanoTime();
        HrDatabankDAO hrDatabankDAO = new HrDatabankDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrDatabankDAO.creationOfDatabankSaveValid();
        } catch (DAOException e) {
            logger.error("Exception occured while executing method creationOfDatabnakSaveValidAction()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method creationOfDatabnakSaveValidAction()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for creationOfDatabnakSaveValidAction is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }

    @Override
    public String saveCreationOfDatabankActionPlan(int compCode, String advertisementNo, String jobSpecification, String candidateId, String examinationQualification, String companyNameExperience, String referanceNameReferanceDetails, HrDatabankTO hrDatabankObj, HrDataQualTO hrDataQualObj, HrDataPrevCompTO hrDatabankPrevObj, HrDataReferenceTO hrDataRefObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrDatabankObj);
        Validator.isNull(hrDataQualObj);
        Validator.isNull(hrDatabankPrevObj);
        Validator.isNull(hrDataRefObj);
        HrDatabank hrDatabank = null;
        HrDataQual hrDataQual = null;
        HrDataPrevComp hrDataPrevComp = null;
        HrDataReference hrDataReference = null;
        try {
            HrDatabankDAO hrDatabankDAO = new HrDatabankDAO(em);
            hrDatabank = ObjectAdaptorHr.adaptToHrDatabankEntity(hrDatabankObj);
            hrDatabankDAO.save(hrDatabank);

            HrDatabankDAO hrDataQualData = new HrDatabankDAO(em);
            List<HrDataQual> hrDataQuality = hrDataQualData.creationofDatabankHrDataQualDeleteAction(compCode, advertisementNo, jobSpecification, candidateId, examinationQualification);
            if (hrDataQuality.size() != 0) {
                Iterator itr = hrDataQuality.iterator();
                hrDataQualData.delete((HrDataQual) itr.next());
            }
            HrDataQualDAO hrDataQualDAO = new HrDataQualDAO(em);
            hrDataQual = ObjectAdaptorHr.adaptToHrDataQualEntity(hrDataQualObj);
            hrDataQualDAO.save(hrDataQual);

            HrDatabankDAO hrDataPrevData = new HrDatabankDAO(em);
            List<HrDataPrevComp> hrDataPrevCompany = hrDataPrevData.creationofDatabankHrDataPrevCompDeleteAction(compCode, advertisementNo, jobSpecification, candidateId, companyNameExperience);
            if (hrDataPrevCompany.size() != 0) {
                Iterator itr = hrDataPrevCompany.iterator();
                hrDataPrevData.delete((HrDataPrevComp) itr.next());
            }
            HrDataPrevCompDAO hrDataPrevCompDAO = new HrDataPrevCompDAO(em);
            hrDataPrevComp = ObjectAdaptorHr.adaptToHrDataPrevCompEntity(hrDatabankPrevObj);
            hrDataPrevCompDAO.save(hrDataPrevComp);

            HrDatabankDAO hrDataRefData = new HrDatabankDAO(em);
            List<HrDataReference> hrDataRefe = hrDataRefData.creationofDatabankHrDataReferenceDeleteAction(compCode, advertisementNo, jobSpecification, candidateId, referanceNameReferanceDetails);
            if (hrDataRefe.size() != 0) {
                Iterator itr = hrDataRefe.iterator();
                hrDataRefData.delete((HrDataReference) itr.next());
            }
            HrDataReferenceDAO hrDataReferenceDAO = new HrDataReferenceDAO(em);
            hrDataReference = ObjectAdaptorHr.adaptToHrDataReferenceEntity(hrDataRefObj);
            hrDataReferenceDAO.save(hrDataReference);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method saveCreationOfDatabankActionPlan()", e);
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
            logger.error("Exception occured while executing method saveCreationOfDatabankActionPlan()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for saveCreationOfDatabankActionPlan is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully saved.";
    }

    @Override
    public String updateCreationOfDatabank(HrDatabankTO dataBankObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(dataBankObj);
        HrDatabank hrDatabank = null;
        try {
            HrDatabankDAO hrDatabankDAO = new HrDatabankDAO(em);
            hrDatabank = ObjectAdaptorHr.adaptToHrDatabankEntity(dataBankObj);
            hrDatabankDAO.update(hrDatabank);

        } catch (DAOException e) {
            logger.error("Exception occured while executing method updateDetail()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("updateCreationOfDatabank")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.DUPLICATE_ENTITY_EXISTS,
                        "Duplicate entity exists."), e);
            } else if (e.getExceptionCode().getExceptionMessage().equals("EntityNotFoundException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_ENTITY_FOUND, "No Entity found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method updateCreationOfDatabank()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for updateCreationOfDatabank is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully updated.";
    }


    @Override

      public String updateEvaluationOfDatabank(HrDatabankTO hrDatabankTO) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrDatabankTO);

        HrDatabank  hrDatabank = null;
        try {
            HrDatabankDAO  hrDatabankDao=new HrDatabankDAO(em);
            hrDatabank=ObjectAdaptorHr.adaptToHrDatabankEntity(hrDatabankTO);
            hrDatabankDao.updateEvaluationOfDatabank(hrDatabank);

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
        return "Data has been successfully updated.";
    }

    @Override
      public String saveEvaluationOfDatabank(HrDatabankTO hrDatabankTO) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrDatabankTO);

        HrDatabank  hrDatabank = null;
        try {
            HrDatabankDAO  hrDatabankDao=new HrDatabankDAO(em);
            hrDatabank=ObjectAdaptorHr.adaptToHrDatabankEntity(hrDatabankTO);
            hrDatabankDao.saveEvaluationOfDatabank(hrDatabank);
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
    public List editEvaluationOfDatabank(int compCode, String organisation) throws ApplicationException {
        long begin = System.nanoTime();
        HrDatabankDAO hrDatabankDao = new HrDatabankDAO(em);
        List editEvaluationList = new ArrayList();
        try {
            editEvaluationList = hrDatabankDao.editEvaluationOfDatabank(compCode,organisation);
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
        return editEvaluationList;

    }

    @Override
    public List addListEvaluationOfDatabank(int compCode, String organisation,String designation) throws ApplicationException {
        long begin = System.nanoTime();
        HrDatabankDAO hrDatabankDao = new HrDatabankDAO(em);
         List addEvaluationList = new ArrayList();
        try {
            addEvaluationList = hrDatabankDao.addListEvaluationOfDatabank(compCode,organisation,designation);
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
        return addEvaluationList;
    }
}
