/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.facade.common;

import com.cbs.dto.ChargesObject;
import com.cbs.dto.LockerRentDetail;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;


/**
 *
 * @author root
 */
@Remote
public interface FtsBulkPostingFacadeRemote {

    /**
     * 
     * @param chargesList
     * @param glAcc
     * @param user
     * @param acNat
     * @param desc
     * @param tranDesc
     * @param tyFlag
     * @param taxFlag
     * @param exceedBalFlag
     * @param dt
     * @param enterBy
     * @param orgnBrCode
     * @return
     */
  public String ftsPostBulkDrCr(List<ChargesObject> chargesList, String glAcc, String user, String acNat, String desc, Integer tranDesc,
          Integer tyFlag, String taxFlag,String exceedBalFlag, String dt, String enterBy, String orgnBrCode) throws ApplicationException;

  /**
   *
   * @param Acno
   * @param status
   * @param penalty
   * @param trans
   * @param optStatus
   * @param Custname
   * @param recNo
   * @param trsNo
   * @param enterBy
   * @param user
   * @param transDecs
   * @param description
   * @param orgnBrCode
   * @param entryDate
   * @return
   */
  public String ftsInsertPending(String Acno, String status, Double penalty, float trans, int optStatus, String Custname, Float recNo,
          Float trsNo, String enterBy, String user, Integer transDecs, String description, String orgnBrCode,String entryDate);

  /**
   *
   * @param acno
   * @param ty
   * @param penalty
   * @param desc
   * @param enterBy
   * @param user
   * @param optStatus
   * @param trsno
   * @param recno
   * @param tranDesc
   * @param trans
   * @param custName
   * @param orgnBrCode
   * @return
   */
  public String ftsNpaEntry(String acno, Integer ty, double penalty, String desc, String enterBy, String user, Integer optStatus,
            Float trsno, Float recno, Integer tranDesc, int trans, String custName, String orgnBrCode) throws ApplicationException;

  /**
   *
   * @param orgnBrCode
   * @return
   */
  public String daybeginDate(String orgnBrCode) throws ApplicationException;

  /**
   *
   * @param acType
   * @return
   */
  public String acNature(String acType) throws ApplicationException;

  /**
   *
   * @param Amt
   * @param Dt
   * @param orgnBrCode
   * @return
   */
//  public String taxCalculation(Double Amt, String Dt,String orgnBrCode);

  /**
   *
   * @param appDT
   * @return
   */
//  public List fnTaxApplicableROT(String appDT);

  /**
   *
   * @param chargesList
   * @param GLACC
   * @param DATE1
   * @param DATE2
   * @param USER
   * @param acNat
   * @param Desc
   * @param TranDesc
   * @param brCode
   * @param EnterBy
   * @param entryDate
   * @return
   */
  public String postInciCharges(List<ChargesObject> chargesList, String glAcc, String fromDt, String toDt, String user, String acNat, String desc, Integer tranDesc,
          String brCode, String enterBy, String entryDate);

  public String contraEntryUriOirHead(String acType, double chargesAmt, String brnCode, String details, String toDt, int tranDesc, float trsNo, String authBy) throws ApplicationException;
  /**
   *
   * @param chargesList
   * @param glAcc
   * @param user
   * @param desc
   * @param tranDesc
   * @param taxFlag
   * @param exceedBalFlag
   * @param orgnBrCode
   * @return
   */
  List<List> postLockerRent(List<LockerRentDetail> chargesList, String glAcc, String user, String desc, Integer tranDesc, String taxFlag,
            String exceedBalFlag, String orgnBrCode);
   
}