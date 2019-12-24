/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.bill;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author admin
 */
@Local
public interface OutwardFacadeRemote {


    public java.util.List acctTypeCombo(java.lang.String brCode) throws com.cbs.exception.ApplicationException;

    public java.util.List limitFlagChk(java.lang.String brCode) throws com.cbs.exception.ApplicationException;

    public java.util.List alphaCodeCombo(java.lang.String brCode) throws com.cbs.exception.ApplicationException;

    public java.util.List gridLoad(java.lang.String brCode) throws com.cbs.exception.ApplicationException;

    public java.util.List alphaCodeLostFocus(java.lang.String brCode, java.lang.String alphaCode) throws com.cbs.exception.ApplicationException;

    public java.util.List setbillDetainDate(int period, java.lang.String instDate) throws com.cbs.exception.ApplicationException;

    public java.util.List setvaluesInFielde(java.lang.String brCode, java.lang.String billType, java.lang.String acno, java.lang.String instNo, double instAmt) throws com.cbs.exception.ApplicationException;

    public java.util.List setCustName(java.lang.String brCode, java.lang.String billType, java.lang.String acno, java.lang.String instNo, double instAmt) throws com.cbs.exception.ApplicationException;

    public java.lang.String deleteRecord(java.lang.String brCode, java.lang.String billType, java.lang.String acno, java.lang.String instNo, double instAmt) throws com.cbs.exception.ApplicationException;

    public java.lang.String acNoChk(java.lang.String brCode, java.lang.String acNo) throws com.cbs.exception.ApplicationException;

    public java.util.List acNoLostFocus(java.lang.String brCode, java.lang.String acNo) throws com.cbs.exception.ApplicationException;

    public int IntAccStatus(int accStatus);

    public java.util.List gridLoadOutwardCheque(java.lang.String brCode) throws com.cbs.exception.ApplicationException;

    public java.util.List acctTypeComboOutwardCheque(java.lang.String brCode) throws com.cbs.exception.ApplicationException;

    public java.lang.String saveBtnOutWardBill(java.lang.String brCode, java.lang.String acNo, java.lang.String billType, java.lang.String alpha, java.lang.String instNo, java.lang.String instAmt, java.lang.String instDate, java.lang.String comm, java.lang.String postage, java.lang.String inFavOf, java.lang.String user, java.lang.String invNo, java.lang.String colBankName, java.lang.String colBankAdd, java.lang.String billDetainPeriod, java.lang.String billDetainDt, boolean limitFlag, java.lang.String collectBankType) throws com.cbs.exception.ApplicationException;

    public java.lang.String updateBtnOutWardBill(java.lang.String brCode, java.lang.String acNo, java.lang.String billType, java.lang.String alpha, java.lang.String instNo, java.lang.String instAmt, java.lang.String instDate, java.lang.String comm, java.lang.String postage, java.lang.String inFavOf, java.lang.String user, java.lang.String invNo, java.lang.String colBankName, java.lang.String colBankAdd, java.lang.String billDetainPeriod, java.lang.String billDetainDt, java.lang.String collectBankType) throws com.cbs.exception.ApplicationException;

    public java.lang.String updateBtnOutWardCheque(java.lang.String brCode, java.lang.String acNo, java.lang.String billType, java.lang.String alpha1, java.lang.String instBankName, java.lang.String instBankAdd, java.lang.String instNo, java.lang.String instAmt, java.lang.String instDate, java.lang.String comm, java.lang.String postage, java.lang.String inFavOf, java.lang.String user, java.lang.String alpha2, java.lang.String colBankName, java.lang.String colBankAdd) throws com.cbs.exception.ApplicationException;


    //public java.lang.String saveBtnOutwardCheque(java.lang.String brCode, java.lang.String acNo, java.lang.String billType, java.lang.String alpha1, java.lang.String instBankName, java.lang.String instBankAdd, java.lang.String instNo, java.lang.String instAmt, java.lang.String instDate, java.lang.String comm, java.lang.String postage, java.lang.String inFavOf, java.lang.String user, java.lang.String alpha2, java.lang.String colBankName, java.lang.String colBankAdd) throws com.cbs.exception.ApplicationException;

    // public java.lang.String saveBtn(java.lang.String brCode, java.lang.String acNo, java.lang.String billType, java.lang.String alpha, java.lang.String instNo, java.lang.String instAmt, java.lang.String instDate, java.lang.String comm, java.lang.String postage, java.lang.String inFavOf, java.lang.String user, java.lang.String invNo, java.lang.String colBankName, java.lang.String colBankAdd, java.lang.String billDetainPeriod, java.lang.String billDetainDt, boolean limitFlag, java.lang.String collectBankType) throws com.cbs.exception.ApplicationException;

    public List setvaluesInFieldeOutCheque(String orgnBrCode, String billType, String acNo, String instNo, double instAmt)throws com.cbs.exception.ApplicationException;


   public java.lang.String saveBtnOutWardCheque(java.lang.String brCode, java.lang.String acNo, java.lang.String billType, java.lang.String alpha1, java.lang.String instBankName, java.lang.String instBankAdd, java.lang.String instNo, java.lang.String instAmt, java.lang.String instDate, java.lang.String comm, java.lang.String postage, java.lang.String inFavOf, java.lang.String user, java.lang.String alpha2, java.lang.String colBankName, java.lang.String colBankAdd) throws com.cbs.exception.ApplicationException;





    
}
