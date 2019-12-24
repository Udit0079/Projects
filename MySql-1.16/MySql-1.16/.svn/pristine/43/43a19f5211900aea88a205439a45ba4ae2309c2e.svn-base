/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.misc;

import com.cbs.dto.misc.ATMReconsilationDTO;
import com.cbs.exception.ApplicationException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

@Remote
public interface ATMMgmtFacadeRemote {

    public Map<String, String> getCbsChargeDetail(String chargeType, String effectiveDt) throws Exception;

    public List<ATMReconsilationDTO> getAllTxnsToReconsile(String txnDt) throws Exception;

    public String postATMReconsilationData(List<ATMReconsilationDTO> gridDetail, String txnDt, String todayDt, 
            String userName) throws Exception;
    
    public String refundFileProcessing(InputStream is, String userName, String orgBrCode, String fileName) throws ApplicationException;
}
