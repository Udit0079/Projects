/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.report.ArfBrcPojo;
import com.cbs.dto.report.CtrArfPojo;
import com.cbs.dto.report.CtrPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.AirPojo;
import com.cbs.pojo.Form61APojo;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface CtrStrAirReportFacadeRemote {

    public CtrPojo ctrReport(List acNatureList, String acType, String fromAmt, String toAmt, String fromDt, String toDt, String brCode, String repType) throws ApplicationException;

    public CtrArfPojo arfReport(List acNatureList, String acType, String fromAmt, String toAmt, String fromDt, String toDt, String brCode, String repType, String userName) throws ApplicationException;

    public List<AirPojo> airReport(String acNature, String fromAmt, String toAmt, String fromDt, String toDt, String brCode, String repType, String userName) throws ApplicationException;

    public List<Form61APojo> form61AReport(String acNature, String fromAmt, String toAmt, String fromDt, String toDt, String brCode, String repType, String userName) throws ApplicationException;

    public CtrArfPojo arfReportStr(List acNatureList, String acType, String fromAmt, String toAmt, String fromDt, String toDt, String brCode, String repType, String userName, String multiplier) throws ApplicationException;

    public List<ArfBrcPojo> getArfBrcData(String brCode, String repType) throws ApplicationException;

//    public List<Form61APojo> form61AForSBAndCAReportData(String acNature, String fromAmt, String toAmt, String fromDt, String toDt, String brCode, String repType, String userName) throws ApplicationException;
//
//    public List<Form61APojo> form61AForTDAndPODDReportData(String acNature, String fromAmt, String toAmt, String fromDt, String toDt, String brCode, String repType, String userName) throws ApplicationException;
}
