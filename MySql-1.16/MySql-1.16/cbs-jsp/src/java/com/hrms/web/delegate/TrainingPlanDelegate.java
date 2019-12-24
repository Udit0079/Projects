/*
 * CREATED BY     :   ROHIT KRISHNA GUPTA
 * CREATIN DATE   :   02 JULY 2011
 */

package com.hrms.web.delegate;

import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrTrainingPlanPKTO;
import com.hrms.common.to.HrTrainingPlanTO;
import com.hrms.utils.HrServiceLocator;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.facade.hrd.TrainingPlanRemote;
import com.hrms.web.exception.WebException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ROHIT KRISHNA
 */
public class TrainingPlanDelegate {

    private final String jndiHomeName = "TrainingPlanBean";
    private TrainingPlanRemote beanRemote = null;

    public TrainingPlanDelegate() throws ServiceLocatorException {
        try{
            Object lookup = HrServiceLocator.getInstance().lookup(jndiHomeName);
            beanRemote = (TrainingPlanRemote) lookup;
        }catch (Exception e) {
            throw new ServiceLocatorException(e);            
        }
    }

    public List addBtnGridLoad(int compCode, int searchFlag, String searchCriteria) throws WebException {
        List resultLt = new ArrayList();
        try {
            resultLt = beanRemote.gridLoadOnAddBtn(compCode,searchFlag,searchCriteria);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return resultLt;
    }

    public List empSearchBtnGridLoad(int compCode,String empId) throws WebException {
        List resultLt = new ArrayList();
        try {
            resultLt = beanRemote.fillValuesOfEmpSearch(compCode,empId);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return resultLt;
    }

    public List trainingSearchBtnGridLoad(int compCode) throws WebException {
        List resultLt = new ArrayList();
        try {
            resultLt = beanRemote.trainingSearchGridBtn(compCode);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return resultLt;
    }

    public List programSearchBtnGridLoad(int compCode,String progCode) throws WebException {
        List resultLt = new ArrayList();
        try {
            resultLt = beanRemote.programSearchGridBtn(compCode,progCode);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return resultLt;
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

    public String saveTrainingPlanDetail(HrTrainingPlanTO trngPlanObj,String mode) throws WebException, ApplicationException {
        String result = "";
        try {
            result = beanRemote.saveTrainingPlanRecord(trngPlanObj,mode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public String deleteTrainingPlanDetail(HrTrainingPlanPKTO trngPlanObj) throws WebException, ApplicationException {
        String result = "";
        try {
            result = beanRemote.deleteRecord(trngPlanObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

}
