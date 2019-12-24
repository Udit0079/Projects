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
public interface CrrDailyEntryFacadeRemote {

    public java.util.List getTableDetails() throws com.cbs.exception.ApplicationException;

    public java.lang.String saveRecordCrrDaily(java.lang.String acDescription, java.lang.String acNo, java.lang.String BalTp) throws com.cbs.exception.ApplicationException;

    public java.util.List gridLoad(java.lang.String acdesc) throws com.cbs.exception.ApplicationException;

    public java.lang.String getAcName(java.lang.String acNo) throws com.cbs.exception.ApplicationException;

    public java.util.List beforeUpdate(java.lang.String acName, java.lang.String facNo, java.lang.String tacNo) throws com.cbs.exception.ApplicationException;

    public java.lang.String deleteRecord(java.lang.String acdesc, java.lang.String Facno, java.lang.String Tacno) throws com.cbs.exception.ApplicationException;

    public java.lang.String update(java.lang.String acName, java.lang.String facno, java.lang.String tacno, java.lang.String newFacno, java.lang.String newTacno) throws com.cbs.exception.ApplicationException;

    public java.lang.String deleteIndivisual(java.lang.String acDesc, java.lang.String accountNo, java.lang.String accountDesc, java.lang.String username) throws com.cbs.exception.ApplicationException;

    //public java.lang.String save1(java.lang.String acdesc, java.lang.String inputText1, java.lang.String inputText2) throws com.cbs.exception.ApplicationException;
    public String saveSeries(String acdesc, String fromHead, String toHead, String isAlternate, String alternateValue) throws com.cbs.exception.ApplicationException;
    
     public String saveCrrSlr(String wefDate,String CrrPerc, String SlrPerc) throws com.cbs.exception.ApplicationException;

    public java.lang.String deleteSeries(java.lang.String acdesc, java.lang.String fromHead, java.lang.String toHead) throws com.cbs.exception.ApplicationException;

    public java.util.List fillAcDescAndAlternateColumn() throws com.cbs.exception.ApplicationException;

    public java.util.List acnoItem() throws com.cbs.exception.ApplicationException;

    public java.util.List getGnoAndFlag(java.lang.String acdesc) throws com.cbs.exception.ApplicationException;

    public java.lang.String saveIndivisual(java.lang.String acdesc, java.lang.String acno, java.lang.String reportView, java.lang.String altOption, java.lang.String altColValue) throws com.cbs.exception.ApplicationException;

    public java.util.List getDateData() throws com.cbs.exception.ApplicationException;

    public java.lang.String dataUpdate(java.lang.String fromDt, java.lang.String toDt, java.lang.String enterBy, java.lang.String orgbrcode) throws com.cbs.exception.ApplicationException;

    public java.util.List getTableDetailsSavingDeposit() throws com.cbs.exception.ApplicationException;

    public java.lang.String saveRecord(java.lang.String dateOfEffect, double SBTL, java.lang.String enterBy) throws com.cbs.exception.ApplicationException;

    public java.lang.String setRowUpdate(java.lang.String dateOfEffect, double SBTL, java.lang.String enterBy) throws com.cbs.exception.ApplicationException;

    public java.util.List getSubGroupCode(java.lang.String classify, int groupcode) throws com.cbs.exception.ApplicationException;

    public java.util.List getAcNo(java.lang.String acno) throws com.cbs.exception.ApplicationException;

    public java.lang.String getGroupDescription(int groupCode, int subGroupCode) throws com.cbs.exception.ApplicationException;

    public java.lang.String save(java.lang.String classifi, int grcode, int subgrcode, java.lang.String code, java.lang.String grpdesc, java.lang.String lastupdate, java.lang.String mode, java.lang.String subgrpdesc) throws com.cbs.exception.ApplicationException;

    public java.lang.String updatedesc(java.lang.String grpcode, java.lang.String subgrpcode, java.lang.String grpdesc) throws com.cbs.exception.ApplicationException;

    public java.lang.String updatePlMaster(java.lang.String input, java.lang.String grpcode, java.lang.String subgrpcode, java.lang.String grpdesc, java.lang.String subglcode) throws com.cbs.exception.ApplicationException;

    public java.util.List subgrpdesc(java.lang.String grpcode, java.lang.String subgrpcode, java.lang.String grpdesc, java.lang.String subglcode) throws com.cbs.exception.ApplicationException;

    public java.util.List subGlCode(java.lang.String grpcode, java.lang.String subgrpcode, java.lang.String grpdesc) throws com.cbs.exception.ApplicationException;

    public java.util.List getGrpDesc(java.lang.String grpcode, java.lang.String subgrpcode) throws com.cbs.exception.ApplicationException;

    public java.util.List getsubgrpcode(java.lang.String grpcode) throws com.cbs.exception.ApplicationException;

    public java.util.List getgrpcode() throws com.cbs.exception.ApplicationException;

    public List selectFincompMast() throws ApplicationException;

    public String deleteFincompPlMast(int groupCode, int subGroupCode) throws ApplicationException;
    
    public List gethoCrrSlrData() throws ApplicationException;
}
