/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cbs.web.security;

import com.cbs.facade.admin.AdminManagementFacadeRemote;
import com.cbs.utils.ServiceLocator;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 *
 * @author root
 */
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws ServletException, IOException {
         try {
            AdminManagementFacadeRemote beanRemote = (AdminManagementFacadeRemote) ServiceLocator.getInstance().lookup("AdminManagementFacade");
           if (exception instanceof CredentialsExpiredException) {
                setDefaultFailureUrl("/faces/ChgPassword.jsp");
            } else if (exception instanceof BadCredentialsException && request.getSession().getAttribute("userid") != null) {
                try {
                    beanRemote.updateFailedLoginStatus(request.getSession().getAttribute("userid").toString());
                } catch (Exception ex) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: " + ex.getMessage());
                }
                setDefaultFailureUrl("/login.jsp?error=1");
            }else{
                setDefaultFailureUrl("/login.jsp?error=1");
            }
         }catch(Exception ex){
             throw new IOException(ex.getMessage());
         }
        super.onAuthenticationFailure(request, response, exception);
    }
    
}
