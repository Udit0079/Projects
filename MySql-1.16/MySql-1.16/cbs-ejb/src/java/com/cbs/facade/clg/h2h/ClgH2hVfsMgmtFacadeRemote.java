/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.clg.h2h;

import javax.ejb.Remote;

@Remote
public interface ClgH2hVfsMgmtFacadeRemote {

    public void processUploadCts();
}
