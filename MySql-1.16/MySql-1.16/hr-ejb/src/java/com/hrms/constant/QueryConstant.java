/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.constant;

public class QueryConstant {

    public static final String FIND_BY_COMP_CODE_EMP_CODE_EMP_ID = "SELECT h FROM HrPersonnelDetails h WHERE h.hrPersonnelDetailsPK.compCode = :compCode AND (h.hrPersonnelDetailsPK.empCode = :empCode OR h.empId = :empId)";
    public static final String FIND_CATEGORIZATION_BASED_EMPLOYEES = "SELECT h FROM HrPersonnelDetails h WHERE h.hrPersonnelDetailsPK.compCode = :compCode AND h.empStatus = 'Y' AND (h.deptCode= :type OR h.gradeCode= :type OR h.unitName= :type OR h.block =:type OR h.desgCode= :type OR h.catgCode= :type OR h.zones= :type OR h.locatCode= :type OR h.empType= :type)";
    public static final String FIND_BY_COMP_CODE_AND_LIKE_EMP_ID = "SELECT h FROM HrPersonnelDetails h WHERE h.hrPersonnelDetailsPK.compCode = :compCode AND h.empId like :empId order by h.empId";
    public static final String FIND_BY_COMP_CODE_AND_LIKE_EMP_NAME = "SELECT h FROM HrPersonnelDetails h WHERE h.hrPersonnelDetailsPK.compCode = :compCode AND h.empName like :empName order by h.empName";
    public static final String FIND_EMP_BY_COMP_CODE_WITH_STATUS_Y = "SELECT h FROM HrPersonnelDetails h WHERE h.hrPersonnelDetailsPK.compCode = :compCode and h.empStatus = :empStatus";
    public static final String FIND_BY_COMP_EMP_ID_WITH_STATUS_Y = "SELECT h FROM HrPersonnelDetails h WHERE h.hrPersonnelDetailsPK.compCode = :compCode AND h.empId like :empId AND h.empStatus = :empStatus order by h.empId";
    public static final String FIND_CATEGORIZATION_BASED_EMPLOYEES_WITH_EMP_STATUS = "SELECT h FROM HrPersonnelDetails h WHERE h.hrPersonnelDetailsPK.compCode = :compCode AND h.empStatus = :empstatus AND (h.deptCode= :type OR h.gradeCode= :type OR h.unitName= :type OR h.block =:type OR h.desgCode= :type OR h.catgCode= :type OR h.zones= :type OR h.locatCode= :type OR h.empType= :type)";
}
