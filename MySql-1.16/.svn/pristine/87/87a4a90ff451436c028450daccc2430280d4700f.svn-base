/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.clg;

import com.cbs.dto.report.CtsChequeStatusReportPojo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface CtsReportManagementFacadeRemote {

    public List<CtsChequeStatusReportPojo> getCtsChequeStatusReportDetails(String destCode, String reportDt, int status, String clgType, int ctsSponsor) throws ApplicationException;

    public List<String> getBankDetails(String orgCode) throws ApplicationException;
}
