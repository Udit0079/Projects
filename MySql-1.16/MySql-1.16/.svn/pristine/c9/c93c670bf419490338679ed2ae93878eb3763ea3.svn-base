/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.other.MinimumBalSmsTo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface OtherMiscReportFacadeRemote {

    public List<MinimumBalSmsTo> getMinimumBalSmsReport(String brncode, String frDt, String toDt) throws ApplicationException;
}
