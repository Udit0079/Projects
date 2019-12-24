/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ho.master;

import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Deepshikha
 */
@Remote
public interface HoCircularDetailsRemote {
    public List selectRecord() throws com.cbs.exception.ApplicationException;
    public String saveData(String cirNo,String cirDetails) throws ApplicationException ;
}
