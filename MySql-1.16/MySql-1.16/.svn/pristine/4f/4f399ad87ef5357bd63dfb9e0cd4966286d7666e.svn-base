/*
 * CREATED BY    :   ROHIT KRISHNA GUPTA
 * CREATION DATE :   21 JUNE 2011
 */

package com.hrms.facade.hrd;

import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrTrainingPlanPKTO;
import com.hrms.common.to.HrTrainingPlanTO;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author ROHIT KRISHNA
 */
@Remote
public interface TrainingPlanRemote {
    public List gridLoadOnAddBtn(int compCode, int searchFlag, String searchCriteria) throws ApplicationException;
    public List fillValuesOfEmpSearch(int compCode,String empId) throws ApplicationException;
    public List trainingSearchGridBtn(int compCode) throws ApplicationException;
    public List programSearchGridBtn(int compCode,String progCode) throws ApplicationException;
    public String saveTrainingPlanRecord(HrTrainingPlanTO trngPlanObj,String mode) throws ApplicationException;
    public List trainingPlanGridDetail(int compCode, String trngExec) throws ApplicationException;
    public String deleteRecord(HrTrainingPlanPKTO trngPlanObj) throws ApplicationException;
    //public String updateTrainingPlanRecord(List<TrainingPlanData> trngObj, int compCode, int empCode, String trngCode, String progCode, String fromDt, String toDt);
}
