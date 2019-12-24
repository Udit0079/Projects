/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.misc;

import com.cbs.dao.master.PrizmMasterPojo;
import com.cbs.dto.NpciFileDto;
import com.cbs.exception.ApplicationException;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface TransferBatchDeletionFacadeRemote {

    public List batchDetailGridLoad(String date, Float batchNo, String brCode) throws ApplicationException;

    public String deleteBatchNoProc(String date, Float batchNo, String brCode, String user) throws ApplicationException;

    public String updateDetailOfBatch(float batchNo, String acno, float recNo, String date,
            String oldDetail, String newDetail, String enterBy, String brCode) throws ApplicationException;

    public List<PrizmMasterPojo> retrieveUnAuthData(String orgnCode, String todayDate) throws ApplicationException;

    public String verifyPrizmAtmRegistration(String function, String acno, String userName) throws ApplicationException;

    public List getPrizmAtmRegisterdAcToModify(String acno) throws ApplicationException;

    public String addAcToPrizmAtm(String acno, BigDecimal minLimit, String cbsStatus,
            String userName, String flag) throws ApplicationException;

    public String expLostStolenAndCloseProcess(String acno, String cbsStatus,
            String userName, String cardNo, String expDt) throws ApplicationException;

    public String modifyProcess(String cbsStatus, String acno, String cardNo, String expDt,
            BigDecimal minLimit, String userName) throws ApplicationException;

    public String generatePrizmCardInputFile(String userName, String todayDate,
            String orgnCode) throws ApplicationException;

    public List<NpciFileDto> showGeneratedFiles(String fileShowDt, String orgnCode) throws ApplicationException;

    public boolean checkPrizmRegisteredAc(String acno) throws ApplicationException;

    public List getPrizmAcDetails(String acno, String fromDt, String toDt,
            String flag, String brncode) throws ApplicationException;
}
