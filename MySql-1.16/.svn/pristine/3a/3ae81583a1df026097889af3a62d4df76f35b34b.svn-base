package com.cbs.facade.ho.deadstock;

import com.cbs.dto.DeadstockTranTable;
import com.cbs.dto.ItemMasterTable;
import com.cbs.dto.ItemStatusReportTable;
import com.cbs.exception.ApplicationException;
import java.util.List;
import java.util.Vector;
import javax.ejb.Remote;

@Remote
public interface DeadstockFacadeRemote {

    //DepreciationApplyRemote
    public java.util.List getDepreciationGroupCodde() throws ApplicationException;

    public java.util.List getBrachList() throws ApplicationException;

    public java.util.List getItemCodeList(java.lang.String brncode, java.lang.String groupCode) throws ApplicationException;
    
    public java.util.List getItemCodeListReport(java.lang.String groupCode) throws ApplicationException;
   
    public java.util.List getDistinctNoReport(java.lang.String groupCode, int itemCode ) throws com.cbs.exception. ApplicationException;

    public List<Vector> getDpriciation(String groupCode, String itemcode, String brncode, int itemdistNo,String itemOption,String frDt,String toDt) throws ApplicationException;

    public java.util.List getDistinctNo(java.lang.String brncode, java.lang.String groupCode, int itemCode) throws com.cbs.exception.ApplicationException;

    public String postDepreciation(java.util.List<Vector> data,String frDt,String toDt) throws ApplicationException;

    //DsItemMasterRemote
    public List getGroupList() throws ApplicationException;

    public List viewData() throws ApplicationException;

    public Vector getGroupCode(String groupCode) throws ApplicationException;

    public String getMaxItemCode() throws ApplicationException;

    public String saveItemMasterRecord(String selectGroup, long itemCode, String itemName, String userName) throws ApplicationException;

    public String updateItemMasterRecord(String selectGroup, long itemCode, String itemName, String userName) throws ApplicationException;

    //DsItemStatusReportLocal
    public java.util.Vector getBranchNameandAddress(java.lang.String orgnbrcode) throws ApplicationException;

    public java.util.List<ItemStatusReportTable> getDataAccordingToBrnCode(java.lang.String brncode, String fromDt, String toDt) throws ApplicationException;

    //DsPurchaseTransferRemote
    public List getAccountDetailsGlhead(String acno) throws ApplicationException;

    public String getItemName(String itemCode) throws ApplicationException;

    public List selectAlphaCodeByBranchCode(String brncode) throws ApplicationException;

    public List selectAlphaCode() throws ApplicationException;

    public List getItemList(String orgnBrCode, String string) throws ApplicationException;

    public List getDataInGrid(String itemCode, String orgnBrCode) throws ApplicationException;

    public String checkAmountForSuspenseAc(String seqenceNo, String year, String acno, String amount) throws ApplicationException;

    public List getDataInGridForWriteOff(String itemCode, String orgnBrCode) throws ApplicationException;

    public String saveTransactionRecord(List<DeadstockTranTable> batchTable, float trsno, List<ItemMasterTable> itemTable, String modeOfTrns, String userName) throws ApplicationException;

    public String checkAmountForSundryAc(String seqenceNo, String year, String acno, String amount) throws ApplicationException;

    public List getAccountNoAccordingToGlhead(String acName) throws ApplicationException;

    //ItemGroupDepreciationRemote
    public Vector getDepreciationGroupCode(String groupCode) throws ApplicationException;

    public List viewDepreciationData() throws ApplicationException;

    public List getDepreciationGroupList() throws ApplicationException;

    public String updateRecord(String selectGroup, String wefPeriod, float depPercent, String method, String round, String roundBase, String range, String userName) throws ApplicationException;

    public String saveRecord(String selectGroup, String wefPeriod, float depPercent, String method, String round, String roundBase, String range, String userName) throws ApplicationException;

    //ItemGroupRemote
    public long getMaxGroupCode() throws ApplicationException;

    public String saveData(String code, String name, String dt, String userName) throws ApplicationException;

    public List viewItemGroupData() throws ApplicationException;

    public String updateItemGroupRecord(String code, String name, String dt, String userName) throws ApplicationException;

    public java.util.List viewDataInGrid(int trsNo) throws com.cbs.exception.ApplicationException;

    public java.lang.String deleteBatch(int trsno, String userName) throws com.cbs.exception.ApplicationException;

    public List<ItemStatusReportTable> getDeadStockData(String brncode, String toDt) throws ApplicationException;
    
    public List<ItemStatusReportTable>getDepreciationReportData(String groupCode, String itemcode, int itemDistNo, String calFromDate, String caltoDate,String itemOption)throws ApplicationException;
    
    public List getFrToDateByItemGroup(String brncode, String groupCode) throws ApplicationException;
}
