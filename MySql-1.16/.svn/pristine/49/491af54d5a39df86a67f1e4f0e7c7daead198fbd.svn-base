/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.facade.personnel;

import com.hrms.adaptor.ObjectAdaptorPersonnel;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.ClearSlipDetTO;
import com.hrms.common.to.ClearSlipHdTO;
import com.hrms.common.to.HrApprisalDetailsTO;
import com.hrms.common.to.HrExitInterviewDtTO;
import com.hrms.common.to.HrExitInterviewHdTO;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.common.to.HrPromotionDetailsTO;
import com.hrms.common.to.HrSeparationDetailsTO;
import com.hrms.dao.ClearSlipDetDAO;
import com.hrms.dao.ClearSlipHdDAO;
import com.hrms.dao.HrApprisalDetailsDAO;
import com.hrms.dao.HrExitInterviewDtDAO;
import com.hrms.dao.HrExitInterviewHdDAO;
import com.hrms.dao.HrMstStructDAO;
import com.hrms.dao.HrPersonnelDetailsDAO;
import com.hrms.dao.HrPromotionDetailsDAO;
import com.hrms.dao.HrSeparationDetailsDAO;
import com.hrms.dao.exception.DAOException;
import com.hrms.entity.hr.HrMstStruct;
import com.hrms.entity.hr.HrPersonnelDetails;
import com.hrms.entity.personnel.ClearSlipDet;
import com.hrms.entity.personnel.ClearSlipHd;
import com.hrms.entity.personnel.HrApprisalDetails;
import com.hrms.entity.personnel.HrExitInterviewDt;
import com.hrms.entity.personnel.HrExitInterviewHd;
import com.hrms.entity.personnel.HrPromotionDetails;
import com.hrms.entity.personnel.HrSeparationDetails;
import com.hrms.entity.personnel.HrSeparationDetailsPK;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author admin
 */
@Stateless(mappedName = "PersonnelMasterFacade")
@Remote({PersonnelMasterFacadeRemote.class})
public class PersonnelMasterFacade implements PersonnelMasterFacadeRemote {

    private static final Logger logger = Logger.getLogger(PersonnelMasterFacade.class);
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<HrMstStructTO> getIntialData(int compCode, String strcode) throws ApplicationException {
        long begin = System.nanoTime();
        HrMstStructDAO structMasterDao = new HrMstStructDAO(em);
        List<HrMstStructTO> structMasterTOs = new ArrayList<HrMstStructTO>();
        try {
            List<HrMstStruct> structMasterList = structMasterDao.findByStructCode(compCode, strcode);
            for (HrMstStruct hrMstStruct : structMasterList) {
                structMasterTOs.add(com.hrms.adaptor.ObjectAdaptorHr.adaptToStructMasterTO(hrMstStruct));
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
    public List viewDataAcceptancefacade(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        List result = null;
        HrPersonnelDetailsDAO hrPersonnelDetailsdao = new HrPersonnelDetailsDAO(em);
        try {
            result = hrPersonnelDetailsdao.viewDataAcceptance(compCode);
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
        return result;
    }

    @Override
    public String performOperationAcceptanceletter(int compCode, String empid, String flag, HrSeparationDetailsTO to) throws ApplicationException {
        String empcode, msg = "System Error";
        long begin = System.nanoTime();
        long empCode;
        HrPersonnelDetailsDAO hrprdao = new HrPersonnelDetailsDAO(em);
        HrSeparationDetailsDAO hrsepdao = new HrSeparationDetailsDAO(em);
        try {
            if (flag.equals("ADD")) {
                empcode = hrprdao.checkreturnEmpId(compCode, empid);
                if (empcode != null) {
                    empCode = Long.parseLong(empcode);
                    to.getHrSeparationDetailsPK().setEmpCode(empCode);
                    HrSeparationDetails entity = ObjectAdaptorPersonnel.adaptToPersonnelSeprationEntity(to);
                    hrsepdao.save(entity);
                    msg = "Data has been saved successfully!";
                }
            }
            if (flag.equals("EDIT")) {
                empcode = hrprdao.checkreturnEmpId(compCode, empid);
                if (empcode != null) {
                    empCode = Long.parseLong(empcode);
                    to.getHrSeparationDetailsPK().setEmpCode(empCode);
                    HrSeparationDetails entity = ObjectAdaptorPersonnel.adaptToPersonnelSeprationEntity(to);
                    hrsepdao.update(entity);
                    msg = "Data has been updated successfully!";

                }
            }
            if (flag.equals("DELETE")) {

                empcode = hrprdao.checkreturnEmpId(compCode, empid);
                if (empcode != null) {
                    empCode = Long.parseLong(empcode);
                    to.getHrSeparationDetailsPK().setEmpCode(empCode);
                    HrSeparationDetails entity = ObjectAdaptorPersonnel.adaptToPersonnelSeprationEntity(to);
                    // HrSeparationDetails entity = hrsepdao.checkDataByCompCodeEmpCode(compCode, empCode);
                    hrsepdao.delete(entity, entity.getHrSeparationDetailsPK());
                    msg = "Data has been deleted successfully!";
                }
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
        return msg;
    }

    @Override
    public List<HrPersonnelDetailsTO> findDataPersonalDetailFacade(int compCode, String search, int seatchflag)
            throws ApplicationException {
        long begin = System.nanoTime();
        HrPersonnelDetailsDAO obj = new HrPersonnelDetailsDAO(em);
        List<HrPersonnelDetailsTO> hrPersonnelDetailsTO = new ArrayList<HrPersonnelDetailsTO>();
        try {
            List<HrPersonnelDetails> hr = obj.findDataPersonalDetail(compCode, search, seatchflag);
            for (HrPersonnelDetails h : hr) {
                hrPersonnelDetailsTO.add(com.hrms.adaptor.ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(h));

            }

        } catch (Exception e) {
            logger.error("Exception occured while executing method getIntialData()", e);
            throw new ApplicationException(e.getMessage());
//            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
//                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for getIntialData is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrPersonnelDetailsTO;
    }
    //selectDataEmpid(int compCode,String empId)

    @Override
    public List selectDataEmpidFacade(int compCode, String empId) throws ApplicationException {
        long begin = System.nanoTime();
        List result = null;
        HrPersonnelDetailsDAO hrprsnldao = new HrPersonnelDetailsDAO(em);
        try {
            result = hrprsnldao.selectDataEmpid(compCode, empId);

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
        return result;
    }

    @Override
    public List viewDataAppraisalFacade(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        List data = null;
        try {
            HrApprisalDetailsDAO daoobj = new HrApprisalDetailsDAO(em);
            data = daoobj.viewAppraisalData(compCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
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
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for payrollCalendarSaveUpdate is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return data;

    }

    @Override
    public String performOperationAppraisal(String flag, HrApprisalDetailsTO to) throws ApplicationException {
        long begin = System.nanoTime();
        String msg = "";
        HrApprisalDetailsDAO daoobj = new HrApprisalDetailsDAO(em);
        try {
            if (flag.equals("Add")) {
                HrApprisalDetails entity = ObjectAdaptorPersonnel.addaptEntityHrApprisalDetailsTo(to);
                daoobj.save(entity);
                msg = "Data has been succesfully Saved !";
            }
            if (flag.equals("Edit")) {
                HrApprisalDetails entity = ObjectAdaptorPersonnel.addaptEntityHrApprisalDetailsTo(to);
                daoobj.update(entity);
                msg = "Data has been succesfully Updated !";
            }
            if (flag.equals("Delete")) {
                HrApprisalDetails entity = ObjectAdaptorPersonnel.addaptEntityHrApprisalDetailsTo(to);
                daoobj.delete(entity, entity.getHrApprisalDetailsPK());
                msg = "Data has been succesfully Deleted !";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
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
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for payrollCalendarSaveUpdate is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return msg;
    }

    @Override
    public List viewDataSettlementLetter(int compCode) throws ApplicationException {
        ClearSlipHdDAO daoobj = new ClearSlipHdDAO(em);
        try {
            List result = daoobj.viewDataSettelementLetter(compCode);
            return result;
        } catch (DAOException e) {
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
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
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
    }

    @Override
    public List viewSetelementSearch(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrSeparationDetailsDAO dao = new HrSeparationDetailsDAO(em);
        List result = new ArrayList();
        try {
            result = dao.setelementSearch(compCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
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
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for payrollCalendarSaveUpdate is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return result;
    }

    @Override
    public List empDataSetelementFacade(int compCode, String empId) throws ApplicationException {
        long begin = System.nanoTime();
        List result = new ArrayList();
        try {
            HrSeparationDetailsDAO dao = new HrSeparationDetailsDAO(em);
            result = dao.empDataSetelement(compCode, empId);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
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
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for payrollCalendarSaveUpdate is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return result;
    }

    @Override
    public String performOperationSettlement(String flag, ClearSlipHdTO to, ClearSlipDetTO to1) throws ApplicationException {
        long begin = System.nanoTime();
        String msg = "";
        ClearSlipHdDAO clearSlipHdDAO = new ClearSlipHdDAO(em);
        ClearSlipDetDAO clearSlipDetDAO = new ClearSlipDetDAO(em);
        HrSeparationDetailsDAO hrSeparationDetailsDAO = new HrSeparationDetailsDAO(em);
        try {
            if (flag.equalsIgnoreCase("ADD")) {
                ClearSlipHd entity = ObjectAdaptorPersonnel.addaptEntityClearSlipHd(to);
                clearSlipHdDAO.save(entity);
                ClearSlipDet clearSlipDet = ObjectAdaptorPersonnel.addaptEntityClearSlipDet(to1);
                clearSlipDetDAO.save(clearSlipDet);
                HrSeparationDetailsPK hrSeparationDetailsPK = new HrSeparationDetailsPK();
                hrSeparationDetailsPK.setCompCode(to.getClearSlipHdPK().getCompCode());
                hrSeparationDetailsPK.setEmpCode(to.getClearSlipHdPK().getEmpCode());
                HrSeparationDetails entity1 = (HrSeparationDetails) hrSeparationDetailsDAO.findById(new HrSeparationDetails(), hrSeparationDetailsPK);
                entity1.setStatFlag("N");
                hrSeparationDetailsDAO.update(entity1);
                msg = "Data has been succesfully Saved !";

            }
            if (flag.equalsIgnoreCase("EDIT")) {
                ClearSlipHd clearSlipHd = clearSlipHdDAO.findByPrimaryKey(to);
                if (clearSlipHd != null) {
                    ClearSlipHd entity = ObjectAdaptorPersonnel.addaptEntityClearSlipHd(to);
                    clearSlipHdDAO.update(entity);
                    ClearSlipDet clearSlipDet = ObjectAdaptorPersonnel.addaptEntityClearSlipDet(to1);
                    clearSlipDetDAO.update(clearSlipDet);
                }
                msg = "Data has been succesfully Updated !";
            }
            if (flag.equalsIgnoreCase("DELETE")) {
                ClearSlipHd clearSlipHd = ObjectAdaptorPersonnel.addaptEntityClearSlipHd(to);
                ClearSlipDet clearSlipDet = ObjectAdaptorPersonnel.addaptEntityClearSlipDet(to1);
                HrSeparationDetailsPK hrSeparationDetailsPK = new HrSeparationDetailsPK();
                hrSeparationDetailsPK.setCompCode(to.getClearSlipHdPK().getCompCode());
                hrSeparationDetailsPK.setEmpCode(to.getClearSlipHdPK().getEmpCode());
                HrSeparationDetails entity = (HrSeparationDetails) hrSeparationDetailsDAO.findById(new HrSeparationDetails(), hrSeparationDetailsPK);
                entity.setStatFlag("N");
                hrSeparationDetailsDAO.update(entity);
                clearSlipHdDAO.delete(clearSlipHd, clearSlipHd.getClearSlipHdPK());
                clearSlipDetDAO.delete(clearSlipDet, clearSlipDet.getClearSlipDetPK());
                msg = "Data has been succesfully Deleted !";
            }

        } catch (DAOException e) {
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
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
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for payrollCalendarSaveUpdate is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return msg;
    }

    @Override
    public String performoperationPromotion(String flag, HrPromotionDetailsTO to, HrPersonnelDetailsTO to1) throws ApplicationException {
        String msg = "";
        long begin = System.nanoTime();
        HrPersonnelDetailsDAO dao = new HrPersonnelDetailsDAO(em);
        HrPromotionDetailsDAO daoPromotion = new HrPromotionDetailsDAO(em);
        HrPersonnelDetails hrobj = new HrPersonnelDetails();
        try {
            if (flag.equals("ADD")) {
                HrPromotionDetails entity = ObjectAdaptorPersonnel.addaptEntitypkHrPromotionDetails(to);
                daoPromotion.save(entity);

                HrPersonnelDetails entity1 = (HrPersonnelDetails) dao.findById(hrobj, com.hrms.adaptor.ObjectAdaptorHr.adaptToHrPeronnelDetailPKEntity(to1.getHrPersonnelDetailsPKTO()));
                entity1.setLocatCode(to1.getLocatCode());
                entity1.setDeptCode(to1.getDeptCode());
                entity1.setZones(to1.getZones());
                entity1.setBlock(to1.getBlock());
                entity1.setDesgCode(to1.getDesgCode());
                entity1.setRepTo(to1.getRepTo());
                dao.update(entity1);
                msg = "Data has been succesfully Saved !";
            }
            if (flag.equals("EDIT")) {

                HrPromotionDetails entity = ObjectAdaptorPersonnel.addaptEntitypkHrPromotionDetails(to);
                daoPromotion.update(entity);
                HrPersonnelDetails entity1 = (HrPersonnelDetails) dao.findById(hrobj, com.hrms.adaptor.ObjectAdaptorHr.adaptToHrPeronnelDetailPKEntity(to1.getHrPersonnelDetailsPKTO()));
                entity1.setLocatCode(to1.getLocatCode());
                entity1.setDeptCode(to1.getDeptCode());
                entity1.setZones(to1.getZones());
                entity1.setBlock(to1.getBlock());
                entity1.setDesgCode(to1.getDesgCode());
                entity1.setRepTo(to1.getRepTo());
                dao.update(entity1);
                msg = "Data has been succesfully Updated !";
            }
            if (flag.equals("DELETE")) {
                HrPromotionDetails entity = ObjectAdaptorPersonnel.addaptEntitypkHrPromotionDetails(to);
                daoPromotion.delete(entity, entity.getHrPromotionDetailsPK());
                msg = "Data has been succesfully Deleted !";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
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
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for payrollCalendarSaveUpdate is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return msg;
    }

    @Override
    public List viewDataPromotionFacade(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        List result = new ArrayList();
        try {
            HrPromotionDetailsDAO daoPromotion = new HrPromotionDetailsDAO(em);
            result = daoPromotion.viewDataPromotion(compCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
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
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for payrollCalendarSaveUpdate is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return result;
    }
    //generateArnCode

    @Override
    public String generateArnCodeFacade(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        String arno = "";
        try {
            HrPromotionDetailsDAO dao = new HrPromotionDetailsDAO(em);
            arno = dao.generateArnCode(compCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
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
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for payrollCalendarSaveUpdate is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return arno;
    }

    @Override
    public List<HrPersonnelDetailsTO> addviewData(String flag, int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        String msg = "";
        HrSeparationDetailsDAO hrSeparationDetailsDAO = new HrSeparationDetailsDAO(em);
        HrPersonnelDetailsDAO hrPersonnelDetailsDAO = new HrPersonnelDetailsDAO(em);
        List<HrPersonnelDetails> data = null;
        List<HrPersonnelDetailsTO> hrPersonnelDetailsTOs = new ArrayList<HrPersonnelDetailsTO>();
        try {
            if (flag.equals("ADD")) {
                List<HrSeparationDetails> empCodeList = hrSeparationDetailsDAO.checkempCode(compCode);
                if (empCodeList.size() != 0) {
                    for (HrSeparationDetails hr : empCodeList) {
                        long empCode = hr.getHrSeparationDetailsPK().getEmpCode();
                        data = hrPersonnelDetailsDAO.findbyExitEmpSearch(compCode, empCode, 'Y');
                        for (HrPersonnelDetails hrpr : data) {
                            hrPersonnelDetailsTOs.add(com.hrms.adaptor.ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrpr));
                        }
                    }
                } else {
                    msg = "Data Not found";
                }
            } else {
                List<HrSeparationDetails> empCodeList = hrSeparationDetailsDAO.checkempCodeView(compCode);
                if (empCodeList.size() != 0) {
                    for (HrSeparationDetails hr : empCodeList) {
                        long empCode = hr.getHrSeparationDetailsPK().getEmpCode();
                        data = hrPersonnelDetailsDAO.findbyExitEmpSearch(compCode, empCode, 'N');
                        for (HrPersonnelDetails hrpr : data) {
                            hrPersonnelDetailsTOs.add(com.hrms.adaptor.ObjectAdaptorHr.adaptTOHrPersonnelDetailsTO(hrpr));
                        }
                    }
                } else {
                    msg = "Data Not found";
                }
            }

        } catch (Exception e) {
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for payrollCalendarSaveUpdate is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return hrPersonnelDetailsTOs;
    }

    @Override
    public String performOperationExitInterview(String flag, HrPersonnelDetailsTO hrPersonnelDetailsTO, HrExitInterviewDtTO exitInterviewDtTO, HrExitInterviewHdTO exitInterviewHdTO) throws ApplicationException {
        long begin = System.nanoTime();
        String msg = "";
        HrExitInterviewDtDAO hrExitInterviewDtDAO = new HrExitInterviewDtDAO(em);
        HrExitInterviewHdDAO hrExitInterviewHdDAO = new HrExitInterviewHdDAO(em);
        HrPersonnelDetailsDAO hrPersonnelDetailsDAO = new HrPersonnelDetailsDAO(em);
        try {
            HrExitInterviewDt hrExitInterviewDt = ObjectAdaptorPersonnel.addaptEntityHrExitInterviewDt(exitInterviewDtTO);
            HrExitInterviewHd hrExitInterviewHd = ObjectAdaptorPersonnel.addaptEntityHrExitInterviewHd(exitInterviewHdTO);
            HrPersonnelDetails hrPersonnelDetails = com.hrms.adaptor.ObjectAdaptorHr.adaptTOHrPersonnelDetailsEntity(hrPersonnelDetailsTO);
            if (flag.equalsIgnoreCase("ADD")) {
                hrExitInterviewHdDAO.save(hrExitInterviewHd);
                hrExitInterviewDtDAO.save(hrExitInterviewDt);
                HrPersonnelDetails entity = (HrPersonnelDetails) hrPersonnelDetailsDAO.findById(hrPersonnelDetails, hrPersonnelDetails.getHrPersonnelDetailsPK());
                entity.setEmpStatus('N');
                hrPersonnelDetailsDAO.update(entity);
                msg = "Data has been succesfully Saved !";
            }
            if (flag.equalsIgnoreCase("EDIT")) {
                hrExitInterviewHdDAO.update(hrExitInterviewHd);
                hrExitInterviewDtDAO.update(hrExitInterviewDt);
                msg = "Data has been succesfully Updated !";
            }
            if (flag.equalsIgnoreCase("DELETE")) {
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
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
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for payrollCalendarSaveUpdate is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return msg;
    }

    @Override
    public List viewDataExitInterviewFacade(int compCode, long empCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrExitInterviewDtDAO hrExitInterviewDtDAO = new HrExitInterviewDtDAO(em);
        List result = null;
        try {
            result = hrExitInterviewDtDAO.viewDataExitInterview(compCode, empCode);
        } catch (Exception e) {
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for payrollCalendarSaveUpdate is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return result;
    }
    
    @Override
    public List viewEmployeeForTaxSearch() throws ApplicationException {
        long begin = System.nanoTime();
        HrSeparationDetailsDAO dao = new HrSeparationDetailsDAO(em);
        List result = new ArrayList();
        try {
            result = dao.viewEmployeeForTaxSearch();
        } catch (DAOException e) {
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
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
            logger.error("Exception occured while executing method payrollCalendarSaveUpdate()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for payrollCalendarSaveUpdate is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return result;
    }
}
