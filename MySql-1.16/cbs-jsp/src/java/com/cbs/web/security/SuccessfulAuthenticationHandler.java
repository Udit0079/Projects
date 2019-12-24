/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.security;

import com.cbs.exception.ApplicationException;
import com.cbs.exception.ServiceLocatorException;
import com.cbs.facade.admin.AdminManagementFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.hrms.web.utils.WebUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 *
 * @author root
 */
//public class SuccessfulAuthenticationHandler extends SavedRequestAwareAuthenticationSuccessHandler {
public class SuccessfulAuthenticationHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    AdminManagementFacadeRemote beanRemote;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
            handle(request, response, authentication);
       // super.onAuthenticationSuccess(request, response, authentication);
    }

    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        try {

            beanRemote = (AdminManagementFacadeRemote) ServiceLocator.getInstance().lookup("AdminManagementFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            //beanRemote.updateLoginTime(authentication.getName());
            List<String> versionList = beanRemote.getCbsVersionNo(authentication.getName());
            request.getSession().setAttribute("verNos", versionList);
            
            String formName = beanRemote.getBiometricParameters(authentication.getName());
            
            request.getSession().setAttribute("userid", authentication.getName());
            
            request.getSession().setAttribute("usrbr", Init.IP2BR.getBranch(InetAddress.getByName(WebUtil.getClientIP(request))));
            request.getSession().setAttribute("maxlen",ftsPostRemote.getCodeByReportName("ACNO-LENGTH"));
            String targetUrl = "";
            System.out.println(new Date() + ": The User " + authentication.getName() + " is logged in from IP Address " + WebUtil.getClientIP(request));
            if (formName.equals("VERIFICATION")) {
                targetUrl = "/faces/pages/admin/biometricVerification.jsp";
            } 
            else if (formName.equals("REGISTRATION")) {
                request.getSession().setAttribute("RegMode","self");
                targetUrl = "/faces/pages/admin/biometricReg.jsp";
            }
            else{
                 targetUrl = "/faces/pages/admin/Welcome.jsp";
            }
            
            
        if (response.isCommitted()) {
            System.out.println("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
        } catch (ServiceLocatorException ex) {
            throw new IOException(ex.getRootCause());
        } 
        catch (ApplicationException ex) {
            throw new IOException(ex.getMessage());
        }
        
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
}
