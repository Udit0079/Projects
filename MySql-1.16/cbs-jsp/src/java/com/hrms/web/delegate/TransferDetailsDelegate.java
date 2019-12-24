/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.delegate;

import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrContractorDetailsTO;
import com.hrms.common.to.HrTempStaffTO;
import com.hrms.common.to.HrTransferDetailsTO;
import com.hrms.utils.HrServiceLocator;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.facade.personnel.TransferFacadeRemote;
import com.hrms.web.exception.WebException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zeeshan Waris
 */
public class TransferDetailsDelegate {

    private final String jndiHomeName = "TransferFacade";
    private TransferFacadeRemote beanRemote = null;

    /**
     *
     * @throws ServiceLocatorException
     */
    public TransferDetailsDelegate() throws ServiceLocatorException {
        try{
            Object lookup = HrServiceLocator.getInstance().lookup(jndiHomeName);
            beanRemote = (TransferFacadeRemote) lookup;
        }catch (Exception e) {
            throw new ServiceLocatorException(e);            
        }
    }

    /**
     *
     * @param empId
     * @param compCode
     * @return
     * @throws WebException
     */
    public List transferEmpDetails(String empId, int compCode) throws WebException {
        List detailList = new ArrayList();
        try {
            detailList = beanRemote.transferEmployeeDetail(empId, compCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return detailList;
    }

    /**
     *
     * @param compCode
     * @param arNo
     * @return
     * @throws WebException
     */
    public List transferArNum(int compCode, String arNo) throws WebException {
        List detailList = new ArrayList();
        try {
            detailList = beanRemote.transferArNo(compCode, arNo);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return detailList;
    }

    /**
     *
     * @param compCode
     * @param arNo
     * @return
     * @throws WebException
     */
    public List transferEditDetails(int compCode, String arNo) throws WebException {
        List detailList = new ArrayList();
        try {
            detailList = beanRemote.transferEditDetail(compCode, arNo);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return detailList;
    }

    /**
     *
     * @param trnObj
     * @return
     * @throws WebException
     */
    public String transferDetailsSave(HrTransferDetailsTO trnObj) throws WebException {
        String result = null;
        try {
            result = beanRemote.transferSave(trnObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    /**
     *
     * @param trnObj
     * @return
     * @throws WebException
     */
    public String transferDetailsUpdate(HrTransferDetailsTO trnObj) throws WebException {
        String result = null;
        try {
            result = beanRemote.transferUpdate(trnObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    /**
     *
     * @param compCode
     * @param arNo
     * @return
     * @throws WebException
     */
    public String transferDetailsDelete(int compCode, String arNo) throws WebException {
        String result = null;
        try {
            result = beanRemote.transferdelete(compCode, arNo);
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
    public List temporaryStaffContractorDetails(int compCode) throws WebException {
        List detailList = new ArrayList();
        try {
            detailList = beanRemote.temporaryContractorName(compCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return detailList;
    }

    /**
     *
     * @param compCode
     * @return
     * @throws WebException
     */
    public List<HrTempStaffTO> getEditList(int compCode) throws WebException {
        List<HrTempStaffTO> tempStaffTOs = new ArrayList<HrTempStaffTO>();
        try {
            tempStaffTOs = beanRemote.getTemporaryStaffData(compCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return tempStaffTOs;
    }

    /**
     *
     * @param tempObj
     * @return
     * @throws WebException
     */
    public String temporaryStaffSave(HrTempStaffTO tempObj) throws WebException {
        String result = null;
        try {
            result = beanRemote.temporaryStaffSave(tempObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    /**
     *
     * @param tempObj
     * @return
     * @throws WebException
     */
    public String temporaryStaffUpdate(HrTempStaffTO tempObj) throws WebException {
        String result = null;
        try {
            result = beanRemote.temporaryStaffUpdate(tempObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    /**
     *
     * @param compCode
     * @param arNum
     * @return
     * @throws WebException
     */
    public String temporaryStaffDelete(int compCode, String arNum) throws WebException {
        String result = null;
        try {
            result = beanRemote.temporaryStaffDelete(compCode, arNum);
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
    public String getcontractorCode(int compCode) throws WebException {
        String advtCode = "";
        try {
            advtCode = beanRemote.contractorDetailsGetContCode(compCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return advtCode;
    }

    /**
     *
     * @param compCode
     * @return
     * @throws WebException
     */
    public List<HrContractorDetailsTO> contractorEditList(int compCode) throws WebException {
        List<HrContractorDetailsTO> contractorTOs = new ArrayList<HrContractorDetailsTO>();
        try {
            contractorTOs = beanRemote.getContractorEditData(compCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return contractorTOs;
    }

    /**
     *
     * @param tempObj
     * @return
     * @throws WebException
     */
    public String contractorDetailsSave(HrContractorDetailsTO tempObj) throws WebException {
        String result = null;
        try {
            result = beanRemote.contractorDetailsSave(tempObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    /**
     *
     * @param tempObj
     * @return
     * @throws WebException
     */
    public String contractorDetailsUpdate(HrContractorDetailsTO tempObj) throws WebException {
        String result = null;
        try {
            result = beanRemote.contractorDetailsUpdate(tempObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    /**
     *
     * @param compCode
     * @param contCode
     * @return
     * @throws WebException
     */
    public String contractorDetailsDelete(int compCode, String contCode) throws WebException {
        String result = null;
        try {
            result = beanRemote.contractorDetailsDelete(compCode, contCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }
}
