/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.facade.hrd;

import com.hrms.adaptor.ObjectAdaptorPersonnel;
import com.hrms.adaptor.ObjectAdaptorHr;
import javax.ejb.Stateless;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrMstDesgTO;
import com.hrms.common.to.HrOrgChartTO;
import com.hrms.common.utils.Validator;
import com.hrms.dao.HrMstDesgDAO;
import com.hrms.dao.HrOrgChartDAO;
import com.hrms.dao.exception.DAOException;
import com.hrms.entity.hr.HrMstDesg;
import com.hrms.entity.hr.HrOrgChart;
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
@Stateless(mappedName = "OrganizationFacade")
@Remote({OrganizationFacadeRemote.class})
public class OrganizationFacade implements OrganizationFacadeRemote {

    private static final Logger logger = Logger.getLogger(OrganizationFacade.class);
    @PersistenceContext
    private EntityManager em;

    @Override
    public List reportingStructureList(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrOrgChartDAO hrDatabankDAO = new HrOrgChartDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrDatabankDAO.reportingStructureEditDetail(compCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method reportingStructureList()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method reportingStructureList()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for reportingStructureList is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }

    @Override
    public String reportingDetailsSave(HrOrgChartTO orgObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(orgObj);
        HrOrgChart hrTemp = null;
        try {
            HrOrgChartDAO hrOrgChartDAO = new HrOrgChartDAO(em);
            hrTemp = ObjectAdaptorPersonnel.adaptToHrOrgChartEntity(orgObj);
            List checkData = hrOrgChartDAO.reportingStructurePrimrycheck(orgObj.getHrOrgChartPKTO().getCompCode(), orgObj.getHrOrgChartPKTO().getDesgCode());
            if (checkData.size() == 0) {
                hrOrgChartDAO.save(hrTemp);
            } else {
                return "Duplicate data exist please select another Designation";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method reportingDetailsSave()", e);
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
            logger.error("Exception occured while executing method reportingDetailsSave()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for reportingDetailsSave is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully saved.";
    }

    @Override
    public String reportingDetailsUpdate(HrOrgChartTO orgObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(orgObj);
        HrOrgChart hrTemp = null;
        try {
            HrOrgChartDAO hrOrgChartDAO = new HrOrgChartDAO(em);
            hrTemp = ObjectAdaptorPersonnel.adaptToHrOrgChartEntity(orgObj);
            hrOrgChartDAO.update(hrTemp);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method reportingDetailsUpdate()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("temporaryStaffUpdate")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.DUPLICATE_ENTITY_EXISTS,
                        "Duplicate entity exists."), e);
            } else if (e.getExceptionCode().getExceptionMessage().equals("EntityNotFoundException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_ENTITY_FOUND, "No Entity found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method reportingDetailsUpdate()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for reportingDetailsUpdate is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully updated.";
    }

    @Override
    public String reportingDetailsDelete(int compCode, String contcode) throws ApplicationException {
        long begin = System.nanoTime();
        HrOrgChart hrOrgChart = new HrOrgChart();
        try {
            HrOrgChartDAO hrOrgChartDAO = new HrOrgChartDAO(em);
            hrOrgChart = hrOrgChartDAO.deleteReportingDetails(compCode, contcode);
            hrOrgChartDAO.delete(hrOrgChart);
        } catch (Exception e) {
            logger.error("Exception occured while executing method contractorDetailsDelete()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for contractorDetailsDelete is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "true";
    }

    /******************** View Organization ********************/
    @Override
    public List viewOrgList(int compcode, String dept, String grade) throws ApplicationException {
        long begin = System.nanoTime();
        HrOrgChartDAO hrDatabankDAO = new HrOrgChartDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrDatabankDAO.viewOrgDetail(compcode, dept, grade);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method viewOrgList()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method viewOrgList()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for viewOrgList is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }

    /****************** Prepare Organization View *********************/
    @Override
    public List prepareOrgDetail(int compCode, String contCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstDesgDAO hrMstDesgDAO = new HrMstDesgDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrMstDesgDAO.prepareOrgnEditDetail(compCode, contCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method prepareOrgDetail()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method prepareOrgDetail()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for prepareOrgDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }

    @Override
    public List prepareOrgSaveDetail(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstDesgDAO hrMstDesgDAO = new HrMstDesgDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrMstDesgDAO.prepareOrgnizationSaveDetail(compCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method prepareOrgSaveDetail()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method prepareOrgSaveDetail()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for prepareOrgSaveDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }

    @Override
    public String prepareDetailsSave(HrMstDesgTO hrTempObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrTempObj);
        HrMstDesg hrTemp = null;
        try {
            HrMstDesgDAO hrMstDesgDAO = new HrMstDesgDAO(em);
            hrTemp = ObjectAdaptorHr.adaptToHrMstDesgEntity(hrTempObj);
            hrMstDesgDAO.save(hrTemp);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method prepareDetailsSave()", e);
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
            logger.error("Exception occured while executing method prepareDetailsSave()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for prepareDetailsSave is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully saved.";
    }

    @Override
    public String prepareDetailsUpdate(HrMstDesgTO hrTempObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrTempObj);
        HrMstDesg hrTemp = null;
        try {
            HrMstDesgDAO hrMstDesgDao = new HrMstDesgDAO(em);
            String result = hrMstDesgDao.deletePrepareOrgnDetails(hrTempObj.getHrMstDesgPKTO().getCompCode(), hrTempObj.getGradeCode());
            if (!result.equalsIgnoreCase("true")) {
                return "Data Not Updated";
            }
            HrMstDesgDAO hrMstDesgDAO = new HrMstDesgDAO(em);
            hrTemp = ObjectAdaptorHr.adaptToHrMstDesgEntity(hrTempObj);
            hrMstDesgDAO.save(hrTemp);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method prepareDetailsUpdate()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("temporaryStaffUpdate")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.DUPLICATE_ENTITY_EXISTS,
                        "Duplicate entity exists."), e);
            } else if (e.getExceptionCode().getExceptionMessage().equals("EntityNotFoundException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_ENTITY_FOUND, "No Entity found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method prepareDetailsUpdate()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for prepareDetailsUpdate is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully updated.";
    }

    @Override
    public List prepareUpdateCheckCode(int compCode, String gradeCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstDesgDAO hrMstDesgDAO = new HrMstDesgDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrMstDesgDAO.prepareOrgnUpdateDesgCode(compCode, gradeCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method prepareOrgSaveDetail()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method prepareOrgSaveDetail()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for prepareOrgSaveDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }
}
