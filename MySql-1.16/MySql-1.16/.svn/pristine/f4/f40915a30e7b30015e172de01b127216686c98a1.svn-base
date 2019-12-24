/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.misc;

import com.cbs.dto.ChargesObject;
import com.cbs.dto.Table;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface InoperativeChargesFacadeRemote {

    List getDropdownList() throws ApplicationException;

    public List<ChargesObject> inoperativeChargesCaculate(String acType, String fromDt, String toDt, String orgnBrCode, String enterBy, String entryDate) throws ApplicationException;

    List setAcToCredit(String acType) throws ApplicationException;

    public String inoperativeChargesPost(String acType, String glAcNo, String fromDt, String toDt, String authBy,
            String orgnBrCode, String todayDate) throws ApplicationException;

    List setDate(String acType, String date, String orgnBrCode) throws ApplicationException;

    public List getDropdownListInoperative() throws ApplicationException;

    //public List inoperativeMarking(String orgBrnCode, String acType, int days, String date) throws ApplicationException;

    public List getInoperativeAccounts(String orgBrnCode, String acType, int days, String date) throws ApplicationException;

    public String inoperativeMark(String orgBrnCode, String acType, List<Table> dataList, String authby, String tillDt) throws ApplicationException;

    public List<ChargesObject> calculateCharges(String acType, int minChg, String dtFrom, String dtTo, String status, String brnCode, String enterBy, String enterDate) throws ApplicationException;

    public List accType() throws ApplicationException;

    public String getMinimumIncidentalCharges() throws ApplicationException;
            
    public String fYearData(String brCode) throws ApplicationException;

    String crAccount(String acctype) throws ApplicationException;

    String postData(String brcode, String entryby, String entrydate, String crdaccount, String acType, String dtpFrom, String dtpTo, int minChg, String status) throws ApplicationException;

    public List<ChargesObject> reportPrintDetail(float trsno, String acType, String enterBy, String enterDt, String brCode) throws ApplicationException;
    
    public List<ChargesObject> calculateChargesAllBranch(String acType, int minChg, String dtFrom, String dtTo, String status, String brCode, String enterBy, String enterDate) throws ApplicationException;
    
    public String postDataAllBranch(String brcode, String entryby, String entrydate, String crdaccount, String acType,
            String dtpFrom, String dtpTo, int minChg, String status) throws ApplicationException;
    
    public List<ChargesObject> reportPrintDetailAllBranch(float trsno, String acType, String enterBy, String enterDt, String brCode) throws ApplicationException;
    
    public String crAccountAll(String acctype) throws ApplicationException;
}
