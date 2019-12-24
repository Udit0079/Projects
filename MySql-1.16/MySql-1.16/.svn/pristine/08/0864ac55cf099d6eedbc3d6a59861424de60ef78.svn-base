/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.listener;

import com.cbs.exception.ApplicationException;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.facade.admin.AdminManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

/**
 * Web application Session time out listener.
 * @author Dhirendra Singh
 */
public class CbsSessionTracker implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("Session Created : " + se.getSession().getId());
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        System.out.println("Session destroyed :" + session.getId() + " Logging out user...");
        logoutActiveUser(se.getSession());
    }

    /**
     * Gets the logged in user data from SecurityContext and makes necessary logout operations. 
     */
    public void logoutActiveUser(HttpSession httpSession) {
        SecurityContext context = (SecurityContext) httpSession.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        if (context != null) {
            Authentication authentication = context.getAuthentication();
            System.out.println("User Name = " + authentication.getName());
            try {
                AdminManagementFacadeRemote beanRemote = (AdminManagementFacadeRemote) ServiceLocator.getInstance().lookup("AdminManagementFacade");
                beanRemote.logOut(authentication.getName());
            } catch (ServiceLocatorException ex) {
                Logger.getLogger(CbsSessionTracker.class.getName()).log(Level.ERROR, null, ex);
            } catch (ApplicationException ex) {
                Logger.getLogger(CbsSessionTracker.class.getName()).log(Level.ERROR, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(CbsSessionTracker.class.getName()).log(Level.ERROR, null, ex);
            }
        } else {
            System.out.println("SecuityContext doesn't exist, no need to call logout service");
        }
    }
}
