/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.neftrtgs;

import com.cbs.entity.neftrtgs.CbsAutoNeftDetails;
import com.cbs.entity.neftrtgs.NeftOwDetails;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.neftrtgs.ExcelReaderPojo;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface H2HMgmtFacadeRemote {

    public void eftProcess() throws ApplicationException;

    public boolean isH2HModuleOn() throws ApplicationException;

    public String finalIblReturn(List<ExcelReaderPojo> fileData, CbsAutoNeftDetails neftAutoObj) throws Exception;

    public String[] idbiReturnDescription(String cbsReason) throws Exception;
    
    public String outwardNeftRtgsReversalProcessing(List<ExcelReaderPojo> pojoList, CbsAutoNeftDetails autoObj,
            String orgnBrCode, String userName) throws Exception;
    
    public String reverseOutwardTransaction(NeftOwDetails obj, String brncode, String user,
            CbsAutoNeftDetails neftAutoObj) throws ApplicationException;
}
