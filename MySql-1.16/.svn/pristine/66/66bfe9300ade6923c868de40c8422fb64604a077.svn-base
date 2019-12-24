/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.facade.personnel;

import javax.ejb.Remote;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrContractorDetailsTO;
import com.hrms.common.to.HrTempStaffTO;
import com.hrms.common.to.HrTransferDetailsTO;
import java.util.List;

/**
 *
 * @author Zeeshan Waris
 */
@Remote
public interface TransferFacadeRemote {

    public List transferEmployeeDetail(String empId, int compCode) throws ApplicationException;

    public List transferArNo(int compCode, String arNo) throws ApplicationException;

    public List transferEditDetail(int compCode, String arNo) throws ApplicationException;

    public String transferSave(HrTransferDetailsTO hrParamObj) throws ApplicationException;

    public String transferUpdate(HrTransferDetailsTO hrParamObj) throws ApplicationException;

    public String transferdelete(int compCode, String arNo) throws ApplicationException;

    public List temporaryContractorName(int compCode) throws ApplicationException;

    public List<HrTempStaffTO> getTemporaryStaffData(int compCode) throws ApplicationException;

    public String temporaryStaffSave(HrTempStaffTO hrTempObj) throws ApplicationException;

    public String temporaryStaffUpdate(HrTempStaffTO hrTempObj) throws ApplicationException;

    public String temporaryStaffDelete(int compCode, String arNo) throws ApplicationException;

    public String contractorDetailsGetContCode(int compCode) throws ApplicationException;

    public List<HrContractorDetailsTO> getContractorEditData(int compCode) throws ApplicationException;

    public String contractorDetailsSave(HrContractorDetailsTO hrTempObj) throws ApplicationException;

    public String contractorDetailsUpdate(HrContractorDetailsTO hrTempObj) throws ApplicationException;

    public String contractorDetailsDelete(int compCode, String contcode) throws ApplicationException;
}
