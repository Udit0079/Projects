/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.bill;

import javax.ejb.Local;

/**
 *
 * @author admin
 */
@Local
public interface InwardFacadeRemote {

    public java.util.List acctTypeCombo(java.lang.String brCode) throws com.cbs.exception.ApplicationException;

    public java.util.List alphaCodeCombo(java.lang.String brCode) throws com.cbs.exception.ApplicationException;

    public java.util.List remTypeCombo(java.lang.String brCode) throws com.cbs.exception.ApplicationException;

    public java.util.List gridLoad(java.lang.String brCode) throws com.cbs.exception.ApplicationException;

    public java.util.List alphaCodeLostFocus(java.lang.String brCode, java.lang.String alphaCode) throws com.cbs.exception.ApplicationException;

    public java.util.List acNoLostFocus(java.lang.String brCode, java.lang.String acNo) throws com.cbs.exception.ApplicationException;

    public java.lang.String acNoChk(java.lang.String brCode, java.lang.String acNo) throws com.cbs.exception.ApplicationException;

    public int IntAccStatus(int accStatus)throws com.cbs.exception.ApplicationException;

    public java.lang.String deleteRecord(java.lang.String brCode, java.lang.String acno, java.lang.String billtype, java.lang.String instNo, java.lang.String instAmt) throws com.cbs.exception.ApplicationException;

    public java.util.List setCustName(java.lang.String brCode, java.lang.String acno) throws com.cbs.exception.ApplicationException;

    public java.util.List setvaluesInFielde(java.lang.String brCode, java.lang.String acno, java.lang.String billType, java.lang.String instNo) throws com.cbs.exception.ApplicationException;

  //  public java.lang.String saveBtn(java.lang.String brCode, java.lang.String acNo, java.lang.String refNo, java.lang.String billType, java.lang.String alphaCode, java.lang.String payableAt, java.lang.String inFavOf, java.lang.String bankName, java.lang.String bankAdd, java.lang.String instNo, java.lang.Double instAmt, float comm, java.lang.String remType, java.lang.String instDate, java.lang.String user, float postage) throws com.cbs.exception.ApplicationException;
    public String saveBtnInwardBillBooking(String brCode, String acNo, String refNo, String billType, String alphaCode, String payableAt, String inFavOf, String bankName, String bankAdd, String instNo, Double instAmt, float comm, String remType, String instDate, String user, float postage) throws com.cbs.exception.ApplicationException;;
    public java.lang.String updateBtn(java.lang.String brCode, java.lang.String acNo, java.lang.String refNo, java.lang.String billType, java.lang.String alphaCode, java.lang.String payableAt, java.lang.String inFavOf, java.lang.String bankName, java.lang.String bankAdd, java.lang.String instNo, java.lang.Double instAmt, float comm, java.lang.String remType, java.lang.String instDate, java.lang.String user, float postage) throws com.cbs.exception.ApplicationException;

 //   public String saveBtn(String brCode, String acNo, String refNo, String billType, String alphaCode, String payableAt, String inFavOf, String bankName, String bankAdd, String instNo, double instAmt, float comm, String remType, String instDate, String user, float postage) throws com.cbs.exception.ApplicationException;;
 //   public java.lang.String saveBtn(java.lang.String brCode, java.lang.String acNo, java.lang.String refNo, java.lang.String billType, java.lang.String alphaCode, java.lang.String payableAt, java.lang.String inFavOf, java.lang.String bankName, java.lang.String bankAdd, java.lang.String instNo, double instAmt, float comm, java.lang.String remType, java.lang.String instDate, java.lang.String user, float postage) throws com.cbs.exception.ApplicationException;




    public java.util.List gridLoadInwardcheque(java.lang.String brCode) throws com.cbs.exception.ApplicationException;

    public java.lang.String updateBtn(java.lang.String brCode, java.lang.String acNo, java.lang.String refNo, java.lang.String billType, java.lang.String alphaCode, java.lang.String payableAt, java.lang.String inFavOf, java.lang.String bankName, java.lang.String bankAdd, java.lang.String instNo, double instAmt, float comm, java.lang.String remType, java.lang.String instDate, java.lang.String user, float postage) throws com.cbs.exception.ApplicationException;

    public java.util.List dateDiff(java.lang.String dt) throws com.cbs.exception.ApplicationException;

    public java.util.List fYear(java.lang.String brCode) throws com.cbs.exception.ApplicationException;

    public java.util.List descriptionCodeBook() throws com.cbs.exception.ApplicationException;

    public java.util.List tabDataObcRealization(float billNo, int fyear, java.lang.String billType, java.lang.String brCode) throws com.cbs.exception.ApplicationException;

    public java.lang.String custName(float billNo, int fyear, java.lang.String billType, java.lang.String brCode) throws com.cbs.exception.ApplicationException;

    public java.util.List grdDataObcRealization(java.lang.String BRCODE) throws com.cbs.exception.ApplicationException;

    public float OtherCharges(float billNo, int fyear, java.lang.String billType, float TxtOtherCharges, float instAmt, float comm, float postage, java.lang.String brCode) throws com.cbs.exception.ApplicationException;

    public java.lang.String FieldCheck(java.lang.String billType, float billNo, int fyear, float TxtOtherCharges, float instAmt, float comm, float postage, float TxtAmtCredited, java.lang.String reason, float retCarges, java.lang.String PanelReason);

    public java.lang.String passBc(float billNo, int fyear, java.lang.String tmpAlpha, java.lang.String accountNo, double TxtAmtCredited, java.lang.String InstNum, float TmpTrsno, java.lang.String Tempbd, java.lang.String user, java.lang.String BRCODE, double comm, double postage, java.lang.String GLBCComm, java.lang.String GLBillCol, double instAmt, float TxtOtherCharges, java.lang.String GLHO, java.lang.String GLBillLodg, java.lang.String billType, java.lang.String realiseDate, java.lang.String instDt) throws com.cbs.exception.ApplicationException;

    public java.lang.String dishonorBc(java.lang.Float retCharges, java.lang.String accountNo, java.lang.String instNo, double ourCharges, double instAmt, double postage, float billNo, int fYear, java.lang.String alphaCodes, java.lang.String BRCODE, java.lang.String user, java.lang.String instDt) throws com.cbs.exception.ApplicationException;

    public java.util.List fYearInward(java.lang.String brCode) throws com.cbs.exception.ApplicationException;

    public java.util.List alphaCode(java.lang.String brCode) throws com.cbs.exception.ApplicationException;

    public java.util.List reasonForCancel() throws com.cbs.exception.ApplicationException;

    public java.util.List LoadGridBookedData(java.lang.String brCode) throws com.cbs.exception.ApplicationException;

    public java.lang.String accountValidation(java.lang.String accountNo, float instAmt) throws com.cbs.exception.ApplicationException;

    public java.lang.String accountValidationResult(java.lang.String accountNo, java.lang.String instNo) throws com.cbs.exception.ApplicationException;

    public java.lang.String procPass(java.lang.String billType, double ourCharges, java.lang.String accountNo, java.lang.String instNo, float billNo, int fYear, double AmtToDebited, int payBy, double instAmt, java.lang.String BRCODE, java.lang.String user, java.lang.String instDt) throws com.cbs.exception.ApplicationException;

    public java.lang.String fieldCheck(java.lang.String billType, java.lang.Float billNo, java.lang.Integer fYear, java.lang.String panelReason, double ourCharges, double amtDebited, double instAmt, double retCharges, java.lang.String resonCancel);

    public java.lang.String fieldDisplay(float billNo, java.lang.Integer fYear, java.lang.String billType, java.lang.String BRCODE) throws com.cbs.exception.ApplicationException;

    public java.lang.String taxAmount(java.lang.Float amt, java.lang.String type) throws com.cbs.exception.ApplicationException;

    public java.util.List fnTaxApplicableROT(java.lang.String appDT) throws com.cbs.exception.ApplicationException;

    public java.lang.String findTax(java.lang.Float commAmt) throws com.cbs.exception.ApplicationException;

    public java.lang.String saveBtnInwardBillBookingCheque(java.lang.String brCode, java.lang.String acNo, java.lang.String refNo, java.lang.String billType, java.lang.String alphaCode, java.lang.String payableAt, java.lang.String inFavOf, java.lang.String bankName, java.lang.String bankAdd, java.lang.String instNo, double instAmt, float comm, java.lang.String remType, java.lang.String instDate, java.lang.String user, float postage) throws com.cbs.exception.ApplicationException;

    public java.lang.String passClickODBCRealization(float billNo, int fyear, java.lang.String billType, float TxtOtherCharges, float instAmt, float comm, float postage, float TxtAmtCredited, java.lang.String accountNo, java.lang.String InstNum, java.lang.String realiseDate, java.lang.String user, java.lang.String BRCODE, java.lang.String instDt, java.lang.String PanelReason) throws com.cbs.exception.ApplicationException;

    public java.lang.String dishonorClick(java.lang.String instNo, java.lang.String instDt, double instAmt, java.lang.String billType, float billNo, int fYear, java.lang.String panelReason, double ourCharges, double amtDebited, double retCharges, java.lang.String resonCancel, java.lang.String accountNo, java.lang.String user, java.lang.String BRCODE) throws com.cbs.exception.ApplicationException;

    public java.lang.String dishonorClickOBCRealization(java.lang.String instNo, java.lang.String instDt, float instAmt, java.lang.String billType, float billNo, int fYear, java.lang.String panelReason, java.lang.Float ourCharges, java.lang.Float retCharges, java.lang.String resonCancel, java.lang.String accountNo, float cumu, float postage, float amtCredited, java.lang.String user, java.lang.String BRCODE) throws com.cbs.exception.ApplicationException;

    public java.lang.String passClick(java.lang.String instNo, java.lang.String instDt, double instAmt, java.lang.String billType, float billNo, int fYear, java.lang.String panelReason, double ourCharges, double amtDebited, double retCharges, java.lang.String resonCancel, java.lang.String accountNo, java.lang.String name, int payBy, java.lang.String user, java.lang.String BRCODE) throws com.cbs.exception.ApplicationException;
    
}
