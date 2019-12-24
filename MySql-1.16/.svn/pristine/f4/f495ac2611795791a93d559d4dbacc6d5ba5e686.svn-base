/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.clg;

import com.cbs.dto.master.BillTypeMasterTO;
import com.cbs.dto.npci.cts.FileHeaderDTO;
import com.cbs.dto.npci.cts.reverse.ReturnDTO;
import com.cbs.dto.report.CTSUploadReportPojo;
import com.cbs.dto.report.CtsChequeStatusReportPojo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

@Remote
public interface NpciClearingMgmtFacadeRemote {

    public Integer getCbsGeneratedScheduleNo(String curDt, String branchCode, String clearingType) throws Exception;

    public boolean isFileUploaded(String fileType, String fileName) throws Exception;

    public String uploadClearingData(FileHeaderDTO pxfDTO, String xmlFileName, String imgFileName,
            Integer scheduleNo, String requestAddr, String userName, String todayDt, String orgnCode) throws Exception;

    public Map getBranchDetailInMap() throws Exception;

    public Map<String, String> getTransCodeMap() throws Exception;

    public BillTypeMasterTO getBillTypeDetail(String billCode) throws Exception;

    public List getAllScheduleNos(String curDt, String branchCode, String clearingType) throws Exception;

    public String[] getSANDetail(String transCode, String san, String serialNo, BillTypeMasterTO billTO, String payOrderRoutNo) throws Exception;

    public List<CtsChequeStatusReportPojo> getctsUploadDataToEdit(String date, String chequeType, String scheduleNo) throws ApplicationException;

    public String updateCTSUploadData(CtsChequeStatusReportPojo ctsUploadData) throws ApplicationException;

    public List<CTSUploadReportPojo> getctsUploadDataToReport(String date, String chequeType, String scheduleNo, String branchCode) throws ApplicationException;

    public String generateCtsReturnXml(List<ReturnDTO> returnData) throws Exception;

    public List<ReturnDTO> getCtsReturnData(String fileGenDt, String clgType, String scheduleNo, String orgnCode) throws Exception;

    public List<String> updateOutwardClgReturn(List<com.cbs.dto.npci.cts.ow.reverse.FileHeader.Item> items, String userName, String todayDt, String mode) throws Exception;

    public int getInstrumentStatus(String date, String emFlag, int scheduleNo, String txnId, String itemSeqNo) throws Exception;

    public String generateCtsZipReturnXml(List<ReturnDTO> returnData) throws Exception;

    public List<ReturnDTO> getCtsArchivingData(String frdt, String todt, String chequeNo, String fileType) throws Exception;

    public List<ReturnDTO> getNpciManualReturnData(String fileGenDt, String clgType, String scheduleNo, String orgnCode) throws Exception;

    public String generateCtsReturnTxt(List<ReturnDTO> returnData) throws Exception;
    
    public String[] getMICRDetail(String transCode, String serialNo, BillTypeMasterTO billTO, String micrBrCode) throws Exception;
}
