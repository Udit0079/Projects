/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrms.facade;

import java.util.List;
import javax.ejb.Remote;
import com.hrms.common.exception.ApplicationException;
import com.hrms.common.to.HrMstStructTO;
import com.hrms.common.to.ParametersTO;

/**
 *
 * @author Zeeshan Waris
 */
@Remote
public interface ParametersFacadeRemote {

    public List<ParametersTO> getParameterList() throws ApplicationException;

    public List<HrMstStructTO> parametertDescriptionList(int compCode, String code) throws ApplicationException;

    public String parameterSave(HrMstStructTO hrParamObj) throws ApplicationException;

    public String parameterUpdate(HrMstStructTO hrParamObj) throws ApplicationException;

    public String deleteParameterAction(int compCode, String structcode) throws ApplicationException;
}
