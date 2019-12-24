/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.bill;

import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author admin
 */
@Local
public interface InstrumentFacadeRemote {

    public String instrumentDeletionInstrumentActive(int instrumentNo, int srNo, String dt, String authBy, String billType) throws ApplicationException;

    public List getDropDownValuesInstrumentActive() throws ApplicationException;

    public List tableDataInstrumentActive(String billType, String instNo) throws ApplicationException;

    public String instrumentActivate(String instNo, int srNo, String authBy, String billType) throws ApplicationException;

    public List branchCodeDropDown() throws ApplicationException;

    public List billtypeDropDown() throws ApplicationException;

    public String tmpbdInstrumentLost() throws ApplicationException;

    public List tableDataInstrumentLost(String billType, String status, String orgBrnCode) throws ApplicationException;

    public String instLostDeletion(String instNo, int srNo, String authBy, String billType) throws ApplicationException;

    public String instLostSaveUpdate(String command, String billType, String instNo, float seqNo, float amt, String infavourof, 
            String issueBrCode, String reportBrCode, String draweeBrCode, String dtOrigin, String dtLoss, String circular, String authBy, 
            int srNo, String dt, String dtCircular, String orgnBrCode) throws ApplicationException;
    
    public List getInstDetails(String billType, String instNo, String originDt, String issueBr) throws ApplicationException;
    
    public String instVerified(String instrumentNo, int srNo, String authBy, String billType) throws ApplicationException;
}
