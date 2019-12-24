/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Sudhir
 */
@Remote
public interface StandingInstructionManagementRemote {

    List accNoPass(String acno, int listIndex) throws ApplicationException;

    List loadGridPending(String BRCODE) throws ApplicationException;

    String postButton(String user, String BRCODE) throws ApplicationException;

    public String getCustomerName(String acno) throws ApplicationException;

    List tableData(String insType, String brCode) throws ApplicationException;

    String postButton(String user, String BRCODE, String insType, List generalGrid) throws ApplicationException;

    List loadGrdData(String acno, String instype) throws ApplicationException;

    String acnoFind(String acno, String instype) throws ApplicationException;

    String deleteTransData(String fromAcno, int orgbrCode, float instrNo, String authby) throws ApplicationException;

    String deleteTransData1(String fromAcno, int orgbrCode, float instrNo, String authby, int sno) throws ApplicationException;

    String deleteGenData(float instrno, String dt, String user, int orgbrCode) throws ApplicationException;
    
    //String siAutoCoverPost(String dt) throws ApplicationException;   
}
