/**
 * 
 */
package com.cbs.utils;

import com.cbs.exception.ServiceLocatorException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

/**
 * FIXME please implement proper pooling logic.
 * @author Dhirendra Singh
 * 
 */

public class ServiceLocator {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(ServiceLocator.class);
    private InitialContext context;
    private Map<String, Object> cache;
    private static ServiceLocator me;
    private static String LOOKUP_STR = "java:global/cbs/cbs-ejb/";

    /**
     * @throws ServiceLocatorException
     */
    private ServiceLocator() throws ServiceLocatorException {

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
     * @return
     * @throws ServiceLocatorException 
     */
    static public ServiceLocator getInstance() throws ServiceLocatorException {
        try {
            if (ServiceLocator.me == null) {
                ServiceLocator.me = new ServiceLocator();
                return ServiceLocator.me;
            } else {
                return ServiceLocator.me;
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
