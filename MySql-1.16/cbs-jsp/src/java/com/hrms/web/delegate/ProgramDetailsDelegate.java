/*
 * CREATED BY    :  ROHIT KRISHNA GUPTA
 * CREATION DATE :  01 JULY 2011
 */

package com.hrms.web.delegate;

import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrDetailsProgramTO;
import com.hrms.common.to.HrMstProgramTO;
import com.hrms.utils.HrServiceLocator;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.facade.hrd.ProgramDetailsRemote;
import com.hrms.web.exception.WebException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ROHIT KRISHNA
 */
public class ProgramDetailsDelegate {

    private final String jndiHomeName = "ProgramDetailsBean";
    private ProgramDetailsRemote beanRemote = null;

    public ProgramDetailsDelegate() throws ServiceLocatorException {
        try{ 
            Object lookup = HrServiceLocator.getInstance().lookup(jndiHomeName);
            beanRemote = (ProgramDetailsRemote) lookup;
        }catch (Exception e) {
            throw new ServiceLocatorException(e);            
        }
    }


    public List<HrDetailsProgramTO> progDetailOnload(int compCode) throws WebException {
        List<HrDetailsProgramTO> resultLt = new ArrayList<HrDetailsProgramTO>();
        try {
            resultLt = beanRemote.programDetailGridLoad(compCode);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return resultLt;
    }

    public List minSkill(int compCode) throws WebException  {
        List resultLt = new ArrayList();
        try {
            resultLt = beanRemote.minSkilSetList(compCode);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return resultLt;
    }

    public List skilProgName(int compCode) throws WebException  {
        List resultLt = new ArrayList();
        try {
            resultLt = beanRemote.skilProgNameList(compCode);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return resultLt;
    }

    public List instDeptNameList(String instDeptCode, String training) throws WebException  {
        List resultLt = new ArrayList();
        try {
            resultLt = beanRemote.instituteDeptName(instDeptCode,training);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return resultLt;
    }

    public List skilGridDetails(int compCode) throws WebException  {
        List resultLt = new ArrayList();
        try {
            resultLt = beanRemote.skilGridDetail(compCode);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return resultLt;
    }

    public List instituteSearchGridDetail(int compCode, String training) throws WebException  {
        List resultLt = new ArrayList();
        try {
            resultLt = beanRemote.instituteSearchGridLoad(compCode,training);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return resultLt;
    }

    public List facultySearchGridDetail(int compCode) throws WebException  {
        List resultLt = new ArrayList();
        try {
            resultLt = beanRemote.facultySearchGridLoad(compCode);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return resultLt;
    }

    public List employeeSearchGridDetail(int compCode, String deptCode) throws WebException  {
        List resultLt = new ArrayList();
        try {
            resultLt = beanRemote.employeeSearchGridLoad(compCode,deptCode);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return resultLt;
    }

    public String maxProgramCode(int compCode) throws WebException  {
        String code="";
        try {
            code = beanRemote.getMaxProgCode(compCode);
        } catch (Exception e) {
            throw new WebException(e.getMessage());
        }
        return code;
    }

    public String saveProgramDetail(HrMstProgramTO instObj) throws WebException, ApplicationException {
        String result = "";
        try {
            result = beanRemote.saveRecord(instObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public String updateProgramDetail(HrMstProgramTO instObj) throws WebException, ApplicationException {
        String result = "";
        try {
            result = beanRemote.updateRecord(instObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public String deleteProgramDetail(int compCode, String progCode) throws WebException, ApplicationException {
        String result = "";
        try {
            result = beanRemote.deleteRecord(compCode,progCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public String saveSkillDetails(HrDetailsProgramTO detProgObj) throws WebException, ApplicationException {
        String result = "";
        try {
            result = beanRemote.saveSkillRecord(detProgObj);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public String updateSkillDetails(int compCode, String progCode, String skillcode, String oldProgCode, String oldSkillCode) throws WebException, ApplicationException {
        String result = "";
        try {
            result = beanRemote.updateSkillRecord(compCode,progCode,skillcode,oldProgCode,oldSkillCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

    public String deleteSkillDetails(int compCode, String progCode, String skillCode) throws WebException, ApplicationException {
        String result = "";
        try {
            result = beanRemote.deleteSkillRecord(compCode,progCode,skillCode);
        } catch (ApplicationException e) {
            throw new WebException(e.getExceptionCode().getMessageKey());
        }
        return result;
    }

}
