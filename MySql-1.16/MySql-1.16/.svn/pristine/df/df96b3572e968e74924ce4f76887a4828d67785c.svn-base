package com.cbs.facade.ho.share;

import com.cbs.dto.DividendTable;
import com.cbs.exception.ApplicationException;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.ejb.Remote;

/**
 *
 * @author Navneet Goyal
 */
@Remote
public interface DividendCalculationFacadeRemote {

    public Double getShareValue() throws ApplicationException;

    public List getAlphaCodeList() throws ApplicationException;

    public List getFinancialYear() throws ApplicationException;

    public List<DividendTable> calculateDividend(String alphaCode, Double shareAmount, Double dividend, Integer financialYear, Date postDate, String Auth1, String brCode, String custType) throws ApplicationException;

    public String postDividend(List<DividendTable> dividendTable, String alphaCode, Integer fyear, Date postDate, String Auth1,String brCode,String custType) throws ApplicationException;

    public Vector getBranchNameandAddress(String orgnbrcode) throws ApplicationException;

   // public String beforePosting(Integer fyear, String alphaCode) throws ApplicationException;

    public String getHOAddress() throws ApplicationException;
}
