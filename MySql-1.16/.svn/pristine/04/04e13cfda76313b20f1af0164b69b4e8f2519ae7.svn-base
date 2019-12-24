/*
 * CREATED BY   :  ROHIT KRISHNA GUPTA
 * CREATION DATE:  13 JUNE 2011
 */
package com.hrms.facade.hrd;

import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrMstInstitutePKTO;
import com.hrms.common.to.HrMstInstituteTO;
import com.hrms.entity.hr.HrMstInstitute;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author ROHIT KRISHNA
 */
@Remote
public interface InstituteDetailsRemote {

    public List<HrMstInstituteTO> instDetailGridLoad(int compCode) throws ApplicationException;

    public String getMaxInstCode(int compCode);

    public String saveRecord(HrMstInstituteTO instObj) throws ApplicationException;

    public String updateInstituteRecord(HrMstInstituteTO entity) throws ApplicationException;

    //public String deleteRecordOfInstitues(int compCode, String instCode) throws ApplicationException;
    public String deleteInstituteDetail(HrMstInstitutePKTO instObj) throws ApplicationException;
}
