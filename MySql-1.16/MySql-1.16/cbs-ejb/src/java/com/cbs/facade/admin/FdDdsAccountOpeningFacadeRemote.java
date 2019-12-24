/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.admin;

import com.cbs.exception.ApplicationException;
import javax.ejb.Remote;

/**
 *
 * @author Ankit Verma
 */
@Remote
public interface FdDdsAccountOpeningFacadeRemote {

    public String saveAccountOpenFd(String custType, String custId, String actype, String title, String custname, String craddress,
            String praddress, String phoneno, String dob, Integer occupation, Integer operatingMode, String status, String panno,
            String grdname, String grd_relation, String agcode, String DateText, String UserText, String fathername, String acnoIntro,
            String JtName1, String JtName2, String JtName3, String JtName4, String orgncode, String nominee, String nomineerelatioship,
            Integer docuno, String docudetails, String remark, String cust_nature, String tds_flag, String tds_details,
            String nonname, String nomadd, String relation, String minor, String nomdob, Integer nomage, String custId1, String custId2, String custId3, String custId4, String actCategory) throws ApplicationException;

    public java.util.List getAcctTypeIntro(java.lang.String brcode) throws com.cbs.exception.ApplicationException;

    public String saveAccountOpenDDS(String custType, String custId, String actype, String title, String custname, String craddress,
            String praddress, String phoneno, String dob, Integer occupation, Integer operatingMode, String status, String panno,
            String grdname, String grd_relation, String agcode, String DateText, String UserText, String fathername, String acnoIntro,
            String JtName1, String JtName2, String JtName3, String JtName4, String orgncode, String nominee, String nomineerelatioship,
            Integer docuno, String docudetails, float rdperiod, float rdInstallment, float roi,
            String nomname, String nomadd, String relation, String minor, String nomdob, Integer nomage, String custId1, String custId2, String custId3, String custId4, String actCateg) throws ApplicationException;

    public java.util.List getROIForDDS(double months, double amt, String acTp) throws com.cbs.exception.ApplicationException;

    public java.lang.String introducerAcDetailForDDS(java.lang.String introAcno) throws com.cbs.exception.ApplicationException;

    public java.util.List getAccountTypeNatureByKC() throws com.cbs.exception.ApplicationException;

    public java.util.List getOccupationDetails() throws com.cbs.exception.ApplicationException;

    public java.util.List getOperationMode() throws com.cbs.exception.ApplicationException;

    public java.util.List getSecurity() throws com.cbs.exception.ApplicationException;

    public java.lang.String saveData(java.lang.String TxtRemarks, java.lang.String acType, java.lang.String occupation, java.lang.String titalName, java.lang.String TxtName, java.lang.String txtadd, java.lang.String village, java.lang.String state, java.lang.String phoneNo, java.lang.String dob, java.lang.String fathersName, java.lang.String roi, java.lang.String sancAmount, java.lang.String sancDate, java.lang.String accOpenDate, java.lang.String education, java.lang.String TxtRabiLimit, java.lang.String TxtKharifLimit, java.lang.String TxtAgriInc, java.lang.String TxtOthInc, java.lang.String CmbFarmer, java.lang.String CmbPriSec1, java.lang.String CmbPriSec2, java.lang.String CmbColSec1, java.lang.String CmbColSec2, java.util.List holdingGrid, java.util.List MHLiab, java.util.List MHMAssets, java.util.List MHIMAssets, java.lang.String user, java.lang.String brCode) throws com.cbs.exception.ApplicationException;

    public java.util.List getbsrVillage() throws com.cbs.exception.ApplicationException;

    public java.util.List getAcctType() throws com.cbs.exception.ApplicationException;

    public java.util.List getAgCode(java.lang.String brnCode) throws com.cbs.exception.ApplicationException;

    public java.util.List getROIForFinInclusion(int days, float amount, java.lang.String date) throws com.cbs.exception.ApplicationException;

    public String saveAccountOpenDDS(String custType, String actype, String title, String custname, String craddress,
            String praddress, String phoneno, String dob, Integer occupation, Integer operatingMode, String status, String panno,
            String grdname, String grd_relation, String agcode, String DateText, String UserText, String fathername, String acnoIntro,
            String JtName1, String JtName2, String JtName3, String JtName4, String orgncode, String nominee, String nomineerelatioship,
            Integer docuno, String docudetails, int rdperiod, float rdInstallment, float roi,
            String nomname, String nomadd, String relation, String minor, String nomdob, String nomage, String custId1, String custId2, String custId3, String custId4) throws ApplicationException;

    public java.lang.String saveAccountOpenSbRd(java.lang.String cust_type, java.lang.String actype, java.lang.String title, java.lang.String custname, java.lang.String craddress, java.lang.String praddress, java.lang.String phoneno, java.lang.String dob, int occupation, int operatingMode, java.lang.String panno, java.lang.String grdname, java.lang.String grd_relation, java.lang.String agcode, java.lang.String DateText, java.lang.String UserText, java.lang.String fathername, java.lang.String acnoIntro, java.lang.String JtName1, java.lang.String JtName2, java.lang.String orgncode, java.lang.String nominee, java.lang.String nominee_relatioship, java.lang.String JtName3, java.lang.String JtName4, int rdperiod, float rdinstall, float rdroi, int docuno, java.lang.String docudetails, java.lang.String nomineeAdd, java.lang.String nomineeDate, java.lang.String custid1, java.lang.String custid2, java.lang.String custid3, java.lang.String custid4) throws com.cbs.exception.ApplicationException;

    public String getMaxToMonForDDS(String acTp) throws com.cbs.exception.ApplicationException;
}
