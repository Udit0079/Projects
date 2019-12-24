package com.cbs.facade.ho;

import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Navneet Goyal
 */
@Remote
public interface BatchDeletionRemote {

    public java.util.List getBatchNoForDeletion() throws ApplicationException;

    public List getBatchData(int batchNo, String tempBd) throws ApplicationException;

    public java.lang.String getBalanceForAcNature(java.lang.String accountNo) throws ApplicationException;

    public java.lang.String hoGlbInsert(java.lang.String[] array, java.lang.String userName) throws com.cbs.exception.ApplicationException;
}
