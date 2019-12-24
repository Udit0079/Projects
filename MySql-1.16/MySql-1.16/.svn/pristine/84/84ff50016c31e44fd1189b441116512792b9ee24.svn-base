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
public interface IbcObcFacadeRemote {

    public java.lang.String billEnquiry(java.lang.String bill, java.lang.String billType, java.lang.Float billNo, java.lang.Integer year, java.lang.String brCode) throws com.cbs.exception.ApplicationException;

    public java.util.List FYear(java.lang.String brCode, java.lang.String billType) throws com.cbs.exception.ApplicationException;

    public java.util.List RealInwardChqOnLoad(java.lang.String brCode) throws com.cbs.exception.ApplicationException;

    public java.util.List chkInstrument(java.lang.String acno, java.lang.String instNo) throws com.cbs.exception.ApplicationException;

    public java.lang.String RealOutBillFyearProcess(java.lang.String BRCODE, java.lang.String billType, java.lang.Float billNo, java.lang.Integer year) throws com.cbs.exception.ApplicationException;

    public java.util.List RealOutBillFormLoad(java.lang.String brCode) throws com.cbs.exception.ApplicationException;

    public java.util.List RealOutBillFormLoad1() throws com.cbs.exception.ApplicationException;

    public java.util.List RealOutBillFormLoad2() throws com.cbs.exception.ApplicationException;

    public java.lang.String RealOutBillRealized(java.lang.String BRCODE, java.lang.String enterby, java.lang.String billType, java.lang.Float billNo, java.lang.Integer year, java.lang.String acno, java.lang.String instno, double instamount, java.lang.String instdt, double OtherCharges, double OtherBankCharges, double AmtDebited, java.lang.String CustName, java.lang.String DTRealize) throws com.cbs.exception.ApplicationException;

    public java.lang.String RealOutBillDishonorOutBill(java.lang.String BRCODE, java.lang.String enterby, java.lang.String billType, java.lang.Float billNo, java.lang.Integer year, java.lang.String acno, java.lang.String instno, double instamount, java.lang.String instdt, double RetCharges, java.lang.String Reason) throws com.cbs.exception.ApplicationException;

    public java.lang.String RealInwardChqPass(java.lang.String BRCODE, java.lang.String enterby, java.lang.String billType, java.lang.Float billNo, java.lang.Integer year, java.lang.String acno, java.lang.String instno, double instamount, java.lang.String instdt, double OtherCharges, double AmtDebited, java.lang.String CustName, java.lang.Integer VarPayBy) throws com.cbs.exception.ApplicationException;

    public java.util.List RealOutBillOnLoad(java.lang.String brCode) throws com.cbs.exception.ApplicationException;

    public java.lang.String RealOutBillFyearProcess(java.lang.String billType, java.lang.Float billNo, java.lang.Integer year, java.lang.String brCode) throws com.cbs.exception.ApplicationException;
    
}
