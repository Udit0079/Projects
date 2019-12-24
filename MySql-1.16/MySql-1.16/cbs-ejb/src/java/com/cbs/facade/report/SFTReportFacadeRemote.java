/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.exception.ApplicationException;
import com.cbs.pojo.Form61APojo;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface SFTReportFacadeRemote {

    public List<Form61APojo> form61AForSBAndCAReportData(String acNature, String fromAmt, String toAmt, String fromDt, String toDt, String brCode, String repType, String userName) throws ApplicationException;

    public List<Form61APojo> form61AForTDAndPODDReportData(String acNature, String fromAmt, String toAmt, String fromDt, String toDt, String brCode, String repType, String userName) throws ApplicationException;
}
