/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ho.master;

import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author admin
 */
@Remote
public interface DailyMasterFacadeRemote {

    public java.util.List getTableDetails() throws com.cbs.exception.ApplicationException;

    public java.util.List subGroupCode(java.lang.String groupCode, java.lang.String glbAccType) throws com.cbs.exception.ApplicationException;

    public java.util.List subSubGroupCode(java.lang.String groupCode, java.lang.String subGroupCode, java.lang.String glbAccType) throws com.cbs.exception.ApplicationException;

    public java.util.List subSubSubGroupCode(java.lang.String groupCode, java.lang.String subGroupCode, java.lang.String glbAccType, java.lang.String subSubGroupCode) throws com.cbs.exception.ApplicationException;

    public java.util.List selectdescription(java.lang.String groupCode, java.lang.String subGroupCode, java.lang.String subSubGroupCode) throws com.cbs.exception.ApplicationException;

    public java.util.List getSubGrpDesc(java.lang.String acno) throws com.cbs.exception.ApplicationException;

    public java.lang.String saveData(BalanceMastGrid grid, java.lang.String userName) throws com.cbs.exception.ApplicationException;

    //public java.util.List selectGroupCode(java.lang.String type) throws com.cbs.exception.ApplicationException;

    //public java.util.List selectsubGroupCode(java.lang.String type, java.lang.String groupCode) throws com.cbs.exception.ApplicationException;

    //public java.util.List selectsubSubGroupCode(java.lang.String type, java.lang.String groupCode, java.lang.String subGroupCode) throws com.cbs.exception.ApplicationException;

    //public java.util.List selectsubSubSubGroupCode(java.lang.String type, java.lang.String groupCode, java.lang.String subGroupCode, java.lang.String subSubGroupCode) throws com.cbs.exception.ApplicationException;

    //public java.util.List selectDescription(java.lang.String type, java.lang.String groupCode) throws com.cbs.exception.ApplicationException;

    //public java.util.List subCodeDesc(java.lang.String groupCode, java.lang.String subGroupCode) throws com.cbs.exception.ApplicationException;

    //public java.util.List subSubCodeDesc(java.lang.String groupCode, java.lang.String subGroupCode, java.lang.String subSubGroupCode) throws com.cbs.exception.ApplicationException;

    //public java.util.List subSubSubCodeDesc(java.lang.String groupCode, java.lang.String subGroupCode, java.lang.String subSubGroupCode, java.lang.String subSubSubGroupCode) throws com.cbs.exception.ApplicationException;

    public java.lang.String updateRecord(java.util.List arraylist, java.lang.String userName) throws com.cbs.exception.ApplicationException;

    public java.util.List codeCombo() throws com.cbs.exception.ApplicationException;

    public java.util.List acctTypeCombo() throws com.cbs.exception.ApplicationException;

    public java.util.List gridDetail(java.lang.String brCode) throws com.cbs.exception.ApplicationException;

    public java.util.List acName(java.lang.String acno) throws com.cbs.exception.ApplicationException;

    public java.util.List acDesc(java.lang.String acType) throws com.cbs.exception.ApplicationException;

    public java.util.List subGrCodeChk(java.lang.String glbAcType, int grCode, int subGrCode) throws com.cbs.exception.ApplicationException;

    public java.lang.String deleteRecord(java.lang.String brCode, java.lang.String sno, java.lang.String glbAcType) throws com.cbs.exception.ApplicationException;

    public java.lang.String saveRecord(java.lang.String brCode, int grCode, int subGrCode, java.lang.String codeAcNo, java.lang.String description, java.lang.String actype, java.lang.String glbAcType, int acStatus, java.lang.String username) throws com.cbs.exception.ApplicationException;

    public java.lang.String getTableDetailsHoreCouncile() throws com.cbs.exception.ApplicationException;

    public java.lang.String getTableDetails1() throws com.cbs.exception.ApplicationException;

    public java.lang.String getTableDetails2() throws com.cbs.exception.ApplicationException;
    
    public List acNameForBankPurpose(String acno) throws ApplicationException;
    
    public List getBalanceMast() throws ApplicationException;
    
    public String deleteBalanceMast(int groupCode, int subGroupCode, int subSubGroupCode, int subSubSubSubGroupCode) throws ApplicationException;
    
    public List getGlbDtl(String type) throws ApplicationException;
}
