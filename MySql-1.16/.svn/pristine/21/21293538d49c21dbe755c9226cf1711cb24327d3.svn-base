/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrms.web.delegate;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.ParametersTO;
import com.hrms.utils.HrServiceLocator;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.facade.ParametersFacadeRemote;
import com.hrms.web.exception.WebException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zeeshan Waris
 */
public class ParametersDelegate {
    private final String jndiHomeName = "ParametersFacade";
    private ParametersFacadeRemote beanRemote = null;

/**
 *
 * @throws ServiceLocatorException
 */
      public ParametersDelegate() throws ServiceLocatorException {
          try{
            Object lookup = HrServiceLocator.getInstance().lookup(jndiHomeName);
            beanRemote = (ParametersFacadeRemote) lookup;
          }catch (Exception e) {
            throw new ServiceLocatorException(e);            
       }  
    }
     /**
      *
      * @return
      * @throws WebException
      */

     public List<ParametersTO> getParameterData() throws WebException {
        List<ParametersTO> ParametersTos = new ArrayList<ParametersTO>();
        try {
            ParametersTos = beanRemote.getParameterList();
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return ParametersTos;
    }
     /**
      *
      * @param compCode
      * @param code
      * @return
      * @throws WebException
      */
       public List<HrMstStructTO> parameterDesList(int compCode,String code) throws WebException {
        List<HrMstStructTO> structMasterTOs = new ArrayList<HrMstStructTO>();
        try {
            structMasterTOs = beanRemote.parametertDescriptionList(compCode,code);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return structMasterTOs;
    }
       /**
        *
        * @param paramObj
        * @return
        * @throws WebException
        */
    public String parameterSave(HrMstStructTO paramObj) throws WebException {
        String result = null;
        try {
            result = beanRemote.parameterSave(paramObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }
/**
 *
 * @param paramObj
 * @return
 * @throws WebException
 */
     public String parametersUpdate(HrMstStructTO paramObj) throws WebException {
        String result = null;
        try {
            result = beanRemote.parameterUpdate(paramObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }
/**
 * 
 * @param compCode
 * @param structCode
 * @return
 * @throws WebException
 */
     public String parameterDelete(int compCode, String structCode) throws WebException {
        String result = null;
        try {
            result = beanRemote.deleteParameterAction(compCode,structCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }
}
