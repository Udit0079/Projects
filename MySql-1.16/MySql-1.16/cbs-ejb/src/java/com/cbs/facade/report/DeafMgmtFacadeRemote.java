/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.exception.ApplicationException;
import com.cbs.pojo.deaf.DeafForm1Pojo;
import com.cbs.pojo.deaf.DeafMarkPojo;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface DeafMgmtFacadeRemote {

    public List<DeafForm1Pojo> getForm1Data(String inputBr, String inputfrDt, String inputToDt, String repType, String prevEndDt) throws ApplicationException;

    public List<DeafMarkPojo> getDeafInfoData(String inputBr, String acNature, String inputAcType, String frDt, String toDt) throws ApplicationException;

    public List<DeafMarkPojo> getUnAcnoInfoData(String branch, String acNature, String dt) throws ApplicationException;

    public String setTelephoneNo(String brCode) throws ApplicationException;
}
