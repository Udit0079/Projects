/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ho;

import com.cbs.dto.report.FdMaturityPojo;
import com.cbs.exception.ApplicationException;
import javax.ejb.Remote;
import java.util.List;

/**
 *
 * @author root
 */
@Remote
public interface HoReportBeanRemote {
    
    public List<FdMaturityPojo> getFdMaturityData(String brncode,double amt, int fyear, int toyear, String asonDt)throws ApplicationException;
    
}
