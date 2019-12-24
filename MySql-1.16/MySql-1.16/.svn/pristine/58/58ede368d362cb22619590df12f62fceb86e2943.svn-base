package com.cbs.facade.inventory;

import com.cbs.dto.InventoryMasterGridFile;
import com.cbs.dto.InventoryMovementGridDetail;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Zeeshan waris
 */
@Remote
public interface InventorySplitAndMergeFacadeRemote {

    public List gridLoadForSplitDetail(String brnCode, String invtClass, String invtType, String invtSrNo) throws ApplicationException;

    public List gridLoadForMergeDetail(String brnCode, String invtClass, String invtType, String invtSrNo) throws ApplicationException;

    public String splitInventory(String brCode, String alpha, String invtClass, String invtType, String fromNo,
            String endNo, String itemsPerUnit, String quantity, String txnId, String enterDt, String enterBy) throws ApplicationException;

    public String mergingInventoryDetail(String brCode, String alpha, String invtClass, String invtType, String fromNo, String endNo,
            String itemsPerUnit, String quantity, String enterDt, String enterBy) throws ApplicationException;

    public String saveRecord(List<InventoryMasterGridFile> gridDetail, String brCode) throws ApplicationException;

    public List inventoryClassCombo() throws ApplicationException;

    public List inventoryClassAndDescription(String invtClass) throws ApplicationException;

    public List gridDetailOnModify(String invtClass) throws ApplicationException;

    public String updateRecord(String invtClass, String oldInvtType, String oldInvtTypeDesc, String invtType, String invtTypeDesc, String modBy) throws ApplicationException;

    public String deleteRecord(String invtClass, String invtType, String invtTypeDesc) throws ApplicationException;

    public String addNewInvtTypeInExistingInvtClass(List<InventoryMasterGridFile> gridDetail, String brCode) throws ApplicationException;

    public List getAccountDetails(String acno) throws ApplicationException;

    public List cheqSeriesNum(String acctType) throws ApplicationException;

    public List tableDataIssue(String acctNum) throws ApplicationException;

    public List cheqSeriesValidation(String chqSeriesNo, String orgnCode) throws ApplicationException;

    public String introducerAcDetail(String introAcno) throws ApplicationException;

    public List cheqFromValidation(String acctNum, String chSrNo) throws ApplicationException;

    public String ChqBookIssueDelete(String acctNo, int chqFrom, int chqTo, String acctType, String invtSrNo, String invtClass, String invtType) throws ApplicationException;

    public String ChqBookIssueSaveUpdation(String command, int chqFrom, int chqTo, String cheqSeries, int noOfLeaves, String fullAccountNo, String acctType, String remarks,
            String userName, String todayDate, String chqbookcharges, String invtClass, String invtType, String atPar, String chBookType) throws ApplicationException;

    public List gridLoadForStockDetail(String brnCode, String invtClass, String invtType, String invtSrNo) throws ApplicationException;

    public List inventoryClassComboList() throws ApplicationException;

    public List inventoryTypeCombo(String invtClass) throws ApplicationException;

    public List locationClassDesc(String locClass) throws ApplicationException;

    public List locationClass() throws ApplicationException;

    public List branchName(String brnCode) throws ApplicationException;

    public String saveMovementDetail(List<InventoryMovementGridDetail> gridDetail) throws ApplicationException;

    public List gridLoadOnMod(String brnCode) throws ApplicationException;

    public List gridLoadOnEnquiry(String brnCode) throws ApplicationException;

    public String updateMovementDetail(String tranNo, String tranSrNo, String fromBrnId, String toBrnId, String fromBrnLocClass, String toBrnLocClass,
            String invtClass, String invtType, String alpha, String fromNo, String toNo,
            String quantity, String trfParticular, String invtValue, String modBy, String modDate, String brCode) throws ApplicationException;

    public String verifyTranNumber(String tranNo, String authBy, String trnSrNo) throws ApplicationException;

    public List gridLoadOnAcknowledge(String brnCode) throws ApplicationException;

    public String acknowledgeTranNumber(String alpha, int startNo, int endNo, String enterDate, String toBrnId, String invtClass, String invtType, int quantity, String invtTranNo, String userName, String invtTranSrNo) throws ApplicationException;

    public String deleteInvtTransactionNumber(String tranNo, String delBy, String trnSrNo) throws ApplicationException;

    public List chequeSeriesCombo(String brnCode, String invClass, String invType) throws ApplicationException;

    public List chequeSeriesList(String brnCode, String invClass, String invType, String entFlag) throws ApplicationException;

    public String alphaCodeList(int brncode, String acType, int endNo) throws ApplicationException;

    public List getAccountNatureList() throws ApplicationException;

    public String SaveupdateRecordATM(String update, String atmIdentifier, String atmBranch, String atmCashHead,
            String atmStatus, String city, String address, String userName, String todayDate, String atmName,
            String state, String locationType, String site) throws ApplicationException;

    public List gridDetailATMMaster() throws ApplicationException;

    public List getPoDetails(String flag, String seqNoInstNo, String issueDt, String orgBrCode) throws ApplicationException;

    public String getBranchName(String brcode) throws ApplicationException;

    public String saveBiometricDeviceRecord(String function, String deviceSrlNo, String deviceModel, String deviceMake, String enterBy, String branch) throws ApplicationException;

    public List gridBiometricData(String deviceSrlNo) throws ApplicationException;

    public String ChqBookIssueWithSlabDeatilSave(String chqFrom, String chqTo, String noOfLeaves, String cheqSeries,
            String instType, String cmbAmtfrm, String cmbAmtTo, String combCode, String invtClass, String invtType,
            String printlot, String brnCode, String userName, String Date) throws ApplicationException;

    public List getBusinessDetails(String custId) throws ApplicationException;

    public int addCustomerBusinessData(String custId, String bankName, String bankIfsc, String bankAccoNo, String pan,
            String businessGst, String companyName, String AccountName, String firstName, String lastName,
            String email, String mobile, String username, String businessCate, String businessType,
            String flag) throws Exception;

    public List getAddAndModifyData();

    public int updateCustomerById(String custId, String bankName, String bankIfsc, String bankAccoNo, String pan,
            String businessGst, String companyName, String AccountName, String firstName, String lastName, String email,
            String mobile, String username, String businessCate, String businessType, String flag) throws Exception;

    public int verfiyCustomerBusinessData(String custId, String flag, String userName) throws Exception;
}
