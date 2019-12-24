/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.web.delegate;

import com.hrms.facade.hrd.DatabankFacadeRemote;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrDataPrevCompTO;
import com.hrms.common.to.HrDataQualTO;
import com.hrms.common.to.HrDataReferenceTO;
import com.hrms.common.to.HrDatabankTO;
import com.hrms.utils.HrServiceLocator;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.web.exception.WebException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Zeeshan Waris
 */
public class DatabankDelegate {
    private final String jndiHomeName = "DatabankFacade";
    private DatabankFacadeRemote beanRemote = null;
/**
 * 
 * @throws ServiceLocatorException
 */
    public DatabankDelegate() throws ServiceLocatorException {
        try{
            Object lookup = HrServiceLocator.getInstance().lookup(jndiHomeName);
            beanRemote = (DatabankFacadeRemote) lookup;
        }catch (Exception e) {
            throw new ServiceLocatorException(e);            
       }
    }
/**
 *
 * @param compCode
 * @param advtCode
 * @return
 * @throws WebException
 */
  public List creationOfDatabankSearchList(int compCode,String advtCode) throws WebException {
        List searchList = new ArrayList();
        try {
            searchList = beanRemote.creationOfDatabnakSearchDetail(compCode,advtCode);
       } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
       }
        return searchList;
    }
/**
 *
 * @param compCode
 * @param canId
 * @return
 * @throws WebException
 */
  public List<HrDatabankTO> creationOfDatabankViewDetails(int compCode,String canId) throws WebException {
        List<HrDatabankTO> hrDatabankTOs = new ArrayList<HrDatabankTO>();
        try {
            hrDatabankTOs = beanRemote.creationOfDatabankViewDetails(compCode,canId);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return hrDatabankTOs;
    }
/**
 *
 * @param compCode
 * @return
 * @throws WebException
 */
   public List creationOfDatabankCustomerNameList(int compCode) throws WebException {
        List searchList = new ArrayList();
        try {
            searchList = beanRemote.creationOfDatabnakCustomerNameList(compCode);
       } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
       }
        return searchList;
    }
/**
 *
 * @param compCode
 * @param candSRno
 * @param advtCode
 * @param jobCode
 * @return
 * @throws WebException
 */
    public List creationOfDatabankReferanceDetails(int compCode, String candSRno,String advtCode,String jobCode) throws WebException {
        List searchList = new ArrayList();
        try {
            searchList = beanRemote.creationOfDatabnakReferencedetails(compCode,candSRno,advtCode,jobCode);
       } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
       }
        return searchList;
    }
/**
 *
 * @param compCode
 * @param candSRno
 * @param advtCode
 * @param jobCode
 * @return
 * @throws WebException
 */
     public List creationOfDatabankQualificationDetails(int compCode, String candSRno,String advtCode,String jobCode) throws WebException {
        List searchList = new ArrayList();
        try {
            searchList = beanRemote.creationOfDatabnakQualificationDetails(compCode,candSRno,advtCode,jobCode);
       } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
       }
        return searchList;
    }

   /**
    *
    * @param compCode
    * @param candSRno
    * @param advtCode
    * @param jobCode
    * @return
    * @throws WebException
    */
   public List creationOfDatabankPreEmpDetails(int compCode, String candSRno,String advtCode,String jobCode) throws WebException {
        List searchList = new ArrayList();
        try {
            searchList = beanRemote.creationOfDatabnakPreEmpDetails(compCode,candSRno,advtCode,jobCode);
       } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
       }
        return searchList;
    }
/**
 *
 * @return
 * @throws WebException
 */
    public List creationOfDatabankValidCheck() throws WebException {
        List detailList = new ArrayList();
        try {
            detailList = beanRemote.creationOfDatabnakSaveValidAction();
       } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
       }
        return detailList;
       }
   /**
    *
    * @param compCode
    * @param advertisementNo
    * @param jobSpecification
    * @param candidateId
    * @param examinationQualification
    * @param companyNameExperience
    * @param referanceNameReferanceDetails
    * @param hrDatabank
    * @param hrDataQual
    * @param hrDatabankPrev
    * @param hrDataRef
    * @return
    * @throws WebException
    */

     public String saveCreationOfDatabank(int compCode,String advertisementNo,String jobSpecification,String candidateId,String examinationQualification,String companyNameExperience,String referanceNameReferanceDetails,HrDatabankTO hrDatabank,HrDataQualTO hrDataQual,HrDataPrevCompTO hrDatabankPrev,HrDataReferenceTO hrDataRef) throws WebException {
        String result = null;
        try {
            result = beanRemote.saveCreationOfDatabankActionPlan(compCode,advertisementNo,jobSpecification,candidateId,examinationQualification,companyNameExperience,referanceNameReferanceDetails,hrDatabank,hrDataQual,hrDatabankPrev,hrDataRef);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
        }
/**
 * 
 * @param databankObj
 * @return
 * @throws WebException
 */
     public String updateCreationOfDatabank(HrDatabankTO databankObj) throws WebException {
        String result = null;
        try {
            result = beanRemote.updateCreationOfDatabank(databankObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }
  
   public String saveEvaluationOfDatabankDelegation(HrDatabankTO hrDatabankTO) throws WebException {
        String result = null;
        try {
            result = beanRemote.saveEvaluationOfDatabank(hrDatabankTO);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

      public String updateEvaluationOfDatabankDelegation(HrDatabankTO hrDatabankTO) throws WebException {
        String result = null;
        try {
            result = beanRemote.updateEvaluationOfDatabank(hrDatabankTO);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }
   public List<HrDatabankTO> editEvaluationOfDatabankDelegation(int compCode, String organisation) throws WebException {
        List<HrDatabankTO> hrdatabankTOs = new ArrayList<HrDatabankTO>();
        try {
            hrdatabankTOs = beanRemote.editEvaluationOfDatabank(compCode,organisation);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return hrdatabankTOs;
    }

    public List<HrDatabankTO> addListEvaluationOfDatabankDelegation(int compCode, String organisation,String designation) throws WebException {
        List<HrDatabankTO> hrdatabankTOs = new ArrayList<HrDatabankTO>();
        try {
            hrdatabankTOs = beanRemote.addListEvaluationOfDatabank(compCode,organisation,designation);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return hrdatabankTOs;
    }
}
