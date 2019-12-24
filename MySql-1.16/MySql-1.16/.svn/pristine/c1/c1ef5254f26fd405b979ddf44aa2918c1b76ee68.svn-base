/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.npci.h2h;

import com.cbs.exception.ApplicationException;
import java.io.IOException;
import javax.ejb.Remote;

@Remote
public interface H2HNpciMgmtFacadeRemote {

    public void npciH2HProcess();

    public void writeEncryptedFiles() throws Exception;

    public void upload(String hostname, String username, String password, String localFileDir, String remoteFileDir) throws Exception;

    public void createBackupAndThenRemoveFile(String localIwFileDir, String localIwBackupDir) throws IOException;
    
    public String updateModuleConfigProperty(String key, String value) throws ApplicationException;
}
