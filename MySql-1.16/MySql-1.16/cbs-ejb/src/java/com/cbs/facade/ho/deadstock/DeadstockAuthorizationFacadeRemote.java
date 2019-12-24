package com.cbs.facade.ho.deadstock;

import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface DeadstockAuthorizationFacadeRemote {

    public List viewDataInGrid() throws ApplicationException;

    public String authorizeRecords(int trSNo, String AuthBy, String dt, String orgnBrCode, String itemName) throws ApplicationException;
}
