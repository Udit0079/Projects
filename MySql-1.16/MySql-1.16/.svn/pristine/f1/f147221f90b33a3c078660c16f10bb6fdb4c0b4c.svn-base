/**
 *
 */
package com.hrms.utils;

import com.hrms.exception.ServiceLocatorException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

/**
 * FIXME please implement proper pooling logic.
 *
 * @author Dhirendra Singh
 *
 */
public class HrServiceLocator {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(HrServiceLocator.class);
    private InitialContext context;
    private Map<String, Object> cache;
    private static HrServiceLocator me;
    private static String LOOKUP_STR = "java:global/cbs/hr-ejb/";

    /**
     * @throws ServiceLocatorException
     */
    private HrServiceLocator() throws ServiceLocatorException {

        try {
            context = new InitialContext();
            cache = Collections.synchronizedMap(new HashMap<String, Object>());
        } catch (NamingException ne) {
            logger.error("Exception occured while executing method ServiceLocator()", ne);
            throw new ServiceLocatorException(ne);
        } catch (Exception e) {
            logger.error("Exception occured while executing method ServiceLocator()", e);
            throw new ServiceLocatorException(e);
        }
    }

    /**
     * @return @throws ServiceLocatorException
     */
    static public HrServiceLocator getInstance() throws ServiceLocatorException {
        try {
            if (HrServiceLocator.me == null) {
                HrServiceLocator.me = new HrServiceLocator();
                return HrServiceLocator.me;
            } else {
                return HrServiceLocator.me;
            }
        } catch (ServiceLocatorException e) {
            logger.error(
                    "Exception occured while executing method ServiceLocator()", e);
            throw new ServiceLocatorException(e);
        }

    }

    /**
     * @return the corresponding object to the jndiHomeName
     */
    public Object lookup(String jndiHomeName) throws ServiceLocatorException {
        Object remote = null;
        try {
            if (cache.containsKey(LOOKUP_STR + jndiHomeName)) {
                remote = cache.get(LOOKUP_STR + jndiHomeName);
            } else {
                remote = context.lookup(LOOKUP_STR + jndiHomeName);
                cache.put(LOOKUP_STR + jndiHomeName, remote);
            }
        } catch (NamingException ne) {
            logger.error("Exception occured while executing method lookup(jndiHomeName)", ne);
            throw new ServiceLocatorException(ne);
        } catch (Exception e) {
            logger.error("Exception occured while executing method lookup(jndiHomeName)", e);
            throw new ServiceLocatorException(e);
        }
        return remote;
    }
}
