/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.facade.personnel;

import com.hrms.adaptor.ObjectAdaptorPersonnel;
import javax.ejb.Stateless;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.exception.ExceptionCode;
import com.hrms.common.to.HrContractorDetailsTO;
import com.hrms.common.to.HrTempStaffTO;
import com.hrms.common.to.HrTransferDetailsTO;
import com.hrms.common.utils.Validator;
import com.hrms.dao.HrContractorDetailsDAO;
import com.hrms.dao.HrPersonnelDetailsDAO;
import com.hrms.dao.HrTempStaffDAO;
import com.hrms.dao.HrTransferDetailsDAO;
import com.hrms.dao.exception.DAOException;
import com.hrms.entity.personnel.HrContractorDetails;
import com.hrms.entity.personnel.HrTempStaff;
import com.hrms.entity.personnel.HrTransferDetails;
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
@Stateless(mappedName = "TransferFacade")
@Remote({TransferFacadeRemote.class})
public class TransferFacade implements TransferFacadeRemote {

    private static final Logger logger = Logger.getLogger(TransferFacade.class);
    @PersistenceContext
    private EntityManager em;

    @Override
    public List transferEmployeeDetail(String empId, int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrPersonnelDetailsDAO hrPersonnelDetailsDAO = new HrPersonnelDetailsDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrPersonnelDetailsDAO.transferEmpDetail(empId, compCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method transferEmployeeDetail()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method transferEmployeeDetail()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for transferEmployeeDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }

    @Override
    public List transferArNo(int compCode, String arNo) throws ApplicationException {
        long begin = System.nanoTime();
        HrTransferDetailsDAO hrTransferDetailsDAO = new HrTransferDetailsDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrTransferDetailsDAO.transferGetArNo(compCode, arNo);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method transferArNo()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method transferArNo()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for transferArNo is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }

    @Override
    public List transferEditDetail(int compCode, String arNo) throws ApplicationException {
        long begin = System.nanoTime();
        HrTransferDetailsDAO hrTransferDetailsDAO = new HrTransferDetailsDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrTransferDetailsDAO.transferEditData(compCode, arNo);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method transferEditDetail()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method transferEditDetail()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for transferEditDetail is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }

    @Override
    public String transferSave(HrTransferDetailsTO hrTransferObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrTransferObj);
        HrTransferDetails hrTransfer = null;
        try {
            HrTransferDetailsDAO hrTransferDetailsDAO = new HrTransferDetailsDAO(em);
            hrTransfer = ObjectAdaptorPersonnel.adaptToHrTransferDetailsEntity(hrTransferObj);
            List checkData = hrTransferDetailsDAO.transferPrimrycheck(hrTransferObj.getHrTransferDetailsPKTO().getCompCode(), hrTransferObj.getHrTransferDetailsPKTO().getArNo());
            if (checkData.size() == 0) {
                hrTransferDetailsDAO.save(hrTransfer);
            } else {
                return "Duplicate data exist please fill another A/R No";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method parameterSave()", e);
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
            logger.error("Exception occured while executing method parameterSave()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for parameterSave is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully saved.";
    }

    @Override
    public String transferUpdate(HrTransferDetailsTO hrTransferObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrTransferObj);
        HrTransferDetails hrTransfer = null;
        try {
            HrTransferDetailsDAO hrTransferDetailsDAO = new HrTransferDetailsDAO(em);
            hrTransfer = ObjectAdaptorPersonnel.adaptToHrTransferDetailsEntity(hrTransferObj);
            hrTransferDetailsDAO.update(hrTransfer);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method parameterUpdate()", e);
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
            logger.error("Exception occured while executing method parameterUpdate()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for parameterUpdate is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully updated.";
    }

    @Override
    public String transferdelete(int compCode, String arNo) throws ApplicationException {
        long begin = System.nanoTime();
        HrTransferDetails hrTransferDetails = new HrTransferDetails();
        try {
            HrTransferDetailsDAO hrTransferDetailsDAO = new HrTransferDetailsDAO(em);
            hrTransferDetails = hrTransferDetailsDAO.deleteTransfer(compCode, arNo);
            hrTransferDetailsDAO.delete(hrTransferDetails);
        } catch (Exception e) {
            logger.error("Exception occured while executing method deleteParameterAction()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for deleteParameterAction is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "true";
    }

    /**************** Temporary Staff ***********************/
    @Override
    public List temporaryContractorName(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrContractorDetailsDAO hrContractorDetailsDAO = new HrContractorDetailsDAO(em);
        List detailList = new ArrayList();
        try {
            detailList = hrContractorDetailsDAO.temporaryContractorName(compCode);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method temporaryContractorName()", e);
            if (e.getExceptionCode().getExceptionMessage().equals("NoResultException")) {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.NO_RESULT_FOUND, "No result found."), e);
            } else {
                throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                        "System exception has occured"), e);
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method temporaryContractorName()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for temporaryContractorName is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return detailList;
    }

    @Override
    public List<HrTempStaffTO> getTemporaryStaffData(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrTempStaffDAO hrTempStaffDAO = new HrTempStaffDAO(em);
        List<HrTempStaffTO> tempStaffTOs = new ArrayList<HrTempStaffTO>();
        try {
            List<HrTempStaff> structMasterList = hrTempStaffDAO.findByCompCode(compCode);
            for (HrTempStaff hrMstStruct : structMasterList) {
                tempStaffTOs.add(ObjectAdaptorPersonnel.adaptToHrTempStaffTO(hrMstStruct));
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
        return tempStaffTOs;
    }

    @Override
    public String temporaryStaffSave(HrTempStaffTO hrTempObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrTempObj);
        HrTempStaff hrTemp = null;
        try {
            HrTempStaffDAO hrTempStaffDAO = new HrTempStaffDAO(em);
            hrTemp = ObjectAdaptorPersonnel.adaptToHrTempStaffEntity(hrTempObj);
            List checkData = hrTempStaffDAO.temporaryStaffPrimrycheck(hrTempObj.getHrTempStaffPKTO().getCompCode(), hrTempObj.getHrTempStaffPKTO().getArNo());
            if (checkData.size() == 0) {
                hrTempStaffDAO.save(hrTemp);
            } else {
                return "Duplicate data exist please fill another A/R No";
            }
        } catch (DAOException e) {
            logger.error("Exception occured while executing method temporaryStaffSave()", e);
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
            logger.error("Exception occured while executing method temporaryStaffSave()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for temporaryStaffSave is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully saved.";
    }

    @Override
    public String temporaryStaffUpdate(HrTempStaffTO hrTempObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrTempObj);
        HrTempStaff hrTemp = null;
        try {
            HrTempStaffDAO hrTempStaffDAO = new HrTempStaffDAO(em);
            hrTemp = ObjectAdaptorPersonnel.adaptToHrTempStaffEntity(hrTempObj);
            hrTempStaffDAO.update(hrTemp);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method temporaryStaffUpdate()", e);
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
            logger.error("Exception occured while executing method temporaryStaffUpdate()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for temporaryStaffUpdate is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully updated.";
    }

    @Override
    public String temporaryStaffDelete(int compCode, String arNo) throws ApplicationException {
        long begin = System.nanoTime();
        HrTempStaff hrTempStaff = new HrTempStaff();
        try {
            HrTempStaffDAO hrTempStaffDAO = new HrTempStaffDAO(em);
            hrTempStaff = hrTempStaffDAO.deleteTemporaryStaff(compCode, arNo);
            hrTempStaffDAO.delete(hrTempStaff);
        } catch (Exception e) {
            logger.error("Exception occured while executing method temporaryStaffDelete()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for temporaryStaffDelete is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "true";
    }

    /******************* Contractor Details ********************/
    @Override
    public String contractorDetailsGetContCode(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrContractorDetailsDAO hrContractorDetailsDAO = new HrContractorDetailsDAO(em);
        String contractorCode;
        try {
            contractorCode = hrContractorDetailsDAO.contractorDetailsContcode(compCode);
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
        return contractorCode;
    }

    @Override
    public List<HrContractorDetailsTO> getContractorEditData(int compCode) throws ApplicationException {
        long begin = System.nanoTime();
        HrContractorDetailsDAO hrContractorDetailsDAO = new HrContractorDetailsDAO(em);
        List<HrContractorDetailsTO> contractorTOs = new ArrayList<HrContractorDetailsTO>();
        try {
            List<HrContractorDetails> contractorList = hrContractorDetailsDAO.findByCompCodeContractorDetail(compCode);
            for (HrContractorDetails contractor : contractorList) {
                contractorTOs.add(ObjectAdaptorPersonnel.adaptToHrContractorDetailsTO(contractor));
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
        return contractorTOs;
    }

    @Override
    public String contractorDetailsSave(HrContractorDetailsTO hrTempObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrTempObj);
        HrContractorDetails hrTemp = null;
        try {
            HrContractorDetailsDAO hrContractorDetailsDAO = new HrContractorDetailsDAO(em);
            hrTemp = ObjectAdaptorPersonnel.adaptToHrContractorDetailsEntity(hrTempObj);
            hrContractorDetailsDAO.save(hrTemp);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method contractorDetailsSave()", e);
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
            logger.error("Exception occured while executing method contractorDetailsSave()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for contractorDetailsSave is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully saved.";
    }

    @Override
    public String contractorDetailsUpdate(HrContractorDetailsTO hrTempObj) throws ApplicationException {
        long begin = System.nanoTime();
        Validator.isNull(hrTempObj);
        HrContractorDetails hrTemp = null;
        try {
            HrContractorDetailsDAO hrContractorDetailsDAO = new HrContractorDetailsDAO(em);
            hrTemp = ObjectAdaptorPersonnel.adaptToHrContractorDetailsEntity(hrTempObj);
            hrContractorDetailsDAO.update(hrTemp);
        } catch (DAOException e) {
            logger.error("Exception occured while executing method contractorDetailsUpdate()", e);
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
            logger.error("Exception occured while executing method contractorDetailsUpdate()", e);
            throw new ApplicationException(new ExceptionCode(ExceptionCode.SYSTEM_EXCEPTION_OCCURED,
                    "System exception has occured"));
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Execution time for contractorDetailsUpdate is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        }
        return "Data has been successfully updated.";
    }

    @Override
    public String contractorDetailsDelete(int compCode, String contcode) throws ApplicationException {
        long begin = System.nanoTime();
        HrContractorDetails hrTempStaff = new HrContractorDetails();
        try {
            HrContractorDetailsDAO hrContractorDetailsDAO = new HrContractorDetailsDAO(em);
            hrTempStaff = hrContractorDetailsDAO.deleteContractoerDetails(compCode, contcode);
            hrContractorDetailsDAO.delete(hrTempStaff);
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
}
