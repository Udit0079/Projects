/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.misc;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.BankLevelSTRPojo;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface StrAlertFacadeRemote {
    
    public String getStrAlert(String custid, String dt, String ty, String tranType, String trnAmt)throws ApplicationException;
    //Added by Manish Kumar

    public List<BankLevelSTRPojo> getBankLevelStr() throws ApplicationException;

    public int insertCBSAlertIndicator(List<BankLevelSTRPojo> strList, String enterBy, String branchCode, boolean checkBox) throws Exception;
    //---------
        
    public List getCustDataById(String custid) throws ApplicationException;

    public String getSaveAlertIndicator(String custid, String dt, String alertCide, String branch, String userName) throws ApplicationException;

    public List getRfaAlertList(String custId) throws ApplicationException;

    public String getCustIdLevelStr(String custid,List<BankLevelSTRPojo> strList, String branch, String userName, boolean checkBox) throws ApplicationException;
    
    public String getBranchByCustId(String custid) throws ApplicationException;
}
