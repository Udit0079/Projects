/*
 * CREATED BY    :   ROHIT KRISHNA GUPTA
 * CREATION DATE :   05 JULY 2011
 */

package com.hrms.facade.hrd;

import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrTrainingExecutionTO;
import com.hrms.common.to.HrTrainingPlanTO;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author ROHIT KRISHNA
 */
@Remote
public interface TrainingExecutionRemote {
    public List trainingPlanGridDetail(int compCode, String trngExec) throws ApplicationException;
    public String getMaxTrngExecCode() throws ApplicationException;
    public String saveTrainingExecutionDetail(HrTrainingExecutionTO hrTrngExec, HrTrainingPlanTO hrTrngPlan) throws ApplicationException;
    public List trainingExucutionEditist(int compCode) throws ApplicationException;
    public String updateTrainingExecutionDetail(HrTrainingExecutionTO hrTrngExec) throws ApplicationException ;
    public String deleteTrainingExecutionDetail(HrTrainingExecutionTO hrTrngExec, HrTrainingPlanTO hrTrngPlan) throws ApplicationException;
}
