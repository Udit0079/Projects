/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.facade.personnel;

import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrPersonnelDetailsTO;
import com.hrms.common.to.HrSeparationDetailsTO;
import java.util.List;

/**
 *
 * @author admin
 */
public interface PersonnelMasterFacadeRemote {

    public String performOperationAcceptanceletter(int compCode, String empid, String flag, HrSeparationDetailsTO to) throws ApplicationException;

    public java.util.List viewDataAcceptancefacade(int compCode) throws ApplicationException;

    public List<HrPersonnelDetailsTO> findDataPersonalDetailFacade(int compCode, String search, int seatchflag) throws ApplicationException;

    public java.util.List selectDataEmpidFacade(int compCode, java.lang.String empId) throws com.hrms.common.exception.ApplicationException;

    public java.util.List<com.hrms.common.to.HrMstStructTO> getIntialData(int compCode, java.lang.String strcode) throws com.hrms.common.exception.ApplicationException;

    public java.util.List viewDataAppraisalFacade(int compCode) throws com.hrms.common.exception.ApplicationException;

    public java.lang.String performOperationAppraisal(java.lang.String flag, com.hrms.common.to.HrApprisalDetailsTO to) throws ApplicationException;

    public java.util.List viewDataSettlementLetter(int compCode) throws ApplicationException;

    public List viewDataPromotionFacade(int compCode) throws ApplicationException;

    public String generateArnCodeFacade(int compCode) throws ApplicationException;

    public java.lang.String performoperationPromotion(java.lang.String flag, com.hrms.common.to.HrPromotionDetailsTO to, com.hrms.common.to.HrPersonnelDetailsTO to1) throws ApplicationException;

    public java.util.List empDataSetelementFacade(int compCode, java.lang.String empId) throws ApplicationException;

    public java.util.List viewSetelementSearch(int compCode) throws com.hrms.common.exception.ApplicationException;

    public java.lang.String performOperationSettlement(java.lang.String flag, com.hrms.common.to.ClearSlipHdTO to, com.hrms.common.to.ClearSlipDetTO to1) throws ApplicationException;

    public java.util.List<com.hrms.common.to.HrPersonnelDetailsTO> addviewData(java.lang.String flag, int compCode) throws ApplicationException;

    public java.lang.String performOperationExitInterview(java.lang.String flag, com.hrms.common.to.HrPersonnelDetailsTO hrPersonnelDetailsTO, com.hrms.common.to.HrExitInterviewDtTO exitInterviewDtTO, com.hrms.common.to.HrExitInterviewHdTO exitInterviewHdTO) throws ApplicationException;

    public java.util.List viewDataExitInterviewFacade(int compCode, long empCode) throws ApplicationException;
    
    public java.util.List viewEmployeeForTaxSearch() throws com.hrms.common.exception.ApplicationException;
}
