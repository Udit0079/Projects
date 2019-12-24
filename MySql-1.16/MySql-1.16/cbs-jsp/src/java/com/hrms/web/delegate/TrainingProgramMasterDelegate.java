/*
 * CREATED BY    :  ROHIT KRISHNA GUPTA
 * CREATION DATE :  02 JULY 2011
 */
package com.hrms.web.delegate;

import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrMstTrngProgramPKTO;
import com.hrms.common.to.HrMstTrngProgramTO;
import com.hrms.utils.HrServiceLocator;
import com.hrms.common.utils.ServiceLocatorException;

import com.hrms.facade.hrd.TrainingProgramMasterRemote;
import com.hrms.web.exception.WebException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ROHIT KRISHNA
 */
public class TrainingProgramMasterDelegate {

    private final String jndiHomeName = "TrainingProgramMasterBean";
    private TrainingProgramMasterRemote beanRemote = null;

    public TrainingProgramMasterDelegate() throws ServiceLocatorException {
        try{
            Object lookup = HrServiceLocator.getInstance().lookup(jndiHomeName);
            beanRemote = (TrainingProgramMasterRemote) lookup;
        }catch (Exception e) {
            throw new ServiceLocatorException(e);            
        }
    }

    public List trainingNameList(int compCode) throws WebException {
        List resultLt = new ArrayList();
        try {
            resultLt = beanRemote.trainingNameDd(compCode);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return resultLt;
    }

    public List progNameList(int compCode) throws WebException {
        List resultLt = new ArrayList();
        try {
            resultLt = beanRemote.progNameDd(compCode);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return resultLt;
    }

    public List trainingProgramGridDetail(int compCode, String trainingCode) throws WebException {
        List resultLt = new ArrayList();
        try {
            resultLt = beanRemote.trngMstGridLoad(compCode, trainingCode);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return resultLt;
    }

    public String saveTrainingDetail(HrMstTrngProgramTO trngProgObj) throws WebException, ApplicationException {
        String result = "";
        try {
            result = beanRemote.saveTrainingRecord(trngProgObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public String deleteTrainingDetail(HrMstTrngProgramPKTO trngProgObj) throws WebException, ApplicationException {
        String result = "";
        try {
            result = beanRemote.deleteTrngRecord(trngProgObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }
}
