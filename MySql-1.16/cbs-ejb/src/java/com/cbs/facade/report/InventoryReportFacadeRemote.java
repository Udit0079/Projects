package com.cbs.facade.report;

import com.cbs.dto.report.InventoryTransferReceiptTable;
import com.cbs.dto.report.IssueChequeBookRegisterPojo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface InventoryReportFacadeRemote {

    public java.util.List<com.cbs.dto.report.InventoryReportPojo> getInvtUsedDestroyReport(java.lang.String type, java.lang.String date, java.lang.String brncode) throws com.cbs.exception.ApplicationException;

    public java.util.List<com.cbs.dto.report.InventoryReportPojo> getInvtStockRegister(java.lang.String type, java.lang.String date, java.lang.String brncode) throws com.cbs.exception.ApplicationException;

    public List getAlphacode() throws ApplicationException;
    
    public List<InventoryTransferReceiptTable> getInventoryTransferData(String getOptions, String selectBoxData, String orgBrnCode , String currentDate) throws ApplicationException;
    
    public List<IssueChequeBookRegisterPojo> getChequeBookData(String repOption, String acNo, String issueDt , String chequeType) throws ApplicationException;

}
