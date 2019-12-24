/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ho;

import com.cbs.exception.ApplicationException;
import javax.ejb.Remote;

/**
 *
 * @author Sudhir Kr Bisht
 */
@Remote
public interface HoExtractEntryRemote {

    public String save(String originBranch, String respondingBranch, String date, String originDate, String respondDate, String instno, 
            Float amount, Integer ty, String enterBy, String authBy, String details, String entryType) throws ApplicationException;
}
