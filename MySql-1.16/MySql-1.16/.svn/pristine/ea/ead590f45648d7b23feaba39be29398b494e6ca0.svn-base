/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.facade.ho;

import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface EvcAccountValidationFacadeRemote {
    
    /**
     * 
     * @param evcUniqueReqId
     * @param evcJsonReq
     * @return
     * @throws ApplicationException 
     */
    public String logEvcAccountValidationReq(String evcUniqueReqId, String evcJsonReq) throws ApplicationException;
    
    /**
     * 
     * @param evcUniqueReqId
     * @param evcJsonRes
     * @return
     * @throws ApplicationException 
     */
    public String updateEvcAccountValidationRes(String evcUniqueReqId, String evcJsonRes) throws ApplicationException;
    
    /**
     * 
     * @param pan
     * @param acno
     * @param ifsc
     * @return
     * @throws ApplicationException 
     */
    public List evcAccountValidate(String pan, String acno, String ifsc) throws ApplicationException;
}
