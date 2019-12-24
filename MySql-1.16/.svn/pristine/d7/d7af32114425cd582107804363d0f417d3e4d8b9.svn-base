/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ho.master;

import javax.ejb.Remote;


/**
 * @author Deepshikha
 */
@Remote
public interface ExpenditureLimitFacadeRemote {
    public java.util.List loadBranchNames();
    public String saveData(String userDate,String branchName,String amount) throws com.cbs.exception.ApplicationException;
    public String updateData(String userDate,String brncode,String amount) throws com.cbs.exception.ApplicationException;
    public double getLimitDetails(java.lang.String brncode);
}
