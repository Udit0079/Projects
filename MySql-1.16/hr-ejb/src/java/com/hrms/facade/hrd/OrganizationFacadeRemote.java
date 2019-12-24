/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.facade.hrd;

import javax.ejb.Remote;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrMstDesgTO;
import com.hrms.common.to.HrOrgChartTO;
import java.util.List;

/**
 *
 * @author Zeeshan Waris
 */
@Remote
public interface OrganizationFacadeRemote {

    public List reportingStructureList(int compCode) throws ApplicationException;

    public String reportingDetailsSave(HrOrgChartTO orgObj) throws ApplicationException;

    public String reportingDetailsUpdate(HrOrgChartTO orgObj) throws ApplicationException;

    public String reportingDetailsDelete(int compCode, String contcode) throws ApplicationException;

    public List viewOrgList(int compcode, String dept, String grade) throws ApplicationException;

    public List prepareOrgDetail(int compCode, String contCode) throws ApplicationException;

    public List prepareOrgSaveDetail(int compCode) throws ApplicationException;

    public String prepareDetailsSave(HrMstDesgTO hrTempObj) throws ApplicationException;

    public String prepareDetailsUpdate(HrMstDesgTO hrTempObj) throws ApplicationException;

    public List prepareUpdateCheckCode(int compCode, String gradeCode) throws ApplicationException;
}
