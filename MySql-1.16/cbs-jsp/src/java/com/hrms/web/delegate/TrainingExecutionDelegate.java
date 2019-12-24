/*
 * CREATED BY    :   ROHIT KRISHNA GUPTA
 * CREATION DATE :   05 JULY 2011
 */

package com.hrms.web.delegate;

import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrTrainingExecutionTO;
import com.hrms.common.to.HrTrainingPlanTO;
import com.hrms.utils.HrServiceLocator;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.facade.hrd.TrainingExecutionRemote;
import com.hrms.web.exception.WebException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ROHIT KRISHNA
 */
public class TrainingExecutionDelegate {
    
    private final String jndiHomeName = "TrainingExecutionBean";
    private TrainingExecutionRemote beanRemote = null;

    public TrainingExecutionDelegate() throws ServiceLocatorException {
        try{
            Object lookup = HrServiceLocator.getInstance().lookup(jndiHomeName);
            beanRemote = (TrainingExecutionRemote) lookup;
        }catch (Exception e) {
            throw new ServiceLocatorException(e);            
        }
    }

    public List trainingPlanGridLoad(int compCode, String trngExec) throws WebException {
        List resultLt = new ArrayList();
        try {
            resultLt = beanRemote.trainingPlanGridDetail(compCode,trngExec);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return resultLt;
    }

    public String maxTrngExecutionCode() throws WebException  {
        String code="";
        try {
            code = beanRemote.getMaxTrngExecCode();
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return code;
    }

    public String saveTrngExecDetail(HrTrainingExecutionTO hrTrngExec, HrTrainingPlanTO hrTrngPlan) throws WebException, ApplicationException {
        String result = "";
        try {
            result = beanRemote.saveTrainingExecutionDetail(hrTrngExec,hrTrngPlan);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

      public String updateTrngExecDetail(HrTrainingExecutionTO hrTrngExec) throws WebException, ApplicationException {
        String result = "";
        try {
            result = beanRemote.updateTrainingExecutionDetail(hrTrngExec);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }
    public String deleteTrngExecDetail(HrTrainingExecutionTO hrTrngExec, HrTrainingPlanTO hrTrngPlan) throws WebException, ApplicationException {
        String result = "";
        try {
            result = beanRemote.deleteTrainingExecutionDetail(hrTrngExec,hrTrngPlan);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

     public List trainingExcEditList(int compCode) throws WebException {
        List resultLt = new ArrayList();
        try {
            resultLt = beanRemote.trainingExucutionEditist(compCode);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return resultLt;
    }
}
