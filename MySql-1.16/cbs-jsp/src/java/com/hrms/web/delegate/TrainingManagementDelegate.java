/*
 * CREATED BY    :   ROHIT KRISHNA GUPTA
 * CREATION DT   :   30 JUNE 2011
 */
package com.hrms.web.delegate;

import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrMstInstitutePKTO;
import com.hrms.common.to.HrMstInstituteTO;
import com.hrms.utils.HrServiceLocator;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.facade.hrd.InstituteDetailsRemote;
import com.hrms.web.exception.WebException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ROHIT KRISHNA
 */
public class TrainingManagementDelegate {

    private final String jndiHomeName = "InstituteDetailsBean";
    private InstituteDetailsRemote beanRemote = null;

    public TrainingManagementDelegate() throws ServiceLocatorException {
        try{
            Object lookup = HrServiceLocator.getInstance().lookup(jndiHomeName);
            beanRemote = (InstituteDetailsRemote) lookup;
        }catch (Exception e) {
            throw new ServiceLocatorException(e);            
        }
    }

    public List<HrMstInstituteTO> getInstituteDetail(int compCode) throws WebException {
        List<HrMstInstituteTO> resultLt = new ArrayList<HrMstInstituteTO>();
        try {
            resultLt = beanRemote.instDetailGridLoad(compCode);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return resultLt;
    }

    public String saveInstituteDetail(HrMstInstituteTO instObj) throws WebException, ApplicationException {
        String result = "";
        try {
            result = beanRemote.saveRecord(instObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public String getMaxInstitute(int compCode) throws WebException {
        String result = "";
        try {
            result = beanRemote.getMaxInstCode(compCode);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return result;
    }

    public String updateInstituteDetail(HrMstInstituteTO instObj) throws WebException {
        String result = "";
        try {
            result = beanRemote.updateInstituteRecord(instObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public String deleteInstituteDetail(HrMstInstitutePKTO instObj) throws WebException {
        String result = "";
        try {
            result = beanRemote.deleteInstituteDetail(instObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }
}
