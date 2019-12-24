/*
 * CREATED BY   :  ROHIT KRISHNA GUPTA
 * CREATION DATE:  16 JUNE 2011
 */
package com.hrms.facade.hrd;

import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrDetailsProgramTO;
import com.hrms.common.to.HrMstProgramTO;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author ROHIT KRISHNA
 */
@Remote
public interface ProgramDetailsRemote {

    public List programDetailGridLoad(int compCode) throws ApplicationException;

    public String getMaxProgCode(int compCode) throws ApplicationException;

    public String saveRecord(HrMstProgramTO instObj) throws ApplicationException;

    public List instituteSearchGridLoad(int compCode, String training) throws ApplicationException;

    public List facultySearchGridLoad(int compCode) throws ApplicationException;

    public List instituteDeptName(String instDeptCode, String training) throws ApplicationException;

    public List employeeSearchGridLoad(int compCode, String intDeptCode) throws ApplicationException;

    public String updateRecord(HrMstProgramTO instObj) throws ApplicationException;

    public String deleteRecord(int compCode,String progCode) throws ApplicationException;

    //public List programDetailGridForFindBtn(int compCode,String characters);

    public List minSkilSetList(int compCode) throws ApplicationException ;

    public List skilProgNameList(int compCode) throws ApplicationException ;

    public List skilGridDetail(int compCode) throws ApplicationException ;

    public String saveSkillRecord(HrDetailsProgramTO detProgObj) throws ApplicationException;

    public String updateSkillRecord(int compCode, String progCode, String skillcode,String oldProgCode,String oldSkillCode)throws ApplicationException;

    public String deleteSkillRecord(int compCode, String progCode,String skillCode)throws ApplicationException;
}
