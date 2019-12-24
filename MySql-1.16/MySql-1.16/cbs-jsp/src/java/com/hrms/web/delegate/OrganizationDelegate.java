/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.web.delegate;

import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrMstDesgTO;
import com.hrms.common.to.HrOrgChartTO;
import com.hrms.utils.HrServiceLocator;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.facade.hrd.OrganizationFacadeRemote;
import com.hrms.web.exception.WebException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zeeshan Waris
 */
public class OrganizationDelegate {
/**
 *
 */
    private final String jndiHomeName = "OrganizationFacade";
    private OrganizationFacadeRemote beanRemote = null;

    public OrganizationDelegate() throws ServiceLocatorException {
        try{
            Object lookup = HrServiceLocator.getInstance().lookup(jndiHomeName);
            beanRemote = (OrganizationFacadeRemote) lookup;
        }catch (Exception e) {
            throw new ServiceLocatorException(e);            
        }    
    }
/**
 *
 * @param compCode
 * @return
 * @throws WebException
 */
    public List reportingStructureEditList(int compCode) throws WebException {
        List searchList = new ArrayList();
        try {
            searchList = beanRemote.reportingStructureList(compCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return searchList;
    }
/**
 *
 * @param orgObj
 * @return
 * @throws WebException
 */
    public String reportingDetailsSave(HrOrgChartTO orgObj) throws WebException {
        String result = null;
        try {
            result = beanRemote.reportingDetailsSave(orgObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }
/**
 *
 * @param orgObj
 * @return
 * @throws WebException
 */
    public String reportingDetailsUpdate(HrOrgChartTO orgObj) throws WebException {
        String result = null;
        try {
            result = beanRemote.reportingDetailsUpdate(orgObj);
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
    public String reportingDetailsDelete(int compCode, String contCode) throws WebException {
        String result = null;
        try {
            result = beanRemote.reportingDetailsDelete(compCode, contCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }
/**
 *
 * @param compcode
 * @param dept
 * @param grade
 * @return
 * @throws WebException
 */
    public List viewOrgnList(int compcode, String dept, String grade) throws WebException {

        List searchList = new ArrayList();
        try {
            searchList = beanRemote.viewOrgList(compcode, dept, grade);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return searchList;
    }
/**
 *
 * @param compCode
 * @param contCode
 * @return
 * @throws WebException
 */
    public List prepareOrgnEditList(int compCode, String contCode) throws WebException {
        List searchList = new ArrayList();
        try {
            searchList = beanRemote.prepareOrgDetail(compCode, contCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return searchList;
    }
/**
 *
 * @param compCode
 * @return
 * @throws WebException
 */
    public List prepareOrgnSaveList(int compCode) throws WebException {
        List searchList = new ArrayList();
        try {
            searchList = beanRemote.prepareOrgSaveDetail(compCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return searchList;
    }
/**
 *
 * @param tempObj
 * @return
 * @throws WebException
 */
      public String  prepareOrgnSave(HrMstDesgTO tempObj) throws WebException {
        String result = null;
        try {
            result = beanRemote.prepareDetailsSave(tempObj);
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
     public String  prepareOrgnUpdate(HrMstDesgTO tempObj) throws WebException {
        String result = null;
        try {
            result = beanRemote.prepareDetailsUpdate(tempObj);
            
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }
/**
 *
 * @param compCode
 * @param gradeCode
 * @return
 * @throws WebException
 */
    public List prepareUpdateDesgCodeList(int compCode,String gradeCode) throws WebException {
        List searchList = new ArrayList();
        try {
          searchList = beanRemote.prepareUpdateCheckCode(compCode,gradeCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return searchList;
    }

}
