/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.misc;

import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface CustomerDetailsReportFacadeRemote {

    String accNoPass(String accno, String acType, String date) throws ApplicationException;

    public List securityDetails(String acno) throws ApplicationException;

    public List otherAcnoDetails(String acno) throws ApplicationException;

    public List jtDetails(String accno1, String orgnBrCode) throws ApplicationException;
    
    public List proprietorDetails(String acno)throws ApplicationException;

    public List misDetails(String acno) throws ApplicationException;

    public List getCustDetails(String acno) throws ApplicationException;

    public List getChequeFacility(String acno) throws ApplicationException;

    public String getDateOfBirth(String accno) throws ApplicationException;
}
