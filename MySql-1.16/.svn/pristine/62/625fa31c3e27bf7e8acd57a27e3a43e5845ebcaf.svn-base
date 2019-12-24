package com.cbs.sms.service;

import com.cbs.dao.exception.DAOException;
import com.cbs.exception.ApplicationException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * @author
 *
 */
public class VelocityEngineService {

    private static final Logger logger = Logger.getLogger(VelocityEngineService.class);
    private static VelocityEngineService engineService;
    private static VelocityEngine engine;

    /**
     * @throws DAOException
     *
     */
    private VelocityEngineService() throws Exception {
        // setting common properties for configuring velocity engine.
        Properties props = new Properties();
        /*
         * COMMENTS ajayhada
         * Following properties will load velocity temples from file system not from classpath
         * props.setProperty(RuntimeConstants.RESOURCE_LOADER, "file");
         * props.setProperty("file.resource.loader.class","org.apache.velocity.runtime.resource.loader.FileResourceLoader");
         * //props.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH , file.getAbsolutePath());
         * \
         * */
        props.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_CACHE, "true");
        props.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        props.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        if (logger.isDebugEnabled()) {
            logger.debug("VelocityEngineService() - Initialzing Velocity Engine....");
        }
        logger.debug("Initialzing Velocity Engine....");
        engine = new VelocityEngine(props);
        engine.init();
        return;

    }

    /**
     * @return @throws ApplicationException
     */
    public static VelocityEngineService getInstance() throws ApplicationException {
        try {
            engineService = new VelocityEngineService();
        } catch (Exception e) {
            logger.debug("Velocity Engine Initialization Failed....", e);
            throw new ApplicationException("Fail to instenciate Velocity Engine for news and mail service!!", e);
        }
        return engineService;
    }

    /**
     * @return @throws DAOException
     */
    public VelocityEngine getVelocityEngine() {
        return engine;
    }
}
