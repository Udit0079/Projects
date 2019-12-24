/*
 * CREATED BY    :  ROHIT KRISHNA GUPTA
 * CREATION DATE :  20 JUNE 2011
 */
package com.hrms.facade.hrd;

import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrMstTrngProgramPKTO;
import com.hrms.common.to.HrMstTrngProgramTO;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author ROHIT KRISHNA
 */
@Remote
public interface TrainingProgramMasterRemote {

    public List trainingNameDd(int compCode)throws ApplicationException;

    public List progNameDd(int compCode)throws ApplicationException;

    public String saveTrainingRecord(HrMstTrngProgramTO trngProgObj) throws ApplicationException;

    public List trngMstGridLoad(int compCode,String trainingCode)throws ApplicationException;

    public String deleteTrngRecord(HrMstTrngProgramPKTO trngProgObj) throws ApplicationException;
}
