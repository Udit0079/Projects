/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.dto.other.venderMasterPojo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface OtherMgmtFacadeRemote {

    public void activityAlertCheck(String todayDt, String orgBrnCode) throws Exception;

    public String saveVenderMasterDetail(List<venderMasterPojo> list, String username, String date, String funtion) throws Exception;

    public List getVenderDetail(String gstNo) throws Exception;

    public String updateVenderMasterDetail(String gstIn, String Name, String state, String user) throws Exception;

    public String saveVenderInvoiceDetail(String gstin, String invoiceNo, String invcAmt, String cgst, String sgst, String igst, String user, String date) throws ApplicationException;

    public List getVenderInvoiceUnauthorizeDetail() throws ApplicationException;

    public String authorizeDetail(venderMasterPojo obj, String user) throws ApplicationException;

    public String deleteDetail(venderMasterPojo obj) throws ApplicationException;

    public List isgstexits(String gstNo) throws ApplicationException;

    public List cbsAutoNeftDetailFieldListBasesOnModulePrirorty(String gNo, String module) throws ApplicationException;

    public List cbsNeftBankNameList() throws ApplicationException;

    public String updateH2hParameter(String field, String value, String neftBankName, String process) throws ApplicationException;
}
