/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.security;

import javax.servlet.http.HttpServletRequest;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author root
 */
public class PostAuthenticationValidator implements UserDetailsChecker{
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    
    public void check(UserDetails user) {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        if (!user.isCredentialsNonExpired()) {
            req.getSession().setAttribute("userid", user.getUsername());
            throw new CredentialsExpiredException("Change Password", user);
        }
//        ServletWebRequest servletWebRequest=new ServletWebRequest(req);
//        HttpServletResponse response=servletWebRequest.getResponse();
//        response.sendRedirect("/login");
    }
}
