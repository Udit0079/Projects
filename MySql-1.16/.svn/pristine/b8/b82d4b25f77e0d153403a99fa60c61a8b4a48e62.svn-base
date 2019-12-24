/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.sms.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author root
 */
public class PropertyContainer {

    private static Properties properties;

    public static synchronized Properties getProperties() {
        try {
            if (properties != null) {
                return properties;
            }
            properties = new Properties();
            InputStream incoming = new FileInputStream(new File("/opt/conf/sms.properties"));
            properties.load(incoming);
            incoming.close();
        } catch (Exception ex) {
            System.out.println("Sms properties file was not initialized");
        }
        return properties;
    }
}
