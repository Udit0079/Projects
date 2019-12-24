/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.neftrtgs;

import com.cbs.dto.NeftRtgsMismatchPojo;
import com.cbs.entity.neftrtgs.CbsAutoNeftDetails;
import com.cbs.entity.neftrtgs.NeftRtgsStatus;
import com.cbs.entity.sms.MbSmsSenderBankDetail;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.neftrtgs.ExcelReaderPojo;
import com.cbs.pojo.neftrtgs.HDFCInwardNEFTReturnPojo;
import com.cbs.pojo.neftrtgs.NeftRtgsReportPojo;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface UploadNeftRtgsMgmtFacadeRemote {

    public String neftRtgsUploadProcess(List<ExcelReaderPojo> inputList, String orgBrCode,
            String user, String iwHead, String process, String neftBankName) throws ApplicationException;

    public List<NeftRtgsReportPojo> getNeftRtgsReportStatus(Date frDt, Date toDt, String status,
            String orgncode, String processType, String neftbank) throws ApplicationException;

    public List<NeftRtgsStatus> getMismatchData(Date currentDt, String status, String processType,
            String neftBankName) throws ApplicationException;

    public String processMismatchData(List<NeftRtgsMismatchPojo> processList, String pStatus,
            String orgBrCode, String user, String status, String processType,
            String neftBankName) throws ApplicationException;

    public List<MbSmsSenderBankDetail> getBankCode() throws ApplicationException;

    public String csvUploadProcess(List<ExcelReaderPojo> inputList, String orgBrCode,
            String user) throws ApplicationException;

    public List<CbsAutoNeftDetails> getAutoNeftDetailsByProcess(String process) throws ApplicationException;

    public List<CbsAutoNeftDetails> getNeftMisMatchBankName(String process) throws ApplicationException;

    public CbsAutoNeftDetails getNeftDetailsByNefBankNameAndProcess(String neftBankName,
            String process) throws ApplicationException;

    public List getAccountDetails(String acNoList) throws ApplicationException;

    public List<HDFCInwardNEFTReturnPojo> getHDFCInwardNEFTReturn(String toDt) throws ApplicationException;

    public String getAxisInwHead(String process, String neftBankName) throws Exception;

    public List<NeftRtgsStatus> getUnSuccessEntity(String process, String neftBankName) throws ApplicationException;

    public String generateIblIwReturnFiles(List<NeftRtgsMismatchPojo> processList, String leafStatus) throws Exception;
}
