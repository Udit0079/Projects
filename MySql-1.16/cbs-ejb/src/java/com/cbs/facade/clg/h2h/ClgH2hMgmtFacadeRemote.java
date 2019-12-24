/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.clg.h2h;

import com.cbs.exception.ApplicationException;
import javax.ejb.Remote;

@Remote
public interface ClgH2hMgmtFacadeRemote {

    public void processUploadCts();
    
    public String updateModuleConfigProperty(String key, String value) throws ApplicationException;
}
