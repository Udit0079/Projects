package com.hrms.web.utils;

import com.cbs.exception.ApplicationException;
import com.hrms.common.to.CompanyMasterTO;
import com.hrms.common.to.PayrollCalendarTO;
import com.hrms.utils.HrServiceLocator;
import com.hrms.common.utils.ServiceLocatorException;
import com.hrms.facade.DefinitionFacadeRemote;

import com.hrms.facade.payroll.PayrollMasterFacadeRemote;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class WebUtil {

    private static final Logger logger = Logger.getLogger(WebUtil.class);

    public WebUtil() {
        try {
        } catch (Exception e) {
        }

    }

    public static List<String> getFinancialYear(int compCode) throws ServiceLocatorException {
        SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
        List<String> list = new ArrayList<String>();
        try {
            Object lookup = HrServiceLocator.getInstance().lookup("DefinitionFacade");
            DefinitionFacadeRemote definitionFacadeRemote = (DefinitionFacadeRemote) lookup;
            List<PayrollCalendarTO> payrollCalendarTOs = definitionFacadeRemote.getFinYear(compCode);
            if (!payrollCalendarTOs.isEmpty()) {
                list.add(dmyFormat.format(payrollCalendarTOs.get(0).getPayrollCalendarPKTO().getDateFrom()));
                list.add(dmyFormat.format(payrollCalendarTOs.get(0).getPayrollCalendarPKTO().getDateTo()));
            } else {
                list.add("01/01/1900");
                list.add("01/01/1901");
            }
        } catch (Exception e) {
            logger.error("Exception occured while executing method busValidation()", e);
        }
        return list;
    }

    public List<CompanyMasterTO> getCompanyMasterTO(int compCode) throws ServiceLocatorException {
        try {
            Object lookup = HrServiceLocator.getInstance().lookup("PayrollMasterFacade");
            PayrollMasterFacadeRemote payrollMasterFacadeRemote = (PayrollMasterFacadeRemote) lookup;
            List<CompanyMasterTO> companyMasterTOs = payrollMasterFacadeRemote.getCompanyMasterTO(compCode);
            return companyMasterTOs;
        } catch (Exception e) {
            logger.error("Exception occured while executing method busValidation()", e);
        }
        return null;
    }
    
    public static String getClientIP(HttpServletRequest req)throws ApplicationException{
        return req.getHeader("X-FORWARDED-FOR") == null? req.getRemoteAddr():req.getHeader("X-FORWARDED-FOR");
    }
}
