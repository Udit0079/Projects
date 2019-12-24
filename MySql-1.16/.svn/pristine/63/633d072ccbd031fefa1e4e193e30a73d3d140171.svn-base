/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.admin;

import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Ankit Verma
 */
@Remote
public interface AccountMaintenanceFacadeRemote {

    public String introducerCity(String recCode, String cityCode) throws ApplicationException;

    public String introducerAcDetail( String introAcno) throws ApplicationException;

    public String updateAcctOpenEdit(String acno, String custname, String craddress, String praddress, String phoneno, 
            int occupation, int operatingMode, int document, String documentDetails, String panno, String grdname, String fathername, 
            String acnoIntro, String JtName1, String JtName2, String nominee, String nominee_rel, String JtName3, String JtName4, 
            float rdinstall, float rdroi, String openDt, String MatDt, String acctIns, int minBalOpt, int chkBookOpt, String int_option, 
            String DateText, String UserText, String brcode, String nomineeAdd, String nominee_relatioship, String nomineeDate, String minor, 
            Integer nomage, String jtName1Code, String jtName2Code, String jtName3Code, String jtName4Code,int dmdFlag,String actCateg,String tdsApp, String hufFamily) throws ApplicationException;
    
    public String customerId(String custId) throws ApplicationException;

    public String custInfoEdit(String acno) throws ApplicationException;

    public List grid(String acno) throws ApplicationException;

    public List onLoadList5() throws ApplicationException;

    public List onLoadList4() throws ApplicationException;

    public List onLoadList3() throws ApplicationException;

    public List onLoadList1() throws ApplicationException;
    
    public String dmdAmtFlag() throws ApplicationException;

}
