/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.bill;

import com.cbs.dto.master.RemittanceTypeMaster;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author admin
 */
@Remote
public interface BillCommissionFacadeRemote {

    public List loadGridBillCommission() throws ApplicationException;

    public List billTypeLoad() throws ApplicationException;

    public String saveDetailBillCommission(String collectType, Double amtFrom, Double amtTo, Float minChg, Float comChg, Float postage, String slabDate, String placeType, Float surCharge, Integer payBY, String comFlag, String enterBy, Float maxCanChg, boolean eFlag, String oldBillType, double oldAmtFrom, double OldAmtTo, Float oldComChg) throws ApplicationException;

    public String deleteRecordBillCommission(String collectType, double amtFrom, float comChg) throws ApplicationException;

    public List loadGridBranchMaster() throws ApplicationException;

    public String modifyDetailBranchMaster(String brName, String brAddress, int brCode, String alphaCode, String city,
            String state, String regOffice, int pinCode, String ifscCode, String locationType,
            String compStatus, String mobileNo) throws ApplicationException;

    public String saveDetailBranchMaster(String brName, String brAddress, int brCode, String alphaCode, String city,
            String state, String regOffice, int pinCode, String ifscCode, String locationType,
            String compStatus, String block, String taluk, String office_type, String opendate, String closedate, String mobileNo) throws ApplicationException;

    public String deleteRecordBranchMaster(int brCode) throws ApplicationException;

    public List checkBrnCode(int brCode) throws ApplicationException;

    public List dataList() throws ApplicationException;

    public List listCharge(String type, String date, int pay, double amt) throws ApplicationException;

//    public List rot(String date) throws ApplicationException;
    public List instTypeStock() throws ApplicationException;

    public List setSlabStockBook(String instcode) throws ApplicationException;

    public List setAmtSlabStockBook(String instcode, int slabcode) throws ApplicationException;

    public List showDataStockBook(String instcode, String brCode) throws ApplicationException;

    public String numberFromData(String from, String brCode) throws ApplicationException;

    public String chkSeriesStock(String instcode, Integer slabcode, String printlot, Integer numFrom, Integer numTo, String brCode) throws ApplicationException;

    public String saveStockBook(List<com.cbs.dto.Stock> table, String dt, String orgnBrCode) throws ApplicationException;

    public List setCodeTableIssue(String instcode, int slabcode, String brCode) throws ApplicationException;

    public List setNumberFromData(String instcode, int slabcode, String brCode) throws ApplicationException;

    public List setAmtSlabIssueStockBook(String instcode, int slabcode, String brCode) throws ApplicationException;

    public String saveBookIssue(List<com.cbs.dto.Issue> table, String dt, String orgnBrCode, String userName) throws ApplicationException;

    public String bookIssueGridDoubleClick(String dt, String orgnBrCode, String userName, String instCode, int slabcode, float sCode, String NumFrom) throws ApplicationException;

    public String showAllDataIssue(String instcode, Integer slabcode, String brCode) throws ApplicationException;

    public List instFind(String remType, String instNum, String brcode) throws ApplicationException;

    public String revalidateSave(String remType, String instNum, String orgndt, String validDT, String enterBy, String brcode) throws ApplicationException;

    public List getInstrumentCodeData() throws ApplicationException;

    public List setFromRemaittance(String instcode) throws ApplicationException;

    public String amountSalbDeleteRemaittance(String instrumentNo, int slab, int sno) throws ApplicationException;

    public List amountSlabTable(String instcode) throws ApplicationException;

    public String saveSlabMasterRemaittance(RemittanceTypeMaster obj, String enterBy, String dt) throws ApplicationException;

    public String deleteSeries(double fromDDNo, double toDDNo, String instrumentType, String slabCode, String series, String userName, String orgBrCode) throws ApplicationException;

    public List getcustInfoByAcno(String acno) throws ApplicationException;

    public List getChargeDays() throws ApplicationException;

    public String chequePurchaseSave(String bntButton, String branch, String seqNo, String bcBillNo, String activityType, String billNo, String accountNo, String acHolder,
            String trantType, String accType, String groupName, double totalLimit, double avgLimit, String bankCode, String bankName,
            String baranch, String schemeCode,String schemeCodeDesc, String docType, String docNo, String docSerise, String docDate, String billDate,
            double amount, int usanceDays, int gracePeriod, String maturityDate, double collectionChr, double postageAmt, double pocketExp,
            double margin, double interestRate, double interestAmt, String status, double realisationAmt, double commPaidToBankers,
            String auth, String authBy, String enterBy, String updateBy, String updateDt) throws ApplicationException;

    public List getSerialNo() throws ApplicationException;

    public List getChequePurchaseDataBySeqNo(String seqNo) throws ApplicationException;

    public List getChequePurchaseDataByBcBillNo(String bcBillNO) throws ApplicationException;

    public String chequePurchaseRealisation(String bcBillNo, String acNo, double amount, String enterBy, String branch, String todayDt) throws ApplicationException;
    
    public List getBcBillNoRealisationN(String branch) throws ApplicationException;

    public List setCodeTableIssueBook(String instcode, int slabcode, String brCode) throws ApplicationException;
}