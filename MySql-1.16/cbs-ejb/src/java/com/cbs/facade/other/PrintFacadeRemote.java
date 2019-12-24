/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.dto.SalaryProcessPojo;
import com.cbs.exception.ApplicationException;
import java.io.File;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Sudhir
 */
@Remote
public interface PrintFacadeRemote {

    public String save(String billType, String fieldName, int fieldOrder, int x, int y, int width) throws ApplicationException;

    public String update(String billType, String lableValue, int fieldOrder, int x, int y, int width) throws ApplicationException;

    public String delete(String billType, int fieldOrder) throws ApplicationException;

    public List getData(String billType, String fieldType) throws ApplicationException;

    public List getOnloadData() throws ApplicationException;

    public List getAlphaCode(String orgnCode) throws ApplicationException;

    public List getPODetail(String instNo, String issueDt, String ogrnAlphaCode) throws ApplicationException;
    
    public List getPOPaidDetail(String instNo, String paidDt, String ogrnAlphaCode) throws ApplicationException;

    public List getBillParameters(String billType) throws ApplicationException;

    public List getDDDetail(String instNo, String issueDt, String ogrnAlphaCode) throws ApplicationException;

    public List reportOption() throws ApplicationException;

    public String createFile(Integer reportOption, String date, String brCode) throws ApplicationException;

    public List getFDDetails(String acno, Float rtNo) throws ApplicationException;

    public String insertSalaryData(List<SalaryProcessPojo> dataList, String dt, String valueDt, String enterBy, String orgBrnCode) throws ApplicationException;

    public String getMsgFlagByAcno(String acno) throws ApplicationException;

    public List getFDValues() throws ApplicationException;

    public String insertSalaryFromXLS(String acno, String custname, String dt, String valueDt, double cramt, double dramt, Integer ty, Float recno,
            String instNo, String instDt, Integer payby, String enterby, String details,
            Float trsno, String orgBrCode, String destBrCode) throws ApplicationException;

    public List<String> generateCbsAtmReconFiles(String fileGenerationDt, String generatedBy) throws ApplicationException;
    // Added by Manish kumar

    public List getReconFileType() throws ApplicationException;

    public List<String> generatePosAtmReconFiles(String fileGenerationDt, String generatedBy) throws ApplicationException;
    //28-04-2017

    public int uploadClearingFile(File path) throws ApplicationException;
    //------

    public List getPOValues() throws ApplicationException;

    public List getPODtValues() throws ApplicationException;

    public List getDDValues() throws ApplicationException;

    public boolean isNewPrinting(String repName) throws ApplicationException;

    public String getBillNature(String billType) throws ApplicationException;

    public List getFieldNameList(String billType, String fieldType) throws ApplicationException;

    public List getPrintingParameters(String type, String opt) throws ApplicationException;

    public List<String> getNewFdDetails(String acNo, Float rtNo) throws ApplicationException;

    public List<String> getNewPassbookDetails(String acNo) throws ApplicationException;

    public List getPrintingHeader(String type) throws ApplicationException;
    
    public List<String> generateFinacusReconFiles(String fileGenerationDt, String generatedBy) throws ApplicationException;

    public List<String> generatePosAtmFinacusFiles(String fileGenerationDt, String generatedBy) throws ApplicationException;

    public List<String> generateImpsOwTxnFile(String fileGenerationDt, String fileGenBy) throws ApplicationException;
    
    public List<String> generateSarvatraImpsReconFile(String fileGenerationDt, String fileGenBy) throws ApplicationException;
}
