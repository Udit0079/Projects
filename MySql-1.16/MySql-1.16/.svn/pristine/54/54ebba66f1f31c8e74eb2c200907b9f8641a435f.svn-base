/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.facade.hrd;

import javax.ejb.Remote;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrDataPrevCompTO;
import com.hrms.common.to.HrDataQualTO;
import com.hrms.common.to.HrDataReferenceTO;
import com.hrms.common.to.HrDatabankTO;
import java.util.List;

/**
 *
 * @author Zeeshan Waris
 */
@Remote
public interface DatabankFacadeRemote {

    public List creationOfDatabnakSearchDetail(int compCode, String advtCode) throws ApplicationException;

    public List<HrDatabankTO> creationOfDatabankViewDetails(int compCode, String canId) throws ApplicationException;

    public List creationOfDatabnakCustomerNameList(int compCode) throws ApplicationException;

    public List creationOfDatabnakReferencedetails(int compCode, String candSRno, String advtCode, String jobCode) throws ApplicationException;

    public List creationOfDatabnakQualificationDetails(int compCode, String candSRno, String advtCode, String jobCode) throws ApplicationException;

    public List creationOfDatabnakPreEmpDetails(int compCode, String candSRno, String advtCode, String jobCode) throws ApplicationException;

    public List creationOfDatabnakSaveValidAction() throws ApplicationException;

    public String saveCreationOfDatabankActionPlan(int compCode, String advertisementNo, String jobSpecification, String candidateId, String examinationQualification, String companyNameExperience, String referanceNameReferanceDetails, HrDatabankTO hrDatabankObj, HrDataQualTO hrDataQualObj, HrDataPrevCompTO hrDatabankPrevObj, HrDataReferenceTO hrDataRefObj) throws ApplicationException;

    public String updateCreationOfDatabank(HrDatabankTO dataBankObj) throws ApplicationException;

    public List<HrDatabankTO> addListEvaluationOfDatabank(int compCode, String organisation, String designation) throws ApplicationException;

    public List<HrDatabankTO> editEvaluationOfDatabank(int compCode, String organisation) throws ApplicationException;

    public String saveEvaluationOfDatabank(HrDatabankTO hrDatabankTO) throws ApplicationException;

    public String updateEvaluationOfDatabank(HrDatabankTO hrDatabankTO) throws ApplicationException;
}
